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
import com.shroggle.exception.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.menu.CreateMenuRequest;
import com.shroggle.presentation.menu.CreateMenuService;
import com.shroggle.presentation.menu.ItemIdIncludeInMenu;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@RunWith(TestRunnerWithMockServices.class)
public class CreateMenuServiceTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void executeFromSiteEditPage() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager page = TestUtil.createPageVersionAndPage(site);
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftMenu menu = TestUtil.createMenu(site);

        WidgetItem widgetMenu = new WidgetItem();
        widgetMenu.setDraftItem(null);
        persistance.putWidget(widgetMenu);
        widgetMenu.setDraftItem(menu);
        page.addWidget(widgetMenu);

        request.setMenuId(menu.getId());
        request.setName("name1");
        request.setMenuStyleType(MenuStyleType.TREE_STYLE_HORIZONTAL);
        request.setWidgetId(widgetMenu.getWidgetId());
        request.setMenuStructure(MenuStructureType.OWN);
        request.setIncludeInMenus(Collections.<ItemIdIncludeInMenu>emptyList());

        FunctionalWidgetInfo response = service.execute(request);

        Assert.assertNotNull(response);
        Assert.assertEquals(widgetMenu.getWidgetId(), response.getWidget().getWidgetId());

        Assert.assertEquals("name1", menu.getName());
        Assert.assertEquals(site.getSiteId(), menu.getSiteId());
        Assert.assertEquals(0, menu.getMenuItems().size());
        Assert.assertEquals(MenuStructureType.OWN, menu.getMenuStructure());
    }
    
    @Test
    public void executeFromManageItems() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftMenu menu = TestUtil.createMenu(site);

        request.setMenuId(menu.getId());
        request.setName("name1");
        request.setMenuStyleType(MenuStyleType.TREE_STYLE_HORIZONTAL);
        request.setMenuStructure(MenuStructureType.OWN);
        request.setIncludeInMenus(Collections.<ItemIdIncludeInMenu>emptyList());

        FunctionalWidgetInfo response = service.execute(request);

        Assert.assertNotNull(response);
        Assert.assertNull(response.getWidget());

        Assert.assertEquals("name1", menu.getName());
        Assert.assertEquals(site.getSiteId(), menu.getSiteId());
        Assert.assertEquals(0, menu.getMenuItems().size());
        Assert.assertEquals(MenuStructureType.OWN, menu.getMenuStructure());
    }

    @Test
    public void executeWithUpdate() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final Page page1 = TestUtil.createPage(site);
        final Page page2 = TestUtil.createPage(site);
        final Page page3 = TestUtil.createPage(site);
        final Page page4 = TestUtil.createPage(site);
        final Page page5 = TestUtil.createPage(site);
        final Page page6 = TestUtil.createPage(site);

        final MenuItem menuItem = TestUtil.createMenuItem(page1.getPageId(), site.getMenu());
        menuItem.setParent(null);
        menuItem.setUrlType(MenuUrlType.CUSTOM_URL);
        final MenuItem menuItem2 = TestUtil.createMenuItem(page2.getPageId(), site.getMenu());
        menuItem2.setParent(null);
        menuItem2.setUrlType(MenuUrlType.CUSTOM_URL);
        final MenuItem menuItem3 = TestUtil.createMenuItem(page3.getPageId(), site.getMenu());
        menuItem3.setParent(null);
        menuItem3.setUrlType(MenuUrlType.CUSTOM_URL);

        final DraftMenu menu = new DraftMenu();
        menu.setName("name1");
        menu.setSiteId(site.getSiteId());
        persistance.putMenu(menu);

        final MenuItem menuItem4 = TestUtil.createMenuItem(page4.getPageId(), menu, true);
        menuItem4.setParent(null);
        menuItem4.setUrlType(MenuUrlType.CUSTOM_URL);
        final MenuItem menuItem5 = TestUtil.createMenuItem(page5.getPageId(), menu, true);
        menuItem5.setParent(menuItem4);
        menuItem5.setUrlType(MenuUrlType.CUSTOM_URL);
        final MenuItem menuItem6 = TestUtil.createMenuItem(page6.getPageId(), menu, true);
        menuItem6.setParent(menuItem5);
        menuItem6.setUrlType(MenuUrlType.CUSTOM_URL);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final WidgetItem widgetMenu = TestUtil.createWidgetItem();
        widgetMenu.setDraftItem(menu);
        persistance.putWidget(widgetMenu);
        pageVersion.addWidget(widgetMenu);

        request.setMenuId(menu.getId());
        request.setName("name2");
        request.setMenuStructure(MenuStructureType.OWN);

        request.setWidgetId(widgetMenu.getWidgetId());
        request.setMenuStyleType(MenuStyleType.TREE_STYLE_HORIZONTAL);
        List<ItemIdIncludeInMenu> includeInMenus = Arrays.asList(new ItemIdIncludeInMenu(menuItem5.getId(), false), new ItemIdIncludeInMenu(menuItem6.getId(), false));
        request.setIncludeInMenus(includeInMenus);

        /*-----------------------------------------Structure before execution-----------------------------------------*/
        Assert.assertEquals("name1", menu.getName());
        Assert.assertEquals(site.getSiteId(), menu.getSiteId());
        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(menuItem4, menu.getMenuItems().get(0));
        Assert.assertEquals(menuItem5, menu.getMenuItems().get(0).getChildren().get(0));
        Assert.assertEquals(menuItem6, menu.getMenuItems().get(0).getChildren().get(0).getChildren().get(0));

        Assert.assertEquals(true, menu.getMenuItems().get(0).isIncludeInMenu());
        Assert.assertEquals(true, menu.getMenuItems().get(0).getChildren().get(0).isIncludeInMenu());
        Assert.assertEquals(true, menu.getMenuItems().get(0).getChildren().get(0).getChildren().get(0).isIncludeInMenu());
        /*-----------------------------------------Structure before execution-----------------------------------------*/

        FunctionalWidgetInfo response = service.execute(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(widgetMenu.getWidgetId(), response.getWidget().getWidgetId());

        final DraftMenu newMenu = (DraftMenu)widgetMenu.getDraftItem();//persistance.getMenuById();
        Assert.assertEquals("name2", newMenu.getName());
        Assert.assertEquals(site.getSiteId(), newMenu.getSiteId());
        Assert.assertEquals(MenuStructureType.OWN, menu.getMenuStructure());

        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(menuItem4, menu.getMenuItems().get(0));
        Assert.assertEquals(menuItem5, menu.getMenuItems().get(0).getChildren().get(0));
        Assert.assertEquals(menuItem6, menu.getMenuItems().get(0).getChildren().get(0).getChildren().get(0));

        Assert.assertEquals("We update includeInMenu here", true, menu.getMenuItems().get(0).isIncludeInMenu());
        Assert.assertEquals("We update includeInMenu here", false, menu.getMenuItems().get(0).getChildren().get(0).isIncludeInMenu());
        Assert.assertEquals("We update includeInMenu here", false, menu.getMenuItems().get(0).getChildren().get(0).getChildren().get(0).isIncludeInMenu());

    }

    @Test(expected = MenuNameNotUniqueException.class)
    public void executeWithNotUniqueName() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftMenu menu = TestUtil.createMenu(site);

        DraftMenu menu2 = TestUtil.createMenu(site);
        menu2.setName("name1");

        request.setMenuId(menu.getId());
        request.setName("name1");
        request.setMenuStyleType(MenuStyleType.DROP_DOWN_STYLE_HORIZONTAL);

        service.execute(request);
    }

    @Test(expected = MenuNotFoundException.class)
    public void executeWithNotFoundMenu() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        request.setMenuId(-1);
        request.setName("name1");
        request.setMenuStyleType(MenuStyleType.DROP_DOWN_STYLE_HORIZONTAL);

        service.execute(request);
    }

    @Test(expected = MenuStyleTypeNotSetException.class)
    public void executeWithoutStyleType() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftMenu menu = TestUtil.createMenu(site);

        request.setMenuId(menu.getId());
        request.setName("name1");

        service.execute(request);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() throws Exception {
        service.execute(request);
    }

    private final CreateMenuService service = new CreateMenuService();
    private final CreateMenuRequest request = new CreateMenuRequest();
    private final Persistance persistance = ServiceLocator.getPersistance();

}