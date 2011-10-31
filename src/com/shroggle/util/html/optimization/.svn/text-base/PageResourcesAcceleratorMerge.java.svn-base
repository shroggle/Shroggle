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

package com.shroggle.util.html.optimization;

import com.shroggle.util.ServiceLocator;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Artem Stasuk
 */
public class PageResourcesAcceleratorMerge implements PageResourcesAccelerator {

    @Override
    public PageResourcesValue execute(final PageResourcesContext context, final String name)
            throws IOException, ServletException {
        final StringBuilder result = new StringBuilder(1024 * 2);

        final PageResourcesPart part = new PageResourcesStorage().get(name);
        if (part == null) {
            throw new IOException("Can't find page resources part with name: \"" + name + "\"");
        }

        result.append("\n/* ").append(name).append(" */\n\n");
        for (final PageResourcesItem item : part.getItems()) {
            result.append("\n/* ").append(item.getPath()).append(" */\n\n");
            result.append(
                    ServiceLocator.getHtmlGetter().get(
                            item.getPath(), context.getRequest(), context.getResponse(),
                            context.getServletContext()));
        }

        return new PageResourcesValue(result.toString());
    }

}