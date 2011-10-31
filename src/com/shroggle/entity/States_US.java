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

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.EnumConverter;

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject(converter = EnumConverter.class)
public enum States_US implements State {

    AL(4.0, null),
    AK(null, null),
    AZ(5.6, null),
    AR(6.0, 2.0),
    CA(8.25, null),
    CO(2.9, null),
    CT(6.0, null),
    DE(null, null),
    FL(6.0, null),
    GA(4.0, null),
    HI(4.0, null),
    ID(6.0, null),
    IL(6.25, 1.0),
    IN(7.0, null),
    IA(6.0, null),
    KS(5.3, null),
    KY(6.0, null),
    LA(4.0, null),
    ME(5.0, null),
    MD(6.0, null),
    MA(6.25, null),
    MI(6.0, null),
    MN(6.875, null),
    MS(7.0, null),
    MO(4.225, 1.225),
    MT(null, null),
    NE(5.5, null),
    NV(6.85, null),
    NH(null, null),
    NJ(7.0, null),
    NM(5.0, null),
    NY(4.0, null),
    NC(5.75, null),
    ND(5.0, null),
    OH(5.5, null),
    OK(4.5, null),
    OR(null, null),
    PA(6.0, null),
    RI(7.0, null),
    SC(6.0, null),
    SD(4.0, null),
    TN(7.0, 5.5),
    TX(6.25, null),
    UT(4.7, 1.75),
    VT(6.0, null),
    VA(5.0, 2.5),
    WA(6.5, null),
    WV(6.0, 3.0),
    WI(5.0, null),
    WY(4.0, null);

    States_US(Double commonTaxRates, Double foodTaxRates) {
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
        return Country.US;
    }
}
