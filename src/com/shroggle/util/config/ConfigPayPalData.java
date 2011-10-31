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

/**
 * @author dmitry.solomadin
 */
public class ConfigPayPalData {

    private String APIUserName;

    private String APIPassword;

    private String signature;

    private String env;

    public String getAPIUserName() {
        return APIUserName;
    }

    public void setAPIUserName(String APIUserName) {
        this.APIUserName = APIUserName;
    }

    public String getAPIPassword() {
        return APIPassword;
    }

    public void setAPIPassword(String APIPassword) {
        this.APIPassword = APIPassword;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }
}
