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

import com.shroggle.entity.ChildSiteSettings;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.SiteType;
import com.shroggle.logic.childSites.childSiteSettings.ChildSiteSettingsManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.*;

/**
 * @author Balakirev Anatoliy
 */
public class SitesManager {

    public static final int MAX_AVAILABLE_SITES_BEFORE_CHECK_VERIFICATION_CODE = 20;

    public SitesManager(final int userId) {
        final Persistance persistance = ServiceLocator.getPersistance();
        allCommonSites = Collections.unmodifiableList(
                persistance.getSites(userId, SiteAccessLevel.getUserAccessLevels(),
                        SiteType.COMMON)
        );
        this.userId = userId;
    }

    public SitesManager(final int userId, final boolean forceLoad) {
        final Persistance persistance = ServiceLocator.getPersistance();
        allCommonSites = Collections.unmodifiableList(
                persistance.getSites(userId, SiteAccessLevel.getUserAccessLevels(),
                        SiteType.COMMON)
        );
        if (forceLoad) {
            getNetworkSitesByUserId();
            getChildSitesByUserId();
            getCommonSitesWithoutNetworkAndChildSitesByUserId();
        }
        this.userId = userId;
    }

    public List<Site> getCommonSitesWithoutNetworkAndChildSitesByUserId() {
        if (sitesWithoutNetworkAndChild != null) {
            return sitesWithoutNetworkAndChild;
        } else {
            sitesWithoutNetworkAndChild = new ArrayList<Site>(this.allCommonSites);
            sitesWithoutNetworkAndChild.removeAll(getNetworkSitesByUserId());
            sitesWithoutNetworkAndChild.removeAll(getChildSitesByUserId());
            return sitesWithoutNetworkAndChild;
        }
    }

    public List<Site> getNetworkSitesByUserId() {
        if (networkSites != null) {
            return networkSites;
        } else {
            networkSites = new ArrayList<Site>();
            for (Site site : allCommonSites) {
                if (site.getChildSiteRegistrationsId() != null && site.getChildSiteRegistrationsId().size() != 0) {
                    networkSites.add(site);
                }
            }
            return networkSites;
        }
    }

    public List<Site> getChildSitesByUserId() {
        if (childSites != null) {
            return childSites;
        } else {
            childSites = new ArrayList<Site>();
            for (Site site : allCommonSites) {
                if (site.getChildSiteSettings() != null) {
                    childSites.add(site);
                }
            }
            return childSites;
        }
    }

    public List<ChildSiteSettingsManager> getChildSiteSettingsForCreatedAndNotChildSitesByUserId() {
        if (settingsForCreatedAndNotChildSites != null) {
            return new ArrayList<ChildSiteSettingsManager>(settingsForCreatedAndNotChildSites);
        } else {
            settingsForCreatedAndNotChildSites = new HashSet<ChildSiteSettingsManager>();
            final List<Site> createdChildSites = getChildSitesByUserId();
            for (Site site : createdChildSites) {
                settingsForCreatedAndNotChildSites.add(new ChildSiteSettingsManager(site.getChildSiteSettings()));
            }
            final Persistance persistance = ServiceLocator.getPersistance();
            for (Integer childSiteSettingsId : persistance.getUserById(userId).getChildSiteSettingsId()) {
                final ChildSiteSettings childSiteSettings = persistance.getChildSiteSettingsById(childSiteSettingsId);
                if (childSiteSettings != null) {
                    settingsForCreatedAndNotChildSites.add(new ChildSiteSettingsManager(childSiteSettings));
                }
            }
            return new ArrayList<ChildSiteSettingsManager>(settingsForCreatedAndNotChildSites);
        }
    }

    public List<Site> getBlueprintsByUserId() {
        return ServiceLocator.getPersistance().getSites(userId, SiteAccessLevel.getUserAccessLevels(), SiteType.BLUEPRINT);
    }

    private Set<ChildSiteSettingsManager> settingsForCreatedAndNotChildSites;
    private final List<Site> allCommonSites;
    private List<Site> networkSites;
    private List<Site> childSites;
    private List<Site> sitesWithoutNetworkAndChild;
    private final int userId;
}
