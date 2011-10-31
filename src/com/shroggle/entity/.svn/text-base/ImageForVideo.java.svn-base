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
@Entity(name = "imagesForVideo")
public class ImageForVideo implements Resource {

    @Id
    private int imageForVideoId;

    @Column(nullable = false)
    private int siteId;

    @Column(nullable = false, length = 10)
    private String sourceExtension;

    @Column(nullable = false, length = 10)
    private String thumbnailExtension;

    private int width;

    private int height;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date created;

    @Column(length = 250)
    private String title;

    public static final int THUMBNAIL_HEIGHT = 150;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public ResourceSize getResourceSize() {
        return null;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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

    public int getImageForVideoId() {
        return imageForVideoId;
    }

    public void setImageForVideoId(int imageForVideoId) {
        this.imageForVideoId = imageForVideoId;
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
        return imageForVideoId;
    }

    @Override
    public String getExtension() {
        return sourceExtension;
    }

    @Override
    public String getName() {
        return title;
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.IMAGE_FOR_VIDEO;
    }

    public void setSiteId(final int siteId) {
        this.siteId = siteId;
    }

}