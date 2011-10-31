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

/**
 * @author Stasuk Artem
 */
public class RenderVisitorTracker implements Render {

    public RenderVisitorTracker(final PageManager pageManager) {
        if (pageManager == null) {
            throw new UnsupportedOperationException("Can't create without page version!");
        }
        this.pageId = pageManager.getPageId();
    }

    public void execute(final RenderContext context, final StringBuilder html) {
        // Adding page Id to onuload script
        final int i = html.indexOf("<body");
        if (i > -1) {
            html.replace(
                    i + "<body".length(), i + "<body".length(),
                    " onunload=\"leavingPage(" +
                            pageId + ")\"");
        }

        // Adding visitor tacking sript
        html.append("<script type=\"text/javascript\"> trackVisitor(");
        html.append(pageId);
        html.append(") </script>");
    }

    private final int pageId;

}