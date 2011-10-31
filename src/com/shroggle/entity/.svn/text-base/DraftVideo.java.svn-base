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

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "videos1")
public class DraftVideo extends DraftItem implements Video1 {

    private Integer flvVideoId;

    private Integer largeFlvVideoId;

    private Integer smallFlvVideoId;

    private Integer imageId;

    private Integer imageWidth;

    private Integer imageHeight;

    @Column(length = 50)
    private String videoSmallSize;

    @Column(length = 50)
    private String videoLargeSize;

    private String keywords = "";

    private boolean includeDescription = true;

    private boolean playInCurrentPage;

    private boolean displaySmallOptions;

    private boolean displayLargeOptions;

    private boolean saveRatio;

    public boolean isDisplaySmallOptions() {
        return displaySmallOptions;
    }

    public void setDisplaySmallOptions(boolean displaySmallOptions) {
        this.displaySmallOptions = displaySmallOptions;
    }

    public boolean isDisplayLargeOptions() {
        return displayLargeOptions;
    }

    public void setDisplayLargeOptions(boolean displayLargeOptions) {
        this.displayLargeOptions = displayLargeOptions;
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

    public boolean isSaveRatio() {
        return saveRatio;
    }

    public void setSaveRatio(boolean saveRatio) {
        this.saveRatio = saveRatio;
    }

    public String getVideoSmallSize() {
        return videoSmallSize;
    }

    public void setVideoSmallSize(String videoSmallSize) {
        this.videoSmallSize = videoSmallSize;
    }

    public String getVideoLargeSize() {
        return videoLargeSize;
    }

    public void setVideoLargeSize(String videoLargeSize) {
        this.videoLargeSize = videoLargeSize;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.VIDEO;
    }

    public boolean isIncludeDescription() {
        return includeDescription;
    }

    public void setIncludeDescription(boolean includeDescription) {
        this.includeDescription = includeDescription;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public boolean isPlayInCurrentPage() {
        return playInCurrentPage;
    }

    public void setPlayInCurrentPage(boolean playInCurrentPage) {
        this.playInCurrentPage = playInCurrentPage;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Integer getFlvVideoId() {
        return flvVideoId;
    }

    public void setFlvVideoId(Integer flvVideoId) {
        this.flvVideoId = flvVideoId;
    }

    public Integer getLargeFlvVideoId() {
        return largeFlvVideoId;
    }

    public void setLargeFlvVideoId(Integer largeFlvVideoId) {
        this.largeFlvVideoId = largeFlvVideoId;
    }

    public Integer getSmallFlvVideoId() {
        return smallFlvVideoId;
    }

    public void setSmallFlvVideoId(Integer smallFlvVideoId) {
        this.smallFlvVideoId = smallFlvVideoId;
    }

}