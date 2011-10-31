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

package com.shroggle.util.filesystem;

import com.shroggle.entity.MenuStyleType;
import com.shroggle.entity.ItemType;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stasuk Artem
 */
class CssParametersFloatRecord {

    @XmlAttribute
    public MenuStyleType getMenuStyle() {
        return menuStyle;
    }

    public void setMenuStyle(final MenuStyleType menuStyle) {
        this.menuStyle = menuStyle;
    }

    @XmlElement(name = "floatParameter")
    public List<CssParameterFloat> getFloatCssParameters() {
        return cssParametersFloat;
    }

    public void setFloatCssParameters(final List<CssParameterFloat> cssParametersFloat) {
        this.cssParametersFloat = cssParametersFloat;
    }

    @XmlAttribute
    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    private ItemType itemType;
    private MenuStyleType menuStyle;
    private List<CssParameterFloat> cssParametersFloat = new ArrayList<CssParameterFloat>();

}