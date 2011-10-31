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

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject
public class AddGroupResponse {

    public AddGroupResponse() {
    }

    public AddGroupResponse(GroupIdWithNewName groupIdWithNewName, String html) {
        this.groupIdWithNewName = groupIdWithNewName;
        this.html = html;
    }

    @RemoteProperty
    private GroupIdWithNewName groupIdWithNewName;

    @RemoteProperty
    private String html;

    public GroupIdWithNewName getGroupIdWithNewName() {
        return groupIdWithNewName;
    }

    public void setGroupIdWithNewName(GroupIdWithNewName groupIdWithNewName) {
        this.groupIdWithNewName = groupIdWithNewName;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
