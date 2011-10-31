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

/**
 * @author Artem Stasuk
 */
class CreateUserShowSimple implements CreateUserShow {

    public void execute(final CreateUserAction action) {
        final CreateUserRequest request = action.getRequest();
        final User invitedUser = action.getState().getInvitedUser();
        request.setEmail(invitedUser.getEmail());
        request.setEmailConfirm(invitedUser.getEmail());
        request.setFirstName(invitedUser.getFirstName());
        request.setLastName(invitedUser.getLastName());
        sites.execute(action);
    }

    private final CreateUserSites sites = new CreateUserSites();

}
