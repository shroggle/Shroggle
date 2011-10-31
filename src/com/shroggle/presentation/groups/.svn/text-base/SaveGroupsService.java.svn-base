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
import com.shroggle.presentation.manageRegistrants.ShowManageRegistrantsTableService;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.exception.DuplicateGroupNamesException;
import com.shroggle.exception.EmptyGroupNameException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.entity.Group;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.Set;
import java.util.HashSet;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class SaveGroupsService extends AbstractService {

    public String execute(final SaveGroupsRequest request) throws Exception {
        new UsersManager().getLogined();
        final Set<String> names = new HashSet<String>((request.getGroupIdWithNewName().size() * 2));
        for (GroupIdWithNewName groupIdWithNewName : request.getGroupIdWithNewName()) {
            if (!names.add(groupIdWithNewName.getName())) {
                throw new DuplicateGroupNamesException();
            }
            if (groupIdWithNewName.getName().isEmpty()) {
                throw new EmptyGroupNameException();
            }
        }
        ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
            public void run() {
                for (GroupIdWithNewName groupIdWithNewName : request.getGroupIdWithNewName()) {
                    final Group group = ServiceLocator.getPersistance().getGroup(groupIdWithNewName.getGroupId());
                    if (group != null) {
                        group.setName(groupIdWithNewName.getName());
                    }
                }
            }
        });
        return new ShowManageRegistrantsTableService().execute(request.getManageRegistrantsRequest());
    }

}
