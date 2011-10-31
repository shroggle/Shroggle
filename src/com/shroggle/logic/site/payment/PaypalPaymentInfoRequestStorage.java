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
package com.shroggle.logic.site.payment;

import com.shroggle.presentation.site.payment.PaypalPaymentInfoRequest;
import com.shroggle.presentation.site.payment.PaypalPaymentInfoGenericRequest;
import com.shroggle.presentation.site.payment.PaypalButtonPaymentInfoRequest;

import java.util.*;

/**
 * @author dmitry.solomadin
 */
public class PaypalPaymentInfoRequestStorage {

    private Map<Integer, PaypalPaymentInfoGenericRequest> requests = new HashMap<Integer, PaypalPaymentInfoGenericRequest>();

    public int put(PaypalPaymentInfoGenericRequest request) {
        final int requestId = new Random().nextInt();

        requests.put(requestId, request);

        return requestId;
    }

    public PaypalPaymentInfoRequest getPaymentForChildSiteRequest(final int requestId) {
        PaypalPaymentInfoGenericRequest request = requests.get(requestId);
        if (request instanceof PaypalPaymentInfoRequest) {
            return (PaypalPaymentInfoRequest) request;
        } else {
            throw new IllegalArgumentException("Can't find payment request for child site by id=" + requestId);
        }
    }

    public PaypalButtonPaymentInfoRequest getPaymentForPaypalButtonRequest(final int requestId) {
        PaypalPaymentInfoGenericRequest request = requests.get(requestId);
        if (request instanceof PaypalButtonPaymentInfoRequest) {
            return (PaypalButtonPaymentInfoRequest) request;
        } else {
            throw new IllegalArgumentException("Can't find payment request for paypal button by id=" + requestId);
        }
    }

    public String getRequestsLog() {
        String log = "";

        for (Map.Entry entry : requests.entrySet()) {
            final PaypalPaymentInfoRequest request = (PaypalPaymentInfoRequest) entry.getValue();

            log += "Charge Type: " + request.getChargeType();
            log += "User Id: " + request.getUserId();
            log += "Payment Reason:" + request.getPaymentReason();
            log += "Payment Settings Owner Id: " + request.getPaymentSettingsOwnerId();
            log += "Payment Settings Owner Type: " + request.getPaymentSettingsOwnerType();
            log += "Redirect On Error Url: " + request.getRedirectOnErrorUrl();
            log += "Redirect To Url: " + request.getRedirectToUrl();
            log += "\n<br>";
        }

        return log;
    }

}
