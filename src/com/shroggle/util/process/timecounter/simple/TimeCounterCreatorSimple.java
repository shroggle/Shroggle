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

import com.shroggle.util.process.timecounter.TimeCounter;
import com.shroggle.util.process.timecounter.TimeCounterCreator;
import com.shroggle.util.process.timecounter.TimeCounterResult;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Count time only for stopped counters.
 *
 * @author Artem Stasuk
 */
public class TimeCounterCreatorSimple implements TimeCounterCreator {

    @Override
    public TimeCounter create(final String name) {
        TimeCounterResultSimple resultSimple = (TimeCounterResultSimple) resultByNames.get(name);
        if (resultSimple == null) {
            resultSimple = new TimeCounterResultSimple(name);
            final TimeCounterResultSimple oldResultSimple =
                    (TimeCounterResultSimple) resultByNames.putIfAbsent(name, resultSimple);
            if (oldResultSimple != null) {
                resultSimple = oldResultSimple;
            }
        }
        return new TimeCounterSimple(resultSimple);
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
