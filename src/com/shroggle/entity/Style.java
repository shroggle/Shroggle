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

import org.directwebremoting.annotations.RemoteProperty;
import org.directwebremoting.annotations.DataTransferObject;

import javax.persistence.*;

/**
 * @author Balakirev Anatoliy
 *         Date: 22.07.2009
 *         Time: 17:56:58
 */
@DataTransferObject
@Entity(name = "styles")
public class Style {

    public Style() {
        values = new StyleValue();
        measureUnits = new MeasureUnits();
        type = StyleType.EACH_SIDE_SEPARATELY;// According to the: http://jira.web-deva.com/browse/SW-6062
        name = "";
        styleId = -1;
    }

    @Id
    private int styleId;

    @RemoteProperty
    @Column(nullable = false, length = 250)
    private String name;

    @RemoteProperty
    @Column(nullable = false)
    @Embedded
    private StyleValue values;

    @RemoteProperty
    @Column(nullable = false)
    @Embedded
    private MeasureUnits measureUnits;

    @RemoteProperty
    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private StyleType type;

    public String stringValue(final boolean useMeasureUnit) {
        switch (type) {
            case NONE: {
                return " ";
            }
            default: {
                return areAllValuesEmpty() ? " " : (name + ":" +
                        createValue(values.getTopValue(), measureUnits.getTopMeasureUnit(), useMeasureUnit) + " " +
                        createValue(values.getRightValue(), measureUnits.getRightMeasureUnit(), useMeasureUnit) + " " +
                        createValue(values.getBottomValue(), measureUnits.getBottomMeasureUnit(), useMeasureUnit) + " " +
                        createValue(values.getLeftValue(), measureUnits.getLeftMeasureUnit(), useMeasureUnit) + " !important;");
            }
        }
    }

    private String createValue(final String value, final MeasureUnit measureUnit, final boolean useMeasureUnit) {
        if (useMeasureUnit) {
            return createValue(value, measureUnit);
        } else {
            return createValue(value);
        }
    }

    private String createValue(final String value, final MeasureUnit measureUnit) {
        return createValue(value) + measureUnit.toString().toLowerCase();
    }

    private String createValue(final String value) {
        return value.isEmpty() ? "0" : value;
    }

    private boolean areAllValuesEmpty(){
        return values.getTopValue().isEmpty() && values.getRightValue().isEmpty() &&
                values.getBottomValue().isEmpty() && values.getLeftValue().isEmpty();
    }

    public StyleValue getValues() {
        return values;
    }

    public void setValues(StyleValue values) {
        this.values = values;
    }

    public MeasureUnits getMeasureUnits() {
        return measureUnits;
    }

    public void setMeasureUnits(MeasureUnits measureUnits) {
        this.measureUnits = measureUnits;
    }

    public StyleType getType() {
        return type;
    }

    public void setType(StyleType type) {
        this.type = type;
    }

    public int getStyleId() {
        return styleId;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
