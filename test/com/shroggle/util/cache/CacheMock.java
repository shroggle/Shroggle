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
final class CacheMock<I, V> implements Cache<I, V> {

    public Object getInternal() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void remove(I id) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void add(final I id, final V value) {
        this.id = id;
        this.value = value;
    }

    public V get(final I id) {
        return id.equals(this.id) ? value : null;
    }

    public String getName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public CacheStatistic getStatistic() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void reset() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private I id;
    private V value;

}
