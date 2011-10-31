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

import org.hibernate.annotations.ForeignKey;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * @author Stasuk Artem
 */
@Embeddable
public class UserOnSiteRightId implements Serializable {

    public UserOnSiteRightId(final User user, final Site site) {
        this.user = user;
        this.site = site;
    }

    public UserOnSiteRightId() {

    }

    public User getUser() {
        return user;
    }

    /**
     * Don't use this method in logic, use user.addUserOnSite() it will make all link work 
     * @param user - user
     */
    void setUser(final User user) {
        this.user = user;
    }

    public Site getSite() {
        return site;
    }

    /**
     * Don't use this method in logic, use site.addUserOnSite() it will make all link work
     * @param site - site
     */
    void setSite(final Site site) {
        this.site = site;
    }

    public boolean equals(final Object object) {
        if (object == null) return false;
        if (object.getClass() != UserOnSiteRightId.class) return false;
        final UserOnSiteRightId userOnSiteRightId = (UserOnSiteRightId) object;
        return userOnSiteRightId.user == user && userOnSiteRightId.site == site;
    }

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    @ForeignKey(name = "userOnSiteRightsUserId")
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false, name = "siteId")
    @ForeignKey(name = "userOnSiteRightsSiteId")
    private Site site;

}