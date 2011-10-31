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
 * @author Artem Stasuk
 */
@Embeddable
public class SiteOnItemId implements Serializable {

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public DraftItem getItem() {
        return item;
    }

    public void setItem(DraftItem item) {
        this.item = item;
    }

    @ManyToOne
    @ForeignKey(name = "siteOnSiteItemsSiteId")
    @JoinColumn(name = "siteId", nullable = false)
    private Site site;

    @ManyToOne
    @ForeignKey(name = "siteOnSiteItemsBlogId")
    @JoinColumn(name = "siteItemId", nullable = false)
    private DraftItem item;

    private static final long serialVersionUID = 6376576605610637227L;

}