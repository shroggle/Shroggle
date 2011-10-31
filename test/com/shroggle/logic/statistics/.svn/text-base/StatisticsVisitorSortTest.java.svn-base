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

package com.shroggle.logic.statistics;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Page;
import com.shroggle.entity.User;
import com.shroggle.logic.paginator.Paginator;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * author: dmitry.solomadin
 * date: 07.11.2008
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class StatisticsVisitorSortTest {
    private StatisticsVisitorSort statisticsVisitorSort = new StatisticsVisitorSort();

    @Test
    public void testConsistency() {
        User account = TestUtil.createUser();
        Page page = TestUtil.initSitesForStatisticsTests().get(1).getPages().get(1);

        final Paginator paginator = statisticsVisitorSort.sort(page, StatisticsSortType.VISITOR_ID,
                false, StatisticsTimePeriodType.ALL_TIME, Paginator.getFirstPageNumber());
        List<VisitorTrafficInfo> visitorTrafficInfo = paginator.getItems();
        Assert.assertEquals(9, visitorTrafficInfo.get(0).getHits());
        Assert.assertEquals(100, visitorTrafficInfo.get(0).getTime());
        Assert.assertEquals(3, visitorTrafficInfo.get(0).getPageVisitorId());
        Assert.assertEquals("2_my_url_visit_1", visitorTrafficInfo.get(0).getRefURLs().get(0));
    }

    @Test
    public void testSortByVisitorId() {
        User account = TestUtil.createUser();
        Page page = TestUtil.initSitesForStatisticsTests().get(1).getPages().get(1);

        final Paginator paginator = statisticsVisitorSort.sort(page, StatisticsSortType.VISITOR_ID,
                true, StatisticsTimePeriodType.ALL_TIME, Paginator.getFirstPageNumber());
        List<VisitorTrafficInfo> visitorTrafficInfo = paginator.getItems();
        Assert.assertEquals(4, visitorTrafficInfo.get(0).getPageVisitorId());
        Assert.assertEquals(3, visitorTrafficInfo.get(1).getPageVisitorId());
    }

    @Test
    public void testSortByTime() {
        User account = TestUtil.createUser();
        Page page = TestUtil.initSitesForStatisticsTests().get(1).getPages().get(1);

        final Paginator paginator = statisticsVisitorSort.sort(page, StatisticsSortType.TIME,
                true, StatisticsTimePeriodType.ALL_TIME, Paginator.getFirstPageNumber());
        List<VisitorTrafficInfo> visitorTrafficInfo = paginator.getItems();
        Assert.assertEquals(100, visitorTrafficInfo.get(0).getTime());
        Assert.assertEquals(100, visitorTrafficInfo.get(1).getTime());
    }

    @Test
    public void testSortByHits() {
        User account = TestUtil.createUser();
        Page page = TestUtil.initSitesForStatisticsTests().get(1).getPages().get(1);

        final Paginator paginator = statisticsVisitorSort.sort(page, StatisticsSortType.HITS,
                true, StatisticsTimePeriodType.ALL_TIME, Paginator.getFirstPageNumber());
        List<VisitorTrafficInfo> visitorTrafficInfo = paginator.getItems();
        Assert.assertEquals(9, visitorTrafficInfo.get(0).getHits());
        Assert.assertEquals(3, visitorTrafficInfo.get(1).getHits());
    }
}
