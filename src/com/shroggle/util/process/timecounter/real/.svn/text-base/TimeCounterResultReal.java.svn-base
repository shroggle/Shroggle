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

import com.shroggle.util.process.timecounter.TimeCounterResult;

import java.util.*;

/**
 * @author Artem Stasuk
 */
class TimeCounterResultReal implements TimeCounterResult {

    TimeCounterResultReal(final String name) {
        if (name == null) {
            throw new UnsupportedOperationException(
                    "Can't create time counter result by null name!");
        }
        this.name = name;
    }

    @Override
    public long getExecutedTime() {
        return executedTime;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public synchronized int getExecutingCount() {
        return executing.size();
    }

    @Override
    public synchronized Long getExecutingTime() {
        long time = 0;

        final long now = System.currentTimeMillis();
        for (final TimeCounterReal counter : executing) {
            time += now - counter.getStart();
        }

        return time;
    }

    @Override
    public List<Long> getExecutedHistory() {
        return Collections.emptyList();
    }

    @Override
    public synchronized int getExecutedCount() {
        return executedCount;
    }

    synchronized void start(final TimeCounterReal counter) {
        executing.add(counter);
    }

    synchronized void stop(final TimeCounterReal counter) {
        if (!executing.remove(counter)) {
            throw new UnsupportedOperationException("Can't stop already stopped time counter!");
        }

        final long delta = System.currentTimeMillis() - counter.getStart();
        executedCount++;
        executedTime += delta;
    }

    private final String name;
    private long executedTime;
    private int executedCount;
    private final List<Long> executedTimes = new ArrayList<Long>();

    /**
     * For this collection use HashSet more better instead ArrayList, because
     * it has fast remove method. For example: we created 50000 time counter
     * and after stop it all and if we use ArrayList we has total execution time: 1156 msec
     * with HashSet only 85 msec.
     *
     * @see com.shroggle.util.process.timecounter.real.TimeCounterResultReal
     */
    private final Collection<TimeCounterReal> executing = new HashSet<TimeCounterReal>();

}