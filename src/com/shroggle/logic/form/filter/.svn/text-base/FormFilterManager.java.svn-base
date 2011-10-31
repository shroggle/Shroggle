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

import com.shroggle.entity.DraftForm;
import com.shroggle.entity.DraftFormFilter;
import com.shroggle.entity.DraftFormFilterRule;
import com.shroggle.entity.FilledForm;
import com.shroggle.logic.form.FilledFormLogic;
import com.shroggle.logic.form.filtering.FormFilterRuleProcessor;
import com.shroggle.logic.form.filtering.FormFilterRulesContainer;
import com.shroggle.logic.form.filtering.FormFilterSingleRule;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.ArrayList;
import java.util.List;

/**
 * Gallery can return filtered filled forms.
 * <pre>galleryLogic.getFilledForms();</pre>
 * <p/>
 * For any form and any filter you can use next code:
 * <pre>
 * final FormFilterLogic formFilterLogic = userLogic.getFormFilterById(formFilterId);
 * final FormLogic formLogic = ...;
 * formFilterLogic.getFilledForms(formLogic);
 * </pre>
 *
 * @author Artem Stasuk
 */
public class FormFilterManager {

    public FormFilterManager(final DraftFormFilter formFilter) {
        this.formFilter = formFilter;
    }

    public DraftFormFilter getFormFilter() {
        return formFilter;
    }

    public List<FormFilterRuleLogic> getRules() {
        final List<FormFilterRuleLogic> ruleLogics = new ArrayList<FormFilterRuleLogic>();
        for (final DraftFormFilterRule rule : formFilter.getRules()) {
            ruleLogics.add(new FormFilterRuleLogic(rule));
        }
        return ruleLogics;
    }

    public List<FilledForm> getFilledForms() {
        final List<FilledForm> filteredFilledForms = new ArrayList<FilledForm>();
        final List<FilledForm> filledForms = persistance.getFilledFormsByFormId(formFilter.getForm().getId());
        final FormFilterRuleProcessor ruleProcessor = getRuleProcessor();
        for (final FilledForm filledForm : filledForms) {
            final FilledFormLogic filledFormLogic = new FilledFormLogic(filledForm);
            if (ruleProcessor.accept(filledFormLogic)) {
                filteredFilledForms.add(filledForm);
            }
        }
        return filteredFilledForms;
    }

    public String getName() {
        return formFilter.getName();
    }

    public int getId() {
        return formFilter.getFormFilterId();
    }

    public DraftForm getForm() {
        return formFilter.getDraftForm();
    }

    /**
     * @return - create by form filter entity need rule processors and pack their in
     *         one rule processor "AND" and return it
     * @see FormFilterRulesContainer
     */
    private FormFilterRuleProcessor getRuleProcessor() {
        final List<FormFilterRuleProcessor> singleRuleProcessors = new ArrayList<FormFilterRuleProcessor>();
        for (final DraftFormFilterRule rule : formFilter.getRules()) {
            singleRuleProcessors.add(new FormFilterSingleRule(rule));
        }
        return new FormFilterRulesContainer(singleRuleProcessors);
    }

    private final DraftFormFilter formFilter;
    private final Persistance persistance = ServiceLocator.getPersistance();

}
