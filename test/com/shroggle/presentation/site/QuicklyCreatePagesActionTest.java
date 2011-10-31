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
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.PagesWithoutSystem;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.filesystem.FileSystemMock;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.action.ActionBeanContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stasuk Artem
 */
@RunWith(TestRunnerWithMockServices.class)
public class QuicklyCreatePagesActionTest {

    @Before
    public void before() {
        action.setContext(new ActionBeanContext());
    }

    @Test
    public void show() {
        final Site site = createStandartSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        action.setSiteId(site.getSiteId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.view();

        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals("/site/quicklyCreatePages.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void showForBlueprintChild() {
        final Site blueprint = createStandartSite();
        final Site site = createStandartSite();
        blueprint.addBlueprintChild(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        action.setSiteId(site.getSiteId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.view();

        Assert.assertEquals(SiteEditPageAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals(site.getSiteId(), resolutionMock.getRedirectByActionParameters()[0].getValue());
        Assert.assertEquals(1, resolutionMock.getRedirectByActionParameters().length);
        Assert.assertEquals("siteId", resolutionMock.getRedirectByActionParameters()[0].getName());
    }

    @Test
    public void execute() {
        final Site site = createStandartSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        action.setPages(new ArrayList<PageType>());
        action.getPages().add(PageType.BLANK);
        action.setSiteId(site.getSiteId());

        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(SiteEditPageAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals(2, site.getPages().size());
        final Page page = site.getPages().get(1);
        PageManager pageVersion = new PageManager(page);
        Assert.assertEquals("Blank", pageVersion.getName());
        Assert.assertEquals("Blank", pageVersion.getTitle());
        Assert.assertEquals("Blank", pageVersion.getDraftPageSettings().getUrl());

        final DraftMenu menu = site.getMenu();
        Assert.assertNotNull(menu);
        Assert.assertNotNull(menu.getMenuItems());
        final List<MenuItem> items = menu.getMenuItems();
        Assert.assertEquals("New pages exclude system!", 1, items.size());
    }

    @Test
    public void executeForBlueprintChild() {
        final Site blueprint = TestUtil.createSite();
        final Site site = TestUtil.createSite();
        blueprint.addBlueprintChild(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        action.setPages(new ArrayList<PageType>());
        action.getPages().add(PageType.BLANK);
        action.setSiteId(site.getSiteId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(SiteEditPageAction.class, resolutionMock.getRedirectByAction());
        Assert.assertEquals(site.getSiteId(), resolutionMock.getRedirectByActionParameters()[0].getValue());
        Assert.assertEquals(1, resolutionMock.getRedirectByActionParameters().length);
        Assert.assertEquals("siteId", resolutionMock.getRedirectByActionParameters()[0].getName());
        Assert.assertEquals(1, site.getPages().size());
    }

    @Test
    public void showWithDeniedPage() {
        final Site site = createStandartSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        Page page = TestUtil.createPage(site);
        page.setType(PageType.LOGIN);

        action.setPages(new ArrayList<PageType>());
        action.setSiteId(site.getSiteId());
        action.view();

        Assert.assertEquals(0, action.getPages().size());
    }

    private Site createStandartSite() {
        final Site site = TestUtil.createSite();

        final DraftBlog blog = TestUtil.createBlog(site);
        blog.setName("BLOG2");

        site.setThemeId(new ThemeId("a", "test.html"));

        final FileSystemMock fileSystemMock = (FileSystemMock) fileSystem;
        final Template template = new Template();
        template.setDirectory("a");
        final Layout layout = new Layout();
        layout.setFile("test.html");
        layout.setTemplate(template);
        template.getLayouts().add(layout);
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "test.html", "<!-- MEDIA_BLOCK --><!-- MEDIA_BLOCK -->");
        return site;
    }

    @Test
    public void executeWithNoSetSite() {
        TestUtil.createUserAndLogin();

        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void showWithNoFoundSite() {
        TestUtil.createUserAndLogin();

        final ResolutionMock resolutionMock = (ResolutionMock) action.view();

        Assert.assertTrue(action.getContext().getValidationErrors().isEmpty());
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithoutDefault() {
        final Site site = TestUtil.createSite();
        site.setThemeId(new ThemeId("a", "test.html"));
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftBlog blog = new DraftBlog();
        blog.setName("BLOG2");
        persistance.putItem(blog);

        FileSystemMock fileSystemMock = (FileSystemMock) fileSystem;
        Template template = new Template();
        template.setDirectory("a");
        Layout layout = new Layout();
        layout.setFile("test.html");
        layout.setTemplate(template);
        template.getLayouts().add(layout);
        fileSystemMock.putTemplate(template);
        fileSystemMock.addTemplateResource("a", "test.html", "");

        action.setSiteId(site.getSiteId());
        action.setPages(new ArrayList<PageType>());
        action.getPages().add(PageType.BLANK);
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        final List<Page> pagesWithoutSystem = PagesWithoutSystem.get(site.getPages());
        Assert.assertEquals(1, pagesWithoutSystem.size());
        final Page page = pagesWithoutSystem.get(0);
        Assert.assertEquals(new PageManager(page).getName(), "Blank");
        Assert.assertEquals(new PageManager(page).getTitle(), "Blank");
        Assert.assertEquals("test.html", new PageManager(page).getLayoutFile());

        Assert.assertEquals(SiteEditPageAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithNoSetPages() {
        final Site site = createStandartSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        action.setSiteId(site.getSiteId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(1, site.getPages().size());
        Assert.assertEquals(SiteEditPageAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void executeWithNotMySite() {
        TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);

        action.setSiteId(site.getSiteId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(1, site.getPages().size());
        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    @Test
    public void showWithNotMySite() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR);
        TestUtil.createUserAndLogin();

        action.setSiteId(site.getSiteId());
        final ResolutionMock resolutionMock = (ResolutionMock) action.view();

        Assert.assertEquals(DashboardAction.class, resolutionMock.getRedirectByAction());
    }

    private final FileSystem fileSystem = ServiceLocator.getFileSystem();
    private final QuicklyCreatePagesAction action = new QuicklyCreatePagesAction();
    private final Persistance persistance = ServiceLocator.getPersistance();

}