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
package com.shroggle.util.payment.authorize;

import com.shroggle.entity.*;
import com.shroggle.exception.AuthorizeNetException;
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
public abstract class AuthorizeNet extends PaymentSystem {

    protected PaymentResult activateActivePaymentSettingsOwnerInternal(PaymentSystemRequest request) {
        final PaymentSettingsOwner owner = request.getOwner();
        if (owner.getSitePaymentSettings().getSiteStatus() != SiteStatus.ACTIVE) {
            throw new AuthorizeNetException("'activateActivePaymentSettingsOwner' method should be used only for ACTIVE PaymentSettingsOwner!");
        }
        if (request.getCreditCard() == null) {
            throw new AuthorizeNetException("Can`t update info without credit card!");
        }
        if (owner.getSitePaymentSettings().getRecurringPaymentId() != null) {
            logger.log(Level.INFO, "Trying to cancel active paypal recurring profile. recurringProfileId = " +
                    owner.getSitePaymentSettings().getRecurringPaymentId());
            try {
                // It`s not common situation, so I`m trying to get own payPal system or our one. Tolik
                final PayPal payPal = (PayPal)new PaymentSettingsOwnerManager(owner).getAppropriatePaymentSystem(PaymentMethod.PAYPAL);
                payPal.cancelRecurringProfile(owner.getSitePaymentSettings().getRecurringPaymentId());
                owner.getSitePaymentSettings().setRecurringPaymentId(null);
            } catch (Exception exception) {
                // todo. I think we should move this log to our payment logs in DB. Its important bug. Tolik
                logger.log(Level.SEVERE, "Unable to deactivate active paypal recurring profile. Its id = " +
                        owner.getSitePaymentSettings().getRecurringPaymentId() + ". Please, look at it and do something!");
            }
        }
        final PaymentResult paymentResult;
        if (request.getCreditCard() != null && request.getCreditCard().getCreditCardNumber().equals(CreditCardManager.getTestCreditCardNumber())) {
            logger.info("Skipping test credit card purchasing.");
            paymentResult = PaymentResult.SKIPPED;
        } else {
            purchase(request.getCreditCard(), owner, request.getPrice(), request.getPaymentReason(), request.getUserId());
            paymentResult = PaymentResult.ENFORCED;
        }
        owner.getSitePaymentSettings().setCreditCard(request.getCreditCard());
        return paymentResult;
    }

    protected PaymentResult activatePendingPaymentSettingsOwnerInternal(PaymentSystemRequest request) {
        final PaymentSettingsOwner owner = request.getOwner();
        if (owner.getSitePaymentSettings().getSiteStatus() != SiteStatus.PENDING) {
            throw new AuthorizeNetException("'activatePendingPaymentSettingsOwner' method should be used only for PENDING PaymentSettingsOwner!");
        }
        if (request.getCreditCard() == null) {
            throw new AuthorizeNetException("Can`t update info without credit card!");
        }
        if (owner.getSitePaymentSettings().getRecurringPaymentId() != null) {
            PaymentSettingsOwnerManager paymentSettingsOwnerManager = new PaymentSettingsOwnerManager(owner);
            final PaymentSystem paymentSystem = paymentSettingsOwnerManager.getAppropriatePaymentSystem(PaymentMethod.PAYPAL);
            if (((PayPal) paymentSystem).getProfileStatus(owner.getSitePaymentSettings().getRecurringPaymentId()) == PayPalRecurringProfileStatus.ACTIVE) {
                logger.log(Level.SEVERE, "Pending PaymentSettingsOwner has active recurring profile! recurringProfileId = " +
                        owner.getSitePaymentSettings().getRecurringPaymentId() +
                        ". It means, that we get money from users paypal account but his site still not activated. " +
                        "Please, check payment methods!");
            }
            logger.log(Level.INFO, "Trying to cancel unused paypal recurring profile. recurringProfileId = " +
                    owner.getSitePaymentSettings().getRecurringPaymentId());
            try {
                ((PayPal) paymentSystem).cancelRecurringProfile(owner.getSitePaymentSettings().getRecurringPaymentId());
                owner.getSitePaymentSettings().setRecurringPaymentId(null);
            } catch (Exception exception) {
                // todo. I think we should move this log to our payment logs in DB. Its important bug. Tolik
                logger.log(Level.SEVERE, "Unable to deactivate unused paypal recurring profile. Its id = " +
                        owner.getSitePaymentSettings().getRecurringPaymentId() + "Please, look at it and do something!");
            }
        }
        final PaymentResult paymentResult;
        if (request.getCreditCard() != null && request.getCreditCard().getCreditCardNumber().equals(CreditCardManager.getTestCreditCardNumber())) {
            logger.info("Skipping test credit card purchasing.");
            paymentResult = PaymentResult.SKIPPED;
        } else {
            purchase(request.getCreditCard(), owner, request.getPrice(), request.getPaymentReason(), request.getUserId());
            paymentResult = PaymentResult.ENFORCED;
        }
        owner.getSitePaymentSettings().setCreditCard(request.getCreditCard());
        return paymentResult;
    }

    protected PaymentResult prolongActivityInternal(PaymentSettingsOwner owner) {
        final SitePaymentSettings sitePaymentSettings = owner.getSitePaymentSettings();
        if (sitePaymentSettings.getCreditCard() == null) {
            throw new AuthorizeNetException("Can`t prolong activity without credit card!");
        }
        if (sitePaymentSettings.getCreditCard() != null && sitePaymentSettings.getCreditCard().getCreditCardNumber().equals(CreditCardManager.getTestCreditCardNumber())) {
            logger.info("Skipping test credit card purchasing.");
            return PaymentResult.SKIPPED;
        } else {
            purchase(sitePaymentSettings.getCreditCard(), owner, sitePaymentSettings.getPrice(),
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

    abstract void purchase(final CreditCard card,
                           final PaymentSettingsOwner owner, final double price,
                           final PaymentReason paymentReason, final Integer userId);

    protected final Logger logger = Logger.getLogger(this.getClass().getName());
}
