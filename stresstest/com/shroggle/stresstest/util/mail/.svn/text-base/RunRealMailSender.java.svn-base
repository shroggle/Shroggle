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
package com.shroggle.stresstest.util.mail;

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.MockConfigStorage;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.mail.MailSender;
import com.shroggle.util.mail.RealMailSender;

/**
 * @author Artem Stasuk
 */
public class RunRealMailSender {

    public static void main(String[] args) {
        ServiceLocator.setConfigStorage(new MockConfigStorage());
        ServiceLocator.getConfigStorage().get().getMail().setLogin("artem.stasuk@gmail.com");
        ServiceLocator.getConfigStorage().get().getMail().setUrl("smtp.svitonline.com");
        ServiceLocator.getConfigStorage().get().getMail().setPassword("");
        final MailSender mailSender = new RealMailSender();
        mailSender.send(new Mail("artem.stasuk@gmail.com;marina@infoprog.com.ua", "ff", "f"));
    }

}
