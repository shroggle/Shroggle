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
@Entity(name = "icons")
public class Icon implements Resource {

    @Id
    private int iconId;

    @Column(length = 250, nullable = false)
    private String name;

    @Column(nullable = false, length = 10)
    private String extension;

    private Integer width;

    private Integer height;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date created = new Date();

    @Transient
    private final static int DEFAULT_WIDTH = 16;

    @Transient
    private final static int DEFAULT_HEIGHT = 16;

    public static int getDefaultWidth() {
        return DEFAULT_WIDTH;
    }

    public static int getDefaultHeight() {
        return DEFAULT_HEIGHT;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getSiteId() {
        return -1;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public int getResourceId() {
        return getIconId();
    }

    public String getName() {
        return name;
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.ICON;
    }

    @Override
    public ResourceSize getResourceSize() {
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

}
