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
public class ConfigAuthorizeNet {

    private ConfigAuthorizeNetData testData;

    private ConfigAuthorizeNetData realData;

    public String getUrl() {
        return getConfigAuthorizeNetData().getUrl();
    }

    public String getLogin() {
        return getConfigAuthorizeNetData().getLogin();
    }

    public String getTransactionKey() {
        return getConfigAuthorizeNetData().getTransactionKey();
    }

    public ConfigAuthorizeNetData getConfigAuthorizeNetData() {
        return ServiceLocator.getConfigStorage().get().getBillingInfoProperties().isUseTestPaymentSystem() ? testData : realData;
    }

    public ConfigAuthorizeNetData getTestData() {
        return testData;
    }

    public void setTestData(ConfigAuthorizeNetData testData) {
        this.testData = testData;
    }

    public ConfigAuthorizeNetData getRealData() {
        return realData;
    }

    public void setRealData(ConfigAuthorizeNetData realData) {
        this.realData = realData;
    }
}
