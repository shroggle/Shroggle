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
 *         Date: 22.07.2009
 *         Time: 16:33:44
 */
public enum MeasureUnit {

    PX("px", 1), EM("em", 16), PT("pt", 1.4), IN("in", 96.3), CM("cm", 37.9), MM("mm", 3.8), EX("ex", 7.6), PC("pc", 16);

    MeasureUnit(String value, double pxMultiplier) {
        this.value = value;
        this.pxMultiplier = pxMultiplier;
    }

    public String getValue() {
        return value;
    }

    public double getPxMultiplier() {
        return pxMultiplier;
    }

    private final String value;
    private final double pxMultiplier;
}
