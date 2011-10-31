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
package com.shroggle.util.testspeed;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Artem Stasuk
 */
public class CheckPerformanceStatistics {

    void add(final CheckPerformanceStatisticsItem item) {
        items.add(item);
    }

    public List<CheckPerformanceStatisticsItem> get() {
        return Collections.unmodifiableList(items);
    }

    private final List<CheckPerformanceStatisticsItem> items = new CopyOnWriteArrayList<CheckPerformanceStatisticsItem>();

}