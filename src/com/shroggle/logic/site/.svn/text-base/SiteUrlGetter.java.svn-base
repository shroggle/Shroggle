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
import com.shroggle.entity.SiteType;
import com.shroggle.entity.WorkChildSiteRegistration;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

/**
 * @author Artem Stasuk
 */
public class SiteUrlGetter {

    public String get(final Site site) {
        String url = null;

        if (site != null) {
            if (site.getType() == SiteType.BLUEPRINT) {
                final String userSitesUrl = ServiceLocator.getConfigStorage().get().getUserSitesUrl();
                url = "&lt;site-domain&gt;" + "." + userSitesUrl;
            }

            if (url == null && site.getCustomUrl() != null) {
                url = site.getCustomUrl();
            }

            if (url == null && site.getBrandedSubDomain() != null && site.getChildSiteSettings() != null) {
                final Persistance persistance = ServiceLocator.getPersistance();
                final int childSiteRegistrationId = site.getChildSiteSettings().getChildSiteRegistration().getId();
                final WorkChildSiteRegistration workChildSiteRegistration = persistance.getWorkItem(childSiteRegistrationId);
                if (workChildSiteRegistration != null) {
                    url = site.getBrandedSubDomain() + "." + workChildSiteRegistration.getBrandedUrl();
                }
            }

            if (url == null) {
                final String userSitesUrl = ServiceLocator.getConfigStorage().get().getUserSitesUrl();
                url = site.getSubDomain() + "." + userSitesUrl;
            }
        }

        if (url == null) {
            url = "";
        } else {
            url = "http://" + url;
        }

        return url;
    }


}
