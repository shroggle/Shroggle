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
package com.shroggle.logic.site.billingInfo;

import com.shroggle.entity.*;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.payment.*;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.config.BillingInfoProperties;
import com.shroggle.util.config.Config;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.payment.PaymentSystem;
import com.shroggle.util.process.synchronize.SynchronizeContext;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.SynchronizeRequest;
import com.shroggle.util.process.synchronize.SynchronizeRequestEntity;

import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public class EnforcePayment {

    public static void execute() {
        final Date currentDate = new Date();
        for (final Site site : ServiceLocator.getPersistance().getAllSites()) {
            if (new SiteManager(site).isInactive() || site.getSitePaymentSettings().getExpirationDate().after(currentDate)) {
                continue;
            }
            if (site.getSitePaymentSettings() == null) {
                logger.log(Level.SEVERE, "Something is wrong here! Site (siteId = " + site.getSiteId() + ") " +
                        "without SitePaymentSettings. Check database and your code.");
                continue;
            }
            if (site.getSitePaymentSettings().getChargeType() == ChargeType.SITE_ONE_TIME_FEE) {
                logger.log(Level.SEVERE, "Something is wrong here! Sites with ChargeType = 'SITE_ONE_TIME_FEE' must not " +
                        "be here (EnforcePayment class). Please, check code, which set expirationDate for " +
                        "PaymentSettingsOwner with SITE_ONE_TIME_FEE chargeType! siteId = " + site.getSiteId());
                continue;
            }
            try {
                //------------------------------------------Purchase site-------------------------------------------
                //todo check site traffic
                final ChargeTypeManager manager = new ChargeTypeManager(site.getSitePaymentSettings().getChargeType());
                final Date oldExpirationDate = site.getSitePaymentSettings().getExpirationDate();
                final Date newExpirationDate = manager.createNewExpirationDateForActiveOwner(oldExpirationDate);

                PaymentSettingsOwnerManager paymentSettingsOwnerManager = new PaymentSettingsOwnerManager(site);
                final PaymentSystem paymentSystem = paymentSettingsOwnerManager.getAppropriatePaymentSystem();
                final PaymentResult paymentResult = paymentSystem.prolongActivity(site, oldExpirationDate, newExpirationDate);
                setNewExpirationDate(site.getSitePaymentSettings(), newExpirationDate);


                final ChildSiteSettings childSiteSettings = paymentSettingsOwnerManager.getChildSiteSettings();
                if (paymentResult != PaymentResult.SKIPPED && childSiteSettings != null && paymentSystem.isShrogglePaymentSystem()) {// We should add difference only if our paymentSystem is used. Tolik
                    //todo fix this. Now for "One Time Fee" settings we return all money to parent site owner. Tolik
                    final ChargeType chargeType = site.getSitePaymentSettings().getChargeType();
                    final double price = paymentSettingsOwnerManager.getPriceByChargeType(chargeType);
                    new SiteManager(childSiteSettings.getParentSite()).addDifferenceToIncomeSettings(price, chargeType, paymentSettingsOwnerManager.getInfoForPaymentLog());
                }
                //------------------------------------------Purchase site-------------------------------------------
            } catch (Exception exception) {
                logger.log(Level.SEVERE, "Can't purchase active site with siteId = " + site.getSiteId(), exception);
                deactivateOrSendNotificationMail(site);
            }
        }
    }

    /*-------------------------------------------------Private Methods------------------------------------------------*/

    protected static void setNewExpirationDate(final SitePaymentSettings sitePaymentSettings, final Date newExpirationDate) {
        try {
            final SynchronizeRequest synchronizeRequest = new SynchronizeRequestEntity(
                    SitePaymentSettings.class, SynchronizeMethod.WRITE, sitePaymentSettings.getSitePaymentSettingsId());
            ServiceLocator.getSynchronize().execute(synchronizeRequest, new SynchronizeContext<Void>() {
                public Void execute() {
                    ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
                        public void run() {
                            sitePaymentSettings.setExpirationDate(newExpirationDate);
                        }
                    });
                    return null;
                }
            });
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "Can't set new expiration date for sitePaymentSettings with id = " +
                    sitePaymentSettings.getSitePaymentSettingsId() + ".", exception);
        }
    }

    private static void deactivateOrSendNotificationMail(final Site site) {
        final Config config = ServiceLocator.getConfigStorage().get();
        final BillingInfoProperties properties = config.getBillingInfoProperties();
        PaymentSettingsOwnerManager manager = new PaymentSettingsOwnerManager(site);
        if (manager.hasToBeDeactivated()) {
            if (properties.isSendPaymentNotificationEmails()) {
                sendSiteDeactivatedMail(site);
            }
            try {
                manager.deactivate();
            } catch (Exception exception) {
                logger.log(Level.SEVERE, "Can't deactivate Site with id = " + site.getSiteId() + ".", exception);
            }
        } else if (properties.isSendPaymentNotificationEmails()) {
            sendSiteNotificationMail(site);
        }
    }


    private static void sendSiteDeactivatedMail(final Site site) {
        Config config = ServiceLocator.getConfigStorage().get();
        final String applicationUrl = StringUtil.getEmptyOrString(config.getApplicationUrl());
        String messageSubject = ServiceLocator.getInternationStorage().get("enforcePayments", Locale.US).get("siteSuspendedEmailSubject");
        final String fromEmail = (site.getChildSiteSettings() != null && !StringUtil.isNullOrEmpty(site.getChildSiteSettings().getFromEmail())) ? site.getChildSiteSettings().getFromEmail() : "";
        final SiteManager siteManager = new SiteManager(site);
        final String networkName = siteManager.getHisNetworkName();
        for (User user : siteManager.getAdmins()) {
            final String email = user.getEmail();
            final String firstName = StringUtil.getEmptyOrString(user.getFirstName());
            final String lastName = StringUtil.getEmptyOrString(user.getLastName());
            String messageBody = ServiceLocator.getInternationStorage().get("enforcePayments", Locale.US).get("siteSuspendedEmailBody", firstName, lastName, networkName, StringUtil.getEmptyOrString(site.getTitle()),
                    applicationUrl, site.getSiteId());
            try {
                final Mail mail = new Mail(email, messageBody, messageSubject);
                mail.setFrom(fromEmail);
                ServiceLocator.getMailSender().send(mail);
            } catch (Exception cantSendMailException) {
                logger.log(Level.SEVERE, "Can't send mail!", cantSendMailException);
            }
        }
    }

    private static void sendSiteNotificationMail(final Site site) {
        final SiteManager siteManager = new SiteManager(site);
        final String networkName = siteManager.getHisNetworkName();
        String messageSubject = ServiceLocator.getInternationStorage().get("enforcePayments", Locale.US).get("siteDeactivationWarningEmailSubject", networkName);
        Config config = ServiceLocator.getConfigStorage().get();
        final String applicationUrl = StringUtil.getEmptyOrString(config.getApplicationUrl());
        final CreditCard creditCard = site.getSitePaymentSettings().getCreditCard();
        final int daysBeforeDeactivation = new PaymentSettingsOwnerManager(site).getDaysBeforeDeactivation();
        String ccExpirationDate = "";
        if (creditCard != null) {
            ccExpirationDate = DateUtil.toMonthAndYear(creditCard.getExpirationYear(), creditCard.getExpirationMonth());
        }
        final String fromEmail = (site.getChildSiteSettings() != null && !StringUtil.isNullOrEmpty(site.getChildSiteSettings().getFromEmail())) ? site.getChildSiteSettings().getFromEmail() : "";
        for (User user : siteManager.getAdmins()) {
            final String firstName = StringUtil.getEmptyOrString(user.getFirstName());
            final String lastName = StringUtil.getEmptyOrString(user.getLastName());
            String messageBody = ServiceLocator.getInternationStorage().get("enforcePayments", Locale.US).get("siteDeactivationWarningEmailBody", firstName, lastName, networkName,
                    ccExpirationDate, site.getTitle(), daysBeforeDeactivation, applicationUrl, site.getSiteId());
            try {
                final Mail mail = new Mail(user.getEmail(), messageBody, messageSubject);
                mail.setFrom(fromEmail);
                ServiceLocator.getMailSender().send(mail);
            } catch (Exception cantSendMailException) {
                logger.log(Level.SEVERE, "Can`t send deactivation warning mail!", cantSendMailException);
            }
        }
    }

    private static final Logger logger = Logger.getLogger(EnforcePayment.class.getName());

}
