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
import com.shroggle.entity.Site;
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
public class StatisticsSiteSortTest {
    private StatisticsSiteSort statisticsSiteSort = new StatisticsSiteSort();

    @Test
    public void testConsistency() {
        TestUtil.createUser();
        List<Site> sites = TestUtil.initSitesForStatisticsTests();

        final Paginator paginator = statisticsSiteSort.sort(sites, StatisticsSortType.NAME,
                false, Paginator.getFirstPageNumber());
        List<SiteTrafficInfo> siteTrafficInfo = paginator.getItems();
        Assert.assertEquals(siteTrafficInfo.get(0).getSiteName(), "1_site");
        Assert.assertEquals(siteTrafficInfo.get(0).getHits(), 1);
        Assert.assertEquals(siteTrafficInfo.get(0).getVisits(), 1);
        Assert.assertEquals(siteTrafficInfo.get(0).getTime(), 100);
        Assert.assertEquals(siteTrafficInfo.get(0).getRefURLs().get(0), "1_my_url");
    }

    @Test
    public void testSortByName() {
        TestUtil.createUser();
        List<Site> sites = TestUtil.initSitesForStatisticsTests();


        final Paginator paginator = statisticsSiteSort.sort(sites, StatisticsSortType.NAME,
                true, Paginator.getFirstPageNumber());
        List<SiteTrafficInfo> siteTrafficInfo = paginator.getItems();
        Assert.assertEquals(siteTrafficInfo.get(0).getSiteName(), "3_site");
        Assert.assertEquals(siteTrafficInfo.get(1).getSiteName(), "2_site");
        Assert.assertEquals(siteTrafficInfo.get(2).getSiteName(), "1_site");
    }

    @Test
    public void testSortByTime() {
        TestUtil.createUser();
        List<Site> sites = TestUtil.initSitesForStatisticsTests();

        final Paginator paginator = statisticsSiteSort.sort(sites, StatisticsSortType.TIME,
                true, Paginator.getFirstPageNumber());
        List<SiteTrafficInfo> siteTrafficInfo = paginator.getItems();
        Assert.assertEquals(siteTrafficInfo.get(0).getTime(), 11700);
        Assert.assertEquals(siteTrafficInfo.get(1).getTime(), 2000);
        Assert.assertEquals(siteTrafficInfo.get(2).getTime(), 100);
    }

    @Test
    public void testSortByHits() {
        TestUtil.createUser();
        List<Site> sites = TestUtil.initSitesForStatisticsTests();

        final Paginator paginator = statisticsSiteSort.sort(sites, StatisticsSortType.HITS,
                true, Paginator.getFirstPageNumber());
        List<SiteTrafficInfo> siteTrafficInfo = paginator.getItems();
        Assert.assertEquals(siteTrafficInfo.get(0).getHits(), 1017);
        Assert.assertEquals(siteTrafficInfo.get(1).getHits(), 20);
        Assert.assertEquals(siteTrafficInfo.get(2).getHits(), 1);
    }

    @Test
    public void testSortByVisits() {
        TestUtil.createUser();
        List<Site> sites = TestUtil.initSitesForStatisticsTests();

        final Paginator paginator = statisticsSiteSort.sort(sites, StatisticsSortType.VISITS,
                true, Paginator.getFirstPageNumber());
        List<SiteTrafficInfo> siteTrafficInfo = paginator.getItems();
        Assert.assertEquals(siteTrafficInfo.get(0).getVisits(), 4);
        Assert.assertEquals(siteTrafficInfo.get(1).getVisits(), 2);
        Assert.assertEquals(siteTrafficInfo.get(2).getVisits(), 1);
    }
}
