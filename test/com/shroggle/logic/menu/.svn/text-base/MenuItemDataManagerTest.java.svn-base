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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.entity.SiteShowOption;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class MenuItemDataManagerTest {

    /*-----------------------------------------------------Create-----------------------------------------------------*/

    @Test
    public void testCreate_TREE_STYLE() {
        final DraftMenu menu = TestUtil.createMenu();
        menu.setMenuStyleType(MenuStyleType.TREE_STYLE_HORIZONTAL);

        final Site site = TestUtil.createSite();
        final Page page1 = TestUtil.createPage(site);
        final Page page2 = TestUtil.createPage(site);
        final Page page3 = TestUtil.createPage(site);
        final Page page4 = TestUtil.createPage(site);

        final MenuItem menuItem1 = TestUtil.createMenuItem(page1.getPageId(), menu, false);
        final MenuItem menuItem2 = TestUtil.createMenuItem(page2.getPageId(), menu, true);
        final MenuItem menuItem3 = TestUtil.createMenuItem(page3.getPageId(), menu, true);
        final MenuItem menuItem4 = TestUtil.createMenuItem(page4.getPageId(), menu, true);
        menuItem1.setParent(null);
        menuItem2.setParent(menuItem1);
        menuItem3.setParent(menuItem2);
        menuItem4.setParent(menuItem3);


        menuItem1.setName("menuItem1");
        menuItem1.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem2.setName("menuItem2");
        menuItem2.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem3.setName("menuItem3");
        menuItem3.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem4.setName("menuItem4");
        menuItem4.setUrlType(MenuUrlType.CUSTOM_URL);


        final MenuItemDataManager manager = new MenuItemDataManager(menu, SiteShowOption.INSIDE_APP, 1);


        final List<MenuItemData> rootItemData = manager.getRootElementsData();
        Assert.assertEquals(1, rootItemData.size());
        Assert.assertEquals("menuItem1 is not included in menu so we dont include it in the new structure",
                "menuItem2", rootItemData.get(0).getName());


        final List<MenuItemDataHolder> dataHolders = manager.getParentsWithChildrenInColumns();

        Assert.assertEquals(2, dataHolders.size());
        for (MenuItemDataHolder holder : dataHolders) {
            Assert.assertEquals(1, holder.getColumnsWithChildren().size());
            final List<MenuItemData> children = holder.getColumnsWithChildren().get(0).getChildren();
            if (holder.getParent().getName().equals("menuItem2")) {
                Assert.assertEquals(1, children.size());
                Assert.assertEquals("menuItem3", children.get(0).getName());
                continue;
            }
            if (holder.getParent().getName().equals("menuItem3")) {
                Assert.assertEquals(1, children.size());
                Assert.assertEquals("menuItem4", children.get(0).getName());
                continue;
            }
            throw new IllegalStateException();
        }
    }

    @Test
    public void testCreate_DROP_DOWN_STYLE() {
        final DraftMenu menu = TestUtil.createMenu();
        menu.setMenuStyleType(MenuStyleType.DROP_DOWN_STYLE_HORIZONTAL);

        final Site site = TestUtil.createSite();
        final Page page1 = TestUtil.createPage(site);
        final Page page2 = TestUtil.createPage(site);
        final Page page3 = TestUtil.createPage(site);
        final Page page4 = TestUtil.createPage(site);

        final MenuItem menuItem1 = TestUtil.createMenuItem(page1.getPageId(), menu, false);
        final MenuItem menuItem2 = TestUtil.createMenuItem(page1.getPageId(), menu, true);
        final MenuItem menuItem3 = TestUtil.createMenuItem(page1.getPageId(), menu, true);
        final MenuItem menuItem4 = TestUtil.createMenuItem(page1.getPageId(), menu, true);
        menuItem1.setParent(null);
        menuItem2.setParent(menuItem1);
        menuItem3.setParent(menuItem2);
        menuItem4.setParent(menuItem3);


        menuItem1.setName("menuItem1");
        menuItem1.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem2.setName("menuItem2");
        menuItem2.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem3.setName("menuItem3");
        menuItem3.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem4.setName("menuItem4");
        menuItem4.setUrlType(MenuUrlType.CUSTOM_URL);


        final MenuItemDataManager manager = new MenuItemDataManager(menu, SiteShowOption.INSIDE_APP, 1);


        final List<MenuItemData> rootItemData = manager.getRootElementsData();
        Assert.assertEquals(1, rootItemData.size());
        Assert.assertEquals("menuItem1 is not included in menu so we dont include it in the new structure",
                "menuItem2", rootItemData.get(0).getName());


        final List<MenuItemDataHolder> dataHolders = manager.getParentsWithChildrenInColumns();

        Assert.assertEquals(1, dataHolders.size());
        for (MenuItemDataHolder holder : dataHolders) {
            Assert.assertEquals(1, holder.getColumnsWithChildren().size());
            final List<MenuItemData> children = holder.getColumnsWithChildren().get(0).getChildren();
            if (holder.getParent().getName().equals("menuItem2")) {
                Assert.assertEquals(2, children.size());
                Assert.assertEquals("menuItem3", children.get(0).getName());
                Assert.assertEquals("menuItem4", children.get(1).getName());
                continue;
            }
            throw new IllegalStateException();
        }
    }

    @Test
    public void testCreate_FULL_HEIGHT_STYLE() {
        final DraftMenu menu = TestUtil.createMenu();
        menu.setMenuStyleType(MenuStyleType.FULL_HEIGHT_STYLE_HORIZONTAL);

        final Site site = TestUtil.createSite();
        final Page page1 = TestUtil.createPage(site);
        final Page page2 = TestUtil.createPage(site);
        final Page page3 = TestUtil.createPage(site);
        final Page page4 = TestUtil.createPage(site);

        final MenuItem menuItem1 = TestUtil.createMenuItem(page1.getPageId(), menu, false);
        final MenuItem menuItem2 = TestUtil.createMenuItem(page2.getPageId(), menu, true);
        final MenuItem menuItem3 = TestUtil.createMenuItem(page3.getPageId(), menu, true);
        final MenuItem menuItem4 = TestUtil.createMenuItem(page4.getPageId(), menu, true);
        menuItem1.setParent(null);
        menuItem2.setParent(menuItem1);
        menuItem3.setParent(menuItem2);
        menuItem4.setParent(menuItem3);


        menuItem1.setName("menuItem1");
        menuItem1.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem2.setName("menuItem2");
        menuItem2.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem3.setName("menuItem3");
        menuItem3.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem4.setName("menuItem4");
        menuItem4.setUrlType(MenuUrlType.CUSTOM_URL);


        final MenuItemDataManager manager = new MenuItemDataManager(menu, SiteShowOption.INSIDE_APP, 1);


        final List<MenuItemData> rootItemData = manager.getRootElementsData();
        Assert.assertEquals(1, rootItemData.size());
        Assert.assertEquals("menuItem1 is not included in menu so we dont include it in the new structure",
                "menuItem2", rootItemData.get(0).getName());


        final List<MenuItemDataHolder> dataHolders = manager.getParentsWithChildrenInColumns();

        Assert.assertEquals(1, dataHolders.size());
        for (MenuItemDataHolder holder : dataHolders) {
            Assert.assertEquals(1, holder.getColumnsWithChildren().size());
            final List<MenuItemData> children = holder.getColumnsWithChildren().get(0).getChildren();
            if (holder.getParent().getName().equals("menuItem2")) {
                Assert.assertEquals(2, children.size());
                Assert.assertEquals("menuItem3", children.get(0).getName());
                Assert.assertEquals("menuItem4", children.get(1).getName());
                continue;
            }
            throw new IllegalStateException();
        }
    }

    /*-----------------------------------------------------TABBED_STYLE-----------------------------------------------------*/
    @Test
    public void testCreate_TABBED_oneParentWithOneColumn_withOnlySecondLevelChildren() {
        final Site site = TestUtil.createSite();
        final DraftMenu menu = TestUtil.createMenu();
        menu.setMenuStyleType(MenuStyleType.TABBED_STYLE_HORIZONTAL);

        final Page page1 = TestUtil.createPage(site);
        final Page page2 = TestUtil.createPage(site);

        final MenuItem menuItem1 = TestUtil.createMenuItem(page1.getPageId(), menu, true);
        final MenuItem menuItem2 = TestUtil.createMenuItem(page2.getPageId(), menu, true);
        menuItem1.setParent(null);
        menuItem2.setParent(menuItem1);


        menuItem1.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem1.setCustomUrl("menuItem1 custom url");
        final MenuImage menuImage = TestUtil.createMenuImage(site.getSiteId());
        menuItem1.setImageId(menuImage.getMenuImageId());
        menuItem1.setName("menuItem1 name");
        menuItem1.setTitle("menuItem1 title");

        menuItem2.setName("menuItem2");
        menuItem2.setCustomUrl("menuItem2 custom url");
        menuItem2.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem2.setName("menuItem2 name");
        menuItem2.setTitle("menuItem2 title");


        final MenuItemDataManager manager = new MenuItemDataManager(menu, SiteShowOption.INSIDE_APP, 1);


        final List<MenuItemData> rootItemData = manager.getRootElementsData();
        Assert.assertEquals(1, rootItemData.size());
        Assert.assertEquals("menuItem1 name", rootItemData.get(0).getName());


        final List<MenuItemDataHolder> dataHolders = manager.getParentsWithChildrenInColumns();

        Assert.assertEquals(1, dataHolders.size());
        final MenuItemDataHolder holder = dataHolders.get(0);
        final MenuItemData parent = holder.getParent();
        final List<MenuItemDataHolderColumn> columns = holder.getColumnsWithChildren();

        Assert.assertEquals(1, columns.size());
        Assert.assertEquals("menuItem1 name", parent.getName());
        final List<MenuItemData> childrenFromFirstColumn = columns.get(0).getChildren();
        Assert.assertEquals("Here is two children instead of one (menuItem1 in menu has only one child) because we add " +
                "one default children with image and href from parent and without name and title when we have only " +
                "second level children in menu.",
                2, childrenFromFirstColumn.size());

        final MenuItemData firstDefaultElement = childrenFromFirstColumn.get(0);
        Assert.assertEquals("", firstDefaultElement.getName());
        Assert.assertEquals("", firstDefaultElement.getDescription());
        Assert.assertEquals("http://menuItem1 custom url", firstDefaultElement.getHref());
        Assert.assertEquals(true, firstDefaultElement.isShowImage());
        Assert.assertEquals(new MenuItemManager(menuItem1, SiteShowOption.getDraftOption()).getImageUrl(), firstDefaultElement.getImageUrl());
        Assert.assertEquals(true, firstDefaultElement.isLastRight());
        Assert.assertEquals(false, firstDefaultElement.isLastBottom());

        final MenuItemData secondElement = childrenFromFirstColumn.get(1);
        Assert.assertEquals("menuItem2 name", secondElement.getName());
        Assert.assertEquals("menuItem2 title", secondElement.getDescription());
        Assert.assertEquals("http://menuItem2 custom url", secondElement.getHref());
        Assert.assertEquals(false, secondElement.isShowImage());
        Assert.assertEquals("", secondElement.getImageUrl());
        Assert.assertEquals(true, secondElement.isLastRight());
        Assert.assertEquals(true, secondElement.isLastBottom());
    }

    @Test
    public void testCreate_TABBED_oneParentWithOneColumn_withOnlySecondLevelChildren_withoutImageInParent() {
        final Site site = TestUtil.createSite();
        final DraftMenu menu = TestUtil.createMenu();
        menu.setMenuStyleType(MenuStyleType.TABBED_STYLE_HORIZONTAL);

        final Page page1 = TestUtil.createPage(site);
        final Page page2 = TestUtil.createPage(site);

        final MenuItem menuItem1 = TestUtil.createMenuItem(page1.getPageId(), menu, true);
        final MenuItem menuItem2 = TestUtil.createMenuItem(page2.getPageId(), menu, true);
        menuItem1.setParent(null);
        menuItem2.setParent(menuItem1);


        menuItem1.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem1.setCustomUrl("menuItem1 custom url");
        // final MenuImage menuImage = TestUtil.createMenuImage(site.getSiteId());
        // menuItem1.setImageId(menuImage.getMenuImageId());
        menuItem1.setName("menuItem1 name");
        menuItem1.setTitle("menuItem1 title");

        menuItem2.setName("menuItem2");
        menuItem2.setCustomUrl("menuItem2 custom url");
        menuItem2.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem2.setName("menuItem2 name");
        menuItem2.setTitle("menuItem2 title");


        final MenuItemDataManager manager = new MenuItemDataManager(menu, SiteShowOption.INSIDE_APP, 1);


        final List<MenuItemData> rootItemData = manager.getRootElementsData();
        Assert.assertEquals(1, rootItemData.size());
        Assert.assertEquals("menuItem1 name", rootItemData.get(0).getName());


        final List<MenuItemDataHolder> dataHolders = manager.getParentsWithChildrenInColumns();

        Assert.assertEquals(1, dataHolders.size());
        final MenuItemDataHolder holder = dataHolders.get(0);
        final MenuItemData parent = holder.getParent();
        final List<MenuItemDataHolderColumn> columns = holder.getColumnsWithChildren();

        Assert.assertEquals(1, columns.size());
        Assert.assertEquals("menuItem1 name", parent.getName());
        final List<MenuItemData> childrenFromFirstColumn = columns.get(0).getChildren();
        Assert.assertEquals(1, childrenFromFirstColumn.size());

//        final MenuItemData firstDefaultElement = childrenFromFirstColumn.get(0);
//        Assert.assertEquals("", firstDefaultElement.getName());
//        Assert.assertEquals("", firstDefaultElement.getDescription());
//        Assert.assertEquals("http://menuItem1 custom url", firstDefaultElement.getHref());
//        Assert.assertEquals(true, firstDefaultElement.isShowImage());
//        Assert.assertEquals(new MenuItemManager(menuItem1).getResourceUrl(), firstDefaultElement.getResourceUrl());

        final MenuItemData secondElement = childrenFromFirstColumn.get(0);
        Assert.assertEquals("menuItem2 name", secondElement.getName());
        Assert.assertEquals("menuItem2 title", secondElement.getDescription());
        Assert.assertEquals("http://menuItem2 custom url", secondElement.getHref());
        Assert.assertEquals(false, secondElement.isShowImage());
        Assert.assertEquals("", secondElement.getImageUrl());
        Assert.assertEquals(true, secondElement.isLastRight());
        Assert.assertEquals(true, secondElement.isLastBottom());
    }

    @Test
    public void testCreate_TABBED_oneParentWithOneColumn_withThirdLevelChildren() {
        final Site site = TestUtil.createSite();
        final DraftMenu menu = TestUtil.createMenu();
        menu.setMenuStyleType(MenuStyleType.TABBED_STYLE_HORIZONTAL);

        final Page page1 = TestUtil.createPage(site);
        final Page page2 = TestUtil.createPage(site);
        final Page page3 = TestUtil.createPage(site);

        final MenuItem menuItem1 = TestUtil.createMenuItem(page1.getPageId(), menu, true);
        final MenuItem menuItem2 = TestUtil.createMenuItem(page2.getPageId(), menu, true);
        final MenuItem menuItem3 = TestUtil.createMenuItem(page3.getPageId(), menu, true);
        menuItem1.setParent(null);
        menuItem2.setParent(menuItem1);
        menuItem3.setParent(menuItem2);


        menuItem1.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem1.setCustomUrl("menuItem1 custom url");
        final MenuImage menuImage1 = TestUtil.createMenuImage(site.getSiteId());
        menuItem1.setImageId(menuImage1.getMenuImageId());
        menuItem1.setName("menuItem1 name");
        menuItem1.setTitle("menuItem1 title");

        menuItem2.setName("menuItem2");
        menuItem2.setCustomUrl("menuItem2 custom url");
        menuItem2.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem2.setName("menuItem2 name");
        menuItem2.setTitle("menuItem2 title");
        final MenuImage menuImage2 = TestUtil.createMenuImage(site.getSiteId());
        menuItem2.setImageId(menuImage2.getMenuImageId());

        menuItem3.setName("menuItem3");
        menuItem3.setCustomUrl("menuItem3 custom url");
        menuItem3.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem3.setName("menuItem3 name");
        menuItem3.setTitle("menuItem3 title");
        final MenuImage menuImage3 = TestUtil.createMenuImage(site.getSiteId());
        menuItem3.setImageId(menuImage3.getMenuImageId());

        final MenuItemDataManager manager = new MenuItemDataManager(menu, SiteShowOption.INSIDE_APP, 1);


        final List<MenuItemData> rootItemData = manager.getRootElementsData();
        Assert.assertEquals(1, rootItemData.size());
        Assert.assertEquals("menuItem1 name", rootItemData.get(0).getName());


        final List<MenuItemDataHolder> dataHolders = manager.getParentsWithChildrenInColumns();

        Assert.assertEquals(1, dataHolders.size());
        final MenuItemDataHolder holder = dataHolders.get(0);
        final MenuItemData parent = holder.getParent();
        final List<MenuItemDataHolderColumn> columns = holder.getColumnsWithChildren();

        Assert.assertEquals(1, columns.size());
        Assert.assertEquals("menuItem1 name", parent.getName());
        final List<MenuItemData> childrenFromFirstColumn = columns.get(0).getChildren();
        Assert.assertEquals(2, childrenFromFirstColumn.size());

        final MenuItemData firstElement = childrenFromFirstColumn.get(0);
        Assert.assertEquals("menuItem2 name", firstElement.getName());
        Assert.assertEquals("menuItem2 title", firstElement.getDescription());
        Assert.assertEquals("http://menuItem2 custom url", firstElement.getHref());
        Assert.assertEquals(true, firstElement.isShowImage());
        Assert.assertEquals(new MenuItemManager(menuItem2, SiteShowOption.getDraftOption()).getImageUrl(), firstElement.getImageUrl());
        Assert.assertEquals(true, firstElement.isLastRight());
        Assert.assertEquals(false, firstElement.isLastBottom());

        final MenuItemData secondElement = childrenFromFirstColumn.get(1);
        Assert.assertEquals("menuItem3 name", secondElement.getName());
        Assert.assertEquals("menuItem3 title", secondElement.getDescription());
        Assert.assertEquals("http://menuItem3 custom url", secondElement.getHref());
        Assert.assertEquals(false, secondElement.isShowImage());
        Assert.assertEquals(new MenuItemManager(menuItem3, SiteShowOption.getDraftOption()).getImageUrl(), secondElement.getImageUrl());
        Assert.assertEquals(true, secondElement.isLastRight());
        Assert.assertEquals(true, secondElement.isLastBottom());
    }

    @Test
    public void testCreate_TABBED_oneParentWithTwoColumns_withTwoSecondLevelChildrenAndTwoThirdLevelChildren() {
        final Site site = TestUtil.createSite();
        final DraftMenu menu = TestUtil.createMenu();
        menu.setMenuStyleType(MenuStyleType.TABBED_STYLE_HORIZONTAL);

        final Page page1 = TestUtil.createPage(site);
        final Page page2 = TestUtil.createPage(site);
        final Page page3 = TestUtil.createPage(site);
        final Page page4 = TestUtil.createPage(site);
        final Page page5 = TestUtil.createPage(site);

        final MenuItem menuItem1 = TestUtil.createMenuItem(page1.getPageId(), menu, true);
        final MenuItem menuItem2 = TestUtil.createMenuItem(page2.getPageId(), menu, true);
        final MenuItem menuItem3 = TestUtil.createMenuItem(page3.getPageId(), menu, true);
        final MenuItem menuItem4 = TestUtil.createMenuItem(page4.getPageId(), menu, true);
        final MenuItem menuItem5 = TestUtil.createMenuItem(page5.getPageId(), menu, true);

        menuItem1.setParent(null);// First level (root element)
        /*------------------------------------------------First Column------------------------------------------------*/
        menuItem2.setParent(menuItem1);// Second level
        menuItem3.setParent(menuItem2);// Third level
        /*------------------------------------------------First Column------------------------------------------------*/

        /*------------------------------------------------Second Column-----------------------------------------------*/
        menuItem4.setParent(menuItem1);// Second level
        menuItem5.setParent(menuItem4);// Third level
        /*------------------------------------------------Second Column-----------------------------------------------*/

        menuItem1.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem1.setCustomUrl("menuItem1 custom url");
        final MenuImage menuImage1 = TestUtil.createMenuImage(site.getSiteId());
        menuItem1.setImageId(menuImage1.getMenuImageId());
        menuItem1.setName("menuItem1 name");
        menuItem1.setTitle("menuItem1 title");

        menuItem2.setName("menuItem2");
        menuItem2.setCustomUrl("menuItem2 custom url");
        menuItem2.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem2.setName("menuItem2 name");
        menuItem2.setTitle("menuItem2 title");
        final MenuImage menuImage2 = TestUtil.createMenuImage(site.getSiteId());
        menuItem2.setImageId(menuImage2.getMenuImageId());

        menuItem3.setName("menuItem3");
        menuItem3.setCustomUrl("menuItem3 custom url");
        menuItem3.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem3.setName("menuItem3 name");
        menuItem3.setTitle("menuItem3 title");
        final MenuImage menuImage3 = TestUtil.createMenuImage(site.getSiteId());
        menuItem3.setImageId(menuImage3.getMenuImageId());


        menuItem4.setName("menuItem4");
        menuItem4.setCustomUrl("menuItem4 custom url");
        menuItem4.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem4.setName("menuItem4 name");
        menuItem4.setTitle("menuItem4 title");
        final MenuImage menuImage4 = TestUtil.createMenuImage(site.getSiteId());
        menuItem4.setImageId(menuImage4.getMenuImageId());


        menuItem5.setName("menuItem5");
        menuItem5.setCustomUrl("menuItem5 custom url");
        menuItem5.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem5.setName("menuItem5 name");
        menuItem5.setTitle("menuItem5 title");
        final MenuImage menuImage5 = TestUtil.createMenuImage(site.getSiteId());
        menuItem5.setImageId(menuImage5.getMenuImageId());

        final MenuItemDataManager manager = new MenuItemDataManager(menu, SiteShowOption.INSIDE_APP, 1);


        final List<MenuItemData> rootItemData = manager.getRootElementsData();
        Assert.assertEquals(1, rootItemData.size());
        Assert.assertEquals("menuItem1 name", rootItemData.get(0).getName());


        final List<MenuItemDataHolder> dataHolders = manager.getParentsWithChildrenInColumns();

        Assert.assertEquals(1, dataHolders.size());
        final MenuItemDataHolder holder = dataHolders.get(0);
        final MenuItemData parent = holder.getParent();
        Assert.assertEquals("menuItem1 name", parent.getName());

        final List<MenuItemDataHolderColumn> columns = holder.getColumnsWithChildren();
        Assert.assertEquals(2, columns.size());

        /*------------------------------------------------First Column------------------------------------------------*/
        final List<MenuItemData> childrenFromFirstColumn = columns.get(0).getChildren();
        Assert.assertEquals(2, childrenFromFirstColumn.size());

        final MenuItemData firstElement = childrenFromFirstColumn.get(0);
        Assert.assertEquals("menuItem2 name", firstElement.getName());
        Assert.assertEquals("menuItem2 title", firstElement.getDescription());
        Assert.assertEquals("http://menuItem2 custom url", firstElement.getHref());
        Assert.assertEquals(true, firstElement.isShowImage());
        Assert.assertEquals(new MenuItemManager(menuItem2, SiteShowOption.getDraftOption()).getImageUrl(), firstElement.getImageUrl());
        Assert.assertEquals(false, firstElement.isLastRight());
        Assert.assertEquals(false, firstElement.isLastBottom());

        final MenuItemData secondElement = childrenFromFirstColumn.get(1);
        Assert.assertEquals("menuItem3 name", secondElement.getName());
        Assert.assertEquals("menuItem3 title", secondElement.getDescription());
        Assert.assertEquals("http://menuItem3 custom url", secondElement.getHref());
        Assert.assertEquals(false, secondElement.isShowImage());
        Assert.assertEquals(new MenuItemManager(menuItem3, SiteShowOption.getDraftOption()).getImageUrl(), secondElement.getImageUrl());
        Assert.assertEquals(false, secondElement.isLastRight());
        Assert.assertEquals(true, secondElement.isLastBottom());
        /*------------------------------------------------First Column------------------------------------------------*/

        /*------------------------------------------------Second Column-----------------------------------------------*/
        final List<MenuItemData> childrenFromSecondColumn = columns.get(1).getChildren();
        Assert.assertEquals(2, childrenFromSecondColumn.size());

        final MenuItemData firstElement2 = childrenFromSecondColumn.get(0);
        Assert.assertEquals("menuItem4 name", firstElement2.getName());
        Assert.assertEquals("menuItem4 title", firstElement2.getDescription());
        Assert.assertEquals("http://menuItem4 custom url", firstElement2.getHref());
        Assert.assertEquals(true, firstElement2.isShowImage());
        Assert.assertEquals(new MenuItemManager(menuItem4, SiteShowOption.getDraftOption()).getImageUrl(), firstElement2.getImageUrl());
        Assert.assertEquals(true, firstElement2.isLastRight());
        Assert.assertEquals(false, firstElement2.isLastBottom());

        final MenuItemData secondElement2 = childrenFromSecondColumn.get(1);
        Assert.assertEquals("menuItem5 name", secondElement2.getName());
        Assert.assertEquals("menuItem5 title", secondElement2.getDescription());
        Assert.assertEquals("http://menuItem5 custom url", secondElement2.getHref());
        Assert.assertEquals(false, secondElement2.isShowImage());
        Assert.assertEquals(new MenuItemManager(menuItem5, SiteShowOption.getDraftOption()).getImageUrl(), secondElement2.getImageUrl());
        Assert.assertEquals(true, secondElement2.isLastRight());
        Assert.assertEquals(true, secondElement2.isLastBottom());
        /*------------------------------------------------Second Column-----------------------------------------------*/
    }
    /*-----------------------------------------------------TABBED_STYLE-----------------------------------------------------*/
    /*-----------------------------------------------------Create-----------------------------------------------------*/


    /*---------------------------------------------Create Column Indexes----------------------------------------------*/

    @Test
    public void testCreateColumnIndexes() {

        MenuItemData menuItemDataParent1 = new MenuItemData();
        MenuItemData menuItemDataCild1_1 = new MenuItemData();
        MenuItemData menuItemDataCild1_2 = new MenuItemData();
        MenuItemData menuItemDataCild1_3 = new MenuItemData();
        menuItemDataParent1.setChildren(Arrays.asList(menuItemDataCild1_1, menuItemDataCild1_2, menuItemDataCild1_3));

        MenuItemData menuItemDataParent2 = new MenuItemData();
        MenuItemData menuItemDataCild2_1 = new MenuItemData();
        MenuItemData menuItemDataCild2_2 = new MenuItemData();
        MenuItemData menuItemDataCild2_3 = new MenuItemData();
        menuItemDataParent2.setChildren(Arrays.asList(menuItemDataCild2_1, menuItemDataCild2_2, menuItemDataCild2_3));

        MenuItemData menuItemDataCild2_1_1 = new MenuItemData();
        MenuItemData menuItemDataCild2_1_2 = new MenuItemData();
        MenuItemData menuItemDataCild2_1_3 = new MenuItemData();
        menuItemDataCild2_1.setChildren(Arrays.asList(menuItemDataCild2_1_1, menuItemDataCild2_1_2, menuItemDataCild2_1_3));


        new MenuItemDataManager().addColumnIndexes(Arrays.asList(menuItemDataParent1, menuItemDataParent2));
        Assert.assertEquals("1", menuItemDataParent1.getNumber());
        Assert.assertEquals("1_1", menuItemDataCild1_1.getNumber());
        Assert.assertEquals("1_2", menuItemDataCild1_2.getNumber());
        Assert.assertEquals("1_3", menuItemDataCild1_3.getNumber());

        Assert.assertEquals("2", menuItemDataParent2.getNumber());

        Assert.assertEquals("2_1", menuItemDataCild2_1.getNumber());

        Assert.assertEquals("2_2", menuItemDataCild2_1_1.getNumber());
        Assert.assertEquals("2_3", menuItemDataCild2_1_2.getNumber());
        Assert.assertEquals("2_4", menuItemDataCild2_1_3.getNumber());

        Assert.assertEquals("2_5", menuItemDataCild2_2.getNumber());
        Assert.assertEquals("2_6", menuItemDataCild2_3.getNumber());
    }
    /*---------------------------------------------Create Column Indexes----------------------------------------------*/

    /*-----------------------------------Get Root Parents With All Their Children-------------------------------------*/

    @Test
    public void testGetRootParentsWithAllTheirChildren() {
        /*--------------------------------------First root element with children--------------------------------------*/
        final MenuItemData root1 = new MenuItemData();
        final MenuItemData child1Level2_root1 = new MenuItemData();
        final MenuItemData child1Level3_root1 = new MenuItemData();
        root1.setChildren(new ArrayList<MenuItemData>(Arrays.asList(child1Level2_root1)));
        child1Level2_root1.setChildren(new ArrayList<MenuItemData>(Arrays.asList(child1Level3_root1)));
        /*--------------------------------------First root element with children--------------------------------------*/

        /*-------------------------------------Second root element with children--------------------------------------*/
        final MenuItemData root2 = new MenuItemData();
        final MenuItemData child1Level2_root2 = new MenuItemData();
        final MenuItemData child1Level3_root2 = new MenuItemData();
        root2.setChildren(new ArrayList<MenuItemData>(Arrays.asList(child1Level2_root2)));
        child1Level2_root2.setChildren(new ArrayList<MenuItemData>(Arrays.asList(child1Level3_root2)));
        /*-------------------------------------Second root element with children--------------------------------------*/
        final List<MenuItemData> menuItemDatas = new ArrayList<MenuItemData>(Arrays.asList(root1, root2));


        //final Map<MenuItemData, List<MenuItemData>> map = new MenuItemDataManager().getRootParentsWithAllTheirChildren(menuItemDatas);
        List<MenuItemDataHolder> dataHolders = new MenuItemDataManager().getRootParentsWithAllTheirChildren(menuItemDatas);

        Assert.assertEquals(2, dataHolders.size());
        final List<MenuItemData> parentsFromHolder = new ArrayList<MenuItemData>();
        for (MenuItemDataHolder holder : dataHolders) {
            parentsFromHolder.add(holder.getParent());
        }
        Assert.assertEquals(true, parentsFromHolder.contains(root1));
        Assert.assertEquals(true, parentsFromHolder.contains(root2));

        final MenuItemDataHolder holder_root1 = selectByParent(dataHolders, root1);
        Assert.assertNotNull(holder_root1);
        Assert.assertEquals(1, holder_root1.getColumnsWithChildren().size());
        final List<MenuItemData> children_root1 = holder_root1.getColumnsWithChildren().get(0).getChildren();
        Assert.assertEquals(2, children_root1.size());
        Assert.assertEquals(true, children_root1.contains(child1Level2_root1));
        Assert.assertEquals(true, children_root1.contains(child1Level3_root1));

        final MenuItemDataHolder holder_root2 = selectByParent(dataHolders, root2);
        Assert.assertNotNull(holder_root2);
        Assert.assertEquals(1, holder_root2.getColumnsWithChildren().size());
        final List<MenuItemData> children_root2 = holder_root2.getColumnsWithChildren().get(0).getChildren();
        Assert.assertEquals(2, children_root2.size());
        Assert.assertEquals(true, children_root2.contains(child1Level2_root2));
        Assert.assertEquals(true, children_root2.contains(child1Level3_root2));
    }
    /*-----------------------------------Get Root Parents With All Their Children-------------------------------------*/


    /*--------------------------------------Get All Parents With Their Children---------------------------------------*/

    @Test
    public void testGetAllParentsWithTheirChildren() {
        /*--------------------------------------First root element with children--------------------------------------*/
        final MenuItemData root1 = new MenuItemData();
        final MenuItemData child1Level2_root1 = new MenuItemData();
        final MenuItemData child1Level3_root1 = new MenuItemData();
        root1.setChildren(new ArrayList<MenuItemData>(Arrays.asList(child1Level2_root1)));
        child1Level2_root1.setChildren(new ArrayList<MenuItemData>(Arrays.asList(child1Level3_root1)));
        /*--------------------------------------First root element with children--------------------------------------*/

        /*-------------------------------------Second root element with children--------------------------------------*/
        final MenuItemData root2 = new MenuItemData();
        final MenuItemData child1Level2_root2 = new MenuItemData();
        final MenuItemData child1Level3_root2 = new MenuItemData();
        root2.setChildren(new ArrayList<MenuItemData>(Arrays.asList(child1Level2_root2)));
        child1Level2_root2.setChildren(new ArrayList<MenuItemData>(Arrays.asList(child1Level3_root2)));
        /*-------------------------------------Second root element with children--------------------------------------*/
        final List<MenuItemData> menuItemDatas = new ArrayList<MenuItemData>(Arrays.asList(root1, root2));


        //final Map<MenuItemData, List<MenuItemData>> map = new MenuItemDataManager().getAllParentsWithTheirChildren(menuItemDatas);
        List<MenuItemDataHolder> dataHolders = new MenuItemDataManager().getAllParentsWithTheirChildren(menuItemDatas);

        Assert.assertEquals(4, dataHolders.size());
        final List<MenuItemData> parentsFromHolder = new ArrayList<MenuItemData>();
        for (MenuItemDataHolder holder : dataHolders) {
            parentsFromHolder.add(holder.getParent());
        }
        Assert.assertEquals(true, parentsFromHolder.contains(root1));
        Assert.assertEquals(true, parentsFromHolder.contains(root2));
        Assert.assertEquals(true, parentsFromHolder.contains(child1Level2_root1));
        Assert.assertEquals(true, parentsFromHolder.contains(child1Level2_root2));

        final MenuItemDataHolder holder_root1 = selectByParent(dataHolders, root1);
        Assert.assertNotNull(holder_root1);
        Assert.assertEquals(1, holder_root1.getColumnsWithChildren().size());
        final List<MenuItemData> children_root1 = holder_root1.getColumnsWithChildren().get(0).getChildren();
        Assert.assertEquals(1, children_root1.size());
        Assert.assertEquals(true, children_root1.contains(child1Level2_root1));

        final MenuItemDataHolder holder_root2 = selectByParent(dataHolders, root2);
        Assert.assertNotNull(holder_root2);
        Assert.assertEquals(1, holder_root2.getColumnsWithChildren().size());
        final List<MenuItemData> children_root2 = holder_root2.getColumnsWithChildren().get(0).getChildren();
        Assert.assertEquals(1, children_root2.size());
        Assert.assertEquals(true, children_root2.contains(child1Level2_root2));

        final MenuItemDataHolder holder_child1Level2_root1 = selectByParent(dataHolders, child1Level2_root1);
        Assert.assertNotNull(holder_child1Level2_root1);
        Assert.assertEquals(1, holder_child1Level2_root1.getColumnsWithChildren().size());
        final List<MenuItemData> children_child1Level2_root1 = holder_child1Level2_root1.getColumnsWithChildren().get(0).getChildren();
        Assert.assertEquals(1, children_child1Level2_root1.size());
        Assert.assertEquals(true, children_child1Level2_root1.contains(child1Level3_root1));

        final MenuItemDataHolder holder_child1Level2_root2 = selectByParent(dataHolders, child1Level2_root2);
        Assert.assertNotNull(holder_child1Level2_root2);
        Assert.assertEquals(1, holder_child1Level2_root2.getColumnsWithChildren().size());
        final List<MenuItemData> children_child1Level2_root2 = holder_child1Level2_root2.getColumnsWithChildren().get(0).getChildren();
        Assert.assertEquals(1, children_child1Level2_root2.size());
        Assert.assertEquals(true, children_child1Level2_root2.contains(child1Level3_root2));
    }
    /*--------------------------------------Get All Parents With Their Children---------------------------------------*/

    private MenuItemDataHolder selectByParent(final List<MenuItemDataHolder> dataHolders, final MenuItemData parent) {
        for (MenuItemDataHolder holder : dataHolders) {
            if (holder.getParent() == parent) {
                return holder;
            }
        }
        return null;
    }

    /*---------------------------------Create MenuItemData For Included In Menu Items---------------------------------*/

    @Test
    public void testCreateMenuItemDataForIncludedInMenuItems() {
        final Site site = TestUtil.createSite();
        final DraftMenu menu = TestUtil.createMenu();

        final Page page1 = TestUtil.createPage(site);
        final Page page2 = TestUtil.createPage(site);
        final Page page3 = TestUtil.createPage(site);

        final MenuItem menuItem1 = TestUtil.createMenuItem(page1.getPageId(), menu, false);
        final MenuItem menuItem2 = TestUtil.createMenuItem(page2.getPageId(), menu, true);
        final MenuItem menuItem3 = TestUtil.createMenuItem(page3.getPageId(), menu, true);
        menuItem1.setParent(null);
        menuItem2.setParent(menuItem1);
        menuItem3.setParent(menuItem2);

        menuItem1.setName("menuItem1");
        menuItem1.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem2.setName("menuItem2");
        menuItem2.setUrlType(MenuUrlType.CUSTOM_URL);
        menuItem3.setName("menuItem3");
        menuItem3.setUrlType(MenuUrlType.CUSTOM_URL);


        final List<MenuItemData> itemData = new MenuItemDataManager(SiteShowOption.INSIDE_APP, 1).createMenuItemDataForIncludedInMenuItems(menu.getMenuItems());


        Assert.assertEquals(1, itemData.size());
        Assert.assertEquals("menuItem1 is not included in menu so we dont include it in the new structure",
                "menuItem2", itemData.get(0).getName());

        Assert.assertEquals(1, itemData.get(0).getChildren().size());
        Assert.assertEquals("menuItem3", itemData.get(0).getChildren().get(0).getName());
    }
    /*---------------------------------Create MenuItemData For Included In Menu Items---------------------------------*/

    @Test
    public void testClone() {
        final MenuItemData data = new MenuItemData();
        data.setName("name");
        data.setDescription("description");
        data.setShowImage(true);
        data.setExternalUrl(false);
        data.setHref("Href");
        data.setImageUrl("ImageUrl");
        data.setLevel(1);
        data.setNumber("Number");
        data.setSelected(true);

        final MenuItemData clone = data.clone();
        Assert.assertNotNull(clone);

        Assert.assertEquals(data.getName(), clone.getName());
        Assert.assertEquals(data.getDescription(), clone.getDescription());
        Assert.assertEquals(data.isShowImage(), clone.isShowImage());
        Assert.assertEquals(data.isExternalUrl(), clone.isExternalUrl());
        Assert.assertEquals(data.getHref(), clone.getHref());
        Assert.assertEquals(data.getImageUrl(), clone.getImageUrl());
        Assert.assertEquals(data.getLevel(), clone.getLevel());
        Assert.assertEquals(data.getNumber(), clone.getNumber());
        Assert.assertEquals(data.isSelected(), clone.isSelected());
        Assert.assertNotSame(data.getId(), clone.getId());
    }
}
