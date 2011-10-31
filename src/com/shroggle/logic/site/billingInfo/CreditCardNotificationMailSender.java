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

import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.creditCard.CreditCardManager;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.international.International;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.persistance.PersistanceContext;

import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This service informing users with admin rights for current active site that credit card
 * associated with this site will soon expire or has expired.
 *
 * @author Balakirev Anatoliy
 */
public class CreditCardNotificationMailSender extends SitesBillingInfoChecker {

    CreditCardNotificationMailSender(final long delay, final long period) {
        super(delay, period);
    }

    public void run() {
        ServiceLocator.getPersistance().inContext(new PersistanceContext<Void>() {
            public Void execute() {
                final Date currentDate = new Date();
                final International international = ServiceLocator.getInternationStorage().get("creditCardNotificationMail", Locale.US);
                final Logger logger = Logger.getLogger(CreditCardNotificationMailSender.class.getName());
                for (Site site : ServiceLocator.getPersistance().getAllSites()) {
                    final SiteManager siteManager = new SiteManager(site);
                    if (siteManager.isInactive() || site.getSitePaymentSettings().getCreditCard() == null ||
                            site.getSitePaymentSettings().getCreditCard().getCreditCardNumber().equals(CreditCardManager.getTestCreditCardNumber())) {
                        continue;
                    }
                    final CreditCardManager manager = new CreditCardManager(site.getSitePaymentSettings().getCreditCard());
                    if (!manager.isCreditCardDueToExpireOrExpired(currentDate)) {
                        continue;
                    }
                    if (manager.isNotificationMessageSent()) {
                        continue;
                    }
                    final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
                    String applicationUrl = StringUtil.getEmptyOrString(configStorage.get().getApplicationUrl());
                    String messageSubject = international.get("expiringCreditCardsWarningEmailSubject", site.getTitle());
                    final String fromEmail = (site.getChildSiteSettings() != null && !StringUtil.isNullOrEmpty(site.getChildSiteSettings().getFromEmail())) ? site.getChildSiteSettings().getFromEmail() : "";

                    final String networkName = siteManager.getHisNetworkName();
                    for (User user : siteManager.getAdmins()) {
                        if (new UserRightManager(user).toSite(site).isFromNetwork()) {
                            continue;
                        }
                        String email = user.getEmail();
                        final String firstName = StringUtil.getEmptyOrString(user.getFirstName());
                        final String lastName = StringUtil.getEmptyOrString(user.getLastName());
                        String messageBody = international.get(
                                "expiringCreditCardsWarningEmailBody", firstName, lastName, networkName, site.getTitle(),
                                applicationUrl, site.getSiteId()
                        );
                        try {
                            final Mail mail = new Mail(email, messageBody, messageSubject);
                            mail.setFrom(fromEmail);
                            ServiceLocator.getMailSender().send(mail);
                            manager.setNotificationMessageSent(new Date());
                        } catch (Exception exception) {
                            logger.log(Level.SEVERE, "Can't send mail!", exception);
                        }
                    }
                }
                return null;
            }
        });
    }
}