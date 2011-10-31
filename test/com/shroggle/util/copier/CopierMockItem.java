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
package com.shroggle.util.copier;

import java.util.Arrays;
import java.util.List;

/**
 * @author Artem Stasuk
 */
public class CopierMockItem {

    public CopierMockItem(Object source, Object destination, String[] exclude) {
        this.source = source;
        this.destination = destination;
        this.exclude = exclude;
    }

    public Object getSource() {
        return source;
    }

    public Object getDestination() {
        return destination;
    }

    public String[] getExclude() {
        return exclude;
    }

    public List<String> getExcludeAsList() {
        return Arrays.asList(exclude);
    }

    private final Object source;
    private final Object destination;
    private final String[] exclude;

}
