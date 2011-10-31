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

/**
 * @author Artem Stasuk
 */
@DataTransferObject
public class VideoRangeEdit {

    public VideoRangeEdit() {

    }

    public VideoRangeEdit(
            final int galleryId, final int filledFormId, final float start,
            final float finish, final float total) {
        this.start = start;
        this.finish = finish;
        this.total = total;
        this.filledFormId = filledFormId;
        this.galleryId = galleryId;
    }

    public float getStart() {
        return start;
    }

    public void setStart(final float start) {
        this.start = start;
    }

    public float getFinish() {
        return finish;
    }

    public void setFinish(final float finish) {
        this.finish = finish;
    }

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

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    private int galleryId;

    /**
     * If you want create range without form set it to 0 or -1
     */
    private int filledFormId;

    private float start;
    private float finish;

    /**
     * It's video duration time. We set it because very
     * difficult find it by videoFlvId
     */
    private float total;

}