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
import com.shroggle.logic.site.item.ItemCopier;
import com.shroggle.logic.site.item.ItemCopierContext;
import com.shroggle.logic.site.item.ItemCopyResult;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.Date;
import java.util.Map;

/**
 * ItemCopier.copy
 * BlueprintCopier.copy
 *
 * @author Stasuk Artem
 */
public class ItemCopierBlueprint implements ItemCopier {

    public ItemCopierBlueprint(
            final ItemCopier itemCopier, final Site blueprint, final Map<Integer, Integer> crossWidgetIds) {
        this.itemCopier = itemCopier;
        this.blueprint = blueprint;
        this.crossWidgetIds = crossWidgetIds;
    }

    @Override
    public ItemCopyResult execute(ItemCopierContext context, final DraftItem draftItem, final Widget widget) {
        final ItemCopyResult result = itemCopier.execute(context, draftItem, widget);

        if (result.isCopied()) {
            if (result.getDraftItem() instanceof DraftBlogSummary) {
                final DraftBlogSummary draftBlogSummary = (DraftBlogSummary) draftItem;
                final DraftBlogSummary copyDraftBlogSummary = (DraftBlogSummary) result.getDraftItem();
                copyDraftBlogSummary.getIncludedCrossWidgetId().clear();
                for (final Integer crossWidgetId : draftBlogSummary.getIncludedCrossWidgetId()) {
                    copyDraftBlogSummary.getIncludedCrossWidgetId().add(crossWidgetIds.get(crossWidgetId));
                }
            }

            if (result.getDraftItem() instanceof DraftContactUs) {
                final DraftContactUs copiedDraftContactUs = (DraftContactUs) result.getDraftItem();
                final User user = new UsersManager().getLoginedUser();
                if (user != null) {
                    copiedDraftContactUs.setEmail(user.getEmail());
                }
            }

            if (result.getDraftItem() instanceof DraftGallery && ((DraftGallery) result.getDraftItem()).isIncludeECommerce()) {
                final DraftGallery copiedDraftGallery = (DraftGallery) result.getDraftItem();
                final User user = new UsersManager().getLoginedUser();
                if (user != null) {
                    copiedDraftGallery.getPaypalSettings().setPaypalEmail(user.getEmail());
                }
            }

            final SiteOnItem itemRight = ((DraftItem) result.getDraftItem()).createSiteOnItemRight(blueprint);
            itemRight.setAcceptDate(new Date());
            itemRight.setFromBlueprint(true);
            itemRight.setType(SiteOnItemRightType.READ);
            persistance.putSiteOnItem(itemRight);
        }

        return result;
    }

    private final Site blueprint;
    private final ItemCopier itemCopier;
    private final Map<Integer, Integer> crossWidgetIds;
    private final Persistance persistance = ServiceLocator.getPersistance();

}