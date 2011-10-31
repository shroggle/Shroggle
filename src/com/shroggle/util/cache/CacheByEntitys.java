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

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.PersistanceListener;

import java.util.List;

/**
 * @author Artem Stasuk
 */
public class CacheByEntitys<V> implements Cache<Object, List<V>> {

    public CacheByEntitys(final CacheByAttributes<V> cacheByAttributes) {
        this.cacheByAttributes = cacheByAttributes;
        final PersistanceListener listener = new PersistanceListener() {

            public void execute(final Class entityClass, final Object entityId) {
                cacheByAttributes.remove(new CacheByEntitysId(entityClass, entityId));
            }

        };
        ServiceLocator.getPersistance().addRemoveListener(listener);
        ServiceLocator.getPersistance().addUpdateListener(listener);
    }

    public Object getInternal() {
        return this;
    }

    public void remove(final Object id) {
        cacheByAttributes.remove(id);
    }

    public void add(final Object ids, final List<V> value) {
        cacheByAttributes.add(ids, value);
    }

    public List<V> get(final Object id) {
        return cacheByAttributes.get(id);
    }

    public String getName() {
        return "cacheByEntitys on " + cacheByAttributes.getName();
    }

    public CacheStatistic getStatistic() {
        return cacheByAttributes.getStatistic();
    }

    public void reset() {
        cacheByAttributes.reset();
    }

    public void destroy() {
        cacheByAttributes.destroy();
    }

    private final CacheByAttributes<V> cacheByAttributes;

}
