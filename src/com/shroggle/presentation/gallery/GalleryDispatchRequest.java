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
package com.shroggle.presentation.gallery;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import com.shroggle.logic.gallery.GalleryDispatchType;
import com.shroggle.entity.SiteShowOption;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
public class GalleryDispatchRequest {

    @RemoteProperty
    private int galleryId;

    @RemoteProperty
    private int widgetId;

    @RemoteProperty
    private GalleryDispatchType galleryDispatchType;

    @RemoteProperty
    private SiteShowOption siteShowOption;

    @RemoteProperty
    private int filledFormId;

    public int getFilledFormId() {
        return filledFormId;
    }

    public void setFilledFormId(int filledFormId) {
        this.filledFormId = filledFormId;
    }

    public SiteShowOption getSiteShowOption() {
        return siteShowOption;
    }

    public void setSiteShowOption(SiteShowOption siteShowOption) {
        this.siteShowOption = siteShowOption;
    }

    public int getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(int galleryId) {
        this.galleryId = galleryId;
    }

    public int getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(int widgetId) {
        this.widgetId = widgetId;
    }

    public GalleryDispatchType getGalleryDispatchType() {
        return galleryDispatchType;
    }

    public void setGalleryDispatchType(GalleryDispatchType galleryDispatchType) {
        this.galleryDispatchType = galleryDispatchType;
    }
}
