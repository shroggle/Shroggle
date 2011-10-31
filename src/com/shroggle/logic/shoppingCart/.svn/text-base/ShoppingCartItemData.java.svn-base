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

import com.shroggle.logic.gallery.paypal.PaypalButtonData;
import com.shroggle.presentation.gallery.GalleryNavigationUrl;

/**
 * @author dmitry.solomadin
 */
public class ShoppingCartItemData {

    public ShoppingCartItemData(final PaypalButtonData paypalButtonData, final int quantity,
                                final GalleryNavigationUrl productPageUrl) {
        this.paypalButtonData = paypalButtonData;
        this.quantity = quantity;
        this.productPageUrl = productPageUrl;
    }

    private final PaypalButtonData paypalButtonData;

    private final int quantity;

    private final GalleryNavigationUrl productPageUrl;

    public GalleryNavigationUrl getProductPageUrl() {
        return productPageUrl;
    }

    public PaypalButtonData getPaypalButtonData() {
        return paypalButtonData;
    }

    public int getQuantity() {
        return quantity;
    }
}
