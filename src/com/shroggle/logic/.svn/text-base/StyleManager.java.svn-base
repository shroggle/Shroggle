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

package com.shroggle.logic;

import com.shroggle.entity.Style;
import com.shroggle.entity.MeasureUnit;

/**
 * @author Balakirev Anatoliy
 */
public class StyleManager {

    public static String createStyleValue(final String fieldName, final Style style) {
        if (style != null && fieldName != null && !fieldName.isEmpty()) {
            if (fieldName.equals("Value") || fieldName.equals("Vertical") || fieldName.equals("Top")) {
                return style.getValues().getTopValue();
            } else if (fieldName.equals("Horizontal") || fieldName.equals("Right")) {
                return style.getValues().getRightValue();
            } else if (fieldName.equals("Bottom")) {
                return style.getValues().getBottomValue();
            } else if (fieldName.equals("Left")) {
                return style.getValues().getLeftValue();
            }
        }
        return "";
    }

    public static MeasureUnit createMeasureValue(final String fieldName, final Style style) {
        if (style != null && fieldName != null && !fieldName.isEmpty()) {
            if (fieldName.equals("Value") || fieldName.equals("Vertical") || fieldName.equals("Top")) {
                return style.getMeasureUnits().getTopMeasureUnit();
            } else if (fieldName.equals("Horizontal") || fieldName.equals("Right")) {
                return style.getMeasureUnits().getRightMeasureUnit();
            } else if (fieldName.equals("Bottom")) {
                return style.getMeasureUnits().getBottomMeasureUnit();
            } else if (fieldName.equals("Left")) {
                return style.getMeasureUnits().getLeftMeasureUnit();
            }
        }
        return MeasureUnit.PX;
    }

    public static int createPX(String nonPXValue, MeasureUnit oldMeasurementUnit) {
        try {
            Integer intValue = Integer.parseInt(nonPXValue);
            return new Double(intValue * oldMeasurementUnit.getPxMultiplier()).intValue();
        } catch (Exception e) {
            return 0;
        }
    }
}