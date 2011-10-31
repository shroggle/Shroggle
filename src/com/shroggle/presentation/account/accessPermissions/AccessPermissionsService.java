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

package com.shroggle.presentation.account.accessPermissions;

import com.shroggle.presentation.ServiceWithExecutePage;

import javax.servlet.ServletException;
import java.io.IOException;


/**
 * @author Balakirev Anatoliy
 */
public class AccessPermissionsService extends ServiceWithExecutePage {

    public String getUsersTable() throws IOException, ServletException {
        return getUsersTable(null);
    }

    public String getUsersTable(final RemoveUserRightsUtil.RemovedRightsResponse removedRightsResponse) throws IOException, ServletException {
        final AccessPermissionsModel model = new AccessPermissionsModel();
        model.setRemovedRightsResponse(removedRightsResponse);
        getRequest().setAttribute("model", model);
        return executePage("/account/accessPermissions/accountUsers.jsp");
    }

}