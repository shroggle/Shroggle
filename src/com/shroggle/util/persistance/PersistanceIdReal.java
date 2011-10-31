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

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Artem Stasuk
 */
public class PersistanceIdReal implements PersistanceId {

    public PersistanceIdReal(
            final Map<Class, Field> idFieldByEntityClasses,
            final Map<Field, Integer> maxIdByIdFields) {
        if (idFieldByEntityClasses == null) {
            throw new UnsupportedOperationException(
                    "Can't create real by null id field by classes!");
        }
        if (maxIdByIdFields == null) {
            throw new UnsupportedOperationException(
                    "Can't create real by null max id by classes!");
        }

        this.fieldIdByEntityClasses = Collections.unmodifiableMap(idFieldByEntityClasses);
        final Map<Field, AtomicInteger> tempIdByIdFields = new HashMap<Field, AtomicInteger>();
        for (final Class entityClass : idFieldByEntityClasses.keySet()) {
            final Field field = idFieldByEntityClasses.get(entityClass);
            if (field == null) continue;
            field.setAccessible(true);

            AtomicInteger entityId = tempIdByIdFields.get(field);
            if (entityId == null) {
                Integer maxEntityId = maxIdByIdFields.get(field);
                if (maxEntityId == null) {
                    maxEntityId = 0;
                }
                entityId = new AtomicInteger(maxEntityId + 1);
            }
            tempIdByIdFields.put(field, entityId);
        }
        idByIdFields = Collections.unmodifiableMap(tempIdByIdFields);
    }

    public void set(final Object entity) {
        if (entity == null) {
            throw new UnsupportedOperationException("Can't set id for null entity!");
        }

        final Field field = fieldIdByEntityClasses.get(entity.getClass());
        if (field == null) return;
        final AtomicInteger entityId = idByIdFields.get(field);

        try {
            // Check if entity alredy has id
            final Object currentEntityId = field.get(entity);
            if (currentEntityId instanceof Integer) {
                if (((Integer) currentEntityId) > 0) return;
            }

            field.set(entity, entityId.getAndIncrement());
        } catch (IllegalAccessException exception) {
            throw new UnsupportedOperationException(
                    "Can't set id to entity!", exception);
        }
    }

    public Object get(final Object entity) {
        if (entity == null) {
            throw new UnsupportedOperationException("Can't set id for null entity!");
        }

        final Field field = fieldIdByEntityClasses.get(entity.getClass());
        if (field == null) return null;
        final AtomicInteger entityId = idByIdFields.get(field);

        return entityId.getAndIncrement();
    }

    private final Map<Class, Field> fieldIdByEntityClasses;
    private final Map<Field, AtomicInteger> idByIdFields;

}
