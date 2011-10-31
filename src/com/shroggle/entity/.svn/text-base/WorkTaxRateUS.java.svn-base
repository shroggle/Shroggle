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
@Entity(name = "workTaxRate")
public class WorkTaxRateUS implements TaxRateUS {

    @Id
    private int id;

    private Double taxRate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 2)
    private States_US state = States_US.AL;

    @ManyToOne
    @ForeignKey(name = "workTaxRateWorkTaxRatesId")
    @JoinColumn(name = "taxRatesId", nullable = false)
    private WorkTaxRatesUS taxRates;

    private boolean included;

    public boolean isIncluded() {
        return included;
    }

    public void setIncluded(boolean included) {
        this.included = included;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public State getState() {
        return state;
    }

    public void setState(States_US state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WorkTaxRatesUS getTaxRates() {
        return taxRates;
    }

    public void setTaxRates(TaxRatesUS taxRates) {
        this.taxRates = (WorkTaxRatesUS)taxRates;
    }
}
