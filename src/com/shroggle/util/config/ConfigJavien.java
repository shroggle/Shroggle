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

import com.shroggle.util.ServiceLocator;

/**
 * @author Balakirev Anatoliy
 */
public class ConfigJavien {

    private ConfigJavienData testData;

    private ConfigJavienData realData;

    public ConfigJavienData getConfigJavienData() {
        return ServiceLocator.getConfigStorage().get().getBillingInfoProperties().isUseTestPaymentSystem() ? testData : realData;
    }

    public ConfigJavienData getTestData() {
        return testData;
    }

    public void setTestData(ConfigJavienData testData) {
        this.testData = testData;
    }

    public ConfigJavienData getRealData() {
        return realData;
    }

    public void setRealData(ConfigJavienData realData) {
        this.realData = realData;
    }
}