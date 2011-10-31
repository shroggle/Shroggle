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

import com.shroggle.entity.UserOnSiteRight;
import com.shroggle.util.ServiceLocator;

/**
 * @author Artem Stasuk
 */
class CreateUserRightMoveAndActivate implements CreateUserRight {

    public void execute(final CreateUserState state) {
        for (final UserOnSiteRight userOnSiteRight : state.getUserOnSiteRights()) {
            ServiceLocator.getPersistance().removeUserOnSiteRight(userOnSiteRight);

            UserOnSiteRight invitedUserOnSiteRight = new UserOnSiteRight();
            userOnSiteRight.getId().getSite().addUserOnSiteRight(invitedUserOnSiteRight);
            invitedUserOnSiteRight.setActive(true);
            invitedUserOnSiteRight.setSiteAccessType(userOnSiteRight.getSiteAccessType());
            state.getInvitedUser().addUserOnSiteRight(invitedUserOnSiteRight);
            ServiceLocator.getPersistance().putUserOnSiteRight(invitedUserOnSiteRight);
        }
    }

}