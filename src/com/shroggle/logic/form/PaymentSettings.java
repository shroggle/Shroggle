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

import com.shroggle.entity.ChildSiteSettings;
import com.shroggle.exception.ChildSiteSettingsNotFoundException;
import com.shroggle.exception.FormDataNotFoundException;

import java.util.Date;

/**
 * @author Balakirev Anatoliy
 */
public class PaymentSettings {

    public PaymentSettings(final FormData formData) {
        if (formData == null) {
            throw new FormDataNotFoundException("Can`t create payment setting by null ChildSiteRegistration!");
        }
        oneTimeFee = formData.getOneTimeFee();
        useOneTimeFee = formData.isUseOneTimeFee();
        price250mb = formData.getPrice250mb();
        startDate = formData.getStartDate();
        endDate = formData.getEndDate();
        formId = formData.getFormId();
        childSiteRegistrationName = formData.getFormName();
    }

    public PaymentSettings(final ChildSiteSettings childSiteSettings) {
        if (childSiteSettings == null) {
            throw new ChildSiteSettingsNotFoundException("Can`t create payment setting by null ChildSiteRegistration!");
        }
        oneTimeFee = childSiteSettings.getOneTimeFee();
        useOneTimeFee = childSiteSettings.isUseOneTimeFee();
        price250mb = childSiteSettings.getPrice250mb();
        startDate = childSiteSettings.getStartDate();
        endDate = childSiteSettings.getEndDate();
        formId = childSiteSettings.getChildSiteRegistration().getFormId();
        childSiteRegistrationName = childSiteSettings.getChildSiteRegistration().getName();
    }

    private final double oneTimeFee;

    private final boolean useOneTimeFee;

    private final double price250mb;

    private final Date startDate;

    private final Date endDate;

    private final int formId;

    private final String childSiteRegistrationName;

    public double getOneTimeFee() {
        return oneTimeFee;
    }

    public boolean isUseOneTimeFee() {
        return useOneTimeFee;
    }

    public double getPrice250mb() {
        return price250mb;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getFormId() {
        return formId;
    }

    public String getChildSiteRegistrationName() {
        return childSiteRegistrationName;
    }
}
