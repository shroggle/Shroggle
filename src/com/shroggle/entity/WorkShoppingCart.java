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

/**
 * @author Artem Stasuk
 */
@Entity(name = "workShoppingCarts")
public class WorkShoppingCart extends WorkItem implements ShoppingCart {

    @Override
    public ItemType getItemType() {
        return ItemType.SHOPPING_CART;
    }

    @Override
    public boolean isDescriptionAfterName() {
        return descriptionAfterName;
    }

    @Override
    public void setDescriptionAfterName(boolean descriptionAfterName) {
        this.descriptionAfterName = descriptionAfterName;
    }

    @Override
    public int getImageHeight() {
        return imageHeight;
    }

    @Override
    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    @Override
    public boolean isImageInFirstColumn() {
        return imageInFirstColumn;
    }

    @Override
    public void setImageInFirstColumn(boolean imageInFirstColumn) {
        this.imageInFirstColumn = imageInFirstColumn;
    }

    @Override
    public int getImageWidth() {
        return imageWidth;
    }

    @Override
    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    private boolean imageInFirstColumn = true;
    private int imageWidth = 120;
    private int imageHeight = 120;
    private boolean descriptionAfterName;

}