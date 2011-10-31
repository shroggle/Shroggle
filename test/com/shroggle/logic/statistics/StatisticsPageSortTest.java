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
import com.shroggle.logic.paginator.Paginator;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * author: dmitry.solomadin
 * date: 07.11.2008
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class StatisticsPageSortTest {
    private StatisticsPageSort statisticsPageSort = new StatisticsPageSort();

    @Test
    public void testConsistency() {
        TestUtil.createUser();
        List<Page> pages = new ArrayList<Page>();
        for (Page page : TestUtil.initSitesForStatisticsTests().get(1).getPages()) {
            pages.add(page);
        }

        
        final Paginator<PageTrafficInfo> paginator = statisticsPageSort.sort(pages, StatisticsSortType.NAME, false,
                StatisticsTimePeriodType.ALL_TIME, Paginator.getFirstPageNumber());
        List<PageTrafficInfo> pageTrafficInfo = paginator.getItems();
        Assert.assertEquals(pageTrafficInfo.get(0).getPageName(), "1_page");
        Assert.assertEquals(pageTrafficInfo.get(0).getHits(), 5);
        Assert.assertEquals(pageTrafficInfo.get(0).getVisits(), 1);
        Assert.assertEquals(pageTrafficInfo.get(0).getTime(), 500);
        Assert.assertEquals(pageTrafficInfo.get(0).getRefURLs().get(0), "2_my_url");
    }

    @Test
    public void testSortByName() {
        TestUtil.createUser();
        List<Page> pages = new ArrayList<Page>();
        for (Page page : TestUtil.initSitesForStatisticsTests().get(1).getPages()) {
            pages.add(page);
        }

        final Paginator<PageTrafficInfo> paginator = statisticsPageSort.sort(pages, StatisticsSortType.NAME, true,
                StatisticsTimePeriodType.ALL_TIME, Paginator.getFirstPageNumber());
        List<PageTrafficInfo> pageTrafficInfo = paginator.getItems();
        Assert.assertEquals(pageTrafficInfo.get(0).getPageName(), "3_page");
        Assert.assertEquals(pageTrafficInfo.get(1).getPageName(), "2_page");
        Assert.assertEquals(pageTrafficInfo.get(2).getPageName(), "1_page");
    }

    @Test
    public void testSortByTime() {
        TestUtil.createUser();
        List<Page> pages = new ArrayList<Page>();
        for (Page page : TestUtil.initSitesForStatisticsTests().get(1).getPages()) {
            pages.add(page);
        }

        final Paginator<PageTrafficInfo> paginator = statisticsPageSort.sort(pages, StatisticsSortType.TIME, true,
                StatisticsTimePeriodType.ALL_TIME, Paginator.getFirstPageNumber());
        List<PageTrafficInfo> pageTrafficInfo = paginator.getItems();
        Assert.assertEquals(pageTrafficInfo.get(0).getTime(), 10000);
        Assert.assertEquals(pageTrafficInfo.get(1).getTime(), 1200);
        Assert.assertEquals(pageTrafficInfo.get(2).getTime(), 500);
    }

    @Test
    public void testSortByHits() {
        TestUtil.createUser();
        List<Page> pages = new ArrayList<Page>();
        for (Page page : TestUtil.initSitesForStatisticsTests().get(1).getPages()) {
            pages.add(page);
        }

        final Paginator<PageTrafficInfo> paginator = statisticsPageSort.sort(pages, StatisticsSortType.HITS, true,
                StatisticsTimePeriodType.ALL_TIME, Paginator.getFirstPageNumber());
        List<PageTrafficInfo> pageTrafficInfo = paginator.getItems();
        Assert.assertEquals(pageTrafficInfo.get(0).getHits(), 1000);
        Assert.assertEquals(pageTrafficInfo.get(1).getHits(), 12);
        Assert.assertEquals(pageTrafficInfo.get(2).getHits(), 5);
    }

    @Test
    public void testSortByVisits() {
        TestUtil.createUser();
        List<Page> pages = new ArrayList<Page>();
        for (Page page : TestUtil.initSitesForStatisticsTests().get(1).getPages()) {
            pages.add(page);
        }

        final Paginator<PageTrafficInfo> paginator = statisticsPageSort.sort(pages, StatisticsSortType.VISITS, true,
                StatisticsTimePeriodType.ALL_TIME, Paginator.getFirstPageNumber());
        List<PageTrafficInfo> pageTrafficInfo = paginator.getItems();
        Assert.assertEquals(pageTrafficInfo.get(0).getVisits(), 2);
        Assert.assertEquals(pageTrafficInfo.get(1).getVisits(), 1);
        Assert.assertEquals(pageTrafficInfo.get(2).getVisits(), 1);
    }
}
