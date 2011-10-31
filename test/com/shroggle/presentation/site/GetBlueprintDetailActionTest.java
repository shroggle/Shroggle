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

import com.shroggle.PersistanceMock;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemMock;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class GetBlueprintDetailActionTest {

    @Test
    public void executeWithoutBlueprint() {
        TestUtil.createUserAndLogin();

        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(0, action.getItemNames().size());
        Assert.assertEquals(0, action.getPageNames().size());
        Assert.assertNull(action.getTemplateName());
        Assert.assertNull(action.getThemeName());
        Assert.assertEquals("/WEB-INF/pages/getBlueprintDetail.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void executeWithoutLogin() {
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(action, resolutionMock.getLoginInUserAction());
    }

    @Test
    public void executeNotFound() {
        TestUtil.createUserAndLogin();
        action.setBlueprintId(-1);

        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(0, action.getItemNames().size());
        Assert.assertEquals(0, action.getPageNames().size());
        Assert.assertNull(action.getTemplateName());
        Assert.assertNull(action.getThemeName());
        Assert.assertEquals("/WEB-INF/pages/getBlueprintDetail.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void executeNotPublished() {
        final User user = TestUtil.createUserAndLogin();
        final Site blueprint = TestUtil.createBlueprint();
        TestUtil.createUserOnSiteRightActive(user, blueprint, SiteAccessLevel.ADMINISTRATOR);
        action.setBlueprintId(blueprint.getSiteId());

        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals(0, action.getItemNames().size());
        Assert.assertEquals(0, action.getPageNames().size());
        Assert.assertNull(action.getTemplateName());
        Assert.assertNull(action.getThemeName());
        Assert.assertEquals("/WEB-INF/pages/getBlueprintDetail.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void executeWithPublishedBlueprintsWithDrafts() {
        TestUtil.createUserAndLogin();

        final Site blueprint = TestUtil.createBlueprint();
        blueprint.setActivated(new Date());
        blueprint.setTitle("1");
        blueprint.setThemeId(new ThemeId("a", "b"));

        action.setBlueprintId(blueprint.getSiteId());

        final Template template1 = TestUtil.createTemplate();
        template1.setDirectory("a");
        template1.setName("fff");
        final Theme theme1 = TestUtil.createTheme(template1);
        theme1.setName("vvv");
        theme1.setFile("b");
        fileSystemMock.getTemplates().add(template1);

        final Template template2 = TestUtil.createTemplate();
        template2.setDirectory("a1");
        template2.setName("fff1");
        TestUtil.createTheme(template2);
        final Theme theme2 = TestUtil.createTheme(template2);
        theme2.setName("vvv1");
        theme2.setFile("b1");
        fileSystemMock.getTemplates().add(template2);

        final PageManager pageManager = TestUtil.createPageVersionAndPage(blueprint);

        final DraftBlog draftBlog1 = TestUtil.createBlog(blueprint);

        final WidgetItem widgetItem1 = TestUtil.createWidgetItem();
        widgetItem1.setDraftItem(draftBlog1);
        pageManager.addWidget(widgetItem1);

        final DraftAdminLogin draftAdminLogin1 = TestUtil.createAdminLogin(blueprint);
        final WidgetItem widgetItem2 = TestUtil.createWidgetItem();
        widgetItem2.setDraftItem(draftAdminLogin1);
        pageManager.addWidget(widgetItem2);

        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals("fff", action.getTemplateName());
        Assert.assertEquals("vvv", action.getThemeName());
        Assert.assertEquals("We can't show draft pages!", 0, action.getPageNames().size());
        Assert.assertEquals("We can't show draft items!", 0, action.getItemNames().size());
        Assert.assertEquals("/WEB-INF/pages/getBlueprintDetail.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void showWithPublishedBlueprintsWithWork() {
        TestUtil.createUserAndLogin();

        final Site blueprint = TestUtil.createBlueprint();
        blueprint.setActivated(new Date());
        blueprint.setTitle("1");
        blueprint.setThemeId(new ThemeId("a", "b"));
        action.setBlueprintId(blueprint.getId());

        final Template template1 = TestUtil.createTemplate();
        template1.setDirectory("a");
        template1.setName("fff");
        final Theme theme1 = TestUtil.createTheme(template1);
        theme1.setName("vvv");
        theme1.setFile("b");
        fileSystemMock.getTemplates().add(template1);

        final Template template2 = TestUtil.createTemplate();
        template2.setDirectory("a1");
        template2.setName("fff1");
        TestUtil.createTheme(template2);
        final Theme theme2 = TestUtil.createTheme(template2);
        theme2.setName("vvv1");
        theme2.setFile("b1");
        fileSystemMock.getTemplates().add(template2);

        final PageManager pageManager1 = TestUtil.createPageVersionAndPage(blueprint);
        final WorkPageSettings workPageSettings1 = TestUtil.createWorkPageSettings(pageManager1.getDraftPageSettings());
        workPageSettings1.setName("xuy");

        final DraftBlog draftBlog1 = TestUtil.createBlog(blueprint);

        final WorkBlog workBlog1 = new WorkBlog();
        workBlog1.setName("quyr");
        workBlog1.setId(draftBlog1.getId());
        persistanceMock.putItem(workBlog1);

        final WidgetItem widgetItem1 = TestUtil.createWidgetItem();
        widgetItem1.setDraftItem(draftBlog1);
        workPageSettings1.addWidget(widgetItem1);

        final DraftAdminLogin draftAdminLogin1 = TestUtil.createAdminLogin(blueprint);

        final WorkAdminLogin workAdminLogin1 = new WorkAdminLogin();
        workAdminLogin1.setName("aras");
        workAdminLogin1.setId(draftAdminLogin1.getId());
        persistanceMock.putItem(workAdminLogin1);

        final WidgetItem widgetItem2 = TestUtil.createWidgetItem();
        widgetItem2.setDraftItem(draftAdminLogin1);
        workPageSettings1.addWidget(widgetItem2);

        final PageManager pageManager2 = TestUtil.createPageVersionAndPage(blueprint);
        final WorkPageSettings workPageSettings2 = TestUtil.createWorkPageSettings(pageManager2.getDraftPageSettings());
        workPageSettings2.setName("aruna");

        // Creating site menu. Pages are get by default site menu.
        final DraftMenu siteMenu = TestUtil.createMenu(blueprint);
        final MenuItem menuItem1 = TestUtil.createMenuItem(siteMenu);
        menuItem1.setPageId(pageManager1.getPageId());
        siteMenu.addChild(menuItem1);
        final MenuItem menuItem2 = TestUtil.createMenuItem(siteMenu);
        menuItem2.setPageId(pageManager2.getPageId());
        siteMenu.addChild(menuItem2);

        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals("Where are my work pages?", 2, action.getPageNames().size());
        Assert.assertEquals("Where are blueprint items?", 2, action.getItemNames().size());
        Assert.assertEquals("xuy", action.getPageNames().get(0));
        Assert.assertEquals("aruna", action.getPageNames().get(1));
        Assert.assertEquals("aras", action.getItemNames().get(0));
        Assert.assertEquals("quyr", action.getItemNames().get(1));
        Assert.assertEquals("/WEB-INF/pages/getBlueprintDetail.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void showWithItemsThatHasEmptyName() {
        TestUtil.createUserAndLogin();

        final Site blueprint = TestUtil.createBlueprint();
        blueprint.setActivated(new Date());
        blueprint.setTitle("1");
        blueprint.setThemeId(new ThemeId("a", "b"));
        action.setBlueprintId(blueprint.getId());

        final Template template1 = TestUtil.createTemplate();
        template1.setDirectory("a");
        template1.setName("fff");
        final Theme theme1 = TestUtil.createTheme(template1);
        theme1.setName("vvv");
        theme1.setFile("b");
        fileSystemMock.getTemplates().add(template1);

        final Template template2 = TestUtil.createTemplate();
        template2.setDirectory("a1");
        template2.setName("fff1");
        TestUtil.createTheme(template2);
        final Theme theme2 = TestUtil.createTheme(template2);
        theme2.setName("vvv1");
        theme2.setFile("b1");
        fileSystemMock.getTemplates().add(template2);

        final PageManager pageManager1 = TestUtil.createPageVersionAndPage(blueprint);
        final WorkPageSettings workPageSettings1 = TestUtil.createWorkPageSettings(pageManager1.getDraftPageSettings());
        workPageSettings1.setName("xuy");

        final DraftBlog draftBlog1 = TestUtil.createBlog(blueprint);

        final WorkBlog workBlog1 = new WorkBlog();
        workBlog1.setName("quyr");
        workBlog1.setId(draftBlog1.getId());
        persistanceMock.putItem(workBlog1);

        final WidgetItem widgetItem1 = TestUtil.createWidgetItem();
        widgetItem1.setDraftItem(draftBlog1);
        workPageSettings1.addWidget(widgetItem1);

        final DraftAdminLogin draftAdminLogin1 = TestUtil.createAdminLogin(blueprint);

        final WorkAdminLogin workAdminLogin1 = new WorkAdminLogin();
        workAdminLogin1.setName("aras");
        workAdminLogin1.setId(draftAdminLogin1.getId());
        persistanceMock.putItem(workAdminLogin1);

        final WorkAdminLogin workAdminLogin2 = new WorkAdminLogin();
        workAdminLogin2.setName("");
        workAdminLogin2.setId(workAdminLogin2.getId());
        persistanceMock.putItem(workAdminLogin2);

        final WidgetItem widgetItem2 = TestUtil.createWidgetItem();
        widgetItem2.setDraftItem(draftAdminLogin1);
        workPageSettings1.addWidget(widgetItem2);

        final PageManager pageManager2 = TestUtil.createPageVersionAndPage(blueprint);
        final WorkPageSettings workPageSettings2 = TestUtil.createWorkPageSettings(pageManager2.getDraftPageSettings());
        workPageSettings2.setName("aruna");

        // Creating site menu. Pages are get by default site menu.
        final DraftMenu siteMenu = TestUtil.createMenu(blueprint);
        final MenuItem menuItem1 = TestUtil.createMenuItem(siteMenu);
        menuItem1.setPageId(pageManager1.getPageId());
        siteMenu.addChild(menuItem1);
        final MenuItem menuItem2 = TestUtil.createMenuItem(siteMenu);
        menuItem2.setPageId(pageManager2.getPageId());
        siteMenu.addChild(menuItem2);

        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertEquals("Where are my work pages?", 2, action.getPageNames().size());
        Assert.assertEquals("Where are blueprint items?", 2, action.getItemNames().size());
        Assert.assertEquals("xuy", action.getPageNames().get(0));
        Assert.assertEquals("aruna", action.getPageNames().get(1));
        Assert.assertEquals("aras", action.getItemNames().get(0));
        Assert.assertEquals("quyr", action.getItemNames().get(1));
        Assert.assertEquals("/WEB-INF/pages/getBlueprintDetail.jsp", resolutionMock.getForwardToUrl());
    }

    private final GetBlueprintDetailAction action = new GetBlueprintDetailAction();
    private final PersistanceMock persistanceMock = (PersistanceMock) ServiceLocator.getPersistance();
    private final FileSystemMock fileSystemMock = (FileSystemMock) ServiceLocator.getFileSystem();

}