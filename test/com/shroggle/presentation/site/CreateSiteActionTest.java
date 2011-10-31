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

package com.shroggle.presentation.site;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.CreateSiteRequest;
import com.shroggle.logic.site.SiteByUrlGetterMock;
import com.shroggle.logic.site.SiteCreatorOrUpdaterMock;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.presentation.account.dashboard.DashboardSiteType;
import com.shroggle.presentation.account.dashboard.siteInfo.DashboardSiteInfo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.filesystem.FileSystemMock;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.Assert.assertEquals;

/**
 * @author Stasuk Artem
 */
@RunWith(TestRunnerWithMockServices.class)
public class CreateSiteActionTest {

    @Before
    public void before() {
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        Template template = new Template();
        template.setDirectory("a");
        Theme theme = new Theme();
        theme.setFile("b");
        template.getThemes().add(theme);
        fileSystemMock.putTemplate(template);
        fileSystemMock.setLoginPageDefaultHtml("FFF");
        fileSystemMock.setLoginAdminPageDefaultHtml("FFF1");

        final ActionBeanContext context = new ActionBeanContext();
        context.setRequest(new MockHttpServletRequest("", ""));
        action.setContext(context);

        siteCreatorOrUpdaterMock.setSite(new Site());
    }

    @Test
    public void createForthUserSite() {
        final User user = TestUtil.createUserAndLogin();
        for (int i = 0; i < 20; i++) {
            Site site = TestUtil.createSite();
            TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        }
        sessionStorage.setNoBotCode(null, "createSite", "GG");

        final ResolutionMock resolutionMock = (ResolutionMock) action.createOrEdit();

        Assert.assertTrue(action.isShowNoBotCodeConfirm());
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void edit_withThirtySites() {
        final User user = TestUtil.createUserAndLogin();
        for (int i = 0; i < 30; i++) {
            TestUtil.createUserOnSiteRightActiveAdmin(user, TestUtil.createSite());
        }
        sessionStorage.setNoBotCode(null, "createSite", "GG");

        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        action.setSiteId(site.getSiteId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.createOrEdit();

        Assert.assertFalse("No matter how many sites user has - we must not show word verification for site editing. Tolik", action.isShowNoBotCodeConfirm());
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void createBlueprint() {
        final User user = TestUtil.createUserAndLogin();
        for (int i = 0; i < 30; i++) {
            TestUtil.createUserOnSiteRightActiveAdmin(user, TestUtil.createSite());
        }
        sessionStorage.setNoBotCode(null, "createSite", "GG");

        action.setSiteType(SiteType.BLUEPRINT);
        final ResolutionMock resolutionMock = (ResolutionMock) action.createOrEdit();

        Assert.assertFalse("No matter how many sites user has - we must not show word verification for blueprints. Tolik", action.isShowNoBotCodeConfirm());
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void createWithoutSiteType() {
        TestUtil.createUserAndLogin();

        final ResolutionMock resolutionMock = (ResolutionMock) action.createOrEdit();

        Assert.assertNotNull(action.getKeywordsGroups());
        Assert.assertEquals(SiteType.COMMON, action.getSiteType());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());
        Assert.assertNull(action.getTitle());
        Assert.assertNull(action.getSubDomain());
        Assert.assertFalse(action.isShowNoBotCodeConfirm());
    }

    @Test
    public void createWithChildSiteSettings() {
        TestUtil.createUserAndLogin();

        FilledForm filledForm = new FilledForm();
        FilledFormItem domainNameItem = TestUtil.createFilledFormItem(FormItemName.YOUR_OWN_DOMAIN_NAME);
        domainNameItem.setValue("domain name");
        FilledFormItem siteNameItem = TestUtil.createFilledFormItem(FormItemName.YOUR_PAGE_SITE_NAME);
        siteNameItem.setValue("site name");
        filledForm.setFilledFormItems(Arrays.asList(domainNameItem, siteNameItem));
        persistance.putFilledForm(filledForm);

        ChildSiteSettings settings = new ChildSiteSettings();
        settings.setFilledFormId(filledForm.getFilledFormId());
        persistance.putChildSiteSettings(settings);
        action.setCreateChildSite(true);
        action.setSettingsId(settings.getChildSiteSettingsId());

        final ResolutionMock resolutionMock = (ResolutionMock) action.createOrEdit();

        Assert.assertNull(action.getNetworkName());
        Assert.assertNull(action.getChildSiteRegistrationId());
        Assert.assertFalse(action.isBrandedAllowShroggleDomain());
        Assert.assertNull(action.getBrandedUrl());
        Assert.assertNotNull(action.getKeywordsGroups());
        Assert.assertEquals(SiteType.COMMON, action.getSiteType());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());

        Assert.assertNull(action.getSiteId());
        Assert.assertEquals("site name", action.getTitle());
        Assert.assertFalse(action.isShowNoBotCodeConfirm());
        Assert.assertEquals("domain name", action.getSubDomain());
    }

    @Test
    public void createWithChildSiteSettingsWithBrandedUrl() {
        TestUtil.createUserAndLogin();

        final DraftChildSiteRegistration draftChildSiteRegistration = new DraftChildSiteRegistration();
        persistance.putItem(draftChildSiteRegistration);

        final WorkChildSiteRegistration workChildSiteRegistration = new WorkChildSiteRegistration();
        workChildSiteRegistration.setName("333");
        workChildSiteRegistration.setBrandedUrl("fff.com");
        workChildSiteRegistration.setBrandedAllowShroggleDomain(true);
        workChildSiteRegistration.setId(draftChildSiteRegistration.getId());
        persistance.putItem(workChildSiteRegistration);

        FilledForm filledForm = new FilledForm();
        FilledFormItem domainNameItem = TestUtil.createFilledFormItem(FormItemName.YOUR_OWN_DOMAIN_NAME);
        domainNameItem.setValue("domain name");
        FilledFormItem siteNameItem = TestUtil.createFilledFormItem(FormItemName.YOUR_PAGE_SITE_NAME);
        siteNameItem.setValue("site name");
        filledForm.setFilledFormItems(Arrays.asList(domainNameItem, siteNameItem));
        persistance.putFilledForm(filledForm);

        ChildSiteSettings settings = new ChildSiteSettings();
        settings.setChildSiteRegistration(draftChildSiteRegistration);
        settings.setFilledFormId(filledForm.getFilledFormId());
        persistance.putChildSiteSettings(settings);
        action.setCreateChildSite(true);
        action.setSettingsId(settings.getChildSiteSettingsId());

        final ResolutionMock resolutionMock = (ResolutionMock) action.createOrEdit();

        TestUtil.assertIntAndBigInt(workChildSiteRegistration.getId(), action.getChildSiteRegistrationId());
        Assert.assertEquals("333", action.getNetworkName());
        Assert.assertTrue(action.isBrandedAllowShroggleDomain());
        Assert.assertEquals("fff.com", action.getBrandedUrl());
        Assert.assertNotNull(action.getKeywordsGroups());
        Assert.assertEquals(SiteType.COMMON, action.getSiteType());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());

        Assert.assertNull(action.getSiteId());
        Assert.assertEquals("site name", action.getTitle());
        Assert.assertEquals("domain name", action.getSubDomain());
    }

    @Test
    public void createWithChildSiteSettingsForDraftChildSiteRegistration() {
        TestUtil.createUserAndLogin();

        final DraftChildSiteRegistration draftChildSiteRegistration = new DraftChildSiteRegistration();
        draftChildSiteRegistration.setBrandedUrl("fff.com");
        draftChildSiteRegistration.setName("333");
        draftChildSiteRegistration.setBrandedAllowShroggleDomain(false);
        persistance.putItem(draftChildSiteRegistration);

        FilledForm filledForm = new FilledForm();
        FilledFormItem domainNameItem = TestUtil.createFilledFormItem(FormItemName.YOUR_OWN_DOMAIN_NAME);
        domainNameItem.setValue("domain name");
        FilledFormItem siteNameItem = TestUtil.createFilledFormItem(FormItemName.YOUR_PAGE_SITE_NAME);
        siteNameItem.setValue("site name");
        filledForm.setFilledFormItems(Arrays.asList(domainNameItem, siteNameItem));
        persistance.putFilledForm(filledForm);

        ChildSiteSettings settings = new ChildSiteSettings();
        settings.setChildSiteRegistration(draftChildSiteRegistration);
        settings.setFilledFormId(filledForm.getFilledFormId());
        persistance.putChildSiteSettings(settings);
        action.setCreateChildSite(true);
        action.setSettingsId(settings.getChildSiteSettingsId());

        final ResolutionMock resolutionMock = (ResolutionMock) action.createOrEdit();

        TestUtil.assertIntAndBigInt(draftChildSiteRegistration.getId(), action.getChildSiteRegistrationId());
        Assert.assertEquals("333", action.getNetworkName());
        Assert.assertTrue(action.isCreateChildSite());
        Assert.assertFalse(action.isBrandedAllowShroggleDomain());
        Assert.assertEquals("fff.com", action.getBrandedUrl());
        Assert.assertNotNull(action.getKeywordsGroups());
        Assert.assertEquals(SiteType.COMMON, action.getSiteType());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());

        Assert.assertNull(action.getSiteId());
        Assert.assertEquals("site name", action.getTitle());
        Assert.assertEquals("domain name", action.getSubDomain());
    }

    @Test
    public void editWithChildSiteSettingsWithBrandedUrl() {
        final User user = TestUtil.createUserAndLogin();

        final Site networkSite = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(networkSite, SiteAccessLevel.ADMINISTRATOR);
        final DraftChildSiteRegistration draftChildSiteRegistration = TestUtil.createChildSiteRegistration(networkSite);
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(draftChildSiteRegistration, networkSite);
        childSiteSettings.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        childSiteSettings.setSiteStatus(SiteStatus.ACTIVE);

        final WorkChildSiteRegistration workChildSiteRegistration = new WorkChildSiteRegistration();
        workChildSiteRegistration.setName("333");
        workChildSiteRegistration.setBrandedUrl("fff.com");
        workChildSiteRegistration.setBrandedAllowShroggleDomain(true);
        workChildSiteRegistration.setId(draftChildSiteRegistration.getId());
        persistance.putItem(workChildSiteRegistration);

        FilledForm filledForm = new FilledForm();
        FilledFormItem domainNameItem = TestUtil.createFilledFormItem(FormItemName.YOUR_OWN_DOMAIN_NAME);
        domainNameItem.setValue("domain name");
        FilledFormItem siteNameItem = TestUtil.createFilledFormItem(FormItemName.YOUR_PAGE_SITE_NAME);
        siteNameItem.setValue("site name");
        filledForm.setFilledFormItems(Arrays.asList(domainNameItem, siteNameItem));
        persistance.putFilledForm(filledForm);

        ChildSiteSettings settings = TestUtil.createChildSiteSettings(draftChildSiteRegistration, networkSite);
        settings.setFilledFormId(filledForm.getFilledFormId());

        final Site site = TestUtil.createSite();
        site.setChildSiteSettings(settings);
        site.setBrandedSubDomain("g");
        site.setCustomUrl("1");
        site.setSubDomain("22");

        TestUtil.createUserOnSiteRightActiveAdmin(user, site);

        action.setCreateChildSite(true);
        action.setSettingsId(settings.getChildSiteSettingsId());
        action.setSiteId(site.getSiteId());

        final ResolutionMock resolutionMock = (ResolutionMock) action.createOrEdit();

        TestUtil.assertIntAndBigInt(workChildSiteRegistration.getId(), action.getChildSiteRegistrationId());
        Assert.assertEquals("333", action.getNetworkName());
        Assert.assertTrue(action.isBrandedAllowShroggleDomain());
        Assert.assertEquals("fff.com", action.getBrandedUrl());
        Assert.assertNotNull(action.getKeywordsGroups());
        Assert.assertEquals(SiteType.COMMON, action.getSiteType());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());

        TestUtil.assertIntAndBigInt(site.getId(), action.getSiteId());
        Assert.assertEquals("title", action.getTitle());
        Assert.assertEquals("22", action.getSubDomain());
    }

    @Test
    public void create_withCreateChildSiteWithoutChildSiteSettings() {
        TestUtil.createUserAndLogin();

        FilledForm filledForm = new FilledForm();
        FilledFormItem domainNameItem = TestUtil.createFilledFormItem(FormItemName.YOUR_OWN_DOMAIN_NAME);
        domainNameItem.setValue("domain name");
        FilledFormItem siteNameItem = TestUtil.createFilledFormItem(FormItemName.YOUR_PAGE_SITE_NAME);
        siteNameItem.setValue("site name");
        filledForm.setFilledFormItems(Arrays.asList(domainNameItem, siteNameItem));
        persistance.putFilledForm(filledForm);

        ChildSiteSettings settings = new ChildSiteSettings();
        settings.setFilledFormId(filledForm.getFilledFormId());
        persistance.putChildSiteSettings(settings);
        action.setCreateChildSite(true);
        action.setSettingsId(null);

        final ResolutionMock resolutionMock = (ResolutionMock) action.createOrEdit();

        Assert.assertNotNull(action.getKeywordsGroups());
        Assert.assertEquals(SiteType.COMMON, action.getSiteType());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());

        Assert.assertNull(action.getTitle());
        Assert.assertNull(action.getSubDomain());
    }

    @Test
    public void create_withCreateChildSiteWithWrongChildSiteSettingsId() {
        TestUtil.createUserAndLogin();

        FilledForm filledForm = new FilledForm();
        FilledFormItem domainNameItem = TestUtil.createFilledFormItem(FormItemName.YOUR_OWN_DOMAIN_NAME);
        domainNameItem.setValue("domain name");
        FilledFormItem siteNameItem = TestUtil.createFilledFormItem(FormItemName.YOUR_PAGE_SITE_NAME);
        siteNameItem.setValue("site name");
        filledForm.setFilledFormItems(Arrays.asList(domainNameItem, siteNameItem));
        persistance.putFilledForm(filledForm);

        ChildSiteSettings settings = new ChildSiteSettings();
        settings.setFilledFormId(filledForm.getFilledFormId());
        persistance.putChildSiteSettings(settings);
        action.setCreateChildSite(true);
        action.setSettingsId(-1);

        final ResolutionMock resolutionMock = (ResolutionMock) action.createOrEdit();

        Assert.assertNotNull(action.getKeywordsGroups());
        Assert.assertEquals(SiteType.COMMON, action.getSiteType());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());

        Assert.assertNull(action.getTitle());
        Assert.assertNull(action.getSubDomain());
    }

    @Test
    public void create_withCreateChildSiteWithoutFilledForm() {
        TestUtil.createUserAndLogin();

        FilledForm filledForm = new FilledForm();
        FilledFormItem domainNameItem = TestUtil.createFilledFormItem(FormItemName.YOUR_OWN_DOMAIN_NAME);
        domainNameItem.setValue("domain name");
        FilledFormItem siteNameItem = TestUtil.createFilledFormItem(FormItemName.YOUR_PAGE_SITE_NAME);
        siteNameItem.setValue("site name");
        filledForm.setFilledFormItems(Arrays.asList(domainNameItem, siteNameItem));
        persistance.putFilledForm(filledForm);

        ChildSiteSettings settings = new ChildSiteSettings();
        //settings.setFilledFormId(filledForm.getFilledFormId());
        persistance.putChildSiteSettings(settings);
        action.setCreateChildSite(true);
        action.setSettingsId(settings.getChildSiteSettingsId());

        final ResolutionMock resolutionMock = (ResolutionMock) action.createOrEdit();

        Assert.assertNotNull(action.getKeywordsGroups());
        Assert.assertEquals(SiteType.COMMON, action.getSiteType());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());

        Assert.assertNull(action.getTitle());
        Assert.assertNull(action.getSubDomain());
    }

    @Test
    public void create_withCreateChildSiteWithoutFilledFormItems() {
        TestUtil.createUserAndLogin();

        FilledForm filledForm = new FilledForm();
        FilledFormItem domainNameItem = TestUtil.createFilledFormItem(FormItemName.YOUR_OWN_DOMAIN_NAME);
        domainNameItem.setValue("domain name");
        FilledFormItem siteNameItem = TestUtil.createFilledFormItem(FormItemName.YOUR_PAGE_SITE_NAME);
        siteNameItem.setValue("site name");
        //filledForm.setFilledFormItems(Arrays.asList(domainNameItem, siteNameItem));
        persistance.putFilledForm(filledForm);

        ChildSiteSettings settings = new ChildSiteSettings();
        settings.setFilledFormId(filledForm.getFilledFormId());
        persistance.putChildSiteSettings(settings);
        action.setCreateChildSite(true);
        action.setSettingsId(settings.getChildSiteSettingsId());

        final ResolutionMock resolutionMock = (ResolutionMock) action.createOrEdit();

        Assert.assertNotNull(action.getKeywordsGroups());
        Assert.assertEquals(SiteType.COMMON, action.getSiteType());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());

        Assert.assertNull(action.getTitle());
        Assert.assertEquals("Default domain name created", "site", action.getSubDomain());
    }

    @Test
    public void createWithSiteTypeBlueprint() {
        TestUtil.createUserAndLogin();

        action.setSiteType(SiteType.BLUEPRINT);
        final ResolutionMock resolutionMock = (ResolutionMock) action.createOrEdit();

        Assert.assertNotNull(action.getKeywordsGroups());
        Assert.assertEquals(SiteBlueprintRightType.CAN_ADD_PAGES, action.getBlueprintRightType());
        Assert.assertEquals(SiteType.BLUEPRINT, action.getSiteType());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void edit() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        site.setSubDomain("bb");
        site.setCustomUrl("tt");
        persistance.putSite(site);

        KeywordsGroup keywordsGroup = TestUtil.createKeywordsGroup(site);
        keywordsGroup.setName("c");
        keywordsGroup.setValue("f");

        action.setSiteId(site.getSiteId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.createOrEdit();

        Assert.assertEquals("bb", action.getSubDomain());
        Assert.assertEquals("tt", action.getCustomUrl());
        Assert.assertEquals((Integer) site.getSiteId(), action.getSiteId());
        Assert.assertNotNull(action.getKeywordsGroups());
        TestUtil.assertIntAndBigInt(site.getId(), action.getSiteId());
        Assert.assertFalse(action.isShowNoBotCodeConfirm());
        Assert.assertEquals("c", action.getKeywordsGroups().get(0).getName());
        Assert.assertEquals("f", action.getKeywordsGroups().get(0).getValue());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void editWithSiteTypeBlueprint() {
        final Site site = TestUtil.createSite();
        site.setDescription("FFF");
        site.setType(SiteType.BLUEPRINT);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        persistance.putSite(site);

        KeywordsGroup keywordsGroup = TestUtil.createKeywordsGroup(site);
        keywordsGroup.setName("c");
        keywordsGroup.setValue("f");

        action.setSiteId(site.getSiteId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.createOrEdit();

        Assert.assertNull(action.getSubDomain());
        Assert.assertNull(action.getCustomUrl());
        Assert.assertEquals((Integer) site.getSiteId(), action.getSiteId());
        Assert.assertNotNull(action.getKeywordsGroups());
        Assert.assertEquals("c", action.getKeywordsGroups().get(0).getName());
        Assert.assertEquals("f", action.getKeywordsGroups().get(0).getValue());
        Assert.assertEquals("FFF", action.getDescription());
        Assert.assertEquals(SiteType.BLUEPRINT, action.getSiteType());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void editWithoutSite() {
        TestUtil.createUserAndLogin();

        final ResolutionMock resolutionMock = (ResolutionMock) action.createOrEdit();

        Assert.assertNull(action.getSiteId());
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void editWithNotMySite() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserAndLogin();

        action.setSiteId(site.getSiteId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.createOrEdit();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void editWithNotFoundSite() {
        TestUtil.createUserAndLogin();
        action.setSiteId(1);
        final ResolutionMock resolutionMock = (ResolutionMock) action.createOrEdit();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void editWithNullKeywords() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        site.setSubDomain("bb");
        site.setCustomUrl("www.super.com");
        persistance.putSite(site);

        action.setSiteId(site.getSiteId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.createOrEdit();

        Assert.assertEquals("bb", action.getSubDomain());
        Assert.assertEquals("www.super.com", action.getCustomUrl());
        Assert.assertEquals((Integer) site.getSiteId(), action.getSiteId());
        Assert.assertNotNull(action.getKeywordsGroups());
        Assert.assertEquals(0, action.getKeywordsGroups().size());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void editWithEmptyKeywords() {
        final Site site = TestUtil.createSite();
        site.setSubDomain("bb");
        site.setCustomUrl("tt");

        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        action.setSiteId(site.getSiteId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.createOrEdit();

        Assert.assertEquals("bb", action.getSubDomain());
        Assert.assertEquals("tt", action.getCustomUrl());
        Assert.assertEquals((Integer) site.getSiteId(), action.getSiteId());
        Assert.assertNotNull(action.getKeywordsGroups());
        Assert.assertEquals(0, action.getKeywordsGroups().size());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void persist_forCommonSite() {
        TestUtil.createUserAndLogin();
        sessionStorage.setNoBotCode(null, "createSite", "A");
        final Icon icon = TestUtil.createIcon();
        action.setNoBotCodeConfirm("A");
        action.setSubDomain("a");
        action.setTitle("t");
        action.setCustomUrl("AaBa.com/ ");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.getKeywordsGroups().get(0).setValue("ta-ta");
        action.getKeywordsGroups().get(0).setName("KeywoRds");
        action.setIconId(icon.getIconId());
        action.setSiteType(SiteType.COMMON);
        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        final CreateSiteRequest request = siteCreatorOrUpdaterMock.getRequest();

        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(1, request.getKeywordsGroups().size());
        Assert.assertEquals(icon, siteCreatorOrUpdaterMock.getSite().getIcon());
        Assert.assertEquals("aaba.com/", request.getCustomUrl());
        Assert.assertEquals("ta-ta", request.getKeywordsGroups().get(0).getValue());
        Assert.assertEquals("keywords", request.getKeywordsGroups().get(0).getName());
        Assert.assertEquals(SelectSiteDesignPageAction.class, resolutionMock.getRedirectByAction());

        final DashboardSiteInfo dashboardSiteInfo = DashboardSiteInfo.newInstance(siteCreatorOrUpdaterMock.getSite(), DashboardSiteType.COMMON);
        Assert.assertEquals(dashboardSiteInfo.hashCode(), ServiceLocator.getSessionStorage().getSelectedSiteInfoHashForDashboard(action).intValue());
    }

    @Test
    public void persist_forCommonBlueprint() {
        TestUtil.createUserAndLogin();
        sessionStorage.setNoBotCode(null, "createSite", "A");
        final Icon icon = TestUtil.createIcon();
        action.setNoBotCodeConfirm("A");
        action.setSubDomain("a");
        action.setTitle("t");
        action.setCustomUrl("AaBa.com/ ");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.getKeywordsGroups().get(0).setValue("ta-ta");
        action.getKeywordsGroups().get(0).setName("KeywoRds");
        action.setIconId(icon.getIconId());
        action.setSiteType(SiteType.BLUEPRINT);

        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        final CreateSiteRequest request = siteCreatorOrUpdaterMock.getRequest();

        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(1, request.getKeywordsGroups().size());
        Assert.assertEquals(icon, siteCreatorOrUpdaterMock.getSite().getIcon());
        Assert.assertEquals("aaba.com/", request.getCustomUrl());
        Assert.assertEquals("ta-ta", request.getKeywordsGroups().get(0).getValue());
        Assert.assertEquals("keywords", request.getKeywordsGroups().get(0).getName());
        Assert.assertEquals(SelectSiteDesignPageAction.class, resolutionMock.getRedirectByAction());

        final DashboardSiteInfo dashboardSiteInfo = DashboardSiteInfo.newInstance(siteCreatorOrUpdaterMock.getSite(), DashboardSiteType.BLUEPRINT);
        Assert.assertEquals(dashboardSiteInfo.hashCode(), ServiceLocator.getSessionStorage().getSelectedSiteInfoHashForDashboard(action).intValue());
    }

    @Test
    public void persist_forChildSite() {
        TestUtil.createUserAndLogin();
        sessionStorage.setNoBotCode(null, "createSite", "A");
        final Icon icon = TestUtil.createIcon();
        action.setNoBotCodeConfirm("A");
        action.setSubDomain("a");
        action.setTitle("t");
        action.setCustomUrl("AaBa.com/ ");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.getKeywordsGroups().get(0).setValue("ta-ta");
        action.getKeywordsGroups().get(0).setName("KeywoRds");
        action.setIconId(icon.getIconId());
        action.setSiteType(SiteType.COMMON);
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings();
        action.setSettingsId(childSiteSettings.getChildSiteSettingsId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        final CreateSiteRequest request = siteCreatorOrUpdaterMock.getRequest();

        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(1, request.getKeywordsGroups().size());
        Assert.assertEquals(icon, siteCreatorOrUpdaterMock.getSite().getIcon());
        Assert.assertEquals("aaba.com/", request.getCustomUrl());
        Assert.assertEquals("ta-ta", request.getKeywordsGroups().get(0).getValue());
        Assert.assertEquals("keywords", request.getKeywordsGroups().get(0).getName());
        Assert.assertEquals(SelectSiteDesignPageAction.class, resolutionMock.getRedirectByAction());

        final DashboardSiteInfo dashboardSiteInfo = DashboardSiteInfo.newInstance(siteCreatorOrUpdaterMock.getSite(), DashboardSiteType.CHILD);
        Assert.assertEquals(dashboardSiteInfo.hashCode(), ServiceLocator.getSessionStorage().getSelectedSiteInfoHashForDashboard(action).intValue());
    }

    @Test
    public void persistFirstUserSite() {
        TestUtil.createUserAndLogin();
        sessionStorage.setNoBotCode(null, "createSite", "A");

        //Setting wrong verification code, no sites were created yet so it should do it.
        action.setNoBotCodeConfirm("wrong");
        action.setSubDomain("a");
        action.setTitle("t");
        action.setCustomUrl("AaBa.com/ ");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.getKeywordsGroups().get(0).setValue("ta-ta");
        action.getKeywordsGroups().get(0).setName("KeywoRds");
        action.persist();

        Assert.assertNotNull(siteCreatorOrUpdaterMock.getRequest());
    }

    @Test
    public void persistForthUserSite() {
        final User user = TestUtil.createUserAndLogin();
        for (int i = 0; i < 20; i++) {
            Site site = TestUtil.createSite();
            TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        }
        sessionStorage.setNoBotCode(null, "createSite", "GG");

        action.setNoBotCodeConfirm("FF");
        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        Assert.assertNotNull(action.getContext().getValidationErrors().get("noBotCodeConfirm"));
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void persistWithBigTitle() {
        TestUtil.createUserAndLogin();
        sessionStorage.setNoBotCode(null, "createSite", "A");

        action.setNoBotCodeConfirm("A");
        action.setSubDomain("a");
        action.setTitle(TestUtil.createString(251));
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.persist();

        Assert.assertEquals(250, siteCreatorOrUpdaterMock.getRequest().getTitle().length());
    }

    @Test
    public void persistWithNotUniqueCustomUrlInOtherCase() {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        site.setCustomUrl("aa");
        sessionStorage.setNoBotCode(null, "createSite", "A");
        siteByUrlGetterMock.setSite(site);

        action.setNoBotCodeConfirm("A");
        action.setSubDomain("a");
        action.setCustomUrl("aA");
        action.setTitle("ggg");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.persist();

        Assert.assertEquals(2, action.getContext().getValidationErrors().size());
        Assert.assertNotNull(action.getContext().getValidationErrors().get("customUrl"));
        Assert.assertNotNull(action.getContext().getValidationErrors().get("siteUrlPrefix"));
    }

    @Test
    public void persistWithBigCustomUrl() {
        TestUtil.createUserAndLogin();
        sessionStorage.setNoBotCode(null, "createSite", "A");

        action.setNoBotCodeConfirm("A");
        action.setSubDomain("a");
        action.setTitle("g");
        action.setCustomUrl(TestUtil.createString(251));
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.persist();

        Assert.assertEquals(250, siteCreatorOrUpdaterMock.getRequest().getCustomUrl().length());

    }

    @Test
    public void persistWithUpperCustomUrl() {
        TestUtil.createUserAndLogin();
        sessionStorage.setNoBotCode(null, "createSite", "A");

        action.setNoBotCodeConfirm("A");
        action.setSubDomain("a");
        action.setTitle("g");
        action.setCustomUrl("aAaA");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.persist();

        Assert.assertEquals("aaaa", siteCreatorOrUpdaterMock.getRequest().getCustomUrl());

    }

    @Test
    public void persistWithBrandedSubDomain() {
        TestUtil.createUserAndLogin();
        sessionStorage.setNoBotCode(null, "createSite", "A");

        action.setNoBotCodeConfirm("A");
        action.setSubDomain("a");
        action.setBrandedSubDomain("ggg");
        action.setTitle("g");
        action.setCustomUrl("aAaA");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.persist();

        Assert.assertNull(siteCreatorOrUpdaterMock.getRequest().getBrandedSubDomain());
        Assert.assertEquals("aaaa", siteCreatorOrUpdaterMock.getRequest().getCustomUrl());

    }

    @Test
    public void persistWithUpperSubDomain() {
        TestUtil.createUserAndLogin();
        sessionStorage.setNoBotCode(null, "createSite", "A");

        action.setNoBotCodeConfirm("A");
        action.setSubDomain("aA");
        action.setTitle("g");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.persist();

        Assert.assertEquals("aa", siteCreatorOrUpdaterMock.getRequest().getSubDomain());

    }

    @Test
    public void persistWithBigKeywordsGroupName() {
        TestUtil.createUserAndLogin();
        sessionStorage.setNoBotCode(null, "createSite", "A");

        action.setNoBotCodeConfirm("A");
        action.setSubDomain("a");
        action.setTitle("d");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.getKeywordsGroups().get(0).setName(TestUtil.createString(251));
        action.getKeywordsGroups().get(0).setValue("f");
        action.persist();

        Assert.assertEquals(250, siteCreatorOrUpdaterMock.getRequest().getKeywordsGroups().get(0).getName().length());

    }

    @Test
    public void persistWithDescription() {
        TestUtil.createUserAndLogin();
        sessionStorage.setNoBotCode(null, "createSite", "A");

        action.setDescription("F");
        action.setNoBotCodeConfirm("A");
        action.setSubDomain("a");
        action.setTitle("t");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(0, siteCreatorOrUpdaterMock.getRequest().getKeywordsGroups().size());
        Assert.assertEquals(SelectSiteDesignPageAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals("F", siteCreatorOrUpdaterMock.getRequest().getBlueprintDescription());
    }

    @Test
    public void persistWithSiteTypeBlueprint() {
        TestUtil.createUserAndLogin();
        sessionStorage.setNoBotCode(null, "createSite", "A");

        action.setSiteType(SiteType.BLUEPRINT);
        action.setNoBotCodeConfirm("A");
        action.setDescription("GGG");
        action.setTitle("t");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.getKeywordsGroups().get(0).setValue("ta-ta");
        action.getKeywordsGroups().get(0).setName("KeywoRds");
        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        final CreateSiteRequest request = siteCreatorOrUpdaterMock.getRequest();
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(1, request.getKeywordsGroups().size());
        Assert.assertEquals(SiteType.BLUEPRINT, request.getSiteType());
        Assert.assertNull("Blueprint site doesn't have aliase url!", request.getCustomUrl());
        Assert.assertNull("Blueprint site doesn't have prefix url!", request.getSubDomain());
        Assert.assertEquals("ta-ta", request.getKeywordsGroups().get(0).getValue());
        Assert.assertEquals("keywords", request.getKeywordsGroups().get(0).getName());
        Assert.assertEquals(SelectSiteDesignPageAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void persistWithSiteTypeBlueprintWithSubDomainAndCustomUrl() {
        TestUtil.createUserAndLogin();
        sessionStorage.setNoBotCode(null, "createSite", "A");

        action.setSubDomain("f");
        action.setCustomUrl("g");
        action.setSiteType(SiteType.BLUEPRINT);
        action.setNoBotCodeConfirm("A");
        action.setTitle("t");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.getKeywordsGroups().get(0).setValue("ta-ta");
        action.getKeywordsGroups().get(0).setName("KeywoRds");
        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        final CreateSiteRequest request = siteCreatorOrUpdaterMock.getRequest();
        Assert.assertEquals(1, request.getKeywordsGroups().size());
        Assert.assertEquals(SiteType.BLUEPRINT, request.getSiteType());
        Assert.assertEquals("g", request.getCustomUrl());
        Assert.assertEquals("f", request.getSubDomain());
        Assert.assertEquals("ta-ta", request.getKeywordsGroups().get(0).getValue());
        Assert.assertEquals("keywords", request.getKeywordsGroups().get(0).getName());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(SelectSiteDesignPageAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void persistForEdit() {
        final Site site = TestUtil.createSite();
        site.setSubDomain("ff");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        sessionStorage.setNoBotCode(null, "createSite", "A");

        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();
        site.setDefaultFormId(registrationForm.getFormId());

        KeywordsGroup keywordsGroup = TestUtil.createKeywordsGroup(site);
        keywordsGroup.setName("all");
        keywordsGroup.setValue("way");

        action.setSiteId(site.getSiteId());
        action.setSubDomain("a");
        action.setTitle("1");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.getKeywordsGroups().get(0).setValue("ta-ta");
        action.getKeywordsGroups().get(0).setName("nameKeywords");
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.getKeywordsGroups().get(1).setName("News");
        action.getKeywordsGroups().get(1).setValue("dead");
        action.setNoBotCodeConfirm("A");
        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        final CreateSiteRequest request = siteCreatorOrUpdaterMock.getRequest();
        Assert.assertEquals(2, request.getKeywordsGroups().size());
        Assert.assertEquals("ta-ta", request.getKeywordsGroups().get(0).getValue());
        Assert.assertEquals("dead", request.getKeywordsGroups().get(1).getValue());
        Assert.assertEquals("a", request.getSubDomain());
        Assert.assertEquals("1", request.getTitle());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void persistForEditWithoutIAgree() {
        final Site site = TestUtil.createSite();
        site.setSubDomain("ff");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        sessionStorage.setNoBotCode(null, "createSite", "A");

        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();
        site.setDefaultFormId(registrationForm.getFormId());

        KeywordsGroup keywordsGroup = TestUtil.createKeywordsGroup(site);
        keywordsGroup.setName("all");
        keywordsGroup.setValue("way");
        persistance.putKeywordsGroup(keywordsGroup);

        action.setSiteId(site.getSiteId());
        action.setSubDomain("a");
        action.setTitle("1");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.setNoBotCodeConfirm("A");
        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        final CreateSiteRequest request = siteCreatorOrUpdaterMock.getRequest();
        Assert.assertEquals("a", request.getSubDomain());
        Assert.assertEquals("1", request.getTitle());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void persistChildSiteWithPaymentSettings() {
        // parent site
        final Site networkSite = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(networkSite, SiteAccessLevel.ADMINISTRATOR);
        final DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration(networkSite);
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(childSiteRegistration, networkSite);
        childSiteSettings.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        childSiteSettings.setSiteStatus(SiteStatus.ACTIVE);

        TestUtil.createUserAndLogin();
        sessionStorage.setNoBotCode(null, "createSite", "A");

        // general settings
        action.setSubDomain("f");
        action.setCustomUrl("g");
        action.setNoBotCodeConfirm("A");
        action.setTitle("t");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.getKeywordsGroups().get(0).setValue("ta-ta");
        action.getKeywordsGroups().get(0).setName("KeywoRds");
        action.setCreateChildSite(true);
        action.setSettingsId(childSiteSettings.getChildSiteSettingsId());
        action.setParentSiteId(childSiteRegistration.getSiteId());

        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        final CreateSiteRequest request = siteCreatorOrUpdaterMock.getRequest();
        TestUtil.assertIntAndBigInt(childSiteSettings.getId(), request.getChildSiteSettingsId());
        assertEquals(SelectSiteDesignPageAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void persistChildSiteWithoutWorkChildSiteRegistration() {
        // parent site
        final Site networkSite = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(networkSite, SiteAccessLevel.ADMINISTRATOR);
        final DraftChildSiteRegistration draftChildSiteRegistration = TestUtil.createChildSiteRegistration(networkSite);
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(draftChildSiteRegistration, networkSite);
        childSiteSettings.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        childSiteSettings.setSiteStatus(SiteStatus.ACTIVE);

        TestUtil.createUserAndLogin();
        sessionStorage.setNoBotCode(null, "createSite", "A");

        // general settings
        action.setSubDomain("ggg");
        action.setNoBotCodeConfirm("A");
        action.setTitle("t");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.setCreateChildSite(true);
        action.setSettingsId(childSiteSettings.getChildSiteSettingsId());
        action.setParentSiteId(draftChildSiteRegistration.getSiteId());

        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        final CreateSiteRequest request = siteCreatorOrUpdaterMock.getRequest();

        TestUtil.assertIntAndBigInt(childSiteSettings.getId(), request.getChildSiteSettingsId());
        assertEquals(SelectSiteDesignPageAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void persistChildSiteWithBrandedAllowShroggleDomain() {
        // parent site
        final Site networkSite = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(networkSite, SiteAccessLevel.ADMINISTRATOR);
        final DraftChildSiteRegistration draftChildSiteRegistration = TestUtil.createChildSiteRegistration(networkSite);
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(draftChildSiteRegistration, networkSite);
        childSiteSettings.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        childSiteSettings.setSiteStatus(SiteStatus.ACTIVE);

        final WorkChildSiteRegistration workChildSiteRegistration = new WorkChildSiteRegistration();
        workChildSiteRegistration.setId(draftChildSiteRegistration.getId());
        workChildSiteRegistration.setBrandedAllowShroggleDomain(false);
        persistance.putItem(workChildSiteRegistration);

        TestUtil.createUserAndLogin();
        sessionStorage.setNoBotCode(null, "createSite", "A");

        // general settings
        action.setNoBotCodeConfirm("A");
        action.setTitle("t");
        action.setBrandedSubDomain("fff");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.setCreateChildSite(true);
        action.setSettingsId(childSiteSettings.getChildSiteSettingsId());
        action.setParentSiteId(draftChildSiteRegistration.getSiteId());

        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        final CreateSiteRequest request = siteCreatorOrUpdaterMock.getRequest();

        TestUtil.assertIntAndBigInt(childSiteSettings.getId(), request.getChildSiteSettingsId());
        assertEquals(SelectSiteDesignPageAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void persistChildSiteWithBrandedAllowShroggleDomainAndDraftChildSiteRegistration() {
        // parent site
        final Site networkSite = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(networkSite, SiteAccessLevel.ADMINISTRATOR);
        final DraftChildSiteRegistration draftChildSiteRegistration = TestUtil.createChildSiteRegistration(networkSite);
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(draftChildSiteRegistration, networkSite);
        childSiteSettings.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        childSiteSettings.setSiteStatus(SiteStatus.ACTIVE);
        draftChildSiteRegistration.setId(draftChildSiteRegistration.getId());
        draftChildSiteRegistration.setBrandedAllowShroggleDomain(false);

        TestUtil.createUserAndLogin();
        sessionStorage.setNoBotCode(null, "createSite", "A");

        // general settings
        action.setNoBotCodeConfirm("A");
        action.setTitle("t");
        action.setBrandedSubDomain("fff");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.setCreateChildSite(true);
        action.setSettingsId(childSiteSettings.getChildSiteSettingsId());
        action.setParentSiteId(draftChildSiteRegistration.getSiteId());

        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        final CreateSiteRequest request = siteCreatorOrUpdaterMock.getRequest();

        TestUtil.assertIntAndBigInt(childSiteSettings.getId(), request.getChildSiteSettingsId());
        assertEquals(SelectSiteDesignPageAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void persistChildSiteWithBrandedAllowShroggleDomainUpperCase() {
        // parent site
        final Site networkSite = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(networkSite, SiteAccessLevel.ADMINISTRATOR);
        final DraftChildSiteRegistration draftChildSiteRegistration = TestUtil.createChildSiteRegistration(networkSite);
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(draftChildSiteRegistration, networkSite);
        childSiteSettings.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        childSiteSettings.setSiteStatus(SiteStatus.ACTIVE);

        final WorkChildSiteRegistration workChildSiteRegistration = new WorkChildSiteRegistration();
        workChildSiteRegistration.setId(draftChildSiteRegistration.getId());
        workChildSiteRegistration.setBrandedAllowShroggleDomain(false);
        workChildSiteRegistration.setBrandedUrl("gg");
        persistance.putItem(workChildSiteRegistration);

        TestUtil.createUserAndLogin();
        sessionStorage.setNoBotCode(null, "createSite", "A");

        // general settings
        action.setNoBotCodeConfirm("A");
        action.setTitle("t");
        action.setBrandedSubDomain("fffF");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.setCreateChildSite(true);
        action.setSettingsId(childSiteSettings.getChildSiteSettingsId());
        action.setParentSiteId(draftChildSiteRegistration.getSiteId());

        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        final CreateSiteRequest request = siteCreatorOrUpdaterMock.getRequest();

        TestUtil.assertIntAndBigInt(childSiteSettings.getId(), request.getChildSiteSettingsId());
        Assert.assertEquals("ffff", request.getBrandedSubDomain());
        assertEquals(SelectSiteDesignPageAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void persistChildSiteWithBrandedAllowShroggleDomainBig() {
        // parent site
        final Site networkSite = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(networkSite, SiteAccessLevel.ADMINISTRATOR);
        final DraftChildSiteRegistration draftChildSiteRegistration = TestUtil.createChildSiteRegistration(networkSite);
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(draftChildSiteRegistration, networkSite);
        childSiteSettings.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        childSiteSettings.setSiteStatus(SiteStatus.ACTIVE);

        final WorkChildSiteRegistration workChildSiteRegistration = new WorkChildSiteRegistration();
        workChildSiteRegistration.setId(draftChildSiteRegistration.getId());
        workChildSiteRegistration.setBrandedAllowShroggleDomain(false);
        workChildSiteRegistration.setBrandedUrl("1");
        persistance.putItem(workChildSiteRegistration);

        TestUtil.createUserAndLogin();
        sessionStorage.setNoBotCode(null, "createSite", "A");

        // general settings
        action.setNoBotCodeConfirm("A");
        action.setTitle("t");
        action.setBrandedSubDomain(TestUtil.createString(251));
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.setCreateChildSite(true);
        action.setSettingsId(childSiteSettings.getChildSiteSettingsId());
        action.setParentSiteId(draftChildSiteRegistration.getSiteId());

        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        final CreateSiteRequest request = siteCreatorOrUpdaterMock.getRequest();

        TestUtil.assertIntAndBigInt(childSiteSettings.getId(), request.getChildSiteSettingsId());
        Assert.assertEquals(250, request.getBrandedSubDomain().length());
        assertEquals(SelectSiteDesignPageAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void persistChildSiteWithoutBrandedAllowShroggleDomainAndDuplicateCustomUrl() {
        // parent site
        final Site networkSite = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(networkSite, SiteAccessLevel.ADMINISTRATOR);
        final DraftChildSiteRegistration draftChildSiteRegistration = TestUtil.createChildSiteRegistration(networkSite);
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(draftChildSiteRegistration, networkSite);
        childSiteSettings.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        childSiteSettings.setSiteStatus(SiteStatus.ACTIVE);

        final WorkChildSiteRegistration workChildSiteRegistration = new WorkChildSiteRegistration();
        workChildSiteRegistration.setBrandedUrl("11");
        workChildSiteRegistration.setId(draftChildSiteRegistration.getId());
        workChildSiteRegistration.setBrandedAllowShroggleDomain(false);
        persistance.putItem(workChildSiteRegistration);

        TestUtil.createUserAndLogin();
        sessionStorage.setNoBotCode(null, "createSite", "A");

        TestUtil.createSite().setCustomUrl("gg.com");

        // general settings
        action.setNoBotCodeConfirm("A");
        action.setTitle("t");
        action.setCustomUrl("gg.com");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.setCreateChildSite(true);
        action.setSettingsId(childSiteSettings.getChildSiteSettingsId());
        action.setParentSiteId(draftChildSiteRegistration.getSiteId());

        action.persist();

        Assert.assertNotNull(action.getContext().getValidationErrors().get("brandedSubDomain"));
    }

    @Test
    public void persistChildSiteWithDuplicateBrandedSubDomain() {
        // parent site
        final Site networkSite = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(networkSite, SiteAccessLevel.ADMINISTRATOR);
        final DraftChildSiteRegistration draftChildSiteRegistration = TestUtil.createChildSiteRegistration(networkSite);
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(draftChildSiteRegistration, networkSite);
        childSiteSettings.setSitePaymentSettings(TestUtil.createPaymentSettingsForJavien(1000, ChargeType.SITE_MONTHLY_FEE));
        childSiteSettings.setSiteStatus(SiteStatus.ACTIVE);

        final WorkChildSiteRegistration workChildSiteRegistration = new WorkChildSiteRegistration();
        workChildSiteRegistration.setId(draftChildSiteRegistration.getId());
        workChildSiteRegistration.setBrandedAllowShroggleDomain(false);
        workChildSiteRegistration.setBrandedUrl("ffg");
        persistance.putItem(workChildSiteRegistration);

        final Site site = TestUtil.createSite();
        site.setBrandedSubDomain("ffg");
        site.setChildSiteSettings(childSiteSettings);

        TestUtil.createUserAndLogin();
        sessionStorage.setNoBotCode(null, "createSite", "A");

        // general settings
        action.setNoBotCodeConfirm("A");
        action.setTitle("t");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.setCreateChildSite(true);
        action.setSettingsId(childSiteSettings.getChildSiteSettingsId());
        action.setParentSiteId(draftChildSiteRegistration.getSiteId());

        action.persist();

        Assert.assertNotNull(action.getContext().getValidationErrors().get("brandedSubDomain"));
    }

    @Test
    public void persistForEditWithSiteTypeBlueprint() {
        final Site site = TestUtil.createSite();
        site.setType(SiteType.BLUEPRINT);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        sessionStorage.setNoBotCode(null, "createSite", "A");

        final DraftRegistrationForm registrationForm = TestUtil.createRegistrationForm();
        site.setDefaultFormId(registrationForm.getFormId());

        KeywordsGroup keywordsGroup = new KeywordsGroup();
        site.addKeywordsGroup(keywordsGroup);
        keywordsGroup.setName("all");
        keywordsGroup.setValue("way");

        action.setSiteId(site.getSiteId());
        action.setDescription("H");
        action.setSubDomain("a");
        action.setTitle("1");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.getKeywordsGroups().get(0).setValue("ta-ta");
        action.getKeywordsGroups().get(0).setName("nameKeywords");
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.getKeywordsGroups().get(1).setName("News");
        action.getKeywordsGroups().get(1).setValue("dead");
        action.setNoBotCodeConfirm("A");
        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        final CreateSiteRequest request = siteCreatorOrUpdaterMock.getRequest();
        Assert.assertEquals(2, request.getKeywordsGroups().size());
        Assert.assertEquals("ta-ta", request.getKeywordsGroups().get(0).getValue());
        Assert.assertEquals("dead", request.getKeywordsGroups().get(1).getValue());
        Assert.assertEquals("a", request.getSubDomain());
        Assert.assertNull(request.getCustomUrl());
        Assert.assertEquals("1", request.getTitle());
        Assert.assertEquals("H", request.getBlueprintDescription());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void persistForEditWithEqualsKeywordsGroup() {
        final Site site = TestUtil.createSite();
        site.setSubDomain("ff");

        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        sessionStorage.setNoBotCode(null, "createSite", "FF");

        KeywordsGroup keywordsGroup = TestUtil.createKeywordsGroup(site);
        keywordsGroup.setName("all");
        keywordsGroup.setValue("way");

        action.setSiteId(site.getSiteId());
        action.setSubDomain("a");
        action.setTitle("1");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.getKeywordsGroups().get(0).setName("nameKeywords");
        action.getKeywordsGroups().get(0).setValue("ta-ta");
        action.getKeywordsGroups().get(0).setId("0");
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.getKeywordsGroups().get(1).setName("nameKeywords");
        action.getKeywordsGroups().get(1).setValue("dead");
        action.getKeywordsGroups().get(1).setId("1");
        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        Assert.assertNotNull(action.getContext().getValidationErrors().get("keywordsGroupName1"));
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void persistForEditWithEqualsKeywordsGroupButOtherCase() {
        final Site site = TestUtil.createSite();
        site.setSubDomain("ff");

        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        sessionStorage.setNoBotCode(null, "createSite", "FF");

        action.setSiteId(site.getSiteId());
        action.setSubDomain("a");
        action.setTitle("1");
        action.setNoBotCodeConfirm("FF");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.getKeywordsGroups().get(0).setName("namEKeywords");
        action.getKeywordsGroups().get(0).setValue("ta-ta");
        action.getKeywordsGroups().get(0).setId("0");
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.getKeywordsGroups().get(1).setName("namekeywords");
        action.getKeywordsGroups().get(1).setValue("dead");
        action.getKeywordsGroups().get(1).setId("1");
        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        Assert.assertNotNull(action.getContext().getValidationErrors().get("keywordsGroupName1"));
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void persistForEditWithKeywordsGroupNameButEmptyValue() {
        final Site site = TestUtil.createSite();
        site.setSubDomain("ff");

        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        sessionStorage.setNoBotCode(null, "createSite", "FF");

        action.setSiteId(site.getSiteId());
        action.setSubDomain("a");
        action.setTitle("1");
        action.setNoBotCodeConfirm("FF");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.getKeywordsGroups().get(0).setName("namEKeywords");
        action.getKeywordsGroups().get(0).setValue(null);
        action.getKeywordsGroups().get(0).setId("0");
        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        Assert.assertNotNull(action.getContext().getValidationErrors().get("keywordsGroupValue0"));
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void persistForEditWithKeywordsGroupNameButEmptyName() {
        final Site site = TestUtil.createSite();
        site.setSubDomain("ff");

        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        sessionStorage.setNoBotCode(null, "createSite", "FF");

        action.setSiteId(site.getSiteId());
        action.setSubDomain("a");
        action.setTitle("1");
        action.setNoBotCodeConfirm("FF");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.getKeywordsGroups().get(0).setName(null);
        action.getKeywordsGroups().get(0).setValue("a");
        action.getKeywordsGroups().get(0).setId("0");
        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        Assert.assertNotNull(action.getContext().getValidationErrors().get("keywordsGroupName0"));
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void persistForEditWithKeywordsGroupNameButEmptyNameAndValue() {
        final Site site = TestUtil.createSite();
        site.setSubDomain("ff");

        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        sessionStorage.setNoBotCode(null, "createSite", "Z");

        KeywordsGroup keywordsGroup = TestUtil.createKeywordsGroup(site);
        keywordsGroup.setName("all");
        keywordsGroup.setValue("way");

        action.setSiteId(site.getSiteId());
        action.setSubDomain("a");
        action.setTitle("1");
        action.setNoBotCodeConfirm("Z");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.getKeywordsGroups().get(0).setName(null);
        action.getKeywordsGroups().get(0).setValue(null);
        action.getKeywordsGroups().get(0).setId("0");
        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void persistWithEqualsKeywordsGroup() {
        TestUtil.createUserAndLogin();

        sessionStorage.setNoBotCode(null, "createSite", "F");
        action.setSubDomain("a");
        action.setNoBotCodeConfirm("F");
        action.setTitle("1");
        action.setKeywordsGroups(new ArrayList<CreateSiteKeywordsGroup>());
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.getKeywordsGroups().get(0).setName("nameKeywords");
        action.getKeywordsGroups().get(0).setValue("ta-ta");
        action.getKeywordsGroups().get(0).setId("0");
        action.getKeywordsGroups().add(new CreateSiteKeywordsGroup());
        action.getKeywordsGroups().get(1).setName("nameKeywords");
        action.getKeywordsGroups().get(1).setValue("dead");
        action.getKeywordsGroups().get(1).setId("1");
        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        Assert.assertNotNull(action.getContext().getValidationErrors().get("keywordsGroupName1"));
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void persistForEditWithNoChangePrefix() {
        final Site site = TestUtil.createSite();
        site.setSubDomain("ff");

        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        sessionStorage.setNoBotCode(null, "createSite", "A");
        action.setSiteId(site.getSiteId());
        action.setSubDomain("ff");
        action.setTitle("ff");
        action.setNoBotCodeConfirm("A");
        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        Assert.assertEquals(0, site.getKeywordsGroups().size());
        Assert.assertEquals("ff", site.getSubDomain());
        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void persistForEditWithNotFoundSite() {
        TestUtil.createUserAndLogin();
        sessionStorage.setNoBotCode(null, "createSite", "A");

        action.setSiteId(-1);
        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void persistForEditWithNotMySite() {
        final Site site = TestUtil.createSite();
        site.setSubDomain("ff");
        TestUtil.createUserAndLogin();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);
        sessionStorage.setNoBotCode(null, "createSite", "FFF");

        action.setSiteId(site.getSiteId());
        action.setSubDomain("a");
        action.setNoBotCodeConfirm("FFF");
        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void persistWithNotUniqueUrlPrefix() {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        site.setSubDomain("a");
        siteByUrlGetterMock.setSite(site);

        sessionStorage.setNoBotCode(null, "createSite", "F");

        action.setNoBotCodeConfirm("F");
        action.setTitle("ggg");
        action.setSubDomain("a");
        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        Assert.assertNotNull(action.getContext().getValidationErrors().get("siteUrlPrefix"));
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void persistWithNotUniqueCustomUrl() {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        site.setSubDomain("a");
        site.setCustomUrl("f.com");
        siteByUrlGetterMock.setSite(site);

        sessionStorage.setNoBotCode(null, "createSite", "A");
        action.setNoBotCodeConfirm("A");
        action.setSubDomain("g");
        action.setCustomUrl("f.com");
        action.setTitle("ggg");
        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        Assert.assertNotNull(action.getContext().getValidationErrors().get("customUrl"));
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void persistWithNotUniqueCustomUrl_withWWW() {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        site.setSubDomain("a");
        site.setCustomUrl("f.com");
        siteByUrlGetterMock.setSite(site);

        sessionStorage.setNoBotCode(null, "createSite", "A");
        action.setNoBotCodeConfirm("A");
        action.setSubDomain("g");
        action.setCustomUrl("www.f.com");
        action.setTitle("ggg");
        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        Assert.assertNotNull(action.getContext().getValidationErrors().get("customUrl"));
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void persistForEditWithNotUniqueCustomUrl() {
        final Site site = TestUtil.createSite();
        site.setSubDomain("g");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Site otherSite = TestUtil.createSite();
        otherSite.setSubDomain("a");
        otherSite.setCustomUrl("f.com");
        siteByUrlGetterMock.setSite(otherSite);

        sessionStorage.setNoBotCode(null, "createSite", "NBC");
        action.setNoBotCodeConfirm("NBC");
        action.setSubDomain("g");
        action.setTitle("ggg");
        action.setSiteId(site.getSiteId());
        action.setCustomUrl("f.com");
        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        Assert.assertFalse(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void persistForEditWithEqualsCustomUrl() {
        final Site site = TestUtil.createSite();
        site.setSubDomain("1g");
        site.setCustomUrl("f.com");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        sessionStorage.setNoBotCode(null, "createSite", "NBC");

        action.setNoBotCodeConfirm("NBC");
        action.setSubDomain("g");
        action.setTitle("ggg");
        action.setSiteId(site.getSiteId());
        action.setCustomUrl("f.com");
        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void persistWithNullSubDomain() {
        TestUtil.createUserAndLogin();

        action.setTitle("11");

        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        Assert.assertNotNull(action.getContext().getValidationErrors().get("siteUrlPrefix"));
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void persistWithNullSubDomainAndNotNullCustomUrl() {
        TestUtil.createUserAndLogin();

        action.setTitle("11");
        action.setCustomUrl("33");

        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        Assert.assertNotNull(action.getContext().getValidationErrors().get("siteUrlPrefix"));
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void persistWithEmptyCustomUrl() {
        TestUtil.createUserAndLogin();

        action.setSubDomain("a");
        action.setCustomUrl(" ");
        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        Assert.assertFalse(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void persistWithSpaceInSubDomain() {
        TestUtil.createUserAndLogin();

        action.setSubDomain("a a");
        final ResolutionMock resolutionMock = (ResolutionMock) action.persist();

        Assert.assertFalse(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals("/site/createSite.jsp", resolutionMock.getForwardToUrl());
    }

    private final SiteByUrlGetterMock siteByUrlGetterMock = (SiteByUrlGetterMock) ServiceLocator.getSiteByUrlGetter();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final CreateSiteAction action = new CreateSiteAction();
    private final SiteCreatorOrUpdaterMock siteCreatorOrUpdaterMock =
            (SiteCreatorOrUpdaterMock) ServiceLocator.getSiteCreatorOrUpdater();

}