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

package com.shroggle.logic.advancedSearch;

import com.shroggle.entity.*;
import com.shroggle.exception.AdvancedSearchNotFoundException;
import com.shroggle.exception.AdvancedSearchOptionNotFoundException;
import com.shroggle.logic.form.FilledFormLogic;
import com.shroggle.logic.form.filtering.FormFilterRuleProcessor;
import com.shroggle.logic.form.filtering.FormFilterRulesContainer;
import com.shroggle.logic.form.filtering.FormFilterSingleRule;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
public class AdvancedSearchManager {

    private DraftAdvancedSearch advancedSearch;

    public AdvancedSearchManager(final int advancedSearchId) {
        this.advancedSearch = persistance.getDraftItem(advancedSearchId);

        if (this.advancedSearch == null) {
            throw new AdvancedSearchNotFoundException("Cannot find advanced search by Id=" + advancedSearchId);
        }
    }

    public AdvancedSearchManager(final DraftAdvancedSearch advancedSearch) {
        if (advancedSearch == null) {
            throw new AdvancedSearchNotFoundException("Cannot initialize AdvancedSearchManager with null adv. search.");
        }

        this.advancedSearch = advancedSearch;
    }

    public DraftAdvancedSearchOption getSearchOptionById(final int searchOptionId) {
        for (DraftAdvancedSearchOption searchOption : advancedSearch.getAdvancedSearchOptions()) {
            if (searchOption.getAdvancedSearchOptionId() == searchOptionId) {
                return searchOption;
            }
        }

        return null;
    }

    public List<FilledForm> getFilteredFilledForms(List<FilledForm> initialFilledForms) {
        final List<FilledForm> filteredFilledForms = new ArrayList<FilledForm>();
        final FormFilterRuleProcessor ruleProcessor = getRuleProcessor();

        for (final FilledForm filledForm : initialFilledForms) {
            final FilledFormLogic filledFormLogic = new FilledFormLogic(filledForm);
            if (ruleProcessor.accept(filledFormLogic)) {
                filteredFilledForms.add(filledForm);
            }
        }

        return filteredFilledForms;
    }

    public void removeAllOptions() {
        final List<DraftAdvancedSearchOption> optionsToDelete = new ArrayList<DraftAdvancedSearchOption>();
        optionsToDelete.addAll(advancedSearch.getAdvancedSearchOptions());
        for (DraftAdvancedSearchOption searchOption : optionsToDelete) {
            new AdvancedSearchOptionManager(searchOption).remove();
        }
    }

    public void createOrUpdateSearchOptions(final List<DraftAdvancedSearchOption> searchOptions) {
        persistanceTransaction.execute(new Runnable() {
            public void run() {
                for (DraftAdvancedSearchOption searchOption : searchOptions) {
                    if (searchOption.getAdvancedSearchOptionId() == 0) {
                        searchOption.setAdvancedSearch(advancedSearch);
                        persistance.putAdvancedSearchOption(searchOption);
                        advancedSearch.addSearchOption(searchOption);
                    } else {
                        final DraftAdvancedSearchOption existingSearchOption =
                                persistance.getAdvancedSearchOptionById(searchOption.getAdvancedSearchOptionId());

                        if (existingSearchOption == null) {
                            throw new AdvancedSearchOptionNotFoundException("Cannot find advanced search option by Id=" +
                                    searchOption.getAdvancedSearchOptionId() + ". Wrong Id was specified in request.");
                        }

                        existingSearchOption.setOptionCriteria(searchOption.getOptionCriteria());
                        existingSearchOption.setAlsoSearchByFields(searchOption.getAlsoSearchByFields());
                        existingSearchOption.setDisplayType(searchOption.getDisplayType());
                        existingSearchOption.setFieldLabel(searchOption.getFieldLabel());
                        existingSearchOption.setPosition(searchOption.getPosition());
                    }
                }
            }
        });
    }

    private FormFilterRuleProcessor getRuleProcessor() {
        final List<FormFilterRuleProcessor> singleRuleProcessors = new ArrayList<FormFilterRuleProcessor>();
        for (final DraftAdvancedSearchOption rule : advancedSearch.getAdvancedSearchOptions()) {
            singleRuleProcessors.add(new FormFilterSingleRule(rule));
        }
        return new FormFilterRulesContainer(singleRuleProcessors);
    }

    private Persistance persistance = ServiceLocator.getPersistance();
    private PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}
