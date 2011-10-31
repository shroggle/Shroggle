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
package com.shroggle.util.payment;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.JavienException;
import com.shroggle.logic.payment.PaymentSystemRequest;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import com.shroggle.util.mail.MockMailSender;
import com.shroggle.util.payment.authorize.AuthorizeNet;
import com.shroggle.util.payment.javien.JavienMock;
import com.shroggle.util.payment.javien.JavienWithExceptionsInMethods;
import com.shroggle.util.payment.paypal.PayPal;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class PaymentSystemTest {

    private final Config config = ServiceLocator.getConfigStorage().get();

    @Before
    public void before() {
        config.setApplicationUrl("www.site-builder.com");
        config.setUserSitesUrl("site-builder.com");
        config.setSupportEmail("ourSupportEmailFromConfig");
        ServiceLocator.setMailSender(mockMailSender);
    }
    /*------------------------------------------New Instance creation tests------------------------------------------*/

    /*@Test
    public void testGetInstance_JAVIEN() {
        PaymentSystem paymentSystem = PaymentSystem.newInstance(PaymentMethod.AUTHORIZE_NET);
        Assert.assertTrue(paymentSystem instanceof Javien);
    }*/

    /*@Test
    public void testGetInstance_JAVIEN_withFalseAuthorizeNetInConfig() {
        ServiceLocator.getConfigStorage().get().setUseAuthorizeNetInsteadOfJavien(false);
        PaymentSystem paymentSystem = PaymentSystem.newInstance(PaymentMethod.AUTHORIZE_NET);
        Assert.assertTrue(paymentSystem instanceof Javien);
    }*/

    @Test
    public void testCreationProperties_PAYPAL() throws Exception {
        final PaymentSystem.CreationProperties creationProperties = new PaymentSystem.CreationProperties("name", "password", "signature");

        Assert.assertEquals(PaymentMethod.PAYPAL, creationProperties.getPaymentMethod());
        Assert.assertEquals("name", creationProperties.getPaypalApiUserName());
        Assert.assertEquals("password", creationProperties.getPaypalApiPassword());
        Assert.assertEquals("signature", creationProperties.getPaypalSignature());

        Assert.assertEquals(null, creationProperties.getAuthorizeLogin());
        Assert.assertEquals(null, creationProperties.getAuthorizeTransactionKey());
    }

    @Test
    public void testCreationProperties_AUTHORIZE_NET() throws Exception {
        final PaymentSystem.CreationProperties creationProperties = new PaymentSystem.CreationProperties("name", "key");

        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, creationProperties.getPaymentMethod());
        Assert.assertEquals(null, creationProperties.getPaypalApiUserName());
        Assert.assertEquals(null, creationProperties.getPaypalApiPassword());
        Assert.assertEquals(null, creationProperties.getPaypalSignature());

        Assert.assertEquals("name", creationProperties.getAuthorizeLogin());
        Assert.assertEquals("key", creationProperties.getAuthorizeTransactionKey());
    }

    @Test
    public void testGetInstance_JAVIEN_withAuthorizeNetInConfig() {

        PaymentSystem paymentSystem = PaymentSystem.newInstance(PaymentMethod.AUTHORIZE_NET);
        Assert.assertTrue(paymentSystem instanceof AuthorizeNet);
    }

    @Test
    public void testGetInstance_PAYPAL() {
        PaymentSystem paymentSystem = PaymentSystem.newInstance(PaymentMethod.PAYPAL);
        Assert.assertTrue(paymentSystem instanceof PayPal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetInstance_withoutPaymentMethod() {
        final PaymentMethod paymentMethod = null;
        PaymentSystem.newInstance(paymentMethod);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetInstance_withoutProperties() {
        final PaymentSystem.CreationProperties creationProperties = null;
        PaymentSystem.newInstance(creationProperties);
    }
    /*------------------------------------------New Instance creation tests------------------------------------------*/

    /*-------------------------------------Activate Active Payment Settings Owner-------------------------------------*/

    @Test
    public void activateActivePaymentSettingsOwnerTest_forSite_monthly() {
        final User user = TestUtil.createUser();
        user.setFirstName("Anatoliy");
        user.setLastName("Balakirev");
        final PaymentSystem paymentSystem = new JavienMock();

        final Site owner = TestUtil.createSite();
        owner.setTitle("'My Site'");
        owner.getSitePaymentSettings().setPrice(123);
        owner.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        owner.getSitePaymentSettings().setUserId(user.getUserId());
        owner.setSubDomain("site");

        final PaymentSystemRequest request = new PaymentSystemRequest(owner, 10, -1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, null, TestUtil.createCreditCard(new Date(), owner), ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(new GregorianCalendar(2010, 1, 1).getTime());
        request.setOldExpirationDate(owner.getSitePaymentSettings().getExpirationDate());


        Assert.assertEquals(0, persistance.getAllPurchaseMailLogs().size());
        paymentSystem.activateActivePaymentSettingsOwner(request);
        Assert.assertEquals(1, persistance.getAllPurchaseMailLogs().size());
        final PurchaseMailLog log = persistance.getAllPurchaseMailLogs().get(0);
        Assert.assertTrue(log.isPurchaseComplete());
        Assert.assertFalse(log.isErrorsWhileSendingMail());
        Assert.assertFalse(log.isUserNotFound());
        Assert.assertEquals(owner.getSiteId(), log.getSiteId().intValue());
        Assert.assertEquals(user.getUserId(), log.getUserId().intValue());
        Assert.assertEquals(log.getEmailText(), mockMailSender.getMails().get(0).getText());

        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals("Web-Deva has received payment for 'My Site'", mockMailSender.getMails().get(0).getSubject());
        Assert.assertEquals("Dear Anatoliy Balakirev,\n" +
                "\n" +
                "Thank you for choosing Web-Deva. This email confirms that you have paid for 1 month of service and hosting from Web-Deva.com\n" +
                "\n" +
                "Your site has been activated and is available on http://site.site-builder.com address.\n" +
                "\n" +
                "Your credit card / paypal account was charged on " + DateUtil.toMonthDayAndYear(new Date()) + " in the amount of: $10\n" +
                "\n" +
                "Your next payment will be charged automatically on 02/01/2010\n" +
                "\n" +
                "To cancel service at any time log into your account and select `Deactivate.`\n" +
                "\n" +
                "\n" +
                "\n" +
                "Web-Deva is a fabulous new tool for creating and maintaining professional web sites at the push of a button. Web-Deva allows you to create functionally rich sites, with plenty of media.\n" +
                "\n" +
                "If you have any questions regarding your account, refer to the FAQs at http://www.demo." + config.getUserSitesUrl() + "/FAQ\n" +
                "\n" +
                "or contact us directly with any questions or comments: " + config.getSupportEmail(), mockMailSender.getMails().get(0).getText());
    }

    @Test
    public void activateActivePaymentSettingsOwnerTest_forSite_annual() {
        final User user = TestUtil.createUser();
        user.setFirstName("Anatoliy");
        user.setLastName("Balakirev");
        final PaymentSystem paymentSystem = new JavienMock();

        final Site owner = TestUtil.createSite();
        owner.setTitle("'My Site'");
        owner.getSitePaymentSettings().setPrice(123);
        owner.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        owner.getSitePaymentSettings().setUserId(user.getUserId());
        owner.setSubDomain("site");

        final PaymentSystemRequest request = new PaymentSystemRequest(owner, 10, -1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, null, TestUtil.createCreditCard(new Date(), owner), ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(new GregorianCalendar(2010, 1, 1).getTime());
        request.setOldExpirationDate(owner.getSitePaymentSettings().getExpirationDate());


        Assert.assertEquals(0, persistance.getAllPurchaseMailLogs().size());
        paymentSystem.activateActivePaymentSettingsOwner(request);
        Assert.assertEquals(1, persistance.getAllPurchaseMailLogs().size());
        final PurchaseMailLog log = persistance.getAllPurchaseMailLogs().get(0);
        Assert.assertTrue(log.isPurchaseComplete());
        Assert.assertFalse(log.isErrorsWhileSendingMail());
        Assert.assertFalse(log.isUserNotFound());
        Assert.assertEquals(owner.getSiteId(), log.getSiteId().intValue());
        Assert.assertEquals(user.getUserId(), log.getUserId().intValue());
        Assert.assertEquals(log.getEmailText(), mockMailSender.getMails().get(0).getText());


        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals("Web-Deva has received payment for 'My Site'", mockMailSender.getMails().get(0).getSubject());
        Assert.assertEquals("Dear Anatoliy Balakirev,\n" +
                "\n" +
                "Thank you for choosing Web-Deva. This email confirms that you have paid for 1 month of service and hosting from Web-Deva.com\n" +
                "\n" +
                "Your site has been activated and is available on http://site.site-builder.com address.\n" +
                "\n" +
                "Your credit card / paypal account was charged on " + DateUtil.toMonthDayAndYear(new Date()) + " in the amount of: $10\n" +
                "\n" +
                "Your next payment will be charged automatically on 02/01/2010\n" +
                "\n" +
                "To cancel service at any time log into your account and select `Deactivate.`\n" +
                "\n" +
                "\n" +
                "\n" +
                "Web-Deva is a fabulous new tool for creating and maintaining professional web sites at the push of a button. Web-Deva allows you to create functionally rich sites, with plenty of media.\n" +
                "\n" +
                "If you have any questions regarding your account, refer to the FAQs at http://www.demo." + config.getUserSitesUrl() + "/FAQ\n" +
                "\n" +
                "or contact us directly with any questions or comments: " + config.getSupportEmail(), mockMailSender.getMails().get(0).getText());
    }

    @Test
    public void activateActivePaymentSettingsOwnerTest_forSite_withoutUser() {
        final User user = TestUtil.createUser();
        user.setFirstName("Anatoliy");
        user.setLastName("Balakirev");
        final PaymentSystem paymentSystem = new JavienMock();

        final Site owner = TestUtil.createSite();
        owner.setTitle("'My Site'");
        owner.getSitePaymentSettings().setPrice(123);
        owner.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        owner.getSitePaymentSettings().setUserId(-1);
        owner.setSubDomain("site");

        final PaymentSystemRequest request = new PaymentSystemRequest(owner, 10, -1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, null, TestUtil.createCreditCard(new Date(), owner), ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(new GregorianCalendar(2010, 1, 1).getTime());
        request.setOldExpirationDate(owner.getSitePaymentSettings().getExpirationDate());


        Assert.assertEquals(0, persistance.getAllPurchaseMailLogs().size());
        paymentSystem.activateActivePaymentSettingsOwner(request);
        Assert.assertEquals(1, persistance.getAllPurchaseMailLogs().size());
        final PurchaseMailLog log = persistance.getAllPurchaseMailLogs().get(0);
        Assert.assertTrue(log.isPurchaseComplete());
        Assert.assertFalse(log.isErrorsWhileSendingMail());
        Assert.assertTrue(log.isUserNotFound());

        Assert.assertEquals(0, mockMailSender.getMails().size());
    }

    @Test
    public void activateActivePaymentSettingsOwnerTest_forSite_withException() {
        final User user = TestUtil.createUser();
        user.setFirstName("Anatoliy");
        user.setLastName("Balakirev");
        final PaymentSystem paymentSystem = new JavienWithExceptionsInMethods();

        final Site owner = TestUtil.createSite();
        owner.setTitle("'My Site'");
        owner.getSitePaymentSettings().setPrice(123);
        owner.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        owner.getSitePaymentSettings().setUserId(user.getUserId());
        owner.setSubDomain("site");

        final PaymentSystemRequest request = new PaymentSystemRequest(owner, 10, -1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, null, TestUtil.createCreditCard(new Date(), owner), ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(new GregorianCalendar(2010, 1, 1).getTime());
        request.setOldExpirationDate(owner.getSitePaymentSettings().getExpirationDate());

        boolean exceptionThrown = false;
        try {


            Assert.assertEquals(0, persistance.getAllPurchaseMailLogs().size());
            paymentSystem.activateActivePaymentSettingsOwner(request);
        } catch (JavienException e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);
        Assert.assertEquals(0, mockMailSender.getMails().size());


        Assert.assertEquals(1, persistance.getAllPurchaseMailLogs().size());
        final PurchaseMailLog log = persistance.getAllPurchaseMailLogs().get(0);
        Assert.assertFalse(log.isPurchaseComplete());
        Assert.assertFalse(log.isErrorsWhileSendingMail());
        Assert.assertFalse(log.isUserNotFound());
        Assert.assertEquals(owner.getSiteId(), log.getSiteId().intValue());
        Assert.assertEquals(user.getUserId(), log.getUserId().intValue());
        Assert.assertEquals("Dear Anatoliy Balakirev,\n" +
                "\n" +
                "Thank you for choosing Web-Deva. This email confirms that you have paid for 1 month of service and hosting from Web-Deva.com\n" +
                "\n" +
                "Your site has been activated and is available on http://site.site-builder.com address.\n" +
                "\n" +
                "Your credit card / paypal account was charged on " + DateUtil.toMonthDayAndYear(new Date()) + " in the amount of: $10\n" +
                "\n" +
                "Your next payment will be charged automatically on 02/01/2010\n" +
                "\n" +
                "To cancel service at any time log into your account and select `Deactivate.`\n" +
                "\n" +
                "\n" +
                "\n" +
                "Web-Deva is a fabulous new tool for creating and maintaining professional web sites at the push of a button. Web-Deva allows you to create functionally rich sites, with plenty of media.\n" +
                "\n" +
                "If you have any questions regarding your account, refer to the FAQs at http://www.demo." + config.getUserSitesUrl() + "/FAQ\n" +
                "\n" +
                "or contact us directly with any questions or comments: " + config.getSupportEmail(), log.getEmailText());
    }
    /*-------------------------------------Activate Active Payment Settings Owner-------------------------------------*/

    /*------------------------------------Activate Pending Payment Settings Owner-------------------------------------*/

    @Test
    public void activatePendingPaymentSettingsOwnerTest_forSite_monthly() {
        final User user = TestUtil.createUser();
        user.setFirstName("Anatoliy");
        user.setLastName("Balakirev");
        final PaymentSystem paymentSystem = new JavienMock();

        final Site owner = TestUtil.createSite();
        owner.setTitle("'My Site'");
        owner.getSitePaymentSettings().setPrice(123);
        owner.getSitePaymentSettings().setSiteStatus(SiteStatus.PENDING);
        owner.getSitePaymentSettings().setUserId(user.getUserId());
        owner.setSubDomain("site");

        final PaymentSystemRequest request = new PaymentSystemRequest(owner, 10, -1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, null, TestUtil.createCreditCard(new Date(), owner), ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(new GregorianCalendar(2010, 1, 1).getTime());
        request.setOldExpirationDate(owner.getSitePaymentSettings().getExpirationDate());


        Assert.assertEquals(0, persistance.getAllPurchaseMailLogs().size());
        paymentSystem.activatePendingPaymentSettingsOwner(request);
        Assert.assertEquals(1, persistance.getAllPurchaseMailLogs().size());
        final PurchaseMailLog log = persistance.getAllPurchaseMailLogs().get(0);
        Assert.assertTrue(log.isPurchaseComplete());
        Assert.assertFalse(log.isErrorsWhileSendingMail());
        Assert.assertFalse(log.isUserNotFound());
        Assert.assertEquals(owner.getSiteId(), log.getSiteId().intValue());
        Assert.assertEquals(user.getUserId(), log.getUserId().intValue());
        Assert.assertEquals(log.getEmailText(), mockMailSender.getMails().get(0).getText());


        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals("Web-Deva has received payment for 'My Site'", mockMailSender.getMails().get(0).getSubject());
        Assert.assertEquals("Dear Anatoliy Balakirev,\n" +
                "\n" +
                "Thank you for choosing Web-Deva. This email confirms that you have paid for 1 month of service and hosting from Web-Deva.com\n" +
                "\n" +
                "Your site has been activated and is available on http://site.site-builder.com address.\n" +
                "\n" +
                "Your credit card / paypal account was charged on " + DateUtil.toMonthDayAndYear(new Date()) + " in the amount of: $10\n" +
                "\n" +
                "Your next payment will be charged automatically on 02/01/2010\n" +
                "\n" +
                "To cancel service at any time log into your account and select `Deactivate.`\n" +
                "\n" +
                "\n" +
                "\n" +
                "Web-Deva is a fabulous new tool for creating and maintaining professional web sites at the push of a button. Web-Deva allows you to create functionally rich sites, with plenty of media.\n" +
                "\n" +
                "If you have any questions regarding your account, refer to the FAQs at http://www.demo." + config.getUserSitesUrl() + "/FAQ\n" +
                "\n" +
                "or contact us directly with any questions or comments: " + config.getSupportEmail(), mockMailSender.getMails().get(0).getText());
    }

    @Test
    public void activatePendingPaymentSettingsOwnerTest_forSite_annual() {
        final User user = TestUtil.createUser();
        user.setFirstName("Anatoliy");
        user.setLastName("Balakirev");
        final PaymentSystem paymentSystem = new JavienMock();

        final Site owner = TestUtil.createSite();
        owner.setTitle("'My Site'");
        owner.getSitePaymentSettings().setPrice(123);
        owner.getSitePaymentSettings().setSiteStatus(SiteStatus.PENDING);
        owner.getSitePaymentSettings().setUserId(user.getUserId());
        owner.setSubDomain("site");

        final PaymentSystemRequest request = new PaymentSystemRequest(owner, 10, -1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, null, TestUtil.createCreditCard(new Date(), owner), ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(new GregorianCalendar(2010, 1, 1).getTime());
        request.setOldExpirationDate(owner.getSitePaymentSettings().getExpirationDate());


        Assert.assertEquals(0, persistance.getAllPurchaseMailLogs().size());
        paymentSystem.activatePendingPaymentSettingsOwner(request);
        Assert.assertEquals(1, persistance.getAllPurchaseMailLogs().size());
        final PurchaseMailLog log = persistance.getAllPurchaseMailLogs().get(0);
        Assert.assertTrue(log.isPurchaseComplete());
        Assert.assertFalse(log.isErrorsWhileSendingMail());
        Assert.assertFalse(log.isUserNotFound());
        Assert.assertEquals(owner.getSiteId(), log.getSiteId().intValue());
        Assert.assertEquals(user.getUserId(), log.getUserId().intValue());
        Assert.assertEquals(log.getEmailText(), mockMailSender.getMails().get(0).getText());


        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals("Web-Deva has received payment for 'My Site'", mockMailSender.getMails().get(0).getSubject());
        Assert.assertEquals("Dear Anatoliy Balakirev,\n" +
                "\n" +
                "Thank you for choosing Web-Deva. This email confirms that you have paid for 1 month of service and hosting from Web-Deva.com\n" +
                "\n" +
                "Your site has been activated and is available on http://site.site-builder.com address.\n" +
                "\n" +
                "Your credit card / paypal account was charged on " + DateUtil.toMonthDayAndYear(new Date()) + " in the amount of: $10\n" +
                "\n" +
                "Your next payment will be charged automatically on 02/01/2010\n" +
                "\n" +
                "To cancel service at any time log into your account and select `Deactivate.`\n" +
                "\n" +
                "\n" +
                "\n" +
                "Web-Deva is a fabulous new tool for creating and maintaining professional web sites at the push of a button. Web-Deva allows you to create functionally rich sites, with plenty of media.\n" +
                "\n" +
                "If you have any questions regarding your account, refer to the FAQs at http://www.demo." + config.getUserSitesUrl() + "/FAQ\n" +
                "\n" +
                "or contact us directly with any questions or comments: " + config.getSupportEmail(), mockMailSender.getMails().get(0).getText());
    }

    @Test
    public void activatePendingPaymentSettingsOwnerTest_forSite_withoutUser() {
        final User user = TestUtil.createUser();
        user.setFirstName("Anatoliy");
        user.setLastName("Balakirev");
        final PaymentSystem paymentSystem = new JavienMock();

        final Site owner = TestUtil.createSite();
        owner.setTitle("'My Site'");
        owner.getSitePaymentSettings().setPrice(123);
        owner.getSitePaymentSettings().setSiteStatus(SiteStatus.PENDING);
        owner.getSitePaymentSettings().setUserId(-1);
        owner.setSubDomain("site");

        final PaymentSystemRequest request = new PaymentSystemRequest(owner, 10, -1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, null, TestUtil.createCreditCard(new Date(), owner), ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(new GregorianCalendar(2010, 1, 1).getTime());
        request.setOldExpirationDate(owner.getSitePaymentSettings().getExpirationDate());


        Assert.assertEquals(0, persistance.getAllPurchaseMailLogs().size());
        paymentSystem.activatePendingPaymentSettingsOwner(request);
        Assert.assertEquals(1, persistance.getAllPurchaseMailLogs().size());
        final PurchaseMailLog log = persistance.getAllPurchaseMailLogs().get(0);
        Assert.assertTrue(log.isPurchaseComplete());
        Assert.assertFalse(log.isErrorsWhileSendingMail());
        Assert.assertTrue(log.isUserNotFound());

        Assert.assertEquals(0, mockMailSender.getMails().size());
    }

    @Test
    public void activatePendingPaymentSettingsOwnerTest_forSite_withException() {
        final User user = TestUtil.createUser();
        user.setFirstName("Anatoliy");
        user.setLastName("Balakirev");
        final PaymentSystem paymentSystem = new JavienWithExceptionsInMethods();

        final Site owner = TestUtil.createSite();
        owner.setTitle("'My Site'");
        owner.getSitePaymentSettings().setPrice(123);
        owner.getSitePaymentSettings().setSiteStatus(SiteStatus.PENDING);
        owner.getSitePaymentSettings().setUserId(user.getUserId());
        owner.setSubDomain("site");

        final PaymentSystemRequest request = new PaymentSystemRequest(owner, 10, -1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, null, TestUtil.createCreditCard(new Date(), owner), ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(new GregorianCalendar(2010, 1, 1).getTime());
        request.setOldExpirationDate(owner.getSitePaymentSettings().getExpirationDate());

        boolean exceptionThrown = false;
        try {
            Assert.assertEquals(0, persistance.getAllPurchaseMailLogs().size());
            paymentSystem.activatePendingPaymentSettingsOwner(request);
        } catch (JavienException e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);
        Assert.assertEquals(0, mockMailSender.getMails().size());

        Assert.assertEquals(1, persistance.getAllPurchaseMailLogs().size());
        final PurchaseMailLog log = persistance.getAllPurchaseMailLogs().get(0);
        Assert.assertFalse(log.isPurchaseComplete());
        Assert.assertFalse(log.isErrorsWhileSendingMail());
        Assert.assertFalse(log.isUserNotFound());
        Assert.assertEquals(owner.getSiteId(), log.getSiteId().intValue());
        Assert.assertEquals(user.getUserId(), log.getUserId().intValue());
        Assert.assertEquals("Dear Anatoliy Balakirev,\n" +
                "\n" +
                "Thank you for choosing Web-Deva. This email confirms that you have paid for 1 month of service and hosting from Web-Deva.com\n" +
                "\n" +
                "Your site has been activated and is available on http://site.site-builder.com address.\n" +
                "\n" +
                "Your credit card / paypal account was charged on " + DateUtil.toMonthDayAndYear(new Date()) + " in the amount of: $10\n" +
                "\n" +
                "Your next payment will be charged automatically on 02/01/2010\n" +
                "\n" +
                "To cancel service at any time log into your account and select `Deactivate.`\n" +
                "\n" +
                "\n" +
                "\n" +
                "Web-Deva is a fabulous new tool for creating and maintaining professional web sites at the push of a button. Web-Deva allows you to create functionally rich sites, with plenty of media.\n" +
                "\n" +
                "If you have any questions regarding your account, refer to the FAQs at http://www.demo." + config.getUserSitesUrl() + "/FAQ\n" +
                "\n" +
                "or contact us directly with any questions or comments: " + config.getSupportEmail(), log.getEmailText());
    }
    /*-------------------------------------Activate Active Payment Settings Owner-------------------------------------*/


    /*-----------------------------------------------Prolong Activity-------------------------------------------------*/

    @Test
    public void prolongActivityTest_forSite_monthly() {
        final User user = TestUtil.createUser();
        user.setFirstName("Anatoliy");
        user.setLastName("Balakirev");
        final PaymentSystem paymentSystem = new JavienMock();

        final Site owner = TestUtil.createSite();
        owner.setTitle("'My Site'");
        owner.getSitePaymentSettings().setPrice(123);
        owner.getSitePaymentSettings().setSiteStatus(SiteStatus.PENDING);
        owner.getSitePaymentSettings().setUserId(user.getUserId());
        owner.getSitePaymentSettings().setCreditCard(TestUtil.createCreditCard(new Date(), owner));
        owner.setSubDomain("site");


        Assert.assertEquals(0, persistance.getAllPurchaseMailLogs().size());
        Calendar oldExpirationDate = new GregorianCalendar(2010, 1, 1);
        Calendar newExpirationDate = new GregorianCalendar(2010, 2, 1);
        paymentSystem.prolongActivity(owner, oldExpirationDate.getTime(), newExpirationDate.getTime());
        Assert.assertEquals(1, persistance.getAllPurchaseMailLogs().size());
        final PurchaseMailLog log = persistance.getAllPurchaseMailLogs().get(0);
        Assert.assertTrue(log.isPurchaseComplete());
        Assert.assertFalse(log.isErrorsWhileSendingMail());
        Assert.assertFalse(log.isUserNotFound());
        Assert.assertEquals(owner.getSiteId(), log.getSiteId().intValue());
        Assert.assertEquals(user.getUserId(), log.getUserId().intValue());
        Assert.assertEquals(log.getEmailText(), mockMailSender.getMails().get(0).getText());
        Assert.assertEquals(oldExpirationDate.getTime(), log.getOldExpirationDate());
        Assert.assertEquals(newExpirationDate.getTime(), log.getNewExpirationDate());


        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals("Web-Deva has received payment for 'My Site'", mockMailSender.getMails().get(0).getSubject());
        Assert.assertEquals("Dear Anatoliy Balakirev,\n" +
                "\n" +
                "Thank you for choosing Web-Deva. This email confirms that you have paid for 1 month of service and hosting from Web-Deva.com\n" +
                "\n" +
                "Your site has been activated and is available on http://site.site-builder.com address.\n" +
                "\n" +
                "Your credit card / paypal account was charged on " + DateUtil.toMonthDayAndYear(new Date()) + " in the amount of: $123\n" +
                "\n" +
                "Your next payment will be charged automatically on 03/01/2010\n" +
                "\n" +
                "To cancel service at any time log into your account and select `Deactivate.`\n" +
                "\n" +
                "\n" +
                "\n" +
                "Web-Deva is a fabulous new tool for creating and maintaining professional web sites at the push of a button. Web-Deva allows you to create functionally rich sites, with plenty of media.\n" +
                "\n" +
                "If you have any questions regarding your account, refer to the FAQs at http://www.demo." + config.getUserSitesUrl() + "/FAQ\n" +
                "\n" +
                "or contact us directly with any questions or comments: " + config.getSupportEmail(), mockMailSender.getMails().get(0).getText());
    }

    @Test
    public void prolongActivityTest_forSite_annual() {
        final User user = TestUtil.createUser();
        user.setFirstName("Anatoliy");
        user.setLastName("Balakirev");
        final PaymentSystem paymentSystem = new JavienMock();

        final Site owner = TestUtil.createSite();
        owner.setTitle("'My Site'");
        owner.getSitePaymentSettings().setPrice(123);
        owner.getSitePaymentSettings().setSiteStatus(SiteStatus.PENDING);
        owner.getSitePaymentSettings().setUserId(user.getUserId());
        owner.getSitePaymentSettings().setCreditCard(TestUtil.createCreditCard(new Date(), owner));
        owner.setSubDomain("site");


        Assert.assertEquals(0, persistance.getAllPurchaseMailLogs().size());
        Calendar oldExpirationDate = new GregorianCalendar(2010, 1, 1);
        Calendar newExpirationDate = new GregorianCalendar(2010, 2, 1);
        paymentSystem.prolongActivity(owner, oldExpirationDate.getTime(), newExpirationDate.getTime());
        Assert.assertEquals(1, persistance.getAllPurchaseMailLogs().size());
        final PurchaseMailLog log = persistance.getAllPurchaseMailLogs().get(0);
        Assert.assertTrue(log.isPurchaseComplete());
        Assert.assertFalse(log.isErrorsWhileSendingMail());
        Assert.assertFalse(log.isUserNotFound());
        Assert.assertEquals(owner.getSiteId(), log.getSiteId().intValue());
        Assert.assertEquals(user.getUserId(), log.getUserId().intValue());
        Assert.assertEquals(log.getEmailText(), mockMailSender.getMails().get(0).getText());
        Assert.assertEquals(oldExpirationDate.getTime(), log.getOldExpirationDate());
        Assert.assertEquals(newExpirationDate.getTime(), log.getNewExpirationDate());


        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals("Web-Deva has received payment for 'My Site'", mockMailSender.getMails().get(0).getSubject());
        Assert.assertEquals("Dear Anatoliy Balakirev,\n" +
                "\n" +
                "Thank you for choosing Web-Deva. This email confirms that you have paid for 1 month of service and hosting from Web-Deva.com\n" +
                "\n" +
                "Your site has been activated and is available on http://site.site-builder.com address.\n" +
                "\n" +
                "Your credit card / paypal account was charged on " + DateUtil.toMonthDayAndYear(new Date()) + " in the amount of: $123\n" +
                "\n" +
                "Your next payment will be charged automatically on 03/01/2010\n" +
                "\n" +
                "To cancel service at any time log into your account and select `Deactivate.`\n" +
                "\n" +
                "\n" +
                "\n" +
                "Web-Deva is a fabulous new tool for creating and maintaining professional web sites at the push of a button. Web-Deva allows you to create functionally rich sites, with plenty of media.\n" +
                "\n" +
                "If you have any questions regarding your account, refer to the FAQs at http://www.demo." + config.getUserSitesUrl() + "/FAQ\n" +
                "\n" +
                "or contact us directly with any questions or comments: " + config.getSupportEmail(), mockMailSender.getMails().get(0).getText());
    }

    @Test
    public void prolongActivityTest_forSite_withoutUser() {
        final User user = TestUtil.createUser();
        user.setFirstName("Anatoliy");
        user.setLastName("Balakirev");
        final PaymentSystem paymentSystem = new JavienMock();

        final Site owner = TestUtil.createSite();
        owner.getSitePaymentSettings().setCreditCard(TestUtil.createCreditCard(new Date(), owner));
        owner.setTitle("'My Site'");
        owner.getSitePaymentSettings().setPrice(123);
        owner.getSitePaymentSettings().setSiteStatus(SiteStatus.PENDING);
        owner.getSitePaymentSettings().setUserId(-1);
        owner.setSubDomain("site");


        Assert.assertEquals(0, persistance.getAllPurchaseMailLogs().size());
        Calendar oldExpirationDate = new GregorianCalendar(2010, 1, 1);
        Calendar newExpirationDate = new GregorianCalendar(2010, 2, 1);
        paymentSystem.prolongActivity(owner, oldExpirationDate.getTime(), newExpirationDate.getTime());
        Assert.assertEquals(1, persistance.getAllPurchaseMailLogs().size());
        final PurchaseMailLog log = persistance.getAllPurchaseMailLogs().get(0);
        Assert.assertTrue(log.isPurchaseComplete());
        Assert.assertFalse(log.isErrorsWhileSendingMail());
        Assert.assertTrue(log.isUserNotFound());
        Assert.assertEquals(oldExpirationDate.getTime(), log.getOldExpirationDate());
        Assert.assertEquals(newExpirationDate.getTime(), log.getNewExpirationDate());

        Assert.assertEquals(0, mockMailSender.getMails().size());
    }

    @Test
    public void prolongActivityTest_forSite_withException() {
        final User user = TestUtil.createUser();
        user.setFirstName("Anatoliy");
        user.setLastName("Balakirev");
        final PaymentSystem paymentSystem = new JavienWithExceptionsInMethods();

        final Site owner = TestUtil.createSite();
        owner.getSitePaymentSettings().setCreditCard(TestUtil.createCreditCard(new Date(), owner));
        owner.setTitle("'My Site'");
        owner.getSitePaymentSettings().setPrice(123);
        owner.getSitePaymentSettings().setSiteStatus(SiteStatus.PENDING);
        owner.getSitePaymentSettings().setUserId(user.getUserId());
        owner.setSubDomain("site");

        final PaymentSystemRequest request = new PaymentSystemRequest(owner, 10, -1, PaymentReason.SHROGGLE_MONTHLY_PAYMENT, null, TestUtil.createCreditCard(new Date(), owner), ChargeType.SITE_MONTHLY_FEE);
        request.setNewExpirationDate(new GregorianCalendar(2010, 1, 1).getTime());
        request.setOldExpirationDate(owner.getSitePaymentSettings().getExpirationDate());

        Calendar oldExpirationDate = new GregorianCalendar(2010, 1, 1);
        Calendar newExpirationDate = new GregorianCalendar(2010, 2, 1);
        boolean exceptionThrown = false;
        try {
            Assert.assertEquals(0, persistance.getAllPurchaseMailLogs().size());
            paymentSystem.prolongActivity(owner, oldExpirationDate.getTime(), newExpirationDate.getTime());
        } catch (JavienException e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);
        Assert.assertEquals(0, mockMailSender.getMails().size());


        Assert.assertEquals(1, persistance.getAllPurchaseMailLogs().size());
        final PurchaseMailLog log = persistance.getAllPurchaseMailLogs().get(0);
        Assert.assertFalse(log.isPurchaseComplete());
        Assert.assertFalse(log.isErrorsWhileSendingMail());
        Assert.assertFalse(log.isUserNotFound());
        Assert.assertEquals(oldExpirationDate.getTime(), log.getOldExpirationDate());
        Assert.assertEquals(newExpirationDate.getTime(), log.getNewExpirationDate());
        Assert.assertEquals(owner.getSiteId(), log.getSiteId().intValue());
        Assert.assertEquals(user.getUserId(), log.getUserId().intValue());
        Assert.assertEquals("Dear Anatoliy Balakirev,\n" +
                "\n" +
                "Thank you for choosing Web-Deva. This email confirms that you have paid for 1 month of service and hosting from Web-Deva.com\n" +
                "\n" +
                "Your site has been activated and is available on http://site.site-builder.com address.\n" +
                "\n" +
                "Your credit card / paypal account was charged on " + DateUtil.toMonthDayAndYear(new Date()) + " in the amount of: $123\n" +
                "\n" +
                "Your next payment will be charged automatically on 03/01/2010\n" +
                "\n" +
                "To cancel service at any time log into your account and select `Deactivate.`\n" +
                "\n" +
                "\n" +
                "\n" +
                "Web-Deva is a fabulous new tool for creating and maintaining professional web sites at the push of a button. Web-Deva allows you to create functionally rich sites, with plenty of media.\n" +
                "\n" +
                "If you have any questions regarding your account, refer to the FAQs at http://www.demo." + config.getUserSitesUrl() + "/FAQ\n" +
                "\n" +
                "or contact us directly with any questions or comments: " + config.getSupportEmail(), log.getEmailText());
    }
    /*-----------------------------------------------Prolong Activity-------------------------------------------------*/


    private final MockMailSender mockMailSender = new MockMailSender();
    private final Persistance persistance = ServiceLocator.getPersistance();
}
