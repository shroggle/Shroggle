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

package com.shroggle.presentation.site;

import com.shroggle.entity.ChildSiteRegistration;
import com.shroggle.entity.DraftChildSiteRegistration;
import com.shroggle.entity.Site;
import com.shroggle.entity.WorkChildSiteRegistration;
import com.shroggle.logic.site.SiteByUrlGetter;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.international.InternationalStorage;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.url.UrlValidator;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.Locale;

@RemoteProxy
public class CheckBrandedSubDomainService {

    @RemoteMethod
    public String execute(final String brandedSubDomain, final int childSiteRegistrationId, final Integer siteId) {
        final International international = internationalStorage.get("domainCheck", Locale.US);

        if (brandedSubDomain == null || brandedSubDomain.isEmpty()) {
            return international.get("nullOrEmpty");
        }

        if (!UrlValidator.isSystemSubDomainValid(brandedSubDomain)) {
            return international.get("invalid");
        }

        ChildSiteRegistration childSiteRegistration = (WorkChildSiteRegistration)
                persistance.getWorkItem(childSiteRegistrationId);

        if (childSiteRegistration == null) {
            childSiteRegistration = (DraftChildSiteRegistration)
                    persistance.getDraftItem(childSiteRegistrationId);
        }

        if (childSiteRegistration != null) {
            final Site site = siteByUrlGetter.get(brandedSubDomain + "." + childSiteRegistration.getBrandedUrl());
            if (site != null && (siteId == null || site.getSiteId() != siteId)) {
                return international.get("notUnique");
            }
        }

        return null;
    }

    private final SiteByUrlGetter siteByUrlGetter = ServiceLocator.getSiteByUrlGetter();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final InternationalStorage internationalStorage = ServiceLocator.getInternationStorage();

}