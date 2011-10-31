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
package com.shroggle.logic.gallery.paypal;

import com.shroggle.entity.Gallery;
import com.shroggle.entity.PaypalSettingsForGallery;
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.exception.GalleryNotFoundException;
import com.shroggle.exception.PaypalSettingsNotFoundException;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.logic.gallery.GalleryManager;
import com.shroggle.logic.gallery.PaypalSettingsData;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.international.International;

import java.util.Locale;

/**
 * @author Balakirev Anatoliy, dmitry.solomadin
 */
public class PaypalSettingsForGalleryManager {

    public PaypalSettingsForGalleryManager(final Gallery gallery) {
        if (gallery == null) {
            throw new GalleryNotFoundException("");
        }

        if (gallery.getPaypalSettings() == null) {
            throw new PaypalSettingsNotFoundException("PaypalSettings in gallery by id=" + gallery.getId() +
                    "are null. This is wrong situation that should be fixed by our updates.");
        }

        this.gallery = gallery;
    }

    public PaypalSettingsData createPaypalSettingsData() {
        return createPaypalSettingsData(gallery.getPaypalSettings(), gallery.getSiteId());
    }

    public static PaypalSettingsData createDefaultPaypalSettingsData(final int siteId) {
        return createPaypalSettingsData(new PaypalSettingsForGallery(), siteId);
    }

    public void fillPaypalSettingsForGallery(final PaypalSettingsData data) {
        final PaypalSettingsForGallery paypalSettings = gallery.getPaypalSettings();

        paypalSettings.setEnable(data.isEnable());
        paypalSettings.setPaypalEmail(data.getPaypalEmail());
        paypalSettings.setFormItemIdWithPrice(data.getFormItemIdWithFullPrice());
        paypalSettings.setFormItemIdWithProductName(data.getFormItemIdWithProductName());
        paypalSettings.setFormItemIdWithProductDescription(data.getFormItemIdWithProductDescription());
        paypalSettings.setFormItemIdWithProductImage(data.getFormItemIdWithProductImage());
        paypalSettings.setShoppingCartId(data.getShoppingCartId());
        paypalSettings.setSelectedShoppingCartPageId(data.getSelectedShoppingCartPageId());
        paypalSettings.setPurchaseHistoryId(data.getPurchaseHistoryId());
        paypalSettings.setSelectedPurchaseHistoryPageId(data.getSelectedPurchaseHistoryPageId());

        paypalSettings.setPaypalSettingsPosition(data.getPosition());
        paypalSettings.setPaypalSettingsRow(data.getRow());
        paypalSettings.setPaypalSettingsColumn(data.getColumn());
        paypalSettings.setPaypalSettingsAlign(data.getAlign());
        paypalSettings.setShowPaypalButton(data.isDisplay());
        paypalSettings.setPaypalSettingsName(data.getName());

        paypalSettings.setGoToShoppingCartPosition(data.getGoToShoppingCartPosition());
        paypalSettings.setGoToShoppingCartRow(data.getGoToShoppingCartRow());
        paypalSettings.setGoToShoppingCartColumn(data.getGoToShoppingCartColumn());
        paypalSettings.setGoToShoppingCartAlign(data.getGoToShoppingCartAlign());
        paypalSettings.setGoToShoppingCartName(data.getGoToShoppingCartName());
        paypalSettings.setGoToShoppingCartDisplay(data.isGoToShoppingCartDisplay());

        paypalSettings.setViewPurchaseHistoryPosition(data.getViewPurchaseHistoryPosition());
        paypalSettings.setViewPurchaseHistoryRow(data.getViewPurchaseHistoryRow());
        paypalSettings.setViewPurchaseHistoryColumn(data.getViewPurchaseHistoryColumn());
        paypalSettings.setViewPurchaseHistoryAlign(data.getViewPurchaseHistoryAlign());
        paypalSettings.setViewPurchaseHistoryName(data.getViewPurchaseHistoryName());
        paypalSettings.setViewPurchaseHistoryDisplay(data.isViewPurchaseHistoryDisplay());

        paypalSettings.setRegistrationFormForBuyers(data.getRegistrationFormId());

        if (paypalSettings.isEnable() && paypalSettings.getOrdersFormId() == null) {
            paypalSettings.setOrdersFormId(new GalleryManager(gallery).createOrdersForm(
                    paypalSettings.getFormItemIdWithProductName(),
                    paypalSettings.getRegistrationFormForBuyers(),
                    new UsersManager().getLogined()).getId());
        }
    }

    private static PaypalSettingsData createPaypalSettingsData(final PaypalSettingsForGallery paypalSettings, final int siteId) {
        if (paypalSettings == null) {
            throw new PaypalSettingsNotFoundException();
        }

        final User user = new UsersManager().getLoginedUser();
        String paypalEmail = StringUtil.getEmptyOrString(paypalSettings.getPaypalEmail());
        if (paypalEmail.isEmpty()) {
            paypalEmail = user.getEmail();
        }

        final Site site = ServiceLocator.getPersistance().getSite(siteId);

        if (site == null) {
            throw new SiteNotFoundException("Cannot find site to get groups for paypal gallery button by id=" + siteId);
        }
        final International international = ServiceLocator.getInternationStorage().get("paypalSettingsForGallery", Locale.US);
        PaypalSettingsData data = new PaypalSettingsData(
                paypalSettings.getPaypalSettingsPosition(),
                paypalSettings.getPaypalSettingsRow(),
                paypalSettings.getPaypalSettingsColumn(),
                paypalSettings.getPaypalSettingsAlign(),
                international.get("payPal"),
                paypalSettings.getPaypalSettingsName(),
                paypalEmail,
                paypalSettings.getFormItemIdWithPrice(),
                paypalSettings.getFormItemIdWithProductName(),
                paypalSettings.getFormItemIdWithProductDescription(),
                paypalSettings.getFormItemIdWithProductImage(),
                paypalSettings.isShowPaypalButton(),
                paypalSettings.getRegistrationFormForBuyers()
        );
        data.setEnable(paypalSettings.isEnable());
        data.setShoppingCartId(paypalSettings.getShoppingCartId());
        data.setSelectedShoppingCartPageId(paypalSettings.getSelectedShoppingCartPageId());
        data.setPurchaseHistoryId(paypalSettings.getPurchaseHistoryId());
        data.setSelectedPurchaseHistoryPageId(paypalSettings.getSelectedPurchaseHistoryPageId());

        data.setGoToShoppingCartAlign(paypalSettings.getGoToShoppingCartAlign());
        data.setGoToShoppingCartColumn(paypalSettings.getGoToShoppingCartColumn());
        data.setGoToShoppingCartName(paypalSettings.getGoToShoppingCartName());
        data.setGoToShoppingCartRow(paypalSettings.getGoToShoppingCartRow());
        data.setGoToShoppingCartPosition(paypalSettings.getGoToShoppingCartPosition());
        data.setGoToShoppingCartDisplay(paypalSettings.isGoToShoppingCartDisplay());

        data.setViewPurchaseHistoryAlign(paypalSettings.getViewPurchaseHistoryAlign());
        data.setViewPurchaseHistoryColumn(paypalSettings.getViewPurchaseHistoryColumn());
        data.setViewPurchaseHistoryName(paypalSettings.getViewPurchaseHistoryName());
        data.setViewPurchaseHistoryRow(paypalSettings.getViewPurchaseHistoryRow());
        data.setViewPurchaseHistoryPosition(paypalSettings.getViewPurchaseHistoryPosition());
        data.setViewPurchaseHistoryDisplay(paypalSettings.isViewPurchaseHistoryDisplay());

        return data;
    }

    private final Gallery gallery;

}
