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
import com.shroggle.util.ServiceLocator;

/**
 * @author Artem Stasuk
 */
class CreateUserYesCreateUser implements CreateUserCreateUser {

    public CreateUserYesCreateUser(final CreateUserUpdateUser updateUser) {
        this.updateUser = updateUser;
    }

    public void execute(final CreateUserAction action) {
        final User user = new User();
        updateUser.execute(user, action.getRequest());
        ServiceLocator.getPersistance().putUser(user);
        action.getRequest().setInvitedUserId(user.getUserId());
        action.getState().setInvitedUser(user);
    }

    private final CreateUserUpdateUser updateUser;

}
