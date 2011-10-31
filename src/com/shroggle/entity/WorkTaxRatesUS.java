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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@Entity(name = "workTaxRates")
public class WorkTaxRatesUS extends WorkItem implements TaxRatesUS {

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "taxRates")
    private List<WorkTaxRateUS> taxRates = new ArrayList<WorkTaxRateUS>();

    public List<WorkTaxRateUS> getTaxRates() {
        return Collections.unmodifiableList(taxRates);
    }

    public void setTaxRates(List<WorkTaxRateUS> taxRates) {
        this.taxRates = taxRates;
    }

    public void addTaxRate(TaxRateUS taxRate) {
        taxRate.setTaxRates(this);
        this.taxRates.add((WorkTaxRateUS)taxRate);
    }

    @Override
    public ItemType getItemType() {
        return ItemType.TAX_RATES;
    }
}
