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
package com.shroggle.presentation.purchaseHistory;

import com.shroggle.entity.DraftPurchaseHistory;
import com.shroggle.entity.ItemType;
import com.shroggle.entity.Site;
import com.shroggle.entity.Widget;
import com.shroggle.exception.PurchaseHistoryNameNotUniqueException;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.FunctionalWidgetInfo;
import com.shroggle.presentation.site.GetFunctionalWidgetsService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.Locale;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class SavePurchaseHistoryService extends AbstractService {

    @RemoteMethod
    public FunctionalWidgetInfo execute(final Integer widgetId, final DraftPurchaseHistory draftPurchaseHistory) throws Exception {
        final UserManager userManager = new UsersManager().getLogined();

        final Widget widget;
        if (widgetId != null) {
            widget = userManager.getRight().getSiteRight().getWidgetForEditInPresentationalService(widgetId);
        } else {
            widget = null;
        }

        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                final DraftPurchaseHistory tempDraftPurchaseHistory = userManager.getSiteItemForEditById(
                        draftPurchaseHistory.getId(), ItemType.PURCHASE_HISTORY);

                final Site site;
                if (widget != null) {
                    site = widget.getSite();
                } else {
                    site = persistance.getSite(tempDraftPurchaseHistory.getSiteId());
                }

                final DraftPurchaseHistory duplicatePurchaseHistory = persistance.getPurchaseHistoryByNameAndSiteId(
                        draftPurchaseHistory.getName(), site.getSiteId());
                if (duplicatePurchaseHistory != null && duplicatePurchaseHistory != tempDraftPurchaseHistory) {
                    throw new PurchaseHistoryNameNotUniqueException(international.get("PurchaseHistoryNameNotUniqueException"));
                }

                tempDraftPurchaseHistory.setShowDescription(draftPurchaseHistory.isShowDescription());
                tempDraftPurchaseHistory.setDescription(draftPurchaseHistory.getDescription());
                tempDraftPurchaseHistory.setName(draftPurchaseHistory.getName());
                tempDraftPurchaseHistory.setShowProductImage(draftPurchaseHistory.isShowProductImage());
                tempDraftPurchaseHistory.setShowProductDescription(draftPurchaseHistory.isShowProductDescription());
                tempDraftPurchaseHistory.setImageHeight(draftPurchaseHistory.getImageHeight());
                tempDraftPurchaseHistory.setImageWidth(draftPurchaseHistory.getImageWidth());
            }

        });

        return new GetFunctionalWidgetsService().createFunctionalWidgetInfo(widget, "widget", true);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final International international = ServiceLocator.getInternationStorage().get("purchaseHistory", Locale.US);

}
