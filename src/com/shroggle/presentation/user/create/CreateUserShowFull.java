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
class CreateUserShowFull implements CreateUserShow {

    public void execute(final CreateUserAction action) {
        final User invitedUser = action.getState().getInvitedUser();
        final CreateUserRequest request = action.getRequest();
        request.setPass(invitedUser.getPassword());
        request.setPasswordConfirm(invitedUser.getPassword());
        request.setOriginalPassword(invitedUser.getPassword());
        request.setOriginalPasswordConfirm(invitedUser.getPassword());
        request.setOriginalEmail(invitedUser.getEmail());
        request.setEmail(invitedUser.getEmail());
        request.setOriginalEmailConfirm(invitedUser.getEmail());
        request.setEmailConfirm(invitedUser.getEmail());
        request.setOriginalFirstName(invitedUser.getFirstName());
        request.setFirstName(invitedUser.getFirstName());
        request.setOriginalLastName(invitedUser.getLastName());
        request.setLastName(invitedUser.getLastName());
        request.setPostalCode(invitedUser.getPostalCode());
        request.setOriginalTelephone(invitedUser.getTelephone());
        request.setTelephone(invitedUser.getTelephone());
        sites.execute(action);
    }

    private final CreateUserSites sites = new CreateUserSites();

}
