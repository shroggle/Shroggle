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

import com.shroggle.entity.Icon;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.resource.provider.ResourceGetterType;

/**
 * <link rel="shortcut icon" href="/s/360/1/_/favicon.ico">
 * <link rel="icon" type="image/png" href="/s/360/1/_/images/icons/favicon.png">
 *
 * @author Artem Stasuk
 */
public class RenderIcon implements Render {

    public RenderIcon(final PageManager pageManager) {
        this.pageManager = pageManager;
    }

    public void execute(final RenderContext context, final StringBuilder html) {
        Icon icon = pageManager.getSite().getIcon();
        if (icon != null) {
            final int i = html.indexOf("<!-- PAGE_HEADER -->");
            if (i > -1) {
                final String url = ResourceGetterType.ICON.getUrl(icon.getResourceId());
                if ("ico".equals(icon.getExtension())) {
                    html.insert(i, "<link rel=\"shortcut icon\" href=\"" + url + "\">");
                } else if ("png".equals(icon.getExtension())) {
                    html.insert(i, "<link rel=\"icon\" type=\"image/png\" href=\"" + url + "\">\n");
                } else if ("gif".equals(icon.getExtension())) {
                    html.insert(i, "<link rel=\"icon\" type=\"image/gif\" href=\"" + url + "\">\n");
                }
            }
        }
    }

    private final PageManager pageManager;

}