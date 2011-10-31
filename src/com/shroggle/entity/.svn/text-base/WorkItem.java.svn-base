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

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * @author Artem Stasuk
 */
@DataTransferObject
@Inheritance(strategy = InheritanceType.JOINED)
@Entity(name = "workSiteItems")
@org.hibernate.annotations.Table(
        appliesTo = "workSiteItems",
        indexes = {
                @org.hibernate.annotations.Index(
                        name = "siteIdIndex",
                        columnNames = {"siteId"}
                )
        }
)
public abstract class WorkItem extends AbstractItem {

    @Id
    private int id;

    public final int getId() {
        return id;
    }

    public final void setId(final int id) {
        this.id = id;
    }

}