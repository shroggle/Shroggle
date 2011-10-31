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
import javax.persistence.Column;

/**
 * @author Balakirev Anatoliy
 */
@Entity(name = "menuImages")
public class MenuImage implements Resource {

    @Id
    private int menuImageId;

    @Column(nullable = false)
    private int siteId;

    @Column(nullable = false, length = 10)
    private String extension;

    @Column(nullable = false)
    private String name;

    public void setMenuImageId(int menuImageId) {
        this.menuImageId = menuImageId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMenuImageId() {
        return menuImageId;
    }

    public int getSiteId() {
        return siteId;
    }

    @Override
    public ResourceSize getResourceSize() {
        return null;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public int getResourceId() {
        return menuImageId;
    }

    @Override
    public String getExtension() {
        return extension;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ResourceType getResourceType() {
        return ResourceType.MENU_IMAGE;
    }

}
