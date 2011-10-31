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

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
@Entity(name = "workFormItems")
public class WorkFormItem extends FormItem {

    @ManyToOne
    @JoinColumn(name = "formId", nullable = false)
    @ForeignKey(name = "workFormItemsWorkFormId")
    private WorkForm form;

    public Form getForm() {
        return form;
    }

    public void setWorkForm(final WorkForm workForm) {
        this.form = workForm;
    }

}