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
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import com.shroggle.util.config.MockConfigStorage;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class RealMailSenderTest {

    @Test(expected = UnsupportedOperationException.class)
    public void sendWithNullConfigSmtpLogin() {
        Config config = ServiceLocator.getConfigStorage().get();
        config.getMail().setLogin(null);
        config.getMail().setPassword("v");
        config.getMail().setUrl("111");
        MailSender mailSender = new RealMailSender();
        mailSender.send(new Mail("", "", ""));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void sendWithNullConfigSmtpPassword() {
        Config config = ServiceLocator.getConfigStorage().get();
        config.getMail().setLogin("a");
        config.getMail().setPassword(null);
        config.getMail().setUrl("111");
        MailSender mailSender = new RealMailSender();
        mailSender.send(new Mail("", "", ""));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void sendWithNullConfigSmtpUrl() {
        Config config = ServiceLocator.getConfigStorage().get();
        config.getMail().setLogin("a");
        config.getMail().setPassword("gg");
        config.getMail().setUrl(null);
        MailSender mailSender = new RealMailSender();
        mailSender.send(new Mail("", "", ""));
    }

    public static void main(String[] args) {
        ServiceLocator.setConfigStorage(new MockConfigStorage());
        Config config = ServiceLocator.getConfigStorage().get();
        config.getMail().setLogin("admin@roombook.com.ua");
        config.getMail().setPassword("sj_&(_88t");
        config.getMail().setUrl("www.roombook.com.ua");

        Mail mail = new Mail();
        mail.setText("-1");
        mail.setTo("admin@roombook.com.ua");
        mail.setCc("admin@roombook.com.ua");
        mail.setFrom("artem.stasuk@gmail.com");
        new RealMailSender().send(mail);
    }


}
