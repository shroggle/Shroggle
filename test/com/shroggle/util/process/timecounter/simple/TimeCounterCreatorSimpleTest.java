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

import com.shroggle.util.process.ThreadUtil;
import com.shroggle.util.process.timecounter.TimeCounter;
import com.shroggle.util.process.timecounter.TimeCounterResult;
import com.shroggle.util.process.timecounter.simple.TimeCounterCreatorSimple;
import com.shroggle.util.process.timecounter.simple.TimeCounterResultSimple;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Collection;

/**
 * @author Artem Stasuk 
 */
public class TimeCounterCreatorSimpleTest {

    @Test
    public void create() {
        TimeCounterCreatorSimple creator = new TimeCounterCreatorSimple();
        Assert.assertNotSame(creator.create("f"), creator.create("f"));
        Assert.assertNotSame(creator.create("f"), creator.create("g"));
    }

    @Test
    public void getResults() {
        TimeCounterCreatorSimple creator = new TimeCounterCreatorSimple();
        creator.create("f");

        Assert.assertNotNull(creator.getResults());
        Assert.assertEquals(1, creator.getResults().size());
    }

    @Test
    public void worked() {
        TimeCounterCreatorSimple creator = new TimeCounterCreatorSimple();
        TimeCounter timeCounter = creator.create("f");
        ThreadUtil.sleep(11);
        timeCounter.stop();

        Collection<TimeCounterResult> results = creator.getResults();
        Assert.assertTrue(results.iterator().next().getExecutedTime() > 0);
        Assert.assertEquals("f", results.iterator().next().getName());
        Assert.assertEquals(1, results.iterator().next().getExecutedCount());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getResultsNotModificable() {
        TimeCounterCreatorSimple creator = new TimeCounterCreatorSimple();
        creator.create("f");

        creator.getResults().add(new TimeCounterResultSimple("f"));
    }

}
