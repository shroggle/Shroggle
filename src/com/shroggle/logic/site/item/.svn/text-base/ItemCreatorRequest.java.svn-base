package com.shroggle.logic.site.item;

import com.shroggle.entity.ItemType;
import com.shroggle.entity.Site;

/**
* @author Artem Stasuk
*/
public class ItemCreatorRequest {

    public ItemCreatorRequest(Integer itemId, boolean copyContent, ItemType itemType, Site site) {
        this.itemId = itemId;
        this.copyContent = copyContent;
        this.itemType = itemType;
        this.site = site;
    }

    public ItemCreatorRequest(ItemType itemType, Site site) {
        this.itemId = null;
        this.copyContent = false;
        this.itemType = itemType;
        this.site = site;
    }

    private Integer itemId;

    private boolean copyContent;

    private ItemType itemType;

    private Site site;

    public Integer getItemId() {
        return itemId;
    }

    public boolean isCopyContent() {
        return copyContent;
    }

    public Site getSite() {
        return site;
    }

    public ItemType getItemType() {
        return itemType;
    }
}
