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
import com.shroggle.logic.site.SiteManager;
import com.shroggle.presentation.site.CreateSiteKeywordsGroup;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.Collections;

/**
 * @author Artem Stasuk
 */
public class CreateSitesBlueprintChildMethod implements CreateSitesMethod {

    public CreateSitesBlueprintChildMethod(final int blueprintId, final String urlPrefix) {
        this.blueprintId = blueprintId;
        this.urlPrefix = urlPrefix;
    }

    @Override
    public void execute(CreateSitesStatus status) {
        final Site blueprint = persistance.getSite(blueprintId);
        if (blueprint == null) {
            info = "Can't find blueprint.";
            throw new UnsupportedOperationException(info);
        }

        if (blueprint.getUserOnSiteRights().isEmpty()) {
            info = "Can't find any user for blueprint.";
            throw new UnsupportedOperationException(info);
        }

        final User user = blueprint.getUserOnSiteRights().get(0).getId().getUser();

        final CreateSiteRequest request = new CreateSiteRequest(
                null, user, urlPrefix, null, urlPrefix, null, null,
                Collections.<CreateSiteKeywordsGroup>emptyList(), null, SiteType.COMMON, new SEOSettings(), null);
        final Site child = SiteCreator.updateSiteOrCreateNew(request);

        child.setThemeId(blueprint.getThemeId());

        new SiteManager(child).connectToBlueprint(new SiteManager(blueprint), true);
    }

    @Override
    public String toString() {
        return "url prefix: " + urlPrefix + ", blueprint site id: " + blueprintId;
    }

    private String info;
    private String urlPrefix;
    private int blueprintId;
    private final Persistance persistance = ServiceLocator.getPersistance();

}