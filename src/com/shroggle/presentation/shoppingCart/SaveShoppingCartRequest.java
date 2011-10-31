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
package com.shroggle.presentation.shoppingCart;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

/**
 * @author Artem Stasuk
 */
@DataTransferObject
public class SaveShoppingCartRequest {

    public boolean isDescriptionAfterName() {
        return descriptionAfterName;
    }

    public void setDescriptionAfterName(boolean descriptionAfterName) {
        this.descriptionAfterName = descriptionAfterName;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public boolean isImageInFirstColumn() {
        return imageInFirstColumn;
    }

    public void setImageInFirstColumn(boolean imageInFirstColumn) {
        this.imageInFirstColumn = imageInFirstColumn;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public boolean isShowDescription() {
        return showDescription;
    }

    public void setShowDescription(boolean showDescription) {
        this.showDescription = showDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(Integer widgetId) {
        this.widgetId = widgetId;
    }

    @RemoteProperty
    private boolean showDescription;

    @RemoteProperty
    private String description;

    @RemoteProperty
    private boolean imageInFirstColumn = true;

    @RemoteProperty
    private int imageWidth = 120;

    @RemoteProperty
    private int imageHeight = 120;

    @RemoteProperty
    private boolean descriptionAfterName;

    @RemoteProperty
    private Integer widgetId;

    @RemoteProperty
    private int id;

    @RemoteProperty
    private String name;

}