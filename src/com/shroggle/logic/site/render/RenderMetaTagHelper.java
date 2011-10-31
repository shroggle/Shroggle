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
package com.shroggle.logic.site.render;

import com.shroggle.entity.*;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.gallery.ShowGalleryUtils;
import com.shroggle.presentation.site.render.RenderContext;
import com.shroggle.util.ServiceLocator;

/**
 * @author dmitry.solomadin
 */
public class RenderMetaTagHelper {

    public String extractDescriptionOrTitleFromForm(final RenderContext context, final PageManager pageManager,
                                                    final SiteShowOption siteShowOption) {
        String descriptionFromForm = "";
        for (final Widget widget : pageManager.getWidgets()) {
            if (widget.isWidgetItem()) {
                WidgetItem widgetItem = (WidgetItem) widget;
                final Item item = new WidgetManager(widgetItem).getItemManager().getItem(siteShowOption);

                final Integer currentDisplayedFilledFormId = ShowGalleryUtils.getCurrentlyDisplayedFilledFormId(item, widget, context);

                if (currentDisplayedFilledFormId != null) {
                    FilledForm filledForm = ServiceLocator.getPersistance().getFilledFormById(currentDisplayedFilledFormId);

                    if (filledForm != null) {
                        FilledFormItem nameFilledItem = FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.NAME);

                        if (nameFilledItem != null && !nameFilledItem.getValue().isEmpty()) {
                            descriptionFromForm = descriptionFromForm + (descriptionFromForm.isEmpty() ? "" : " ") + nameFilledItem.getValue();
                        } else {
                            FilledFormItem seoKeywordsFilledItem = FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.SEO_KEYWORDS);

                            if (seoKeywordsFilledItem != null && !seoKeywordsFilledItem.getValue().isEmpty()) {
                                descriptionFromForm = descriptionFromForm + (descriptionFromForm.isEmpty() ? "" : " ") + seoKeywordsFilledItem.getValue();
                            }
                        }
                    }
                }
            }
        }

        return descriptionFromForm;
    }

}
