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
package com.shroggle.util.process.timecounter.real;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.util.process.ThreadUtil;
import com.shroggle.util.process.timecounter.TimeCounter;
import com.shroggle.util.process.timecounter.TimeCounterResult;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class TimeCounterCreatorRealTest {

    @Test
    public void getResults() {
        TimeCounterCreatorReal creator = new TimeCounterCreatorReal();
        creator.create("f");

        Assert.assertNotNull(creator.getResults());
        Assert.assertEquals(1, creator.getResults().size());
    }

    @Test
    public void worked() {
        TimeCounterCreatorReal creator = new TimeCounterCreatorReal();
        TimeCounter timeCounter = creator.create("f");
        ThreadUtil.sleep(11);

        Collection<TimeCounterResult> results = creator.getResults();

        Assert.assertEquals(1, results.iterator().next().getExecutingCount());
        Assert.assertTrue(results.iterator().next().getExecutingTime() >= 11);

        timeCounter.stop();

        results = creator.getResults();
        Assert.assertTrue(results.iterator().next().getExecutedTime() > 0);
        Assert.assertEquals(new Long(0), results.iterator().next().getExecutingTime());
        Assert.assertEquals(0, results.iterator().next().getExecutingCount());
        Assert.assertEquals("f", results.iterator().next().getName());
        Assert.assertEquals(1, results.iterator().next().getExecutedCount());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getResultsNotModificable() {
        TimeCounterCreatorReal creator = new TimeCounterCreatorReal();
        creator.create("f");

        creator.getResults().add(new TimeCounterResultReal("f"));
    }

}