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
import com.shroggle.entity.ItemType;
import com.shroggle.entity.Site;
import com.shroggle.entity.Widget;
import com.shroggle.exception.ShoppingCartNameNotUniqueException;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.FunctionalWidgetInfo;
import com.shroggle.presentation.site.GetFunctionalWidgetsService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.Locale;

/**
 * @author Artem Stasuk
 */
@RemoteProxy
public class SaveShoppingCartService extends AbstractService {

    @RemoteMethod
    public FunctionalWidgetInfo execute(final SaveShoppingCartRequest request) throws Exception {
        final UserManager userManager = new UsersManager().getLogined();

        final Widget widget;
        if (request.getWidgetId() != null) {
            widget = userManager.getRight().getSiteRight().getWidgetForEditInPresentationalService(request.getWidgetId());
        } else {
            widget = null;
        }

        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                final DraftShoppingCart tempDraftShoppingCart = userManager.getSiteItemForEditById(
                        request.getId(), ItemType.SHOPPING_CART);

                final Site site;
                if (widget != null) {
                    site = widget.getSite();
                } else {
                    site = persistance.getSite(tempDraftShoppingCart.getSiteId());
                }

                final DraftShoppingCart duplicateShoppingCart = persistance.getShoppingCartByNameAndSiteId(
                        request.getName(), site.getSiteId());
                if (duplicateShoppingCart != null && duplicateShoppingCart != tempDraftShoppingCart) {
                    throw new ShoppingCartNameNotUniqueException(international.get("ShoppingCartNameNotUniqueException"));
                }

                tempDraftShoppingCart.setShowDescription(request.isShowDescription());
                tempDraftShoppingCart.setDescription(StringUtil.getEmptyOrString(request.getDescription()));
                tempDraftShoppingCart.setName(request.getName());
                tempDraftShoppingCart.setDescriptionAfterName(request.isDescriptionAfterName());
                tempDraftShoppingCart.setImageInFirstColumn(request.isImageInFirstColumn());
                tempDraftShoppingCart.setImageHeight(request.getImageHeight());
                tempDraftShoppingCart.setImageWidth(request.getImageWidth());
            }

        });

        return new GetFunctionalWidgetsService().createFunctionalWidgetInfo(widget, "widget", true);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final International international = ServiceLocator.getInternationStorage().get("shoppingCart", Locale.US);

}