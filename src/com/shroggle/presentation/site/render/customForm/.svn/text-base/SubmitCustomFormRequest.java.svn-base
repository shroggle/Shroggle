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
package com.shroggle.presentation.site.render.customForm;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import com.shroggle.entity.FilledFormItem;

import java.util.List;

/**
 * Author: dmitry.solomadin
 */
@DataTransferObject
public class SubmitCustomFormRequest {

    @RemoteProperty
    private List<FilledFormItem> filledFormItems;

    @RemoteProperty
    private String verificationCode;

    @RemoteProperty
    private int formId;

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

    public Integer getFilledFormId() {
        return filledFormId;
    }

    public void setFilledFormId(Integer filledFormId) {
        this.filledFormId = filledFormId;
    }

    public int getPageBreaksToPass() {
        return pageBreaksToPass;
    }

    public void setPageBreaksToPass(int pageBreaksToPass) {
        this.pageBreaksToPass = pageBreaksToPass;
    }

    public boolean isRequestNextPage() {
        return requestNextPage;
    }

    public void setRequestNextPage(boolean requestNextPage) {
        this.requestNextPage = requestNextPage;
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

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
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
}
