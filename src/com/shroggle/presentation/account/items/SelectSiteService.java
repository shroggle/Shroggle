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
package com.shroggle.presentation.account.items;

import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class SelectSiteService extends ServiceWithExecutePage {

    @RemoteMethod
    public String execute() throws IOException, ServletException {
        final UserManager userManager = new UsersManager().getLogined();
        sites = persistance.getSites(userManager.getUserId(), SiteAccessLevel.getUserAccessLevels());

        return executePage("/user/selectSite.jsp");
    }

    public List<Site> getSites() {
        return sites;
    }

    private List<Site> sites;
    private final Persistance persistance = ServiceLocator.getPersistance();

}