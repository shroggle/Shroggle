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

import com.shroggle.entity.*;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.ContextStorage;

/**
 * @author Artem Stasuk
 */
public class SiteOnItemAsRightManager extends SiteOnItemAsOwnerManager {

    public SiteOnItemAsRightManager(final SiteOnItem siteOnItemRight) {
        super(siteOnItemRight.getSite());
        this.siteOnItemRight = siteOnItemRight;
    }

    @Override
    public boolean isEditableOverHere() {
        return siteOnItemRight.getType() == SiteOnItemRightType.EDIT
                && siteOnItemRight.getAcceptDate() != null
                && super.isEditableOverHere();
    }

    @Override
    public SiteOnItemRightType getType() {
        return siteOnItemRight.getType();
    }

    @Override
    public boolean isDeletable() {
        final ContextStorage contextStorage = ServiceLocator.getContextStorage();
        final Integer userId = contextStorage.get().getUserId();
        if (userId != null) {
            final DraftItem siteItem = siteOnItemRight.getItem();
            if (siteItem.getSiteId() > 0) {
                final Site ownerSite = ServiceLocator.getPersistance().getSite(siteItem.getSiteId());
                if (isSiteHasActiveAdministratorRight(ownerSite)) {
                    return true;
                }
            }

            if (isSiteHasActiveAdministratorRight(siteOnItemRight.getSite())) {
                return true;
            }
        }
        return false;
    }

    private boolean isSiteHasActiveAdministratorRight(final Site site) {
        final Integer userId = ServiceLocator.getContextStorage().get().getUserId();
        if (site != null) {
            final UserOnSiteRight userOnSiteRight = new SiteManager(site).getActiveRightForUser(userId);
            if (userOnSiteRight != null && userOnSiteRight.getSiteAccessType() == SiteAccessLevel.ADMINISTRATOR) {
                return true;
            }
        }
        return false;
    }

    private final SiteOnItem siteOnItemRight;

}
