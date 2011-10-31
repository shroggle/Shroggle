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
package com.shroggle.presentation.account.dashboard;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.childSites.childSiteSettings.ChildSiteSettingsManager;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.item.ItemTypeManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.account.dashboard.siteInfo.DashboardSiteInfo;
import com.shroggle.presentation.account.dashboard.siteInfo.DashboardSiteInfoForCreatedSite;
import com.shroggle.presentation.account.dashboard.siteInfo.DashboardSiteInfoForNotCreatedSite;
import com.shroggle.util.DateUtil;
import com.shroggle.util.html.HtmlUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;


/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class DashboardSiteInfoTest {

    @Test
    public void testNewInstance_forNotCreated() throws Exception {
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings();
        final ChildSiteSettingsManager manager = new ChildSiteSettingsManager(childSiteSettings);

        Assert.assertTrue(DashboardSiteInfo.newInstance(manager) instanceof DashboardSiteInfoForNotCreatedSite);
    }

    @Test
    public void testNewInstance_forSite() throws Exception {
        Assert.assertTrue(DashboardSiteInfo.newInstance(TestUtil.createSite(), DashboardSiteType.COMMON) instanceof DashboardSiteInfoForCreatedSite);
    }

    @Test
    public void testGetChildSites() throws Exception {

    }

    @Test
    public void testGetName() throws Exception {
        final Site site = TestUtil.createSite();
        site.setTitle("title");
        final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON);
        Assert.assertEquals("title", siteInfo.getName());
    }

    @Test
    public void testGetChildSiteSettingsId() throws Exception {
        final Site site = TestUtil.createSite();
        site.setTitle("title");
        final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON);
        Assert.assertEquals(-1, siteInfo.getChildSiteSettingsId());
    }

    @Test
    public void testGetName_withLongSiteTitle() throws Exception {
        final Site site = TestUtil.createSite();
        site.setTitle("titleaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON);
        Assert.assertEquals("titleaaaaaaaaaaaaaaaaaaaaaaaaaaa...", siteInfo.getName());
    }

    @Test
    public void testGetLastUpdatedDate() throws Exception {
        final Site site = TestUtil.createSite();
        junit.framework.Assert.assertEquals(DateUtil.toCommonDateStr(site.getCreationDate()), DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON).getLastUpdatedDate());
    }

    @Test
    public void testGetUrl() throws Exception {
        final Site site = TestUtil.createSite();
        final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON);
        Assert.assertEquals(new SiteManager(site).getPublicUrl(), siteInfo.getUrl());
    }

    @Test
    public void testGetLimitedUrl() throws Exception {
        final Site site = TestUtil.createSite();
        final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON);
        Assert.assertEquals(HtmlUtil.limitName(new SiteManager(site).getPublicUrl(), 35), siteInfo.getLimitedUrl());
    }

    @Test
    public void testIsActivePENDING() throws Exception {
        final Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.PENDING);
        final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON);
        Assert.assertEquals(false, siteInfo.isActive());
    }

    @Test
    public void testIsActiveSUSPENDED() throws Exception {
        final Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.SUSPENDED);
        final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON);
        Assert.assertEquals(false, siteInfo.isActive());
    }

    @Test
    public void testIsActiveACTIVE() throws Exception {
        final Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON);
        Assert.assertEquals(true, siteInfo.isActive());
    }

    @Test
    public void testGetSiteId() throws Exception {
        final Site site = TestUtil.createSite();
        final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON);
        Assert.assertEquals(site.getSiteId(), siteInfo.getSiteId());
    }

    @Test(expected = UserNotLoginedException.class)
    public void testCanBeDeactivated_withoutLoginedUser() throws Exception {
        final Site site = TestUtil.createSite();
        final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON);
        siteInfo.canBeDeactivated();
    }

    @Test
    public void testCanBeDeactivated_withLoginedUser() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON);
        Assert.assertEquals(true, siteInfo.canBeDeactivated());
    }

    @Test
    public void testCanBeDeactivated_withLoginedUser_withNotActiveSite() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.PENDING);
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON);
        Assert.assertEquals(false, siteInfo.canBeDeactivated());
    }

    @Test
    public void testCanBeDeactivated_withLoginedUser_withNotActiveUserOnSiteRights() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        TestUtil.createUserOnSiteRightInactiveAdmin(user, site);
        final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON);
        Assert.assertEquals(false, siteInfo.canBeDeactivated());
    }

    @Test
    public void testCanBeDeactivated_withLoginedUser_withNotAdminUserOnSiteRights() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        new SiteManager(site).setSiteStatus(SiteStatus.ACTIVE);
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.EDITOR);
        final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON);
        Assert.assertEquals(false, siteInfo.canBeDeactivated());
    }

    @Test
    public void testGetFirstSitePageId() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager pageManager = TestUtil.createPageVersion(TestUtil.createPage(site));
        TestUtil.createPageVersion(TestUtil.createPage(site));
        TestUtil.createPageVersion(TestUtil.createPage(site));

        final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON);
        Assert.assertEquals(pageManager.getPageId(), siteInfo.getFirstSitePageId());
    }

    @Test
    public void testGetFirstSitePageId_withoutPages() throws Exception {
        final Site site = TestUtil.createSite();

        final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON);
        Assert.assertEquals(-1, siteInfo.getFirstSitePageId());
    }

    @Test
    public void testIsEditable_false() throws Exception {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();

        Assert.assertEquals(false, DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON).isEditable());
    }

    @Test
    public void testIsEditable_ADMINISTRATOR() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON);
        Assert.assertEquals(true, siteInfo.isEditable());
    }

    @Test
    public void testIsEditable_EDITOR() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.EDITOR);

        final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON);
        Assert.assertEquals(true, siteInfo.isEditable());
    }

    @Test
    public void testIsEditable_GUEST() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.GUEST);

        final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON);
        Assert.assertEquals(false, siteInfo.isEditable());
    }

    @Test
    public void testIsEditable_VISITOR() throws Exception {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.VISITOR);

        final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON);
        Assert.assertEquals(false, siteInfo.isEditable());
    }

    @Test
    public void testIsBlueprint_BLUEPRINT() throws Exception {
        final Site site = TestUtil.createSite();
        site.setType(SiteType.BLUEPRINT);

        final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(site, DashboardSiteType.BLUEPRINT);
        Assert.assertEquals(true, siteInfo.isBlueprint());
    }

    @Test
    public void testIsBlueprint_COMMON() throws Exception {
        final Site site = TestUtil.createSite();
        site.setType(SiteType.COMMON);

        final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON);
        Assert.assertEquals(false, siteInfo.isBlueprint());
    }

    @Test
    public void testIsNetworkSite_withChildSiteRegistrations() throws Exception {
        final Site site = TestUtil.createSite();
        site.setChildSiteRegistrationsId(Arrays.asList(1));

        final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(site, DashboardSiteType.NETWORK);
        Assert.assertEquals(true, siteInfo.isNetworkSite());
    }

    @Test
    public void testIsNetworkSite_withChildSiteSettings() throws Exception {
        final Site site = TestUtil.createSite();
        site.setChildSiteSettings(TestUtil.createChildSiteSettings(new Site(), site));

        final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(site, DashboardSiteType.CHILD);
        Assert.assertEquals(false, siteInfo.isNetworkSite());
        Assert.assertEquals(true, siteInfo.isChildSite());
    }

    @Test
    public void testGetDashboardWidgets() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftRegistrationForm registrationForm1 = TestUtil.createRegistrationForm(site);
        final DraftRegistrationForm registrationForm2 = TestUtil.createRegistrationForm(site);
        final DraftRegistrationForm registrationForm3 = TestUtil.createRegistrationForm(site);

        registrationForm1.setName("registrationForm1");
        registrationForm2.setName("registrationForm2");
        registrationForm3.setName("registrationForm3");

        final PageManager pageManager1 = TestUtil.createPageVersion(TestUtil.createPage(site));
        final PageManager pageManager2 = TestUtil.createPageVersion(TestUtil.createPage(site));
        final PageManager pageManager3 = TestUtil.createPageVersion(TestUtil.createPage(site));

        pageManager1.setName("pageManager1");
        pageManager2.setName("pageManager2");
        pageManager3.setName("pageManager3");

        final WidgetItem widgetItem11 = TestUtil.createWidgetItem();
        final WidgetItem widgetItem12 = TestUtil.createWidgetItem();

        final WidgetItem widgetItem21 = TestUtil.createWidgetItem();
        final WidgetItem widgetItem22 = TestUtil.createWidgetItem();

        final WidgetItem widgetItem31 = TestUtil.createWidgetItem();
        final WidgetItem widgetItem32 = TestUtil.createWidgetItem();


        widgetItem11.setDraftItem(registrationForm1);
        widgetItem12.setDraftItem(registrationForm2);

        widgetItem21.setDraftItem(registrationForm1);
        widgetItem22.setDraftItem(registrationForm2);

        widgetItem31.setDraftItem(registrationForm1);
        widgetItem32.setDraftItem(registrationForm2);


        pageManager1.addWidget(widgetItem11);
        pageManager1.addWidget(widgetItem12);

        pageManager2.addWidget(widgetItem21);
        pageManager2.addWidget(widgetItem22);

        pageManager3.addWidget(widgetItem31);
        pageManager3.addWidget(widgetItem32);

        final DashboardSiteInfo siteInfo = DashboardSiteInfo.newInstance(site, DashboardSiteType.COMMON);
        Assert.assertEquals(8, siteInfo.getDashboardWidgets().size());

        Assert.assertNull(siteInfo.getDashboardWidgets().get(0).getPageName());
        Assert.assertEquals("Account Registration: registrationForm3", new ItemTypeManager(siteInfo.getDashboardWidgets().get(0).getItemType()).getItemName() + ": " + siteInfo.getDashboardWidgets().get(0).getItemName());

        Assert.assertNull(siteInfo.getDashboardWidgets().get(1).getPageName());
        Assert.assertEquals("Menu: Default site menu", new ItemTypeManager(siteInfo.getDashboardWidgets().get(1).getItemType()).getItemName() + ": " + siteInfo.getDashboardWidgets().get(1).getItemName());

        Assert.assertEquals("pageManager1 / Account Registration: registrationForm1", siteInfo.getDashboardWidgets().get(2).getPageName() + " / " + new ItemTypeManager(siteInfo.getDashboardWidgets().get(2).getItemType()).getItemName() + ": " + siteInfo.getDashboardWidgets().get(2).getItemName());
        Assert.assertEquals("pageManager1 / Account Registration: registrationForm2", siteInfo.getDashboardWidgets().get(3).getPageName() + " / " + new ItemTypeManager(siteInfo.getDashboardWidgets().get(3).getItemType()).getItemName() + ": " + siteInfo.getDashboardWidgets().get(3).getItemName());
        Assert.assertEquals("pageManager2 / Account Registration: registrationForm1", siteInfo.getDashboardWidgets().get(4).getPageName() + " / " + new ItemTypeManager(siteInfo.getDashboardWidgets().get(4).getItemType()).getItemName() + ": " + siteInfo.getDashboardWidgets().get(4).getItemName());
        Assert.assertEquals("pageManager2 / Account Registration: registrationForm2", siteInfo.getDashboardWidgets().get(5).getPageName() + " / " + new ItemTypeManager(siteInfo.getDashboardWidgets().get(5).getItemType()).getItemName() + ": " + siteInfo.getDashboardWidgets().get(5).getItemName());
        Assert.assertEquals("pageManager3 / Account Registration: registrationForm1", siteInfo.getDashboardWidgets().get(6).getPageName() + " / " + new ItemTypeManager(siteInfo.getDashboardWidgets().get(6).getItemType()).getItemName() + ": " + siteInfo.getDashboardWidgets().get(6).getItemName());
        Assert.assertEquals("pageManager3 / Account Registration: registrationForm2", siteInfo.getDashboardWidgets().get(7).getPageName() + " / " + new ItemTypeManager(siteInfo.getDashboardWidgets().get(7).getItemType()).getItemName() + ": " + siteInfo.getDashboardWidgets().get(7).getItemName());
    }
}

