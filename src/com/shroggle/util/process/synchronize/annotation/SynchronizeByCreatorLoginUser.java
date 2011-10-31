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
package com.shroggle.util.process.synchronize.annotation;

import com.shroggle.entity.User;
import com.shroggle.util.context.Context;
import com.shroggle.util.context.ContextStorage;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.SynchronizeRequest;
import com.shroggle.util.process.synchronize.SynchronizeRequestEntity;
import com.shroggle.util.ServiceLocator;

/**
 * @author Artem Stasuk
 */
class SynchronizeByCreatorLoginUser implements SynchronizeByCreator {

    public SynchronizeRequest create(final Object object, final Object... parameters) {
        final Context context = ServiceLocator.getContextStorage().get();
        if (context == null) {
            throw new UnsupportedOperationException("Can't create by null context!");
        }
        return new SynchronizeRequestEntity(User.class, SynchronizeMethod.READ, context.getUserId(), 0);
    }

}
