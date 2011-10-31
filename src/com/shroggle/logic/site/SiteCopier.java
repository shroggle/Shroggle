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
package com.shroggle.logic.site;

import com.shroggle.entity.*;
import com.shroggle.logic.site.item.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.PagesWithoutSystem;

import java.util.List;

/**
 * @author Stasuk Artem
 */
public class SiteCopier {

    public SiteCopier(final Site copiedSite) {
        this(copiedSite, new ItemNamingSameAsInOriginal());
    }

    public SiteCopier(final Site copiedSite, final ItemNaming itemNaming) {
        context.setCopiedSite(copiedSite);
        context.setItemNaming(itemNaming);
    }

    public void copyFrom(final Site site) {
        final List<Page> pages = PagesWithoutSystem.get(site.getPages());
        final List<Page> copiedPages = SiteCopierUtil.copyPages(
                pages, true, context.getCopiedSite(), context.getPageToCopiedPageIds(), new WidgetCrossIdSetterNone());

        ItemCopierUtil.copyItemsWithoutDraftAndSetCorrectPageIds(
                context.getPageToCopiedPageIds(), site.getMenu().getMenuItems(),
                context.getCopiedSite().getMenu(), null);

        itemCopier = new ItemCopierCache(new ItemCopierSimple());

        for (final Page page : copiedPages) {
            for (final Widget widget : new PageManager(page).getWidgets()) {
                copyWidget(widget);
            }
        }
    }

    private void copyWidget(final Widget widget) {
        if (!widget.isWidgetItem()) {
            return;
        }

        final WidgetItem widgetItem = (WidgetItem) widget;
        if (widgetItem.getDraftItem() == null) {
            return;
        }

        final DraftItem siteItem = widgetItem.getDraftItem();
        final DraftItem copiedSiteItem = (DraftItem) itemCopier.execute(context, siteItem, widget).getDraftItem();
        widgetItem.setDraftItem(copiedSiteItem);
    }

    private final ItemCopierContext context = new ItemCopierContext();
    private ItemCopier itemCopier;

}