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

import com.shroggle.util.StringUtil;

/**
 * @author Artem Stasuk
 */
public class ConsoleMailSender implements MailSender {

    @Override
    public void send(final Mail mail) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nServer start send mail!");
        stringBuilder.append("\nto ------------------------------------------------------------------------:\n");
        stringBuilder.append(format(mail.getTo()));
        stringBuilder.append("\ncc ------------------------------------------------------------------------:\n");
        stringBuilder.append(format(mail.getCc()));
        stringBuilder.append("\nreply ---------------------------------------------------------------------:\n");
        stringBuilder.append(format(mail.getReply()));
        stringBuilder.append("\nfrom ----------------------------------------------------------------------:\n");
        stringBuilder.append(format(mail.getFrom()));
        stringBuilder.append("\nsubject -------------------------------------------------------------------:\n");
        stringBuilder.append(format(mail.getSubject()));
        stringBuilder.append("\ntext ----------------------------------------------------------------------:\n");
        stringBuilder.append(format(mail.getText()));
        stringBuilder.append("\nhtml ----------------------------------------------------------------------:\n");
        stringBuilder.append(format(mail.getHtml()));
        stringBuilder.append("\n---------------------------------------------------------------------------\n");
        stringBuilder.append("\nServer finish send mail!\n");
        System.out.println(stringBuilder);
    }

    private String format(final String value) {
        if (StringUtil.isNullOrEmpty(value)) {
            return "<not specified>";
        } else {
            return value;
        }
    }
}
