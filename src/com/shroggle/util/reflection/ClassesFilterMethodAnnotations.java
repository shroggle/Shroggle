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

import java.lang.reflect.Method;

/**
 * @author Artem Stasuk
 */
public class ClassesFilterMethodAnnotations implements ClassesFilter {

    public ClassesFilterMethodAnnotations(final Class... annotationClasses) {
        if (annotationClasses == null) {
            throw new IllegalArgumentException("Null annotation classes!");
        }

        for (final Class annotationClass : annotationClasses) {
            if (annotationClass == null) {
                throw new IllegalArgumentException("Null annotation classes!");
            }

            if (!annotationClass.isAnnotation()) {
                throw new IllegalArgumentException("Not annotation class!");
            }
        }

        this.annotationClasses = annotationClasses;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean accept(final Class cClass) {
        for (final Method method : cClass.getMethods()) {
            for (final Class annotationClass : annotationClasses) {
                if (method.getAnnotation(annotationClass) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    private final Class[] annotationClasses;

}
