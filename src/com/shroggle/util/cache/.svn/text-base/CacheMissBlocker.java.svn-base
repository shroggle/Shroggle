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
public class CacheMissBlocker<I, V> implements Cache<I, V> {

    public CacheMissBlocker(
            final Cache<I, V> cache, final CacheMissBlockerListener<I, V> listener) {
        this.cache = cache;
        this.listener = listener;
    }

    public Object getInternal() {
        return cache.getInternal();
    }

    public void remove(final I id) {
        cache.remove(id);
    }

    /**
     * Don't use this method. Becose if value not found in cache,
     * cache call or listener synchronize on cache item id
     *
     * @param id    - cache item id
     * @param value - cache item value
     */
    @Deprecated
    public void add(final I id, final V value) {
        cache.add(id, value);
    }

    public V get(final I id) {
        V value = cache.get(id);
        synchronized (this) {
            if (value == null) {
                value = listener.execute(id);
                cache.add(id, value);
            }
        }
        return value;
    }

    public String getName() {
        return cache.getName();
    }

    public CacheStatistic getStatistic() {
        return cache.getStatistic();
    }

    public void reset() {
        cache.reset();
    }

    public void destroy() {
        cache.destroy();
    }

    private final Cache<I, V> cache;
    private final CacheMissBlockerListener<I, V> listener;

}
