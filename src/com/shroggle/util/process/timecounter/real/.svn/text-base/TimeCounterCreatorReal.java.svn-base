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

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.process.timecounter.TimeCounter;
import com.shroggle.util.process.timecounter.TimeCounterCreator;
import com.shroggle.util.process.timecounter.TimeCounterResult;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Count time for executed and executing timers. But need more resources.
 * If don't need executing timers time use simple counters it's more fast and use less memory.
 *
 * @author Artem Stasuk
 * @see com.shroggle.util.process.timecounter.simple.TimeCounterCreatorSimple
 */
public class TimeCounterCreatorReal implements TimeCounterCreator {

    @Override
    public TimeCounter create(final String name) {
        TimeCounterResultReal result = (TimeCounterResultReal) resultByNames.get(name);
        if (result == null) {
            result = ServiceLocator.getConfigStorage().get().isUseStatisticsExecutedHistory() ?
                    new TimeCounterResultRealWithHistory(name) : new TimeCounterResultReal(name);
            final TimeCounterResultReal oldResult =
                    (TimeCounterResultReal) resultByNames.putIfAbsent(name, result);
            if (oldResult != null) {
                result = oldResult;
            }
        }

        return new TimeCounterReal(result);
    }

    @Override
    public Collection<TimeCounterResult> getResults() {
        return Collections.unmodifiableCollection(resultByNames.values());
    }

    @Override
    public void clear() {
        resultByNames.clear();
    }

    private final ConcurrentMap<String, TimeCounterResult> resultByNames =
            new ConcurrentHashMap<String, TimeCounterResult>();

}