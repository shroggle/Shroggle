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

@Entity(name = "workVideos")
public class WorkVideo extends WorkItem implements Video1 {

    @Override
    public boolean isDisplaySmallOptions() {
        return displaySmallOptions;
    }

    @Override
    public void setDisplaySmallOptions(boolean displaySmallOptions) {
        this.displaySmallOptions = displaySmallOptions;
    }

    @Override
    public boolean isDisplayLargeOptions() {
        return displayLargeOptions;
    }

    @Override
    public void setDisplayLargeOptions(boolean displayLargeOptions) {
        this.displayLargeOptions = displayLargeOptions;
    }

    @Override
    public Integer getImageWidth() {
        return imageWidth;
    }

    @Override
    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    @Override
    public Integer getImageHeight() {
        return imageHeight;
    }

    @Override
    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }

    @Override
    public boolean isSaveRatio() {
        return saveRatio;
    }

    @Override
    public void setSaveRatio(boolean saveRatio) {
        this.saveRatio = saveRatio;
    }

    @Override
    public String getVideoSmallSize() {
        return videoSmallSize;
    }

    @Override
    public void setVideoSmallSize(String videoSmallSize) {
        this.videoSmallSize = videoSmallSize;
    }

    @Override
    public String getVideoLargeSize() {
        return videoLargeSize;
    }

    @Override
    public void setVideoLargeSize(String videoLargeSize) {
        this.videoLargeSize = videoLargeSize;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.VIDEO;
    }

    @Override
    public boolean isIncludeDescription() {
        return includeDescription;
    }

    @Override
    public void setIncludeDescription(boolean includeDescription) {
        this.includeDescription = includeDescription;
    }

    @Override
    public String getKeywords() {
        return keywords;
    }

    @Override
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean isPlayInCurrentPage() {
        return playInCurrentPage;
    }

    @Override
    public void setPlayInCurrentPage(boolean playInCurrentPage) {
        this.playInCurrentPage = playInCurrentPage;
    }

    @Override
    public Integer getImageId() {
        return imageId;
    }

    @Override
    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    @Override
    public Integer getFlvVideoId() {
        return flvVideoId;
    }

    @Override
    public void setFlvVideoId(Integer flvVideoId) {
        this.flvVideoId = flvVideoId;
    }

    @Override
    public Integer getLargeFlvVideoId() {
        return largeFlvVideoId;
    }

    @Override
    public void setLargeFlvVideoId(Integer largeFlvVideoId) {
        this.largeFlvVideoId = largeFlvVideoId;
    }

    @Override
    public Integer getSmallFlvVideoId() {
        return smallFlvVideoId;
    }

    @Override
    public void setSmallFlvVideoId(Integer smallFlvVideoId) {
        this.smallFlvVideoId = smallFlvVideoId;
    }

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

}