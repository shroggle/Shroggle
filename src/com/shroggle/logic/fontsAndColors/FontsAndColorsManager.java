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
package com.shroggle.logic.fontsAndColors;

import com.shroggle.entity.FontsAndColors;
import com.shroggle.entity.FontsAndColorsValue;
import com.shroggle.presentation.site.cssParameter.CreateFontsAndColorsRequest;
import com.shroggle.presentation.site.cssParameter.CssParameter;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

/**
 * @author Balakirev Anatoliy
 */
public class FontsAndColorsManager {

    public FontsAndColorsManager(FontsAndColors fontsAndColors) {
        if (fontsAndColors == null) {
            throw new IllegalArgumentException("Unable to create FontsAndColorsManager without FontsAndColors.");
        }
        this.fontsAndColors = fontsAndColors;
    }

    public void updateValues(final CreateFontsAndColorsRequest request) {
        for (final CssParameter cssParameter : request.getCssParameters()) {
            FontsAndColorsValue value = getFontsAndColorsValueInternal(cssParameter.getName(), cssParameter.getSelector());
            if (value == null) {
                value = new FontsAndColorsValue();
                value.setName(cssParameter.getName());
                value.setDescription(cssParameter.getDescription());
                value.setSelector(cssParameter.getSelector());
                fontsAndColors.addCssValue(value);
                persistance.putFontsAndColorsValue(value);
            }
            value.setValue(cssParameter.getValue());
        }
    }

    public FontsAndColorsValueManager getFontsAndColorsValue(final String name, final String selector) {
        final FontsAndColorsValue value = getFontsAndColorsValueInternal(name, selector);
        return value == null ? null : new FontsAndColorsValueManager(value);
    }

    private FontsAndColorsValue getFontsAndColorsValueInternal(final String name, final String selector) {
        for (FontsAndColorsValue value : fontsAndColors.getCssValues()) {
            if (value.getName().equals(name) && value.getSelector().equals(selector)) {
                return value;
            }
        }
        return null;
    }

    private final FontsAndColors fontsAndColors;
    private final Persistance persistance = ServiceLocator.getPersistance();
}
