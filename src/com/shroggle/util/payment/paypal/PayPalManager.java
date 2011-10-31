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

package com.shroggle.util.payment.paypal;

import com.paypal.sdk.profiles.APIProfile;
import com.paypal.sdk.profiles.ProfileFactory;
import com.paypal.sdk.services.NVPCallerServices;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;

/**
 * @author dmitry.solomadin
 */
final class PayPalManager {

    public PayPalManager(final String paypalApiUserName, final String paypalApiPassword, final String paypalSignature) throws Exception {
        this.APIUserName = paypalApiUserName;
        this.APIPassword = paypalApiPassword;
        this.signature = paypalSignature;

        profile = ProfileFactory.createSignatureAPIProfile();
        profile.setSignature(signature);
        profile.setAPIUsername(APIUserName);
        profile.setAPIPassword(APIPassword);
        profile.setEnvironment(env);

        caller.setAPIProfile(profile);
    }

    public NVPCallerServices getCaller() {
        return caller;
    }

    public APIProfile getProfile() {
        return profile;
    }

    public String getEnv() {
        return env;
    }

    private final Config config = ServiceLocator.getConfigStorage().get();
    private final String APIUserName;
    private final String APIPassword;
    private final String signature;
    private final String env = config.getPayPal().getPayPalData().getEnv();
    private APIProfile profile;
    private NVPCallerServices caller = new NVPCallerServices();

}