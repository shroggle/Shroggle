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

import javax.persistence.Column;

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject
public class ChildSiteLinkData {

    public ChildSiteLinkData() {
        childSiteLink = true;
    }

    public ChildSiteLinkData(
            int position, GalleryAlign align, GalleryItemColumn column,
            String itemName, String name, boolean display, int row) {
        this.position = position;
        this.align = align;
        this.column = column;
        this.itemName = itemName;
        this.name = name;
        this.row = row;
        this.display = display;
        childSiteLink = true;
    }

    @RemoteProperty
    private int position;

    @Column(name = "row1")
    private int row;

    @RemoteProperty
    private GalleryAlign align = GalleryAlign.CENTER;

    @RemoteProperty
    private GalleryItemColumn column;

    @RemoteProperty
    private String itemName;

    @RemoteProperty
    private String name;

    @RemoteProperty
    private boolean display;

    @RemoteProperty
    private final boolean childSiteLink;

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

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public boolean isChildSiteLink() {
        return childSiteLink;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
