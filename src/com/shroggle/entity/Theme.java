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
import org.directwebremoting.annotations.RemoteProperty;

/**
 * @author Stasuk Artem
 */
@DataTransferObject
public class Theme {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getThumbnailFile() {
        return thumbnailFile;
    }

    public void setThumbnailFile(String thumbnailFile) {
        this.thumbnailFile = thumbnailFile;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getColorTileFile() {
        return colorTileFile;
    }

    public void setColorTileFile(String colorTileFile) {
        this.colorTileFile = colorTileFile;
    }

    /**
     * This variable contain path image file. It's represent small image for
     * select checkbox.
     *
     * @link http://jira.web-deva.com/browse/SW-1521
     */
    @RemoteProperty
    private String colorTileFile;

    @RemoteProperty
    private String imageFile;

    @RemoteProperty
    private String thumbnailFile;

    @RemoteProperty
    private String file;

    @RemoteProperty
    private String name;

    private Template template;

}
