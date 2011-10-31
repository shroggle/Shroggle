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

package com.shroggle.logic.site.page;

import com.shroggle.entity.Widget;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stasuk Artem
 */
public class PageWidgetsByPosition {

    public static Map<Integer, Widget> execute(final PageManager pageManager) {
        final Map<Integer, Widget> widgetsByPosition = new HashMap<Integer, Widget>();
        for (final Widget widget : pageManager.getWidgets()) {
            if (widget.getParent() == null) {
                widgetsByPosition.put(widget.getPosition(), widget);
            }
        }
        return widgetsByPosition;
    }

}
