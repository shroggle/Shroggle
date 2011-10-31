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

import com.shroggle.entity.DraftItem;
import com.shroggle.entity.Widget;
import com.shroggle.logic.site.item.*;

/**
 * ItemCopier.copy
 * BlueprintCopier.copy
 *
 * @author Stasuk Artem
 */
public class ItemCopierWithPublish implements ItemCopier {

    public ItemCopierWithPublish(final ItemCopier itemCopier) {
        this.itemCopier = itemCopier;
    }

    @Override
    public ItemCopyResult execute(ItemCopierContext context, final DraftItem draftItem, final Widget widget) {
        final ItemCopyResult result = itemCopier.execute(context, draftItem, widget);

        if (result.isCopied()) {
            WidgetPoster.post(widget, itemPoster);
        }

        return result;
    }

    private final ItemPoster itemPoster = new ItemPosterCache();
    private final ItemCopier itemCopier;

}