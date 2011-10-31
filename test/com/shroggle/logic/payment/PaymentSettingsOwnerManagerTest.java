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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.PayPalException;
import com.shroggle.exception.PaymentException;
import com.shroggle.exception.SitePaymentSettingsNotFoundException;
import com.shroggle.logic.site.billingInfo.ChargeTypeManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.BillingInfoProperties;
import com.shroggle.util.config.Config;
import com.shroggle.util.payment.PaymentSystem;
import com.shroggle.util.payment.authorize.AuthorizeNet;
import com.shroggle.util.payment.authorize.AuthorizeNetMock;
import com.shroggle.util.payment.authorize.AuthorizeNetWithExceptionsInMethods;
import com.shroggle.util.payment.javien.JavienWithExceptionsInMethods;
import com.shroggle.util.payment.paypal.PayPal;
import com.shroggle.util.payment.paypal.PayPalMock;
import com.shroggle.util.payment.paypal.PayPalRecurringProfileStatus;
import com.shroggle.util.payment.paypal.PayPalWithExceptionsInMethods;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class PaymentSettingsOwnerManagerTest {

    @Before
    public void testBefore() {
        final BillingInfoProperties billingInfoProperties = new BillingInfoProperties();
        billingInfoProperties.setAnnualBillingExpirationDate(YEAR);
        billingInfoProperties.setMonthlyBillingExpirationDate(MONTH);
        ServiceLocator.getConfigStorage().get().setBillingInfoProperties(billingInfoProperties);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testCreate_withoutSettings() {
        new PaymentSettingsOwnerManager(null);
    }
    /*------------------------------------------------Suspend Activity------------------------------------------------*/

    @Test
    public void testSuspendActivity_site_ACTIVE_withPaypal() throws Exception {
        final Date currentDate = new Date();

        final Calendar expirationDate = new GregorianCalendar();
        expirationDate.setTime(currentDate);
        expirationDate.set(Calendar.MONTH, expirationDate.get(Calendar.MONTH) + 1); // Setting expirationDate = currentDate + one month

        final PayPalMock payPal = (PayPalMock) ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        final String profileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(profileId, PayPalRecurringProfileStatus.ACTIVE, "Note...");

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setExpirationDate(expirationDate.getTime());
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.PAYPAL);
        site.getSitePaymentSettings().setRecurringPaymentId(profileId);


        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        manager.suspendActivity();


        final SitePaymentSettings sitePaymentSettings = site.getSitePaymentSettings();
        Assert.assertEquals(SiteStatus.SUSPENDED, sitePaymentSettings.getSiteStatus());
        Assert.assertNotNull(sitePaymentSettings.getRemainingTimeOfUsage().longValue());

        long remainingTimeOfUsage = sitePaymentSettings.getRemainingTimeOfUsage().longValue();
        double remainingDaysOfUsage = ((float) remainingTimeOfUsage) / 1000 / 60 / 60 / 24;
        Assert.assertTrue(remainingDaysOfUsage > 27 && remainingDaysOfUsage < 32);// Remaining days of usage shuld be ~ one month

        final PayPalMock payPalMock = (PayPalMock) ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        Assert.assertEquals("Paypal recurring profile should be suspended!",
                payPalMock.getProfileStatus(site.getSitePaymentSettings().getRecurringPaymentId()),
                PayPalRecurringProfileStatus.SUSPENDED);
    }

    @Test
    public void testSuspendActivity_site_ACTIVE_withPaypal_withoutRecurringProfileId() throws Exception {
        final Date currentDate = new Date();

        final Calendar expirationDate = new GregorianCalendar();
        expirationDate.setTime(currentDate);
        expirationDate.set(Calendar.MONTH, expirationDate.get(Calendar.MONTH) + 1); // Setting expirationDate = currentDate + one month

        final PayPalMock payPal = (PayPalMock) ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        final String profileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(profileId, PayPalRecurringProfileStatus.ACTIVE, "Note...");

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setExpirationDate(expirationDate.getTime());
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.PAYPAL);
        site.getSitePaymentSettings().setRecurringPaymentId(null);


        ServiceLocator.setPayPal(new PayPalWithExceptionsInMethods());
        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        boolean payPalExceptionThrown = false;
        try {
            manager.suspendActivity();
        } catch (PayPalException e) {
            payPalExceptionThrown = true;
        }
        Assert.assertTrue(payPalExceptionThrown);

        final SitePaymentSettings sitePaymentSettings = site.getSitePaymentSettings();
        Assert.assertEquals(SiteStatus.ACTIVE, sitePaymentSettings.getSiteStatus());
        Assert.assertEquals(expirationDate.getTime(), site.getSitePaymentSettings().getExpirationDate());
    }


    @Test(expected = PayPalException.class)
    public void testSuspendActivity_site_ACTIVE_withPaypal_withRecurringProfileId_butWithExceptionInPaypal() throws Exception {
        final Date currentDate = new Date();

        final Calendar expirationDate = new GregorianCalendar();
        expirationDate.setTime(currentDate);
        expirationDate.set(Calendar.MONTH, expirationDate.get(Calendar.MONTH) + 1); // Setting expirationDate = currentDate + one month

        final PayPalMock payPal = (PayPalMock) ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        final String profileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(profileId, PayPalRecurringProfileStatus.ACTIVE, "Note...");

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setExpirationDate(expirationDate.getTime());
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.PAYPAL);
        site.getSitePaymentSettings().setRecurringPaymentId(profileId);


        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        ServiceLocator.setPayPal(new PayPalWithExceptionsInMethods());
        manager.suspendActivity();
    }

    @Test
    public void testSuspendActivity_site_ACTIVE_withJavienAndWithRecurringProfileId() throws Exception {
        final Date currentDate = new Date();

        final Calendar expirationDate = new GregorianCalendar();
        expirationDate.setTime(currentDate);
        expirationDate.set(Calendar.MONTH, expirationDate.get(Calendar.MONTH) + 1); // Setting expirationDate = currentDate + one month

        final PayPalMock payPal = (PayPalMock) ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        final String profileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(profileId, PayPalRecurringProfileStatus.ACTIVE, "Note...");

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setExpirationDate(expirationDate.getTime());
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);
        site.getSitePaymentSettings().setRecurringPaymentId(profileId);


        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        manager.suspendActivity();


        final SitePaymentSettings sitePaymentSettings = site.getSitePaymentSettings();
        Assert.assertEquals(SiteStatus.SUSPENDED, sitePaymentSettings.getSiteStatus());
        Assert.assertNotNull(sitePaymentSettings.getRemainingTimeOfUsage().longValue());

        long remainingTimeOfUsage = sitePaymentSettings.getRemainingTimeOfUsage().longValue();
        double remainingDaysOfUsage = ((float) remainingTimeOfUsage) / 1000 / 60 / 60 / 24;
        Assert.assertTrue((remainingDaysOfUsage > 27 && remainingDaysOfUsage < 32));// Remaining days of usage shuld be ~ one month
        Assert.assertEquals("Paypal recurring profile should be suspended!",
                payPal.getProfileStatus(site.getSitePaymentSettings().getRecurringPaymentId()),
                PayPalRecurringProfileStatus.SUSPENDED);
    }

    @Test
    public void testSuspendActivity_site_ACTIVE_withJavien() throws Exception {
        final Date currentDate = new Date();

        final Calendar expirationDate = new GregorianCalendar();
        expirationDate.setTime(currentDate);
        expirationDate.set(Calendar.MONTH, expirationDate.get(Calendar.MONTH) + 1); // Setting expirationDate = currentDate + one month

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setExpirationDate(expirationDate.getTime());
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);


        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        manager.suspendActivity();


        final SitePaymentSettings sitePaymentSettings = site.getSitePaymentSettings();
        Assert.assertEquals(SiteStatus.SUSPENDED, sitePaymentSettings.getSiteStatus());
        Assert.assertNotNull(sitePaymentSettings.getRemainingTimeOfUsage().longValue());

        long remainingTimeOfUsage = sitePaymentSettings.getRemainingTimeOfUsage().longValue();
        double remainingDaysOfUsage = ((float) remainingTimeOfUsage) / 1000 / 60 / 60 / 24;
        Assert.assertTrue((remainingDaysOfUsage > 27 && remainingDaysOfUsage < 32));// Remaining days of usage shuld be ~ one month
    }

    @Test
    public void testSuspendActivity_site_ACTIVE_withExpirationDateBeforeCurrent() throws Exception {
        final Date currentDate = new Date();

        final Calendar expirationDate = new GregorianCalendar();
        expirationDate.setTime(currentDate);
        expirationDate.set(Calendar.MONTH, expirationDate.get(Calendar.MONTH) - 10); // Setting expirationDate = currentDate - ten months

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setExpirationDate(expirationDate.getTime());
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);


        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        manager.suspendActivity();


        final SitePaymentSettings sitePaymentSettings = site.getSitePaymentSettings();
        Assert.assertEquals(SiteStatus.SUSPENDED, sitePaymentSettings.getSiteStatus());
        Assert.assertTrue(sitePaymentSettings.getRemainingTimeOfUsage().longValue() < 0);
    }
    /*--------------------------------------------------AuthorizeNet--------------------------------------------------*/

    @Test
    public void testSuspendActivity_site_ACTIVE_withAuthorizeNetAndWithRecurringProfileId() throws Exception {

        final Date currentDate = new Date();

        final Calendar expirationDate = new GregorianCalendar();
        expirationDate.setTime(currentDate);
        expirationDate.set(Calendar.MONTH, expirationDate.get(Calendar.MONTH) + 1); // Setting expirationDate = currentDate + one month

        final PayPalMock payPal = (PayPalMock) ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        final String profileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(profileId, PayPalRecurringProfileStatus.ACTIVE, "Note...");

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setExpirationDate(expirationDate.getTime());
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);
        site.getSitePaymentSettings().setRecurringPaymentId(profileId);


        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        manager.suspendActivity();


        final SitePaymentSettings sitePaymentSettings = site.getSitePaymentSettings();
        Assert.assertEquals(SiteStatus.SUSPENDED, sitePaymentSettings.getSiteStatus());
        Assert.assertNotNull(sitePaymentSettings.getRemainingTimeOfUsage().longValue());

        long remainingTimeOfUsage = sitePaymentSettings.getRemainingTimeOfUsage().longValue();
        double remainingDaysOfUsage = ((float) remainingTimeOfUsage) / 1000 / 60 / 60 / 24;
        Assert.assertTrue((remainingDaysOfUsage > 27 && remainingDaysOfUsage < 32));// Remaining days of usage shuld be ~ one month
        Assert.assertEquals("Paypal recurring profile should be suspended!",
                payPal.getProfileStatus(site.getSitePaymentSettings().getRecurringPaymentId()),
                PayPalRecurringProfileStatus.SUSPENDED);
    }

    @Test
    public void testSuspendActivity_site_ACTIVE_AuthorizeNet() throws Exception {

        final Date currentDate = new Date();

        final Calendar expirationDate = new GregorianCalendar();
        expirationDate.setTime(currentDate);
        expirationDate.set(Calendar.MONTH, expirationDate.get(Calendar.MONTH) + 1); // Setting expirationDate = currentDate + one month

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setExpirationDate(expirationDate.getTime());
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);


        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        manager.suspendActivity();


        final SitePaymentSettings sitePaymentSettings = site.getSitePaymentSettings();
        Assert.assertEquals(SiteStatus.SUSPENDED, sitePaymentSettings.getSiteStatus());
        Assert.assertNotNull(sitePaymentSettings.getRemainingTimeOfUsage().longValue());

        long remainingTimeOfUsage = sitePaymentSettings.getRemainingTimeOfUsage().longValue();
        double remainingDaysOfUsage = ((float) remainingTimeOfUsage) / 1000 / 60 / 60 / 24;
        Assert.assertTrue((remainingDaysOfUsage > 27 && remainingDaysOfUsage < 32));// Remaining days of usage shuld be ~ one month
    }

    @Test
    public void testSuspendActivity_site_ACTIVE_withExpirationDateBeforeCurrent_AuthorizeNet() throws Exception {

        final Date currentDate = new Date();

        final Calendar expirationDate = new GregorianCalendar();
        expirationDate.setTime(currentDate);
        expirationDate.set(Calendar.MONTH, expirationDate.get(Calendar.MONTH) - 10); // Setting expirationDate = currentDate - ten months

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setExpirationDate(expirationDate.getTime());
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);


        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        manager.suspendActivity();


        final SitePaymentSettings sitePaymentSettings = site.getSitePaymentSettings();
        Assert.assertEquals(SiteStatus.SUSPENDED, sitePaymentSettings.getSiteStatus());
        Assert.assertTrue(sitePaymentSettings.getRemainingTimeOfUsage().longValue() < 0);
    }
    /*--------------------------------------------------AuthorizeNet--------------------------------------------------*/

    @Test
    public void testSuspendActivity_childSiteSettings_ACTIVE() throws Exception {
        final Date currentDate = new Date();

        final Calendar expirationDate = new GregorianCalendar();
        expirationDate.setTime(currentDate);
        expirationDate.set(Calendar.MONTH, expirationDate.get(Calendar.MONTH) + 1); // Setting expirationDate = currentDate + one month

        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        childSiteSettings.getSitePaymentSettings().setExpirationDate(expirationDate.getTime());
        childSiteSettings.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);


        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(childSiteSettings);
        manager.suspendActivity();


        final SitePaymentSettings sitePaymentSettings = childSiteSettings.getSitePaymentSettings();
        Assert.assertEquals(SiteStatus.SUSPENDED, sitePaymentSettings.getSiteStatus());
        Assert.assertNotNull(sitePaymentSettings.getRemainingTimeOfUsage().longValue());

        long remainingTimeOfUsage = sitePaymentSettings.getRemainingTimeOfUsage().longValue();
        double remainingDaysOfUsage = ((float) remainingTimeOfUsage) / 1000 / 60 / 60 / 24;
        Assert.assertTrue((remainingDaysOfUsage > 27 && remainingDaysOfUsage < 32));// Remaining days of usage shuld be ~ one month
    }

    @Test
    public void testSuspendActivity_site_ACTIVE_withoutSitePaymentSettings() throws Exception {
        final Date currentDate = new Date();

        final Calendar expirationDate = new GregorianCalendar();
        expirationDate.setTime(currentDate);
        expirationDate.set(Calendar.MONTH, expirationDate.get(Calendar.MONTH) + 1); // Setting expirationDate = currentDate + one month

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setExpirationDate(expirationDate.getTime());
        site.setSitePaymentSettings(null);


        boolean illegalStateExceptionThrown = false;
        try {
            final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
            manager.suspendActivity();
        } catch (SitePaymentSettingsNotFoundException e) {
            illegalStateExceptionThrown = true;
        }
        Assert.assertTrue(illegalStateExceptionThrown);

        Assert.assertNull(site.getSitePaymentSettings());
    }

    @Test
    public void testSuspendActivity_site_ACTIVE_withoutExpirationDate() throws Exception {
        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);


        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        boolean illegalStateExceptionThrown = false;
        try {
            manager.suspendActivity();
        } catch (IllegalStateException e) {
            illegalStateExceptionThrown = true;
        }
        Assert.assertTrue(illegalStateExceptionThrown);

        final SitePaymentSettings sitePaymentSettings = site.getSitePaymentSettings();
        Assert.assertEquals(SiteStatus.ACTIVE, sitePaymentSettings.getSiteStatus());
        Assert.assertNull(site.getSitePaymentSettings().getExpirationDate());
    }

    @Test
    public void testSuspendActivity_site_PENDING() throws Exception {
        final Date currentDate = new Date();

        final Calendar expirationDate = new GregorianCalendar();
        expirationDate.setTime(currentDate);
        expirationDate.set(Calendar.MONTH, expirationDate.get(Calendar.MONTH) + 1); // Setting expirationDate = currentDate + one month

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setExpirationDate(expirationDate.getTime());
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.PENDING);


        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        boolean illegalStateExceptionThrown = false;
        try {
            manager.suspendActivity();
        } catch (IllegalStateException e) {
            illegalStateExceptionThrown = true;
        }
        Assert.assertTrue(illegalStateExceptionThrown);

        final SitePaymentSettings sitePaymentSettings = site.getSitePaymentSettings();
        Assert.assertEquals(SiteStatus.PENDING, sitePaymentSettings.getSiteStatus());
        Assert.assertEquals(expirationDate.getTime(), site.getSitePaymentSettings().getExpirationDate());
    }

    @Test
    public void testSuspendActivity_site_SUSPENDED() throws Exception {
        final Date currentDate = new Date();

        final Calendar expirationDate = new GregorianCalendar();
        expirationDate.setTime(currentDate);
        expirationDate.set(Calendar.MONTH, expirationDate.get(Calendar.MONTH) + 1); // Setting expirationDate = currentDate + one month

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setExpirationDate(expirationDate.getTime());
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.SUSPENDED);


        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        boolean illegalStateExceptionThrown = false;
        try {
            manager.suspendActivity();
        } catch (IllegalStateException e) {
            illegalStateExceptionThrown = true;
        }
        Assert.assertTrue(illegalStateExceptionThrown);

        final SitePaymentSettings sitePaymentSettings = site.getSitePaymentSettings();
        Assert.assertEquals(SiteStatus.SUSPENDED, sitePaymentSettings.getSiteStatus());
        Assert.assertEquals(expirationDate.getTime(), site.getSitePaymentSettings().getExpirationDate());
    }

    /*------------------------------------------------Suspend Activity------------------------------------------------*/

    /*----------------------------------------------Reactivate Activity-----------------------------------------------*/

    @Test
    public void testReactivate_site_SUSPENDED_JAVIEN() throws Exception {
        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.SUSPENDED);
        final long remainingTimeOfUsage = 365 * 24 * 60 * 60 * 1000L;
        site.getSitePaymentSettings().setRemainingTimeOfUsage(remainingTimeOfUsage);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);
        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);


        manager.reactivate();


        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertNull(site.getSitePaymentSettings().getRemainingTimeOfUsage());


        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(System.currentTimeMillis() + remainingTimeOfUsage));

        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(site.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testReactivate_site_SUSPENDED_JAVIEN_withNewExpirationDateBeforeCurrent() throws Exception {
        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.SUSPENDED);
        final long remainingTimeOfUsage = 365 * 24 * 60 * 60 * 1000L;
        site.getSitePaymentSettings().setRemainingTimeOfUsage(-remainingTimeOfUsage);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);
        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);


        manager.reactivate();


        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertNull(site.getSitePaymentSettings().getRemainingTimeOfUsage());


        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(System.currentTimeMillis() - remainingTimeOfUsage));

        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(site.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testReactivate_childSiteSettings_SUSPENDED_JAVIEN() throws Exception {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        childSiteSettings.getSitePaymentSettings().setSiteStatus(SiteStatus.SUSPENDED);
        final long remainingTimeOfUsage = 365 * 24 * 60 * 60 * 1000L;
        childSiteSettings.getSitePaymentSettings().setRemainingTimeOfUsage(remainingTimeOfUsage);
        childSiteSettings.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);
        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(childSiteSettings);


        manager.reactivate();


        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());


        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(System.currentTimeMillis() + remainingTimeOfUsage));

        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    /*--------------------------------------------------AuthorizeNet--------------------------------------------------*/

    @Test
    public void testReactivate_site_SUSPENDED_AuthorizeNet() throws Exception {

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.SUSPENDED);
        final long remainingTimeOfUsage = 365 * 24 * 60 * 60 * 1000L;
        site.getSitePaymentSettings().setRemainingTimeOfUsage(remainingTimeOfUsage);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);
        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);


        manager.reactivate();


        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertNull(site.getSitePaymentSettings().getRemainingTimeOfUsage());


        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(System.currentTimeMillis() + remainingTimeOfUsage));

        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(site.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testReactivate_site_SUSPENDED_AuthorizeNet_withNewExpirationDateBeforeCurrent() throws Exception {

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.SUSPENDED);
        final long remainingTimeOfUsage = 365 * 24 * 60 * 60 * 1000L;
        site.getSitePaymentSettings().setRemainingTimeOfUsage(-remainingTimeOfUsage);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);
        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);


        manager.reactivate();


        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertNull(site.getSitePaymentSettings().getRemainingTimeOfUsage());


        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(System.currentTimeMillis() - remainingTimeOfUsage));

        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(site.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testReactivate_childSiteSettings_SUSPENDED_AuthorizeNet() throws Exception {

        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        childSiteSettings.getSitePaymentSettings().setSiteStatus(SiteStatus.SUSPENDED);
        final long remainingTimeOfUsage = 365 * 24 * 60 * 60 * 1000L;
        childSiteSettings.getSitePaymentSettings().setRemainingTimeOfUsage(remainingTimeOfUsage);
        childSiteSettings.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);
        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(childSiteSettings);


        manager.reactivate();


        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());


        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(System.currentTimeMillis() + remainingTimeOfUsage));

        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }
    /*--------------------------------------------------AuthorizeNet--------------------------------------------------*/

    @Test
    public void testReactivate_site_SUSPENDED_PAYPAL() throws Exception {
        final PayPalMock payPal = (PayPalMock) ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        final String profileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(profileId, PayPalRecurringProfileStatus.SUSPENDED, "Note...");

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.SUSPENDED);
        site.getSitePaymentSettings().setRecurringPaymentId(profileId);
        final long remainingTimeOfUsage = 365 * 24 * 60 * 60 * 1000L;
        site.getSitePaymentSettings().setRemainingTimeOfUsage(remainingTimeOfUsage);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.PAYPAL);
        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);


        manager.reactivate();


        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertNull(site.getSitePaymentSettings().getRemainingTimeOfUsage());


        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(System.currentTimeMillis() + remainingTimeOfUsage));

        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(site.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));


        final PayPalMock payPalMock = (PayPalMock) ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        Assert.assertEquals("Paypal recurring profile should be activated!",
                payPalMock.getProfileStatus(site.getSitePaymentSettings().getRecurringPaymentId()),
                PayPalRecurringProfileStatus.ACTIVE);
    }

    @Test
    public void testReactivate_childSiteSettings_SUSPENDED_PAYPAL() throws Exception {
        final PayPalMock payPal = (PayPalMock) ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        final String profileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(profileId, PayPalRecurringProfileStatus.SUSPENDED, "Note...");

        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        childSiteSettings.getSitePaymentSettings().setSiteStatus(SiteStatus.SUSPENDED);
        childSiteSettings.getSitePaymentSettings().setRecurringPaymentId(profileId);
        final long remainingTimeOfUsage = 365 * 24 * 60 * 60 * 1000L;
        childSiteSettings.getSitePaymentSettings().setRemainingTimeOfUsage(remainingTimeOfUsage);
        childSiteSettings.getSitePaymentSettings().setPaymentMethod(PaymentMethod.PAYPAL);
        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(childSiteSettings);


        manager.reactivate();


        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());


        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(System.currentTimeMillis() + remainingTimeOfUsage));

        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));


        final PayPalMock payPalMock = (PayPalMock) ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        Assert.assertEquals("Paypal recurring profile should be activated!",
                payPalMock.getProfileStatus(childSiteSettings.getSitePaymentSettings().getRecurringPaymentId()),
                PayPalRecurringProfileStatus.ACTIVE);
    }


    @Test
    public void testReactivate_site_SUSPENDED_PAYPAL_withoutRecurringProfileId() throws Exception {
        final PayPalMock payPal = (PayPalMock) ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        final String profileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(profileId, PayPalRecurringProfileStatus.SUSPENDED, "Note...");

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.SUSPENDED);
        site.getSitePaymentSettings().setRecurringPaymentId(null);
        final long remainingTimeOfUsage = 365 * 24 * 60 * 60 * 1000L;
        site.getSitePaymentSettings().setRemainingTimeOfUsage(remainingTimeOfUsage);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.PAYPAL);


        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        boolean illegalStateExceptionThrown = false;
        try {
            manager.reactivate();
        } catch (PayPalException e) {
            illegalStateExceptionThrown = true;
        }
        Assert.assertTrue(illegalStateExceptionThrown);


        Assert.assertEquals(SiteStatus.SUSPENDED, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(remainingTimeOfUsage, site.getSitePaymentSettings().getRemainingTimeOfUsage().longValue());

        final PayPalMock payPalMock = (PayPalMock) ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        Assert.assertEquals("Paypal recurring profile should not be activated!",
                payPalMock.getProfileStatus(profileId),
                PayPalRecurringProfileStatus.SUSPENDED);
    }


    @Test
    public void testReactivate_site_SUSPENDED_PAYPAL_withRecurringProfileIdButWithExceptionInPaypal() throws Exception {
        final PayPalMock payPal = (PayPalMock) ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        final String profileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(profileId, PayPalRecurringProfileStatus.SUSPENDED, "Note...");

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.SUSPENDED);
        site.getSitePaymentSettings().setExpirationDate(new Date());
        site.getSitePaymentSettings().setRecurringPaymentId(profileId);
        final long remainingTimeOfUsage = 365 * 24 * 60 * 60 * 1000L;
        site.getSitePaymentSettings().setRemainingTimeOfUsage(remainingTimeOfUsage);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.PAYPAL);


        ServiceLocator.setPayPal(new PayPalWithExceptionsInMethods());
        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        boolean payPalExceptionThrown = false;
        try {
            manager.reactivate();
        } catch (PayPalException e) {
            payPalExceptionThrown = true;
        }
        Assert.assertTrue(payPalExceptionThrown);


        Assert.assertEquals(SiteStatus.SUSPENDED, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(remainingTimeOfUsage, site.getSitePaymentSettings().getRemainingTimeOfUsage().longValue());


        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(System.currentTimeMillis() + remainingTimeOfUsage));

        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(site.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals("Paypal recurring profile should not be activated!",
                payPal.getProfileStatus(site.getSitePaymentSettings().getRecurringPaymentId()),
                PayPalRecurringProfileStatus.SUSPENDED);
    }

    @Test
    public void testReactivate_site_withoutSitePaymentSettings() throws Exception {
        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.SUSPENDED);
        //final long remainingTimeOfUsage = 365 * 24 * 60 * 60 * 1000L;
        site.setSitePaymentSettings(null);


        boolean illegalStateExceptionThrown = false;
        try {
            final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
            manager.reactivate();
        } catch (SitePaymentSettingsNotFoundException e) {
            illegalStateExceptionThrown = true;
        }
        Assert.assertTrue(illegalStateExceptionThrown);


        Assert.assertNull(site.getSitePaymentSettings());
    }

    @Test
    public void testReactivate_site_PENDING() throws Exception {
        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.PENDING);
        final long remainingTimeOfUsage = 365 * 24 * 60 * 60 * 1000L;
        site.getSitePaymentSettings().setRemainingTimeOfUsage(remainingTimeOfUsage);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);


        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        boolean illegalStateExceptionThrown = false;
        try {
            manager.reactivate();
        } catch (IllegalStateException e) {
            illegalStateExceptionThrown = true;
        }
        Assert.assertTrue(illegalStateExceptionThrown);


        Assert.assertEquals(SiteStatus.PENDING, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(remainingTimeOfUsage, site.getSitePaymentSettings().getRemainingTimeOfUsage().longValue());
    }

    @Test
    public void testReactivate_site_ACTIVE() throws Exception {
        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        final long remainingTimeOfUsage = 365 * 24 * 60 * 60 * 1000L;
        site.getSitePaymentSettings().setRemainingTimeOfUsage(remainingTimeOfUsage);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);


        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        boolean illegalStateExceptionThrown = false;
        try {
            manager.reactivate();
        } catch (IllegalStateException e) {
            illegalStateExceptionThrown = true;
        }
        Assert.assertTrue(illegalStateExceptionThrown);


        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(remainingTimeOfUsage, site.getSitePaymentSettings().getRemainingTimeOfUsage().longValue());
    }

    @Test
    public void testReactivate_site_SUSPENDED_withoutRemainingTimeOfUsage() throws Exception {
        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.SUSPENDED);
        site.getSitePaymentSettings().setRemainingTimeOfUsage(null);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);


        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        boolean illegalStateExceptionThrown = false;
        try {
            manager.reactivate();
        } catch (IllegalStateException e) {
            illegalStateExceptionThrown = true;
        }
        Assert.assertTrue(illegalStateExceptionThrown);


        Assert.assertEquals(SiteStatus.SUSPENDED, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertNull(site.getSitePaymentSettings().getRemainingTimeOfUsage());
    }


    /*--------------------------------------------------AuthorizeNet--------------------------------------------------*/

    @Test
    public void testReactivate_site_PENDING_AuthorizeNet() throws Exception {

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.PENDING);
        final long remainingTimeOfUsage = 365 * 24 * 60 * 60 * 1000L;
        site.getSitePaymentSettings().setRemainingTimeOfUsage(remainingTimeOfUsage);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);


        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        boolean illegalStateExceptionThrown = false;
        try {
            manager.reactivate();
        } catch (IllegalStateException e) {
            illegalStateExceptionThrown = true;
        }
        Assert.assertTrue(illegalStateExceptionThrown);


        Assert.assertEquals(SiteStatus.PENDING, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(remainingTimeOfUsage, site.getSitePaymentSettings().getRemainingTimeOfUsage().longValue());
    }

    @Test
    public void testReactivate_site_ACTIVE_AuthorizeNet() throws Exception {

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        final long remainingTimeOfUsage = 365 * 24 * 60 * 60 * 1000L;
        site.getSitePaymentSettings().setRemainingTimeOfUsage(remainingTimeOfUsage);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);


        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        boolean illegalStateExceptionThrown = false;
        try {
            manager.reactivate();
        } catch (IllegalStateException e) {
            illegalStateExceptionThrown = true;
        }
        Assert.assertTrue(illegalStateExceptionThrown);


        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(remainingTimeOfUsage, site.getSitePaymentSettings().getRemainingTimeOfUsage().longValue());
    }

    @Test
    public void testReactivate_site_SUSPENDED_withoutRemainingTimeOfUsage_AuthorizeNet() throws Exception {

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.SUSPENDED);
        site.getSitePaymentSettings().setRemainingTimeOfUsage(null);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);


        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        boolean illegalStateExceptionThrown = false;
        try {
            manager.reactivate();
        } catch (IllegalStateException e) {
            illegalStateExceptionThrown = true;
        }
        Assert.assertTrue(illegalStateExceptionThrown);


        Assert.assertEquals(SiteStatus.SUSPENDED, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertNull(site.getSitePaymentSettings().getRemainingTimeOfUsage());
    }
    /*--------------------------------------------------AuthorizeNet--------------------------------------------------*/
    /*----------------------------------------------Reactivate Activity-----------------------------------------------*/


    /*---------------------------------------------------Deactivate---------------------------------------------------*/

    @Test
    public void testDeactivate_withJavien_ACTIVE() throws Exception {
        final Site site = TestUtil.createSite();
        CreditCard creditCard = TestUtil.createCreditCard(new Date(), site);
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        site.getSitePaymentSettings().setRemainingTimeOfUsage(12030L);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);
        site.getSitePaymentSettings().setCreditCard(creditCard);
        site.getSitePaymentSettings().setRecurringPaymentId(null);

        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        manager.deactivate();

        Assert.assertEquals(SiteStatus.PENDING, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(null, site.getSitePaymentSettings().getCreditCard());
    }

    @Test
    public void testDeactivate_withJavien_ACTIVE_withExceptionInJavien() throws Exception {
        ServiceLocator.setJavien(new JavienWithExceptionsInMethods());
        final Site site = TestUtil.createSite();
        CreditCard creditCard = TestUtil.createCreditCard(new Date(), site);
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        site.getSitePaymentSettings().setRemainingTimeOfUsage(12030L);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);
        site.getSitePaymentSettings().setCreditCard(creditCard);
        site.getSitePaymentSettings().setRecurringPaymentId(null);

        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        manager.deactivate();

        Assert.assertEquals(SiteStatus.PENDING, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(null, site.getSitePaymentSettings().getCreditCard());
    }

    @Test
    public void testDeactivate_withJavien_SUSPENDED() throws Exception {
        final Site site = TestUtil.createSite();
        CreditCard creditCard = TestUtil.createCreditCard(new Date(), site);
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.SUSPENDED);
        site.getSitePaymentSettings().setRemainingTimeOfUsage(12030L);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);
        site.getSitePaymentSettings().setCreditCard(creditCard);
        site.getSitePaymentSettings().setRecurringPaymentId(null);

        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        manager.deactivate();

        Assert.assertEquals(SiteStatus.PENDING, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(null, site.getSitePaymentSettings().getCreditCard());
    }

    @Test
    public void testDeactivate_withJavien_PENDING() throws Exception {
        final Site site = TestUtil.createSite();
        CreditCard creditCard = TestUtil.createCreditCard(new Date(), site);
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.PENDING);
        site.getSitePaymentSettings().setRemainingTimeOfUsage(12030L);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);
        site.getSitePaymentSettings().setCreditCard(creditCard);
        site.getSitePaymentSettings().setRecurringPaymentId(null);

        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        manager.deactivate();

        Assert.assertEquals(SiteStatus.PENDING, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(null, site.getSitePaymentSettings().getCreditCard());
    }

    @Test
    public void testDeactivate_withJavien_ACTIVE_andWithOldRecurringProfileId() throws Exception {
        final PayPalMock payPal = (PayPalMock) ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        final String profileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(profileId, PayPalRecurringProfileStatus.ACTIVE, "Note...");

        final Site site = TestUtil.createSite();
        CreditCard creditCard = TestUtil.createCreditCard(new Date(), site);
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        site.getSitePaymentSettings().setRemainingTimeOfUsage(12030L);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);
        site.getSitePaymentSettings().setCreditCard(creditCard);
        site.getSitePaymentSettings().setRecurringPaymentId(profileId);

        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        manager.deactivate();

        Assert.assertEquals(SiteStatus.PENDING, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(null, site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(PayPalRecurringProfileStatus.CANCELED, payPal.getProfileStatus(profileId));
    }


    /*--------------------------------------------------AuthorizeNet--------------------------------------------------*/

    @Test
    public void testDeactivate_withAuthorizeNet_ACTIVE() throws Exception {

        final Site site = TestUtil.createSite();
        CreditCard creditCard = TestUtil.createCreditCard(new Date(), site);
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        site.getSitePaymentSettings().setRemainingTimeOfUsage(12030L);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);
        site.getSitePaymentSettings().setCreditCard(creditCard);
        site.getSitePaymentSettings().setRecurringPaymentId(null);

        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        manager.deactivate();

        Assert.assertEquals(SiteStatus.PENDING, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(null, site.getSitePaymentSettings().getCreditCard());
    }

    @Test
    public void testDeactivate_withAuthorizeNet_ACTIVE_withExceptionInAuthorizeNet() throws Exception {

        ServiceLocator.setAuthorizeNet(new AuthorizeNetWithExceptionsInMethods());
        final Site site = TestUtil.createSite();
        CreditCard creditCard = TestUtil.createCreditCard(new Date(), site);
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        site.getSitePaymentSettings().setRemainingTimeOfUsage(12030L);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);
        site.getSitePaymentSettings().setCreditCard(creditCard);
        site.getSitePaymentSettings().setRecurringPaymentId(null);

        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        manager.deactivate();

        Assert.assertEquals(SiteStatus.PENDING, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(null, site.getSitePaymentSettings().getCreditCard());
    }

    @Test
    public void testDeactivate_withAuthorizeNet_SUSPENDED() throws Exception {

        final Site site = TestUtil.createSite();
        CreditCard creditCard = TestUtil.createCreditCard(new Date(), site);
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.SUSPENDED);
        site.getSitePaymentSettings().setRemainingTimeOfUsage(12030L);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);
        site.getSitePaymentSettings().setCreditCard(creditCard);
        site.getSitePaymentSettings().setRecurringPaymentId(null);

        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        manager.deactivate();

        Assert.assertEquals(SiteStatus.PENDING, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(null, site.getSitePaymentSettings().getCreditCard());
    }

    @Test
    public void testDeactivate_withAuthorizeNet_PENDING() throws Exception {

        final Site site = TestUtil.createSite();
        CreditCard creditCard = TestUtil.createCreditCard(new Date(), site);
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.PENDING);
        site.getSitePaymentSettings().setRemainingTimeOfUsage(12030L);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);
        site.getSitePaymentSettings().setCreditCard(creditCard);
        site.getSitePaymentSettings().setRecurringPaymentId(null);

        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        manager.deactivate();

        Assert.assertEquals(SiteStatus.PENDING, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(null, site.getSitePaymentSettings().getCreditCard());
    }

    @Test
    public void testDeactivate_withAuthorizeNet_ACTIVE_andWithOldRecurringProfileId() throws Exception {

        final PayPalMock payPal = (PayPalMock) ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        final String profileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(profileId, PayPalRecurringProfileStatus.ACTIVE, "Note...");

        final Site site = TestUtil.createSite();
        CreditCard creditCard = TestUtil.createCreditCard(new Date(), site);
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        site.getSitePaymentSettings().setRemainingTimeOfUsage(12030L);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);
        site.getSitePaymentSettings().setCreditCard(creditCard);
        site.getSitePaymentSettings().setRecurringPaymentId(profileId);

        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        manager.deactivate();

        Assert.assertEquals(SiteStatus.PENDING, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(null, site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(PayPalRecurringProfileStatus.CANCELED, payPal.getProfileStatus(profileId));
    }
    /*--------------------------------------------------AuthorizeNet--------------------------------------------------*/

    @Test
    public void testDeactivate_withPaypal_ACTIVE() throws Exception {
        final PayPalMock payPal = (PayPalMock) ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        final String profileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(profileId, PayPalRecurringProfileStatus.ACTIVE, "Note...");

        final Site site = TestUtil.createSite();
        CreditCard creditCard = TestUtil.createCreditCard(new Date(), site);
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        site.getSitePaymentSettings().setRemainingTimeOfUsage(12030L);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.PAYPAL);
        site.getSitePaymentSettings().setCreditCard(creditCard);
        site.getSitePaymentSettings().setRecurringPaymentId(profileId);

        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        manager.deactivate();

        Assert.assertEquals(SiteStatus.PENDING, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(null, site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(PayPalRecurringProfileStatus.CANCELED, payPal.getProfileStatus(profileId));
    }

    @Test
    public void testDeactivate_withPaypal_SUSPENDED() throws Exception {
        final PayPalMock payPal = (PayPalMock) ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        final String profileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(profileId, PayPalRecurringProfileStatus.ACTIVE, "Note...");

        final Site site = TestUtil.createSite();
        CreditCard creditCard = TestUtil.createCreditCard(new Date(), site);
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.SUSPENDED);
        site.getSitePaymentSettings().setRemainingTimeOfUsage(12030L);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.PAYPAL);
        site.getSitePaymentSettings().setCreditCard(creditCard);
        site.getSitePaymentSettings().setRecurringPaymentId(profileId);

        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        manager.deactivate();

        Assert.assertEquals(SiteStatus.PENDING, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(null, site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(PayPalRecurringProfileStatus.CANCELED, payPal.getProfileStatus(profileId));
    }

    @Test
    public void testDeactivate_withPaypal_PENDING() throws Exception {
        final PayPalMock payPal = (PayPalMock) ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        final String profileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(profileId, PayPalRecurringProfileStatus.ACTIVE, "Note...");

        final Site site = TestUtil.createSite();
        CreditCard creditCard = TestUtil.createCreditCard(new Date(), site);
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.PENDING);
        site.getSitePaymentSettings().setRemainingTimeOfUsage(12030L);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.PAYPAL);
        site.getSitePaymentSettings().setCreditCard(creditCard);
        site.getSitePaymentSettings().setRecurringPaymentId(profileId);

        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        manager.deactivate();

        Assert.assertEquals(SiteStatus.PENDING, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(null, site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(PayPalRecurringProfileStatus.CANCELED, payPal.getProfileStatus(profileId));
    }


    @Test
    public void testDeactivate_withPaypal_withoutProfileId() throws Exception {
        final PayPalMock payPal = (PayPalMock) ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        final String profileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(profileId, PayPalRecurringProfileStatus.ACTIVE, "Note...");

        final Site site = TestUtil.createSite();
        CreditCard creditCard = TestUtil.createCreditCard(new Date(), site);
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        site.getSitePaymentSettings().setRemainingTimeOfUsage(12030L);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.PAYPAL);
        site.getSitePaymentSettings().setCreditCard(creditCard);
        site.getSitePaymentSettings().setRecurringPaymentId(null);

        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        boolean exceptionThrown = false;
        try {
            manager.deactivate();
        } catch (Exception e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(12030L, site.getSitePaymentSettings().getRemainingTimeOfUsage().longValue());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(creditCard, site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(PayPalRecurringProfileStatus.ACTIVE, payPal.getProfileStatus(profileId));
    }
    /*---------------------------------------------------Deactivate---------------------------------------------------*/


    /*--------------------------------------------Is Has To Be Deactivated--------------------------------------------*/

    @Test
    public void hasToBeDeactivated_withExpirationDateEqualCurrentDate() {
        final Config config = ServiceLocator.getConfigStorage().get();
        final BillingInfoProperties properties = config.getBillingInfoProperties();
        properties.setDeactivateSiteAfter((15 * 24 * 60));

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setExpirationDate(new Date());

        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        final boolean deactivateSite = manager.hasToBeDeactivated();
        Assert.assertFalse(deactivateSite);
    }

    @Test
    public void hasToBeDeactivated_withExpirationDateEqualCurrentDateMinus16Days() {
        final Config config = ServiceLocator.getConfigStorage().get();
        final BillingInfoProperties properties = config.getBillingInfoProperties();
        properties.setDeactivateSiteAfter((15 * 24 * 60));

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setExpirationDate(new Date(System.currentTimeMillis() - (16 * 24 * 60 * 60 * 1000L)));


        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        final boolean deactivateSite = manager.hasToBeDeactivated();
        Assert.assertTrue(deactivateSite);
    }

    @Test
    public void hasToBeDeactivated_withExpirationDateEqualCurrentDateMinus1Day_DeactivateSiteAfterEqual0() {
        final Config config = ServiceLocator.getConfigStorage().get();
        final BillingInfoProperties properties = config.getBillingInfoProperties();
        properties.setDeactivateSiteAfter(0);

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setExpirationDate(new Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000L)));

        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        final boolean deactivateSite = manager.hasToBeDeactivated();
        Assert.assertTrue(deactivateSite);
    }


    @Test
    public void hasToBeDeactivated_withExpirationDateEqualCurrentDatePlus1Day_DeactivateSiteAfterEqual0Days() {
        final Config config = ServiceLocator.getConfigStorage().get();
        final BillingInfoProperties properties = config.getBillingInfoProperties();
        properties.setDeactivateSiteAfter(0);

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setExpirationDate(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000L)));

        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        final boolean deactivateSite = manager.hasToBeDeactivated();
        Assert.assertFalse(deactivateSite);
    }

    @Test
    public void hasToBeDeactivated_withExpirationDateEqualCurrentDate_DeactivateSiteAfterEqual1Day() {
        final Config config = ServiceLocator.getConfigStorage().get();
        final BillingInfoProperties properties = config.getBillingInfoProperties();
        properties.setDeactivateSiteAfter((24 * 60));

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setExpirationDate(new Date());


        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        final boolean deactivateSite = manager.hasToBeDeactivated();
        Assert.assertFalse(deactivateSite);
    }
    /*--------------------------------------------Is Has To Be Deactivated--------------------------------------------*/

    /*------------------------------------------Get Days Before Deactivation------------------------------------------*/

    @Test
    public void getDaysBeforeDeactivation_withExpirationDateEqualCurrentDate_DeactivateSiteAfterEqual1Day() {
        final BillingInfoProperties properties = ServiceLocator.getConfigStorage().get().getBillingInfoProperties();
        properties.setDeactivateSiteAfter((24 * 60));


        final Date currentDate = new Date();
        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setExpirationDate(currentDate);


        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        final int daysBeforeDeactivation = manager.getDaysBeforeDeactivation();
        Assert.assertEquals(1, daysBeforeDeactivation);
    }

    @Test
    public void getDaysBeforeDeactivation_withExpirationDateEqualCurrentDate_DeactivateSiteAfterEqual3Days() {
        final BillingInfoProperties properties = ServiceLocator.getConfigStorage().get().getBillingInfoProperties();
        properties.setDeactivateSiteAfter((3 * 24 * 60));


        final Date currentDate = new Date();
        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setExpirationDate(currentDate);

        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        final int daysBeforeDeactivation = manager.getDaysBeforeDeactivation();
        Assert.assertEquals(true, (daysBeforeDeactivation == 3 || daysBeforeDeactivation == 2));
    }


    @Test
    public void getDaysBeforeDeactivation_withExpirationDateBeforeCurrentDate_DeactivateSiteAfterEqual0Days() {
        final BillingInfoProperties properties = ServiceLocator.getConfigStorage().get().getBillingInfoProperties();
        properties.setDeactivateSiteAfter(0);

        final Site site = TestUtil.createSite();
        site.getSitePaymentSettings().setExpirationDate(new Date(System.currentTimeMillis() - (24 * 60 * 60 * 1000L)));


        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        final int daysBeforeDeactivation = manager.getDaysBeforeDeactivation();
        Assert.assertEquals(0, daysBeforeDeactivation);
    }
    /*------------------------------------------Get Days Before Deactivation------------------------------------------*/


    /*---------------------------------------GetAppropriatePaymentSystem PayPal---------------------------------------*/

    @Test
    public void testGetAppropriatePaymentSystem_ChildSiteSettings_ShrogglePaypal() throws Exception {
        final ChildSiteSettings owner = new ChildSiteSettings();
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(new Site());
        registration.setUseOwnPaypal(false);
        owner.setChildSiteRegistration(registration);
        owner.getSitePaymentSettings().setPaymentMethod(PaymentMethod.PAYPAL);

        final PayPalMock defaultShrogglePaypal = new PayPalMock();
        ServiceLocator.setPayPal(defaultShrogglePaypal);
        final PaymentSystem paymentSystem = new PaymentSettingsOwnerManager(owner).getAppropriatePaymentSystem();
        Assert.assertTrue(paymentSystem instanceof PayPal);
        Assert.assertEquals(paymentSystem, defaultShrogglePaypal);
    }

    @Ignore("Unable to create PayPalReal")
    @Test
    public void testGetAppropriatePaymentSystem_ChildSiteSettings_OwnPaypal() throws Exception {
        final ChildSiteSettings owner = new ChildSiteSettings();
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(new Site());
        registration.setUseOwnPaypal(true);
        owner.setChildSiteRegistration(registration);
        owner.getSitePaymentSettings().setPaymentMethod(PaymentMethod.PAYPAL);

        final PayPalMock defaultShrogglePaypal = new PayPalMock();
        ServiceLocator.setPayPal(defaultShrogglePaypal);
        final PaymentSystem paymentSystem = new PaymentSettingsOwnerManager(owner).getAppropriatePaymentSystem();
        Assert.assertTrue(paymentSystem instanceof PayPal);
        Assert.assertNotSame(paymentSystem, defaultShrogglePaypal);
    }

    @Test
    public void testGetAppropriatePaymentSystem_ChildSite_ShrogglePaypal() throws Exception {
        final Site site = new Site();
        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        site.setChildSiteSettings(childSiteSettings);
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(new Site());
        registration.setUseOwnPaypal(false);
        childSiteSettings.setChildSiteRegistration(registration);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.PAYPAL);

        final PayPalMock defaultShrogglePaypal = new PayPalMock();
        ServiceLocator.setPayPal(defaultShrogglePaypal);
        final PaymentSystem paymentSystem = new PaymentSettingsOwnerManager(site).getAppropriatePaymentSystem();
        Assert.assertTrue(paymentSystem instanceof PayPal);
        Assert.assertEquals(paymentSystem, defaultShrogglePaypal);
    }

    @Ignore("Unable to create PayPalReal")
    @Test
    public void testGetAppropriatePaymentSystem_ChildSite_OwnPaypal() throws Exception {
        final Site site = new Site();
        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        site.setChildSiteSettings(childSiteSettings);
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(new Site());
        registration.setUseOwnPaypal(true);
        childSiteSettings.setChildSiteRegistration(registration);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.PAYPAL);

        final PayPalMock defaultShrogglePaypal = new PayPalMock();
        ServiceLocator.setPayPal(defaultShrogglePaypal);
        final PaymentSystem paymentSystem = new PaymentSettingsOwnerManager(site).getAppropriatePaymentSystem();
        Assert.assertTrue(paymentSystem instanceof PayPal);
        Assert.assertNotSame(paymentSystem, defaultShrogglePaypal);
    }

    @Test
    public void testGetAppropriatePaymentSystem_Site_Paypal() throws Exception {
        final Site site = new Site();
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.PAYPAL);

        final PayPalMock defaultShrogglePaypal = new PayPalMock();
        ServiceLocator.setPayPal(defaultShrogglePaypal);
        final PaymentSystem paymentSystem = new PaymentSettingsOwnerManager(site).getAppropriatePaymentSystem();
        Assert.assertTrue(paymentSystem instanceof PayPal);
        Assert.assertEquals(paymentSystem, defaultShrogglePaypal);
    }
    /*---------------------------------------GetAppropriatePaymentSystem PayPal---------------------------------------*/

    /*--------------------------------------GetAppropriatePaymentSystem Authorize-------------------------------------*/

    @Test
    public void testGetAppropriatePaymentSystem_ChildSiteSettings_ShroggleAuthorize() throws Exception {
        final ChildSiteSettings owner = new ChildSiteSettings();
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(new Site());
        registration.setUseOwnAuthorize(false);
        owner.setChildSiteRegistration(registration);
        owner.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);

        final AuthorizeNetMock defaultAuthorizeNet = new AuthorizeNetMock();
        ServiceLocator.setAuthorizeNet(defaultAuthorizeNet);
        final PaymentSystem paymentSystem = new PaymentSettingsOwnerManager(owner).getAppropriatePaymentSystem();
        Assert.assertTrue(paymentSystem instanceof AuthorizeNet);
        Assert.assertEquals(paymentSystem, defaultAuthorizeNet);
    }

    @Test
    public void testGetAppropriatePaymentSystem_ChildSiteSettings_OwnAuthorize() throws Exception {
        final ChildSiteSettings owner = new ChildSiteSettings();
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(new Site());
        registration.setUseOwnAuthorize(true);
        owner.setChildSiteRegistration(registration);
        owner.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);

        final AuthorizeNetMock defaultAuthorizeNet = new AuthorizeNetMock();
        ServiceLocator.setAuthorizeNet(defaultAuthorizeNet);
        final PaymentSystem paymentSystem = new PaymentSettingsOwnerManager(owner).getAppropriatePaymentSystem();
        Assert.assertTrue(paymentSystem instanceof AuthorizeNet);
        Assert.assertNotSame(paymentSystem, defaultAuthorizeNet);
    }

    @Test
    public void testGetAppropriatePaymentSystem_ChildSite_ShroggleAuthorize() throws Exception {
        final Site site = new Site();
        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        site.setChildSiteSettings(childSiteSettings);
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(new Site());
        registration.setUseOwnAuthorize(false);
        childSiteSettings.setChildSiteRegistration(registration);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);

        final AuthorizeNetMock defaultAuthorizeNet = new AuthorizeNetMock();
        ServiceLocator.setAuthorizeNet(defaultAuthorizeNet);
        final PaymentSystem paymentSystem = new PaymentSettingsOwnerManager(site).getAppropriatePaymentSystem();
        Assert.assertTrue(paymentSystem instanceof AuthorizeNet);
        Assert.assertEquals(paymentSystem, defaultAuthorizeNet);
    }

    @Test
    public void testGetAppropriatePaymentSystem_ChildSite_OwnAuthorize() throws Exception {
        final Site site = new Site();
        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        site.setChildSiteSettings(childSiteSettings);
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(new Site());
        registration.setUseOwnAuthorize(true);
        childSiteSettings.setChildSiteRegistration(registration);
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);

        final AuthorizeNetMock defaultAuthorizeNet = new AuthorizeNetMock();
        ServiceLocator.setAuthorizeNet(defaultAuthorizeNet);
        final PaymentSystem paymentSystem = new PaymentSettingsOwnerManager(site).getAppropriatePaymentSystem();
        Assert.assertTrue(paymentSystem instanceof AuthorizeNet);
        Assert.assertNotSame(paymentSystem, defaultAuthorizeNet);
    }

    @Test
    public void testGetAppropriatePaymentSystem_Site_Authorize() throws Exception {
        final Site site = new Site();
        site.getSitePaymentSettings().setPaymentMethod(PaymentMethod.AUTHORIZE_NET);

        final AuthorizeNetMock defaultAuthorizeNet = new AuthorizeNetMock();
        ServiceLocator.setAuthorizeNet(defaultAuthorizeNet);
        final PaymentSystem paymentSystem = new PaymentSettingsOwnerManager(site).getAppropriatePaymentSystem();
        Assert.assertTrue(paymentSystem instanceof AuthorizeNet);
        Assert.assertEquals(paymentSystem, defaultAuthorizeNet);
    }
    /*--------------------------------------GetAppropriatePaymentSystem Authorize-------------------------------------*/

    @Test
    public void testGetChildSiteSettingsByPaymentOwner_site() {
        final PaymentSettingsOwner owner = new Site();
        Assert.assertNull(new PaymentSettingsOwnerManager(owner).getChildSiteSettings());
    }

    @Test
    public void testGetChildSiteSettingsByPaymentOwner_childSiteSettings() {
        final PaymentSettingsOwner owner = new ChildSiteSettings();
        Assert.assertEquals(owner, new PaymentSettingsOwnerManager(owner).getChildSiteSettings());
    }

    @Test
    public void testGetChildSiteSettingsByPaymentOwner_siteWithChildSiteSettings() {
        Site site = new Site();
        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        site.setChildSiteSettings(childSiteSettings);
        Assert.assertEquals(childSiteSettings, new PaymentSettingsOwnerManager(site).getChildSiteSettings());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithoutPaymentSettingsOwner() {
        new PaymentSettingsOwnerManager(null);
    }

    @Test
    public void testGetSumByPaymentOwnerAndChargeType_site() {
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_250MB_MONTHLY_FEE).getPrice(), new PaymentSettingsOwnerManager(new Site()).getPriceByChargeType(ChargeType.SITE_250MB_MONTHLY_FEE));
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_500MB_MONTHLY_FEE).getPrice(), new PaymentSettingsOwnerManager(new Site()).getPriceByChargeType(ChargeType.SITE_500MB_MONTHLY_FEE));
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_1GB_MONTHLY_FEE).getPrice(), new PaymentSettingsOwnerManager(new Site()).getPriceByChargeType(ChargeType.SITE_1GB_MONTHLY_FEE));
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_3GB_MONTHLY_FEE).getPrice(), new PaymentSettingsOwnerManager(new Site()).getPriceByChargeType(ChargeType.SITE_3GB_MONTHLY_FEE));
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice(), new PaymentSettingsOwnerManager(new Site()).getPriceByChargeType(ChargeType.SITE_ANNUAL_FEE));
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_MONTHLY_FEE).getPrice(), new PaymentSettingsOwnerManager(new Site()).getPriceByChargeType(ChargeType.SITE_MONTHLY_FEE));
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_ONE_TIME_FEE).getPrice(), new PaymentSettingsOwnerManager(new Site()).getPriceByChargeType(ChargeType.SITE_ONE_TIME_FEE));
    }

    @Test
    public void testGetSumByPaymentOwnerAndChargeType_childSiteSettings() {
        ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.setPrice250mb(250);
        childSiteSettings.setPrice500mb(500);
        childSiteSettings.setPrice1gb(1000);
        childSiteSettings.setPrice3gb(3000);
        childSiteSettings.setOneTimeFee(10000);

        Assert.assertEquals(childSiteSettings.getPrice250mb(), new PaymentSettingsOwnerManager(childSiteSettings).getPriceByChargeType(ChargeType.SITE_250MB_MONTHLY_FEE));
        Assert.assertEquals(childSiteSettings.getPrice500mb(), new PaymentSettingsOwnerManager(childSiteSettings).getPriceByChargeType(ChargeType.SITE_500MB_MONTHLY_FEE));
        Assert.assertEquals(childSiteSettings.getPrice1gb(), new PaymentSettingsOwnerManager(childSiteSettings).getPriceByChargeType(ChargeType.SITE_1GB_MONTHLY_FEE));
        Assert.assertEquals(childSiteSettings.getPrice3gb(), new PaymentSettingsOwnerManager(childSiteSettings).getPriceByChargeType(ChargeType.SITE_3GB_MONTHLY_FEE));
        Assert.assertEquals(childSiteSettings.getOneTimeFee(), new PaymentSettingsOwnerManager(childSiteSettings).getPriceByChargeType(ChargeType.SITE_ONE_TIME_FEE));
    }

    @Test
    public void testGetSumByPaymentOwnerAndChargeType_siteWithChildSiteSettings() {
        ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.setPrice250mb(250);
        childSiteSettings.setPrice500mb(500);
        childSiteSettings.setPrice1gb(1000);
        childSiteSettings.setPrice3gb(3000);
        childSiteSettings.setOneTimeFee(10000);
        Site site = new Site();
        site.setChildSiteSettings(childSiteSettings);

        Assert.assertEquals(childSiteSettings.getPrice250mb(), new PaymentSettingsOwnerManager(site).getPriceByChargeType(ChargeType.SITE_250MB_MONTHLY_FEE));
        Assert.assertEquals(childSiteSettings.getPrice500mb(), new PaymentSettingsOwnerManager(site).getPriceByChargeType(ChargeType.SITE_500MB_MONTHLY_FEE));
        Assert.assertEquals(childSiteSettings.getPrice1gb(), new PaymentSettingsOwnerManager(site).getPriceByChargeType(ChargeType.SITE_1GB_MONTHLY_FEE));
        Assert.assertEquals(childSiteSettings.getPrice3gb(), new PaymentSettingsOwnerManager(site).getPriceByChargeType(ChargeType.SITE_3GB_MONTHLY_FEE));
        Assert.assertEquals(childSiteSettings.getOneTimeFee(), new PaymentSettingsOwnerManager(site).getPriceByChargeType(ChargeType.SITE_ONE_TIME_FEE));
    }

    @Test(expected = PaymentException.class)
    public void testGetSumByPaymentOwnerAndChargeType_childSiteSettings_wrongPaymentType_SITE_ANNUAL_FEE() {
        ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice(), new PaymentSettingsOwnerManager(childSiteSettings).getPriceByChargeType(ChargeType.SITE_ANNUAL_FEE));
    }

    @Test(expected = PaymentException.class)
    public void testGetSumByPaymentOwnerAndChargeType_childSiteSettings_wrongPaymentType_SITE_MONTHLY_FEE() {
        ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_MONTHLY_FEE).getPrice(), new PaymentSettingsOwnerManager(childSiteSettings).getPriceByChargeType(ChargeType.SITE_MONTHLY_FEE));
    }

    @Test
    public void testGetOwnerInfoForPaymentLog_forSite() {
        final Site site = TestUtil.createSite("sites title", "sites url");
        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        Assert.assertEquals("child site (sites title, siteId = " + site.getSiteId() + ")", manager.getInfoForPaymentLog());
    }

    @Test
    public void testGetOwnerInfoForPaymentLog_forChildSiteSettingsWithSite() {
        final ChildSiteSettings settings = new ChildSiteSettings();
        final Site site = TestUtil.createSite("sites title", "sites url");
        settings.setSite(site);
        ServiceLocator.getPersistance().putChildSiteSettings(settings);

        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(settings);
        Assert.assertEquals("child site (sites title, siteId = " + site.getSiteId() + ")", manager.getInfoForPaymentLog());
    }

    @Test
    public void testGetOwnerInfoForPaymentLog_forChildSiteSettingsWithoutSite() {
        final ChildSiteSettings settings = new ChildSiteSettings();
        ServiceLocator.getPersistance().putChildSiteSettings(settings);

        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(settings);
        Assert.assertEquals("child site settings (childSiteSettingsId = " + settings.getChildSiteSettingsId() + ", site has not been created yet.)", manager.getInfoForPaymentLog());
    }

    /*----------------------------------------------Get Payment Reason------------------------------------------------*/

    @Test
    public void testGetPaymentReason_site() {
        final Site site = TestUtil.createSite();
        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        Assert.assertEquals(PaymentReason.SHROGGLE_MONTHLY_PAYMENT, manager.getPaymentReason());
    }

    @Test
    public void testGetPaymentReason_childSite() {
        final Site site = TestUtil.createSite();
        site.setChildSiteSettings(new ChildSiteSettings());
        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        Assert.assertEquals(PaymentReason.CHILD_SITE_CREATION, manager.getPaymentReason());
    }

    @Test
    public void testGetPaymentReason_childSiteSettings() {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        final PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(childSiteSettings);
        Assert.assertEquals(PaymentReason.CHILD_SITE_CREATION, manager.getPaymentReason());
    }

    /*----------------------------------------------Get Payment Reason------------------------------------------------*/
    private final int YEAR = 365 * 24 * 60;
    private final int MONTH = 30 * 24 * 60;
}
