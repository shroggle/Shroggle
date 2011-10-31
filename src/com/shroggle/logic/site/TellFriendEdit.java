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
package com.shroggle.logic.site;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

/**
 * @author Artem Stasuk
 */
@DataTransferObject
public class TellFriendEdit {

    public Integer getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(Integer widgetId) {
        this.widgetId = widgetId;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getMailText() {
        return mailText;
    }

    public void setMailText(String mailText) {
        this.mailText = mailText;
    }

    public String getTellFriendName() {
        return tellFriendName;
    }

    public void setTellFriendName(String tellFriendName) {
        this.tellFriendName = tellFriendName;
    }

    public Integer getTellFriendId() {
        return tellFriendId;
    }

    public void setTellFriendId(Integer tellFriendId) {
        this.tellFriendId = tellFriendId;
    }

    public boolean isSendEmails() {
        return sendEmails;
    }

    public void setSendEmails(boolean sendEmails) {
        this.sendEmails = sendEmails;
    }

    @RemoteProperty
    private Integer tellFriendId;

    @RemoteProperty
    private String tellFriendName;

    @RemoteProperty
    private String mailText;

    @RemoteProperty
    private String mailSubject;

    @RemoteProperty
    private Integer widgetId;

    @RemoteProperty
    private boolean sendEmails;

}
