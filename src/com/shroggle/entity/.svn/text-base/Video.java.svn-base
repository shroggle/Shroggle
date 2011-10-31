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

import javax.persistence.*;
import java.util.Date;

/**
 * @author Stasuk Artem
 */
@Entity(name = "videos")
public class Video implements Resource {

    @Id
    private int videoId;

    @Column(nullable = false)
    private int siteId;

    private Integer filledFormId;

    private Integer filledFormItemId;

    @Column(nullable = false)
    private String sourceExtension;

    @Column(nullable = false)
    private String sourceName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date created = new Date();

    private int sourceWidth;

    private int sourceHeight;

    public int getSourceWidth() {
        return sourceWidth;
    }

    public void setSourceWidth(int sourceWidth) {
        this.sourceWidth = sourceWidth;
    }

    public int getSourceHeight() {
        return sourceHeight;
    }

    public void setSourceHeight(int sourceHeight) {
        this.sourceHeight = sourceHeight;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
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
        return videoId;
    }

    @Override
    public String getExtension() {
        return sourceExtension;
    }

    @Override
    public String getName() {
        return sourceName;
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.VIDEO;
    }

    @Override
    public ResourceSize getResourceSize() {
        return null;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public String getSourceExtension() {
        return sourceExtension;
    }

    public void setSourceExtension(String sourceExtension) {
        this.sourceExtension = sourceExtension;
    }

    public Integer getFilledFormId() {
        return filledFormId;
    }

    public void setFilledFormId(Integer filledFormId) {
        this.filledFormId = filledFormId;
    }

    public Integer getFilledFormItemId() {
        return filledFormItemId;
    }

    public void setFilledFormItemId(Integer filledFormItemId) {
        this.filledFormItemId = filledFormItemId;
    }
}