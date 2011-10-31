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
 * @author Balakirev Anatoliy
 */


@Entity(name = "imageFiles")
public class ImageFile implements Resource {

    @Id
    private int imageFileId;

    @Column(nullable = false, length = 10)
    private String sourceExtension;

    @Column(nullable = false, length = 250)
    private String sourceName;

    @Column(nullable = false)
    private int siteId;

    @Column(nullable = false)
    ImageFileType imageFileType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date created;

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

    public ImageFileType getImageFileType() {
        return imageFileType;
    }

    public void setImageFileType(ImageFileType imageFileType) {
        this.imageFileType = imageFileType;
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
        return imageFileId;
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
        return ResourceType.IMAGE_FILE;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceExtension() {
        return sourceExtension;
    }

    public void setSourceExtension(String sourceExtension) {
        this.sourceExtension = sourceExtension;
    }

    public int getImageFileId() {
        return imageFileId;
    }

    public void setImageFileId(int imageFileId) {
        this.imageFileId = imageFileId;
    }
}