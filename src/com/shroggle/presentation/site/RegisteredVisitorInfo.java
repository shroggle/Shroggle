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

import com.shroggle.entity.DraftForm;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
public class RegisteredVisitorInfo {

    @RemoteProperty
    private String firstName;

    @RemoteProperty
    private String lastName;

    @RemoteProperty
    private String email;

    @RemoteProperty
    private String created;

    @RemoteProperty
    private String updated;

    @RemoteProperty
    private String status;

    @RemoteProperty
    private int visitorId;

    @RemoteProperty
    private boolean invited;

    @RemoteProperty
    private List<DraftForm> forms = new ArrayList<DraftForm>();

    @RemoteProperty
    private Date createdDateRaw;

    @RemoteProperty
    private Date updatedDateRaw;

    @RemoteProperty
    private String groupsNames = "";

    public String getGroupsNames() {
        return groupsNames;
    }

    public void setGroupsNames(String groupsNames) {
        this.groupsNames = groupsNames;
    }

    public boolean isInvited() {
        return invited;
    }

    public void setInvited(boolean invited) {
        this.invited = invited;
    }

    public int getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(int visitorId) {
        this.visitorId = visitorId;
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedDateRaw() {
        return createdDateRaw;
    }

    public void setCreatedDateRaw(Date createdDateRaw) {
        this.createdDateRaw = createdDateRaw;
    }

    public Date getUpdatedDateRaw() {
        return updatedDateRaw;
    }

    public void setUpdatedDateRaw(Date updatedDateRaw) {
        this.updatedDateRaw = updatedDateRaw;
    }

    public List<DraftForm> getForms() {
        return forms;
    }

    public void setForms(List<DraftForm> forms) {
        this.forms = forms;
    }

    public void addForm(final DraftForm form){
        this.forms.add(form);
    }

}
