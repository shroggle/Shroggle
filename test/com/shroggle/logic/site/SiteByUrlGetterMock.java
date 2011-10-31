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

/**
 * @author Artem Stasuk
 */
public final class SiteByUrlGetterMock implements SiteByUrlGetter {

    @Override
    public Site get(final String url) {
        called = true;
        return site;
    }

    public void setSite(final Site site) {
        called = false;
        this.site = site;
    }

    public boolean isCalled() {
        return called;
    }

    private Site site;
    private boolean called;

}
