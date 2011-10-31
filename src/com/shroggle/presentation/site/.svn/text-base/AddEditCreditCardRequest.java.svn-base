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

package com.shroggle.presentation.site;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import com.shroggle.entity.ChargeType;
import com.shroggle.entity.CreditCardType;
import com.shroggle.entity.Country;


@DataTransferObject
public class AddEditCreditCardRequest {

    @RemoteProperty
    private boolean updateCCInfo;

    @RemoteProperty
    private boolean useInfo;

    @RemoteProperty
    private Integer childSiteSettingsId;

    @RemoteProperty
    private Integer updatedCCId;

    @RemoteProperty
    private CreditCardType creditCardType;

    @RemoteProperty
    private String creditCardNumber;

    @RemoteProperty
    private Byte creditCardExpirationMonth;

    @RemoteProperty
    private Short creditCardExpirationYear;

    @RemoteProperty
    private String securityCode;

    @RemoteProperty
    private String billingAddress1;

    @RemoteProperty
    private String billingAddress2;

    @RemoteProperty
    private String city;

    @RemoteProperty
    private Country country = Country.US;

    @RemoteProperty
    private String region;

    @RemoteProperty
    private String postalCode;

    @RemoteProperty
    private ChargeType chargeType;

    @RemoteProperty
    private Integer childSiteUserId;

    public Integer getChildSiteUserId() {
        return childSiteUserId;
    }

    public void setChildSiteUserId(Integer childSiteUserId) {
        this.childSiteUserId = childSiteUserId;
    }

    public ChargeType getChargeType() {
        return chargeType;
    }

    public void setChargeType(ChargeType chargeType) {
        this.chargeType = chargeType;
    }

    public Integer getChildSiteSettingsId() {
        return childSiteSettingsId;
    }

    public void setChildSiteSettingsId(Integer childSiteSettingsId) {
        this.childSiteSettingsId = childSiteSettingsId;
    }

    public Integer getUpdatedCCId() {
        return updatedCCId;
    }

    public void setUpdatedCCId(Integer updatedCCId) {
        this.updatedCCId = updatedCCId;
    }

    public boolean isUpdateCCInfo() {
        return updateCCInfo;
    }

    public void setUpdateCCInfo(boolean updateCCInfo) {
        this.updateCCInfo = updateCCInfo;
    }

    public boolean isUseInfo() {
        return useInfo;
    }

    public void setUseInfo(boolean useInfo) {
        this.useInfo = useInfo;
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

    public Byte getCreditCardExpirationMonth() {
        return creditCardExpirationMonth;
    }

    public void setCreditCardExpirationMonth(Byte creditCardExpirationMonth) {
        this.creditCardExpirationMonth = creditCardExpirationMonth;
    }

    public Short getCreditCardExpirationYear() {
        return creditCardExpirationYear;
    }

    public void setCreditCardExpirationYear(Short creditCardExpirationYear) {
        this.creditCardExpirationYear = creditCardExpirationYear;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
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
}
