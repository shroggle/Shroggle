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
package com.shroggle.logic.widget;

import com.shroggle.entity.WidgetItem;

/**
 * @author Artem Stasuk
 */
public class WidgetGalleryDataManager {

    public WidgetGalleryDataManager(final WidgetItem widgetGalleryData) {
        this.widgetGalleryData = widgetGalleryData;
    }

    public WidgetTitle getTitle() {
        return new WidgetTitleGetter(widgetGalleryData);
    }

    private final WidgetItem widgetGalleryData;

}
