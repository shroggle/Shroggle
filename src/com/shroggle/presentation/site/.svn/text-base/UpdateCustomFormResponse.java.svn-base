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

import org.directwebremoting.annotations.RemoteProperty;
import org.directwebremoting.annotations.DataTransferObject;
import com.shroggle.entity.*;

import java.util.List;

/**
 * Author: dmitry.solomadin
 * Date: 29.01.2009
 */
@DataTransferObject
public class UpdateCustomFormResponse {
    @RemoteProperty
    private DraftCustomForm customForm;
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

    public DraftCustomForm getCustomForm() {
        return customForm;
    }

    public void setCustomForm(DraftCustomForm customForm) {
        this.customForm = customForm;
    }

    public List<DraftFormItem> getFormItems() {
        return formItems;
    }

    public void setFormItems(List<DraftFormItem> formItems) {
        this.formItems = formItems;
    }
}
