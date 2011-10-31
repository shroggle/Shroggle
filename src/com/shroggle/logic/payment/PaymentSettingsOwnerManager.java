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
package com.shroggle.logic.payment;

import com.shroggle.entity.*;
import com.shroggle.exception.SitePaymentSettingsNotFoundException;
import com.shroggle.logic.childSites.childSiteSettings.ChildSiteSettingsManager;
import com.shroggle.logic.site.billingInfo.ChargeTypeManager;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.BillingInfoProperties;
import com.shroggle.util.config.Config;
import com.shroggle.util.payment.PaymentSystem;
import com.shroggle.util.payment.paypal.PayPal;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public class PaymentSettingsOwnerManager {

    public PaymentSettingsOwnerManager(PaymentSettingsOwner owner) {
        if (owner == null) {
            throw new IllegalArgumentException("Can`t create PaymentSettingsOwnerManager without PaymentSettingsOwner!");
        }
        this.owner = owner;
        this.sitePaymentSettingsManager = new SitePaymentSettingsManager(owner.getSitePaymentSettings());
    }

    public PaymentSystem getAppropriatePaymentSystem(final PaymentMethod paymentMethod) {
        final ChildSiteSettings childSiteSettings = getChildSiteSettings();
        final ChildSiteRegistration registration = childSiteSettings != null ? childSiteSettings.getChildSiteRegistration() : null;
        switch (paymentMethod) {
            case PAYPAL: {
                if (registration != null && registration.isUseOwnPaypal()) {
                    return PaymentSystem.newInstance(
                            new PaymentSystem.CreationProperties(registration.getPaypalApiUserName(),
                                    registration.getPaypalApiPassword(), registration.getPaypalSignature())
                    );
                } else {
                    return PaymentSystem.newInstance(PaymentMethod.PAYPAL);
                }
            }
            case AUTHORIZE_NET: {
                if (registration != null && registration.isUseOwnAuthorize()) {
                    return PaymentSystem.newInstance(
                            new PaymentSystem.CreationProperties(registration.getAuthorizeLogin(), registration.getAuthorizeTransactionKey())
                    );
                } else {
                    return PaymentSystem.newInstance(PaymentMethod.AUTHORIZE_NET);
                }
            }
            default: {
                throw new IllegalArgumentException("Unknown paymentMethod = " + paymentMethod);
            }
        }
    }

    public PaymentSystem getAppropriatePaymentSystem() {
        return getAppropriatePaymentSystem(owner.getSitePaymentSettings().getPaymentMethod());
    }

    public String getInfoForPaymentLog() {
        if (owner instanceof ChildSiteSettings) {
            final ChildSiteSettings settings = (ChildSiteSettings) owner;
            final String info;
            if (settings.getSite() != null) {
                info = getSiteInfo(settings.getSite());
            } else {
                info = "child site settings (childSiteSettingsId = " + settings.getChildSiteSettingsId() + ", site has not been created yet.)";
            }
            return info;
        } else if (owner instanceof Site) {
            return getSiteInfo((Site) owner);
        } else {
            throw new IllegalArgumentException("Unknown type of PaymentSettingsOwner!");
        }
    }

    public ChildSiteSettings getChildSiteSettings() {
        if (isChildSiteSettings()) {
            return (ChildSiteSettings) owner;
        } else if (isSite() && ((Site) owner).getChildSiteSettings() != null) {
            return ((Site) owner).getChildSiteSettings();
        } else {
            return null;
        }
    }

    public double getPriceByChargeType(final ChargeType chargeType) {
        if (owner instanceof ChildSiteSettings) {
            return new ChildSiteSettingsManager(((ChildSiteSettings) owner)).getPaymentSumByChargeType(chargeType);
        } else if (owner instanceof Site && ((Site) owner).getChildSiteSettings() != null) {
            return new ChildSiteSettingsManager(((Site) owner).getChildSiteSettings()).getPaymentSumByChargeType(chargeType);
        } else {
            return new ChargeTypeManager(chargeType).getPrice();
        }
    }

    protected PaymentReason getPaymentReason() {
        if (getChildSiteSettings() != null) {
            return PaymentReason.CHILD_SITE_CREATION;
        } else {
            return PaymentReason.SHROGGLE_MONTHLY_PAYMENT;
        }
    }

    public boolean isSite() {
        return owner instanceof Site;
    }

    public boolean isChildSiteSettings() {
        return owner instanceof ChildSiteSettings;
    }

    public void suspendActivity() throws Exception {
        sitePaymentSettingsManager.suspendActivity();
    }

    public void reactivate() throws Exception {
        sitePaymentSettingsManager.reactivate();
    }

    public void deactivate() throws Exception {
        sitePaymentSettingsManager.deactivate();
    }

    public boolean hasToBeDeactivated() {
        return sitePaymentSettingsManager.hasToBeDeactivated();
    }

    public int getDaysBeforeDeactivation() {
        return sitePaymentSettingsManager.getDaysBeforeDeactivation();
    }

    /*------------------------------------------------Private methods-------------------------------------------------*/

    private String getSiteInfo(final Site site) {
        return "child site (" + site.getTitle() + ", siteId = " + site.getSiteId() + ")";
    }

    private final PaymentSettingsOwner owner;
    private final SitePaymentSettingsManager sitePaymentSettingsManager;

    private class SitePaymentSettingsManager {

        public SitePaymentSettingsManager(SitePaymentSettings paymentSettings) {
            if (paymentSettings == null) {
                throw new SitePaymentSettingsNotFoundException("Can`t create SitePaymentSettingsManager without SitePaymentSettings!");
            }
            this.sitePaymentSettings = paymentSettings;
        }


        public void suspendActivity() throws Exception {
            if (sitePaymentSettings.getSiteStatus() != SiteStatus.ACTIVE) {
                throw new IllegalStateException("This method should be used only for ACTIVE SitePaymentSettings!"
                        + "SitePaymentSettings status = " + sitePaymentSettings.getSiteStatus() + ", id = " + sitePaymentSettings.getSitePaymentSettingsId());
            }
            if (sitePaymentSettings.getExpirationDate() == null) {
                throw new IllegalStateException("SitePaymentSettings is ACTIVE but its expirationDate is null! " +
                        " Please, check EnforcePayment class or data in DB. Something is wrong there!");
            }
            if (sitePaymentSettings.getPaymentMethod() == PaymentMethod.PAYPAL) {
                ((PayPal) getAppropriatePaymentSystem()).suspendActiveRecurringProfile(sitePaymentSettings.getRecurringPaymentId());
            } else if (sitePaymentSettings.getRecurringPaymentId() != null) {
                logger.log(Level.SEVERE, "SitePaymentSettings with PaymentMethod = AUTHORIZE_NET has pypal recurringProfileId = "
                        + sitePaymentSettings.getRecurringPaymentId() + ". Check your code!\n" +
                        " Trying to suspend this unused paypal profile: ");
                try {
                    // It`s not common situation, so I`m trying to get own payPal system or our one. Tolik
                    final PayPal payPal = (PayPal) new PaymentSettingsOwnerManager(owner).getAppropriatePaymentSystem(PaymentMethod.PAYPAL);
                    payPal.suspendActiveRecurringProfile(sitePaymentSettings.getRecurringPaymentId());
                    logger.log(Level.SEVERE, "Pypal recurringProfileId = "
                            + sitePaymentSettings.getRecurringPaymentId() + " successfuly suspended.");
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Unable to suspend pypal recurringProfileId = "
                            + sitePaymentSettings.getRecurringPaymentId() + ". Please, look at it.");
                }
            }
            final SynchronizeRequest synchronizeRequest = new SynchronizeRequestEntity((SitePaymentSettings.class), SynchronizeMethod.WRITE, sitePaymentSettings.getSitePaymentSettingsId());
            synchronize.execute(synchronizeRequest, new SynchronizeContext<Void>() {
                public Void execute() {
                    persistanceTransaction.execute(new Runnable() {
                        public void run() {
                            long remainingTimeOfUsage = sitePaymentSettings.getExpirationDate().getTime() - System.currentTimeMillis();
                            if (remainingTimeOfUsage <= 0) {
                                logger.log(Level.SEVERE, "Active SitePaymentSettings with expired " +
                                        "expirationDate = " + DateUtil.toCommonDateStr(sitePaymentSettings.getExpirationDate()) + "! " +
                                        "Please, check payment methods. Probably its EnforcePayment class.");
                            }
                            sitePaymentSettings.setSiteStatus(SiteStatus.SUSPENDED);
                            sitePaymentSettings.setRemainingTimeOfUsage(remainingTimeOfUsage);
                        }
                    });
                    return null;
                }
            });
        }

        public void reactivate() throws Exception {
            if (sitePaymentSettings.getSiteStatus() != SiteStatus.SUSPENDED) {
                throw new IllegalStateException("This method should be used only for SUSPENDED SitePaymentSettings!"
                        + "SitePaymentSettings status = " + sitePaymentSettings.getSiteStatus() + ", id = " + sitePaymentSettings.getSitePaymentSettingsId());
            }
            final ChargeTypeManager chargeTypeManager = new ChargeTypeManager(sitePaymentSettings.getChargeType());
            final Date newExpirationDate = chargeTypeManager.createNewExpirationDateForSuspendedOwner(sitePaymentSettings.getRemainingTimeOfUsage());
            final PaymentSystem paymentSystem = getAppropriatePaymentSystem();
            paymentSystem.activateSuspendedRecurringProfile(sitePaymentSettings.getRecurringPaymentId());

            final SynchronizeRequest synchronizeRequest = new SynchronizeRequestEntity((SitePaymentSettings.class), SynchronizeMethod.WRITE, sitePaymentSettings.getSitePaymentSettingsId());
            synchronize.execute(synchronizeRequest, new SynchronizeContext<Void>() {
                public Void execute() {
                    persistanceTransaction.execute(new Runnable() {
                        public void run() {
                            if (sitePaymentSettings.getRemainingTimeOfUsage() <= 0) {
                                logger.log(Level.SEVERE, "Suspended SitePaymentSettings with negative " +
                                        "remainingTimeOfUsage. It will be reactivated now but its new expirationDate will be = " +
                                        DateUtil.toCommonDateStr(newExpirationDate) + "! And it will be deactivated by " +
                                        "EnforcePayment class at midnight.");
                            }
                            sitePaymentSettings.setSiteStatus(SiteStatus.ACTIVE);
                            sitePaymentSettings.setExpirationDate(newExpirationDate);
                            sitePaymentSettings.setRemainingTimeOfUsage(null);
                        }
                    });
                    return null;
                }
            });
        }

        public void deactivate() throws Exception {
            if (sitePaymentSettings.getPaymentMethod() == PaymentMethod.PAYPAL) {
                ((PayPal) getAppropriatePaymentSystem()).cancelRecurringProfile(sitePaymentSettings.getRecurringPaymentId());
            } else if (sitePaymentSettings.getRecurringPaymentId() != null) {
                logger.log(Level.SEVERE, "SitePaymentSettings with PaymentMethod = AUTHORIZE_NET has pypal recurringProfileId = "
                        + sitePaymentSettings.getRecurringPaymentId() + " Check your code!\n" +
                        " Trying to cancel this unused paypal profile: ");
                try {
                    // It`s not common situation, so I`m trying to get own payPal system or our one. Tolik
                    final PayPal payPal = (PayPal) new PaymentSettingsOwnerManager(owner).getAppropriatePaymentSystem(PaymentMethod.PAYPAL);
                    payPal.cancelRecurringProfile(sitePaymentSettings.getRecurringPaymentId());
                    logger.log(Level.SEVERE, "Pypal recurringProfileId = "
                            + sitePaymentSettings.getRecurringPaymentId() + " successfuly canceled.");
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Unable to cancel pypal recurringProfileId = "
                            + sitePaymentSettings.getRecurringPaymentId() + ". Please, look at it.");
                }
            }
            final SynchronizeRequest synchronizeRequest = new SynchronizeRequestEntity(SitePaymentSettings.class,
                    SynchronizeMethod.WRITE, sitePaymentSettings.getSitePaymentSettingsId());
            ServiceLocator.getSynchronize().execute(synchronizeRequest, new SynchronizeContext<Void>() {
                public Void execute() {
                    ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
                        public void run() {
                            sitePaymentSettings.setSiteStatus(SiteStatus.PENDING);
                            sitePaymentSettings.setCreditCard(null);
                            sitePaymentSettings.setRecurringPaymentId(null);
                            sitePaymentSettings.setRemainingTimeOfUsage(null);
                        }
                    });
                    return null;
                }
            });
        }

        public boolean hasToBeDeactivated() {
            final Config config = ServiceLocator.getConfigStorage().get();
            final BillingInfoProperties properties = config.getBillingInfoProperties();

            final Calendar expirationDate = new GregorianCalendar();
            expirationDate.setTime(sitePaymentSettings.getExpirationDate());
            expirationDate.set(Calendar.MINUTE, expirationDate.get(Calendar.MINUTE) + properties.getDeactivateSiteAfter());

            final Calendar currentDateCalendar = new GregorianCalendar();
            currentDateCalendar.setTime(new Date());

            return currentDateCalendar.after(expirationDate);
        }

        public int getDaysBeforeDeactivation() {
            final Calendar expirationDate = new GregorianCalendar();
            expirationDate.setTime(DateUtil.roundDateTo(sitePaymentSettings.getExpirationDate(), Calendar.DAY_OF_MONTH));

            final BillingInfoProperties properties = ServiceLocator.getConfigStorage().get().getBillingInfoProperties();
            expirationDate.set(Calendar.MINUTE, expirationDate.get(Calendar.MINUTE) + properties.getDeactivateSiteAfter());

            final Calendar currentDate = new GregorianCalendar();
            currentDate.setTime(DateUtil.roundDateTo(new Date(), Calendar.DAY_OF_MONTH));
            if (currentDate.before(expirationDate)) {
                final long difference = (expirationDate.getTimeInMillis() - currentDate.getTimeInMillis());
                return DateUtil.toDays(difference);
            }
            return 0;
        }


        private final SitePaymentSettings sitePaymentSettings;
        private final Logger logger = Logger.getLogger(this.getClass().getName());
        private final Synchronize synchronize = ServiceLocator.getSynchronize();
        private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    }

}
