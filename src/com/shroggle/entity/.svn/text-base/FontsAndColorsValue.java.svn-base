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

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * @author Balakirev Anatoliy
 */
@Entity(name = "fontsAndColorsValues")
public class FontsAndColorsValue {

    @EmbeddedId
    private FontsAndColorsValuesId id = new FontsAndColorsValuesId();

    @Column(length = 250)
    private String value;

    @Column(nullable = false, length = 250)
    private String description;

    public FontsAndColors getFontsAndColors() {
        return id.getFontsAndColors();
    }

    public void setFontsAndColors(FontsAndColors fontsAndColors) {
        this.id.setFontsAndColors(fontsAndColors);
    }

    public String getName() {
        return id.getName();
    }

    public void setName(String name) {
        this.id.setName(name);
    }

    public String getSelector() {
        return id.getSelector();
    }

    public void setSelector(String selector) {
        this.id.setSelector(selector);
    }

    public FontsAndColorsValuesId getId() {
        return id;
    }

    public void setId(FontsAndColorsValuesId id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
