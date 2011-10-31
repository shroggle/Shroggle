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
package com.shroggle.presentation.account.dashboard.keywordManager;

import com.shroggle.logic.user.dashboard.keywordManager.SEOTerm;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
public class UpdateKeyphraseResponse {

    @RemoteProperty
    private SEOTerm seoTerm;

    @RemoteProperty
    private boolean present;

    @RemoteProperty
    private String density;

    public SEOTerm getSeoTerm() {
        return seoTerm;
    }

    public void setSeoTerm(SEOTerm seoTerm) {
        this.seoTerm = seoTerm;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }
}
