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
package com.shroggle.logic.customtag;

import com.shroggle.entity.Page;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;

/**
 * @author Artem Stasuk
 */
public class CustomTagInternalToHtmlPageLink implements CustomTag {

    @Override
    public void execute(final HtmlTag tag, final SiteShowOption siteShowOption) {
        if (tag.getName().equals("a")) {

            final int pageId = StringUtil.getInt(tag.getAttribute("pageId"), -1);
            final Page page = ServiceLocator.getPersistance().getPage(pageId);

            if (page != null) {
                final PageManager pageManager = new PageManager(page, siteShowOption);
                tag.setAttribute("href", pageManager.getPublicUrl());
            }
        }
    }

}
