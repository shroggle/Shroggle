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
package com.shroggle.util.process.timecounter.rrd;

import com.shroggle.util.process.timecounter.TimeCounter;
import com.shroggle.util.process.timecounter.TimeCounterCreator;
import com.shroggle.util.process.timecounter.TimeCounterResult;

import java.util.Collection;

/**
 * @author Artem Stasuk
 */
public class TimeCounterCreatorRrd implements TimeCounterCreator {

    public TimeCounterCreatorRrd(final TimeCounterCreator creator) {
        this.creator = creator;
        new TimeCounterThreadRrd(creator).start();
    }

    @Override
    public TimeCounter create(final String name) {
        return creator.create(name);
    }

    @Override
    public Collection<TimeCounterResult> getResults() {
        return creator.getResults();
    }

    /**
     * For RRD by clear we can clear db, but it's stupid operation,
     * and we don't want it.
     */
    @Override
    public void clear() {
        creator.clear();
    }

    private final TimeCounterCreator creator;

}
