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
package com.shroggle.util.payment.paypal;

import com.shroggle.entity.PaymentPeriod;
import com.shroggle.entity.PaymentReason;
import com.shroggle.entity.PaymentSettingsOwner;
import com.shroggle.entity.SiteStatus;
import com.shroggle.exception.PayPalException;
import com.shroggle.logic.payment.PaymentResult;
import com.shroggle.logic.payment.PaymentSystemRequest;
import com.shroggle.util.payment.PaymentSystem;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Artem Stasuk
 */
public abstract class PayPal extends PaymentSystem {

    protected PaymentResult activateActivePaymentSettingsOwnerInternal(PaymentSystemRequest request) {
        final PaymentSettingsOwner owner = request.getOwner();
        if (owner.getSitePaymentSettings().getSiteStatus() != SiteStatus.ACTIVE) {
            throw new PayPalException("'activateActivePaymentSettingsOwner' method should be used only for ACTIVE PaymentSettingsOwner!");
        }
        if (owner.getSitePaymentSettings().getRecurringPaymentId() != null) {
            logger.log(Level.INFO, "Trying to cancel active paypal recurring profile. recurringProfileId = " +
                    owner.getSitePaymentSettings().getRecurringPaymentId());
            try {
                cancelRecurringProfile(owner.getSitePaymentSettings().getRecurringPaymentId());
                owner.getSitePaymentSettings().setRecurringPaymentId(null);
            } catch (Exception exception) {
                // todo. I think we should move this log to our payment logs in DB. Its important bug. Tolik
                logger.log(Level.SEVERE, "Unable to deactivate active paypal recurring profile. Its id = " +
                        owner.getSitePaymentSettings().getRecurringPaymentId() + "Please, look at it and do something!");
            }
        }
        final String profileId = createRecurringPaymentsProfile(request.getNewExpirationDate(),
                owner.getSitePaymentSettings().getChargeType().getPaymentPeriod(),
                request.getPaypalToken(), request.getPrice(), owner, request.getPaymentReason(), request.getUserId());
        owner.getSitePaymentSettings().setRecurringPaymentId(profileId);
        return PAYMENT_ENFORCED;
    }

    protected PaymentResult activatePendingPaymentSettingsOwnerInternal(PaymentSystemRequest request) {
        final PaymentSettingsOwner owner = request.getOwner();
        if (owner.getSitePaymentSettings().getSiteStatus() != SiteStatus.PENDING) {
            throw new PayPalException("'activatePendingPaymentSettingsOwner' method should be used only for PENDING PaymentSettingsOwner!");
        }
        if (owner.getSitePaymentSettings().getRecurringPaymentId() != null) {
            if (getProfileStatus(owner.getSitePaymentSettings().getRecurringPaymentId()) == PayPalRecurringProfileStatus.ACTIVE) {
                logger.log(Level.SEVERE, "Pending PaymentSettingsOwner has active recurring profile! recurringProfileId = " +
                        owner.getSitePaymentSettings().getRecurringPaymentId() +
                        ". It means, that we get money from users paypal account but his site still not activated. " +
                        "Please, check payment methods!");
            }
            logger.log(Level.INFO, "Trying to cancel active unused paypal recurring profile. recurringProfileId = " +
                    owner.getSitePaymentSettings().getRecurringPaymentId());
            try {
                cancelRecurringProfile(owner.getSitePaymentSettings().getRecurringPaymentId());
                owner.getSitePaymentSettings().setRecurringPaymentId(null);
            } catch (Exception exception) {
                // todo. I think we should move this log to our payment logs in DB. Its important bug. Tolik
                logger.log(Level.SEVERE, "Unable to deactivate unused active paypal recurring profile. Its id = " +
                        owner.getSitePaymentSettings().getRecurringPaymentId() + "Please, look at it and do something!");
            }
        }
        final String profileId = createRecurringPaymentsProfile(request.getNewExpirationDate(),
                owner.getSitePaymentSettings().getChargeType().getPaymentPeriod(),
                request.getPaypalToken(), request.getPrice(), owner, request.getPaymentReason(), request.getUserId());
        owner.getSitePaymentSettings().setRecurringPaymentId(profileId);
        return PAYMENT_ENFORCED;
    }

    protected PaymentResult prolongActivityInternal(PaymentSettingsOwner owner) {
        final String profileId = owner.getSitePaymentSettings().getRecurringPaymentId();
        if (profileId == null) {
            throw new PayPalException("Trying to prolong PaymentSettingsOwner activity without recurringProfileId." +
                    " Please, check your code or DB state.");
        }
        if (getProfileStatus(profileId) != PayPalRecurringProfileStatus.ACTIVE) {
            // Paypal automatically withdraws the money from the site owners account and if it cannot do it -
            // profileStatus will be not ACTIVE. So we just check profile status.
            throw new PayPalException("Paypal system can`t prolong profile activity. recurringProfileId = " + profileId);
        }
        return PAYMENT_ENFORCED;
    }

    public PaymentResult activateSuspendedRecurringProfile(final String recurringProfileId) {
        if (recurringProfileId == null) {
            throw new PayPalException("Trying to activate suspended PaymentSettingsOwner without recurringProfileId." +
                    " Please, check your code or DB state.");
        }
        setProfileStatus(recurringProfileId, PayPalRecurringProfileStatus.ACTIVE, "Profile reactivated due to site reactivation.");
        return PAYMENT_ENFORCED;
    }

    public void suspendActiveRecurringProfile(final String recurringProfileId) {
        if (recurringProfileId == null) {
            throw new PayPalException("Trying to suspend active PaymentSettingsOwner without recurringProfileId." +
                    " Please, check your code or DB state.");
        }
        setProfileStatus(recurringProfileId, PayPalRecurringProfileStatus.SUSPENDED, "Profile suspended due to site deactivation.");
    }

    public void cancelRecurringProfile(String recurringProfileId) {
        if (recurringProfileId == null) {
            throw new PayPalException("Trying to suspend active PaymentSettingsOwner without recurringProfileId." +
                    " Please, check your code or DB state.");
        }
        setProfileStatus(recurringProfileId, PayPalRecurringProfileStatus.CANCELED, "Profile canceled.");
    }

    public abstract String getPaypalLink();

    public abstract String setCustomerBillingAgreement(String returnURL, String cancelURL, String description,
                                                       PaymentReason paymentReason, Integer siteId, Integer childSiteSettingsId, Integer userId);

    public abstract String setCustomerBillingAgreementForPaypalButton(String returnURL, String cancelURL,
                                                                      String description, Integer filledFormId, Integer userId);

    public abstract String createRecurringPaymentsProfileForPaypalButton(final Date profileStartDate, final PaymentPeriod paymentPeriod,
                                                                         final String token, final double paymentAmount,
                                                                         final int billingFrequency, final String description,
                                                                         final int filledFormId, final Integer userId,
                                                                         final Integer groupsFilledItemId,
                                                                         final Integer createdOrdersRecordId,
                                                                         final String userAddedToGroups);

    public abstract PayPalRecurringProfileStatus getProfileStatus(String profileId);

    public abstract void updateProfilePrice(String profileId, double newPrice, String note);

    public abstract void massPayment(List<PayPalPaymentRequest> paymentRequests, PaymentReason paymentReason);

    abstract void setProfileStatus(String profileId, PayPalRecurringProfileStatus neededStatus, String note);

    abstract String createRecurringPaymentsProfile(Date profileStartDate, PaymentPeriod paymentPeriod, String token,
                                                   double paymentAmount, PaymentSettingsOwner owner,
                                                   PaymentReason paymentReason, Integer userId);

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    // We need following parameter only for javien because we have "test credit cards" there. For paypal we can`t do
    // such things so we always return "ENFORCED" payment type. If you`ll add skipping paypal payment for some cases
    // (such as test credit card in javien) - change this logic to return PaymentResult.SKIPPED when you skip payment processing. Tolik.
    private final static PaymentResult PAYMENT_ENFORCED = PaymentResult.ENFORCED;
}