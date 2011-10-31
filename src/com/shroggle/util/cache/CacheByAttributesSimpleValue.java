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
package com.shroggle.util.cache;

/**
 * @author Artem Stasuk
 */
final class CacheByAttributesSimpleValue<V> {

    public CacheByAttributesSimpleValue(final V value) {
        this.value = value;
    }

    public V getValue() {
        return value;
    }

    public long getUsed() {
        return used;
    }

    public void setUsed(final long used) {
        this.used = used;
    }

    private final V value;
    private long used;

}
