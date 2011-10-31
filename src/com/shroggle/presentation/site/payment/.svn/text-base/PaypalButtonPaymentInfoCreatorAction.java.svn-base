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

import com.shroggle.entity.FilledForm;
import com.shroggle.entity.FilledFormItem;
import com.shroggle.entity.PaymentMethod;
import com.shroggle.exception.PayPalException;
import com.shroggle.exception.PaymentSettingsOwnerNotFoundException;
import com.shroggle.logic.gallery.paypal.PaypalButtonHelper;
import com.shroggle.logic.groups.SubscriptionTimeType;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.payment.PaymentSystem;
import com.shroggle.util.payment.paypal.PayPal;
import com.shroggle.util.persistance.PersistanceTransaction;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author dmitry.solomadin
 */
@UrlBinding("/account/createPaypalButtonProfile.action")
public class PaypalButtonPaymentInfoCreatorAction extends Action {

    @DefaultHandler
    public Resolution execute() throws Exception {
        try {
            return executeInternal();
        } catch (PayPalException e) {
            final PaypalButtonPaymentInfoRequest request =
                    ServiceLocator.getPaypalPaymentInfoRequestStorage().getPaymentForPaypalButtonRequest(requestId);
            return resolutionCreator.redirectToUrl(request.getRedirectOnErrorUrl());
        }
    }

    private Resolution executeInternal() throws Exception {
        final PaypalButtonPaymentInfoRequest request =
                ServiceLocator.getPaypalPaymentInfoRequestStorage().getPaymentForPaypalButtonRequest(requestId);
        if (request == null) {
            logger.log(Level.SEVERE, "Request that came to PaypalPaymentInfoCreatorAction is null for some reason");
            return resolutionCreator.redirectToUrl("/paymentException.jsp");
        }
        final String token = getContext().getRequest().getParameter("token");

        try {
            persistanceTransaction.execute(new Runnable() {
                public void run() {
                    FilledForm createdOrdersForm = paypalButtonHelper.createOrderRecord(request.getGalleryId(), request.getUserId(),
                            request.getProductFilledItemId(), request.getPriceFilledItemId(), request.getRegistrationFormId());

                    String userWasAddedToGroups = "";
                    if (request.getGroupsFilledItemId() != null) {
                        userWasAddedToGroups = paypalButtonHelper.addUserToGroups(request.getUserId(), request.getGroupsFilledItemId());
                    }

                    final double priceWithTax = request.getPriceWithTax();

                    final FilledFormItem productFilledItem =
                            ServiceLocator.getPersistance().getFilledFormItemById(request.getProductFilledItemId());
                    final String productName = productFilledItem.getValue();

                    final FilledFormItem subscriptionFilledItem =
                            ServiceLocator.getPersistance().getFilledFormItemById(request.getSubscriptionFilledItemId());
                    final SubscriptionTimeType subscriptionTimeType =
                            SubscriptionTimeType.valueOf(subscriptionFilledItem.getValue());

                    payPal.createRecurringPaymentsProfileForPaypalButton(new Date(), subscriptionTimeType.getPeriod(),
                            token, priceWithTax, subscriptionTimeType.getFrequency(),
                            "Recurring payment profile creation for: " + productName + ", $" + priceWithTax + "/" + subscriptionTimeType.getText(),
                            request.getFilledFormId(), request.getUserId(), request.getGroupsFilledItemId(),
                            (createdOrdersForm != null ? createdOrdersForm.getFilledFormId() : null),
                            userWasAddedToGroups);
                }
            });
        } catch (PaymentSettingsOwnerNotFoundException exception) {
            return resolutionCreator.redirectToUrl("/paymentException.jsp");
        }

        return resolutionCreator.redirectToUrl(request.getRedirectToUrl());
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
    private final PayPal payPal = ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL));
    private final PaypalButtonHelper paypalButtonHelper = new PaypalButtonHelper();
    private int requestId;


}
