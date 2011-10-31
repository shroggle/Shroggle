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
 * @author Balakirev Anatoliy
 */
@Embeddable
public class UsersGroupId implements Serializable {

    @ManyToOne
    @JoinColumn(nullable = false, name = "userId")
    @ForeignKey(name = "usersGroupsUserId")
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false, name = "groupId")
    @ForeignKey(name = "usersGroupsGroupId")
    private Group group;

    public UsersGroupId(User user, Group group) {
        this.user = user;
        this.group = group;
    }

    public UsersGroupId() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean equals(final Object o) {
        if (o instanceof UsersGroupId) {
            final UsersGroupId usersGroupId = (UsersGroupId) o;
            return usersGroupId.getUser().getUserId() == this.getUser().getUserId() &&
                    usersGroupId.getGroup().getGroupId() == this.getGroup().getGroupId();
        }
        return false;
    }

    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Integer.valueOf(this.getUser().getUserId()).hashCode() +
                Integer.valueOf(this.getGroup().getGroupId()).hashCode();
        return hash;
    }
}

