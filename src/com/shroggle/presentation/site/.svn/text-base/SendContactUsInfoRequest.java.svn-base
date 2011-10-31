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

import com.shroggle.entity.SiteShowOption;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import com.shroggle.entity.FilledFormItem;

import java.util.List;

/**
 * @author Balakirev Anatoliy
 */

@DataTransferObject
public class SendContactUsInfoRequest {

    @RemoteProperty
    private int widgetId;

    @RemoteProperty
    private int formId;

    @RemoteProperty
    private String verificationCode;

    @RemoteProperty
    private List<FilledFormItem> filledFormItems;

    @RemoteProperty
    private boolean requestNextPage;

    @RemoteProperty
    private int pageBreaksToPass;

    @RemoteProperty
    private Integer filledFormId;

    @RemoteProperty
    private boolean showFromAddRecord;

    @RemoteProperty
    private SiteShowOption siteShowOption;

    public SiteShowOption getSiteShowOption() {
        return siteShowOption;
    }

    public void setSiteShowOption(SiteShowOption siteShowOption) {
        this.siteShowOption = siteShowOption;
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

    public int getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(int widgetId) {
        this.widgetId = widgetId;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}