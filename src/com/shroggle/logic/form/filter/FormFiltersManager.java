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
import com.shroggle.entity.SiteOnItemRightType;
import com.shroggle.exception.FormFilterNotFoundException;
import com.shroggle.exception.FormFilterNotUniqueNameException;
import com.shroggle.exception.FormNotFoundException;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.PersistanceTransactionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * @author Artem Stasuk
 */
public class FormFiltersManager {

    public FormFiltersManager(final UserManager userManager) {
        this.userManager = userManager;
    }

    public FormFilterManager save(final FormFilterEdit edit) {
        return persistanceTransaction.execute(new PersistanceTransactionContext<FormFilterManager>() {

            @Override
            public FormFilterManager execute() {
                final DraftFormFilter originalFormFilter = persistance.getFormFilterByNameAndFormId(
                        edit.getName(), edit.getFormId());
                if (originalFormFilter != null && edit.getId() == null) {
                    throw new FormFilterNotUniqueNameException("Not unique name " + edit.getName());
                } else if (originalFormFilter != null && originalFormFilter.getFormFilterId() != edit.getId()) {
                    throw new FormFilterNotUniqueNameException("Not unique name " + edit.getName());
                }

                final DraftFormFilter formFilter;
                final UserRightManager userRightManager = userManager.getRight();
                if (edit.getId() == null) {
                    final DraftForm form = persistance.getFormById(edit.getFormId());
                    if (userRightManager.toSiteItem(form) != SiteOnItemRightType.EDIT) {
                        throw new FormNotFoundException("Can't find form " + edit.getFormId());
                    }

                    formFilter = new DraftFormFilter();
                    formFilter.setName(edit.getName());
                    form.addFilter(formFilter);
                    persistance.putFormFilter(formFilter);
                } else {
                    formFilter = persistance.getFormFilterById(edit.getId());
                    if (formFilter == null || userRightManager.toSiteItem(formFilter.getForm()) != SiteOnItemRightType.EDIT) {
                        throw new FormFilterNotFoundException("Can't find " + edit.getId());
                    }
                    formFilter.setName(edit.getName());
                }


                final List<DraftFormFilterRule> rules = new ArrayList<DraftFormFilterRule>(formFilter.getRules());
                for (final DraftFormFilterRule rule : rules) {
                    persistance.removeFormFilterRule(rule);
                }

                for (final FormFilterRuleEdit ruleEdit : edit.getRules()) {
                    final DraftFormFilterRule rule = new DraftFormFilterRule();
                    rule.setInclude(ruleEdit.isInclude());
                    rule.setCriteria(ruleEdit.getCriteria());
                    rule.setFormItemId(ruleEdit.getFormItemId());
                    formFilter.addRule(rule);
                    persistance.putFormFilterRule(rule);
                }

                return new FormFilterManager(formFilter);
            }

        });
    }

    public List<FormFilterManager> getFilters() {
        final List<DraftFormFilter> filters = persistance.getFormFiltersByUserId(userManager.getUserId());
        final List<FormFilterManager> filterManagers = new ArrayList<FormFilterManager>(filters.size());
        for (final DraftFormFilter filter : filters) {
            filterManagers.add(new FormFilterManager(filter));
        }
        return filterManagers;
    }

    public FormFilterManager get(final Integer id) {
        if (id != null) {
            final DraftFormFilter formFilter = persistance.getFormFilterById(id);
            if (formFilter != null) {
                return new FormFilterManager(formFilter);
            }
        }
        throw new FormFilterNotFoundException("Can't find " + id);
    }

    public String getDefaultName(final int formId){
        final Set<String> usedNames = new HashSet<String>();
        for (final DraftFormFilter filter : persistance.getFormById(formId).getDraftFilters()) {
            usedNames.add(filter.getName());
        }

        int number = 1;
        final String name = "Form Filter";

        while (true) {
            if (!usedNames.contains(name + number)) {
                return name + number;
            }
            if (number >= Integer.MAX_VALUE) {
                number = (int) Math.round(Math.random());
            } else {
                number++;
            }
        }
    }

    public void delete(final int id) {
        final DraftFormFilter formFilter = persistance.getFormFilterById(id);
        final UserRightManager userRightManager = userManager.getRight();
        if (formFilter != null && userRightManager.toSiteItem(formFilter.getForm()) == SiteOnItemRightType.EDIT) {
            persistanceTransaction.execute(new Runnable() {

                @Override
                public void run() {
                    persistance.removeFormFilter(formFilter);
                    formFilter.getDraftForm().removeFilter(formFilter);
                }

            });
        }
    }

    private final UserManager userManager;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}
