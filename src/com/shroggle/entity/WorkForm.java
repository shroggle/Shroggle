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
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
@Inheritance(strategy = InheritanceType.JOINED)
@Entity(name = "workForms")
public abstract class WorkForm extends WorkItem implements Form {

    public abstract FormType getType();

    public List<FormItem> getFormItems() {
        return (List)formItems;
    }

    public List<Filter> getFilters() {
        return (List) filters;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "form")
    private List<WorkFormItem> formItems = new ArrayList<WorkFormItem>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "form")
    private List<WorkFormFilter> filters = new ArrayList<WorkFormFilter>();

}