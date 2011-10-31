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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Artem Stasuk
 */
public class PayPalMock extends PayPal {

    public String getPaypalLink() {
        return "";
    }

    public String setCustomerBillingAgreement(final String returnURL, final String cancelURL,
                                              final String description, final PaymentReason paymentReason,
                                              final Integer siteId, final Integer childSiteSettingsId, final Integer userId) {
        return null;
    }

    public String setCustomerBillingAgreementForPaypalButton(String returnURL, String cancelURL, String description, Integer filledFormId, Integer userId) {
        return null;
    }

    public String createRecurringPaymentsProfileForPaypalButton(final Date profileStartDate, final PaymentPeriod paymentPeriod,
                                                                final String token, final double paymentAmount,
                                                                final int billingFrequency, final String description,
                                                                final int filledFormId, final Integer userId,
                                                                final Integer groupsFilledItemId,
                                                                final Integer createdOrdersRecordId,
                                                                final String userAddedToGroups) {
        return null;
    }

    public String createRecurringPaymentsProfile(
            final Date dateInGMTFormat, final PaymentPeriod paymentPeriod, final String token,
            final double paymentAmount, final PaymentSettingsOwner owner,
            final PaymentReason paymentReason, final Integer userId) {
        final MockRecurringProfile recurringProfile = new MockRecurringProfile(String.valueOf(createId()), dateInGMTFormat);
        recurringProfiles.add(recurringProfile);
        return recurringProfile.getRecurringProfileId();
    }

    private int createId(){
        int newId = 1;
        for(MockRecurringProfile profile : recurringProfiles){
            if(Integer.valueOf(profile.getRecurringProfileId()) >= newId){
                newId++;
            }
        }
        return newId;
    }

    public PayPalRecurringProfileStatus getProfileStatus(String profileId) {
        for (MockRecurringProfile mockRecurringProfile : recurringProfiles) {
            if (mockRecurringProfile.getRecurringProfileId().equals(profileId)) {
                return mockRecurringProfile.getStatus();
            }
        }
        throw new PayPalException("Can`t find recurringProfile by id = " + profileId);
    }

    public void setProfileStatus(String profileId, PayPalRecurringProfileStatus neededStatus, String note) {
        for (MockRecurringProfile mockRecurringProfile : recurringProfiles) {
            if (mockRecurringProfile.getRecurringProfileId().equals(profileId)) {
                mockRecurringProfile.setStatus(neededStatus);
                return;
            }
        }
        throw new PayPalException("Can`t find recurringProfile by id = " + profileId);
    }

    public void updateProfilePrice(String profileId, double newPrice, String note) {
    }

    public void massPayment(List<PayPalPaymentRequest> paymentRequests, PaymentReason paymentReason) {
        for (PayPalPaymentRequest request : paymentRequests) {
            final String transaction = "Mass payment for site with siteId = " + request.getSiteId() + ", sum = " +
                    request.getSum() + ". Note:" + request.getNote();
            transactions.add(transaction);
        }
    }

    public List<String> getMassPaymentTransactions() {
        return transactions;
    }

    public List<MockRecurringProfile> getRecurringProfiles() {
        return recurringProfiles;
    }

    // This method is only for tests. Tolik
    protected MockRecurringProfile getMockRecurringProfile(final String recurringProfileId) {
        for (MockRecurringProfile mockRecurringProfile : recurringProfiles) {
            if (mockRecurringProfile.getRecurringProfileId().equals(recurringProfileId)) {
                return mockRecurringProfile;
            }
        }
        return null;
    }

    private final List<String> transactions = new ArrayList<String>();
    private final List<MockRecurringProfile> recurringProfiles = new ArrayList<MockRecurringProfile>();

}