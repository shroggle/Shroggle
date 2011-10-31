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

import com.shroggle.logic.user.UsersGroupManager;
import com.shroggle.presentation.manageRegistrants.ShowManageRegistrantsTableService;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.RemoteMethod;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.manageRegistrants.ShowManageRegistrantsRequest;
import com.shroggle.entity.User;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.ServiceLocator;

import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class RemoveVisitorsFromGroupService extends AbstractService {

    @RemoteMethod
    public String execute(final int groupId, final List<Integer> usersId, final ShowManageRegistrantsRequest manageRegistrantsRequest) throws Exception {
        new UsersManager().getLogined();
        for (User user : persistance.getUsersByUsersId(usersId)) {
            new UsersGroupManager(user).removeAccessToGroup(groupId);
        }
        return new ShowManageRegistrantsTableService().execute(manageRegistrantsRequest);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}
