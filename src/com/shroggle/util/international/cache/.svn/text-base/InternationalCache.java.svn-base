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

package com.shroggle.util.international.cache;

import com.shroggle.util.international.International;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Stasuk Artem
 */
class InternationalCache implements International {

    public InternationalCache(final International international) {
        this.international = international;
    }

    public String get(final String name, final Object... parameters) {
        String value = values.get(name);
        if (value == null) {
            value = international.get(name, parameters);
            values.putIfAbsent(name, value);
        }

        return value;
    }

    private final International international;
    private final ConcurrentMap<String, String> values = new ConcurrentHashMap<String, String>();

}