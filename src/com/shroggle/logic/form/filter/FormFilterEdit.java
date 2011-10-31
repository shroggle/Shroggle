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

import java.util.List;
import java.util.ArrayList;

import com.shroggle.logic.form.filter.FormFilterRuleEdit;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
public class FormFilterEdit {

    public int getFormId() {
        return formId;
    }

    public void setFormId(final int formId) {
        this.formId = formId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FormFilterRuleEdit> getRules() {
        return rules;
    }

    public void setRules(List<FormFilterRuleEdit> rules) {
        this.rules = rules;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @RemoteProperty
    private String name;

    @RemoteProperty
    private List<FormFilterRuleEdit> rules = new ArrayList<FormFilterRuleEdit>();

    @RemoteProperty
    private Integer id;

    @RemoteProperty
    private int formId;

}
