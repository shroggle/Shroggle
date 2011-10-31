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

import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.StartAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/account/logoutFromUser.action")
public class LogoutFromUserAction extends Action {

    public Resolution execute() {
        UsersManager.logout();
        return resolutionCreator.redirectToAction(StartAction.class);
    }

    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();

}