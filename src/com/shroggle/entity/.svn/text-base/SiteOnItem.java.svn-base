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
 * @author Artem Stasuk
 */
@Entity(name = "siteOnSiteItems")
public class SiteOnItem {

    public final DraftItem getItem() {
        return id.getItem();
    }

    public final Site getSite() {
        return id.getSite();
    }

    public final Date getAcceptDate() {
        return acceptDate;
    }

    public final void setAcceptDate(final Date acceptDate) {
        this.acceptDate = acceptDate;
    }

    public final Date getSendDate() {
        return sendDate;
    }

    public final void setSendDate(final Date sendDate) {
        this.sendDate = sendDate;
    }

    public final String getAcceptCode() {
        return acceptCode;
    }

    public final void setAcceptCode(final String acceptCode) {
        this.acceptCode = acceptCode;
    }

    public final SiteOnItemRightType getType() {
        return type;
    }

    public final void setType(final SiteOnItemRightType type) {
        this.type = type;
    }

    public final boolean isFromBlueprint() {
        return fromBlueprint;
    }

    public final void setFromBlueprint(final boolean fromBlueprint) {
        this.fromBlueprint = fromBlueprint;
    }

    public Integer getRequestedUserId() {
        return requestedUserId;
    }

    public void setRequestedUserId(Integer requestedUserId) {
        this.requestedUserId = requestedUserId;
    }

    public SiteOnItemId getId() {
        return id;
    }

    public void setId(SiteOnItemId id) {
        this.id = id;
    }

    /**
     * This field not null when user alredy accepted right.
     * In other cases is null.
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date acceptDate;

    /**
     * This field containt last date send request content accept
     * to user
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date sendDate;

    @Column(length = 250)
    private String acceptCode;

    @Enumerated
    @Column(nullable = false)
    private SiteOnItemRightType type = SiteOnItemRightType.READ;

    private boolean fromBlueprint;

    private Integer requestedUserId;

    @EmbeddedId
    private SiteOnItemId id = new SiteOnItemId();

}
