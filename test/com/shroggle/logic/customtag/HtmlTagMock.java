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
package com.shroggle.logic.customtag;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Artem Stasuk
 */
public class HtmlTagMock implements HtmlTag {

    @Override
    public String getAttribute(final String name) {
        return attributes.get(name);
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public void setAttribute(final String name, final String value) {
        attributes.put(name, value);
    }

    @Override
    public void setBody(final String value) {
        body = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isChanged() {
        return false;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private String body;
    private final Map<String, String> attributes = new HashMap<String, String>();

}
