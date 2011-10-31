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

package com.shroggle.util;

import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stasuk Artem
 */
public class CollectionUtilTest {

    @Test(expected = NullPointerException.class)
    public void moveWithNullItem() {
        List<Object> objects = new ArrayList<Object>();
        objects.add(new Object());
        CollectionUtil.move(null, objects, 0);
    }

    @Test(expected = NullPointerException.class)
    public void moveWithNullItems() {
        CollectionUtil.move(new Object(), null, 0);
    }

    @Test
    public void moveFromMiddleToStart() {
        List<Object> objects = new ArrayList<Object>();
        final Object object0 = new Object();
        objects.add(object0);
        final Object object1 = new Object();
        objects.add(object1);
        final Object object2 = new Object();
        objects.add(object2);
        CollectionUtil.move(object1, objects, 0);

        Assert.assertEquals(object1, objects.get(0));
        Assert.assertEquals(object0, objects.get(1));
        Assert.assertEquals(object2, objects.get(2));
    }

    @Test
    public void moveFromStartToStratInListWithOneItem() {
        List<Object> objects = new ArrayList<Object>();
        final Object object0 = new Object();
        objects.add(object0);
        CollectionUtil.move(object0, objects, 0);

        Assert.assertEquals(object0, objects.get(0));
    }

    @Test
    public void moveFromStartToStart() {
        List<Object> objects = new ArrayList<Object>();
        final Object object0 = new Object();
        objects.add(object0);
        final Object object1 = new Object();
        objects.add(object1);
        final Object object2 = new Object();
        objects.add(object2);
        CollectionUtil.move(object0, objects, 0);

        Assert.assertEquals(object0, objects.get(0));
        Assert.assertEquals(object1, objects.get(1));
        Assert.assertEquals(object2, objects.get(2));
    }

    @Test
    public void moveFromStartToFinish() {
        List<Object> objects = new ArrayList<Object>();
        final Object object0 = new Object();
        objects.add(object0);
        final Object object1 = new Object();
        objects.add(object1);
        final Object object2 = new Object();
        objects.add(object2);
        CollectionUtil.move(object0, objects, 2);

        Assert.assertEquals(object0, objects.get(2));
        Assert.assertEquals(object1, objects.get(0));
        Assert.assertEquals(object2, objects.get(1));
    }

    @Test
    public void moveFromStartToBeforeLast() {
        List<Object> objects = new ArrayList<Object>();
        final Object object0 = new Object();
        objects.add(object0);
        final Object object1 = new Object();
        objects.add(object1);
        final Object object2 = new Object();
        objects.add(object2);
        CollectionUtil.move(object0, objects, 1);

        Assert.assertEquals(object1, objects.get(0));
        Assert.assertEquals(object0, objects.get(1));
        Assert.assertEquals(object2, objects.get(2));
    }

    @Test
    public void moveFromMiddleToFinish() {
        List<Object> objects = new ArrayList<Object>();
        final Object object0 = new Object();
        objects.add(object0);
        final Object object1 = new Object();
        objects.add(object1);
        final Object object2 = new Object();
        objects.add(object2);
        CollectionUtil.move(object1, objects, 2);

        Assert.assertEquals(object0, objects.get(0));
        Assert.assertEquals(object1, objects.get(2));
        Assert.assertEquals(object2, objects.get(1));
    }

    @Test
    public void moveFromMiddleToMoreFinish() {
        List<Object> objects = new ArrayList<Object>();
        final Object object0 = new Object();
        objects.add(object0);
        final Object object1 = new Object();
        objects.add(object1);
        final Object object2 = new Object();
        objects.add(object2);
        CollectionUtil.move(object1, objects, 3);

        Assert.assertEquals(object0, objects.get(0));
        Assert.assertEquals(object1, objects.get(2));
        Assert.assertEquals(object2, objects.get(1));
    }

    @Test
    public void moveFromMiddleToNegativePosition() {
        List<Object> objects = new ArrayList<Object>();
        final Object object0 = new Object();
        objects.add(object0);
        final Object object1 = new Object();
        objects.add(object1);
        final Object object2 = new Object();
        objects.add(object2);
        CollectionUtil.move(object1, objects, -1);

        Assert.assertEquals(object0, objects.get(1));
        Assert.assertEquals(object1, objects.get(0));
        Assert.assertEquals(object2, objects.get(2));
    }

    @Test
    public void moveFromMiddleToMoreVeryFinish() {
        List<Object> objects = new ArrayList<Object>();
        final Object object0 = new Object();
        objects.add(object0);
        final Object object1 = new Object();
        objects.add(object1);
        final Object object2 = new Object();
        objects.add(object2);
        CollectionUtil.move(object1, objects, 33);

        Assert.assertEquals(object0, objects.get(0));
        Assert.assertEquals(object1, objects.get(2));
        Assert.assertEquals(object2, objects.get(1));
    }

    @Test
    public void create() {
        new CollectionUtil();
    }

}
