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
package com.shroggle.presentation.video;

import com.shroggle.entity.User;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.site.LoginedUserInfo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.process.synchronize.SynchronizeContext;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.SynchronizeRequest;
import com.shroggle.util.process.synchronize.SynchronizeRequestEntity;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;

public abstract class ActionWithLoginUser extends Action implements LoginedUserInfo {

    @DefaultHandler
    public Resolution execute() throws Exception {
        user = new UsersManager().getLoginedUser();

        final Integer loginUserId = user != null ? user.getUserId() : null;
        if (loginUserId != null) {
            final SynchronizeRequest synchronizeRequest =
                    new SynchronizeRequestEntity(User.class, SynchronizeMethod.READ, loginUserId);
            ServiceLocator.getSynchronize().execute(synchronizeRequest, new SynchronizeContext<Void>() {

                public Void execute() {
                    user = ServiceLocator.getPersistance().getUserById(loginUserId);
                    return null;
                }

            });
        }

        return getResolution();
    }

    public final User getLoginUser() {
        return user;
    }

    protected abstract Resolution getResolution();

    private User user;

}