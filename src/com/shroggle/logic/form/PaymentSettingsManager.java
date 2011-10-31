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
package com.shroggle.logic.form;

import com.shroggle.util.international.International;
import com.shroggle.util.ServiceLocator;

import java.util.Locale;

/**
 * @author Balakirev Anatoliy
 */
public class PaymentSettingsManager {

    public PaymentSettingsManager(PaymentSettings paymentSettings) {
        this.paymentSettings = paymentSettings;
    }

    public String getAgreement() {
        final International international = ServiceLocator.getInternationStorage().get("paymentAgreements", Locale.US);
        if (paymentSettings != null && !paymentSettings.isUseOneTimeFee() && paymentSettings.getEndDate() != null) {
            return international.get("monthlyFeeWithEndDate", paymentSettings.getChildSiteRegistrationName(), "<br>");
        } else if (paymentSettings != null && paymentSettings.isUseOneTimeFee() && paymentSettings.getEndDate() != null) {
            return international.get("oneTimeFeeWithEndDate", paymentSettings.getChildSiteRegistrationName(), "<br>");
        } else if (paymentSettings != null && paymentSettings.isUseOneTimeFee() && paymentSettings.getEndDate() == null) {
            return international.get("oneTimeFeeWithNoEndDate", "<br>");
        } else {
            return international.get("monthlyFeeWithoutEndDate", "<br>");
        }
    }

    private final PaymentSettings paymentSettings;
}
