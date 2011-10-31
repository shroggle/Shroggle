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
package com.shroggle.logic.gallery.paypal;

/**
 * @author dmitry.solomadin
 */
public class PaypalButtonIPNRequest {

    private int galleryId;

    private int userId;

    private double priceWithTax;

    private int siteId;

    private int productNameFilledItemId;

    private int priceFilledItemId;

    private Integer groupsFilledItemId;

    private int registrationFormId;

    public double getPriceWithTax() {
        return priceWithTax;
    }

    public void setPriceWithTax(double price, double tax) {
        this.priceWithTax = price + tax; // Tax is not in percent here.
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public int getRegistrationFormId() {
        return registrationFormId;
    }

    public void setRegistrationFormId(int registrationFormId) {
        this.registrationFormId = registrationFormId;
    }

    public int getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(int galleryId) {
        this.galleryId = galleryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductNameFilledItemId() {
        return productNameFilledItemId;
    }

    public void setProductNameFilledItemId(int productNameFilledItemId) {
        this.productNameFilledItemId = productNameFilledItemId;
    }

    public int getPriceFilledItemId() {
        return priceFilledItemId;
    }

    public void setPriceFilledItemId(int priceFilledItemId) {
        this.priceFilledItemId = priceFilledItemId;
    }

    public Integer getGroupsFilledItemId() {
        return groupsFilledItemId;
    }

    public void setGroupsFilledItemId(Integer groupsFilledItemId) {
        this.groupsFilledItemId = groupsFilledItemId;
    }
}
