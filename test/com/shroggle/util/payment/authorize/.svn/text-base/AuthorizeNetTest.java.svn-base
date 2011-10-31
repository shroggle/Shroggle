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
package com.shroggle.util.payment.authorize;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.AuthorizeNetException;
import com.shroggle.logic.payment.PaymentResult;
import com.shroggle.logic.payment.PaymentSystemRequest;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.payment.PaymentSystem;
import com.shroggle.util.payment.paypal.PayPal;
import com.shroggle.util.payment.paypal.PayPalMock;
import com.shroggle.util.payment.paypal.PayPalRecurringProfileStatus;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class AuthorizeNetTest {

    private final AuthorizeNet authorizeNet = (AuthorizeNet)PaymentSystem.newInstance(PaymentMethod.AUTHORIZE_NET);

/*------------------------------------Activate Pending Payment Settings Owner-------------------------------------*/

    @Test
    public void testActivatePendingPaymentSettingsOwner() {
        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        final Site site = TestUtil.createSite();
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        sitePaymentSettings.setRecurringPaymentId(null);
        site.setSitePaymentSettings(sitePaymentSettings);

        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), site);

        final PaymentSystemRequest request = new PaymentSystemRequest(site, 10.0, 1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, null, creditCard, ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(site.getSitePaymentSettings().getExpirationDate());
        request.setOldExpirationDate(currentExpirationDate.getTime());
        request.setOldExpirationDate(currentExpirationDate.getTime());
        Assert.assertEquals(PaymentResult.ENFORCED, authorizeNet.activatePendingPaymentSettingsOwner(request));

        Assert.assertEquals(creditCard, site.getSitePaymentSettings().getCreditCard());
    }

    @Test
    public void testActivatePendingPaymentSettingsOwner_withTestCredirCard() {
        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        final Site site = TestUtil.createSite();
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        sitePaymentSettings.setRecurringPaymentId(null);
        site.setSitePaymentSettings(sitePaymentSettings);

        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), site);
        creditCard.setCreditCardNumber("4444333322221111");

        final PaymentSystemRequest request = new PaymentSystemRequest(site, 10.0, 1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, null, creditCard, ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(site.getSitePaymentSettings().getExpirationDate());
        request.setOldExpirationDate(currentExpirationDate.getTime());
        Assert.assertEquals(PaymentResult.SKIPPED, authorizeNet.activatePendingPaymentSettingsOwner(request));

        Assert.assertEquals(creditCard, site.getSitePaymentSettings().getCreditCard());
    }


    @Test
    public void testActivatePendingPaymentSettingsOwner_withOldPaypalProfile() {
        final PayPalMock payPal = (PayPalMock) ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL));

        final String oldProfileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(oldProfileId, PayPalRecurringProfileStatus.ACTIVE, "Note...");

        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        final Site site = TestUtil.createSite();
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        sitePaymentSettings.setRecurringPaymentId(oldProfileId);
        site.setSitePaymentSettings(sitePaymentSettings);

        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), site);

        final PaymentSystemRequest request = new PaymentSystemRequest(site, 10.0, 1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, null, creditCard, ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(site.getSitePaymentSettings().getExpirationDate());
        request.setOldExpirationDate(currentExpirationDate.getTime());
        Assert.assertEquals(PaymentResult.ENFORCED, authorizeNet.activatePendingPaymentSettingsOwner(request));

        Assert.assertEquals(creditCard, site.getSitePaymentSettings().getCreditCard());
        Assert.assertNull(site.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals("Old recurring profile should be canceld!", PayPalRecurringProfileStatus.CANCELED, payPal.getProfileStatus(oldProfileId));
    }

    @Test
    public void testActivatePendingPaymentSettingsOwner_withoutCreditCard() {
        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        final Site site = TestUtil.createSite();
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        site.setSitePaymentSettings(sitePaymentSettings);

        final PaymentSystemRequest request = new PaymentSystemRequest(site, 10.0, 1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, "token", null, ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(site.getSitePaymentSettings().getExpirationDate());
        request.setOldExpirationDate(currentExpirationDate.getTime());


        boolean exceptionThrown = false;
        try {
            authorizeNet.activatePendingPaymentSettingsOwner(request);
        } catch (AuthorizeNetException e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        Assert.assertNull("Recurring profile has not been created!", site.getSitePaymentSettings().getCreditCard());
    }

    @Test
    public void testActivatePendingPaymentSettingsOwner_ACTIVE() {
        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        final Site site = TestUtil.createSite();
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        site.setSitePaymentSettings(sitePaymentSettings);

        final PaymentSystemRequest request = new PaymentSystemRequest(site, 10.0, 1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, "token", new CreditCard(), ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(site.getSitePaymentSettings().getExpirationDate());
        request.setOldExpirationDate(currentExpirationDate.getTime());


        boolean exceptionThrown = false;
        try {
            authorizeNet.activatePendingPaymentSettingsOwner(request);
        } catch (AuthorizeNetException e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        Assert.assertNull("Recurring profile has not been created!", site.getSitePaymentSettings().getCreditCard());
    }

    @Test
    public void testActivatePendingPaymentSettingsOwner_SUSPENDED() {
        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        final Site site = TestUtil.createSite();
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.SUSPENDED);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        site.setSitePaymentSettings(sitePaymentSettings);

        final PaymentSystemRequest request = new PaymentSystemRequest(site, 10.0, 1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, "token", new CreditCard(), ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(site.getSitePaymentSettings().getExpirationDate());
        request.setOldExpirationDate(currentExpirationDate.getTime());

        boolean exceptionThrown = false;
        try {
            authorizeNet.activatePendingPaymentSettingsOwner(request);
        } catch (AuthorizeNetException e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        Assert.assertNull("Recurring profile has not been created!", site.getSitePaymentSettings().getCreditCard());
    }

    /*------------------------------------Activate Pending Payment Settings Owner-------------------------------------*/

    /*------------------------------------Activate Active Payment Settings Owner--------------------------------------*/

    @Test
    public void testActivateActivePaymentSettingsOwner() {
        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        final Site site = TestUtil.createSite();
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        sitePaymentSettings.setRecurringPaymentId(null);
        site.setSitePaymentSettings(sitePaymentSettings);

        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), site);

        final PaymentSystemRequest request = new PaymentSystemRequest(site, 10.0, 1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, null, creditCard, ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(site.getSitePaymentSettings().getExpirationDate());
        request.setOldExpirationDate(currentExpirationDate.getTime());
        Assert.assertEquals(PaymentResult.ENFORCED, authorizeNet.activateActivePaymentSettingsOwner(request));

        Assert.assertEquals(creditCard, site.getSitePaymentSettings().getCreditCard());
    }


    @Test
    public void testActivateActivePaymentSettingsOwner_withOldPaypalProfile() {
        final PayPalMock payPal = (PayPalMock) ((PayPal)PaymentSystem.newInstance(PaymentMethod.PAYPAL));

        final String oldProfileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(oldProfileId, PayPalRecurringProfileStatus.ACTIVE, "Note...");

        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        final Site site = TestUtil.createSite();
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        sitePaymentSettings.setRecurringPaymentId(oldProfileId);
        site.setSitePaymentSettings(sitePaymentSettings);

        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), site);

        final PaymentSystemRequest request = new PaymentSystemRequest(site, 10.0, 1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, null, creditCard, ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(site.getSitePaymentSettings().getExpirationDate());
        request.setOldExpirationDate(currentExpirationDate.getTime());
        Assert.assertEquals(PaymentResult.ENFORCED, authorizeNet.activateActivePaymentSettingsOwner(request));

        Assert.assertEquals(creditCard, site.getSitePaymentSettings().getCreditCard());
        Assert.assertNull(site.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals("Old recurring profile should be canceld!", PayPalRecurringProfileStatus.CANCELED, payPal.getProfileStatus(oldProfileId));
    }


    @Test
    public void testActivateActivePaymentSettingsOwner_PENDING() {
        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        final Site site = TestUtil.createSite();
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        site.setSitePaymentSettings(sitePaymentSettings);

        final PaymentSystemRequest request = new PaymentSystemRequest(site, 10.0, 1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, "token", new CreditCard(), ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(site.getSitePaymentSettings().getExpirationDate());
        request.setOldExpirationDate(currentExpirationDate.getTime());

        boolean exceptionThrown = false;
        try {
            authorizeNet.activateActivePaymentSettingsOwner(request);
        } catch (AuthorizeNetException e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        Assert.assertNull("Recurring profile has not been created!", site.getSitePaymentSettings().getCreditCard());
    }

    @Test
    public void testActivateActivePaymentSettingsOwner_SUSPENDED() {
        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        final Site site = TestUtil.createSite();
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.SUSPENDED);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        site.setSitePaymentSettings(sitePaymentSettings);

        final PaymentSystemRequest request = new PaymentSystemRequest(site, 10.0, 1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, "token", new CreditCard(), ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(site.getSitePaymentSettings().getExpirationDate());
        request.setOldExpirationDate(currentExpirationDate.getTime());

        boolean exceptionThrown = false;
        try {
            authorizeNet.activateActivePaymentSettingsOwner(request);
        } catch (AuthorizeNetException e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        Assert.assertNull("Recurring profile has not been created!", site.getSitePaymentSettings().getCreditCard());
    }

    @Test
    public void testActivateActivePaymentSettingsOwner_withoutCreditCard() {
        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        final Site site = TestUtil.createSite();
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        site.setSitePaymentSettings(sitePaymentSettings);

        final PaymentSystemRequest request = new PaymentSystemRequest(site, 10.0, 1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, "token", null, ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(site.getSitePaymentSettings().getExpirationDate());
        request.setOldExpirationDate(currentExpirationDate.getTime());


        boolean exceptionThrown = false;
        try {
            authorizeNet.activateActivePaymentSettingsOwner(request);
        } catch (AuthorizeNetException e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        Assert.assertNull("Recurring profile has not been created!", site.getSitePaymentSettings().getCreditCard());
    }

    /*------------------------------------Activate Active Payment Settings Owner--------------------------------------*/

    /*------------------------------------------------Prolong Activity------------------------------------------------*/

    @Test
    public void testProlongActivity() {

        final Site site = TestUtil.createSite();
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        sitePaymentSettings.setCreditCard(creditCard);
        site.setSitePaymentSettings(sitePaymentSettings);


        boolean exceptionThrown = false;
        try {
            Assert.assertEquals(PaymentResult.ENFORCED, authorizeNet.prolongActivity(site, new Date(), new Date()));
        } catch (Exception e) {
            exceptionThrown = true;
        }
        Assert.assertFalse(exceptionThrown);

        // Everything is the same. We just check paypal recurring profile status in this method.
        Assert.assertEquals(SiteStatus.ACTIVE, sitePaymentSettings.getSiteStatus());
        Assert.assertEquals(ChargeType.SITE_MONTHLY_FEE, sitePaymentSettings.getChargeType());
        Assert.assertEquals(site.getSitePaymentSettings(), sitePaymentSettings);
        Assert.assertEquals(creditCard, site.getSitePaymentSettings().getCreditCard());
    }

    @Test
    public void testProlongActivity_forTestCreditCard() {

        final Site site = TestUtil.createSite();
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());
        creditCard.setCreditCardNumber("4444333322221111");

        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        sitePaymentSettings.setCreditCard(creditCard);
        site.setSitePaymentSettings(sitePaymentSettings);


        boolean exceptionThrown = false;
        try {
            Assert.assertEquals(PaymentResult.SKIPPED, authorizeNet.prolongActivity(site, new Date(), new Date()));
        } catch (Exception e) {
            exceptionThrown = true;
        }
        Assert.assertFalse(exceptionThrown);

        // Everything is the same. We just check paypal recurring profile status in this method.
        Assert.assertEquals(SiteStatus.ACTIVE, sitePaymentSettings.getSiteStatus());
        Assert.assertEquals(ChargeType.SITE_MONTHLY_FEE, sitePaymentSettings.getChargeType());
        Assert.assertEquals(site.getSitePaymentSettings(), sitePaymentSettings);
        Assert.assertEquals(creditCard, site.getSitePaymentSettings().getCreditCard());
    }

    @Test
    public void testProlongActivity_withoutCreditCard() {

        final Site site = TestUtil.createSite();
        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        sitePaymentSettings.setCreditCard(null);
        site.setSitePaymentSettings(sitePaymentSettings);


        boolean exceptionThrown = false;
        try {
            authorizeNet.prolongActivity(site, new Date(), new Date());
        } catch (Exception e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        // Everything is the same. We just check paypal recurring profile status in this method.
        Assert.assertEquals(SiteStatus.ACTIVE, sitePaymentSettings.getSiteStatus());
        Assert.assertEquals(ChargeType.SITE_MONTHLY_FEE, sitePaymentSettings.getChargeType());
        Assert.assertEquals(site.getSitePaymentSettings(), sitePaymentSettings);
        Assert.assertEquals(null, site.getSitePaymentSettings().getCreditCard());
    }

    @Test
    public void testProlongActivity_withAuthorizeNetException() {
        final AuthorizeNet authorizeNet = new AuthorizeNetWithExceptionsInMethods();
        ServiceLocator.setAuthorizeNet(authorizeNet);

        final Site site = TestUtil.createSite();
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        sitePaymentSettings.setCreditCard(creditCard);
        site.setSitePaymentSettings(sitePaymentSettings);


        boolean exceptionThrown = false;
        try {
            authorizeNet.prolongActivity(site, new Date(), new Date());
        } catch (Exception e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        // Everything is the same. We just check paypal recurring profile status in this method.
        Assert.assertEquals(SiteStatus.ACTIVE, sitePaymentSettings.getSiteStatus());
        Assert.assertEquals(ChargeType.SITE_MONTHLY_FEE, sitePaymentSettings.getChargeType());
        Assert.assertEquals(site.getSitePaymentSettings(), sitePaymentSettings);
        Assert.assertEquals(creditCard, site.getSitePaymentSettings().getCreditCard());
    }
    /*------------------------------------------------Prolong Activity------------------------------------------------*/


    // Nothing should happend here. This methods used only for paypal

    @Test
    public void activateSuspendedRecurringProfileTest() {
        Assert.assertEquals(PaymentResult.ENFORCED, authorizeNet.activateSuspendedRecurringProfile(null));
    }

}
