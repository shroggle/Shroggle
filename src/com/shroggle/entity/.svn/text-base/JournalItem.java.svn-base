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
@Entity(name = "journalItems")
public class JournalItem {

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getJournalItemId() {
        return journalItemId;
    }

    public void setJournalItemId(int journalItemId) {
        this.journalItemId = journalItemId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(Integer visitorId) {
        this.visitorId = visitorId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public JournalItemPriority getPriority() {
        return priority;
    }

    public void setPriority(JournalItemPriority priority) {
        this.priority = priority;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(final String sessionId) {
        this.sessionId = sessionId;
    }

    @Column(updatable = false, nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date created;

    @Id
    private int journalItemId;

    @Lob
    @Column(updatable = false)
    private String message;

    @Column(updatable = false)
    private Integer userId;

    @Column(updatable = false)
    private Integer visitorId;

    @Column(nullable = false, length = 250, updatable = false)
    private String className;

    @Enumerated
    @Column(nullable = false, updatable = false)
    private JournalItemPriority priority;

    @Column(length = 200)
    private String sessionId;

}
