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
package com.shroggle.presentation.account.accessPermissions;

import com.shroggle.entity.*;
import com.shroggle.logic.user.ConfirmCodeGetter;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.site.SiteAccessLevelHolderRequest;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.international.International;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.persistance.Persistance;

import java.util.*;

/**
 * @author Balakirev Anatoliy
 */
public class InviteUserHelper {

    public static void createRightsForExistingUser(final List<SiteAccessLevelHolderRequest> selectedSitesAndAccessLevel,
                                                   final User existingUser, final boolean setActive) {
        createRightsForExistingUser(selectedSitesAndAccessLevel, existingUser, setActive, null, null);
    }

    public static void createRightsForExistingUser(final List<SiteAccessLevelHolderRequest> selectedSitesAndAccessLevel,
                                                   final User existingUser, final boolean setActive,
                                                   final String invitationText, final Integer loginedUserId) {
        createRights(selectedSitesAndAccessLevel, existingUser, setActive, invitationText, loginedUserId);
    }

    public static void createNewUser(final AddEditPermissionsRequest request, final String invitationText,
                                     final boolean setActive, final int loginedUserId) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail().toLowerCase());
        user.setRegistrationDate(new Date());
        ServiceLocator.getPersistance().putUser(user);
        createRights(request.getSelectedSites(), user, setActive, invitationText, loginedUserId);
    }


    public static String createInvitationText(final AddEditPermissionsRequest request) {
        String invitationText = request.getInvitationText();
        invitationText = invitationText.replace("<first name last name>", ((request.getFirstName() != null ? request.getFirstName() : "") + " " + request.getLastName()));
        final String selectedSites = getSelectedSitesAsString(request);
        if (selectedSites.equals("")) {
            invitationText = invitationText.replace("<site names> web site(s).", ServiceLocator.getInternationStorage().get("addEditPermissions", Locale.US).get("noSiteIsSelected"));
        } else {
            invitationText = invitationText.replace("<site names>", selectedSites);
        }
        return invitationText;
    }

    public static boolean isSetNewRightActive(final Integer userId, final Integer loginedUserId) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final List<Site> sitesAvailableForLoginedUser =
                persistance.getSites(loginedUserId, new SiteAccessLevel[]{SiteAccessLevel.ADMINISTRATOR});
        final User user = persistance.getUserById(userId);
        for (final Site site : sitesAvailableForLoginedUser) {
            final UserOnSiteRight right = persistance.getUserOnSiteRightById(
                    new UserOnSiteRightId(user, site));
            if (right != null) {
                return right.isActive();
            }
        }
        return false;
    }

    public static boolean hasAccessToUserSites(final String newUserEmail, final List<SiteAccessLevelHolderRequest> selectedSites) {
        final User existingUser = ServiceLocator.getPersistance().getUserByEmail(newUserEmail);

        if (existingUser == null) {
            return false;
        }

        List<Site> siteListThatInvitedUserHasAccessTo = ServiceLocator.getPersistance().getSites(
                existingUser.getUserId(), SiteAccessLevel.getUserAccessLevels());
        List<Integer> selectedSiteIds = new ArrayList<Integer>();
        for (SiteAccessLevelHolderRequest site : selectedSites) {
            selectedSiteIds.add(site.getSiteId());
        }

        for (Site site : siteListThatInvitedUserHasAccessTo) {
            if (selectedSiteIds.contains(site.getSiteId())) {
                return true;
            }
        }

        return false;
    }

    public static void sendMessageForInvitedUser(final String email, final String invitationText, final Integer loginedUserId) {
        final International international = ServiceLocator.getInternationStorage().get("addEditPermissions", Locale.US);
        final String messageBody = createInvitationMessageBody(email, invitationText, loginedUserId);
        String messageSubject = international.get("invitationMessageSubject");
        ServiceLocator.getMailSender().send(new Mail(email, messageBody, messageSubject));
    }

    public static Set<User> getUsersWithAccessToUserSites(final Integer userId) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final SiteAccessLevel[] userAccessLevels = SiteAccessLevel.getUserAccessLevels();
        final List<Site> availableSites = persistance.getSites(userId, userAccessLevels);
        final Set<User> users = new HashSet<User>();
        for (Site site : availableSites) {
            users.addAll(persistance.getUsersWithRightsToSite(site.getSiteId(), userAccessLevels));
        }
        return users;
    }


    public static String getInvitationTextByUser(final User user, final int loginedUserId) {
        Persistance persistance = ServiceLocator.getPersistance();
        String invitationText = "";
        if (user != null) {
            List<UserOnSiteRight> rights = user.getUserOnSiteRights();
            for (UserOnSiteRight right : rights) {
                if (right.getRequesterUserId() != null && loginedUserId == right.getRequesterUserId()) {
                    return right.getInvitationText();
                }
            }
        }
        final List<Site> sitesAvailableForLoginedUser =
                persistance.getSites(loginedUserId, new SiteAccessLevel[]{SiteAccessLevel.ADMINISTRATOR});
        for (final Site site : sitesAvailableForLoginedUser) {
            final UserOnSiteRight right = persistance.getUserOnSiteRightById(
                    new UserOnSiteRightId(user, site));
            if (right != null && right.getInvitationText() != null) {
                return right.getInvitationText();
            }
        }
        return invitationText;
    }

    public static List<UserOnSiteRight> getInactiveRightsWithParentUserIdByUser(final User user) {
        final List<UserOnSiteRight> oldRights = user != null ? user.getUserOnSiteRights() :
                new ArrayList<UserOnSiteRight>();
        List<UserOnSiteRight> newRights = new ArrayList<UserOnSiteRight>();
        for (final UserOnSiteRight right : oldRights) {
            if (right != null && !right.isActive() && right.getRequesterUserId() != null &&
                    !containRightWithParentUserId(newRights, right.getRequesterUserId())) {
                newRights.add(right);
            }
        }
        return newRights;
    }

    /*-------------------------------------------------Private methods------------------------------------------------*/

    private static String createInvitationMessageBody(final String email, final String invitationText, final Integer loginedUserId) {
        final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
        final International international = ServiceLocator.getInternationStorage().get("addEditPermissions", Locale.US);
        final Persistance persistance = ServiceLocator.getPersistance();
        final User invitedUser = persistance.getUserByEmail(email);
        final User loginedUser = persistance.getUserById(loginedUserId);
        return international.get("invitationMessageBody",
                invitationText, 
                configStorage.get().getApplicationUrl(),
                invitedUser.getUserId(),
                loginedUserId,
                ConfirmCodeGetter.execute(invitedUser, loginedUser),
                UsersManager.getNetworkNameForNetworkUserOrOurAppName(),
                UsersManager.getNetworkEmailForNetworkUserOrOurAppEmail()
        );
    }

    private static String getSelectedSitesAsString(final AddEditPermissionsRequest request) {
        String siteNamesString = "";
        for (SiteAccessLevelHolderRequest siteAccessLevelHolder : request.getSelectedSites()) {
            siteNamesString += ServiceLocator.getPersistance().getSite(siteAccessLevelHolder.getSiteId()).getTitle() + ", ";
        }
        if (siteNamesString.length() > 2) {
            siteNamesString = siteNamesString.substring(0, siteNamesString.length() - 2);
        }
        return siteNamesString;
    }

    // This mehtod should be executed in transaction

    private static void createRights(final List<SiteAccessLevelHolderRequest> selectedSites, final User user, final boolean setActive,
                                     final String invitationText, final Integer loginedUserId) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final Date date = new Date();
        for (final SiteAccessLevelHolderRequest siteAccessLevelHolder : selectedSites) {
            // I don`t use UserRightManager.toSite() method here because it returns only activated rights but we must
            // check all existing ones. Tolik
            final UserOnSiteRight existingUserOnSiteRight = persistance.getUserOnSiteRightByUserAndSiteId(user.getUserId(), siteAccessLevelHolder.getSiteId());
            if (existingUserOnSiteRight != null) {
                //Upgrading existing rights.
                existingUserOnSiteRight.setSiteAccessType(siteAccessLevelHolder.getAccessLevel());
                existingUserOnSiteRight.setActive(setActive);
            } else {
                //Creating new rights.
                final UserOnSiteRight userOnSiteRight = new UserOnSiteRight();

                /**
                 * What is it? We use same date for all user on site right, that allow
                 * find all not active userOnSiteRight with same date and send only one
                 * activation mail.
                 */
                userOnSiteRight.setCreated(date);
                userOnSiteRight.setActive(setActive);
                if (loginedUserId != null) {
                    userOnSiteRight.setInvitationText(invitationText);
                    userOnSiteRight.setRequesterUserId(loginedUserId);
                }
                userOnSiteRight.setSiteAccessType(siteAccessLevelHolder.getAccessLevel());
                final Site site = persistance.getSite(siteAccessLevelHolder.getSiteId());
                site.addUserOnSiteRight(userOnSiteRight);
                user.addUserOnSiteRight(userOnSiteRight);
                persistance.putUserOnSiteRight(userOnSiteRight);
            }
        }
    }

    private static boolean containRightWithParentUserId(final List<UserOnSiteRight> rights, final int parentUserId) {
        for (final UserOnSiteRight right : rights) {
            if (right.getRequesterUserId() != null && parentUserId == right.getRequesterUserId()) {
                return true;
            }
        }
        return false;
    }
    /*-------------------------------------------------Private methods------------------------------------------------*/
}