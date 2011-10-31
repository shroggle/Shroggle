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

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Balakirev Anatoliy
 */
@Entity(name = "flvVideos")
public class FlvVideo implements Resource {

    @Id
    private int flvVideoId;

    private int siteId;

    private int sourceVideoId;

    private Integer width;

    private Integer height;

    private int quality = DEFAULT_VIDEO_QUALITY;


    public static final int MIN_VIDEO_QUALITY = 31;
    public static final int MAX_VIDEO_QUALITY = 1;
    public static final int DEFAULT_VIDEO_QUALITY = 7;

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
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

    public int getSourceVideoId() {
        return sourceVideoId;
    }

    public void setSourceVideoId(int sourceVideoId) {
        this.sourceVideoId = sourceVideoId;
    }

    public int getFlvVideoId() {
        return flvVideoId;
    }

    public void setFlvVideoId(int flvVideoId) {
        this.flvVideoId = flvVideoId;
    }

    public int getSiteId() {
        return siteId;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public int getResourceId() {
        return sourceVideoId;
    }

    @Override
    public String getExtension() {
        return "flv";
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.VIDEO_FLV;
    }

    @Override
    public ResourceSize getResourceSize() {
        return ResourceSizeCustom.createByWidthHeight(width, height);
    }

    public void setSiteId(final int siteId) {
        this.siteId = siteId;
    }

}