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
package com.shroggle.presentation.system;

import com.shroggle.presentation.Action;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.process.timecounter.*;
import net.sourceforge.stripes.action.*;

import java.util.*;

/**
 * @author Artem Stasuk
 */
@UrlBinding("/system/timeCounterResults.action")
public class TimeCounterResultsAction extends Action {

    @DefaultHandler
    public Resolution execute() {
        results = new ArrayList<TimeCounterResult>(
                ServiceLocator.getTimeCounterCreator().getResults());

        if (!StringUtil.isNullOrEmpty(filter)) {
            final Iterator<TimeCounterResult> iterator = results.iterator();
            while (iterator.hasNext()) {
                if (!iterator.next().getName().contains(filter)) {
                    iterator.remove();
                }
            }
        }

        Comparator<TimeCounterResult> comparator = TimeCounterResultComparatorByAverage.instance;
        if ("executedTime".equals(order)) {
            comparator = TimeCounterResultComparatorByTotal.instance;
        } else if ("executedCount".equals(order)) {
            comparator = TimeCounterResultComparatorByTotalCount.instance;
        }
        Collections.sort(results, comparator);

        final TimeCounterResultManual total = new TimeCounterResultManual();
        total.setName("Total");
        for (final TimeCounterResult result : results) {
            total.setExecutedCount(total.getExecutedCount() + result.getExecutedCount());
            total.setExecutingCount(total.getExecutingCount() + result.getExecutingCount());
            total.setExecutedTime(total.getExecutedTime() + result.getExecutedTime());
        }
        results.add(0, total);

        return new ForwardResolution("/system/timeCounterResults.jsp");
    }

    public Resolution clear() {
        ServiceLocator.getTimeCounterCreator().clear();
        return execute();
    }

    public long getStartTime() {
        return startTime;
    }

    public Collection<TimeCounterResult> getResults() {
        return results;
    }

    public void setOrder(final String order) {
        this.order = order;
    }

    public void setFilter(final String filter) {
        this.filter = filter;
    }

    private List<TimeCounterResult> results;
    private String filter;
    private String order;
    private static final long startTime = System.currentTimeMillis();

}
