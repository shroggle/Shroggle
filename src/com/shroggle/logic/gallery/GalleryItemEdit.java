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
package com.shroggle.logic.gallery;

import com.shroggle.entity.GalleryAlign;
import com.shroggle.entity.GalleryItemColumn;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

/**
 * @author Artem Stasuk
 */
@DataTransferObject
public class GalleryItemEdit implements GalleryIdedEdit {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GalleryAlign getAlign() {
        return align;
    }

    public void setAlign(GalleryAlign align) {
        this.align = align;
    }

    public GalleryItemColumn getColumn() {
        return column;
    }

    public void setColumn(GalleryItemColumn column) {
        this.column = column;
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

    public int getRow() {
        return row;
    }

    public void setRow(final int row) {
        this.row = row;
    }

    public boolean isCrop() {
        return crop != null && crop;
    }

    public void setCrop(Boolean crop) {
        this.crop = crop;
    }

    @RemoteProperty
    private int id;

    @RemoteProperty
    private String name;

    @RemoteProperty
    private GalleryAlign align = GalleryAlign.LEFT;

    @RemoteProperty
    private GalleryItemColumn column;

    @RemoteProperty
    private Integer width;

    @RemoteProperty
    private int row;

    @RemoteProperty
    private Boolean crop;

    @RemoteProperty
    private Integer height;

}
