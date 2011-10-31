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
import com.shroggle.entity.Page;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.entity.Widget;
import com.shroggle.logic.gallery.paypal.PaypalButtonData;
import com.shroggle.logic.gallery.paypal.PaypalButtonHelper;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.gallery.GalleryNavigationUrl;
import com.shroggle.presentation.gallery.GalleryNavigationUrlCreator;
import com.shroggle.presentation.site.render.shoppingCart.FilledFormGalleryIdsPair;
import com.shroggle.util.DoubleUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
public class ShoppingCartHelper {

    public static List<ShoppingCartGroupData> splitItems(final List<FilledFormGalleryIdsPair> items,
                                                         final SiteShowOption siteShowOption,
                                                         final Widget shoppingCartWidget) {
        final Persistance persistance = ServiceLocator.getPersistance();
        List<ShoppingCartGroupData> splittedItems = new ArrayList<ShoppingCartGroupData>();

        List<ShoppingCartItemData> itemsGroup = new ArrayList<ShoppingCartItemData>();
        Gallery previousGallery = null;
        Gallery gallery = null;
        for (FilledFormGalleryIdsPair item : items) {
            gallery = persistance.getGalleryById(item.getGalleryId());
            final Widget widget = persistance.getWidget(item.getWidgetId());

            if (gallery == null || widget == null) {
                continue;
            }

            ShoppingCartItemData itemToAdd = fillData(gallery, widget, shoppingCartWidget, item.getFilledFormId(), item.getQuantity(), siteShowOption);

            // If item is recurrent profile flush all items in existing items list and create new group.
            if (itemToAdd.getPaypalButtonData().isRecurrent()) {
                if (!itemsGroup.isEmpty()) {
                    splittedItems.add(new ShoppingCartGroupData(itemsGroup, ShoppingCartGroupType.NORMAL, gallery));
                    itemsGroup = new ArrayList<ShoppingCartItemData>();
                }

                itemsGroup.add(itemToAdd);
                splittedItems.add(new ShoppingCartGroupData(itemsGroup, ShoppingCartGroupType.RECURRENT, gallery));
                itemsGroup = new ArrayList<ShoppingCartItemData>();
                continue;
            }

            // If item has other galleryId than previousItem then flush items in existing items list and create new group.
            if ((previousGallery != null && previousGallery.getId() != gallery.getId()) && !itemsGroup.isEmpty()) {
                splittedItems.add(new ShoppingCartGroupData(itemsGroup, ShoppingCartGroupType.NORMAL, previousGallery));
                itemsGroup = new ArrayList<ShoppingCartItemData>();
            }

            itemsGroup.add(itemToAdd);
            previousGallery = gallery;
        }

        if (!itemsGroup.isEmpty()) {
            splittedItems.add(new ShoppingCartGroupData(itemsGroup, ShoppingCartGroupType.NORMAL, gallery));
        }

        return splittedItems;
    }

    private static ShoppingCartItemData fillData(final Gallery gallery, final Widget widget, final Widget shoppingCartWidget,
                                                 final int filledFormId, final int quantity, final SiteShowOption siteShowOption) {
        final PaypalButtonData paypalButtonData = PaypalButtonHelper.fillData(gallery, filledFormId, widget.getSiteId());
        final GalleryNavigationUrl productPageUrl =
                GalleryNavigationUrlCreator.executeForOtherWidget(gallery, widget, shoppingCartWidget, filledFormId, siteShowOption);

        return new ShoppingCartItemData(paypalButtonData, quantity, productPageUrl);
    }

    public static String getTotalPrice(final List<Double> itemPricesWithTaxPoweredByQty) {
        double total = 0.0;
        for (Double itemPriceWithTaxPoweredByQty : itemPricesWithTaxPoweredByQty) {
            total += itemPriceWithTaxPoweredByQty;
        }

        return DoubleUtil.roundWithPrecision(total, 2);
    }

    public String getGalleryPageUrlByGalleryId(final int galleryId, final SiteShowOption siteShowOption) {
        final Widget widget = ServiceLocator.getPersistance().getFirstWidgetByItemId(galleryId);
        if (widget != null) {
            final Page page = widget.getPage();

            return new PageManager(page, siteShowOption).getUrl();
        }

        return null;
    }

}
