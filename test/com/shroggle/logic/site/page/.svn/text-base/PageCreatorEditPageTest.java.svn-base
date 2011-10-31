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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.exception.PageVersionAliaseUrlNotUniqueException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.presentation.site.page.SavePageRequest;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemMock;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

/**
 * @author dmitry.solomadin
 */
@RunWith(TestRunnerWithMockServices.class)
public class PageCreatorEditPageTest {

    @Before
    public void before() {
        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        template.getLayouts().add(layout);
        layout.setFile("b");
        FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "b", "");
    }

    @Test
    public void execute() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        site.setThemeId(new ThemeId("a", "b"));
        pageVersion.setLayoutFile("b");

        KeywordsGroup keywordsGroup1 = TestUtil.createKeywordsGroup(site);

        KeywordsGroup keywordsGroup2 = TestUtil.createKeywordsGroup(site);

        KeywordsGroup keywordsGroup3 = TestUtil.createKeywordsGroup(site);

        pageVersion.setName("2222");
        pageVersion.setUrl("a");
        pageVersion.setLayoutFile("b");

        KeywordsGroup pageKeywordsGroup1 = TestUtil.createKeywordsGroup(site);

        KeywordsGroup pageKeywordsGroup2 = TestUtil.createKeywordsGroup(site);

        final SavePageRequest request = new SavePageRequest();
        request.setPageToEditId(pageVersion.getPageId());
        request.setName("a");
        request.setTitle("1a");
        request.setUrl("url");
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

        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.getKeywordsGroupIds().add(keywordsGroup1.getKeywordsGroupId());
        request.getKeywordsGroupIds().add(keywordsGroup3.getKeywordsGroupId());

        pageCreator.savePageNameTab(request, false);

        Assert.assertNotNull(pageVersion);
        Assert.assertEquals("a", pageVersion.getName());
        Assert.assertEquals("1a", pageVersion.getTitle());
        Assert.assertEquals(site, pageVersion.getPage().getSite());
        Assert.assertEquals("url", pageVersion.getDraftPageSettings().getUrl());
        Assert.assertNotNull(pageVersion.getSeoSettings());
        Assert.assertTrue("Edit page service not set for this page version flag is changed!", pageVersion.isChanged());
        Assert.assertEquals(2, pageVersion.getKeywordsGroups().size());
        Assert.assertEquals(keywordsGroup3, pageVersion.getKeywordsGroups().get(1));
        Assert.assertEquals(keywordsGroup1, pageVersion.getKeywordsGroups().get(0));
    }

    @Test
    public void executeWithNullTitle() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        site.setThemeId(new ThemeId("a", "b"));
        pageVersion.setLayoutFile("b");

        pageVersion.setName("2222");
        pageVersion.setUrl("a");
        pageVersion.setLayoutFile("b");

        final SavePageRequest request = new SavePageRequest();
        request.setPageToEditId(pageVersion.getPageId());
        request.setName("a");
        request.setTitle(null);
        request.setUrl("url");
        request.setKeywordsGroupIds(new HashSet<Integer>());

        pageCreator.savePageNameTab(request, false);

        Assert.assertNotNull(pageVersion);
        Assert.assertEquals("a", pageVersion.getName());
        Assert.assertEquals("a", pageVersion.getTitle());
        Assert.assertEquals(site, pageVersion.getPage().getSite());
        Assert.assertEquals("url", pageVersion.getDraftPageSettings().getUrl());
        Assert.assertTrue("Edit page service not set for this page version flag is changed!", pageVersion.isChanged());
        Assert.assertEquals(0, pageVersion.getKeywordsGroups().size());
    }

    @Test
    public void executeWithPlusInUrl() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        site.setThemeId(new ThemeId("a", "b"));
        pageVersion.setLayoutFile("b");

        pageVersion.setName("2222");
        pageVersion.setUrl("a");
        pageVersion.setLayoutFile("b");

        final SavePageRequest request = new SavePageRequest();
        request.setPageToEditId(pageVersion.getPageId());
        request.setName("a");
        request.setTitle(null);
        request.setUrl("+u+rl+");
        request.setKeywordsGroupIds(new HashSet<Integer>());

        pageCreator.savePageNameTab(request, false);

        Assert.assertNotNull(pageVersion);
        Assert.assertEquals("a", pageVersion.getName());
        Assert.assertEquals("a", pageVersion.getTitle());
        Assert.assertEquals(site, pageVersion.getPage().getSite());
        Assert.assertEquals("+u+rl+", pageVersion.getDraftPageSettings().getUrl());
        Assert.assertTrue("Edit page service not set for this page version flag is changed!", pageVersion.isChanged());
        Assert.assertEquals(0, pageVersion.getKeywordsGroups().size());
    }

    @Test
    public void executeWithMinusInUrl() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        site.setThemeId(new ThemeId("a", "b"));
        pageVersion.setLayoutFile("b");

        pageVersion.setName("2222");
        pageVersion.setUrl("a");
        pageVersion.setLayoutFile("b");

        final SavePageRequest request = new SavePageRequest();
        request.setPageToEditId(pageVersion.getPageId());
        request.setName("a");
        request.setTitle(null);
        request.setUrl("-u-rl-");
        request.setKeywordsGroupIds(new HashSet<Integer>());

        pageCreator.savePageNameTab(request, false);

        Assert.assertNotNull(pageVersion);
        Assert.assertEquals("a", pageVersion.getName());
        Assert.assertEquals("a", pageVersion.getTitle());
        Assert.assertEquals(site, pageVersion.getPage().getSite());
        Assert.assertEquals("-u-rl-", pageVersion.getDraftPageSettings().getUrl());
        Assert.assertTrue("Edit page service not set for this page version flag is changed!", pageVersion.isChanged());
        Assert.assertEquals(0, pageVersion.getKeywordsGroups().size());
    }

    @Test
    public void executeWithEmptyTitle() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        site.setThemeId(new ThemeId("a", "b"));
        pageVersion.setLayoutFile("b");

        pageVersion.setName("2222");
        pageVersion.setUrl("a");
        pageVersion.setLayoutFile("b");

        final SavePageRequest request = new SavePageRequest();
        request.setPageToEditId(pageVersion.getPageId());
        request.setName("a");
        request.setTitle("");
        request.setUrl("url");
        request.setKeywordsGroupIds(new HashSet<Integer>());

        pageCreator.savePageNameTab(request, false);

        Assert.assertNotNull(pageVersion);
        Assert.assertEquals("a", pageVersion.getName());
        Assert.assertEquals("a", pageVersion.getTitle());
        Assert.assertEquals(site, pageVersion.getPage().getSite());
        Assert.assertEquals("url", pageVersion.getDraftPageSettings().getUrl());
        Assert.assertTrue("Edit page service not set for this page version flag is changed!", pageVersion.isChanged());
        Assert.assertEquals(0, pageVersion.getKeywordsGroups().size());
    }

    @Test
    public void executeWithTitleMore110() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        site.setThemeId(new ThemeId("a", "b"));
        pageVersion.setLayoutFile("b");

        pageVersion.setName("2222");
        pageVersion.setUrl("a");
        pageVersion.setLayoutFile("b");

        final SavePageRequest request = new SavePageRequest();
        request.setPageToEditId(pageVersion.getPageId());
        request.setName("a");
        request.setTitle(TestUtil.createString(111));
        request.setUrl("url");
        request.setKeywordsGroupIds(new HashSet<Integer>());

        pageCreator.savePageNameTab(request, false);

        Assert.assertNotNull(pageVersion);
        Assert.assertEquals("a", pageVersion.getName());
        Assert.assertEquals(110, pageVersion.getTitle().length());
        Assert.assertEquals(site, pageVersion.getPage().getSite());
        Assert.assertEquals("url", pageVersion.getDraftPageSettings().getUrl());
        Assert.assertTrue("Edit page service not set for this page version flag is changed!", pageVersion.isChanged());
        Assert.assertEquals(0, pageVersion.getKeywordsGroups().size());
    }

    @Test
    public void executeWithEmptyTitleAndNameMore110() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        site.setThemeId(new ThemeId("a", "b"));
        pageVersion.setLayoutFile("b");

        pageVersion.setName("2222");
        pageVersion.setUrl("a");
        pageVersion.setLayoutFile("b");

        final SavePageRequest request = new SavePageRequest();
        request.setPageToEditId(pageVersion.getPageId());
        request.setName(TestUtil.createString(111));
        request.setUrl("url");
        request.setKeywordsGroupIds(new HashSet<Integer>());

        pageCreator.savePageNameTab(request, false);

        Assert.assertNotNull(pageVersion);
        Assert.assertEquals(110, pageVersion.getTitle().length());
        Assert.assertEquals(site, pageVersion.getPage().getSite());
        Assert.assertEquals("url", pageVersion.getDraftPageSettings().getUrl());
        Assert.assertTrue("Edit page service not set for this page version flag is changed!", pageVersion.isChanged());
        Assert.assertEquals(0, pageVersion.getKeywordsGroups().size());
    }

    @Test
    public void executeWithAliaseUrl() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        site.setThemeId(new ThemeId("a", "b"));
        pageVersion.setLayoutFile("b");

        pageVersion.setName("2222");
        pageVersion.setUrl("a");
        pageVersion.setLayoutFile("b");

        final SavePageRequest request = new SavePageRequest();
        request.setPageToEditId(pageVersion.getPageId());
        request.setName("a");
        request.setTitle("1a");
        request.setAliaseUrl("eeee");
        request.setUrl("url");
        request.setKeywordsGroupIds(new HashSet<Integer>());

        pageCreator.savePageNameTab(request, false);

        Assert.assertEquals("eeee", pageVersion.getOwnDomainName());
    }

    @Test
    public void executeWithAliaseUrlInUpCase() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        site.setThemeId(new ThemeId("a", "b"));
        pageVersion.setLayoutFile("b");

        pageVersion.setName("2222");
        pageVersion.setUrl("a");
        pageVersion.setLayoutFile("b");

        final SavePageRequest request = new SavePageRequest();
        request.setPageToEditId(pageVersion.getPageId());
        request.setName("a");
        request.setTitle("1a");
        request.setAliaseUrl("eeEE");
        request.setUrl("url");
        request.setKeywordsGroupIds(new HashSet<Integer>());

        pageCreator.savePageNameTab(request, false);

        Assert.assertEquals("eeee", pageVersion.getOwnDomainName());
    }

    @Test
    public void executeWithAliaseUrlWithEndingSlash() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        site.setThemeId(new ThemeId("a", "b"));
        pageVersion.setLayoutFile("b");

        pageVersion.setName("2222");
        pageVersion.setUrl("a");
        pageVersion.setLayoutFile("b");

        final SavePageRequest request = new SavePageRequest();
        request.setPageToEditId(pageVersion.getPageId());
        request.setName("a");
        request.setTitle("1a");
        request.setAliaseUrl("eeee/");
        request.setUrl("url");
        request.setKeywordsGroupIds(new HashSet<Integer>());

        pageCreator.savePageNameTab(request, false);

        Assert.assertEquals("eeee", pageVersion.getOwnDomainName());
    }

    @Test(expected = PageVersionAliaseUrlNotUniqueException.class)
    public void executeWithNotUnqiueAliaseUrl() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createPageVersionAndPage(site).setOwnDomainName("eeee");

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setName("2222");
        pageVersion.setUrl("a");
        pageVersion.setLayoutFile("b");

        final SavePageRequest request = new SavePageRequest();
        request.setPageToEditId(pageVersion.getPageId());
        request.setName("a");
        request.setTitle("1a");
        request.setAliaseUrl("eeee");
        request.setUrl("url");
        request.setKeywordsGroupIds(new HashSet<Integer>());

        pageCreator.savePageNameTab(request, false);

        Assert.assertEquals("eeee", pageVersion.getOwnDomainName());
    }

    @Test(expected = PageVersionAliaseUrlNotUniqueException.class)
    public void executeWithNotUnqiueAliaseUrlInOtherCase() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createPageVersionAndPage(site).setOwnDomainName("eeee");

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        pageVersion.setName("2222");
        pageVersion.setUrl("a");
        pageVersion.setLayoutFile("b");

        final SavePageRequest request = new SavePageRequest();
        request.setPageToEditId(pageVersion.getPageId());
        request.setName("a");
        request.setTitle("1a");
        request.setAliaseUrl("EEEe");
        request.setUrl("url");
        request.setKeywordsGroupIds(new HashSet<Integer>());

        pageCreator.savePageNameTab(request, false);

        Assert.assertEquals("eeee", pageVersion.getOwnDomainName());
    }

    @Test
    public void executeWithNotUnqiueAliaseUrlInSamePage() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final Page page = TestUtil.createPage(site);
        TestUtil.createPageVersion(page).setOwnDomainName("eeee");

        final PageManager pageVersion = new PageManager(page);
        pageVersion.setName("2222");
        pageVersion.setUrl("a");
        pageVersion.setLayoutFile("b");

        site.setThemeId(new ThemeId("a", "b"));

        final SavePageRequest request = new SavePageRequest();
        request.setPageToEditId(pageVersion.getPageId());
        request.setName("a");
        request.setTitle("1a");
        request.setAliaseUrl("eeee");
        request.setUrl("url");
        request.setKeywordsGroupIds(new HashSet<Integer>());

        pageCreator.savePageNameTab(request, false);

        Assert.assertEquals("eeee", pageVersion.getOwnDomainName());
    }

    @Test
    public void executeWithNotChangeUrl() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.setName("2222");
        pageVersion.setUrl("a");
        pageVersion.setLayoutFile("b");

        site.setThemeId(new ThemeId("a", "b"));
        pageVersion.setLayoutFile("b");

        final SavePageRequest request = new SavePageRequest();
        request.setPageToEditId(pageVersion.getPageId());
        request.setName("a");
        request.setTitle("1a");
        request.setUrl("a");
        request.setKeywordsGroupIds(new HashSet<Integer>());

        pageCreator.savePageNameTab(request, false);

        Assert.assertNotNull(pageVersion);
        Assert.assertEquals("a", pageVersion.getName());
        Assert.assertEquals("1a", pageVersion.getTitle());
        Assert.assertEquals(site, pageVersion.getPage().getSite());
        Assert.assertEquals("a", pageVersion.getDraftPageSettings().getUrl());
    }

    @Test
    public void executeWithEmptyMetaTagName() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.setName("2222");
        pageVersion.setUrl("a");

        site.setThemeId(new ThemeId("a", "b"));
        pageVersion.setLayoutFile("b");

        final SavePageRequest request = new SavePageRequest();
        request.setPageToEditId(pageVersion.getPageId());
        request.setName("a");
        request.setTitle("1a");
        request.setUrl("url");
        request.setKeywordsGroupIds(new HashSet<Integer>());

        pageCreator.savePageNameTab(request, false);

        Assert.assertNotNull(pageVersion);
        Assert.assertEquals("a", pageVersion.getName());
        Assert.assertEquals("1a", pageVersion.getTitle());
        Assert.assertEquals("url", pageVersion.getDraftPageSettings().getUrl());
        Assert.assertEquals(site, pageVersion.getPage().getSite());
        Assert.assertEquals(0, pageVersion.getKeywordsGroups().size());
    }

    @Test
    public void executeWithSpaceInMetaTagName() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.setName("2222");
        pageVersion.setUrl("a");

        site.setThemeId(new ThemeId("a", "b"));
        pageVersion.setLayoutFile("b");

        SavePageRequest request = new SavePageRequest();
        request.setPageToEditId(pageVersion.getPageId());
        request.setName("a");
        request.setTitle("1a");
        request.setUrl("url");
        request.setKeywordsGroupIds(new HashSet<Integer>());

        pageCreator.savePageNameTab(request, false);

        Assert.assertNotNull(pageVersion);
        Assert.assertEquals("a", pageVersion.getName());
        Assert.assertEquals("1a", pageVersion.getTitle());
        Assert.assertEquals(site, pageVersion.getPage().getSite());
        Assert.assertEquals(0, pageVersion.getKeywordsGroups().size());
    }

    @Test
    public void executeWithNotUniqueNewMetaTagName() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.setName("2222");
        pageVersion.setUrl("a");

        site.setThemeId(new ThemeId("a", "b"));
        pageVersion.setLayoutFile("b");

        SavePageRequest request = new SavePageRequest();
        request.setPageToEditId(pageVersion.getPageId());
        request.setName("a");
        request.setTitle("1a");
        request.setUrl("url");
        request.setKeywordsGroupIds(new HashSet<Integer>());

        pageCreator.savePageNameTab(request, false);
    }

    @Test
    public void executeWithEqualsName() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);

        site.setThemeId(new ThemeId("a", "b"));
        page.getPageSettings().setLayoutFile("b");

        KeywordsGroup keywordsGroup = TestUtil.createKeywordsGroup(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.setName("a");
        pageVersion.setUrl("a");
        pageVersion.setTitle("abb");

        final SavePageRequest request = new SavePageRequest();
        request.setPageToEditId(pageVersion.getPageId());
        request.setName("a");
        request.setTitle("1a");
        request.setUrl("url");
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.getKeywordsGroupIds().add(keywordsGroup.getKeywordsGroupId());

        pageCreator.savePageNameTab(request, false);

        Assert.assertNotNull(pageVersion);
        Assert.assertEquals("a", pageVersion.getName());
        Assert.assertEquals("1a", pageVersion.getTitle());
        Assert.assertEquals(site, pageVersion.getPage().getSite());
        Assert.assertEquals(1, pageVersion.getKeywordsGroups().size());
        Assert.assertEquals(keywordsGroup, pageVersion.getKeywordsGroups().get(0));
    }

    @Test
    public void executeWithEqualsInOtherCaseName() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);

        site.setThemeId(new ThemeId("a", "b"));
        page.getPageSettings().setLayoutFile("b");

        KeywordsGroup keywordsGroup = TestUtil.createKeywordsGroup(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.setName("a");
        pageVersion.setUrl("a");
        pageVersion.setTitle("abb");
        WorkPageSettings workPageVersion = TestUtil.createWorkPageSettings(pageVersion.getDraftPageSettings());

        workPageVersion.setName("A");
        workPageVersion.setUrl("a");
        workPageVersion.setTitle("abb");
        workPageVersion.setCreationDate(new Date(System.currentTimeMillis() * 2));

        final SavePageRequest request = new SavePageRequest();
        request.setPageToEditId(pageVersion.getPageId());
        request.setName("A");
        request.setTitle("1a");
        request.setUrl("url");
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.getKeywordsGroupIds().add(keywordsGroup.getKeywordsGroupId());

        pageCreator.savePageNameTab(request, false);

        Assert.assertNotNull(pageVersion);
        Assert.assertEquals("A", pageVersion.getName());
        Assert.assertEquals("1a", pageVersion.getTitle());
        Assert.assertEquals(site, pageVersion.getPage().getSite());
        Assert.assertEquals(1, pageVersion.getKeywordsGroups().size());
        Assert.assertEquals(keywordsGroup, pageVersion.getKeywordsGroups().get(0));
    }

    @Test(expected = PageNotFoundException.class)
    public void executeWithNotFoundPage() {
        TestUtil.createUserAndLogin();

        final SavePageRequest request = new SavePageRequest();
        request.setPageToEditId(-1);
        request.setName("a");
        request.setTitle("1a");
        request.setKeywordsGroupIds(new HashSet<Integer>());

        pageCreator.savePageNameTab(request, false);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() {
        pageCreator.savePageNameTab(new SavePageRequest(), false);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PageCreator pageCreator = new PageCreator();

}
