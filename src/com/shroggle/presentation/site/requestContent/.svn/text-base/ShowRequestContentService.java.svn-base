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
package com.shroggle.presentation.site.requestContent;

import com.shroggle.entity.Site;
import com.shroggle.entity.SiteType;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.SiteStatus;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.presentation.site.SiteByTitleComparator;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByAllEntity;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Artem Stasuk
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class ShowRequestContentService extends ServiceWithExecutePage {

    @SynchronizeByAllEntity(
            entityClass = Site.class)
    @RemoteMethod
    public String execute(final Integer targetSiteId) throws IOException, ServletException {
        userSitesUrl = configStorage.get().getUserSitesUrl();

        final UserManager userManager = new UsersManager().getLogined();

        if (targetSiteId != null) {
            targetSite = userManager.getRight().getSiteRight().getSiteForEdit(targetSiteId).getSite();
            targetSites = Arrays.asList(targetSite);
        } else {
            targetSites = persistance.getSites(
                    userManager.getUserId(), SiteAccessLevel.getUserAccessLevels());
            Collections.sort(targetSites, new SiteByTitleComparator());
        }

        sites = new ArrayList<Site>();
        for (final Site site : persistance.getAllSites()) {
            if (site.getType() == SiteType.COMMON && site.getSitePaymentSettings().getSiteStatus() == SiteStatus.ACTIVE) {
                sites.add(site);
            }
        }
        Collections.sort(sites, new SiteByTitleComparator());
        return executePage("/site/showRequestContent.jsp");
    }

    public List<Site> getSites() {
        return sites;
    }

    public Site getTargetSite() {
        return targetSite;
    }

    public List<Site> getTargetSites() {
        return targetSites;
    }

    public String getUserSitesUrl() {
        return userSitesUrl;
    }

    private List<Site> sites;
    private List<Site> targetSites;
    private Site targetSite;
    private String userSitesUrl;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ConfigStorage configStorage = ServiceLocator.getConfigStorage();

}
