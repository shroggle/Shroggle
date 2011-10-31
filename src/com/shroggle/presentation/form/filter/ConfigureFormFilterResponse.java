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
package com.shroggle.presentation.form.filter;

import org.directwebremoting.annotations.RemoteProperty;
import org.directwebremoting.annotations.DataTransferObject;
import com.shroggle.logic.form.filter.FormFilterRuleEdit;
import com.shroggle.logic.form.filter.FilterFormItemInfo;

import java.util.List;

/**
 * @author Artem Stasuk
 */
@DataTransferObject
public class ConfigureFormFilterResponse {

    public List<FormFilterRuleEdit> getRules() {
        return rules;
    }

    public void setRules(List<FormFilterRuleEdit> rules) {
        this.rules = rules;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public List<FilterFormItemInfo> getItemInfos() {
        return itemInfos;
    }

    public void setItemInfos(List<FilterFormItemInfo> itemInfos) {
        this.itemInfos = itemInfos;
    }

    @RemoteProperty
    private String html;

    @RemoteProperty
    private List<FormFilterRuleEdit> rules;

    @RemoteProperty
    private List<FilterFormItemInfo> itemInfos;

}
