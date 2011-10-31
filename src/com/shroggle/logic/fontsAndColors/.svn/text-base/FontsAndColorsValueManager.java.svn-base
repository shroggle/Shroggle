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
import com.shroggle.entity.FontsAndColorsValuesId;

/**
 * @author Balakirev Anatoliy
 */
public class FontsAndColorsValueManager {

    public FontsAndColorsValueManager(FontsAndColorsValue fontsAndColorsValue) {
        if (fontsAndColorsValue == null) {
            throw new IllegalArgumentException("Unable to create FontsAndColorsValueManager without FontsAndColorsValue.");
        }
        this.fontsAndColorsValue = fontsAndColorsValue;
    }

    /*----------------------------------SpanShapeRenderer.Simple getters and setters----------------------------------*/

    public FontsAndColors getFontsAndColors() {
        return fontsAndColorsValue.getId().getFontsAndColors();
    }

    public void setFontsAndColors(FontsAndColors fontsAndColors) {
        this.fontsAndColorsValue.getId().setFontsAndColors(fontsAndColors);
    }

    public String getName() {
        return fontsAndColorsValue.getId().getName();
    }

    public void setName(String name) {
        this.fontsAndColorsValue.getId().setName(name);
    }

    public String getSelector() {
        return fontsAndColorsValue.getId().getSelector();
    }

    public void setSelector(String selector) {
        this.fontsAndColorsValue.getId().setSelector(selector);
    }

    public FontsAndColorsValuesId getId() {
        return fontsAndColorsValue.getId();
    }

    public void setId(FontsAndColorsValuesId id) {
        this.fontsAndColorsValue.setId(id);
    }

    public String getValue() {
        return fontsAndColorsValue.getValue();
    }

    public void setValue(String value) {
        this.fontsAndColorsValue.setValue(value);
    }

    public String getDescription() {
        return fontsAndColorsValue.getDescription();
    }

    public void setDescription(String description) {
        this.fontsAndColorsValue.setDescription(description);
    }
    /*----------------------------------SpanShapeRenderer.Simple getters and setters----------------------------------*/
    
    private final FontsAndColorsValue fontsAndColorsValue;
}
