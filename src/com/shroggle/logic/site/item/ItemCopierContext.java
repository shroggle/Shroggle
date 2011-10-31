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
package com.shroggle.logic.site.item;

import com.shroggle.entity.Site;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Artem Stasuk
 */
public class ItemCopierContext {

    public ItemNaming getItemNaming() {
        return itemNaming;
    }

    public void setItemNaming(ItemNaming itemNaming) {
        this.itemNaming = itemNaming;
    }

    public Site getCopiedSite() {
        return copiedSite;
    }

    public void setCopiedSite(Site copiedSite) {
        this.copiedSite = copiedSite;
    }

    public Map<Integer, Integer> getPageToCopiedPageIds() {
        return pageToCopiedPageIds;
    }

    private ItemNaming itemNaming;
    private Site copiedSite;
    private final Map<Integer, Integer> pageToCopiedPageIds = new HashMap<Integer, Integer>();

}