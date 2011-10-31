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
import com.shroggle.entity.State;
import com.shroggle.logic.countries.states.StateManager;

import java.math.BigDecimal;

/**
 * @author Balakirev Anatoliy
 */
public class TaxRateUSManager {

    public TaxRateUSManager(DraftTaxRateUS draftTaxRateUS) {
        if (draftTaxRateUS == null) {
            throw new IllegalArgumentException("Unable to create TaxRateUSManager without DraftTaxRateUS.");
        }
        this.draftTaxRateUS = draftTaxRateUS;
    }

    public double getTaxRate() {
        return draftTaxRateUS.getTaxRate() != null ? draftTaxRateUS.getTaxRate() : 0.0;
    }

    public void setTaxRate(Double commonTaxRates) {
        draftTaxRateUS.setTaxRate(commonTaxRates);
    }

    public String getTaxRateAsString() {
        if (draftTaxRateUS.getTaxRate() == null) {
            return "";
        } else {
            return new BigDecimal(draftTaxRateUS.getTaxRate().toString()).toString();
        }
    }

    public State getState() {
        return draftTaxRateUS.getState();
    }

    public int getId() {
        return draftTaxRateUS.getId();
    }

    public boolean isIncluded() {
        return draftTaxRateUS.isIncluded();
    }

    public void setIncluded(final boolean included) {
        draftTaxRateUS.setIncluded(included);
    }

    public String getStateName() {
        return new StateManager(draftTaxRateUS.getState()).getName();
    }

    private final DraftTaxRateUS draftTaxRateUS;
}
