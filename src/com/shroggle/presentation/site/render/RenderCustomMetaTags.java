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
package com.shroggle.presentation.site.render;

import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
public class RenderCustomMetaTags implements Render {

    public RenderCustomMetaTags(final PageManager pageManager) {
        if (pageManager == null) {
            throw new IllegalArgumentException("Cannot initialize RenderCustomMetaTags with null pageManager.");
        }

        this.pageManager = pageManager;
    }

    public void execute(final RenderContext context, final StringBuilder html) {
        final List<String> customMetaTags = new ArrayList<String>();

        //Add custom meta tags from page.
        if (pageManager.getSeoSettings().getCustomMetaTagList() != null) {
            customMetaTags.addAll(pageManager.getSeoSettings().getCustomMetaTagList());
        }

        //Add custom meta tags from site.
        if (pageManager.getPage().getSite().getSeoSettings().getCustomMetaTagList() != null) {
            customMetaTags.addAll(pageManager.getPage().getSite().getSeoSettings().getCustomMetaTagList());
        }

        for (String customMetaTag : customMetaTags) {
            if (!StringUtil.isNullOrEmpty(customMetaTag)) {
                final int i = html.indexOf("<!-- PAGE_HEADER -->");
                if (i > -1) {
                    html.insert(i, customMetaTag);
                }
            }
        }
    }

    private final PageManager pageManager;

}
