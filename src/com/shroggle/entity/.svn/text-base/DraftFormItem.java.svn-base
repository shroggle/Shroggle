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

import com.shroggle.util.cache.CachePolicy;
import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
@CachePolicy(maxElementsInMemory = 1000)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity(name = "formItems")
public class DraftFormItem extends FormItem {

    @ManyToOne
    @JoinColumn(name = "formId", nullable = false)
    @ForeignKey(name = "formItemsFormId")
    private DraftForm form;

    public Form getForm() {
        return form;
    }

    public void setDraftForm(final DraftForm draftForm) {
        this.form = draftForm;
    }

}
