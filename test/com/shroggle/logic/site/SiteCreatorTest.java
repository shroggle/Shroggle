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
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.payment.PaymentLogRequest;
import com.shroggle.logic.site.payment.TransactionStatus;
import com.shroggle.logic.user.UserManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemMock;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class SiteCreatorTest {

    @Before
    public void before() {
        FileSystemMock fileSystemMock = ((FileSystemMock) ServiceLocator.getFileSystem());
        fileSystemMock.putTemplate(TestUtil.createTemplate());
    }

    @Test
    public void testUpdateSiteOrCreateNew_COMMON() {
        final User user = TestUtil.createUser();
        Site parentSite = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(parentSite);
        final ChildSiteSettings settings = TestUtil.createChildSiteSettings(registration, parentSite);
        settings.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
        settings.getSitePaymentSettings().setUserId(123);

        final SEOSettings seoSettings = new SEOSettings();
        seoSettings.setAuthorMetaTag("author");
        seoSettings.setCopyrightMetaTag("copyright");
        seoSettings.setRobotsMetaTag("robots");
        seoSettings.setCustomMetaTagList(new ArrayList<String>() {{
            add("custom1");
            add("custom2");
        }});
        seoSettings.setHtmlCodeList(new ArrayList<SEOHtmlCode>() {{
            final SEOHtmlCode seoHtmlCode1 = new SEOHtmlCode();
            seoHtmlCode1.setName("name1");
            seoHtmlCode1.setCode("code1");
            seoHtmlCode1.setCodePlacement(CodePlacement.BEGINNING);

            add(seoHtmlCode1);

            final SEOHtmlCode seoHtmlCode2 = new SEOHtmlCode();
            seoHtmlCode2.setName("name2");
            seoHtmlCode2.setCode("code2");
            seoHtmlCode2.setCodePlacement(CodePlacement.END);

            add(seoHtmlCode2);
        }});
        CreateSiteRequest createSiteRequest = new CreateSiteRequest(null, user, "urlPrefix", "aliaseUrl", "title",
                "description", SiteBlueprintRightType.CAN_ADD_PAGES, null, settings.getChildSiteSettingsId(), SiteType.COMMON, seoSettings, null);

        Assert.assertNull(settings.getSite());
        Assert.assertNotNull(settings.getConfirmCode());

        Site site = SiteCreator.updateSiteOrCreateNew(createSiteRequest);

        Assert.assertNotNull(site);
        Assert.assertNotNull(settings.getSite());
        Assert.assertEquals(true, site.getMenu().isDefaultSiteMenu());
        Assert.assertEquals(site, settings.getSite());
        Assert.assertEquals(SiteType.COMMON, site.getType());
        Assert.assertEquals(1, site.getOwnGroups().size());
        Assert.assertEquals("Invited", site.getOwnGroups().get(0).getName());
        Assert.assertEquals("urlPrefix", site.getSubDomain());
        Assert.assertEquals("aliaseurl", site.getCustomUrl());
        Assert.assertEquals("title", site.getTitle());
        Assert.assertEquals(null, site.getDescription());
        Assert.assertEquals(settings.getAccessLevel(), new UserRightManager(user).toSite(site).getSiteAccessType());
        Assert.assertNotNull(persistance.getSite(site.getSiteId()));
        Assert.assertEquals(site, persistance.getSite(site.getSiteId()));
        Assert.assertEquals(ServiceLocator.getFileSystem().getTemplates().get(0).getDirectory(), site.getThemeId().getTemplateDirectory());
        Assert.assertEquals(ServiceLocator.getFileSystem().getTemplates().get(0).getThemes().get(0).getFile(), site.getThemeId().getThemeCss());
        Assert.assertEquals(0, site.getKeywordsGroups().size());
        Assert.assertEquals(settings.getSitePaymentSettings().getUserId().intValue(), site.getSitePaymentSettings().getUserId().intValue());

        Assert.assertEquals("author", site.getSeoSettings().getAuthorMetaTag());
        Assert.assertEquals("copyright", site.getSeoSettings().getCopyrightMetaTag());
        Assert.assertEquals("robots", site.getSeoSettings().getRobotsMetaTag());
        Assert.assertEquals(2, site.getSeoSettings().getCustomMetaTagList().size());
        Assert.assertEquals("custom1", site.getSeoSettings().getCustomMetaTagList().get(0));
        Assert.assertEquals("custom2", site.getSeoSettings().getCustomMetaTagList().get(1));
        Assert.assertEquals(2, site.getSeoSettings().getHtmlCodeList().size());
        Assert.assertEquals("name1", site.getSeoSettings().getHtmlCodeList().get(0).getName());
        Assert.assertEquals("code1", site.getSeoSettings().getHtmlCodeList().get(0).getCode());
        Assert.assertEquals(CodePlacement.BEGINNING, site.getSeoSettings().getHtmlCodeList().get(0).getCodePlacement());
        Assert.assertEquals("name2", site.getSeoSettings().getHtmlCodeList().get(1).getName());
        Assert.assertEquals("code2", site.getSeoSettings().getHtmlCodeList().get(1).getCode());
        Assert.assertEquals(CodePlacement.END, site.getSeoSettings().getHtmlCodeList().get(1).getCodePlacement());

        Assert.assertNotNull(site.getLoginAdminPage());
        PageManager pageManager = new PageManager(site.getLoginAdminPage());
        Assert.assertNotNull(pageManager);
        Assert.assertEquals(3, pageManager.getWidgets().size());
        Assert.assertEquals(true, pageManager.getWidgets().get(0).isWidgetComposit());

        Assert.assertNotNull(site.getLoginPage());
        PageManager loginPageManager = new PageManager(site.getLoginPage());
        Assert.assertNotNull(loginPageManager);
        Assert.assertEquals(3, loginPageManager.getWidgets().size());
        Assert.assertEquals(true, loginPageManager.getWidgets().get(0).isWidgetComposit());

        Assert.assertEquals(settings, site.getChildSiteSettings());
        Assert.assertNotNull(settings.getConfirmCode());
    }

    @Test
    public void testUpdateSiteOrCreateNew_COMMONWithNullSeoCustomHtml() {
        final User user = TestUtil.createUser();
        Site parentSite = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(parentSite);
        final ChildSiteSettings settings = TestUtil.createChildSiteSettings(registration, parentSite);
        settings.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
        settings.getSitePaymentSettings().setUserId(123);

        final SEOSettings seoSettings = new SEOSettings();
        seoSettings.setCustomMetaTagList(new ArrayList<String>());

        seoSettings.setHtmlCodeList(new ArrayList<SEOHtmlCode>() {{
            final SEOHtmlCode seoHtmlCode1 = new SEOHtmlCode();
            seoHtmlCode1.setName("name1");
            seoHtmlCode1.setCode("code1");
            seoHtmlCode1.setCodePlacement(CodePlacement.BEGINNING);

            add(seoHtmlCode1);
            add(null);

            final SEOHtmlCode seoHtmlCode2 = new SEOHtmlCode();
            seoHtmlCode2.setName("name2");
            seoHtmlCode2.setCode("code2");
            seoHtmlCode2.setCodePlacement(CodePlacement.END);

            add(seoHtmlCode2);
        }});
        CreateSiteRequest createSiteRequest = new CreateSiteRequest(null, user, "urlPrefix", "aliaseUrl", "title",
                "description", SiteBlueprintRightType.CAN_ADD_PAGES, null, settings.getChildSiteSettingsId(), SiteType.COMMON, seoSettings, null);

        Assert.assertNull(settings.getSite());
        Assert.assertNotNull(settings.getConfirmCode());

        Site site = SiteCreator.updateSiteOrCreateNew(createSiteRequest);

        Assert.assertEquals(2, site.getSeoSettings().getHtmlCodeList().size());
        Assert.assertEquals("name1", site.getSeoSettings().getHtmlCodeList().get(0).getName());
        Assert.assertEquals("code1", site.getSeoSettings().getHtmlCodeList().get(0).getCode());
        Assert.assertEquals(CodePlacement.BEGINNING, site.getSeoSettings().getHtmlCodeList().get(0).getCodePlacement());
        Assert.assertEquals("name2", site.getSeoSettings().getHtmlCodeList().get(1).getName());
        Assert.assertEquals("code2", site.getSeoSettings().getHtmlCodeList().get(1).getCode());
        Assert.assertEquals(CodePlacement.END, site.getSeoSettings().getHtmlCodeList().get(1).getCodePlacement());
    }

    @Test
    public void testUpdateSiteOrCreateNew_COMMON_withoutBlueprintFields() {
        final User user = TestUtil.createUser();
        Site parentSite = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(parentSite);
        final ChildSiteSettings settings = TestUtil.createChildSiteSettings(registration, parentSite);
        settings.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
        settings.getSitePaymentSettings().setUserId(123);
        settings.setFilledFormId(12345);
        CreateSiteRequest createSiteRequest = new CreateSiteRequest(null, user, "urlPrefix", null, "title",
                null, null, null, settings.getChildSiteSettingsId(), SiteType.COMMON, new SEOSettings(), null);


        Assert.assertNull(settings.getSite());
        Assert.assertNotNull(settings.getConfirmCode());

        Site site = SiteCreator.updateSiteOrCreateNew(createSiteRequest);

        Assert.assertNotNull(site);
        Assert.assertNotNull(settings.getSite());
        Assert.assertEquals(true, site.getMenu().isDefaultSiteMenu());
        Assert.assertEquals(site, settings.getSite());
        Assert.assertEquals(SiteType.COMMON, site.getType());
        Assert.assertEquals("urlPrefix", site.getSubDomain());
        Assert.assertEquals(null, site.getCustomUrl());
        Assert.assertEquals("title", site.getTitle());
        Assert.assertEquals(null, site.getDescription());
        Assert.assertEquals(settings.getAccessLevel(), new UserRightManager(user).toSite(site).getSiteAccessType());
        Assert.assertNotNull(persistance.getSite(site.getSiteId()));
        Assert.assertEquals(site, persistance.getSite(site.getSiteId()));
        Assert.assertEquals(ServiceLocator.getFileSystem().getTemplates().get(0).getDirectory(), site.getThemeId().getTemplateDirectory());
        Assert.assertEquals(ServiceLocator.getFileSystem().getTemplates().get(0).getThemes().get(0).getFile(), site.getThemeId().getThemeCss());
        Assert.assertEquals(0, site.getKeywordsGroups().size());
        Assert.assertEquals(settings.getSitePaymentSettings().getUserId().intValue(), site.getSitePaymentSettings().getUserId().intValue());

        Assert.assertNotNull(site.getLoginAdminPage());
        PageManager pageManager = new PageManager(site.getLoginAdminPage());
        Assert.assertNotNull(pageManager);
        Assert.assertEquals(3, pageManager.getWidgets().size());
        Assert.assertEquals(true, pageManager.getWidgets().get(0).isWidgetComposit());

        Assert.assertNotNull(site.getLoginPage());
        PageManager loginPageManager = new PageManager(site.getLoginPage());
        Assert.assertNotNull(loginPageManager);
        Assert.assertEquals(3, loginPageManager.getWidgets().size());
        Assert.assertEquals(true, loginPageManager.getWidgets().get(0).isWidgetComposit());

        Assert.assertEquals(settings, site.getChildSiteSettings());
        Assert.assertNotNull(settings.getConfirmCode());
        // Groups
        Assert.assertEquals(1, site.getOwnGroups().size());
        Assert.assertEquals("Invited", site.getOwnGroups().get(0).getName());

        Assert.assertEquals(12345, site.getChildSiteFilledFormId().intValue());
    }

    @Test
    public void testUpdateSiteOrCreateNew_COMMON_withoutChildSiteSettings() {
        final User user = TestUtil.createUser();
        Site parentSite = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(parentSite);
        final ChildSiteSettings settings = TestUtil.createChildSiteSettings(registration, parentSite);
        settings.getSitePaymentSettings().setUserId(123);
        settings.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
        CreateSiteRequest createSiteRequest = new CreateSiteRequest(null, user, "urlPrefix", "aliaseUrl", "title",
                "description", SiteBlueprintRightType.CAN_ADD_PAGES, null, null, SiteType.COMMON, new SEOSettings(), null);


        Assert.assertNull(settings.getSite());
        Assert.assertNotNull(settings.getConfirmCode());

        Site site = SiteCreator.updateSiteOrCreateNew(createSiteRequest);

        Assert.assertNotNull(site);
        Assert.assertNull(settings.getSite());
        Assert.assertEquals(true, site.getMenu().isDefaultSiteMenu());
        Assert.assertEquals(SiteType.COMMON, site.getType());
        Assert.assertEquals("urlPrefix", site.getSubDomain());
        Assert.assertEquals("aliaseurl", site.getCustomUrl());
        Assert.assertEquals("title", site.getTitle());
        Assert.assertEquals(null, site.getDescription());
        Assert.assertEquals(settings.getAccessLevel(), new UserRightManager(user).toSite(site).getSiteAccessType());
        Assert.assertNotNull(persistance.getSite(site.getSiteId()));
        Assert.assertEquals(site, persistance.getSite(site.getSiteId()));
        Assert.assertEquals(ServiceLocator.getFileSystem().getTemplates().get(0).getDirectory(), site.getThemeId().getTemplateDirectory());
        Assert.assertEquals(ServiceLocator.getFileSystem().getTemplates().get(0).getThemes().get(0).getFile(), site.getThemeId().getThemeCss());
        Assert.assertEquals(0, site.getKeywordsGroups().size());

        Assert.assertNotNull(site.getLoginAdminPage());
        PageManager pageManager = new PageManager(site.getLoginAdminPage());
        Assert.assertNotNull(pageManager);
        Assert.assertEquals(3, pageManager.getWidgets().size());
        Assert.assertEquals(true, pageManager.getWidgets().get(0).isWidgetComposit());

        Assert.assertNotNull(site.getLoginPage());
        PageManager loginPageManager = new PageManager(site.getLoginPage());
        Assert.assertNotNull(loginPageManager);
        Assert.assertEquals(3, loginPageManager.getWidgets().size());
        Assert.assertEquals(true, loginPageManager.getWidgets().get(0).isWidgetComposit());

        Assert.assertNotNull(settings.getConfirmCode());
        // Groups
        Assert.assertEquals(1, site.getOwnGroups().size());
        Assert.assertEquals("Invited", site.getOwnGroups().get(0).getName());

        Assert.assertEquals(user.getUserId(), site.getSitePaymentSettings().getUserId().intValue());
    }


    @Test
    public void testUpdateSiteOrCreateNew_BLUEPRINT() {
        final User user = TestUtil.createUser();
        Site parentSite = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(parentSite);
        final ChildSiteSettings settings = TestUtil.createChildSiteSettings(registration, parentSite);
        settings.getSitePaymentSettings().setUserId(123);
        settings.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
        CreateSiteRequest createSiteRequest = new CreateSiteRequest(null, user, "urlPrefix", "aliaseUrl", "title",
                "description", SiteBlueprintRightType.CANNOT_ADD_PAGE, null, settings.getChildSiteSettingsId(), SiteType.BLUEPRINT, new SEOSettings(), null);


        Assert.assertNull(settings.getSite());
        Assert.assertNotNull(settings.getConfirmCode());

        Site site = SiteCreator.updateSiteOrCreateNew(createSiteRequest);

        Assert.assertNotNull(site);
        Assert.assertNotNull(settings.getSite());
        Assert.assertEquals(true, site.getMenu().isDefaultSiteMenu());
        Assert.assertEquals(site, settings.getSite());
        Assert.assertEquals(SiteType.BLUEPRINT, site.getType());
        Assert.assertEquals(null, site.getSubDomain());
        Assert.assertEquals(null, site.getCustomUrl());
        Assert.assertEquals("title", site.getTitle());
        Assert.assertEquals("description", site.getDescription());
        Assert.assertEquals(SiteBlueprintRightType.CANNOT_ADD_PAGE, site.getBlueprintRightType());
        Assert.assertEquals(settings.getAccessLevel(), new UserRightManager(user).toSite(site).getSiteAccessType());
        Assert.assertNotNull(persistance.getSite(site.getSiteId()));
        Assert.assertEquals(site, persistance.getSite(site.getSiteId()));
        Assert.assertEquals(ServiceLocator.getFileSystem().getTemplates().get(0).getDirectory(), site.getThemeId().getTemplateDirectory());
        Assert.assertEquals(ServiceLocator.getFileSystem().getTemplates().get(0).getThemes().get(0).getFile(), site.getThemeId().getThemeCss());
        Assert.assertEquals(0, site.getKeywordsGroups().size());

        Assert.assertEquals(settings, site.getChildSiteSettings());
        Assert.assertNotNull(settings.getConfirmCode());
        // Groups
        Assert.assertEquals(1, site.getOwnGroups().size());
        Assert.assertEquals("Invited", site.getOwnGroups().get(0).getName());

        Assert.assertEquals(settings.getSitePaymentSettings().getUserId().intValue(), site.getSitePaymentSettings().getUserId().intValue());
    }

    @Test
    public void testUpdateSiteOrCreateNew_BLUEPRINT_withNullSiteBlueprintRightType() {
        final User user = TestUtil.createUser();
        Site parentSite = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(parentSite);
        final ChildSiteSettings settings = TestUtil.createChildSiteSettings(registration, parentSite);
        settings.getSitePaymentSettings().setUserId(123);
        settings.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);
        CreateSiteRequest createSiteRequest = new CreateSiteRequest(null, user, "urlPrefix", "aliaseUrl", "title",
                "description", null, null, settings.getChildSiteSettingsId(), SiteType.BLUEPRINT, new SEOSettings(), null);


        Assert.assertNull(settings.getSite());
        Assert.assertNotNull(settings.getConfirmCode());

        Site site = SiteCreator.updateSiteOrCreateNew(createSiteRequest);

        Assert.assertNotNull(site);
        Assert.assertNotNull(settings.getSite());
        Assert.assertEquals(true, site.getMenu().isDefaultSiteMenu());
        Assert.assertEquals(site, settings.getSite());
        Assert.assertEquals(SiteType.BLUEPRINT, site.getType());
        Assert.assertEquals(null, site.getSubDomain());
        Assert.assertEquals(null, site.getCustomUrl());
        Assert.assertEquals("title", site.getTitle());
        Assert.assertEquals("description", site.getDescription());
        Assert.assertEquals(SiteBlueprintRightType.CAN_ADD_PAGES, site.getBlueprintRightType());
        Assert.assertEquals(settings.getAccessLevel(), new UserRightManager(user).toSite(site).getSiteAccessType());
        Assert.assertNotNull(persistance.getSite(site.getSiteId()));
        Assert.assertEquals(site, persistance.getSite(site.getSiteId()));
        Assert.assertEquals(ServiceLocator.getFileSystem().getTemplates().get(0).getDirectory(), site.getThemeId().getTemplateDirectory());
        Assert.assertEquals(ServiceLocator.getFileSystem().getTemplates().get(0).getThemes().get(0).getFile(), site.getThemeId().getThemeCss());
        Assert.assertEquals(0, site.getKeywordsGroups().size());

        Assert.assertEquals(settings, site.getChildSiteSettings());
        Assert.assertNotNull(settings.getConfirmCode());
        // Groups
        Assert.assertEquals(1, site.getOwnGroups().size());
        Assert.assertEquals("Invited", site.getOwnGroups().get(0).getName());

        Assert.assertEquals(settings.getSitePaymentSettings().getUserId().intValue(), site.getSitePaymentSettings().getUserId().intValue());
    }


    @Test
    public void testUpdateSiteOrCreateNew_COMMON_updateSite() {
        final User user = TestUtil.createUser();
        Site parentSite = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(parentSite);
        final ChildSiteSettings settings = TestUtil.createChildSiteSettings(registration, parentSite);
        settings.getSitePaymentSettings().setUserId(123);
        settings.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);

        Site site = new Site();
        persistance.putSite(site);
        final Group group = TestUtil.createGroup("groupName", site);
        CreateSiteRequest createSiteRequest = new CreateSiteRequest(site, user, "urlPrefix", "aliaseUrl", "title",
                "description", SiteBlueprintRightType.CAN_ADD_PAGES, null, settings.getChildSiteSettingsId(), SiteType.COMMON, new SEOSettings(), null);


        Assert.assertNull(settings.getSite());
        Assert.assertNotNull(settings.getConfirmCode());

        Site newSite = SiteCreator.updateSiteOrCreateNew(createSiteRequest);

        Assert.assertNotNull(newSite);
        Assert.assertNotNull(settings.getSite());
        Assert.assertEquals(newSite, settings.getSite());
        Assert.assertEquals(SiteType.COMMON, newSite.getType());
        Assert.assertEquals("urlPrefix", newSite.getSubDomain());
        Assert.assertEquals("aliaseurl", newSite.getCustomUrl());
        Assert.assertEquals("title", newSite.getTitle());
        Assert.assertEquals(null, newSite.getDescription());
        Assert.assertEquals(null, new UserRightManager(user).toSite(newSite));
        Assert.assertNotNull(persistance.getSite(newSite.getSiteId()));
        Assert.assertEquals(newSite, persistance.getSite(newSite.getSiteId()));
        Assert.assertEquals(null, newSite.getThemeId().getTemplateDirectory());
        Assert.assertEquals(null, newSite.getThemeId().getThemeCss());
        Assert.assertEquals(0, newSite.getKeywordsGroups().size());

        Assert.assertEquals(settings, newSite.getChildSiteSettings());
        Assert.assertNotNull(settings.getConfirmCode());
        // Groups
        Assert.assertEquals(1, site.getOwnGroups().size());
        Assert.assertEquals(group, site.getOwnGroups().get(0));

        Assert.assertEquals(settings.getSitePaymentSettings().getUserId().intValue(), site.getSitePaymentSettings().getUserId().intValue());
    }


    @Test
    public void testAddRightsForParentSite() {
        final Site parentSite = TestUtil.createSite();
        final Site childSite = TestUtil.createSite();
        final User childSiteOwner = TestUtil.createUser();
        TestUtil.createUserOnSiteRightActive(childSiteOwner, childSite, SiteAccessLevel.ADMINISTRATOR);

        /*-------------------------------------Users without rights to child site-------------------------------------*/
        User user1WithAdminRightToParentSite = TestUtil.createUser();
        User user2WithAdminRightParentSite = TestUtil.createUser();
        User user1WithVisitorRightParentSite = TestUtil.createUser();
        TestUtil.createUserOnSiteRightActive(user1WithAdminRightToParentSite, parentSite, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserOnSiteRightActive(user2WithAdminRightParentSite, parentSite, SiteAccessLevel.EDITOR);
        TestUtil.createUserOnSiteRightActive(user1WithVisitorRightParentSite, parentSite, SiteAccessLevel.VISITOR);
        /*-------------------------------------Users without rights to child site-------------------------------------*/


        Assert.assertNull(new UserManager(user1WithAdminRightToParentSite).getRight().toSite(childSite));
        Assert.assertNull(new UserManager(user2WithAdminRightParentSite).getRight().toSite(childSite));
        Assert.assertNull(new UserManager(user1WithVisitorRightParentSite).getRight().toSite(childSite));

        SiteCreator.addRightsForParentSite(childSite, childSiteOwner, parentSite, SiteAccessLevel.ADMINISTRATOR);

        Assert.assertNotNull(new UserManager(user1WithAdminRightToParentSite).getRight().toSite(childSite));
        Assert.assertNotNull(new UserManager(user2WithAdminRightParentSite).getRight().toSite(childSite));
        Assert.assertNull(new UserManager(user1WithVisitorRightParentSite).getRight().toSite(childSite));

        Assert.assertEquals(SiteAccessLevel.ADMINISTRATOR, new UserManager(childSiteOwner).getRight().toSite(childSite).getSiteAccessType());
        Assert.assertFalse(new UserManager(childSiteOwner).getRight().toSite(childSite).isFromNetwork());

        Assert.assertEquals(SiteAccessLevel.ADMINISTRATOR, new UserManager(user1WithAdminRightToParentSite).getRight().toSite(childSite).getSiteAccessType());
        Assert.assertTrue(new UserManager(user1WithAdminRightToParentSite).getRight().toSite(childSite).isFromNetwork());
        Assert.assertEquals(SiteAccessLevel.ADMINISTRATOR, new UserManager(user2WithAdminRightParentSite).getRight().toSite(childSite).getSiteAccessType());
        Assert.assertTrue(new UserManager(user2WithAdminRightParentSite).getRight().toSite(childSite).isFromNetwork());
    }

    @Test
    public void testSetUserAndSiteIdForPaymentLogs() {
        final User user = TestUtil.createUser();
        Site parentSite = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(parentSite);
        final ChildSiteSettings settings = TestUtil.createChildSiteSettings(registration, parentSite);
        settings.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);

        Site childSite = TestUtil.createSite();

        //Creating logs.
        PaymentLogRequest paymentLogRequest1 = new PaymentLogRequest();
        paymentLogRequest1.setChildSiteSettingsId(settings.getChildSiteSettingsId());
        PaymentLog paymentLog1 = TestUtil.createPaymentLog(PaymentMethod.PAYPAL,
                TransactionStatus.SENT_CONFIRMED, paymentLogRequest1);
        PaymentLogRequest paymentLogRequest2 = new PaymentLogRequest();
        paymentLogRequest2.setChildSiteSettingsId(settings.getChildSiteSettingsId());
        PaymentLog paymentLog2 = TestUtil.createPaymentLog(PaymentMethod.PAYPAL,
                TransactionStatus.SENT_CONFIRMED, paymentLogRequest2);

        SiteCreator.setUserAndSiteIdForPaymentLogs(settings, user, childSite);
        Assert.assertEquals(settings.getChildSiteSettingsId(), (int) paymentLog1.getChildSiteSettingsId());
        Assert.assertEquals(childSite.getSiteId(), (int) paymentLog1.getSiteId());
        Assert.assertEquals(user.getUserId(), (int) paymentLog1.getUserId());
        Assert.assertEquals(settings.getChildSiteSettingsId(), (int) paymentLog2.getChildSiteSettingsId());
        Assert.assertEquals(childSite.getSiteId(), (int) paymentLog2.getSiteId());
        Assert.assertEquals(user.getUserId(), (int) paymentLog2.getUserId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithoutSEOSettings() {
        final User user = TestUtil.createUser();
        Site parentSite = TestUtil.createSite();
        DraftChildSiteRegistration registration = TestUtil.createChildSiteRegistration(parentSite);
        final ChildSiteSettings settings = TestUtil.createChildSiteSettings(registration, parentSite);
        settings.getSitePaymentSettings().setUserId(123);
        settings.setAccessLevel(SiteAccessLevel.ADMINISTRATOR);

        CreateSiteRequest createSiteRequest = new CreateSiteRequest(null, user, "urlPrefix", "aliaseUrl", "title",
                "description", SiteBlueprintRightType.CAN_ADD_PAGES, null, settings.getChildSiteSettingsId(), SiteType.COMMON, null, null);

        Assert.assertNull(settings.getSite());
        Assert.assertNotNull(settings.getConfirmCode());

        SiteCreator.updateSiteOrCreateNew(createSiteRequest);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
}
