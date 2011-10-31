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
import com.shroggle.util.StringUtil;

/**
 * @author dmitry.solomadin
 */
public class RenderTitleMetaTag implements Render {

    public RenderTitleMetaTag(final PageManager pageManager, final SiteShowOption siteShowOption) {
        if (pageManager == null) {
            throw new IllegalArgumentException("Cannot initialize RenderTitleMetaTag with null pageManager.");
        }

        this.pageManager = pageManager;
        this.siteShowOption = siteShowOption;
    }

    public void execute(final RenderContext context, final StringBuilder html) {
        final String titleFromForm =
                new RenderMetaTagHelper().extractDescriptionOrTitleFromForm(context, pageManager, siteShowOption);
        final String titleFromPage = StringUtil.getEmptyOrString(pageManager.getSeoSettings().getTitleMetaTag());
        final String titleToInsert;
        if (!titleFromPage.isEmpty()){
            titleToInsert = titleFromPage + (titleFromForm.isEmpty() ? "" : ". " +  titleFromForm);
        } else {
            titleToInsert = titleFromForm;
        }

        if (!titleToInsert.isEmpty()) {
            final int i = html.indexOf("<!-- PAGE_HEADER -->");
            if (i > -1) {
                html.insert(i, "<meta name=\"title\" content=\"" + titleToInsert + "\"/>\n");
            }
        }
    }

    private final PageManager pageManager;
    private final SiteShowOption siteShowOption;

}
