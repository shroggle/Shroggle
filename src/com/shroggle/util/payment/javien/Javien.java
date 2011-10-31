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

package com.shroggle.util.payment.javien;

import com.shroggle.entity.*;
import com.shroggle.exception.JavienException;
import com.shroggle.logic.creditCard.CreditCardManager;
import com.shroggle.logic.payment.PaymentResult;
import com.shroggle.logic.payment.PaymentSettingsOwnerManager;
import com.shroggle.logic.payment.PaymentSystemRequest;
import com.shroggle.util.payment.PaymentSystem;
import com.shroggle.util.payment.paypal.PayPal;
import com.shroggle.util.payment.paypal.PayPalRecurringProfileStatus;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public abstract class Javien extends PaymentSystem {

    protected PaymentResult activateActivePaymentSettingsOwnerInternal(PaymentSystemRequest request) {
        final PaymentSettingsOwner owner = request.getOwner();
        if (owner.getSitePaymentSettings().getSiteStatus() != SiteStatus.ACTIVE) {
            throw new JavienException("'activateActivePaymentSettingsOwner' method should be used only for ACTIVE PaymentSettingsOwner!");
        }
        if (request.getCreditCard() == null) {
            throw new JavienException("Can`t update info without credit card!");
        }
        if (owner.getSitePaymentSettings().getRecurringPaymentId() != null) {
            logger.log(Level.INFO, "Trying to cancel active paypal recurring profile. recurringProfileId = " +
                    owner.getSitePaymentSettings().getRecurringPaymentId());
            try {

                ((PayPal)new PaymentSettingsOwnerManager(owner).getAppropriatePaymentSystem()).cancelRecurringProfile(owner.getSitePaymentSettings().getRecurringPaymentId());
                owner.getSitePaymentSettings().setRecurringPaymentId(null);
            } catch (Exception exception) {
                // todo. I think we should move this log to our payment logs in DB. Its important bug. Tolik
                logger.log(Level.SEVERE, "Unable to deactivate active paypal recurring profile. Its id = " +
                        owner.getSitePaymentSettings().getRecurringPaymentId() + "Please, look at it and do something!");
            }
        }
        final String productName = getExistingProductNameByPriceOrCreateNew(request.getPrice());
        final PaymentResult paymentResult;
        if (request.getCreditCard() != null && request.getCreditCard().getCreditCardNumber().equals(CreditCardManager.getTestCreditCardNumber())) {
            logger.info("Skipping test credit card purchasing.");
            paymentResult = PaymentResult.SKIPPED;
        } else {
            purchaseProduct(request.getCreditCard(), productName, owner, request.getPrice(), request.getPaymentReason(), request.getUserId());
            paymentResult = PaymentResult.ENFORCED;
        }
        owner.getSitePaymentSettings().setCreditCard(request.getCreditCard());
        return paymentResult;
    }

    protected PaymentResult activatePendingPaymentSettingsOwnerInternal(PaymentSystemRequest request) {
        final PaymentSettingsOwner owner = request.getOwner();
        if (owner.getSitePaymentSettings().getSiteStatus() != SiteStatus.PENDING) {
            throw new JavienException("'activatePendingPaymentSettingsOwner' method should be used only for PENDING PaymentSettingsOwner!");
        }
        if (request.getCreditCard() == null) {
            throw new JavienException("Can`t update info without credit card!");
        }
        if (owner.getSitePaymentSettings().getRecurringPaymentId() != null) {
            if (((PayPal)new PaymentSettingsOwnerManager(owner).getAppropriatePaymentSystem()).getProfileStatus(owner.getSitePaymentSettings().getRecurringPaymentId()) == PayPalRecurringProfileStatus.ACTIVE) {
                logger.log(Level.SEVERE, "Pending PaymentSettingsOwner has active recurring profile! recurringProfileId = " +
                        owner.getSitePaymentSettings().getRecurringPaymentId() +
                        ". It means, that we get money from users paypal account but his site still not activated. " +
                        "Please, check payment methods!");
            }
            logger.log(Level.INFO, "Trying to cancel unused paypal recurring profile. recurringProfileId = " +
                    owner.getSitePaymentSettings().getRecurringPaymentId());
            try {
                ((PayPal)new PaymentSettingsOwnerManager(owner).getAppropriatePaymentSystem()).cancelRecurringProfile(owner.getSitePaymentSettings().getRecurringPaymentId());
                owner.getSitePaymentSettings().setRecurringPaymentId(null);
            } catch (Exception exception) {
                // todo. I think we should move this log to our payment logs in DB. Its important bug. Tolik
                logger.log(Level.SEVERE, "Unable to deactivate unused paypal recurring profile. Its id = " +
                        owner.getSitePaymentSettings().getRecurringPaymentId() + "Please, look at it and do something!");
            }
        }
        final String productName = getExistingProductNameByPriceOrCreateNew(request.getPrice());
        final PaymentResult paymentResult;
        if (request.getCreditCard() != null && request.getCreditCard().getCreditCardNumber().equals(CreditCardManager.getTestCreditCardNumber())) {
            logger.info("Skipping test credit card purchasing.");
            paymentResult = PaymentResult.SKIPPED;
        } else {
            purchaseProduct(request.getCreditCard(), productName, owner, request.getPrice(), request.getPaymentReason(), request.getUserId());
            paymentResult = PaymentResult.ENFORCED;
        }
        owner.getSitePaymentSettings().setCreditCard(request.getCreditCard());
        return paymentResult;
    }

    protected PaymentResult prolongActivityInternal(PaymentSettingsOwner owner) {
        final SitePaymentSettings sitePaymentSettings = owner.getSitePaymentSettings();
        if (sitePaymentSettings.getCreditCard() == null) {
            throw new JavienException("Can`t prolong activity without credit card!");
        }
        final String productCode = getExistingProductNameByPriceOrCreateNew(sitePaymentSettings.getPrice());
        if (sitePaymentSettings.getCreditCard() != null && sitePaymentSettings.getCreditCard().getCreditCardNumber().equals(CreditCardManager.getTestCreditCardNumber())) {
            logger.info("Skipping test credit card purchasing.");
            return PaymentResult.SKIPPED;
        } else {
            purchaseProduct(sitePaymentSettings.getCreditCard(), productCode, owner, sitePaymentSettings.getPrice(),
                    PaymentReason.AUTO_PAYMENT, null);
            return PaymentResult.ENFORCED;
        }
    }

    public PaymentResult activateSuspendedRecurringProfile(String recurringProfileId) {
        // We don`t have to do something here. This method is used only for paypal recurring profile reactivating.
        // Look at the PayPalReal.
        // Tolik
        return PaymentResult.ENFORCED;
    }

    abstract void purchaseProduct(final CreditCard card, final String productName,
                                  final PaymentSettingsOwner owner, final double price,
                                  final PaymentReason paymentReason, final Integer userId);

    abstract String getExistingProductNameByPriceOrCreateNew(final double sum);

    private final Logger logger = Logger.getLogger(this.getClass().getName());

}
