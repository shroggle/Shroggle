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
package com.shroggle.util.payment;

import com.shroggle.entity.*;
import com.shroggle.exception.UnknownPaymentMethodException;
import com.shroggle.logic.payment.PaymentResult;
import com.shroggle.logic.payment.PaymentSystemRequest;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.international.International;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.payment.authorize.AuthorizeNet;
import com.shroggle.util.payment.authorize.AuthorizeNetMock;
import com.shroggle.util.payment.authorize.AuthorizeNetReal;
import com.shroggle.util.payment.javien.Javien;
import com.shroggle.util.payment.paypal.PayPal;
import com.shroggle.util.payment.paypal.PayPalMock;
import com.shroggle.util.payment.paypal.PayPalReal;

import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public abstract class PaymentSystem {

    public boolean isShrogglePaymentSystem() {
        return this == shroggleDefaultAuthorizeNet ||
                this == shroggleDefaultPayPal || this == shroggleDefaultJavien;
    }

    /**
     * @param paymentMethod - PayPal or Authorize.net
     * @return - Web-Deva`s payment system
     */
    public static PaymentSystem newInstance(final PaymentMethod paymentMethod) {
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Can`t create PaymentSystem without paymentMethod!");
        }
        switch (paymentMethod) {
            case AUTHORIZE_NET: {
                return shroggleDefaultAuthorizeNet;
            }
            case PAYPAL: {
                return shroggleDefaultPayPal;
            }
            default: {
                throw new UnknownPaymentMethodException("Unknown payment system = " + paymentMethod);
            }
        }
    }

    /**
     * @param properties - properties, which contains payment method: PayPal or Authorize.net and appropriate credentials.
     * @return - User`s custom payment system
     */
    public static PaymentSystem newInstance(final CreationProperties properties) {
        if (properties == null) {
            throw new IllegalArgumentException("Can`t create PaymentSystem without CreationProperties!");
        }
        switch (properties.getPaymentMethod()) {
            case AUTHORIZE_NET: {
                return createAuthorizeNet(properties);
            }
            case PAYPAL: {
                return createPayPal(properties);
            }
            default: {
                throw new UnknownPaymentMethodException("Unknown payment system = " + properties.getPaymentMethod());
            }
        }
    }


    public PaymentResult activateActivePaymentSettingsOwner(final PaymentSystemRequest request) {
        boolean purchaseComplete = true;
        try {
            return activateActivePaymentSettingsOwnerInternal(request);
        } catch (RuntimeException e) {
            purchaseComplete = false;
            throw e;
        } finally {
            sendPurchaseCompleteEmailAndSaveItToLog(purchaseComplete, request.getOwner(), request.getPrice(), request.getOldExpirationDate(), request.getNewExpirationDate(), request.getChargeType());
        }
    }

    public PaymentResult activatePendingPaymentSettingsOwner(final PaymentSystemRequest request) {
        boolean purchaseComplete = true;
        try {
            return activatePendingPaymentSettingsOwnerInternal(request);
        } catch (RuntimeException e) {
            purchaseComplete = false;
            throw e;
        } finally {
            sendPurchaseCompleteEmailAndSaveItToLog(purchaseComplete, request.getOwner(), request.getPrice(), request.getOldExpirationDate(), request.getNewExpirationDate(), request.getChargeType());
        }
    }

    public PaymentResult prolongActivity(final PaymentSettingsOwner owner, final Date oldExpirationDate, final Date newExpirationDate) {
        boolean purchaseComplete = true;
        try {
            return prolongActivityInternal(owner);
        } catch (RuntimeException e) {
            purchaseComplete = false;
            throw e;
        } finally {
            sendPurchaseCompleteEmailAndSaveItToLog(purchaseComplete, owner, owner.getSitePaymentSettings().getPrice(), oldExpirationDate, newExpirationDate, owner.getSitePaymentSettings().getChargeType());
        }
    }

    public abstract PaymentResult activateSuspendedRecurringProfile(final String recurringProfileId);


    /*------------------------------------------------Private methods.------------------------------------------------*/

    protected abstract PaymentResult activateActivePaymentSettingsOwnerInternal(final PaymentSystemRequest request);

    protected abstract PaymentResult activatePendingPaymentSettingsOwnerInternal(final PaymentSystemRequest request);

    protected abstract PaymentResult prolongActivityInternal(final PaymentSettingsOwner owner);

    private void sendPurchaseCompleteEmailAndSaveItToLog(final boolean purchaseComplete, final PaymentSettingsOwner owner,
                                                         final double price, final Date oldExpirationDate, final Date newExpirationDate, final ChargeType chargeType) {
        // Now we send email only for sites because we have site name/url in email and we don`t know it till a site has not been created. Tolik
        if (owner instanceof Site) {
            final Site site = (Site) owner;
            final User user = ServiceLocator.getPersistance().getUserById(site.getSitePaymentSettings().getUserId());

            final String emailBody = international.get(
                    "emailBody",
                    StringUtil.getEmptyOrString(user != null ? user.getFirstName() : ""),
                    StringUtil.getEmptyOrString(user != null ? user.getLastName() : ""),
                    international.get(chargeType.getPaymentPeriod().toString()),
                    new SiteManager(site).getPublicUrl(),
                    DateUtil.toMonthDayAndYear(new Date()),
                    price,
                    DateUtil.toMonthDayAndYear(newExpirationDate),
                    ServiceLocator.getConfigStorage().get().getUserSitesUrl(),
                    ServiceLocator.getConfigStorage().get().getSupportEmail()
            );
            boolean mailSent = true;
            if (purchaseComplete && user != null) {
                try {
                    final String fromEmail = (site.getChildSiteSettings() != null && !StringUtil.isNullOrEmpty(site.getChildSiteSettings().getFromEmail())) ? site.getChildSiteSettings().getFromEmail() : "";
                    final Mail mail = new Mail(user.getEmail(), emailBody, international.get("emailSubject", site.getTitle()));
                    mail.setFrom(fromEmail);
                    ServiceLocator.getMailSender().send(mail);
                } catch (Exception e) {
                    mailSent = false;
                }
            }
            final boolean errorsWhileSendingMail = !mailSent;
            ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
                @Override
                public void run() {
                    final PurchaseMailLog log = new PurchaseMailLog(purchaseComplete, errorsWhileSendingMail,
                            (user == null), emailBody, site.getSiteId(), site.getSitePaymentSettings().getUserId(),
                            oldExpirationDate, newExpirationDate);
                    ServiceLocator.getPersistance().putPurchaseMailLog(log);
                }
            });
        } else {
            Logger.getLogger(this.getClass().getName()).info("We don`t send 'purchase complete emails' for ChildSiteSettings. \n" +
                    "Its id = " + owner.getId() + ", " + (purchaseComplete ? "purchase complete successfully." :
                    "purchase is not complete."));
        }
    }
    /*------------------------------------------------Private methods.------------------------------------------------*/

    public static void setShroggleDefaultPayPal(PayPal shroggleDefaultPayPal) {
        PaymentSystem.shroggleDefaultPayPal = shroggleDefaultPayPal;
    }

    public static void setShroggleDefaultJavien(Javien shroggleDefaultJavien) {
        PaymentSystem.shroggleDefaultJavien = shroggleDefaultJavien;
    }

    public static void setShroggleDefaultAuthorizeNet(AuthorizeNet shroggleDefaultAuthorizeNet) {
        PaymentSystem.shroggleDefaultAuthorizeNet = shroggleDefaultAuthorizeNet;
    }

    // We need this method only for tests: we can`t use real AuthorizeNet in tests. Tolik

    private static AuthorizeNet createAuthorizeNet(final CreationProperties properties) {
        if (shroggleDefaultAuthorizeNet instanceof AuthorizeNetReal) {
            return new AuthorizeNetReal(properties.getAuthorizeLogin(), properties.getAuthorizeTransactionKey());
        } else {
            return new AuthorizeNetMock();
        }
    }

    // We need this method only for tests: we can`t use real PayPal in tests. Tolik

    private static PayPal createPayPal(final CreationProperties properties) {
        if (shroggleDefaultPayPal instanceof PayPalReal) {
            return new PayPalReal(properties.getPaypalApiUserName(), properties.getPaypalApiPassword(), properties.getPaypalSignature());
        } else {
            return new PayPalMock();
        }
    }

    private static PayPal shroggleDefaultPayPal;

    private static Javien shroggleDefaultJavien;

    private static AuthorizeNet shroggleDefaultAuthorizeNet;

    private final International international = ServiceLocator.getInternationStorage().get("paymentSystem", Locale.US);

    public static class CreationProperties {

        public CreationProperties(String authorizeLogin, String authorizeTransactionKey) {
            this.paymentMethod = PaymentMethod.AUTHORIZE_NET;
            this.authorizeLogin = authorizeLogin;
            this.authorizeTransactionKey = authorizeTransactionKey;
            this.paypalApiUserName = null;
            this.paypalApiPassword = null;
            this.paypalSignature = null;
        }

        public CreationProperties(String paypalApiUserName, String paypalApiPassword, String paypalSignature) {
            this.paymentMethod = PaymentMethod.PAYPAL;
            this.paypalApiUserName = paypalApiUserName;
            this.paypalApiPassword = paypalApiPassword;
            this.paypalSignature = paypalSignature;
            this.authorizeLogin = null;
            this.authorizeTransactionKey = null;
        }

        public PaymentMethod getPaymentMethod() {
            return paymentMethod;
        }

        public String getAuthorizeLogin() {
            return authorizeLogin;
        }

        public String getAuthorizeTransactionKey() {
            return authorizeTransactionKey;
        }

        public String getPaypalApiUserName() {
            return paypalApiUserName;
        }

        public String getPaypalApiPassword() {
            return paypalApiPassword;
        }

        public String getPaypalSignature() {
            return paypalSignature;
        }

        private final PaymentMethod paymentMethod;

        private final String authorizeLogin;

        private final String authorizeTransactionKey;

        private final String paypalApiUserName;

        private final String paypalApiPassword;

        private final String paypalSignature;
    }
}
