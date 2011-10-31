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

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import java.util.List;

import com.shroggle.entity.DraftFormItem;

/**
 * @author Balakirev Anatoliy
 */

@DataTransferObject
public class CreateContactUsWidgetRequest {

    public List<DraftFormItem> getFormItems() {
        return formItems;
    }

    public void setFormItems(List<DraftFormItem> formItems) {
        this.formItems = formItems;
    }

    public int getContactUsId() {
        return contactUsId;
    }

    public void setContactUsId(int contactUsId) {
        this.contactUsId = contactUsId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Integer getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(Integer widgetId) {
        this.widgetId = widgetId;
    }

    public String getContactUsName() {
        return contactUsName;
    }

    public void setContactUsName(String contactUsName) {
        this.contactUsName = contactUsName;
    }

    public boolean getDisplayHeader() {
        return displayHeader;
    }

    public void setDisplayHeader(boolean displayHeader) {
        this.displayHeader = displayHeader;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @RemoteProperty
    private Integer widgetId;

    @RemoteProperty
    private String contactUsName;

    @RemoteProperty
    private int contactUsId;

    @RemoteProperty
    private boolean displayHeader;

    @RemoteProperty
    private String email;

    @RemoteProperty
    private String header;

    @RemoteProperty
    private List<DraftFormItem> formItems;

}