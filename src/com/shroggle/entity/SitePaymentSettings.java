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

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.Date;

/**
 * Author: dmitry.solomadin
 */
@Entity(name = "sitePaymentSettings")
public class SitePaymentSettings {

    public SitePaymentSettings() {
        paymentMethod = PaymentMethod.AUTHORIZE_NET;
        chargeType = ChargeType.SITE_MONTHLY_FEE;
        price = 0;
        siteStatus = SiteStatus.PENDING;
        remainingTimeOfUsage = null;
        recurringPaymentId = null;
        userId = null;
    }

    @Id
    private int sitePaymentSettingsId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    private double price;

    /*PAYPAL SPECIFIC*/
    @Column(length = 250)
    private String recurringPaymentId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ChargeType chargeType;

    // We use this parameter only for suspended payment settings
    private Long remainingTimeOfUsage;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private SiteStatus siteStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    @OneToOne
    @JoinColumn(name = "creditCardId")
    @ForeignKey(name = "sitePaymentSettingsCreditCardId")
    private CreditCard creditCard;

    @Column
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public SiteStatus getSiteStatus() {
        return siteStatus;
    }

    public void setSiteStatus(SiteStatus siteStatus) {
        this.siteStatus = siteStatus;
    }

    public Long getRemainingTimeOfUsage() {
        return remainingTimeOfUsage;
    }

    public void setRemainingTimeOfUsage(Long remainingTimeOfUsage) {
        this.remainingTimeOfUsage = remainingTimeOfUsage;
    }

    public ChargeType getChargeType() {
        return chargeType;
    }

    public void setChargeType(ChargeType chargeType) {
        this.chargeType = chargeType;
    }

    public int getSitePaymentSettingsId() {
        return sitePaymentSettingsId;
    }

    public void setSitePaymentSettingsId(int sitePaymentSettingsId) {
        this.sitePaymentSettingsId = sitePaymentSettingsId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getRecurringPaymentId() {
        return recurringPaymentId;
    }

    public void setRecurringPaymentId(String recurringPaymentId) {
        this.recurringPaymentId = recurringPaymentId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
