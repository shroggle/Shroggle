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
package com.shroggle.util;

import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.menu.MenuItemsManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.persistance.hibernate.TestRunnerWithHibernateService;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithHibernateService.class)
public class MenuItemsManagerTestRunWithHibernate extends MenuItemsManager{


    @Test
    public void testRemoveItemsAndMoveChildren() {
        final Site site1 = TestUtil.createSite();
        DraftMenu menu1 = TestUtil.createMenu(site1);
        MenuItem menuItem1 = TestUtil.createMenuItem(1, menu1);
        MenuItem menuItem2 = TestUtil.createMenuItem(2, menu1);
        MenuItem menuItem3 = TestUtil.createMenuItem(1, menu1);
        menuItem1.setParent(null);
        menuItem2.setParent(menuItem1);
        menuItem3.setParent(menuItem2);
        /*-------------------------------------Testing structure before execution-------------------------------------*/
        DraftMenu testMenu1 = ServiceLocator.getPersistance().getMenuById(menu1.getId());
        Assert.assertEquals(1, testMenu1.getMenuItems().size());
        Assert.assertEquals(1, testMenu1.getMenuItems().get(0).getPageId().intValue());
        Assert.assertEquals(menuItem1, testMenu1.getMenuItems().get(0));
        Assert.assertEquals(1, testMenu1.getMenuItems().get(0).getChildren().size());
        Assert.assertEquals(menuItem2, testMenu1.getMenuItems().get(0).getChildren().get(0));
        Assert.assertEquals(1, testMenu1.getMenuItems().get(0).getChildren().get(0).getChildren().size());
        Assert.assertEquals(menuItem3, testMenu1.getMenuItems().get(0).getChildren().get(0).getChildren().get(0));
        /*-------------------------------------Testing structure before execution-------------------------------------*/

        final Site site2 = TestUtil.createSite();
        DraftMenu menu2 = TestUtil.createMenu(site2);
        MenuItem menuItem4 = TestUtil.createMenuItem(1, menu2);
        menuItem4.setParent(null);
        /*-------------------------------------Testing structure before execution-------------------------------------*/
        DraftMenu testMenu2 = ServiceLocator.getPersistance().getMenuById(menu2.getId());
        Assert.assertEquals(1, testMenu2.getMenuItems().size());
        Assert.assertEquals(1, testMenu2.getMenuItems().get(0).getPageId().intValue());
        Assert.assertEquals(menuItem4, testMenu2.getMenuItems().get(0));
        Assert.assertEquals(0, testMenu2.getMenuItems().get(0).getChildren().size());
        /*-------------------------------------Testing structure before execution-------------------------------------*/


        MenuItemsManager.removeItemsAndMoveChildren(1);


        /*-------------------------------------Testing structure after execution--------------------------------------*/
        DraftMenu newMenu1 = ServiceLocator.getPersistance().getMenuById(menu1.getId());
        Assert.assertEquals(1, newMenu1.getMenuItems().size());
        Assert.assertEquals(2, newMenu1.getMenuItems().get(0).getPageId().intValue());
        Assert.assertEquals(menuItem2, newMenu1.getMenuItems().get(0));
        Assert.assertEquals(0, newMenu1.getMenuItems().get(0).getChildren().size());

        DraftMenu newMenu2 = ServiceLocator.getPersistance().getMenuById(menu2.getId());
        Assert.assertEquals(0, newMenu2.getMenuItems().size());
        /*-------------------------------------Testing structure after execution--------------------------------------*/
    }

    @Test
    public void testNormalizePositions() {
        final Site site1 = TestUtil.createSite();
        DraftMenu menu1 = TestUtil.createMenu(site1);
        MenuItem menuItem1 = TestUtil.createMenuItem(1, menu1);
        MenuItem menuItem2 = TestUtil.createMenuItem(2, menu1);
        MenuItem menuItem3 = TestUtil.createMenuItem(3, menu1);
        menuItem1.setParent(null);
        menuItem2.setParent(null);
        menuItem3.setParent(null);

        menuItem1.setPosition(10);
        menuItem2.setPosition(7);
        menuItem3.setPosition(100);

        // Children returned sorted by ther position
        Assert.assertEquals(7, menu1.getMenuItems().get(0).getPosition());
        Assert.assertEquals(10, menu1.getMenuItems().get(1).getPosition());
        Assert.assertEquals(100, menu1.getMenuItems().get(2).getPosition());

        Assert.assertEquals(10, menuItem1.getPosition());
        Assert.assertEquals(7, menuItem2.getPosition());
        Assert.assertEquals(100, menuItem3.getPosition());


        MenuItemsManager.normalizePositions(menu1.getMenuItems());


        Assert.assertEquals(0, menu1.getMenuItems().get(0).getPosition());
        Assert.assertEquals(1, menu1.getMenuItems().get(1).getPosition());
        Assert.assertEquals(2, menu1.getMenuItems().get(2).getPosition());

        // Menu items now has normalized positions from 0 to 2
        Assert.assertEquals(1, menuItem1.getPosition());
        Assert.assertEquals(0, menuItem2.getPosition());
        Assert.assertEquals(2, menuItem3.getPosition());
    }

    @Test
    public void testSelectItemWithDefaultTypeAndNeededPageIdFromChildren() {
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(2, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(2, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(2, menu);

        element2.setParent(null);
        element3.setParent(null);
        element4.setParent(null);
        element5.setParent(null);
        element6.setParent(null);
        element7.setParent(null);
        element8.setParent(null);

        element2.setDefaultPageId(null);
        element6.setDefaultPageId(null);
        element7.setDefaultPageId(null);
        element8.setDefaultPageId(null);

        Assert.assertEquals(element4, selectItemWithDefaultTypeAndNeededPageIdFromChildren(menu.getMenuItems(), 2));
    }

    @Test
    public void testSelectItemWithDefaultTypeAndNeededPageIdFromChildren_withoutNeededId() {
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem element2 = TestUtil.createMenuItem(1, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(324, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(1423, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(8, menu);

        element2.setParent(null);
        element3.setParent(null);
        element4.setParent(null);
        element5.setParent(null);
        element6.setParent(null);
        element7.setParent(null);
        element8.setParent(null);

        element2.setDefaultPageId(null);
        element6.setDefaultPageId(null);
        element7.setDefaultPageId(null);
        element8.setDefaultPageId(null);

        Assert.assertEquals(null, selectItemWithDefaultTypeAndNeededPageIdFromChildren(menu.getMenuItems(), 2));
    }

    @Test
    public void testSelectItemWithDefaultTypeAndNeededPageIdFromChildren_withoutNeededId_searchByNullId() {
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(2, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(2, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(2, menu);

        element2.setParent(null);
        element3.setParent(null);
        element4.setParent(null);
        element5.setParent(null);
        element6.setParent(null);
        element7.setParent(null);
        element8.setParent(null);

        element2.setDefaultPageId(null);
        element6.setDefaultPageId(null);
        element7.setDefaultPageId(null);
        element8.setDefaultPageId(null);

        Assert.assertEquals(null, selectItemWithDefaultTypeAndNeededPageIdFromChildren(menu.getMenuItems(), null));
    }

    @Test
    public void testSelectItemWithDefaultTypeAndNeededPageIdFromChildren_withNeededIdButWithoutDefaultType() {
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(2, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(2, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(2, menu);

        element2.setParent(null);
        element3.setParent(null);
        element4.setParent(null);
        element5.setParent(null);
        element6.setParent(null);
        element7.setParent(null);
        element8.setParent(null);

        element2.setDefaultPageId(null);
        element3.setDefaultPageId(null);
        element4.setDefaultPageId(null);
        element5.setDefaultPageId(null);
        element6.setDefaultPageId(null);
        element7.setDefaultPageId(null);
        element8.setDefaultPageId(null);

        Assert.assertEquals(null, selectItemWithDefaultTypeAndNeededPageIdFromChildren(menu.getMenuItems(), 2));
    }

    @Test
    public void testMoveItemsToNeddedPosition_moveToRootElementBecauseParentWithNeededIdNotFound() {
        final Site site = TestUtil.createSite();

        /*---------------------------Menu1 with default structure and default type of items---------------------------*/
        final DraftMenu menu1 = TestUtil.createMenu(site);
        menu1.setMenuStructure(MenuStructureType.DEFAULT);
        final MenuItem element1 = TestUtil.createMenuItem(1, menu1);
        final MenuItem element2 = TestUtil.createMenuItem(2, menu1);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu1);
        element1.setParent(null);
        element2.setParent(element1);
        element3.setParent(element2);
        /*---------------------------Menu1 with default structure and default type of items---------------------------*/


        MenuItemsManager.moveItemsToNeededPosition(site.getSiteId(), 3, 10, 0);


        /*------------------------------------------Checking menu1 structure------------------------------------------*/
        /*Structure of this menu is changed because it has DEFAULT structure and there is pages with needed id and DEFAULT type*/
        Assert.assertEquals(2, menu1.getMenuItems().size());
        Assert.assertEquals(true, menu1.getMenuItems().contains(element1));
        Assert.assertEquals(true, menu1.getMenuItems().contains(element3));
        Assert.assertEquals(1, element1.getChildren().size());
        Assert.assertEquals(true, element1.getChildren().contains(element2));
        Assert.assertEquals(0, element2.getChildren().size());
        Assert.assertEquals(0, element3.getChildren().size());
        Assert.assertEquals("This element has position 0 as expected", 0, element3.getPosition());
        Assert.assertEquals("And this element now moved to position 1", 1, element1.getPosition());
        /*------------------------------------------Checking menu1 structure------------------------------------------*/
    }

    @Test
    public void testMoveItemsToNeddedPosition_moveToRootElementBecauseParentWithDefaultStructureNotFound() {
        final Site site = TestUtil.createSite();

        /*---------------------------Menu1 with default structure and default type of items---------------------------*/
        final DraftMenu menu1 = TestUtil.createMenu(site);
        menu1.setMenuStructure(MenuStructureType.DEFAULT);
        final MenuItem element1 = TestUtil.createMenuItem(1, menu1);
        final MenuItem element2 = TestUtil.createMenuItem(2, menu1);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu1);
        element1.setDefaultPageId(null);
        element1.setParent(null);
        element2.setParent(element1);
        element3.setParent(element2);
        /*---------------------------Menu1 with default structure and default type of items---------------------------*/


        MenuItemsManager.moveItemsToNeededPosition(site.getSiteId(), 3, 1, 0);


        /*------------------------------------------Checking menu1 structure------------------------------------------*/
        /*Structure of this menu is changed because it has DEFAULT structure and there is pages with needed id and DEFAULT type*/
        Assert.assertEquals(2, menu1.getMenuItems().size());
        Assert.assertEquals(true, menu1.getMenuItems().contains(element1));
        Assert.assertEquals(true, menu1.getMenuItems().contains(element3));
        Assert.assertEquals(1, element1.getChildren().size());
        Assert.assertEquals(true, element1.getChildren().contains(element2));
        Assert.assertEquals(0, element2.getChildren().size());
        Assert.assertEquals(0, element3.getChildren().size());
        Assert.assertEquals("This element has position 0 as expected", 0, element3.getPosition());
        Assert.assertEquals("And this element now moved to position 1", 1, element1.getPosition());
        /*------------------------------------------Checking menu1 structure------------------------------------------*/
    }

    @Test
    public void testMoveItemsToNeddedPosition_moveToRootElementBecauseOfNullParentId() {
        final Site site = TestUtil.createSite();

        /*---------------------------Menu1 with default structure and default type of items---------------------------*/
        final DraftMenu menu1 = TestUtil.createMenu(site);
        menu1.setMenuStructure(MenuStructureType.DEFAULT);
        final MenuItem element1 = TestUtil.createMenuItem(1, menu1);
        final MenuItem element2 = TestUtil.createMenuItem(2, menu1);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu1);
        element1.setParent(null);
        element2.setParent(element1);
        element3.setParent(element2);
        /*---------------------------Menu1 with default structure and default type of items---------------------------*/


        MenuItemsManager.moveItemsToNeededPosition(site.getSiteId(), 3, null, 0);


        /*------------------------------------------Checking menu1 structure------------------------------------------*/
        /*Structure of this menu is changed because it has DEFAULT structure and there is pages with needed id and DEFAULT type*/
        Assert.assertEquals(2, menu1.getMenuItems().size());
        Assert.assertEquals(true, menu1.getMenuItems().contains(element1));
        Assert.assertEquals(true, menu1.getMenuItems().contains(element3));
        Assert.assertEquals(1, element1.getChildren().size());
        Assert.assertEquals(true, element1.getChildren().contains(element2));
        Assert.assertEquals(0, element2.getChildren().size());
        Assert.assertEquals(0, element3.getChildren().size());
        Assert.assertEquals("This element has position 0 as expected", 0, element3.getPosition());
        Assert.assertEquals("And this element now moved to position 1", 1, element1.getPosition());
        /*------------------------------------------Checking menu1 structure------------------------------------------*/
    }

    @Test
    public void testMoveItemsToNeddedPosition_StructureDoesNotChangedBecauseChildNotFoundById() {
        final Site site = TestUtil.createSite();

        /*---------------------------Menu1 with default structure and default type of items---------------------------*/
        final DraftMenu menu1 = TestUtil.createMenu(site);
        menu1.setMenuStructure(MenuStructureType.DEFAULT);
        final MenuItem element1 = TestUtil.createMenuItem(1, menu1);
        final MenuItem element2 = TestUtil.createMenuItem(2, menu1);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu1);
        element1.setParent(null);
        element2.setParent(element1);
        element3.setParent(element2);
        /*---------------------------Menu1 with default structure and default type of items---------------------------*/


        MenuItemsManager.moveItemsToNeededPosition(site.getSiteId(), 30, 1, 0);


        /*------------------------------------------Checking menu1 structure------------------------------------------*/
        /*Structure is the same*/
        Assert.assertEquals(1, menu1.getMenuItems().size());
        Assert.assertEquals(true, menu1.getMenuItems().contains(element1));
        Assert.assertEquals(1, element1.getChildren().size());
        Assert.assertEquals(true, element1.getChildren().contains(element2));
        Assert.assertEquals(1, element2.getChildren().size());
        Assert.assertEquals(true, element2.getChildren().contains(element3));
        Assert.assertEquals(0, element3.getChildren().size());
        /*------------------------------------------Checking menu1 structure------------------------------------------*/
    }

    @Test
    public void testMoveItemsToNeddedPosition_StructureDoesNotChangedBecauseChildWithDefaultTypeNotFound() {
        final Site site = TestUtil.createSite();

        /*---------------------------Menu1 with default structure and default type of items---------------------------*/
        final DraftMenu menu1 = TestUtil.createMenu(site);
        menu1.setMenuStructure(MenuStructureType.DEFAULT);
        final MenuItem element1 = TestUtil.createMenuItem(1, menu1);
        final MenuItem element2 = TestUtil.createMenuItem(2, menu1);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu1);
        element3.setDefaultPageId(null);
        element1.setParent(null);
        element2.setParent(element1);
        element3.setParent(element2);
        /*---------------------------Menu1 with default structure and default type of items---------------------------*/


        MenuItemsManager.moveItemsToNeededPosition(site.getSiteId(), 3, 1, 0);


        /*------------------------------------------Checking menu1 structure------------------------------------------*/
        /*Structure is the same*/
        Assert.assertEquals(1, menu1.getMenuItems().size());
        Assert.assertEquals(true, menu1.getMenuItems().contains(element1));
        Assert.assertEquals(1, element1.getChildren().size());
        Assert.assertEquals(true, element1.getChildren().contains(element2));
        Assert.assertEquals(1, element2.getChildren().size());
        Assert.assertEquals(true, element2.getChildren().contains(element3));
        Assert.assertEquals(0, element3.getChildren().size());
        /*------------------------------------------Checking menu1 structure------------------------------------------*/
    }

    @Test
    public void testMoveItemsToNeddedPosition_chengingForFewMenus() {
        final Site site = TestUtil.createSite();

        /*---------------------------Menu1 with default structure and default type of items---------------------------*/
        final DraftMenu menu1 = TestUtil.createMenu(site);
        menu1.setMenuStructure(MenuStructureType.DEFAULT);
        final MenuItem element1 = TestUtil.createMenuItem(1, menu1);
        final MenuItem element2 = TestUtil.createMenuItem(2, menu1);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu1);
        element1.setParent(null);
        element2.setParent(element1);
        element3.setParent(element2);
        /*---------------------------Menu1 with default structure and default type of items---------------------------*/

        /*---------------------------Menu2 with default structure and default type of items---------------------------*/
        final DraftMenu menu2 = TestUtil.createMenu(site);
        menu2.setMenuStructure(MenuStructureType.DEFAULT);
        final MenuItem element4 = TestUtil.createMenuItem(1, menu2);
        final MenuItem element5 = TestUtil.createMenuItem(2, menu2);
        final MenuItem element6 = TestUtil.createMenuItem(3, menu2);
        element4.setParent(null);
        element5.setParent(element4);
        element6.setParent(element5);
        /*---------------------------Menu2 with default structure and default type of items---------------------------*/

        /*---------------------------Menu3 with default structure and custom type of items----------------------------*/
        final DraftMenu menu3 = TestUtil.createMenu(site);
        menu3.setMenuStructure(MenuStructureType.DEFAULT);
        final MenuItem element7 = TestUtil.createMenuItem(1, menu3);
        final MenuItem element8 = TestUtil.createMenuItem(2, menu3);
        final MenuItem element9 = TestUtil.createMenuItem(3, menu3);
        element7.setDefaultPageId(null);
        element8.setDefaultPageId(null);
        element9.setDefaultPageId(null);
        element7.setParent(null);
        element8.setParent(element7);
        element9.setParent(element8);
        /*---------------------------Menu3 with default structure and custom type of items----------------------------*/

        /*---------------------------Menu4 with custom structure and default type of items----------------------------*/
        final DraftMenu menu4 = TestUtil.createMenu(site);
        menu4.setMenuStructure(MenuStructureType.OWN);
        final MenuItem element10 = TestUtil.createMenuItem(1, menu4);
        final MenuItem element11 = TestUtil.createMenuItem(2, menu4);
        final MenuItem element12 = TestUtil.createMenuItem(3, menu4);
        element10.setParent(null);
        element11.setParent(element10);
        element12.setParent(element11);
        /*---------------------------Menu4 with default structure and custom type of items----------------------------*/


        MenuItemsManager.moveItemsToNeededPosition(site.getSiteId(), 3, 1, 0);

        /*------------------------------------------Checking menu1 structure------------------------------------------*/
        /*Structure of this menu is changed because it has DEFAULT structure and there is pages with needed id and DEFAULT type*/
        Assert.assertEquals(1, menu1.getMenuItems().size());
        Assert.assertEquals(true, menu1.getMenuItems().contains(element1));
        Assert.assertEquals(2, element1.getChildren().size());
        Assert.assertEquals(true, element1.getChildren().contains(element2));
        Assert.assertEquals(true, element1.getChildren().contains(element3));
        Assert.assertEquals(0, element2.getChildren().size());
        Assert.assertEquals(0, element3.getChildren().size());
        Assert.assertEquals("This element has position 0 as expected", 0, element3.getPosition());
        Assert.assertEquals("And this element now moved to position 1", 1, element2.getPosition());
        /*------------------------------------------Checking menu1 structure------------------------------------------*/

        /*------------------------------------------Checking menu2 structure------------------------------------------*/
        /*Structure of this menu is changed because it has DEFAULT structure and there is pages with needed id and DEFAULT type*/
        Assert.assertEquals(1, menu2.getMenuItems().size());
        Assert.assertEquals(true, menu2.getMenuItems().contains(element4));
        Assert.assertEquals(2, element4.getChildren().size());
        Assert.assertEquals(true, element4.getChildren().contains(element5));
        Assert.assertEquals(true, element4.getChildren().contains(element6));
        Assert.assertEquals(0, element5.getChildren().size());
        Assert.assertEquals(0, element6.getChildren().size());
        Assert.assertEquals("This element has position 0 as expected", 0, element6.getPosition());
        Assert.assertEquals("And this element now moved to position 1", 1, element5.getPosition());
        /*------------------------------------------Checking menu2 structure------------------------------------------*/

        /*------------------------------------------Checking menu3 structure------------------------------------------*/
        /*Structure of this menu does not changed because it has DEFAULT structure but it has no pages with needed id and DEFAULT type*/
        Assert.assertEquals(1, menu3.getMenuItems().size());
        Assert.assertEquals(true, menu3.getMenuItems().contains(element7));
        Assert.assertEquals(1, element7.getChildren().size());
        Assert.assertEquals(true, element7.getChildren().contains(element8));
        Assert.assertEquals(1, element8.getChildren().size());
        Assert.assertEquals(true, element8.getChildren().contains(element9));
        Assert.assertEquals(0, element9.getChildren().size());
        /*------------------------------------------Checking menu3 structure------------------------------------------*/

        /*------------------------------------------Checking menu4 structure------------------------------------------*/
        /*Structure of this menu does not changed because it has OWN structure*/
        Assert.assertEquals(1, menu4.getMenuItems().size());
        Assert.assertEquals(true, menu4.getMenuItems().contains(element10));
        Assert.assertEquals(1, element10.getChildren().size());
        Assert.assertEquals(true, element10.getChildren().contains(element11));
        Assert.assertEquals(1, element11.getChildren().size());
        Assert.assertEquals(true, element11.getChildren().contains(element12));
        Assert.assertEquals(0, element12.getChildren().size());
        /*------------------------------------------Checking menu4 structure------------------------------------------*/
    }

    @Test
    public void testAddMenuItemsToDefaultMenus() {
        final Site site = TestUtil.createSite();

        /*---------------------------Menu1 with default structure and default type of items---------------------------*/
        final DraftMenu menu1 = TestUtil.createMenu(site);
        menu1.setMenuStructure(MenuStructureType.DEFAULT);
        final MenuItem element1 = TestUtil.createMenuItem(1, menu1);
        final MenuItem element2 = TestUtil.createMenuItem(2, menu1);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu1);
        element1.setParent(null);
        element2.setParent(element1);
        element3.setParent(element2);
        /*---------------------------Menu1 with default structure and default type of items---------------------------*/

        /*---------------------------Menu2 with default structure and default type of items---------------------------*/
        final DraftMenu menu2 = TestUtil.createMenu(site);
        menu2.setMenuStructure(MenuStructureType.DEFAULT);
        final MenuItem element4 = TestUtil.createMenuItem(1, menu2);
        final MenuItem element5 = TestUtil.createMenuItem(2, menu2);
        final MenuItem element6 = TestUtil.createMenuItem(3, menu2);
        element4.setParent(null);
        element5.setParent(element4);
        element6.setParent(element5);
        /*---------------------------Menu2 with default structure and default type of items---------------------------*/

        /*---------------------------Menu3 with default structure and custom type of items----------------------------*/
        final DraftMenu menu3 = TestUtil.createMenu(site);
        menu3.setMenuStructure(MenuStructureType.DEFAULT);
        final MenuItem element7 = TestUtil.createMenuItem(1, menu3);
        final MenuItem element8 = TestUtil.createMenuItem(2, menu3);
        final MenuItem element9 = TestUtil.createMenuItem(3, menu3);
        element7.setDefaultPageId(null);
        element8.setDefaultPageId(null);
        element9.setDefaultPageId(null);
        element7.setParent(null);
        element8.setParent(element7);
        element9.setParent(element8);
        /*---------------------------Menu3 with default structure and custom type of items----------------------------*/

        /*---------------------------Menu4 with custom structure and default type of items----------------------------*/
        final DraftMenu menu4 = TestUtil.createMenu(site);
        menu4.setMenuStructure(MenuStructureType.OWN);
        final MenuItem element10 = TestUtil.createMenuItem(1, menu4);
        final MenuItem element11 = TestUtil.createMenuItem(2, menu4);
        final MenuItem element12 = TestUtil.createMenuItem(3, menu4);
        element10.setParent(null);
        element11.setParent(element10);
        element12.setParent(element11);
        /*---------------------------Menu4 with default structure and custom type of items----------------------------*/


        MenuItemsManager.addMenuItemsToAllMenusBySiteId(site.getSiteId(), 10, true);


        /*------------------------------------------Checking menu1 structure------------------------------------------*/
        Assert.assertEquals(2, menu1.getMenuItems().size());
        Assert.assertEquals(element1, menu1.getMenuItems().get(0));
        Assert.assertEquals("new element", 10, menu1.getMenuItems().get(1).getPageId().intValue());
        Assert.assertEquals("new element", true, menu1.getMenuItems().get(1).isIncludeInMenu());

        Assert.assertEquals(0, menu1.getMenuItems().get(1).getChildren().size());
        Assert.assertEquals(true, menu1.getMenuItems().contains(element1));
        Assert.assertEquals(1, element1.getChildren().size());
        Assert.assertEquals(true, element1.getChildren().contains(element2));
        Assert.assertEquals(1, element2.getChildren().size());
        Assert.assertEquals(true, element2.getChildren().contains(element3));
        Assert.assertEquals(0, element3.getChildren().size());
        /*------------------------------------------Checking menu1 structure------------------------------------------*/


        /*------------------------------------------Checking menu2 structure------------------------------------------*/
        Assert.assertEquals(2, menu2.getMenuItems().size());
        Assert.assertEquals(element4, menu2.getMenuItems().get(0));
        Assert.assertEquals("new element", 10, menu2.getMenuItems().get(1).getPageId().intValue());
        Assert.assertEquals("new element", true, menu2.getMenuItems().get(1).isIncludeInMenu());

        Assert.assertEquals(0, menu2.getMenuItems().get(1).getChildren().size());
        Assert.assertEquals(true, menu2.getMenuItems().contains(element4));
        Assert.assertEquals(1, element4.getChildren().size());
        Assert.assertEquals(true, element4.getChildren().contains(element5));
        Assert.assertEquals(1, element5.getChildren().size());
        Assert.assertEquals(true, element5.getChildren().contains(element6));
        Assert.assertEquals(0, element6.getChildren().size());
        /*------------------------------------------Checking menu2 structure------------------------------------------*/


        /*------------------------------------------Checking menu3 structure------------------------------------------*/
        Assert.assertEquals(2, menu3.getMenuItems().size());
        Assert.assertEquals(element7, menu3.getMenuItems().get(0));
        Assert.assertEquals("new element", 10, menu3.getMenuItems().get(1).getPageId().intValue());
        Assert.assertEquals("new element", true, menu3.getMenuItems().get(1).isIncludeInMenu());

        Assert.assertEquals(0, menu3.getMenuItems().get(1).getChildren().size());
        Assert.assertEquals(true, menu3.getMenuItems().contains(element7));
        Assert.assertEquals(1, element7.getChildren().size());
        Assert.assertEquals(true, element7.getChildren().contains(element8));
        Assert.assertEquals(1, element8.getChildren().size());
        Assert.assertEquals(true, element8.getChildren().contains(element9));
        Assert.assertEquals(0, element9.getChildren().size());
        /*------------------------------------------Checking menu3 structure------------------------------------------*/


        /*------------------------------------------Checking menu4 structure------------------------------------------*/
        Assert.assertEquals(2, menu4.getMenuItems().size());
        Assert.assertEquals(element10, menu4.getMenuItems().get(0));
        Assert.assertEquals(true, menu4.getMenuItems().contains(element10));
        Assert.assertEquals(1, element10.getChildren().size());
        Assert.assertEquals(true, element10.getChildren().contains(element11));
        Assert.assertEquals(1, element11.getChildren().size());
        Assert.assertEquals(true, element11.getChildren().contains(element12));
        Assert.assertEquals(0, element12.getChildren().size());

        Assert.assertEquals("new element", 10, menu4.getMenuItems().get(1).getPageId().intValue());
        Assert.assertEquals("new element", true, menu4.getMenuItems().get(1).isIncludeInMenu());
        /*------------------------------------------Checking menu4 structure------------------------------------------*/

        Assert.assertNotSame(menu1.getMenuItems().get(1), menu2.getMenuItems().get(1));
        Assert.assertNotSame(menu2.getMenuItems().get(1), menu3.getMenuItems().get(1));
    }

/*-----------------------------------------------Get Included Pages-----------------------------------------------*/

    @Test
    public void testGetIncludedPageIds() throws Exception {
        final DraftMenu menu = TestUtil.createMenu();
        final List<MenuItem> siteMenuItems = new ArrayList<MenuItem>();
        MenuItem siteMenuItem1 = new DraftMenuItem(1, true, menu);
        MenuItem siteMenuItem1_1 = new DraftMenuItem(2, false, menu);
        MenuItem siteMenuItem1_2 = new DraftMenuItem(3, true, menu);
        siteMenuItem1_1.setParent(siteMenuItem1);
        siteMenuItem1_2.setParent(siteMenuItem1);
        siteMenuItems.add(siteMenuItem1);

        final List<Integer> includedPages = MenuItemsManager.getIncludedPageIds(siteMenuItems);
        Assert.assertEquals(includedPages.size(), 2);
        Assert.assertTrue(includedPages.contains(1));
        Assert.assertTrue(includedPages.contains(3));
    }
    /*-----------------------------------------------Get Included Pages-----------------------------------------------*/


    @Test
    public void testCopyItemsAndAddThemToMenu() {
        final Site site = TestUtil.createSite();

        DraftMenu menu = TestUtil.createMenu(site);
        MenuItem menuItem1 = TestUtil.createMenuItem(1, menu);
        MenuItem menuItem2 = TestUtil.createMenuItem(2, menu);
        MenuItem menuItem3 = TestUtil.createMenuItem(1, menu);
        menuItem1.setParent(null);
        menuItem2.setParent(menuItem1);
        menuItem3.setParent(menuItem2);


        DraftMenu newMenu = TestUtil.createMenu();
        Assert.assertEquals(0, newMenu.getMenuItems().size());

        MenuItemsManager.copyItemsAndAddThemToMenu(newMenu, menu.getMenuItems());

        Assert.assertEquals(1, newMenu.getMenuItems().size());
        MenuItem newMenuItem1 = newMenu.getMenuItems().get(0);// Copy of menuItem1
        MenuItem newMenuItem2 = newMenuItem1.getChildren().get(0);// Copy of menuItem2
        MenuItem newMenuItem3 = newMenuItem2.getChildren().get(0);// Copy of menuItem3


        /*------------------------------------------------newMenuItem1------------------------------------------------*/
        Assert.assertNotSame("items not the same. its copy", newMenuItem1, menuItem1);
        Assert.assertEquals("but all fields are the same", newMenuItem1.getPageId(), menuItem1.getPageId());
        Assert.assertEquals("but all fields are the same", newMenuItem1.isIncludeInMenu(), menuItem1.isIncludeInMenu());
        Assert.assertEquals("but all fields are the same", newMenuItem1.getChildren().size(), menuItem1.getChildren().size());
        Assert.assertEquals("but all fields are the same", newMenuItem1.getDefaultPageId(), menuItem1.getDefaultPageId());
        /*------------------------------------------------newMenuItem1------------------------------------------------*/

        /*------------------------------------------------newMenuItem2------------------------------------------------*/
        Assert.assertNotSame("items not the same. its copy", newMenuItem2, menuItem2);
        Assert.assertEquals("but all fields are the same", newMenuItem2.getPageId(), menuItem2.getPageId());
        Assert.assertEquals("but all fields are the same", newMenuItem2.isIncludeInMenu(), menuItem2.isIncludeInMenu());
        Assert.assertEquals("but all fields are the same", newMenuItem2.getChildren().size(), menuItem2.getChildren().size());
        Assert.assertEquals("but all fields are the same", newMenuItem2.getDefaultPageId(), menuItem2.getDefaultPageId());
        /*------------------------------------------------newMenuItem2------------------------------------------------*/

        /*------------------------------------------------newMenuItem3------------------------------------------------*/
        Assert.assertNotSame("items not the same. its copy", newMenuItem3, menuItem3);
        Assert.assertEquals("but all fields are the same", newMenuItem3.getPageId(), menuItem3.getPageId());
        Assert.assertEquals("but all fields are the same", newMenuItem3.isIncludeInMenu(), menuItem3.isIncludeInMenu());
        Assert.assertEquals("but all fields are the same", newMenuItem3.getChildren().size(), menuItem3.getChildren().size());
        Assert.assertEquals("but all fields are the same", newMenuItem3.getDefaultPageId(), menuItem3.getDefaultPageId());
        /*------------------------------------------------newMenuItem3------------------------------------------------*/
    }

    @Test
    public void testExecute_withAddedCustomPages_onePageWasMovedToAnotherPosition() {
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
        menu.setSiteId(site.getSiteId());
        menu.setMenuStructure(MenuStructureType.OWN);
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

        MenuItemsManager.restoreDefaultStructure(defaultMenu, menu);

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
    public void testExecute_withAddedCustomPages_onePageWasRemoved() {
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
        // This page has been removed from structure
        //final NewMenuItem menuItem4 = TestUtil.createMenuItem(pageVersion4.getPage().getPageId(), menu, false);
        final MenuItem menuItem5 = TestUtil.createMenuItem(pageVersion5.getPage().getPageId(), menu, true);
        // New custom page
        final MenuItem menuItem6 = TestUtil.createMenuItem(pageVersion6.getPage().getPageId(), menu, true);
        menuItem6.setDefaultPageId(null);


        /*-----------------------------------------------New Structure------------------------------------------------*/
        menuItem1.setParent(null);
        menuItem2.setParent(null);
        menuItem3.setParent(null);

        menuItem5.setParent(menuItem2);
        // This newly created custom page added to menuItem2
        menuItem6.setParent(menuItem2);
        /*-----------------------------------------------New Structure------------------------------------------------*/

        MenuItemsManager.restoreDefaultStructure(defaultMenu, menu);

        /*--------------------------------Checking restored default structure for menu--------------------------------*/
        Assert.assertEquals(MenuStructureType.DEFAULT, menu.getMenuStructure());
        Assert.assertEquals(3, menu.getMenuItems().size());
        Assert.assertEquals(menuItem1, menu.getMenuItems().get(0));
        Assert.assertEquals(menuItem2, menu.getMenuItems().get(1));
        Assert.assertEquals(menuItem3, menu.getMenuItems().get(2));

        Assert.assertEquals(0, menuItem1.getChildren().size());


        Assert.assertEquals("Here is two children because new custom page has been added.", 2, menuItem2.getChildren().size());
        final MenuItem restoredItem = menuItem2.getChildren().get(0);
        Assert.assertEquals("This item restored on its old position = 0", pageVersion4.getPage().getPageId(), restoredItem.getPageId().intValue());
        Assert.assertEquals("And it has defaultPageId", pageVersion4.getPage().getPageId(), restoredItem.getDefaultPageId().intValue());
        Assert.assertEquals("And this new custom item moved to the second position", menuItem6, menuItem2.getChildren().get(1));

        Assert.assertEquals(0, menuItem3.getChildren().size());

        Assert.assertEquals("restored item has child as before", 1, restoredItem.getChildren().size());
        Assert.assertEquals(menuItem5, restoredItem.getChildren().get(0));


        Assert.assertEquals(0, menuItem5.getChildren().size());
        /*--------------------------------Checking restored default structure for menu--------------------------------*/
    }

    @Test
    public void testExecute_withAddedCustomPages_onePagesTypeChangedToCustom() {
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
        // This pages type changed to CUSTOM.
        final MenuItem menuItem4 = TestUtil.createMenuItem(pageVersion4.getPage().getPageId(), menu, false);
        menuItem4.setDefaultPageId(null);
        final MenuItem menuItem5 = TestUtil.createMenuItem(pageVersion5.getPage().getPageId(), menu, true);
        // New custom page
        final MenuItem menuItem6 = TestUtil.createMenuItem(pageVersion6.getPage().getPageId(), menu, true);
        menuItem6.setDefaultPageId(null);


        /*-----------------------------------------------New Structure------------------------------------------------*/
        menuItem1.setParent(null);
        menuItem2.setParent(null);
        menuItem3.setParent(null);

        menuItem4.setParent(menuItem2);
        // This newly created custom page added to menuItem2
        menuItem6.setParent(menuItem2);

        menuItem5.setParent(menuItem4);
        /*-----------------------------------------------New Structure------------------------------------------------*/

        MenuItemsManager.restoreDefaultStructure(defaultMenu, menu);

        /*--------------------------------Checking restored default structure for menu--------------------------------*/
        Assert.assertEquals(MenuStructureType.DEFAULT, menu.getMenuStructure());
        Assert.assertEquals(3, menu.getMenuItems().size());
        Assert.assertEquals(menuItem1, menu.getMenuItems().get(0));
        Assert.assertEquals(menuItem2, menu.getMenuItems().get(1));
        Assert.assertEquals(menuItem3, menu.getMenuItems().get(2));

        Assert.assertEquals(0, menuItem1.getChildren().size());


        Assert.assertEquals("Here is two children because new custom page has been added.", 3, menuItem2.getChildren().size());
        final MenuItem restoredItem = menuItem2.getChildren().get(0);
        Assert.assertEquals("This item restored on its old position = 0", pageVersion4.getPage().getPageId(), restoredItem.getPageId().intValue());
        Assert.assertEquals("And it has defaultPageId", pageVersion4.getPage().getPageId(), restoredItem.getDefaultPageId().intValue());
        Assert.assertEquals("Type of this item has been changed to custom so it moved as other custom pages",
                menuItem4, menuItem2.getChildren().get(1));
        Assert.assertEquals("This new custom item moved to the third position", menuItem6, menuItem2.getChildren().get(2));

        Assert.assertEquals(0, menuItem3.getChildren().size());

        Assert.assertEquals("restored item has child as before", 1, restoredItem.getChildren().size());
        Assert.assertEquals(menuItem5, restoredItem.getChildren().get(0));

        Assert.assertEquals("This item changed to custom so after normalization it has no children",
                0, menuItem4.getChildren().size());

        Assert.assertEquals(0, menuItem5.getChildren().size());
        /*--------------------------------Checking restored default structure for menu--------------------------------*/
    }
}
