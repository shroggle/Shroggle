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
package com.shroggle.logic.site;

import com.shroggle.entity.Site;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceListener;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Artem Stasuk
 * @see com.shroggle.logic.site.SiteByUrlGetterReal
 */
public class SiteByUrlGetterCache implements SiteByUrlGetter {

    public SiteByUrlGetterCache(final SiteByUrlGetter siteByUrlGetter) {
        this.siteByUrlGetter = siteByUrlGetter;

        final PersistanceListener listener = new PersistanceListener() {

            @Override
            public void execute(final Class entityClass, final Object entityId) {
                if (entityClass == Site.class) {
                    /**
                     * I known it's not good solve, but i can't find fast and simply way
                     * for remove from concurrency map enter by value.
                     */
                    idByUrls.clear();
                }
            }

        };

        persistance.addRemoveListener(listener);
        persistance.addUpdateListener(listener);
    }

    @Override
    public Site get(final String url) {
        final Integer id = idByUrls.get(url);
        if (id != null) {
            return persistance.getSite(id);
        }

        final Site site = siteByUrlGetter.get(url);
        if (site != null) {
            idByUrls.put(url, site.getId());
        }

        return site;
    }

    private final SiteByUrlGetter siteByUrlGetter;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ConcurrentMap<String, Integer> idByUrls = new ConcurrentHashMap<String, Integer>();

}
