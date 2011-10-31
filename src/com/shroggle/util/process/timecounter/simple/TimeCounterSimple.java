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

/**
 * @author Artem Stasuk
 */
class TimeCounterSimple implements TimeCounter {

    TimeCounterSimple(final TimeCounterResultSimple resultSimple) {
        if (resultSimple == null) {
            throw new UnsupportedOperationException(
                    "Can't create time counter by null result!");
        }
        this.start = System.currentTimeMillis();
        this.resultSimple = resultSimple;
        this.resultSimple.start();
    }

    public void stop() {
        if (stoped) {
            throw new UnsupportedOperationException(
                    "Can't stop already stopped time counter!");
        }
        stoped = true;
        resultSimple.stop(System.currentTimeMillis() - start);
    }

    private boolean stoped = false;
    private final long start;
    private final TimeCounterResultSimple resultSimple;

}
