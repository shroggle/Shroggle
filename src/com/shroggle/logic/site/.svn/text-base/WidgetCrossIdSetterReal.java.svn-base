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

import com.shroggle.entity.PageSettings;
import com.shroggle.entity.Widget;
import com.shroggle.logic.site.page.PageSettingsManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Artem Stasuk
 */
public class WidgetCrossIdSetterReal implements WidgetCrossIdSetter {

    @Override
    public void execute(final PageSettings pageSettings) {
        for (final Widget widget : new PageSettingsManager(pageSettings).getWidgets()) {
            final int oldCrossWidgetId = widget.getCrossWidgetId();
            widget.setParentCrossWidgetId(oldCrossWidgetId);
            widget.setCrossWidgetId(widget.getWidgetId());
            ids.put(oldCrossWidgetId, widget.getCrossWidgetId());
        }
    }

    public Map<Integer, Integer> get() {
        return ids;
    }

    private final Map<Integer, Integer> ids = new HashMap<Integer, Integer>();

}
