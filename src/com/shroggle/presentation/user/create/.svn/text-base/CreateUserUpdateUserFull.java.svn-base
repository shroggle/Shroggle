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
import com.shroggle.logic.coordinates.CoordinateCreator;
import com.shroggle.util.StringUtil;

import java.util.Date;

/**
 * @author Artem Stasuk
 */
class CreateUserUpdateUserFull extends CreateUserUpdateUserEmail {

    @Override
    public void execute(final User invitedUser, final CreateUserRequest request) {
        super.execute(invitedUser, request);

        invitedUser.setTelephone(request.getTelephone());
        invitedUser.setFirstName(request.getFirstName());
        invitedUser.setLastName(request.getLastName());
        invitedUser.setPostalCode(request.getPostalCode());
        invitedUser.setRegistrationDate(new Date());
        invitedUser.setPassword(request.getPass());
    }

}
