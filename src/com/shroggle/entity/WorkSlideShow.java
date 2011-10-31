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

import org.hibernate.annotations.CollectionOfElements;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@Entity(name = "workSlideShows")
public class WorkSlideShow extends WorkItem implements SlideShow {

    @Column
    private Integer imageWidth;

    @Column
    private Integer imageHeight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SlideShowType slideShowType = SlideShowType.MULTIPLE_IMAGES;

    @Column
    private int numberOfImagesShown = 12; // Applies only to SlideShowType.MULTIPLE_IMAGES;

    @Enumerated(EnumType.STRING)
    private SlideShowDisplayType displayType; // Applies only to SlideShowType.MULTIPLE_IMAGES;

    @Enumerated(EnumType.STRING)
    private SlideShowTransitionEffectType transitionEffectType;

    private boolean displayControls;

    private boolean autoPlay = true;

    private int autoPlayInterval = 5000; // value in ms.

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "slideShow")
    private List<WorkSlideShowImage> images = new ArrayList<WorkSlideShowImage>();

    public int getAutoPlayInterval() {
        return autoPlayInterval;
    }

    public void setAutoPlayInterval(int autoPlayInterval) {
        this.autoPlayInterval = autoPlayInterval;
    }

    public boolean isAutoPlay() {
        return autoPlay;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    public List<? extends SlideShowImage> getImages() {
        return images;
    }

    public List<WorkSlideShowImage> getWorkImages() {
        return images;
    }

    public void setImages(List<WorkSlideShowImage> images) {
        this.images = images;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.SLIDE_SHOW;
    }

    public Integer getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    public Integer getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }

    public SlideShowType getSlideShowType() {
        return slideShowType;
    }

    public void setSlideShowType(SlideShowType slideShowType) {
        this.slideShowType = slideShowType;
    }

    public int getNumberOfImagesShown() {
        return numberOfImagesShown;
    }

    public void setNumberOfImagesShown(int numberOfImagesShown) {
        this.numberOfImagesShown = numberOfImagesShown;
    }

    public SlideShowDisplayType getDisplayType() {
        return displayType;
    }

    public void setDisplayType(SlideShowDisplayType displayType) {
        this.displayType = displayType;
    }

    public SlideShowTransitionEffectType getTransitionEffectType() {
        return transitionEffectType;
    }

    public void setTransitionEffectType(SlideShowTransitionEffectType transitionEffectType) {
        this.transitionEffectType = transitionEffectType;
    }

    public boolean isDisplayControls() {
        return displayControls;
    }

    public void setDisplayControls(boolean displayControls) {
        this.displayControls = displayControls;
    }

    public void addSlideShowImage(final WorkSlideShowImage slideShowImage){
        images.add(slideShowImage);
        slideShowImage.setSlideShow(this);
    }

}
