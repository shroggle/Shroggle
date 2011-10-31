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

import com.shroggle.entity.Site;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;

import java.util.Locale;

/**
 * @author Artem Stasuk
 */
public class PageTitleGetter implements PageTitle {

    public PageTitleGetter(final PageManager pageManager) {
        if (pageManager == null) {
            throw new UnsupportedOperationException(
                    "Can't create page version title getter by null page version!");
        }
        if (pageManager.getPage().isSaved()) {
            this.pageVersionTitle = StringUtil.isNullOrEmpty(pageManager.getTitle())
                    ? pageManager.getName() : pageManager.getTitle();
        } else {
            this.pageVersionTitle = ServiceLocator.getInternationStorage()
                    .get("configurePageName", Locale.US).get("PAGE_CREATE");
        }
        final Site site = pageManager.getPage().getSite();
        this.siteTitle = StringUtil.isNullOrEmpty(site.getTitle()) ? site.getSubDomain() : site.getTitle();
    }

    public String getPageVersionTitle() {
        return pageVersionTitle;
    }

    public String getSiteTitle() {
        return siteTitle;
    }

    private final String pageVersionTitle;
    private final String siteTitle;

}
