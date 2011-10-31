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

/**
 * @author Balakirev Anatoliy
 */
public enum States_CA implements State {

    AB(null, null),
    BC(null, null),
    MB(null, null),
    NB(null, null),
    NF(null, null),
    NT(null, null),
    NS(null, null),
    ON(null, null),
    PE(null, null),
    PQ(null, null),
    SK(null, null),
    YT(null, null);

    States_CA(Double commonTaxRates, Double foodTaxRates) {
        this.commonTaxRates = commonTaxRates;
        this.foodTaxRates = foodTaxRates;
    }

    private final Double commonTaxRates;

    private final Double foodTaxRates;

    public Double getCommonTaxRates() {
        return commonTaxRates;
    }

    public Double getFoodTaxRates() {
        return foodTaxRates;
    }

    @Override
    public Country getCountry() {
        return Country.CA;
    }

}
