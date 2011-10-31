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
package com.shroggle.logic.javien;

import com.shroggle.util.config.ConfigJavien;
import com.shroggle.util.config.Config;
import com.shroggle.util.config.ConfigJavienData;
import com.shroggle.util.ServiceLocator;

/**
 * @author Balakirev Anatoliy
 */
public class JavienManager {

    public static String getJavienUrl() {
        final Config config = ServiceLocator.getConfigStorage().get();
        final ConfigJavien configJavien = config.getJavien();
        ConfigJavienData configJavienData = configJavien.getConfigJavienData();
        return configJavienData.getUrl();
    }

    public static String getMerchantName() {
        final Config config = ServiceLocator.getConfigStorage().get();
        final ConfigJavien configJavien = config.getJavien();
        ConfigJavienData configJavienData = configJavien.getConfigJavienData();
        return configJavienData.getMerchantName();
    }

    public static String getAdminLogin() {
        final Config config = ServiceLocator.getConfigStorage().get();
        final ConfigJavien configJavien = config.getJavien();
        ConfigJavienData configJavienData = configJavien.getConfigJavienData();
        return configJavienData.getAdminLogin();
    }

    public static String getAdminPassword() {
        final Config config = ServiceLocator.getConfigStorage().get();
        final ConfigJavien configJavien = config.getJavien();
        ConfigJavienData configJavienData = configJavien.getConfigJavienData();
        return configJavienData.getAdminPassword();
    }
}
