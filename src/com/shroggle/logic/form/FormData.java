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

import com.shroggle.entity.*;
import com.shroggle.logic.childSites.ExpirationDateLogic;
import com.shroggle.logic.childSites.childSiteRegistration.ChildSiteRegistrationManager;
import com.shroggle.util.ServiceLocator;

import java.util.Date;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
public class FormData {

    public FormData(final Form form) {
        description = form.getDescription();
        formItems = form.getFormItems();
        formId = form.getId();
        formName = form.getName();
        showDescription = form.isShowDescription();
        termsAndConditions = form instanceof DraftRegistrationForm ? ((DraftRegistrationForm) form).getTermsAndConditions() : null;
        requireTermsAndConditions = form instanceof DraftRegistrationForm && ((DraftRegistrationForm) form).isRequireTermsAndConditions();

        updateChildSiteRegistrationFields(form);
    }

    public FormData(final FilledForm filledForm) {
        description = filledForm.getFormDescription();
        final DraftForm form = ServiceLocator.getPersistance().getFormById(filledForm.getFormId());
        formId = form.getFormId();
        formName = form.getName();
        showDescription = form.isShowDescription();
        termsAndConditions = form instanceof DraftRegistrationForm ? ((DraftRegistrationForm) form).getTermsAndConditions() : null;
        requireTermsAndConditions = form instanceof DraftRegistrationForm && ((DraftRegistrationForm) form).isRequireTermsAndConditions();
        updateChildSiteRegistrationFields(form);
        this.formItems = FormItemsManager.createFormItemsByFilledForm(filledForm, form);
    }

    private final String description;

    private final boolean showDescription;

    private final List<FormItem> formItems;

    private final int formId;

    private final String formName;

    private final String termsAndConditions;

    private final boolean requireTermsAndConditions;

    /*----------------------------------------Child Site Registration Settings----------------------------------------*/
    private double oneTimeFee;

    private boolean useOneTimeFee;

    private double price250mb;

    private Date startDate;

    private Date endDate;

    private boolean useStartDate;

    private boolean useEndDate;

    private boolean registrationCanceled;

    private String thanksForRegisteringText;
    /*----------------------------------------Child Site Registration Settings----------------------------------------*/

    private void updateChildSiteRegistrationFields(final Form form) {
        if (form instanceof DraftChildSiteRegistration) {
            DraftChildSiteRegistration childSiteRegistration = (DraftChildSiteRegistration) form;
            oneTimeFee = childSiteRegistration.getOneTimeFee();
            useOneTimeFee = childSiteRegistration.isUseOneTimeFee();
            price250mb = childSiteRegistration.getPrice250mb();
            startDate = childSiteRegistration.getStartDate();
            endDate = childSiteRegistration.getEndDate();
            useStartDate = startDate != null;
            useEndDate = endDate != null;
            registrationCanceled = ExpirationDateLogic.isNetworkMembershipExpired(new Date(), ((DraftChildSiteRegistration) form).getEndDate());
            thanksForRegisteringText = new ChildSiteRegistrationManager(childSiteRegistration).getNormalizedThanksForRegisteringText();
        } else {
            oneTimeFee = 0.0;
            useOneTimeFee = false;
            price250mb = 0.0;
            startDate = null;
            endDate = null;
            useStartDate = false;
            useEndDate = false;
            registrationCanceled = false;
            thanksForRegisteringText = null;
        }
    }

    public String getThanksForRegisteringText() {
        return thanksForRegisteringText;
    }

    public void setThanksForRegisteringText(String thanksForRegisteringText) {
        this.thanksForRegisteringText = thanksForRegisteringText;
    }

    public boolean isRegistrationCanceled() {
        return registrationCanceled;
    }

    public boolean isUseStartDate() {
        return useStartDate;
    }

    public boolean isUseEndDate() {
        return useEndDate;
    }

    public boolean isShowDescription() {
        return showDescription;
    }

    public String getDescription() {
        return description;
    }

    public List<FormItem> getFormItems() {
        return formItems;
    }

    public int getFormId() {
        return formId;
    }

    public String getFormName() {
        return formName;
    }

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

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public boolean isRequireTermsAndConditions() {
        return requireTermsAndConditions;
    }
}
