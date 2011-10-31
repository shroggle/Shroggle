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
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.presentation.Action;
import com.shroggle.util.ServiceLocator;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * @author dmitry.solomadin
 */
@UrlBinding("/site/robotsTxtGenerator.action")
public class RobotsTxtGeneratorAction extends Action {

    public Resolution generate() {
        final Site site = ServiceLocator.getPersistance().getSite(siteId);

        if (site == null) {
            throw new SiteNotFoundException("Cannot find site by Id=" + siteId);
        }

        final String robotsTxt = "<pre style='word-wrap: break-word; white-space: pre-wrap;'>" +
                "User-agent: *\n" +
                "Disallow: \n" +
                "Sitemap: " + new SiteManager(site).getPublicUrl() + "/sitemap.xml" +
                "</pre>";

        return new StreamingResolution("text/html", robotsTxt);
    }

    public int siteId;

}
