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
package com.shroggle.entity;


import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Balakirev Anatoliy
 */
public class CoordinateTest {

    @Test
    public void testCreate_with() {
        Coordinate coordinate = new Coordinate(Country.UA, 1.0, 2.0, "123");
        Assert.assertEquals("123", coordinate.getZip());
        Assert.assertEquals(Country.UA, coordinate.getCountry());
        Assert.assertEquals(1.0, coordinate.getLatitude());
        Assert.assertEquals(2.0, coordinate.getLongitude());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreate_withoutZip() {
        new Coordinate(Country.UA, 1.0, 2.0, null);
    }

    @Test
    public void testCreate_withoutCountry() {
        Coordinate coordinate = new Coordinate(null, 1.0, 2.0, "123");

        Assert.assertEquals("123", coordinate.getZip());
        Assert.assertEquals(null, coordinate.getCountry());
        Assert.assertEquals(1.0, coordinate.getLatitude());
        Assert.assertEquals(2.0, coordinate.getLongitude());
    }

    @Test
    public void testToStringArray() throws Exception {
        Coordinate coordinate = new Coordinate(Country.US, 1.0, 2.0, "123");
        String[] array = coordinate.toStringArray();
        Assert.assertEquals(4, array.length);

        Assert.assertEquals("123", array[0]);
        Assert.assertEquals("US", array[1]);
        Assert.assertEquals("1.0", array[2]);
        Assert.assertEquals("2.0", array[3]);
    }

    @Test
    public void testToStringArrayWithNullCountry() throws Exception {
        Coordinate coordinate = new Coordinate(null, 1.0, 2.0, "123");
        String[] array = coordinate.toStringArray();
        Assert.assertEquals(4, array.length);

        Assert.assertEquals("123", array[0]);
        Assert.assertEquals(null, array[1]);
        Assert.assertEquals("1.0", array[2]);
        Assert.assertEquals("2.0", array[3]);
    }

    @Test
    public void testToStringArrayWithNullLatitude() throws Exception {
        Coordinate coordinate = new Coordinate(Country.US, null, 2.0, "123");
        String[] array = coordinate.toStringArray();
        Assert.assertEquals(4, array.length);

        Assert.assertEquals("123", array[0]);
        Assert.assertEquals("US", array[1]);
        Assert.assertEquals(null, array[2]);
        Assert.assertEquals("2.0", array[3]);
    }

    @Test
    public void testToStringArrayWithNullLongitude() throws Exception {
        Coordinate coordinate = new Coordinate(Country.US, 1.0, null, "123");
        String[] array = coordinate.toStringArray();
        Assert.assertEquals(4, array.length);

        Assert.assertEquals("123", array[0]);
        Assert.assertEquals("US", array[1]);
        Assert.assertEquals("1.0", array[2]);
        Assert.assertEquals(null, array[3]);
    }

    @Test
    public void testIsValid() {
        Coordinate coordinate = new Coordinate(Country.UA, 1.0, 2.0, "123");
        Assert.assertTrue(coordinate.isValid());
    }

    @Test
    public void testIsValid_withoutCountry() {
        Coordinate coordinate = new Coordinate(null, 1.0, 2.0, "123");
        Assert.assertFalse(coordinate.isValid());
    }

    @Test
    public void testIsValid_withoutLatitude() {
        Coordinate coordinate = new Coordinate(Country.US, null, 2.0, "123");
        Assert.assertFalse(coordinate.isValid());
    }

    @Test
    public void testIsValid_withoutLongitude() {
        Coordinate coordinate = new Coordinate(Country.US, 1.0, null, "123");
        Assert.assertFalse(coordinate.isValid());
    }
}
