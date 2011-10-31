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
package com.shroggle.presentation.gallery.comment;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

/**
 * GalleryCommentsManager {add, get}
 * GalleryCommentManager {edit, remove}
 *
 * @author Artem Stasuk
 */
@DataTransferObject
public class AddGalleryCommentRequest {

    public int getFilledFormId() {
        return filledFormId;
    }

    public void setFilledFormId(int filledFormId) {
        this.filledFormId = filledFormId;
    }

    public int getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(int galleryId) {
        this.galleryId = galleryId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    @RemoteProperty
    private String text;

    @RemoteProperty
    private int galleryId;

    @RemoteProperty
    private int filledFormId;

    @RemoteProperty
    private int siteId;

}
