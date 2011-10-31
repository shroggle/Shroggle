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

import com.shroggle.entity.AccessibleForRender;
import com.shroggle.entity.*;
import com.shroggle.logic.user.UsersGroupManager;
import com.shroggle.logic.user.UsersManager;

import java.util.List;
import java.util.Arrays;

/**
 * @author Balakirev Anatoliy
 */
public class AccessibleForRenderManager {

    public AccessibleForRenderManager(AccessibleForRender accessibleForRender) {
        this.accessibleForRender = accessibleForRender;
    }

    public boolean isAccessibleForRender() {
        boolean accessible = true;
        AccessibleForRender parent = accessibleForRender;
        while (parent != null) {
            accessible &= isAccessibleForRender(parent);
            parent = parent.getAccessibleParent();
        }
        return accessible;
    }

    public boolean isOnlyAdminsHasAccess() {
        boolean onlyAdmin = false;
        AccessibleForRender parent = accessibleForRender;
        while (parent != null) {
            onlyAdmin |= (whoHasAccess(parent.getAccessibleSettings()) == WhoHasAccess.ADMINS_ONLY);
            parent = parent.getAccessibleParent();
        }
        return onlyAdmin;
    }

    /*-------------------------------------------------Private methods------------------------------------------------*/
    private boolean isAccessibleForRender(final AccessibleForRender accessibleForRender) {
        if (accessibleForRender == null) {
            throw new IllegalArgumentException("accessibleForRender can`t be null!");
        }

        final AccessibleSettings settings = accessibleForRender.getAccessibleSettings();
        if (settings.getAccess() == AccessForRender.UNLIMITED) {
            return true;
        }

        final User user = new UsersManager().getLoginedUser();
        if (user == null) {
            return false;
        }

        final UserRightManager userRightManager = new UserRightManager(user);
        final UserOnSiteRight userOnSiteRight = userRightManager.getValidUserOnSiteRight(accessibleForRender.getSiteId());

        final boolean hasAdminRights = userOnSiteRight != null && userAccessLevel.contains(userOnSiteRight.getSiteAccessType());
        final boolean hasVisitorRights = userOnSiteRight != null && visitorAccessLevel.contains(userOnSiteRight.getSiteAccessType());

        final UsersGroupManager usersGroupManager = new UsersGroupManager(user);
        switch (whoHasAccess(settings)) {
            case ADMINS_ONLY: {
                return hasAdminRights;
            }
            case ADMINS_AND_ALL_VISITORS: {
                return hasAdminRights || hasVisitorRights;
            }
            case ADMINS_AND_GROUP_VISITORS: {
                return hasAdminRights || (hasVisitorRights && usersGroupManager.hasAccessToOneOfGroups(settings.getVisitorsGroups()));
            }
            case ALL_VISITORS_ONLY: {
                return hasVisitorRights;
            }
            case GROUP_VISITORS: {
                return (hasVisitorRights && usersGroupManager.hasAccessToOneOfGroups(settings.getVisitorsGroups()));
            }
            case NOBODY: {
                return false;
            }
        }
        return false;
    }

    private WhoHasAccess whoHasAccess(final AccessibleSettings accessibleSettings) {
        if (accessibleSettings.isAdministrators() && !accessibleSettings.isVisitors()) {
            return WhoHasAccess.ADMINS_ONLY;
        }
        if (accessibleSettings.isAdministrators() && (accessibleSettings.isVisitors() && accessibleSettings.getVisitorsGroups().isEmpty())) {
            return WhoHasAccess.ADMINS_AND_ALL_VISITORS;
        }
        if (accessibleSettings.isAdministrators() && (accessibleSettings.isVisitors() && !accessibleSettings.getVisitorsGroups().isEmpty())) {
            return WhoHasAccess.ADMINS_AND_GROUP_VISITORS;
        }
        if (!accessibleSettings.isAdministrators() && (accessibleSettings.isVisitors() && accessibleSettings.getVisitorsGroups().isEmpty())) {
            return WhoHasAccess.ALL_VISITORS_ONLY;
        }
        if (!accessibleSettings.isAdministrators() && (accessibleSettings.isVisitors() && !accessibleSettings.getVisitorsGroups().isEmpty())) {
            return WhoHasAccess.GROUP_VISITORS;
        }
        return WhoHasAccess.NOBODY;
    }

    private static enum WhoHasAccess {

        NOBODY,
        ADMINS_ONLY,
        ADMINS_AND_ALL_VISITORS,
        ADMINS_AND_GROUP_VISITORS,
        ALL_VISITORS_ONLY,
        GROUP_VISITORS

    }

    private final AccessibleForRender accessibleForRender;
    private final static List<SiteAccessLevel> userAccessLevel = Arrays.asList(SiteAccessLevel.getUserAccessLevels());
    private final static List<SiteAccessLevel> visitorAccessLevel = Arrays.asList(SiteAccessLevel.getVisitorAccessLevels());

}
