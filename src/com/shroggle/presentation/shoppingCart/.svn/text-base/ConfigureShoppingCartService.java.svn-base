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

package com.shroggle.presentation.shoppingCart;

import com.shroggle.entity.DraftShoppingCart;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteOnItemRightType;
import com.shroggle.entity.WidgetItem;
import com.shroggle.exception.ShoppingCartNotFoundException;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.logic.widget.WidgetTitleGetter;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.presentation.site.WithWidgetTitle;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author Artem Stasuk
 */
@RemoteProxy
public class ConfigureShoppingCartService extends ServiceWithExecutePage implements WithWidgetTitle {

    @RemoteMethod
    public void execute(final Integer widgetId, final Integer shoppingCartId) throws Exception {
        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();

        if (widgetId == null && shoppingCartId == null) {
            throw new IllegalArgumentException("Both widgetId and shoppingCartId cannot be null. " +
                    "This service is only for configuring existing shopping carts.");
        }

        if (widgetId == null) {
            shoppingCart = persistance.getDraftItem(shoppingCartId);

            if (shoppingCart == null) {
                throw new ShoppingCartNotFoundException("Cannot find shopping cart by Id=" + shoppingCartId);
            }

            site = persistance.getSite(shoppingCart.getSiteId());
        } else {
            widget = userRightManager.getSiteRight().getWidgetItemForEditInPresentationalService(
                    widgetId);
            shoppingCart = (DraftShoppingCart) widget.getDraftItem();
            site = widget.getSite();

            if (shoppingCart == null) {
                throw new ShoppingCartNotFoundException("Cannot find shopping cart inside widget");
            }

            widgetTitle = new WidgetTitleGetter(widget);
        }

        getContext().getHttpServletRequest().setAttribute("shoppingCartService", this);
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public DraftShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public Site getSite() {
        return site;
    }

    public WidgetItem getWidget() {
        return widget;
    }

    private DraftShoppingCart shoppingCart;
    private WidgetItem widget;
    private WidgetTitle widgetTitle;
    private Site site;

    private final Persistance persistance = ServiceLocator.getPersistance();

}