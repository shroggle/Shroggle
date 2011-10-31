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
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.international.International;
import com.shroggle.util.international.InternationalStorage;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeContext;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.SynchronizeRequest;
import com.shroggle.util.process.synchronize.SynchronizeRequestEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Balakirev Anatoliy
 */
public class RemoveUserRightsUtil {

    public boolean isOneSiteAdmin(final List<Integer> selectedUsersId, final Integer loginedUserId) {
        List<Site> sitesAvailableForLoginedUser = persistance.getSites(loginedUserId, new SiteAccessLevel[]{SiteAccessLevel.ADMINISTRATOR});
        List<Integer> siteAdminsId;
        for (Site site : sitesAvailableForLoginedUser) {
            siteAdminsId = new ArrayList<Integer>();
            siteAdminsId.addAll(getSiteAdminsId(site));
            siteAdminsId.removeAll(selectedUsersId);
            if (siteAdminsId.size() == 0) {
                return true;
            }
        }
        return false;
    }

    private List<Integer> getSiteAdminsId(final Site site) {
        List<Integer> siteAdmins = new ArrayList<Integer>();
        for (UserOnSiteRight right : site.getUserOnSiteRights()) {
            if (right.isActive() && right.getSiteAccessType() == SiteAccessLevel.ADMINISTRATOR) {
                siteAdmins.add(right.getId().getUser().getUserId());
            }
        }
        return siteAdmins;
    }

    public RemovedRightsResponse removeRight(final Integer userId, final boolean removeEmptyUser, final boolean sendMessage) throws Exception {
        final List<RemovedRights> removedUsers = new ArrayList<RemovedRights>();
        final List<RemovedRights> removedRights = new ArrayList<RemovedRights>();
        final SynchronizeRequest synchronizeRequest = new SynchronizeRequestEntity(User.class, SynchronizeMethod.WRITE, userId);
        ServiceLocator.getSynchronize().execute(synchronizeRequest, new SynchronizeContext<Void>() {

            public Void execute() {
                final Integer loginedUserId = new UsersManager().getLogined().getUserId();
                // We must remove userOnSiteRights only for site to which logined user has "ADMINISTRATOR" access. Tolik
                final List<Site> sitesAvailableForLoginedUser =
                        persistance.getSites(loginedUserId, new SiteAccessLevel[]{SiteAccessLevel.ADMINISTRATOR});
                final User user = persistance.getUserById(userId);
                persistanceTransaction.execute(new Runnable() {

                    public void run() {
                        for (final Site site : sitesAvailableForLoginedUser) {
                            final UserOnSiteRight right = persistance.getUserOnSiteRightById(
                                    new UserOnSiteRightId(user, site));
                            if (right != null) {
                                if (sendMessage && !right.isActive()) {
                                    sendMessageAboutRepealedAccess(user, loginedUserId, site.getTitle());
                                }
                                removedRights.add(new RemovedRights(right.getId().getUser().getEmail(), right.getId().getSite().getTitle()));
                                persistance.removeUserOnSiteRight(right);
                                if (removeEmptyUser && (user.getUserOnSiteRights() == null || user.getUserOnSiteRights().size() == 0)) {
                                    removedUsers.add(new RemovedRights(user.getEmail()));
                                    persistance.removeUser(user);
                                }
                            }
                        }
                    }

                });
                return null;
            }

        });
        return new RemovedRightsResponse(removedUsers, removedRights);
    }

    private boolean hasActiveRights(final User user) {
        for (UserOnSiteRight right : user.getUserOnSiteRights()) {
            if (right.isActive()) {
                return true;
            }
        }
        return false;
    }

    void sendMessageAboutRepealedAccess(final User userWithRepealedRight, final Integer loginedUserId, final String siteName) {
        final User loginedUser = persistance.getUserById(loginedUserId);
        final InternationalStorage internationalStorage = ServiceLocator.getInternationStorage();
        final International international = internationalStorage.get("addEditPermissions", Locale.US);
        final String messageBody = international.get(
                "accessRepealedBody",
                StringUtil.getEmptyOrString(userWithRepealedRight.getLastName()),
                StringUtil.getEmptyOrString(userWithRepealedRight.getFirstName()),
                StringUtil.getEmptyOrString(loginedUser.getLastName()),
                StringUtil.getEmptyOrString(loginedUser.getFirstName()),
                siteName,
                UsersManager.getNetworkNameForNetworkUserOrOurAppName(),
                ServiceLocator.getConfigStorage().get().getApplicationUrl(),
                UsersManager.getNetworkEmailForNetworkUserOrOurAppEmail()
        );
        final String messageSubject = international.get("accessRepealedSubject");
        ServiceLocator.getMailSender().send(new Mail(userWithRepealedRight.getEmail(), messageBody, messageSubject));
    }

    public static class RemovedRightsResponse {

        public RemovedRightsResponse() {
            this.removedUsers = new ArrayList<RemovedRights>();
            this.removedRights = new ArrayList<RemovedRights>();
        }

        public RemovedRightsResponse(List<RemovedRights> removedUsers, List<RemovedRights> removedRights) {
            this.removedUsers = removedUsers;
            this.removedRights = removedRights;
        }

        private final List<RemovedRights> removedUsers;
        private final List<RemovedRights> removedRights;

        public List<RemovedRights> getRemovedUsers() {
            return removedUsers;
        }

        public String getRemovedUsersEmails() {
            final StringBuilder emails = new StringBuilder();
            for (RemovedRights removedUser : removedUsers) {
                emails.append(removedUser.getEmail());
                emails.append("<br>");
            }
            return emails.toString();
        }

        public List<RemovedRights> getRemovedRights() {
            return removedRights;
        }

        public void add(final RemovedRightsResponse removedRightsResponse) {
            this.removedUsers.addAll(removedRightsResponse.getRemovedUsers());
            this.removedRights.addAll(removedRightsResponse.getRemovedRights());
        }
    }

    public class RemovedRights {

        public RemovedRights(String email) {
            this(email, null);
        }

        public RemovedRights(String email, String siteTitle) {
            this.email = email;
            this.siteTitle = siteTitle;
        }

        private String email;
        private String siteTitle;

        public String getEmail() {
            return email;
        }

        public String getSiteTitle() {
            return siteTitle;
        }
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
}
