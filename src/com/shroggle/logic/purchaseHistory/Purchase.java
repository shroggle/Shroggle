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
package com.shroggle.logic.purchaseHistory;

import com.shroggle.entity.FilledFormItem;

/**
 * @author dmitry.solomadin
 */
public class Purchase {

    private String productName;

    private String productPrice;

    private String purchaseDate;

    private FilledFormItem productImageItem;

    private String productOrderStatus;

    public String getProductOrderStatus() {
        return productOrderStatus;
    }

    public void setProductOrderStatus(String productOrderStatus) {
        this.productOrderStatus = productOrderStatus;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public FilledFormItem getProductImageItem() {
        return productImageItem;
    }

    public void setProductImageItem(FilledFormItem productImageItem) {
        this.productImageItem = productImageItem;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
