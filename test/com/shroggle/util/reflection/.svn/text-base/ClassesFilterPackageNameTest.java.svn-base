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

import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Artem Stasuk
 */
public class ClassesFilterPackageNameTest {

    @Test(expected = IllegalArgumentException.class)
    public void createWithNull() {
        new ClassesFilterPackageName(null);
    }

    @Test
    public void create() {
        new ClassesFilterPackageName("aa");
    }

    @Test
    public void acceptStartWith() {
        final ClassesFilter classesFilter = new ClassesFilterPackageName("com.shroggle");
        Assert.assertTrue(classesFilter.accept(ClassesFilterPackageNameTest.class));
    }

    @Test
    public void acceptOnEmpty() {
        final ClassesFilter classesFilter = new ClassesFilterPackageName("");
        Assert.assertTrue(classesFilter.accept(String.class));
    }

    @Test
    public void accept() {
        final ClassesFilter classesFilter = new ClassesFilterPackageName("com.shroggle.util.reflection");
        Assert.assertTrue(classesFilter.accept(ClassesFilterPackageNameTest.class));
    }

    @Test
    public void acceptNo() {
        final ClassesFilter classesFilter = new ClassesFilterPackageName("com.shroggle1");
        Assert.assertFalse(classesFilter.accept(ClassesFilterPackageNameTest.class));
    }

}
