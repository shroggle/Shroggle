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
package com.shroggle.exception;

import com.shroggle.entity.User;
import com.shroggle.logic.user.UsersManager;

/**
 * Inherit this exception if you a need access to logined user in catch code, without additional steps.
 *
 * @author Artem Stasuk
 * @link http://jira.web-deva.com/browse/SW-5789
 */
public abstract class LogicException extends RuntimeException {

    public LogicException() {
        super();
    }

    public LogicException(final String message) {
        super(message);
    }

    public User getLoginedUser() {
        return new UsersManager().getLoginedUser();
    }

}
