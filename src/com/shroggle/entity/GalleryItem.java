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

/**
 * @author Artem Stasuk
 */
public interface GalleryItem {

    public boolean isCrop();

    public void setCrop(boolean crop);

    public GalleryItemId getId();

    public String getName();

    public void setName(String name);

    public int getPosition();

    public void setPosition(int position);

    public GalleryAlign getAlign();

    public void setAlign(GalleryAlign align);

    public GalleryItemColumn getColumn();

    public void setColumn(GalleryItemColumn column);

    public Integer getWidth();

    public void setWidth(Integer width);

    public Integer getHeight();

    public void setHeight(Integer height);

    public int getRow();

    public int getItemId();

    public void setRow(final int row);

    public void setId(final GalleryItemId id);

    public String toString();

}