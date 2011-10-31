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
import com.shroggle.util.reflection.FarField;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 17.09.2008
 */
class SynchronizeByCreatorMethodParameterProperty implements SynchronizeByCreator {

    public SynchronizeByCreatorMethodParameterProperty(
            final SynchronizeByMethodParameterProperty byMethodParameterProperty,
            final Object... parameters) {
        if (byMethodParameterProperty == null) {
            throw new UnsupportedOperationException(
                    "Can't create synchronize request by null class property!");
        }
        if (byMethodParameterProperty.entityIdParameterIndex() < 0) {
            throw new UnsupportedOperationException(
                    "Can't create synchronize request by negative parameter index!");
        }
        if (byMethodParameterProperty.entityIdParameterIndex() >= parameters.length) {
            throw new UnsupportedOperationException(
                    "Can't create synchronize request by parameter index "
                            + byMethodParameterProperty.entityIdParameterIndex() + " from "
                            + parameters.length + "!");
        }
        final Object object = parameters[byMethodParameterProperty.entityIdParameterIndex()];
        final Class objectClass = object.getClass();
        try {
            field = new FarField(byMethodParameterProperty.entityIdPropertyPath(), objectClass);
        } catch (NoSuchFieldException exception) {
            throw new UnsupportedOperationException(
                    "Can't find field " + byMethodParameterProperty.entityIdPropertyPath()
                            + " in " + objectClass, exception);
        }
        this.deepParent = byMethodParameterProperty.deepParent();
        this.parameterIndex = byMethodParameterProperty.entityIdParameterIndex();
        this.entityClass = byMethodParameterProperty.entityClass();
        this.method = byMethodParameterProperty.method();
    }

    public SynchronizeRequest create(
            final Object object, final Object... parameters) {
        if (parameterIndex >= parameters.length) {
            throw new UnsupportedOperationException(
                    "Can't create synchronize request by parameter index "
                            + parameterIndex + " from " + parameters.length + "!");
        }
        try {
            Object entityId = field.get(parameters[parameterIndex]);
            return new SynchronizeRequestEntity(entityClass, method, entityId, deepParent);
        } catch (IllegalAccessException exception) {
            throw new UnsupportedOperationException(
                    "Can't get value from field " + field
                            + " for object " + object, exception);
        }
    }

    private final FarField field;
    private final int parameterIndex;
    private final int deepParent;
    private final Class entityClass;
    private final SynchronizeMethod method;

}