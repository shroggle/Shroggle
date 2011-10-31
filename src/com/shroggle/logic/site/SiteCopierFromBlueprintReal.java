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
import com.shroggle.exception.SiteAlreadyConnectedToBlueprintException;
import com.shroggle.util.ServiceLocator;

/**
 * @author Stasuk Artem
 */
public class SiteCopierFromBlueprintReal implements SiteCopierFromBlueprint {

    @Override
    public void execute(final Site blueprint, final Site site, final boolean publish) {
        if (site.getBlueprintParent() != null) {
            throw new SiteAlreadyConnectedToBlueprintException();
        }

        blueprint.addBlueprintChild(site);

        ServiceLocator.getSiteCopierFromActivatedBlueprint().execute(blueprint, site, publish);
    }

}
