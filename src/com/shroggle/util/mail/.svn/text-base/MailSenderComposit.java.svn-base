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

/**
 * @author Artem Stasuk
 */
public class MailSenderComposit implements MailSender {

    public MailSenderComposit(final MailSender... senders) {
        this.senders = senders;
    }

    @Override
    public void send(final Mail mail) {
        for (final MailSender sender : senders) {
            sender.send(mail);
        }
    }

    private final MailSender[] senders;

}
