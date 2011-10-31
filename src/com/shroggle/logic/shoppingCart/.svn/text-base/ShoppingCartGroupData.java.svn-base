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
package com.shroggle.logic.shoppingCart;

import com.shroggle.entity.Gallery;
import com.shroggle.logic.gallery.paypal.PaypalButtonData;

import java.util.List;

/**
 * @author dmitry.solomadin
 */
public class ShoppingCartGroupData {

    public ShoppingCartGroupData(final List<ShoppingCartItemData> shoppingCartItemDataList,
                                 final ShoppingCartGroupType shoppingCartGroupType, final Gallery gallery) {
        this.shoppingCartItemDataList = shoppingCartItemDataList;
        this.shoppingCartGroupType = shoppingCartGroupType;
        this.gallery = gallery;
    }

    private final List<ShoppingCartItemData> shoppingCartItemDataList;

    private final ShoppingCartGroupType shoppingCartGroupType;

    private final Gallery gallery;

    public Gallery getGallery() {
        return gallery;
    }

    public List<ShoppingCartItemData> getShoppingCartItemDataList() {
        return shoppingCartItemDataList;
    }

    public ShoppingCartGroupType getShoppingCartGroupType() {
        return shoppingCartGroupType;
    }
}
