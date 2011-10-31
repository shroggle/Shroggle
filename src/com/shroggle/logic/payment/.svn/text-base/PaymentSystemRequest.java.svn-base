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
import com.shroggle.entity.CreditCard;
import com.shroggle.entity.PaymentReason;
import com.shroggle.entity.PaymentSettingsOwner;

import java.util.Date;

/**
 * @author Balakirev Anatoliy
 */
public class PaymentSystemRequest {

    public PaymentSystemRequest(PaymentSettingsOwner owner, double price, Integer userId, PaymentReason paymentReason, String paypalToken, CreditCard creditCard, ChargeType chargeType) {
        this.owner = owner;
        this.price = price;
        this.userId = userId;
        this.paymentReason = paymentReason;
        this.paypalToken = paypalToken;
        this.creditCard = creditCard;
        this.chargeType = chargeType;
    }

    private final PaymentSettingsOwner owner;

    private final double price;

    private final Integer userId;

    private final PaymentReason paymentReason;

    private final String paypalToken;

    private final CreditCard creditCard;

    private final ChargeType chargeType;

    private Date newExpirationDate;

    private Date oldExpirationDate;

    public Date getOldExpirationDate() {
        return oldExpirationDate;
    }

    public void setOldExpirationDate(Date oldExpirationDate) {
        this.oldExpirationDate = oldExpirationDate;
    }

    public ChargeType getChargeType() {
        return chargeType;
    }

    public void setNewExpirationDate(Date newExpirationDate) {
        this.newExpirationDate = newExpirationDate;
    }

    public Date getNewExpirationDate() {
        return newExpirationDate;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public String getPaypalToken() {
        return paypalToken;
    }

    public PaymentSettingsOwner getOwner() {
        return owner;
    }

    public double getPrice() {
        return price;
    }

    public Integer getUserId() {
        return userId;
    }

    public PaymentReason getPaymentReason() {
        return paymentReason;
    }
}
