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

import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.site.render.Render;
import com.shroggle.presentation.site.render.RenderComposit;
import com.shroggle.presentation.site.render.RenderResources;
import com.shroggle.presentation.site.render.RenderWidgets;
import net.sourceforge.stripes.action.Resolution;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Artem Stasuk
 */
public class ShowWidgetPreviewResolution implements Resolution {

    public ShowWidgetPreviewResolution(
            final Widget widget, final ServletContext servletContext, final Map<ItemType, String> parameterMap) {
        if (widget == null) {
            throw new UnsupportedOperationException(
                    "Can't create with null widget!");
        }
        final Page page = new Page();
        DraftPageSettings draftPageSettings = new DraftPageSettings();
        draftPageSettings.setAccessibleSettings(new AccessibleSettings());
        page.setPageSettings(draftPageSettings);
        draftPageSettings.setPage(page);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.setHtml("<html><head><!-- PAGE_HEADER --></head><body><!-- MEDIA_BLOCK --></body></html>");
        pageVersion.addWidget(widget);

        final List<Render> renders = new ArrayList<Render>();
        renders.add(new RenderWidgets(pageVersion, SiteShowOption.INSIDE_APP));
        renders.add(new RenderResources(SiteShowOption.INSIDE_APP));
        final RenderComposit renderComposit = new RenderComposit(renders);

        resolution = new ShowPageVersionResolution(
                pageVersion, servletContext, renderComposit, parameterMap, AccessGroup.ALL);
    }

    public void execute(
            final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        resolution.execute(request, response);
    }

    private final ShowPageVersionResolution resolution;

}