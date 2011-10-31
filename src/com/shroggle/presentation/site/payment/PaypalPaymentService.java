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
import com.shroggle.exception.PaymentException;
import com.shroggle.logic.payment.PaymentSettingsOwnerManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.payment.paypal.PayPal;
import com.shroggle.util.payment.paypal.PayPalRecurringProfileStatus;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * Author: dmitry.solomadin
 */
//todo. Add tests. Solomadin Dmitry
@RemoteProxy
public class PaypalPaymentService extends AbstractService {

    @RemoteMethod
    public GoToPaypalResponse goToPaypal(final PaypalRequest request) throws Exception {
        final PaymentReason paymentReason = PaymentReason.SHROGGLE_MONTHLY_PAYMENT;
        final Site site = ServiceLocator.getPersistance().getSite(request.getOwnerId());

        if (site == null) {
            throw new PaymentException("Cannot pay for null site. Site by Id=" + request.getOwnerId() + " not found.");
        }

        final GoToPaypalResponse response = new GoToPaypalResponse();
        if (site.getSitePaymentSettings().getSiteStatus() == SiteStatus.ACTIVE &&
                site.getSitePaymentSettings().getChargeType() == ChargeType.SITE_ONE_TIME_FEE) {
            response.setActivatedWithOneTimeFee(true);
            return response;
        }

        final String redirectOnErrorUrl = "/account/updatePaymentInfo.action?showPaypalError=true";
        final PaypalPaymentInfoRequest paymentInfoRequest =
                new PaypalPaymentInfoRequest(request.getOwnerId(), PaymentSettingsOwnerType.SITE, request.getChargeType(),
                        request.getRedirectToUrl(), redirectOnErrorUrl, paymentReason, null);
        final int requestId = ServiceLocator.getPaypalPaymentInfoRequestStorage().put(paymentInfoRequest);

        final String description;
        if (request.getChargeType().getPaymentPeriod().equals(PaymentPeriod.MONTH)) {
            description = "Monthly cost of " + site.getTitle() + " site.";
        } else {
            description = "Annual cost of " + site.getTitle() + " site.";
        }
        // Here we should check, which paypal we should use: default Web-Deva`s or own. Tolik
        final PayPal payPal = (PayPal) new PaymentSettingsOwnerManager(site).getAppropriatePaymentSystem();

        final String applicationUrl = "http://" + ServiceLocator.getConfigStorage().get().getApplicationUrl();
        final String token = payPal.setCustomerBillingAgreement(applicationUrl + "/account/createProfile.action?requestId=" + requestId,
                applicationUrl + "/account/updatePaymentInfo.action", description, paymentReason, site.getSiteId(), null, null);

        response.setPaypalLink(payPal.getPaypalLink() + token);

        if (site.getSitePaymentSettings().getSiteStatus() == SiteStatus.ACTIVE) {
            if (site.getSitePaymentSettings().getPaymentMethod() == PaymentMethod.AUTHORIZE_NET) {
                response.setJavienActivated(true);
            } else if (payPal.getProfileStatus(site.getSitePaymentSettings().getRecurringPaymentId()) == PayPalRecurringProfileStatus.ACTIVE) {
                response.setActiveProfile(true);
            }
        }
        return response;
    }

    @RemoteMethod
    public String goToPaypalCSR(final PaypalRequest request) throws Exception {
        final PaymentReason paymentReason = PaymentReason.CHILD_SITE_CREATION;

        //Modifying redirect url
        //Check on hash presence. We sould remove all old hash.
        if (request.getRedirectToUrl().contains("#")) {
            request.setRedirectToUrl(request.getRedirectToUrl().substring(0, request.getRedirectToUrl().indexOf("#")));
        }

        if (request.getPageBreakIndex() > request.getTotalPageBreaks()) {
            //If payment page is last page then update return url with setting that will show message about registration completion.
            request.setRedirectToUrl(request.getRedirectToUrl() + "#showSuccessMessageLastPage" + request.getWidgetId());
        } else {
            //Upadting return from paypal url with correct form page.
            request.setRedirectToUrl(request.getRedirectToUrl() + (request.getRedirectToUrl().contains("?") ? "&" : "?")
                    + "pageBreaksToPass=" + request.getPageBreakIndex()
                    + "&filledFormToUpdateId=" + request.getFilledFormToUpdateId()
                    + "&childSiteUserId=" + request.getChildSiteUserId()
                    + "&settingsId=" + request.getSettingsId()
                    + "#showSuccessMessage" + request.getWidgetId());
        }

        final PaypalPaymentInfoRequest paymentInfoRequest =
                new PaypalPaymentInfoRequest(request.getOwnerId(), PaymentSettingsOwnerType.CHILD_SITE_SETTINGS,
                        request.getChargeType(), request.getRedirectToUrl(), createRedirectOnErrorUrl(request),
                        paymentReason, request.getChildSiteUserId());
        final int requestId = ServiceLocator.getPaypalPaymentInfoRequestStorage().put(paymentInfoRequest);

        final ChildSiteSettings childSiteSettings = ServiceLocator.getPersistance().getChildSiteSettingsById(request.getSettingsId());
        // Here we should check, which paypal we should use: default Web-Deva`s or own. Tolik
        final PayPal payPal = (PayPal) new PaymentSettingsOwnerManager(childSiteSettings).getAppropriatePaymentSystem();

        final String networkName = childSiteSettings.getChildSiteRegistration().getName();
        final String applicationUrl = "http://" + ServiceLocator.getConfigStorage().get().getApplicationUrl();
        final String token = payPal.setCustomerBillingAgreement(
                applicationUrl + "/account/createProfile.action?requestId=" + requestId,
                request.getRedirectToUrl() + (request.getRedirectToUrl().contains("?") ? "&" : "?") + "pageBreaksToPass=" + request.getPageBreakIndex() +
                        "&filledFormToUpdateId=" + request.getFilledFormToUpdateId(), (networkName + " fee for child site"), paymentReason,
                null, request.getOwnerId(), request.getChildSiteUserId());

        return (payPal.getPaypalLink() + token);
    }

    private String createRedirectOnErrorUrl(final PaypalRequest request) {
        final int pageBreakIndexAfterError = request.isPaymentRequired() ? (request.getPageBreakIndex() - 1) : request.getPageBreakIndex();
        final String showPaypalErrorMessage = request.isPaymentRequired() ? "&paymentError" + request.getFilledFormToUpdateId() + "=true" : "";
        return request.getRedirectToUrl() + (request.getRedirectToUrl().contains("?") ? "&" : "?") +
                "pageBreaksToPass=" + pageBreakIndexAfterError + "&filledFormToUpdateId=" +
                request.getFilledFormToUpdateId() + showPaypalErrorMessage;
    }


}
