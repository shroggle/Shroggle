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

import com.shroggle.entity.User;
import com.shroggle.entity.UserOnSiteRight;
import com.shroggle.logic.user.ConfirmCodeGetter;
import static com.shroggle.presentation.user.create.CreateUserMode.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;

import java.util.List;

/**
 * @author Artem Stasuk
 * @see CreateUserMode
 */
class CreateUserModeCreator {

    public static CreateUserMode get(final CreateUserRequest request, final CreateUserState state) {
        final Config config = ServiceLocator.getConfigStorage().get();
        if (state.getUser() == null || state.getInvitedUser() == null) {
            if (config.getRegistration().isConfirm()) {
                return NEW;
            }
            return NEW_WITHOUT_CONFIRM;
        }

        final String confirmCode = ConfirmCodeGetter.execute(state.getInvitedUser(), state.getUser());
        if (!confirmCode.equals(request.getConfirmCode())) {
            if (config.getRegistration().isConfirm()) {
                return NEW;
            }
            return NEW_WITHOUT_CONFIRM;
        }

        final List<UserOnSiteRight> userOnSiteRights = state.getUserOnSiteRights();
        if (userOnSiteRights.isEmpty()) {
            return INVITED_DELETE;
        }

        if (state.getInvitedUser().getPassword() == null) {
            return INVITED_NEW;
        }
        return INVITED_EXSIST;
    }

    public static CreateUserMode next(final CreateUserRequest request, final CreateUserState state) {
        final Config config = ServiceLocator.getConfigStorage().get();
        if (state.getUser() == null || state.getInvitedUser() == null) {
            if (config.getRegistration().isConfirm()) {
                return NEW;
            }
            return NEW_WITHOUT_CONFIRM;
        }

        final String confirmCode = ConfirmCodeGetter.execute(state.getInvitedUser(), state.getUser());
        if (!confirmCode.equals(request.getConfirmCode())) {
            if (config.getRegistration().isConfirm()) {
                return NEW;
            }
            return NEW_WITHOUT_CONFIRM;
        }

        if (state.getUserOnSiteRights().isEmpty()) {
            return INVITED_DELETE;
        }

        final boolean emailChanged;
        if (state.getUser() != null) {
            final User invitedUser = state.getInvitedUser();
            emailChanged = !invitedUser.getEmail().equalsIgnoreCase(request.getEmail());
        } else {
            emailChanged = false;
        }

        if (request.getMode() == INVITED_EXSIST) {
            if (!request.isNotWantNewUser()) {
                return INVITED_EXSIST_WANT_NEW;
            }
        }

        if (request.getMode() == INVITED_DELETE && emailChanged) {
            return DELETE_INVITED_WITH_NEW_EMAIL;
        }

        if (request.getMode() == INVITED_NEW && emailChanged) {
            return NEW_INVITED_WITH_NEW_EMAIL;
        }

        return request.getMode();
    }

}
