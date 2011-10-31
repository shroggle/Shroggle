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
package com.shroggle.presentation.account.items;

import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represent one site what used some item.
 *
 * @author Artem Stasuk
 * @see com.shroggle.presentation.account.items.ShowUserItem
 */
public final class ShowUserItemSite implements Comparable {

    static List<ShowUserItemSite> get(final DraftItem siteItem, final Site ownerSite) {
        final List<ShowUserItemSite> sites = new ArrayList<ShowUserItemSite>();
        if (ownerSite != null) {
            sites.add(new ShowUserItemSite(ownerSite));
        }
        final Persistance persistance = ServiceLocator.getPersistance();
        for (final SiteOnItem siteOnItemRight : persistance.getSiteOnItemsByItem(siteItem.getId())) {
            sites.add(new ShowUserItemSite(siteOnItemRight));
        }
        Collections.sort(sites);
        return Collections.unmodifiableList(sites);
    }

    ShowUserItemSite(final SiteOnItem right) {
        if (right == null) {
            throw new UnsupportedOperationException(
                    "Can't create without right!");
        }

        name = right.getSite().getTitle();
        siteId = right.getSite().getSiteId();
        final String userSitesUrl = ServiceLocator.getConfigStorage().get().getUserSitesUrl();
        url = right.getSite().getSubDomain() + "." + userSitesUrl;
        rightType = right.getType();
    }

    ShowUserItemSite(final Site site) {
        if (site == null) {
            throw new UnsupportedOperationException(
                    "Can't create without site!");
        }

        name = site.getTitle();
        siteId = site.getSiteId();
        final String userSitesUrl = ServiceLocator.getConfigStorage().get().getUserSitesUrl();
        url = site.getSubDomain() + "." + userSitesUrl;
        rightType = null;
    }

    public String getName() {
        return name;
    }

    public int getSiteId() {
        return siteId;
    }

    public String getUrl() {
        return url;
    }

    public SiteOnItemRightType getRightType() {
        return rightType;
    }

    public int compareTo(final Object object) {
        final ShowUserItemSite show = (ShowUserItemSite) object;
        if (rightType != null) {
            if (show.rightType != null) {
                return name.compareTo(show.name);
            } else {
                return 1;
            }
        } else {
            if (show.rightType != null) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    private final String url;
    private final String name;
    private final int siteId;

    /**
     * If this field has null then this site owner for item.
     * In other cases it has access for item over siteOnItemRight.
     *
     * @link com.shroggle.entity.SiteOnItemRight
     */
    private final SiteOnItemRightType rightType;

}
