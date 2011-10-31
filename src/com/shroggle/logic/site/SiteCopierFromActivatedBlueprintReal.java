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
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.List;

/**
 * @author Stasuk Artem
 */
public class SiteCopierFromActivatedBlueprintReal implements SiteCopierFromActivatedBlueprint {

    @Override
    public void execute(final Site blueprint, final Site site, final boolean publish) {
        site.setThemeId(blueprint.getThemeId());
        
        final List<Page> blueprintPages = PagesWithoutSystem.get(blueprint.getPages());
        final WidgetCrossIdSetterReal widgetCrossIdSetterReal = new WidgetCrossIdSetterReal();

        final ItemCopierContext context = new ItemCopierContext();
        context.setCopiedSite(site);
        context.setItemNaming(new ItemNamingSameAsInOriginal());

        final List<Page> pages = SiteCopierUtil.copyPages(
                blueprintPages, publish, site, context.getPageToCopiedPageIds(), widgetCrossIdSetterReal);

        // We need to remain in the site menu only copied from blueprint pages and create correct pageIds. Tolik
        ItemCopierUtil.copyItemsWithoutDraftAndSetCorrectPageIds(
                context.getPageToCopiedPageIds(), blueprint.getMenu().getMenuItems(), site.getMenu(), null);

        final ItemCopier itemCopier =
                new ItemCopierWithPublish(
                        new ItemCopierBlueprint(
                                new ItemCopierCache(
                                        new ItemCopierSimple()),
                                blueprint, widgetCrossIdSetterReal.get()));

        // Here we are copying or sharing widgets content for pages copied from blueprint to connected site.
        final ItemPosterCache itemPoster = new ItemPosterCache();
        for (final Page page : pages) {
            final PageManager pageManager = new PageManager(page);
            for (final Widget widget : pageManager.getWidgets()) {
                copyOrShareWidgetsContent(site, pageManager.isBlueprintLocked(), widget, itemCopier, context);
                if (publish) {
                    WidgetPoster.post(widget, itemPoster);
                }
            }
        }
    }

    private void copyOrShareWidgetsContent(
            final Site site, final boolean blueprintLocked, final Widget widget,
            final ItemCopier itemCopier, final ItemCopierContext context) {
        final Persistance persistance = ServiceLocator.getPersistance();

        widget.setCreatedByBlueprintWidget(true);

        if (!widget.isWidgetItem()) {
            return;
        }

        final WidgetItem widgetItem = (WidgetItem) widget;
        if (widgetItem.getDraftItem() == null) {
            return;
        }

        final DraftItem siteItem = widgetItem.getDraftItem();
        if (widget.isBlueprintShareble()) {
            if (siteItem instanceof DraftGallery) {
                final DraftGallery gallery = (DraftGallery) siteItem;
                final DraftForm form = persistance.getFormById(gallery.getFormId1());
                SiteCopierUtil.shareSiteItem(site, form, SiteOnItemRightType.READ);

                if (gallery.getVoteSettings().getRegistrationFormIdForVoters() != null) {
                    final DraftForm votingRegistrationForm =
                            persistance.getFormById(gallery.getVoteSettings().getRegistrationFormIdForVoters());
                    SiteCopierUtil.shareSiteItem(site, votingRegistrationForm, SiteOnItemRightType.READ);
                }
            }

            final SiteOnItemRightType siteOnItemRightType =
                    !widget.isBlueprintEditable() || blueprintLocked
                            ? SiteOnItemRightType.READ : SiteOnItemRightType.EDIT;

            SiteCopierUtil.shareSiteItem(site, siteItem, siteOnItemRightType);
        } else {
            final DraftItem copiedSiteItem = (DraftItem) itemCopier.execute(context, siteItem, widget).getDraftItem();
            widgetItem.setDraftItem(copiedSiteItem);
        }
    }

}