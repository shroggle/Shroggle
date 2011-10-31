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

import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.copier.CopierUtil;

import java.util.List;

/**
 * Copy menu items as is. You can use this only if new item has same site id with old items.
 *
 * @author Artem Stasuk
 */

public class ItemCopierWithSameMenuItems implements ItemCopier {

    public ItemCopierWithSameMenuItems(final ItemCopier itemCopier) {
        this.itemCopier = itemCopier;
    }

    @Override
    public ItemCopyResult execute(final ItemCopierContext context, final DraftItem draftItem, final Widget widget) {
        final ItemCopyResult itemCopyResult = itemCopier.execute(context, draftItem, widget);
        if (itemCopyResult.isCopied() && draftItem instanceof DraftMenu) {
            final DraftMenu draftMenu = (DraftMenu) draftItem;
            final DraftMenu copiedDraftMenu = (DraftMenu) itemCopyResult.getDraftItem();

            copyMenu(draftMenu, copiedDraftMenu);
        }
        return itemCopyResult;
    }

    public static void copyMenu(final DraftMenu draftMenu, final DraftMenu copiedDraftMenu) {
        final List<MenuItem> menuItems = draftMenu.getMenuItems();
        copyMenuItems(copiedDraftMenu, null, menuItems);
    }

    private static void copyMenuItems(
            final DraftMenu copiedDraftMenu, final DraftMenuItem copiedDraftMenuItemParent,
            final List<MenuItem> menuItems) {
        for (final MenuItem menuItem : menuItems) {
            final DraftMenuItem copiedDraftMenuItem = new DraftMenuItem();
            copiedDraftMenuItem.setMenu(copiedDraftMenu);
            copiedDraftMenu.addChild(copiedDraftMenuItem);
            if (copiedDraftMenuItemParent != null) {
                copiedDraftMenuItem.setParent(copiedDraftMenuItemParent);
            }
            CopierUtil.copyProperties(menuItem, copiedDraftMenuItem, "Menu", "Children", "Id", "Parent");
            ServiceLocator.getPersistance().putMenuItem(copiedDraftMenuItem);
            copyMenuItems(copiedDraftMenu, copiedDraftMenuItem, menuItem.getChildren());
        }
    }

    private final ItemCopier itemCopier;

}
