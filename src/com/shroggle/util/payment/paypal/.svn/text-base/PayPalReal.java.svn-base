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

import com.paypal.sdk.core.nvp.NVPDecoder;
import com.paypal.sdk.core.nvp.NVPEncoder;
import com.shroggle.entity.*;
import com.shroggle.exception.PayPalException;
import com.shroggle.exception.UnknownRecurringProfileStatusException;
import com.shroggle.logic.site.payment.PaymentLogRequest;
import com.shroggle.logic.site.payment.PaymentLogger;
import com.shroggle.logic.site.payment.TransactionStatus;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.international.International;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author Artem Stasuk, dmitry.solomadin
 *         Note that all paypal methods throws PayPalException if smth goes wrong
 *         those exception should be processed in apropriate way.
 */
public class PayPalReal extends PayPal {

    public PayPalReal(final String paypalApiUserName, final String paypalApiPassword, final String paypalSignature) {
        try {
            payPalManager = new PayPalManager(paypalApiUserName, paypalApiPassword, paypalSignature);
        } catch (final Exception exception) {
            throw new PayPalException("Failed to initialize paypal please check config parameters", exception);
        }
    }

    public String getPaypalLink() {
        return "https://www." + (payPalManager.getEnv().equals("sandbox") ? "sandbox.paypal.com" : "paypal.com") +
                "/webscr?cmd=_customer-billing-agreement&token=";
    }

    public String setCustomerBillingAgreement(final String returnURL, final String cancelURL,
                                              final String description, final PaymentReason paymentReason,
                                              final Integer siteId, final Integer childSiteSettingsId, final Integer userId) {
        final String operationNote = "Setting up customer billing agreement";

        /*Forming log request*/
        final PaymentLogRequest log = new PaymentLogRequest();
        log.setMessage(operationNote);
        log.setPaymentReason(paymentReason);
        log.setSiteId(siteId);
        log.setChildSiteSettingsId(childSiteSettingsId);
        log.setUserId(userId);

        return setCustomerBillingAgreementInternal(returnURL, cancelURL, description, log);
    }

    public String setCustomerBillingAgreementForPaypalButton(final String returnURL, final String cancelURL,
                                                             final String description, Integer filledFormId, Integer userId) {
        final String operationNote = "Setting up customer billing agreement for Paypal button";

        /*Forming log request*/
        final PaymentLogRequest log = new PaymentLogRequest();
        log.setMessage(operationNote);
        log.setPaymentReason(PaymentReason.PAYPAL_BUTTON_RECURRING_PAYMENT);
        log.setUserId(userId);

        return setCustomerBillingAgreementInternal(returnURL, cancelURL, description, log);
    }

    private String setCustomerBillingAgreementInternal(final String returnURL, final String cancelURL,
                                                       final String description, final PaymentLogRequest log) {
        //NVPEncoder object is created and all the name value pairs are loaded into it.
        final NVPEncoder encoder = new NVPEncoder();

        encoder.add("METHOD", "SetCustomerBillingAgreement");
        encoder.add("RETURNURL", returnURL);
        encoder.add("CANCELURL", cancelURL);

        encoder.add("VERSION", "53.0");
        encoder.add("BILLINGTYPE", "RecurringPayments");
        encoder.add("BILLINGAGREEMENTDESCRIPTION", description);
        encoder.add("FIRSTNAME", description);
        encoder.add("LASTNAME", description);
        encoder.add("SUBSCRIBERNAME", description);

        final NVPDecoder decoder = new NVPDecoder();
        try {
            final String strNVPRequest = encoder.encode();
            final String ppresponse = payPalManager.getCaller().call(strNVPRequest);
            decoder.decode(ppresponse);
        } catch (final com.paypal.sdk.exceptions.PayPalException exception) {
            logPayPalError(log, "Failed to set customer billing agreement.", decoder);
            throw new PayPalException(paypalInternational.get("exception"), exception);
        }

        //checks for Acknowledgement and redirects accordingly to display error messages
        final String acknowledgement = decoder.get("ACK");
        if (acknowledgement != null && !(acknowledgement.equals("Success") || acknowledgement.equals("SuccessWithWarning"))) {
            logPayPalError(log, "Failed to set customer billing agreement. Acknowledgement:" + acknowledgement + ".", decoder);
            throw new PayPalException(paypalInternational.get("exception"));
        }

        paymentLogger.logPayPalTransaction(TransactionStatus.SENT_CONFIRMED, log);
        return decoder.get("TOKEN");
    }

    /**
     * @param profileStartDate - profile start date.
     * @param paymentPeriod    - period of payment.
     * @param token            - token recieved from Paypal by setCustomerBillingAgreement mehtod.
     * @param paymentAmount    - Payment amount that withdrawed every month or year depending on paymentPeriod.
     * @param owner            - payment settings owner. Can be either ChildSiteSettings or Site depending for which user is paying for.
     * @param paymentReason    - parameter for logging. Represents place or product for which payment is performed.
     * @param userId           - parameter for logging. Represents user that performing payment.
     * @return created profile Id.
     */
    String createRecurringPaymentsProfile(final Date profileStartDate, final PaymentPeriod paymentPeriod,
                                          final String token, final double paymentAmount,
                                          final PaymentSettingsOwner owner,
                                          final PaymentReason paymentReason, final Integer userId) {
        final String operationNote = "Create recurring payments profile.";

        /*Forming log request*/
        final PaymentLogRequest paymentLogRequest = new PaymentLogRequest();
        paymentLogRequest.setMessage(operationNote);
        paymentLogRequest.setMonthlySum("" + paymentAmount);
        paymentLogRequest.setPaymentPeriod(paymentPeriod.getPayPalPeriod());
        paymentLogRequest.setProfileStartDate(profileStartDate.toString());
        paymentLogRequest.setPaymentReason(paymentReason);
        paymentLogRequest.setUserId(userId);
        final Integer siteId = owner instanceof Site ? owner.getId() : null;
        final Integer childSiteSettingsId = owner instanceof ChildSiteSettings ? owner.getId() : null;
        paymentLogRequest.setChildSiteSettingsId(childSiteSettingsId);
        paymentLogRequest.setSiteId(siteId);
        paymentLogRequest.setSum("" + paymentAmount);

        //Saving pre-sent log to DB.
        final PaymentLog log = paymentLogger.logPayPalTransaction(TransactionStatus.SENT_UNCONFIRMED, paymentLogRequest);

        return createRecurringPaymentsProfileInternal(token, paymentAmount, paymentPeriod, 1, profileStartDate,
                "Monthly cost of", log, "" + log.getLogId());
    }

    public String createRecurringPaymentsProfileForPaypalButton(final Date profileStartDate, final PaymentPeriod paymentPeriod,
                                                                final String token, final double paymentAmount,
                                                                final int billingFrequency, final String description,
                                                                final int filledFormId, final Integer userId,
                                                                final Integer groupsFilledItemId,
                                                                final Integer createdOrdersRecordId,
                                                                final String userAddedToGroups) {
        final String operationNote = "Create recurring payments profile for paypal button." +
                " Profile settings are: paymentPeriod - " + paymentPeriod + ". billingFrequency - " + billingFrequency +
                " Operation descrption: " + description + "." +
                " Created orders form Id = " + createdOrdersRecordId + "." +
                (groupsFilledItemId != null ?
                        " User should be added to groups: " + ServiceLocator.getPersistance().getFilledFormItemById(groupsFilledItemId).getValue() +
                                " User was added to next groups: " + userAddedToGroups : "");

        /*Forming log request*/
        final PaymentLogRequest paymentLogRequest = new PaymentLogRequest();
        paymentLogRequest.setMessage(operationNote);
        paymentLogRequest.setMonthlySum("" + paymentAmount);
        paymentLogRequest.setPaymentPeriod(paymentPeriod.getPayPalPeriod());
        paymentLogRequest.setProfileStartDate(profileStartDate.toString());
        paymentLogRequest.setPaymentReason(PaymentReason.PAYPAL_BUTTON_RECURRING_PAYMENT);
        paymentLogRequest.setUserId(userId);
        paymentLogRequest.setSum("" + paymentAmount);
        paymentLogRequest.setFilledFormId(filledFormId);

        //Saving pre-sent log to DB.
        final PaymentLog log = paymentLogger.logPayPalTransaction(TransactionStatus.SENT_UNCONFIRMED, paymentLogRequest);

        return createRecurringPaymentsProfileInternal(token, paymentAmount, paymentPeriod, billingFrequency, profileStartDate,
                description, log, userId + ";" + groupsFilledItemId);
    }

    // profileReference - is a special custom parameters that Paypal will send us back into IPN.
    // for child site creation this parameter = logId, by this we are updating log.
    // for paypal button this parameter = userId;groupsFilledItemId, we need this to put user into groups.

    private String createRecurringPaymentsProfileInternal(final String token, final double paymentAmount,
                                                          final PaymentPeriod paymentPeriod, final int billingFrequency,
                                                          final Date profileStartDate, final String description, final PaymentLog log,
                                                          final String profileReference) {
        //NVPEncoder object is created and all the name value pairs are loaded into it.
        final NVPEncoder encoder = new NVPEncoder();
        final SimpleDateFormat GMTformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        encoder.add("METHOD", "CreateRecurringPaymentsProfile");
        encoder.add("TOKEN", token);
        encoder.add("PAYMENTACTION", "Order");
        encoder.add("VERSION", "53.0");
        encoder.add("AMT", "" + paymentAmount);
        encoder.add("CURRENCYCODE", "USD");
        encoder.add("BILLINGPERIOD", paymentPeriod.getPayPalPeriod());
        encoder.add("BILLINGFREQUENCY", "" + billingFrequency);
        encoder.add("PROFILESTARTDATE", GMTformat.format(profileStartDate));
        encoder.add("PROFILEREFERENCE", profileReference);
        encoder.add("INITAMT", "" + paymentAmount);
        encoder.add("DESC", description);

        final NVPDecoder decoder = new NVPDecoder();
        try {
            String strNVPString = encoder.encode();
            String strNVPResponse = payPalManager.getCaller().call(strNVPString);
            decoder.decode(strNVPResponse);
        } catch (final com.paypal.sdk.exceptions.PayPalException exception) {
            updatePayPalTransactionError(log, "Failed to create recurring payments profile.", decoder);
            throw new PayPalException(paypalInternational.get("exception"), exception);
        }

        final String acknowledgement = decoder.get("ACK");
        if (acknowledgement != null && !(acknowledgement.equals("Success") || acknowledgement.equals("SuccessWithWarning"))) {
            updatePayPalTransactionError(log, "Failed to create recurring payments profile. Acknowledgement:" + acknowledgement + ".", decoder);
            throw new PayPalException(paypalInternational.get("exception"));
        }

        final String profileId = decoder.get("PROFILEID");
        paymentLogger.updatePaypalTransactionStatusAndSetProfileId(TransactionStatus.SENT_CONFIRMED, profileId, log.getLogId());
        return profileId;
    }

    public PayPalRecurringProfileStatus getProfileStatus(final String profileId) {
        final NVPEncoder encoder = new NVPEncoder();
        final String operationNote = "Attempt to retrive profile status";
        encoder.add("METHOD", "GetRecurringPaymentsProfileDetails");
        encoder.add("PROFILEID", profileId);

        /*Forming log request*/
        final PaymentLogRequest paymentLogRequest = new PaymentLogRequest();
        paymentLogRequest.setProfileId(profileId);
        paymentLogRequest.setMessage(operationNote);

        final NVPDecoder decoder = new NVPDecoder();
        try {
            String strNVPString = encoder.encode();
            String strNVPResponse = payPalManager.getCaller().call(strNVPString);
            decoder.decode(strNVPResponse);
        } catch (final com.paypal.sdk.exceptions.PayPalException exception) {
            logPayPalError(paymentLogRequest, "Failed to get profile status.", decoder);
            throw new PayPalException(paypalInternational.get("exception"), exception);
        }

        final String acknowledgement = decoder.get("ACK");
        if (acknowledgement != null && !(acknowledgement.equals("Success") || acknowledgement.equals("SuccessWithWarning"))) {
            logPayPalError(paymentLogRequest, "Failed to get profile status. Acknowledgement:" + acknowledgement + ".", decoder);
            throw new PayPalException(paypalInternational.get("exception"));
        }

        paymentLogger.logPayPalTransaction(TransactionStatus.SENT_CONFIRMED, paymentLogRequest);

        return createRecurringPaymentStatus(decoder.get("STATUS"));
    }

    //Possible values are:
    //Suspend - i.e on site deactiavtion.
    //Reactivate - i.e on site reactivation.
    //Cancel - i.e on payment system change.

    void setProfileStatus(final String profileId, final PayPalRecurringProfileStatus neededStatus, final String note) {
        final NVPEncoder encoder = new NVPEncoder();
        encoder.add("METHOD", "ManageRecurringPaymentsProfileStatus");
        encoder.add("PROFILEID", profileId);
        encoder.add("ACTION", neededStatus.getActionForPaypalExcecution());
        encoder.add("NOTE", note);

        /*Forming log request*/
        final PaymentLogRequest paymentLogRequest = new PaymentLogRequest();
        paymentLogRequest.setProfileId(profileId);
        paymentLogRequest.setMessage(note);

        final NVPDecoder decoder = new NVPDecoder();
        try {
            String strNVPString = encoder.encode();
            String strNVPResponse = payPalManager.getCaller().call(strNVPString);
            decoder.decode(strNVPResponse);
        } catch (final com.paypal.sdk.exceptions.PayPalException exception) {
            logPayPalError(paymentLogRequest, "Failed to " + neededStatus + " profile.", decoder);
            throw new PayPalException(paypalInternational.get("exception"), exception);
        }

        final String acknowledgement = decoder.get("ACK");
        if (acknowledgement != null && !(acknowledgement.equals("Success") || acknowledgement.equals("SuccessWithWarning"))) {
            logPayPalError(paymentLogRequest, "Failed to " + neededStatus + " profile. Acknowledgement: " + acknowledgement + ".", decoder);
            throw new PayPalException(paypalInternational.get("exception"));
        }

        paymentLogger.logPayPalTransaction(TransactionStatus.SENT_CONFIRMED, paymentLogRequest);
    }

    public void updateProfilePrice(final String profileId, final double newPrice, final String note) {
        final NVPEncoder encoder = new NVPEncoder();
        encoder.add("METHOD", "UpdateRecurringPaymentsProfile");
        encoder.add("PROFILEID", profileId);
        encoder.add("AMT", "" + newPrice);
        encoder.add("NOTE", note);

        /*Forming log request*/
        final PaymentLogRequest paymentLogRequest = new PaymentLogRequest();
        paymentLogRequest.setProfileId(profileId);
        paymentLogRequest.setMessage(note);
        paymentLogRequest.setMonthlySum("" + newPrice);

        final NVPDecoder decoder = new NVPDecoder();
        try {
            String strNVPString = encoder.encode();
            String strNVPResponse = payPalManager.getCaller().call(strNVPString);
            decoder.decode(strNVPResponse);
        } catch (final com.paypal.sdk.exceptions.PayPalException exception) {
            logPayPalError(paymentLogRequest, "Failed to update profile price", decoder);
            throw new PayPalException(paypalInternational.get("exception"), exception);
        }

        final String acknowledgement = decoder.get("ACK");
        if (acknowledgement != null && !(acknowledgement.equals("Success") || acknowledgement.equals("SuccessWithWarning"))) {
            logPayPalError(paymentLogRequest, "Failed to update profile price. Acknowledgement: " + acknowledgement + ".", decoder);
            throw new PayPalException(paypalInternational.get("exception"));
        }

        paymentLogger.logPayPalTransaction(TransactionStatus.SENT_CONFIRMED, paymentLogRequest);
    }

    public void massPayment(List<PayPalPaymentRequest> paymentRequests, final PaymentReason paymentReason) {
        final NVPEncoder encoder = new NVPEncoder();
        encoder.add("METHOD", "MassPay");
        encoder.add("CURRENCYCODE", "USD");

        final List<PaymentLog> logsToUpdate = new ArrayList<PaymentLog>();
        for (int i = 0; i < paymentRequests.size(); i++) {
            final PayPalPaymentRequest request = paymentRequests.get(i);

            // Let us log a 'payment sent' operation.
            /*Forming log request*/
            final PaymentLogRequest paymentLogRequest = new PaymentLogRequest();
            paymentLogRequest.setSiteId(request.getSiteId());
            paymentLogRequest.setMessage(request.getNote());
            paymentLogRequest.setSum("" + request.getSum());
            paymentLogRequest.setPaymentReason(paymentReason);
            final PaymentLog log = paymentLogger.logPayPalTransaction(TransactionStatus.SENT_UNCONFIRMED, paymentLogRequest);

            encoder.add("L_EMAIL" + i, request.getEmail());
            encoder.add("L_AMT" + i, "" + request.getSum());
            encoder.add("L_NOTE" + i, request.getNote());
            encoder.add("L_UNIQUEID" + i, "" + log.getLogId());
            encoder.add("FIRSTNAME" + i, request.getNote());
            encoder.add("LASTNAME" + i, request.getNote());
            encoder.add("SUBSCRIBERNAME" + i, request.getNote());

            logsToUpdate.add(log);
        }

        final NVPDecoder decoder = new NVPDecoder();
        try {
            String strNVPString = encoder.encode();
            String strNVPResponse = payPalManager.getCaller().call(strNVPString);
            decoder.decode(strNVPResponse);
        } catch (final com.paypal.sdk.exceptions.PayPalException exception) {
            logPayPalErrors(logsToUpdate, "Failed to execute mass payment. PayPal payment mechanism internal error.", decoder);
            throw new PayPalException(paypalInternational.get("exception"), exception);
        }

        final String acknowledgement = decoder.get("ACK");
        if (acknowledgement != null && !(acknowledgement.equals("Success") || acknowledgement.equals("SuccessWithWarning"))) {
            logPayPalErrors(logsToUpdate, "Failed to execute mass payment. Acknowledgement: " + acknowledgement + ".", decoder);
            throw new PayPalException(paypalInternational.get("exception"));
        }

        // Ok, we got a successfull response from PayPal. Let's log this also.
        for (PaymentLog log : logsToUpdate) {
            paymentLogger.updateTransactionStatus(TransactionStatus.SENT_CONFIRMED, log.getLogId());
        }
    }

    /*-----------------------------------------------INTERNAL METHODS-------------------------------------------------*/

    private void updatePayPalTransactionError(final PaymentLog paymentLog, final String errorMessage, final NVPDecoder decoder) {
        final String resultErrorMessage = getPayPalError(errorMessage, decoder);
        paymentLogger.updateFailedTransaction(paymentLog.getLogId(), resultErrorMessage);
    }

    private void logPayPalError(final PaymentLogRequest paymentLogRequest, final String errorMessage, final NVPDecoder decoder) {
        final String resultErrorMessage = getPayPalError(errorMessage, decoder);
        paymentLogRequest.setErrorMessage(resultErrorMessage);
        paymentLogger.logPayPalTransaction(TransactionStatus.FAILED, paymentLogRequest);
    }

    private void logPayPalErrors(final List<PaymentLog> paymentLogs, final String errorMessage, final NVPDecoder decoder) {
        for (PaymentLog paymentLog : paymentLogs) {
            updatePayPalTransactionError(paymentLog, errorMessage, decoder);
        }
    }

    private String getPayPalError(final String errorMessage, final NVPDecoder decoder) {
        String result = "";
        int i = 0;
        while (!StringUtil.isNullOrEmpty(decoder.get("L_LONGMESSAGE" + i))) {
            result += decoder.get("L_LONGMESSAGE" + i) + ". ";
            i++;
        }
        return "Error:" + errorMessage + ". Error cause: " + result;
    }

    protected static PayPalRecurringProfileStatus createRecurringPaymentStatus(final String recurringPaymentStatus) {
        if (recurringPaymentStatus != null) {
            if (recurringPaymentStatus.equals("ActiveProfile") || recurringPaymentStatus.equals("Active")) {
                return PayPalRecurringProfileStatus.ACTIVE;
            }
            if (recurringPaymentStatus.equals("CancelledProfile") || recurringPaymentStatus.equals("Cancelled")) {
                return PayPalRecurringProfileStatus.CANCELED;
            }
            if (recurringPaymentStatus.equals("ExpiredProfile") || recurringPaymentStatus.equals("Expired")) {
                return PayPalRecurringProfileStatus.EXPIRED;
            }
            if (recurringPaymentStatus.equals("PendingProfile") || recurringPaymentStatus.equals("Pending")) {
                return PayPalRecurringProfileStatus.PENDING;
            }
            if (recurringPaymentStatus.equals("SuspendedProfile") || recurringPaymentStatus.equals("Suspended")) {
                return PayPalRecurringProfileStatus.SUSPENDED;
            }
        }
        throw new UnknownRecurringProfileStatusException("Can`t create PayPalRecurringProfileStatus by response: " + recurringPaymentStatus);
    }

    private final PayPalManager payPalManager;
    private final PaymentLogger paymentLogger = new PaymentLogger();
    private final International paypalInternational = ServiceLocator.getInternationStorage().get("paypal", Locale.US);
}