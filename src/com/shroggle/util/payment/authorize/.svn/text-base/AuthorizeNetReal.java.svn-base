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
import com.shroggle.exception.CreditCardNotFoundException;
import com.shroggle.exception.HttpConnectionException;
import com.shroggle.logic.creditCard.CreditCardManager;
import com.shroggle.logic.site.payment.PaymentLogRequest;
import com.shroggle.logic.site.payment.PaymentLogger;
import com.shroggle.logic.site.payment.TransactionStatus;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigAuthorizeNet;
import com.shroggle.util.http.HttpConnection;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Balakirev Anatoliy
 */
public class AuthorizeNetReal extends AuthorizeNet {

    public AuthorizeNetReal(final String login, String transactionKey) {
        this.login = login;
        this.transactionKey = transactionKey;
    }

    /*
    * userId - parameter for logging. May be null. For purchasing from child site registration represents child user id
    * that was created during form filling.
    */

    void purchase(final CreditCard card, final PaymentSettingsOwner owner, final double price,
                  final PaymentReason paymentReason, final Integer userId) {
        if (card == null) {
            throw new CreditCardNotFoundException("Cant purchase by null CreditCard");
        }

        final PaymentLogRequest paymentLogRequest = initPurchasingLog(card, owner, price, paymentReason, userId);
        final PaymentLogger paymentLogger = new PaymentLogger();
        try {
            purchase(card, price, paymentLogRequest.getMessage());
        } catch (Exception e) {
            paymentLogRequest.setErrorMessage(e.getMessage());
            paymentLogger.logAuthorizeNetTransaction(TransactionStatus.FAILED, paymentLogRequest);
            throw new AuthorizeNetException(e);
        }
        paymentLogger.logAuthorizeNetTransaction(TransactionStatus.SENT_CONFIRMED, paymentLogRequest);
    }

    private PaymentLogRequest initPurchasingLog(final CreditCard card, final PaymentSettingsOwner owner, final double price,
                                                final PaymentReason paymentReason, final Integer userId) {
        final PaymentLogRequest paymentLogRequest = new PaymentLogRequest();
        final Site site = owner instanceof Site ? (Site) owner : null;
        final ChildSiteSettings childSiteSettings = owner instanceof ChildSiteSettings ? (ChildSiteSettings) owner : null;
        paymentLogRequest.setChildSiteSettingsId(childSiteSettings != null ? childSiteSettings.getId() : null);
        paymentLogRequest.setSiteId(site != null ? site.getSiteId() : null);
        paymentLogRequest.setSum("" + price);
        paymentLogRequest.setPaymentReason(paymentReason);
        paymentLogRequest.setUserId(userId == null ?
                (site != null ? site.getSitePaymentSettings().getUserId() : null) : userId);
        paymentLogRequest.setCreditCard(card);
        paymentLogRequest.setMessage("Purchasing " + getSiteNameOrChildSiteSettingsInfo(site, childSiteSettings) + ".");
        return paymentLogRequest;
    }

    private void purchase(final CreditCard creditCard, final double price, final String description) {
        // Forming request
        final Map<String, String> post_values = new HashMap<String, String>();
        setLogin(post_values, login);
        setTransactionKey(post_values, transactionKey);
        setDefaultValues(post_values); // Setting default values
        setCreditCardNumber(post_values, creditCard.getCreditCardNumber());
        setCreditCardCode(post_values, creditCard.getSecurityCode());
        setBillingInfo(post_values, creditCard.getBillingAddress1(), creditCard.getPostalCode());// Need for AVS(Address Verification Service).
        setExpirationDate(post_values, new CreditCardManager(creditCard).createRealExpirationDate());
        setAmount(post_values, String.valueOf(price));
        setDescription(post_values, description);

        try {
            final ConfigAuthorizeNet config = ServiceLocator.getConfigStorage().get().getAuthorizeNet();
            final String response = new HttpConnection(config.getUrl(), post_values).submitData().getResponseBodyAsString();
            processResponse(response);
        } catch (HttpConnectionException e) {
            throw new AuthorizeNetException("Unable to purchase.", e);
        }
    }

    private void processResponse(final String response) {
        final Response authorizeNetResponse = new Response(response, DELIMITER);
        switch (authorizeNetResponse.getResponseCode()) {
            case APPROVED:
            case HELD_FOR_REVIEW: {
                return;
            }
            case ERROR:
            case DECLINE: {
                throw new AuthorizeNetException(authorizeNetResponse.getDetailedReasonText());
            }
            default: {
                throw new AuthorizeNetException("Unknown response code! " + authorizeNetResponse.getResponseCode());
            }
        }

    }


    private void setLogin(final Map<String, String> post_values, final String login) {
        post_values.put("x_login", login);
    }

    private void setTransactionKey(final Map<String, String> post_values, final String key) {
        post_values.put("x_tran_key", key);
    }

    private void setCreditCardNumber(final Map<String, String> post_values, final String creditCardNumber) {
        post_values.put("x_card_num", creditCardNumber);
    }

    private void setCreditCardCode(final Map<String, String> post_values, final String creditCardCode) {
        post_values.put("x_card_code", creditCardCode);
    }

    private void setBillingInfo(final Map<String, String> post_values, final String billingAddress, final String zipCode) {
        post_values.put("x_address", billingAddress);
        post_values.put("x_zip", zipCode);
    }


    private void setExpirationDate(final Map<String, String> post_values, final Date expirationDate) {
        post_values.put("x_exp_date", DateUtil.getAuthorizeNetDateFormat(expirationDate));
    }

    private void setAmount(final Map<String, String> post_values, final String amount) {
        post_values.put("x_amount", amount);
    }

    private void setDescription(final Map<String, String> post_values, final String description) {
        post_values.put("x_description", description);
    }

    private void setDefaultValues(final Map<String, String> post_values) {
        post_values.put("x_version", VERSION);
        post_values.put("x_delim_data", "TRUE");
        post_values.put("x_delim_char", DELIMITER);
        post_values.put("x_relay_response", "FALSE");
        post_values.put("x_type", "AUTH_CAPTURE");
        post_values.put("x_method", "CC");
        post_values.put("x_duplicate_window", "0");
    }

    private String getSiteNameOrChildSiteSettingsInfo(final Site site, final ChildSiteSettings childSiteSettings) {
        if (site != null) {
            return site.getTitle();
        }
        if (childSiteSettings != null) {
            return "Site has not been created yet. ChildSiteSettings id = " + childSiteSettings.getChildSiteSettingsId();
        }
        return "";
    }

    private final String login;
    private final String transactionKey;
    private final static String DELIMITER = "|";
    private final static String VERSION = "3.1";
}
