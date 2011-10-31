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
public class RenderRobotsMetaTag implements Render {

    public RenderRobotsMetaTag(final PageManager pageManager) {
        if (pageManager == null) {
            throw new IllegalArgumentException("Cannot initialize RenderRobotsMetaTag with null pageManager.");
        }

        this.pageManager = pageManager;
    }

    public void execute(final RenderContext context, final StringBuilder html) {
        final String robotsMetaTag =
                StringUtil.isNullOrEmpty(pageManager.getSeoSettings().getRobotsMetaTag()) ?
                pageManager.getPage().getSite().getSeoSettings().getRobotsMetaTag() :
                pageManager.getSeoSettings().getRobotsMetaTag();

        if (!StringUtil.isNullOrEmpty(robotsMetaTag)) {
            final int i = html.indexOf("<!-- PAGE_HEADER -->");
            if (i > -1) {
                html.insert(i, "<meta name=\"ROBOTS\" content=\"" + robotsMetaTag + "\"/>\n");
            }
        }
    }

    private final PageManager pageManager;

}
