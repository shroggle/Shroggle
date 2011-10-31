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
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.BillingInfoProperties;
import com.shroggle.util.config.Config;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.mail.MockMailSender;
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
public class CreditCardNotificationMailSenderTest {

    @Before
    public void before() {
        Config config = ServiceLocator.getConfigStorage().get();
        BillingInfoProperties properties = config.getBillingInfoProperties();
        properties.setAnnualBillingExpirationDate(365 * 24 * 60);
        properties.setMonthlyBillingExpirationDate(30 * 24 * 60);
        properties.setDeactivateSiteAfter((15 * 24 * 60));
        config.setApplicationUrl("www.shroggle.com");
    }

    @Test
    public void testRun_withEqualCurrentAndExpirationDates() {
        final Config config = ServiceLocator.getConfigStorage().get();
        config.setApplicationUrl("testApplicationUrl");
        config.setApplicationName("testApplicationName");
        Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);

        User user1 = TestUtil.createUser("user1Email");
        User user2 = TestUtil.createUser("user2Email");
        User user3 = TestUtil.createUser("user3Email");

        user1.setFirstName("userFirstName");
        user1.setLastName("userLastName");

        user2.setFirstName("userFirstName");
        user2.setLastName("userLastName");

        user3.setFirstName("userFirstName");
        user3.setLastName("userLastName");

        TestUtil.createUserOnSiteRightActive(user1, site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user2, site, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user3, site, SiteAccessLevel.ADMINISTRATOR);


        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.set(Calendar.YEAR, 2008);
        expirationDateCalendar.set(Calendar.MONTH, Calendar.MAY);

        CreditCard card = TestUtil.createCreditCard(expirationDateCalendar.getTime(), site);

        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(card.getCreditCardId(), site.getSitePaymentSettings().getCreditCard().getCreditCardId());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());

        new CreditCardNotificationMailSender(0, 0).run();

        Assert.assertEquals(2, mockMailSender.getMails().size());
        Assert.assertEquals(user1.getEmail(), mockMailSender.getMails().get(0).getTo());
        Assert.assertEquals(user3.getEmail(), mockMailSender.getMails().get(1).getTo());

        Assert.assertEquals(2, mockMailSender.getMails().size());
        Assert.assertEquals(2, mockMailSender.getMails().size());

        for (Mail mail : mockMailSender.getMails()) {
            Assert.assertEquals(site.getTitle() + " credit card is about to expire", mail.getSubject());
        }
        final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
        for (Mail mail : mockMailSender.getMails()) {
            Assert.assertEquals(
                    "Dear userFirstName userLastName,\n\n" +
                        "You are receiving this email because you are registered as a site administrator for the " + config.getApplicationName() + " powered web site: " + site.getTitle() + "\n\n" +
                        "This is a courtesy notice to inform you that the credit card that we have on record for this site, will soon expire.\n\n" +
                        "To ensure continued service this credit card information will need to be updated. Please note that the site will be suspended if valid payment details are not provided.\n\n" +
                        "To update the payment information please follow the link. http://" + configStorage.get().getApplicationUrl() + "/account/updatePaymentInfo.action?selectedSiteId=" + site.getSiteId() + "\n\n" +
                        config.getApplicationName() + " is a fabulous tool for creating and maintaining professional web sites at the push of a button. " + config.getApplicationName() + " allows you to create functionally rich sites, with plenty of rich media",
                    mail.getText());
        }
    }

    @Test
    public void testRun_withEqualCurrentAndExpirationDates_forChildSite() {
        final Site parentSite = TestUtil.createSite();
        parentSite.setSubDomain("parentSite");
        Site site = TestUtil.createChildSite(parentSite);
        site.getChildSiteSettings().getChildSiteRegistration().setName("Child Site Registration");
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);

        User user1 = TestUtil.createUser("user1Email");
        User user2 = TestUtil.createUser("user2Email");
        User user3 = TestUtil.createUser("user3Email");

        user1.setFirstName("userFirstName");
        user1.setLastName("userLastName");

        user2.setFirstName("userFirstName");
        user2.setLastName("userLastName");

        user3.setFirstName("userFirstName");
        user3.setLastName("userLastName");

        TestUtil.createUserOnSiteRightActive(user1, site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user2, site, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user3, site, SiteAccessLevel.ADMINISTRATOR);


        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.set(Calendar.YEAR, 2008);
        expirationDateCalendar.set(Calendar.MONTH, Calendar.MAY);

        CreditCard card = TestUtil.createCreditCard(expirationDateCalendar.getTime(), site);

        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(card.getCreditCardId(), site.getSitePaymentSettings().getCreditCard().getCreditCardId());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());

        new CreditCardNotificationMailSender(0, 0).run();

        Assert.assertEquals(2, mockMailSender.getMails().size());
        Assert.assertEquals(user1.getEmail(), mockMailSender.getMails().get(0).getTo());
        Assert.assertEquals(user3.getEmail(), mockMailSender.getMails().get(1).getTo());

        Assert.assertEquals(2, mockMailSender.getMails().size());
        Assert.assertEquals(2, mockMailSender.getMails().size());

        for (Mail mail : mockMailSender.getMails()) {
            Assert.assertEquals(site.getTitle() + " credit card is about to expire", mail.getSubject());
        }
        final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
        for (Mail mail : mockMailSender.getMails()) {
            Assert.assertEquals(
                    "Dear userFirstName userLastName,\n\n" +
                        "You are receiving this email because you are registered as a site administrator for the Child Site Registration powered web site: " + site.getTitle() + "\n\n" +
                        "This is a courtesy notice to inform you that the credit card that we have on record for this site, will soon expire.\n\n" +
                        "To ensure continued service this credit card information will need to be updated. Please note that the site will be suspended if valid payment details are not provided.\n\n" +
                        "To update the payment information please follow the link. http://" + configStorage.get().getApplicationUrl() + "/account/updatePaymentInfo.action?selectedSiteId=" + site.getSiteId() + "\n\n" +
                        "Child Site Registration is a fabulous tool for creating and maintaining professional web sites at the push of a button. Child Site Registration allows you to create functionally rich sites, with plenty of rich media",
                    mail.getText());
        }
    }


    @Test
    public void testRun_withEqualCurrentAndExpirationDates_forRegistrantAndNetworkAdmin() {
        final Config config = ServiceLocator.getConfigStorage().get();
        config.setApplicationUrl("testApplicationUrl");
        config.setApplicationName("testApplicationName");
        Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);

        User user1 = TestUtil.createUser("user1Email");
        User user2 = TestUtil.createUser("user2Email");
        User user3 = TestUtil.createUser("user3Email");

        user1.setFirstName("userFirstName");
        user1.setLastName("userLastName");

        user2.setFirstName("userFirstName");
        user2.setLastName("userLastName");

        user3.setFirstName("userFirstName");
        user3.setLastName("userLastName");

        TestUtil.createUserOnSiteRightActive(user1, site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user2, site, SiteAccessLevel.EDITOR);
        final UserOnSiteRight userOnSiteRight = TestUtil.createUserOnSiteRightActive(user3, site, SiteAccessLevel.ADMINISTRATOR);
        userOnSiteRight.setFromNetwork(true);


        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.set(Calendar.YEAR, 2008);
        expirationDateCalendar.set(Calendar.MONTH, Calendar.MAY);

        CreditCard card = TestUtil.createCreditCard(expirationDateCalendar.getTime(), site);

        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(card.getCreditCardId(), site.getSitePaymentSettings().getCreditCard().getCreditCardId());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());

        new CreditCardNotificationMailSender(0, 0).run();

        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals(user1.getEmail(), mockMailSender.getMails().get(0).getTo());

        Assert.assertEquals(1, mockMailSender.getMails().size());

        for (Mail mail : mockMailSender.getMails()) {
            Assert.assertEquals(site.getTitle() + " credit card is about to expire", mail.getSubject());
        }
        final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
        Assert.assertEquals(
                "Dear userFirstName userLastName,\n\n" +
                        "You are receiving this email because you are registered as a site administrator for the " + config.getApplicationName() + " powered web site: " + site.getTitle() + "\n\n" +
                        "This is a courtesy notice to inform you that the credit card that we have on record for this site, will soon expire.\n\n" +
                        "To ensure continued service this credit card information will need to be updated. Please note that the site will be suspended if valid payment details are not provided.\n\n" +
                        "To update the payment information please follow the link. http://" + configStorage.get().getApplicationUrl() + "/account/updatePaymentInfo.action?selectedSiteId=" + site.getSiteId() + "\n\n" +
                         config.getApplicationName() + " is a fabulous tool for creating and maintaining professional web sites at the push of a button. " + config.getApplicationName() + " allows you to create functionally rich sites, with plenty of rich media"
                , mockMailSender.getMails().get(0).getText());

    }

    @Test
    public void testRun_withEqualCurrentAndExpirationDates_withTestCerditCard() {
        Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);

        User user1 = TestUtil.createUser("user1Email");
        User user2 = TestUtil.createUser("user2Email");
        User user3 = TestUtil.createUser("user3Email");

        user1.setFirstName("userFirstName");
        user1.setLastName("userLastName");

        user2.setFirstName("userFirstName");
        user2.setLastName("userLastName");

        user3.setFirstName("userFirstName");
        user3.setLastName("userLastName");

        TestUtil.createUserOnSiteRightActive(user1, site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user2, site, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user3, site, SiteAccessLevel.ADMINISTRATOR);


        Calendar expirationDateCalendar = new GregorianCalendar();
        expirationDateCalendar.set(Calendar.YEAR, 2008);
        expirationDateCalendar.set(Calendar.MONTH, Calendar.MAY);

        CreditCard card = TestUtil.createCreditCard(expirationDateCalendar.getTime(), site);
        card.setCreditCardNumber(com.shroggle.logic.creditCard.CreditCardManager.getTestCreditCardNumber());

        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(card.getCreditCardId(), site.getSitePaymentSettings().getCreditCard().getCreditCardId());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());

        new CreditCardNotificationMailSender(0, 0).run();

        Assert.assertEquals(0, mockMailSender.getMails().size());

        Assert.assertEquals(0, mockMailSender.getMails().size());
    }


    @Test
    public void testRun_withExpirationDateEqualCurrentDatePlus1year() {
        Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);

        User user1 = TestUtil.createUser("user1Email");
        User user2 = TestUtil.createUser("user2Email");
        User user3 = TestUtil.createUser("user3Email");

        TestUtil.createUserOnSiteRightActive(user1, site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user2, site, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user3, site, SiteAccessLevel.ADMINISTRATOR);

        CreditCard card = TestUtil.createCreditCard(new Date(System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000L), site);

        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(card.getCreditCardId(), site.getSitePaymentSettings().getCreditCard().getCreditCardId());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());

        new CreditCardNotificationMailSender(0, 0).run();

        Assert.assertEquals(0, mockMailSender.getMails().size());
    }


    @Test
    public void testRun_withEqualCurrentAndExpirationDates_SUSPENDED() {
        Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.SUSPENDED);

        User user1 = TestUtil.createUser("user1Email");
        User user2 = TestUtil.createUser("user2Email");
        User user3 = TestUtil.createUser("user3Email");

        TestUtil.createUserOnSiteRightActive(user1, site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user2, site, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user3, site, SiteAccessLevel.ADMINISTRATOR);

        CreditCard card = TestUtil.createCreditCard(new Date(), site);

        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(card.getCreditCardId(), site.getSitePaymentSettings().getCreditCard().getCreditCardId());
        Assert.assertEquals(SiteStatus.SUSPENDED, new SiteManager(site).getSiteStatus());

        new CreditCardNotificationMailSender(0, 0).run();


        Assert.assertEquals(0, mockMailSender.getMails().size());
    }


    @Test
    public void testRun_withExpirationDateEqualCurrentDatePlus1year_SUSPENDED() {
        Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.SUSPENDED);

        User user1 = TestUtil.createUser("user1Email");
        User user2 = TestUtil.createUser("user2Email");
        User user3 = TestUtil.createUser("user3Email");

        TestUtil.createUserOnSiteRightActive(user1, site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user2, site, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user3, site, SiteAccessLevel.ADMINISTRATOR);

        CreditCard card = TestUtil.createCreditCard(new Date(System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000L), site);

        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(card.getCreditCardId(), site.getSitePaymentSettings().getCreditCard().getCreditCardId());
        Assert.assertEquals(SiteStatus.SUSPENDED, new SiteManager(site).getSiteStatus());

        new CreditCardNotificationMailSender(0, 0).run();

        Assert.assertEquals(0, mockMailSender.getMails().size());
    }


    @Test
    public void testRun_withExpirationDateEqualCurrentDatePlus15days_SUSPENDED() {
        Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.SUSPENDED);

        User user1 = TestUtil.createUser("user1Email");
        User user2 = TestUtil.createUser("user2Email");
        User user3 = TestUtil.createUser("user3Email");

        TestUtil.createUserOnSiteRightActive(user1, site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user2, site, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user3, site, SiteAccessLevel.ADMINISTRATOR);

        CreditCard card = TestUtil.createCreditCard(new Date(System.currentTimeMillis() + 15 * 24 * 60 * 60 * 1000L), site);

        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(card.getCreditCardId(), site.getSitePaymentSettings().getCreditCard().getCreditCardId());
        Assert.assertEquals(SiteStatus.SUSPENDED, new SiteManager(site).getSiteStatus());

        new CreditCardNotificationMailSender(0, 0).run();

        Assert.assertEquals(0, mockMailSender.getMails().size());
    }


    @Test
    public void testRun_withEqualCurrentAndExpirationDates_PENDING() {
        Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.PENDING);

        User user1 = TestUtil.createUser("user1Email");
        User user2 = TestUtil.createUser("user2Email");
        User user3 = TestUtil.createUser("user3Email");

        TestUtil.createUserOnSiteRightActive(user1, site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user2, site, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user3, site, SiteAccessLevel.ADMINISTRATOR);

        CreditCard card = TestUtil.createCreditCard(new Date(), site);

        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(card.getCreditCardId(), site.getSitePaymentSettings().getCreditCard().getCreditCardId());
        Assert.assertEquals(SiteStatus.PENDING, new SiteManager(site).getSiteStatus());

        new CreditCardNotificationMailSender(0, 0).run();

        Assert.assertEquals(0, mockMailSender.getMails().size());
    }


    @Test
    public void testRun_withExpirationDateEqualCurrentDatePlus1year_PENDING() {
        Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.PENDING);

        User user1 = TestUtil.createUser("user1Email");
        User user2 = TestUtil.createUser("user2Email");
        User user3 = TestUtil.createUser("user3Email");

        TestUtil.createUserOnSiteRightActive(user1, site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user2, site, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user3, site, SiteAccessLevel.ADMINISTRATOR);

        CreditCard card = TestUtil.createCreditCard(new Date(System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000L), site);

        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(card.getCreditCardId(), site.getSitePaymentSettings().getCreditCard().getCreditCardId());
        Assert.assertEquals(SiteStatus.PENDING, new SiteManager(site).getSiteStatus());

        new CreditCardNotificationMailSender(0, 0).run();

        Assert.assertEquals(0, mockMailSender.getMails().size());
    }


    @Test
    public void testRun_withExpirationDateEqualCurrentDatePlus15days_PENDING() {
        Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.PENDING);

        User user1 = TestUtil.createUser("user1Email");
        User user2 = TestUtil.createUser("user2Email");
        User user3 = TestUtil.createUser("user3Email");

        TestUtil.createUserOnSiteRightActive(user1, site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user2, site, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user3, site, SiteAccessLevel.ADMINISTRATOR);

        CreditCard card = TestUtil.createCreditCard(new Date(System.currentTimeMillis() + 15 * 24 * 60 * 60 * 1000L), site);

        Assert.assertNotNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(card.getCreditCardId(), site.getSitePaymentSettings().getCreditCard().getCreditCardId());
        Assert.assertEquals(SiteStatus.PENDING, new SiteManager(site).getSiteStatus());

        new CreditCardNotificationMailSender(0, 0).run();

        Assert.assertEquals(0, mockMailSender.getMails().size());
    }


    @Test
    public void testRun_withEqualCurrentAndExpirationDates_ACTIVE_withoutCreditCard() {
        Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);

        User user1 = TestUtil.createUser("user1Email");
        User user2 = TestUtil.createUser("user2Email");
        User user3 = TestUtil.createUser("user3Email");

        TestUtil.createUserOnSiteRightActive(user1, site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user2, site, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user3, site, SiteAccessLevel.ADMINISTRATOR);

        Assert.assertNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());

        new CreditCardNotificationMailSender(0, 0).run();

        Assert.assertEquals(0, mockMailSender.getMails().size());
    }


    @Test
    public void testRun_withExpirationDateEqualCurrentDatePlus1year_ACTIVE_withoutCreditCard() {
        Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);

        User user1 = TestUtil.createUser("user1Email");
        User user2 = TestUtil.createUser("user2Email");
        User user3 = TestUtil.createUser("user3Email");

        TestUtil.createUserOnSiteRightActive(user1, site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user2, site, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user3, site, SiteAccessLevel.ADMINISTRATOR);

        Assert.assertNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());

        new CreditCardNotificationMailSender(0, 0).run();

        Assert.assertEquals(0, mockMailSender.getMails().size());
    }


    @Test
    public void testRun_withExpirationDateEqualCurrentDatePlus15days_ACTIVE_withoutCreditCard() {
        Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);

        User user1 = TestUtil.createUser("user1Email");
        User user2 = TestUtil.createUser("user2Email");
        User user3 = TestUtil.createUser("user3Email");

        TestUtil.createUserOnSiteRightActive(user1, site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user2, site, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user3, site, SiteAccessLevel.ADMINISTRATOR);

        Assert.assertNull(site.getSitePaymentSettings().getCreditCard());
        Assert.assertEquals(SiteStatus.ACTIVE, new SiteManager(site).getSiteStatus());

        new CreditCardNotificationMailSender(0, 0).run();


        Assert.assertEquals(0, mockMailSender.getMails().size());
    }


    private final MockMailSender mockMailSender = (MockMailSender) ServiceLocator.getMailSender();
}
