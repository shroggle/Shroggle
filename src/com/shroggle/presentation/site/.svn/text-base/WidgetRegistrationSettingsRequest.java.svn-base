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
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import java.util.List;

@DataTransferObject
public class WidgetRegistrationSettingsRequest {

    public List<DraftFormItem> getFormItems() {
        return formItems;
    }

    public void setFormItems(List<DraftFormItem> formItems) {
        this.formItems = formItems;
    }

    public boolean isDefaultForm() {
        return defaultForm;
    }

    public void setDefaultForm(boolean defaultForm) {
        this.defaultForm = defaultForm;
    }

    public boolean isShowHeader() {
        return showHeader;
    }

    public void setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
    }

    public String getRegistrationFormHeader() {
        return registrationFormHeader;
    }

    public void setRegistrationFormHeader(String registrationFormHeader) {
        this.registrationFormHeader = registrationFormHeader;
    }

    public String getRegistartionFormName() {
        return registartionFormName;
    }

    public void setRegistartionFormName(String registartionFormName) {
        this.registartionFormName = registartionFormName;
    }

    public Integer getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(Integer widgetId) {
        this.widgetId = widgetId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
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

    @RemoteProperty
    private String registartionFormName;

    @RemoteProperty
    private String registrationFormHeader;

    @RemoteProperty
    private boolean defaultForm;

    @RemoteProperty
    private boolean showHeader;

    @RemoteProperty
    private Integer siteId;

    @RemoteProperty
    private Integer widgetId;

    private List<DraftFormItem> formItems;

    @RemoteProperty
    private String termsAndConditions;

    @RemoteProperty
    private boolean requireTermsAndConditions;

}
