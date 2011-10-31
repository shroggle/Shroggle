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

import com.shroggle.entity.Group;
import com.shroggle.entity.User;
import com.shroggle.logic.groups.GroupsTime;
import com.shroggle.logic.user.UsersGroupManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.manageRegistrants.ShowManageRegistrantsRequest;
import com.shroggle.presentation.manageRegistrants.ShowManageRegistrantsTableService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class AddVisitorsToGroupService extends AbstractService {

    @RemoteMethod
    public String execute(final List<GroupsTime> groupsWithTime, final List<Integer> usersId, final ShowManageRegistrantsRequest manageRegistrantsRequest) throws Exception {
        new UsersManager().getLogined();
        final List<User> users = persistance.getUsersByUsersId(usersId);
        for (final GroupsTime groupsTime : groupsWithTime) {
            final Group group = persistance.getGroup(groupsTime.getGroupId());
            if (group != null) {
                ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
                    public void run() {
                        for (User user : users) {
                            new UsersGroupManager(user).addAccessToGroup(group, groupsTime.getTimeInterval());
                        }
                    }
                });
            } else {
                logger.warning("Unable to find group with id = " + groupsTime.getGroupId());
            }
        }

        return new ShowManageRegistrantsTableService().execute(manageRegistrantsRequest);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final Logger logger = Logger.getLogger(this.getClass().getName());
}
