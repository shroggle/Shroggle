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
@Entity(name = "images")
public class Image implements Resource {

    @Id
    private int imageId;

    private int siteId;

    @Column(length = 250, nullable = false)
    private String name;

    @Lob
    private String description;

    @Column(length = 250)
    private String keywords;

    @Column(nullable = false, length = 10)
    private String sourceExtension;

    @Column(nullable = false, length = 10)
    private String thumbnailExtension;

    private Integer width;

    private Integer height;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date created = new Date();

    public static final int THUMBNAIL_HEIGHT = 120;

    public Date getCreated() {
        return created;
    }

    @Override
    public ResourceSize getResourceSize() {
        return null;
    }

    public void setCreated(Date created) {
        this.created = created;
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
        return imageId;
    }

    @Override
    public String getExtension() {
        return sourceExtension;
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.IMAGE;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
