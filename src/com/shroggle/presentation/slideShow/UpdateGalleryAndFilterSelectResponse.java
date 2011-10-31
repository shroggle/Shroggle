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
package com.shroggle.presentation.slideShow;

import com.shroggle.entity.Filter;
import com.shroggle.entity.FormItem;
import com.shroggle.entity.Widget;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
public class UpdateGalleryAndFilterSelectResponse {

    @RemoteProperty
    private List<Filter> filters = new ArrayList<Filter>();

    @RemoteProperty
    private Map<Integer, String> linkBackToGalleryWidgetIdLocationPair =
            new HashMap<Integer, String>(); // contains <widgetId, pageName / galleryName>

    @RemoteProperty
    private List<FormItem> imageFormItems = new ArrayList<FormItem>();

    public List<FormItem> getImageFormItems() {
        return imageFormItems;
    }

    public void setImageFormItems(List<FormItem> imageFormItems) {
        this.imageFormItems = imageFormItems;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public Map<Integer, String> getLinkBackToGalleryWidgetIdLocationPair() {
        return linkBackToGalleryWidgetIdLocationPair;
    }

    public void setLinkBackToGalleryWidgetIdLocationPair(Map<Integer, String> linkBackToGalleryWidgetIdLocationPair) {
        this.linkBackToGalleryWidgetIdLocationPair = linkBackToGalleryWidgetIdLocationPair;
    }
}
