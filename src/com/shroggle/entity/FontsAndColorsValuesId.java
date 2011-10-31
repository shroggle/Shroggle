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

import org.hibernate.annotations.ForeignKey;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * @author Balakirev Anatoliy
 */
@Embeddable
public class FontsAndColorsValuesId implements Serializable {

    @Column(nullable = false, length = 250)
    private String name;

    @Column(nullable = false, length = 250)
    private String selector;

    @ManyToOne
    @JoinColumn(nullable = false, name = "fontsAndColorsId")
    @ForeignKey(name = "fontsAndColorsValuesFontsAndColorsId")
    private FontsAndColors fontsAndColors;

    public FontsAndColors getFontsAndColors() {
        return fontsAndColors;
    }

    public void setFontsAndColors(FontsAndColors fontsAndColors) {
        this.fontsAndColors = fontsAndColors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }
}
