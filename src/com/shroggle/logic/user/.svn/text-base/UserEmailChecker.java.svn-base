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

package com.shroggle.logic.user;

import com.shroggle.entity.User;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.exception.NotUniqueUserEmailException;

/**
 * @author Stasuk Artem
 */
public class UserEmailChecker {

    public void execute(final String email, final Integer userId) {
        checker.execute(email);

        final String lowerEmail = email.toLowerCase();
        final Persistance persistance = ServiceLocator.getPersistance();
        final User foundUser = persistance.getUserByEmail(lowerEmail);
        if (foundUser != null) {
            final Integer findedUserId = foundUser.getUserId();
            if (!findedUserId.equals(userId)) {
                throw new NotUniqueUserEmailException();
            }
        }
    }

    private final EmailChecker checker = new EmailChecker();

}