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

import com.shroggle.entity.DraftFormFilterRule;
import com.shroggle.entity.DraftFormItem;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.ServiceLocator;

/**
 * @author Artem Stasuk
 */
public class FormFilterRuleLogic {

    public FormFilterRuleLogic(final DraftFormFilterRule formFilterRule) {
        this.formFilterRule = formFilterRule;
    }

    public DraftFormFilterRule getFormFilterRule() {
        return formFilterRule;
    }

    public DraftFormItem getFormItem() {
        return persistance.getFormItemById(formFilterRule.getFormItemId());
    }

    private final DraftFormFilterRule formFilterRule;
    private final Persistance persistance = ServiceLocator.getPersistance();

}
