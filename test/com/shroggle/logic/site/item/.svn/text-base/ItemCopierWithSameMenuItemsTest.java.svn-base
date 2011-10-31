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
package com.shroggle.logic.site.item;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.DraftMenu;
import com.shroggle.entity.DraftMenuItem;
import com.shroggle.entity.Site;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class ItemCopierWithSameMenuItemsTest {

    @Test
    public void copyWithoutItems() {
        final Site site = TestUtil.createSite();
        final DraftMenu draftMenu = new DraftMenu();
        persistance.putItem(draftMenu);

        final ItemCopierContext context = new ItemCopierContext();
        context.setCopiedSite(site);
        context.setItemNaming(new ItemNamingNextFreeName());
        final ItemCopier itemCopier = new ItemCopierWithSameMenuItems(
                new ItemCopierCache(new ItemCopierSimple()));
        final ItemCopyResult itemCopyResult = itemCopier.execute(context, draftMenu, null);
        final DraftMenu copiedDraftMenu = (DraftMenu) itemCopyResult.getDraftItem();

        Assert.assertEquals(draftMenu.getMenuItems().size(), copiedDraftMenu.getMenuItems().size());
    }

    @Test
    public void copy() {
        final Site site = TestUtil.createSite();
        final DraftMenu draftMenu = new DraftMenu();
        persistance.putItem(draftMenu);

        final DraftMenuItem draftMenuItem = new DraftMenuItem();
        draftMenuItem.setMenu(draftMenu);
        draftMenu.addChild(draftMenuItem);
        draftMenuItem.setName("fff");
        persistance.putMenuItem(draftMenuItem);

        final DraftMenuItem draftMenuItem1 = new DraftMenuItem();
        draftMenuItem1.setMenu(draftMenu);
        draftMenuItem1.setParent(draftMenuItem);
        draftMenuItem1.setName("ffa");
        persistance.putMenuItem(draftMenuItem1);

        final ItemCopierContext context = new ItemCopierContext();
        context.setCopiedSite(site);
        context.setItemNaming(new ItemNamingNextFreeName());
        final ItemCopier itemCopier = new ItemCopierWithSameMenuItems(
                new ItemCopierCache(new ItemCopierSimple()));
        final ItemCopyResult itemCopyResult = itemCopier.execute(context, draftMenu, null);
        final DraftMenu copiedDraftMenu = (DraftMenu) itemCopyResult.getDraftItem();

        Assert.assertEquals(draftMenu.getMenuItems().size(), copiedDraftMenu.getMenuItems().size());
        Assert.assertEquals("fff", copiedDraftMenu.getMenuItems().get(0).getName());
        Assert.assertEquals(draftMenu.getMenuItems().get(0).getChildren().size(), copiedDraftMenu.getMenuItems().get(0).getChildren().size());
        Assert.assertEquals("ffa", copiedDraftMenu.getMenuItems().get(0).getChildren().get(0).getName());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}
