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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@DataTransferObject
@Entity(name = "formFiles")
public class FormFile implements Resource {

    @Id
    private int formFileId;

    @Column(nullable = false, length = 10)
    private String sourceExtension;

    @Column(nullable = false, length = 250)
    private String sourceName;

    @Column(nullable = false)
    private int siteId;

    private Integer width;

    private Integer height;

    @Transient
    public static final int MAX_SOURCE_IMAGE_WIDTH = 1280;

    @Transient
    public static final int MAX_SOURCE_IMAGE_HEIGHT = 1024;

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

    public int getSiteId() {
        return siteId;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public ResourceSize getResourceSize() {
        return null;
    }

    @Override
    public int getResourceId() {
        return formFileId;
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
        return ResourceType.FORM_FILE;
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


    public int getFormFileId() {
        return formFileId;
    }

    public void setFormFileId(int formFileId) {
        this.formFileId = formFileId;
    }
}