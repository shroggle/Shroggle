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

import java.util.HashSet;
import java.util.Set;

/**
 * Doesn't post same draft item twice.
 *
 * @author Artem Stasuk
 */
public class ItemPosterCache implements ItemPoster {

    @Override
    public void publish(final DraftItem draftItem) {
        if (!alreadyPosted.contains(draftItem)) {
            itemPoster.publish(draftItem);
            alreadyPosted.add(draftItem);
        }
    }

    private final ItemPoster itemPoster = new ItemPosterReal();
    private final Set<DraftItem> alreadyPosted = new HashSet<DraftItem>();

}
