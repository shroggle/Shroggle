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
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.config.Config;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.mail.MockMailSender;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class ChildSitesBillingInfoCheckerTest {


    @Test
    public void testExecute_withEndDateBeforeCurrentDate() throws Exception {
        final Config config = ServiceLocator.getConfigStorage().get();
        config.setSupportEmail("ourSupport@email.com");
        config.setApplicationName("testApplicationName");
        config.getBillingInfoProperties().setMembersipExpireNotifyTime(10 * 24 * 60);

        Site site = TestUtil.createSite();
        site.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_250MB_MONTHLY_FEE));
        site.setTitle("siteTitle");

        User user = TestUtil.createUser("userEmail");
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");

        ChildSiteSettings childSiteSettings1 = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        childSiteSettings1.setEndDate(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000L));

        Assert.assertEquals(0, mockMailSender.getMails().size());
        Assert.assertNotNull(site.getChildSiteSettings());
        Assert.assertEquals(1000.0, site.getSitePaymentSettings().getPrice());
        Assert.assertEquals(1, ServiceLocator.getPersistance().getChildSites().size());

        ChildSitesBillingInfoChecker.execute();

        Assert.assertNull(site.getChildSiteSettings());
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_250MB_MONTHLY_FEE).getPrice(), site.getSitePaymentSettings().getPrice());
        Assert.assertEquals(2, mockMailSender.getMails().size());
        Assert.assertEquals(user.getEmail(), mockMailSender.getMails().get(0).getTo());
        Assert.assertEquals(2, mockMailSender.getMails().size());
        Assert.assertEquals(2, mockMailSender.getMails().size());

        Assert.assertEquals(childSiteRegistration.getName() + " Membership Expiration Reminder.", mockMailSender.getMails().get(0).getSubject());
        String endDate = DateUtil.toMonthDayAndYear(childSiteSettings1.getEndDate());
        Assert.assertEquals(
                StringUtil.getEmptyOrString(user.getFirstName()) + " \n" + StringUtil.getEmptyOrString(user.getLastName()) + "\n" +
                        "Just a reminder that your membership with " + childSiteRegistration.getName() + " is due to expire on " + endDate + ".\n" +
                        "We know that you have probably put a lot of work into it, so we do not want to just take it down automatically.\n" +
                        "\n" +
                        "Therefor your site " + site.getTitle() + " will be allowed to continue, until such time as you choose to manually delete it.\n" +
                        "Your new monthly subscription fee will be: $" + new ChargeTypeManager(ChargeType.SITE_250MB_MONTHLY_FEE).getPrice() + "\n" +
                        "\n" +
                        "If you want to keep your site, you do not need to take any action.\n" +
                        "\n" +
                        "If you want to delete your site then please login at " + config.getApplicationUrl() + ", and from your dashboard page, select the delete option beneath your site name.\n" +
                        "\n" +
                        "Sincere Regards,\n" +
                        "\n" +
                        "name and " + config.getApplicationName() + ".com",
                mockMailSender.getMails().get(0).getText());


        Assert.assertEquals("Your " + childSiteRegistration.getName() + " membership has expired.", mockMailSender.getMails().get(1).getSubject());
        Assert.assertEquals(
                "Following the earlier email notification you were sent on " + DateUtil.toMonthDayAndYear(new Date()) + " \n" +
                        "your " + site.getTitle() + " web site is now independent of the " + childSiteRegistration.getName() + " web site.\n" +
                        "As a result prices have changed : New prices are:\n" +
                        "250mb - $" + new ChargeTypeManager(ChargeType.SITE_250MB_MONTHLY_FEE).getPrice() + "\n" +
                        "500mb - $" + new ChargeTypeManager(ChargeType.SITE_500MB_MONTHLY_FEE).getPrice() + "\n" +
                        "1gb - $" + new ChargeTypeManager(ChargeType.SITE_1GB_MONTHLY_FEE).getPrice() + "\n" +
                        "3gb - $" + new ChargeTypeManager(ChargeType.SITE_3GB_MONTHLY_FEE).getPrice() + "\n" +
                        "For more information please contact: " + config.getSupportEmail() + "\n" +
                        "or to make changes to your account please login to your account by going to: " + config.getApplicationName() + "\n" +
                        "\n" +
                        "This service is powered by " + config.getApplicationName() + " - a great web based tool for building sites and networks of sites.",
                mockMailSender.getMails().get(1).getText());
        Assert.assertEquals(0, ServiceLocator.getPersistance().getChildSites().size());
    }

    @Test
    public void testExecute_withEndDateAfterCurrentDate_withCanBePublishedMessageSent() throws Exception {
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setMembersipExpireNotifyTime(10 * 24 * 60);
        Site site = TestUtil.createSite();
        site.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_250MB_MONTHLY_FEE));
        site.setTitle("siteTitle");


        User user = TestUtil.createUser("userEmail");
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");

        ChildSiteSettings childSiteSettings1 = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        childSiteSettings1.setCanBePublishedMessageSent(true);
        childSiteSettings1.setEndDate(new Date(System.currentTimeMillis() + 12 * 24 * 60 * 60 * 1000L));

        Assert.assertEquals(0, mockMailSender.getMails().size());
        Assert.assertNotNull(site.getChildSiteSettings());
        Assert.assertEquals(1000.0, site.getSitePaymentSettings().getPrice());
        Assert.assertEquals(1, ServiceLocator.getPersistance().getChildSites().size());

        ChildSitesBillingInfoChecker.execute();


        Assert.assertNotNull(site.getChildSiteSettings());
        Assert.assertEquals(childSiteSettings1.getChildSiteSettingsId(), site.getChildSiteSettings().getChildSiteSettingsId());
        Assert.assertEquals(1000.0, site.getSitePaymentSettings().getPrice());
        Assert.assertEquals(0, mockMailSender.getMails().size());
        Assert.assertEquals(0, mockMailSender.getMails().size());
        Assert.assertEquals(0, mockMailSender.getMails().size());
        Assert.assertEquals(1, ServiceLocator.getPersistance().getChildSites().size());
    }


    @Test
    public void testExecute_withEndDateAfterCurrentDate_withoutCanBePublishedMessageSent() throws Exception {
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setMembersipExpireNotifyTime(10 * 24 * 60);
        Site site = TestUtil.createSite();
        site.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_250MB_MONTHLY_FEE));
        site.setTitle("siteTitle");


        User user = TestUtil.createUser("userEmail");
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");

        ChildSiteSettings childSiteSettings1 = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        childSiteSettings1.setCanBePublishedMessageSent(false);
        childSiteSettings1.setEndDate(new Date(System.currentTimeMillis() + 12 * 24 * 60 * 60 * 1000L));
        childSiteSettings1.setCreatedDate(new Date(100000000L));

        Assert.assertEquals(0, mockMailSender.getMails().size());
        Assert.assertNotNull(site.getChildSiteSettings());
        Assert.assertEquals(1000.0, site.getSitePaymentSettings().getPrice());
        Assert.assertEquals(1, ServiceLocator.getPersistance().getChildSites().size());
        Assert.assertFalse(childSiteSettings1.isCanBePublishedMessageSent());

        ChildSitesBillingInfoChecker.execute();


        Assert.assertNotNull(site.getChildSiteSettings());
        Assert.assertEquals(childSiteSettings1.getChildSiteSettingsId(), site.getChildSiteSettings().getChildSiteSettingsId());
        Assert.assertTrue(childSiteSettings1.isCanBePublishedMessageSent());
        Assert.assertEquals(1000.0, site.getSitePaymentSettings().getPrice());
        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals(1, mockMailSender.getMails().size());
        Assert.assertEquals(1, ServiceLocator.getPersistance().getChildSites().size());

        final Mail mail = mockMailSender.getMails().get(0);
        Assert.assertEquals("Your " + site.getTitle() + " site can be posted live now.", mail.getSubject());
        final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
        Assert.assertEquals("Please post your " + site.getTitle() + " site live now. \nAs of today your site can be published to the Internet.\nTo post your site live, please, login: http://" + configStorage.get().getApplicationUrl() + " \nPlease feel free to contact technical support directly with any questions, " + configStorage.get().getSupportEmail() + ".",
                mail.getText());

    }


    @Test
    public void testExecute_withEndDateAfterCurrentDate_withoutCanBePublishedMessageSent_withCreatedAfterStartDate() throws Exception {
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setMembersipExpireNotifyTime(10 * 24 * 60);
        Site site = TestUtil.createSite();
        site.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_250MB_MONTHLY_FEE));
        site.setTitle("siteTitle");


        User user = TestUtil.createUser("userEmail");
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");

        ChildSiteSettings childSiteSettings1 = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        childSiteSettings1.setCanBePublishedMessageSent(false);
        childSiteSettings1.setEndDate(new Date(System.currentTimeMillis() + 12 * 24 * 60 * 60 * 1000L));

        Assert.assertEquals(0, mockMailSender.getMails().size());
        Assert.assertNotNull(site.getChildSiteSettings());
        Assert.assertEquals(1000.0, site.getSitePaymentSettings().getPrice());
        Assert.assertEquals(1, ServiceLocator.getPersistance().getChildSites().size());
        Assert.assertFalse(childSiteSettings1.isCanBePublishedMessageSent());

        ChildSitesBillingInfoChecker.execute();


        Assert.assertNotNull(site.getChildSiteSettings());
        Assert.assertEquals(childSiteSettings1.getChildSiteSettingsId(), site.getChildSiteSettings().getChildSiteSettingsId());
        Assert.assertFalse(childSiteSettings1.isCanBePublishedMessageSent());
        Assert.assertEquals(1000.0, site.getSitePaymentSettings().getPrice());
        Assert.assertEquals(0, mockMailSender.getMails().size());
        Assert.assertEquals(1, ServiceLocator.getPersistance().getChildSites().size());
    }


    @Test
    public void testExecute_withExpirationDateEqualCurrentDatePlus9days() throws Exception {
        final Config config = ServiceLocator.getConfigStorage().get();
        config.setSupportEmail("ourSupport@email.com");
        config.setApplicationName("testApplicationName");

        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setMembersipExpireNotifyTime(10 * 24 * 60);
        Site site = TestUtil.createSite();
        site.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_250MB_MONTHLY_FEE));
        site.setTitle("siteTitle");

        User user = TestUtil.createUser("userEmail");
        user.setFirstName("userFirstName");
        user.setLastName("userLastName");
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        User user2 = TestUtil.createUser("userEmail2");
        user2.setFirstName("userFirstName2");
        user2.setLastName("userLastName2");
        TestUtil.createUserOnSiteRightActive(user2, site, SiteAccessLevel.ADMINISTRATOR);

        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");

        ChildSiteSettings childSiteSettings1 = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        childSiteSettings1.setEndDate(new Date(System.currentTimeMillis() + 9 * 24 * 60 * 60 * 1000L));
        childSiteSettings1.setCanBePublishedMessageSent(true);

        Assert.assertEquals(0, mockMailSender.getMails().size());
        Assert.assertNotNull(site.getChildSiteSettings());
        Assert.assertEquals(1000.0, site.getSitePaymentSettings().getPrice());
        Assert.assertEquals(1, ServiceLocator.getPersistance().getChildSites().size());


        ChildSitesBillingInfoChecker.execute();


        Assert.assertNotNull(site.getChildSiteSettings());
        Assert.assertEquals(1000.0, site.getSitePaymentSettings().getPrice());
        Assert.assertEquals(2, mockMailSender.getMails().size());
        final Mail mail = mockMailSender.getMails().get(0);
        Assert.assertEquals(user.getEmail(), mail.getTo());

        Assert.assertEquals(childSiteRegistration.getName() + " Membership Expiration Reminder.", mail.getSubject());

        Assert.assertEquals(
                user.getFirstName() + " " + user.getLastName() + "\n\n" +
                        "Just a reminder that your membership with " + childSiteRegistration.getName() + " is due to expire on " + DateUtil.toMonthDayAndYear(childSiteSettings1.getEndDate()) + ".\n" +
                        "We know that you have probably put a lot of work into it, so we do not want to just take it down automatically.\n" +
                        "\n" +
                        "Therefor your site " + site.getTitle() + " will be allowed to continue, until such time as you choose to manually delete it.\n" +
                        "Your new monthly subscription fee will be: $" + new ChargeTypeManager(ChargeType.SITE_250MB_MONTHLY_FEE).getPrice() + "\n" +
                        "\nIf you want to keep your site, you do not need to take any action.\n" +
                        "\n" +
                        "If you want to delete your site then please login at " + config.getApplicationUrl() + ", and from your dashboard page, select the delete option beneath your site name.\n" +
                        "\nSincere Regards,\n\n" +
                        childSiteRegistration.getName() + " and " + config.getApplicationName() + ".com",
                mail.getText());


        final Mail mail2 = mockMailSender.getMails().get(1);
        Assert.assertEquals(user2.getEmail(), mail2.getTo());

        Assert.assertEquals(childSiteRegistration.getName() + " Membership Expiration Reminder.", mail2.getSubject());

        Assert.assertEquals(
                user2.getFirstName() + " " + user2.getLastName() + "\n\n" +
                        "Just a reminder that your membership with " + childSiteRegistration.getName() + " is due to expire on " + DateUtil.toMonthDayAndYear(childSiteSettings1.getEndDate()) + ".\n" +
                        "We know that you have probably put a lot of work into it, so we do not want to just take it down automatically.\n" +
                        "\n" +
                        "Therefor your site " + site.getTitle() + " will be allowed to continue, until such time as you choose to manually delete it.\n" +
                        "Your new monthly subscription fee will be: $" + new ChargeTypeManager(ChargeType.SITE_250MB_MONTHLY_FEE).getPrice() + "\n" +
                        "\nIf you want to keep your site, you do not need to take any action.\n" +
                        "\n" +
                        "If you want to delete your site then please login at " + config.getApplicationUrl() + ", and from your dashboard page, select the delete option beneath your site name.\n" +
                        "\nSincere Regards,\n\n" +
                        childSiteRegistration.getName() + " and " + config.getApplicationName() + ".com",
                mail2.getText());

        Assert.assertEquals(1, ServiceLocator.getPersistance().getChildSites().size());
    }

    @Test
    public void reateInstance_CheckSiteStatusBeforePostLiveTrue() {
        final Config config = new Config();
        config.getBillingInfoProperties().setCheckSitesBillingInfo(true);
        final SitesBillingInfoChecker checker = SiteBillingInfoFactory.createInstance(config.getBillingInfoProperties());
        Assert.assertFalse(checker instanceof SitesBillingInfoLightChecker);
        Assert.assertTrue(checker instanceof SitesBillingInfoFullChecker);
    }


    @Test
    public void reateInstance_CheckSiteStatusBeforePostLiveFalse() {
        final Config config = new Config();
        config.getBillingInfoProperties().setCheckSitesBillingInfo(false);
        final SitesBillingInfoChecker checker = SiteBillingInfoFactory.createInstance(config.getBillingInfoProperties());
        Assert.assertFalse(checker instanceof SitesBillingInfoFullChecker);
        Assert.assertTrue(checker instanceof SitesBillingInfoLightChecker);
    }

    private final MockMailSender mockMailSender = (MockMailSender) ServiceLocator.getMailSender();
}
