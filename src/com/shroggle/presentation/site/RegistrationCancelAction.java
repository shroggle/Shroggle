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
import com.shroggle.logic.user.UserManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.ResolutionParameter;
import com.shroggle.presentation.StartAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.mail.MailSender;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/account/registrationCancel.action")
public class RegistrationCancelAction extends Action implements LoginedUserInfo {

    @DefaultHandler
    public Resolution show() {
        final User user = persistance.getUserById(userId);
        if (user == null || !new UserManager(user).createRegistrationCode().equals(registrationCode)) {
            return resolutionCreator.redirectToAction(RegistrationConfirmationAction.class,
                    new ResolutionParameter("registrationCode", registrationCode),
                    new ResolutionParameter("userId", userId));
        }

        if (user.getActiveted() != null) {
            return resolutionCreator.redirectToUrl(
                    "/account/registrationConfirmation.action?registrationCode=" + registrationCode);
        }
        return resolutionCreator.forwardToUrl("/account/registration/registrationCancel.jsp");
    }

    /**
     * @return - return always null, because user want cancel invite.
     */
    public User getLoginUser() {
        return null;
    }

    public Resolution execute() {
        final User user = persistance.getUserById(userId);
        if (user == null || !new UserManager(user).createRegistrationCode().equals(registrationCode)) {
            return resolutionCreator.redirectToAction(RegistrationConfirmationAction.class,
                    new ResolutionParameter("registrationCode", registrationCode),
                    new ResolutionParameter("userId", userId));
        }

        final String mailTo = configStorage.get().getSupportEmail();
        final String messageSubject = "Registration canceled!";
        final String messageBody = "User Info" +
                "\n Id: " + user.getUserId() +
                "\n Last name: " + user.getLastName() +
                "\n Email: " + user.getEmail() +
                "\n City: " + user.getCity() +
                "\n Country: " + user.getCountry() +
                "\n Telephone: " + user.getTelephone() +
                "\n Answer: " + cancel;

        mailSender.send(new Mail(mailTo, messageBody, messageSubject));

        return resolutionCreator.redirectToAction(StartAction.class);
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
    private final MailSender mailSender = ServiceLocator.getMailSender();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private String registrationCode;
    private String cancel;
    private int userId;

}
