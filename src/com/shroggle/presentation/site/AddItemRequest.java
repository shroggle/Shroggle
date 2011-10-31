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
public class AddItemRequest {

    private Integer itemId;

    private Integer siteId;

    private ItemType itemType;

    private boolean copyContent;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public boolean isCopyContent() {
        return copyContent;
    }

    public void setCopyContent(boolean copyContent) {
        this.copyContent = copyContent;
    }
}
