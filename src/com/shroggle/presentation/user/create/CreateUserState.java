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
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.logic.user.ConfirmCodeGetter;

import java.util.List;

/**
 * Instance of this class create every call of action.
 * It containt user and site for this mode.
 *
 * @author Artem Stasuk
 */
final class CreateUserState {

    public CreateUserState(final CreateUserRequest request) {
        final Persistance persistance = ServiceLocator.getPersistance();

        if (request.getUserId() != null) {
            user = persistance.getUserById(request.getUserId());
        } else {
            user = null;
        }

        if (request.getInvitedUserId() != null) {
            invitedUser = persistance.getUserById(request.getInvitedUserId());
        }

        if (user != null && invitedUser != null) {
            userOnSiteRights = persistance.getNotActiveUserOnSiteRightsByUserAndInvitedUser(
                    user.getUserId(), invitedUser.getUserId());
        }
        originalUser = invitedUser;

        if (request.getUserId() != null || request.getInvitedUserId() != null || request.getConfirmCode() != null) {
            if (user == null || invitedUser == null || userOnSiteRights == null || userOnSiteRights.isEmpty()) {
                request.setShowWrongUrlMessage(true);
            } else {
                final String confirmCode = ConfirmCodeGetter.execute(invitedUser, user);
                if (!confirmCode.equals(request.getConfirmCode())) {
                    request.setShowWrongUrlMessage(true);
                }
            }
        }

    }

    public User getUser() {
        return user;
    }

    public User getOriginalUser() {
        return originalUser;
    }

    public List<UserOnSiteRight> getUserOnSiteRights() {
        return userOnSiteRights;
    }

    public User getInvitedUser() {
        return invitedUser;
    }

    public void setInvitedUser(final User invitedUser) {
        this.invitedUser = invitedUser;
    }

    private final User user;
    private final User originalUser;
    private List<UserOnSiteRight> userOnSiteRights;
    private User invitedUser;

}
