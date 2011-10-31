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

import com.shroggle.util.process.timecounter.simple.TimeCounterResultSimple;
import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Artem Stasuk
 */
public class TimeCounterResultSimpleConcurentTest {

    @Test
    public void add() throws InterruptedException {
        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {

                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        result.stop(10);
                    }
                }

            });
            threads[i].start();
        }
        for (final Thread thread : threads) {
            thread.join();
        }
        Assert.assertEquals(1000L * 10L * threads.length, result.getExecutedTime());
        Assert.assertEquals(1000L * threads.length, result.getExecutedCount());
    }

    private final TimeCounterResultSimple result = new TimeCounterResultSimple("t");

}
