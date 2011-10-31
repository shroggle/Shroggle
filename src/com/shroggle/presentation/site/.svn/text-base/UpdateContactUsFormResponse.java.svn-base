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

import com.shroggle.entity.DraftContactUs;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import java.util.List;

import com.shroggle.entity.DraftFormItem;
import com.shroggle.entity.SiteOnItemRightType;

/**
 * @author Balakirev Anatoliy
 */

@DataTransferObject
public class UpdateContactUsFormResponse {

    @RemoteProperty
    private DraftContactUs contactUs;
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

    public DraftContactUs getContactUs() {
        return contactUs;
    }

    public void setContactUs(DraftContactUs contactUs) {
        this.contactUs = contactUs;
    }

    public List<DraftFormItem> getFormItems() {
        return formItems;
    }

    public void setFormItems(List<DraftFormItem> formItems) {
        this.formItems = formItems;
    }
}