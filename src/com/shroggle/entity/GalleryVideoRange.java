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
package com.shroggle.entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Artem Stasuk
 */
@Entity(name = "galleryVideoRanges")
public class GalleryVideoRange {

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

    public int getRangeId() {
        return rangeId;
    }

    public void setRangeId(int rangeId) {
        this.rangeId = rangeId;
    }

    public int getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(int galleryId) {
        this.galleryId = galleryId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @Id
    private int rangeId;

    @ManyToOne
    @JoinColumn(name = "userId")
    @ForeignKey(name = "galleryVideoRangesUserId")
    private User user;

    private int galleryId;

    private int filledFormId;

    private float start;

    private float finish;

    /**
     * It's video duration time. We set it because very
     * difficult find it by videoFlvId
     */
    private float total;

}
