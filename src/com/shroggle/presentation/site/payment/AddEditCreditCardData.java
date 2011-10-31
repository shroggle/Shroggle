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

import com.shroggle.util.StringUtil;
import com.shroggle.entity.CreditCardType;
import com.shroggle.entity.CreditCard;
import com.shroggle.entity.User;
import com.shroggle.entity.Country;
import com.shroggle.logic.countries.CountryManager;

import java.util.List;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;

/**
 * @author Balakirev Anatoliy
 */
public class AddEditCreditCardData {

    public AddEditCreditCardData(CreditCard creditCard, final Integer widgetId, User user, final boolean disableDataAndHideCCNumber) {
        creditCard = creditCard != null ? creditCard : new CreditCard();
        this.widgetId = widgetId != null ? widgetId : 0;
        country = creditCard.getCountry();
        states = new CountryManager(country).getStatesByCountry();
        creditCardType = creditCard.getCreditCardType();
        creditCardNumber = createCreditCardNumber(creditCard.getCreditCardNumber(), disableDataAndHideCCNumber);
        securityCode = creditCard.getSecurityCode();
        useContactInfo = creditCard.isUseContactInfo();
        billingAddress1 = creditCard.getBillingAddress1();
        billingAddress2 = creditCard.getBillingAddress2();
        city = creditCard.getCity();
        region = creditCard.getRegion();
        postalCode = creditCard.getPostalCode();
        final Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        expirationMonth = creditCard.getCreditCardId() > 0 ? creditCard.getExpirationMonth() : (byte) calendar.get(Calendar.MONTH);
        expirationYear = creditCard.getCreditCardId() > 0 ? creditCard.getExpirationYear() : (short) calendar.get(Calendar.YEAR);
        user = user != null ? user : new User();
        userBillingAddress1 = StringUtil.getEmptyOrString(user.getStreet());
        userBillingAddress2 = StringUtil.getEmptyOrString(user.getStreet());
        userCity = StringUtil.getEmptyOrString(user.getCity());
        userCountry = user.getCountry();
        userPostalCode = StringUtil.getEmptyOrString(user.getPostalCode());
        userRegion = StringUtil.getEmptyOrString(user.getRegion());
        disableData = disableDataAndHideCCNumber;
    }

    private String createCreditCardNumber(final String creditCardNumber, final boolean hideNumber) {
        String number = creditCardNumber;
        if (hideNumber) {
            if (number != null && number.length() > 4) {
                number = "************" + number.substring((number.length() - 4), number.length());
            }
        }
        return number;
    }

    private final int widgetId;

    private final List<String> states;

    private final CreditCardType creditCardType;

    private final String creditCardNumber;

    private final byte expirationMonth;

    private final short expirationYear;

    private final String securityCode;

    private final boolean useContactInfo;

    private final String billingAddress1;

    private final String billingAddress2;

    private final String city;

    private final Country country;

    private final String region;

    private final String postalCode;

    private final String userBillingAddress1;

    private final String userBillingAddress2;

    private final String userCity;

    private final Country userCountry;

    private final String userPostalCode;

    private final String userRegion;

    private final boolean disableData;

    public boolean isShowCheckbox() {
        return !(StringUtil.isNullOrEmpty(userBillingAddress1) || StringUtil.isNullOrEmpty(userPostalCode) ||
                StringUtil.isNullOrEmpty(userCity) || StringUtil.isNullOrEmpty(userRegion));
    }

    public int getWidgetId() {
        return widgetId;
    }

    public List<String> getStates() {
        return states;
    }

    public CreditCardType getCreditCardType() {
        return creditCardType;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public byte getExpirationMonth() {
        return expirationMonth;
    }

    public short getExpirationYear() {
        return expirationYear;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public boolean isUseContactInfo() {
        return useContactInfo;
    }

    public String getBillingAddress1() {
        return billingAddress1;
    }

    public String getBillingAddress2() {
        return billingAddress2;
    }

    public String getCity() {
        return city;
    }

    public Country getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getUserBillingAddress1() {
        return userBillingAddress1;
    }

    public String getUserBillingAddress2() {
        return userBillingAddress2;
    }

    public String getUserCity() {
        return userCity;
    }

    public Country getUserCountry() {
        return userCountry;
    }

    public String getUserPostalCode() {
        return userPostalCode;
    }

    public String getUserRegion() {
        return userRegion;
    }

    public boolean isDisableData() {
        return disableData;
    }
}
