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
import com.shroggle.logic.gallery.GalleryDispatchType;

/**
 * @author Balakirev Anatoliy
 */
public class GalleryUrlForCurrentPage {

    public GalleryUrlForCurrentPage(int galleryId, int filledFormId, Integer widgetId, SiteShowOption siteShowOption) {
        this.galleryId = galleryId;
        this.filledFormId = filledFormId;
        this.widgetId = widgetId;
        this.siteShowOption = siteShowOption;
    }

    public String getUserScript() {
        return "return ajaxDispatcher.execute(this);";
    }

    public String getAjaxDispatch() {
        return "#dispatchGallery" + galleryId + "=" + GalleryDispatchType.SHOW_GALLERY +
                "/filledFormId=" + filledFormId +
                "/siteShowOption=" + siteShowOption +
                "/widgetId=" + widgetId;
    }

    private int galleryId;

    private int filledFormId;

    private Integer widgetId;

    private SiteShowOption siteShowOption;

    public int getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(int galleryId) {
        this.galleryId = galleryId;
    }

    public int getFilledFormId() {
        return filledFormId;
    }

    public void setFilledFormId(int filledFormId) {
        this.filledFormId = filledFormId;
    }

    public Integer getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(Integer widgetId) {
        this.widgetId = widgetId;
    }

    public SiteShowOption getSiteShowOption() {
        return siteShowOption;
    }

    public void setSiteShowOption(SiteShowOption siteShowOption) {
        this.siteShowOption = siteShowOption;
    }
}
