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

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.reflection.ClassesFilterAnd;
import com.shroggle.util.reflection.ClassesFilterClassAnnotations;
import com.shroggle.util.reflection.ClassesFilterPackageName;

import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Artem Stasuk
 */
public final class PersistanceEntities {

    public static Map<Class, Field> getIdFieldsByClass() {
        return idFieldsByEntityClass;
    }

    public static List<Class> getClasses() {
        return entityClasses;
    }

    private static final List<Class> entityClasses;
    private static final Map<Class, Field> idFieldsByEntityClass;

    static {
        entityClasses = ServiceLocator.getClasses().get(
                new ClassesFilterAnd(
                        new ClassesFilterPackageName("com.shroggle"),
                        new ClassesFilterClassAnnotations(Entity.class)));
        final PersistanceIdFieldsGetter getter = new PersistanceIdFieldsGetter();
        idFieldsByEntityClass = Collections.unmodifiableMap(getter.get(entityClasses));
    }

}
