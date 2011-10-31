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
import com.shroggle.util.testspeed.CheckPerformanceStatisticsItem;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.List;

/**
 * @author Artem Stasuk
 */
@UrlBinding("/system/checkPerformanceData.action")
public class CheckPerformanceDataAction extends Action {

    @DefaultHandler
    public Resolution show() {
        items = ServiceLocator.getCheckPerformance().getStatistics().get();
        work = ServiceLocator.getCheckPerformance().isWork();
        return new ForwardResolution("/system/checkPerformanceData.jsp");
    }

    public Resolution start() {
        ServiceLocator.getCheckPerformance().start(threadCount, requestDelay);

        return show();
    }

    public Resolution stop() {
        ServiceLocator.getCheckPerformance().stop();

        return show();
    }

    public List<CheckPerformanceStatisticsItem> getItems() {
        return items;
    }

    public boolean isWork() {
        return work;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public void setRequestDelay(long requestDelay) {
        this.requestDelay = requestDelay;
    }

    private List<CheckPerformanceStatisticsItem> items;
    private boolean work;
    private long requestDelay;
    private int threadCount;

}