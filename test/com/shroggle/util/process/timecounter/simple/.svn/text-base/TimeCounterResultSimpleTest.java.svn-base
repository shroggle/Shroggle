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
package com.shroggle.util.process.timecounter.simple;

import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Artem Stasuk
 */
public class TimeCounterResultSimpleTest {

    @Test
    public void getCountAndTime() {
        TimeCounterResultSimple result = new TimeCounterResultSimple("f");
        Assert.assertEquals(0, result.getExecutedCount());
        Assert.assertEquals(0L, result.getExecutedTime());
        Assert.assertNull(result.getExecutingTime());
        result.stop(12);
        Assert.assertEquals(1, result.getExecutedCount());
        Assert.assertNull(result.getExecutingTime());
        Assert.assertEquals(12L, result.getExecutedTime());
    }

    @Test
    public void getName() {
        TimeCounterResultSimple result = new TimeCounterResultSimple("f");
        Assert.assertEquals("f", result.getName());
    }

    @Test
    public void create() {
        new TimeCounterResultSimple("f");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createWithNullName() {
        new TimeCounterResultSimple(null);
    }

}
