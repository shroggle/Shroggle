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

import net.sf.ehcache.CacheManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author Taras Puchko
 */
public final class CacheCreatorReal implements CacheCreator {

    public CacheCreatorReal() {
    }

    public CacheByAttributes createByAttributesFifo(final String name, final int size) {
        final CacheByAttributesSimple cacheByAttributes = new CacheByAttributesSimple(name, size);
        caches.add(cacheByAttributes);
        return cacheByAttributes;
    }

    public Cache createFifo(final String name, final int size, final int liveInSecond, final int idleInSecond) {
        final net.sf.ehcache.Cache ehCache = new net.sf.ehcache.Cache(
                name, size, false, false, liveInSecond, idleInSecond);
        ehCacheCreator.addCache(ehCache);
        return new CacheEhCache(ehCache);
    }

    public List<Cache> getAll() {
        return Collections.unmodifiableList(caches);
    }

    public void destroy() {
        caches.clear();
        ehCacheCreator.shutdown();
    }

    private final CacheManager ehCacheCreator = new CacheManager();
    private final List<Cache> caches = new ArrayList<Cache>();

}