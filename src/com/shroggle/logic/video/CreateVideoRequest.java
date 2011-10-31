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

package com.shroggle.logic.video;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject
public class CreateVideoRequest {

    @RemoteProperty
    private Integer widgetId;

    @RemoteProperty
    private int videoItemId;

    @RemoteProperty
    private String videoDescription;

    @RemoteProperty
    private boolean includeDescription;

    @RemoteProperty
    private String keywords;

    @RemoteProperty
    private String videoName;

    @RemoteProperty
    private Integer width;

    @RemoteProperty
    private Integer height;

    @RemoteProperty
    private boolean playInCurrentPage;

    @RemoteProperty
    private int videoId;

    @RemoteProperty
    private Integer videoImageId;

    @RemoteProperty
    private boolean displaySmallOptions = true;

    @RemoteProperty
    private boolean displayLargeOptions = true;

    @RemoteProperty
    private String videoSmallSize = "";

    @RemoteProperty
    private String videoLargeSize = "";

    @RemoteProperty
    private Integer imageWidth;

    @RemoteProperty
    private Integer imageHeight;

    @RemoteProperty
    private boolean saveRatio;

    public int getVideoItemId() {
        return videoItemId;
    }

    public void setVideoItemId(int videoItemId) {
        this.videoItemId = videoItemId;
    }

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

    public boolean isSaveRatio() {
        return saveRatio;
    }

    public void setSaveRatio(boolean saveRatio) {
        this.saveRatio = saveRatio;
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

    public Integer getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(Integer widgetId) {
        this.widgetId = widgetId;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
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

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public boolean isPlayInCurrentPage() {
        return playInCurrentPage;
    }

    public void setPlayInCurrentPage(boolean playInCurrentPage) {
        this.playInCurrentPage = playInCurrentPage;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(final int videoId) {
        this.videoId = videoId;
    }

    public Integer getVideoImageId() {
        return videoImageId;
    }

    public void setVideoImageId(Integer videoImageId) {
        this.videoImageId = videoImageId;
    }
}