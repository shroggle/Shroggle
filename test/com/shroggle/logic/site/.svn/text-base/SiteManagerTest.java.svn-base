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
package com.shroggle.logic.site;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.childSites.childSiteSettings.ChildSiteSettingsManager;
import com.shroggle.logic.site.billingInfo.ChargeTypeManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.account.dashboard.DashboardSiteType;
import com.shroggle.presentation.account.dashboard.siteInfo.DashboardSiteInfo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import com.shroggle.util.config.ConfigJavien;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.mail.MockMailSender;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class SiteManagerTest {

    @Test
    public void create() {
        new SiteManager(TestUtil.createSite());
    }

    @Test
    public void createWithNull() {
        Integer siteId = null;
        new SiteManager(siteId);
    }

    @Test
    public void optOutFromNetworkIfNotChild() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();

        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        childSiteSettings.setParentSite(TestUtil.createSite());
        site.setChildSiteSettings(childSiteSettings);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final FilledForm filledForm = new FilledForm();
        filledForm.setChildSiteSettingsId(childSiteSettings.getChildSiteSettingsId());
        persistance.putFilledForm(filledForm);
        childSiteSettings.setFilledFormId(filledForm.getFilledFormId());

        final SiteManager siteManager = new SiteManager(site);
        siteManager.optOutFromNetwork();
    }

    @Test
    public void getName() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        site.setTitle("f");
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final SiteManager siteManager = new SiteManager(site);

        Assert.assertEquals("f", siteManager.getName());
    }

    @Test
    public void getWithoutTitle() {
        final Site site = TestUtil.createSite();
        site.setTitle(null);
        final SiteManager siteManager = new SiteManager(site);

        Assert.assertNull(siteManager.getName());
    }

    @Test
    public void getPagesWithoutTheir() {
        final Site site = TestUtil.createSite();
        final SiteManager siteManager = new SiteManager(site);
        Assert.assertEquals(0, siteManager.getPages().size());
    }

    @Test
    public void getPages() {
        final Site site = TestUtil.createSite();
        final Page page1 = TestUtil.createPage(site);
        final SiteManager siteManager = new SiteManager(site);
        Assert.assertEquals(1, siteManager.getPages().size());
        Assert.assertEquals(page1.getPageId(), siteManager.getPages().get(0).getPageId());
    }

    @Test
    public void optOutFromNetwork() {
        final Site site = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();

        final FilledForm filledForm = new FilledForm();
        filledForm.setChildSiteSettingsId(childSiteSettings.getChildSiteSettingsId());
        persistance.putFilledForm(filledForm);
        childSiteSettings.setFilledFormId(filledForm.getFilledFormId());

        final Site parent = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(parent, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserAndUserOnSiteRight(parent, SiteAccessLevel.EDITOR);
        childSiteSettings.setParentSite(parent);
        site.setChildSiteSettings(childSiteSettings);

        final User user = TestUtil.createUserAndLogin();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        final User networkUser = TestUtil.createUser();
        TestUtil.createUserOnSiteRightActiveAdmin(networkUser, site).setFromNetwork(true);

        final SiteManager siteManager = new SiteManager(site);
        siteManager.optOutFromNetwork();

        Assert.assertNull(site.getChildSiteSettings());
        Assert.assertEquals(1, site.getUserOnSiteRights().size());
        Assert.assertEquals(1, mailSender.getMails().size());
        Assert.assertEquals(user, site.getUserOnSiteRights().get(0).getId().getUser());
    }

    @Test
    public void optOutFromNetworkWithNotAdmin() {
        final Site site = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        final Site parent = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(parent, SiteAccessLevel.EDITOR);
        childSiteSettings.setParentSite(parent);

        final FilledForm filledForm = new FilledForm();
        filledForm.setChildSiteSettingsId(childSiteSettings.getChildSiteSettingsId());
        persistance.putFilledForm(filledForm);
        childSiteSettings.setFilledFormId(filledForm.getFilledFormId());

        site.setChildSiteSettings(childSiteSettings);

        final SiteManager siteManager = new SiteManager(site);
        siteManager.optOutFromNetwork();

        Assert.assertNull(site.getChildSiteSettings());
        Assert.assertEquals(0, mailSender.getMails().size());
    }

    @Test
    public void optOutFromNetworkWithNotActiveAdministratiorRight() {
        final Site site = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        final Site parent = TestUtil.createSite();
        final User parentUser = TestUtil.createUser();
        TestUtil.createUserOnSiteRightActive(parentUser, parent, SiteAccessLevel.ADMINISTRATOR).setActive(false);
        childSiteSettings.setParentSite(parent);
        site.setChildSiteSettings(childSiteSettings);

        final FilledForm filledForm = new FilledForm();
        filledForm.setChildSiteSettingsId(childSiteSettings.getChildSiteSettingsId());
        persistance.putFilledForm(filledForm);
        childSiteSettings.setFilledFormId(filledForm.getFilledFormId());

        final SiteManager siteManager = new SiteManager(site);
        siteManager.optOutFromNetwork();

        Assert.assertNull(site.getChildSiteSettings());
        Assert.assertEquals(0, mailSender.getMails().size());
    }

    @Test
    public void optOutFromNetworkWithNotActiveAdministratior() {
        final Site site = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        final Site parent = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(parent, SiteAccessLevel.ADMINISTRATOR).setActiveted(null);
        childSiteSettings.setParentSite(parent);
        site.setChildSiteSettings(childSiteSettings);

        final FilledForm filledForm = new FilledForm();
        filledForm.setChildSiteSettingsId(childSiteSettings.getChildSiteSettingsId());
        persistance.putFilledForm(filledForm);
        childSiteSettings.setFilledFormId(filledForm.getFilledFormId());

        final SiteManager siteManager = new SiteManager(site);
        siteManager.optOutFromNetwork();

        Assert.assertNull(site.getChildSiteSettings());
        Assert.assertEquals(0, mailSender.getMails().size());
    }

    @Test
    public void optOutFromNetworkWithNotRegisteredAdministratior() {
        final Site site = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = new ChildSiteSettings();
        final Site parent = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(parent, SiteAccessLevel.ADMINISTRATOR).setRegistrationDate(null);
        childSiteSettings.setParentSite(parent);
        site.setChildSiteSettings(childSiteSettings);

        final FilledForm filledForm = new FilledForm();
        filledForm.setChildSiteSettingsId(childSiteSettings.getChildSiteSettingsId());
        persistance.putFilledForm(filledForm);
        childSiteSettings.setFilledFormId(filledForm.getFilledFormId());

        final SiteManager siteManager = new SiteManager(site);
        siteManager.optOutFromNetwork();

        Assert.assertNull(site.getChildSiteSettings());
        Assert.assertEquals(0, mailSender.getMails().size());
    }

    @Test
    public void disconnectFromNetwork_childSite() {
        final User user = TestUtil.createUserAndLogin();
        final User userForChildSite = TestUtil.createUser();

        final Site parentSite = TestUtil.createSite();

        final Site childSite = TestUtil.createChildSite();
        childSite.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_250MB_MONTHLY_FEE));

        final DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("", "", parentSite);
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(childSiteRegistration, parentSite, childSite);

        final FilledForm filledForm = new FilledForm();
        filledForm.setChildSiteSettingsId(childSiteSettings.getChildSiteSettingsId());
        persistance.putFilledForm(filledForm);
        childSiteSettings.setFilledFormId(filledForm.getFilledFormId());

        Assert.assertNotNull(parentSite.getChildSiteRegistrationsId());
        Assert.assertEquals(1, parentSite.getChildSiteRegistrationsId().size());
        Assert.assertEquals(childSiteRegistration.getFormId(), parentSite.getChildSiteRegistrationsId().get(0).intValue());

        Assert.assertNotNull(childSite.getChildSiteSettings());
        Assert.assertEquals(childSiteSettings, childSite.getChildSiteSettings());
        Assert.assertEquals(1000, childSite.getSitePaymentSettings().getPrice(), 0);
        Assert.assertEquals(childSiteSettings.getFilledFormId(), filledForm.getFilledFormId());

        TestUtil.createUserOnSiteRightActive(userForChildSite, childSite, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user, parentSite, SiteAccessLevel.ADMINISTRATOR);
        UserOnSiteRight right = TestUtil.createUserOnSiteRightActive(user, childSite, SiteAccessLevel.ADMINISTRATOR);
        right.setFromNetwork(true);

        Assert.assertEquals(2, childSite.getUserOnSiteRights().size());
        Assert.assertNotNull(persistance.getChildSiteSettingsById(childSiteSettings.getChildSiteSettingsId()));


        new SiteManager(childSite).disconnectFromNetwork();


        Assert.assertNull(filledForm.getChildSiteSettingsId());
        Assert.assertNull(persistance.getChildSiteSettingsById(childSiteSettings.getChildSiteSettingsId()));
        Assert.assertNull(childSite.getChildSiteSettings());
        Assert.assertEquals(1, childSite.getUserOnSiteRights().size());
        Assert.assertEquals(userForChildSite, childSite.getUserOnSiteRights().get(0).getId().getUser());
        Assert.assertEquals(new ChargeTypeManager(ChargeType.SITE_250MB_MONTHLY_FEE).getPrice(), childSite.getSitePaymentSettings().getPrice(), 0);
    }

    @Test
    public void getHisNetworkName() {
        final Site site = TestUtil.createSite();
        final DraftChildSiteRegistration registration = new DraftChildSiteRegistration();
        registration.setName("g");
        final ChildSiteSettings settings = new ChildSiteSettings();
        settings.setChildSiteRegistration(registration);
        site.setChildSiteSettings(settings);

        final SiteManager siteManager = new SiteManager(site);
        Assert.assertEquals("g", siteManager.getHisNetworkName());
    }

    @Test
    public void getHisNetworkNameNotForNetwork() {
        final Config config = ServiceLocator.getConfigStorage().get();
        config.setApplicationUrl("testApplicationUrl");
        config.setApplicationName("testApplicationName");


        final Site site = TestUtil.createSite();

        final SiteManager siteManager = new SiteManager(site);
        Assert.assertEquals(config.getApplicationName(), siteManager.getHisNetworkName());
    }

    @Test
    public void getHisNetworkNameWithoutRegistration() {
        final Config config = ServiceLocator.getConfigStorage().get();
        config.setSupportEmail("ourSupport@email.com");
        config.setApplicationName("testApplicationName");


        final Site site = TestUtil.createSite();
        site.setChildSiteSettings(new ChildSiteSettings());

        final SiteManager siteManager = new SiteManager(site);
        Assert.assertEquals(config.getApplicationName(), siteManager.getHisNetworkName());
    }

    @Test
    public void getHisNetworkUrl() {
        final Site parentSite = TestUtil.createSite();
        final Site childSite = TestUtil.createSite();
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(parentSite);
        TestUtil.createChildSiteSettings(registration, parentSite, childSite);
        parentSite.setCustomUrl("customUrl");

        final SiteManager siteManager = new SiteManager(childSite);
        Assert.assertEquals("http://customUrl", siteManager.getHisNetworkUrl());
    }

    @Test
    public void getHisNetworkUrlNotForNetwork() {
        final Config config = ServiceLocator.getConfigStorage().get();
        config.setApplicationUrl("testApplicationUrl");
        config.setApplicationName("testApplicationName");

        final Site site = TestUtil.createSite();

        final SiteManager siteManager = new SiteManager(site);
        Assert.assertEquals("http://" + config.getApplicationUrl(), siteManager.getHisNetworkUrl());
    }

    @Test
    public void getIncomeSettings_withIncomeSettings() {
        final Site site = TestUtil.createSite();
        IncomeSettings incomeSettings = TestUtil.createIncomeSettings(site, "paypal", 150);
        Assert.assertEquals(incomeSettings, site.getIncomeSettings());

        final SiteManager siteManager = new SiteManager(site);
        Assert.assertEquals(site.getIncomeSettings(), siteManager.getOrCreateIncomeSettings());
    }

    @Test
    public void getIncomeSettings_withoutIncomeSettings() {
        final Site site = TestUtil.createSite();
        Assert.assertNull(site.getIncomeSettings());
        final SiteManager siteManager = new SiteManager(site);
        IncomeSettings incomeSettings = siteManager.getOrCreateIncomeSettings();
        Assert.assertNotNull(incomeSettings);
        Assert.assertEquals("", incomeSettings.getPaypalAddress());
        Assert.assertEquals(0.0, incomeSettings.getSum(), 1);
    }

    @Test
    public void getAdminsEmails() {
        User user1 = TestUtil.createUser("email1");
        User user2 = TestUtil.createUser("email2");
        User user3 = TestUtil.createUser("email3");
        User user4 = TestUtil.createUser("email4");
        final Site site = TestUtil.createSite();

        TestUtil.createUserOnSiteRightActive(user1, site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user2, site, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user3, site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user4, site, SiteAccessLevel.EDITOR);

        final SiteManager siteManager = new SiteManager(site);

        List<String> adminEmails = siteManager.getAdminsEmails();
        Assert.assertNotNull(adminEmails);
        Assert.assertEquals(2, adminEmails.size());
        Assert.assertEquals(user1.getEmail(), adminEmails.get(0));
        Assert.assertEquals(user3.getEmail(), adminEmails.get(1));
    }

    @Test
    public void getAdminsEmails_withSiteEditors() {
        User user1 = TestUtil.createUser("email1");
        User user2 = TestUtil.createUser("email2");
        User user3 = TestUtil.createUser("email3");
        User user4 = TestUtil.createUser("email4");
        final Site site = TestUtil.createSite();

        TestUtil.createUserOnSiteRightActive(user1, site, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user2, site, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user3, site, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user4, site, SiteAccessLevel.EDITOR);

        final SiteManager siteManager = new SiteManager(site);

        List<String> adminEmails = siteManager.getAdminsEmails();
        Assert.assertNotNull(adminEmails);
        Assert.assertEquals(0, adminEmails.size());
    }

    @Test
    public void getAdminsEmails_withSiteAdmins() {
        User user1 = TestUtil.createUser("email1");
        User user2 = TestUtil.createUser("email2");
        User user3 = TestUtil.createUser("email3");
        User user4 = TestUtil.createUser("email4");
        final Site site = TestUtil.createSite();

        TestUtil.createUserOnSiteRightActive(user1, site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user2, site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user3, site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user4, site, SiteAccessLevel.ADMINISTRATOR);

        final SiteManager siteManager = new SiteManager(site);

        List<String> adminEmails = siteManager.getAdminsEmails();
        Assert.assertNotNull(adminEmails);
        Assert.assertEquals(4, adminEmails.size());
        Assert.assertEquals(user1.getEmail(), adminEmails.get(0));
        Assert.assertEquals(user2.getEmail(), adminEmails.get(1));
        Assert.assertEquals(user3.getEmail(), adminEmails.get(2));
        Assert.assertEquals(user4.getEmail(), adminEmails.get(3));
    }

    @Test
    public void getAdminsEmails_withoutUsers() {
        final Site site = TestUtil.createSite();

        final SiteManager siteManager = new SiteManager(site);

        List<String> adminEmails = siteManager.getAdminsEmails();
        Assert.assertNotNull(adminEmails);
        Assert.assertEquals(0, adminEmails.size());
    }

    //----------------------

    @Test
    public void getAdmins() {
        User user1 = TestUtil.createUser("email1");
        User user2 = TestUtil.createUser("email2");
        User user3 = TestUtil.createUser("email3");
        User user4 = TestUtil.createUser("email4");
        final Site site = TestUtil.createSite();

        TestUtil.createUserOnSiteRightActive(user1, site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user2, site, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user3, site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user4, site, SiteAccessLevel.EDITOR);

        final SiteManager siteManager = new SiteManager(site);

        List<User> admins = siteManager.getAdmins();
        Assert.assertNotNull(admins);
        Assert.assertEquals(2, admins.size());
        Assert.assertEquals(user1.getEmail(), admins.get(0).getEmail());
        Assert.assertEquals(user3.getEmail(), admins.get(1).getEmail());
    }

    @Test
    public void getAdmins_withSiteEditors() {
        User user1 = TestUtil.createUser("email1");
        User user2 = TestUtil.createUser("email2");
        User user3 = TestUtil.createUser("email3");
        User user4 = TestUtil.createUser("email4");
        final Site site = TestUtil.createSite();

        TestUtil.createUserOnSiteRightActive(user1, site, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user2, site, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user3, site, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user4, site, SiteAccessLevel.EDITOR);

        final SiteManager siteManager = new SiteManager(site);

        List<User> admins = siteManager.getAdmins();
        Assert.assertNotNull(admins);
        Assert.assertEquals(0, admins.size());
    }

    @Test
    public void getAdmins_withSiteAdmins() {
        User user1 = TestUtil.createUser("email1");
        User user2 = TestUtil.createUser("email2");
        User user3 = TestUtil.createUser("email3");
        User user4 = TestUtil.createUser("email4");
        final Site site = TestUtil.createSite();

        TestUtil.createUserOnSiteRightActive(user1, site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user2, site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user3, site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user4, site, SiteAccessLevel.ADMINISTRATOR);

        final SiteManager siteManager = new SiteManager(site);

        List<User> admins = siteManager.getAdmins();
        Assert.assertNotNull(admins);
        Assert.assertEquals(4, admins.size());
        Assert.assertEquals(user1.getEmail(), admins.get(0).getEmail());
        Assert.assertEquals(user2.getEmail(), admins.get(1).getEmail());
        Assert.assertEquals(user3.getEmail(), admins.get(2).getEmail());
        Assert.assertEquals(user4.getEmail(), admins.get(3).getEmail());
    }

    @Test
    public void getAdmins_withoutUsers() {
        final Site site = TestUtil.createSite();

        final SiteManager siteManager = new SiteManager(site);

        List<User> admins = siteManager.getAdmins();
        Assert.assertNotNull(admins);
        Assert.assertEquals(0, admins.size());
    }

    @Test
    public void isPendingChildSite_PENDING_notNullSettings() {
        final Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.PENDING);
        site.setChildSiteSettings(new ChildSiteSettings());

        final SiteManager siteManager = new SiteManager(site);

        Assert.assertTrue(siteManager.isPendingChildSite());
    }

    @Test
    public void isPendingChildSite_PENDING_nullSettings() {
        final Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.PENDING);
        site.setChildSiteSettings(null);

        final SiteManager siteManager = new SiteManager(site);

        Assert.assertFalse(siteManager.isPendingChildSite());
    }

    @Test
    public void isPendingChildSite_ACTIVE_nullSettings() {
        final Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        site.setChildSiteSettings(null);

        final SiteManager siteManager = new SiteManager(site);

        Assert.assertFalse(siteManager.isPendingChildSite());
    }

    @Test
    public void isPendingChildSite_ACTIVE_notNullSettings() {
        final Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        site.setChildSiteSettings(new ChildSiteSettings());

        final SiteManager siteManager = new SiteManager(site);

        Assert.assertFalse(siteManager.isPendingChildSite());
    }

    @Test
    public void checkChildSiteStartDate() {
        Persistance persistance = ServiceLocator.getPersistance();
        final Config config = ServiceLocator.getConfigStorage().get();
        final ConfigJavien javien = config.getJavien();
        config.getBillingInfoProperties().setCheckSitesBillingInfo(true);
        config.setJavien(javien);
        Site parentSite = new Site();
        parentSite.setSubDomain(" ");
        parentSite.getSitePaymentSettings().setExpirationDate(new Date(System.currentTimeMillis() + (60l * 60l * 24l * 30l * 1000l)));
        parentSite.setCreationDate(new Date());
        new SiteManager(parentSite).setSiteStatus(SiteStatus.ACTIVE);
        persistance.putSite(parentSite);

        DraftChildSiteRegistration registration = new DraftChildSiteRegistration();
        registration.setName("name");
        persistance.putItem(registration);
        parentSite.addChildSiteRegistrationId(registration.getFormId());

        Site childSiteCanBePublished = new Site();
        childSiteCanBePublished.setSubDomain(" ");
        childSiteCanBePublished.setCreationDate(new Date());
        childSiteCanBePublished.getSitePaymentSettings().setExpirationDate(new Date(System.currentTimeMillis() + (60l * 60l * 24l * 300l * 1000l)));
        new SiteManager(childSiteCanBePublished).setSiteStatus(SiteStatus.ACTIVE);
        persistance.putSite(childSiteCanBePublished);

        ChildSiteSettings settingsCanBePublished = new ChildSiteSettings();
        settingsCanBePublished.setCreatedDate(new Date());
        settingsCanBePublished.setCanBePublishedMessageSent(false);
        settingsCanBePublished.setEndDate(new Date(System.currentTimeMillis() + (60l * 60l * 24l * 300l * 1000l)));
        settingsCanBePublished.setStartDate(new Date(System.currentTimeMillis() - (60l * 60l * 24l * 300l * 1000l)));
        settingsCanBePublished.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
        settingsCanBePublished.setParentSite(parentSite);
        settingsCanBePublished.setChildSiteRegistration(registration);
        persistance.putChildSiteSettings(settingsCanBePublished);
        childSiteCanBePublished.setChildSiteSettings(settingsCanBePublished);

        Site childSiteCantBePublished = new Site();
        childSiteCantBePublished.setSubDomain(" ");
        childSiteCantBePublished.setCreationDate(new Date());
        childSiteCantBePublished.getSitePaymentSettings().setExpirationDate(new Date(System.currentTimeMillis() + (60l * 60l * 24l * 300l * 1000l)));
        new SiteManager(childSiteCantBePublished).setSiteStatus(SiteStatus.ACTIVE);
        persistance.putSite(childSiteCantBePublished);

        ChildSiteSettings settingsCantBePublished = new ChildSiteSettings();
        settingsCantBePublished.setCreatedDate(new Date());
        settingsCantBePublished.setCanBePublishedMessageSent(false);
        settingsCantBePublished.setEndDate(new Date(System.currentTimeMillis() + (60l * 60l * 24l * 300l * 1000l)));
        settingsCantBePublished.setStartDate(new Date(System.currentTimeMillis() + (60l * 60l * 24l * 30l * 1000l)));
        settingsCantBePublished.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
        settingsCantBePublished.setParentSite(parentSite);
        settingsCantBePublished.setChildSiteRegistration(registration);
        persistance.putChildSiteSettings(settingsCantBePublished);
        childSiteCantBePublished.setChildSiteSettings(settingsCantBePublished);

        PublishingInfoResponse response = new SiteManager(childSiteCanBePublished).checkChildSiteStartDate();
        Assert.assertTrue(response.isCanBePublished());
        Assert.assertFalse(response.isCanBePublishedMessageSent());

        response = new SiteManager(parentSite).checkChildSiteStartDate();
        Assert.assertTrue(response.isCanBePublished());
        Assert.assertTrue(response.isCanBePublishedMessageSent());


        response = new SiteManager(childSiteCantBePublished).checkChildSiteStartDate();
        Assert.assertFalse(response.isCanBePublished());
        Assert.assertFalse(response.isCanBePublishedMessageSent());


        response = new ChildSiteSettingsManager(settingsCanBePublished).getPublishingInfo();
        Assert.assertTrue(response.isCanBePublished());
        Assert.assertFalse(response.isCanBePublishedMessageSent());


        response = new ChildSiteSettingsManager(settingsCantBePublished).getPublishingInfo();
        Assert.assertFalse(response.isCanBePublished());
        Assert.assertFalse(response.isCanBePublishedMessageSent());
    }


    @Test
    public void testAddDifferenceToIncomeSettingsSITE_250MB_MONTHLY_FEE_parentSitePriceLowerThanOurPrice() throws Exception {
        Site site = TestUtil.createSite();
        SiteManager siteManager = new SiteManager(site);
        Site childSite = TestUtil.createSite();
        siteManager.addDifferenceToIncomeSettings(10, ChargeType.SITE_250MB_MONTHLY_FEE, "");
        Assert.assertEquals(site.getIncomeSettings().getSum(), 0, 1);
        Assert.assertNull(site.getIncomeSettings().getPaymentDetails());
    }

    @Test
    public void testAddDifferenceToIncomeSettingsSITE_250MB_MONTHLY_FEE_parentSitePriceBiggerThanOurPrice() throws Exception {
        Site site = TestUtil.createSite();
        SiteManager siteManager = new SiteManager(site);
        site.setTitle("Parent Site Title");
        Site childSite = TestUtil.createSite();
        childSite.setTitle("Child Site Title");
        final String infoAboutChildSite = "child site (Child Site Title, siteId = " + childSite.getSiteId() + ")";
        siteManager.addDifferenceToIncomeSettings(40, ChargeType.SITE_250MB_MONTHLY_FEE, infoAboutChildSite);//SITE_250MB_MONTHLY_FEE == 29.99
        Assert.assertEquals(site.getIncomeSettings().getSum(), 10.1, 1);

        final String paymentDetails = "Sending money to parent site (Parent Site Title, siteId = " +
                site.getSiteId() + ") " + "owner from " + infoAboutChildSite + ". Sum = 10.";
        Assert.assertTrue(site.getIncomeSettings().getPaymentDetails().startsWith(paymentDetails));
    }

    @Test
    public void testAddDifferenceToIncomeSettingsSITE_500MB_MONTHLY_FEE_parentSitePriceLowerThanOurPrice() throws Exception {
        Site site = TestUtil.createSite();
        SiteManager siteManager = new SiteManager(site);
        Site childSite = TestUtil.createSite();
        siteManager.addDifferenceToIncomeSettings(10, ChargeType.SITE_500MB_MONTHLY_FEE, "");
        Assert.assertEquals(site.getIncomeSettings().getSum(), 0, 1);
        Assert.assertNull(site.getIncomeSettings().getPaymentDetails());
    }

    @Test
    public void testAddDifferenceToIncomeSettingsSITE_500MB_MONTHLY_FEE_parentSitePriceBiggerThanOurPrice() throws Exception {
        Site site = TestUtil.createSite();
        SiteManager siteManager = new SiteManager(site);
        site.setTitle("Parent Site Title");
        Site childSite = TestUtil.createSite();
        childSite.setTitle("Child Site Title");
        final String infoAboutChildSite = "child site (Child Site Title, siteId = " + childSite.getSiteId() + ")";
        siteManager.addDifferenceToIncomeSettings(30, ChargeType.SITE_500MB_MONTHLY_FEE, infoAboutChildSite);//SITE_500MB_MONTHLY_FEE == 29.99
        Assert.assertEquals(site.getIncomeSettings().getSum(), 0.1, 1);

        final String paymentDetails = "Sending money to parent site (Parent Site Title, siteId = " +
                site.getSiteId() + ") " + "owner from " + infoAboutChildSite + ". Sum = 0.";
        Assert.assertTrue(site.getIncomeSettings().getPaymentDetails().startsWith(paymentDetails));
    }

    @Test
    public void testAddDifferenceToIncomeSettingsSITE_1GB_MONTHLY_FEE_parentSitePriceLowerThanOurPrice() throws Exception {
        Site site = TestUtil.createSite();
        site.setTitle("Parent Site Title");
        Site childSite = TestUtil.createSite();
        childSite.setTitle("Child Site Title");
        SiteManager siteManager = new SiteManager(site);
        siteManager.addDifferenceToIncomeSettings(10, ChargeType.SITE_1GB_MONTHLY_FEE, "");
        Assert.assertEquals(site.getIncomeSettings().getSum(), 0, 1);

        Assert.assertNull(site.getIncomeSettings().getPaymentDetails());
    }

    @Test
    public void testAddDifferenceToIncomeSettingsSITE_1GB_MONTHLY_FEE_parentSitePriceBiggerThanOurPrice() throws Exception {
        Site site = TestUtil.createSite();
        SiteManager siteManager = new SiteManager(site);
        site.setTitle("Parent Site Title");
        Site childSite = TestUtil.createSite();
        childSite.setTitle("Child Site Title");
        final String infoAboutChildSite = "child site (Child Site Title, siteId = " + childSite.getSiteId() + ")";
        siteManager.addDifferenceToIncomeSettings(30, ChargeType.SITE_1GB_MONTHLY_FEE, infoAboutChildSite);//SITE_1GB_MONTHLY_FEE == 29.99
        Assert.assertEquals(site.getIncomeSettings().getSum(), 0.1, 1);

        final String paymentDetails = "Sending money to parent site (Parent Site Title, siteId = " +
                site.getSiteId() + ") " + "owner from " + infoAboutChildSite + ". Sum = 0.";
        Assert.assertTrue(site.getIncomeSettings().getPaymentDetails().startsWith(paymentDetails));
    }

    @Test
    public void testAddDifferenceToIncomeSettingsSITE_3GB_MONTHLY_FEE_parentSitePriceLowerThanOurPrice() throws Exception {
        Site site = TestUtil.createSite();
        SiteManager siteManager = new SiteManager(site);
        Site childSite = TestUtil.createSite();
        siteManager.addDifferenceToIncomeSettings(10, ChargeType.SITE_3GB_MONTHLY_FEE, "");
        Assert.assertEquals(site.getIncomeSettings().getSum(), 0, 1);
        Assert.assertNull(site.getIncomeSettings().getPaymentDetails());
    }

    @Test
    public void testAddDifferenceToIncomeSettingsSITE_3GB_MONTHLY_FEE_parentSitePriceBiggerThanOurPrice() throws Exception {
        Site site = TestUtil.createSite();
        SiteManager siteManager = new SiteManager(site);
        site.setTitle("Parent Site Title");
        Site childSite = TestUtil.createSite();
        childSite.setTitle("Child Site Title");
        final String infoAboutChildSite = "child site (Child Site Title, siteId = " + childSite.getSiteId() + ")";
        siteManager.addDifferenceToIncomeSettings(30, ChargeType.SITE_3GB_MONTHLY_FEE, infoAboutChildSite);//SITE_3GB_MONTHLY_FEE == 29.99
        Assert.assertEquals(site.getIncomeSettings().getSum(), 0.1, 1);

        final String paymentDetails = "Sending money to parent site (Parent Site Title, siteId = " +
                site.getSiteId() + ") " + "owner from " + infoAboutChildSite + ". Sum = 0.";
        Assert.assertTrue(site.getIncomeSettings().getPaymentDetails().startsWith(paymentDetails));
    }

    @Test
    public void testAddDifferenceToIncomeSettingsSITE_MONTHLY_FEE_parentSitePriceLowerThanOurPrice() throws Exception {
        Site site = TestUtil.createSite();
        SiteManager siteManager = new SiteManager(site);
        Site childSite = TestUtil.createSite();
        siteManager.addDifferenceToIncomeSettings(10, ChargeType.SITE_MONTHLY_FEE, "");
        Assert.assertEquals(site.getIncomeSettings().getSum(), 0, 1);
        Assert.assertNull(site.getIncomeSettings().getPaymentDetails());
    }

    @Test
    public void testAddDifferenceToIncomeSettingsSITE_MONTHLY_FEE_parentSitePriceBiggerThanOurPrice() throws Exception {
        Site site = TestUtil.createSite("Parent Site Title", "");
        SiteManager siteManager = new SiteManager(site);
        Site childSite = TestUtil.createSite();
        final String infoAboutChildSite = "child site (Child Site Title, siteId = " + childSite.getSiteId() + ")";
        siteManager.addDifferenceToIncomeSettings(30, ChargeType.SITE_MONTHLY_FEE, infoAboutChildSite);//SITE_MONTHLY_FEE == 29.99
        Assert.assertEquals(site.getIncomeSettings().getSum(), 0.1, 1);

        final String paymentDetails = "Sending money to parent site (Parent Site Title, siteId = " +
                site.getSiteId() + ") " + "owner from " + infoAboutChildSite + ". Sum = 0.";
        Assert.assertTrue(site.getIncomeSettings().getPaymentDetails().startsWith(paymentDetails));
    }


    @Test
    public void testAddDifferenceToIncomeSettingsSITE_ANNUAL_FEE_parentSitePriceLowerThanOurPrice() throws Exception {
        Site site = TestUtil.createSite();
        SiteManager siteManager = new SiteManager(site);
        Site childSite = TestUtil.createSite();
        siteManager.addDifferenceToIncomeSettings(10, ChargeType.SITE_ANNUAL_FEE, "");
        Assert.assertEquals(site.getIncomeSettings().getSum(), 0, 1);
        Assert.assertNull(site.getIncomeSettings().getPaymentDetails());
    }

    @Test
    public void testAddDifferenceToIncomeSettingsSITE_ANNUAL_FEE_parentSitePriceBiggerThanOurPrice() throws Exception {
        Site site = TestUtil.createSite();
        SiteManager siteManager = new SiteManager(site);
        site.setTitle("Parent Site Title");
        Site childSite = TestUtil.createSite();
        childSite.setTitle("Child Site Title");
        final String infoAboutChildSite = "child site (Child Site Title, siteId = " + childSite.getSiteId() + ")";
        siteManager.addDifferenceToIncomeSettings(500, ChargeType.SITE_ANNUAL_FEE, infoAboutChildSite);//SITE_ANNUAL_FEE == 300.00
        Assert.assertEquals(site.getIncomeSettings().getSum(), 200.0, 1);

        final String paymentDetails = "Sending money to parent site (Parent Site Title, siteId = " +
                site.getSiteId() + ") " + "owner from " + infoAboutChildSite + ". Sum = 200.";
        Assert.assertTrue(site.getIncomeSettings().getPaymentDetails().startsWith(paymentDetails));
    }


    @Test
    public void testAddDifferenceToIncomeSettingsSITE_ONE_TIME_FEE_parentSitePriceLowerThanOurPrice() throws Exception {
        Site site = TestUtil.createSite();
        SiteManager siteManager = new SiteManager(site);
        site.setTitle("Parent Site Title");
        Site childSite = TestUtil.createSite();
        childSite.setTitle("Child Site Title");
        final String infoAboutChildSite = "child site (Child Site Title, siteId = " + childSite.getSiteId() + ")";
        siteManager.addDifferenceToIncomeSettings(10, ChargeType.SITE_ONE_TIME_FEE, infoAboutChildSite);
        Assert.assertEquals(site.getIncomeSettings().getSum(), 10, 1);

        final String paymentDetails = "Sending money to parent site (Parent Site Title, siteId = " +
                site.getSiteId() + ") " + "owner from " + infoAboutChildSite + ". Sum = 10.";
        Assert.assertTrue(site.getIncomeSettings().getPaymentDetails().startsWith(paymentDetails));
    }

    @Test
    public void testAddDifferenceToIncomeSettingsSITE_ONE_TIME_FEE_parentSitePriceBiggerThanOurPrice() throws Exception {
        Site site = TestUtil.createSite();
        SiteManager siteManager = new SiteManager(site);
        site.setTitle("Parent Site Title");
        Site childSite = TestUtil.createSite();
        childSite.setTitle("Child Site Title");
        final String infoAboutChildSite = "child site (Child Site Title, siteId = " + childSite.getSiteId() + ")";
        siteManager.addDifferenceToIncomeSettings(500, ChargeType.SITE_ONE_TIME_FEE, infoAboutChildSite);//SITE_ONE_TIME_FEE == 0.0
        Assert.assertEquals(site.getIncomeSettings().getSum(), 500.0, 1);

        final String paymentDetails = "Sending money to parent site (Parent Site Title, siteId = " +
                site.getSiteId() + ") " + "owner from " + infoAboutChildSite + ". Sum = 500.";
        Assert.assertTrue(site.getIncomeSettings().getPaymentDetails().startsWith(paymentDetails));
    }

    @Test
    public void testAddDifferenceToIncomeSettingsSITE_ONE_TIME_FEE_parentSitePriceBiggerThanOurPrice_withSavedTextInIncomeSettings() throws Exception {
        final Site site = TestUtil.createSite();
        final IncomeSettings incomeSettings = new IncomeSettings();
        incomeSettings.setPaymentDetails("Old income settings text");
        site.setIncomeSettings(incomeSettings);
        final SiteManager siteManager = new SiteManager(site);
        site.setTitle("Parent Site Title");
        Site childSite = TestUtil.createSite();
        childSite.setTitle("Child Site Title");
        final String infoAboutChildSite = "child site (Child Site Title, siteId = " + childSite.getSiteId() + ")";
        siteManager.addDifferenceToIncomeSettings(500, ChargeType.SITE_ONE_TIME_FEE, infoAboutChildSite);//SITE_ONE_TIME_FEE == 0.0
        Assert.assertEquals(site.getIncomeSettings().getSum(), 500.0, 1);

        final String paymentDetails = "Old income settings text;Sending money to parent site (Parent Site Title, siteId = " +
                site.getSiteId() + ") " + "owner from " + infoAboutChildSite + ". Sum = 500.";
        Assert.assertTrue(site.getIncomeSettings().getPaymentDetails().startsWith(paymentDetails));
    }

    @Test
    public void testClearIncomeSettings_withIncomeSettings() {
        final Site site = TestUtil.createSite();
        final IncomeSettings incomeSettings = new IncomeSettings();
        incomeSettings.setPaymentDetails("Old payment details.");
        incomeSettings.setSum(123);
        site.setIncomeSettings(incomeSettings);
        final SiteManager siteManager = new SiteManager(site);

        siteManager.clearIncomeSettings();

        Assert.assertNotNull(site.getIncomeSettings());
        Assert.assertNull(site.getIncomeSettings().getPaymentDetails());
        Assert.assertEquals(0, site.getIncomeSettings().getSum(), 0);
    }

    @Test
    public void testClearIncomeSettings_withoutIncomeSettings() {
        final Site site = TestUtil.createSite();
        site.setIncomeSettings(null);
        final SiteManager siteManager = new SiteManager(site);
        Assert.assertNull(site.getIncomeSettings());

        siteManager.clearIncomeSettings();

        Assert.assertNull(site.getIncomeSettings());
    }

    @Test
    public void testGetLastUpdateDate() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createPageVersion(TestUtil.createPage(site));
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        TestUtil.createPageVersion(TestUtil.createPage(site));
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        final PageManager pageManager3 = TestUtil.createPageVersion(TestUtil.createPage(site));

        Assert.assertEquals(pageManager3.getCreationDate(), new SiteManager(site).getLastUpdatedDate());
    }

    @Test
    public void testGetLastUpdatedDate_withoutPages() throws Exception {
        final Site site = TestUtil.createSite();
        junit.framework.Assert.assertEquals(site.getCreationDate().getTime() / 1000, new SiteManager(site).getLastUpdatedDate().getTime() / 1000);
    }

    @Test
    public void testIsActive_ACTIVE() throws Exception {
        final Site site = TestUtil.createSite();
        final SiteManager siteManager = new SiteManager(site);
        siteManager.setSiteStatus(SiteStatus.ACTIVE);
        Assert.assertTrue(siteManager.isActive());
    }

    @Test
    public void testIsActive_PENDING() throws Exception {
        final Site site = TestUtil.createSite();
        final SiteManager siteManager = new SiteManager(site);
        siteManager.setSiteStatus(SiteStatus.PENDING);
        Assert.assertFalse(siteManager.isActive());
    }

    @Test
    public void testIsActive_SUSPENDED() throws Exception {
        final Site site = TestUtil.createSite();
        final SiteManager siteManager = new SiteManager(site);
        siteManager.setSiteStatus(SiteStatus.SUSPENDED);
        Assert.assertFalse(siteManager.isActive());
    }

    @Test
    public void testIsInactive_ACTIVE() throws Exception {
        final Site site = TestUtil.createSite();
        final SiteManager siteManager = new SiteManager(site);
        siteManager.setSiteStatus(SiteStatus.ACTIVE);
        Assert.assertFalse(siteManager.isInactive());
    }

    @Test
    public void testIsInactive_PENDING() throws Exception {
        final Site site = TestUtil.createSite();
        final SiteManager siteManager = new SiteManager(site);
        siteManager.setSiteStatus(SiteStatus.PENDING);
        Assert.assertTrue(siteManager.isInactive());
    }

    @Test
    public void testIsInactive_SUSPENDED() throws Exception {
        final Site site = TestUtil.createSite();
        final SiteManager siteManager = new SiteManager(site);
        siteManager.setSiteStatus(SiteStatus.SUSPENDED);
        Assert.assertTrue(siteManager.isInactive());
    }

    @Test
    public void testPublishBlueprint() throws Exception {
        final User admin1 = TestUtil.createUser("email1@email1.com");
        final User admin2 = TestUtil.createUser("email2@email2.com");
        config.setAdminEmails(Arrays.asList(admin1.getEmail(), admin2.getEmail()));
        config.setApplicationUrl("http://www.web-deva.com");
        final User user = TestUtil.createUserAndLogin();
        user.setFirstName("Balakirev");
        user.setLastName("Anatoliy");
        user.setEmail("email@email.email");
        final Site site = TestUtil.createBlueprint();
        site.setTitle("Blueprint");
        site.getPublicBlueprintsSettings().setActivated(null);
        site.getPublicBlueprintsSettings().setPublished(null);
        site.getPublicBlueprintsSettings().setDescription(null);
        final Page page1 = TestUtil.createPage(site);
        final Page page2 = TestUtil.createPage(site);

        final Map<Integer, Integer> screenShotIds = new HashMap<Integer, Integer>();
        screenShotIds.put(page1.getPageId(), 1234);
        screenShotIds.put(page2.getPageId(), 5678);

        final SiteManager siteManager = new SiteManager(site);
        siteManager.publishBlueprint("description", screenShotIds, BlueprintCategory.ALTERNATIVE_THERAPISTS);

        Assert.assertNotNull(siteManager.getPublicBlueprintsSettings().getPublished());
        Assert.assertEquals("description", siteManager.getPublicBlueprintsSettings().getDescription());
        Assert.assertNull(siteManager.getPublicBlueprintsSettings().getActivated());
        Assert.assertEquals(BlueprintCategory.ALTERNATIVE_THERAPISTS, siteManager.getPublicBlueprintsSettings().getBlueprintCategory());
        Assert.assertEquals(new PageManager(page1).getScreenShotId().intValue(), 1234);
        Assert.assertEquals(new PageManager(page2).getScreenShotId().intValue(), 5678);

        final MockMailSender mailSender = (MockMailSender) ServiceLocator.getMailSender();
        Assert.assertEquals(2, mailSender.getMails().size());

        Assert.assertEquals("Review blueprint email request", mailSender.getMails().get(0).getSubject());
        Assert.assertEquals("Review blueprint email request", mailSender.getMails().get(1).getSubject());

        Assert.assertEquals("A Blueprint site has been submitted for publication.\n" +
                "Name: Blueprint;\n" +
                "Description: description;\n" +
                "Created by: Balakirev Anatoliy;\n" +
                "Business Categories: Alternative Therapists.\n" +
                "\n" +
                "To review this blueprint, please click here:\n" +
                "http://www.web-deva.com/account/dashboard.action?selectedSite=" + DashboardSiteInfo.newInstance(site, DashboardSiteType.PUBLISHED_BLUEPRINT).hashCode() + "\n" +
                "To contact the creator directly: email@email.email.", mailSender.getMails().get(0).getText());

        Assert.assertEquals("A Blueprint site has been submitted for publication.\n" +
                "Name: Blueprint;\n" +
                "Description: description;\n" +
                "Created by: Balakirev Anatoliy;\n" +
                "Business Categories: Alternative Therapists.\n" +
                "\n" +
                "To review this blueprint, please click here:\n" +
                "http://www.web-deva.com/account/dashboard.action?selectedSite=" + DashboardSiteInfo.newInstance(site, DashboardSiteType.PUBLISHED_BLUEPRINT).hashCode() + "\n" +
                "To contact the creator directly: email@email.email.", mailSender.getMails().get(1).getText());

        final UserOnSiteRight userOnSiteRight1 = new UserRightManager(admin1).toSite(site);
        final UserOnSiteRight userOnSiteRight2 = new UserRightManager(admin2).toSite(site);
        Assert.assertNotNull(userOnSiteRight1);
        Assert.assertNotNull(userOnSiteRight2);
        Assert.assertEquals(SiteAccessLevel.ADMINISTRATOR, userOnSiteRight1.getSiteAccessType());
        Assert.assertEquals(SiteAccessLevel.ADMINISTRATOR, userOnSiteRight2.getSiteAccessType());
        Assert.assertEquals(true, userOnSiteRight1.isActive());
        Assert.assertEquals(true, userOnSiteRight2.isActive());
    }

    @Test
    public void activateSite() {
        final User admin1 = TestUtil.createUser("email1@email1.com");
        final User admin2 = TestUtil.createUser("email2@email2.com");
        config.setAdminEmails(Arrays.asList(admin1.getEmail(), admin2.getEmail()));
        config.setApplicationUrl("http://www.web-deva.com");

        final User user = TestUtil.createUserAndLogin();
        user.setFirstName("Balakirev");
        user.setLastName("Anatoliy");
        user.setEmail("email@email.email");

        final Site site = TestUtil.createSite();
        site.setTitle("Blueprint");

        final CustomForm customForm = TestUtil.createCustomForm(site);
        site.setDefaultFormId(customForm.getId());

        final Page page1 = TestUtil.createPage(site);
        final Page page2 = TestUtil.createPage(site);
        TestUtil.createPage(site);
        new PageManager(page1).publish();
        new PageManager(page2).publish();

        final Map<Integer, Integer> screenShotIds = new HashMap<Integer, Integer>();
        screenShotIds.put(page1.getPageId(), 1234);
        screenShotIds.put(page2.getPageId(), 5678);

        final SiteManager siteManager = new SiteManager(site);

        TestUtil.createUserOnSiteRightActiveAdmin(admin1, site);
        TestUtil.createUserOnSiteRightActiveAdmin(admin2, site);

        final SiteManager blueprintManager = siteManager.activateBlueprint(
                "description", screenShotIds, BlueprintCategory.ALTERNATIVE_THERAPISTS);

        Assert.assertNotNull(blueprintManager.getSite().getMenu());

        Assert.assertNull(
                "This site is activated, so 'published' should be null.",
                blueprintManager.getPublicBlueprintsSettings().getPublished());

        Assert.assertEquals(SiteType.BLUEPRINT, blueprintManager.getSite().getType());
        Assert.assertEquals(site.getThemeId(), blueprintManager.getSite().getThemeId());
        Assert.assertNull(blueprintManager.getSite().getDescription());

        Assert.assertNotSame(site.getDefaultFormId(), blueprintManager.getSite().getDefaultFormId());
        final DraftItem defaultForm = persistance.getDraftItem(blueprintManager.getDefaultFormId());
        Assert.assertNotNull("For activated blueprint we must copy and default form!", defaultForm);
        Assert.assertEquals(blueprintManager.getId(), defaultForm.getSiteId());

        Assert.assertEquals(site.getTitle(), blueprintManager.getSite().getTitle());
        Assert.assertEquals("description", blueprintManager.getPublicBlueprintsSettings().getDescription());
        Assert.assertNotNull("This site is activated now.", blueprintManager.getPublicBlueprintsSettings().getActivated());
        Assert.assertEquals(BlueprintCategory.ALTERNATIVE_THERAPISTS, blueprintManager.getPublicBlueprintsSettings().getBlueprintCategory());
        Assert.assertEquals(2, blueprintManager.getPages().size());
        Assert.assertEquals(blueprintManager.getPages().get(0).getScreenShotId().intValue(), 1234);
        Assert.assertEquals(blueprintManager.getPages().get(1).getScreenShotId().intValue(), 5678);

        Assert.assertNull(
                "This site is not published any more, so it can be published one more time.",
                siteManager.getPublicBlueprintsSettings().getPublished());

        final MockMailSender mailSender = (MockMailSender) ServiceLocator.getMailSender();
        Assert.assertEquals(0, mailSender.getMails().size());

        final UserOnSiteRight oldUserOnSiteRight1 = new UserRightManager(admin1).toSite(site);
        final UserOnSiteRight oldUserOnSiteRight2 = new UserRightManager(admin2).toSite(site);
        Assert.assertNotNull(oldUserOnSiteRight1);
        Assert.assertNotNull(oldUserOnSiteRight2);


        final UserOnSiteRight userOnSiteRight1 = new UserRightManager(admin1).toSite(blueprintManager.getSite());
        final UserOnSiteRight userOnSiteRight2 = new UserRightManager(admin2).toSite(blueprintManager.getSite());
        Assert.assertNotNull(userOnSiteRight1);
        Assert.assertNotNull(userOnSiteRight2);
        Assert.assertEquals(SiteAccessLevel.ADMINISTRATOR, userOnSiteRight1.getSiteAccessType());
        Assert.assertEquals(SiteAccessLevel.ADMINISTRATOR, userOnSiteRight2.getSiteAccessType());
        Assert.assertEquals(true, userOnSiteRight1.isActive());
        Assert.assertEquals(true, userOnSiteRight2.isActive());
    }

    // This test should test that menu items have page ids from activated blueprint and not from published blueprint
    // (see SW-6560).
    @Test
    public void activateSite_withMenuItem() {
        final User admin1 = TestUtil.createUser("email1@email1.com");
        final User admin2 = TestUtil.createUser("email2@email2.com");
        config.setAdminEmails(Arrays.asList(admin1.getEmail(), admin2.getEmail()));
        config.setApplicationUrl("http://www.web-deva.com");

        final User user = TestUtil.createUserAndLogin();
        user.setFirstName("Balakirev");
        user.setLastName("Anatoliy");
        user.setEmail("email@email.email");

        final Site site = TestUtil.createSite();
        site.setTitle("Blueprint");

        final CustomForm customForm = TestUtil.createCustomForm(site);
        site.setDefaultFormId(customForm.getId());

        final Page page1 = TestUtil.createPage(site);

        final WidgetComposit widgetComposit = TestUtil.createWidgetComposit();
        new PageManager(page1).addWidget(widgetComposit);

        // Adding menu widget to one of the pages.
        final WidgetItem widgetItem = TestUtil.createWidgetItem();
        final DraftMenu menu = TestUtil.createMenu(site);
        widgetItem.setDraftItem(menu);
        new PageManager(page1).addWidget(widgetItem);
        widgetComposit.addChild(widgetItem);

        final Page page2 = TestUtil.createPage(site);
        TestUtil.createPage(site);

        new PageManager(page1).publish();
        new PageManager(page2).publish();

        // Adding menu items to menu widget.
        final MenuItem menuItem1 = TestUtil.createMenuItem(menu);
        menuItem1.setPageId(page1.getPageId());
        menu.addChild(menuItem1);
        final MenuItem menuItem2 = TestUtil.createMenuItem(menu);
        menuItem2.setPageId(page2.getPageId());
        menu.addChild(menuItem2);

        final Map<Integer, Integer> screenShotIds = new HashMap<Integer, Integer>();
        screenShotIds.put(page1.getPageId(), 1234);
        screenShotIds.put(page2.getPageId(), 5678);

        final SiteManager siteManager = new SiteManager(site);

        TestUtil.createUserOnSiteRightActiveAdmin(admin1, site);
        TestUtil.createUserOnSiteRightActiveAdmin(admin2, site);

        final SiteManager blueprintManager = siteManager.activateBlueprint(
                "description", screenShotIds, BlueprintCategory.ALTERNATIVE_THERAPISTS);

        Assert.assertNotNull(blueprintManager.getSite().getMenu());

        Assert.assertNull(
                "This site is activated, so 'published' should be null.",
                blueprintManager.getPublicBlueprintsSettings().getPublished());

        Assert.assertEquals(SiteType.BLUEPRINT, blueprintManager.getSite().getType());
        Assert.assertEquals(site.getThemeId(), blueprintManager.getSite().getThemeId());
        Assert.assertNull(blueprintManager.getSite().getDescription());

        Assert.assertNotSame(site.getDefaultFormId(), blueprintManager.getSite().getDefaultFormId());
        final DraftItem defaultForm = persistance.getDraftItem(blueprintManager.getDefaultFormId());
        Assert.assertNotNull("For activated blueprint we must copy and default form!", defaultForm);
        Assert.assertEquals(blueprintManager.getId(), defaultForm.getSiteId());

        Assert.assertEquals(site.getTitle(), blueprintManager.getSite().getTitle());
        Assert.assertEquals("description", blueprintManager.getPublicBlueprintsSettings().getDescription());
        Assert.assertNotNull("This site is activated now.", blueprintManager.getPublicBlueprintsSettings().getActivated());
        Assert.assertEquals(BlueprintCategory.ALTERNATIVE_THERAPISTS, blueprintManager.getPublicBlueprintsSettings().getBlueprintCategory());
        Assert.assertEquals(2, blueprintManager.getPages().size());
        Assert.assertEquals(blueprintManager.getPages().get(0).getScreenShotId().intValue(), 1234);
        Assert.assertEquals(blueprintManager.getPages().get(1).getScreenShotId().intValue(), 5678);

        Assert.assertNull(
                "This site is not published any more, so it can be published one more time.",
                siteManager.getPublicBlueprintsSettings().getPublished());

        final MockMailSender mailSender = (MockMailSender) ServiceLocator.getMailSender();
        Assert.assertEquals(0, mailSender.getMails().size());

        final UserOnSiteRight oldUserOnSiteRight1 = new UserRightManager(admin1).toSite(site);
        final UserOnSiteRight oldUserOnSiteRight2 = new UserRightManager(admin2).toSite(site);
        Assert.assertNotNull(oldUserOnSiteRight1);
        Assert.assertNotNull(oldUserOnSiteRight2);

        final UserOnSiteRight userOnSiteRight1 = new UserRightManager(admin1).toSite(blueprintManager.getSite());
        final UserOnSiteRight userOnSiteRight2 = new UserRightManager(admin2).toSite(blueprintManager.getSite());
        Assert.assertNotNull(userOnSiteRight1);
        Assert.assertNotNull(userOnSiteRight2);
        Assert.assertEquals(SiteAccessLevel.ADMINISTRATOR, userOnSiteRight1.getSiteAccessType());
        Assert.assertEquals(SiteAccessLevel.ADMINISTRATOR, userOnSiteRight2.getSiteAccessType());
        Assert.assertEquals(true, userOnSiteRight1.isActive());
        Assert.assertEquals(true, userOnSiteRight2.isActive());

        //Asserting that menu widget created in published blueprint is in the activated blueprint.
        final WidgetItem menuWidget = (WidgetItem)
                blueprintManager.getPages().get(0).getPageSettingsManager().getDraftPageSettings().getWidgets().get(1);
        Assert.assertNotNull(menuWidget);
        Assert.assertNotNull(menuWidget.getDraftItem());

        //Asserting that this menu widget has all menu items with correct page ids(see comment in the top of the test).
        final DraftMenu menuFromActivatedBlueprint = (DraftMenu) menuWidget.getDraftItem();
        Assert.assertEquals(2, menuFromActivatedBlueprint.getMenuItems().size());
        Assert.assertEquals(blueprintManager.getPages().get(0).getPageId(),
                (int) menuFromActivatedBlueprint.getMenuItems().get(0).getPageId());
        Assert.assertEquals(blueprintManager.getPages().get(1).getPageId(),
                (int) menuFromActivatedBlueprint.getMenuItems().get(1).getPageId());

        //Asserting that the menu widget is 'good' in work page settings also. Basically we are checking here that
        //copied pages were posted correctly.
        final WidgetItem workMenuWidget = (WidgetItem)
                blueprintManager.getPages().get(0).getPageSettingsManager().getWorkPageSettings().getWidgets().get(1);
        Assert.assertNotNull(workMenuWidget);
        Assert.assertNotNull(workMenuWidget.getDraftItem());
        final WorkMenu workMenuFromActivatedBlueprint = (WorkMenu) new WidgetManager(workMenuWidget).getItemManager().getWorkItem();
        Assert.assertEquals(2, workMenuFromActivatedBlueprint.getMenuItems().size());
        Assert.assertEquals(blueprintManager.getPages().get(0).getPageId(),
                (int) workMenuFromActivatedBlueprint.getMenuItems().get(0).getPageId());
        Assert.assertEquals(blueprintManager.getPages().get(1).getPageId(),
                (int) workMenuFromActivatedBlueprint.getMenuItems().get(1).getPageId());
    }

    @Test
    public void testRemoveFromNetwork() throws Exception {
        ServiceLocator.getConfigStorage().get().setApplicationUrl("testApplicationUrl");
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final Site childSite = TestUtil.createChildSite(site);
        childSite.setTitle("childSite");
        childSite.getChildSiteSettings().getChildSiteRegistration().setName("network");
        childSite.getChildSiteSettings().setFromEmail("fromEmail");

        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        TestUtil.createUserOnSiteRightActiveAdmin(user, childSite);


        new SiteManager(childSite).removeFromNetwork();


        Assert.assertEquals(null, childSite.getChildSiteSettings());
        final MockMailSender mailSender = (MockMailSender) ServiceLocator.getMailSender();

        Assert.assertEquals(1, mailSender.getMails().size());
        final Mail mail = mailSender.getMails().get(0);
        Assert.assertEquals(user.getEmail(), mail.getTo());
        Assert.assertEquals("fromEmail <shroggle-admin@email>", mail.getFrom());
        Assert.assertEquals(childSite.getTitle() + " has been removed from the network Network, but has NOT been deleted. Should you " +
                "choose to keep your site now that it is independent of the network, you may find that there is a different " +
                "subscription fee: <b>Your monthly subscription fee is now: $15pm</b> The network owners no longer have " +
                "any administrative access to childSite. All content associated with the network blueprint site, belonging " +
                "to the Network has been removed from this site. We recommend that you examine your site carefully now " +
                "to make sure that it is as you would want it to be. To withdraw content manually shared with the Network " +
                "Site please visit the following pages from your Dashboard: Manage Blogs, Manage Forums, Manage Forms & Records. " +
                "You may delete your site at any time by logging in at " + ServiceLocator.getConfigStorage().get().getApplicationUrl()
                + ", going to your dashboard page, and selecting the " +
                "Delete below your site name.", mail.getText());
    }

    private final MockMailSender mailSender = (MockMailSender) ServiceLocator.getMailSender();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final Config config = ServiceLocator.getConfigStorage().get();

}


