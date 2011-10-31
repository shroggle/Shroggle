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

package com.shroggle.entity;


import org.directwebremoting.annotations.DataTransferObject;

import javax.persistence.Entity;
import javax.persistence.Lob;

@DataTransferObject
@Entity(name = "workRegistrationForms")
public class WorkRegistrationForm extends WorkForm implements RegistrationForm {

    @Lob
    private String termsAndConditions;

    private boolean requireTermsAndConditions;

    private boolean networkRegistration;

    @Lob
    private String groupsWithTime = "";

    public String getGroupsWithTime() {
        return groupsWithTime;
    }

    public void setGroupsWithTime(String groupsWithTime) {
        this.groupsWithTime = groupsWithTime;
    }

    public boolean isNetworkRegistration() {
        return networkRegistration;
    }

    public void setNetworkRegistration(boolean networkRegistration) {
        this.networkRegistration = networkRegistration;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public boolean isRequireTermsAndConditions() {
        return requireTermsAndConditions;
    }

    public void setRequireTermsAndConditions(boolean requireTermsAndConditions) {
        this.requireTermsAndConditions = requireTermsAndConditions;
    }

    public ItemType getItemType() {
        return ItemType.REGISTRATION;
    }

    public FormType getType() {
        return FormType.REGISTRATION;
    }
}