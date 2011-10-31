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
package com.shroggle.presentation.site.render;

import com.shroggle.entity.ChildSiteRegistration;
import com.shroggle.entity.Site;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.resource.ResourceGetterUrl;
import com.shroggle.util.resource.provider.ResourceGetterType;

/**
 * @author Artem Stasuk
 */
class RenderFooterCustom {

    public RenderFooterCustom(final Site site, final ChildSiteRegistration childSiteRegistration) {
        this.childSiteRegistration = childSiteRegistration;
        this.site = site;
    }

    public String getHref() {
        String footerHref = childSiteRegistration.getFooterUrl();
        if (footerHref != null)
            return "http://" + footerHref;
        return new SiteManager(childSiteRegistration.getParentSiteId()).getPublicUrl();
//            return "http://"+ServiceLocator.getConfigStorage().get().getApplicationUrl();


    }

    public String getSrc() {
        String footerSrc = RenderPatternFooter.DEFAULT_SRC;
        if (childSiteRegistration.getFooterImageId() != null) {
            footerSrc = resourceGetterUrl.get(ResourceGetterType.FOOTER_IMAGE,
                    childSiteRegistration.getFooterImageId(), 0, 0, 0, false);
        }
        return footerSrc;
    }

    public String getText() {
        String footerText = childSiteRegistration.getFooterText();
        if (footerText == null) {
            footerText = childSiteRegistration.getName();
        }
        return footerText;
    }

    private final Site site;
    private final ChildSiteRegistration childSiteRegistration;
    private final ResourceGetterUrl resourceGetterUrl = ServiceLocator.getResourceGetter();

}