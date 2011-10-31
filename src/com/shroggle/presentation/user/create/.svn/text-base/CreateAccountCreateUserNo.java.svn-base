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

/**
 * @author Artem Stasuk
 */
class CreateUserNoCreateUser implements CreateUserCreateUser {

    public CreateUserNoCreateUser(final CreateUserUpdateUser updateUser) {
        this.updateUser = updateUser;
    }

    public void execute(final CreateUserAction action) {
        updateUser.execute(action.getState().getInvitedUser(), action.getRequest());
    }

    private final CreateUserUpdateUser updateUser;

}
