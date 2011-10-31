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

import com.shroggle.entity.FilledFormItem;
import com.shroggle.entity.PaymentMethod;
import com.shroggle.logic.groups.SubscriptionTimeType;
import com.shroggle.logic.site.taxRates.TaxManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.DoubleUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.payment.PaymentSystem;
import com.shroggle.util.payment.paypal.PayPal;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class PaypalButtonPaymentService extends AbstractService {

    @RemoteMethod
    public String goToPaypalFromPaypalButton(final PaypalButtonPaymentRequest request) throws Exception {
        //Modifying redirect url
        //Check on hash presence. We should remove all old hash.
        if (request.getRedirectToUrl().contains("#")) {
            request.setRedirectToUrl(request.getRedirectToUrl().substring(0, request.getRedirectToUrl().indexOf("#")));
        }

        final int siteId = ServiceLocator.getPersistance().getWidget(request.getWidgetId()).getSiteId();

        final String redirectOnErrorUrl =
                request.getRedirectToUrl() + "#showPaypalButtonPaymentSuccess" + request.getWidgetId();
        request.setRedirectToUrl(request.getRedirectToUrl() + "#showPaypalButtonPaymentSuccess" + request.getWidgetId());


        final FilledFormItem productFilledItem =
                ServiceLocator.getPersistance().getFilledFormItemById(request.getProductFilledItemId());
        final FilledFormItem priceFilledItem =
                ServiceLocator.getPersistance().getFilledFormItemById(request.getPriceFilledItemId());
        final FilledFormItem subscriptionFilledItem =
                ServiceLocator.getPersistance().getFilledFormItemById(request.getSubscriptionFilledItemId());

        final String productName = productFilledItem.getValue();
        final double priceWithTax = (DoubleUtil.safeParse(priceFilledItem.getValue()) + new TaxManager().calculateTaxForRender(
                priceFilledItem.getFilledForm(), DoubleUtil.safeParse(priceFilledItem.getValue()), null, siteId).getTax());
        final SubscriptionTimeType subscription = SubscriptionTimeType.valueOf(subscriptionFilledItem.getValue());

        final PaypalButtonPaymentInfoRequest paypalButtonPaymentInfoRequest =
                new PaypalButtonPaymentInfoRequest(priceWithTax, request.getProductFilledItemId(),
                        request.getPriceFilledItemId(), request.getSubscriptionFilledItemId(),
                        request.getGroupsFilledItemId(), request.getGalleryId(), request.getUserId(),
                        request.getFilledFormId(), request.getRegistrationFormId(), request.getRedirectToUrl(), redirectOnErrorUrl);
        final int requestId = ServiceLocator.getPaypalPaymentInfoRequestStorage().put(paypalButtonPaymentInfoRequest);

        final String applicationUrl = "http://" + ServiceLocator.getConfigStorage().get().getApplicationUrl();
        final String token = payPal.setCustomerBillingAgreementForPaypalButton(
                applicationUrl + "/account/createPaypalButtonProfile.action?requestId=" + requestId, request.getRedirectToUrl(),
                "Recurring payment profile creation for: " + productName + ", $" + priceWithTax + "/" + subscription.getText(), 0, request.getUserId());

        return (payPal.getPaypalLink() + token);
    }

    private final PayPal payPal = ((PayPal) PaymentSystem.newInstance(PaymentMethod.PAYPAL));

}
