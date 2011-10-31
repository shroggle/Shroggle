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
package com.shroggle.presentation.account.dashboard;

import com.shroggle.logic.childSites.childSiteSettings.ChildSiteSettingsManager;
import com.shroggle.presentation.Action;
import com.shroggle.util.ServiceLocator;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * @author Balakirev Anatoliy
 */
@UrlBinding("/deleteChildSiteSettings.action")
public class DeleteChildSiteSettingsAction extends Action {

    @DefaultHandler
    public Resolution execute() {
        new ChildSiteSettingsManager(childSiteSettingsId).remove();
        return ServiceLocator.getResolutionCreator().redirectToAction(DashboardAction.class);
    }

    public void setChildSiteSettingsId(Integer childSiteSettingsId) {
        this.childSiteSettingsId = childSiteSettingsId;
    }

    private Integer childSiteSettingsId;
}
