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

package com.shroggle.presentation.site;

import com.shroggle.entity.User;
import com.shroggle.entity.Widget;
import com.shroggle.exception.UserNotFoundException;
import com.shroggle.exception.VistorEmailAndRetypeEmailAreNotEqual;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.international.International;
import com.shroggle.util.mail.MailSender;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.Locale;

/**
 * author: dmitry.solomadin
 * date: 15.01.2009
 */
@RemoteProxy
public class EmailVisitorPasswordService {
    private Persistance persistance = ServiceLocator.getPersistance();
    private MailSender mailSender = ServiceLocator.getMailSender();
    private ConfigStorage configStorage = ServiceLocator.getConfigStorage();

    @RemoteMethod
    public String email(final String email, final String retypeEmail, final int widgetId) {
        final International international = ServiceLocator.getInternationStorage().get("forgottenMyPasswordWindow", Locale.US);
        User user = persistance.getUserByEmail(email);
        final Widget widget = persistance.getWidget(widgetId);
        if (widget == null) {
            throw new WidgetNotFoundException("Cannot find widget by id=" + widgetId);
        }
        if (user == null) {
            throw new UserNotFoundException(international.get("VisitorNotFoundException"));
        }
        if (!email.equals(retypeEmail)) {
            throw new VistorEmailAndRetypeEmailAreNotEqual(international.get("VistorEmailAndRetypeEmailAreNotEqual"));
        }
        final String password = user.getPassword();
        final String name = getName(user);
        final String messageBody = international.get(
                "message", name, password,
                configStorage.get().getApplicationUrl(), configStorage.get().getSupportEmail());
        mailSender.send(new Mail(email, messageBody, international.get("messageSubject")));
        return "ok";
    }


    @RemoteMethod
    public String emailForChildSiteVisitor(final int visitorId) {
        final International international = ServiceLocator.getInternationStorage().get("forgottenMyPasswordWindow", Locale.US);
        User user = persistance.getUserById(visitorId);
        if (user == null) {
            throw new UserNotFoundException(international.get("VisitorNotFoundException"));
        }
        final String password = user.getPassword();
        final String name = getName(user);
        final String messageBody = international.get(
                "message", name, password,
                configStorage.get().getApplicationUrl(), configStorage.get().getSupportEmail());
        mailSender.send(new Mail(user.getEmail(), messageBody, international.get("messageSubject")));
        return user.getEmail();
    }

    private String getName(User user) {
        String name = "";
        if (user.getFirstName() != null) {
            name = user.getFirstName();
        } else if (user.getLastName() != null) {
            name = user.getLastName();
        }
        return name;
    }
}
