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
package com.shroggle.logic.childSites.childSiteSettings;

import com.shroggle.logic.site.page.PageManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.shroggle.exception.ChildSiteSettingsNotFoundException;
import com.shroggle.exception.PaymentException;
import com.shroggle.TestUtil;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.PublishingInfoResponse;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import com.shroggle.util.config.ConfigJavien;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.entity.*;
import junit.framework.Assert;

import java.util.*;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class ChildSiteSettingsManagerTest {

    @Test
    public void testRemove() throws Exception {
        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(childSiteSettings);

        manager.remove();

        Assert.assertEquals(null, ServiceLocator.getPersistance().getChildSiteSettingsById(childSiteSettings.getId()));
    }

    @Test
    public void getHisNetworkContactUsUrl() {
        final Site parentSite = TestUtil.createSite();
        parentSite.setSubDomain("subDomain");
        final Site childSite = TestUtil.createSite();
        final Page page = TestUtil.createWorkPage(parentSite);
        new PageManager(page).setUrl("pageUrl");
        new PageManager(page).publish();

        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(parentSite);
        registration.setContactUsPageId(page.getPageId());
        TestUtil.createChildSiteSettings(registration, parentSite, childSite);

        final ChildSiteSettingsManager siteManager = new ChildSiteSettingsManager(childSite.getChildSiteSettings());
        org.junit.Assert.assertEquals("http://subDomain.shroggle.com/pageUrl", siteManager.getHisNetworkContactUsUrl());
    }

    @Test
    public void getHisNetworkContactUsUrl_withoutWorkPage() {
        final Site parentSite = TestUtil.createSite();
        final Site childSite = TestUtil.createSite();
        final Page page = TestUtil.createPage(parentSite);
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(parentSite);
        registration.setContactUsPageId(page.getPageId());
        TestUtil.createChildSiteSettings(registration, parentSite, childSite);

        final ChildSiteSettingsManager siteManager = new ChildSiteSettingsManager(childSite.getChildSiteSettings());
        org.junit.Assert.assertEquals("#", siteManager.getHisNetworkContactUsUrl());
    }

    @Test(expected = ChildSiteSettingsNotFoundException.class)
    public void createChildSiteSettingsLogic_withoutChildSiteSettings() {
        final Integer childSiteSettingsId = null;
        new ChildSiteSettingsManager(childSiteSettingsId);
    }

    @Test
    public void isNetworkMembershipExpired_true() {
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        settings.setEndDate(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000L));
        Assert.assertTrue(new ChildSiteSettingsManager(settings).isNetworkMembershipExpired(new Date()));
    }

    @Test
    public void isNetworkMembershipExpired_false() {
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        settings.setEndDate(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000L));
        Assert.assertFalse(new ChildSiteSettingsManager(settings).isNetworkMembershipExpired(new Date()));
    }

    @Test
    public void isNetworkMembershipExpired_withoutEndDate() {
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        settings.setEndDate(null);
        Assert.assertFalse(new ChildSiteSettingsManager(settings).isNetworkMembershipExpired(new Date()));
    }


    //------------------------------------------------------------------------------------------------------------------

    @Test
    public void isNetworkMembershipExpiredInTenDays_withEqualCurrentAndExpirationDates() {
        final Date expirationDate = new Date();
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        settings.setEndDate(expirationDate);

        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setMembersipExpireNotifyTime(10 * 24 * 60);
        Assert.assertTrue(new ChildSiteSettingsManager(settings).isNetworkMembershipDueToExpire(expirationDate));
    }


    @Test
    public void isNetworkMembershipExpiredInTenDays_withExpirationDateEqualCurrentDatePlus10days() {
        final Calendar currentDate = new GregorianCalendar(2010, 5, 1);
        final Calendar expirationDate = new GregorianCalendar(2010, 5, 11);
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        settings.setEndDate(expirationDate.getTime());
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setMembersipExpireNotifyTime(10 * 24 * 60);


        Assert.assertTrue(new ChildSiteSettingsManager(settings).isNetworkMembershipDueToExpire(currentDate.getTime()));
    }


    @Test
    public void isNetworkMembershipExpiredInTenDays_withExpirationDateEqualCurrentDatePlus16days() {
        final Calendar currentDate = new GregorianCalendar(2010, 5, 1);
        final Calendar expirationDate = new GregorianCalendar(2010, 5, 17);
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        settings.setEndDate(expirationDate.getTime());
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setMembersipExpireNotifyTime(10 * 24 * 60);
        Assert.assertFalse(new ChildSiteSettingsManager(settings).isNetworkMembershipDueToExpire(currentDate.getTime()));
    }


    @Test
    public void isNetworkMembershipExpiredInTenDays_withExpirationDateEqualCurrentDateMinus16days() {
        final Calendar currentDate = new GregorianCalendar(2010, 5, 1);
        final Calendar expirationDate = new GregorianCalendar(2010, 4, 16);
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        settings.setEndDate(expirationDate.getTime());
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setMembersipExpireNotifyTime(10 * 24 * 60);
        Assert.assertTrue(new ChildSiteSettingsManager(settings).isNetworkMembershipDueToExpire(currentDate.getTime()));
    }


    @Test
    public void isNetworkMembershipExpiredInTenDays_withExpirationDateEqualCurrentDateMinus3Years() {
        final Calendar currentDate = new GregorianCalendar(2010, 5, 1);
        final Calendar expirationDate = new GregorianCalendar(2007, 5, 1);
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        settings.setEndDate(expirationDate.getTime());
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setMembersipExpireNotifyTime(10 * 24 * 60);
        Assert.assertTrue(new ChildSiteSettingsManager(settings).isNetworkMembershipDueToExpire(currentDate.getTime()));
    }


    @Test
    public void isNetworkMembershipExpiredInTenDays_withExpirationDateEqualCurrentDateMinus2Days() {
        final Calendar currentDate = new GregorianCalendar(2010, 5, 10);
        final Calendar expirationDate = new GregorianCalendar(2010, 5, 8);
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        settings.setEndDate(expirationDate.getTime());
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setMembersipExpireNotifyTime(10 * 24 * 60);
        Assert.assertTrue(new ChildSiteSettingsManager(settings).isNetworkMembershipDueToExpire(currentDate.getTime()));
    }

    @Test
    public void isNetworkMembershipExpiredInTenDays_withExpirationDateEqualCurrentDatePlus1Day() {
        final Calendar currentDate = new GregorianCalendar(2010, 5, 10);
        final Calendar expirationDate = new GregorianCalendar(2010, 5, 11);
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        settings.setEndDate(expirationDate.getTime());
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setMembersipExpireNotifyTime(10 * 24 * 60);
        Assert.assertTrue(new ChildSiteSettingsManager(settings).isNetworkMembershipDueToExpire(currentDate.getTime()));
    }


    @Test
    public void isNetworkMembershipExpiredInTenDays_withExpirationDateEqualCurrentDatePlus1YearPlus15days() {
        final Calendar currentDate = new GregorianCalendar(2010, 5, 10);
        final Calendar expirationDate = new GregorianCalendar(2011, 5, 25);
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        settings.setEndDate(expirationDate.getTime());
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setMembersipExpireNotifyTime(10 * 24 * 60);
        Assert.assertFalse(new ChildSiteSettingsManager(settings).isNetworkMembershipDueToExpire(currentDate.getTime()));
    }


    @Test
    public void isNetworkMembershipExpiredInTenDays_withExpirationDateEqualCurrentDatePlus1YearPlus14days() {
        final Calendar currentDate = new GregorianCalendar(2010, 5, 10);
        final Calendar expirationDate = new GregorianCalendar(2011, 5, 24);
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        settings.setEndDate(expirationDate.getTime());
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setMembersipExpireNotifyTime(10 * 24 * 60);
        Assert.assertFalse(new ChildSiteSettingsManager(settings).isNetworkMembershipDueToExpire(currentDate.getTime()));
    }

    @Test
    public void isNetworkMembershipExpiredInTenDays_withExpirationDateEqualCurrentDatePlus1MonthPlus15days() {
        final Calendar currentDate = new GregorianCalendar(2010, 5, 10);
        final Calendar expirationDate = new GregorianCalendar(2010, 6, 25);
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        settings.setEndDate(expirationDate.getTime());
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setMembersipExpireNotifyTime(10 * 24 * 60);
        Assert.assertFalse(new ChildSiteSettingsManager(settings).isNetworkMembershipDueToExpire(currentDate.getTime()));
    }

    @Test
    public void isNetworkMembershipExpiredInTenDays_withExpirationDateEqualCurrentDatePlus1MonthPlus14days() {
        final Calendar currentDate = new GregorianCalendar(2010, 5, 10);
        final Calendar expirationDate = new GregorianCalendar(2010, 6, 24);
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        settings.setEndDate(expirationDate.getTime());
        ServiceLocator.getConfigStorage().get().getBillingInfoProperties().setMembersipExpireNotifyTime(10 * 24 * 60);
        Assert.assertFalse(new ChildSiteSettingsManager(settings).isNetworkMembershipDueToExpire(currentDate.getTime()));
    }


    @Test
    public void isNotificationMessageSent_false() {
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        settings.setNotificationMailSent(null);

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);

        Assert.assertFalse(manager.isNotificationMessageSent());

        manager.setNotificationMessageSent();

        Assert.assertTrue(manager.isNotificationMessageSent());
    }


    @Test
    public void isNotificationMessageSent_true() {
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        settings.setNotificationMailSent(new Date());
        Assert.assertTrue(new ChildSiteSettingsManager(settings).isNotificationMessageSent());
    }


    @Test
    public void setCanBePublishedMessageSent() {
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);

        settings.setNotificationMailSent(new Date());
        Assert.assertTrue(new ChildSiteSettingsManager(settings).isNotificationMessageSent());
    }


    @Test
    public void edit() {
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
        junit.framework.Assert.assertTrue(response.isCanBePublished());
        junit.framework.Assert.assertFalse(response.isCanBePublishedMessageSent());


        response = new SiteManager(parentSite).checkChildSiteStartDate();
        junit.framework.Assert.assertTrue(response.isCanBePublished());
        junit.framework.Assert.assertTrue(response.isCanBePublishedMessageSent());


        response = new SiteManager(childSiteCantBePublished).checkChildSiteStartDate();
        junit.framework.Assert.assertFalse(response.isCanBePublished());
        junit.framework.Assert.assertFalse(response.isCanBePublishedMessageSent());


        response = new ChildSiteSettingsManager(settingsCanBePublished).getPublishingInfo();
        junit.framework.Assert.assertTrue(response.isCanBePublished());
        junit.framework.Assert.assertFalse(response.isCanBePublishedMessageSent());


        response = new ChildSiteSettingsManager(settingsCantBePublished).getPublishingInfo();
        junit.framework.Assert.assertFalse(response.isCanBePublished());
        junit.framework.Assert.assertFalse(response.isCanBePublishedMessageSent());
    }

    @Test
    public void testShowFirstStatePage() {
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        Site blueprint1 = TestUtil.createSite();
        blueprint1.setType(SiteType.BLUEPRINT);
        childSiteRegistration.setBlueprintsId(Arrays.asList(blueprint1.getSiteId()));
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        settings.setRequiredToUseSiteBlueprint(true);

        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        settings.setSitePaymentSettings(sitePaymentSettings);
        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);

        Assert.assertTrue(manager.childSiteShouldBeCreated());
    }


    @Test
    public void testShowFirstStatePage_withoutChildSiteRegistration() {
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        Site blueprint1 = TestUtil.createSite();
        blueprint1.setType(SiteType.BLUEPRINT);
        childSiteRegistration.setBlueprintsId(Arrays.asList(blueprint1.getSiteId()));
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        settings.setRequiredToUseSiteBlueprint(true);
        settings.setSitePaymentSettings(new SitePaymentSettings());
        settings.setChildSiteRegistration(null);
        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);

        Assert.assertFalse(manager.childSiteShouldBeCreated());
    }


    @Test
    public void testShowFirstStatePage_withTwoBlueprints() {
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");

        Site blueprint1 = TestUtil.createBlueprint();
        Site blueprint2 = TestUtil.createBlueprint();

        childSiteRegistration.setBlueprintsId(Arrays.asList(blueprint1.getSiteId(), blueprint2.getSiteId()));
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        settings.setRequiredToUseSiteBlueprint(true);
        settings.setSitePaymentSettings(new SitePaymentSettings());
        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);

        Assert.assertFalse(manager.childSiteShouldBeCreated());
    }

    @Test
    public void testShowFirstStatePage_withOneBlueprintIdButWithoutBlueprint() {
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        childSiteRegistration.setBlueprintsId(Arrays.asList(1));
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        settings.setRequiredToUseSiteBlueprint(true);
        settings.setSitePaymentSettings(new SitePaymentSettings());
        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);

        Assert.assertFalse(manager.childSiteShouldBeCreated());
    }

    @Test
    public void testShowFirstStatePage_withNotRequiredToUseSiteBlueprint() {
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        Site blueprint1 = TestUtil.createSite();
        blueprint1.setType(SiteType.BLUEPRINT);
        childSiteRegistration.setBlueprintsId(Arrays.asList(blueprint1.getSiteId()));
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        settings.setRequiredToUseSiteBlueprint(false);
        settings.setSitePaymentSettings(new SitePaymentSettings());
        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);

        Assert.assertFalse(manager.childSiteShouldBeCreated());
    }

    @Test
    public void testShowFirstStatePage_withNotActiveSitePaymentSettings() {
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        Site blueprint1 = TestUtil.createSite();
        blueprint1.setType(SiteType.BLUEPRINT);
        childSiteRegistration.setBlueprintsId(Arrays.asList(blueprint1.getSiteId()));
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        settings.setRequiredToUseSiteBlueprint(true);

        final SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();
        sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
        settings.setSitePaymentSettings(sitePaymentSettings);

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);
        Assert.assertTrue(manager.childSiteShouldBeCreated());
    }

    @Test
    public void testGetUniqueDomainName_withoutSitesAndFilledForm() {
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);
        Assert.assertEquals("site", manager.getUniqueDomainName());
        Assert.assertTrue(manager.getUniqueDomainName().length() <= 250);
    }


    @Test
    public void testGetUniqueDomainName_withOneSiteAndWithoutFilledForm() {
        Site site = TestUtil.createSite();
        site.setSubDomain("site");
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);
        Assert.assertEquals("site1", manager.getUniqueDomainName());
        Assert.assertTrue(manager.getUniqueDomainName().length() <= 250);
    }


    @Test
    public void testGetUniqueDomainName_with4SitesAndWithoutFilledForm() {
        Site site = TestUtil.createSite();
        site.setSubDomain("site");
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);

        TestUtil.createSite("title", "site1");
        TestUtil.createSite("title", "site2");
        TestUtil.createSite("title", "site3");

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);
        Assert.assertEquals("site4", manager.getUniqueDomainName());
        Assert.assertTrue(manager.getUniqueDomainName().length() <= 250);
    }


    @Test
    public void testGetUniqueDomainName_with10SitesAndWithoutFilledForm() {
        Site site = TestUtil.createSite();
        site.setSubDomain("site");
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);

        TestUtil.createSite("title", "site1");
        TestUtil.createSite("title", "site2");
        TestUtil.createSite("title", "site3");
        TestUtil.createSite("title", "site4");
        TestUtil.createSite("title", "site5");
        TestUtil.createSite("title", "site6");
        TestUtil.createSite("title", "site7");
        TestUtil.createSite("title", "site8");
        TestUtil.createSite("title", "site9");

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);
        Assert.assertEquals("site10", manager.getUniqueDomainName());
        Assert.assertTrue(manager.getUniqueDomainName().length() <= 250);
    }


    @Test
    public void testGetUniqueDomainName_with4SitesAndWithFilledFormButWithoutDomainNameFilledFormItem() {
        Site site = TestUtil.createSite();
        site.setSubDomain("site");
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        FilledForm filledForm = TestUtil.createFilledForm(childSiteRegistration.getFormId());
        filledForm.setFilledFormItems(TestUtil.createFilledFormItems(FormItemName.ADDRESS));
        settings.setFilledFormId(filledForm.getFilledFormId());

        TestUtil.createSite("title", "site1");
        TestUtil.createSite("title", "site2");
        TestUtil.createSite("title", "site3");

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);
        Assert.assertEquals("site4", manager.getUniqueDomainName());
        Assert.assertTrue(manager.getUniqueDomainName().length() <= 250);
    }

    @Test
    public void testGetUniqueDomainName_with4SitesAndWithFilledFormButWithoutFilledFormItemValues() {
        Site site = TestUtil.createSite();
        site.setSubDomain("site");
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        FilledForm filledForm = TestUtil.createFilledForm(childSiteRegistration.getFormId());
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.YOUR_OWN_DOMAIN_NAME);
        List<String> values = new ArrayList<String>();
        filledFormItem.setValues(values);
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));
        settings.setFilledFormId(filledForm.getFilledFormId());

        TestUtil.createSite("title", "site1");
        TestUtil.createSite("title", "site2");
        TestUtil.createSite("title", "site3");

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);
        Assert.assertEquals("site4", manager.getUniqueDomainName());
        Assert.assertTrue(manager.getUniqueDomainName().length() <= 250);
    }

    @Test
    public void testGetUniqueDomainName_with4SitesAndWithFilledFormButWithNullFilledFormItemValues() {
        Site site = TestUtil.createSite();
        site.setSubDomain("site");
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        FilledForm filledForm = TestUtil.createFilledForm(childSiteRegistration.getFormId());
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.YOUR_OWN_DOMAIN_NAME);
        List<String> values = new ArrayList<String>();
        filledFormItem.setValues(values);
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));
        settings.setFilledFormId(filledForm.getFilledFormId());

        TestUtil.createSite("title", "site1");
        TestUtil.createSite("title", "site2");
        TestUtil.createSite("title", "site3");

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);
        Assert.assertEquals("site4", manager.getUniqueDomainName());
        Assert.assertTrue(manager.getUniqueDomainName().length() <= 250);
    }

    @Test
    public void testGetUniqueDomainName_with4SitesAndWithFilledFormWithNotValidDomainNameInFilledFormItem() {
        Site site = TestUtil.createSite();
        site.setSubDomain("site");
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        FilledForm filledForm = TestUtil.createFilledForm(childSiteRegistration.getFormId());
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.YOUR_OWN_DOMAIN_NAME);
        List<String> values = Arrays.asList("localhost:8080");
        filledFormItem.setValues(values);
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));
        settings.setFilledFormId(filledForm.getFilledFormId());

        TestUtil.createSite("title", "site1");
        TestUtil.createSite("title", "site2");
        TestUtil.createSite("title", "site3");

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);
        Assert.assertEquals("site4", manager.getUniqueDomainName());
        Assert.assertTrue(manager.getUniqueDomainName().length() <= 250);
    }


    @Test
    public void testGetUniqueDomainName_with10SitesAndWithFilledFormWithLongNotUniqueDomainNameInFilledFormItems() {
        Site site = TestUtil.createSite();
        site.setSubDomain("site");
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        FilledForm filledForm = TestUtil.createFilledForm(childSiteRegistration.getFormId());
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.YOUR_OWN_DOMAIN_NAME);

        String domainName = "asdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggasdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffghjkloiuytrewqasdfghjklllllllllll";
        String domainName1 = "asdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggasdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffghjkloiuytrewqasdfghjk";
        String domainName2 = "asdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggasdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffghjkloiuytrewqasdfghj1";
        String domainName3 = "asdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggasdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffghjkloiuytrewqasdfghj2";
        String domainName4 = "asdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggasdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffghjkloiuytrewqasdfghj3";
        String domainName5 = "asdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggasdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffghjkloiuytrewqasdfghj4";
        String domainName6 = "asdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggasdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffghjkloiuytrewqasdfghj5";
        String domainName7 = "asdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggasdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffghjkloiuytrewqasdfghj6";
        String domainName8 = "asdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggasdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffghjkloiuytrewqasdfghj7";
        String domainName9 = "asdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggasdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffghjkloiuytrewqasdfghj8";
        Assert.assertTrue(domainName.length() > 250);

        List<String> values = Arrays.asList(domainName);
        filledFormItem.setValues(values);
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));
        settings.setFilledFormId(filledForm.getFilledFormId());

        TestUtil.createSite("title", domainName1);
        TestUtil.createSite("title", domainName2);
        TestUtil.createSite("title", domainName3);
        TestUtil.createSite("title", domainName4);
        TestUtil.createSite("title", domainName5);
        TestUtil.createSite("title", domainName6);
        TestUtil.createSite("title", domainName7);
        TestUtil.createSite("title", domainName8);
        TestUtil.createSite("title", domainName9);

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);
        Assert.assertEquals("asdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggasdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffghjkloiuytrewqasdfghj9", manager.getUniqueDomainName());
        Assert.assertTrue(manager.getUniqueDomainName().length() == 250);
    }


    @Test
    public void testGetUniqueDomainName_with4SitesAndWithFilledFormWithLongNotUniqueDomainNameInFilledFormItems() {
        Site site = TestUtil.createSite();
        site.setSubDomain("site");
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        FilledForm filledForm = TestUtil.createFilledForm(childSiteRegistration.getFormId());
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.YOUR_OWN_DOMAIN_NAME);

        String domainName = "asdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggasdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffghjkloiuytrewqasdfghjklllllllllll";
        String domainName1 = "asdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggasdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffghjkloiuytrewqasdfghjk";
        String domainName2 = "asdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggasdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffghjkloiuytrewqasdfghj1";
        String domainName3 = "asdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggasdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffghjkloiuytrewqasdfghj2";
        String domainName4 = "asdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggasdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffghjkloiuytrewqasdfghj3";
        String domainName5 = "asdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggasdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffghjkloiuytrewqasdfghj4";
        String domainName6 = "asdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggasdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffghjkloiuytrewqasdfghj5";
        String domainName7 = "asdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggasdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffghjkloiuytrewqasdfghj6";
        String domainName8 = "asdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggasdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffghjkloiuytrewqasdfghj7";
        String domainName9 = "asdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggasdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffghjkloiuytrewqasdfghj8";
        String domainName10 = "asdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggasdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffghjkloiuytrewqasdfghj9";
        String domainName11 = "asdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggasdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffghjkloiuytrewqasdfgh10";
        Assert.assertTrue(domainName.length() > 250);

        List<String> values = Arrays.asList(domainName);
        filledFormItem.setValues(values);
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));
        settings.setFilledFormId(filledForm.getFilledFormId());

        TestUtil.createSite("title", domainName1);
        TestUtil.createSite("title", domainName2);
        TestUtil.createSite("title", domainName3);
        TestUtil.createSite("title", domainName4);
        TestUtil.createSite("title", domainName5);
        TestUtil.createSite("title", domainName6);
        TestUtil.createSite("title", domainName7);
        TestUtil.createSite("title", domainName8);
        TestUtil.createSite("title", domainName9);
        TestUtil.createSite("title", domainName10);
        TestUtil.createSite("title", domainName11);

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);
        Assert.assertEquals("asdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggasdfghjklqwertyuiopmnbvcxzasdfghjklpoiuytrewqasdfghjklmnnhgfggggggggggggggggggggggggggggggggggggggggffffffffffffffffffffffffffffghjkloiuytrewqasdfgh11", manager.getUniqueDomainName());
        Assert.assertTrue(manager.getUniqueDomainName().length() <= 250);
    }


    @Test
    public void testGetUniqueDomainName_withoutDomainName_WithSiteNameConsistOfFewWords() {
        Site site = TestUtil.createSite();
        site.setSubDomain("site");
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        FilledForm filledForm = TestUtil.createFilledForm(childSiteRegistration.getFormId());

        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.YOUR_PAGE_SITE_NAME);
        filledFormItem.setValue("domain name consist of few words");
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));
        settings.setFilledFormId(filledForm.getFilledFormId());

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);
        Assert.assertEquals("domain", manager.getUniqueDomainName());
    }

    @Test
    public void testGetSiteTitle_withoutFilledForm() {
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);
        Assert.assertEquals("", manager.getSiteTitle());
    }


    @Test
    public void testGetSiteTitle_WithFilledFormButWithoutDomainNameFilledFormItem() {
        Site site = TestUtil.createSite();
        site.setSubDomain("site");
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        FilledForm filledForm = TestUtil.createFilledForm(childSiteRegistration.getFormId());
        filledForm.setFilledFormItems(TestUtil.createFilledFormItems(FormItemName.ADDRESS));
        settings.setFilledFormId(filledForm.getFilledFormId());

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);
        Assert.assertEquals("", manager.getSiteTitle());
    }

    @Test
    public void testGetSiteTitle_WithFilledFormButWithoutFilledFormItemValues() {
        Site site = TestUtil.createSite();
        site.setSubDomain("site");
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        FilledForm filledForm = TestUtil.createFilledForm(childSiteRegistration.getFormId());
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.YOUR_PAGE_SITE_NAME);
        List<String> values = new ArrayList<String>();
        filledFormItem.setValues(values);
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));
        settings.setFilledFormId(filledForm.getFilledFormId());

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);
        Assert.assertEquals("", manager.getSiteTitle());
    }

    @Test
    public void testGetSiteTitle_WithFilledFormButWithNullFilledFormItemValues() {
        Site site = TestUtil.createSite();
        site.setSubDomain("site");
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        FilledForm filledForm = TestUtil.createFilledForm(childSiteRegistration.getFormId());
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.YOUR_PAGE_SITE_NAME);
        List<String> values = new ArrayList<String>();
        filledFormItem.setValues(values);
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));
        settings.setFilledFormId(filledForm.getFilledFormId());

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);
        Assert.assertEquals("", manager.getSiteTitle());
    }


    @Test
    public void testGetSiteTitle_WithFilledForm() {
        Site site = TestUtil.createSite();
        site.setSubDomain("site");
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        FilledForm filledForm = TestUtil.createFilledForm(childSiteRegistration.getFormId());
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.YOUR_PAGE_SITE_NAME);
        List<String> values = Arrays.asList("title");
        filledFormItem.setValues(values);
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));
        settings.setFilledFormId(filledForm.getFilledFormId());

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);
        Assert.assertEquals("title", manager.getRegistrantName());
    }

    @Test
    public void testGetRegistrantName_withoutFilledForm() {
        Site site = TestUtil.createSite();
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);
        Assert.assertEquals("", manager.getRegistrantName());
    }


    @Test
    public void testGetRegistrantName_WithFilledFormButWithoutNameFilledFormItem() {
        Site site = TestUtil.createSite();
        site.setSubDomain("site");
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        FilledForm filledForm = TestUtil.createFilledForm(childSiteRegistration.getFormId());
        filledForm.setFilledFormItems(TestUtil.createFilledFormItems(FormItemName.ADDRESS));
        settings.setFilledFormId(filledForm.getFilledFormId());

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);
        Assert.assertEquals("", manager.getRegistrantName());
    }

    @Test
    public void testGetRegistrantName_WithFilledFormButWithoutFilledFormItemValues() {
        Site site = TestUtil.createSite();
        site.setSubDomain("site");
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        FilledForm filledForm = TestUtil.createFilledForm(childSiteRegistration.getFormId());
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.YOUR_PAGE_SITE_NAME);
        List<String> values = new ArrayList<String>();
        filledFormItem.setValues(values);
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));
        settings.setFilledFormId(filledForm.getFilledFormId());

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);
        Assert.assertEquals("", manager.getRegistrantName());
    }

    @Test
    public void testGetRegistrantName_WithFilledFormButWithNullFilledFormItemValues() {
        Site site = TestUtil.createSite();
        site.setSubDomain("site");
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        FilledForm filledForm = TestUtil.createFilledForm(childSiteRegistration.getFormId());
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.YOUR_PAGE_SITE_NAME);
        List<String> values = new ArrayList<String>();
        filledFormItem.setValues(values);
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));
        settings.setFilledFormId(filledForm.getFilledFormId());

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);
        Assert.assertEquals("", manager.getRegistrantName());
    }


    @Test
    public void testGetRegistrantName_WithFilledForm() {
        Site site = TestUtil.createSite();
        site.setSubDomain("site");
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        FilledForm filledForm = TestUtil.createFilledForm(childSiteRegistration.getFormId());
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.YOUR_PAGE_SITE_NAME);
        List<String> values = Arrays.asList("title");
        filledFormItem.setValues(values);
        filledForm.setFilledFormItems(Arrays.asList(filledFormItem));
        settings.setFilledFormId(filledForm.getFilledFormId());

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);
        Assert.assertEquals("title", manager.getRegistrantName());
    }


    @Test
    public void testGetRegistrantName_WithFirstAndLastName() {
        Site site = TestUtil.createSite();
        site.setSubDomain("site");
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        FilledForm filledForm = TestUtil.createFilledForm(childSiteRegistration.getFormId());

        final FilledFormItem firstName = TestUtil.createFilledFormItem(FormItemName.FIRST_NAME);
        firstName.setValues(Arrays.asList("firstName"));

        final FilledFormItem lastName = TestUtil.createFilledFormItem(FormItemName.LAST_NAME);
        lastName.setValues(Arrays.asList("lastName"));

        filledForm.setFilledFormItems(Arrays.asList(firstName, lastName));
        settings.setFilledFormId(filledForm.getFilledFormId());

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);
        Assert.assertEquals("lastName firstName", manager.getRegistrantName());
    }


    @Test
    public void testGetRegistrantName_WithFirstWithoutLastName() {
        Site site = TestUtil.createSite();
        site.setSubDomain("site");
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        FilledForm filledForm = TestUtil.createFilledForm(childSiteRegistration.getFormId());

        final FilledFormItem firstName = TestUtil.createFilledFormItem(FormItemName.FIRST_NAME);
        firstName.setValues(Arrays.asList("firstName"));

        filledForm.setFilledFormItems(Arrays.asList(firstName));
        settings.setFilledFormId(filledForm.getFilledFormId());

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);
        Assert.assertEquals("firstName", manager.getRegistrantName());
    }


    @Test
    public void testGetRegistrantName_WithLastWithoutFirstName() {
        Site site = TestUtil.createSite();
        site.setSubDomain("site");
        DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration("name", "comment");
        ChildSiteSettings settings = TestUtil.createChildSiteSettings(childSiteRegistration, new Site(), site);
        FilledForm filledForm = TestUtil.createFilledForm(childSiteRegistration.getFormId());

        final FilledFormItem lastName = TestUtil.createFilledFormItem(FormItemName.LAST_NAME);
        lastName.setValues(Arrays.asList("lastName"));

        filledForm.setFilledFormItems(Arrays.asList(lastName));
        settings.setFilledFormId(filledForm.getFilledFormId());

        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(settings);
        Assert.assertEquals("lastName", manager.getRegistrantName());
    }

    @Test
    public void testGetPaymentSumByChargeType_forChildSite() {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        childSiteSettings.setPrice250mb(250);
        childSiteSettings.setPrice500mb(500);
        childSiteSettings.setPrice1gb(1000);
        childSiteSettings.setPrice3gb(3000);
        childSiteSettings.setOneTimeFee(1000000);
        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(childSiteSettings);
        Assert.assertEquals(1000000.0, manager.getPaymentSumByChargeType(ChargeType.SITE_ONE_TIME_FEE));
        Assert.assertEquals(250.0, manager.getPaymentSumByChargeType(ChargeType.SITE_250MB_MONTHLY_FEE));
        Assert.assertEquals(500.0, manager.getPaymentSumByChargeType(ChargeType.SITE_500MB_MONTHLY_FEE));
        Assert.assertEquals(1000.0, manager.getPaymentSumByChargeType(ChargeType.SITE_1GB_MONTHLY_FEE));
        Assert.assertEquals(3000.0, manager.getPaymentSumByChargeType(ChargeType.SITE_3GB_MONTHLY_FEE));
    }

    @Test(expected = PaymentException.class)
    public void testGetPaymentSumByChargeType_withoutChargeType() {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(childSiteSettings);
        manager.getPaymentSumByChargeType(null);
    }

    @Test(expected = PaymentException.class)
    public void testGetPaymentSumByChargeType_withWrongChargeTypeSITE_ANNUAL_FEE() {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(childSiteSettings);
        manager.getPaymentSumByChargeType(ChargeType.SITE_ANNUAL_FEE);
    }

    @Test(expected = PaymentException.class)
    public void testGetPaymentSumByChargeType_withWrongChargeTypeSITE_MONTHLY_FEE() {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(childSiteSettings);
        manager.getPaymentSumByChargeType(ChargeType.SITE_MONTHLY_FEE);
    }

    @Test
    public void testGetNetworkName() throws Exception {
        final Site parentSite = TestUtil.createSite();
        final DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(parentSite);
        registration.setName("registration");
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(registration, parentSite, new Site());
        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(childSiteSettings);

        Assert.assertEquals("registration", manager.getNetworkName());
    }

    @Test
    public void testGetNetworkName_withoutRegistration() throws Exception {
        final Config config = ServiceLocator.getConfigStorage().get();
        config.setApplicationName("testApplicationName");

        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setChildSiteRegistration(null);
        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(childSiteSettings);

        Assert.assertEquals(config.getApplicationName(), manager.getNetworkName());
    }

    @Test
    public void testEqualsForChildSiteSettings() throws Exception {
        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setChildSiteRegistration(null);
        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(childSiteSettings);

        Assert.assertEquals(true, manager.equals(childSiteSettings));
    }

    @Test
    public void testEqualsForManager() throws Exception {
        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setChildSiteRegistration(null);
        ChildSiteSettingsManager manager1 = new ChildSiteSettingsManager(childSiteSettings);
        ChildSiteSettingsManager manager2 = new ChildSiteSettingsManager(childSiteSettings);

        Assert.assertEquals(true, manager1.equals(manager2));
    }

    @Test
    public void testEqualsForChildSiteSettings_forNotSame() throws Exception {
        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        final ChildSiteSettings childSiteSettings2 = TestUtil.createChildSiteSettings(new Site(), new Site());
        childSiteSettings.setChildSiteRegistration(null);
        ChildSiteSettingsManager manager = new ChildSiteSettingsManager(childSiteSettings);

        Assert.assertEquals(false, manager.equals(childSiteSettings2));
    }

    @Test
    public void testEqualsForManager_forNotSame() throws Exception {
        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        final ChildSiteSettings childSiteSettings2 = TestUtil.createChildSiteSettings(new Site(), new Site());
        childSiteSettings.setChildSiteRegistration(null);
        ChildSiteSettingsManager manager1 = new ChildSiteSettingsManager(childSiteSettings);
        ChildSiteSettingsManager manager2 = new ChildSiteSettingsManager(childSiteSettings2);

        Assert.assertEquals(false, manager1.equals(manager2));
    }

    @Test
    public void testHashCodeForManager() throws Exception {
        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        childSiteSettings.setChildSiteRegistration(null);
        ChildSiteSettingsManager manager1 = new ChildSiteSettingsManager(childSiteSettings);
        ChildSiteSettingsManager manager2 = new ChildSiteSettingsManager(childSiteSettings);

        Assert.assertEquals(manager1.hashCode(), manager2.hashCode());
    }

    @Test
    public void testHashCodeForManager_forNotSame() throws Exception {
        final Site parentSite = TestUtil.createSite();
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(parentSite, new Site());
        final ChildSiteSettings childSiteSettings2 = TestUtil.createChildSiteSettings(new Site(), new Site());
        childSiteSettings.setChildSiteRegistration(null);
        ChildSiteSettingsManager manager1 = new ChildSiteSettingsManager(childSiteSettings);
        ChildSiteSettingsManager manager2 = new ChildSiteSettingsManager(childSiteSettings2);

        Assert.assertNotSame(manager1.hashCode(), manager2.hashCode());
    }

    @Test
    public void testChildSiteHasBeenCreated() throws Exception {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        final ChildSiteSettingsManager manager = new ChildSiteSettingsManager(childSiteSettings);
        Assert.assertTrue(manager.isChildSiteHasBeenCreated());
    }

    @Test
    public void testChildSiteHasBeenCreated_notCreated() throws Exception {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(new Site(), new Site());
        childSiteSettings.setSite(null);
        final ChildSiteSettingsManager manager = new ChildSiteSettingsManager(childSiteSettings);
        Assert.assertFalse(manager.isChildSiteHasBeenCreated());
    }
}
