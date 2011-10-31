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

package com.shroggle.logic.widget;

import com.shroggle.entity.*;

import java.util.*;

/**
 * @author Balakirev Anatoliy
 */
public class CssParametersLinkSorter {

    public static List<FontsAndColorsValue> execute(final List<FontsAndColorsValue> unsortedValues) {
        final List<FontsAndColorsValue> sortedValues = new ArrayList<FontsAndColorsValue>(unsortedValues);
        final String selectorValues[] = {":link", ":visited", ":active", ":hover"};
        for (String selectorValue : selectorValues) {
            final List<FontsAndColorsValue> valuesBySelector = getValueBySelector(sortedValues, selectorValue);
            if (!valuesBySelector.isEmpty()) {
                sortedValues.removeAll(valuesBySelector);
                sortedValues.addAll(valuesBySelector);
            }
        }
        return sortedValues;
    }

    private static List<FontsAndColorsValue> getValueBySelector(final List<FontsAndColorsValue> values, final String selector) {
        List<FontsAndColorsValue> fontsAndColorsValues = new ArrayList<FontsAndColorsValue>();
        for (FontsAndColorsValue value : values) {
            if (value != null && value.getSelector() != null && selector != null) {
                if (value.getSelector().toLowerCase().contains(selector.toLowerCase())) {
                    fontsAndColorsValues.add(value);
                }
            }
        }
        return fontsAndColorsValues;
    }

}