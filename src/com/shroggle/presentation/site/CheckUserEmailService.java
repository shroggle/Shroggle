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

import com.shroggle.exception.IncorrectEmailException;
import com.shroggle.exception.NotUniqueUserEmailException;
import com.shroggle.exception.NullOrEmptyEmailException;
import com.shroggle.logic.user.UserEmailChecker;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

@RemoteProxy
public class CheckUserEmailService {

    @RemoteMethod
    public String execute(final String userEmail, final Integer userId) {
        try {
            new UserEmailChecker().execute(userEmail, userId);
        } catch (NotUniqueUserEmailException exception) {
            return exception.getClass().getName();
        } catch (NullOrEmptyEmailException exception) {
            return exception.getClass().getName();
        } catch (IncorrectEmailException exception) {
            return exception.getClass().getName();
        }
        return "";
    }

}