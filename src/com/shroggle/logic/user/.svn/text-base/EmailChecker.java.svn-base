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

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.mail.MailAddressValidator;
import com.shroggle.exception.NullOrEmptyEmailException;
import com.shroggle.exception.IncorrectEmailException;

/**
 * Author: Igor Kanshin (igor).
 * </p>
 * Date: 05.09.2008
 */
public class EmailChecker {

    public void execute(final String accountEmail) {
        if (accountEmail == null || accountEmail.trim().isEmpty()) {
            throw new NullOrEmptyEmailException("");
        }

        final MailAddressValidator mailAddressValidator = ServiceLocator.getMailAddressValidator();
        if (mailAddressValidator == null) {
            throw new IllegalArgumentException("Can't get mail address validate service!");
        }

        if (!mailAddressValidator.valid(accountEmail)) {
            throw new IncorrectEmailException("");
        }
    }

}
