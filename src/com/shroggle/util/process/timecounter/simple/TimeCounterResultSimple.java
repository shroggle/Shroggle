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

import com.shroggle.util.process.timecounter.TimeCounterResult;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Artem Stasuk
 */
class TimeCounterResultSimple implements TimeCounterResult {

    TimeCounterResultSimple(final String name) {
        if (name == null) {
            throw new UnsupportedOperationException(
                    "Can't create time counter result by null name!");
        }
        this.name = name;
    }

    @Override
    public long getExecutedTime() {
        return executedTime.get();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getExecutingCount() {
        return executingCount.get();
    }

    @Override
    public int getExecutedCount() {
        return executedCount.get();
    }

    @Override
    public Long getExecutingTime() {
        return null;
    }

    @Override
    public List<Long> getExecutedHistory() {
        return Collections.emptyList();
    }

    void start() {
        executingCount.incrementAndGet();
    }

    void stop(final long delta) {
        executingCount.decrementAndGet();
        executedCount.incrementAndGet();
        executedTime.addAndGet(delta);
    }

    private final String name;
    private final AtomicLong executedTime = new AtomicLong();
    private final AtomicInteger executingCount = new AtomicInteger();
    private final AtomicInteger executedCount = new AtomicInteger();

}
