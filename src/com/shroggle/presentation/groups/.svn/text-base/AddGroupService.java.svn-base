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

import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.manageRegistrants.ShowManageRegistrantsRequest;
import com.shroggle.presentation.manageRegistrants.ShowManageRegistrantsTableService;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.entity.Site;
import com.shroggle.entity.Group;
import com.shroggle.util.persistance.PersistanceTransactionContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.RemoteMethod;

import java.util.Locale;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class AddGroupService extends AbstractService {

    @RemoteMethod
    public AddGroupResponse execute(final int siteId, final ShowManageRegistrantsRequest manageRegistrantsRequest) throws Exception {
        final UserManager userManager = new UsersManager().getLogined();
        final Site site = userManager.getRight().getSiteRight().getSiteForEdit(siteId).getSite();
        final Group group = ServiceLocator.getPersistanceTransaction().execute(new PersistanceTransactionContext<Group>() {
            public Group execute() {
                final Group group = new Group();
                group.setName(getAvailableDefaultName(site.getOwnGroups(), 0));
                group.setOwner(site);
                site.addGroup(group);
                ServiceLocator.getPersistance().putGroup(group);
                return group;
            }
        });
        final String html = new ShowManageRegistrantsTableService().execute(manageRegistrantsRequest);
        return new AddGroupResponse(new GroupIdWithNewName(group.getGroupId(), group.getName()), html);
    }

    private String getAvailableDefaultName(final List<Group> groups, final int level) {
        final String stringLevel = level == 0 ? "" : String.valueOf(level);
        for (Group group : groups) {
            if (group.getName().equals(defaultName + stringLevel)) {
                return getAvailableDefaultName(groups, (level + 1));
            }
        }
        return (defaultName + stringLevel);
    }

    private final International international = ServiceLocator.getInternationStorage().get("configureGroupsWindow", Locale.US);
    private final String defaultName = international.get("newGroup");
}
