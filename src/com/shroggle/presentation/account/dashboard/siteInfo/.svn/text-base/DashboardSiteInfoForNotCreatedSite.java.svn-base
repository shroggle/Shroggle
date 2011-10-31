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

import com.shroggle.logic.childSites.childSiteSettings.ChildSiteSettingsManager;
import com.shroggle.presentation.account.dashboard.DashboardSiteType;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;

import java.util.List;
import java.util.Locale;

/**
 * @author Balakirev Anatoliy
 */
public class DashboardSiteInfoForNotCreatedSite extends DashboardSiteInfo {

    private final ChildSiteSettingsManager manager;

    DashboardSiteInfoForNotCreatedSite(final ChildSiteSettingsManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean isSelected(Integer selectedSiteInfoHashCode) {
        return selectedSiteInfoHashCode != null && selectedSiteInfoHashCode.equals(hashCode());
    }

    @Override
    public int getParentSiteId() {
        return manager.getParentSite().getId();
    }

    @Override
    public int getChildSiteSettingsId() {
        return manager.getId();
    }

    @Override
    public String getCreatedDateAsString() {
        return DateUtil.toCommonDateStr(manager.getChildSiteSettings().getCreatedDate());
    }

    @Override
    public boolean isSiteCreated() {
        return false;
    }

    @Override
    public DashboardSiteType getSiteType() {
        return DashboardSiteType.CHILD;
    }

    @Override
    public boolean isChildSite() {
        return true;
    }

    @Override
    public boolean isNetworkSite() {
        return false;
    }

    @Override
    public String getName() {
        return ServiceLocator.getInternationStorage().get("siteInfo", Locale.US).get("notCreatedSite", manager.getRegistrantName());
    }

    @Override
    public int getSiteId() {
        return -1;
    }

    @Override
    public boolean isEditable() {
        return true;
    }

    /*--------------------------------------------Not implemented methods---------------------------------------------*/

    @Override
    public String getLastUpdatedDate() {
        throw new UnsupportedOperationException("This method can`t be implemented in this class!");
    }

    @Override
    public String getUrl() {
        throw new UnsupportedOperationException("This method can`t be implemented in this class!");
    }

    @Override
    public String getLimitedUrl() {
        throw new UnsupportedOperationException("This method can`t be implemented in this class!");
    }

    @Override
    public boolean isActive() {
        throw new UnsupportedOperationException("This method can`t be implemented in this class!");
    }

    @Override
    public boolean canBeDeactivated() {
        throw new UnsupportedOperationException("This method can`t be implemented in this class!");
    }

    @Override
    public boolean hasAdminsRightOnSite() {
        throw new UnsupportedOperationException("This method can`t be implemented in this class!");
    }

    @Override
    public int getFirstSitePageId() {
        throw new UnsupportedOperationException("This method can`t be implemented in this class!");
    }

    @Override
    public boolean isBlueprint() {
        throw new UnsupportedOperationException("This method can`t be implemented in this class!");
    }

    @Override
    public boolean isPublishedBlueprint() {
        throw new UnsupportedOperationException("This method can`t be implemented in this class!");
    }

    @Override
    public boolean isActivatedBlueprint() {
        throw new UnsupportedOperationException("This method can`t be implemented in this class!");
    }

    @Override
    public boolean hasBeenPublished() {
        throw new UnsupportedOperationException("This method can`t be implemented in this class!");
    }

    @Override
    public String getHisNetworkName() {
        throw new UnsupportedOperationException("This method can`t be implemented in this class!");
    }

    @Override
    public List<DashboardSiteInfoForCreatedSite.DashboardWidget> getDashboardWidgets() {
        throw new UnsupportedOperationException("This method can`t be implemented in this class!");
    }
    /*--------------------------------------------Not implemented methods---------------------------------------------*/
}
