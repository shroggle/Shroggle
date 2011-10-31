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

import javax.persistence.*;

/**
 * @author Balakirev Anatoliy
 */
@Entity(name = "groups")
public class Group {

    @Id
    private int groupId;

    @Column(nullable = false, length = 250)
    private String name;

    @ManyToOne
    @JoinColumn(name = "ownerId", nullable = false)
    @ForeignKey(name = "groupsOwnerId")
    private Site owner;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Site getOwner() {
        return owner;
    }

    public void setOwner(Site owner) {
        this.owner = owner;
    }
}
