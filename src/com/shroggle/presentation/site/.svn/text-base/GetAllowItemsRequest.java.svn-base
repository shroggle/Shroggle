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
package com.shroggle.presentation.site;

import com.shroggle.entity.ItemType;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

/**
 * @author Artem Stasuk
 */
@DataTransferObject
public class GetAllowItemsRequest {

    public boolean isOnlyCurrentSite() {
        return onlyCurrentSite;
    }

    public void setOnlyCurrentSite(boolean onlyCurrentSite) {
        this.onlyCurrentSite = onlyCurrentSite;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    @RemoteProperty
    private boolean onlyCurrentSite;

    @RemoteProperty
    private ItemType itemType;

    @RemoteProperty
    private int siteId;

}
