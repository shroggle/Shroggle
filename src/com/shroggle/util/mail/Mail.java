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
package com.shroggle.util.mail;

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigSmtp;

/**
 * @author Artem Stasuk
 */
public class Mail {

    public Mail() {
        final ConfigSmtp configSmtp = ServiceLocator.getConfigStorage().get().getConfigSmtp();
        this.from = configSmtp.getLogin();
    }

    public Mail(final String to, final String text, final String subject) {
        this();
        this.to = to;
        this.text = text;
        this.subject = subject;
    }

    public Mail(final String to, final String text, final String html, final String subject) {
        this(to, text, subject);
        this.html = html;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(final String from) {
        this.from = from + " <" + this.from + ">";
    }

    public String getTo() {
        return to;
    }

    public void setTo(final String to) {
        this.to = to;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(final String html) {
        this.html = html;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(final String cc) {
        this.cc = cc;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        return "Mail {to: " + to + ", cc: " + cc + ", reply: " + reply
                + ", from: " + from + ", subject: " + subject + "}";
    }

    private String to;
    private String cc;
    private String reply;
    private String html;
    private String from;
    private String text;
    private String subject;

}
