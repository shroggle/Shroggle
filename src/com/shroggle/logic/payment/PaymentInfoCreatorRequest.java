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
package com.shroggle.logic.payment;

import com.shroggle.entity.ChargeType;
import com.shroggle.entity.PaymentMethod;
import com.shroggle.entity.PaymentSettingsOwnerType;
import com.shroggle.entity.CreditCard;

/**
 * @author Balakirev Anatoliy
 */
public class PaymentInfoCreatorRequest {

    public PaymentInfoCreatorRequest(int paymentSettingsOwnerId, PaymentSettingsOwnerType paymentSettingsOwnerType, ChargeType chargeType, PaymentMethod paymentMethod, Integer userId, String paypalToken, CreditCard creditCard) {
        this.paymentSettingsOwnerId = paymentSettingsOwnerId;
        this.paymentSettingsOwnerType = paymentSettingsOwnerType;
        this.chargeType = chargeType;
        this.paymentMethod = paymentMethod;
        this.userId = userId;
        this.paypalToken = paypalToken;
        this.creditCard = creditCard;
    }

    private int paymentSettingsOwnerId;

    private PaymentSettingsOwnerType paymentSettingsOwnerType;

    private ChargeType chargeType;

    private PaymentMethod paymentMethod;

    private Integer userId;

    private String paypalToken;

    private CreditCard creditCard;

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public String getPaypalToken() {
        return paypalToken;
    }

    public void setPaypalToken(String paypalToken) {
        this.paypalToken = paypalToken;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public ChargeType getChargeType() {
        return chargeType;
    }

    public void setChargeType(ChargeType chargeType) {
        this.chargeType = chargeType;
    }

    public int getPaymentSettingsOwnerId() {
        return paymentSettingsOwnerId;
    }

    public void setPaymentSettingsOwnerId(int paymentSettingsOwnerId) {
        this.paymentSettingsOwnerId = paymentSettingsOwnerId;
    }

    public PaymentSettingsOwnerType getPaymentSettingsOwnerType() {
        return paymentSettingsOwnerType;
    }

    public void setPaymentSettingsOwnerType(PaymentSettingsOwnerType paymentSettingsOwnerType) {
        this.paymentSettingsOwnerType = paymentSettingsOwnerType;
    }
}
