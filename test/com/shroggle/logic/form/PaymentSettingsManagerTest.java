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
package com.shroggle.logic.form;

import com.shroggle.entity.DraftChildSiteRegistration;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.shroggle.util.international.International;
import com.shroggle.util.ServiceLocator;
import com.shroggle.TestRunnerWithMockServices;

import java.util.Locale;
import java.util.Date;

import junit.framework.Assert;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class PaymentSettingsManagerTest {

    /*@Test(expected = PaymentSettingsNotFoundException.class)
    public void create_withoutPaymentSettings() {
        new PaymentSettingsManager(null);
    }*/

    @Test
    public void testGetAgreement_withoutSettings() {
        Assert.assertEquals(international.get("monthlyFeeWithoutEndDate", "<br>"), new PaymentSettingsManager(null).getAgreement());
    }

    @Test
    public void testGetAgreement_monthlyFeeWithoutEndDate() {
        DraftChildSiteRegistration registration = new DraftChildSiteRegistration();
        registration.setOneTimeFee(100.00);
        registration.setUseOneTimeFee(false);
        registration.setPrice250mb(132);
        registration.setStartDate(new Date(System.currentTimeMillis() + 10000));
        registration.setEndDate(null);
        registration.setId(10);

        FormData formData = new FormData(registration);
        PaymentSettings settings = new PaymentSettings(formData);

        Assert.assertEquals(international.get("monthlyFeeWithoutEndDate", "<br>"), new PaymentSettingsManager(settings).getAgreement());
    }

    @Test
    public void testGetAgreement_monthlyFeeWithEndDate() {
        DraftChildSiteRegistration registration = new DraftChildSiteRegistration();
        registration.setName("name");
        registration.setOneTimeFee(100.00);
        registration.setUseOneTimeFee(false);
        registration.setPrice250mb(132);
        registration.setStartDate(new Date(System.currentTimeMillis() + 10000));
        registration.setEndDate(new Date());
        registration.setId(10);

        FormData formData = new FormData(registration);
        PaymentSettings settings = new PaymentSettings(formData);

        Assert.assertEquals(international.get("monthlyFeeWithEndDate", registration.getName(), "<br>"), new PaymentSettingsManager(settings).getAgreement());
    }

    @Test
    public void testGetAgreement_oneTimeFeeWithEndDate() {
        DraftChildSiteRegistration registration = new DraftChildSiteRegistration();
        registration.setName("name");
        registration.setOneTimeFee(100.00);
        registration.setUseOneTimeFee(true);
        registration.setPrice250mb(132);
        registration.setStartDate(new Date(System.currentTimeMillis() + 10000));
        registration.setEndDate(new Date());
        registration.setId(10);

        FormData formData = new FormData(registration);
        PaymentSettings settings = new PaymentSettings(formData);

        Assert.assertEquals(international.get("oneTimeFeeWithEndDate", registration.getName(), "<br>"), new PaymentSettingsManager(settings).getAgreement());
    }

    @Test
    public void testGetAgreement_oneTimeFeeWithNoEndDate() {
        DraftChildSiteRegistration registration = new DraftChildSiteRegistration();
        registration.setName("name");
        registration.setOneTimeFee(100.00);
        registration.setUseOneTimeFee(true);
        registration.setPrice250mb(132);
        registration.setStartDate(new Date(System.currentTimeMillis() + 10000));
        registration.setEndDate(null);
        registration.setId(10);

        FormData formData = new FormData(registration);
        PaymentSettings settings = new PaymentSettings(formData);

        Assert.assertEquals(international.get("oneTimeFeeWithNoEndDate", "<br>"), new PaymentSettingsManager(settings).getAgreement());
    }

    private final International international = ServiceLocator.getInternationStorage().get("paymentAgreements", Locale.US);
}
