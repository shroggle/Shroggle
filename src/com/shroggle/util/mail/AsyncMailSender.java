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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Stasuk Artem
 */
public class AsyncMailSender implements MailSender {

    public AsyncMailSender(final MailSender mailSender) {
        if (mailSender == null) {
            throw new NullPointerException(
                    "Can't create mail queue with null mail sender!");
        }
        final Thread thread = new Thread(new Runnable() {

            public void run() {
                try {
                    while (true) {
                        final Mail mail = mails.take();
                        try {
                            mailSender.send(mail);
                        } catch (final Exception exception) {
                            if (exception.getClass() == InterruptedException.class) {
                                Thread.currentThread().interrupt();
                            }
                            log.log(Level.SEVERE, "Can't send mail!", exception);
                        }
                    }
                } catch (final InterruptedException exception) {
                    Thread.currentThread().interrupt();
                }
            }

        });
        thread.setName(AsyncMailSender.class.getSimpleName());
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void send(final Mail mail) {
        mails.add(mail);
    }

    private final BlockingQueue<Mail> mails = new LinkedBlockingQueue<Mail>();
    private final static Logger log = Logger.getLogger(AsyncMailSender.class.getName());

}
