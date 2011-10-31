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
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import com.shroggle.entity.SiteOnItemRightType;

/**
 * @author Artem Stasuk
 */
@DataTransferObject
public class ShowShareUserItem {

    public String getItemSiteName() {
        return itemSiteName;
    }

    public void setItemSiteName(String itemSiteName) {
        this.itemSiteName = itemSiteName;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public SiteOnItemRightType getShareType() {
        return shareType;
    }

    public void setShareType(SiteOnItemRightType shareType) {
        this.shareType = shareType;
    }

    @RemoteProperty
    private int itemId;

    @RemoteProperty
    private ItemType itemType;

    @RemoteProperty
    private String itemName;

    @RemoteProperty
    private SiteOnItemRightType shareType;

    @RemoteProperty
    private String itemSiteName;

}
