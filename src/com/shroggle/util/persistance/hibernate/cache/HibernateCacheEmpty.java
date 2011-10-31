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
import org.hibernate.cache.Timestamper;

import java.util.Collections;
import java.util.Map;

/**
 * @author Taras Puchko
 */
public class HibernateCacheEmpty implements Cache {

    public HibernateCacheEmpty(final String regionName) {
        this.regionName = regionName;
    }

    public Object read(Object key) throws CacheException {
        return null;
    }

    public Object get(Object key) throws CacheException {
        return null;
    }

    public void put(Object key, Object value) throws CacheException {
        // do nothing
    }

    public void update(Object key, Object value) throws CacheException {
        // do nothing
    }

    public void remove(Object key) throws CacheException {
        // do nothing
    }

    public void clear() throws CacheException {
        // do nothing
    }

    public void destroy() throws CacheException {
        // do nothing
    }

    public void lock(Object key) throws CacheException {
        // do nothing
    }

    public void unlock(Object key) throws CacheException {
        // do nothing
    }

    public long nextTimestamp() {
        return Timestamper.next();
    }

    public int getTimeout() {
        return Timestamper.ONE_MS * 60000;
    }

    public String getRegionName() {
        return regionName;
    }

    public long getSizeInMemory() {
        return 0;
    }

    public long getElementCountInMemory() {
        return 0;
    }

    public long getElementCountOnDisk() {
        return 0;
    }

    public Map toMap() {
        return Collections.emptyMap();
    }

    private final String regionName;

}

