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
@Entity(name = "slideShows")
public class DraftSlideShow extends DraftItem implements SlideShow {

    @Column
    private Integer imageWidth = 200;

    @Column
    private Integer imageHeight = 200;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SlideShowType slideShowType = SlideShowType.SINGLE_IMAGE;

    @Column
    private int numberOfImagesShown = 1; // Applies only to SlideShowType.MULTIPLE_IMAGES;

    @Enumerated(EnumType.STRING)
    private SlideShowDisplayType displayType = SlideShowDisplayType.MOVING_STRIP_HORIZONTAL; // Applies only to SlideShowType.MULTIPLE_IMAGES;

    @Enumerated(EnumType.STRING)
    private SlideShowTransitionEffectType transitionEffectType = SlideShowTransitionEffectType.SLIDE_SLOW;

    private boolean displayControls = false;

    private boolean autoPlay = true;

    private int autoPlayInterval = 5000; // value in ms.

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "slideShow")
    private List<DraftSlideShowImage> images = new ArrayList<DraftSlideShowImage>();

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

    public List<DraftSlideShowImage> getDraftImages() {
        return images;
    }

    public void setImages(List<DraftSlideShowImage> images) {
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

    public void addSlideShowImage(final DraftSlideShowImage slideShowImage){
        images.add(slideShowImage);
        slideShowImage.setSlideShow(this);
    }

}
