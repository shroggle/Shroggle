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

package com.shroggle.logic.borderBackground;

import com.shroggle.entity.Background;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.entity.Widget;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;


/**
 * @author Balakirev Anatoliy
 */
public class BackgroundLogic {

    public static Background create(final Integer itemId, final Integer draftItemId, final Integer borderBackgroundId,
                                    final boolean showForPage) {
        final Persistance persistance = ServiceLocator.getPersistance();
        Background background = null;
        if (borderBackgroundId != null) {
            background = persistance.getBackground(borderBackgroundId);
        } else if (showForPage) {
            if (persistance.getPage(itemId) != null) {
                final PageManager pageManager = new PageManager(persistance.getPage(itemId));
                background = pageManager.getBackground();
            }
        } else if (itemId != null) {
            final Widget widget = persistance.getWidget(itemId);
            background = widget != null ? new WidgetManager(widget).getBackground(SiteShowOption.getDraftOption()) : null;
        } else {
            background = new ItemManager(draftItemId).getBackground(SiteShowOption.getDraftOption());
        }
        if (background == null) {
            background = gedDefault();
        }
        return background;
    }

    private static Background gedDefault() {
        Background borderBackground = new Background();
        int borderBackgroundId = -1;
        borderBackground.setId(borderBackgroundId);
        borderBackground.setBackgroundImageId(-1);
        borderBackground.setBackgroundColor("");
        borderBackground.setBackgroundRepeat("repeat");
        borderBackground.setBackgroundPosition("top left");
        return borderBackground;
    }


}