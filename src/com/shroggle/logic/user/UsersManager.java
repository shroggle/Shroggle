/*********************************************************************
 *                                                                   *
 * Copyright (c) 2007-2011 by Web-Deva.                              *
 * All rights reserved.                                              *
 *                                                                   *
 * This computer program is protected by copyright law and           *
 * international treaties. Unauthorized reproduction or distribution *
 * of this program, or any portion of it, may result in severe civil *
 * and criminal penalties, and will be prosecuted to the maximum     *
 * extent possible under the law.                                    *
 *                                                                   *
 *********************************************************************/
package com.shroggle.logic.user;

import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.entity.UserOnSiteRight;
import com.shroggle.exception.*;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.context.Context;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.SynchronizeContext;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.SynchronizeRequest;
import com.shroggle.util.process.synchronize.SynchronizeRequestEntity;

/**
 * @author Artem Stasuk
 */
public class UsersManager {

    public UserManager login(Integer userId) {
        final User user = persistance.getUserById(userId);
        return login((user != null ? user.getEmail() : null), (user != null ? user.getPassword() : null), null);
    }

    /**
     * This method login user in system store his id in session and check all need
     * security constraint for example activeted, registered and etc. You can use it any where
     * in application. If method finish without throw exception it save logined user id in session.
     *
     * @param email    - user email, it's can be with space in any cases.
     * @param password - user password, must be as in db.
     * @param siteId   - if you want login user for specific site set this parameter. System
     *                 need any SiteAccessLevel for success login.
     * @return - user logic in success in other case method produce exception.
     */
    public UserManager login(final String email, final String password, final Integer siteId) {
        if (password == null) {
            throw new UserNotFoundException("Can't find user " + email + " with null password!");
        }

        final UserManager userManager = new UserManager(email);
        final User user = userManager.getUser();
        if (!password.equals(user.getPassword())) {
            throw new UserWithWrongPasswordException("");
        }

        checkUser(user);

        if (siteId != null) {
            final Site site = persistance.getSite(siteId);
            if (site == null) {
                throw new SiteNotFoundException("Can't find site " + siteId);
            }

            if (userManager.getRight().toSite(site) == null) {
                throw new UserWithoutRightException(email + " doesn't have right to site " + siteId);
            }
        }

        context.setUserId(user.getUserId());
        return userManager;
    }

    public static boolean isLoginedUserAppAdmin() {
        final User user = new UsersManager().getLoginedUser();
        if (user == null) {
            return false;
        }
        for (String adminEmail : ServiceLocator.getConfigStorage().get().getAdminEmails()) {
            if (adminEmail.equals(user.getEmail())) {
                return true;
            }
        }
        return false;
    }

    public static void logout() {
        ServiceLocator.getContextStorage().get().setUserId(null);
    }

    private void checkUser(User user) {
        if (user.getActiveted() == null || user.getRegistrationDate() == null) {
            throw new UserNotActivatedException("Can't login not activated user " + user.getEmail());
        }
    }

    /**
     * @return - user logic object if user id exists in session and user
     *         exist in db and its activated and registered in other cases produce
     *         exception.
     */
    public UserManager getLogined() {

        final Integer userId = context.getUserId();
        if (userId != null) {
            final SynchronizeRequest synchronizeRequest =
                    new SynchronizeRequestEntity(User.class, SynchronizeMethod.READ, userId);
            User user = null;
            try {
                user = ServiceLocator.getSynchronize().execute(synchronizeRequest, new SynchronizeContext<User>() {
                    public User execute() {
                        return ServiceLocator.getPersistance().getUserById(userId);
                    }
                });
            } catch (Exception e) {
            }
            if (user != null) {
                checkUser(user);
                return new UserManager(user);
            }
        }

        throw new UserNotLoginedException("Can't find logined user!");
    }


    /**
     * @return - logined user or null
     */
    public User getLoginedUser() {
        try {
            return getLogined().getUser();
        } catch (Exception exception) {
            return null;
        }
    }

    public boolean isUserLoginedAndHasRightsToSite(final int siteId) {
        try {
            final UserManager userManager = getLogined();
            final Site site = ServiceLocator.getPersistance().getSite(siteId);
            final UserOnSiteRight userOnSiteRight = userManager.getRight().toSite(site);
            return userOnSiteRight != null;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUserLoginedAndRegisteredFromRightForm(final Integer formId) {
        if (formId == null) {
            return false;
        }

        try {
            final User user = getLoginedUser();
            final UserOnSiteRight userOnSiteRight = persistance.getUserOnSiteRightByUserAndFormId(user, formId);
            return UserRightManager.isUserOnSiteRightValid(userOnSiteRight);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isNetworkUser() {
        try {
            return new UsersManager().getLogined().isNetworkUser();
        } catch (Exception e) {
            return false;
        }
    }

    public static String getHisNetworkLogoUrl() {
        try {
            return new UsersManager().getLogined().getNetworkForNetworkUser().getHisNetworkLogoUrl();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getParentSiteUrl() {
        try {
            return new UsersManager().getLogined().getNetworkForNetworkUser().getParentSiteUrl();
        } catch (Exception e) {
            return "#";
        }
    }

    public static String getNetworkNameForNetworkUserOrOurAppName() {
        try {
            return new UsersManager().getLogined().getNetworkForNetworkUser().getNetworkName();
        } catch (Exception e) {
            return ServiceLocator.getConfigStorage().get().getApplicationName();
        }
    }

    public static String getNetworkEmailForNetworkUserOrOurAppEmail() {
        final String ourSupportEmail = ServiceLocator.getConfigStorage().get().getSupportEmail();
        try {
            final String fromEmail = new UsersManager().getLogined().getNetworkForNetworkUser().getChildSiteRegistration().getFromEmail();
            return StringUtil.isNullOrEmpty(fromEmail) ? ourSupportEmail : fromEmail;
        } catch (Exception e) {
            return ourSupportEmail;
        }
    }

    private final Context context = ServiceLocator.getContextStorage().get();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
