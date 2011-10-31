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
package com.shroggle.logic.site.page;

import com.shroggle.entity.Page;
import com.shroggle.logic.DefaultNameUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.HashSet;
import java.util.Set;

/**
 * @author dmitry.solomadin
 */
public class PageDefaultNameManager {

    public String getNextName(final int siteId, final String customPattern) {
        final Set<String> namesInUse = new HashSet<String>();
        for (final Page page : persistance.getSite(siteId).getPages()) {
            namesInUse.add(new PageManager(page).getName());
        }

        return new DefaultNameUtil().getNext(customPattern != null ? customPattern : "Page", true, namesInUse);
    }

    public String getNextUrl(final Integer siteId) {
        if (siteId == null) {
            return "";
        }
        final Set<String> urlsInUse = new HashSet<String>();
        for (final Page page : persistance.getSite(siteId).getPages()) {
            urlsInUse.add(new PageManager(page).getRawUrl());
        }

        return new DefaultNameUtil().getNext("PageUrl", true, urlsInUse);
    }

    private Persistance persistance = ServiceLocator.getPersistance();

}
