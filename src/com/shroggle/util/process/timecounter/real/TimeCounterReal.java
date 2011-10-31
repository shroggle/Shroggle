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

import com.shroggle.util.process.timecounter.TimeCounter;

/**
 * @author Artem Stasuk
 * @see com.shroggle.util.process.timecounter.real.TimeCounterCreatorReal
 */
class TimeCounterReal implements TimeCounter {

    TimeCounterReal(final TimeCounterResultReal result) {
        if (result == null) {
            throw new UnsupportedOperationException(
                    "Can't create time counter by null result!");
        }
        this.start = System.currentTimeMillis();
        this.result = result;
        this.result.start(this);
    }

    public void stop() {
        result.stop(this);
    }

    long getStart() {
        return start;
    }

    private final long start;
    private final TimeCounterResultReal result;

}