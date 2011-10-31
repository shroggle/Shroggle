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

import com.shroggle.entity.Item;

import java.util.Map;

/**
 * @author Artem Stasuk
 */
public class ItemCopyResult {

    public ItemCopyResult(Item draftItem, boolean copied) {
        this.draftItem = draftItem;
        this.copied = copied;
        this.formItemsIdWithCopiedEquivalents = null;
    }

    public ItemCopyResult(Item draftItem, boolean copied, Map<Integer, Integer> formItemsIdWithCopiedEquivalents) {
        this.draftItem = draftItem;
        this.copied = copied;
        this.formItemsIdWithCopiedEquivalents = formItemsIdWithCopiedEquivalents;
    }

    public boolean isCopied() {
        return copied;
    }

    public Item getDraftItem() {
        return draftItem;
    }

    public Map<Integer, Integer> getFormItemsIdWithCopiedEquivalents() {
        return formItemsIdWithCopiedEquivalents;
    }

    private final Item draftItem;
    private final boolean copied;
    private final Map<Integer, Integer> formItemsIdWithCopiedEquivalents;

}
