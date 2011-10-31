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
package com.shroggle.entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import junit.framework.Assert;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;

import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class MenuItemTest {

    private DraftMenu menu = TestUtil.createMenu();

    /*--------------------------------------------------Constructors--------------------------------------------------*/

    @Test
    public void testCreate() {
        final MenuItem menuItem = new DraftMenuItem();
        Assert.assertEquals(null, menuItem.getPageId());
        Assert.assertEquals(null, menuItem.getDefaultPageId());
        Assert.assertEquals(false, menuItem.isIncludeInMenu());
        Assert.assertEquals(null, menuItem.getParent());
//        Assert.assertEquals(MenuItemType.DEFAULT, menuItem.getItemType());
        Assert.assertEquals(null, menuItem.getMenu());
        Assert.assertEquals(MenuUrlType.SITE_PAGE, menuItem.getUrlType());
        Assert.assertEquals(null, menuItem.getName());
        Assert.assertEquals(null, menuItem.getTitle());
        Assert.assertEquals(null, menuItem.getCustomUrl());
    }

    @Test
    public void testCreateWithPageIdAndIncludeInMenu() {
        final Site site = TestUtil.createSite();
        final DraftMenu menu = TestUtil.createMenu(site);
        final MenuItem menuItem = new DraftMenuItem(123, true, menu);
        Assert.assertEquals(123, menuItem.getPageId().intValue());
        Assert.assertEquals(123, menuItem.getDefaultPageId().intValue());
        Assert.assertEquals(menuItem.getPageId().intValue(), menuItem.getDefaultPageId().intValue());
        Assert.assertEquals(true, menuItem.isIncludeInMenu());
        Assert.assertEquals(null, menuItem.getParent());
//        Assert.assertEquals(MenuItemType.DEFAULT, menuItem.getItemType());
        Assert.assertEquals(menu, menuItem.getMenu());
        Assert.assertEquals(MenuUrlType.SITE_PAGE, menuItem.getUrlType());
        Assert.assertEquals(null, menuItem.getName());
        Assert.assertEquals(null, menuItem.getTitle());
        Assert.assertEquals(null, menuItem.getCustomUrl());
    }
    /*--------------------------------------------------Constructors--------------------------------------------------*/


    /*---------------------------------------------------Set Parent---------------------------------------------------*/

    @Test
    public void testSetParent() {
        final MenuItem parent = TestUtil.createMenuItem(menu);
        final MenuItem child = TestUtil.createMenuItem(menu);

        child.setParent(parent);

        Assert.assertEquals(parent, child.getParent());
        Assert.assertEquals(1, parent.getChildren().size());
        Assert.assertEquals(child, parent.getChildren().get(0));
    }

    @Test
    public void testSetParent_setElementToHimselfAsParent() {
        final MenuItem parent = TestUtil.createMenuItem(menu);

        parent.setParent(parent);

        Assert.assertNotSame(parent, parent.getParent());
        Assert.assertEquals(0, parent.getChildren().size());
        Assert.assertNull(parent.getParent());
    }

    @Test
    public void testSetParent_setElementToHimselfAsParent_forNotSameElementsButWithSameNotNullIds() {
        final MenuItem parent = TestUtil.createMenuItem(menu);
        parent.setId(11);
        final MenuItem newParent = TestUtil.createMenuItem(menu);
        newParent.setId(11);

        parent.setParent(newParent);

        Assert.assertNotSame(newParent, parent.getParent());
        Assert.assertNull(parent.getParent());
        Assert.assertNull(newParent.getParent());
        Assert.assertEquals(0, newParent.getChildren().size());
        Assert.assertEquals(0, parent.getChildren().size());
    }

    @Test
    public void testSetParent_withOldParent() {
        final MenuItem parent = TestUtil.createMenuItem(menu);
        final MenuItem child = TestUtil.createMenuItem(menu);

        child.setParent(parent);

        Assert.assertEquals(parent, child.getParent());
        Assert.assertEquals(1, parent.getChildren().size());
        Assert.assertEquals(child, parent.getChildren().get(0));

        // Adding new parent.
        final MenuItem newParent = TestUtil.createMenuItem(menu);
        child.setParent(newParent);

        Assert.assertEquals("Child has new parent.", newParent, child.getParent());
        Assert.assertEquals("New parent has one child.", 1, newParent.getChildren().size());
        Assert.assertEquals("Child from newParent children = child", child, newParent.getChildren().get(0));
        Assert.assertEquals("Old parent has no children now.", 0, parent.getChildren().size());
    }

    @Test
    public void testSetParent_makeParentFromOldChildAndChildFromOldParent_ChangeTheirPlaces() {
        final MenuItem parent = TestUtil.createMenuItem(menu);
        final MenuItem child = TestUtil.createMenuItem(menu);

        child.setParent(parent);

        Assert.assertEquals(parent, child.getParent());
        Assert.assertEquals(1, parent.getChildren().size());
        Assert.assertEquals(child, parent.getChildren().get(0));

        parent.setParent(child);

        // We can`t do it in current logic. So structure must be as before method execution
        Assert.assertEquals(parent, child.getParent());
        Assert.assertEquals(1, parent.getChildren().size());
        Assert.assertEquals(child, parent.getChildren().get(0));
    }

    @Test
    public void testSetParentNull() {
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem parent = TestUtil.createMenuItem(menu);
        parent.setMenu(menu);
        final MenuItem child = TestUtil.createMenuItem(menu);
        child.setMenu(menu);

        child.setParent(parent);

        Assert.assertEquals("Now our menu has no items", 0, menu.getMenuItems().size());
        Assert.assertEquals(parent, child.getParent());
        Assert.assertEquals(1, parent.getChildren().size());
        Assert.assertEquals(child, parent.getChildren().get(0));

        // Adding new parent.
        child.setParent(null);

        Assert.assertEquals("Child has new parent.", null, child.getParent());
        Assert.assertEquals("Setting parent to null means that this is root level element and it " +
                "should be in the menuItems in the menu", 1, menu.getMenuItems().size());
        Assert.assertEquals("Setting parent to null means that this is root level element and it " +
                "should be in the menuItems in the menu", child, menu.getMenuItems().get(0));
        Assert.assertEquals("Old parent has no children now.", 0, parent.getChildren().size());
    }

    @Test(expected = IllegalStateException.class)
    public void testSetParentNull_withoutMenu() {
        final MenuItem parent = TestUtil.createMenuItem();
        final MenuItem child = TestUtil.createMenuItem();

        child.setParent(parent);

        Assert.assertEquals(parent, child.getParent());
        Assert.assertEquals(1, parent.getChildren().size());
        Assert.assertEquals(child, parent.getChildren().get(0));


        // Adding new parent.
        child.setParent(null);
    }

    @Test
    public void testSetParent_bigStructure() {
        menu = TestUtil.createMenu();
        final MenuItem element1 = TestUtil.createMenuItem(1, menu);
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(4, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(6, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(8, menu);


        element1.setParent(null);
        element2.setParent(element1);

        element3.setParent(element2);
        element7.setParent(element2);
        element8.setParent(element2);

        element4.setParent(element3);

        element5.setParent(element4);
        element6.setParent(element4);

        /*-----------------------------------------Checking initial structure-----------------------------------------*/
        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(true, menu.getMenuItems().contains(element1));

        Assert.assertEquals(null, element1.getParent());

        Assert.assertEquals(element1, element2.getParent());

        Assert.assertEquals(element2, element3.getParent());
        Assert.assertEquals(element2, element7.getParent());
        Assert.assertEquals(element2, element8.getParent());

        Assert.assertEquals(element3, element4.getParent());

        Assert.assertEquals(element4, element5.getParent());
        Assert.assertEquals(element4, element6.getParent());


        Assert.assertEquals(1, element1.getChildren().size());
        Assert.assertEquals(true, element1.getChildren().contains(element2));

        Assert.assertEquals(3, element2.getChildren().size());
        Assert.assertEquals(true, element2.getChildren().contains(element3));
        Assert.assertEquals(true, element2.getChildren().contains(element7));
        Assert.assertEquals(true, element2.getChildren().contains(element8));

        Assert.assertEquals(1, element3.getChildren().size());
        Assert.assertEquals(true, element3.getChildren().contains(element4));

        Assert.assertEquals(2, element4.getChildren().size());
        Assert.assertEquals(true, element4.getChildren().contains(element5));
        Assert.assertEquals(true, element4.getChildren().contains(element6));

        Assert.assertEquals(0, element5.getChildren().size());
        Assert.assertEquals(0, element6.getChildren().size());
        Assert.assertEquals(0, element7.getChildren().size());
        Assert.assertEquals(0, element8.getChildren().size());
        /*-----------------------------------------Checking initial structure-----------------------------------------*/

        //Changing tree structure
        element4.setParent(element1);

        /*-------------------------------------------Checking new structure-------------------------------------------*/
        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(true, menu.getMenuItems().contains(element1));

        Assert.assertEquals(null, element1.getParent());

        Assert.assertEquals(element1, element2.getParent());
        Assert.assertEquals(element1, element4.getParent());

        Assert.assertEquals(element2, element3.getParent());
        Assert.assertEquals(element2, element7.getParent());
        Assert.assertEquals(element2, element8.getParent());

        Assert.assertEquals(element4, element5.getParent());
        Assert.assertEquals(element4, element6.getParent());


        Assert.assertEquals(2, element1.getChildren().size());
        Assert.assertEquals(true, element1.getChildren().contains(element2));
        Assert.assertEquals(true, element1.getChildren().contains(element4));

        Assert.assertEquals(3, element2.getChildren().size());
        Assert.assertEquals(true, element2.getChildren().contains(element3));
        Assert.assertEquals(true, element2.getChildren().contains(element7));
        Assert.assertEquals(true, element2.getChildren().contains(element8));

        Assert.assertEquals(0, element3.getChildren().size());

        Assert.assertEquals(2, element4.getChildren().size());
        Assert.assertEquals(true, element4.getChildren().contains(element5));
        Assert.assertEquals(true, element4.getChildren().contains(element6));

        Assert.assertEquals(0, element5.getChildren().size());
        Assert.assertEquals(0, element6.getChildren().size());
        Assert.assertEquals(0, element7.getChildren().size());
        Assert.assertEquals(0, element8.getChildren().size());
        /*-------------------------------------------Checking new structure-------------------------------------------*/
    }

    @Test
    public void testSetParent_bigStructure_moveToRootElement() {
        menu = TestUtil.createMenu();
        final MenuItem element1 = TestUtil.createMenuItem(1, menu);
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(4, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(6, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(8, menu);


        element1.setParent(null);
        element2.setParent(element1);

        element3.setParent(element2);
        element7.setParent(element2);
        element8.setParent(element2);

        element4.setParent(element3);

        element5.setParent(element4);
        element6.setParent(element4);

        /*-----------------------------------------Checking initial structure-----------------------------------------*/
        Assert.assertEquals(1, menu.getMenuItems().size());
        Assert.assertEquals(true, menu.getMenuItems().contains(element1));

        Assert.assertEquals(null, element1.getParent());

        Assert.assertEquals(element1, element2.getParent());

        Assert.assertEquals(element2, element3.getParent());
        Assert.assertEquals(element2, element7.getParent());
        Assert.assertEquals(element2, element8.getParent());

        Assert.assertEquals(element3, element4.getParent());

        Assert.assertEquals(element4, element5.getParent());
        Assert.assertEquals(element4, element6.getParent());


        Assert.assertEquals(1, element1.getChildren().size());
        Assert.assertEquals(true, element1.getChildren().contains(element2));

        Assert.assertEquals(3, element2.getChildren().size());
        Assert.assertEquals(true, element2.getChildren().contains(element3));
        Assert.assertEquals(true, element2.getChildren().contains(element7));
        Assert.assertEquals(true, element2.getChildren().contains(element8));

        Assert.assertEquals(1, element3.getChildren().size());
        Assert.assertEquals(true, element3.getChildren().contains(element4));

        Assert.assertEquals(2, element4.getChildren().size());
        Assert.assertEquals(true, element4.getChildren().contains(element5));
        Assert.assertEquals(true, element4.getChildren().contains(element6));

        Assert.assertEquals(0, element5.getChildren().size());
        Assert.assertEquals(0, element6.getChildren().size());
        Assert.assertEquals(0, element7.getChildren().size());
        Assert.assertEquals(0, element8.getChildren().size());
        /*-----------------------------------------Checking initial structure-----------------------------------------*/

        //Changing tree structure
        element4.setParent(null);

        /*-------------------------------------------Checking new structure-------------------------------------------*/
        Assert.assertEquals(2, menu.getMenuItems().size());
        Assert.assertEquals(true, menu.getMenuItems().contains(element1));
        Assert.assertEquals(true, menu.getMenuItems().contains(element4));

        Assert.assertEquals(null, element1.getParent());
        Assert.assertEquals(null, element4.getParent());

        Assert.assertEquals(element1, element2.getParent());

        Assert.assertEquals(element2, element3.getParent());
        Assert.assertEquals(element2, element7.getParent());
        Assert.assertEquals(element2, element8.getParent());

        Assert.assertEquals(element4, element5.getParent());
        Assert.assertEquals(element4, element6.getParent());


        Assert.assertEquals(1, element1.getChildren().size());
        Assert.assertEquals(true, element1.getChildren().contains(element2));

        Assert.assertEquals(3, element2.getChildren().size());
        Assert.assertEquals(true, element2.getChildren().contains(element3));
        Assert.assertEquals(true, element2.getChildren().contains(element7));
        Assert.assertEquals(true, element2.getChildren().contains(element8));

        Assert.assertEquals(0, element3.getChildren().size());

        Assert.assertEquals(2, element4.getChildren().size());
        Assert.assertEquals(true, element4.getChildren().contains(element5));
        Assert.assertEquals(true, element4.getChildren().contains(element6));

        Assert.assertEquals(0, element5.getChildren().size());
        Assert.assertEquals(0, element6.getChildren().size());
        Assert.assertEquals(0, element7.getChildren().size());
        Assert.assertEquals(0, element8.getChildren().size());
        /*-------------------------------------------Checking new structure-------------------------------------------*/
    }

    @Test
    public void testSetParent_bigStructure_withoutChangesBecauseWeTryingSetParentToChild() {
        final MenuItem element1 = TestUtil.createMenuItem(1, menu);
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(4, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(6, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(8, menu);


        element2.setParent(element1);

        element3.setParent(element2);
        element7.setParent(element2);
        element8.setParent(element2);

        element4.setParent(element3);

        element5.setParent(element4);
        element6.setParent(element4);

        /*-----------------------------------------Checking initial structure-----------------------------------------*/
        Assert.assertEquals(null, element1.getParent());

        Assert.assertEquals(element1, element2.getParent());

        Assert.assertEquals(element2, element3.getParent());
        Assert.assertEquals(element2, element7.getParent());
        Assert.assertEquals(element2, element8.getParent());

        Assert.assertEquals(element3, element4.getParent());

        Assert.assertEquals(element4, element5.getParent());
        Assert.assertEquals(element4, element6.getParent());


        Assert.assertEquals(1, element1.getChildren().size());
        Assert.assertEquals(true, element1.getChildren().contains(element2));

        Assert.assertEquals(3, element2.getChildren().size());
        Assert.assertEquals(true, element2.getChildren().contains(element3));
        Assert.assertEquals(true, element2.getChildren().contains(element7));
        Assert.assertEquals(true, element2.getChildren().contains(element8));

        Assert.assertEquals(1, element3.getChildren().size());
        Assert.assertEquals(true, element3.getChildren().contains(element4));

        Assert.assertEquals(2, element4.getChildren().size());
        Assert.assertEquals(true, element4.getChildren().contains(element5));
        Assert.assertEquals(true, element4.getChildren().contains(element6));

        Assert.assertEquals(0, element5.getChildren().size());
        Assert.assertEquals(0, element6.getChildren().size());
        Assert.assertEquals(0, element7.getChildren().size());
        Assert.assertEquals(0, element8.getChildren().size());
        /*-----------------------------------------Checking initial structure-----------------------------------------*/

        //Changing tree structure
        element2.setParent(element3);

        /*-------------------------------------------Checking new structure-------------------------------------------*/
        // New structure must be as old one. Because we cant move parent to his child in current logic.
        Assert.assertEquals(null, element1.getParent());

        Assert.assertEquals(element1, element2.getParent());

        Assert.assertEquals(element2, element3.getParent());
        Assert.assertEquals(element2, element7.getParent());
        Assert.assertEquals(element2, element8.getParent());

        Assert.assertEquals(element3, element4.getParent());

        Assert.assertEquals(element4, element5.getParent());
        Assert.assertEquals(element4, element6.getParent());


        Assert.assertEquals(1, element1.getChildren().size());
        Assert.assertEquals(true, element1.getChildren().contains(element2));

        Assert.assertEquals(3, element2.getChildren().size());
        Assert.assertEquals(true, element2.getChildren().contains(element3));
        Assert.assertEquals(true, element2.getChildren().contains(element7));
        Assert.assertEquals(true, element2.getChildren().contains(element8));

        Assert.assertEquals(1, element3.getChildren().size());
        Assert.assertEquals(true, element3.getChildren().contains(element4));

        Assert.assertEquals(2, element4.getChildren().size());
        Assert.assertEquals(true, element4.getChildren().contains(element5));
        Assert.assertEquals(true, element4.getChildren().contains(element6));

        Assert.assertEquals(0, element5.getChildren().size());
        Assert.assertEquals(0, element6.getChildren().size());
        Assert.assertEquals(0, element7.getChildren().size());
        Assert.assertEquals(0, element8.getChildren().size());
        /*-------------------------------------------Checking new structure-------------------------------------------*/
    }
    /*---------------------------------------------------Set Parent---------------------------------------------------*/


    /*---------------------------------------------------Has Child----------------------------------------------------*/

    @Test
    public void testHasChild() {
        final MenuItem element1 = TestUtil.createMenuItem(1, menu);
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(4, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(6, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(8, menu);


        element2.setParent(element1);

        element3.setParent(element2);
        element7.setParent(element2);
        element8.setParent(element2);

        element4.setParent(element3);

        element5.setParent(element4);
        element6.setParent(element4);

        /*-----------------------------------------Checking initial structure-----------------------------------------*/
        Assert.assertEquals(null, element1.getParent());

        Assert.assertEquals(element1, element2.getParent());

        Assert.assertEquals(element2, element3.getParent());
        Assert.assertEquals(element2, element7.getParent());
        Assert.assertEquals(element2, element8.getParent());

        Assert.assertEquals(element3, element4.getParent());

        Assert.assertEquals(element4, element5.getParent());
        Assert.assertEquals(element4, element6.getParent());


        Assert.assertEquals(1, element1.getChildren().size());
        Assert.assertEquals(true, element1.getChildren().contains(element2));

        Assert.assertEquals(3, element2.getChildren().size());
        Assert.assertEquals(true, element2.getChildren().contains(element3));
        Assert.assertEquals(true, element2.getChildren().contains(element7));
        Assert.assertEquals(true, element2.getChildren().contains(element8));

        Assert.assertEquals(1, element3.getChildren().size());
        Assert.assertEquals(true, element3.getChildren().contains(element4));

        Assert.assertEquals(2, element4.getChildren().size());
        Assert.assertEquals(true, element4.getChildren().contains(element5));
        Assert.assertEquals(true, element4.getChildren().contains(element6));

        Assert.assertEquals(0, element5.getChildren().size());
        Assert.assertEquals(0, element6.getChildren().size());
        Assert.assertEquals(0, element7.getChildren().size());
        Assert.assertEquals(0, element8.getChildren().size());
        /*-----------------------------------------Checking initial structure-----------------------------------------*/

        /*-------------------------------------------------Element 1--------------------------------------------------*/
        Assert.assertFalse(element1.hasChild(element1));

        Assert.assertTrue(element1.hasChild(element2));
        Assert.assertTrue(element1.hasChild(element3));
        Assert.assertTrue(element1.hasChild(element4));
        Assert.assertTrue(element1.hasChild(element5));
        Assert.assertTrue(element1.hasChild(element6));
        Assert.assertTrue(element1.hasChild(element7));
        Assert.assertTrue(element1.hasChild(element8));
        /*-------------------------------------------------Element 1--------------------------------------------------*/

        /*-------------------------------------------------Element 2--------------------------------------------------*/
        Assert.assertFalse(element2.hasChild(element1));
        Assert.assertFalse(element2.hasChild(element2));

        Assert.assertTrue(element2.hasChild(element3));
        Assert.assertTrue(element2.hasChild(element4));
        Assert.assertTrue(element2.hasChild(element5));
        Assert.assertTrue(element2.hasChild(element6));
        Assert.assertTrue(element2.hasChild(element7));
        Assert.assertTrue(element2.hasChild(element8));
        /*-------------------------------------------------Element 2--------------------------------------------------*/

        /*-------------------------------------------------Element 3--------------------------------------------------*/
        Assert.assertFalse(element3.hasChild(element1));
        Assert.assertFalse(element3.hasChild(element2));
        Assert.assertFalse(element3.hasChild(element3));
        Assert.assertFalse(element3.hasChild(element7));
        Assert.assertFalse(element3.hasChild(element8));

        Assert.assertTrue(element3.hasChild(element4));
        Assert.assertTrue(element3.hasChild(element5));
        Assert.assertTrue(element3.hasChild(element6));
        /*-------------------------------------------------Element 3--------------------------------------------------*/

        /*-------------------------------------------------Element 4--------------------------------------------------*/
        Assert.assertFalse(element4.hasChild(element1));
        Assert.assertFalse(element4.hasChild(element2));
        Assert.assertFalse(element4.hasChild(element3));
        Assert.assertFalse(element4.hasChild(element4));
        Assert.assertFalse(element4.hasChild(element7));
        Assert.assertFalse(element4.hasChild(element8));

        Assert.assertTrue(element4.hasChild(element5));
        Assert.assertTrue(element4.hasChild(element6));
        /*-------------------------------------------------Element 4--------------------------------------------------*/

        /*-------------------------------------------------Element 5--------------------------------------------------*/
        Assert.assertFalse(element5.hasChild(element1));
        Assert.assertFalse(element5.hasChild(element2));
        Assert.assertFalse(element5.hasChild(element3));
        Assert.assertFalse(element5.hasChild(element4));
        Assert.assertFalse(element5.hasChild(element5));
        Assert.assertFalse(element5.hasChild(element6));
        Assert.assertFalse(element5.hasChild(element7));
        Assert.assertFalse(element5.hasChild(element8));
        /*-------------------------------------------------Element 5--------------------------------------------------*/

        /*-------------------------------------------------Element 6--------------------------------------------------*/
        Assert.assertFalse(element6.hasChild(element1));
        Assert.assertFalse(element6.hasChild(element2));
        Assert.assertFalse(element6.hasChild(element3));
        Assert.assertFalse(element6.hasChild(element4));
        Assert.assertFalse(element6.hasChild(element5));
        Assert.assertFalse(element6.hasChild(element6));
        Assert.assertFalse(element6.hasChild(element7));
        Assert.assertFalse(element6.hasChild(element8));
        /*-------------------------------------------------Element 6--------------------------------------------------*/

        /*-------------------------------------------------Element 7--------------------------------------------------*/
        Assert.assertFalse(element7.hasChild(element1));
        Assert.assertFalse(element7.hasChild(element2));
        Assert.assertFalse(element7.hasChild(element3));
        Assert.assertFalse(element7.hasChild(element4));
        Assert.assertFalse(element7.hasChild(element5));
        Assert.assertFalse(element7.hasChild(element6));
        Assert.assertFalse(element7.hasChild(element7));
        Assert.assertFalse(element7.hasChild(element8));
        /*-------------------------------------------------Element 7--------------------------------------------------*/

        /*-------------------------------------------------Element 8--------------------------------------------------*/
        Assert.assertFalse(element8.hasChild(element1));
        Assert.assertFalse(element8.hasChild(element2));
        Assert.assertFalse(element8.hasChild(element3));
        Assert.assertFalse(element8.hasChild(element4));
        Assert.assertFalse(element8.hasChild(element5));
        Assert.assertFalse(element8.hasChild(element6));
        Assert.assertFalse(element8.hasChild(element7));
        Assert.assertFalse(element8.hasChild(element8));
        /*-------------------------------------------------Element 8--------------------------------------------------*/
    }
    /*---------------------------------------------------Has Child----------------------------------------------------*/

    /*------------------------------------------------Elements Equals-------------------------------------------------*/

    @Test
    public void testElementsEquals() {
        Assert.assertTrue(MenuItem.elementsEquals(null, null));

        final MenuItem element1 = TestUtil.createMenuItem(1, menu);
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);

        Assert.assertFalse(MenuItem.elementsEquals(element1, element2));
        Assert.assertFalse(MenuItem.elementsEquals(null, element2));
        Assert.assertFalse(MenuItem.elementsEquals(element1, null));

        element1.setId(11);
        element2.setId(11);
        element1.setIncludeInMenu(false);
        element2.setIncludeInMenu(true);
        Assert.assertTrue("Elements are equal if they have same ids", MenuItem.elementsEquals(element1, element2));
    }
    /*------------------------------------------------Elements Equals-------------------------------------------------*/

    @Test
    public void testGetChildren_checkPosition() {
        final MenuItem element1 = TestUtil.createMenuItem(1, menu);
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(4, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(6, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(8, menu);


        element1.setParent(null);

        element2.setParent(element1);
        element3.setParent(element1);
        element4.setParent(element1);
        element5.setParent(element1);
        element6.setParent(element1);
        element7.setParent(element1);
        element8.setParent(element1);

        element2.setPosition(6);
        element3.setPosition(4);
        element4.setPosition(3);
        element5.setPosition(5);
        element6.setPosition(2);
        element7.setPosition(1);
        element8.setPosition(0);

        final List<MenuItem> children = element1.getChildren();
        Assert.assertEquals(7, children.size());
        Assert.assertEquals(0, children.get(0).getPosition());
        Assert.assertEquals(1, children.get(1).getPosition());
        Assert.assertEquals(2, children.get(2).getPosition());
        Assert.assertEquals(3, children.get(3).getPosition());
        Assert.assertEquals(4, children.get(4).getPosition());
        Assert.assertEquals(5, children.get(5).getPosition());
        Assert.assertEquals(6, children.get(6).getPosition());

        Assert.assertEquals(element8, children.get(0));
        Assert.assertEquals(element7, children.get(1));
        Assert.assertEquals(element6, children.get(2));
        Assert.assertEquals(element4, children.get(3));
        Assert.assertEquals(element3, children.get(4));
        Assert.assertEquals(element5, children.get(5));
        Assert.assertEquals(element2, children.get(6));
    }

    @Test
    public void testGetChildrenFromMenu_checkPosition() {
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(4, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(6, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(8, menu);


        element2.setParent(null);
        element3.setParent(null);
        element4.setParent(null);
        element5.setParent(null);
        element6.setParent(null);
        element7.setParent(null);
        element8.setParent(null);

        element2.setPosition(6);
        element3.setPosition(4);
        element4.setPosition(3);
        element5.setPosition(5);
        element6.setPosition(2);
        element7.setPosition(1);
        element8.setPosition(0);

        final List<MenuItem> children = menu.getMenuItems();
        Assert.assertEquals(7, children.size());
        Assert.assertEquals(0, children.get(0).getPosition());
        Assert.assertEquals(1, children.get(1).getPosition());
        Assert.assertEquals(2, children.get(2).getPosition());
        Assert.assertEquals(3, children.get(3).getPosition());
        Assert.assertEquals(4, children.get(4).getPosition());
        Assert.assertEquals(5, children.get(5).getPosition());
        Assert.assertEquals(6, children.get(6).getPosition());

        Assert.assertEquals(element8, children.get(0));
        Assert.assertEquals(element7, children.get(1));
        Assert.assertEquals(element6, children.get(2));
        Assert.assertEquals(element4, children.get(3));
        Assert.assertEquals(element3, children.get(4));
        Assert.assertEquals(element5, children.get(5));
        Assert.assertEquals(element2, children.get(6));
    }

    @Test
    public void testAddChild() {
        final MenuItem element1 = TestUtil.createMenuItem(2, menu);
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(4, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(6, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(8, menu);

        element2.setPosition(1);
        element3.setPosition(6);
        element4.setPosition(7);
        element5.setPosition(2);
        element6.setPosition(5);
        element7.setPosition(3);
        element8.setPosition(4);

        element1.addChild(element2);
        element1.addChild(element3);
        element1.addChild(element4);
        element1.addChild(element5);
        element1.addChild(element6);
        element1.addChild(element7);
        element1.addChild(element8);

        Assert.assertEquals(element2, element1.getChildren().get(0));
        Assert.assertEquals(element3, element1.getChildren().get(1));
        Assert.assertEquals(element4, element1.getChildren().get(2));
        Assert.assertEquals(element5, element1.getChildren().get(3));
        Assert.assertEquals(element6, element1.getChildren().get(4));
        Assert.assertEquals(element7, element1.getChildren().get(5));
        Assert.assertEquals(element8, element1.getChildren().get(6));

        Assert.assertEquals(0, element1.getChildren().get(0).getPosition());
        Assert.assertEquals(1, element1.getChildren().get(1).getPosition());
        Assert.assertEquals(2, element1.getChildren().get(2).getPosition());
        Assert.assertEquals(3, element1.getChildren().get(3).getPosition());
        Assert.assertEquals(4, element1.getChildren().get(4).getPosition());
        Assert.assertEquals(5, element1.getChildren().get(5).getPosition());
        Assert.assertEquals(6, element1.getChildren().get(6).getPosition());
    }

    @Test
    public void testAddChild_menu() {
        menu = TestUtil.createMenu();
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(4, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(6, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(8, menu);

        element2.setPosition(1);
        element3.setPosition(6);
        element4.setPosition(7);
        element5.setPosition(2);
        element6.setPosition(5);
        element7.setPosition(3);
        element8.setPosition(4);

        menu.addChild(element2);
        menu.addChild(element3);
        menu.addChild(element4);
        menu.addChild(element5);
        menu.addChild(element6);
        menu.addChild(element7);
        menu.addChild(element8);

        Assert.assertEquals(element2, menu.getMenuItems().get(0));
        Assert.assertEquals(element3, menu.getMenuItems().get(1));
        Assert.assertEquals(element4, menu.getMenuItems().get(2));
        Assert.assertEquals(element5, menu.getMenuItems().get(3));
        Assert.assertEquals(element6, menu.getMenuItems().get(4));
        Assert.assertEquals(element7, menu.getMenuItems().get(5));
        Assert.assertEquals(element8, menu.getMenuItems().get(6));

        Assert.assertEquals(0, menu.getMenuItems().get(0).getPosition());
        Assert.assertEquals(1, menu.getMenuItems().get(1).getPosition());
        Assert.assertEquals(2, menu.getMenuItems().get(2).getPosition());
        Assert.assertEquals(3, menu.getMenuItems().get(3).getPosition());
        Assert.assertEquals(4, menu.getMenuItems().get(4).getPosition());
        Assert.assertEquals(5, menu.getMenuItems().get(5).getPosition());
        Assert.assertEquals(6, menu.getMenuItems().get(6).getPosition());
    }

    /*-----------------------------------Move To Position for NewMenuItem as parent-----------------------------------*/
    @Test
    public void testMoveToPosition_toFirstPosition_forNewMenuItem() {
        final MenuItem element1 = TestUtil.createMenuItem(2, menu);
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(4, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(6, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(8, menu);

        element2.setParent(element1);
        element3.setParent(element1);
        element4.setParent(element1);
        element5.setParent(element1);
        element6.setParent(element1);
        element7.setParent(element1);
        element8.setParent(element1);

        Assert.assertEquals(0, element2.getPosition());
        Assert.assertEquals(1, element3.getPosition());
        Assert.assertEquals(2, element4.getPosition());
        Assert.assertEquals(3, element5.getPosition());
        Assert.assertEquals(4, element6.getPosition());
        Assert.assertEquals(5, element7.getPosition());
        Assert.assertEquals(6, element8.getPosition());

        element8.moveToPosition(0);

        Assert.assertEquals(0, element8.getPosition());
        Assert.assertEquals(1, element2.getPosition());
        Assert.assertEquals(2, element3.getPosition());
        Assert.assertEquals(3, element4.getPosition());
        Assert.assertEquals(4, element5.getPosition());
        Assert.assertEquals(5, element6.getPosition());
        Assert.assertEquals(6, element7.getPosition());
    }

    @Test
    public void testMoveToPosition_toLastPosition_forNewMenuItem() {
        final MenuItem element1 = TestUtil.createMenuItem(2, menu);
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(4, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(6, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(8, menu);

        element2.setParent(element1);
        element3.setParent(element1);
        element4.setParent(element1);
        element5.setParent(element1);
        element6.setParent(element1);
        element7.setParent(element1);
        element8.setParent(element1);

        Assert.assertEquals(0, element2.getPosition());
        Assert.assertEquals(1, element3.getPosition());
        Assert.assertEquals(2, element4.getPosition());
        Assert.assertEquals(3, element5.getPosition());
        Assert.assertEquals(4, element6.getPosition());
        Assert.assertEquals(5, element7.getPosition());
        Assert.assertEquals(6, element8.getPosition());


        element2.moveToPosition(6);


        Assert.assertEquals(0, element3.getPosition());
        Assert.assertEquals(1, element4.getPosition());
        Assert.assertEquals(2, element5.getPosition());
        Assert.assertEquals(3, element6.getPosition());
        Assert.assertEquals(4, element7.getPosition());
        Assert.assertEquals(5, element8.getPosition());
        Assert.assertEquals(6, element2.getPosition());
    }

    @Test
    public void testMoveToPosition_toLastPosition_usingNullAsDesiredPosition_forNewMenuItem() {
        final MenuItem element1 = TestUtil.createMenuItem(2, menu);
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(4, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(6, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(8, menu);

        element2.setParent(element1);
        element3.setParent(element1);
        element4.setParent(element1);
        element5.setParent(element1);
        element6.setParent(element1);
        element7.setParent(element1);
        element8.setParent(element1);

        Assert.assertEquals(0, element2.getPosition());
        Assert.assertEquals(1, element3.getPosition());
        Assert.assertEquals(2, element4.getPosition());
        Assert.assertEquals(3, element5.getPosition());
        Assert.assertEquals(4, element6.getPosition());
        Assert.assertEquals(5, element7.getPosition());
        Assert.assertEquals(6, element8.getPosition());


        element2.moveToPosition(null);


        Assert.assertEquals(0, element3.getPosition());
        Assert.assertEquals(1, element4.getPosition());
        Assert.assertEquals(2, element5.getPosition());
        Assert.assertEquals(3, element6.getPosition());
        Assert.assertEquals(4, element7.getPosition());
        Assert.assertEquals(5, element8.getPosition());
        Assert.assertEquals(6, element2.getPosition());
    }

    @Test
    public void testMoveToPosition_toLastPosition_usingNegativeAsDesiredPosition_forNewMenuItem() {
        final MenuItem element1 = TestUtil.createMenuItem(2, menu);
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(4, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(6, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(8, menu);

        element2.setParent(element1);
        element3.setParent(element1);
        element4.setParent(element1);
        element5.setParent(element1);
        element6.setParent(element1);
        element7.setParent(element1);
        element8.setParent(element1);

        Assert.assertEquals(0, element2.getPosition());
        Assert.assertEquals(1, element3.getPosition());
        Assert.assertEquals(2, element4.getPosition());
        Assert.assertEquals(3, element5.getPosition());
        Assert.assertEquals(4, element6.getPosition());
        Assert.assertEquals(5, element7.getPosition());
        Assert.assertEquals(6, element8.getPosition());


        element2.moveToPosition(-1);


        Assert.assertEquals(0, element3.getPosition());
        Assert.assertEquals(1, element4.getPosition());
        Assert.assertEquals(2, element5.getPosition());
        Assert.assertEquals(3, element6.getPosition());
        Assert.assertEquals(4, element7.getPosition());
        Assert.assertEquals(5, element8.getPosition());
        Assert.assertEquals(6, element2.getPosition());
    }

    @Test
    public void testMoveToPosition_toMiddlePosition_fromFirst_forNewMenuItem() {
        final MenuItem element1 = TestUtil.createMenuItem(2, menu);
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(4, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(6, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(8, menu);

        element2.setParent(element1);
        element3.setParent(element1);
        element4.setParent(element1);
        element5.setParent(element1);
        element6.setParent(element1);
        element7.setParent(element1);
        element8.setParent(element1);

        Assert.assertEquals(0, element2.getPosition());
        Assert.assertEquals(1, element3.getPosition());
        Assert.assertEquals(2, element4.getPosition());
        Assert.assertEquals(3, element5.getPosition());
        Assert.assertEquals(4, element6.getPosition());
        Assert.assertEquals(5, element7.getPosition());
        Assert.assertEquals(6, element8.getPosition());


        element2.moveToPosition(3);


        Assert.assertEquals(0, element3.getPosition());
        Assert.assertEquals(1, element4.getPosition());
        Assert.assertEquals(2, element5.getPosition());
        Assert.assertEquals(3, element2.getPosition());
        Assert.assertEquals(4, element6.getPosition());
        Assert.assertEquals(5, element7.getPosition());
        Assert.assertEquals(6, element8.getPosition());
    }

    @Test
    public void testMoveToPosition_toMiddlePosition_fromLast_forNewMenuItem() {
        final MenuItem element1 = TestUtil.createMenuItem(2, menu);
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(4, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(6, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(8, menu);

        element2.setParent(element1);
        element3.setParent(element1);
        element4.setParent(element1);
        element5.setParent(element1);
        element6.setParent(element1);
        element7.setParent(element1);
        element8.setParent(element1);

        Assert.assertEquals(0, element2.getPosition());
        Assert.assertEquals(1, element3.getPosition());
        Assert.assertEquals(2, element4.getPosition());
        Assert.assertEquals(3, element5.getPosition());
        Assert.assertEquals(4, element6.getPosition());
        Assert.assertEquals(5, element7.getPosition());
        Assert.assertEquals(6, element8.getPosition());


        element8.moveToPosition(3);


        Assert.assertEquals(0, element2.getPosition());
        Assert.assertEquals(1, element3.getPosition());
        Assert.assertEquals(2, element4.getPosition());
        Assert.assertEquals(3, element8.getPosition());
        Assert.assertEquals(4, element5.getPosition());
        Assert.assertEquals(5, element6.getPosition());
        Assert.assertEquals(6, element7.getPosition());
    }

    @Test
    public void testMoveToPosition_toItsOldPosition_forNewMenuItem() {
        final MenuItem element1 = TestUtil.createMenuItem(2, menu);
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(4, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(6, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(8, menu);

        element2.setParent(element1);
        element3.setParent(element1);
        element4.setParent(element1);
        element5.setParent(element1);
        element6.setParent(element1);
        element7.setParent(element1);
        element8.setParent(element1);

        Assert.assertEquals(0, element2.getPosition());
        Assert.assertEquals(1, element3.getPosition());
        Assert.assertEquals(2, element4.getPosition());
        Assert.assertEquals(3, element5.getPosition());
        Assert.assertEquals(4, element6.getPosition());
        Assert.assertEquals(5, element7.getPosition());
        Assert.assertEquals(6, element8.getPosition());


        element5.moveToPosition(3);


        // Structure must be same as before execution
        Assert.assertEquals(0, element2.getPosition());
        Assert.assertEquals(1, element3.getPosition());
        Assert.assertEquals(2, element4.getPosition());
        Assert.assertEquals(3, element5.getPosition());
        Assert.assertEquals(4, element6.getPosition());
        Assert.assertEquals(5, element7.getPosition());
        Assert.assertEquals(6, element8.getPosition());
    }
    /*-----------------------------------Move To Position for NewMenuItem as parent-----------------------------------*/

    /*---------------------------------------Move To Position for Menu as parent--------------------------------------*/

    @Test
    public void testMoveToPosition_toFirstPosition_forMenu() {
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(4, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(6, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(8, menu);

        element2.setParent(null);
        element3.setParent(null);
        element4.setParent(null);
        element5.setParent(null);
        element6.setParent(null);
        element7.setParent(null);
        element8.setParent(null);

        Assert.assertEquals(0, element2.getPosition());
        Assert.assertEquals(1, element3.getPosition());
        Assert.assertEquals(2, element4.getPosition());
        Assert.assertEquals(3, element5.getPosition());
        Assert.assertEquals(4, element6.getPosition());
        Assert.assertEquals(5, element7.getPosition());
        Assert.assertEquals(6, element8.getPosition());

        element8.moveToPosition(0);

        Assert.assertEquals(0, element8.getPosition());
        Assert.assertEquals(1, element2.getPosition());
        Assert.assertEquals(2, element3.getPosition());
        Assert.assertEquals(3, element4.getPosition());
        Assert.assertEquals(4, element5.getPosition());
        Assert.assertEquals(5, element6.getPosition());
        Assert.assertEquals(6, element7.getPosition());
    }

    @Test
    public void testMoveToPosition_toLastPosition_forMenu() {
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(4, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(6, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(8, menu);

        element2.setParent(null);
        element3.setParent(null);
        element4.setParent(null);
        element5.setParent(null);
        element6.setParent(null);
        element7.setParent(null);
        element8.setParent(null);

        Assert.assertEquals(0, element2.getPosition());
        Assert.assertEquals(1, element3.getPosition());
        Assert.assertEquals(2, element4.getPosition());
        Assert.assertEquals(3, element5.getPosition());
        Assert.assertEquals(4, element6.getPosition());
        Assert.assertEquals(5, element7.getPosition());
        Assert.assertEquals(6, element8.getPosition());


        element2.moveToPosition(6);


        Assert.assertEquals(0, element3.getPosition());
        Assert.assertEquals(1, element4.getPosition());
        Assert.assertEquals(2, element5.getPosition());
        Assert.assertEquals(3, element6.getPosition());
        Assert.assertEquals(4, element7.getPosition());
        Assert.assertEquals(5, element8.getPosition());
        Assert.assertEquals(6, element2.getPosition());
    }

    @Test
    public void testMoveToPosition_toLastPosition_usingNullAsDesiredPosition_forMenu() {
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(4, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(6, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(8, menu);

        element2.setParent(null);
        element3.setParent(null);
        element4.setParent(null);
        element5.setParent(null);
        element6.setParent(null);
        element7.setParent(null);
        element8.setParent(null);

        Assert.assertEquals(0, element2.getPosition());
        Assert.assertEquals(1, element3.getPosition());
        Assert.assertEquals(2, element4.getPosition());
        Assert.assertEquals(3, element5.getPosition());
        Assert.assertEquals(4, element6.getPosition());
        Assert.assertEquals(5, element7.getPosition());
        Assert.assertEquals(6, element8.getPosition());


        element2.moveToPosition(null);


        Assert.assertEquals(0, element3.getPosition());
        Assert.assertEquals(1, element4.getPosition());
        Assert.assertEquals(2, element5.getPosition());
        Assert.assertEquals(3, element6.getPosition());
        Assert.assertEquals(4, element7.getPosition());
        Assert.assertEquals(5, element8.getPosition());
        Assert.assertEquals(6, element2.getPosition());
    }

    @Test
    public void testMoveToPosition_toLastPosition_usingNegativeAsDesiredPosition_forMenu() {
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(4, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(6, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(8, menu);

        element2.setParent(null);
        element3.setParent(null);
        element4.setParent(null);
        element5.setParent(null);
        element6.setParent(null);
        element7.setParent(null);
        element8.setParent(null);

        Assert.assertEquals(0, element2.getPosition());
        Assert.assertEquals(1, element3.getPosition());
        Assert.assertEquals(2, element4.getPosition());
        Assert.assertEquals(3, element5.getPosition());
        Assert.assertEquals(4, element6.getPosition());
        Assert.assertEquals(5, element7.getPosition());
        Assert.assertEquals(6, element8.getPosition());


        element2.moveToPosition(-1);


        Assert.assertEquals(0, element3.getPosition());
        Assert.assertEquals(1, element4.getPosition());
        Assert.assertEquals(2, element5.getPosition());
        Assert.assertEquals(3, element6.getPosition());
        Assert.assertEquals(4, element7.getPosition());
        Assert.assertEquals(5, element8.getPosition());
        Assert.assertEquals(6, element2.getPosition());
    }

    @Test
    public void testMoveToPosition_toMiddlePosition_fromFirst_forMenu() {
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(4, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(6, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(8, menu);

        element2.setParent(null);
        element3.setParent(null);
        element4.setParent(null);
        element5.setParent(null);
        element6.setParent(null);
        element7.setParent(null);
        element8.setParent(null);

        Assert.assertEquals(0, element2.getPosition());
        Assert.assertEquals(1, element3.getPosition());
        Assert.assertEquals(2, element4.getPosition());
        Assert.assertEquals(3, element5.getPosition());
        Assert.assertEquals(4, element6.getPosition());
        Assert.assertEquals(5, element7.getPosition());
        Assert.assertEquals(6, element8.getPosition());


        element2.moveToPosition(3);


        Assert.assertEquals(0, element3.getPosition());
        Assert.assertEquals(1, element4.getPosition());
        Assert.assertEquals(2, element5.getPosition());
        Assert.assertEquals(3, element2.getPosition());
        Assert.assertEquals(4, element6.getPosition());
        Assert.assertEquals(5, element7.getPosition());
        Assert.assertEquals(6, element8.getPosition());
    }

    @Test
    public void testMoveToPosition_toMiddlePosition_fromLast_forMenu() {
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(4, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(6, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(8, menu);

        element2.setParent(null);
        element3.setParent(null);
        element4.setParent(null);
        element5.setParent(null);
        element6.setParent(null);
        element7.setParent(null);
        element8.setParent(null);

        Assert.assertEquals(0, element2.getPosition());
        Assert.assertEquals(1, element3.getPosition());
        Assert.assertEquals(2, element4.getPosition());
        Assert.assertEquals(3, element5.getPosition());
        Assert.assertEquals(4, element6.getPosition());
        Assert.assertEquals(5, element7.getPosition());
        Assert.assertEquals(6, element8.getPosition());


        element8.moveToPosition(3);


        Assert.assertEquals(0, element2.getPosition());
        Assert.assertEquals(1, element3.getPosition());
        Assert.assertEquals(2, element4.getPosition());
        Assert.assertEquals(3, element8.getPosition());
        Assert.assertEquals(4, element5.getPosition());
        Assert.assertEquals(5, element6.getPosition());
        Assert.assertEquals(6, element7.getPosition());
    }

    @Test
    public void testMoveToPosition_toItsOldPosition_forMenu() {
        final MenuItem element2 = TestUtil.createMenuItem(2, menu);
        final MenuItem element3 = TestUtil.createMenuItem(3, menu);
        final MenuItem element4 = TestUtil.createMenuItem(4, menu);
        final MenuItem element5 = TestUtil.createMenuItem(5, menu);
        final MenuItem element6 = TestUtil.createMenuItem(6, menu);
        final MenuItem element7 = TestUtil.createMenuItem(7, menu);
        final MenuItem element8 = TestUtil.createMenuItem(8, menu);

        element2.setParent(null);
        element3.setParent(null);
        element4.setParent(null);
        element5.setParent(null);
        element6.setParent(null);
        element7.setParent(null);
        element8.setParent(null);

        Assert.assertEquals(0, element2.getPosition());
        Assert.assertEquals(1, element3.getPosition());
        Assert.assertEquals(2, element4.getPosition());
        Assert.assertEquals(3, element5.getPosition());
        Assert.assertEquals(4, element6.getPosition());
        Assert.assertEquals(5, element7.getPosition());
        Assert.assertEquals(6, element8.getPosition());


        element5.moveToPosition(3);


        // Structure must be same as before execution
        Assert.assertEquals(0, element2.getPosition());
        Assert.assertEquals(1, element3.getPosition());
        Assert.assertEquals(2, element4.getPosition());
        Assert.assertEquals(3, element5.getPosition());
        Assert.assertEquals(4, element6.getPosition());
        Assert.assertEquals(5, element7.getPosition());
        Assert.assertEquals(6, element8.getPosition());
    }
    /*---------------------------------------Move To Position for Menu as parent--------------------------------------*/
}
