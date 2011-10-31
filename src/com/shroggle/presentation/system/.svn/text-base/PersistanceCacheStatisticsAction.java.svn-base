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
package com.shroggle.presentation.system;

import com.shroggle.presentation.Action;
import net.sf.ehcache.CacheManager;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * @author Artem Stasuk
 */
@UrlBinding("/system/persistanceCacheStatistics.action")
public class PersistanceCacheStatisticsAction extends Action {

    @DefaultHandler
    public Resolution execute() {
//        cacheManager = CachePersistanceCreatorEhCache.getInternal();
        return new ForwardResolution("/system/persistanceCacheStatistics.jsp");
    }

    public CacheManager getInternal() {
        return cacheManager;
    }

    private CacheManager cacheManager;

}
