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

import javax.persistence.*;

/**
 * Base class for all site items entities. It be a substitute for old SiteItem interface.
 *
 * @author Artem Stasuk
 */
@DataTransferObject
@Inheritance(strategy = InheritanceType.JOINED)
@Entity(name = "siteItems")
@org.hibernate.annotations.Table(
        appliesTo = "siteItems",
        indexes = {
                @org.hibernate.annotations.Index(
                        name = "siteIdIndex",
                        columnNames = {"siteId"}
                )
        }
)
public abstract class DraftItem extends AbstractItem {

    /**
     * Why name is formId? Because on work db very difficult change column name if
     * it under constraints.
     */
    @Id
    @Column(name = "formId")
    private int id;

    // We need this field, because we are automatically creating items with custom settings and
    // we need to have an ability to understand whether this custom setting were changed by user or not. Tolik
    private boolean modified;// default: "false". Changed to "true" when user presses "Save" button in the item settings window first time.

    /*----------------------------------------------------Methods-----------------------------------------------------*/

    public final SiteOnItem createSiteOnItemRight(final Site site) {
        final SiteOnItem siteOnItem = new SiteOnItem();
        siteOnItem.getId().setSite(site);
        siteOnItem.getId().setItem(this);
        return siteOnItem;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public final int getFormId() {
        return id;
    }

    public final int getId() {
        return id;
    }

    public final void setId(final int id) {
        this.id = id;
    }
}
