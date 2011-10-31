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
package com.shroggle.util.process.timecounter.composit;

import com.shroggle.util.process.timecounter.TimeCounter;
import com.shroggle.util.process.timecounter.TimeCounterCreator;
import com.shroggle.util.process.timecounter.TimeCounterCreatorEmpty;
import com.shroggle.util.process.timecounter.TimeCounterResult;

import java.util.Collection;

/**
 * @author Artem Stasuk
 */
public class TimeCounterCreatorComposit implements TimeCounterCreator {

    /**
     * @param creators - be careful only first counter will be use for getResults
     */
    public TimeCounterCreatorComposit(final TimeCounterCreator[] creators) {
        if (creators == null || creators.length == 0) {
            this.creators = new TimeCounterCreator[]{new TimeCounterCreatorEmpty()};
        } else {
            this.creators = creators;
        }
    }

    @Override
    public TimeCounter create(final String name) {
        final TimeCounter[] counters = new TimeCounter[creators.length];
        for (int i = 0; i < counters.length; i++) {
            counters[i] = creators[i].create(name);
        }

        return new TimeCounterComposit(counters);
    }

    @Override
    public Collection<TimeCounterResult> getResults() {
        return creators[0].getResults();
    }

    @Override
    public void clear() {
        for (final TimeCounterCreator creator : creators) {
            creator.clear();
        }
    }

    private final TimeCounterCreator[] creators;

}
