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

import com.shroggle.TestRunnerWithMockServices;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stasuk Artem
 */
@RunWith(TestRunnerWithMockServices.class)
public class AsyncMailSenderTest {

    @Test(expected = NullPointerException.class)
    public void createWithNullSender() {
        new AsyncMailSender(null);
    }

    @Test
    public void noWaitOnSend() throws Exception {
        final MailSender mailSender = new AsyncMailSender(new MailSender() {
            @Override
            public void send(final Mail mail) {
                synchronized (this) {
                    try {
                        this.wait();
                    } catch (final InterruptedException exception) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        new Thread(new Runnable() {

            public void run() {
                try {
                    mailSender.send(new Mail("", "", ""));
                    mailIsSended = true;
                } catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
            }

        }).start();

        Thread.sleep(1000);
        Assert.assertTrue("Your mail queue is not async!", mailIsSended);
    }

    @Test
    public void sendWithException() throws Exception {
        final MailSender mailSender = new AsyncMailSender(new MailSender() {
            @Override
            public void send(final Mail mail) {
                throw new UnsupportedOperationException("Test exception!");
            }
        });
        Thread.sleep(1000);
        mailSender.send(new Mail("", "", ""));
    }

    private boolean mailIsSended = false;

}
