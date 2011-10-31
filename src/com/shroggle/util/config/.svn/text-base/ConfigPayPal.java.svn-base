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
 * @author dmitry.solomadin
 */
public class ConfigPayPal {

    private ConfigPayPalData realData;

    private ConfigPayPalData testData;

    public ConfigPayPalData getPayPalData(){
        return ServiceLocator.getConfigStorage().get().getBillingInfoProperties().isUseTestPaymentSystem() ? testData : realData;
    }

    public ConfigPayPalData getRealData() {
        return realData;
    }

    public void setRealData(ConfigPayPalData realData) {
        this.realData = realData;
    }

    public ConfigPayPalData getTestData() {
        return testData;
    }

    public void setTestData(ConfigPayPalData testData) {
        this.testData = testData;
    }
}
