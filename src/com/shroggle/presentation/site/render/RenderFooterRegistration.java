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
import com.shroggle.entity.ChildSiteSettings;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.logic.site.item.ItemManager;

/**
 * @author Artem Stasuk
 */
class RenderFooterRegistration {

    public RenderFooterRegistration(final Site site, final SiteShowOption siteShowOption) {
        final ChildSiteSettings settings = site.getChildSiteSettings();
        if (settings != null && settings.getChildSiteRegistration() != null) {
            final ItemManager itemManager = new ItemManager(settings.getChildSiteRegistration());
            registration = (ChildSiteRegistration) itemManager.getItem(siteShowOption);
        } else {
            registration = null;
        }
    }

    public ChildSiteRegistration get() {
        return registration;
    }

    private final ChildSiteRegistration registration;

}
