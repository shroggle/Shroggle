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
public class RemoveMenuItemServiceTest {

    private final RemoveMenuItemService service = new RemoveMenuItemService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void testExecute() throws Exception {
        Site site = TestUtil.createSite();

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final DraftMenu menu = TestUtil.createMenu();
        menu.setSiteId(site.getSiteId());
        final MenuItem menuItem1 = TestUtil.createMenuItem(pageVersion.getPage().getPageId(), menu);
        final MenuItem menuItem2 = TestUtil.createMenuItem(pageVersion.getPage().getPageId(), menu);
        final MenuItem menuItem3 = TestUtil.createMenuItem(pageVersion.getPage().getPageId(), menu);
        final MenuItem menuItem4 = TestUtil.createMenuItem(pageVersion.getPage().getPageId(), menu);
        final MenuItem menuItem5 = TestUtil.createMenuItem(pageVersion.getPage().getPageId(), menu);

        menuItem1.setParent(null);
        menuItem2.setParent(menuItem1);
        menuItem3.setParent(menuItem1);
        menuItem4.setParent(menuItem3);
        menuItem5.setParent(menuItem3);

        /*-----------------------------------------Checking initial structure-----------------------------------------*/
        Assert.assertEquals(MenuStructureType.DEFAULT, menu.getMenuStructure());
        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(menuItem1, menu.getMenuItems().get(0));
        Assert.assertEquals(2, menuItem1.getChildren().size());
        Assert.assertEquals(menuItem2, menuItem1.getChildren().get(0));
        Assert.assertEquals(menuItem3, menuItem1.getChildren().get(1));

        Assert.assertEquals(0, menuItem2.getChildren().size());

        Assert.assertEquals(2, menuItem3.getChildren().size());
        Assert.assertEquals(menuItem4, menuItem3.getChildren().get(0));
        Assert.assertEquals(menuItem5, menuItem3.getChildren().get(1));

        Assert.assertEquals(0, menuItem4.getChildren().size());
        Assert.assertEquals(0, menuItem5.getChildren().size());
        /*-----------------------------------------Checking initial structure-----------------------------------------*/


        final UpdateMenuItemResponse response = service.execute(menuItem1.getId());

        final MenuPagesHtmlTextCreator pagesHtmlTextCreator = new MenuPagesHtmlTextCreator(menu, new MenuPageCheckSometimes(menu), SiteShowOption.getDraftOption());
        Assert.assertEquals(pagesHtmlTextCreator.getHtml(), response.getTreeHtml());
        Assert.assertEquals(new GetInfoAboutItemService().execute(menu.getMenuItems().get(0).getId()), response.getInfoAboutSelectedItem());
        Assert.assertEquals(menu.getMenuItems().get(0).getId(), response.getSelectedMenuItemId().intValue());
        /*-------------------------------------Checking structure after execution-------------------------------------*/
        Assert.assertEquals(MenuStructureType.OWN, menu.getMenuStructure());
        Assert.assertEquals(2, menu.getMenuItems().size());
        Assert.assertEquals(menuItem2, menu.getMenuItems().get(0));
        Assert.assertEquals(menuItem3, menu.getMenuItems().get(1));

        Assert.assertEquals(0, menuItem2.getChildren().size());

        Assert.assertEquals(2, menuItem3.getChildren().size());
        Assert.assertEquals(menuItem4, menuItem3.getChildren().get(0));
        Assert.assertEquals(menuItem5, menuItem3.getChildren().get(1));

        Assert.assertEquals(0, menuItem4.getChildren().size());
        Assert.assertEquals(0, menuItem5.getChildren().size());
        /*-------------------------------------Checking structure after execution-------------------------------------*/
    }

    @Test
    public void testExecute_withoutId() throws Exception {
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem1 = TestUtil.createMenuItem(1, menu);
        final MenuItem menuItem2 = TestUtil.createMenuItem(2, menu);
        final MenuItem menuItem3 = TestUtil.createMenuItem(3, menu);
        final MenuItem menuItem4 = TestUtil.createMenuItem(4, menu);
        final MenuItem menuItem5 = TestUtil.createMenuItem(5, menu);

        menuItem1.setParent(null);
        menuItem2.setParent(menuItem1);
        menuItem3.setParent(menuItem1);
        menuItem4.setParent(menuItem3);
        menuItem5.setParent(menuItem3);

        /*-----------------------------------------Checking initial structure-----------------------------------------*/
        Assert.assertEquals(MenuStructureType.DEFAULT, menu.getMenuStructure());
        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(menuItem1, menu.getMenuItems().get(0));
        Assert.assertEquals(2, menuItem1.getChildren().size());
        Assert.assertEquals(menuItem2, menuItem1.getChildren().get(0));
        Assert.assertEquals(menuItem3, menuItem1.getChildren().get(1));

        Assert.assertEquals(0, menuItem2.getChildren().size());

        Assert.assertEquals(2, menuItem3.getChildren().size());
        Assert.assertEquals(menuItem4, menuItem3.getChildren().get(0));
        Assert.assertEquals(menuItem5, menuItem3.getChildren().get(1));

        Assert.assertEquals(0, menuItem4.getChildren().size());
        Assert.assertEquals(0, menuItem5.getChildren().size());
        /*-----------------------------------------Checking initial structure-----------------------------------------*/


        final UpdateMenuItemResponse response = service.execute(null);


        Assert.assertNull(response.getTreeHtml());
        Assert.assertEquals("", response.getInfoAboutSelectedItem());
        Assert.assertEquals(null, response.getSelectedMenuItemId());
        /*-------------------------------------Checking structure after execution-------------------------------------*/
        Assert.assertEquals(MenuStructureType.DEFAULT, menu.getMenuStructure());
        // Structure is the same
        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(menuItem1, menu.getMenuItems().get(0));
        Assert.assertEquals(2, menuItem1.getChildren().size());
        Assert.assertEquals(menuItem2, menuItem1.getChildren().get(0));
        Assert.assertEquals(menuItem3, menuItem1.getChildren().get(1));

        Assert.assertEquals(0, menuItem2.getChildren().size());

        Assert.assertEquals(2, menuItem3.getChildren().size());
        Assert.assertEquals(menuItem4, menuItem3.getChildren().get(0));
        Assert.assertEquals(menuItem5, menuItem3.getChildren().get(1));

        Assert.assertEquals(0, menuItem4.getChildren().size());
        Assert.assertEquals(0, menuItem5.getChildren().size());
        /*-------------------------------------Checking structure after execution-------------------------------------*/
    }

    @Test
    public void testExecute_withWrongId() throws Exception {
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem1 = TestUtil.createMenuItem(1, menu);
        final MenuItem menuItem2 = TestUtil.createMenuItem(2, menu);
        final MenuItem menuItem3 = TestUtil.createMenuItem(3, menu);
        final MenuItem menuItem4 = TestUtil.createMenuItem(4, menu);
        final MenuItem menuItem5 = TestUtil.createMenuItem(5, menu);

        menuItem1.setParent(null);
        menuItem2.setParent(menuItem1);
        menuItem3.setParent(menuItem1);
        menuItem4.setParent(menuItem3);
        menuItem5.setParent(menuItem3);

        /*-----------------------------------------Checking initial structure-----------------------------------------*/
        Assert.assertEquals(MenuStructureType.DEFAULT, menu.getMenuStructure());
        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(menuItem1, menu.getMenuItems().get(0));
        Assert.assertEquals(2, menuItem1.getChildren().size());
        Assert.assertEquals(menuItem2, menuItem1.getChildren().get(0));
        Assert.assertEquals(menuItem3, menuItem1.getChildren().get(1));

        Assert.assertEquals(0, menuItem2.getChildren().size());

        Assert.assertEquals(2, menuItem3.getChildren().size());
        Assert.assertEquals(menuItem4, menuItem3.getChildren().get(0));
        Assert.assertEquals(menuItem5, menuItem3.getChildren().get(1));

        Assert.assertEquals(0, menuItem4.getChildren().size());
        Assert.assertEquals(0, menuItem5.getChildren().size());
        /*-----------------------------------------Checking initial structure-----------------------------------------*/


        final UpdateMenuItemResponse response = service.execute(-1);


        Assert.assertNull(response.getTreeHtml());
        Assert.assertEquals("", response.getInfoAboutSelectedItem());
        Assert.assertEquals(null, response.getSelectedMenuItemId());
        /*-------------------------------------Checking structure after execution-------------------------------------*/
        Assert.assertEquals(MenuStructureType.DEFAULT, menu.getMenuStructure());
        // Structure is the same
        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(menuItem1, menu.getMenuItems().get(0));
        Assert.assertEquals(2, menuItem1.getChildren().size());
        Assert.assertEquals(menuItem2, menuItem1.getChildren().get(0));
        Assert.assertEquals(menuItem3, menuItem1.getChildren().get(1));

        Assert.assertEquals(0, menuItem2.getChildren().size());

        Assert.assertEquals(2, menuItem3.getChildren().size());
        Assert.assertEquals(menuItem4, menuItem3.getChildren().get(0));
        Assert.assertEquals(menuItem5, menuItem3.getChildren().get(1));

        Assert.assertEquals(0, menuItem4.getChildren().size());
        Assert.assertEquals(0, menuItem5.getChildren().size());
        /*-------------------------------------Checking structure after execution-------------------------------------*/
    }
}
