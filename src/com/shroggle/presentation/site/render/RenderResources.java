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

import com.shroggle.entity.SiteShowOption;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.html.optimization.PageResourcesAccelerator;
import com.shroggle.util.html.optimization.PageResourcesContext;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Stasuk Artem
 */
public class RenderResources implements Render {

    public RenderResources(final SiteShowOption siteShowOption) {
        this.siteShowOption = siteShowOption;
    }

    public void execute(final RenderContext context, StringBuilder html) throws IOException, ServletException {
        final int i = html.indexOf("<!-- PAGE_HEADER -->");
        if (i > -1) {
            final PageResourcesAccelerator accelerator = ServiceLocator.getPageResourcesAccelerator();

            String links = "";
            links += accelerator.execute(new PageResourcesContext(context), "user.js").getValue();
            links += accelerator.execute(new PageResourcesContext(context), "user.css").getValue();
            links += "<script type=\"text/javascript\" src=\"/tinymce/jquery.tinymce.js\"></script>\n";

            if (siteShowOption == SiteShowOption.INSIDE_APP) {
                links += accelerator.execute(new PageResourcesContext(context), "defaultForInsideApp.css").getValue();
                links += accelerator.execute(new PageResourcesContext(context), "ajaxDispatcherFake.js").getValue();
            }

            if (siteShowOption == SiteShowOption.ON_USER_PAGES || siteShowOption == SiteShowOption.OUTSIDE_APP) {
                links += accelerator.execute(new PageResourcesContext(context), "ajaxDispatcher.js").getValue();
            }

            html.insert(i, links);
        }
    }

    final SiteShowOption siteShowOption;

}