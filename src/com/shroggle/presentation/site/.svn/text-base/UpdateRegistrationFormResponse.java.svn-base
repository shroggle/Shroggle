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

package com.shroggle.presentation.site;

import com.shroggle.entity.DraftFormItem;
import com.shroggle.entity.DraftRegistrationForm;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import java.util.List;

import com.shroggle.entity.SiteOnItemRightType;

/**
 * @author Balakirev Anatoliy
 */

@DataTransferObject
public class UpdateRegistrationFormResponse {

    @RemoteProperty
    private DraftRegistrationForm registrationForm;
    @RemoteProperty
    private List<DraftFormItem> formItems;
    @RemoteProperty
    private SiteOnItemRightType siteOnItemRightType;

    public SiteOnItemRightType getShowOnItemRightType() {
        return siteOnItemRightType;
    }

    public void setShowOnItemRightType(SiteOnItemRightType siteOnItemRightType) {
        this.siteOnItemRightType = siteOnItemRightType;
    }

    public DraftRegistrationForm getRegistrationForm() {
        return registrationForm;
    }

    public void setRegistrationForm(DraftRegistrationForm registrationForm) {
        this.registrationForm = registrationForm;
    }

    public List<DraftFormItem> getFormItems() {
        return formItems;
    }

    public void setFormItems(List<DraftFormItem> formItems) {
        this.formItems = formItems;
    }
}