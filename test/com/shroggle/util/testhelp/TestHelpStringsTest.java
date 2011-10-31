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
package com.shroggle.util.testhelp;

import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Artem Stasuk
 */
public class TestHelpStringsTest {

    @Test
    public void add() {
        testHelpStrings.add("F");

        Assert.assertEquals(1, testHelpStrings.get().size());
        Assert.assertEquals("F", testHelpStrings.get().get(0));
    }

    @Test
    public void addFull() {
        testHelpStrings.add("F");
        testHelpStrings.add("A");

        Assert.assertEquals(2, testHelpStrings.get().size());
        Assert.assertEquals("F", testHelpStrings.get().get(0));
        Assert.assertEquals("A", testHelpStrings.get().get(1));
    }

    @Test
    public void addWithOver() {
        testHelpStrings.add("F");
        testHelpStrings.add("A");
        testHelpStrings.add("B");

        Assert.assertEquals(2, testHelpStrings.get().size());
        Assert.assertEquals("A", testHelpStrings.get().get(0));
        Assert.assertEquals("B", testHelpStrings.get().get(1));
    }

    @Test
    public void addMany() {
        testHelpStrings.add("F");
        testHelpStrings.add("A");
        testHelpStrings.add("B");
        testHelpStrings.add("1");
        testHelpStrings.add("2");

        Assert.assertEquals(2, testHelpStrings.get().size());
        Assert.assertEquals("1", testHelpStrings.get().get(0));
        Assert.assertEquals("2", testHelpStrings.get().get(1));
    }

    private final TestHelpStrings testHelpStrings = new TestHelpStrings(2);

}
