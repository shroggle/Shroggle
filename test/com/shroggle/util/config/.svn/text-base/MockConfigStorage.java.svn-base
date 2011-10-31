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

package com.shroggle.util.config;

import com.shroggle.entity.ChargeType;

/**
 * @author Stasuk Artem
 */
public class MockConfigStorage implements ConfigStorage {

    public MockConfigStorage() {
        config.getPaymentPrices().put(ChargeType.SITE_250MB_MONTHLY_FEE, 29.99);
        config.getPaymentPrices().put(ChargeType.SITE_500MB_MONTHLY_FEE, 29.99);
        config.getPaymentPrices().put(ChargeType.SITE_1GB_MONTHLY_FEE, 29.99);
        config.getPaymentPrices().put(ChargeType.SITE_3GB_MONTHLY_FEE, 29.99);

        config.getPaymentPrices().put(ChargeType.SITE_ANNUAL_FEE, 300.0);
        config.getPaymentPrices().put(ChargeType.SITE_MONTHLY_FEE, 29.99);
        config.getPaymentPrices().put(ChargeType.SITE_ONE_TIME_FEE, 0.0);

        config.setApplicationUrl("testApplicationUrl");
        config.setUserSitesUrl("shroggle.com");

        config.getBillingInfoProperties().setAnnualBillingExpirationDate(525600);
        config.getBillingInfoProperties().setMonthlyBillingExpirationDate(43200);
    }

    public Config get() {
        return config;
    }

    public void clear() {
        config = new Config();
    }

    private Config config = new Config();

}
