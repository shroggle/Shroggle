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

import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.SiteAccessLevelHolderRequest;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import java.util.List;


@DataTransferObject
public class AddEditPermissionsRequest extends AbstractService {

    @RemoteProperty
    private Integer userId;

    @RemoteProperty
    private String email;

    @RemoteProperty
    private String firstName;

    @RemoteProperty
    private String lastName;

    @RemoteProperty
    private String invitationText;

    @RemoteProperty
    private List<SiteAccessLevelHolderRequest> selectedSites;

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setInvitationText(final String invitationText) {
        this.invitationText = invitationText;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getInvitationText() {
        return invitationText;
    }

    public List<SiteAccessLevelHolderRequest> getSelectedSites() {
        return selectedSites;
    }

    public void setSelectedSites(List<SiteAccessLevelHolderRequest> selectedSites) {
        this.selectedSites = selectedSites;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
