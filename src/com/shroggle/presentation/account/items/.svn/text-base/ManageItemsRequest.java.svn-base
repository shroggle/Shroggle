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
package com.shroggle.presentation.account.items;

import com.shroggle.entity.ItemType;
import com.shroggle.logic.user.items.UserItemsSortType;
import org.directwebremoting.annotations.DataTransferObject;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
public class ManageItemsRequest {

    private UserItemsSortType sortType;

    private boolean DESC;

    private int filterByOwnerSiteId; // -1 indicates no filter.

    private Integer pageNumber;

    private ItemType itemType;

    private String searchKeyByItemName;

    public String getSearchKeyByItemName() {
        return searchKeyByItemName;
    }

    public void setSearchKeyByItemName(String searchKeyByItemName) {
        this.searchKeyByItemName = searchKeyByItemName;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getFilterByOwnerSiteId() {
        return filterByOwnerSiteId;
    }

    public void setFilterByOwnerSiteId(int filterByOwnerSiteId) {
        this.filterByOwnerSiteId = filterByOwnerSiteId;
    }

    public UserItemsSortType getSortType() {
        return sortType;
    }

    public void setSortType(UserItemsSortType sortType) {
        this.sortType = sortType;
    }

    public boolean isDESC() {
        return DESC;
    }

    public void setDESC(boolean DESC) {
        this.DESC = DESC;
    }
}
