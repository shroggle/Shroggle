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
import com.shroggle.entity.PaymentLog;
import com.shroggle.exception.PaypalButtonCustomParameterMalformedException;
import com.shroggle.logic.gallery.GalleryManager;
import com.shroggle.logic.gallery.paypal.PaypalButtonHelper;
import com.shroggle.logic.gallery.paypal.PaypalButtonIPNRequest;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.payment.TransactionStatus;
import com.shroggle.logic.user.UserManager;
import com.shroggle.presentation.Action;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.international.International;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.UrlBinding;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author dmitry.solomadin
 *         Instant Payment Notification listener.
 *         <p/>
 *         For more info on this read next links:
 *         IPN general info: https://cms.paypal.com/us/cgi-bin/?&cmd=_render-content&content_ID=developer/e_howto_admin_IPNIntro#id091F0M006Y4
 *         Sample Code: https://cms.paypal.com/us/cgi-bin/?&cmd=_render-content&content_ID=developer/library_code_ipn_code_samples
 *         Mass Payment IPN: https://cms.paypal.com/us/cgi-bin/?cmd=_render-content&content_ID=developer/howto_api_masspay
 *         IPN Testing: https://cms.paypal.com/us/cgi-bin/?&cmd=_render-content&content_ID=developer/e_howto_admin_IPNTesting
 *         IPN Variables: https://cms.paypal.com/us/cgi-bin/?&cmd=_render-content&content_ID=developer/e_howto_html_IPNandPDTVariables
 *         <p/>
 *         This class recieves notification from PayPal about success or failure of transaction.
 *         <p/>
 *         Currently it recieves three types of notifications:
 *         - masspay. Updates transaction status as COMPLETED.
 *         - recurring_payment_profile_created. Updates transaction status as COMPLETED.
 *         - cart. Create's a new record in orders form. Put's visitor/user that have payed for item to specified group.
 *         - recurring_payment. Indicates that recurring payment recieved. In that case we should put user into groups.
 */
@UrlBinding("/site/payment/IPNListener.action")
public class IPNListener extends Action {

    @DefaultHandler
    public void handleNotification() throws IOException {
        final HttpServletRequest request = getContext().getRequest();

        // read post from PayPal system and add 'cmd'
        final Enumeration en = request.getParameterNames();
        String str = "cmd=_notify-validate";
        while (en.hasMoreElements()) {
            String paramName = (String) en.nextElement();
            String paramValue = getContext().getRequest().getParameter(paramName);
            str = str + "&" + paramName + "=" + URLEncoder.encode(paramValue);
        }

        // post back to PayPal system to validate
        final URL u = new URL("https://www.paypal.com/cgi-bin/webscr");
        final URLConnection uc = u.openConnection();
        uc.setDoOutput(true);
        uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        PrintWriter pw = new PrintWriter(uc.getOutputStream());
        pw.println(str);
        pw.close();

        final BufferedReader in = new BufferedReader(
                new InputStreamReader(uc.getInputStream()));
        final String payPalVerifyReponse = in.readLine();
        in.close();

        handleNotificationInternal(payPalVerifyReponse);
    }

    public static String getFullUrl() {
        final String applicationUrl = "http://" + ServiceLocator.getConfigStorage().get().getApplicationUrl();
        return applicationUrl + "/site/payment/IPNListener.action";
    }

    protected void handleNotificationInternal(final String payPalVerifyReponse) {
        final HttpServletRequest request = getContext().getRequest();

        // Logging PayPal IPN.
        IPNLogger.log(request);

        final String txnType = request.getParameter("txn_type");

        if (StringUtil.isNullOrEmpty(txnType)) {
            logger.log(Level.SEVERE, "IPN Listener Exception! Cannot find transaction type in request parameters.");
            return;
        }

        // Check notification validity
        if (payPalVerifyReponse.equals("VERIFIED")) {
            if (txnType.equals("masspay")) {
                processMassPayNotification(request);
            } else if (txnType.equals("recurring_payment_profile_created")) {
                processRecurringProfileNotification(request);
            } else if (txnType.equals("cart")) {
                processPaypalButtonNotification(request);
            } else if (txnType.equals("recurring_payment")) {
                processPaypalButtonRecurringPaymentNotification(request);
            }
        } else if (payPalVerifyReponse.equals("INVALID")) {
            logger.log(Level.SEVERE, "IPN Listener Exception! Got wrong payment notification from PayPal.");
        } else {
            logger.log(Level.SEVERE, "IPN Listener Exception! Got wrong payment notification from PayPal.");
        }
    }

    private void processMassPayNotification(final HttpServletRequest request) {
        final String paymentStatus = request.getParameter("payment_status");
        final TransactionStatus transactionStatus = paymentStatus.equals("Processed") ?
                TransactionStatus.PROCESSING : TransactionStatus.COMPLETED;

        // Mass payment notification processing.
        int i = 1;
        String uniqueId = request.getParameter("unique_id_" + i);
        while (uniqueId != null) {
            final PaymentLog paymentLog = persistance.getPaymentLogById(Integer.parseInt(uniqueId));

            // Getting parameters from request.
            final String txnId = request.getParameter("masspay_txn_id_" + i);
            final String sum = request.getParameter("payment_gross_" + i);

            i++;
            uniqueId = request.getParameter("unique_id_" + i);

            if (paymentLog == null) {
                logger.log(Level.SEVERE, "IPN Listener Exception! Cannot find log by Id=" + uniqueId + " for mass payment request. TransactionId = " + txnId);
                continue;
            }

            persistanceTransaction.execute(new Runnable() {
                public void run() {
                    paymentLog.setTransactionId(txnId);
                    paymentLog.setSum(sum);
                    paymentLog.setTransactionStatus(transactionStatus);
                }
            });
        }
    }

    private void processRecurringProfileNotification(final HttpServletRequest request) {
        final String uniqueId = request.getParameter("rp_invoice_id");
        final String profileStatus = request.getParameter("profile_status");

        final PaymentLog paymentLog = persistance.getPaymentLogById(Integer.parseInt(uniqueId));

        if (paymentLog == null) {
            logger.log(Level.SEVERE, "IPN Listener Exception! Cannot find log by Id=" + uniqueId + " for recurring profile request.");
            return;
        }

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                paymentLog.setProfileStatus(profileStatus);
                paymentLog.setTransactionStatus(TransactionStatus.COMPLETED);
            }
        });
    }

    private void processPaypalButtonNotification(final HttpServletRequest request) {
        final String paypalButtonCustomParameter = request.getParameter("custom");
        final List<PaypalButtonIPNRequest> ipnRequests = new ArrayList<PaypalButtonIPNRequest>();
        for (PaypalButtonIPNRequest ipnRequest : PaypalButtonHelper.parsePaypalButtonCustomParameter(paypalButtonCustomParameter)) {
            ipnRequests.add(ipnRequest);

            new PaypalButtonHelper().createOrderRecord(ipnRequest.getGalleryId(), ipnRequest.getUserId(),
                    ipnRequest.getProductNameFilledItemId(), ipnRequest.getPriceFilledItemId(), ipnRequest.getRegistrationFormId());

            if (ipnRequest.getGroupsFilledItemId() != null) {
                new PaypalButtonHelper().addUserToGroups(ipnRequest.getUserId(), ipnRequest.getGroupsFilledItemId());
            }
        }
        sendPurchaseEmails(ipnRequests);
    }

    private void processPaypalButtonRecurringPaymentNotification(final HttpServletRequest request) {
        // paypalButtonCustomParameter comes with such structure: userId;groupsFilledItemId if
        final String paypalButtonRecurringPaymentCustomParameter = request.getParameter("rp_invoice_id");
        PaypalButtonHelper.ParsedCustomPaypalButtonRecurringPaymentParameter parsedParameter;
        try {
            parsedParameter = PaypalButtonHelper.parseCustomPaypalButtonRecurringPaymentParameter(paypalButtonRecurringPaymentCustomParameter);
        } catch (PaypalButtonCustomParameterMalformedException ex) {
            // If parameter is malformed then it's an IPN for child site or shroggle monthly payement profile.
            return;
        }

        if (parsedParameter.getUserId() == null || parsedParameter.getGroupsFilledItemId() == null) {
            // If userId is null then 'trackOrders' wasn't enabled and if groupsFilledItemId is null then
            // there was no group items in filledForm.
            return;
        }

        new PaypalButtonHelper().addUserToGroups(parsedParameter.getUserId(), parsedParameter.getGroupsFilledItemId());
    }

    void sendPurchaseEmails(final List<PaypalButtonIPNRequest> ipnRequests) { // todo. Add tests. Tolik
        if (ipnRequests == null || ipnRequests.isEmpty()) {
            return;
        }
        // RegistrationFormId and userId in all requests are equals, so we can just take the first.
        final int registrationFormId = ipnRequests.get(0).getRegistrationFormId();
        final int userId = ipnRequests.get(0).getUserId();
        final List<FilledForm> filledForms = persistance.getFilledFormsByFormAndUserId(registrationFormId, userId);

        // I`m not sure that it`s correct.
        final FilledForm filledForm = filledForms.isEmpty() ? null : filledForms.get(0);
        final StringBuilder filledFormItemsNameValuePair = new StringBuilder("");
        if (filledForm != null) {
            for (FilledFormItem filledFormItem : filledForm.getFilledFormItems()) {
                filledFormItemsNameValuePair.append(filledFormItem.getItemName());
                filledFormItemsNameValuePair.append(": ");
                filledFormItemsNameValuePair.append(filledFormItem.getValue());
                filledFormItemsNameValuePair.append("\n");
            }
        }

        final International international = ServiceLocator.getInternationStorage().get("purchaseEmails", Locale.US);
        final StringBuilder productsInfo = new StringBuilder("");
        double total = 0;
        for (Map.Entry<Integer, List<PaypalButtonIPNRequest>> entry : groupRequestByProduct(ipnRequests).entrySet()) {
            final String productName = persistance.getFilledFormItemById(entry.getKey()).getValue();
            final double productPrice = entry.getValue().get(0).getPriceWithTax();
            final int productsCount = entry.getValue().size();
            productsInfo.append(international.get("itemPurchasedQuantityPriceText", productName, productsCount, productPrice));

            total += (productPrice * productsCount);
        }
        final String galleryName = new GalleryManager(ipnRequests.get(0).getGalleryId()).getName();
        final SiteManager siteManager = new SiteManager(ipnRequests.get(0).getSiteId());
        final String siteName = siteManager.getName();

        final String emailForSiteAdminSubject = international.get("emailForSiteAdminSubject", total, galleryName);
        final String emailForSiteAdminBody = international.get("emailForSiteAdminBody", galleryName, filledFormItemsNameValuePair.toString(), productsInfo.toString(), total);

        for (String email : siteManager.getAdminsEmails()) {
            final Mail mail = new Mail(email, emailForSiteAdminBody, emailForSiteAdminSubject);
            ServiceLocator.getMailSender().send(mail);
        }

        final String emailForBuyerSubject = international.get("emailForBuyerSubject");
        final String emailForBuyerBody = international.get("emailForBuyerBody", siteName, filledFormItemsNameValuePair.toString(), productsInfo.toString(), total);

        final Mail mail = new Mail(new UserManager(ipnRequests.get(0).getUserId()).getEmail(), emailForBuyerBody, emailForBuyerSubject);
        ServiceLocator.getMailSender().send(mail);
    }

    private Map<Integer, List<PaypalButtonIPNRequest>> groupRequestByProduct(final List<PaypalButtonIPNRequest> ipnRequests) {
        final Map<Integer, List<PaypalButtonIPNRequest>> groupedRequests = new HashMap<Integer, List<PaypalButtonIPNRequest>>();
        for (PaypalButtonIPNRequest request : ipnRequests) {
            List<PaypalButtonIPNRequest> requestsByProduct = groupedRequests.get(request.getProductNameFilledItemId());
            if (requestsByProduct == null) {
                requestsByProduct = new ArrayList<PaypalButtonIPNRequest>();
                groupedRequests.put(request.getProductNameFilledItemId(), requestsByProduct);
            }
            requestsByProduct.add(request);
        }
        return groupedRequests;
    }

    private final Logger logger = Logger.getLogger(IPNListener.class.getName());
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
