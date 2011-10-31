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
public class SiteCopierFromActivatedBlueprintMock implements SiteCopierFromActivatedBlueprint {

    @Override
    public void execute(final Site blueprint, final Site site, boolean publish) {
        this.site = site;
        this.blueprint = blueprint;
        called = true;
    }

    public Site getBlueprint() {
        return blueprint;
    }

    public void setBlueprint(Site blueprint) {
        this.blueprint = blueprint;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public boolean isCalled() {
        return called;
    }

    private Site site;
    private boolean called;
    private Site blueprint;

}
