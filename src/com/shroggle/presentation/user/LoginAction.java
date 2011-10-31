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
package com.shroggle.presentation.user;

import com.shroggle.exception.UserException;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.ResolutionParameter;
import com.shroggle.presentation.site.SiteEditPageAction;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.Site;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.List;

/**
 * @author Artem Stasuk
 */
@UrlBinding("/user/login.action")
public class LoginAction extends Action {

    @DefaultHandler
    public Resolution execute() {
        final UserManager userManager;
        try {
            userManager = usersManager.login(email, password, null);
        } catch (final UserException exception) {
            return resolutionCreator.loginInUser(this);
        }

        final List<Site> sites = persistance.getSites(
                userManager.getUserId(), SiteAccessLevel.getUserAccessLevels());
        if (sites.size() == 1) {
            return resolutionCreator.redirectToAction(
                    SiteEditPageAction.class, new ResolutionParameter("siteId", sites.get(0).getSiteId()));
        }
        return resolutionCreator.redirectToAction(DashboardAction.class);
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    private String email;
    private String password;
    private final UsersManager usersManager = new UsersManager();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();

}
