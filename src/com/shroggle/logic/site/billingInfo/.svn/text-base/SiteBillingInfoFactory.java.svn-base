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

import com.shroggle.util.config.BillingInfoProperties;
import com.shroggle.util.NowTime;
import com.shroggle.util.ServiceLocator;

/**
 * @author Igor Kanshin, Balakirev Anatoliy
 */
public class SiteBillingInfoFactory {

    public static SitesBillingInfoChecker createInstance(final BillingInfoProperties properties) {
        final BillingInfoType type = properties.isCheckSitesBillingInfo() ? BillingInfoType.FULL_CHECKER : BillingInfoType.LIGHT_CHECKER;
        return createInstance(properties, type);
    }

    public static SitesBillingInfoChecker createInstance(
            final BillingInfoProperties properties, final BillingInfoType type) {
        final NowTime nowTime = ServiceLocator.getNowTime();
        final BillingInfoPropertiesManager manager = new BillingInfoPropertiesManager(properties);
        switch (type) {
            case FULL_CHECKER:
                return new SitesBillingInfoFullChecker(
                        manager.createDelay(nowTime.get()), manager.createPeriod());
            case LIGHT_CHECKER:
                return new SitesBillingInfoLightChecker(
                        manager.createDelay(nowTime.get()), manager.createPeriod());
            case EMAIL_NOTIFICATIONS:
                return new CreditCardNotificationMailSender(
                        manager.createDelay(nowTime.get()), manager.createPeriod());
            default:
                return new SitesBillingInfoFullChecker(
                        manager.createDelay(nowTime.get()), manager.createPeriod());
        }
    }

}
