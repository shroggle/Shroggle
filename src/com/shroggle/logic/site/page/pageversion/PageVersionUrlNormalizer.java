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

package com.shroggle.logic.site.page.pageversion;

import com.shroggle.entity.Page;
import com.shroggle.entity.PageSettings;
import com.shroggle.entity.Site;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.regex.Pattern;

/**
 * @author Stasuk Artem
 */
public class PageVersionUrlNormalizer {

    //todo remove this class. Accord. to new spec server only check is performed. Look in SavePageService

    public void execute(final PageSettings pageManager) {
        if (pageManager == null) {
            throw new NullPointerException("Can't create url by null page!");
        }

        String pageUrl = pageManager.getUrl();
        if (pageUrl != null) {
            pageUrl = pattern.matcher(pageUrl).replaceAll("");
        }

        if (pageUrl == null || pageUrl.isEmpty()) {
            pageUrl = pattern.matcher(pageManager.getName()).replaceAll("");
        }

        if (pageUrl.isEmpty()) {
            pageUrl = "page";
        }

        int pageUrlIndex = 1;
        while (findByUrl(pageUrl, pageManager)) {
            pageUrl += pageUrlIndex;
        }

        pageManager.setUrl(pageUrl);
    }

    private boolean findByUrl(final String pageUrl, final PageSettings pageManager) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final Site site = pageManager.getSite();
        final Page findPage = persistance.getPageByUrlAndAndSiteIgnoreUrlCase(pageUrl, site.getSiteId());
        return findPage != null && findPage != pageManager.getPage();
    }

    private final static Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");

}
