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

import com.shroggle.presentation.manageRegistrants.ShowManageRegistrantsTableService;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.manageRegistrants.ShowManageRegistrantsRequest;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class RemoveGroupService extends AbstractService {

    @RemoteMethod
    public String execute(final int groupId, final ShowManageRegistrantsRequest manageRegistrantsRequest) throws Exception {
        new UsersManager().getLogined();
        ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
            public void run() {
                ServiceLocator.getPersistance().removeGroup(groupId);
            }
        });
        return new ShowManageRegistrantsTableService().execute(manageRegistrantsRequest);
    }
    
}
