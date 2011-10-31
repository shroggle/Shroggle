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

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject
public class AddItemResponse {

    public AddItemResponse() {
    }

    public AddItemResponse(int itemId, ItemType itemType) {
        this.itemId = itemId;
        this.itemType = itemType;
    }

    private int itemId;

    private ItemType itemType;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }
}
