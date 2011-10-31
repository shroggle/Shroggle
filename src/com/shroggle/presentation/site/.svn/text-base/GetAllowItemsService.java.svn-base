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

import com.shroggle.entity.*;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.*;

@RemoteProxy
public class GetAllowItemsService extends AbstractService {

    @RemoteMethod
    public List<GetAllowItem> execute(final GetAllowItemsRequest request) {
        final UserManager userManager = new UsersManager().getLogined();
        userManager.getRight().getSiteRight().getSiteForView(request.getSiteId()).getSite();

        final List<GetAllowItem> allowItems = new ArrayList<GetAllowItem>();

        if (request.getItemType() != null) {
            final Map<DraftItem, List<WidgetItem>> a = persistance.getItemsBySiteAndUser(
                    userManager.getUserId(), request.getSiteId(), request.getItemType(),
                    request.isOnlyCurrentSite());

            for (final DraftItem draftItem : a.keySet()) {

                if (request.getItemType() == ItemType.VOTING) {
                    final DraftGallery draftGallery = (DraftGallery) draftItem;
                    if (!draftGallery.isIncludesVotingModule()) {
                        continue;
                    }
                }

                if (request.getItemType() == ItemType.E_COMMERCE_STORE) {
                    final DraftGallery draftGallery = (DraftGallery) draftItem;
                    if (!draftGallery.getPaypalSettings().isEnable()) {
                        continue;
                    }
                }

                final List<WidgetItem> widgetItems = a.get(draftItem);
                if (widgetItems.isEmpty()) {
                    allowItems.add(new GetAllowItem(draftItem));
                }

                for (final WidgetItem widgetItem : widgetItems) {
                    int countOfRepeatedItemsOnSamePage =
                            getCountOfRepeatedItemsOnSamePage(allowItems, draftItem, widgetItem);
                    allowItems.add(new GetAllowItem(draftItem, widgetItem, countOfRepeatedItemsOnSamePage));
                }
            }
        }

        Collections.sort(allowItems, itemsToInsertComparator);

        return allowItems;
    }

    private int getCountOfRepeatedItemsOnSamePage(
            final List<GetAllowItem> allowItems, final DraftItem draftItem,
            final WidgetItem widgetItem) {
        int count = 0;
        for (final GetAllowItem allowItem : allowItems) {
            if (allowItem.getId() == draftItem.getId() && allowItem.getPageId() != null &&
                    allowItem.getPageId() == widgetItem.getPage().getPageId()) {
                count++;
            }
        }

        return count;
    }

    private static final Comparator<GetAllowItem> itemsToInsertComparator = new Comparator<GetAllowItem>() {

        public int compare(final GetAllowItem i1, final GetAllowItem i2) {
            return i1.getName().compareToIgnoreCase(i2.getName());
        }

    };

    private final Persistance persistance = ServiceLocator.getPersistance();

}