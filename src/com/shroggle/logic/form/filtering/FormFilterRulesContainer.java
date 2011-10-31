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
package com.shroggle.logic.form.filtering;

import com.shroggle.logic.form.filtering.FormFilterRuleProcessor;
import com.shroggle.logic.form.FilledFormLogic;

import java.util.Collections;
import java.util.List;

/**
 * @author Artem Stasuk
 */
public class FormFilterRulesContainer implements FormFilterRuleProcessor {

    public FormFilterRulesContainer(final List<FormFilterRuleProcessor> ruleProcessors) {
        this.ruleProcessors = Collections.unmodifiableList(ruleProcessors);
    }

    @Override
    public boolean accept(final FilledFormLogic filledFormLogic) {
        for (final FormFilterRuleProcessor ruleProcessor : ruleProcessors) {
            if (!ruleProcessor.accept(filledFormLogic)) return false;
        }
        return true;
    }

    private final List<FormFilterRuleProcessor> ruleProcessors;

}
