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
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByLoginUser;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

@RemoteProxy
public class LoginHelpService extends AbstractService {

    @RemoteMethod
    public String execute() {
        final User user = new UsersManager().getLogined().getUser();

        final String accountPassword = user.getPassword();
        final String accountEmail = user.getEmail();
        if (accountPassword == null) {
            return "PasswordNotFound";
        }
        if (accountEmail == null) {
            return "EmailNotFound";
        } else {
            try {
                new ForgottenMyPasswordService().sendEmailWhithForgottenPassword(accountEmail);
            } catch (Exception e) {
                return e.toString();
            }
        }
        return "ok";
    }

    @SynchronizeByLoginUser
    @RemoteMethod
    public String changeUserPassword(final String oldPassword, final String newPassword) {
        final Integer loginUserId = new UsersManager().getLogined().getUserId();
        if (loginUserId == null) {
            throw new UserNotLoginedException("Please login!");
        }
        final User user = persistance.getUserById(loginUserId);
        if (user == null) {
            return "UserNotFound";
        }
        final String accountPassword = user.getPassword();
        final String accountEmail = user.getEmail();
        if (accountPassword == null) {
            return "PasswordNotFound";
        }
        if (accountEmail == null) {
            return "EmailNotFound";
        }
        if (!oldPassword.equals(accountPassword)) {
            return "wrongOldPassword";
        } else {
            persistanceTransaction.execute(new Runnable() {
                public void run() {
                    user.setPassword(newPassword);
                }
            });
            return "ok";
        }
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
}