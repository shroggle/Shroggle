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

import com.shroggle.entity.Group;
import com.shroggle.entity.Site;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class ShowManageGroupsWindowService extends AbstractService {

    @RemoteMethod
    public String execute(final int siteId, final List<Integer> checkedVisitorsId) throws ServletException, IOException {
        new UsersManager().getLogined();

        this.checkedVisitorsId.addAll(checkedVisitorsId);
        final Site site = persistance.getSite(siteId);

        if (site == null) {
            throw new SiteNotFoundException();
        }
        groups.addAll(site.getOwnGroups());

        getContext().getHttpServletRequest().setAttribute("service", this);
        return getContext().forwardToString("/account/groups/manageGroups.jsp");
    }

    public List<Group> getGroups() {
        return groups;
    }

    public String getCheckedVisitorsId() {
        return checkedVisitorsId.isEmpty() ? "" : checkedVisitorsId.toString().replace("[", "").replace("]", "");
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final List<Group> groups = new ArrayList<Group>();
    private final List<Integer> checkedVisitorsId = new ArrayList<Integer>();
}
