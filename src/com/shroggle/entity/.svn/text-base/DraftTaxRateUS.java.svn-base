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

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;

/**
 * @author Balakirev Anatoliy
 */
@Entity(name = "draftTaxRate")
public class DraftTaxRateUS implements TaxRateUS {

    public DraftTaxRateUS() {
    }

    public DraftTaxRateUS(States_US state) {
        this.state = state;
        this.taxRate = state.getCommonTaxRates();
        this.included = false;
    }

    @Id
    private int id;

    private Double taxRate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 2)
    private States_US state = States_US.AL;

    @ManyToOne
    @ForeignKey(name = "draftTaxRateDraftTaxRatesId")
    @JoinColumn(name = "taxRatesId", nullable = false)
    private DraftTaxRatesUS taxRates;

    private boolean included;

    public boolean isIncluded() {
        return included;
    }

    public void setIncluded(boolean included) {
        this.included = included;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public States_US getState() {
        return state;
    }

    public void setState(States_US state) {
        this.state = state;
    }

    public DraftTaxRatesUS getTaxRates() {
        return taxRates;
    }

    public void setTaxRates(TaxRatesUS taxRates) {
        this.taxRates = (DraftTaxRatesUS)taxRates;
    }
}
