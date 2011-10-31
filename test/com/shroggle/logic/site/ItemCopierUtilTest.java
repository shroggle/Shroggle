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
package com.shroggle.logic.site;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.item.ItemCopierUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class ItemCopierUtilTest {


    @Test
    public void testGetMenuStructureWithCopiedPageIdsAndWithoutDraftPagesFromBlueprint() {
        final DraftMenu menu = TestUtil.createMenu();
        final MenuItem menuItem1 = TestUtil.createMenuItem(1, menu, true);
        final MenuItem menuItem2 = TestUtil.createMenuItem(2, menu, false);
        final MenuItem menuItem3 = TestUtil.createMenuItem(3, menu, true);
        final MenuItem menuItem4 = TestUtil.createMenuItem(4, menu, false);
        final MenuItem menuItem5 = TestUtil.createMenuItem(5, menu, true);
        final MenuItem menuItem6 = TestUtil.createMenuItem(6, menu, false);
        final MenuItem menuItem7 = TestUtil.createMenuItem(7, menu, true);

        menuItem1.setParent(null);

        menuItem2.setParent(menuItem1);
        menuItem3.setParent(menuItem1);

        menuItem4.setParent(null);

        menuItem5.setParent(menuItem2);

        menuItem6.setParent(menuItem3);
        menuItem7.setParent(menuItem3);

        // Equivalent pageIds without pageId = 1. It means, that menuItem with this pageId (menuItem1) should not be
        // included in the new menuItems List
        final Map<Integer, Integer> blueprintPageIdsWithItsCopiedEquivalents = new HashMap<Integer, Integer>();
        blueprintPageIdsWithItsCopiedEquivalents.put(1, 11);
        blueprintPageIdsWithItsCopiedEquivalents.put(4, 14);

        blueprintPageIdsWithItsCopiedEquivalents.put(5, 15);
        blueprintPageIdsWithItsCopiedEquivalents.put(6, 16);
        blueprintPageIdsWithItsCopiedEquivalents.put(7, 17);


        final DraftMenu newMenu = TestUtil.createMenu();
        ItemCopierUtil.copyItemsWithoutDraftAndSetCorrectPageIds(
                blueprintPageIdsWithItsCopiedEquivalents, menu.getMenuItems(), newMenu, null);
        final List<MenuItem> menuItems = newMenu.getMenuItems();
        Assert.assertEquals(2, menuItems.size());

        Assert.assertEquals(11, menuItems.get(0).getPageId().intValue());// Equivalent copied pageId for menuItem1
        Assert.assertEquals(14, menuItems.get(1).getPageId().intValue());// Equivalent copied pageId for menuItem4

        Assert.assertEquals(3, menuItems.get(0).getChildren().size());
        Assert.assertEquals(15, menuItems.get(0).getChildren().get(0).getPageId().intValue());// Equivalent copied pageId for menuItem5
        Assert.assertEquals(16, menuItems.get(0).getChildren().get(1).getPageId().intValue());// Equivalent copied pageId for menuItem6
        Assert.assertEquals(17, menuItems.get(0).getChildren().get(2).getPageId().intValue());// Equivalent copied pageId for menuItem7

        Assert.assertEquals(menuItem1.isIncludeInMenu(), menuItems.get(0).isIncludeInMenu());
        Assert.assertEquals(menuItem4.isIncludeInMenu(), menuItems.get(1).isIncludeInMenu());

        Assert.assertEquals(menuItem5.isIncludeInMenu(), menuItems.get(0).getChildren().get(0).isIncludeInMenu());
        Assert.assertEquals(menuItem6.isIncludeInMenu(), menuItems.get(0).getChildren().get(1).isIncludeInMenu());
        Assert.assertEquals(menuItem7.isIncludeInMenu(), menuItems.get(0).getChildren().get(2).isIncludeInMenu());
    }

    @Test
    public void copyImage() {
        Image image = new Image();
        image.setSourceExtension("jpeg");
        image.setCreated(new Date());
        image.setDescription("Description");
        image.setWidth(200);
        image.setHeight(100);
        image.setKeywords("Keywords");
        image.setName("Name");
        image.setSiteId(12);
        image.setThumbnailExtension("ThumbnailExtension");
        persistance.putImage(image);


        Image newImage = ItemCopierUtil.copyImage(image);


        Assert.assertEquals(image.getSourceExtension(), newImage.getSourceExtension());
        Assert.assertEquals(image.getCreated(), newImage.getCreated());
        Assert.assertEquals(image.getDescription(), newImage.getDescription());
        Assert.assertEquals(image.getWidth(), newImage.getWidth());
        Assert.assertEquals(image.getHeight(), newImage.getHeight());
        Assert.assertEquals(image.getKeywords(), newImage.getKeywords());
        Assert.assertEquals(image.getName(), newImage.getName());
        Assert.assertEquals(image.getThumbnailExtension(), newImage.getThumbnailExtension());

        Assert.assertNotSame(image.getSiteId(), newImage.getSiteId());
        Assert.assertNotSame(image.getImageId(), newImage.getImageId());
    }

    @Test
    public void testCopyMenu() {
        final DraftMenu menu = new DraftMenu();

        menu.setIncludePageTitle(true);
        menu.setMenuStyleType(MenuStyleType.DROP_DOWN_STYLE_VERTICAL);
        menu.setMenuStructure(MenuStructureType.DEFAULT);


        final MenuItem menuItem1 = TestUtil.createMenuItem(1, menu);
        final MenuItem menuItem2 = TestUtil.createMenuItem(2, menu);
        menuItem2.setIncludeInMenu(false);
        menuItem2.setParent(menuItem1);
        menuItem1.setParent(null);
        menu.setSiteId(123);
        ServiceLocator.getPersistance().putMenu(menu);

        final FontsAndColors fontsAndColors = new FontsAndColors();
        persistance.putFontsAndColors(fontsAndColors);
        final FontsAndColorsValue value = new FontsAndColorsValue();
        fontsAndColors.addCssValue(value);
        value.setName("margin");
        value.setValue("100px");
        value.setSelector("selector");
        value.setDescription("description");
        menu.setFontsAndColorsId(fontsAndColors.getId());


        final DraftMenu newMenu = ItemCopierUtil.copyMenu(menu);


        Assert.assertNotSame(newMenu.getId(), menu.getId());
        Assert.assertNotSame(newMenu.getSiteId(), menu.getSiteId());
        Assert.assertEquals(newMenu.isIncludePageTitle(), menu.isIncludePageTitle());
        Assert.assertEquals(newMenu.getMenuStyleType(), menu.getMenuStyleType());

        final FontsAndColors newMenuFonts = persistance.getFontsAndColors(newMenu.getFontsAndColorsId());
        final FontsAndColors menuFonts = persistance.getFontsAndColors(menu.getFontsAndColorsId());
        Assert.assertEquals(newMenuFonts.getCssValues().size(), menuFonts.getCssValues().size());
        Assert.assertEquals(newMenuFonts.getCssValues().get(0).getDescription(), menuFonts.getCssValues().get(0).getDescription());
        Assert.assertEquals(newMenuFonts.getCssValues().get(0).getName(), menuFonts.getCssValues().get(0).getName());
        Assert.assertEquals(newMenuFonts.getCssValues().get(0).getSelector(), menuFonts.getCssValues().get(0).getSelector());
        Assert.assertEquals(newMenuFonts.getCssValues().get(0).getValue(), menuFonts.getCssValues().get(0).getValue());

        Assert.assertEquals(newMenu.getMenuStructure(), menu.getMenuStructure());
        Assert.assertNotSame(newMenu.getMenuItems().size(), menu.getMenuItems().size());
        Assert.assertEquals("We don`t copying children", 0, newMenu.getMenuItems().size());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}