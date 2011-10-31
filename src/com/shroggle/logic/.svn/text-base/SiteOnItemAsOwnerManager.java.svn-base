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
package com.shroggle.logic;

import com.shroggle.entity.Site;
import com.shroggle.entity.SiteOnItemRightType;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.ContextStorage;

/**
 * @author Artem Stasuk
 */
public class SiteOnItemAsOwnerManager implements SiteOnItemManager {

    public SiteOnItemAsOwnerManager(final Site site) {
        this.site = site;
    }

    @Override
    public boolean isEditableOverHere() {
        final ContextStorage contextStorage = ServiceLocator.getContextStorage();
        final Integer userId = contextStorage.get().getUserId();
        if (userId != null) {
            final SiteManager siteManager = new SiteManager(site);
            if (siteManager.getActiveRightForUser(userId) != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isDeletable() {
        return false;
    }

    @Override
    public SiteOnItemRightType getType() {
        return null;
    }

    @Override
    public SiteManager getSite() {
        return new SiteManager(site);
    }

    private final Site site;

}
