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
import com.shroggle.entity.User;
import com.shroggle.entity.Page;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class StatisticsManagerTest {

    @Test
    public void testOverallTimeSpendForSite() {
        TestUtil.createUser();
        List<Site> sites = TestUtil.initSitesForStatisticsTests();

        Assert.assertEquals(11700, statisticsManager.getOverallTimeSpendForSite(sites.get(1), StatisticsTimePeriodType.ALL_TIME));
    }

    @Test
    public void testOverallTimeSpendForPagesSeparately() {
        TestUtil.createUser();
        List<Page> pages = TestUtil.initSitesForStatisticsTests().get(1).getPages();
        List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : pages) {
            pageIds.add(page.getPageId());
        }

        Assert.assertEquals((long) 500, (long) statisticsManager.getOverallTimeSpendForPagesSeparately(pageIds, StatisticsTimePeriodType.ALL_TIME).get(pages.get(0).getPageId()));
        Assert.assertEquals((long) 1200, (long) statisticsManager.getOverallTimeSpendForPagesSeparately(pageIds, StatisticsTimePeriodType.ALL_TIME).get(pages.get(1).getPageId()));
        Assert.assertEquals((long) 10000, (long) statisticsManager.getOverallTimeSpendForPagesSeparately(pageIds, StatisticsTimePeriodType.ALL_TIME).get(pages.get(2).getPageId()));
    }

    @Test
    public void testOverallTimeSpendForPage() {
        TestUtil.createUser();
        List<Site> sites = TestUtil.initSitesForStatisticsTests();

        Assert.assertEquals(1200, statisticsManager.getOverallTimeSpendForPage(sites.get(1).getPages().get(1), StatisticsTimePeriodType.ALL_TIME));
    }

    @Test
    public void testGetRefURLsForPage() {
        TestUtil.createUser();
        List<Site> sites = TestUtil.initSitesForStatisticsTests();

        Assert.assertEquals(new Integer(2), statisticsManager.getRefURLsForPage(sites.get(1).getPages().get(1), StatisticsTimePeriodType.ALL_TIME).get("2_my_url_visit_1"));
        Assert.assertEquals(new Integer(3), statisticsManager.getRefURLsForPage(sites.get(1).getPages().get(1), StatisticsTimePeriodType.ALL_TIME).get("2_my_url_visit_2"));
    }

    @Test
    public void testGetRefURLsForSite() {
        TestUtil.createUser();
        Site site = TestUtil.initSitesForStatisticsTests().get(1);

        Assert.assertEquals(new Integer(2), statisticsManager.getRefUrlsForSite(site, StatisticsTimePeriodType.ALL_TIME).get("2_my_url_visit_1"));
        Assert.assertEquals(new Integer(2), statisticsManager.getRefUrlsForSite(site, StatisticsTimePeriodType.ALL_TIME).get("2_my_url"));
        Assert.assertEquals(new Integer(3), statisticsManager.getRefUrlsForSite(site, StatisticsTimePeriodType.ALL_TIME).get("2_my_url_visit_2"));
    }

    @Test
    public void testGetHitsForSite() {
        TestUtil.createUser();
        List<Site> sites = TestUtil.initSitesForStatisticsTests();

        Assert.assertEquals(1017, statisticsManager.getHitsForSite(sites.get(1), StatisticsTimePeriodType.ALL_TIME));
    }

    @Test
    public void testGetHitsForPagesSeparately() {
        TestUtil.createUser();
        List<Page> pages = TestUtil.initSitesForStatisticsTests().get(1).getPages();
        List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : pages) {
            pageIds.add(page.getPageId());
        }

        Assert.assertEquals((long) 5, (long) statisticsManager.getHitsForPagesSeparately(pageIds, StatisticsTimePeriodType.ALL_TIME).get(pages.get(0).getPageId()));
        Assert.assertEquals((long) 12, (long) statisticsManager.getHitsForPagesSeparately(pageIds, StatisticsTimePeriodType.ALL_TIME).get(pages.get(1).getPageId()));
        Assert.assertEquals((long) 1000, (long) statisticsManager.getHitsForPagesSeparately(pageIds, StatisticsTimePeriodType.ALL_TIME).get(pages.get(2).getPageId()));
    }

    @Test
    public void testGetVisitsForPagesSeparately() {
        TestUtil.createUser();
        List<Page> pages = TestUtil.initSitesForStatisticsTests().get(1).getPages();
        List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : pages) {
            pageIds.add(page.getPageId());
        }

        Assert.assertEquals((long) 1, (long) statisticsManager.getVisitsForPagesSeparately(pageIds, StatisticsTimePeriodType.ALL_TIME).get(pages.get(0).getPageId()));
        Assert.assertEquals((long) 2, (long) statisticsManager.getVisitsForPagesSeparately(pageIds, StatisticsTimePeriodType.ALL_TIME).get(pages.get(1).getPageId()));
        Assert.assertEquals((long) 1, (long) statisticsManager.getVisitsForPagesSeparately(pageIds, StatisticsTimePeriodType.ALL_TIME).get(pages.get(2).getPageId()));
    }

    @Test
    public void testGetVisitsForSite() {
        TestUtil.createUser();
        List<Site> sites = TestUtil.initSitesForStatisticsTests();

        Assert.assertEquals(4, statisticsManager.getVisitsForSite(sites.get(1), StatisticsTimePeriodType.ALL_TIME));
    }

    @Test
    public void testSortMapByValue() {
        final Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("a", 1);
        map.put("c", 3);
        map.put("b", 2);

        LinkedList<Map.Entry<String, Integer>> sortedMap = statisticsManager.sortMapByValue(map);
        Assert.assertEquals((int) 3, (int) sortedMap.get(0).getValue());
        Assert.assertEquals((int) 2, (int) sortedMap.get(1).getValue());
        Assert.assertEquals((int) 1, (int) sortedMap.get(2).getValue());
    }

    @Test
    public void testMaxStringInMap() {
        final Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("a", 1);
        map.put("c", 3);
        map.put("b", 2);

        Map.Entry<String, Integer> maxEntry = statisticsManager.maxStringInMap(map);
        Assert.assertEquals("c", maxEntry.getKey());
    }

    @Test
    public void testGetDateIntervalAccordingToTimePeriod_ALL_TIME() {
        DateInterval dateInterval = statisticsManager.getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.ALL_TIME);

        Assert.assertEquals(null, dateInterval.getStartDate());
        Assert.assertEquals(null, dateInterval.getEndDate());
    }

    @Test
    public void testGetDateIntervalAccordingToTimePeriod_THIS_MONTH() {
        final DateInterval dateInterval = statisticsManager.getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.THIS_MONTH);
        final Calendar endDateCalendar = new GregorianCalendar();
        endDateCalendar.setTime(dateInterval.getEndDate());
        final Calendar startDateCalendar = new GregorianCalendar();
        startDateCalendar.setTime(dateInterval.getStartDate());

        final Calendar currentDateCalendar = new GregorianCalendar();
        currentDateCalendar.setTime(new Date());

        final Calendar expectedEndDateCalendar = new GregorianCalendar();
        expectedEndDateCalendar.setTime(new Date());
        final Calendar expectedStartDateCalendar = new GregorianCalendar();
        expectedStartDateCalendar.setTime(new Date());
        if (expectedEndDateCalendar.get(Calendar.MONTH) == 0) {
            expectedStartDateCalendar.set(Calendar.MONTH, 11);
        } else {
            expectedStartDateCalendar.set(Calendar.MONTH, expectedStartDateCalendar.get(Calendar.MONTH) - 1);
        }

        Assert.assertEquals(expectedStartDateCalendar.get(Calendar.MONTH), startDateCalendar.get(Calendar.MONTH));
        Assert.assertEquals(expectedEndDateCalendar.get(Calendar.MONTH), endDateCalendar.get(Calendar.MONTH));
    }

    @Test
    public void testGetDateIntervalAccordingToTimePeriod_LAST_MONTH() {
        final DateInterval dateInterval = statisticsManager.getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.LAST_MONTH);
        final Calendar endDateCalendar = new GregorianCalendar();
        endDateCalendar.setTime(dateInterval.getEndDate());
        final Calendar startDateCalendar = new GregorianCalendar();
        startDateCalendar.setTime(dateInterval.getStartDate());

        final Calendar currentDateCalendar = new GregorianCalendar();
        currentDateCalendar.setTime(new Date());

        final Calendar expectedEndDateCalendar = new GregorianCalendar();
        expectedEndDateCalendar.setTime(new Date());
        if (currentDateCalendar.get(Calendar.MONTH) == 0) {
            expectedEndDateCalendar.set(Calendar.MONTH, 11);
        } else {
            expectedEndDateCalendar.set(Calendar.MONTH, expectedEndDateCalendar.get(Calendar.MONTH) - 1);
        }
        final Calendar expectedStartDateCalendar = new GregorianCalendar();
        expectedStartDateCalendar.setTime(new Date());
        if (currentDateCalendar.get(Calendar.MONTH) == 0) {
            expectedStartDateCalendar.set(Calendar.MONTH, 10);
        } else if (currentDateCalendar.get(Calendar.MONTH) == 1) {
            expectedStartDateCalendar.set(Calendar.MONTH, 11);
        } else {
            expectedStartDateCalendar.set(Calendar.MONTH, expectedStartDateCalendar.get(Calendar.MONTH) - 2);
        }

        Assert.assertEquals(expectedStartDateCalendar.get(Calendar.MONTH), startDateCalendar.get(Calendar.MONTH));
        Assert.assertEquals(expectedEndDateCalendar.get(Calendar.MONTH), endDateCalendar.get(Calendar.MONTH));
    }

    private final StatisticsManager statisticsManager = new StatisticsManager();

}
