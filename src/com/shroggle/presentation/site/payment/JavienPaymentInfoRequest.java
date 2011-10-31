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

/**
 * @author Balakirev Anatoliy
 *         Date: 09.09.2009
 */
public class JavienPaymentInfoRequest {

    public JavienPaymentInfoRequest(Integer cardId, ChargeType chargeType, PaymentSettingsOwnerType paymentSettingsOwnerType, int paymentSettingsOwnerId, Integer userId) {
        this.cardId = cardId;
        this.chargeType = chargeType;
        this.paymentSettingsOwnerType = paymentSettingsOwnerType;
        this.paymentSettingsOwnerId = paymentSettingsOwnerId;
        this.userId = userId;
    }

    private final Integer cardId;

    private final ChargeType chargeType;

    private final PaymentSettingsOwnerType paymentSettingsOwnerType;

    private final int paymentSettingsOwnerId;

    public PaymentSettingsOwnerType getPaymentSettingsOwnerType() {
        return paymentSettingsOwnerType;
    }

    public int getPaymentSettingsOwnerId() {
        return paymentSettingsOwnerId;
    }

    private final Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public Integer getCardId() {
        return cardId;
    }

    public ChargeType getChargeType() {
        return chargeType;
    }
}
