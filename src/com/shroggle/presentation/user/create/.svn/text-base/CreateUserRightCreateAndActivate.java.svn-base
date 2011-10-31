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

import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.UserOnSiteRight;
import com.shroggle.util.ServiceLocator;

/**
 * @author Artem Stasuk
 */
class CreateUserRightCreateAndActivate implements CreateUserRight {

    public void execute(final CreateUserState state) {
        final UserOnSiteRight userOnUserRight = new UserOnSiteRight();
        userOnUserRight.setSiteAccessType(SiteAccessLevel.ADMINISTRATOR);
        userOnUserRight.setActive(true);
        state.getInvitedUser().addUserOnSiteRight(userOnUserRight);
        ServiceLocator.getPersistance().putUserOnSiteRight(userOnUserRight);
    }

}
