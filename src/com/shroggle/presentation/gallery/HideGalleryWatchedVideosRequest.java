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

import com.shroggle.entity.SiteShowOption;

/**
 * @author Artem Stasuk
 */
public class HideGalleryWatchedVideosRequest {

    public int getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(int widgetId) {
        this.widgetId = widgetId;
    }

    public int getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(int galleryId) {
        this.galleryId = galleryId;
    }

    public SiteShowOption getSiteShowOption() {
        return siteShowOption;
    }

    public void setSiteShowOption(SiteShowOption siteShowOption) {
        this.siteShowOption = siteShowOption;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    private int widgetId;
    private int galleryId;
    private SiteShowOption siteShowOption;
    private boolean hide;

}