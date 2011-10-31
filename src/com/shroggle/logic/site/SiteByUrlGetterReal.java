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

import com.shroggle.entity.Site;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

/**
 * @author Artem Stasuk
 */
public class SiteByUrlGetterReal implements SiteByUrlGetter {

    @Override
    public Site get(String url) {
        if (url == null) {
            return null;
        }

        url = url.toLowerCase();

        if (url.startsWith("www.")) {
            url = url.substring("www.".length(), url.length());
        }

        Site site = getBySubDomain(url);

        if (site == null) {
            site = getByBrandedSubDomain(url);
        }

        if (site == null) {
            site = getByCustomUrl(url);
        }

        return site;
    }

    private Site getByBrandedSubDomain(final String url) {
        final int dot = url.indexOf(".");

        if (dot > -1) {
            final Persistance persistance = ServiceLocator.getPersistance();

            final String networkSubDomain = url.substring(0, dot);
            final String networkDomain = url.substring(dot + 1, url.length());

            return persistance.getSiteByBrandedSubDomain(networkSubDomain, networkDomain);
        }

        return null;
    }

    private Site getByCustomUrl(final String url) {
        final Persistance persistance = ServiceLocator.getPersistance();

        return persistance.getSiteByCustomUrl(url);
    }

    private Site getBySubDomain(final String url) {
        final Persistance persistance = ServiceLocator.getPersistance();

        final String subDomain = getSubDomain(url);
        if (subDomain != null) {
            return persistance.getSiteBySubDomain(subDomain);
        }

        return null;
    }

    private String getSubDomain(final String url) {
        final int mailDomainStartingPoint = url.indexOf("." + getUserSitesUrl());
        if (mailDomainStartingPoint > -1) {
            return url.substring(0, mailDomainStartingPoint);
        }

        return null;
    }

    private String getUserSitesUrl() {
        String sitesUrl = ServiceLocator.getConfigStorage().get().getUserSitesUrl();
        int columnIndex = sitesUrl.indexOf(":");
        if (columnIndex == -1) {
            return sitesUrl;
        }
        return sitesUrl.substring(0, columnIndex);
    }

}
