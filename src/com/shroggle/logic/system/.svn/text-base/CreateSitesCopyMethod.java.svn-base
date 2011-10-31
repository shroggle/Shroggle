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
package com.shroggle.logic.system;

import com.shroggle.entity.*;
import com.shroggle.logic.site.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.Collections;

/**
 * @author Artem Stasuk
 */
public class CreateSitesCopyMethod implements CreateSitesMethod {

    public CreateSitesCopyMethod(final int siteId, final String urlPrefix) {
        this.siteId = siteId;
        this.urlPrefix = urlPrefix;
    }

    @Override
    public void execute(final CreateSitesStatus status) {
        final Site site = persistance.getSite(siteId);
        if (site == null) {
            throw new UnsupportedOperationException("Can't find source site.");
        }

        if (site.getUserOnSiteRights().isEmpty()) {
            throw new UnsupportedOperationException("Can't find any user for source site.");
        }

        final User user = site.getUserOnSiteRights().get(0).getId().getUser();

        final CreateSiteRequest request = new CreateSiteRequest(
                null, user, urlPrefix + System.currentTimeMillis(), null,
                urlPrefix + System.currentTimeMillis(), null, null,
                Collections.EMPTY_LIST, null, SiteType.COMMON, new SEOSettings(), null);
        final Site copiedSite = SiteCreator.updateSiteOrCreateNew(request);

        copiedSite.setThemeId(site.getThemeId());

        new SiteCopier(copiedSite).copyFrom(site);
    }

    private final String urlPrefix;
    private int siteId;
    private final Persistance persistance = ServiceLocator.getPersistance();

}