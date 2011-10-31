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

import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.UserException;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.util.ServiceLocator;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * @author Artem Stasuk
 */
@UrlBinding("/site/removeFromNetwork.action")
public class RemoveFromNetworkAction extends Action {

    @DefaultHandler
    public Resolution execute() {
        try {
            usersManager.getLogined().getRight().getSiteRight().getSiteForEdit(siteId).removeFromNetwork();
        } catch (final SiteNotFoundException exception) {
            // None
        } catch (final UserException exception) {
            // None
        }
        return resolutionCreator.redirectToAction(DashboardAction.class);
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    private int siteId;
    private final UsersManager usersManager = new UsersManager();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();

}