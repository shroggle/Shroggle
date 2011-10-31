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
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.international.International;
import com.shroggle.util.international.InternationalStorage;
import com.shroggle.util.url.UrlValidator;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;


@RemoteProxy
public class CheckSiteAliasService {

    @RemoteMethod
    public String execute(final String customUrl, final Integer siteId) {
        final International international = internationalStorage.get("domainCheck", Locale.US);

        if (customUrl == null || customUrl.isEmpty()) {
            return international.get("nullOrEmpty");
        }

        if (!UrlValidator.isDomainValid(customUrl)) {
            return international.get("invalid");
        }

        final Site site = siteByUrlGetter.get(customUrl);
        if (site != null && (siteId == null || site.getSiteId() != siteId)) {
            return international.get("notUnique");
        }

        if (ServiceLocator.getConfigStorage().get().isCheckDomainsIp()) {
            try {
                InetAddress address = InetAddress.getByName(customUrl.toLowerCase().startsWith("www.") || UrlValidator.isIpAddress(customUrl) ? customUrl : "www." + customUrl);
                if (address != null && !address.getHostAddress().equals(configStorage.get().getServerIPAddress())) {
                    return international.get("wrongDnsSettings");
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
                return international.get("cantBeResolved");
            }
        }
        return null;
    }

    private final SiteByUrlGetter siteByUrlGetter = ServiceLocator.getSiteByUrlGetter();
    private final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
    private final InternationalStorage internationalStorage = ServiceLocator.getInternationStorage();

}