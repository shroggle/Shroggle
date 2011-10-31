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

import com.shroggle.entity.DraftChildSiteRegistration;
import com.shroggle.entity.DraftFormItem;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import java.util.List;

/**
 * @author Balakirev Anatoliy
 */

@DataTransferObject
public class UpdateChildSiteRegistrationResponse {

    @RemoteProperty
    private DraftChildSiteRegistration childSiteRegistration;
    @RemoteProperty
    private List<DraftFormItem> formItems;
    @RemoteProperty
    private String startDateString;
    @RemoteProperty
    private String endDateString;

    public String getStartDateString() {
        return startDateString;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public String getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }

    public DraftChildSiteRegistration getChildSiteRegistration() {
        return childSiteRegistration;
    }

    public void setChildSiteRegistration(DraftChildSiteRegistration childSiteRegistration) {
        this.childSiteRegistration = childSiteRegistration;
    }

    public List<DraftFormItem> getFormItems() {
        return formItems;
    }

    public void setFormItems(List<DraftFormItem> formItems) {
        this.formItems = formItems;
    }
}