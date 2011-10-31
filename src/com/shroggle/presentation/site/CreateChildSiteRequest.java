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

import com.shroggle.entity.FilledFormItem;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import java.util.List;

@DataTransferObject
public class CreateChildSiteRequest {

    @RemoteProperty
    private String email;

    @RemoteProperty
    private String password;

    @RemoteProperty
    private String confirmPassword;

    @RemoteProperty
    private String verificationCode;

    @RemoteProperty
    private int widgetId;

    @RemoteProperty
    private boolean showFromAddRecord;

    @RemoteProperty
    private boolean requestNextPage;

    @RemoteProperty
    private int pageBreaksToPass;

    @RemoteProperty
    private Integer filledFormId;

    @RemoteProperty
    private List<FilledFormItem> filledFormItems;

    @RemoteProperty
    private int settingsId;

    @RemoteProperty
    private int userId;

    @RemoteProperty
    private boolean editDetails;

    @RemoteProperty
    private int formId;

    public boolean isEditDetails() {
        return editDetails;
    }

    public void setEditDetails(boolean editDetails) {
        this.editDetails = editDetails;
    }

    public int getSettingsId() {
        return settingsId;
    }

    public void setSettingsId(int settingsId) {
        this.settingsId = settingsId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isRequestNextPage() {
        return requestNextPage;
    }

    public void setRequestNextPage(boolean requestNextPage) {
        this.requestNextPage = requestNextPage;
    }

    public int getPageBreaksToPass() {
        return pageBreaksToPass;
    }

    public void setPageBreaksToPass(int pageBreaksToPass) {
        this.pageBreaksToPass = pageBreaksToPass;
    }

    public Integer getFilledFormId() {
        return filledFormId;
    }

    public void setFilledFormId(Integer filledFormId) {
        this.filledFormId = filledFormId;
    }

    public int getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(int widgetId) {
        this.widgetId = widgetId;
    }

    public boolean isShowFromAddRecord() {
        return showFromAddRecord;
    }

    public void setShowFromAddRecord(boolean showFromAddRecord) {
        this.showFromAddRecord = showFromAddRecord;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public List<FilledFormItem> getFilledFormItems() {
        return filledFormItems;
    }

    public void setFilledFormItems(List<FilledFormItem> filledFormItems) {
        this.filledFormItems = filledFormItems;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}