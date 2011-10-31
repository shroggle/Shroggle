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
package com.shroggle.util.testspeed;

/**
 * @author Artem Stasuk
 */
public class CheckPerformance {

    public void start(final int threadCount, final long requestDelay) {
        thread = new CheckPerformanceThread(statistics, threadCount, requestDelay);
        thread.start();
    }

    public void stop() {
        thread.interrupt();
    }

    public boolean isWork() {
        return thread != null && thread.isAlive();
    }

    public CheckPerformanceStatistics getStatistics() {
        return statistics;
    }

    private CheckPerformanceStatistics statistics = new CheckPerformanceStatistics();
    private volatile CheckPerformanceThread thread;

}
