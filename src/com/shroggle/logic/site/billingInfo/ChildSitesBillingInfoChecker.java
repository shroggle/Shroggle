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

import com.shroggle.entity.ChargeType;
import com.shroggle.entity.ChildSiteSettings;
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.logic.childSites.childSiteSettings.ChildSiteSettingsManager;
import com.shroggle.logic.site.PublishingInfoResponse;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.config.Config;
import com.shroggle.util.international.International;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.process.synchronize.SynchronizeContext;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.SynchronizeRequest;
import com.shroggle.util.process.synchronize.SynchronizeRequestEntity;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public class ChildSitesBillingInfoChecker {

    public static void execute() {
        final International international = ServiceLocator.getInternationStorage().get("childSitesBillingInfoChecker", Locale.US);
        final List<Site> childSites = ServiceLocator.getPersistance().getChildSites();
        for (Site childSite : childSites) {
            logger.info("Removing child site from network. It` id = " + childSite.getSiteId() + "," +
                    " it` number = " + childSites.indexOf(childSite) + " from " + childSites.size());
            final ChildSiteSettings childSiteSettings = childSite.getChildSiteSettings();
            ChildSiteSettingsManager manager = new ChildSiteSettingsManager(childSiteSettings);
            final Date currentDate = new Date();
            //------------------------Ten days before expiration - email is sent to child site admin--------------------
            if (manager.isNetworkMembershipDueToExpire(currentDate) && !manager.isNotificationMessageSent()) {
                sendNetworkMembershipDueToExpireMessage(international, childSite, childSiteSettings);
                manager.setNotificationMessageSent();
            }
            //------------------------Ten days before expiration - email is sent to child site admin--------------------

            //-----------------------If child site end date expired - change child site billing info--------------------
            if (manager.isNetworkMembershipExpired(currentDate)) {
                sendMessageAboutNewBillingInfo(international, childSite, manager.getNotificationMessageSentDate());
//                saveSiteAndSiteOnItemRights(childSite);
                removeFromNetwork(childSite);
            }
            //-----------------------If child site end date expired - change child site billing info--------------------

            //--------------------Send message if child site can be published and message was not sent before-----------
            if (new SiteManager(childSite).isPendingChildSite()) {
                final PublishingInfoResponse publishingInfo = manager.getPublishingInfo();
                if (publishingInfo.isCanBePublished() && publishingInfo.isCreatedBeforeStartDate()
                        && !publishingInfo.isCanBePublishedMessageSent()) {
                    sendCanBePublishedMessage(international, childSite);
                }
            }
            //--------------------Send message if child site can be published and message was not sent before-----------
        }
    }

    private static void sendNetworkMembershipDueToExpireMessage(
            final International international, final Site site, final ChildSiteSettings childSiteSettings) {
        String productPrice = String.valueOf(new ChargeTypeManager(ChargeType.SITE_250MB_MONTHLY_FEE).getPrice());
        final SiteManager siteManager = new SiteManager(site);
        String endDate = DateUtil.toMonthDayAndYear(childSiteSettings.getEndDate());
        final String childSiteRegistrationName = site.getChildSiteSettings().getChildSiteRegistration().getName();
        String messageSubject = international.get("networkMembershipExpiredInTenDaysSubject", childSiteRegistrationName);
        final String fromEmail = !StringUtil.isNullOrEmpty(site.getChildSiteSettings().getFromEmail()) ? site.getChildSiteSettings().getFromEmail() : "";
        final Config config = ServiceLocator.getConfigStorage().get();
        for (User user : siteManager.getAdmins()) {
            String messageBody = international.get("networkMembershipExpiredBody",
                    StringUtil.getEmptyOrString(user.getFirstName()),
                    StringUtil.getEmptyOrString(user.getLastName()),
                    childSiteRegistrationName,
                    endDate,
                    site.getTitle(),
                    productPrice,
                    config.getApplicationUrl(),
                    config.getApplicationName()
            );
            try {
                final Mail mail = new Mail(user.getEmail(), messageBody, messageSubject);
                mail.setFrom(fromEmail);
                ServiceLocator.getMailSender().send(mail);
            } catch (Exception exception) {
                logger.log(Level.SEVERE, "Can't send mail!", exception);
            }
        }

    }


    private static void sendMessageAboutNewBillingInfo(final International international, final Site site, final Date notificationMailDate) {
        final String fromEmail = !StringUtil.isNullOrEmpty(site.getChildSiteSettings().getFromEmail()) ? site.getChildSiteSettings().getFromEmail() : "";
        try {
            final Config config = ServiceLocator.getConfigStorage().get();
            String messageBody = international.get("newBillingInfoBody",
                    (notificationMailDate != null ? DateUtil.toMonthDayAndYear(notificationMailDate) : "unknown"),
                    site.getTitle(),
                    site.getChildSiteSettings().getChildSiteRegistration().getName(),
                    String.valueOf(new ChargeTypeManager(ChargeType.SITE_250MB_MONTHLY_FEE).getPrice()),
                    String.valueOf(new ChargeTypeManager(ChargeType.SITE_500MB_MONTHLY_FEE).getPrice()),
                    String.valueOf(new ChargeTypeManager(ChargeType.SITE_1GB_MONTHLY_FEE).getPrice()),
                    String.valueOf(new ChargeTypeManager(ChargeType.SITE_3GB_MONTHLY_FEE).getPrice()),
                    config.getSupportEmail(),
                    config.getApplicationName()
            );
            String messageSubject = international.get("newBillingInfoSubject", site.getChildSiteSettings().getChildSiteRegistration().getName());
            final SiteManager siteManager = new SiteManager(site);
            for (String email : siteManager.getAdminsEmails()) {
                final Mail mail = new Mail(email, messageBody, messageSubject);
                mail.setFrom(fromEmail);
                ServiceLocator.getMailSender().send(mail);
            }
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "Can't send mail!", exception);
        }
    }

    private static void sendCanBePublishedMessage(final International international, final Site site) {
        if (site != null) {
            try {
                final Config config = ServiceLocator.getConfigStorage().get();
                String messageBody = international.get("siteCanBePublishedMessageBody",
                        site.getTitle(),
                        config.getApplicationUrl(),
                        config.getSupportEmail()
                );
                String messageSubject = international.get("siteCanBePublishedMessageSubject", site.getTitle());
                final SiteManager siteManager = new SiteManager(site);
                final String fromEmail = !StringUtil.isNullOrEmpty(site.getChildSiteSettings().getFromEmail()) ? site.getChildSiteSettings().getFromEmail() : "";
                for (String email : siteManager.getAdminsEmails()) {
                    final Mail mail = new Mail(email, messageBody, messageSubject);
                    mail.setFrom(fromEmail);
                    ServiceLocator.getMailSender().send(mail);
                }
                new ChildSiteSettingsManager(site.getChildSiteSettings()).setCanBePublishedMessageSent();
            } catch (Exception exception) {
                logger.log(Level.SEVERE, "Can't send mail!", exception);
            }
        }
    }

    private static void removeFromNetwork(final Site site) {
        try {
            final SynchronizeRequest synchronizeRequest = new SynchronizeRequestEntity(
                    Site.class, SynchronizeMethod.WRITE, site.getSiteId());
            ServiceLocator.getSynchronize().execute(synchronizeRequest, new SynchronizeContext<Void>() {
                public Void execute() {
                    new SiteManager(site).disconnectFromNetwork();
                    return null;
                }
            });
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "Can't change site billing info! Site id: " + site.getSiteId(), exception);
        }
    }

    /*private static void saveSiteAndSiteOnItemRights(final Site site) {
        if (site.getSiteId() != 462 && site.getSiteId() != 467) {
            return;
        }
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream("D:\\savedSites\\siteWithRights" + site.getSiteId() + ".class"));
            objectOutputStream.writeObject(new SiteWithRights(site, ServiceLocator.getPersistance().getSiteOnItemsBySite(site.getSiteId())));
            logger.info("Saved site with id = " + site.getSiteId());
        } catch (Exception e) {
            logger.info("Unable to write objectOutputStream.");
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.flush();
                    objectOutputStream.close();
                }
            } catch (Exception e) {
                logger.info("Unable to close objectOutputStream.");
            }
        }
    }*/

    private static final Logger logger = Logger.getLogger(ChildSitesBillingInfoChecker.class.getName());
}
