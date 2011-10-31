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
package com.shroggle.stresstest.util.process.timecounter.real;

import com.shroggle.util.process.timecounter.TimeCounter;
import com.shroggle.util.process.timecounter.TimeCounterCreator;
import com.shroggle.util.process.timecounter.real.TimeCounterCreatorReal;

/**
 * @author Artem Stasuk
 */
public class TimeCounterRealRun {

    public static void main(final String[] args) {
        final long start = System.currentTimeMillis();

        final TimeCounterCreator timeCounterCreator = new TimeCounterCreatorReal();

        final TimeCounter[] timeCounters = new TimeCounter[50000];
        for (int i = 0; i < timeCounters.length; i++) {
            timeCounters[i] = timeCounterCreator.create("i");
        }

        timeCounterCreator.getResults().iterator().next().getExecutingCount();
        timeCounterCreator.getResults().iterator().next().getExecutedTime();

        for (final TimeCounter timeCounter : timeCounters) {
            timeCounter.stop();
        }

        timeCounterCreator.getResults().iterator().next().getExecutingCount();
        timeCounterCreator.getResults().iterator().next().getExecutedTime();

        System.out.println("Executed time: " + (System.currentTimeMillis() - start) + " msec");
    }

}
