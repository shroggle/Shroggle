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
@Entity(name = "purchaseMailLogs")
public class PurchaseMailLog {

    public PurchaseMailLog() {
    }

    public PurchaseMailLog(boolean purchaseComplete, boolean errorsWhileSendingMail, boolean userNotFound, String emailText, Integer siteId, Integer userId, Date oldExpirationDate, Date newExpirationDate) {
        this.purchaseComplete = purchaseComplete;
        this.errorsWhileSendingMail = errorsWhileSendingMail;
        this.userNotFound = userNotFound;
        this.emailText = emailText;
        this.siteId = siteId;
        this.userId = userId;
        this.oldExpirationDate = oldExpirationDate;
        this.newExpirationDate = newExpirationDate;
    }

    @Id
    private int id;

    private boolean purchaseComplete;

    private boolean errorsWhileSendingMail;

    private boolean userNotFound;

    @Lob
    private String emailText;

    private Integer siteId;

    private Integer userId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date oldExpirationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date newExpirationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date creationDate = new Date();

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getOldExpirationDate() {
        return oldExpirationDate;
    }

    public void setOldExpirationDate(Date oldExpirationDate) {
        this.oldExpirationDate = oldExpirationDate;
    }

    public Date getNewExpirationDate() {
        return newExpirationDate;
    }

    public void setNewExpirationDate(Date newExpirationDate) {
        this.newExpirationDate = newExpirationDate;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPurchaseComplete() {
        return purchaseComplete;
    }

    public void setPurchaseComplete(boolean purchaseComplete) {
        this.purchaseComplete = purchaseComplete;
    }

    public boolean isErrorsWhileSendingMail() {
        return errorsWhileSendingMail;
    }

    public void setErrorsWhileSendingMail(boolean errorsWhileSendingMail) {
        this.errorsWhileSendingMail = errorsWhileSendingMail;
    }

    public boolean isUserNotFound() {
        return userNotFound;
    }

    public void setUserNotFound(boolean userNotFound) {
        this.userNotFound = userNotFound;
    }

    public String getEmailText() {
        return emailText;
    }

    public void setEmailText(String emailText) {
        this.emailText = emailText;
    }
}
