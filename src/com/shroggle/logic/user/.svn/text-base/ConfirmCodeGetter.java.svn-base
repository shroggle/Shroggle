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
import com.shroggle.util.MD5;

/**
 * @author Artem Stasuk
 */
public class ConfirmCodeGetter {

    public static String execute(final User user1, final User user2) {
        if (user1 == null || user2 == null) {
            throw new UnsupportedOperationException("Can't get confirm by null user!");
        }

        return MD5.crypt(user1.getUserId() + user2.getUserId() + user1.getEmail() + user2.getEmail());
    }

}
