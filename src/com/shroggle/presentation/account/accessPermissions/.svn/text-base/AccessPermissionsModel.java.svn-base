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

import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.User;
import com.shroggle.entity.UserOnSiteRight;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.persistance.Persistance;

import java.util.*;

/**
 * @author Balakirev Anatoliy
 */
public class AccessPermissionsModel {

    public AccessPermissionsModel() {
        final UserManager userManager = new UsersManager().getLogined();
        final List<Site> sitesAvailableForLoginedUser = persistance.getSites(userManager.getUserId(), SiteAccessLevel.getUserAccessLevels());
        final Set<User> users = new TreeSet<User>(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getRegistrationDate().compareTo(o2.getRegistrationDate());
            }
        });
        for (Site site : sitesAvailableForLoginedUser) {
            users.addAll(persistance.getUsersWithRightsToSite(site.getSiteId(), SiteAccessLevel.getUserAccessLevels()));
        }
        for (User user : users) {
            userSites.add(new UserSites(user, sitesAvailableForLoginedUser));
        }
    }


    public int getLoginUserId() {
        return new UsersManager().getLoginedUser().getUserId();
    }

    public int getUsersCount() {
        return userSites.size();
    }

    public RemoveUserRightsUtil.RemovedRightsResponse getRemovedRightsResponse() {
        return removedRightsResponse;
    }

    public List<UserSites> getUserSites() {
        return userSites;
    }

    public class UserSites {

        public UserSites(final User user, final List<Site> sitesAvailableForLoginedUser) {
            this.user = user;
            for (Site site : sitesAvailableForLoginedUser) {
                final UserOnSiteRight right = persistance.getUserOnSiteRightByUserAndSiteId(user.getUserId(), site.getSiteId());
                if (right != null && Arrays.asList(SiteAccessLevel.getUserAccessLevels()).contains(right.getSiteAccessType())) {
                    map.put(site.getTitle(), right);
                }
            }
        }

        public String getUserFirstName() {
            return StringUtil.getEmptyOrString(user.getFirstName());
        }

        public String getUserLastName() {
            return StringUtil.getEmptyOrString(user.getLastName());
        }

        public String getUserEmail() {
            return StringUtil.getEmptyOrString(user.getEmail());
        }

        public int getUserId() {
            return user.getUserId();
        }

        public boolean isUserActivated() {
            return user.getActiveted() != null;
        }

        public List<UserOnSiteRight> getRights() {
            return new ArrayList<UserOnSiteRight>(map.values());
        }

        public List<String> getSiteTitles() {
            return new ArrayList<String>(map.keySet());
        }

        private final User user;
        private final Map<String, UserOnSiteRight> map = new TreeMap<String, UserOnSiteRight>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });
    }

    public void setRemovedRightsResponse(RemoveUserRightsUtil.RemovedRightsResponse removedRightsResponse) {
        this.removedRightsResponse = removedRightsResponse;
    }

    private final List<UserSites> userSites = new ArrayList<UserSites>();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private RemoveUserRightsUtil.RemovedRightsResponse removedRightsResponse;
}
