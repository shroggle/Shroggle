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
package com.shroggle.presentation.manageRegistrants;

import com.shroggle.logic.manageRegistrants.ManageRegistrantsProvider;
import com.shroggle.logic.paginator.Paginator;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.ServiceWithExecutePage;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class ShowManageRegistrantsTableService extends ServiceWithExecutePage {

    @RemoteMethod
    public String execute(final ShowManageRegistrantsRequest request)
            throws IOException, ServletException {
        final UserManager userManager = new UsersManager().getLogined();
        final SiteManager siteManager = userManager.getRight().getSiteRight().getSiteForEdit(request.getSiteId());

        final Paginator paginator = new ManageRegistrantsProvider().execute(request, request.getPageNumber());

        getContext().getHttpServletRequest().setAttribute("paginator", paginator);
        getContext().getHttpServletRequest().setAttribute("manageRegistrantsSiteId", request.getSiteId());
        getContext().getHttpServletRequest().setAttribute("manageRegistrantsSortType", request.getSortType());
        getContext().getHttpServletRequest().setAttribute("manageRegistrantsDesc", request.isDesc());
        getContext().getHttpServletRequest().setAttribute("availableGroups", siteManager.getSite().getOwnGroups());

        return executePage("/account/manageRegistrants/manageRegistrantsList.jsp");
    }

}
