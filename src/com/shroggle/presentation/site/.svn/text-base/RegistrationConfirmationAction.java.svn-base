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
import com.shroggle.logic.registrationConfirmation.RegistrationConfirmationManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.Date;

@UrlBinding("/account/registrationConfirmation.action")
public class RegistrationConfirmationAction extends Action implements LoginedUserInfo {

    @DefaultHandler
    public Resolution show() {
        infoEMail = configStorage.get().getSupportEmail();
        registrationCode = StringUtil.getEmptyOrString(registrationCode);
        final User user = persistance.getUserById(userId);
        if (user == null || !new UserManager(user).createRegistrationCode().equals(registrationCode)) {
            return resolutionCreator.forwardToUrl("/account/registration/registrationLinkNotValid.jsp");
        }

        if (user.getActiveted() != null) {
            return resolutionCreator.forwardToUrl("/account/registration/registrationLinkActive.jsp");
        } else if (RegistrationConfirmationManager.isLinkExpired(user)) {
            return resolutionCreator.forwardToUrl("/account/registration/registrationLinkExpired.jsp");
        } else {
            persistanceTransaction.execute(new Runnable() {

                public void run() {
                    user.setActiveted(new Date());
                }

            });
            this.user = new UsersManager().login(user.getEmail(), user.getPassword(), null).getUser();
            return resolutionCreator.forwardToUrl("/account/registration/registrationConfirmation.jsp");
        }
    }

    public final User getLoginUser() {
        return user;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public String getInfoEMail() {
        return infoEMail;
    }

    public void setUserId(final int userId) {
        this.userId = userId;
    }

    private final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private String infoEMail;
    private int userId;
    private User user;
    private String registrationCode;

}
