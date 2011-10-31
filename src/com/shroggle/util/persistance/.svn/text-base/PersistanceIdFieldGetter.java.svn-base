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
package com.shroggle.util.persistance;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author Artem Stasuk
 */
public class PersistanceIdFieldGetter {

    public Field get(final Class entityClass) {
        return getInternal(entityClass, true);
    }

    public Field getInternal(final Class entityClass, final boolean firstCall) {
        if (entityClass == null) {
            throw new UnsupportedOperationException(
                    "Can't find id field in null entity class!");
        }
        if (firstCall && Modifier.isAbstract(entityClass.getModifiers())) {
            return null;
        }

        final Field[] fields = entityClass.getDeclaredFields();
        for (final Field field : fields) {
            if (field.getAnnotation(Id.class) != null && field.getType() == int.class) {
                return field;
            }
        }
        if (entityClass.getSuperclass() == null) {
            return null;
        }
        return getInternal(entityClass.getSuperclass(), false);
    }

}
