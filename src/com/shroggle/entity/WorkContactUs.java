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

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject
@Entity(name = "workContactUs")
public class WorkContactUs extends WorkForm implements ContactUs {

    @RemoteProperty
    @Column(length = 250)
    private String email;

    public ItemType getItemType() {
        return ItemType.CONTACT_US;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FormType getType() {
        return FormType.CONTACT_US;
    }
    
}