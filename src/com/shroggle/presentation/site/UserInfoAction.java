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

import com.shroggle.entity.Country;
import com.shroggle.entity.EmailUpdateRequest;
import com.shroggle.entity.User;
import com.shroggle.exception.IncorrectEmailException;
import com.shroggle.exception.NotUniqueUserEmailException;
import com.shroggle.exception.NullOrEmptyEmailException;
import com.shroggle.logic.user.UserEmailChecker;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.util.MD5;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.mail.MailSender;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.*;
import net.sourceforge.stripes.action.*;

@UrlBinding("/account/userInfo.action")
public class UserInfoAction extends Action implements LoginedUserInfo {

    @DefaultHandler
    public Resolution show() throws Exception {
        user = new UsersManager().getLoginedUser();
        if (user == null) {
            return new LoginInUserResolution(this);
        }
        return new ForwardResolution("/account/userInfo/userInfo.jsp");
    }

    public Resolution update() throws Exception {
        final User user = new UsersManager().getLoginedUser();
        if (user == null) {
            return new LoginInUserResolution(this);
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            addValidationError("Please enter your email", "email");
        }

        final boolean registrationConfirm = configStorage.get().getRegistration().isConfirm();
        if (registrationConfirm) {
            if (request.getLastName() == null || request.getLastName().isEmpty()) {
                addValidationError("Please enter your Last Name", "lastName");
            }
            if (request.getTelephone() == null || request.getTelephone().isEmpty()) {
                addValidationError("Please enter your Telephone", "telephone");
            }
        }

        if (!getContext().getValidationErrors().isEmpty()) {
            return show();
        }

        final SynchronizeRequest synchronizeRequest = new SynchronizeRequestEntity(
                User.class, SynchronizeMethod.WRITE, user.getUserId());
        synchronize.execute(synchronizeRequest, new SynchronizeContext<Void>() {

            public Void execute() {
                try {
                    new UserEmailChecker().execute(request.getEmail(), user.getUserId());
                } catch (NullOrEmptyEmailException exception) {
                    addValidationError("Email cannot be empty!", "accountEmail");
                    return null;
                } catch (IncorrectEmailException exception) {
                    addValidationError("Email incorrect!", "accountEmail");
                    return null;
                } catch (NotUniqueUserEmailException exception) {
                    addValidationError("Email not unique!", "accountEmail");
                    return null;
                }

                persistanceTransaction.execute(new Runnable() {

                    public void run() {
                        if (!request.getEmail().equals(user.getEmail())) {
                            EmailUpdateRequest emailUpdateRequest = new EmailUpdateRequest();
                            emailUpdateRequest.setUserId(user.getUserId());
                            emailUpdateRequest.setNewEmail(request.getEmail());
                            emailUpdateRequest.setUpdateId(MD5.crypt(user.getEmail() + Math.random()));

                            final Config config = configStorage.get();
                            String messageBody = "Hi " + request.getFirstName() + ",\n" +
                                    "Your email address (login) for the http://" + config.getApplicationUrl() + " site was changed to this address. Please, follow the " + config.getMail().getEmailUpdateApprovalLink() + "?updateId=" + emailUpdateRequest.getUpdateId() + " link to accept the changes.\n" +
                                    "Please feel free to contact us directly with any questions, " + config.getSupportEmail() + "." +
                                    " for creating and maintaining great looking professional web sites with plenty of rich content.";
                            String messageSubject = "Account email update request";

                            persistance.putEmailUpdateRequest(emailUpdateRequest);

                            mailSender.send(new Mail(request.getEmail(), messageBody, messageSubject));
                            emailUpdated = true;
                        }
                        user.setTelephone(request.getTelephone());
                        user.setCity(request.getCity());
                        if (request.getCountry() == null) {
                            user.setCountry(Country.US);
                        } else {
                            user.setCountry(request.getCountry());
                        }
                        user.setFirstName(request.getFirstName());
                        user.setLastName(request.getLastName());
                        user.setUnitNumber(request.getUnitNumber());
                        if (request.getRegion() == null || request.getRegion().equals("-1")) {
                            request.setRegion("");
                        } else {
                            user.setRegion(request.getRegion());
                        }
                        user.setPostalCode(request.getPostalCode());
                        user.setStreet(request.getStreet());
                        user.setFax(request.getFax());
                        user.setTelephone2(request.getTelephone2());
                    }

                });
                return null;
            }

        });

        if (emailUpdated) {
            return new RedirectResolution("/account/userInfo.action?emailUpdated=true");
        } else {
            return new RedirectResolution("/account/userInfo.action?userInfoUpdated=true");
        }
    }

    public UpdateAccountInfoRequest getRequest() {
        return request;
    }

    public void setRequest(UpdateAccountInfoRequest request) {
        this.request = request;
    }

    public User getLoginUser() {
        return user;
    }

    private User user;
    private final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Synchronize synchronize = ServiceLocator.getSynchronize();
    private MailSender mailSender = ServiceLocator.getMailSender();
    private UpdateAccountInfoRequest request = new UpdateAccountInfoRequest();
    public boolean emailUpdated;

}
