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
import com.shroggle.exception.UnknownPaymentPeriodException;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.TimeInterval;
import com.shroggle.util.config.BillingInfoProperties;

import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public class ChargeTypeManager {

    public ChargeTypeManager(ChargeType chargeType) {
        this.chargeType = chargeType;
    }

    public double getPrice() {
        final Map<ChargeType, Double> paymentPrices = ServiceLocator.getConfigStorage().get().getPaymentPrices();
        return paymentPrices.get(chargeType);
    }

    public Date createNewExpirationDateForActiveOwner(Date oldExpirationDate) {
        if (oldExpirationDate == null) {
            logger.log(Level.SEVERE, "Something is wrong here! We can`t have active SitePaymentSettings without" +
                    " expiration date. Now I`ll just use currentDate instead of old expirationDate from " +
                    "SitePaymentSettings, but please, check your code.");
            oldExpirationDate = new Date();
        }
        return new Date(oldExpirationDate.getTime() + getPaymentIntervalMillis());
    }

    public Date createNewExpirationDateForSuspendedOwner(final Long remainingTimeOfUsage) {
        if (remainingTimeOfUsage == null) {
            throw new IllegalStateException("Something is wrong here! Pending paymentSettingsOwner must have it`s " +
                    "remainingTimeOfUsage. Check code, which suspend paymentSettingsOwner activity.");
        }
        return new Date(System.currentTimeMillis() + remainingTimeOfUsage);
    }

    public Date createNewExpirationDateForPendingOwner(final ChildSiteSettings childSiteSettings) {
        if (chargeType == ChargeType.SITE_ONE_TIME_FEE) {
            if (childSiteSettings.getEndDate() != null) {
                return new Date(childSiteSettings.getEndDate().getTime() + TimeInterval.ONE_DAY.getMillis());
            } else {
                return new Date(System.currentTimeMillis() + TimeInterval.ONE_HUNDRED_YEARS.getMillis());
            }
        } else {
            return new Date(System.currentTimeMillis() + getPaymentIntervalMillis());
        }
    }

    /**
     * @return pyament interval in milliseconds from config. Depending on paymentPeriod.
     *         Note: this method should be used only for new expirationDate creation
     *         ("createNewExpirationDateForActiveOwner", "createNewExpirationDateForSuspendedOwner" or
     *         "createNewExpirationDateForPendingOwner" from ChargeTypeManager).
     *         Please, don`t use it from another places.
     */
    protected long getPaymentIntervalMillis() {
        if (chargeType == ChargeType.SITE_ONE_TIME_FEE) {
            throw new IllegalStateException("You can`t use this method for one time fee. We need endDate and other " +
                    "settings from paymentSettingsOwner (site or childSiteRegistration). Somthing is wrong in your code.");
        }
        final BillingInfoProperties properties = ServiceLocator.getConfigStorage().get().getBillingInfoProperties();
        switch (chargeType.getPaymentPeriod()) {
            case YEAR: {
                return DateUtil.minutesToMilliseconds(properties.getAnnualBillingExpirationDate());
            }
            case MONTH: {
                return DateUtil.minutesToMilliseconds(properties.getMonthlyBillingExpirationDate());
            }
            default: {
                throw new UnknownPaymentPeriodException("Unknown payment period: " + chargeType.getPaymentPeriod());
            }
        }
    }

    private final ChargeType chargeType;
    private final Logger logger = Logger.getLogger(this.getClass().getName());
}
