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
package com.shroggle.logic.blueprintsPublishing;

import com.shroggle.entity.BlueprintCategory;
import com.shroggle.entity.Site;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.ServiceLocator;

import java.util.Iterator;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
public class ShowPublishBlueprintModel {

    public ShowPublishBlueprintModel(final Integer siteId, final boolean activationMode) {
        new UsersManager().getLogined();
        Site site = ServiceLocator.getPersistance().getSite(siteId);
        if (site == null) {
            throw new IllegalArgumentException("Unable to create ShowPublishBlueprintModel without site!");
        }
        siteManager = new SiteManager(site);
        this.activationMode = activationMode;
    }

    public String getDescription() {
        return siteManager.getPublicBlueprintsSettings().getDescription();
    }

    public List<PageManager> getPages() {
        if (workPages != null) {
            return workPages;
        }
        workPages = siteManager.getPages();
        final Iterator<PageManager> iterator = workPages.iterator();
        while (iterator.hasNext()) {// We are copying only work pages from blueprint so we`re showing only work pages here. Tolik
            if (iterator.next().getWorkPageSettings() == null) {
                iterator.remove();
            }
        }
        return workPages;
    }

    public int getSiteId() {
        return siteManager.getSiteId();
    }

    public BlueprintCategory getBlueprintCategory() {
        return siteManager.getPublicBlueprintsSettings().getBlueprintCategory();
    }

    public boolean isActivationMode() {
        return activationMode;
    }

    private List<PageManager> workPages;
    private final SiteManager siteManager;
    private final boolean activationMode;
}
