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
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteOnItemRightType;
import com.shroggle.entity.WidgetItem;
import com.shroggle.exception.PurchaseHistoryNotFoundException;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.logic.widget.WidgetTitleGetter;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.presentation.site.WithWidgetTitle;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class ConfigurePurchaseHistoryService extends AbstractService implements WithWidgetTitle {

    @RemoteMethod
    public void execute(final Integer widgetId, final Integer purchaseHistoryId) throws Exception {
        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();

        if (widgetId == null && purchaseHistoryId == null) {
            throw new IllegalArgumentException("Both widgetId and purchaseHistoryId cannot be null. " +
                    "This service is only for configuring existing purchase histories.");
        }

        if (widgetId == null) {
            purchaseHistory = persistance.getDraftItem(purchaseHistoryId);

            if (purchaseHistory == null) {
                throw new PurchaseHistoryNotFoundException("Cannot find purchase history by Id=" + purchaseHistoryId);
            }

            site = persistance.getSite(purchaseHistory.getSiteId());
        } else {
            widget = userRightManager.getSiteRight().getWidgetItemForEditInPresentationalService(
                    widgetId);
            purchaseHistory = (DraftPurchaseHistory) widget.getDraftItem();
            site = widget.getSite();

            if (purchaseHistory == null) {
                throw new PurchaseHistoryNotFoundException("Cannot find registration form by");
            }

            widgetTitle = new WidgetTitleGetter(widget);
        }

        getContext().getHttpServletRequest().setAttribute("purchaseHistoryService", this);
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public DraftPurchaseHistory getPurchaseHistory() {
        return purchaseHistory;
    }

    public WidgetItem getWidget() {
        return widget;
    }

    public Site getSite() {
        return site;
    }

    private WidgetItem widget;
    private DraftPurchaseHistory purchaseHistory;
    private WidgetTitle widgetTitle;
    private Site site;
    private Persistance persistance = ServiceLocator.getPersistance();

}
