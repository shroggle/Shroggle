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

import com.shroggle.entity.ItemType;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.paginator.Paginator;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
public class UserItemsProvider {

    public Paginator executeWithPaginator(final ItemType itemType, final UserItemsSortType sortType, final boolean DESC,
                                          final int filterOwnerSiteId, Integer pageNumber, String searchKeyByItemName) {
        final List<UserItemsInfo> siteItems = execute(itemType, sortType, DESC, filterOwnerSiteId, searchKeyByItemName);
        return new Paginator<UserItemsInfo>(siteItems).setPageNumber(pageNumber);
    }

    //filterOwnerSiteId - -1 indicates no filter by owner site id.

    public List<UserItemsInfo> execute(final ItemType itemType, final UserItemsSortType sortType, final boolean DESC,
                                       final int filterOwnerSiteId, String searchKeyByItemName) {

        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();

        //Get list of all available items.
        final List<ItemManager> siteItems =
                ItemManager.siteItemsToManagers(userRightManager.getSiteItemsForView(itemType));

        //Apply filters.
        final List<ItemManager> filteredItems = new UserItemsFilter().filter(siteItems, filterOwnerSiteId, searchKeyByItemName);

        //Sort items.
        new UserItemsSorter().execute(filteredItems, sortType, DESC);

        return constructInfos(filteredItems);
    }

    protected List<UserItemsInfo> constructInfos(final List<ItemManager> items) {
        final List<UserItemsInfo> returnList = new ArrayList<UserItemsInfo>();
        for (ItemManager item : items) {
            returnList.add(new UserItemsInfo(item));
        }
        return returnList;
    }

}
