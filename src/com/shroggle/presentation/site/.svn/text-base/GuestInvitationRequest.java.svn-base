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
package com.shroggle.presentation.site;

import com.shroggle.logic.groups.GroupsTime;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import java.util.List;
import java.util.ArrayList;

/**
 * Author: dmitry.solomadin
 * Date: 14.02.2009
 */
@DataTransferObject
public class GuestInvitationRequest {

    @RemoteProperty
    private int siteId;

    @RemoteProperty
    private int formId;

    @RemoteProperty
    private String firstName;

    @RemoteProperty
    private String lastName;

    @RemoteProperty
    private String email;

    @RemoteProperty
    private String invitationText;

    @RemoteProperty
    private List<GroupsTime> groupsWithTime = new ArrayList<GroupsTime>();

    public List<GroupsTime> getGroupsWithTimeInterval() {
        return groupsWithTime;
    }

    public void setGroupsWithTimeInterval(List<GroupsTime> groupsWithTime) {
        this.groupsWithTime = groupsWithTime;
    }

    public String getInvitationText() {
        return invitationText;
    }

    public void setInvitationText(String invitationText) {
        this.invitationText = invitationText;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
