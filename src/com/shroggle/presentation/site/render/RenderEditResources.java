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

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.html.optimization.PageResourcesAccelerator;
import com.shroggle.util.html.optimization.PageResourcesContext;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Stasuk Artem
 */
public class RenderEditResources implements Render {

    public void execute(final RenderContext context, final StringBuilder pageHtml)
            throws IOException, ServletException {
        final int i = pageHtml.indexOf("<!-- PAGE_HEADER -->");
        if (i > -1) {
            final PageResourcesAccelerator accelerator = ServiceLocator.getPageResourcesAccelerator();

            String links = accelerator.execute(new PageResourcesContext(context), "editUser.css").getValue();
            links += accelerator.execute(new PageResourcesContext(context), "editUser.js").getValue();
            links += "<script type=\"text/javascript\" src=\"/tinymce/jquery.tinymce.js\"></script>\n";
            pageHtml.insert(i, links);
        }
    }

}