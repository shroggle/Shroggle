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

import com.shroggle.entity.DraftItem;
import com.shroggle.entity.Item;
import com.shroggle.entity.Widget;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stasuk Artem
 */
public class ItemCopierCache implements ItemCopier {

    public ItemCopierCache(final ItemCopier itemCopier) {
        this.itemCopier = itemCopier;
    }

    @Override
    public ItemCopyResult execute(ItemCopierContext context, final DraftItem draftItem, final Widget widget) {
        if (copiedItems.containsKey(draftItem)) {
            return new ItemCopyResult(copiedItems.get(draftItem), false);
        }

        final ItemCopyResult result = itemCopier.execute(context, draftItem, widget);
        copiedItems.put(draftItem, result.getDraftItem());

        return result;
    }

    private final ItemCopier itemCopier;
    private final Map<Item, Item> copiedItems = new HashMap<Item, Item>();

}