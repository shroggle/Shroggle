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
package com.shroggle.entity;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: dmitry.solomadin
 */
@DataTransferObject
@Entity(name = "formFilterRules")
public class DraftFormFilterRule implements FormFilterRuleIface {

    @Id
    private int formFilterRuleId;

    @ManyToOne
    @JoinColumn(name = "formFilterId", nullable = false)
    @ForeignKey(name = "formFilterRulesFormFilterId")
    private DraftFormFilter formFilter;

    private int formItemId;

    @CollectionOfElements
    private List<String> criteria = new ArrayList<String>();

    private boolean include = true;

    public DraftFormFilter getFormFilter() {
        return formFilter;
    }

    /**
     * This is protected method, don't use it. For add rule to filter
     * use FormFilter.addRule()
     *
     * @param formFilter - form filter
     * @see DraftFormFilter
     */
    void setFormFilter(final DraftFormFilter formFilter) {
        this.formFilter = formFilter;
    }

    public int getFormFilterRuleId() {
        return formFilterRuleId;
    }

    public void setFormFilterRuleId(final int formFilterRuleId) {
        this.formFilterRuleId = formFilterRuleId;
    }

    public List<String> getCriteria() {
        return criteria;
    }

    public void setCriteria(final List<String> criteria) {
        this.criteria = criteria;
    }

    public boolean isInclude() {
        return include;
    }

    public List<Integer> getAlsoSearchByFields() {
        return new ArrayList<Integer>();
    }

    public OptionDisplayType getDisplayType() {
        return OptionDisplayType.NONE;
    }

    public void setInclude(final boolean include) {
        this.include = include;
    }

    public int getFormItemId() {
        return formItemId;
    }

    public void setFormItemId(final int formItemId) {
        this.formItemId = formItemId;
    }

}
