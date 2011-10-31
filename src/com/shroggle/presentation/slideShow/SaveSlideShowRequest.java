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
package com.shroggle.presentation.slideShow;

import com.shroggle.entity.SlideShowDisplayType;
import com.shroggle.entity.SlideShowTransitionEffectType;
import com.shroggle.entity.SlideShowType;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
public class SaveSlideShowRequest {

    @RemoteProperty
    private Integer widgetId;

    @RemoteProperty
    private int slideShowId;

    @RemoteProperty
    private String name;

    @RemoteProperty
    private boolean showHeader;

    @RemoteProperty
    private String header;

    @RemoteProperty
    private Integer selectedFormId;

    @RemoteProperty
    private Integer selectedFilterId;

    @RemoteProperty
    private Integer selectedLinkBackToGalleryWidgetId;

    @RemoteProperty
    private int imageWidth;

    @RemoteProperty
    private int imageHeight;

    @RemoteProperty
    private SlideShowTransitionEffectType transitionEffectType;

    @RemoteProperty
    private int numberOfImagesShown;

    @RemoteProperty
    private SlideShowDisplayType displayType;

    @RemoteProperty
    private boolean displayControls;

    @RemoteProperty
    private boolean autoPlay;

    @RemoteProperty
    private int autoPlayInterval;

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

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public SlideShowTransitionEffectType getTransitionEffectType() {
        return transitionEffectType;
    }

    public void setTransitionEffectType(SlideShowTransitionEffectType transitionEffectType) {
        this.transitionEffectType = transitionEffectType;
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

    public boolean isDisplayControls() {
        return displayControls;
    }

    public void setDisplayControls(boolean displayControls) {
        this.displayControls = displayControls;
    }

    public Integer getSelectedFormId() {
        return selectedFormId;
    }

    public void setSelectedFormId(Integer selectedFormId) {
        this.selectedFormId = selectedFormId;
    }

    public Integer getSelectedFilterId() {
        return selectedFilterId;
    }

    public void setSelectedFilterId(Integer selectedFilterId) {
        this.selectedFilterId = selectedFilterId;
    }

    public Integer getSelectedLinkBackToGalleryWidgetId() {
        return selectedLinkBackToGalleryWidgetId;
    }

    public void setSelectedLinkBackToGalleryWidgetId(Integer selectedLinkBackToGalleryWidgetId) {
        this.selectedLinkBackToGalleryWidgetId = selectedLinkBackToGalleryWidgetId;
    }

    public Integer getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(Integer widgetId) {
        this.widgetId = widgetId;
    }

    public int getSlideShowId() {
        return slideShowId;
    }

    public void setSlideShowId(int slideShowId) {
        this.slideShowId = slideShowId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShowHeader() {
        return showHeader;
    }

    public void setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
