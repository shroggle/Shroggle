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

import com.shroggle.entity.Site;
import com.shroggle.logic.site.SiteByUrlGetter;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.international.International;
import com.shroggle.util.international.InternationalStorage;
import com.shroggle.util.url.UrlValidator;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.Locale;

@RemoteProxy
public class CheckSiteUrlPrefixService {

    @RemoteMethod
    public String execute(final String subDomain, final Integer siteId) {
        final International international = internationalStorage.get("domainCheck", Locale.US);

        if (subDomain == null || subDomain.isEmpty()) {
            return international.get("nullOrEmpty");
        }

        if (!UrlValidator.isSystemSubDomainValid(subDomain)) {
            return international.get("invalid");
        }

        final String lowerSubDomain = subDomain.toLowerCase();
        final Config config = configStorage.get();
        for (String usedName : config.getBlockedSubDomain()) {
            if (lowerSubDomain.equalsIgnoreCase(usedName)) {
                return international.get("notUnique");
            }
        }

        final Site site = siteByUrlGetter.get(lowerSubDomain + "." + config.getUserSitesUrl());
        if (site != null && (siteId == null || site.getSiteId() != siteId)) {
            return international.get("notUnique");
        }

        return null;
    }

    private final SiteByUrlGetter siteByUrlGetter = ServiceLocator.getSiteByUrlGetter();
    private final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
    private final InternationalStorage internationalStorage = ServiceLocator.getInternationStorage();

}