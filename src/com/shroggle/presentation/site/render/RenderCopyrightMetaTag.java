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

/**
 * @author dmitry.solomadin
 */
public class RenderCopyrightMetaTag implements Render {

    public RenderCopyrightMetaTag(final PageManager pageManager) {
        if (pageManager == null) {
            throw new IllegalArgumentException("Cannot initialize RenderCopyrightMetaTag with null pageManager.");
        }

        this.pageManager = pageManager;
    }

    public void execute(final RenderContext context, final StringBuilder html) {
        final String copyrightMetaTag =
                StringUtil.isNullOrEmpty(pageManager.getSeoSettings().getCopyrightMetaTag()) ?
                pageManager.getPage().getSite().getSeoSettings().getCopyrightMetaTag() :
                pageManager.getSeoSettings().getCopyrightMetaTag();

        if (!StringUtil.isNullOrEmpty(copyrightMetaTag)) {
            final int i = html.indexOf("<!-- PAGE_HEADER -->");
            if (i > -1) {
                html.insert(i, "<meta name=\"copyright\" content=\"" + copyrightMetaTag + "\"/>\n");
            }
        }
    }

    private final PageManager pageManager;

}
