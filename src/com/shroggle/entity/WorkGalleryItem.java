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

/**
 * @author Artem Stasuk
 */
@Entity(name = "workGalleryItems")
public class WorkGalleryItem implements GalleryItem {

    public WorkGalleryItem() {
        this.name = "";
        this.position = -1;
        this.align = GalleryAlign.LEFT;
        this.width = 100;
        this.height = 100;
    }

    public GalleryItemId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public int getItemId() {
        return id.getFormItemId();
    }

    public void setRow(final int row) {
        this.row = row;
    }

    public boolean isCrop() {
        return crop;
    }

    public void setCrop(boolean crop) {
        this.crop = crop;
    }

    /**
     * Used only for copier
     *
     * @param id - id
     * @see com.shroggle.util.copier.CopierWorker
     */
    public void setId(final GalleryItemId id) {
        this.id = (WorkGalleryItemId)id;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[id: " + id + "]";
    }

    @EmbeddedId
    private WorkGalleryItemId id = new WorkGalleryItemId();

    @Column(length = 250)
    private String name;

    private int position;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private GalleryAlign align = GalleryAlign.LEFT;

    @Column(name = "column1", length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private GalleryItemColumn column;

    @Column(name = "row1")
    private int row;

    private Integer width;

    private Integer height;

    private boolean crop;

}