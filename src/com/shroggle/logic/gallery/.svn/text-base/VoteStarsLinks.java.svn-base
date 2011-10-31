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
 * @author Balakirev Anatoliy
 */
@DataTransferObject
public class VoteStarsLinks {

    public VoteStarsLinks(
            int position, GalleryAlign align, GalleryItemColumn column,
            String name, String itemName, int row) {
        this.position = position;
        this.row = row;
        this.align = align;
        this.column = column;
        this.name = name;
        this.itemName = itemName;
    }

    @RemoteProperty
    private int row;

    @RemoteProperty
    private int position;

    @RemoteProperty
    private GalleryAlign align = GalleryAlign.CENTER;

    @RemoteProperty
    private GalleryItemColumn column = GalleryItemColumn.COLUMN_1;

    @RemoteProperty
    private String itemName;

    @RemoteProperty
    private String name;

    @RemoteProperty
    private final boolean hideCheckbox = true;

    public boolean isHideCheckbox() {
        return hideCheckbox;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
