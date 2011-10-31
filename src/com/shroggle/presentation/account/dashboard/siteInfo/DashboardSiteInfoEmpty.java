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

import com.shroggle.presentation.account.dashboard.DashboardSiteType;

import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
public class DashboardSiteInfoEmpty extends DashboardSiteInfo  {

    public DashboardSiteInfoEmpty(DashboardSiteType dashboardSiteType) {
        this.dashboardSiteType = dashboardSiteType;
    }

    private final DashboardSiteType dashboardSiteType;

    @Override
    public DashboardSiteType getSiteType() {
        return dashboardSiteType;
    }

    @Override
    public boolean isSelected(Integer selectedSiteInfoHashCode) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getParentSiteId() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getChildSiteSettingsId() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getCreatedDateAsString() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isSiteCreated() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getLastUpdatedDate() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getUrl() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getLimitedUrl() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isActive() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getSiteId() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean canBeDeactivated() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean hasAdminsRightOnSite() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getFirstSitePageId() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isEditable() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isBlueprint() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isPublishedBlueprint() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isActivatedBlueprint() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean hasBeenPublished() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isNetworkSite() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isChildSite() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getHisNetworkName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<DashboardSiteInfoForCreatedSite.DashboardWidget> getDashboardWidgets() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
