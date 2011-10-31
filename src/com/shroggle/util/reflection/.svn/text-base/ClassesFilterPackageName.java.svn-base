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

/**
 * @author Artem Stasuk
 */
public class ClassesFilterPackageName implements ClassesFilter {

    public ClassesFilterPackageName(final String packageName) {
        if (packageName == null) {
            throw new IllegalArgumentException("Null package name!");
        }

        this.packageName = packageName;
    }

    @Override
    public boolean accept(final Class cClass) {
        return cClass.getName().startsWith(packageName);
    }

    @Override
    public String toString() {
        return ClassesFilterPackageName.class.getSimpleName() + " {packageName: " + packageName + "}";
    }

    private final String packageName;

}
