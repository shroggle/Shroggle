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

package com.shroggle.presentation.site.cssParameter;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

/**
 * @author Stasuk Artem
 */
@DataTransferObject
public class CssParameter implements Comparable {

    public static final String NONE_SELECTOR = "none";

    @RemoteProperty
    private String name;
    @RemoteProperty
    private String description;
    @RemoteProperty
    private String selector;
    @RemoteProperty
    private String value;
    @RemoteProperty
    private String type;

    public String getSelector() {
        return selector;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int compareTo(Object o) {
        CssParameter parameter = (CssParameter) o;
        if (getDescription() == null && parameter.getDescription() == null)
            return 0;
        if (getDescription() == null)
            return -1;
        if (parameter.getDescription() == null)
            return 1;
        return getDescription().compareTo(parameter.getDescription());
    }
}
