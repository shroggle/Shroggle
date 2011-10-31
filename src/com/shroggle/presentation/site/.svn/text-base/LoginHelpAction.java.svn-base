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

package com.shroggle.presentation.site;

import com.shroggle.entity.User;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.Action;
import com.shroggle.util.ServiceLocator;
import com.shroggle.logic.user.UsersManager;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;


@UrlBinding("/account/loginHelp.action")
public class LoginHelpAction extends Action implements LoginedUserInfo {

    @DefaultHandler
    public Resolution show() throws Exception {
        user = new UsersManager().getLoginedUser();
        if (user == null) {
            return resolutionCreator.loginInUser(this);
        }
        return resolutionCreator.forwardToUrl("/account/loginHelp.jsp");
    }


    public User getLoginUser() {
        return user;
    }

    private User user;
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
}