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
package com.shroggle.presentation.account.dashboard;

import com.shroggle.entity.Site;
import com.shroggle.logic.childSites.childSiteSettings.ChildSiteSettingsManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.account.dashboard.siteInfo.DashboardSiteInfo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * @author Balakirev Anatoliy
 */
@UrlBinding("/account/showDashboardSiteInfo.action")
public class ShowDashboardSiteInfoAction extends Action {

    @DefaultHandler
    public Resolution execute() {
        new UsersManager().getLogined();

        final Site site = persistance.getSite(siteId);
        final DashboardSiteInfo siteInfo;
        if (site != null) {
            siteInfo = DashboardSiteInfo.newInstance(site, siteType);
        } else {
            siteInfo = DashboardSiteInfo.newInstance(new ChildSiteSettingsManager(persistance.getChildSiteSettingsById(childSiteSettingsId)));
        }

        ServiceLocator.getSessionStorage().setSelectedSiteInfoForDashboard(this.getSession(), siteInfo);

        getHttpServletRequest().setAttribute("dashboardSiteInfo", siteInfo);
        return new ForwardResolution("/account/dashboard/siteInfo.jsp");
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public void setSiteType(DashboardSiteType siteType) {
        this.siteType = siteType;
    }

    public void setChildSiteSettingsId(Integer childSiteSettingsId) {
        this.childSiteSettingsId = childSiteSettingsId;
    }

    private Integer siteId;

    private Integer childSiteSettingsId;

    // One site can be parent and child site at the same time so it can be shown twice on a dashboard.
    // I pass this parameter to know under which circumstances I`m showing site now (we should show some special
    // links in case of a childSite). Tolik.
    private DashboardSiteType siteType;

    private final Persistance persistance = ServiceLocator.getPersistance();
}
