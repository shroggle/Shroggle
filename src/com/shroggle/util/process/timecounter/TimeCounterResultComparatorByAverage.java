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

import java.util.Comparator;

/**
 * @author Artem Stasuk
 */
public class TimeCounterResultComparatorByAverage implements Comparator<TimeCounterResult> {

    public final static Comparator<TimeCounterResult> instance =
            new TimeCounterResultComparatorByAverage();

    public int compare(TimeCounterResult o1, TimeCounterResult o2) {
        final Double average1 = o1.getExecutedCount() == 0 ? 0 : (double) o1.getExecutedTime() / (double) o1.getExecutedCount();
        final Double average2 = o2.getExecutedCount() == 0 ? 0 : (double) o2.getExecutedTime() / (double) o2.getExecutedCount();
        return average2.compareTo(average1);
//        return new Long(o2.getExecutedTime()).compareTo(o1.getExecutedTime());
    }

    private TimeCounterResultComparatorByAverage() {

    }

}
