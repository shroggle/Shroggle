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
import com.shroggle.logic.SiteOnItemManager;
import com.shroggle.logic.site.item.ItemManager;

import java.util.Date;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
public class UserItemsInfo {

    public UserItemsInfo(ItemManager item) {
        this.item = item;
    }

    private ItemManager item;


    public List<SiteOnItemManager> getRights() {
        return item.getRights();
    }

    public int getId() {
        return item.getId();
    }

    public ItemType getType() {
        return item.getType();
    }

    public String getName() {
        return item.getName();
    }

    public Date getItemCreatedDate() {
        return item.getItemCreatedDate();
    }

    public Date getItemUpdatedDate() {
        return item.getLastRecordDate();
    }

    public int getRecordCount() {
        return item.getRecordsCount();
    }

    public String getMaskedName() {
        return getName().replaceAll("'", "\\\\'");
    }
}
