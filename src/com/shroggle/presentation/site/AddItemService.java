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
import com.shroggle.logic.site.item.ItemCreator;
import com.shroggle.logic.site.item.ItemCreatorRequest;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.PersistanceTransactionContext;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;


/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class AddItemService {

    @RemoteMethod
    public AddItemResponse execute(final AddItemRequest request) {
        new UsersManager().getLogined();

        final DraftItem draftItem = persistanceTransaction.execute(new PersistanceTransactionContext<DraftItem>() {
            @Override
            public DraftItem execute() {
                return ItemCreator.create(new ItemCreatorRequest(request.getItemId(), request.isCopyContent(),
                        request.getItemType(), persistance.getSite(request.getSiteId())));
            }
        });
        return new AddItemResponse(draftItem.getId(), draftItem.getItemType());
    }

    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();
}
