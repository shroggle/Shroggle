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
package com.shroggle.presentation.account.dashboard.blueprintsPublishing;

import com.shroggle.entity.BlueprintCategory;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.account.dashboard.DashboardSiteType;
import com.shroggle.presentation.account.dashboard.siteInfo.DashboardSiteInfo;
import com.shroggle.util.ServiceLocator;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.Map;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class ActivateBlueprintService extends AbstractService {

    @RemoteMethod
    public void execute(int siteId, String description, Map<Integer, Integer> pageScreenShots, final BlueprintCategory blueprintCategory) {
        final SiteManager activatedCopy = new SiteManager(siteId).activateBlueprint(description, pageScreenShots, blueprintCategory);

        // Setting copied, activated blueprint selected on the dashboard.
        ServiceLocator.getSessionStorage().setSelectedSiteInfoForDashboard(this.getSession(),
                DashboardSiteInfo.newInstance(activatedCopy.getSite(), DashboardSiteType.ACTIVATED_BLUEPRINT));
    }

}
