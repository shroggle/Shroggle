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
package com.shroggle.logic.site.billingInfo;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.TimeInterval;
import com.shroggle.util.config.BillingInfoProperties;
import com.shroggle.util.config.Config;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.mail.MockMailSender;
import com.shroggle.util.payment.authorize.AuthorizeNetWithExceptionsInMethods;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class EnforcePaymentTest {

    @Before
    public void before() {
        Config config = ServiceLocator.getConfigStorage().get();
        BillingInfoProperties properties = config.getBillingInfoProperties();
        properties.setAnnualBillingExpirationDate(365 * 24 * 60);
        properties.setMonthlyBillingExpirationDate(30 * 24 * 60);
        properties.setDeactivateSiteAfter((15 * 24 * 60));
        properties.setSendPaymentNotificationEmails(true);
        config.setApplicationUrl("www.shroggle.com");
    }

    @Test
    public void testExecute_MONTHLY_BILLING_ACTIVE() {
        final Date currentDate = new Date();

        User user = TestUtil.createUser();

        Site site = TestUtil.createSite();
        site.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        site.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()));
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);

        CreditCard card1 = TestUtil.createCreditCard(new Date(), site);
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("4111111111111111");
        card1.setSecurityCode("111");
        card1.setUser(user);

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());

        final Date oldExpirationDate = site.getSitePaymentSettings().getExpirationDate();
        EnforcePayment.execute();

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(oldExpirationDate.getTime() + (30 * TimeInterval.ONE_DAY.getMillis())));

        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.setTime(site.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(calendar.get(Calendar.YEAR), expirationDateCalendar.get(Calendar.YEAR));
        Assert.assertEquals(calendar.get(Calendar.MONTH), expirationDateCalendar.get(Calendar.MONTH));
        Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), expirationDateCalendar.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());

        EnforcePayment.execute();


        expirationDateCalendar.setTime(site.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(calendar.get(Calendar.YEAR), expirationDateCalendar.get(Calendar.YEAR));
        Assert.assertEquals(calendar.get(Calendar.MONTH), expirationDateCalendar.get(Calendar.MONTH));
        Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), expirationDateCalendar.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());
    }

    @Test
    public void testExecute_MONTHLY_BILLING_ACTIVE_expirationDateAfterCurrentDate() {
        final Date currentDate = new Date();

        User user = TestUtil.createUser();

        Site site = TestUtil.createSite();
        site.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        site.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() + TimeInterval.ONE_DAY.getMillis()));
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);

        CreditCard card1 = TestUtil.createCreditCard(new Date(), site);
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("4111111111111111");
        card1.setSecurityCode("111");
        card1.setUser(user);

        Assert.assertEquals(new Date(currentDate.getTime() + TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());

        EnforcePayment.execute();

        Assert.assertEquals(new Date(currentDate.getTime() + TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());

        EnforcePayment.execute();

        Assert.assertEquals(new Date(currentDate.getTime() + TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());
    }

    @Test
    public void testExecute_MONTHLY_BILLING_PENDING() {
        final Date currentDate = new Date();

        User user = TestUtil.createUser();

        Site site = TestUtil.createSite();

        new SiteManager(site).setSiteStatus(SiteStatus.PENDING);
        site.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        site.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()));

        CreditCard card1 = TestUtil.createCreditCard(new Date(), site);
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("4111111111111111");
        card1.setSecurityCode("111");
        card1.setUser(user);

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.PENDING, new SiteManager(site).getSiteStatus());

        EnforcePayment.execute();

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.PENDING, new SiteManager(site).getSiteStatus());

        EnforcePayment.execute();

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.PENDING, new SiteManager(site).getSiteStatus());
    }

    @Test
    public void testExecute_MONTHLY_BILLING_SUSPENDED() {
        final Date currentDate = new Date();

        User user = TestUtil.createUser();

        Site site = TestUtil.createSite();

        site.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.SUSPENDED);
        site.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()));

        CreditCard card1 = TestUtil.createCreditCard(new Date(), site);
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("4111111111111111");
        card1.setSecurityCode("111");
        card1.setUser(user);

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.SUSPENDED, new SiteManager(site).getSiteStatus());

        EnforcePayment.execute();

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.SUSPENDED, new SiteManager(site).getSiteStatus());

        EnforcePayment.execute();

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.SUSPENDED, new SiteManager(site).getSiteStatus());
    }


    @Test
    public void testExecute_ANNUAL_BILLING_ACTIVE() {
        final Date currentDate = new Date();

        User user = TestUtil.createUser();

        Site site = TestUtil.createSite();

        site.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_ANNUAL_FEE));
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        site.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()));

        CreditCard card1 = TestUtil.createCreditCard(new Date(), site);
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("4111111111111111");
        card1.setSecurityCode("111");
        card1.setUser(user);

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());

        final Date oldExpirationDate = site.getSitePaymentSettings().getExpirationDate();
        EnforcePayment.execute();


        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(oldExpirationDate.getTime() + (365 * TimeInterval.ONE_DAY.getMillis())));
        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.setTime(site.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(calendar.get(Calendar.YEAR), expirationDateCalendar.get(Calendar.YEAR));
        Assert.assertEquals(calendar.get(Calendar.MONTH), expirationDateCalendar.get(Calendar.MONTH));
        Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), expirationDateCalendar.get(Calendar.DAY_OF_MONTH));

        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());

        EnforcePayment.execute();

        expirationDateCalendar.setTime(site.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(calendar.get(Calendar.YEAR), expirationDateCalendar.get(Calendar.YEAR));
        Assert.assertEquals(calendar.get(Calendar.MONTH), expirationDateCalendar.get(Calendar.MONTH));
        Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), expirationDateCalendar.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());
    }

    @Test
    public void testExecute_ANNUAL_BILLING_PENDING() {
        final Date currentDate = new Date();

        User user = TestUtil.createUser();

        Site site = TestUtil.createSite();

        new SiteManager(site).setSiteStatus(SiteStatus.PENDING);
        site.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_ANNUAL_FEE));
        site.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()));

        CreditCard card1 = TestUtil.createCreditCard(new Date(), site);
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("4111111111111111");
        card1.setSecurityCode("111");
        card1.setUser(user);

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.PENDING, new SiteManager(site).getSiteStatus());

        EnforcePayment.execute();

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.PENDING, new SiteManager(site).getSiteStatus());

        EnforcePayment.execute();

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.PENDING, new SiteManager(site).getSiteStatus());
    }

    @Test
    public void testExecute_ANNUAL_BILLING_SUSPENDED() {


        final Date currentDate = new Date();

        User user = TestUtil.createUser();

        Site site = TestUtil.createSite();

        site.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_ANNUAL_FEE));
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.SUSPENDED);
        site.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()));

        CreditCard card1 = TestUtil.createCreditCard(new Date(), site);
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("4111111111111111");
        card1.setSecurityCode("111");
        card1.setUser(user);

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.SUSPENDED, new SiteManager(site).getSiteStatus());

        EnforcePayment.execute();

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.SUSPENDED, new SiteManager(site).getSiteStatus());

        EnforcePayment.execute();

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.SUSPENDED, new SiteManager(site).getSiteStatus());
    }


    @Test
    public void testExecute_MONTHLY_BILLING_ACTIVE_childSite() {
        final Date currentDate = new Date();

        User user = TestUtil.createUser();

        Site parentSite = TestUtil.createSite();

        parentSite.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        parentSite.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        parentSite.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() + (365 * TimeInterval.ONE_DAY.getMillis())));

        Site childSite = TestUtil.createSite();

        childSite.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_250MB_MONTHLY_FEE));
        childSite.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        childSite.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()));

        CreditCard card1 = TestUtil.createCreditCard(new Date(), childSite);
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("4111111111111111");
        card1.setSecurityCode("111");
        card1.setUser(user);

        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        parentSite.addChildSiteRegistrationId(childSiteRegistration.getFormId());

        ChildSiteSettings childSiteSettings1 = TestUtil.createChildSiteSettings(childSiteRegistration, parentSite, childSite);
        childSiteSettings1.setEndDate(new Date(currentDate.getTime() + 10 * TimeInterval.ONE_DAY.getMillis()));
        childSiteSettings1.setPrice250mb(10000);

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), childSite.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(childSite).getSiteStatus());

        final Date oldExpirationDate = childSite.getSitePaymentSettings().getExpirationDate();
        Assert.assertNull(parentSite.getIncomeSettings());

        EnforcePayment.execute();

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(oldExpirationDate.getTime() + (30 * TimeInterval.ONE_DAY.getMillis())));
        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.setTime(childSite.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(calendar.get(Calendar.YEAR), expirationDateCalendar.get(Calendar.YEAR));
        Assert.assertEquals(calendar.get(Calendar.MONTH), expirationDateCalendar.get(Calendar.MONTH));
        Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), expirationDateCalendar.get(Calendar.DAY_OF_MONTH));

        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(childSite).getSiteStatus());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(parentSite).getSiteStatus());
        Assert.assertEquals(parentSite.getSitePaymentSettings().getExpirationDate(), new Date(currentDate.getTime() + (365 * TimeInterval.ONE_DAY.getMillis())));
        Assert.assertEquals(9970.01, parentSite.getIncomeSettings().getSum(), 2);

        EnforcePayment.execute();

        expirationDateCalendar.setTime(childSite.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(calendar.get(Calendar.YEAR), expirationDateCalendar.get(Calendar.YEAR));
        Assert.assertEquals(calendar.get(Calendar.MONTH), expirationDateCalendar.get(Calendar.MONTH));
        Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), expirationDateCalendar.get(Calendar.DAY_OF_MONTH));

        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(childSite).getSiteStatus());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(parentSite).getSiteStatus());
        Assert.assertEquals(parentSite.getSitePaymentSettings().getExpirationDate(), new Date(currentDate.getTime() + (365 * TimeInterval.ONE_DAY.getMillis())));
        Assert.assertEquals(9970.01, parentSite.getIncomeSettings().getSum(), 2);
    }




    @Test
    public void testExecute_MONTHLY_BILLING_ACTIVE_childSite_withNotOurPaymentSystem() {
        final Date currentDate = new Date();

        User user = TestUtil.createUser();

        Site parentSite = TestUtil.createSite();
        parentSite.setIncomeSettings(new IncomeSettings());

        parentSite.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        parentSite.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        parentSite.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() + (365 * TimeInterval.ONE_DAY.getMillis())));

        Site childSite = TestUtil.createSite();

        childSite.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_250MB_MONTHLY_FEE));
        childSite.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        childSite.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()));


        CreditCard card1 = TestUtil.createCreditCard(new Date(), childSite);
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("4111111111111111");
        card1.setSecurityCode("111");
        card1.setUser(user);

        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        parentSite.addChildSiteRegistrationId(childSiteRegistration.getFormId());


        ChildSiteSettings childSiteSettings1 = TestUtil.createChildSiteSettings(childSiteRegistration, parentSite, childSite);
        childSiteSettings1.setEndDate(new Date(currentDate.getTime() + 10 * TimeInterval.ONE_DAY.getMillis()));
        childSiteSettings1.setPrice250mb(10000);
        childSiteRegistration.setUseOwnAuthorize(true);

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), childSite.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(childSite).getSiteStatus());

        final Date oldExpirationDate = childSite.getSitePaymentSettings().getExpirationDate();
        Assert.assertEquals("There is no money in income settings before execution. And it should not be there after. Tolik",
                0.0, parentSite.getIncomeSettings().getSum(), 2);

        EnforcePayment.execute();

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(oldExpirationDate.getTime() + (30 * TimeInterval.ONE_DAY.getMillis())));
        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.setTime(childSite.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(calendar.get(Calendar.YEAR), expirationDateCalendar.get(Calendar.YEAR));
        Assert.assertEquals(calendar.get(Calendar.MONTH), expirationDateCalendar.get(Calendar.MONTH));
        Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), expirationDateCalendar.get(Calendar.DAY_OF_MONTH));

        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(childSite).getSiteStatus());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(parentSite).getSiteStatus());
        Assert.assertEquals(parentSite.getSitePaymentSettings().getExpirationDate(), new Date(currentDate.getTime() + (365 * TimeInterval.ONE_DAY.getMillis())));
        Assert.assertEquals("We use users payment credentials, so difference should not be added.",
                0.0, parentSite.getIncomeSettings().getSum(), 2);

        EnforcePayment.execute();

        expirationDateCalendar.setTime(childSite.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(calendar.get(Calendar.YEAR), expirationDateCalendar.get(Calendar.YEAR));
        Assert.assertEquals(calendar.get(Calendar.MONTH), expirationDateCalendar.get(Calendar.MONTH));
        Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), expirationDateCalendar.get(Calendar.DAY_OF_MONTH));

        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(childSite).getSiteStatus());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(parentSite).getSiteStatus());
        Assert.assertEquals(parentSite.getSitePaymentSettings().getExpirationDate(), new Date(currentDate.getTime() + (365 * TimeInterval.ONE_DAY.getMillis())));
        Assert.assertEquals("We use users payment credentials, so difference should not be added.",
                0.0, parentSite.getIncomeSettings().getSum(), 2);
    }

    @Test
    public void testExecute_MONTHLY_BILLING_ACTIVE_childSite_withTestCreditCard() {
        final Date currentDate = new Date();

        User user = TestUtil.createUser();

        Site parentSite = TestUtil.createSite();

        parentSite.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        parentSite.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        parentSite.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() + (365 * TimeInterval.ONE_DAY.getMillis())));

        Site childSite = TestUtil.createSite();

        childSite.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_250MB_MONTHLY_FEE));
        childSite.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        childSite.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()));

        CreditCard card1 = TestUtil.createCreditCard(new Date(), childSite);
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("4444333322221111");
        card1.setSecurityCode("111");
        card1.setUser(user);

        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        parentSite.addChildSiteRegistrationId(childSiteRegistration.getFormId());

        ChildSiteSettings childSiteSettings1 = TestUtil.createChildSiteSettings(childSiteRegistration, parentSite, childSite);
        childSiteSettings1.setEndDate(new Date(currentDate.getTime() + 10 * TimeInterval.ONE_DAY.getMillis()));
        childSiteSettings1.setPrice250mb(10000);

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), childSite.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(childSite).getSiteStatus());

        final Date oldExpirationDate = childSite.getSitePaymentSettings().getExpirationDate();
        parentSite.setIncomeSettings(TestUtil.createIncomeSettings(parentSite, "", 0));
        Assert.assertEquals(0.0, parentSite.getIncomeSettings().getSum(), 1);

        EnforcePayment.execute();

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(oldExpirationDate.getTime() + (30 * TimeInterval.ONE_DAY.getMillis())));
        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.setTime(childSite.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(calendar.get(Calendar.YEAR), expirationDateCalendar.get(Calendar.YEAR));
        Assert.assertEquals(calendar.get(Calendar.MONTH), expirationDateCalendar.get(Calendar.MONTH));
        Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), expirationDateCalendar.get(Calendar.DAY_OF_MONTH));

        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(childSite).getSiteStatus());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(parentSite).getSiteStatus());
        Assert.assertEquals(parentSite.getSitePaymentSettings().getExpirationDate(), new Date(currentDate.getTime() + (365 * TimeInterval.ONE_DAY.getMillis())));
        Assert.assertEquals(0.0, parentSite.getIncomeSettings().getSum(), 1);

        EnforcePayment.execute();

        expirationDateCalendar.setTime(childSite.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(calendar.get(Calendar.YEAR), expirationDateCalendar.get(Calendar.YEAR));
        Assert.assertEquals(calendar.get(Calendar.MONTH), expirationDateCalendar.get(Calendar.MONTH));
        Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), expirationDateCalendar.get(Calendar.DAY_OF_MONTH));

        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(childSite).getSiteStatus());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(parentSite).getSiteStatus());
        Assert.assertEquals(parentSite.getSitePaymentSettings().getExpirationDate(), new Date(currentDate.getTime() + (365 * TimeInterval.ONE_DAY.getMillis())));
        Assert.assertEquals(0.0, parentSite.getIncomeSettings().getSum(), 1);
    }

    @Test
    public void testSetNewExpirationDate_withOldExpirationDateInSite() throws Exception {
        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        final Site site = TestUtil.createSite();

        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        site.setSitePaymentSettings(sitePaymentSettings);


        final Calendar newExpirationDate = new GregorianCalendar();

        final ChargeTypeManager manager = new ChargeTypeManager(site.getSitePaymentSettings().getChargeType());
        EnforcePayment.setNewExpirationDate(site.getSitePaymentSettings(), manager.createNewExpirationDateForActiveOwner(site.getSitePaymentSettings().getExpirationDate()));

        newExpirationDate.setTime(site.getSitePaymentSettings().getExpirationDate());


        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(currentExpirationDate.getTimeInMillis() + DateUtil.minutesToMilliseconds(30 * 24 * 60)));

        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), newExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), newExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), newExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testExecuteForPaypal_SITE_ONE_TIME_FEE() throws Exception {
        final Site site = TestUtil.createSite();
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);
        final Date expirationDate = new Date(System.currentTimeMillis() - TimeInterval.ONE_HUNDRED_YEARS.getMillis());
        sitePaymentSettings.setExpirationDate(expirationDate);
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.AUTHORIZE_NET);
        sitePaymentSettings.setPrice(100);
        site.setSitePaymentSettings(sitePaymentSettings);

        EnforcePayment.execute();

        // We dont execute EnforcePayment for sites with ChargeType = SITE_ONE_TIME_FEE. So all settings are same, as before execution
        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(ChargeType.SITE_ONE_TIME_FEE, site.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(expirationDate, site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, site.getSitePaymentSettings().getPaymentMethod());
        Assert.assertEquals(100, site.getSitePaymentSettings().getPrice(), 0);
    }

    /*--------------------------------------------------AuthorizeNet--------------------------------------------------*/

    @Test
    public void testExecute_MONTHLY_BILLING_ACTIVE_AuthorizeNet() {

        final Date currentDate = new Date();

        User user = TestUtil.createUser();

        Site site = TestUtil.createSite();
        site.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        site.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()));
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);

        CreditCard card1 = TestUtil.createCreditCard(new Date(), site);
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("4111111111111111");
        card1.setSecurityCode("111");
        card1.setUser(user);

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());

        final Date oldExpirationDate = site.getSitePaymentSettings().getExpirationDate();
        EnforcePayment.execute();

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(oldExpirationDate.getTime() + (30 * TimeInterval.ONE_DAY.getMillis())));

        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.setTime(site.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(calendar.get(Calendar.YEAR), expirationDateCalendar.get(Calendar.YEAR));
        Assert.assertEquals(calendar.get(Calendar.MONTH), expirationDateCalendar.get(Calendar.MONTH));
        Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), expirationDateCalendar.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());

        EnforcePayment.execute();


        expirationDateCalendar.setTime(site.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(calendar.get(Calendar.YEAR), expirationDateCalendar.get(Calendar.YEAR));
        Assert.assertEquals(calendar.get(Calendar.MONTH), expirationDateCalendar.get(Calendar.MONTH));
        Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), expirationDateCalendar.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());
    }

    @Test
    public void testExecute_MONTHLY_BILLING_ACTIVE_expirationDateAfterCurrentDate_AuthorizeNet() {

        final Date currentDate = new Date();

        User user = TestUtil.createUser();

        Site site = TestUtil.createSite();
        site.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        site.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() + TimeInterval.ONE_DAY.getMillis()));
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);

        CreditCard card1 = TestUtil.createCreditCard(new Date(), site);
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("4111111111111111");
        card1.setSecurityCode("111");
        card1.setUser(user);

        Assert.assertEquals(new Date(currentDate.getTime() + TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());

        EnforcePayment.execute();

        Assert.assertEquals(new Date(currentDate.getTime() + TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());

        EnforcePayment.execute();

        Assert.assertEquals(new Date(currentDate.getTime() + TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());
    }

    @Test
    public void testExecute_MONTHLY_BILLING_PENDING_AuthorizeNet() {

        final Date currentDate = new Date();

        User user = TestUtil.createUser();

        Site site = TestUtil.createSite();

        new SiteManager(site).setSiteStatus(SiteStatus.PENDING);
        site.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        site.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()));

        CreditCard card1 = TestUtil.createCreditCard(new Date(), site);
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("4111111111111111");
        card1.setSecurityCode("111");
        card1.setUser(user);

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.PENDING, new SiteManager(site).getSiteStatus());

        EnforcePayment.execute();

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.PENDING, new SiteManager(site).getSiteStatus());

        EnforcePayment.execute();

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.PENDING, new SiteManager(site).getSiteStatus());
    }

    @Test
    public void testExecute_MONTHLY_BILLING_SUSPENDED_AuthorizeNet() {

        final Date currentDate = new Date();

        User user = TestUtil.createUser();

        Site site = TestUtil.createSite();

        site.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.SUSPENDED);
        site.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()));

        CreditCard card1 = TestUtil.createCreditCard(new Date(), site);
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("4111111111111111");
        card1.setSecurityCode("111");
        card1.setUser(user);

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.SUSPENDED, new SiteManager(site).getSiteStatus());

        EnforcePayment.execute();

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.SUSPENDED, new SiteManager(site).getSiteStatus());

        EnforcePayment.execute();

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.SUSPENDED, new SiteManager(site).getSiteStatus());
    }


    @Test
    public void testExecute_ANNUAL_BILLING_ACTIVE_AuthorizeNet() {

        final Date currentDate = new Date();

        User user = TestUtil.createUser();

        Site site = TestUtil.createSite();

        site.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_ANNUAL_FEE));
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        site.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()));

        CreditCard card1 = TestUtil.createCreditCard(new Date(), site);
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("4111111111111111");
        card1.setSecurityCode("111");
        card1.setUser(user);

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());

        final Date oldExpirationDate = site.getSitePaymentSettings().getExpirationDate();
        EnforcePayment.execute();


        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(oldExpirationDate.getTime() + (365 * TimeInterval.ONE_DAY.getMillis())));
        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.setTime(site.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(calendar.get(Calendar.YEAR), expirationDateCalendar.get(Calendar.YEAR));
        Assert.assertEquals(calendar.get(Calendar.MONTH), expirationDateCalendar.get(Calendar.MONTH));
        Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), expirationDateCalendar.get(Calendar.DAY_OF_MONTH));

        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());

        EnforcePayment.execute();

        expirationDateCalendar.setTime(site.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(calendar.get(Calendar.YEAR), expirationDateCalendar.get(Calendar.YEAR));
        Assert.assertEquals(calendar.get(Calendar.MONTH), expirationDateCalendar.get(Calendar.MONTH));
        Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), expirationDateCalendar.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());
    }

    @Test
    public void testExecute_ANNUAL_BILLING_PENDING_AuthorizeNet() {

        final Date currentDate = new Date();

        User user = TestUtil.createUser();

        Site site = TestUtil.createSite();

        new SiteManager(site).setSiteStatus(SiteStatus.PENDING);
        site.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_ANNUAL_FEE));
        site.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()));

        CreditCard card1 = TestUtil.createCreditCard(new Date(), site);
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("4111111111111111");
        card1.setSecurityCode("111");
        card1.setUser(user);

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.PENDING, new SiteManager(site).getSiteStatus());

        EnforcePayment.execute();

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.PENDING, new SiteManager(site).getSiteStatus());

        EnforcePayment.execute();

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.PENDING, new SiteManager(site).getSiteStatus());
    }

    @Test
    public void testExecute_ANNUAL_BILLING_SUSPENDED_AuthorizeNet() {


        final Date currentDate = new Date();

        User user = TestUtil.createUser();

        Site site = TestUtil.createSite();

        site.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_ANNUAL_FEE));
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.SUSPENDED);
        site.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()));

        CreditCard card1 = TestUtil.createCreditCard(new Date(), site);
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("4111111111111111");
        card1.setSecurityCode("111");
        card1.setUser(user);

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.SUSPENDED, new SiteManager(site).getSiteStatus());

        EnforcePayment.execute();

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.SUSPENDED, new SiteManager(site).getSiteStatus());

        EnforcePayment.execute();

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.SUSPENDED, new SiteManager(site).getSiteStatus());
    }


    @Test
    public void testExecute_MONTHLY_BILLING_ACTIVE_childSite_AuthorizeNet() {

        final Date currentDate = new Date();

        User user = TestUtil.createUser();

        Site parentSite = TestUtil.createSite();

        parentSite.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        parentSite.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        parentSite.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() + (365 * TimeInterval.ONE_DAY.getMillis())));

        Site childSite = TestUtil.createSite();

        childSite.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_250MB_MONTHLY_FEE));
        childSite.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        childSite.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()));

        CreditCard card1 = TestUtil.createCreditCard(new Date(), childSite);
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("4111111111111111");
        card1.setSecurityCode("111");
        card1.setUser(user);

        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        parentSite.addChildSiteRegistrationId(childSiteRegistration.getFormId());

        ChildSiteSettings childSiteSettings1 = TestUtil.createChildSiteSettings(childSiteRegistration, parentSite, childSite);
        childSiteSettings1.setEndDate(new Date(currentDate.getTime() + 10 * TimeInterval.ONE_DAY.getMillis()));
        childSiteSettings1.setPrice250mb(10000);

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), childSite.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(childSite).getSiteStatus());

        final Date oldExpirationDate = childSite.getSitePaymentSettings().getExpirationDate();
        Assert.assertNull(parentSite.getIncomeSettings());

        EnforcePayment.execute();

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(oldExpirationDate.getTime() + (30 * TimeInterval.ONE_DAY.getMillis())));
        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.setTime(childSite.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(calendar.get(Calendar.YEAR), expirationDateCalendar.get(Calendar.YEAR));
        Assert.assertEquals(calendar.get(Calendar.MONTH), expirationDateCalendar.get(Calendar.MONTH));
        Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), expirationDateCalendar.get(Calendar.DAY_OF_MONTH));

        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(childSite).getSiteStatus());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(parentSite).getSiteStatus());
        Assert.assertEquals(parentSite.getSitePaymentSettings().getExpirationDate(), new Date(currentDate.getTime() + (365 * TimeInterval.ONE_DAY.getMillis())));
        Assert.assertEquals(9970.01, parentSite.getIncomeSettings().getSum(), 2);

        EnforcePayment.execute();

        expirationDateCalendar.setTime(childSite.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(calendar.get(Calendar.YEAR), expirationDateCalendar.get(Calendar.YEAR));
        Assert.assertEquals(calendar.get(Calendar.MONTH), expirationDateCalendar.get(Calendar.MONTH));
        Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), expirationDateCalendar.get(Calendar.DAY_OF_MONTH));

        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(childSite).getSiteStatus());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(parentSite).getSiteStatus());
        Assert.assertEquals(parentSite.getSitePaymentSettings().getExpirationDate(), new Date(currentDate.getTime() + (365 * TimeInterval.ONE_DAY.getMillis())));
        Assert.assertEquals(9970.01, parentSite.getIncomeSettings().getSum(), 2);
    }

    @Test
    public void testExecute_MONTHLY_BILLING_ACTIVE_childSite_withTestCreditCard_AuthorizeNet() {

        final Date currentDate = new Date();

        User user = TestUtil.createUser();

        Site parentSite = TestUtil.createSite();

        parentSite.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        parentSite.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        parentSite.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() + (365 * TimeInterval.ONE_DAY.getMillis())));

        Site childSite = TestUtil.createSite();

        childSite.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_250MB_MONTHLY_FEE));
        childSite.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        childSite.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()));

        CreditCard card1 = TestUtil.createCreditCard(new Date(), childSite);
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("4444333322221111");
        card1.setSecurityCode("111");
        card1.setUser(user);

        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        parentSite.addChildSiteRegistrationId(childSiteRegistration.getFormId());

        ChildSiteSettings childSiteSettings1 = TestUtil.createChildSiteSettings(childSiteRegistration, parentSite, childSite);
        childSiteSettings1.setEndDate(new Date(currentDate.getTime() + 10 * TimeInterval.ONE_DAY.getMillis()));
        childSiteSettings1.setPrice250mb(10000);

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), childSite.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(childSite).getSiteStatus());

        final Date oldExpirationDate = childSite.getSitePaymentSettings().getExpirationDate();
        parentSite.setIncomeSettings(TestUtil.createIncomeSettings(parentSite, "", 0));
        Assert.assertEquals(0.0, parentSite.getIncomeSettings().getSum(), 1);

        EnforcePayment.execute();

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(oldExpirationDate.getTime() + (30 * TimeInterval.ONE_DAY.getMillis())));
        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.setTime(childSite.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(calendar.get(Calendar.YEAR), expirationDateCalendar.get(Calendar.YEAR));
        Assert.assertEquals(calendar.get(Calendar.MONTH), expirationDateCalendar.get(Calendar.MONTH));
        Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), expirationDateCalendar.get(Calendar.DAY_OF_MONTH));

        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(childSite).getSiteStatus());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(parentSite).getSiteStatus());
        Assert.assertEquals(parentSite.getSitePaymentSettings().getExpirationDate(), new Date(currentDate.getTime() + (365 * TimeInterval.ONE_DAY.getMillis())));
        Assert.assertEquals(0.0, parentSite.getIncomeSettings().getSum(), 1);

        EnforcePayment.execute();

        expirationDateCalendar.setTime(childSite.getSitePaymentSettings().getExpirationDate());

        Assert.assertEquals(calendar.get(Calendar.YEAR), expirationDateCalendar.get(Calendar.YEAR));
        Assert.assertEquals(calendar.get(Calendar.MONTH), expirationDateCalendar.get(Calendar.MONTH));
        Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), expirationDateCalendar.get(Calendar.DAY_OF_MONTH));

        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(childSite).getSiteStatus());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(parentSite).getSiteStatus());
        Assert.assertEquals(parentSite.getSitePaymentSettings().getExpirationDate(), new Date(currentDate.getTime() + (365 * TimeInterval.ONE_DAY.getMillis())));
        Assert.assertEquals(0.0, parentSite.getIncomeSettings().getSum(), 1);
    }

    @Test
    public void testExecute_MONTHLY_BILLING_ACTIVE_withPurchaseException_siteExpirationDateEqualsCurrentDateMinus2Days_AuthorizeNet() {
        final Config config = ServiceLocator.getConfigStorage().get();
        config.setApplicationUrl("testApplicationUrl");
        config.setApplicationName("testApplicationName");

        final Date currentDate = new Date();
        ServiceLocator.setAuthorizeNet(new AuthorizeNetWithExceptionsInMethods());

        User user = TestUtil.createUser();
        user.setFirstName("userFirstName");
        user.setLastName("userLastName");


        Site site = TestUtil.createSite("title", "url");

        site.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        site.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis() * 2));

        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        CreditCard creditCard = TestUtil.createCreditCard(new Date(), site);
        creditCard.setBillingAddress1("adr1");
        creditCard.setBillingAddress2("adr2");
        creditCard.setCreditCardNumber("4111111111111111");
        creditCard.setSecurityCode("111");
        creditCard.setUser(user);

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis() * 2), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());
        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(0, mockMailSender.getMails().size());

        EnforcePayment.execute();

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis() * 2), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());
        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());

        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals(user.getEmail(), mail().getTo());
        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals("Deactivation Warning - " + config.getApplicationName() + " was unable to process your credit card", mail().getSubject());
        try {
            Assert.assertEquals("This test is for locale",
                    "Dear " + user.getFirstName() + " " + user.getLastName() + ",\n\n" +
                            "Deactivation Warning! " + config.getApplicationName() + " was unable to process your credit card ending in " + DateUtil.toMonthAndYear(creditCard.getExpirationYear(), creditCard.getExpirationMonth()) + ".\n\n" +
                            "Unless you update your payment information your " + config.getApplicationName() + " powered web site, " + site.getTitle() + ", will be suspended in 13 days,\n\n" +
                            "Follow this link to Update your payment information as soon as possible. http://" + config.getApplicationUrl() + "/account/updatePaymentInfo.action?selectedSiteId=" + site.getSiteId() + "\n\n" +
                             config.getApplicationName() + " is a fabulous tool for creating and maintaining\n" +
                            "professional web sites at the push of a button. " + config.getApplicationName() + " allows you to create functionally rich sites, with plenty of rich media.",
                    mail().getText());
        } catch (Throwable e) {
            Assert.assertEquals("And this one is for server",
                    "Dear " + user.getFirstName() + " " + user.getLastName() + ",\n\n" +
                            "Deactivation Warning! " + config.getApplicationName() + " was unable to process your credit card ending in " + DateUtil.toMonthAndYear(creditCard.getExpirationYear(), creditCard.getExpirationMonth()) + ".\n\n" +
                            "Unless you update your payment information your " + config.getApplicationName() + " powered web site, " + site.getTitle() + ", will be suspended in 12 days,\n\n" +
                            "Follow this link to Update your payment information as soon as possible. http://" + config.getApplicationUrl() + "/account/updatePaymentInfo.action?selectedSiteId=" + site.getSiteId() + "\n\n" +
                             config.getApplicationName() + " is a fabulous tool for creating and maintaining\n" +
                            "professional web sites at the push of a button. " + config.getApplicationName() + " allows you to create functionally rich sites, with plenty of rich media.",
                    mail().getText());
        }
    }

    @Test
    public void testExecute_MONTHLY_BILLING_ACTIVE_withPurchaseException_siteExpirationDateEqualsCurrentDateMinus1Day_sendDeactivationEmailFalse_AuthorizeNet() {

        Config config = ServiceLocator.getConfigStorage().get();
        BillingInfoProperties properties = config.getBillingInfoProperties();
        properties.setSendPaymentNotificationEmails(false);

        final Date currentDate = new Date();
        ServiceLocator.setAuthorizeNet(new AuthorizeNetWithExceptionsInMethods());

        User user = TestUtil.createUser();
        user.setFirstName("userFirstName");
        user.setLastName("userLastName");


        Site site = TestUtil.createSite("title", "url");

        site.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        site.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()));

        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        CreditCard card1 = TestUtil.createCreditCard(new Date(), site);
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("4111111111111111");
        card1.setSecurityCode("111");
        card1.setUser(user);

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());
        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(0, mockMailSender.getMails().size());

        EnforcePayment.execute();

        Assert.assertEquals(new Date(currentDate.getTime() - TimeInterval.ONE_DAY.getMillis()), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());
        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());

        Assert.assertEquals(0, mockMailSender.getMails().size());
    }


    @Test
    public void testExecute_MONTHLY_BILLING_ACTIVE_withPurchaseException_siteExpirationDateEqualsCurrentDateMinus16Days_AuthorizeNet() {
        final Config config = ServiceLocator.getConfigStorage().get();
        config.setApplicationUrl("testApplicationUrl");
        config.setApplicationName("testApplicationName");

        final Date currentDate = new Date();
        ServiceLocator.setAuthorizeNet(new AuthorizeNetWithExceptionsInMethods());

        User user = TestUtil.createUser();
        user.setFirstName("firstName");
        user.setLastName("lastName");


        Site site = TestUtil.createSite();

        site.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        site.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() - (16 * TimeInterval.ONE_DAY.getMillis())));

        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        CreditCard card1 = TestUtil.createCreditCard(new Date(), site);
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("4111111111111111");
        card1.setSecurityCode("111");
        card1.setUser(user);

        Assert.assertEquals(new Date(currentDate.getTime() - (16 * TimeInterval.ONE_DAY.getMillis())), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());
        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(0, mockMailSender.getMails().size());

        EnforcePayment.execute();

        Assert.assertEquals(new Date(currentDate.getTime() - (16 * TimeInterval.ONE_DAY.getMillis())), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.PENDING, new SiteManager(site).getSiteStatus());
        Assert.assertNull(site.getSitePaymentSettings().getCreditCard());

        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals(user.getEmail(), mail().getTo());
        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals("Your site has been suspended", mail().getSubject());
        Assert.assertEquals(
                "Dear " + user.getFirstName() + " " + user.getLastName() + ",\n\n" +
                        "Warning. Your " + config.getApplicationName() + " powered web site, " + StringUtil.getEmptyOrString(site.getTitle()) + " has been suspended, pending updated payment information.\n\n" +
                        "Follow this link to Update your payment information as soon as possible. http://" + config.getApplicationUrl() + "/account/updatePaymentInfo.action?selectedSiteId=" + site.getSiteId() + "\n\n" +
                         config.getApplicationName() + " is a fabulous new tool for creating and maintaining\n" +
                        "professional web sites at the push of a button. " + config.getApplicationName() + " allows you to create functionally rich sites, with plenty of media.",
                mail().getText());
    }

    @Test
    public void testExecute_MONTHLY_BILLING_ACTIVE_withPurchaseException_siteExpirationDateEqualsCurrentDateMinus16Days_sendDeactivationEmailFalse_AuthorizeNet() {


        Config config = ServiceLocator.getConfigStorage().get();
        BillingInfoProperties properties = config.getBillingInfoProperties();
        properties.setSendPaymentNotificationEmails(false);

        final Date currentDate = new Date();
        ServiceLocator.setAuthorizeNet(new AuthorizeNetWithExceptionsInMethods());

        User user = TestUtil.createUser();
        user.setFirstName("firstName");
        user.setLastName("lastName");


        Site site = TestUtil.createSite();

        site.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        site.getSitePaymentSettings().setSiteStatus(SiteStatus.ACTIVE);
        site.getSitePaymentSettings().setExpirationDate(new Date(currentDate.getTime() - (16 * TimeInterval.ONE_DAY.getMillis())));

        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        CreditCard card1 = TestUtil.createCreditCard(new Date(), site);
        card1.setBillingAddress1("adr1");
        card1.setBillingAddress2("adr2");
        card1.setCreditCardNumber("4111111111111111");
        card1.setSecurityCode("111");
        card1.setUser(user);

        Assert.assertEquals(new Date(currentDate.getTime() - (16 * TimeInterval.ONE_DAY.getMillis())), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());
        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(0, mockMailSender.getMails().size());

        EnforcePayment.execute();

        Assert.assertEquals(new Date(currentDate.getTime() - (16 * TimeInterval.ONE_DAY.getMillis())), site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(SiteStatus.PENDING, new SiteManager(site).getSiteStatus());
        Assert.assertNull(site.getSitePaymentSettings().getCreditCard());

        Assert.assertEquals(0, mockMailSender.getMails().size());
    }

    @Test
    public void testSetNewExpirationDate_withOldExpirationDateInSite_AuthorizeNet() throws Exception {


        final Calendar currentExpirationDate = new GregorianCalendar(2010, 3, 1, 0, 0);
        final Site site = TestUtil.createSite();

        SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setExpirationDate(currentExpirationDate.getTime());
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setChargeType(ChargeType.SITE_MONTHLY_FEE);
        site.setSitePaymentSettings(sitePaymentSettings);


        final Calendar newExpirationDate = new GregorianCalendar();

        final ChargeTypeManager manager = new ChargeTypeManager(site.getSitePaymentSettings().getChargeType());
        EnforcePayment.setNewExpirationDate(site.getSitePaymentSettings(), manager.createNewExpirationDateForActiveOwner(site.getSitePaymentSettings().getExpirationDate()));

        newExpirationDate.setTime(site.getSitePaymentSettings().getExpirationDate());


        final Calendar expectedExpirationDate = new GregorianCalendar();
        expectedExpirationDate.setTime(new Date(currentExpirationDate.getTimeInMillis() + DateUtil.minutesToMilliseconds(30 * 24 * 60)));

        Assert.assertEquals(expectedExpirationDate.get(Calendar.YEAR), newExpirationDate.get(Calendar.YEAR));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.MONTH), newExpirationDate.get(Calendar.MONTH));
        Assert.assertEquals(expectedExpirationDate.get(Calendar.DAY_OF_MONTH), newExpirationDate.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testExecuteForPaypal_SITE_ONE_TIME_FEE_AuthorizeNet() throws Exception {


        final Site site = TestUtil.createSite();
        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setChargeType(ChargeType.SITE_ONE_TIME_FEE);
        final Date expirationDate = new Date(System.currentTimeMillis() - TimeInterval.ONE_HUNDRED_YEARS.getMillis());
        sitePaymentSettings.setExpirationDate(expirationDate);
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setPaymentMethod(PaymentMethod.AUTHORIZE_NET);
        sitePaymentSettings.setPrice(100);
        site.setSitePaymentSettings(sitePaymentSettings);

        EnforcePayment.execute();

        // We dont execute EnforcePayment for sites with ChargeType = SITE_ONE_TIME_FEE. So all settings are same, as before execution
        Assert.assertEquals(SiteStatus.ACTIVE, site.getSitePaymentSettings().getSiteStatus());
        Assert.assertEquals(ChargeType.SITE_ONE_TIME_FEE, site.getSitePaymentSettings().getChargeType());
        Assert.assertEquals(expirationDate, site.getSitePaymentSettings().getExpirationDate());
        Assert.assertEquals(PaymentMethod.AUTHORIZE_NET, site.getSitePaymentSettings().getPaymentMethod());
        Assert.assertEquals(100, site.getSitePaymentSettings().getPrice(), 0);
    }
    /*--------------------------------------------------AuthorizeNet--------------------------------------------------*/

    private Mail mail() {
        return mockMailSender.getMails().get(0);
    }
    private final MockMailSender mockMailSender = (MockMailSender) ServiceLocator.getMailSender();
}
