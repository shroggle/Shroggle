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
package com.shroggle.util.security;

import java.lang.reflect.Method;

/**
 * @author Artem Stasuk
 */
class SecurityCheckCreator {

    public SecurityCheck execute(
            final Class securityClass, final Method securityMethod,
            final Object... securityMethodParameters) {
        final SecurityUser securityUser = securityMethod.getAnnotation(SecurityUser.class);
        if (securityUser != null) return new SecurityCheckUser();
        return new SecurityCheckTrue();
    }

}
