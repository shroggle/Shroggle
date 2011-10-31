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
package com.shroggle.logic.payment;

import com.shroggle.entity.*;
import com.shroggle.exception.PaymentInfoCreatorRequestNotFoundException;
import com.shroggle.exception.PaymentSettingsOwnerNotFoundException;
import com.shroggle.exception.UnknownSiteStatusException;
import com.shroggle.logic.site.PublishingInfoResponse;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.payment.PaymentSystem;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.billingInfo.ChargeTypeManager;
import com.shroggle.logic.childSites.childSiteSettings.ChildSiteSettingsManager;

import java.util.Date;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * @author Balakirev Anatoliy
 */
public class PaymentInfoCreator {

    public PaymentInfoCreator(final PaymentInfoCreatorRequest request) {
        if (request == null) {
            throw new PaymentInfoCreatorRequestNotFoundException("Unable create payment info without PaymentInfoCreatorRequest!");
        }
        owner = persistance.getPaymentSettingsOwner(request.getPaymentSettingsOwnerId(), request.getPaymentSettingsOwnerType());
        if (owner == null) {
            throw new PaymentSettingsOwnerNotFoundException("Unable create payment info without PaymentSettingsOwner!");
        }
        chargeType = request.getChargeType();
        if (chargeType == null) {
            throw new IllegalArgumentException("Can`t create PaymentInfoCreator without chargeType!");
        }
        paymentMethod = request.getPaymentMethod();
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Can`t create PaymentInfoCreator without paymentMethod!");
        }
        paymentSettingsOwnerManager = new PaymentSettingsOwnerManager(owner);
        userId = request.getUserId();
        paypalToken = request.getPaypalToken();
        creditCard = request.getCreditCard();
    }

    public PaymentInfoCreatorState create() {
        final SitePaymentSettings sitePaymentSettings;
        if (owner.getSitePaymentSettings() != null) {
            sitePaymentSettings = owner.getSitePaymentSettings();
        } else {
            sitePaymentSettings = new SitePaymentSettings();
            owner.setSitePaymentSettings(sitePaymentSettings);
        }
        final double price = paymentSettingsOwnerManager.getPriceByChargeType(chargeType);
        final PaymentSystemRequest paymentSystemRequest = new PaymentSystemRequest(owner, price, userId,
                paymentSettingsOwnerManager.getPaymentReason(), paypalToken, creditCard, chargeType);

        final Date newExpirationDate;
        PaymentInfoCreatorState methodState;
        final ChargeTypeManager chargeTypeManager = new ChargeTypeManager(chargeType);
        PaymentSettingsOwnerManager paymentSettingsOwnerManager = new PaymentSettingsOwnerManager(owner);
        // PaymentSettingsOwner does not have appropriate PaymentMethod now, so I`m passing it as argument. Tolik
        final PaymentSystem paymentSystem = paymentSettingsOwnerManager.getAppropriatePaymentSystem(paymentMethod);
        final PaymentResult paymentResult;
        switch (sitePaymentSettings.getSiteStatus()) {
            case ACTIVE: {
                newExpirationDate = chargeTypeManager.createNewExpirationDateForActiveOwner(sitePaymentSettings.getExpirationDate());
                paymentSystemRequest.setNewExpirationDate(newExpirationDate);
                paymentSystemRequest.setOldExpirationDate(sitePaymentSettings.getExpirationDate());
                paymentResult = paymentSystem.activateActivePaymentSettingsOwner(paymentSystemRequest);
                methodState = PaymentInfoCreatorState.ACTIVETED_ACTIVE;
                break;
            }
            case PENDING: {
                newExpirationDate = chargeTypeManager.createNewExpirationDateForPendingOwner(paymentSettingsOwnerManager.getChildSiteSettings());
                paymentSystemRequest.setNewExpirationDate(newExpirationDate);
                paymentSystemRequest.setOldExpirationDate(sitePaymentSettings.getExpirationDate());
                paymentResult = paymentSystem.activatePendingPaymentSettingsOwner(paymentSystemRequest);
                methodState = PaymentInfoCreatorState.ACTIVETED_PENDING;
                break;
            }
            case SUSPENDED: {
                newExpirationDate = chargeTypeManager.createNewExpirationDateForSuspendedOwner(sitePaymentSettings.getRemainingTimeOfUsage());
                paymentResult = paymentSystem.activateSuspendedRecurringProfile(sitePaymentSettings.getRecurringPaymentId());
                methodState = PaymentInfoCreatorState.ACTIVETED_SUSPENDED;
                break;
            }
            default: {
                throw new UnknownSiteStatusException("Can`t create SitePaymentSettings by siteStatus = " + sitePaymentSettings.getSiteStatus());
            }
        }
        sitePaymentSettings.setPrice(price);
        sitePaymentSettings.setExpirationDate(newExpirationDate);
        sitePaymentSettings.setChargeType(chargeType);
        sitePaymentSettings.setPaymentMethod(paymentMethod);
        sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
        sitePaymentSettings.setRemainingTimeOfUsage(null);
        final ChildSiteSettings childSiteSettings = paymentSettingsOwnerManager.getChildSiteSettings();
        if (paymentResult != PaymentResult.SKIPPED && childSiteSettings != null) {
            //todo fix this. Now for "One Time Fee" settings we return all money to parent site owner. Tolik
            if (paymentSystem.isShrogglePaymentSystem()) {// We should add difference only if our paymentSystem is used. Tolik
                new SiteManager(childSiteSettings.getParentSite()).addDifferenceToIncomeSettings(price, chargeType, paymentSettingsOwnerManager.getInfoForPaymentLog());
            }
            final PublishingInfoResponse publishingInfo = new ChildSiteSettingsManager(childSiteSettings).getPublishingInfo();
            if (!publishingInfo.isCanBePublished()) {
                try {
                    new PaymentSettingsOwnerManager(owner).suspendActivity();
                } catch (Exception e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Unable to suspend activity for child" +
                            " site settings with id = " + childSiteSettings.getChildSiteSettingsId());
                }
            }
        }
        if (sitePaymentSettings.getSitePaymentSettingsId() <= 0) {
            persistance.putSitePaymentSettings(sitePaymentSettings);
        }
        methodState.setPrice(price);
        return methodState;
    }


    private final PaymentSettingsOwnerManager paymentSettingsOwnerManager;
    private final PaymentSettingsOwner owner;
    private final String paypalToken;
    private final CreditCard creditCard;
    private final ChargeType chargeType;
    private final PaymentMethod paymentMethod;
    private final Integer userId;
    private final Persistance persistance = ServiceLocator.getPersistance();
}
