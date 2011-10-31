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
package com.shroggle.presentation.registration;

import com.shroggle.entity.DraftFormItem;
import com.shroggle.logic.groups.GroupsTime;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import java.util.List;
import java.util.ArrayList;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
public class SaveRegistrationRequest {

    @RemoteProperty
    private Integer widgetId;

    @RemoteProperty
    private int formId;

    @RemoteProperty
    private String formName;

    @RemoteProperty
    private boolean showHeader;

    @RemoteProperty
    private String formHeader;

    @RemoteProperty
    private List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();

    @RemoteProperty
    private boolean defaultForm;

    @RemoteProperty
    private boolean requireTermsAndConditions;

    @RemoteProperty
    private String termsAndConditions;

    @RemoteProperty
    private boolean networkRegistration;

    @RemoteProperty
    private List<GroupsTime> groupsWithTime = new ArrayList<GroupsTime>();

    public List<GroupsTime> getGroupsWithTime() {
        return groupsWithTime;
    }

    public void setGroupsWithTime(List<GroupsTime> groupsWithTime) {
        this.groupsWithTime = groupsWithTime;
    }

    public boolean isNetworkRegistration() {
        return networkRegistration;
    }

    public void setNetworkRegistration(boolean networkRegistration) {
        this.networkRegistration = networkRegistration;
    }

    public boolean isDefaultForm() {
        return defaultForm;
    }

    public void setDefaultForm(boolean defaultForm) {
        this.defaultForm = defaultForm;
    }

    public boolean isRequireTermsAndConditions() {
        return requireTermsAndConditions;
    }

    public void setRequireTermsAndConditions(boolean requireTermsAndConditions) {
        this.requireTermsAndConditions = requireTermsAndConditions;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public List<DraftFormItem> getFormItems() {
        return formItems;
    }

    public void setFormItems(List<DraftFormItem> formItems) {
        this.formItems = formItems;
    }

    public boolean isShowHeader() {
        return showHeader;
    }

    public void setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
    }

    public String getFormHeader() {
        return formHeader;
    }

    public void setFormHeader(String formHeader) {
        this.formHeader = formHeader;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public Integer getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(Integer widgetId) {
        this.widgetId = widgetId;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }
}
