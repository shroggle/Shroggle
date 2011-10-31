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

import com.shroggle.entity.Border;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.entity.Style;
import com.shroggle.entity.Widget;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;


/**
 * @author Balakirev Anatoliy
 */
public class BorderLogic {

    public static Border create(final Integer itemId, final Integer draftItemId, final Integer borderBackgroundId) {
        final Persistance persistance = ServiceLocator.getPersistance();
        Border border = null;
        if (borderBackgroundId != null) {
            border = persistance.getBorder(borderBackgroundId);
        } else if (itemId != null) {
            final Widget widget = persistance.getWidget(itemId);
            border = widget != null ? new WidgetManager(widget).getBorder(SiteShowOption.getDraftOption()) : null;
        } else {
            border = new ItemManager(draftItemId).getBorder(SiteShowOption.getDraftOption());
        }
        if (border == null) {
            border = gedDefault();
        }
        return border;
    }

    private static Border gedDefault() {
        Border border = new Border();
        int borderBackgroundId = -1;
        border.setId(borderBackgroundId);
        border.setBorderColor(new Style());
        border.setBorderPadding(new Style());
        border.setBorderMargin(new Style());
        border.setBorderStyle(new Style());
        border.setBorderWidth(new Style());
        return border;
    }

    public static final int WIDTH = 300;
    public static final int HEIGHT = 240;
}
