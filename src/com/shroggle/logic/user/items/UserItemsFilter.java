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
package com.shroggle.logic.user.items;

import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.util.StringUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author dmitry.solomadin, Balakirev Anatoliy
 */
public class UserItemsFilter {

    public List<ItemManager> filter(final List<ItemManager> inputItems, final int filterByOwnerSiteId, String searchKeyByItemName) {
        if (filterByOwnerSiteId <= 0 && StringUtil.isNullOrEmpty(searchKeyByItemName)) {
            return inputItems;
        }

        final List<ItemManager> filteredItems = new ArrayList<ItemManager>(inputItems);

        //Filtering by ownerSiteId.
        if (filterByOwnerSiteId > 0) {
            for (final Iterator<ItemManager> iterator = filteredItems.iterator(); iterator.hasNext();) {
                if (iterator.next().getOwnerSiteId() != filterByOwnerSiteId) {
                    iterator.remove();
                }
            }
        }

        //Filtering by itemName.
        if (!StringUtil.isNullOrEmpty(searchKeyByItemName)) {
            searchKeyByItemName = searchKeyByItemName.toLowerCase();
            for (final Iterator<ItemManager> iterator = filteredItems.iterator(); iterator.hasNext();) {
                final String itemName = iterator.next().getName().toLowerCase();
                if (!itemName.contains(searchKeyByItemName)) {
                    iterator.remove();
                }
            }
        }

        return filteredItems;
    }

}
