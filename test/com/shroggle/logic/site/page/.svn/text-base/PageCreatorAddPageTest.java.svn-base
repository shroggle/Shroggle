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
package com.shroggle.logic.site.page;

import com.shroggle.presentation.site.page.SavePageRequest;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.entity.*;
import com.shroggle.TestUtil;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.exception.*;
import com.shroggle.util.filesystem.FileSystemMock;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.HashSet;
import java.util.Date;
import java.util.ArrayList;

/**
 * @author dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class PageCreatorAddPageTest {

    @Test
    public void savePageNameTab() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "<!-- MEDIA_BLOCK --><!-- MEDIA_BLOCK -->");

        KeywordsGroup keywordsGroup = TestUtil.createKeywordsGroup(site);

        Page page = TestUtil.createPage(site);
        page.getPageSettings().setLayoutFile("b");

        PageManager pageVersion = new PageManager(page);
        pageVersion.setName("333333333333333");
        pageVersion.setUrl("ff  @# 23423");

        SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLANK);
        request.setName("a");
        request.setTitle("1a");
        request.setUrl("urlP");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.getKeywordsGroupIds().add(keywordsGroup.getKeywordsGroupId());
        request.setPageToEditId(page.getPageId());

        final SavePageResponse response = pageCreator.savePageNameTab(request, false);
        final PageManager addPageVersion = new PageManager(persistance.getPage(response.getPageId()));

        Assert.assertNotNull(addPageVersion);
        Assert.assertEquals("a", addPageVersion.getName());
        Assert.assertEquals("1a", addPageVersion.getTitle());
        Assert.assertEquals("showPageVersion.action?pageId=2&siteShowOption=ON_USER_PAGES", addPageVersion.getUrl());
        Assert.assertEquals("urlp", addPageVersion.getDraftPageSettings().getUrl());// Saved url
        Assert.assertEquals(1, addPageVersion.getKeywordsGroups().size());
        Assert.assertEquals(keywordsGroup, addPageVersion.getKeywordsGroups().get(0));
    }

    @Test
    public void savePageNameTabWithUrlInOtherCase() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "<!-- MEDIA_BLOCK --><!-- MEDIA_BLOCK -->");

        KeywordsGroup keywordsGroup = TestUtil.createKeywordsGroup(site);

        Page page = TestUtil.createPage(site);
        page.getPageSettings().setLayoutFile("b");

        PageManager pageVersion = new PageManager(page);
        pageVersion.setName("333333333333333");
        pageVersion.setUrl("ff  @# 23423");

        SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLANK);
        request.setName("a");
        request.setTitle("1a");
        request.setUrl("urlP");
        request.setAliaseUrl("aAa");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.getKeywordsGroupIds().add(keywordsGroup.getKeywordsGroupId());
        request.setPageToEditId(page.getPageId());

        final SavePageResponse response = pageCreator.savePageNameTab(request, false);
        final PageManager addPageVersion = new PageManager(persistance.getPage(response.getPageId()));

        Assert.assertNotNull(addPageVersion);
        Assert.assertEquals("a", addPageVersion.getName());
        Assert.assertEquals("1a", addPageVersion.getTitle());
        Assert.assertEquals("showPageVersion.action?pageId=2&siteShowOption=ON_USER_PAGES", addPageVersion.getUrl());
        Assert.assertEquals("aaa", addPageVersion.getDraftPageSettings().getOwnDomainName());
        Assert.assertEquals("urlp", addPageVersion.getDraftPageSettings().getUrl());
        Assert.assertEquals(1, addPageVersion.getKeywordsGroups().size());
        Assert.assertEquals(keywordsGroup, addPageVersion.getKeywordsGroups().get(0));
    }
    
    @Test
    public void savePageSEOMetaTags() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "<!-- MEDIA_BLOCK --><!-- MEDIA_BLOCK -->");

        KeywordsGroup keywordsGroup = TestUtil.createKeywordsGroup(site);

        Page page = TestUtil.createPage(site);
        page.getPageSettings().setLayoutFile("b");

        PageManager pageVersion = new PageManager(page);
        pageVersion.setName("333333333333333");
        pageVersion.setUrl("ff  @# 23423");

        SavePageRequest request = new SavePageRequest();
        request.setPageToEditId(pageVersion.getId());
        request.setPageType(PageType.BLANK);
        request.setName("a");
        request.setTitle("1a");
        request.setUrl("urlP");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.getKeywordsGroupIds().add(keywordsGroup.getKeywordsGroupId());
        request.getPageSEOSettings().setPageDescription("page_description");
        request.getPageSEOSettings().setRobotsMetaTag("robots_meta_tag");
        request.getPageSEOSettings().setAuthorMetaTag("author_meta_tag");
        request.getPageSEOSettings().setCopyrightMetaTag("copyright_meta_tag");
        request.getPageSEOSettings().setTitleMetaTag("title_meta_tag");
        request.getPageSEOSettings().setCustomMetaTagList(new ArrayList<String>(){{
            add("custom_meta_tag1");
            add("custom_meta_tag2");
        }});
        request.getPageSEOSettings().setHtmlCodeList(new ArrayList<SEOHtmlCode>(){{
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
        request.setPageToEditId(page.getPageId());

        pageCreator.saveSeoMetaTagsTab(request);

        Assert.assertNotNull(pageVersion);
        Assert.assertEquals("333333333333333", pageVersion.getName());
        Assert.assertEquals("", pageVersion.getTitle());
        Assert.assertEquals("showPageVersion.action?pageId=2&siteShowOption=ON_USER_PAGES", pageVersion.getUrl());
        Assert.assertEquals("ff  @# 23423", pageVersion.getDraftPageSettings().getUrl());// Saved url
        Assert.assertEquals(0, pageVersion.getKeywordsGroups().size());
        Assert.assertEquals("page_description", pageVersion.getSeoSettings().getPageDescription());
        Assert.assertEquals("robots_meta_tag", pageVersion.getSeoSettings().getRobotsMetaTag());
        Assert.assertEquals("author_meta_tag", pageVersion.getSeoSettings().getAuthorMetaTag());
        Assert.assertEquals("copyright_meta_tag", pageVersion.getSeoSettings().getCopyrightMetaTag());
        Assert.assertEquals("title_meta_tag", pageVersion.getSeoSettings().getTitleMetaTag());
        Assert.assertEquals(2, pageVersion.getSeoSettings().getCustomMetaTagList().size());
        Assert.assertEquals("custom_meta_tag1", pageVersion.getSeoSettings().getCustomMetaTagList().get(0));
        Assert.assertEquals("custom_meta_tag2", pageVersion.getSeoSettings().getCustomMetaTagList().get(1));
        Assert.assertEquals(2, pageVersion.getSeoSettings().getHtmlCodeList().size());
        Assert.assertEquals("name1", pageVersion.getSeoSettings().getHtmlCodeList().get(0).getName());
        Assert.assertEquals("code1", pageVersion.getSeoSettings().getHtmlCodeList().get(0).getCode());
        Assert.assertEquals(CodePlacement.BEGINNING, pageVersion.getSeoSettings().getHtmlCodeList().get(0).getCodePlacement());
        Assert.assertEquals("name2", pageVersion.getSeoSettings().getHtmlCodeList().get(1).getName());
        Assert.assertEquals("code2", pageVersion.getSeoSettings().getHtmlCodeList().get(1).getCode());
        Assert.assertEquals(CodePlacement.END, pageVersion.getSeoSettings().getHtmlCodeList().get(1).getCodePlacement());
    }

    @Test
    public void executeWithPlusInUrl() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        final Page page = TestUtil.createPage(site);
        page.getPageSettings().setLayoutFile("b");

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "");

        SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLANK);
        request.setName("a");
        request.setTitle("1a");
        request.setUrl("+u+rlP++");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.setPageToEditId(page.getPageId());

        final int pageId = pageCreator.savePageNameTab(request, false).getPageId();
        final PageManager addPageVersion = new PageManager(persistance.getPage(pageId));

        Assert.assertNotNull(addPageVersion);
        Assert.assertEquals("a", addPageVersion.getName());
        Assert.assertEquals("1a", addPageVersion.getTitle());
        Assert.assertEquals("+u+rlp++", addPageVersion.getDraftPageSettings().getUrl());// Saved url
        Assert.assertEquals(0, addPageVersion.getKeywordsGroups().size());
    }

    @Test
    public void executeWithPageThatShouldContainWidget() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        final Page page = TestUtil.createPage(site);
        page.getPageSettings().setLayoutFile("b");

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "<!-- MEDIA_BLOCK --><!-- MEDIA_BLOCK -->");

        SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLOG);
        request.setName("a");
        request.setTitle("1a");
        request.setUrl("url");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.setPageToEditId(page.getPageId());

        final SavePageResponse response = pageCreator.savePageNameTab(request, false);
        final PageManager addPageVersion = new PageManager(persistance.getPage(response.getPageId()));

        Assert.assertNotNull(response.getCreateWidgetId());
        final Widget createdWidget = persistance.getWidget(response.getCreateWidgetId());

        Assert.assertNotNull(createdWidget);

        Assert.assertNotNull(addPageVersion);
        Assert.assertEquals("a", addPageVersion.getName());
        Assert.assertEquals("1a", addPageVersion.getTitle());
        Assert.assertEquals("url", addPageVersion.getDraftPageSettings().getUrl());// Saved url
        Assert.assertEquals(0, addPageVersion.getKeywordsGroups().size());
    }

    @Test
    public void executeWithPageThatShouldNOTContainWidget() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        final Page page = TestUtil.createPage(site);
        page.getPageSettings().setLayoutFile("b");

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "<!-- MEDIA_BLOCK --><!-- MEDIA_BLOCK -->");

        SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLANK);
        request.setName("a");
        request.setTitle("1a");
        request.setUrl("url");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.setPageToEditId(page.getPageId());

        final SavePageResponse response = pageCreator.savePageNameTab(request, false);
        final PageManager addPageVersion = new PageManager(persistance.getPage(response.getPageId()));

        Assert.assertNull(response.getCreateWidgetId());

        Assert.assertNotNull(addPageVersion);
        Assert.assertEquals("a", addPageVersion.getName());
        Assert.assertEquals("1a", addPageVersion.getTitle());
        Assert.assertEquals("url", addPageVersion.getDraftPageSettings().getUrl());// Saved url
        Assert.assertEquals(0, addPageVersion.getKeywordsGroups().size());
    }

    @Test
    public void executeWithMinusInUrl() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        final Page page = TestUtil.createPage(site);
        page.getPageSettings().setLayoutFile("b");

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "");

        SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLANK);
        request.setName("a");
        request.setTitle("1a");
        request.setUrl("-u-rlP-");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.setPageToEditId(page.getPageId());

        final int pageId = pageCreator.savePageNameTab(request, false).getPageId();
        final PageManager addPageVersion = new PageManager(persistance.getPage(pageId));

        Assert.assertNotNull(addPageVersion);
        Assert.assertEquals("a", addPageVersion.getName());
        Assert.assertEquals("1a", addPageVersion.getTitle());
        Assert.assertEquals("-u-rlp-", addPageVersion.getDraftPageSettings().getUrl());// Saved url
        Assert.assertEquals(0, addPageVersion.getKeywordsGroups().size());
    }

    @Test
    public void executeWithEmptyTitle() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        final Page page = TestUtil.createPage(site);
        page.getPageSettings().setLayoutFile("b");

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "");

        SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLANK);
        request.setName("a");
        request.setTitle("");
        request.setUrl("urlP");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.setPageToEditId(page.getPageId());

        final int pageId = pageCreator.savePageNameTab(request, false).getPageId();
        final PageManager addPageVersion = new PageManager(persistance.getPage(pageId));

        Assert.assertNotNull(addPageVersion);
        Assert.assertEquals("a", addPageVersion.getName());
        Assert.assertEquals("If title is empty, we must set in title page name!", "a", addPageVersion.getTitle());
        Assert.assertEquals("urlp", addPageVersion.getDraftPageSettings().getUrl());// Saved url
        Assert.assertEquals(0, addPageVersion.getKeywordsGroups().size());
    }

    @Test
    public void executeWithTitleMore110Symbols() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        final Page page = TestUtil.createPage(site);
        page.getPageSettings().setLayoutFile("b");

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "");

        SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLANK);
        request.setName("a");
        request.setTitle(TestUtil.createString(111));
        request.setUrl("urlP");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.setPageToEditId(page.getPageId());

        final int pageId = pageCreator.savePageNameTab(request, false).getPageId();
        final PageManager addPageVersion = new PageManager(persistance.getPage(pageId));

        Assert.assertNotNull(addPageVersion);
        Assert.assertEquals("a", addPageVersion.getName());
        Assert.assertEquals(110, addPageVersion.getTitle().length());
        Assert.assertEquals("urlp", addPageVersion.getDraftPageSettings().getUrl());// Saved url
        Assert.assertEquals(0, addPageVersion.getKeywordsGroups().size());
    }

    @Test
    public void executeWithNullTitleAndNameMore110Symbols() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        final Page page = TestUtil.createPage(site);
        page.getPageSettings().setLayoutFile("b");

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "");

        SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLANK);
        request.setName(TestUtil.createString(111));
        request.setTitle(null);
        request.setUrl("urlP");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.setPageToEditId(page.getPageId());

        final int pageId = pageCreator.savePageNameTab(request, false).getPageId();
        final PageManager addPageVersion = new PageManager(persistance.getPage(pageId));

        Assert.assertNotNull(addPageVersion);
//        Assert.assertEquals("a", addPageVersion.getName());
        Assert.assertEquals(110, addPageVersion.getTitle().length());
        Assert.assertEquals("urlp", addPageVersion.getDraftPageSettings().getUrl());// Saved url
        Assert.assertEquals(0, addPageVersion.getKeywordsGroups().size());
    }

    @Test
    public void executeWithNullTitle() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        final Page page = TestUtil.createPage(site);
        page.getPageSettings().setLayoutFile("b");

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "");

        SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLANK);
        request.setName("a");
        request.setTitle(null);
        request.setUrl("urlP");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.setPageToEditId(page.getPageId());

        final int pageId = pageCreator.savePageNameTab(request, false).getPageId();
        final PageManager addPageVersion = new PageManager(persistance.getPage(pageId));

        Assert.assertNotNull(addPageVersion);
        Assert.assertEquals("a", addPageVersion.getName());
        Assert.assertEquals("If title is empty, we must set in title page name!", "a", addPageVersion.getTitle());
        Assert.assertEquals("urlp", addPageVersion.getDraftPageSettings().getUrl());// Saved url
        Assert.assertEquals(0, addPageVersion.getKeywordsGroups().size());
    }

    @Ignore
    @Test // todo, Tolik, please fix this test after you will fix the checkbox.
    public void executeIncludeInMenu() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        final Page page = TestUtil.createPage(site);

        final DraftMenu menu = new DraftMenu();
        menu.setSiteId(site.getSiteId());
        persistance.putMenu(menu);

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "");

        SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLANK);
        request.setIncludeInMenu(true);
        request.setName("a");
        request.setTitle("1a");
        request.setUrl("urlP");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.setPageToEditId(page.getPageId());

        final int pageId = pageCreator.savePageNameTab(request, false).getPageId();

        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(pageId, menu.getMenuItems().get(0).getPageId().intValue());

        Assert.assertEquals(1, site.getMenu().getMenuItems().size());
        Assert.assertEquals(pageId, site.getMenu().getMenuItems().get(0).getPageId().intValue());
    }

    @Ignore
    @Test // todo, Tolik, please fix this test after you will fix the checkbox.
    public void executeIncludeInMenuDeep() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        final Page page = TestUtil.createPage(site);

        final DraftMenu menu = new DraftMenu();
        menu.setSiteId(site.getSiteId());
        menu.setMenuStructure(MenuStructureType.DEFAULT);
        final MenuItem element1 = TestUtil.createMenuItem(1, menu);
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        element1.setParent(null);
        element2.setParent(null);
        persistance.putMenu(menu);

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "");

        SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLANK);
        request.setIncludeInMenu(true);
        request.setName("a");
        request.setTitle("1a");
        request.setUrl("urlP");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.setPageToEditId(page.getPageId());

        pageCreator.savePageNameTab(request, false).getPageId();

         Assert.assertEquals(3, menu.getMenuItems().size());
    }

    @Ignore
    @Test // todo, Tolik, please fix this test after you will fix the checkbox.
    public void executeWithoutIncludeInMenu() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        final Page page = TestUtil.createPage(site);

        final DraftMenu menu = new DraftMenu();
        menu.setSiteId(site.getSiteId());
        menu.setMenuStructure(MenuStructureType.DEFAULT);
        persistance.putMenu(menu);

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "");

        SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLANK);
        request.setIncludeInMenu(false);
        request.setName("a");
        request.setTitle("1a");
        request.setUrl("urlP");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.setPageToEditId(page.getPageId());

        final int pageId = pageCreator.savePageNameTab(request, false).getPageId();

        Assert.assertEquals("Site menu is default, and it must contains all pages.", 1, site.getMenu().getMenuItems().size());
        Assert.assertEquals(pageId, site.getMenu().getMenuItems().get(0).getPageId().intValue());
        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(pageId, menu.getMenuItems().get(0).getPageId().intValue());
    }

    @Test
    public void executeWithAliaseUrl() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        final Page page = TestUtil.createPage(site);
        page.getPageSettings().setLayoutFile("b");

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "");

        SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLANK);
        request.setName("a");
        request.setTitle("1a");
        request.setAliaseUrl("f");
        request.setUrl("urlP");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.setPageToEditId(page.getPageId());

        final int pageId = pageCreator.savePageNameTab(request, false).getPageId();
        final PageManager addPageVersion = new PageManager(persistance.getPage(pageId));

        Assert.assertNotNull(addPageVersion);
        Assert.assertEquals("a", addPageVersion.getName());
        Assert.assertEquals("f", addPageVersion.getOwnDomainName());
        Assert.assertEquals("1a", addPageVersion.getTitle());
        Assert.assertEquals("urlp", addPageVersion.getDraftPageSettings().getUrl());// Saved url
    }

    @Test
    public void executeWithAliaseUrlWithEndingSlash() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        final Page page = TestUtil.createPage(site);
        page.getPageSettings().setLayoutFile("b");

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "");

        SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLANK);
        request.setName("a");
        request.setTitle("1a");
        request.setAliaseUrl("f/");
        request.setUrl("urlP");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.setPageToEditId(page.getPageId());

        final int pageId = pageCreator.savePageNameTab(request, false).getPageId();
        final PageManager addPageVersion = new PageManager(persistance.getPage(pageId));

        Assert.assertNotNull(addPageVersion);
        Assert.assertEquals("a", addPageVersion.getName());
        Assert.assertEquals("f", addPageVersion.getOwnDomainName());
        Assert.assertEquals("1a", addPageVersion.getTitle());
        Assert.assertEquals("urlp", addPageVersion.getDraftPageSettings().getUrl());// Saved url
    }

    @Test(expected = PageVersionAliaseUrlNotUniqueException.class)
    public void executeWithNotUniqueAliaseUrl() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        final Page page = TestUtil.createPage(site);

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "");

        Page notUniquePage = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(notUniquePage);
        pageVersion.setName("333333333333333");
        pageVersion.setUrl("ff  @# 23423");
        pageVersion.setOwnDomainName("f");

        SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLANK);
        request.setName("a");
        request.setTitle("1a");
        request.setAliaseUrl("f");
        request.setUrl("urlP");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.setPageToEditId(page.getPageId());

        pageCreator.savePageNameTab(request, false);
    }

    @Test(expected = PageVersionNameNotUniqueException.class)
    public void executeWithNotUniqueNameOnDraft() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        final Page page = TestUtil.createPage(site);

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "");

        Page notUnqiuePage = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(notUnqiuePage);
        pageVersion.setName("3");
        pageVersion.setUrl("f");

        SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLANK);
        request.setName("3");
        request.setTitle("1a");
        request.setAliaseUrl("f1");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.setPageToEditId(page.getPageId());

        pageCreator.savePageNameTab(request, false);
    }

    @Test(expected = PageVersionNameNotUniqueException.class)
    public void executeWithNotUniqueNameOnWork() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        final Page page = TestUtil.createPage(site);

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "");

        Page notUnqiuePage = TestUtil.createPage(site);

        TestUtil.createWorkPageSettings(notUnqiuePage.getPageSettings());
        PageManager pageVersion = new PageManager(notUnqiuePage);
        pageVersion.getWorkPageSettings().setName("3");
        pageVersion.getWorkPageSettings().setCreationDate(new Date(System.currentTimeMillis() * 2));
        pageVersion.getWorkPageSettings().setUrl("f");

        SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLANK);
        request.setName("3");
        request.setTitle("1a");
        request.setAliaseUrl("f1");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.setPageToEditId(page.getPageId());

        pageCreator.savePageNameTab(request, false);
    }

    @Test(expected = PageVersionAliaseUrlNotUniqueException.class)
    public void executeWithNotUniqueAliaseUrlInOtherCase() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        final Page page = TestUtil.createPage(site);

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "");

        Page notUnqiuePage = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(notUnqiuePage);
        pageVersion.setName("333333333333333");
        pageVersion.setUrl("ff  @# 23423");
        pageVersion.setOwnDomainName("f");

        SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLANK);
        request.setName("a");
        request.setTitle("1a");
        request.setAliaseUrl("F");
        request.setUrl("urlP");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.setPageToEditId(page.getPageId());

        pageCreator.savePageNameTab(request, false);
    }

    @Test
    public void executeWithoutPages() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        final Page page = TestUtil.createPage(site);
        page.getPageSettings().setLayoutFile("b");

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "");

        SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLANK);
        request.setName("a");
        request.setTitle("1a");
        request.setUrl("urlP");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.setPageToEditId(page.getPageId());

        final int pageId = pageCreator.savePageNameTab(request, false).getPageId();
        final PageManager addPageVersion = new PageManager(persistance.getPage(pageId));

        Assert.assertNotNull(addPageVersion);
        Assert.assertEquals("a", addPageVersion.getName());
        Assert.assertEquals("1a", addPageVersion.getTitle());
        Assert.assertEquals("urlp", addPageVersion.getDraftPageSettings().getUrl());// Saved url
    }

    @Test(expected = KeywordsGroupNotFoundException.class)
    public void executeWithNotFoundKeywordsGroup() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        final Page page = TestUtil.createPage(site);

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("a");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);

        SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLANK);
        request.setName("a");
        request.setTitle("1a");
        request.setUrl("urlP");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.getKeywordsGroupIds().add(1);
        request.setPageToEditId(page.getPageId());

        pageCreator.savePageNameTab(request, false);
    }

    @Test(expected = PageVersionUrlIncorrectException.class)
    public void executeWithNullUrl() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        final Page page = TestUtil.createPage(site);

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("a");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);

        Page notUnqiuePage = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(notUnqiuePage);
        pageVersion.setUrl("ff");
        pageVersion.setName("333333333333333");

        SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLANK);
        request.setName("a");
        request.setTitle("1a");
        request.setUrl(null);
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.setPageToEditId(page.getPageId());

        final int pageId = pageCreator.savePageNameTab(request, false).getPageId();
        PageManager addPageVersion = new PageManager(persistance.getPage(pageId));

        Assert.assertNull(addPageVersion);
    }

    @Test
    public void executeWithNullDescription() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "");

        Page page = TestUtil.createPage(site);
        page.getPageSettings().setLayoutFile("b");

        PageManager pageVersion = new PageManager(page);
        pageVersion.setUrl("ff");
        pageVersion.setName("333333333333333");        

        SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLANK);
        request.setName("a");
        request.setTitle("1a");
        request.setUrl("a");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.setPageToEditId(page.getPageId());

        final int pageId = pageCreator.savePageNameTab(request, false).getPageId();
        PageManager addPageVersion = new PageManager(persistance.getPage(pageId));

        Assert.assertNotNull(addPageVersion);
        Assert.assertEquals("a", addPageVersion.getName());
        Assert.assertEquals("1a", addPageVersion.getTitle());
        Assert.assertEquals(site, pageVersion.getPage().getSite());
        Assert.assertEquals("a", addPageVersion.getDraftPageSettings().getUrl());// Saved url
        Assert.assertEquals(0, addPageVersion.getKeywordsGroups().size());
    }

    @Test
    public void executeAsTwo() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "");

        KeywordsGroup keywordsGroup = TestUtil.createKeywordsGroup(site);

        Page page = TestUtil.createPage(site);
        page.getPageSettings().setLayoutFile("b");

        PageManager pageVersion = new PageManager(page);
        pageVersion.setUrl("ff");
        pageVersion.setName("b");
        

        SavePageRequest request = new SavePageRequest();
        request.setName("a");
        request.setPageType(PageType.BLANK);
        request.setTitle("1a");
        request.setUrl("some_url");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.getKeywordsGroupIds().add(keywordsGroup.getKeywordsGroupId());
        request.setPageToEditId(page.getPageId());

        final int pageId = pageCreator.savePageNameTab(request, false).getPageId();
        PageManager testPageVersion = new PageManager(persistance.getPage(pageId));

        Assert.assertNotNull(testPageVersion);
        Assert.assertEquals("a", testPageVersion.getName());
        Assert.assertEquals("1a", testPageVersion.getTitle());
        Assert.assertEquals(1, testPageVersion.getKeywordsGroups().size());
        Assert.assertEquals(keywordsGroup, testPageVersion.getKeywordsGroups().get(0));
    }

    @Test(expected = PageVersionNameIncorrectException.class)
    public void executeWithIncorrectName() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final Page page = TestUtil.createPage(site);

        SavePageRequest request = new SavePageRequest();
        request.setSiteId(site.getSiteId());
        request.setPageToEditId(page.getPageId());

        pageCreator.savePageNameTab(request, false);
    }

    @Test(expected = PageVersionNameIncorrectException.class)
    public void executeWithEmptyName() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final Page page = TestUtil.createPage(site);

        SavePageRequest request = new SavePageRequest();
        request.setName("");
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.setPageType(PageType.BLANK);
        request.setSiteId(site.getSiteId());
        request.setPageToEditId(page.getPageId());

        pageCreator.savePageNameTab(request, false);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);

        SavePageRequest request = new SavePageRequest();
        request.setSiteId(site.getSiteId());

        pageCreator.savePageNameTab(request, false);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void executeWithoutSEOSettings() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        site.setThemeId(new ThemeId("a", "b"));

        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "");

        KeywordsGroup keywordsGroup = TestUtil.createKeywordsGroup(site);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.setName("333333333333333");
        pageVersion.setUrl("ff  @# 23423");
        

        SavePageRequest request = new SavePageRequest();
        request.setPageType(PageType.BLANK);
        request.setName("a");
        request.setTitle("1a");
        request.setUrl("urlP");
        request.setSiteId(site.getSiteId());
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.getKeywordsGroupIds().add(keywordsGroup.getKeywordsGroupId());
        request.setPageSEOSettings(null);

        pageCreator.savePageNameTab(request, false);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PageCreator pageCreator = new PageCreator();

}
