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
 * @author Artem Stasuk
 */
class SynchronizeByCreatorClassParameter implements SynchronizeByCreator {

    public SynchronizeByCreatorClassParameter(
            final Object object, final SynchronizeByClassProperty byClassProperty) {
        if (byClassProperty == null) {
            throw new UnsupportedOperationException(
                    "Can't create synchronize by null class property!");
        }
        final Class objectClass = object.getClass();
        try {
            field = new FarField(byClassProperty.entityIdFieldPath(), objectClass);
        } catch (NoSuchFieldException exception) {
            throw new UnsupportedOperationException(
                    "Can't find field " + byClassProperty.entityIdFieldPath()
                            + " in " + objectClass, exception);
        }
        this.deepParent = byClassProperty.deepParent();
        this.entityClass = byClassProperty.entityClass();
        this.method = byClassProperty.method();
    }

    public SynchronizeRequest create(
            final Object object, final Object... parameters) {
        try {
            Object entityId = field.get(object);
            return new SynchronizeRequestEntity(entityClass, method, entityId, deepParent);
        } catch (IllegalAccessException exception) {
            throw new UnsupportedOperationException(
                    "Can't get value from field " + field
                            + " for object " + object, exception);
        }
    }

    private final FarField field;
    private final int deepParent;
    private final Class entityClass;
    private final SynchronizeMethod method;

}
