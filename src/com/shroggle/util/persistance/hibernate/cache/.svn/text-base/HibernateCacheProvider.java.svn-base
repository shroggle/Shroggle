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

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.cache.CacheCreatorByPolicy;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.hibernate.EhCache;
import org.hibernate.cache.*;

import java.util.Properties;

/**
 * @author Taras Puchko
 */
public final class HibernateCacheProvider implements CacheProvider {

    public void start(final Properties properties) {
    }

    public Cache buildCache(
            final String regionName, final Properties properties) throws CacheException {
        final CacheCreatorByPolicy creatorByPolicy = ServiceLocator.getCacheCreatorByPolicy();
        if (creatorByPolicy == null) {
            return new HibernateCacheEmpty(regionName);
        }

        final Ehcache ehcache = (Ehcache) creatorByPolicy.createFifo(regionName).getInternal();
        if (ehcache == null) {
            return new HibernateCacheEmpty(regionName);
        }

        Cache result = new EhCache(ehcache);
        if (regionName.equals(UpdateTimestampsCache.class.getName())) {
            result = new HibernateCacheWrapper(result);
        }
        return result;
    }

    public long nextTimestamp() {
        return Timestamper.next();
    }

    public void stop() {
    }

    public boolean isMinimalPutsEnabledByDefault() {
        return false;
    }

}

