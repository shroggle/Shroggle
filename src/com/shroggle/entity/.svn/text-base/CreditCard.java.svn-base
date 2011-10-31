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


import com.shroggle.util.persistance.hibernate.CrypedStringUserType;
import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.*;

@DataTransferObject
@TypeDef(name = "cryptedString", typeClass = CrypedStringUserType.class)
@Entity(name = "creditCards")
public class CreditCard {

    @Id
    private int creditCardId;

    @Column(length = 25, nullable = false)
    @Enumerated(EnumType.STRING)
    private CreditCardType creditCardType = CreditCardType.VISA;

    @Column(length = 250, nullable = false)
    @Type(type = "cryptedString")
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

    @ManyToOne
    @JoinColumn(nullable = false, name = "userId")
    @ForeignKey(name = "creditCardsUserId")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date notificationMailSent;

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public CreditCardType getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(CreditCardType creditCardType) {
        this.creditCardType = creditCardType;
    }

    public Date getNotificationMailSent() {
        return notificationMailSent;
    }

    public void setNotificationMailSent(Date notificationMailSent) {
        this.notificationMailSent = notificationMailSent;
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

    public int getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(int creditCardId) {
        this.creditCardId = creditCardId;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
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

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

//    public void addSite(final Site site) {
//        sites.add(site);
//    }

//    public List<Site> getAllSites() {
//        return Collections.unmodifiableList(sites);
//    }


//    public void removeSite(final Site site) {
//        if (sites != null && site != null) {
//            sites.remove(site);
//        }
//    }

}