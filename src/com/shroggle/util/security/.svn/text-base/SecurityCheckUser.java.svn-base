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

import com.shroggle.util.context.Context;
import com.shroggle.util.context.ContextStorage;
import com.shroggle.util.ServiceLocator;

/**
 * @author Artem Stasuk
 */
class SecurityCheckUser implements SecurityCheck {

    public boolean execute(final Object checkClass, final Object... checkParameters) {
        final Context context = ServiceLocator.getContextStorage().get();
        return context != null && context.getUserId() != null;
    }

}
