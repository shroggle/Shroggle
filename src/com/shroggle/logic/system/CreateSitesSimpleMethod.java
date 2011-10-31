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
package com.shroggle.logic.system;

import com.shroggle.entity.*;
import com.shroggle.logic.site.CreateSiteRequest;
import com.shroggle.logic.site.SiteCreator;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.template.TemplateManager;
import com.shroggle.presentation.site.QuicklyCreatePagesAction;
import com.shroggle.util.ServiceLocator;

import java.util.Collections;

/**
 * @author Artem Stasuk
 */
public class CreateSitesSimpleMethod implements CreateSitesMethod {

    public CreateSitesSimpleMethod(
            final int userId, final String urlPrefix) {
        this.userId = userId;
        this.urlPrefix = urlPrefix;
    }

    @Override
    public void execute(CreateSitesStatus status) {
        final User user = ServiceLocator.getPersistance().getUserById(userId);

        final CreateSiteRequest request = new CreateSiteRequest(
                null, user, urlPrefix + index, null, urlPrefix + index, null, null,
                Collections.EMPTY_LIST, null, SiteType.COMMON, new SEOSettings(), null);
        index++;

        final Site site = SiteCreator.updateSiteOrCreateNew(request);

        final TemplateManager templateManager = new TemplateManager(site.getThemeId().getTemplateDirectory());
        final PageManager pageManager = QuicklyCreatePagesAction.createPageAndAddToSite(
                templateManager, PageType.BLANK, site);
        pageManager.publish();
    }

    @Override
    public String toString() {
        return "Url prefix: " + urlPrefix + ", user id: " + userId;
    }

    private int index;
    private String urlPrefix;
    private int userId;

}