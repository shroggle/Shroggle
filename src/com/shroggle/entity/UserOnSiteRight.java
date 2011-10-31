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

package com.shroggle.entity;

import org.hibernate.annotations.CollectionOfElements;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * @author Artem Stasuk
 */
@Entity(name = "userOnSiteRights")
public class UserOnSiteRight {

    @EmbeddedId
    private UserOnSiteRightId id = new UserOnSiteRightId();

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private SiteAccessLevel siteAccessType = SiteAccessLevel.ADMINISTRATOR;

    @Column(nullable = false)
    private boolean active;

    @Enumerated
    @Column(nullable = false)
    private VisitorStatus visitorStatus = VisitorStatus.REGISTERED;

    /**
     * This field is true only if user was invited as visitor on some user site.
     * In other cases is false.
     */
    private boolean invited;

    @CollectionOfElements
    private List<Integer> filledRegistrationFormIds = new ArrayList<Integer>();

    /**
     * This field is not null only when user was invited as admin or editor on other
     * site and it hasn't activated rights over special email yet. When after activation
     * this right system will set field value to null.
     */
    @Lob
    private String invitationText;

    /**
     * This field is has value only if right create as invite user
     * to edit or admin on other site.
     */
    private Integer requesterUserId;

    /**
     * True if this object create when user registered over child site registration form.
     * In other cases is false.
     */
    private boolean fromNetwork;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date created = new Date();

    public UserOnSiteRightId getId() {
        return id;
    }

    public void setId(final UserOnSiteRightId id) {
        this.id = id;
    }

    public SiteAccessLevel getSiteAccessType() {
        return siteAccessType;
    }

    public void setSiteAccessType(final SiteAccessLevel siteAccessType) {
        this.siteAccessType = siteAccessType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getInvitationText() {
        return invitationText;
    }

    public void setInvitationText(String invitationText) {
        this.invitationText = invitationText;
    }

    public Integer getRequesterUserId() {
        return requesterUserId;
    }

    public void setRequesterUserId(Integer requesterUserId) {
        this.requesterUserId = requesterUserId;
    }

    public boolean isFromNetwork() {
        return fromNetwork;
    }

    public void setFromNetwork(final boolean fromNetwork) {
        this.fromNetwork = fromNetwork;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public boolean isInvited() {
        return invited;
    }

    public void setInvited(boolean invited) {
        this.invited = invited;
    }

    public VisitorStatus getVisitorStatus() {
        return visitorStatus;
    }

    public void setVisitorStatus(VisitorStatus visitorStatus) {
        this.visitorStatus = visitorStatus;
    }

    public List<Integer> getFilledRegistrationFormIds() {
        return Collections.unmodifiableList(filledRegistrationFormIds);
    }

    public void setFilledRegistrationFormIds(List<Integer> filledRegistrationFormIds) {
        this.filledRegistrationFormIds = filledRegistrationFormIds;
    }

    public void addFilledRegistrationFormId(Integer formId) {
        if (formId == null) {
            throw new IllegalArgumentException("formId cannot be null.");
        }

        if (this.filledRegistrationFormIds.contains(formId)) {
            return;
        }

        this.filledRegistrationFormIds.add(formId);
    }

    public void removeFilledRegistrationFormId(Integer formId) {
        if (formId == null) {
            throw new IllegalArgumentException("formId cannot be null.");
        }

        this.filledRegistrationFormIds.remove(formId);
    }

    public int getUserId() {
        return getId().getUser().getUserId();
    }

    public int getSiteId() {
        return getId().getSite().getSiteId();
    }

}