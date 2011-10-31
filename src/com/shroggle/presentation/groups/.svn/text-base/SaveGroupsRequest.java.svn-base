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

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import java.util.List;

import com.shroggle.presentation.manageRegistrants.ShowManageRegistrantsRequest;

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject
public class SaveGroupsRequest {

    @RemoteProperty
    private List<GroupIdWithNewName> groupIdWithNewName;

    @RemoteProperty
    private ShowManageRegistrantsRequest manageRegistrantsRequest;

    public ShowManageRegistrantsRequest getManageRegistrantsRequest() {
        return manageRegistrantsRequest;
    }

    public void setManageRegistrantsRequest(ShowManageRegistrantsRequest manageRegistrantsRequest) {
        this.manageRegistrantsRequest = manageRegistrantsRequest;
    }

    public List<GroupIdWithNewName> getGroupIdWithNewName() {
        return groupIdWithNewName;
    }

    public void setGroupIdWithNewName(List<GroupIdWithNewName> groupIdWithNewName) {
        this.groupIdWithNewName = groupIdWithNewName;
    }
}
