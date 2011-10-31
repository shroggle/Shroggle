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
import com.shroggle.entity.Page;
import com.shroggle.entity.PageVersionType;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stasuk Artem
 */
@RunWith(TestRunnerWithMockServices.class)
public class CopyPageVersionServiceTest {

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);
        final Page page = TestUtil.createPage(site);

        service.execute(page.getPageId(), PageVersionType.DRAFT, null);
    }

    @Test(expected = PageNotFoundException.class)
    public void executeWithNotMy() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndLogin();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);
        final Page page = TestUtil.createPage(site);

        service.execute(page.getPageId(), PageVersionType.DRAFT, null);
    }

    @Test(expected = PageNotFoundException.class)
    public void executeWithNotFoundPageVersion() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        service.execute(-1, PageVersionType.DRAFT, null);
    }

    /*@Test
    public void executeNeedDraft() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        TestUtil.createWorkPageSettings(pageVersion.getDraftPageSettings());

        WidgetComposit widgetComposit = new WidgetComposit();
        widgetComposit.setPosition(0);
        pageVersion.addWidget(widgetComposit);
        persistance.putWidget(widgetComposit);

        WidgetItem widgetBlog = TestUtil.createWidgetItem();
        widgetComposit.addChild(widgetBlog);
        pageVersion.addWidget(widgetBlog);
        persistance.putWidget(widgetBlog);

        WidgetItem widgetForum = TestUtil.createWidgetItem();
        widgetComposit.addChild(widgetForum);
        pageVersion.addWidget(widgetForum);
        persistance.putWidget(widgetForum);

        WidgetItem widgetText = TestUtil.createWidgetItem();
        widgetComposit.addChild(widgetText);
        pageVersion.addWidget(widgetText);
        persistance.putWidget(widgetText);

        final WidgetItem widgetImage = TestUtil.createWidgetItem();
        final DraftImage image1 = TestUtil.createDraftImage(site);
        widgetImage.setDraftItem(image1);
        widgetComposit.addChild(widgetImage);
        pageVersion.addWidget(widgetImage);
        persistance.putWidget(widgetImage);

        final DraftVideo video1 = new DraftVideo();
        persistance.putItem(video1);

        WidgetItem widgetVideo = TestUtil.createWidgetItem();
        widgetVideo.setDraftItem(video1);
        widgetComposit.addChild(widgetImage);
        pageVersion.addWidget(widgetVideo);
        persistance.putWidget(widgetVideo);

        WidgetItem widgetBlogSummary = TestUtil.createWidgetItem();
        widgetComposit.addChild(widgetBlogSummary);
        pageVersion.addWidget(widgetBlogSummary);
        persistance.putWidget(widgetBlogSummary);

        WidgetItem widgetMenu = TestUtil.createWidgetItem();
        widgetComposit.addChild(widgetMenu);
        pageVersion.addWidget(widgetMenu);
        persistance.putWidget(widgetMenu);

        WidgetItem widgetRegistration = TestUtil.createWidgetItem();
        widgetComposit.addChild(widgetRegistration);
        pageVersion.addWidget(widgetRegistration);
        persistance.putWidget(widgetRegistration);

        KeywordsGroup keywordsGroup = TestUtil.createKeywordsGroup(site);
        keywordsGroup.setName("key");
        keywordsGroup.setValue("11");

        KeywordsGroup pageKeywordsGroup = TestUtil.createKeywordsGroup(site);
        pageVersion.addKeywordsGroup(pageKeywordsGroup);


        final int copyPageVersionId = service.execute(page.getPageId(), PageVersionType.DRAFT, null);
        final PageManager copyPageVersion = persistance.getPageVersionById(copyPageVersionId);

        Assert.assertNotSame(pageVersion, copyPageVersion);
        Assert.assertEquals(PageVersionType.DRAFT, copyPageVersion.getItemType());
        Assert.assertNull("For draft page version theme id always must be null!", copyPageVersion.getThemeId());
        Assert.assertEquals(2, page.getVersions().size());
    }

    @Test
    public void executeNeedDraftWithExistDraft() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        TestUtil.createWorkPageSettings(pageVersion.getDraftPageSettings());


        final int copyPageVersionId = service.execute(page.getPageId(), PageVersionType.DRAFT, null);
        final PageManager copyPageVersion = persistance.getPageVersionById(copyPageVersionId);

        Assert.assertNotSame(pageVersion, copyPageVersion);
        Assert.assertEquals(PageVersionType.DRAFT, copyPageVersion.getItemType());
        Assert.assertNull("For draft page version theme id always must be null!", copyPageVersion.getThemeId());
        Assert.assertEquals(2, page.getVersions().size());
        Assert.assertEquals(workPageVersion, page.getVersions().get(0));
        Assert.assertEquals(copyPageVersion, page.getVersions().get(1));
    }

    @Test
    public void executeWithParentCrossWidgetId() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);
        PageManager pageVersion = new PageManager(page);
        TestUtil.createWorkPageSettings(pageVersion.getDraftPageSettings());

        WidgetComposit widgetComposit = new WidgetComposit();
        widgetComposit.setParentCrossWidgetId(23);
        widgetComposit.setPosition(0);
        pageVersion.addWidget(widgetComposit);
        persistance.putWidget(widgetComposit);

        final int copyPageVersionId = service.execute(page.getPageId(), PageVersionType.DRAFT, null);
        final PageManager copyPageVersion = persistance.getPageVersionById(copyPageVersionId);

        Assert.assertNotSame(pageVersion, copyPageVersion);
        Assert.assertEquals(1, copyPageVersion.getWidgetsWithItemSize().size());
        final Widget widget = copyPageVersion.getWidgetsWithItemSize().get(0);
        TestUtil.assertIntAndBigInt(23, widget.getParentCrossWidgetId());
        Assert.assertEquals(PageVersionType.DRAFT, copyPageVersion.getItemType());
        Assert.assertNull("For draft page version theme id always must be null!", copyPageVersion.getThemeId());
        Assert.assertEquals(2, page.getVersions().size());
    }

    @Test
    public void execute() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        TestUtil.createWorkPageSettings(pageVersion.getDraftPageSettings());

        WidgetComposit widgetComposit = new WidgetComposit();
        widgetComposit.setPosition(0);
        pageVersion.addWidget(widgetComposit);
        persistance.putWidget(widgetComposit);

        final int copyPageVersionId = service.execute(page.getPageId(), PageVersionType.DRAFT, null);
        final PageManager copyPageVersion = persistance.getPageVersionById(copyPageVersionId);

        Assert.assertNotSame(pageVersion, copyPageVersion);
        Assert.assertEquals(1, copyPageVersion.getWidgetsWithItemSize().size());
        final Widget widget = copyPageVersion.getWidgetsWithItemSize().get(0);
        Assert.assertNull(widget.getParentCrossWidgetId());
        Assert.assertEquals(PageVersionType.DRAFT, copyPageVersion.getItemType());
        Assert.assertNull("For draft page version theme id always must be null!", copyPageVersion.getThemeId());
        Assert.assertEquals(2, page.getVersions().size());
    }

    @Test
    public void executeWidgetShowRight() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);
        TestUtil.createWorkPageSettings(pageVersion.getDraftPageSettings());

        WidgetItem widgetItem = TestUtil.createWidgetItem();
        widgetItem.setPosition(0);
        widgetItem.getAccessibleSettings().setAccess(AccessForRender.RESTRICTED);
        widgetItem.getAccessibleSettings().setAdministrators(true);
        widgetItem.getAccessibleSettings().setVisitorsGroups(Arrays.asList(1, 2));
        pageVersion.addWidget(widgetItem);
        persistance.putWidget(widgetItem);

        final int copyPageVersionId = service.execute(pageVersion.getPage().getPageId(), PageVersionType.DRAFT, null);
        final PageManager copyPageVersion = persistance.getPageVersionById(copyPageVersionId);

        Assert.assertNotSame(pageVersion, copyPageVersion);
        Assert.assertEquals(
                "Must copy widget show right!",
                AccessForRender.RESTRICTED, ((WidgetItem) copyPageVersion.getWidgetsWithItemSize().get(0)).getAccessibleSettings().getAccess());
        Assert.assertEquals(
                "Must copy widget show right!",
                true, ((WidgetItem) copyPageVersion.getWidgetsWithItemSize().get(0)).getAccessibleSettings().isAdministrators());
        Assert.assertEquals(
                "Must copy widget show right!",
                2, ((WidgetItem) copyPageVersion.getWidgetsWithItemSize().get(0)).getAccessibleSettings().getVisitorsGroups().size());
        Assert.assertEquals(PageVersionType.DRAFT, copyPageVersion.getItemType());
    }

    @Test
    public void executeWithHtmlAndThemeCss() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        TestUtil.createWorkPageSettings(pageVersion.getDraftPageSettings());
        pageVersion.setHtml("aaa");
        pageVersion.setCss("fff");

        final int copyPageVersionId = service.execute(page.getPageId(), PageVersionType.DRAFT, null);
        final PageManager copyPageVersion = persistance.getPageVersionById(copyPageVersionId);

        Assert.assertEquals(pageVersion.getHtml(), copyPageVersion.getHtml());
        Assert.assertEquals(pageVersion.getCss(), copyPageVersion.getCss());
    }

    @Test
    public void executeNeedPreview() throws Exception {
        final Site site = TestUtil.createSite();
        site.setThemeId(new ThemeId("a", "b"));
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);

        final int copyPageVersionId = service.execute(page.getPageId(), PageVersionType.PREVIEW, null);
        final PageManager copyPageVersion = persistance.getPageVersionById(copyPageVersionId);

        Assert.assertNotSame(pageVersion, copyPageVersion);
        Assert.assertTrue(pageVersion.isChanged());
        Assert.assertEquals(PageVersionType.PREVIEW, copyPageVersion.getItemType());
        Assert.assertEquals(site.getThemeId(), copyPageVersion.getThemeId());
        Assert.assertEquals(2, page.getVersions().size());
    }

    @Test
    public void executeNeedWork() throws Exception {
        final Site site = TestUtil.createSite();
        site.getThemeId().setTemplateDirectory("A");
        site.getThemeId().setThemeCss("F");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.getAccessibleSettings().setAccess(AccessForRender.RESTRICTED);
        pageVersion.getAccessibleSettings().setVisitorsGroups(Arrays.asList(1, 2));
        pageVersion.getAccessibleSettings().setVisitors(true);
        pageVersion.getAccessibleSettings().setAdministrators(false);
        pageVersion.setKeywords("g");

        final int copyPageVersionId = service.execute(page.getPageId(), PageVersionType.WORK, null);
        final PageManager copyPageVersion = persistance.getPageVersionById(copyPageVersionId);

        Assert.assertNotSame(pageVersion, copyPageVersion);
        Assert.assertEquals("Must copy show right!", AccessForRender.RESTRICTED, copyPageVersion.getAccessibleSettings().getAccess());
        Assert.assertEquals("Must copy show right!", 2, copyPageVersion.getAccessibleSettings().getVisitorsGroups().size());
        Assert.assertEquals("Must copy show right!", true, copyPageVersion.getAccessibleSettings().isVisitors());
        Assert.assertEquals("Must copy show right!", false, copyPageVersion.getAccessibleSettings().isAdministrators());
        Assert.assertEquals(PageVersionType.WORK, copyPageVersion.getItemType());
        Assert.assertFalse(pageVersion.isChanged());
        Assert.assertEquals(site.getThemeId(), copyPageVersion.getThemeId());
        Assert.assertEquals("g", copyPageVersion.getKeywords());
        Assert.assertEquals(2, page.getVersions().size());
    }

    @Test
    public void executeNeedWorkWithSetThemeId() throws Exception {
        final Site site = TestUtil.createSite();
        site.getThemeId().setTemplateDirectory("A");
        site.getThemeId().setThemeCss("F");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.setOwnDomainName("ggg");
        pageVersion.setThemeId(new ThemeId("F", "a"));
        pageVersion.getAccessibleSettings().setAccess(AccessForRender.UNLIMITED);

        Page page2 = TestUtil.createPage(site);

        PageManager pageVersion2 = new PageManager(page);
        pageVersion2.getAccessibleSettings().setAccess(AccessForRender.UNLIMITED);
        pageVersion2.setKeywords("g");

        Assert.assertEquals(1, page.getVersions().size());
        Assert.assertEquals(1, page2.getVersions().size());

        final int copyPageVersionId = service.execute(page.getPageId(), PageVersionType.WORK, null);
        Assert.assertEquals(2, page.getVersions().size());
        Assert.assertEquals(1, page2.getVersions().size());
        final PageManager copyPageVersion = persistance.getPageVersionById(copyPageVersionId);

        Assert.assertNotSame(pageVersion, copyPageVersion);
        Assert.assertEquals("Must copy show right!", AccessForRender.UNLIMITED, copyPageVersion.getAccessibleSettings().getAccess());
        Assert.assertEquals(PageVersionType.WORK, copyPageVersion.getItemType());
        Assert.assertEquals(new ThemeId("F", "a"), copyPageVersion.getThemeId());
        Assert.assertEquals("ggg", copyPageVersion.getOwnDomainName());
        Assert.assertFalse(pageVersion.isChanged());
        Assert.assertEquals(2, page.getVersions().size());
    }

    @Test
    public void executeNeedWork_withSiteId() throws Exception {
        final Site site = TestUtil.createSite();
        site.getThemeId().setTemplateDirectory("A");
        site.getThemeId().setThemeCss("F");
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);

        PageManager pageVersion = new PageManager(page);
        pageVersion.getAccessibleSettings().setAccess(AccessForRender.UNLIMITED);
        pageVersion.setKeywords("g");

        Page page2 = TestUtil.createPage(site);

        PageManager pageVersion2 = new PageManager(page);
        pageVersion2.getAccessibleSettings().setAccess(AccessForRender.UNLIMITED);
        pageVersion2.setKeywords("g");

        Assert.assertEquals(1, page.getVersions().size());
        Assert.assertEquals(1, page2.getVersions().size());

        service.execute(page.getPageId(), PageVersionType.WORK, site.getSiteId());
        Assert.assertEquals(2, page.getVersions().size());
        Assert.assertEquals(2, page2.getVersions().size());

        final PageManager copyPageVersionForPage1 = page.getVersions().get(1);
        Assert.assertNotSame(pageVersion, copyPageVersionForPage1);
        Assert.assertEquals("Must copy show right!", AccessForRender.UNLIMITED, copyPageVersionForPage1.getAccessibleSettings().getAccess());
        Assert.assertEquals("Must copy show right!", false, copyPageVersionForPage1.getAccessibleSettings().isVisitors());
        Assert.assertEquals("Must copy show right!", false, copyPageVersionForPage1.getAccessibleSettings().isAdministrators());
        Assert.assertEquals(PageVersionType.WORK, copyPageVersionForPage1.getItemType());
        Assert.assertFalse(pageVersion.isChanged());
        Assert.assertEquals(site.getThemeId(), copyPageVersionForPage1.getThemeId());
        Assert.assertEquals("g", copyPageVersionForPage1.getKeywords());
        Assert.assertEquals(2, page.getVersions().size());

        final PageManager copyPageVersionForPage2 = page2.getVersions().get(1);
        Assert.assertNotSame(pageVersion, copyPageVersionForPage2);
        Assert.assertEquals("Must copy show right!", AccessForRender.UNLIMITED, copyPageVersionForPage2.getAccessibleSettings().getAccess());
        Assert.assertEquals(PageVersionType.WORK, copyPageVersionForPage2.getItemType());
        Assert.assertFalse(pageVersion.isChanged());
        Assert.assertEquals(site.getThemeId(), copyPageVersionForPage2.getThemeId());
        Assert.assertEquals("g", copyPageVersionForPage2.getKeywords());
        Assert.assertEquals(2, page2.getVersions().size());
    }

    @Test(expected = PageNotFoundException.class)
    public void executeForPageWithoutVersion() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);

        service.execute(page.getPageId(), PageVersionType.WORK, null);
    }*/

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final CopyPageVersionService service = new CopyPageVersionService();

}