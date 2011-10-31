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

/**
 * @author Artem Stasuk
 */
public class PageResourcesItem {

    @XmlAttribute
    public String getPath() {
        return path;
    }

    @XmlAttribute
    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    private String type;
    private String path;

}
