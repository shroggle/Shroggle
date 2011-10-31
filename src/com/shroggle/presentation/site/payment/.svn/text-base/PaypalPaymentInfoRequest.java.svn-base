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

import com.shroggle.entity.ChargeType;
import com.shroggle.entity.PaymentSettingsOwnerType;
import com.shroggle.entity.PaymentReason;

import java.util.Date;
import java.util.Random;

/**
 * @author Balakirev Anatoliy
 *         Date: 10.09.2009
 */
public class PaypalPaymentInfoRequest implements PaypalPaymentInfoGenericRequest{

    public PaypalPaymentInfoRequest(
            final int paymentSettingsOwnerId, final PaymentSettingsOwnerType paymentSettingsOwnerType,
            final ChargeType chargeType, final String redirectToUrl, final String redirectOnErrorUrl,
            final PaymentReason paymentReason, final Integer userId) {
        this.paymentSettingsOwnerId = paymentSettingsOwnerId;
        this.paymentSettingsOwnerType = paymentSettingsOwnerType;
        this.chargeType = chargeType;
        this.redirectToUrl = redirectToUrl;
        this.redirectOnErrorUrl = redirectOnErrorUrl;
        this.paymentReason = paymentReason;
        this.userId = userId;
    }

    private final int paymentSettingsOwnerId;

    private final ChargeType chargeType;

    private final String redirectToUrl;

    private final String redirectOnErrorUrl;

    private final PaymentSettingsOwnerType paymentSettingsOwnerType;

    private final PaymentReason paymentReason;

    private final Integer userId;

    public PaymentReason getPaymentReason() {
        return paymentReason;
    }

    public String getRedirectOnErrorUrl() {
        return redirectOnErrorUrl;
    }

    public PaymentSettingsOwnerType getPaymentSettingsOwnerType() {
        return paymentSettingsOwnerType;
    }

    public int getPaymentSettingsOwnerId() {
        return paymentSettingsOwnerId;
    }

    public ChargeType getChargeType() {
        return chargeType;
    }

    public String getRedirectToUrl() {
        return redirectToUrl;
    }

    public Integer getUserId() {
        return userId;
    }

}
