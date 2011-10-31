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
package com.shroggle.presentation.site;

import com.shroggle.entity.AccessGroup;
import com.shroggle.entity.ItemType;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.site.render.Render;
import com.shroggle.presentation.site.render.RenderContext;
import com.shroggle.util.html.HtmlUtil;
import net.sourceforge.stripes.action.Resolution;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Artem Stasuk
 */
public class ShowPageVersionResolution implements Resolution {

    public ShowPageVersionResolution(
            final PageManager pageManager, final ServletContext servletContext,
            final Render render, final Map<ItemType, String> parameterMap, final AccessGroup accessGroup) {
        if (pageManager == null) {
            throw new UnsupportedOperationException(
                    "Can't create with null page version!");
        }
        if (render == null) {
            throw new UnsupportedOperationException(
                    "Can't create with null render!");
        }
        if (servletContext == null) {
            throw new UnsupportedOperationException(
                    "Can't create with null servlet context!");
        }
        if (accessGroup == null) {
            throw new UnsupportedOperationException(
                    "Can't create with null show right!");
        }
        this.parameterMap = parameterMap;
        this.pageManager = pageManager;
        this.servletContext = servletContext;
        this.render = render;
    }

    public void execute(
            final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RenderContext context = new RenderContext(request, response, servletContext, parameterMap, false);
        final StringBuilder htmlBuilder = new StringBuilder(pageManager.getSavedHtmlOrDefault());
        render.execute(context, htmlBuilder);
        HtmlUtil.writeHeaderInfo(response.getWriter(), response);
        response.getWriter().append(htmlBuilder.toString());
    }

    private final Map<ItemType, String> parameterMap;
    private final PageManager pageManager;
    private final ServletContext servletContext;
    private final Render render;

}
