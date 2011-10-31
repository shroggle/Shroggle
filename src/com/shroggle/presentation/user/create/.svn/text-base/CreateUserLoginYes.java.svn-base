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

import com.shroggle.logic.user.UsersManager;

/**
 * @author Artem Stasuk
 */
class CreateUserLoginYes implements CreateUserLogin {

    public void execute(final CreateUserAction action) {
        final CreateUserState state = action.getState();
        new UsersManager().login(state.getInvitedUser().getUserId());
    }

}
