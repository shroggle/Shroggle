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
package com.shroggle.logic.site;

import com.shroggle.entity.Widget;
import com.shroggle.entity.WidgetItem;
import com.shroggle.logic.site.item.ItemPoster;

/**
 * @author Artem Stasuk
 */
public class WidgetPoster {

    public static void post(final Widget widget, final ItemPoster itemPoster) {
        if (widget.isWidgetItem()) {
            final WidgetItem widgetItem = (WidgetItem) widget;
            itemPoster.publish(widgetItem.getDraftItem());
        }
    }

    private WidgetPoster() {

    }

}
