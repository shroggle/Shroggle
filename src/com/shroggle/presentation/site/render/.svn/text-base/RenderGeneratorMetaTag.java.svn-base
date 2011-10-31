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

/**
 * @author Balakirev Anatoliy
 */
public class RenderGeneratorMetaTag implements Render {

    public void execute(final RenderContext context, StringBuilder html) {
        String metaTags = "<meta name=\"generator\" content=\"" + ServiceLocator.getConfigStorage().get().getApplicationUrl() + "\" />\n";
        final int i = html.indexOf("<!-- PAGE_HEADER -->");
        if (i > -1) {
            html.insert(i, metaTags);
        }
    }
}
