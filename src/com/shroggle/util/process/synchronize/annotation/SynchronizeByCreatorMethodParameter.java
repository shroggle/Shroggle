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

import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.SynchronizeRequest;
import com.shroggle.util.process.synchronize.SynchronizeRequestEntity;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 17.09.2008
 */
class SynchronizeByCreatorMethodParameter implements SynchronizeByCreator {

    public SynchronizeByCreatorMethodParameter(
            final SynchronizeByMethodParameter byMethodParameter) {
        if (byMethodParameter == null) {
            throw new UnsupportedOperationException(
                    "Can't create synchronize request by null class property!");
        }
        this.entityClass = byMethodParameter.entityClass();
        this.method = byMethodParameter.method();
        this.deepParent = byMethodParameter.deepParent();
        this.entityIdParameterIndex = byMethodParameter.entityIdParameterIndex();
    }

    public SynchronizeRequest create(Object object, Object... parameters) {
        if (entityIdParameterIndex < 0) {
            throw new UnsupportedOperationException(
                    "Can't create synchronize request by negative parameter index!");
        }
        if (entityIdParameterIndex >= parameters.length) {
            throw new UnsupportedOperationException(
                    "Can't create synchronize request by parameter index "
                            + entityIdParameterIndex + " from "
                            + parameters.length + "!");
        }
        return new SynchronizeRequestEntity(entityClass, method, parameters[entityIdParameterIndex], deepParent);
    }

    private final SynchronizeMethod method;
    private final Class entityClass;
    private final int deepParent;
    private final int entityIdParameterIndex;

}
