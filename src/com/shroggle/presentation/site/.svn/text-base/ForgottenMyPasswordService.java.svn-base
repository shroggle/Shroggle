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
import com.shroggle.exception.UserNotFoundException;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.account.accessPermissions.AddEditPermissionsService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.international.International;
import com.shroggle.util.international.InternationalStorage;
import com.shroggle.util.mail.MailSender;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Locale;

/**
 * @author Balakirev Anatoliy
 */

// todo add synchronzie
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class, params = {})
public class ForgottenMyPasswordService extends AbstractService {

    @RemoteMethod
    public String execute() throws IOException, ServletException {
        getContext().getHttpServletRequest().setAttribute("service", this);
        return getContext().forwardToString("/account/forgottenMyPassword.jsp");
    }

    //    @SynchronizeByMethodParameter
    @RemoteMethod
    public String sendEmailWhithForgottenPassword(final String email) throws IOException, ServletException {
        final User user = persistance.getUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("Can't find user with email: " + email);
        }
        final String password = user.getPassword();
        if (password != null) {
            sendForgottenPasswordMail(user, email, password);
        } else {
            new AddEditPermissionsService().resendInvitation(user.getEmail().toLowerCase());
        }
        return getContext().forwardToString("/account/passwordSentWindow.jsp");
    }

    private void sendForgottenPasswordMail(final User user, final String email, final String password) {
        final International international = internationalStorage.get("forgottenMyPasswordWindow", Locale.US);
        final String firstName;
        if (user.getFirstName() != null) {
            firstName = user.getFirstName();
        } else {
            firstName = "";
        }
        final String messageBody = international.get(
                "message", firstName, password,
                configStorage.get().getApplicationUrl(), configStorage.get().getSupportEmail());
        mailSender.send(new Mail(email, messageBody, international.get("messageSubject")));
    }

    private final MailSender mailSender = ServiceLocator.getMailSender();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
    private final InternationalStorage internationalStorage = ServiceLocator.getInternationStorage();
}