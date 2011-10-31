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
public class ClassesFilterAndTest {

    @Test
    public void createEmpty() {
        new ClassesFilterAnd();
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNull() {
        new ClassesFilterAnd(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNullNull() {
        new ClassesFilterAnd(null, null);
    }

    @Test
    public void acceptEmpty() {
        Assert.assertTrue(new ClassesFilterAnd().accept(ClassesFilterAndTest.class));
        Assert.assertTrue(new ClassesFilterAnd().accept(String.class));
    }

    @Test
    public void acceptOneFalse() {
        Assert.assertFalse(new ClassesFilterAnd(new ClassesFilter() {

            @Override
            public boolean accept(final Class cClass) {
                return false;
            }

        }, new ClassesFilter() {

            @Override
            public boolean accept(final Class cClass) {
                return true;
            }

        }).accept(ClassesFilterAndTest.class));
    }

    @Test
    public void acceptAllTrue() {
        Assert.assertTrue(new ClassesFilterAnd(new ClassesFilter() {

            @Override
            public boolean accept(final Class cClass) {
                return true;
            }

        }, new ClassesFilter() {

            @Override
            public boolean accept(final Class cClass) {
                return true;
            }

        }).accept(ClassesFilterAndTest.class));
    }

}
