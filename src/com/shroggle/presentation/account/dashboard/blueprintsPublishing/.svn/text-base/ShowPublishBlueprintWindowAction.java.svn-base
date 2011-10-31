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

import com.shroggle.logic.blueprintsPublishing.ShowPublishBlueprintModel;
import com.shroggle.presentation.Action;
import com.shroggle.util.ServiceLocator;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * @author Balakirev Anatoliy
 */
@UrlBinding("/showPublishBlueprintWindow.action")
public class ShowPublishBlueprintWindowAction extends Action {

    @DefaultHandler
    public Resolution execute() throws Exception {
        getHttpServletRequest().setAttribute("model", new ShowPublishBlueprintModel(siteId, activationMode));
        return ServiceLocator.getResolutionCreator().forwardToUrl("/account/dashboard/blueprintsPublishing/publishBlueprint.jsp");
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public void setActivationMode(boolean activationMode) {
        this.activationMode = activationMode;
    }

    private Integer siteId;

    private boolean activationMode;
}
