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
public class SiteCopierFromBlueprintMock implements SiteCopierFromBlueprint {

    @Override
    public void execute(final Site blueprint, final Site site, final boolean publish) {
        this.blueprint = blueprint;
        this.site = site;
        this.publish = publish;
        this.called = true;
    }

    public boolean isCalled() {
        return called;
    }

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Site getBlueprint() {
        return blueprint;
    }

    public void setBlueprint(Site blueprint) {
        this.blueprint = blueprint;
    }

    private Site blueprint;
    private Site site;
    private boolean publish;
    private boolean called;

}
