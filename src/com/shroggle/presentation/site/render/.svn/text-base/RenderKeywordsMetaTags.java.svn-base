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
import com.shroggle.logic.form.FilledFormItemManager;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.gallery.ShowGalleryUtils;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;

/**
 * @author Stasuk Artem
 */
public class RenderKeywordsMetaTags implements Render {

    public RenderKeywordsMetaTags(final PageManager pageManager, final SiteShowOption siteShowOption) {
        this.pageManager = pageManager;
        this.siteShowOption = siteShowOption;
    }

    public void execute(final RenderContext context, StringBuilder html) {
        // Getting SEO_KEYWORDS and NAME / TITLE fields from all galleries on page using their displayed filledFrom Ids
        // to insert in description tag.
        final String keywordsFromForm = extractKeywordsFromForm(context);

        String pageKeywords = pageManager.getKeywords();
        for (final KeywordsGroup pageKeywordsGroup : pageManager.getKeywordsGroups()) {
            if (!pageKeywords.isEmpty()) {
                pageKeywords += ",";
            }
            pageKeywords += pageKeywordsGroup.getValue();
        }

        final String keywordsToInsert = !pageKeywords.isEmpty() ? pageKeywords : keywordsFromForm;

        if (!keywordsToInsert.isEmpty()) {
            final int i = html.indexOf("<!-- PAGE_HEADER -->");
            if (i > -1) {
                html.insert(i, "<meta name=\"keywords\" content=\"" + keywordsToInsert + "\">\n");
            }
        }
    }

    private String extractKeywordsFromForm(final RenderContext context) {
        String keywordsFromForm = "";
        for (Widget widget : pageManager.getWidgets()) {
            if (widget.isWidgetItem()) {
                WidgetItem widgetItem = (WidgetItem) widget;
                final Item item = new WidgetManager(widgetItem).getItemManager().getItem(siteShowOption);

                final Integer currentDisplayedFilledFormId = ShowGalleryUtils.getCurrentlyDisplayedFilledFormId(item, widget, context);

                if (currentDisplayedFilledFormId != null) {
                    FilledForm filledForm = ServiceLocator.getPersistance().getFilledFormById(currentDisplayedFilledFormId);

                    if (filledForm != null) {
                        FilledFormItem seoKeywordsFilledItem = FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.SEO_KEYWORDS);
                        String extractedKeywords = "";
                        if (seoKeywordsFilledItem != null) {
                            extractedKeywords = FilledFormItemManager.extractKeywordsValue(seoKeywordsFilledItem, ", ");
                        }

                        if (!extractedKeywords.isEmpty()) {
                            keywordsFromForm = keywordsFromForm + (keywordsFromForm.isEmpty() ? "" : " ") + extractedKeywords;
                        } else {
                            FilledFormItem nameFilledItem = FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.NAME);

                            if (nameFilledItem != null) {
                                keywordsFromForm = keywordsFromForm + (keywordsFromForm.isEmpty() ? "" : " ") + nameFilledItem.getValue();
                            }
                        }
                    }
                }
            }
        }

        return keywordsFromForm;
    }

    private final PageManager pageManager;
    private final SiteShowOption siteShowOption;

}