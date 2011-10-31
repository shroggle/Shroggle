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
package com.shroggle.util.payment.paypal;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.PayPalException;
import com.shroggle.logic.payment.PaymentResult;
import com.shroggle.logic.payment.PaymentSystemRequest;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.payment.PaymentSystem;
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
public class PayPalTest {

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

        final PaymentSystemRequest request = new PaymentSystemRequest(site, 10.0, 1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, "token", null, ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(site.getSitePaymentSettings().getExpirationDate());
        request.setOldExpirationDate(currentExpirationDate.getTime());
        final PayPal payPal = ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        Assert.assertEquals(PaymentResult.ENFORCED, payPal.activatePendingPaymentSettingsOwner(request));

        final String profileId = site.getSitePaymentSettings().getRecurringPaymentId();
        Assert.assertNotNull(profileId);
        Assert.assertEquals(PayPalRecurringProfileStatus.ACTIVE, payPal.getProfileStatus(profileId));
        final MockRecurringProfile mockRecurringProfile = ((PayPalMock) payPal).getMockRecurringProfile(profileId);
        Assert.assertEquals(site.getSitePaymentSettings().getExpirationDate(), mockRecurringProfile.getProfileStartDate());
    }

    @Test
    public void testActivatePendingPaymentSettingsOwner_withPaypalException() {
        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        final Site site = TestUtil.createSite();
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        sitePaymentSettings.setRecurringPaymentId(null);
        site.setSitePaymentSettings(sitePaymentSettings);

        final PaymentSystemRequest request = new PaymentSystemRequest(site, 10.0, 1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, "token", null, ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(site.getSitePaymentSettings().getExpirationDate());
        request.setOldExpirationDate(currentExpirationDate.getTime());
        final PayPal payPal = new PayPalWithExceptionsInMethods();
        ServiceLocator.setPayPal(payPal);

        boolean exceptionThrown = false;
        try {
            payPal.activatePendingPaymentSettingsOwner(request);
        } catch (PayPalException e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        final String profileId = site.getSitePaymentSettings().getRecurringPaymentId();
        Assert.assertNull(profileId);
    }

    @Test
    public void testActivatePendingPaymentSettingsOwner_withOldRecurringProfile() {
        final PayPal payPal = ((PayPal)PaymentSystem.newInstance(PaymentMethod.PAYPAL));

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

        final PaymentSystemRequest request = new PaymentSystemRequest(site, 10.0, 1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, "token", null, ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(site.getSitePaymentSettings().getExpirationDate());
        request.setOldExpirationDate(currentExpirationDate.getTime());

        Assert.assertEquals(PaymentResult.ENFORCED, payPal.activatePendingPaymentSettingsOwner(request));

        final String profileId = site.getSitePaymentSettings().getRecurringPaymentId();
        Assert.assertNotNull(profileId);
        Assert.assertEquals(PayPalRecurringProfileStatus.ACTIVE, payPal.getProfileStatus(profileId));
        final MockRecurringProfile mockRecurringProfile = ((PayPalMock) payPal).getMockRecurringProfile(profileId);
        Assert.assertEquals(site.getSitePaymentSettings().getExpirationDate(), mockRecurringProfile.getProfileStartDate());

        Assert.assertEquals("Old recurring profile should be canceld!", PayPalRecurringProfileStatus.CANCELED, payPal.getProfileStatus(oldProfileId));
    }

    @Test
    public void testActivatePendingPaymentSettingsOwner_ACTIVE() {
        final PayPal payPal = ((PayPal)PaymentSystem.newInstance(PaymentMethod.PAYPAL));

        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        final Site site = TestUtil.createSite();
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        site.setSitePaymentSettings(sitePaymentSettings);

        final PaymentSystemRequest request = new PaymentSystemRequest(site, 10.0, 1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, "token", null, ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(site.getSitePaymentSettings().getExpirationDate());
        request.setOldExpirationDate(currentExpirationDate.getTime());

        boolean exceptionThrown = false;
        try {
            payPal.activatePendingPaymentSettingsOwner(request);
        } catch (PayPalException e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);


        Assert.assertNull("Recurring profile has not been created!", site.getSitePaymentSettings().getRecurringPaymentId());
    }

    @Test
    public void testActivatePendingPaymentSettingsOwner_SUSPENDED() {
        final PayPal payPal = ((PayPal)PaymentSystem.newInstance(PaymentMethod.PAYPAL));

        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        final Site site = TestUtil.createSite();
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.SUSPENDED);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        site.setSitePaymentSettings(sitePaymentSettings);

        final PaymentSystemRequest request = new PaymentSystemRequest(site, 10.0, 1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, "token", null, ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(site.getSitePaymentSettings().getExpirationDate());
        request.setOldExpirationDate(currentExpirationDate.getTime());

        boolean exceptionThrown = false;
        try {
            payPal.activatePendingPaymentSettingsOwner(request);
        } catch (PayPalException e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        Assert.assertNull("Recurring profile has not been created!", site.getSitePaymentSettings().getRecurringPaymentId());
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

        final PaymentSystemRequest request = new PaymentSystemRequest(site, 10.0, 1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, "token", null, ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(site.getSitePaymentSettings().getExpirationDate());
        request.setOldExpirationDate(currentExpirationDate.getTime());
        final PayPal payPal = ((PayPal)PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        Assert.assertEquals(PaymentResult.ENFORCED, payPal.activateActivePaymentSettingsOwner(request));

        final String profileId = site.getSitePaymentSettings().getRecurringPaymentId();
        Assert.assertNotNull(profileId);
        Assert.assertEquals(PayPalRecurringProfileStatus.ACTIVE, payPal.getProfileStatus(profileId));
        final MockRecurringProfile mockRecurringProfile = ((PayPalMock) payPal).getMockRecurringProfile(profileId);
        Assert.assertEquals(site.getSitePaymentSettings().getExpirationDate(), mockRecurringProfile.getProfileStartDate());
    }

    @Test
    public void testActivateActivePaymentSettingsOwner_withPaypalException() {
        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        final Site site = TestUtil.createSite();
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        sitePaymentSettings.setRecurringPaymentId(null);
        site.setSitePaymentSettings(sitePaymentSettings);

        final PaymentSystemRequest request = new PaymentSystemRequest(site, 10.0, 1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, "token", null, ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(site.getSitePaymentSettings().getExpirationDate());
        request.setOldExpirationDate(currentExpirationDate.getTime());
        final PayPal payPal = new PayPalWithExceptionsInMethods();
        ServiceLocator.setPayPal(payPal);

        boolean exceptionThrown = false;
        try {
            payPal.activateActivePaymentSettingsOwner(request);
        } catch (PayPalException e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        final String profileId = site.getSitePaymentSettings().getRecurringPaymentId();
        Assert.assertNull(profileId);
    }

    @Test
    public void testActivateActivePaymentSettingsOwner_withOldRecurringProfile() {
        final PayPal payPal = ((PayPal)PaymentSystem.newInstance(PaymentMethod.PAYPAL));

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

        final PaymentSystemRequest request = new PaymentSystemRequest(site, 10.0, 1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, "token", null, ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(site.getSitePaymentSettings().getExpirationDate());
        request.setOldExpirationDate(currentExpirationDate.getTime());

        Assert.assertEquals(PaymentResult.ENFORCED, payPal.activateActivePaymentSettingsOwner(request));

        final String profileId = site.getSitePaymentSettings().getRecurringPaymentId();
        Assert.assertNotNull(profileId);
        Assert.assertEquals(PayPalRecurringProfileStatus.ACTIVE, payPal.getProfileStatus(profileId));
        final MockRecurringProfile mockRecurringProfile = ((PayPalMock) payPal).getMockRecurringProfile(profileId);
        Assert.assertEquals(site.getSitePaymentSettings().getExpirationDate(), mockRecurringProfile.getProfileStartDate());

        Assert.assertEquals("Old recurring profile should be canceld!", PayPalRecurringProfileStatus.CANCELED, payPal.getProfileStatus(oldProfileId));
    }

    @Test
    public void testActivateActivePaymentSettingsOwner_PENDING() {
        final PayPal payPal = ((PayPal)PaymentSystem.newInstance(PaymentMethod.PAYPAL));

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
            payPal.activateActivePaymentSettingsOwner(request);
        } catch (PayPalException e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);


        Assert.assertNull("Recurring profile has not been created!", site.getSitePaymentSettings().getRecurringPaymentId());
    }

    @Test
    public void testActivateActivePaymentSettingsOwner_SUSPENDED() {
        final PayPal payPal = ((PayPal)PaymentSystem.newInstance(PaymentMethod.PAYPAL));

        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        final Site site = TestUtil.createSite();
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.SUSPENDED);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        site.setSitePaymentSettings(sitePaymentSettings);

        final PaymentSystemRequest request = new PaymentSystemRequest(site, 10.0, 1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, "token", null, ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(site.getSitePaymentSettings().getExpirationDate());
        request.setOldExpirationDate(currentExpirationDate.getTime());

        boolean exceptionThrown = false;
        try {
            payPal.activateActivePaymentSettingsOwner(request);
        } catch (PayPalException e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        Assert.assertNull("Recurring profile has not been created!", site.getSitePaymentSettings().getRecurringPaymentId());
    }

    /*------------------------------------Activate Active Payment Settings Owner--------------------------------------*/

    /*------------------------------------Prolong Active Payment Settings Owner---------------------------------------*/

    @Test
    public void testProlongActivePaymentSettingsOwner() {
        final PayPal payPal = ((PayPal)PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        final String oldProfileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(oldProfileId, PayPalRecurringProfileStatus.ACTIVE, "Note...");
        final Site site = TestUtil.createSite();
        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        sitePaymentSettings.setRecurringPaymentId(oldProfileId);
        site.setSitePaymentSettings(sitePaymentSettings);


        payPal.prolongActivity(site, new Date(), new Date());


        // Everything is the same. We just check paypal recurring profile status in this method.
        Assert.assertEquals(PayPalRecurringProfileStatus.ACTIVE, payPal.getProfileStatus(oldProfileId));
        Assert.assertEquals(SiteStatus.ACTIVE, sitePaymentSettings.getSiteStatus());
        Assert.assertEquals(ChargeType.SITE_MONTHLY_FEE, sitePaymentSettings.getChargeType());
        Assert.assertEquals(oldProfileId, sitePaymentSettings.getRecurringPaymentId());
        Assert.assertEquals(site.getSitePaymentSettings(), sitePaymentSettings);
    }

    @Test
    public void testProlongActivePaymentSettingsOwner_withoutRecurringProfileId() {
        final PayPal payPal = ((PayPal)PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        final String oldProfileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(oldProfileId, PayPalRecurringProfileStatus.ACTIVE, "Note...");
        final Site site = TestUtil.createSite();
        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        sitePaymentSettings.setRecurringPaymentId(null);
        site.setSitePaymentSettings(sitePaymentSettings);


        boolean exceptionThrown = false;
        try {
            payPal.prolongActivity(site, new Date(), new Date());
        } catch (Exception e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        // Everything is the same. We just check paypal recurring profile status in this method.
        Assert.assertEquals(PayPalRecurringProfileStatus.ACTIVE, payPal.getProfileStatus(oldProfileId));
        Assert.assertEquals(SiteStatus.ACTIVE, sitePaymentSettings.getSiteStatus());
        Assert.assertEquals(ChargeType.SITE_MONTHLY_FEE, sitePaymentSettings.getChargeType());
        Assert.assertEquals(null, sitePaymentSettings.getRecurringPaymentId());
        Assert.assertEquals(site.getSitePaymentSettings(), sitePaymentSettings);
    }

    @Test
    public void testProlongActivePaymentSettingsOwner_PENDING_Profile() {
        final PayPal payPal = ((PayPal)PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        final String oldProfileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(oldProfileId, PayPalRecurringProfileStatus.PENDING, "Note...");
        final Site site = TestUtil.createSite();
        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        sitePaymentSettings.setRecurringPaymentId(oldProfileId);
        site.setSitePaymentSettings(sitePaymentSettings);


        boolean exceptionThrown = false;
        try {
            payPal.prolongActivity(site, new Date(), new Date());
        } catch (Exception e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        // Everything is the same. We just check paypal recurring profile status in this method.
        Assert.assertEquals(PayPalRecurringProfileStatus.PENDING, payPal.getProfileStatus(oldProfileId));
        Assert.assertEquals(SiteStatus.ACTIVE, sitePaymentSettings.getSiteStatus());
        Assert.assertEquals(ChargeType.SITE_MONTHLY_FEE, sitePaymentSettings.getChargeType());
        Assert.assertEquals(oldProfileId, sitePaymentSettings.getRecurringPaymentId());
        Assert.assertEquals(site.getSitePaymentSettings(), sitePaymentSettings);
    }

    @Test
    public void testProlongActivePaymentSettingsOwner_SUSPENDED_Profile() {
        final PayPal payPal = ((PayPal)PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        final String oldProfileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(oldProfileId, PayPalRecurringProfileStatus.SUSPENDED, "Note...");
        final Site site = TestUtil.createSite();
        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        sitePaymentSettings.setRecurringPaymentId(oldProfileId);
        site.setSitePaymentSettings(sitePaymentSettings);


        boolean exceptionThrown = false;
        try {
            payPal.prolongActivity(site, new Date(), new Date());
        } catch (Exception e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        // Everything is the same. We just check paypal recurring profile status in this method.
        Assert.assertEquals(PayPalRecurringProfileStatus.SUSPENDED, payPal.getProfileStatus(oldProfileId));
        Assert.assertEquals(SiteStatus.ACTIVE, sitePaymentSettings.getSiteStatus());
        Assert.assertEquals(ChargeType.SITE_MONTHLY_FEE, sitePaymentSettings.getChargeType());
        Assert.assertEquals(oldProfileId, sitePaymentSettings.getRecurringPaymentId());
        Assert.assertEquals(site.getSitePaymentSettings(), sitePaymentSettings);
    }
    /*------------------------------------Prolong Active Payment Settings Owner---------------------------------------*/

    @Test
    public void testActivateSuspended() {
        final PayPal payPal = ((PayPal)PaymentSystem.newInstance(PaymentMethod.PAYPAL));

        final String profileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(profileId, PayPalRecurringProfileStatus.SUSPENDED, "Note...");

        Assert.assertEquals(PaymentResult.ENFORCED, payPal.activateSuspendedRecurringProfile(profileId));

        Assert.assertEquals(PayPalRecurringProfileStatus.ACTIVE, payPal.getProfileStatus(profileId));
    }

    @Test(expected = PayPalException.class)
    public void testActivateSuspended_withoutProfileId() {
        final PayPal payPal = ((PayPal)PaymentSystem.newInstance(PaymentMethod.PAYPAL));

        final String profileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(profileId, PayPalRecurringProfileStatus.SUSPENDED, "Note...");

        Assert.assertEquals(PaymentResult.ENFORCED, payPal.activateSuspendedRecurringProfile(null));
    }

    @Test
    public void testSuspendActive() {
        final PayPal payPal = ((PayPal)PaymentSystem.newInstance(PaymentMethod.PAYPAL));

        final String profileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(profileId, PayPalRecurringProfileStatus.ACTIVE, "Note...");

        payPal.suspendActiveRecurringProfile(profileId);

        Assert.assertEquals(PayPalRecurringProfileStatus.SUSPENDED, payPal.getProfileStatus(profileId));
    }

    @Test(expected = PayPalException.class)
    public void testSuspendActive_withoutProfileId() {
        final PayPal payPal = ((PayPal)PaymentSystem.newInstance(PaymentMethod.PAYPAL));

        final String profileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(profileId, PayPalRecurringProfileStatus.ACTIVE, "Note...");

        payPal.suspendActiveRecurringProfile(null);
    }

    @Test
    public void testCancelRecurringProfile() {
        final PayPal payPal = ((PayPal)PaymentSystem.newInstance(PaymentMethod.PAYPAL));

        final String profileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(profileId, PayPalRecurringProfileStatus.ACTIVE, "Note...");

        payPal.cancelRecurringProfile(profileId);

        Assert.assertEquals(PayPalRecurringProfileStatus.CANCELED, payPal.getProfileStatus(profileId));
    }

    @Test(expected = PayPalException.class)
    public void testCancelRecurringProfile_withoutProfileId() {
        final PayPal payPal = ((PayPal)PaymentSystem.newInstance(PaymentMethod.PAYPAL));

        final String profileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(profileId, PayPalRecurringProfileStatus.ACTIVE, "Note...");

        payPal.cancelRecurringProfile(null);
    }
}
