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
import com.shroggle.exception.PaymentInfoCreatorRequestNotFoundException;
import com.shroggle.exception.PaymentSettingsOwnerNotFoundException;
import com.shroggle.logic.site.billingInfo.ChargeTypeManager;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.mail.MockMailSender;
import com.shroggle.util.payment.PaymentSystem;
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
@RunWith(value = TestRunnerWithMockServices.class)
public class PaymentInfoCreatorTest {

    final MockMailSender mockMailSender = new MockMailSender();

    @Before
    public void before() {
        ServiceLocator.getConfigStorage().get().setApplicationUrl("www.site-builder.com");
        ServiceLocator.getConfigStorage().get().setUserSitesUrl("site-builder.com");
        ServiceLocator.setMailSender(mockMailSender);
    }

    /*-----------------------------------------------WITHOUT EXCEPTIONS-----------------------------------------------*/

    /*-------------------------------------Execute few times with different states------------------------------------*/

    /*-------------------------------------Execute few times with different states------------------------------------*/

    @Test
    public void testCreate_forPendingSiteWithAuthorizeNetThenForActiveSiteWithPaypalAndThenForActiveWithAuthorizeNet() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site site = TestUtil.createSite();
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2110, 1, 1, 0, 0);// we will not use this date.
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        site.setSitePaymentSettings(sitePaymentSettings);


        /*-----------------------------Executing first time for pending site with AuthorizeNet------------------------------*/
        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(site.getSiteId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_PENDING, state);
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_MONTHLY_FEE).getPrice(), state.getPrice());
        Assert.assertNotNull(site.getSitePaymentSettings());
        Assert.assertEquals(site.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_MONTHLY_FEE).getPrice(), site.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_MONTHLY_FEE, site.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, site.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(site.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(site.getSitePaymentSettings().getRemainingTimeOfUsage());

        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(System.currentTimeMillis() + DateUtil.minutesToMilliseconds(30 * 24 * 60)));// Current date + 30 days
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
        /*-----------------------------Executing first time for pending site with AuthorizeNet------------------------------*/


        /*----------------------------Executing second time for active site with paypal-------------------------------*/
        final PaymentInfoCreatorRequest request2 = new PaymentInfoCreatorRequest(site.getSiteId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_MONTHLY_FEE, PaymentMethod.PAYPAL, null, "tiken", null);
        final PaymentInfoCreator paymentInfoCreator2 = new PaymentInfoCreator(request2);
        final PaymentInfoCreatorState state2 = paymentInfoCreator2.create();

        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_ACTIVE, state2);
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_MONTHLY_FEE).getPrice(), state2.getPrice());
        Assert.assertNotNull(site.getSitePaymentSettings());
        Assert.assertEquals(site.getSitePaymentSettings().getPrice(), state2.getPrice());
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_MONTHLY_FEE).getPrice(), site.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_MONTHLY_FEE, site.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.PAYPAL, site.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());
//        Assert.assertEquals(creditCard, site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(site.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(site.getSitePaymentSettings().getRemainingTimeOfUsage());

        final String oldProfileId = site.getSitePaymentSettings().getRecurringPaymentId();

        Assert.assertNotNull(site.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(PayPalRecurringProfileStatus.ACTIVE, ((PayPal)PaymentSystem.newInstance(PaymentMethod.PAYPAL)).getProfileStatus(site.getSitePaymentSettings().getRecurringPaymentId()));

        final Calendar expectedExpirationDate2 = new GregorianCalendar();
        expectedExpirationDate2.setTime(new Date(System.currentTimeMillis() +
                DateUtil.minutesToMilliseconds(60 * 24 * 60)));// Current date + 60 days. Because we activate site second time.
        final Calendar currentExpirationDate2 = new GregorianCalendar();
        currentExpirationDate2.setTime(site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate2.get(Calendar.YEAR), currentExpirationDate2.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate2.get(Calendar.MONTH), currentExpirationDate2.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate2.get(Calendar.DAY_OF_MONTH), currentExpirationDate2.get(Calendar.DAY_OF_MONTH));
        /*----------------------------Executing second time for active site with paypal-------------------------------*/


        /*----------------------------Executing third time for active site with AuthorizeNet--------------------------------*/
        final CreditCard newCreditCard = TestUtil.createCreditCard(new Date(), new Site());

        final PaymentInfoCreatorRequest request3 = new PaymentInfoCreatorRequest(site.getSiteId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, newCreditCard);
        final PaymentInfoCreator paymentInfoCreator3 = new PaymentInfoCreator(request3);

        final PaymentInfoCreatorState state3 = paymentInfoCreator3.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_ACTIVE, state3);
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_MONTHLY_FEE).getPrice(), state3.getPrice());
        Assert.assertNotNull(site.getSitePaymentSettings());
        Assert.assertEquals(site.getSitePaymentSettings().getPrice(), state3.getPrice());
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_MONTHLY_FEE).getPrice(), site.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_MONTHLY_FEE, site.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, site.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(newCreditCard, site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(site.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(site.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertNull(site.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(PayPalRecurringProfileStatus.CANCELED, ((PayPal)PaymentSystem.newInstance(PaymentMethod.PAYPAL)).getProfileStatus(oldProfileId));

        final Calendar expectedExpirationDate3 = new GregorianCalendar();
        expectedExpirationDate3.setTime(new Date(System.currentTimeMillis() +
                DateUtil.minutesToMilliseconds(90 * 24 * 60)));// Current date + 90 days. Because we activate site third time.
        final Calendar currentExpirationDate3 = new GregorianCalendar();
        currentExpirationDate3.setTime(site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate3.get(Calendar.YEAR), currentExpirationDate3.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate3.get(Calendar.MONTH), currentExpirationDate3.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate3.get(Calendar.DAY_OF_MONTH), currentExpirationDate3.get(Calendar.DAY_OF_MONTH));
        /*----------------------------Executing third time for active site with AuthorizeNet--------------------------------*/
    }
    /*-------------------------------------Execute few times with different states------------------------------------*/

    /*------------------------------------------------Site with Javien------------------------------------------------*/

    @Test
    public void testCreate_forActiveSiteWithJavien() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site site = TestUtil.createSite();
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2010, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        site.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(site.getSiteId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_ANNUAL_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_ACTIVE, state);
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice(), state.getPrice());
        Assert.assertNotNull(site.getSitePaymentSettings());
        Assert.assertEquals(site.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice(), site.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_ANNUAL_FEE, site.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, site.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(site.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(site.getSitePaymentSettings().getRemainingTimeOfUsage());

        final Calendar expectedExpirationDate = new GregorianCalendar(2011, 1, 1, 0, 0);
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreate_forActiveSiteWithJavien_2() {
        final User user = TestUtil.createUser();
        user.setFirstName("Anatoliy");
        user.setLastName("Balakirev");

        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site site = TestUtil.createSite();
        site.setTitle("'My Site'");
        site.setSubDomain("site");
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setUserId(user.getUserId());
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2010, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        site.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(site.getSiteId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_ANNUAL_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_ACTIVE, state);
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice(), state.getPrice());
        Assert.assertNotNull(site.getSitePaymentSettings());
        Assert.assertEquals(site.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice(), site.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_ANNUAL_FEE, site.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, site.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(site.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(site.getSitePaymentSettings().getRemainingTimeOfUsage());

        final Calendar expectedExpirationDate = new GregorianCalendar(2011, 1, 1, 0, 0);
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));

        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals("Web-Deva has received payment for 'My Site'", mockMailSender.getMails().get(0).getSubject());
        Assert.assertEquals("Dear Anatoliy Balakirev,\n" +
                "\n" +
                "Thank you for choosing Web-Deva. This email confirms that you have paid for 1 year of service and hosting from Web-Deva.com\n" +
                "\n" +
                "Your site has been activated and is available on http://site.site-builder.com address.\n" +
                "\n" +
                "Your credit card / paypal account was charged on " + DateUtil.toMonthDayAndYear(new Date()) + " in the amount of: $300\n" +
                "\n" +
                "Your next payment will be charged automatically on 02/01/2011\n" +
                "\n" +
                "To cancel service at any time log into your account and select `Deactivate.`\n" +
                "\n" +
                "\n" +
                "\n" +
                "Web-Deva is a fabulous new tool for creating and maintaining professional web sites at the push of a button. Web-Deva allows you to create functionally rich sites, with plenty of media.\n" +
                "\n" +
                "If you have any questions regarding your account, refer to the FAQs at http://www.demo." + ServiceLocator.getConfigStorage().get().getUserSitesUrl() + "/FAQ\n" +
                "\n" +
                "or contact us directly with any questions or comments: " + ServiceLocator.getConfigStorage().get().getSupportEmail(), mockMailSender.getMails().get(0).getText());
    }

    @Test
    public void testCreate_forPendingSiteWithJavien() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site site = TestUtil.createSite();
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2110, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        site.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(site.getSiteId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_ANNUAL_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_PENDING, state);
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice(), state.getPrice());
        Assert.assertNotNull(site.getSitePaymentSettings());
        Assert.assertEquals(site.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice(), site.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_ANNUAL_FEE, site.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, site.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(site.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(site.getSitePaymentSettings().getRemainingTimeOfUsage());

        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date());
        expectedExpirationDate.set(Calendar.DAY_OF_MONTH, (expectedExpirationDate.get(Calendar.DAY_OF_MONTH) + 365));
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreate_forSuspendedSiteWithJavien() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site site = TestUtil.createSite();
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.SUSPENDED);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should not be changed for this state
        sitePaymentSettings.setRemainingTimeOfUsage((10 * 24 * 60 * 60 * 1000L));// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2110, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        site.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(site.getSiteId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_ANNUAL_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_SUSPENDED, state);
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice(), state.getPrice());
        Assert.assertNotNull(site.getSitePaymentSettings());
        Assert.assertEquals(site.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice(), site.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_ANNUAL_FEE, site.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, site.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(site.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(site.getSitePaymentSettings().getRemainingTimeOfUsage());

        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date());
        expectedExpirationDate.set(Calendar.DAY_OF_MONTH, (expectedExpirationDate.get(Calendar.DAY_OF_MONTH) + 10));
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }
    /*------------------------------------------------Site with Javien------------------------------------------------*/

    /*------------------------------------------------Site with AuthorizeNet------------------------------------------------*/

    @Test
    public void testCreate_forActiveSiteWithAuthorizeNet() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site site = TestUtil.createSite();
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2010, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        site.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(site.getSiteId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_ANNUAL_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_ACTIVE, state);
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice(), state.getPrice());
        Assert.assertNotNull(site.getSitePaymentSettings());
        Assert.assertEquals(site.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice(), site.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_ANNUAL_FEE, site.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, site.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(site.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(site.getSitePaymentSettings().getRemainingTimeOfUsage());

        final Calendar expectedExpirationDate = new GregorianCalendar(2011, 1, 1, 0, 0);
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreate_forActiveSiteWithAuthorizeNet_2() {
        final User user = TestUtil.createUser();
        user.setFirstName("Anatoliy");
        user.setLastName("Balakirev");

        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site site = TestUtil.createSite();
        site.setTitle("'My Site'");
        site.setSubDomain("site");
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setUserId(user.getUserId());
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2010, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        site.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(site.getSiteId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_ANNUAL_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_ACTIVE, state);
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice(), state.getPrice());
        Assert.assertNotNull(site.getSitePaymentSettings());
        Assert.assertEquals(site.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice(), site.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_ANNUAL_FEE, site.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, site.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(site.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(site.getSitePaymentSettings().getRemainingTimeOfUsage());

        final Calendar expectedExpirationDate = new GregorianCalendar(2011, 1, 1, 0, 0);
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));

        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals("Web-Deva has received payment for 'My Site'", mockMailSender.getMails().get(0).getSubject());
        Assert.assertEquals("Dear Anatoliy Balakirev,\n" +
                "\n" +
                "Thank you for choosing Web-Deva. This email confirms that you have paid for 1 year of service and hosting from Web-Deva.com\n" +
                "\n" +
                "Your site has been activated and is available on http://site.site-builder.com address.\n" +
                "\n" +
                "Your credit card / paypal account was charged on " + DateUtil.toMonthDayAndYear(new Date()) + " in the amount of: $300\n" +
                "\n" +
                "Your next payment will be charged automatically on 02/01/2011\n" +
                "\n" +
                "To cancel service at any time log into your account and select `Deactivate.`\n" +
                "\n" +
                "\n" +
                "\n" +
                "Web-Deva is a fabulous new tool for creating and maintaining professional web sites at the push of a button. Web-Deva allows you to create functionally rich sites, with plenty of media.\n" +
                "\n" +
                "If you have any questions regarding your account, refer to the FAQs at http://www.demo." + ServiceLocator.getConfigStorage().get().getUserSitesUrl() + "/FAQ\n" +
                "\n" +
                "or contact us directly with any questions or comments: " + ServiceLocator.getConfigStorage().get().getSupportEmail(), mockMailSender.getMails().get(0).getText());
    }

    @Test
    public void testCreate_forPendingSiteWithAuthorizeNet() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site site = TestUtil.createSite();
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2110, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        site.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(site.getSiteId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_ANNUAL_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_PENDING, state);
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice(), state.getPrice());
        Assert.assertNotNull(site.getSitePaymentSettings());
        Assert.assertEquals(site.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice(), site.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_ANNUAL_FEE, site.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, site.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(site.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(site.getSitePaymentSettings().getRemainingTimeOfUsage());

        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date());
        expectedExpirationDate.set(Calendar.DAY_OF_MONTH, (expectedExpirationDate.get(Calendar.DAY_OF_MONTH) + 365));
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreate_forSuspendedSiteWithAuthorizeNet() {

        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site site = TestUtil.createSite();
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.SUSPENDED);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should not be changed for this state
        sitePaymentSettings.setRemainingTimeOfUsage((10 * 24 * 60 * 60 * 1000L));// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2110, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        site.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(site.getSiteId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_ANNUAL_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_SUSPENDED, state);
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice(), state.getPrice());
        Assert.assertNotNull(site.getSitePaymentSettings());
        Assert.assertEquals(site.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice(), site.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_ANNUAL_FEE, site.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, site.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(site.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(site.getSitePaymentSettings().getRemainingTimeOfUsage());

        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date());
        expectedExpirationDate.set(Calendar.DAY_OF_MONTH, (expectedExpirationDate.get(Calendar.DAY_OF_MONTH) + 10));
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }
    /*------------------------------------------------Site with AuthorizeNet------------------------------------------------*/

    /*------------------------------------------------Site with Paypal------------------------------------------------*/

    @Test
    public void testCreate_forActiveSiteWithPaypal() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site site = TestUtil.createSite();
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.AUTHORIZE_NET);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2010, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        site.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(site.getSiteId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_ANNUAL_FEE, PaymentMethod.PAYPAL, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_ACTIVE, state);
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice(), state.getPrice());
        Assert.assertNotNull(site.getSitePaymentSettings());
        Assert.assertEquals(site.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice(), site.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_ANNUAL_FEE, site.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.PAYPAL, site.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(site.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(site.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertNotNull(site.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(PayPalRecurringProfileStatus.ACTIVE, ((PayPal)PaymentSystem.newInstance(PaymentMethod.PAYPAL)).getProfileStatus(site.getSitePaymentSettings().getRecurringPaymentId()));

        final Calendar expectedExpirationDate = new GregorianCalendar(2011, 1, 1, 0, 0);
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreate_forPendingSiteWithPaypal() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site site = TestUtil.createSite();
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.AUTHORIZE_NET);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2110, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        site.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(site.getSiteId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_ANNUAL_FEE, PaymentMethod.PAYPAL, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_PENDING, state);
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice(), state.getPrice());
        Assert.assertNotNull(site.getSitePaymentSettings());
        Assert.assertEquals(site.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice(), site.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_ANNUAL_FEE, site.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.PAYPAL, site.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(site.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(site.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertNotNull(site.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(PayPalRecurringProfileStatus.ACTIVE, ((PayPal)PaymentSystem.newInstance(PaymentMethod.PAYPAL)).getProfileStatus(site.getSitePaymentSettings().getRecurringPaymentId()));

        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date());
        expectedExpirationDate.set(Calendar.DAY_OF_MONTH, (expectedExpirationDate.get(Calendar.DAY_OF_MONTH) + 365));
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreate_forSuspendedSiteWithPaypal() {
        final PayPalMock payPal = (PayPalMock) ((PayPal)PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        final String profileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(profileId, PayPalRecurringProfileStatus.SUSPENDED, "Note...");

        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site site = TestUtil.createSite();
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.SUSPENDED);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.AUTHORIZE_NET);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(profileId);// this should not be changed for this state
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        sitePaymentSettings.setRemainingTimeOfUsage((10 * 24 * 60 * 60 * 1000L));// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2010, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        site.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(site.getSiteId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_ANNUAL_FEE, PaymentMethod.PAYPAL, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_SUSPENDED, state);
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice(), state.getPrice());
        Assert.assertNotNull(site.getSitePaymentSettings());
        Assert.assertEquals(site.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_ANNUAL_FEE).getPrice(), site.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_ANNUAL_FEE, site.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.PAYPAL, site.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(site.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(site.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertNotNull(site.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(profileId, site.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(PayPalRecurringProfileStatus.ACTIVE, payPal.getProfileStatus(site.getSitePaymentSettings().getRecurringPaymentId()));

        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date());
        expectedExpirationDate.set(Calendar.DAY_OF_MONTH, (expectedExpirationDate.get(Calendar.DAY_OF_MONTH) + 10));
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }
    /*------------------------------------------------Site with Paypal------------------------------------------------*/


    /*-----------------------------------------ChildSiteSettings with Javien------------------------------------------*/

    @Test
    public void testCreate_forActiveChildSiteSettingsWithJavien() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2010, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSiteSettings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();

        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_ACTIVE, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings());
        Assert.assertEquals(childSiteSettings.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteSettings.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSiteSettings.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, childSiteSettings.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSiteSettings.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertTrue(parentSite.getIncomeSettings().getSum() > 0);

        final Calendar expectedExpirationDate = new GregorianCalendar(2010, 2, 1, 0, 0);
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
//        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreate_forPendingChildSiteSettingsWithJavien() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2110, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSiteSettings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);


        final PaymentInfoCreatorState state = paymentInfoCreator.create();


        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_PENDING, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings());
        Assert.assertEquals(childSiteSettings.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteSettings.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSiteSettings.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, childSiteSettings.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSiteSettings.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertTrue(parentSite.getIncomeSettings().getSum() > 0);

        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date());
        expectedExpirationDate.set(Calendar.DAY_OF_MONTH, (expectedExpirationDate.get(Calendar.DAY_OF_MONTH) + 30));
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreate_forActiveChildSiteSettingsWithJavien_withTestCreditCard() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());
        creditCard.setCreditCardNumber("4444333322221111");

        final Site parentSite = TestUtil.createSite();
        parentSite.setIncomeSettings(TestUtil.createIncomeSettings(parentSite, "", 0));
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2010, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSiteSettings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();

        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_ACTIVE, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings());
        Assert.assertEquals(childSiteSettings.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteSettings.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSiteSettings.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, childSiteSettings.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSiteSettings.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertTrue("We don`t add idfference to parent sites income setting when using test credit card",
                parentSite.getIncomeSettings().getSum() == 0);

        final Calendar expectedExpirationDate = new GregorianCalendar(2010, 2, 1, 0, 0);
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
//        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreate_forPendingChildSiteSettingsWithJavien_withTestCreditCard() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());
        creditCard.setCreditCardNumber("4444333322221111");

        final Site parentSite = TestUtil.createSite();
        parentSite.setIncomeSettings(TestUtil.createIncomeSettings(parentSite, "", 0));
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2110, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSiteSettings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_PENDING, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings());
        Assert.assertEquals(childSiteSettings.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteSettings.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSiteSettings.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, childSiteSettings.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSiteSettings.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertTrue("We don`t add idfference to parent sites income setting when using test credit card",
                parentSite.getIncomeSettings().getSum() == 0);

        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date());
        expectedExpirationDate.set(Calendar.DAY_OF_MONTH, (expectedExpirationDate.get(Calendar.DAY_OF_MONTH) + 30));
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreate_forSuspendedChildSiteSettingsWithJavien() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.SUSPENDED);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should not be changed for this state
        sitePaymentSettings.setRemainingTimeOfUsage((10 * 24 * 60 * 60 * 1000L));// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2110, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSiteSettings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_SUSPENDED, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings());
        Assert.assertEquals(childSiteSettings.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteSettings.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSiteSettings.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, childSiteSettings.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSiteSettings.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertTrue(parentSite.getIncomeSettings().getSum() > 0);

        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date());
        expectedExpirationDate.set(Calendar.DAY_OF_MONTH, (expectedExpirationDate.get(Calendar.DAY_OF_MONTH) + 10));
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }
    /*-----------------------------------------ChildSiteSettings with Javien------------------------------------------*/

    /*-----------------------------------------ChildSiteSettings with AuthorizeNet------------------------------------------*/

    @Test
    public void testCreate_forActiveChildSiteSettingsWithAuthorizeNet() {

        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2010, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSiteSettings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();

        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_ACTIVE, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings());
        Assert.assertEquals(childSiteSettings.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteSettings.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSiteSettings.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, childSiteSettings.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSiteSettings.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertTrue(parentSite.getIncomeSettings().getSum() > 0);

        final Calendar expectedExpirationDate = new GregorianCalendar(2010, 2, 1, 0, 0);
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
//        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreate_forPendingChildSiteSettingsWithAuthorizeNet() {

        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2110, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSiteSettings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);


        final PaymentInfoCreatorState state = paymentInfoCreator.create();


        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_PENDING, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings());
        Assert.assertEquals(childSiteSettings.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteSettings.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSiteSettings.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, childSiteSettings.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSiteSettings.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertTrue(parentSite.getIncomeSettings().getSum() > 0);

        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date());
        expectedExpirationDate.set(Calendar.DAY_OF_MONTH, (expectedExpirationDate.get(Calendar.DAY_OF_MONTH) + 30));
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreate_forActiveChildSiteSettingsWithAuthorizeNet_withTestCreditCard() {

        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());
        creditCard.setCreditCardNumber("4444333322221111");

        final Site parentSite = TestUtil.createSite();
        parentSite.setIncomeSettings(TestUtil.createIncomeSettings(parentSite, "", 0));
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2010, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSiteSettings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();

        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_ACTIVE, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings());
        Assert.assertEquals(childSiteSettings.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteSettings.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSiteSettings.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, childSiteSettings.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSiteSettings.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertTrue("We don`t add idfference to parent sites income setting when using test credit card",
                parentSite.getIncomeSettings().getSum() == 0);

        final Calendar expectedExpirationDate = new GregorianCalendar(2010, 2, 1, 0, 0);
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
//        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreate_forPendingChildSiteSettingsWithAuthorizeNet_withTestCreditCard() {

        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());
        creditCard.setCreditCardNumber("4444333322221111");

        final Site parentSite = TestUtil.createSite();
        parentSite.setIncomeSettings(TestUtil.createIncomeSettings(parentSite, "", 0));
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2110, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSiteSettings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_PENDING, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings());
        Assert.assertEquals(childSiteSettings.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteSettings.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSiteSettings.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, childSiteSettings.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSiteSettings.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertTrue("We don`t add idfference to parent sites income setting when using test credit card",
                parentSite.getIncomeSettings().getSum() == 0);

        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date());
        expectedExpirationDate.set(Calendar.DAY_OF_MONTH, (expectedExpirationDate.get(Calendar.DAY_OF_MONTH) + 30));
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreate_forSuspendedChildSiteSettingsWithAuthorizeNet() {

        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.SUSPENDED);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should not be changed for this state
        sitePaymentSettings.setRemainingTimeOfUsage((10 * 24 * 60 * 60 * 1000L));// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2110, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSiteSettings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_SUSPENDED, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings());
        Assert.assertEquals(childSiteSettings.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteSettings.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSiteSettings.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, childSiteSettings.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSiteSettings.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertTrue(parentSite.getIncomeSettings().getSum() > 0);

        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date());
        expectedExpirationDate.set(Calendar.DAY_OF_MONTH, (expectedExpirationDate.get(Calendar.DAY_OF_MONTH) + 10));
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }
    /*-----------------------------------------ChildSiteSettings with AuthorizeNet------------------------------------------*/

    /*-----------------------------------------ChildSiteSettings with Paypal------------------------------------------*/

    @Test
    public void testCreate_forActiveChildSiteSettingsWithPaypal() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.AUTHORIZE_NET);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2010, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSiteSettings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.PAYPAL, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_ACTIVE, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings());
        Assert.assertEquals(childSiteSettings.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteSettings.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSiteSettings.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.PAYPAL, childSiteSettings.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSiteSettings.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(PayPalRecurringProfileStatus.ACTIVE, ((PayPal)PaymentSystem.newInstance(PaymentMethod.PAYPAL)).getProfileStatus(childSiteSettings.getSitePaymentSettings().getRecurringPaymentId()));
        Assert.assertTrue(parentSite.getIncomeSettings().getSum() > 0);

        final Calendar expectedExpirationDate = new GregorianCalendar(2010, 2, 1, 0, 0);
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
//        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreate_forPendingChildSiteSettingsWithPaypal() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.AUTHORIZE_NET);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2110, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSiteSettings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.PAYPAL, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_PENDING, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings());
        Assert.assertEquals(childSiteSettings.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteSettings.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSiteSettings.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.PAYPAL, childSiteSettings.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSiteSettings.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(PayPalRecurringProfileStatus.ACTIVE, ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL)).getProfileStatus(childSiteSettings.getSitePaymentSettings().getRecurringPaymentId()));
        Assert.assertTrue(parentSite.getIncomeSettings().getSum() > 0);

        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date());
        expectedExpirationDate.set(Calendar.DAY_OF_MONTH, (expectedExpirationDate.get(Calendar.DAY_OF_MONTH) + 30));
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testCreate_forSuspendedChildSiteSettingsWithPaypal() {
        final PayPalMock payPal = (PayPalMock) ((PayPal)PaymentSystem.newInstance(PaymentMethod.PAYPAL));
        final String profileId = payPal.createRecurringPaymentsProfile(null, null, null, 0.0, null, null, null);
        payPal.setProfileStatus(profileId, PayPalRecurringProfileStatus.SUSPENDED, "Note...");

        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.SUSPENDED);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.AUTHORIZE_NET);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(profileId);// this should not be changed for this state
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        sitePaymentSettings.setRemainingTimeOfUsage((10 * 24 * 60 * 60 * 1000L));// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2010, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSiteSettings.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSiteSettings.getChildSiteSettingsId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.PAYPAL, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();
        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_SUSPENDED, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings());
        Assert.assertEquals(childSiteSettings.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSiteSettings.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSiteSettings.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.PAYPAL, childSiteSettings.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSiteSettings.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSiteSettings.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSiteSettings.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertNotNull(childSiteSettings.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(profileId, childSiteSettings.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(PayPalRecurringProfileStatus.ACTIVE, payPal.getProfileStatus(childSiteSettings.getSitePaymentSettings().getRecurringPaymentId()));
        Assert.assertTrue(parentSite.getIncomeSettings().getSum() > 0);

        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date());
        expectedExpirationDate.set(Calendar.DAY_OF_MONTH, (expectedExpirationDate.get(Calendar.DAY_OF_MONTH) + 10));
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSiteSettings.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), currentExpirationDate.get(Calendar.DAY_OF_MONTH));
    }
    /*-----------------------------------------ChildSiteSettings with Paypal------------------------------------------*/


    /*------------------------------------Site with ChildSiteSettings with Javien-------------------------------------*/

    @Test
    public void testCreate_forActiveSiteWithChildSiteSettingsWithJavien() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site parentSite = TestUtil.createSite();
        final Site childSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, childSite);
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2010, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSite.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSite.getSiteId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();

        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_ACTIVE, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSite.getSitePaymentSettings());
        Assert.assertEquals(childSite.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSite.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSite.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, childSite.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(childSite.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, childSite.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSite.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSite.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSite.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertTrue(parentSite.getIncomeSettings().getSum() > 0);

        final Calendar expectedExpirationDate = new GregorianCalendar(2010, 2, 1, 0, 0);
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSite.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
    }

    @Test
    public void testCreate_forPendingSiteWithChildSiteSettingsWithJavien_mustBeDeactivated() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site parentSite = TestUtil.createSite();
        final Site childSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, childSite);
        childSiteSettings.setPrice250mb(100);
        childSiteSettings.setStartDate(new Date(System.currentTimeMillis() + (100 * 24 * 60 * 60 * 1000L)));
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2010, 3, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSite.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSite.getSiteId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();

        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_PENDING, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSite.getSitePaymentSettings());
        Assert.assertEquals(childSite.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSite.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSite.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, childSite.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(childSite.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, childSite.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.SUSPENDED, childSite.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSite.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNotNull(childSite.getSitePaymentSettings().getRemainingTimeOfUsage());
        final long remainingDaysOfUsage = childSite.getSitePaymentSettings().getRemainingTimeOfUsage() / 24 / 60 / 60 / 1000;

        Assert.assertTrue((remainingDaysOfUsage > 28 && remainingDaysOfUsage < 32));
    }
    /*------------------------------------Site with ChildSiteSettings with Javien-------------------------------------*/

    /*------------------------------------Site with ChildSiteSettings with AuthorizeNet-------------------------------------*/
    @Test
    public void testCreate_forActiveSiteWithChildSiteSettingsWithAuthorizeNet() {

        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site parentSite = TestUtil.createSite();
        final Site childSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, childSite);
        childSiteSettings.setPrice250mb(100);
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2010, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSite.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSite.getSiteId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();

        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_ACTIVE, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSite.getSitePaymentSettings());
        Assert.assertEquals(childSite.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSite.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSite.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, childSite.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(childSite.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, childSite.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, childSite.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSite.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNull(childSite.getSitePaymentSettings().getRemainingTimeOfUsage());
        Assert.assertTrue(parentSite.getIncomeSettings().getSum() > 0);

        final Calendar expectedExpirationDate = new GregorianCalendar(2010, 2, 1, 0, 0);
        final Calendar currentExpirationDate = new GregorianCalendar();
        currentExpirationDate.setTime(childSite.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), currentExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), currentExpirationDate.get(Calendar.MONTH));
    }

    @Test
    public void testCreate_forPendingSiteWithChildSiteSettingsWithAuthorizeNet_mustBeDeactivated() {

        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site parentSite = TestUtil.createSite();
        final Site childSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, childSite);
        childSiteSettings.setPrice250mb(100);
        childSiteSettings.setStartDate(new Date(System.currentTimeMillis() + (100 * 24 * 60 * 60 * 1000L)));
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2010, 3, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        childSite.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(childSite.getSiteId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_250MB_MONTHLY_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        final PaymentInfoCreatorState state = paymentInfoCreator.create();

        Assert.assertEquals(PaymentInfoCreatorState.ACTIVETED_PENDING, state);
        Assert.assertEquals(childSiteSettings.getPrice250mb(), state.getPrice());
        Assert.assertNotNull(childSite.getSitePaymentSettings());
        Assert.assertEquals(childSite.getSitePaymentSettings().getPrice(), state.getPrice());
        Assert.assertEquals(childSiteSettings.getPrice250mb(), childSite.getSitePaymentSettings().getPrice());
        Assert.assertEquals(ChargeType.SITE_250MB_MONTHLY_FEE, childSite.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, childSite.getSitePaymentSettings().getPaymentMethod());
        Assert.assertNotNull(childSite.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(creditCard, childSite.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.SUSPENDED, childSite.getSitePaymentSettings().getSiteStatus());
        Assert.assertNotNull(ServiceLocator.getPersistance().getSitePaymentSettingsById(childSite.getSitePaymentSettings().getSitePaymentSettingsId()));
        Assert.assertNotNull(childSite.getSitePaymentSettings().getRemainingTimeOfUsage());
        final long remainingDaysOfUsage = childSite.getSitePaymentSettings().getRemainingTimeOfUsage() / 24 / 60 / 60 / 1000;

        Assert.assertTrue((remainingDaysOfUsage > 28 && remainingDaysOfUsage < 32));
    }
    /*------------------------------------Site with ChildSiteSettings with AuthorizeNet-------------------------------------*/
    /*-----------------------------------------------WITHOUT EXCEPTIONS-----------------------------------------------*/

    /*------------------------------------------------WITH EXCEPTIONS-------------------------------------------------*/
    @Ignore("This test is ignored, because we don`t use Javien any more. We use Authorize.net instead.")
    @Test
    public void testCreate_forPendingSiteWithJavienException() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site site = TestUtil.createSite();
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2010, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        site.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(site.getSiteId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_ANNUAL_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        ServiceLocator.setJavien(new JavienWithExceptionsInMethods());

        boolean exceptionThrown = false;
        try {
            paymentInfoCreator.create();
        } catch (Exception e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        //All parameters have to be the same as before execution
        Assert.assertEquals(SiteStatus.PENDING, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(PaymentMethod.PAYPAL, site.getSitePaymentSettings().getPaymentMethod());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(ChargeType.SITE_ONE_TIME_FEE, site.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(100.0, site.getSitePaymentSettings().getPrice(), 0);
        Assert.assertEquals(null, site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(oldExpirationDtae.getTime(), site.getSitePaymentSettings().getExpirationDate());
    }
    
    @Test
    public void testCreate_forPendingSiteWithAuthorizeNetException() {

        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site site = TestUtil.createSite();
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2010, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        site.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(site.getSiteId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_ANNUAL_FEE, PaymentMethod.AUTHORIZE_NET, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        ServiceLocator.setAuthorizeNet(new AuthorizeNetWithExceptionsInMethods());

        boolean exceptionThrown = false;
        try {
            paymentInfoCreator.create();
        } catch (Exception e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        //All parameters have to be the same as before execution
        Assert.assertEquals(SiteStatus.PENDING, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(PaymentMethod.PAYPAL, site.getSitePaymentSettings().getPaymentMethod());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(ChargeType.SITE_ONE_TIME_FEE, site.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(100.0, site.getSitePaymentSettings().getPrice(), 0);
        Assert.assertEquals(null, site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(oldExpirationDtae.getTime(), site.getSitePaymentSettings().getExpirationDate());
    }

    @Test
    public void testCreate_forPendingSiteWithPaypalException() {
        final CreditCard creditCard = TestUtil.createCreditCard(new Date(), new Site());

        final Site site = TestUtil.createSite();
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.PAYPAL);// this should be changed after execution
        sitePaymentSettings.setRecurringPaymentId(null);// this should be changed after execution
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);// this should be changed after execution
        sitePaymentSettings.setPrice(100.0);// this should be changed after execution
        sitePaymentSettings.setCreditCard(null);// this should be changed after execution
        final Calendar oldExpirationDtae = new GregorianCalendar(2010, 1, 1, 0, 0);
        sitePaymentSettings.setExpirationDate(oldExpirationDtae.getTime());// this should be changed after execution
        site.setSitePaymentSettings(sitePaymentSettings);

        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(site.getSiteId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_ANNUAL_FEE, PaymentMethod.PAYPAL, null, null, creditCard);
        final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(request);

        ServiceLocator.setPayPal(new PayPalWithExceptionsInMethods());

        boolean exceptionThrown = false;
        try {
            paymentInfoCreator.create();
        } catch (Exception e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        //All parameters have to be the same as before execution
        Assert.assertEquals(SiteStatus.PENDING, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(PaymentMethod.PAYPAL, site.getSitePaymentSettings().getPaymentMethod());
        Assert.assertEquals(null, site.getSitePaymentSettings().getRecurringPaymentId());
        Assert.assertEquals(ChargeType.SITE_ONE_TIME_FEE, site.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(100.0, site.getSitePaymentSettings().getPrice(), 0);
        Assert.assertEquals(null, site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(oldExpirationDtae.getTime(), site.getSitePaymentSettings().getExpirationDate());
    }

    @Test(expected = PaymentInfoCreatorRequestNotFoundException.class)
    public void testCreate_withExceptionInConstructor_withoutRequest() {
        new PaymentInfoCreator(null);
    }

    @Test(expected = PaymentSettingsOwnerNotFoundException.class)
    public void testCreate_withExceptionInConstructor_withoutOwner() {
        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(-1, PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_ANNUAL_FEE, PaymentMethod.PAYPAL, null, null, null);
        new PaymentInfoCreator(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_withExceptionInConstructor_withoutChargeType() {
        final Site site = TestUtil.createSite();
        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(site.getSiteId(), PaymentSettingsOwnerType.SITE,
                null, PaymentMethod.PAYPAL, null, null, null);
        new PaymentInfoCreator(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_withExceptionInConstructor_withoutPaymentMethod() {
        final Site site = TestUtil.createSite();
        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(site.getSiteId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_MONTHLY_FEE, null, null, null, null);
        new PaymentInfoCreator(request);
    }
    /*------------------------------------------------WITH EXCEPTIONS-------------------------------------------------*/

    @Test
    public void testCreate_withExceptionInConstructor_withoutException() {
        final Site site = TestUtil.createSite();
        final PaymentInfoCreatorRequest request = new PaymentInfoCreatorRequest(site.getSiteId(), PaymentSettingsOwnerType.SITE,
                ChargeType.SITE_MONTHLY_FEE, PaymentMethod.PAYPAL, null, null, null);
        new PaymentInfoCreator(request);
    }
}
