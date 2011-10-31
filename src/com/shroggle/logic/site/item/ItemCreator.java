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

import com.shroggle.entity.*;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.copier.stack.CopierStack;
import com.shroggle.util.copier.stack.CopierStackExecutorNone;
import com.shroggle.util.copier.stack.CopierStackSimple;
import com.shroggle.util.persistance.Persistance;

import java.util.Collections;
import java.util.Map;

/**
 * @author Balakirev Anatoliy
 */
public class ItemCreator {

    public static DraftItem create(final ItemCreatorRequest request) {
        final Persistance persistance = ServiceLocator.getPersistance();

        if (request.getItemId() != null) {
            final DraftItem draftItem = persistance.getDraftItem(request.getItemId());

            if (isItemCanBeInserted(request.getSite(), draftItem)) {
                if (request.isCopyContent()) {
                    return copy(request, draftItem);
                } else {
                    return share(request);
                }
            }
        } else {
            return createNew(request);
        }

        return null;
    }

    private static DraftItem createNew(final ItemCreatorRequest request) {
        return ItemCreators.newInstance(request.getItemType()).create(request.getSite().getSiteId());
    }

    private static DraftItem share(final ItemCreatorRequest request) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final DraftItem item = persistance.getDraftItem(request.getItemId());

        //If this item is from the other user site(user should have admin access to this site)
        //and it's not shared yet then share it.
        if (isItemFromUserSite(item) && item.getSiteId() != request.getSite().getSiteId()) {
            new ItemManager(item).share(request.getSite(), SiteOnItemRightType.EDIT);

            if (item.getItemType() == ItemType.GALLERY) {
                final DraftForm draftForm = persistance.getDraftItem(((DraftGallery) item).getFormId1());
                // Share gallery form if it's from other site too.
                if (draftForm.getSiteId() != request.getSite().getSiteId()) {
                    new ItemManager(draftForm).share(request.getSite(), SiteOnItemRightType.EDIT);
                }
            }
        }

        return item;
    }

    private static DraftItem copy(final ItemCreatorRequest request, final DraftItem draftItem) {
        /**
         * This parameter need for menu for map page ids. If it null we copy menu item and
         * link in they on pages as it, if empty we don't copy menu items! 
         */
        final Map<Integer,Integer> pageIds = request.getSite().getId() == draftItem.getSiteId()
                ? null : Collections.<Integer, Integer>emptyMap();

        final CopierStack stack = new CopierStackSimple(
                new CopierStackExecutorFirst(
                        draftItem,
                        new CopierStackExecutorItemFull(
                                new ItemNamingRealWithAddingCopyWord(),
                                request.getSite().getId(),
                                pageIds),
                        new CopierStackExecutorShare(
                                request.getSite(),
                                new CopierStackExecutorNone()
                        )));
        return stack.copy(draftItem);
    }

    private static boolean isItemFromUserSite(final DraftItem draftItem) {
        return new UsersManager().getLogined().getRight().getAvailableSiteIds().contains(draftItem.getSiteId());
    }

    private static boolean isItemCanBeInserted(final Site site, final DraftItem draftItem) {
        // Item can be inserted only if it belongs to one of the user sites or it's shared with this site.
        return draftItem != null && (isItemFromUserSite(draftItem) || new ItemManager(draftItem).isSharedWithSite(site));
    }

}
