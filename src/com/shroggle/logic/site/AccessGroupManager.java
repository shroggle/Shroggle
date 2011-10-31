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
package com.shroggle.logic.site;

import com.shroggle.entity.*;
import com.shroggle.logic.accessibility.UserRightManager;

import java.util.Arrays;

/**
 * @author dmitry.solomadin
 */
public class AccessGroupManager {

    public static boolean isUserFitsForAccessGroup(final AccessGroup accessGroup, final User user, final Site site) {
        if (accessGroup == AccessGroup.ALL) return true;

        if (user == null) {
            return false;
        }

        final UserOnSiteRight userOnSiteRight = new UserRightManager(user).toSite(site);
        if (userOnSiteRight == null || !userOnSiteRight.isActive() ||
                userOnSiteRight.getVisitorStatus() == VisitorStatus.PENDING ||
                userOnSiteRight.getVisitorStatus() == VisitorStatus.EXPIRED) {
            return false;
        } else {
            if (accessGroup == AccessGroup.VISITORS) {
                return true;
            } else if (accessGroup == AccessGroup.GUEST &&
                    (Arrays.asList(SiteAccessLevel.getUserAccessLevels()).contains(userOnSiteRight.getSiteAccessType())
                            || userOnSiteRight.getSiteAccessType() == SiteAccessLevel.GUEST)) {
                return true;
            } else if (accessGroup == AccessGroup.OWNER &&
                    Arrays.asList(SiteAccessLevel.getUserAccessLevels()).contains(userOnSiteRight.getSiteAccessType())) {
                return true;
            }
        }

        return false;
    }

}
