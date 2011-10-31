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
package com.shroggle.presentation.site.payment;

import com.shroggle.entity.PaymentReason;

/**
 * @author dmitry.solomadin
 */
public class PaypalButtonPaymentInfoRequest implements PaypalPaymentInfoGenericRequest {

    public PaypalButtonPaymentInfoRequest(double priceWithTax,
                                          int productFilledItemId, int priceFilledItemId,
                                          int subscriptionFilledItemId, Integer groupsFilledItemId,
                                          int galleryId, Integer userId, int filledFormId,
                                          Integer registrationFormId,
                                          String redirectToUrl, String redirectOnErrorUrl) {
        this.priceWithTax = priceWithTax;
        this.productFilledItemId = productFilledItemId;
        this.priceFilledItemId = priceFilledItemId;
        this.subscriptionFilledItemId = subscriptionFilledItemId;
        this.groupsFilledItemId = groupsFilledItemId;
        this.galleryId = galleryId;
        this.userId = userId;
        this.filledFormId = filledFormId;
        this.registrationFormId = registrationFormId;
        this.redirectToUrl = redirectToUrl;
        this.redirectOnErrorUrl = redirectOnErrorUrl;
    }

    private final int productFilledItemId;

    private final int priceFilledItemId;

    private final int subscriptionFilledItemId;

    private final Integer groupsFilledItemId;

    private final int galleryId;

    private final Integer userId;

    private final int filledFormId;

    private final Integer registrationFormId;

    private final String redirectToUrl;

    private final String redirectOnErrorUrl;

    private final PaymentReason paymentReason = PaymentReason.PAYPAL_BUTTON_RECURRING_PAYMENT;

    private final double priceWithTax;

    public double getPriceWithTax() {
        return priceWithTax;
    }

    public Integer getRegistrationFormId() {
        return registrationFormId;
    }

    public int getFilledFormId() {
        return filledFormId;
    }

    public int getGalleryId() {
        return galleryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public PaymentReason getPaymentReason() {
        return paymentReason;
    }

    public String getRedirectOnErrorUrl() {
        return redirectOnErrorUrl;
    }

    public String getRedirectToUrl() {
        return redirectToUrl;
    }

    public int getProductFilledItemId() {
        return productFilledItemId;
    }

    public int getPriceFilledItemId() {
        return priceFilledItemId;
    }

    public int getSubscriptionFilledItemId() {
        return subscriptionFilledItemId;
    }

    public Integer getGroupsFilledItemId() {
        return groupsFilledItemId;
    }
}
