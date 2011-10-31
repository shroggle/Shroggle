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

package com.shroggle.presentation.site.payment;

import com.shroggle.entity.*;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.util.ServiceLocator;

import java.util.List;

/**
 * @author Balakirev Anatoliy
 */

public final class UpdatePaymentInfoData {

    public UpdatePaymentInfoData(final List<Site> availableSites, final Integer selectedSiteId, final User loginedUser) {
        this.availableSites = availableSites;
        if (selectedSiteId != null) {
            Site site = ServiceLocator.getPersistance().getSite(selectedSiteId);
            this.availableSites.remove(site);
            this.availableSites.add(0, site);
        }
        selectedSite = this.availableSites != null && !this.availableSites.isEmpty() ? this.availableSites.get(0) : null;
        final UserOnSiteRight right = new UserRightManager(loginedUser).toSite(selectedSite);
        hasAdminAccessToThisSite = right != null && right.getSiteAccessType() == SiteAccessLevel.ADMINISTRATOR;    
        creditCards = CreditCardDataManager.createCreditCardData(loginedUser, selectedSite);
    }

    private final List<Site> availableSites;

    private final boolean hasAdminAccessToThisSite;

    private final List<CreditCardData> creditCards;

    private final Site selectedSite;

    public SiteStatus getSelectedSiteStatus() {
        return selectedSite != null ? new SiteManager(selectedSite).getSiteStatus() : null;
    }

    public Site getSelectedSite() {
        return selectedSite;
    }

    public List<CreditCardData> getCreditCards() {
        return creditCards;
    }

    public boolean isHasAdminAccessToThisSite() {
        return hasAdminAccessToThisSite;
    }

    public List<Site> getAvailableSites() {
        return availableSites;
    }
}