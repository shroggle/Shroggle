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
package com.shroggle.presentation.groups;

import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.entity.Site;
import com.shroggle.entity.Group;
import com.shroggle.exception.SiteNotFoundException;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class ShowGroupsService extends ServiceWithExecutePage {

    public String execute(final int siteId) throws Exception {
        new UsersManager().getLogined();
        final Site site = persistance.getSite(siteId);
        if (site == null) {
            throw new SiteNotFoundException();
        }
        final List<GroupData> groupsData = new ArrayList<GroupData>();
        for (Group group : site.getOwnGroups()) {
            final GroupData groupData = new GroupData(group.getGroupId(), group.getName(), persistance.getUsersCountWithAccessToGroup(group.getGroupId()));
            groupsData.add(groupData);
        }
        if (groupsData.isEmpty()) {
            ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
                public void run() {
                    final Group group = new Group();
                    group.setName(international.get("invited"));
                    group.setOwner(site);
                    persistance.putGroup(group);
                    final GroupData groupData = new GroupData(group.getGroupId(), group.getName(), persistance.getUsersCountWithAccessToGroup(group.getGroupId()));
                    groupsData.add(groupData);
                }
            });
        }
        getContext().getHttpServletRequest().setAttribute("groupsData", groupsData);
        getContext().getHttpServletRequest().setAttribute("siteName", site.getTitle());
        getContext().getHttpServletRequest().setAttribute("siteId", site.getSiteId());

        return executePage("/account/groups/configureGroupsWindow.jsp");
    }

    private final International international = ServiceLocator.getInternationStorage().get("configureGroupsWindow", Locale.US);
    private final Persistance persistance = ServiceLocator.getPersistance();
}
