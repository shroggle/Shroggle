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
package com.shroggle.presentation.user.create;

import com.shroggle.logic.user.UserManager;
import com.shroggle.presentation.site.CreateSiteAction;
import com.shroggle.presentation.site.RegistrationFinishedAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import net.sourceforge.stripes.action.Resolution;

/**
 * @author Artem Stasuk
 */
class CreateUserRedirectFinishRegistration implements CreateUserRedirect {

    public Resolution execute(final CreateUserState state) {
        final Config config = ServiceLocator.getConfigStorage().get();
        if (config.getRegistration().isConfirm()) {
            new UserManager(state.getInvitedUser()).sendActivationOrInvitationEmails();
            return ServiceLocator.getResolutionCreator().redirectToAction(RegistrationFinishedAction.class);
        } else {
            return ServiceLocator.getResolutionCreator().redirectToAction(CreateSiteAction.class);
        }
    }

}
