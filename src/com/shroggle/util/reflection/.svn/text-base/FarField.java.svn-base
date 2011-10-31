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
package com.shroggle.util.reflection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Artem Stasuk
 */
public class FarField {

    public FarField(final String path, final Class farClass) throws NoSuchFieldException {
        Class fieldClass = farClass;
        final List<Field> tempFields = new ArrayList<Field>();
        for (final String fieldName : path.split("\\.")) {
            final Field field = fieldClass.getDeclaredField(fieldName);
            fieldClass = field.getType();
            field.setAccessible(true);
            tempFields.add(field);
        }
        this.fields = Collections.unmodifiableList(tempFields);
    }

    public Object get(final Object farObject) throws IllegalAccessException {
        Object object = farObject;
        for (final Field field : fields) {
            object = field.get(object);
        }
        return object;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FarField with path: ");
        for (final Field field : fields) {
            stringBuilder.append(field);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    /**
     * This is a path from start class field to destination field. It is unmodificable. For all field
     * in this list constructor set accessible.
     */
    private final List<Field> fields;

}
