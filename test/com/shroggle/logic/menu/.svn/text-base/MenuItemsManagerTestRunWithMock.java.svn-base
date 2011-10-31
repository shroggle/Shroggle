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
package com.shroggle.logic.menu;

import org.junit.runner.RunWith;
import org.junit.Test;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import junit.framework.Assert;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class MenuItemsManagerTestRunWithMock {

    @Test
    public void testCopyMenuItem() {
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem parent = TestUtil.createMenuItem(2114, menu);
        final MenuItem child = TestUtil.createMenuItem(114, menu);

        final MenuItem menuItem = TestUtil.createMenuItem();
        menuItem.setMenu(menu);
        menuItem.setParent(parent);
        child.setParent(menuItem);
        menuItem.setCustomUrl("customUrl");
        menuItem.setId(11);
        menuItem.setImageId(4);
        menuItem.setName("name");
        menuItem.setPosition(9);
        menuItem.setTitle("title");
        menuItem.setDefaultPageId(null);
        menuItem.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem.setPageId(123);
        menuItem.setIncludeInMenu(true);

        final MenuItem newMenuItem = MenuItemsManager.createDraftCopy(menuItem);
        Assert.assertEquals(0, newMenuItem.getId());
        Assert.assertEquals(null, newMenuItem.getParent());
        Assert.assertEquals(0, newMenuItem.getChildren().size());
        Assert.assertEquals(null, newMenuItem.getMenu());

        Assert.assertEquals(menuItem.getCustomUrl(), newMenuItem.getCustomUrl());
        Assert.assertEquals(menuItem.getImageId(), newMenuItem.getImageId());
        Assert.assertEquals(menuItem.getName(), newMenuItem.getName());
        Assert.assertEquals(menuItem.getPosition(), newMenuItem.getPosition());
        Assert.assertEquals(menuItem.getTitle(), newMenuItem.getTitle());
//        Assert.assertEquals(menuItem.getItemType(), newMenuItem.getItemType());
        Assert.assertEquals(menuItem.getDefaultPageId(), newMenuItem.getDefaultPageId());
        Assert.assertEquals(menuItem.getUrlType(), newMenuItem.getUrlType());
        Assert.assertEquals(menuItem.getPageId(), newMenuItem.getPageId());
        Assert.assertEquals(menuItem.isIncludeInMenu(), newMenuItem.isIncludeInMenu());
    }


    @Test
    public void testGetInfoAboutMenuItems() {
        final Site site = TestUtil.createSite();
        final Page page1 = TestUtil.createPage(site);
        final Page page2 = TestUtil.createPage(site);
        final Page page3 = TestUtil.createPage(site);
        final Page pageNotFromMenu = TestUtil.createPage(site);
        final DraftMenu defaultMenu = TestUtil.createMenu(site);

        final MenuItem menuItem1 = TestUtil.createMenuItem(page1.getPageId(), defaultMenu);
        final MenuItem menuItem2 = TestUtil.createMenuItem(page2.getPageId(), defaultMenu);
        final MenuItem menuItem3 = TestUtil.createMenuItem(page3.getPageId(), defaultMenu);
        menuItem1.setParent(null);
        menuItem2.setParent(menuItem1);
        menuItem3.setParent(menuItem2);


        final InfoAboutMenuItemResponse response = MenuItemsManager.getInfoAboutMenuItems(menuItem1);
        Assert.assertNotNull(response);
        Assert.assertEquals(menuItem1, response.getSelectedMenuItem());
        Assert.assertEquals(3, response.getPages().size());
        Assert.assertEquals(true, response.getPages().contains(page1));
        Assert.assertEquals(true, response.getPages().contains(page2));
        Assert.assertEquals(true, response.getPages().contains(page3));
        Assert.assertEquals(false, response.getPages().contains(pageNotFromMenu));
    }
     /*-----------------------------------------------Get Included Items-----------------------------------------------*/
    @Test
    public void testGetIncludedMenuItemsIds() throws Exception {
        final DraftMenu menu = TestUtil.createMenu();
        final List<MenuItem> siteMenuItems = new ArrayList<MenuItem>();
        MenuItem siteMenuItem1 = TestUtil.createMenuItem(1, menu, true);
        MenuItem siteMenuItem1_1 = TestUtil.createMenuItem(2, menu, false);
        MenuItem siteMenuItem1_2 = TestUtil.createMenuItem(3, menu, true);
        siteMenuItem1_1.setParent(siteMenuItem1);
        siteMenuItem1_2.setParent(siteMenuItem1);
        siteMenuItems.add(siteMenuItem1);


        final List<Integer> includedItems = MenuItemsManager.getIncludedMenuItemIds(siteMenuItems);


        Assert.assertEquals(includedItems.size(), 2);
        Assert.assertTrue(includedItems.contains(siteMenuItem1.getId()));
        Assert.assertFalse(includedItems.contains(siteMenuItem1_1.getId()));
        Assert.assertTrue(includedItems.contains(siteMenuItem1_2.getId()));
    }
    /*-----------------------------------------------Get Included Items-----------------------------------------------*/
}
