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
package com.shroggle.util.html.optimization;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * @author Artem Stasuk
 */
public class PageResourcesPart {

    @XmlAttribute
    public String getName() {
        return name;
    }

    @XmlElement(name = "item")
    public List<PageResourcesItem> getItems() {
        return items;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setItems(final List<PageResourcesItem> items) {
        this.items = items;
    }

    private String name;
    private List<PageResourcesItem> items;

}
