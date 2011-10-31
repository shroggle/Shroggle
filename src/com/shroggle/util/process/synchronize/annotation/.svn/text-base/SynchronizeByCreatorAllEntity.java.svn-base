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

import com.shroggle.util.process.synchronize.SynchronizeRequest;
import com.shroggle.util.process.synchronize.SynchronizeRequestAllEntity;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 20.10.2008
 */
class SynchronizeByCreatorAllEntity implements SynchronizeByCreator {

    public SynchronizeByCreatorAllEntity(final Class entityClass) {
        this.entityClass = entityClass;
    }

    public SynchronizeRequest create(final Object object, final Object... parameters) {
        return new SynchronizeRequestAllEntity(entityClass);
    }

    private final Class entityClass;

}
