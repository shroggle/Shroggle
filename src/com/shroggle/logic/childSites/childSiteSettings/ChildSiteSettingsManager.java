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
package com.shroggle.logic.childSites.childSiteSettings;

import com.shroggle.entity.*;
import com.shroggle.exception.ChildSiteRegistrationNotFoundException;
import com.shroggle.exception.ChildSiteSettingsNotFoundException;
import com.shroggle.exception.PaymentException;
import com.shroggle.logic.childSites.ExpirationDateLogic;
import com.shroggle.logic.childSites.childSiteRegistration.ChildSiteRegistrationManager;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.site.PublishingInfoResponse;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.config.Config;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeContext;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.SynchronizeRequest;
import com.shroggle.util.process.synchronize.SynchronizeRequestEntity;
import com.shroggle.util.resource.provider.ResourceGetterType;
import com.shroggle.util.url.UrlValidator;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 *         Date: 13.07.2009
 *         Time: 20:55:28
 */
public class ChildSiteSettingsManager {

    public ChildSiteSettingsManager(Integer settingsId) {
        this(ServiceLocator.getPersistance().getChildSiteSettingsById(settingsId));
    }

    public ChildSiteSettingsManager(ChildSiteSettings settings) {
        if (settings == null) {
            throw new ChildSiteSettingsNotFoundException("Cant create ChildSiteSettingsManager by null ChildSiteSettings");
        }
        this.settings = settings;
    }

    public ChildSiteRegistrationManager getChildSiteRegistration() {
        return new ChildSiteRegistrationManager(settings.getChildSiteRegistration());
    }

    public Site getChildSite() {
        return settings.getSite();
    }

    public Site getParentSite() {
        return settings.getParentSite();
    }

    public boolean isChildSiteHasBeenCreated() {
        return settings.getSite() != null;
    }

    public ChildSiteSettings getChildSiteSettings() {
        return settings;
    }

    public void remove() {
        ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
            public void run() {
                if (settings.getSite() != null) {
                    settings.getSite().setChildSiteSettings(null);
                }
                persistance.removeChildSiteSettings(settings);
            }
        });
    }

    public String getHisNetworkContactUsUrl() {
        final Page page = persistance.getPage(settings.getChildSiteRegistration().getContactUsPageId());
        if (page == null) {
            return "#";
        } else {
            final String workUrl = new PageManager(page, SiteShowOption.getWorkOption()).getPublicUrl();
            return StringUtil.isNullOrEmpty(workUrl) ? "#" : workUrl;
        }
    }

    public String getParentSiteUrl() {
        return new SiteManager(getParentSite()).getPublicUrl();
    }

    public String getHisNetworkLogoUrl() {
        String logoUrl = "";
        if (settings.getLogoId() != null && settings.getLogoId() > 0) {
            logoUrl = ServiceLocator.getResourceGetter().get(ResourceGetterType.LOGO, settings.getLogoId(), 0, 0, 0, false);
        }
        return logoUrl;
    }

    public boolean isNetworkMembershipExpired(final Date currentDate) {
        return ExpirationDateLogic.isNetworkMembershipExpired(currentDate, settings.getEndDate());
    }

    public boolean isNetworkMembershipDueToExpire(final Date currentDate) {
        if (settings.getEndDate() != null) {
            Config config = ServiceLocator.getConfigStorage().get();
            final int membersipExpireNotifyTime = config.getBillingInfoProperties().getMembersipExpireNotifyTime();

            Calendar currentTimePlusTenDays = new GregorianCalendar();
            currentTimePlusTenDays.setTime(currentDate);
            currentTimePlusTenDays.set(Calendar.MINUTE, currentTimePlusTenDays.get(Calendar.MINUTE) + membersipExpireNotifyTime);

            Calendar expirationTime = new GregorianCalendar();
            expirationTime.setTime(settings.getEndDate());

            // For example consider if today is March 10th and expirationDate = March 20th or March 19th:
            // we`ll have March 20th in "currentTimePlusTenDays" so it will be equal to expirationDate or after it
            // and we`ll return true.
            // It means that we have to send email in ten (or less but not more) days before expiration date. 
            return currentTimePlusTenDays.getTime().equals(expirationTime.getTime()) ||
                    currentTimePlusTenDays.getTime().after(expirationTime.getTime());
        } else {
            return false;
        }
    }

    public boolean isNotificationMessageSent() {
        return settings.getNotificationMailSent() != null;
    }

    public Date getNotificationMessageSentDate() {
        return settings.getNotificationMailSent();
    }

    public void setNotificationMessageSent() {
        if (settings != null) {
            try {
                final SynchronizeRequest synchronizeRequest = new SynchronizeRequestEntity(
                        ChildSiteSettings.class, SynchronizeMethod.WRITE, settings.getChildSiteSettingsId());
                ServiceLocator.getSynchronize().execute(synchronizeRequest, new SynchronizeContext<Void>() {
                    public Void execute() {
                        persistanceTransaction.execute(new Runnable() {
                            public void run() {
                                settings.setNotificationMailSent(new Date());
                            }
                        });
                        return null;
                    }
                });
            } catch (Exception exception) {
                logger.log(Level.SEVERE, "Can't update notificationMailSent field! ChildSiteSettings id = " +
                        settings.getChildSiteSettingsId(), exception);
            }
        }
    }


    public void setCanBePublishedMessageSent() {
        if (settings != null) {
            try {
                final SynchronizeRequest synchronizeRequest = new SynchronizeRequestEntity(
                        ChildSiteSettings.class, SynchronizeMethod.WRITE, settings.getChildSiteSettingsId());
                ServiceLocator.getSynchronize().execute(synchronizeRequest, new SynchronizeContext<Void>() {
                    public Void execute() {
                        persistanceTransaction.execute(new Runnable() {
                            public void run() {
                                settings.setCanBePublishedMessageSent(true);
                            }
                        });
                        return null;
                    }
                });
            } catch (Exception exception) {
                logger.log(Level.SEVERE, "Can't update canBePublishedMessageSent field! ChildSiteSettings id = " +
                        settings.getChildSiteSettingsId(), exception);
            }
        }
    }

    public PublishingInfoResponse getPublishingInfo() {
        PublishingInfoResponse response = new PublishingInfoResponse();
        response.setCanBePublishedMessageSent(settings.isCanBePublishedMessageSent());
        if (settings.getStartDate() != null) {
            response.setCreatedBeforeStartDate(settings.getCreatedDate().before(settings.getStartDate()));
            final Date currentDate = new Date();
            final Date startDate = settings.getStartDate();
            if (startDate.after(currentDate)) {
                response.setCanBePublished(false);
                response.setStartDateString(DateUtil.toMonthDayAndYear(startDate));
            }
        }
        return response;
    }

    public boolean childSiteShouldBeCreated() {
        // According to http://wiki.web-deva.com/display/SRS2/Child+Site+Registration+Visual+Item, Use Case R:
        // child site should be created even if it was not payed, but in this case it should not be published. Tolik
        return settings.getChildSiteRegistration() != null &&
                settings.isRequiredToUseSiteBlueprint() &&
                new ChildSiteRegistrationManager(settings.getChildSiteRegistration()).getDefaultBlueprint() != null;
    }

    public String getSiteTitle() {
        String siteTitle = "";
        FilledForm filledForm = persistance.getFilledFormById(settings.getFilledFormId());
        if (filledForm != null) {
            FilledFormItem item = FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.YOUR_PAGE_SITE_NAME);
            if (item != null && item.getValues().size() > 0) {
                siteTitle = item.getValues().get(0);
                siteTitle = StringUtil.trimCutIfNeed(siteTitle, 250);
                siteTitle = StringUtil.getEmptyOrString(siteTitle);
            }
        }
        return siteTitle;
    }

    public String getRegistrantName() {
        final FilledForm filledForm = persistance.getFilledFormById(settings.getFilledFormId());
        if (filledForm != null) {
            final FilledFormItem firstNameItem = FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.FIRST_NAME);
            final FilledFormItem lastNameItem = FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.LAST_NAME);
            if (firstNameItem != null || lastNameItem != null) {
                final String firstName = StringUtil.getEmptyOrString(firstNameItem != null ? firstNameItem.getValue() : "");
                final String lastName = StringUtil.getEmptyOrString(lastNameItem != null ? lastNameItem.getValue() : "");
                if (!lastName.isEmpty() && !firstName.isEmpty()) {
                    return lastName + " " + firstName;
                } else {
                    return lastName + firstName;
                }
            } else {
                final FilledFormItem siteNameItem = FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.YOUR_PAGE_SITE_NAME);
                return siteNameItem != null ? StringUtil.getEmptyOrString(siteNameItem.getValue()) : "";
            }
        }
        return "";
    }


    public String getUniqueDomainName() {
        String defaultDomainName = "site";
        String domainName = null;
        FilledForm filledForm = persistance.getFilledFormById(settings.getFilledFormId());
        if (filledForm != null) {
            domainName = FilledFormManager.getDomainName(filledForm.getFilledFormItems());
            if (domainName == null) {
                final String siteName = FilledFormManager.getPageSiteName(filledForm.getFilledFormItems());
                if (siteName != null) {
                    domainName = siteName.split(" ")[0];
                }
            }
            domainName = StringUtil.trimCutIfNeedAndLower(domainName, 250);
        }
        domainName = (domainName != null && UrlValidator.isSystemSubDomainValid(domainName)) ? domainName : defaultDomainName;
        if (persistance.getSiteBySubDomain(domainName) == null) {
            return domainName;
        } else {
            return createUniqueDomainName(domainName, "1");
        }
    }

    public double getPaymentSumByChargeType(final ChargeType chargeType) {
        try {
            switch (chargeType) {
                case SITE_ONE_TIME_FEE: {
                    return settings.getOneTimeFee();
                }
                case SITE_250MB_MONTHLY_FEE: {
                    return settings.getPrice250mb();
                }
                case SITE_500MB_MONTHLY_FEE: {
                    return settings.getPrice500mb();
                }
                case SITE_1GB_MONTHLY_FEE: {
                    return settings.getPrice1gb();
                }
                case SITE_3GB_MONTHLY_FEE: {
                    return settings.getPrice3gb();
                }
                default: {
                    throw new UnsupportedOperationException();
                }
            }
        } catch (Exception e) {
            throw new PaymentException("Cant create payment sum!");
        }
    }

    public String getNetworkName() {
        try {
            return getChildSiteRegistration().getName();
        } catch (ChildSiteRegistrationNotFoundException e) {
            final String appName = ServiceLocator.getConfigStorage().get().getApplicationName();
            logger.warning("ChildSiteSettings without childSiteRegistration! '" + appName + "' will be returned instead of " +
                    "network name, but you should check this childSiteSettings (its id = " + settings.getChildSiteSettingsId() + ")");
            return appName;
        }
    }

    public int getId() {
        return settings.getId();
    }

    private String createUniqueDomainName(String domainName, String index) {
        if (domainName.length() + index.length() > 250) {
            domainName = StringUtil.trimCutIfNeedAndLower(domainName, (250 - index.length()));
        }
        if (persistance.getSiteBySubDomain(domainName + index) == null) {
            return domainName + index;
        } else {
            return createUniqueDomainName(domainName, String.valueOf(Integer.valueOf(index) + 1));
        }
    }

    public boolean equals(final Object o) {
        final ChildSiteSettings childSiteSettings;
        if (o instanceof ChildSiteSettings) {
            childSiteSettings = (ChildSiteSettings) o;
        } else if (o instanceof ChildSiteSettingsManager) {
            childSiteSettings = ((ChildSiteSettingsManager) o).getChildSiteSettings();
        } else {
            childSiteSettings = null;
        }

        return childSiteSettings != null && childSiteSettings.getId() == this.getChildSiteSettings().getId();
    }

    @Override
    public int hashCode() {
        return getChildSiteSettings().getId();
    }

    private final ChildSiteSettings settings;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Logger logger = Logger.getLogger(this.getClass().getName());
}
