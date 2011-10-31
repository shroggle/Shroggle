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

import com.shroggle.entity.DraftItem;
import com.shroggle.entity.WidgetItem;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

/**
 * @author dmitry.solomadin
 */
public class ItemsManager {

    public <T extends DraftItem> ConfigureItemData<T> getConfigureItemData(Integer widgetId, Integer itemId) {
        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();

        if (widgetId == null && itemId == null) {
            throw new IllegalArgumentException("Both widgetId and imageId cannot be null.");
        }

        final ConfigureItemData<T> itemData = new ConfigureItemData<T>();
        if (widgetId == null) {
            // edit forum from dashboard or manage items.
            final T draftItem = persistance.<T>getDraftItem(itemId);
            if (draftItem == null) {
                throw new IllegalArgumentException("Cannot find draft item by Id=" + itemId);
            }

            itemData.setDraftItem(draftItem);
            itemData.setSite(persistance.getSite(draftItem.getSiteId()));
        } else {
            final WidgetItem widget = userRightManager.getSiteRight().getWidgetItemForEditInPresentationalService(
                    widgetId);
            itemData.setWidget(widget);
            itemData.setSite(widget.getSite());

            if (widget.getDraftItem() != null) {
                itemData.setDraftItem((T) widget.getDraftItem());
            } else {
                throw new IllegalArgumentException("Seems like widget with Id= " + widgetId + " got no item.");
            }
        }

        return itemData;
    }

    private Persistance persistance = ServiceLocator.getPersistance();

}
