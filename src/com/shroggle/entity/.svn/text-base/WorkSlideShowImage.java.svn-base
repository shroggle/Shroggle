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

import javax.persistence.*;

/**
 * @author dmitry.solomadin
 */
@Entity(name = "workSlideShowImages")
public class WorkSlideShowImage implements SlideShowImage {

    @Id
    private int slideShowImageId;

    @ManyToOne
    @ForeignKey(name = "workSlideShowImagesSlideShowId")
    @JoinColumn(name = "slideShowId", nullable = false)
    private WorkSlideShow slideShow;

    private int position;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SlideShowImageType imageType;

    private int imageId; // This can be id of either Image or FormFile depending on imageType.

    private Integer linkBackGalleryWidgetId;

    public Integer getLinkBackGalleryWidgetId() {
        return linkBackGalleryWidgetId;
    }

    public void setLinkBackGalleryWidgetId(Integer linkBackGalleryWidgetId) {
        this.linkBackGalleryWidgetId = linkBackGalleryWidgetId;
    }

    public WorkSlideShow getSlideShow() {
        return slideShow;
    }

    public void setSlideShow(SlideShow slideShow) {
        this.slideShow = (WorkSlideShow) slideShow;
    }

    public int getSlideShowImageId() {
        return slideShowImageId;
    }

    public void setSlideShowImageId(int slideShowImageId) {
        this.slideShowImageId = slideShowImageId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public SlideShowImageType getImageType() {
        return imageType;
    }

    public void setImageType(SlideShowImageType imageType) {
        this.imageType = imageType;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

}
