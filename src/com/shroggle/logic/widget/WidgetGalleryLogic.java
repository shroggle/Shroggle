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

import com.shroggle.entity.Site;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.entity.WidgetItem;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.Arrays;
import java.util.List;

/**
 * @author Artem Stasuk
 */
public class WidgetGalleryLogic {

    public WidgetGalleryLogic(final WidgetItem widgetGallery) {
        this.widgetGallery = widgetGallery;
    }

    public WidgetItem getWidget() {
        return widgetGallery;
    }

    public int getId() {
        return widgetGallery.getWidgetId();
    }

    public List<WidgetItem> getAllowDatas() {
        final Site site = widgetGallery.getSite();
        final Persistance persistance = ServiceLocator.getPersistance();
        return persistance.getGalleryDataWidgetsBySitesId(Arrays.asList(site.getSiteId()), SiteShowOption.getDraftOption());
    }

    private final WidgetItem widgetGallery;

}
