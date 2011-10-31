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

import org.junit.Test;
import org.junit.runner.RunWith;
import com.shroggle.entity.CreditCard;
import com.shroggle.entity.CreditCardType;
import com.shroggle.entity.User;
import com.shroggle.entity.Country;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.logic.countries.CountryManager;
import junit.framework.Assert;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class AddEditCreditCardDataTest {

    @Test
    public void create_withCreditCard_withNotHiddenNumber() {
        final CreditCard creditCard = new CreditCard();
        creditCard.setCreditCardId(1);
        creditCard.setBillingAddress1("setBillingAddress1");
        creditCard.setBillingAddress2("setBillingAddress2");
        creditCard.setCreditCardType(CreditCardType.DISCOVER);
        creditCard.setCreditCardNumber("1234567890");
        creditCard.setExpirationYear((short) 2009);
        creditCard.setExpirationMonth((byte) 10);
        creditCard.setSecurityCode("123");
        creditCard.setUseContactInfo(true);
        creditCard.setCity("Kiev");
        creditCard.setCountry(Country.UA);
        creditCard.setRegion("region");

        User user = new User();
        user.setStreet("street");
        user.setCity("city");
        user.setCountry(Country.US);
        user.setRegion("region");
        user.setPostalCode("postalCode");

        AddEditCreditCardData data = new AddEditCreditCardData(creditCard, 10, user, false);
        Assert.assertEquals(data.getCreditCardType(), creditCard.getCreditCardType());
        Assert.assertEquals(data.getCreditCardNumber(), creditCard.getCreditCardNumber());
        Assert.assertEquals(data.getExpirationYear(), creditCard.getExpirationYear());
        Assert.assertEquals(data.getExpirationMonth(), creditCard.getExpirationMonth());
        Assert.assertEquals(data.getSecurityCode(), creditCard.getSecurityCode());
        Assert.assertEquals(data.isUseContactInfo(), creditCard.isUseContactInfo());
        Assert.assertEquals(data.getBillingAddress1(), creditCard.getBillingAddress1());
        Assert.assertEquals(data.getBillingAddress2(), creditCard.getBillingAddress2());
        Assert.assertEquals(data.getCity(), creditCard.getCity());
        Assert.assertEquals(data.getCountry(), creditCard.getCountry());
        Assert.assertEquals(data.getRegion(), creditCard.getRegion());
        Assert.assertEquals(data.getPostalCode(), creditCard.getPostalCode());
        Assert.assertEquals(data.getStates(), new CountryManager(creditCard.getCountry()).getStatesByCountry());
        Assert.assertEquals(data.getWidgetId(), 10);
        Assert.assertEquals(data.getUserBillingAddress1(), user.getStreet());
        Assert.assertEquals(data.getUserBillingAddress2(), user.getStreet());
        Assert.assertEquals(data.getUserCity(), user.getCity());
        Assert.assertEquals(data.getUserCountry(), user.getCountry());
        Assert.assertEquals(data.getUserPostalCode(), user.getPostalCode());
        Assert.assertEquals(data.getUserRegion(), user.getRegion());
        Assert.assertEquals(data.isDisableData(), false);
    }

    @Test
    public void create_withCreditCard_withHiddenNumber() {
        final CreditCard creditCard = new CreditCard();
        creditCard.setCreditCardId(1);
        creditCard.setBillingAddress1("setBillingAddress1");
        creditCard.setBillingAddress2("setBillingAddress2");
        creditCard.setCreditCardType(CreditCardType.DISCOVER);
        creditCard.setCreditCardNumber("1234567890");
        creditCard.setExpirationYear((short) 2009);
        creditCard.setExpirationMonth((byte) 10);
        creditCard.setSecurityCode("123");
        creditCard.setUseContactInfo(true);
        creditCard.setCity("Kiev");
        creditCard.setCountry(Country.UA);
        creditCard.setRegion("region");

        User user = new User();
        user.setStreet("street");
        user.setCity("city");
        user.setCountry(Country.US);
        user.setRegion("region");
        user.setPostalCode("postalCode");

        AddEditCreditCardData data = new AddEditCreditCardData(creditCard, 10, user, true);
        Assert.assertEquals(data.getCreditCardType(), creditCard.getCreditCardType());
        Assert.assertEquals(data.getCreditCardNumber(), "************7890");
        Assert.assertEquals(data.getExpirationYear(), creditCard.getExpirationYear());
        Assert.assertEquals(data.getExpirationMonth(), creditCard.getExpirationMonth());
        Assert.assertEquals(data.getSecurityCode(), creditCard.getSecurityCode());
        Assert.assertEquals(data.isUseContactInfo(), creditCard.isUseContactInfo());
        Assert.assertEquals(data.getBillingAddress1(), creditCard.getBillingAddress1());
        Assert.assertEquals(data.getBillingAddress2(), creditCard.getBillingAddress2());
        Assert.assertEquals(data.getCity(), creditCard.getCity());
        Assert.assertEquals(data.getCountry(), creditCard.getCountry());
        Assert.assertEquals(data.getRegion(), creditCard.getRegion());
        Assert.assertEquals(data.getPostalCode(), creditCard.getPostalCode());
        Assert.assertEquals(data.getStates(), new CountryManager(creditCard.getCountry()).getStatesByCountry());
        Assert.assertEquals(data.getWidgetId(), 10);
        Assert.assertEquals(data.getUserBillingAddress1(), user.getStreet());
        Assert.assertEquals(data.getUserBillingAddress2(), user.getStreet());
        Assert.assertEquals(data.getUserCity(), user.getCity());
        Assert.assertEquals(data.getUserCountry(), user.getCountry());
        Assert.assertEquals(data.getUserPostalCode(), user.getPostalCode());
        Assert.assertEquals(data.getUserRegion(), user.getRegion());
        Assert.assertEquals(data.isDisableData(), true);
    }

    @Test
    public void create_withoutCreditCardAndWidgetId_withNotHiddenNumber() {
        AddEditCreditCardData data = new AddEditCreditCardData(null, null, null, false);
        Assert.assertEquals(data.getCreditCardType(), CreditCardType.VISA);
        Assert.assertEquals(data.getCreditCardNumber(), "");
        final Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        Assert.assertEquals(data.getExpirationYear(), (short) calendar.get(Calendar.YEAR));
        Assert.assertEquals(data.getExpirationMonth(), (byte) calendar.get(Calendar.MONTH));
        Assert.assertEquals(data.getSecurityCode(), "");
        Assert.assertEquals(data.isUseContactInfo(), false);
        Assert.assertEquals(data.getBillingAddress1(), "");
        Assert.assertEquals(data.getBillingAddress2(), "");
        Assert.assertEquals(data.getCity(), "");
        Assert.assertEquals(data.getCountry(), Country.US);
        Assert.assertEquals(data.getRegion(), "");
        Assert.assertEquals(data.getPostalCode(), "");
        Assert.assertEquals(data.getStates(), new CountryManager(Country.US).getStatesByCountry());
        Assert.assertEquals(data.getWidgetId(), 0);
        Assert.assertEquals(data.getUserBillingAddress1(), "");
        Assert.assertEquals(data.getUserBillingAddress2(), "");
        Assert.assertEquals(data.getUserCity(), "");
        Assert.assertEquals(data.getUserCountry(), Country.US);
        Assert.assertEquals(data.getUserPostalCode(), "");
        Assert.assertEquals(data.getUserRegion(), "");
        Assert.assertEquals(data.isDisableData(), false);
    }

    @Test
    public void create_withoutCreditCardAndWidgetId_withHiddenNumber() {
        AddEditCreditCardData data = new AddEditCreditCardData(null, null, null, true);
        Assert.assertEquals(data.getCreditCardType(), CreditCardType.VISA);
        Assert.assertEquals(data.getCreditCardNumber(), "");
        final Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        Assert.assertEquals(data.getExpirationYear(), (short) calendar.get(Calendar.YEAR));
        Assert.assertEquals(data.getExpirationMonth(), (byte) calendar.get(Calendar.MONTH));
        Assert.assertEquals(data.getSecurityCode(), "");
        Assert.assertEquals(data.isUseContactInfo(), false);
        Assert.assertEquals(data.getBillingAddress1(), "");
        Assert.assertEquals(data.getBillingAddress2(), "");
        Assert.assertEquals(data.getCity(), "");
        Assert.assertEquals(data.getCountry(), Country.US);
        Assert.assertEquals(data.getRegion(), "");
        Assert.assertEquals(data.getPostalCode(), "");
        Assert.assertEquals(data.getStates(), new CountryManager(Country.US).getStatesByCountry());
        Assert.assertEquals(data.getWidgetId(), 0);
        Assert.assertEquals(data.getUserBillingAddress1(), "");
        Assert.assertEquals(data.getUserBillingAddress2(), "");
        Assert.assertEquals(data.getUserCity(), "");
        Assert.assertEquals(data.getUserCountry(), Country.US);
        Assert.assertEquals(data.getUserPostalCode(), "");
        Assert.assertEquals(data.getUserRegion(), "");
        Assert.assertEquals(data.isDisableData(), true);
    }

    @Test
    public void testShowCheckbox() {
        final CreditCard creditCard = new CreditCard();

        User user = new User();
        user.setCountry(Country.US);

        user.setStreet("street");
        user.setCity("city");
        user.setRegion("region");
        user.setPostalCode("postalCode");

        final AddEditCreditCardData data = new AddEditCreditCardData(creditCard, 10, user, false);

        Assert.assertEquals(true, data.isShowCheckbox());
    }

    @Test
    public void testShowCheckbox_withoutStreet() {
        final CreditCard creditCard = new CreditCard();

        User user = new User();
        user.setCountry(Country.US);

//        user.setStreet("street");
        user.setCity("city");
        user.setRegion("region");
        user.setPostalCode("postalCode");

        final AddEditCreditCardData data = new AddEditCreditCardData(creditCard, 10, user, false);

        Assert.assertEquals(false, data.isShowCheckbox());
    }

    @Test
    public void testShowCheckbox_withoutCity() {
        final CreditCard creditCard = new CreditCard();

        User user = new User();
        user.setCountry(Country.US);

        user.setStreet("street");
//        user.setCity("city");
        user.setRegion("region");
        user.setPostalCode("postalCode");

        final AddEditCreditCardData data = new AddEditCreditCardData(creditCard, 10, user, false);

        Assert.assertEquals(false, data.isShowCheckbox());
    }

    @Test
    public void testShowCheckbox_withoutRegion() {
        final CreditCard creditCard = new CreditCard();

        User user = new User();
        user.setCountry(Country.US);

        user.setStreet("street");
        user.setCity("city");
//        user.setRegion("region");
        user.setPostalCode("postalCode");

        final AddEditCreditCardData data = new AddEditCreditCardData(creditCard, 10, user, false);

        Assert.assertEquals(false, data.isShowCheckbox());
    }

    @Test
    public void testShowCheckbox_withoutPostCode() {
        final CreditCard creditCard = new CreditCard();

        User user = new User();
        user.setCountry(Country.US);

        user.setStreet("street");
        user.setCity("city");
        user.setRegion("region");
//        user.setPostalCode("postalCode");

        final AddEditCreditCardData data = new AddEditCreditCardData(creditCard, 10, user, false);

        Assert.assertEquals(false, data.isShowCheckbox());
    }
}
