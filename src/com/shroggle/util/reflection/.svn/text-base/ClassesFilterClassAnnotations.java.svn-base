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

import java.util.Arrays;

/**
 * @author Artem Stasuk
 */
public class ClassesFilterClassAnnotations implements ClassesFilter {

    public ClassesFilterClassAnnotations(final Class... annotationClasses) {
        if (annotationClasses == null) {
            throw new IllegalArgumentException("Null annotation classes!");
        }

        for (final Class annotationClass : annotationClasses) {
            if (annotationClass == null) {
                throw new IllegalArgumentException("Null annotation classes!");
            }

            if (!annotationClass.isAnnotation()) {
                throw new IllegalArgumentException("Not annotation class: " + annotationClass);
            }
        }

        this.annotationClasses = annotationClasses;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean accept(final Class cClass) {
        for (final Class annotationClass : annotationClasses) {
            if (cClass.getAnnotation(annotationClass) != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return ClassesFilterClassAnnotations.class.getSimpleName() + " {annotationClasses: " + Arrays.asList(annotationClasses) + "}";
    }

    private final Class[] annotationClasses;

}
