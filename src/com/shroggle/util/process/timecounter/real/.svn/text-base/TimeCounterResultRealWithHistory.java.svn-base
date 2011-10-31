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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
public class TimeCounterResultRealWithHistory extends TimeCounterResultReal {

    TimeCounterResultRealWithHistory(final String name) {
        super(name);
    }

    @Override
    synchronized void stop(final TimeCounterReal counter) {
        executedHistory.add(System.currentTimeMillis() - counter.getStart());
        super.stop(counter);
    }

    @Override
    public List<Long> getExecutedHistory() {
        return executedHistory;
    }

    private final List<Long> executedHistory = new ArrayList<Long>();
}
