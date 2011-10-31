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

import javax.persistence.*;
import java.util.Date;

/**
 * @author Balakirev Anatoliy
 */
@Entity(name = "usersGroup")
public class UsersGroup {

    @EmbeddedId
    private UsersGroupId id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    public int getGroupId() {
        return id.getGroup().getGroupId();
    }

    public int getUserId() {
        return id.getUser().getUserId();
    }

    public UsersGroupId getId() {
        return id;
    }

    public void setId(UsersGroupId id) {
        this.id = id;
    }

    public void setId(User user, Group group) {
        this.id = new UsersGroupId(user, group);
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
