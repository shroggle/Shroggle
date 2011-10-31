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

import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

@RemoteProxy
public class LoginUserService {

    @RemoteMethod
    public boolean execute(final LoginUserRequest request) {
        final UserManager userManager = usersManager.login(request.getEmail(), request.getPassword(), null);
        return userManager.getUser().getEmail().equals(config.getAdminLogin());
    }

    private final UsersManager usersManager = new UsersManager();
    private final Config config = ServiceLocator.getConfigStorage().get();

}