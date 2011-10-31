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
package com.shroggle.presentation.site.payment;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

/**
 * Author: dmitry.solomadin
 */
@DataTransferObject
public class GoToPaypalResponse {

    public GoToPaypalResponse() {
        javienActivated = false;
        activeProfile = false;
        activatedWithOneTimeFee = false;
    }

    @RemoteProperty
    private String paypalLink;

    @RemoteProperty
    private boolean javienActivated;

    @RemoteProperty
    private boolean activeProfile;

    @RemoteProperty
    private boolean activatedWithOneTimeFee;

    public boolean isActivatedWithOneTimeFee() {
        return activatedWithOneTimeFee;
    }

    public void setActivatedWithOneTimeFee(boolean activatedWithOneTimeFee) {
        this.activatedWithOneTimeFee = activatedWithOneTimeFee;
    }

    public boolean isJavienActivated() {
        return javienActivated;
    }

    public void setJavienActivated(boolean javienActivated) {
        this.javienActivated = javienActivated;
    }

    public boolean isActiveProfile() {
        return activeProfile;
    }

    public void setActiveProfile(boolean activeProfile) {
        this.activeProfile = activeProfile;
    }

    public String getPaypalLink() {
        return paypalLink;
    }

    public void setPaypalLink(String paypalLink) {
        this.paypalLink = paypalLink;
    }
}
