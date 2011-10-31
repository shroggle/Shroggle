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
package com.shroggle.logic.gallery;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

/**
 * @author Artem Stasuk
 */
@DataTransferObject
public class SaveGalleryRequest {

    public Integer getWidgetGalleryId() {
        return widgetGalleryId;
    }

    public void setWidgetGalleryId(Integer widgetGalleryId) {
        this.widgetGalleryId = widgetGalleryId;
    }

    public int getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(int galleryId) {
        this.galleryId = galleryId;
    }

    public GalleryEdit getGallerySave() {
        return gallerySave;
    }

    public void setGallerySave(GalleryEdit gallerySave) {
        this.gallerySave = gallerySave;
    }

    @RemoteProperty
    private GalleryEdit gallerySave;

    @RemoteProperty
    private int galleryId;

    @RemoteProperty
    private Integer widgetGalleryId;

}
