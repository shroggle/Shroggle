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

/**
 * @author Artem Stasuk
 */
final class TimeCounterComposit implements TimeCounter {

    TimeCounterComposit(final TimeCounter[] counters) {
        this.counters = counters;
    }

    @Override
    public void stop() {
        for (final TimeCounter counter : counters) {
            counter.stop();
        }
    }

    private final TimeCounter[] counters;

}
