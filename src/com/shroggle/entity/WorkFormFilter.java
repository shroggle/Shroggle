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
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
@Entity(name = "workFormFilters")
public class WorkFormFilter extends Filter {

    public List<WorkFormFilterRule> getRules() {
        return Collections.unmodifiableList(rules);
    }

    public void removeRule(final WorkFormFilterRule rule) {
        rules.remove(rule);
    }

    public Form getForm() {
        return form;
    }

    public void setWorkForm(WorkForm form) {
        this.form = form;
    }

    @JoinColumn(name = "formId")
    @ManyToOne
    @ForeignKey(name = "workFormFiltersWorkFormId")
    private WorkForm form;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formFilter")
    private List<WorkFormFilterRule> rules = new ArrayList<WorkFormFilterRule>();

}