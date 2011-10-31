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

import com.shroggle.entity.DraftItem;
import com.shroggle.entity.Site;
import com.shroggle.entity.WidgetItem;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

/**
 * @author Artem Stasuk
 */
@DataTransferObject
public class GetAllowItem {

    public GetAllowItem(DraftItem draftItem, WidgetItem widgetItem, int countRepeatedItemsOnSamePage) {
        id = draftItem.getId();

        final PageManager pageVersion = new PageManager(widgetItem.getPage());
        pageId = pageVersion.getPageId();
        pageName = pageVersion.getName();

        final Site site = pageVersion.getPage().getSite();
        siteName = new SiteManager(site).getName();

        final String itemName = ItemManager.getTitle(draftItem);
        name = StringUtil.cutIfNeed(siteName + " / " + pageName + " / " + itemName +
                (countRepeatedItemsOnSamePage >= 1 ? " (" + (countRepeatedItemsOnSamePage + 1) + ")" : ""), 50);
    }

    public GetAllowItem(DraftItem draftItem) {
        pageId = null;
        pageName = null;
        id = draftItem.getId();
        final Site site = ServiceLocator.getPersistance().getSite(draftItem.getSiteId());

        if (site == null) {
            siteName = null;
            name = StringUtil.cutIfNeed(draftItem.getName(), 50);
        } else {
            siteName = new SiteManager(site).getName();
            name = StringUtil.cutIfNeed(siteName + " / " +
                    draftItem.getName(), 50);
        }
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Integer getPageId() {
        return pageId;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getPageName() {
        return pageName;
    }

    @RemoteProperty
    private final int id;

    @RemoteProperty
    private final String name;

    private final String siteName;

    private final String pageName;

    private final Integer pageId;

}
