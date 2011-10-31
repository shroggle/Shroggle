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

import javax.persistence.Entity;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
@Entity(name = "draftPurchaseHistory")
public class DraftPurchaseHistory extends DraftItem implements PurchaseHistory {

    private boolean showProductImage = true;

    private int imageWidth = 120;

    private int imageHeight = 120;

    private boolean showProductDescription = false;

    public boolean isShowProductDescription() {
        return showProductDescription;
    }

    public void setShowProductDescription(boolean showProductDescription) {
        this.showProductDescription = showProductDescription;
    }

    public boolean isShowProductImage() {
        return showProductImage;
    }

    public void setShowProductImage(boolean showProductImage) {
        this.showProductImage = showProductImage;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public ItemType getItemType() {
        return ItemType.PURCHASE_HISTORY;
    }
}
