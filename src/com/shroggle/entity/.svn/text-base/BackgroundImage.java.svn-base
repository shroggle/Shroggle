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

import org.directwebremoting.annotations.DataTransferObject;

import javax.persistence.*;
import java.util.Date;

@DataTransferObject
@Entity(name = "backgroundImages")
public class BackgroundImage implements Resource {

    @Id
    private int backgroundImageId;

    @Column(length = 250)
    private String title;

    @Lob
    private String description;

    @Column(length = 250)
    private String keywords;

    @Column(nullable = false)
    private int siteId;

    @Column(nullable = false, length = 10)
    private String sourceExtension;

    @Column(nullable = false, length = 10)
    private String thumbnailExtension;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date created;

    public static final int THUMBNAIL_HEIGHT = 150;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    @Override
    public ResourceSize getResourceSize() {
        return null;
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
        return backgroundImageId;
    }

    @Override
    public String getExtension() {
        return sourceExtension;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.BACKGROUND_IMAGE;
    }

    public int getBackgroundImageId() {
        return backgroundImageId;
    }

    public void setBackgroundImageId(int backgroundImageId) {
        this.backgroundImageId = backgroundImageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSourceExtension() {
        return sourceExtension;
    }

    public void setSourceExtension(String sourceExtension) {
        this.sourceExtension = sourceExtension;
    }

    public String getThumbnailExtension() {
        return thumbnailExtension;
    }

    public void setThumbnailExtension(String thumbnailExtension) {
        this.thumbnailExtension = thumbnailExtension;
    }

}