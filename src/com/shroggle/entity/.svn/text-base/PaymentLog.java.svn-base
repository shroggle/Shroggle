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

import com.shroggle.util.cache.CachePolicy;
import com.shroggle.logic.site.payment.TransactionStatus;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Date;

/**
 * @author dmitry.solomadin
 */
@CachePolicy(maxElementsInMemory = 1000)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity(name = "paymentLogs")
public class PaymentLog {

    @Id
    private int logId;

    @Lob
    @Column(updatable = false, nullable = false)
    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, nullable = false)
    private Date creationDate = new Date();

    @Column
    private Integer siteId;

    @Column
    private Integer childSiteSettingsId;


    /* Represents instant sum. Sum that was payed in current logged transaction */
    private String sum;

    /* Represents monthly sum. Sum that is set for paypal recurring payment profile. */
    private String monthlySum;

    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(value = EnumType.STRING)
    private PaymentReason paymentReason;

    private String recurringPaymentProfileId;

    @Lob
    private String errorMessage;

    private String transactionId;

    private String recieverEmail;

    private String profileStatus;

    //Id of logined user during operation execution.
    private Integer userId;

    private Integer filledFormId;

    /*---------------------------------------------CREDIT CARD PROPERTIES---------------------------------------------*/

    @Column
    private Integer creditCardId;

    @Column(length = 25)
    @Enumerated(EnumType.STRING)
    private CreditCardType creditCardType;

    @Column(length = 250)
    private String creditCardNumber = "";

    private short expirationYear = -1;

    private byte expirationMonth = -1;

    @Column(length = 250)
    private String securityCode = "";

    private boolean useContactInfo = false;

    @Column(length = 250)
    private String billingAddress1 = "";

    @Column(length = 250)
    private String billingAddress2 = "";

    @Column(length = 250)
    private String city = "";

    @Enumerated(EnumType.STRING)
    @Column(length = 2, nullable = false)
    private Country country = Country.US;

    @Column(length = 250)
    private String region = "";

    @Column(length = 250)
    private String postalCode = "";

    /*-------------------------------------------CREDIT CARD PROPERTIES END-------------------------------------------*/

    public Integer getFilledFormId() {
        return filledFormId;
    }

    public void setFilledFormId(Integer filledFormId) {
        this.filledFormId = filledFormId;
    }

    public CreditCardType getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(CreditCardType creditCardType) {
        this.creditCardType = creditCardType;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public short getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(short expirationYear) {
        this.expirationYear = expirationYear;
    }

    public byte getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(byte expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public boolean isUseContactInfo() {
        return useContactInfo;
    }

    public void setUseContactInfo(boolean useContactInfo) {
        this.useContactInfo = useContactInfo;
    }

    public String getBillingAddress1() {
        return billingAddress1;
    }

    public void setBillingAddress1(String billingAddress1) {
        this.billingAddress1 = billingAddress1;
    }

    public String getBillingAddress2() {
        return billingAddress2;
    }

    public void setBillingAddress2(String billingAddress2) {
        this.billingAddress2 = billingAddress2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public PaymentReason getPaymentReason() {
        return paymentReason;
    }

    public void setPaymentReason(PaymentReason paymentReason) {
        this.paymentReason = paymentReason;
    }

    public String getProfileStatus() {
        return profileStatus;
    }

    public void setProfileStatus(String profileStatus) {
        this.profileStatus = profileStatus;
    }

    public Integer getChildSiteSettingsId() {
        return childSiteSettingsId;
    }

    public void setChildSiteSettingsId(Integer childSiteSettingsId) {
        this.childSiteSettingsId = childSiteSettingsId;
    }

    public Integer getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Integer creditCardId) {
        this.creditCardId = creditCardId;
    }

    public String getMonthlySum() {
        return monthlySum;
    }

    public void setMonthlySum(String monthlySum) {
        this.monthlySum = monthlySum;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getRecieverEmail() {
        return recieverEmail;
    }

    public void setRecieverEmail(String recieverEmail) {
        this.recieverEmail = recieverEmail;
    }

    public String getRecurringPaymentProfileId() {
        return recurringPaymentProfileId;
    }

    public void setRecurringPaymentProfileId(String recurringPaymentProfileId) {
        this.recurringPaymentProfileId = recurringPaymentProfileId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
