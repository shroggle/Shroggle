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

import com.shroggle.entity.*;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.gallery.GalleryData;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.render.RenderMetaTagHelper;
import com.shroggle.presentation.gallery.ShowGalleryUtils;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Render render = new Render();
 * render.execute(context);
 *
 * @author Stasuk Artem
 */
public class RenderDescriptionMetaTag implements Render {

    public RenderDescriptionMetaTag(final PageManager pageManager, final SiteShowOption siteShowOption) {
        if (pageManager == null) {
            throw new IllegalArgumentException("Cannot initialize RenderDescriptionMetaTag with null pageManager.");
        }

        this.pageManager = pageManager;
        this.siteShowOption = siteShowOption;
    }

    public void execute(final RenderContext context, final StringBuilder html) {
        // Getting SEO_KEYWORDS and NAME / TITLE fields from all galleries on page using their displayed filledFrom Ids
        // to insert in description tag.
        final String descriptionFromForm =
                new RenderMetaTagHelper().extractDescriptionOrTitleFromForm(context, pageManager, siteShowOption);
        final String descriptionFromPage = StringUtil.getEmptyOrString(pageManager.getSeoSettings().getPageDescription());
        final String descriptionToInsert;
        if (!descriptionFromPage.isEmpty()){
            descriptionToInsert = descriptionFromPage + (descriptionFromForm.isEmpty() ? "" : ". " +  descriptionFromForm);
        } else {
            descriptionToInsert = descriptionFromForm;
        }

        if (!descriptionToInsert.isEmpty()) {
            final int i = html.indexOf("<!-- PAGE_HEADER -->");
            if (i > -1) {
                html.insert(i, "<meta name=\"description\" content=\"" + descriptionToInsert + "\"/>\n");
            }
        }
    }

    private final PageManager pageManager;
    private final SiteShowOption siteShowOption;

}