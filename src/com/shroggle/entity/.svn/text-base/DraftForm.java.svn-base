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

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
@Inheritance(strategy = InheritanceType.JOINED)
@Entity(name = "forms")
public abstract class DraftForm extends DraftItem implements Form {

    public abstract FormType getType();

    public List<FormItem> getFormItems() {
        return (List)formItems;
    }

    public List<DraftFormItem> getDraftFormItems() {
        return formItems;
    }

    public void setFormItems(List<? extends FormItem> formItems) {
        this.formItems = (List<DraftFormItem>)formItems;
    }

    public void addFormItem(final DraftFormItem formItem) {
        formItem.setDraftForm(this);
        formItems.add(formItem);
    }

    public List<Filter> getFilters() {
        return (List) Collections.unmodifiableList(filters);
    }

    public List<DraftFormFilter> getDraftFilters() {
        return Collections.unmodifiableList(filters);
    }

    public void setFilters(List<DraftFormFilter> filters) {
        this.filters = filters;
    }

    public void addFilter(DraftFormFilter formFilter) {
        formFilter.setForm(this);
        filters.add(formFilter);
    }

    public void removeFilter(final DraftFormFilter formFilter) {
        filters.remove(formFilter);
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "form")
    private List<DraftFormItem> formItems = new ArrayList<DraftFormItem>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "form")
    private List<DraftFormFilter> filters = new ArrayList<DraftFormFilter>();

}
