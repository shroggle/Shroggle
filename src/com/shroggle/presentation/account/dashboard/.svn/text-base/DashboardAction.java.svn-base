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
import com.shroggle.entity.User;
import com.shroggle.exception.UserException;
import com.shroggle.logic.childSites.childSiteSettings.ChildSiteSettingsManager;
import com.shroggle.logic.site.SitesManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.site.LoginedUserInfo;
import com.shroggle.presentation.account.dashboard.siteInfo.DashboardSiteInfo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByLoginUser;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.*;

/**
 * @author Balakirev Anatoliy
 */
@UrlBinding("/account/dashboard.action")
public class DashboardAction extends Action implements LoginedUserInfo {

    @SynchronizeByLoginUser
    @DefaultHandler
    public Resolution show() {
        try {
            user = new UsersManager().getLogined().getUser();
        } catch (final UserException exception) {
            return resolutionCreator.loginInUser(this);
        }
        final SitesManager sitesManager = new SitesManager(user.getUserId());

        /*We show common and network sites in "Common Sites" section on a dashboard. According to http://jira.web-deva.com/browse/SW-5975*/
        for (Site site : sitesManager.getCommonSitesWithoutNetworkAndChildSitesByUserId()) {
            dashboardCommonSitesInfo.add(DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON));
        }
        for (Site site : sitesManager.getNetworkSitesByUserId()) {
            dashboardCommonSitesInfo.add(DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON));
        }
        /*We show common and network sites in "Common Sites" section on a dashboard. According to http://jira.web-deva.com/browse/SW-5975*/

        /*-------------------------------------------------Blueprints-------------------------------------------------*/
        for (Site site : sitesManager.getBlueprintsByUserId()) {
            dashboardBlueprintsInfo.add(DashboardSiteInfo.newInstance(site, DashboardSiteType.BLUEPRINT));
        }
        /*-------------------------------------------------Blueprints-------------------------------------------------*/

        /*--------------------------------------------------Networks--------------------------------------------------*/
        dashboardNetworksInfo.addAll(createNetworks(sitesManager));
        /*--------------------------------------------------Networks--------------------------------------------------*/

        if (UsersManager.isLoginedUserAppAdmin()) {
            final List<Integer> publishedAndActivatedIds = new ArrayList<Integer>();
            for (Site site : persistance.getPublishedBlueprints()) {
                publishedBlueprints.add(DashboardSiteInfo.newInstance(site, DashboardSiteType.PUBLISHED_BLUEPRINT));
                publishedAndActivatedIds.add(site.getSiteId());
            }
            for (Site site : persistance.getActiveBlueprints(null)) {
                activatedBlueprints.add(DashboardSiteInfo.newInstance(site, DashboardSiteType.ACTIVATED_BLUEPRINT));
                publishedAndActivatedIds.add(site.getSiteId());
            }
            // Published and activated blueprints should not be shown in common blueprints for admin (SW-6333). Tolik
            final Iterator<DashboardSiteInfo> iterator = dashboardBlueprintsInfo.iterator();
            while (iterator.hasNext()) {
                if (publishedAndActivatedIds.contains(iterator.next().getSiteId())) {
                    iterator.remove();
                }
            }
        }

        Collections.sort(dashboardCommonSitesInfo, TITLE_COMPARATOR);// Without this network sites will be at the end of the list. Tolik (SW-6220)
        Collections.sort(dashboardBlueprintsInfo, TITLE_COMPARATOR);
        Collections.sort(dashboardNetworksInfo, TITLE_COMPARATOR);
        Collections.sort(publishedBlueprints, TITLE_COMPARATOR);
        Collections.sort(activatedBlueprints, TITLE_COMPARATOR);
        selectedSiteInfo = getSelectedSiteInfoInternal();

        addCreateBlueprintCells(dashboardBlueprintsInfo);// Adding empty cells with "Create a blueprint" link.
        getHttpServletRequest().setAttribute("sitesCount", SITES_COUNT);
        return resolutionCreator.forwardToUrl("/account/dashboard/dashboard.jsp");
    }

    public User getLoginUser() {
        return user;
    }

    public User getUser() {
        return user;
    }

    public int getUserId() {
        return getUser() != null ? getUser().getUserId() : -1;
    }

    public List<DashboardSiteInfo> getDashboardCommonSitesInfo() {
        return dashboardCommonSitesInfo;
    }

    public List<DashboardSiteInfo> getDashboardBlueprintsInfo() {
        return dashboardBlueprintsInfo;
    }

    public List<DashboardSiteInfo> getDashboardNetworksInfo() {
        return dashboardNetworksInfo;
    }

    public boolean isCommonSitesInfoEmpty() {
        return dashboardCommonSitesInfo.isEmpty();
    }

    public boolean isBlueprintsInfoEmpty() {
        return dashboardBlueprintsInfo.isEmpty();
    }

    public boolean isPublishedBlueprintsInfoEmpty() {
        return publishedBlueprints.isEmpty();
    }

    public boolean isActivatedBlueprintsInfoEmpty() {
        return activatedBlueprints.isEmpty();
    }

    public boolean isNetworksInfoEmpty() {
        return dashboardNetworksInfo.isEmpty();
    }

    public DashboardSiteInfo getSelectedSiteInfo() {
        return selectedSiteInfo;
    }

    public List<DashboardSiteInfo> getPublishedBlueprints() {
        return publishedBlueprints;
    }

    public List<DashboardSiteInfo> getActivatedBlueprints() {
        return activatedBlueprints;
    }

    public DashboardSiteType getSelectedSiteType() {
        // I return NETWORK in case of childSite because we show them in one common block on a dashboard.
        if (selectedSiteInfo != null) {
            return (selectedSiteInfo.getSiteType() == DashboardSiteType.CHILD) ? DashboardSiteType.NETWORK : selectedSiteInfo.getSiteType();
        } else {
            return null;
        }
    }

    public void setSelectedSite(Integer selectedSite) {
        this.selectedSite = selectedSite;
    }

    public static int getSitesCount() {
        return SITES_COUNT;
    }

    private User user;
    // Following filed used for saving selected site when user is publishing his blueprint. 
    // This field is added to the email and contains selected site`s hash. Tolik
    private Integer selectedSite;
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private final static int SITES_COUNT = 10;


    private final List<DashboardSiteInfo> dashboardCommonSitesInfo = new ArrayList<DashboardSiteInfo>();
    private final List<DashboardSiteInfo> dashboardNetworksInfo = new ArrayList<DashboardSiteInfo>();
    private final List<DashboardSiteInfo> dashboardBlueprintsInfo = new ArrayList<DashboardSiteInfo>();
    private final List<DashboardSiteInfo> publishedBlueprints = new ArrayList<DashboardSiteInfo>();
    private final List<DashboardSiteInfo> activatedBlueprints = new ArrayList<DashboardSiteInfo>();
    private DashboardSiteInfo selectedSiteInfo;
    private final Persistance persistance = ServiceLocator.getPersistance();

    /*-------------------------------------------------Private methods------------------------------------------------*/


    private DashboardSiteInfo getSelectedSiteInfoInternal() {
        /*-------------------------------Selecting siteInfo by saved in session siteId--------------------------------*/
        final Integer selectedSiteInfoHash = selectedSite != null ? selectedSite : ServiceLocator.getSessionStorage().getSelectedSiteInfoHashForDashboard(this);
        DashboardSiteInfo savedSiteInfo = selectDashboardSiteInfoBySiteId(dashboardCommonSitesInfo, selectedSiteInfoHash);
        if (savedSiteInfo != null) {
            return savedSiteInfo;
        }
        savedSiteInfo = selectDashboardSiteInfoBySiteId(dashboardNetworksInfo, selectedSiteInfoHash);
        if (savedSiteInfo != null) {
            return savedSiteInfo;
        }
        savedSiteInfo = selectDashboardSiteInfoBySiteId(dashboardBlueprintsInfo, selectedSiteInfoHash);
        if (savedSiteInfo != null) {
            return savedSiteInfo;
        }
        savedSiteInfo = selectDashboardSiteInfoBySiteId(publishedBlueprints, selectedSiteInfoHash);
        if (savedSiteInfo != null) {
            return savedSiteInfo;
        }
        savedSiteInfo = selectDashboardSiteInfoBySiteId(activatedBlueprints, selectedSiteInfoHash);
        if (savedSiteInfo != null) {
            return savedSiteInfo;
        }
        /*-------------------------------Selecting siteInfo by saved in session siteId--------------------------------*/

        /*-----------------------------------------Selecting default siteInfo-----------------------------------------*/
        if (!dashboardCommonSitesInfo.isEmpty()) {
            ServiceLocator.getSessionStorage().setSelectedSiteInfoForDashboard(this.getSession(), dashboardCommonSitesInfo.get(0));
            return dashboardCommonSitesInfo.get(0);
        }
        if (!dashboardNetworksInfo.isEmpty()) {
            final DashboardSiteInfo siteInfo = getFirstEditableDashboardSiteInfoFromNetworks(dashboardNetworksInfo);
            if (siteInfo != null) {
                ServiceLocator.getSessionStorage().setSelectedSiteInfoForDashboard(this.getSession(), siteInfo);
                return siteInfo;
            }
        }
        if (!dashboardBlueprintsInfo.isEmpty()) {
            ServiceLocator.getSessionStorage().setSelectedSiteInfoForDashboard(this.getSession(), dashboardBlueprintsInfo.get(0));
            return dashboardBlueprintsInfo.get(0);
        }
        if (!publishedBlueprints.isEmpty()) {
            ServiceLocator.getSessionStorage().setSelectedSiteInfoForDashboard(this.getSession(), publishedBlueprints.get(0));
            return publishedBlueprints.get(0);
        }
        if (!activatedBlueprints.isEmpty()) {
            ServiceLocator.getSessionStorage().setSelectedSiteInfoForDashboard(this.getSession(), activatedBlueprints.get(0));
            return activatedBlueprints.get(0);
        }
        /*-----------------------------------------Selecting default siteInfo-----------------------------------------*/
        return null;
    }

    private DashboardSiteInfo selectDashboardSiteInfoBySiteId(final List<DashboardSiteInfo> dashboardSiteInfo, final Integer selectedSiteInfoHash) {
        for (DashboardSiteInfo siteInfo : dashboardSiteInfo) {
            if (siteInfo.isSelected(selectedSiteInfoHash) && siteInfo.isEditable()) {
                return siteInfo;
            }
        }
        return null;
    }

    private DashboardSiteInfo getFirstEditableDashboardSiteInfoFromNetworks(List<DashboardSiteInfo> dashboardNetworksInfo) {
        for (DashboardSiteInfo siteInfo : dashboardNetworksInfo) {
            if (siteInfo.isEditable()) {
                return siteInfo;
            }
        }
        return null;
    }

    private static Comparator<DashboardSiteInfo> TITLE_COMPARATOR = new Comparator<DashboardSiteInfo>() {
        public int compare(final DashboardSiteInfo site1, final DashboardSiteInfo site2) {
            /*-We must not change network and child sites ordering. Network site must precedes it`s childSites. Tolik-*/
            if (site1.isNetworkSite() && site2.isChildSite()) {
                return -1;
            }
            if (site1.isChildSite() && site2.isNetworkSite()) {
                return -1;
            }
            /*-We must not change network and child sites ordering. Network site must precedes it`s childSites. Tolik-*/
            return site1.getName().compareToIgnoreCase(site2.getName());
        }
    };
    /*------------------------------------------------Getting networks------------------------------------------------*/

    private List<DashboardSiteInfo> createNetworks(final SitesManager sitesManager) {
        // I`m getting childSiteSettings, not sites, because sites may be not created yet, and I`ve to show info for them too. Tolik
        final List<ChildSiteSettingsManager> childSites = sitesManager.getChildSiteSettingsForCreatedAndNotChildSitesByUserId();
        final List<Site> networkSites = sitesManager.getNetworkSitesByUserId();
        final List<DashboardSiteInfo> dashboardSiteInfo = new ArrayList<DashboardSiteInfo>();
        // Getting child sites by available networks sites
        for (Site networkSite : networkSites) {
            final List<ChildSiteSettingsManager> childSitesByNetwork = selectChildSitesByNetworkSite(childSites, networkSite);
            childSites.removeAll(childSitesByNetwork);

            final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(networkSite, DashboardSiteType.NETWORK);
            dashboardSiteInfo.add(siteInfo);
            for (ChildSiteSettingsManager childSiteSettings : childSitesByNetwork) {
                final DashboardSiteInfo childSiteInfo = DashboardSiteInfo.newInstance(childSiteSettings);
                dashboardSiteInfo.add(childSiteInfo);
            }
        }
        // Getting child sites without available network sites (if user create child site in network site, to which he has no admin or editor rights).
        while (!childSites.isEmpty()) {
            final Site networkSite = childSites.get(0).getChildSiteSettings().getParentSite();
            final List<ChildSiteSettingsManager> childSitesByNetwork = selectChildSitesByNetworkSite(childSites, networkSite);
            childSites.removeAll(childSitesByNetwork);

            dashboardSiteInfo.add(DashboardSiteInfo.newInstance(networkSite, DashboardSiteType.NETWORK));
            for (ChildSiteSettingsManager childSiteSettings : childSitesByNetwork) {
                final DashboardSiteInfo childSiteInfo = DashboardSiteInfo.newInstance(childSiteSettings);
                dashboardSiteInfo.add(childSiteInfo);
            }
        }
        return dashboardSiteInfo;
    }

    private List<ChildSiteSettingsManager> selectChildSitesByNetworkSite(final List<ChildSiteSettingsManager> childSites, final Site networkSite) {
        final List<ChildSiteSettingsManager> childSitesByNetwork = new ArrayList<ChildSiteSettingsManager>();
        for (ChildSiteSettingsManager manager : childSites) {
            if (manager.getParentSite().getSiteId() == networkSite.getSiteId()) {
                childSitesByNetwork.add(manager);
            }
        }
        return childSitesByNetwork;
    }

    private void addCreateBlueprintCells(final List<DashboardSiteInfo> dashboardBlueprintsInfo) {
        dashboardBlueprintsInfo.add(0, DashboardSiteInfo.newEmptyInstance(DashboardSiteType.CREATE_BLUEPRINT_CELL));
        int currentIndex = SITES_COUNT;
        while (currentIndex < dashboardBlueprintsInfo.size()) {
            dashboardBlueprintsInfo.add(currentIndex, DashboardSiteInfo.newEmptyInstance(DashboardSiteType.CREATE_BLUEPRINT_CELL));
            currentIndex += SITES_COUNT;
        }
    }
    /*------------------------------------------------Getting networks------------------------------------------------*/
}
