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
package com.shroggle.logic.form.filter;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import java.util.Map;
import java.util.List;

import com.shroggle.entity.FormItemType;

/**
 * Author: dmitry.solomadin
 */
@DataTransferObject
public class FilterFormItemInfo {

    @RemoteProperty
    private int formItemId;

    @RemoteProperty
    private Map<Integer, List<String>> itemOptions;

    @RemoteProperty
    private String formItemText;

    @RemoteProperty
    private FormItemType formItemType;

    public int getFormItemId() {
        return formItemId;
    }

    public void setFormItemId(int formItemId) {
        this.formItemId = formItemId;
    }

    public Map<Integer, List<String>> getItemOptions() {
        return itemOptions;
    }

    public void setItemOptions(Map<Integer, List<String>> itemOptions) {
        this.itemOptions = itemOptions;
    }

    public String getFormItemText() {
        return formItemText;
    }

    public void setFormItemText(String formItemText) {
        this.formItemText = formItemText;
    }

    public FormItemType getFormItemType() {
        return formItemType;
    }

    public void setFormItemType(FormItemType formItemType) {
        this.formItemType = formItemType;
    }
}
