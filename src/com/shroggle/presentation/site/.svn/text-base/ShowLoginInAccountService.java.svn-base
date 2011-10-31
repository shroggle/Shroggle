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
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author Stasuk Artem
 */
@RemoteProxy
public class ShowLoginInAccountService extends AbstractService {

    @RemoteMethod
    public String execute(final Boolean exception) throws Exception {
        this.exception = exception != null ? exception : false;
        loginedUser = new UsersManager().getLoginedUser();
        getContext().getHttpServletRequest().setAttribute("service", this);
        return getContext().forwardToString("/account/loginInAccount.jsp");
    }

    public User getLoginedUser() {
        return loginedUser;
    }

    public boolean getException() {
        return exception;
    }

    private boolean exception = true;
    private User loginedUser;

}
