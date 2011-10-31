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

package com.shroggle.presentation.account.accessPermissions;

import com.shroggle.entity.User;
import com.shroggle.exception.UserException;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.site.LoginInUserResolution;
import com.shroggle.presentation.site.LoginedUserInfo;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;


@UrlBinding("/account/accessPermissions.action")
public class AccessPermissionsAction extends Action implements LoginedUserInfo {

    @DefaultHandler
    public Resolution show() {
        try {
            final AccessPermissionsModel model = new AccessPermissionsModel();
            getHttpServletRequest().setAttribute("model", model);
        } catch (final UserException exception) {
            return new LoginInUserResolution(this);
        }
        return new ForwardResolution("/account/accessPermissions/accessPermissions.jsp");
    }

    public User getLoginUser() {
        return new UsersManager().getLogined().getUser();
    }
}