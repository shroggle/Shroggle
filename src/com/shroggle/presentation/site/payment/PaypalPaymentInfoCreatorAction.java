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
package com.shroggle.presentation.site.payment;

import com.shroggle.entity.*;
import com.shroggle.logic.payment.PaymentInfoCreator;
import com.shroggle.logic.payment.PaymentInfoCreatorRequest;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.util.ServiceLocator;
import com.shroggle.exception.PayPalException;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.exception.PaymentSettingsOwnerNotFoundException;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
@UrlBinding("/account/createProfile.action")
public class PaypalPaymentInfoCreatorAction extends Action {

    @DefaultHandler
    public Resolution execute() throws Exception {
        try {
            return executeInternal();
        } catch (PayPalException e) {
            final PaypalPaymentInfoRequest request = ServiceLocator.getPaypalPaymentInfoRequestStorage().getPaymentForChildSiteRequest(requestId);
            return resolutionCreator.redirectToUrl(request.getRedirectOnErrorUrl());
        }
    }

    private Resolution executeInternal() throws Exception {
        final PaypalPaymentInfoRequest request;
        try {
            request = ServiceLocator.getPaypalPaymentInfoRequestStorage().getPaymentForChildSiteRequest(requestId);
        } catch (IllegalArgumentException ex) {
            logger.log(Level.SEVERE, "Request that came to PaypalPaymentInfoCreatorAction is null for some reason." +
                    " RequestId=" + requestId);
            return resolutionCreator.redirectToUrl("/paymentException.jsp");
        }

        final String token = getContext().getRequest().getParameter("token");
        final PaymentInfoCreatorRequest paymentInfoCreatorRequest = new PaymentInfoCreatorRequest(request.getPaymentSettingsOwnerId(),
                request.getPaymentSettingsOwnerType(), request.getChargeType(), PaymentMethod.PAYPAL, request.getUserId(), token, null);
        try {
            persistanceTransaction.execute(new Runnable() {
                public void run() {
                    final PaymentInfoCreator paymentInfoCreator = new PaymentInfoCreator(paymentInfoCreatorRequest);
                    paymentInfoCreator.create();
                }
            });
        } catch (PaymentSettingsOwnerNotFoundException exception) {
            return resolutionCreator.redirectToUrl("/paymentException.jsp");
        }
        if (request.getPaymentSettingsOwnerType() == PaymentSettingsOwnerType.CHILD_SITE_SETTINGS) {
            return resolutionCreator.redirectToUrl(request.getRedirectToUrl());
        } else {
            return resolutionCreator.forwardToUrl(request.getRedirectToUrl());
        }
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private static final Logger logger = Logger.getLogger(PaypalPaymentInfoCreatorAction.class.getName());
    private int requestId;

}
