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
package com.shroggle.logic.site.taxRates;

import com.shroggle.entity.DraftTaxRateUS;
import com.shroggle.entity.DraftTaxRatesUS;
import com.shroggle.entity.State;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public class TaxRatesUSManager {

    public TaxRatesUSManager(DraftTaxRatesUS draftTaxRatesUS) {
        if (draftTaxRatesUS == null) {
            throw new IllegalArgumentException("Unable to create TaxRatesUSManager without DraftTaxRatesUS.");
        }
        this.draftTaxRatesUS = draftTaxRatesUS;
    }

    public int getId() {
        return draftTaxRatesUS.getId();
    }

    public String getName() {
        return draftTaxRatesUS.getName();
    }

    public void setName(final String name) {
        draftTaxRatesUS.setName(name);
    }

    public List<TaxRateUSManager> getTaxRates() {
        final List<TaxRateUSManager> managers = new ArrayList<TaxRateUSManager>();
        for (DraftTaxRateUS taxRate : draftTaxRatesUS.getTaxRates()) {
            final TaxRateUSManager manager = new TaxRateUSManager(taxRate);
            managers.add(manager);
        }
        return Collections.unmodifiableList(managers);
    }

    public void updateTaxByState(final State state, final Double taxRate, final boolean included) {
        final TaxRateUSManager manager = getFieldByState(state);
        if (manager != null) {
            manager.setTaxRate(taxRate);
            manager.setIncluded(included);
        }
    }

    public TaxRateUSManager getIncludedTaxRate(final State state) {
        final TaxRateUSManager manager = getFieldByState(state);
        return (manager != null && manager.isIncluded()) ? manager : null;
    }

    /*------------------------------------------------Private methods-------------------------------------------------*/

    private TaxRateUSManager getFieldByState(final State state) {
        for (TaxRateUSManager manager : getTaxRates()) {
            if (manager.getState() == state) {
                return manager;
            }
        }
        logger.severe("Unable to find tax rate by stateCode = " + state + ". Check your state codes.");
        return null;
    }
    /*------------------------------------------------Private methods-------------------------------------------------*/

    private final DraftTaxRatesUS draftTaxRatesUS;
    private Logger logger = Logger.getLogger(this.getClass().getName());
}
