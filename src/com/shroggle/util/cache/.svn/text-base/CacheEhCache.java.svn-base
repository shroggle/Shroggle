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

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;


/**
 * @author Taras Puchko
 */
final class CacheEhCache<I, V> implements Cache<I, V> {

    public CacheEhCache(final Ehcache ehCache) {
        this.ehCache = ehCache;
    }

    public Object getInternal() {
        return ehCache;
    }

    public void remove(final I id) {
        ehCache.remove(id);
    }

    public void add(final I id, final V value) {
        ehCache.put(new Element(id, value));
    }

    @SuppressWarnings({"unchecked"})
    public V get(final I id) {
        return (V) ehCache.get(id).getValue();
    }

    public String getName() {
        return ehCache.getName();
    }

    public CacheStatistic getStatistic() {
        return null;
    }

    public void reset() {
        ehCache.dispose();
    }

    public void destroy() {
        ehCache.dispose();
    }

    private final Ehcache ehCache;

}