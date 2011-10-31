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

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.exception.ChildSiteSettingsNotFoundException;
import com.shroggle.exception.FormDataNotFoundException;
import com.shroggle.entity.DraftChildSiteRegistration;
import com.shroggle.entity.ChildSiteSettings;
import com.shroggle.TestRunnerWithMockServices;

import java.util.Date;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class PaymentSettingsTest {

    /*----------------------------------------------ChildSiteRegistration---------------------------------------------*/
    @Test(expected = FormDataNotFoundException.class)
    public void create_withoutChildSiteRegistration() {
        FormData formData = null;
        new PaymentSettings(formData);
    }

    @Test
    public void testCreate_byChildSiteRegistration() {
        DraftChildSiteRegistration registration = new DraftChildSiteRegistration();
        registration.setOneTimeFee(100.00);
        registration.setName("name");
        registration.setUseOneTimeFee(true);
        registration.setPrice250mb(132);
        registration.setStartDate(new Date(System.currentTimeMillis() + 10000));
        registration.setEndDate(new Date(System.currentTimeMillis() + 1000000));
        registration.setId(10);

        FormData formData = new FormData(registration);
        PaymentSettings paymentSettings = new PaymentSettings(formData);

        Assert.assertNotNull(paymentSettings);
        Assert.assertEquals(registration.getOneTimeFee(), paymentSettings.getOneTimeFee(), 0);
        Assert.assertEquals(registration.isUseOneTimeFee(), paymentSettings.isUseOneTimeFee());
        Assert.assertEquals(registration.getPrice250mb(), paymentSettings.getPrice250mb(), 0);
        Assert.assertEquals(registration.getStartDate(), paymentSettings.getStartDate());
        Assert.assertEquals(registration.getEndDate(), paymentSettings.getEndDate());
        Assert.assertEquals(registration.getFormId(), paymentSettings.getFormId());
        Assert.assertEquals(registration.getName(), paymentSettings.getChildSiteRegistrationName());
    }
    /*----------------------------------------------ChildSiteRegistration---------------------------------------------*/


    
    /*------------------------------------------------ChildSiteSettings-----------------------------------------------*/
    @Test(expected = ChildSiteSettingsNotFoundException.class)
    public void create_withoutChildSiteSettings() {
        ChildSiteSettings childSiteSettings = null;
        new PaymentSettings(childSiteSettings);
    }

    @Test
    public void testCreate_byChildSiteSettings() {
        DraftChildSiteRegistration registration = new DraftChildSiteRegistration();
        registration.setName("name");
        registration.setId(10);

        ChildSiteSettings settings = new ChildSiteSettings();
        settings.setOneTimeFee(100.00);
        settings.setUseOneTimeFee(true);
        settings.setPrice250mb(132);
        settings.setStartDate(new Date(System.currentTimeMillis() + 10000));
        settings.setEndDate(new Date(System.currentTimeMillis() + 1000000));
        settings.setChildSiteRegistration(registration);


        PaymentSettings paymentSettings = new PaymentSettings(settings);
        Assert.assertNotNull(paymentSettings);
        Assert.assertEquals(settings.getOneTimeFee(), paymentSettings.getOneTimeFee(), 0);
        Assert.assertEquals(settings.isUseOneTimeFee(), paymentSettings.isUseOneTimeFee());
        Assert.assertEquals(settings.getPrice250mb(), paymentSettings.getPrice250mb(), 0);
        Assert.assertEquals(settings.getStartDate(), paymentSettings.getStartDate());
        Assert.assertEquals(settings.getEndDate(), paymentSettings.getEndDate());
        Assert.assertEquals(registration.getFormId(), paymentSettings.getFormId());
        Assert.assertEquals(registration.getName(), paymentSettings.getChildSiteRegistrationName());
    }
    /*------------------------------------------------ChildSiteSettings-----------------------------------------------*/
}
