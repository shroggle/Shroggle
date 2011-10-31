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

import java.util.Date;

/**
 * @author Artem Stasuk
 */
class CreateUserUpdateUserAndActivateIfNeed implements CreateUserUpdateUser {

    public CreateUserUpdateUserAndActivateIfNeed(final CreateUserUpdateUser createUserUpdateUser) {
        this.createUserUpdateUser = createUserUpdateUser;
    }

    @Override
    public void execute(final User invitedUser, final CreateUserRequest request) {
        createUserUpdateUser.execute(invitedUser, request);
        if (invitedUser.getActiveted() == null) {
            invitedUser.setActiveted(new Date());
        }
    }

    private CreateUserUpdateUser createUserUpdateUser;

}