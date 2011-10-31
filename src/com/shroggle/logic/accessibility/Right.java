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

package com.shroggle.logic.accessibility;

import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

/**
 * This class doesn't check all need security constraints. Use UsersLogin and UserLogic.
 * todo: We should refactor this class. Let's do it slowly and nicely. Method by method.
 *
 * @author Artem, Dima, Tolik
 * @see com.shroggle.logic.user.UserManager
 * @see com.shroggle.logic.user.UsersManager
 */
@Deprecated
public class Right {

    @Deprecated
    public static boolean isAuthorizedUser(final Item item, final Integer userId) {
        if (item == null || item.getSiteId() <= 0) return false;
        final Site site = ServiceLocator.getPersistance().getSite(item.getSiteId());
        return isAlovedByUser(item.getSiteId(), userId) || isAuthorizedUser(site, userId) || isAllowedBySharingRights(item, userId);
    }

    public static boolean isAuthorizedUser(final PageManager page, final Integer userId) {
        return page != null && isAuthorizedUser(page.getSite(), userId);
    }

    private static boolean isAuthorizedUser(final Site site, final Integer userId) {
        if (userId == null) return false;
        if (site == null) return false;

        final User user = ServiceLocator.getPersistance().getUserById(userId);
        if (user == null) return false;

        for (final UserOnSiteRight userOnSiteRight : site.getUserOnSiteRights()) {
            if (userOnSiteRight.getId().getUser() == user) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAlovedByUser(final int siteId, final Integer userId) {
        if (userId == null) return false;
        final Persistance persistance = ServiceLocator.getPersistance();
        final UserOnSiteRight userOnAccountRight = persistance.getUserOnSiteRightById(
                new UserOnSiteRightId(persistance.getUserById(userId), persistance.getSite(siteId)));
        return userOnAccountRight != null && userOnAccountRight.isActive();
    }

    private static boolean isAllowedBySharingRights(final Item item, final Integer userId) {
        if (userId != null) {
            final Persistance persistance = ServiceLocator.getPersistance();
            for (SiteOnItem siteOnItemRight : persistance.getSiteOnItemsByItem(item.getId())) {
                User user = ServiceLocator.getPersistance().getUserById(userId);
                if (user != null) {
                    for (final UserOnSiteRight userOnSiteRight : user.getUserOnSiteRights()) {
                        if (userOnSiteRight.getId().getSite().getSiteId() == siteOnItemRight.getSite().getSiteId()) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

}
