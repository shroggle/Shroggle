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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Artem Stasuk
 */
public class PersistanceIdFieldsGetter {

    public Map<Class, Field> get(final List<Class> entityClasses) {
        final Map<Class, Field> idFieldByEntityClasses = new HashMap<Class, Field>();
        for (final Class entityClass : entityClasses) {
            idFieldByEntityClasses.put(entityClass, persistanceIdFieldGetter.get(entityClass));
        }
        return idFieldByEntityClasses;
    }

    private final PersistanceIdFieldGetter persistanceIdFieldGetter = new PersistanceIdFieldGetter();

}
