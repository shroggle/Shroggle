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
package com.shroggle.util.persistance.hibernate.cache;

import org.hibernate.cache.Cache;
import org.hibernate.cache.CacheException;

import java.util.Map;

/**
 * Save and return current time when update timestamp was lost from the cache.
 *
 * @author Taras Puchko
 */
public class HibernateCacheWrapper implements Cache {

    private Cache cache;

    public HibernateCacheWrapper(Cache cache) {
        this.cache = cache;
    }

    public Object read(Object key) throws CacheException {
        Object result = cache.read(key);
        if (result == null) {
            update(key, result = nextTimestamp());
        }
        return result;
    }

    public Object get(Object key) throws CacheException {
        Object result = cache.get(key);
        if (result == null) {
            put(key, result = nextTimestamp());
        }
        return result;
    }

    public void put(Object key, Object value) throws CacheException {
        cache.put(key, value);
    }

    public void update(Object key, Object value) throws CacheException {
        cache.update(key, value);
    }

    public void remove(Object key) throws CacheException {
        cache.remove(key);
    }

    public void clear() throws CacheException {
        cache.clear();
    }

    public void destroy() throws CacheException {
        cache.destroy();
    }

    public void lock(Object key) throws CacheException {
        cache.lock(key);
    }

    public void unlock(Object key) throws CacheException {
        cache.unlock(key);
    }

    public long nextTimestamp() {
        return cache.nextTimestamp();
    }

    public int getTimeout() {
        return cache.getTimeout();
    }

    public String getRegionName() {
        return cache.getRegionName();
    }

    public long getSizeInMemory() {
        return cache.getSizeInMemory();
    }

    public long getElementCountInMemory() {
        return cache.getElementCountInMemory();
    }

    public long getElementCountOnDisk() {
        return cache.getElementCountOnDisk();
    }

    public Map toMap() {
        return cache.toMap();
    }

}

