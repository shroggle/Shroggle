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

import com.shroggle.entity.User;
import junit.framework.Assert;
import org.junit.Test;

import javax.persistence.Entity;

/**
 * @author Artem Stasuk
 */
public class ClassesFilterClassAnnotationsTest {

    @Test
    public void createWithEmpty() {
        new ClassesFilterClassAnnotations();
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithEmptyNull() {
        new ClassesFilterClassAnnotations(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithEmptyNullNull() {
        new ClassesFilterClassAnnotations(null, null);
    }

    @Test
    public void create() {
        new ClassesFilterClassAnnotations(Test.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNotAnnotation() {
        new ClassesFilterClassAnnotations(ClassesFilterClassAnnotationsTest.class);
    }

    @Test
    public void acceptWithoutAnnotation() {
        Assert.assertFalse(new ClassesFilterClassAnnotations(Test.class).accept(Object.class));
    }

    @Test
    public void accept() {
        Assert.assertTrue(new ClassesFilterClassAnnotations(Entity.class).accept(User.class));
    }

    @Test
    public void acceptOnFromMany() {
        Assert.assertTrue(new ClassesFilterClassAnnotations(Entity.class, Test.class).accept(User.class));
    }

    @Test
    public void acceptEmpty() {
        Assert.assertFalse(new ClassesFilterClassAnnotations().accept(User.class));
    }

}
