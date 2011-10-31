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
package com.shroggle.entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.List;

/**
 * @author Artem Stasuk
 */
@Entity(name = "widgetItems")
public class WidgetItem extends Widget {

    /**
     * If you need work version of this draft item you have to use WidgetManager.getItem() or ItemManager.getItem()
     * with SiteShowOption.OUTSIDE_APP parameter
     */
    @ManyToOne
    @ForeignKey(name = "widgetItemsDraftItemId")
    @JoinColumn(name = "itemId")
    private DraftItem draftItem;



    @Override
    public ItemType getItemType() {
        return this.draftItem.getItemType();
    }

    @Override
    public String getItemName() {
        return this.draftItem.getName();
    }

    @Override
    public boolean isWidgetItem(){
        return true;
    }

    @Override
    public boolean isWidgetComposit() {
        return false;
    }

    public DraftItem getDraftItem() {
        return draftItem;
    }

    public void setDraftItem(DraftItem draftItem) {
        this.draftItem = draftItem;
    }

    public int getSiteId() {
        return getSite().getSiteId();
    }

    public List<Group> getAvailableGroups() {
        return getSite().getAvailableGroups();
    }
}