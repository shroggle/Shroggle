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

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author Artem Stasuk
 */
@Entity(name = "workAdminLogins")
public class WorkAdminLogin extends WorkItem implements AdminLogin {

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.ADMIN_LOGIN;
    }

    @Column(length = 250)
    private String text;

}