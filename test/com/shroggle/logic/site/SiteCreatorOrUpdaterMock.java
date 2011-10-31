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
public class SiteCreatorOrUpdaterMock implements SiteCreatorOrUpdater {

    @Override
    public Site execute(final CreateSiteRequest request) {
        this.request = request;
        site.setType(request.getSiteType());
        return site;
    }

    public CreateSiteRequest getRequest() {
        return request;
    }

    public void setSite(final Site site) {
        this.site = site;
    }

    public Site getSite() {
        return site;
    }

    private CreateSiteRequest request;
    private Site site;

}
