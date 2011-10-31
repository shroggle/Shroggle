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
package com.shroggle.presentation.account.dashboard.siteInfo;

import com.shroggle.entity.Site;
import com.shroggle.logic.childSites.childSiteSettings.ChildSiteSettingsManager;
import com.shroggle.presentation.account.dashboard.DashboardSiteType;

import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
public abstract class DashboardSiteInfo {

    public static DashboardSiteInfo newInstance(final Site site, final DashboardSiteType siteType) {
        return new DashboardSiteInfoForCreatedSite(site, siteType);
    }

    public static DashboardSiteInfo newEmptyInstance(final DashboardSiteType siteType) {
        return new DashboardSiteInfoEmpty(siteType);
    }

    public static DashboardSiteInfo newInstance(final ChildSiteSettingsManager manager) {
        if (manager.isChildSiteHasBeenCreated()) {
            return new DashboardSiteInfoForCreatedSite(manager.getChildSite(), DashboardSiteType.CHILD);
        } else {
            return new DashboardSiteInfoForNotCreatedSite(manager);
        }
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + getSiteId();
        result = 31 * result + getChildSiteSettingsId();
        result = 31 * result + getSiteType().hashCode();
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof DashboardSiteInfo) {
            final DashboardSiteInfo dashboardSiteInfo = (DashboardSiteInfo) object;
            return dashboardSiteInfo.getSiteId() == getSiteId() &&
                    dashboardSiteInfo.getChildSiteSettingsId() == getChildSiteSettingsId() &&
                    dashboardSiteInfo.getSiteType() == getSiteType();
        } else {
            return false;
        }
    }

    public abstract boolean isSelected(final Integer selectedSiteInfoHashCode);

    public abstract int getParentSiteId();

    public abstract int getChildSiteSettingsId();

    public abstract String getCreatedDateAsString();

    public abstract boolean isSiteCreated();

    public abstract DashboardSiteType getSiteType();

    public abstract String getName();

    public abstract String getLastUpdatedDate();

    public abstract String getUrl();

    public abstract String getLimitedUrl();

    public abstract boolean isActive();

    public abstract int getSiteId();

    public abstract boolean canBeDeactivated();

    public abstract boolean hasAdminsRightOnSite();

    public abstract int getFirstSitePageId();

    public abstract boolean isEditable();

    public abstract boolean isBlueprint();

    public abstract boolean isPublishedBlueprint();

    public abstract boolean isActivatedBlueprint();

    public abstract boolean hasBeenPublished();

    public abstract boolean isNetworkSite();

    public abstract boolean isChildSite();

    public abstract String getHisNetworkName();

    public abstract List<DashboardSiteInfoForCreatedSite.DashboardWidget> getDashboardWidgets();
}
