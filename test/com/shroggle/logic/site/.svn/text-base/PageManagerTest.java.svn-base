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
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.logic.site.page.Doctype;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.PageSettingsManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemMock;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class PageManagerTest {

    @Test
    public void testGetKeywords() throws Exception {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(page);
        draftPageSettings.setKeywords(null);

        Assert.assertEquals("", new PageManager(page).getKeywords());
    }

    @Test
    public void testCreateWithPageSettings_draft() throws Exception {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(page);
        TestUtil.createWorkPageSettings(draftPageSettings);

        final PageManager pageManager = new PageManager(new PageSettingsManager(draftPageSettings));
        Assert.assertEquals(false, pageManager.getPageSettingsManager().isWork());
    }

    @Test
    public void testCreateWithPageSettings_work() throws Exception {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final DraftPageSettings draftPageSettings = TestUtil.createPageSettings(page);
        final WorkPageSettings workPageSettings = TestUtil.createWorkPageSettings(draftPageSettings);

        final PageManager pageManager = new PageManager(new PageSettingsManager(workPageSettings));
        Assert.assertEquals(true, pageManager.getPageSettingsManager().isWork());
    }

    @Test
    public void getPublicUrlForWorkWithoutDraft() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final WorkPageSettings pageVersionWork = TestUtil.createWorkPageSettings(page.getPageSettings());

        pageVersionWork.setPage(page);
        pageVersionWork.setUrl("workPageVersionUrl");

        page.getSite().setSubDomain("temp");
        page.getPageSettings().setUrl("godzila");

        final SiteShowOption siteShowOption = null;
        final PageManager pageVersionManager = new PageManager(page, siteShowOption);
        Assert.assertEquals("http://temp.shroggle.com/godzila", pageVersionManager.getPublicUrl());
    }

    @Test
    public void getPublicUrlForDraftWithoutWork() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final DraftPageSettings pageSettings = TestUtil.createPageSettings(page);

        pageSettings.setPage(page);
        pageSettings.setUrl("workPageVersionUrl");

        page.getSite().setSubDomain("temp");
        page.getPageSettings().setUrl("godzila");

        final SiteShowOption siteShowOption = null;
        final PageManager pageManager = new PageManager(page, siteShowOption);
        Assert.assertEquals("http://temp.shroggle.com/godzila", pageManager.getPublicUrl());
    }

    @Test
    public void getWorkPublicUrl() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final WorkPageSettings pageVersionWork = TestUtil.createWorkPageSettings(page.getPageSettings());

        pageVersionWork.setPage(page);
        pageVersionWork.setUrl("workPageVersionUrl");

        page.getSite().setSubDomain("temp");
        page.getPageSettings().setUrl("godzila");

        final SiteShowOption siteShowOption = null;
        final PageManager pageVersionManager = new PageManager(page, siteShowOption);
        Assert.assertEquals("http://temp.shroggle.com/workPageVersionUrl", pageVersionManager.getWorkPublicUrl());
    }

    @Test
    public void getWorkPublicUrlWithSiteAliaseUrl() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        page.getSite().setSubDomain("temp");
        page.getSite().setCustomUrl("ff");
        page.getPageSettings().setUrl("godzila");

        final WorkPageSettings pageVersionWork = TestUtil.createWorkPageSettings(page.getPageSettings());
        pageVersionWork.setPage(page);
        pageVersionWork.setUrl("workPageVersionUrl");

        final SiteShowOption siteShowOption = null;
        final PageManager pageVersionManager = new PageManager(page, siteShowOption);
        Assert.assertEquals("http://ff/workPageVersionUrl", pageVersionManager.getWorkPublicUrl());
    }
    /*---------------------------------------------Get Internal Page Url----------------------------------------------*/

    @Test
    public void testGetInternalPageUrl_withDraftAndWork_INSIDE_APP() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final DraftPageSettings draftVersion = page.getPageSettings();
        draftVersion.setName("pageVersionNameDraft");
        draftVersion.setUrl("pageVersionUrlDraft");

        final PageSettings workVersion = TestUtil.createWorkPageSettings(draftVersion);
        workVersion.setName("pageVersionNameWork");
        workVersion.setUrl("pageVersionUrlWork");

        // When we are working inside app or on user pages, we should always work with draft versions.
        Assert.assertEquals("showPageVersion.action?pageId=" + page.getPageId() +
                "&siteShowOption=ON_USER_PAGES", new PageManager(page, SiteShowOption.INSIDE_APP).getUrl());
    }

    @Test
    public void testGetInternalPageUrl_withDraftAndWork_ON_USER_PAGES() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final DraftPageSettings draftVersion = page.getPageSettings();
        draftVersion.setName("pageVersionNameDraft");
        draftVersion.setUrl("pageVersionUrlDraft");

        final PageSettings workVersion = TestUtil.createWorkPageSettings(draftVersion);
        workVersion.setName("pageVersionNameWork");
        workVersion.setUrl("pageVersionUrlWork");

        // When we are working inside app or on user pages, we should always work with draft versions.
        Assert.assertEquals("showPageVersion.action?pageId=" + page.getPageId() +
                "&siteShowOption=ON_USER_PAGES", new PageManager(page, SiteShowOption.ON_USER_PAGES).getUrl());
    }

    @Test
    public void testGetInternalPageUrl_withDraftAndWork_OUTSIDE_APP() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final DraftPageSettings draftVersion = page.getPageSettings();
        draftVersion.setName("pageVersionNameDraft");
        draftVersion.setUrl("pageVersionUrlDraft");

        final PageSettings workVersion = TestUtil.createWorkPageSettings(draftVersion);
        workVersion.setName("pageVersionNameWork");
        workVersion.setUrl("pageVersionUrlWork");

        Assert.assertEquals("/pageVersionUrlWork", new PageManager(page, SiteShowOption.OUTSIDE_APP).getUrl());
    }

    @Test
    public void testGetInternalPageUrl_withDraft_INSIDE_APP() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final DraftPageSettings draftVersion = page.getPageSettings();
        draftVersion.setName("pageVersionNameDraft");
        draftVersion.setUrl("pageVersionUrlDraft");

        Assert.assertEquals("showPageVersion.action?pageId=" + page.getPageId() +
                "&siteShowOption=ON_USER_PAGES", new PageManager(page, SiteShowOption.INSIDE_APP).getUrl());
    }

    @Test
    public void testGetInternalPageUrl_withDraft_ON_USER_PAGES() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final DraftPageSettings draftVersion = page.getPageSettings();
        draftVersion.setName("pageVersionNameDraft");
        draftVersion.setUrl("pageVersionUrlDraft");

        Assert.assertEquals("showPageVersion.action?pageId=" + page.getPageId() +
                "&siteShowOption=ON_USER_PAGES", new PageManager(page, SiteShowOption.ON_USER_PAGES).getUrl());
    }

    @Test
    public void testGetInternalPageUrl_withDraft_OUTSIDE_APP() {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createWorkPage(site);
        PageManager pageManager = new PageManager(page);
        pageManager.getWorkPageSettings().setName("pageVersionNameWork");
        pageManager.getWorkPageSettings().setUrl("pageVersionUrlWork");

        Assert.assertEquals("/pageVersionUrlWork", new PageManager(page, SiteShowOption.OUTSIDE_APP).getUrl());
    }
    /*---------------------------------------------Get Internal Page Url----------------------------------------------*/


    /*----------------------------------Get Page For Render Or Login Page For draft-----------------------------------*/

    @Test
    public void testGetPageVersionForRenderOrLoginPageVersion_withAccess() {
        final Site site = TestUtil.createSite();
        site.getAccessibleSettings().setAccess(AccessForRender.UNLIMITED);
        final Page page = TestUtil.createPage(site);
        page.getPageSettings().getAccessibleSettings().setAccess(AccessForRender.UNLIMITED);


        final PageManager pageVersionForRender = PageManager.getPageForRenderOrLoginPage(page.getPageId(), SiteShowOption.INSIDE_APP);


        Assert.assertNotNull(pageVersionForRender);
        Assert.assertEquals(page.getPageId(), pageVersionForRender.getPageId());
    }

    @Test
    public void testGetPageVersionForRenderOrLoginPageVersion_withAccess_withoutPageVersion() {
        final Site site = TestUtil.createSite();
        site.getAccessibleSettings().setAccess(AccessForRender.UNLIMITED);
        final Page page = TestUtil.createPage(site);
        page.getPageSettings().getAccessibleSettings().setAccess(AccessForRender.UNLIMITED);


        final PageManager pageVersionForRender = PageManager.getPageForRenderOrLoginPage(null, SiteShowOption.INSIDE_APP);


        Assert.assertNull(pageVersionForRender);
    }

    @Test
    public void testGetPageVersionForRenderOrLoginPageVersion_withoutAccess_withoutAdminRequired() {
        final Site site = TestUtil.createSite();
        site.getAccessibleSettings().setAccess(AccessForRender.RESTRICTED);
        final Page page = TestUtil.createPage(site);
        page.getPageSettings().getAccessibleSettings().setAccess(AccessForRender.UNLIMITED);


        final PageManager pageVersionForRender = PageManager.getPageForRenderOrLoginPage(page.getPageId(), SiteShowOption.INSIDE_APP);


        Assert.assertNotNull(pageVersionForRender);
        Assert.assertNotSame(page.getPageId(), pageVersionForRender.getPageId());
        Assert.assertEquals(site.getLoginPage().getPageId(), pageVersionForRender.getPageId());
    }

    @Test
    public void testGetPageVersionForRenderOrLoginPageVersion_withoutAccess_withoutAdminRequired_withoutLoginPage() {
        final Site site = TestUtil.createSite();
        site.setLoginPage(null);
        site.getAccessibleSettings().setAccess(AccessForRender.RESTRICTED);
        final Page page = TestUtil.createPage(site);
        page.getPageSettings().getAccessibleSettings().setAccess(AccessForRender.UNLIMITED);


        final PageManager pageVersionForRender = PageManager.getPageForRenderOrLoginPage(page.getPageId(), SiteShowOption.INSIDE_APP);


        Assert.assertNull(pageVersionForRender);
    }

    @Test
    public void testGetPageVersionForRenderOrLoginPageVersion_withoutAccess_withAdminRequired() {
        final Site site = TestUtil.createSite();
        final Page loginAdminPage = TestUtil.createPage(site);
        TestUtil.createPageVersion(loginAdminPage);
        site.setLoginAdminPage(loginAdminPage);
        site.getAccessibleSettings().setAccess(AccessForRender.RESTRICTED);
        site.getAccessibleSettings().setAdministrators(true);
        final Page page = TestUtil.createPage(site);
        page.getPageSettings().getAccessibleSettings().setAccess(AccessForRender.UNLIMITED);


        final PageManager pageVersionForRender = PageManager.getPageForRenderOrLoginPage(page.getPageId(), SiteShowOption.INSIDE_APP);


        Assert.assertNotNull(pageVersionForRender);
        Assert.assertNotSame(page.getPageId(), pageVersionForRender.getPageId());
        Assert.assertEquals(site.getLoginAdminPage().getPageId(), pageVersionForRender.getPageId());
    }

    @Test
    public void testGetPageVersionForRenderOrLoginPageVersion_withoutAccess_withAdminRequired_withoutLoginAdminPage() {
        final Site site = TestUtil.createSite();
        site.setLoginAdminPage(null);
        site.getAccessibleSettings().setAccess(AccessForRender.RESTRICTED);
        site.getAccessibleSettings().setAdministrators(true);
        final Page page = TestUtil.createPage(site);
        page.getPageSettings().getAccessibleSettings().setAccess(AccessForRender.UNLIMITED);


        final PageManager pageVersionForRender = PageManager.getPageForRenderOrLoginPage(page.getPageId(), SiteShowOption.INSIDE_APP);


        Assert.assertNull(pageVersionForRender);
    }

    /*----------------------------------Get Page For Render Or Login Page For draft-----------------------------------*/

    /*----------------------------------Get Page For Render Or Login Page For work------------------------------------*/

    @Test
    public void testGetPageVersionForRenderOrLoginPageVersion_withAccess_work() {
        final Site site = TestUtil.createSite();
        site.getAccessibleSettings().setAccess(AccessForRender.UNLIMITED);
        final Page page = TestUtil.createPage(site);
        page.getPageSettings().getAccessibleSettings().setAccess(AccessForRender.UNLIMITED);
        TestUtil.createWorkPageSettings(page.getPageSettings());

        final PageManager pageVersionForRender = PageManager.getPageForRenderOrLoginPage(page.getPageId(), SiteShowOption.OUTSIDE_APP);


        Assert.assertNotNull(pageVersionForRender);
        Assert.assertEquals(page.getPageId(), pageVersionForRender.getPageId());
    }

    @Test
    public void testGetPageVersionForRenderOrLoginPageVersion_withAccess_withoutPageVersion_work() {
        final Site site = TestUtil.createSite();
        site.getAccessibleSettings().setAccess(AccessForRender.UNLIMITED);
        final Page page = TestUtil.createPage(site);
        page.getPageSettings().getAccessibleSettings().setAccess(AccessForRender.UNLIMITED);
        TestUtil.createWorkPageSettings(page.getPageSettings());


        final PageManager pageVersionForRender = PageManager.getPageForRenderOrLoginPage(null, SiteShowOption.OUTSIDE_APP);


        Assert.assertNull(pageVersionForRender);
    }

    @Test
    public void testGetPageVersionForRenderOrLoginPageVersion_withoutAccess_withoutAdminRequired_work() {
        final Site site = TestUtil.createSite();
        site.getAccessibleSettings().setAccess(AccessForRender.RESTRICTED);
        final Page page = TestUtil.createPage(site);
        page.getPageSettings().getAccessibleSettings().setAccess(AccessForRender.UNLIMITED);
        TestUtil.createWorkPageSettings(page.getPageSettings());


        final PageManager pageVersionForRender = PageManager.getPageForRenderOrLoginPage(page.getPageId(), SiteShowOption.OUTSIDE_APP);


        Assert.assertNotNull(pageVersionForRender);
        Assert.assertNotSame(page.getPageId(), pageVersionForRender.getPageId());
        Assert.assertEquals(site.getLoginPage().getPageId(), pageVersionForRender.getPageId());
    }

    @Test
    public void testGetPageVersionForRenderOrLoginPageVersion_withoutAccess_withoutAdminRequired_withoutLoginPage_work() {
        final Site site = TestUtil.createSite();
        site.setLoginPage(null);
        site.getAccessibleSettings().setAccess(AccessForRender.RESTRICTED);
        final Page page = TestUtil.createPage(site);
        page.getPageSettings().getAccessibleSettings().setAccess(AccessForRender.UNLIMITED);
        TestUtil.createWorkPageSettings(page.getPageSettings());


        final PageManager pageVersionForRender = PageManager.getPageForRenderOrLoginPage(page.getPageId(), SiteShowOption.OUTSIDE_APP);


        Assert.assertNull(pageVersionForRender);
    }

    @Test
    public void testGetPageVersionForRenderOrLoginPageVersion_withoutAccess_withAdminRequired_work() {
        final Site site = TestUtil.createSite();
        final Page loginAdminPage = TestUtil.createPage(site);
        TestUtil.createPageVersion(loginAdminPage);
        site.setLoginAdminPage(loginAdminPage);
        site.getAccessibleSettings().setAccess(AccessForRender.RESTRICTED);
        site.getAccessibleSettings().setAdministrators(true);
        final Page page = TestUtil.createPage(site);
        page.getPageSettings().getAccessibleSettings().setAccess(AccessForRender.UNLIMITED);
        TestUtil.createWorkPageSettings(page.getPageSettings());


        final PageManager pageVersionForRender = PageManager.getPageForRenderOrLoginPage(page.getPageId(), SiteShowOption.OUTSIDE_APP);


        Assert.assertNotNull(pageVersionForRender);
        Assert.assertNotSame(page.getPageId(), pageVersionForRender.getPageId());
        Assert.assertEquals(site.getLoginAdminPage().getPageId(), pageVersionForRender.getPageId());
    }

    @Test
    public void testGetPageVersionForRenderOrLoginPageVersion_withoutAccess_withAdminRequired_withoutLoginAdminPage_work() {
        final Site site = TestUtil.createSite();
        site.setLoginAdminPage(null);
        site.getAccessibleSettings().setAccess(AccessForRender.RESTRICTED);
        site.getAccessibleSettings().setAdministrators(true);
        final Page page = TestUtil.createPage(site);
        page.getPageSettings().getAccessibleSettings().setAccess(AccessForRender.UNLIMITED);
        TestUtil.createWorkPageSettings(page.getPageSettings());


        final PageManager pageVersionForRender = PageManager.getPageForRenderOrLoginPage(page.getPageId(), SiteShowOption.OUTSIDE_APP);


        Assert.assertNull(pageVersionForRender);
    }

    /*----------------------------------Get Page For Render Or Login Page For work------------------------------------*/


    @Test
    public void getRealHtmlInPageVersion() {
        final Site site = TestUtil.createSite();
        Page pageVersion = TestUtil.createPage(site);
        pageVersion.getPageSettings().setHtml("F");
        PageManager pageVersionManager = new PageManager(pageVersion, SiteShowOption.INSIDE_APP);

        Assert.assertEquals(Doctype.TRANSITIONAL + "\nF", pageVersionManager.getSavedHtmlOrDefault());
    }

    @Test
    public void getRealHtmlInPageVersion_withDoctype() {
        final Site site = TestUtil.createSite();
        Page pageVersion = TestUtil.createPage(site);
        pageVersion.getPageSettings().setHtml(Doctype.TRANSITIONAL + "F");
        PageManager pageVersionManager = new PageManager(pageVersion, SiteShowOption.INSIDE_APP);

        Assert.assertEquals("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">F", pageVersionManager.getSavedHtmlOrDefault());
    }

    @Test
    public void getRealHtmlInPageVersionTemplate() {
        final Site site = TestUtil.createSite();
        Page pageVersion = TestUtil.createPage(site);
        pageVersion.getPageSettings().setThemeId(new ThemeId("A", "F"));
        pageVersion.getPageSettings().setLayoutFile("g");
        fileSystemMock.addTemplateResource("A", "g", "M");
        PageManager pageVersionManager = new PageManager(pageVersion, SiteShowOption.INSIDE_APP);

        Assert.assertEquals(Doctype.TRANSITIONAL + "\nM", pageVersionManager.getSavedHtmlOrDefault());
    }

    @Test
    public void getRealHtmlInSiteTemplate() {
        Site site = new Site();
        site.setThemeId(new ThemeId("A", "F"));
        Page page = TestUtil.createPage(site);
        Page pageVersion = TestUtil.createPage(site);
        pageVersion.getPageSettings().setPage(page);
        pageVersion.getPageSettings().setLayoutFile("g");
        fileSystemMock.addTemplateResource("A", "g", "M");
        PageManager pageVersionManager = new PageManager(pageVersion, SiteShowOption.INSIDE_APP);

        Assert.assertEquals(Doctype.TRANSITIONAL + "\nM", pageVersionManager.getSavedHtmlOrDefault());
    }

    @Test
    public void getRealThemeIdInPageVersion() {
        final Site site = TestUtil.createSite();
        Page pageVersion = TestUtil.createPage(site);
        pageVersion.getPageSettings().setThemeId(new ThemeId("A", "F"));
        Assert.assertEquals(
                new ThemeId("A", "F"),
                new PageManager(pageVersion, SiteShowOption.INSIDE_APP).getRealThemeId());
    }

    @Test
    public void getRealThemeIdInSite() {
        Site site = new Site();
        site.setThemeId(new ThemeId("A", "F"));
        Page page = TestUtil.createPage(site);
        Page pageVersion = TestUtil.createPage(site);


        Assert.assertEquals(
                new ThemeId("A", "F"),
                new PageManager(pageVersion, SiteShowOption.INSIDE_APP).getRealThemeId());
    }

    /*----------------------------------------------------Publish-----------------------------------------------------*/

    @Test
    public void testPublish_withoutOldWorkSettings() throws Exception {
        final Page page = TestUtil.createPageAndSite();
        final PageManager pageManager = new PageManager(page);

        pageManager.setBlueprintLocked(true);
        pageManager.setBlueprintRequired(true);
        pageManager.setCss("oldCss");
        pageManager.setHtml("oldHtml");
        pageManager.setName("oldName");
        pageManager.setOwnDomainName("oldDomainName");


        final Widget widget = new WidgetItem();
        ServiceLocator.getPersistance().putWidget(widget);
        pageManager.addWidget(widget);

        Assert.assertEquals(null, pageManager.getWorkPageSettings());
        Assert.assertEquals(false, pageManager.getDraftPageSettings().isChanged());


        pageManager.publish();


        Assert.assertNotNull(pageManager.getWorkPageSettings());
        Assert.assertEquals(false, pageManager.getDraftPageSettings().isChanged());
        Assert.assertEquals(1, pageManager.getWorkPageSettings().getWidgets().size());
        Assert.assertNotSame(widget.getWidgetId(), pageManager.getWorkPageSettings().getWidgets().get(0));
        Assert.assertNotNull(pageManager.getWorkPageSettings().getWidgets().get(0));

        /*Checking few pageSettings properties to be sure that PageSettingsManager.Copier.copyGeneralSettingsTo(); has been executed*/
        Assert.assertEquals(true, pageManager.getWorkPageSettings().isBlueprintLocked());
        Assert.assertEquals(true, pageManager.getWorkPageSettings().isBlueprintRequired());
        Assert.assertEquals("oldCss", pageManager.getWorkPageSettings().getCss());
        Assert.assertEquals("oldHtml", pageManager.getWorkPageSettings().getHtml());
        Assert.assertEquals("oldName", pageManager.getWorkPageSettings().getName());
        Assert.assertEquals("oldDomainName", pageManager.getWorkPageSettings().getOwnDomainName());
        /*Checking few pageSettings properties to be sure that PageSettingsManager.Copier.copyGeneralSettingsTo(); has been executed*/
    }


    @Test
    public void testPublish_withOldWorkSettings() throws Exception {
        final Page page = TestUtil.createPage(TestUtil.createSite());

        final WorkPageSettings workPageSettings = new WorkPageSettings();
        workPageSettings.setPage(page);
        workPageSettings.setPageSettingsId(page.getPageSettings().getPageSettingsId());
        workPageSettings.setWidgets(Collections.<Widget>emptyList());
        ServiceLocator.getPersistance().putPageSettings(workPageSettings);
        final AccessibleSettings accessibleSettings = new AccessibleSettings();
        ServiceLocator.getPersistance().putAccessibleSettings(accessibleSettings);
        workPageSettings.setAccessibleSettings(accessibleSettings);

        final Widget widget = new WidgetItem();
        ServiceLocator.getPersistance().putWidget(widget);

        PageManager pageManager = new PageManager(page);
        pageManager.addWidget(widget);
        pageManager.setChanged(true);

        Assert.assertEquals(true, pageManager.getDraftPageSettings().isChanged());


        pageManager.publish();


        Assert.assertNotNull(pageManager.getWorkPageSettings());
        Assert.assertEquals(false, pageManager.getDraftPageSettings().isChanged());
        Assert.assertEquals(1, pageManager.getWorkPageSettings().getWidgets().size());
        Assert.assertNotSame(widget.getWidgetId(), pageManager.getWorkPageSettings().getWidgets().get(0));
        Assert.assertNotNull(pageManager.getWorkPageSettings().getWidgets().get(0));
    }
    /*----------------------------------------------------Publish-----------------------------------------------------*/


    /*-------------------------------------------------Reset Changes--------------------------------------------------*/

    @Test
    public void testResetChanges_withoutOldWorkSettings() throws Exception {
        final Page page = TestUtil.createPageAndSite();
        PageManager pageManager = new PageManager(page);

        final Widget widget = new WidgetItem();
        ServiceLocator.getPersistance().putWidget(widget);
        pageManager.addWidget(widget);

        Assert.assertEquals(null, pageManager.getWorkPageSettings());
        Assert.assertEquals(false, pageManager.getDraftPageSettings().isChanged());


        pageManager.resetChanges();


        Assert.assertEquals(null, pageManager.getWorkPageSettings());
        Assert.assertEquals(false, pageManager.getDraftPageSettings().isChanged());
    }


    @Test
    public void testResetChanges_withOldWorkSettings() throws Exception {
        final Page page = TestUtil.createPage(TestUtil.createSite());
        page.getPageSettings().setWidgets(Collections.<Widget>emptyList());
        final Widget widget = new WidgetItem();
        ServiceLocator.getPersistance().putWidget(widget);


        final WorkPageSettings workPageSettings = new WorkPageSettings();
        workPageSettings.setPage(page);
        workPageSettings.setPageSettingsId(page.getPageSettings().getPageSettingsId());
        final AccessibleSettings accessibleSettings = new AccessibleSettings();
        ServiceLocator.getPersistance().putAccessibleSettings(accessibleSettings);
        ServiceLocator.getPersistance().putPageSettings(workPageSettings);
        workPageSettings.addWidget(widget);
        workPageSettings.setAccessibleSettings(accessibleSettings);

        PageManager pageManager = new PageManager(page);
        pageManager.setChanged(true);


        pageManager.resetChanges();


        Assert.assertEquals(false, pageManager.getDraftPageSettings().isChanged());
        Assert.assertEquals(1, pageManager.getDraftPageSettings().getWidgets().size());
        Assert.assertNotSame(widget.getWidgetId(), pageManager.getDraftPageSettings().getWidgets().get(0));
        Assert.assertNotNull(pageManager.getDraftPageSettings().getWidgets().get(0));
    }

    @Test
    public void testUpdateBackground_withOld() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager pageManager = TestUtil.createPageVersionAndPage(site);
        final Background background = TestUtil.createBackground();
        background.setBackgroundRepeat("repeatttttttt");
        background.setBackgroundColor("green");
        pageManager.setBackground(background);

        final Background request = new Background();
        request.setBackgroundRepeat("no-repeat");
        request.setBackgroundColor("red");
        pageManager.updateBackground(request, false);


        Assert.assertEquals(background.getId(), pageManager.getBackground().getId());
        Assert.assertEquals(pageManager.getBackground().getBackgroundColor(), "red");
        Assert.assertEquals(pageManager.getBackground().getBackgroundRepeat(), "no-repeat");
    }

    @Test
    public void testUpdateBackground_withoutOld() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager pageManager = TestUtil.createPageVersionAndPage(site);

        final Background request = new Background();
        request.setBackgroundRepeat("no-repeat");
        request.setBackgroundColor("red");
        pageManager.updateBackground(request, false);

        Assert.assertEquals(pageManager.getBackground().getBackgroundColor(), "red");
        Assert.assertEquals(pageManager.getBackground().getBackgroundRepeat(), "no-repeat");
    }

    @Test
    public void testUpdateBackground_withOld_applyToAllPages() throws Exception {
        final Site site = TestUtil.createSite();

        final PageManager pageManager = TestUtil.createPageVersionAndPage(site);
        final Background background = TestUtil.createBackground();
        background.setBackgroundRepeat("repeatttttttt");
        background.setBackgroundColor("green");
        pageManager.setBackground(background);

        final PageManager pageManager2 = TestUtil.createPageVersionAndPage(site);
        final Background background2 = TestUtil.createBackground();
        background2.setBackgroundRepeat("aaaaaaaaa");
        background2.setBackgroundColor("yellow");
        pageManager2.setBackground(background2);


        final Background request = new Background();
        request.setBackgroundRepeat("no-repeat");
        request.setBackgroundColor("red");
        pageManager.updateBackground(request, true);


        Assert.assertEquals(background.getId(), pageManager.getBackground().getId());
        Assert.assertEquals(pageManager.getBackground().getBackgroundColor(), "red");
        Assert.assertEquals(pageManager.getBackground().getBackgroundRepeat(), "no-repeat");


        Assert.assertEquals(background2.getId(), pageManager2.getBackground().getId());
        Assert.assertEquals(pageManager2.getBackground().getBackgroundColor(), "red");
        Assert.assertEquals(pageManager2.getBackground().getBackgroundRepeat(), "no-repeat");
    }

    @Test
    public void testUpdateBackground_withOld_notApplyToAllPages() throws Exception {
        final Site site = TestUtil.createSite();

        final PageManager pageManager = TestUtil.createPageVersionAndPage(site);
        final Background background = TestUtil.createBackground();
        background.setBackgroundRepeat("repeatttttttt");
        background.setBackgroundColor("green");
        pageManager.setBackground(background);

        final PageManager pageManager2 = TestUtil.createPageVersionAndPage(site);
        final Background background2 = TestUtil.createBackground();
        background2.setBackgroundRepeat("aaaaaaaaa");
        background2.setBackgroundColor("yellow");
        pageManager2.setBackground(background2);


        final Background request = new Background();
        request.setBackgroundRepeat("no-repeat");
        request.setBackgroundColor("red");
        pageManager.updateBackground(request, false);


        Assert.assertEquals(background.getId(), pageManager.getBackground().getId());
        Assert.assertEquals(pageManager.getBackground().getBackgroundColor(), "red");
        Assert.assertEquals(pageManager.getBackground().getBackgroundRepeat(), "no-repeat");


        Assert.assertEquals(background2.getId(), pageManager2.getBackground().getId());
        Assert.assertEquals(pageManager2.getBackground().getBackgroundColor(), "yellow");
        Assert.assertEquals(pageManager2.getBackground().getBackgroundRepeat(), "aaaaaaaaa");
    }
    /*------------------------------------------------Update Background-----------------------------------------------*/

    /*--------------------------------------------------Update Border-------------------------------------------------*/

    @Test
    public void testUpdateBorder_withOld() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager pageManager = TestUtil.createPageVersionAndPage(site);
        final Border border = TestUtil.createBorder();
        final StyleValue styleValue = new StyleValue();
        styleValue.setTopValue("top");
        styleValue.setRightValue("right");
        styleValue.setBottomValue("bottom");
        styleValue.setLeftValue("left");
        border.getBorderColor().setValues(styleValue);
        pageManager.setBorder(border);

        final Border request = new Border();
        final StyleValue styleValue2 = new StyleValue();
        styleValue2.setTopValue("aaa");
        styleValue2.setRightValue("aaaaaaaaa");
        styleValue2.setBottomValue("aaaa");
        styleValue2.setLeftValue("aaa");
        request.getBorderColor().setValues(styleValue2);
        pageManager.updateBorder(request, false);


        Assert.assertEquals(border.getId(), pageManager.getBorder().getId());
        Assert.assertEquals(pageManager.getBorder().getBorderColor().getValues().getTopValue(), "aaa");
        Assert.assertEquals(pageManager.getBorder().getBorderColor().getValues().getRightValue(), "aaaaaaaaa");
        Assert.assertEquals(pageManager.getBorder().getBorderColor().getValues().getBottomValue(), "aaaa");
        Assert.assertEquals(pageManager.getBorder().getBorderColor().getValues().getLeftValue(), "aaa");
    }

    @Test
    public void testUpdateBorder_withoutOld() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager pageManager = TestUtil.createPageVersionAndPage(site);

        final Border request = new Border();
        final StyleValue styleValue = new StyleValue();
        styleValue.setTopValue("aaa");
        styleValue.setRightValue("aaaaaaaaa");
        styleValue.setBottomValue("aaaa");
        styleValue.setLeftValue("aaa");
        request.getBorderColor().setValues(styleValue);
        pageManager.updateBorder(request, false);

        Assert.assertEquals(pageManager.getBorder().getBorderColor().getValues().getTopValue(), "aaa");
        Assert.assertEquals(pageManager.getBorder().getBorderColor().getValues().getRightValue(), "aaaaaaaaa");
        Assert.assertEquals(pageManager.getBorder().getBorderColor().getValues().getBottomValue(), "aaaa");
        Assert.assertEquals(pageManager.getBorder().getBorderColor().getValues().getLeftValue(), "aaa");
    }

    @Test
    public void testUpdateBorder_withOld_applyToAllPages() throws Exception {
        final Site site = TestUtil.createSite();

        final PageManager pageManager = TestUtil.createPageVersionAndPage(site);
        final Border border = TestUtil.createBorder();
        final StyleValue styleValue = new StyleValue();
        styleValue.setTopValue("top");
        styleValue.setRightValue("right");
        styleValue.setBottomValue("bottom");
        styleValue.setLeftValue("left");
        border.getBorderColor().setValues(styleValue);
        pageManager.setBorder(border);

        final PageManager pageManager2 = TestUtil.createPageVersionAndPage(site);
        final Border border2 = TestUtil.createBorder();
        final StyleValue styleValue2 = new StyleValue();
        styleValue2.setTopValue("top");
        styleValue2.setRightValue("right");
        styleValue2.setBottomValue("bottom");
        styleValue2.setLeftValue("left");
        border2.getBorderColor().setValues(styleValue2);
        pageManager2.setBorder(border2);


        final Border request = new Border();
        final StyleValue styleValue3 = new StyleValue();
        styleValue3.setTopValue("aaa");
        styleValue3.setRightValue("aaaaaaaaa");
        styleValue3.setBottomValue("aaaa");
        styleValue3.setLeftValue("aaa");
        request.getBorderColor().setValues(styleValue3);
        pageManager.updateBorder(request, true);

        Assert.assertEquals(border.getId(), pageManager.getBorder().getId());
        Assert.assertEquals(pageManager.getBorder().getBorderColor().getValues().getTopValue(), "aaa");
        Assert.assertEquals(pageManager.getBorder().getBorderColor().getValues().getRightValue(), "aaaaaaaaa");
        Assert.assertEquals(pageManager.getBorder().getBorderColor().getValues().getBottomValue(), "aaaa");
        Assert.assertEquals(pageManager.getBorder().getBorderColor().getValues().getLeftValue(), "aaa");

        Assert.assertEquals(border2.getId(), pageManager2.getBorder().getId());
        Assert.assertEquals(pageManager2.getBorder().getBorderColor().getValues().getTopValue(), "aaa");
        Assert.assertEquals(pageManager2.getBorder().getBorderColor().getValues().getRightValue(), "aaaaaaaaa");
        Assert.assertEquals(pageManager2.getBorder().getBorderColor().getValues().getBottomValue(), "aaaa");
        Assert.assertEquals(pageManager2.getBorder().getBorderColor().getValues().getLeftValue(), "aaa");
    }

    @Test
    public void testUpdateBorder_withOld_notApplyToAllPages() throws Exception {
        final Site site = TestUtil.createSite();

        final PageManager pageManager = TestUtil.createPageVersionAndPage(site);
        final Border border = TestUtil.createBorder();
        final StyleValue styleValue = new StyleValue();
        styleValue.setTopValue("top");
        styleValue.setRightValue("right");
        styleValue.setBottomValue("bottom");
        styleValue.setLeftValue("left");
        border.getBorderColor().setValues(styleValue);
        pageManager.setBorder(border);

        final PageManager pageManager2 = TestUtil.createPageVersionAndPage(site);
        final Border border2 = TestUtil.createBorder();
        final StyleValue styleValue2 = new StyleValue();
        styleValue2.setTopValue("top2");
        styleValue2.setRightValue("right2");
        styleValue2.setBottomValue("bottom2");
        styleValue2.setLeftValue("left2");
        border2.getBorderColor().setValues(styleValue2);
        pageManager2.setBorder(border2);


        final Border request = new Border();
        final StyleValue styleValue3 = new StyleValue();
        styleValue3.setTopValue("aaa");
        styleValue3.setRightValue("aaaaaaaaa");
        styleValue3.setBottomValue("aaaa");
        styleValue3.setLeftValue("aaa");
        request.getBorderColor().setValues(styleValue3);
        pageManager.updateBorder(request, false);


        Assert.assertEquals(border.getId(), pageManager.getBorder().getId());
        Assert.assertEquals(pageManager.getBorder().getBorderColor().getValues().getTopValue(), "aaa");
        Assert.assertEquals(pageManager.getBorder().getBorderColor().getValues().getRightValue(), "aaaaaaaaa");
        Assert.assertEquals(pageManager.getBorder().getBorderColor().getValues().getBottomValue(), "aaaa");
        Assert.assertEquals(pageManager.getBorder().getBorderColor().getValues().getLeftValue(), "aaa");


        Assert.assertEquals(border2.getId(), pageManager2.getBorder().getId());
        Assert.assertEquals(pageManager2.getBorder().getBorderColor().getValues().getTopValue(), "top2");
        Assert.assertEquals(pageManager2.getBorder().getBorderColor().getValues().getRightValue(), "right2");
        Assert.assertEquals(pageManager2.getBorder().getBorderColor().getValues().getBottomValue(), "bottom2");
        Assert.assertEquals(pageManager2.getBorder().getBorderColor().getValues().getLeftValue(), "left2");
    }
    /*--------------------------------------------------Update Border-------------------------------------------------*/

    @Test
    public void testGetAccessibleParent() throws Exception {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        Assert.assertEquals(site, new PageManager(page).getAccessibleParent());
    }

    @Test
    public void testGetAccessibleParent_forSystemPage() throws Exception {
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        page.setSystem(true);
        Assert.assertEquals(null, new PageManager(page).getAccessibleParent());
    }

    @Test(expected = PageNotFoundException.class)
    public void testGetAccessibleParent_withoutPageSettings() throws Exception {
        final Page page = null;
        new PageManager(page).getAccessibleParent();
    }

    private final FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
}
