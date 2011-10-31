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
public class ClassesFilterAnd implements ClassesFilter {

    public ClassesFilterAnd(final ClassesFilter... filters) {
        if (filters == null) {
            throw new IllegalArgumentException("Null classes filter!");
        }

        for (final ClassesFilter filter : filters) {
            if (filter == null) {
                throw new IllegalArgumentException("Null classes filter!");
            }
        }

        this.filters = filters;
    }

    @Override
    public boolean accept(final Class cClass) {
        for (final ClassesFilter filter : filters) {
            if (!filter.accept(cClass)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return ClassesFilterAnd.class.getSimpleName() + " {filters: " + Arrays.asList(filters) + "}";
    }

    private final ClassesFilter[] filters;

}
