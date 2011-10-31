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
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.render.RenderMetaTagHelper;


/**
 * @author Stasuk Artem, dmitry.solomadin
 */
public class RenderTitle implements Render {

    public RenderTitle(final PageManager pageManager, final SiteShowOption siteShowOption) {
        if (pageManager == null) {
            throw new UnsupportedOperationException("Can't create with null page version!");
        }

        this.pageManager = pageManager;
        this.siteShowOption = siteShowOption;
    }

    public void execute(final RenderContext context, final StringBuilder html) {
        final String titleFromForm =
                new RenderMetaTagHelper().extractDescriptionOrTitleFromForm(context, pageManager, siteShowOption);
        final String titleFromPage;
        if (pageManager.getTitle() != null && !pageManager.getTitle().trim().isEmpty()) {
            titleFromPage = pageManager.getTitle();
        } else {
            titleFromPage = pageManager.getName();
        }
        final String titleToInsert = titleFromPage + (!titleFromForm.isEmpty() ? ". " + titleFromForm : "");

        final int pageTitleStart = html.indexOf("<!-- PAGE_TITLE -->");
        if (pageTitleStart > -1) {
            html.replace(pageTitleStart, pageTitleStart + "<!-- PAGE_TITLE -->".length(), titleToInsert);
        }
    }

    private final PageManager pageManager;
    private SiteShowOption siteShowOption;

}