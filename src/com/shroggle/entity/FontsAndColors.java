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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@Entity(name = "fontsAndColors")
public class FontsAndColors {

    @Id
    private int id;

    @OneToMany(mappedBy = "id.fontsAndColors", cascade = CascadeType.REMOVE)
    private List<FontsAndColorsValue> cssValues = new ArrayList<FontsAndColorsValue>();
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }    

    public final void addCssValue(FontsAndColorsValue cssValue) {        
        cssValue.setFontsAndColors(this);
        cssValues.add(cssValue);
    }

    public List<FontsAndColorsValue> getCssValues() {
        return Collections.unmodifiableList(cssValues);
    }

    public void setCssValues(List<FontsAndColorsValue> cssValues) {
        this.cssValues = cssValues;
    }
}
