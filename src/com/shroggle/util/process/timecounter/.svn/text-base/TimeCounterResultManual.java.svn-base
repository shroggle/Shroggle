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
package com.shroggle.util.process.timecounter;

import java.util.Collections;
import java.util.List;

/**
 * @author Stasuk Artem
 */
public class TimeCounterResultManual implements TimeCounterResult {

    @Override
    public long getExecutedTime() {
        return executedTime;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getExecutingCount() {
        return executingCount;
    }

    @Override
    public int getExecutedCount() {
        return executedCount;
    }

    @Override
    public Long getExecutingTime() {
        return null;
    }

    @Override
    public List<Long> getExecutedHistory() {
        return Collections.emptyList();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExecutedTime(long executedTime) {
        this.executedTime = executedTime;
    }

    public void setExecutedCount(int executedCount) {
        this.executedCount = executedCount;
    }

    public void setExecutingCount(int executingCount) {
        this.executingCount = executingCount;
    }

    private String name;
    private int executingCount;
    private int executedCount;
    private long executedTime;

}
