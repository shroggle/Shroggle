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
package com.shroggle.presentation.menu;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.menu.MenuPageCheckSometimes;
import com.shroggle.logic.site.page.MenuPagesHtmlTextCreator;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class RestoreDefaultStructureServiceTest {

    private final RestoreDefaultStructureService service = new RestoreDefaultStructureService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void testExecute_withAddedCustomPages_onePageWasMovedToAnotherPosition() throws Exception {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion1 = TestUtil.createPageVersionAndPage(site);
        final PageManager pageVersion2 = TestUtil.createPageVersionAndPage(site);
        final PageManager pageVersion3 = TestUtil.createPageVersionAndPage(site);
        final PageManager pageVersion4 = TestUtil.createPageVersionAndPage(site);
        final PageManager pageVersion5 = TestUtil.createPageVersionAndPage(site);
        final PageManager pageVersion6 = TestUtil.createPageVersionAndPage(site);

        final DraftMenu defaultMenu = site.getMenu();
        final MenuItem defaultMenuItem1 = TestUtil.createMenuItem(pageVersion1.getPage().getPageId(), defaultMenu, true);
        final MenuItem defaultMenuItem2 = TestUtil.createMenuItem(pageVersion2.getPage().getPageId(), defaultMenu, false);
        final MenuItem defaultMenuItem3 = TestUtil.createMenuItem(pageVersion3.getPage().getPageId(), defaultMenu, true);
        final MenuItem defaultMenuItem4 = TestUtil.createMenuItem(pageVersion4.getPage().getPageId(), defaultMenu, false);
        final MenuItem defaultMenuItem5 = TestUtil.createMenuItem(pageVersion5.getPage().getPageId(), defaultMenu, true);

        /*---------------------------------------------Default Structure----------------------------------------------*/
        defaultMenuItem1.setParent(null);
        defaultMenuItem2.setParent(null);
        defaultMenuItem3.setParent(null);
        defaultMenuItem4.setParent(defaultMenuItem2);
        defaultMenuItem5.setParent(defaultMenuItem4);
        /*---------------------------------------------Default Structure----------------------------------------------*/


        /*-------------------------------------Checking initial default structure-------------------------------------*/
        Assert.assertEquals(3, defaultMenu.getMenuItems().size());
        Assert.assertEquals(defaultMenuItem1, defaultMenu.getMenuItems().get(0));
        Assert.assertEquals(defaultMenuItem2, defaultMenu.getMenuItems().get(1));
        Assert.assertEquals(defaultMenuItem3, defaultMenu.getMenuItems().get(2));

        Assert.assertEquals(0, defaultMenuItem1.getChildren().size());


        Assert.assertEquals(1, defaultMenuItem2.getChildren().size());
        Assert.assertEquals(defaultMenuItem4, defaultMenuItem2.getChildren().get(0));

        Assert.assertEquals(0, defaultMenuItem3.getChildren().size());

        Assert.assertEquals(1, defaultMenuItem4.getChildren().size());
        Assert.assertEquals(defaultMenuItem5, defaultMenuItem4.getChildren().get(0));


        Assert.assertEquals(0, defaultMenuItem5.getChildren().size());
        /*-------------------------------------Checking initial default structure-------------------------------------*/


        final DraftMenu menu = TestUtil.createMenu();
        menu.setMenuStructure(MenuStructureType.OWN);
        menu.setSiteId(site.getSiteId());

        final MenuItem menuItem1 = TestUtil.createMenuItem(pageVersion1.getPage().getPageId(), menu, true);
        final MenuItem menuItem2 = TestUtil.createMenuItem(pageVersion2.getPage().getPageId(), menu, false);
        final MenuItem menuItem3 = TestUtil.createMenuItem(pageVersion3.getPage().getPageId(), menu, true);
        final MenuItem menuItem4 = TestUtil.createMenuItem(pageVersion4.getPage().getPageId(), menu, false);
        final MenuItem menuItem5 = TestUtil.createMenuItem(pageVersion5.getPage().getPageId(), menu, true);
        // New custom page
        final MenuItem menuItem6 = TestUtil.createMenuItem(pageVersion6.getPage().getPageId(), menu, true);
        menuItem6.setDefaultPageId(null);


        /*-----------------------------------------------New Structure------------------------------------------------*/
        menuItem1.setParent(null);
        menuItem2.setParent(null);
        menuItem3.setParent(null);

        // This page moved to new parent. In default structure its parent - menuItem2
        menuItem4.setParent(menuItem3);
        menuItem5.setParent(menuItem4);

        // This newly created custom page added to menuItem2
        menuItem6.setParent(menuItem2);
        /*-----------------------------------------------New Structure------------------------------------------------*/


        final UpdateMenuItemResponse response = service.execute(menu.getId());


        Assert.assertNotNull(response);
        final MenuPagesHtmlTextCreator pagesHtmlTextCreator = new MenuPagesHtmlTextCreator(menu, new MenuPageCheckSometimes(menu), SiteShowOption.getDraftOption());
        Assert.assertEquals(pagesHtmlTextCreator.getHtml(), response.getTreeHtml());
        Assert.assertEquals(new GetInfoAboutItemService().execute(menu.getMenuItems().get(0).getId()), response.getInfoAboutSelectedItem());
        Assert.assertEquals(menu.getMenuItems().get(0).getId(), response.getSelectedMenuItemId().intValue());


        /*--------------------------------Checking restored default structure for menu--------------------------------*/
        Assert.assertEquals(MenuStructureType.DEFAULT, menu.getMenuStructure());
        Assert.assertEquals(3, menu.getMenuItems().size());
        Assert.assertEquals(menuItem1, menu.getMenuItems().get(0));
        Assert.assertEquals(menuItem2, menu.getMenuItems().get(1));
        Assert.assertEquals(menuItem3, menu.getMenuItems().get(2));

        Assert.assertEquals(0, menuItem1.getChildren().size());


        Assert.assertEquals("Here is two children because new custom page has been added.", 2, menuItem2.getChildren().size());
        Assert.assertEquals("This item restored on its old position = 0", menuItem4, menuItem2.getChildren().get(0));
        Assert.assertEquals("And this new custom item moved to the second position", menuItem6, menuItem2.getChildren().get(1));

        Assert.assertEquals(0, menuItem3.getChildren().size());

        Assert.assertEquals(1, menuItem4.getChildren().size());
        Assert.assertEquals(menuItem5, menuItem4.getChildren().get(0));


        Assert.assertEquals(0, menuItem5.getChildren().size());
        /*--------------------------------Checking restored default structure for menu--------------------------------*/
    }

    @Test
    public void testExecute_withNotFoundMenu_withoutMenuId() throws Exception {
        final UpdateMenuItemResponse response = service.execute(null);
        Assert.assertNull(response.getTreeHtml());
        Assert.assertEquals("", response.getInfoAboutSelectedItem());
        Assert.assertEquals(null, response.getSelectedMenuItemId());
    }

    @Test
    public void testExecute_withNotFoundMenu_withWrongMenuId() throws Exception {
        TestUtil.createMenu();
        final UpdateMenuItemResponse response = service.execute(-1);
        Assert.assertNull(response.getTreeHtml());
        Assert.assertEquals("", response.getInfoAboutSelectedItem());
        Assert.assertEquals(null, response.getSelectedMenuItemId());
    }

    @Test
    public void testExecute_withNotFoundSite() throws Exception {
        final DraftMenu menu = TestUtil.createMenu();
        final UpdateMenuItemResponse response = service.execute(menu.getId());
        Assert.assertNull(response.getTreeHtml());
        Assert.assertEquals("", response.getInfoAboutSelectedItem());
        Assert.assertEquals(null, response.getSelectedMenuItemId());
    }

    @Test
    public void testExecute_withNotFoundDefaultMenuInSite() throws Exception {
        final Site site = TestUtil.createSite();
        site.setMenu(null);
        final DraftMenu menu = TestUtil.createMenu();
        menu.setSiteId(site.getSiteId());

        final UpdateMenuItemResponse response = service.execute(menu.getId());
        Assert.assertNull(response.getTreeHtml());
        Assert.assertEquals("", response.getInfoAboutSelectedItem());
        Assert.assertEquals(null, response.getSelectedMenuItemId());
    }
}
