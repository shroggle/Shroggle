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
import com.shroggle.exception.PayPalException;

import java.util.Date;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
public class PayPalWithExceptionsInMethods extends PayPal {

    public String setCustomerBillingAgreement(String returnURL, String cancelURL, String description, PaymentReason paymentReason, Integer siteId, Integer childSiteSettingsId, Integer userId) {
        throw new PayPalException("");
    }

    public String setCustomerBillingAgreementForPaypalButton(String returnURL, String cancelURL, String description, Integer filledFormId, Integer userId) {
        throw new PayPalException("");
    }

    public String createRecurringPaymentsProfileForPaypalButton(final Date profileStartDate, final PaymentPeriod paymentPeriod,
                                                                final String token, final double paymentAmount,
                                                                final int billingFrequency, final String description,
                                                                final int filledFormId, final Integer userId,
                                                                final Integer groupsFilledItemId,
                                                                final Integer createdOrdersRecordId,
                                                                final String userAddedToGroups) {
        throw new PayPalException("");
    }

    public String createRecurringPaymentsProfile(Date profileStartDate, PaymentPeriod paymentPeriod, String token, double paymentAmount, PaymentSettingsOwner owner, PaymentReason paymentReason, Integer userId) {
        throw new PayPalException("");
    }

    public PayPalRecurringProfileStatus getProfileStatus(String profileId) {
        throw new PayPalException("");
    }

    void setProfileStatus(String profileId, PayPalRecurringProfileStatus neededStatus, String note) {
        throw new PayPalException("");
    }

    public void updateProfilePrice(String profileId, double newPrice, String note) {
        throw new PayPalException("");
    }

    public void massPayment(List<PayPalPaymentRequest> paymentRequests, PaymentReason paymentReason) {
        throw new PayPalException("");
    }

    public String getPaypalLink() {
        throw new PayPalException("");
    }
}