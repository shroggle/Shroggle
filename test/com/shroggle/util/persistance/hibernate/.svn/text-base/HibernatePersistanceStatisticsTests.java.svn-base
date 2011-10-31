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

package com.shroggle.util.persistance.hibernate;

import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.statistics.DateInterval;
import com.shroggle.logic.statistics.StatisticsManager;
import com.shroggle.logic.statistics.StatisticsTimePeriodType;
import junit.framework.Assert;
import org.junit.Test;

import java.util.*;

/**
 * @author dmitry.solomadin
 */
public class HibernatePersistanceStatisticsTests extends HibernatePersistanceTestBase {

    @Test
    public void testGetHitsForPages_ALL_TIME() {
        final Site site = initStructureForTests().get(0);
        final List<Integer> pageIds = new ArrayList<Integer>();
        pageIds.add(site.getPages().get(0).getPageId());
        pageIds.add(site.getPages().get(1).getPageId());

        DateInterval dateInterval = new StatisticsManager().getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.ALL_TIME);
        Assert.assertEquals(9, persistance.getHitsForPages(pageIds, dateInterval));
    }

    @Test
    public void testGetHitsForPages_THIS_MONTH() {
        final Site site = initStructureForTests().get(0);
        final List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : site.getPages()) {
            pageIds.add(page.getPageId());
        }

        DateInterval dateInterval = new StatisticsManager().getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.THIS_MONTH);
        Assert.assertEquals(4, persistance.getHitsForPages(pageIds, dateInterval));
    }

    @Test
    public void testGetHitsForPages_LAST_MONTH() {
        final Site site = initStructureForTests().get(0);
        final List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : site.getPages()) {
            pageIds.add(page.getPageId());
        }

        DateInterval dateInterval = new StatisticsManager().getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.LAST_MONTH);
        Assert.assertEquals(5, persistance.getHitsForPages(pageIds, dateInterval));
    }

    @Test
    public void testGetUniqueVisitsForPages_ALL_TIME() {
        final Site site = initStructureForTests().get(0);
        final List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : site.getPages()) {
            pageIds.add(page.getPageId());
        }

        DateInterval dateInterval = new StatisticsManager().getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.ALL_TIME);
        Assert.assertEquals(2, persistance.getUniqueVisitsCountForPages(pageIds, dateInterval));
    }

    @Test
    public void testGetUniqueVisitsForPages_THIS_MONTH() {
        final Site site = initStructureForTests().get(0);
        final List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : site.getPages()) {
            pageIds.add(page.getPageId());
        }

        DateInterval dateInterval = new StatisticsManager().getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.THIS_MONTH);
        Assert.assertEquals(2, persistance.getUniqueVisitsCountForPages(pageIds, dateInterval));
    }

    @Test
    public void testGetUniqueVisitsForPages_LAST_MONTH() {
        final Site site = initStructureForTests().get(0);
        final List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : site.getPages()) {
            pageIds.add(page.getPageId());
        }

        DateInterval dateInterval = new StatisticsManager().getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.LAST_MONTH);
        Assert.assertEquals(1, persistance.getUniqueVisitsCountForPages(pageIds, dateInterval));
    }

    @Test
    public void testGetOverallTimeForPages_ALL_TIME() {
        final Site site = initStructureForTests().get(0);
        final List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : site.getPages()) {
            pageIds.add(page.getPageId());
        }

        DateInterval dateInterval = new StatisticsManager().getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.ALL_TIME);
        Assert.assertEquals(1000, persistance.getOverallTimeForPages(pageIds, dateInterval));
    }

    @Test
    public void testGetOverallTimeForPages_THIS_MONTH() {
        final Site site = initStructureForTests().get(0);
        final List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : site.getPages()) {
            pageIds.add(page.getPageId());
        }

        DateInterval dateInterval = new StatisticsManager().getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.THIS_MONTH);
        Assert.assertEquals(700, persistance.getOverallTimeForPages(pageIds, dateInterval));
    }

    @Test
    public void testGetOverallTimeForPages_LAST_MONTH() {
        final Site site = initStructureForTests().get(0);
        final List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : site.getPages()) {
            pageIds.add(page.getPageId());
        }

        DateInterval dateInterval = new StatisticsManager().getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.LAST_MONTH);
        Assert.assertEquals(100, persistance.getOverallTimeForPages(pageIds, dateInterval));
    }

    @Test
    public void testGetOverallTimeForPagesSeparately_ALL_TIME() {
        final Site site = initStructureForTests().get(0);
        final List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : site.getPages()) {
            pageIds.add(page.getPageId());
        }

        DateInterval dateInterval = new StatisticsManager().getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.ALL_TIME);
        Assert.assertEquals(3, persistance.getOverallTimeForPagesSeparately(pageIds, dateInterval).size());
        Assert.assertEquals((long) 100, (long) persistance.getOverallTimeForPagesSeparately(pageIds, dateInterval).get(1));
        Assert.assertEquals((long) 500, (long) persistance.getOverallTimeForPagesSeparately(pageIds, dateInterval).get(2));
        Assert.assertEquals((long) 400, (long) persistance.getOverallTimeForPagesSeparately(pageIds, dateInterval).get(3));
    }

    @Test
    public void testGetOverallTimeForPagesSeparately_THIS_MONTH() {
        final Site site = initStructureForTests().get(0);
        final List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : site.getPages()) {
            pageIds.add(page.getPageId());
        }

        DateInterval dateInterval = new StatisticsManager().getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.THIS_MONTH);
        Assert.assertEquals(2, persistance.getOverallTimeForPagesSeparately(pageIds, dateInterval).size());
        Assert.assertEquals((long) 300, (long) persistance.getOverallTimeForPagesSeparately(pageIds, dateInterval).get(2));
        Assert.assertEquals((long) 400, (long) persistance.getOverallTimeForPagesSeparately(pageIds, dateInterval).get(3));
    }

    @Test
    public void testGetOverallTimeForPagesSeparately_LAST_MONTH() {
        final Site site = initStructureForTests().get(0);
        final List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : site.getPages()) {
            pageIds.add(page.getPageId());
        }

        DateInterval dateInterval = new StatisticsManager().getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.LAST_MONTH);
        Assert.assertEquals(1, persistance.getOverallTimeForPagesSeparately(pageIds, dateInterval).size());
        Assert.assertEquals((long) 100, (long) persistance.getOverallTimeForPagesSeparately(pageIds, dateInterval).get(1));
    }

    @Test
    public void testGetRefUrlsByPages_ALL_TIME() {
        final Site site = initStructureForTests().get(0);
        final List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : site.getPages()) {
            pageIds.add(page.getPageId());
        }

        DateInterval dateInterval = new StatisticsManager().getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.ALL_TIME);
        Assert.assertEquals(5, persistance.getRefUrlsByPages(pageIds, dateInterval).size());
        Assert.assertEquals((long) 1, (long) persistance.getRefUrlsByPages(pageIds, dateInterval).get("s1_p1_v1_url1"));
        Assert.assertEquals((long) 2, (long) persistance.getRefUrlsByPages(pageIds, dateInterval).get("s1_p1_v1_url2"));
        Assert.assertEquals((long) 1, (long) persistance.getRefUrlsByPages(pageIds, dateInterval).get("s1_p2_v1_url1"));
        Assert.assertEquals((long) 2, (long) persistance.getRefUrlsByPages(pageIds, dateInterval).get("s1_p2_v2_url1"));
        Assert.assertEquals((long) 5, (long) persistance.getRefUrlsByPages(pageIds, dateInterval).get("s1_p3_v1_url1"));
    }

    @Test
    public void testGetRefUrlsByPages_THIS_MONTH() {
        final Site site = initStructureForTests().get(0);
        final List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : site.getPages()) {
            pageIds.add(page.getPageId());
        }

        DateInterval dateInterval = new StatisticsManager().getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.THIS_MONTH);
        Assert.assertEquals(2, persistance.getRefUrlsByPages(pageIds, dateInterval).size());
        Assert.assertEquals((long) 2, (long) persistance.getRefUrlsByPages(pageIds, dateInterval).get("s1_p2_v2_url1"));
        Assert.assertEquals((long) 5, (long) persistance.getRefUrlsByPages(pageIds, dateInterval).get("s1_p3_v1_url1"));
    }
    
    @Test
    public void testGetRefUrlsByPages_LAST_MONTH() {
        final Site site = initStructureForTests().get(0);
        final List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : site.getPages()) {
            pageIds.add(page.getPageId());
        }

        DateInterval dateInterval = new StatisticsManager().getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.LAST_MONTH);
        Assert.assertEquals(2, persistance.getRefUrlsByPages(pageIds, dateInterval).size());
        Assert.assertEquals((long) 1, (long) persistance.getRefUrlsByPages(pageIds, dateInterval).get("s1_p1_v1_url1"));
        Assert.assertEquals((long) 2, (long) persistance.getRefUrlsByPages(pageIds, dateInterval).get("s1_p1_v1_url2"));
    }

    @Test
    public void testGetRefSearchTermsByPages_ALL_TIME() {
        final Site site = initStructureForTests().get(0);
        final List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : site.getPages()) {
            pageIds.add(page.getPageId());
        }

        DateInterval dateInterval = new StatisticsManager().getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.ALL_TIME);
        Assert.assertEquals(5, persistance.getRefSearchTermsByPages(pageIds, dateInterval).size());
        Assert.assertEquals((long) 2, (long) persistance.getRefSearchTermsByPages(pageIds, dateInterval).get("s1_p1_v1_term1"));
        Assert.assertEquals((long) 3, (long) persistance.getRefSearchTermsByPages(pageIds, dateInterval).get("s1_p1_v1_term2"));
        Assert.assertEquals((long) 2, (long) persistance.getRefSearchTermsByPages(pageIds, dateInterval).get("s1_p2_v1_term1"));
        Assert.assertEquals((long) 3, (long) persistance.getRefSearchTermsByPages(pageIds, dateInterval).get("s1_p2_v2_term1"));
        Assert.assertEquals((long) 6, (long) persistance.getRefSearchTermsByPages(pageIds, dateInterval).get("s1_p3_v1_term1"));
    }

    @Test
    public void testGetRefSearchTermsByPages_THIS_MONTH() {
        final Site site = initStructureForTests().get(0);
        final List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : site.getPages()) {
            pageIds.add(page.getPageId());
        }

        DateInterval dateInterval = new StatisticsManager().getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.THIS_MONTH);
        Assert.assertEquals(2, persistance.getRefSearchTermsByPages(pageIds, dateInterval).size());
        Assert.assertEquals((long) 3, (long) persistance.getRefSearchTermsByPages(pageIds, dateInterval).get("s1_p2_v2_term1"));
        Assert.assertEquals((long) 6, (long) persistance.getRefSearchTermsByPages(pageIds, dateInterval).get("s1_p3_v1_term1"));
    }

    @Test
    public void testGetRefSearchTermsByPages_LAST_MONTH() {
        final Site site = initStructureForTests().get(0);
        final List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : site.getPages()) {
            pageIds.add(page.getPageId());
        }

        DateInterval dateInterval = new StatisticsManager().getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.LAST_MONTH);
        Assert.assertEquals(2, persistance.getRefSearchTermsByPages(pageIds, dateInterval).size());
        Assert.assertEquals((long) 2, (long) persistance.getRefSearchTermsByPages(pageIds, dateInterval).get("s1_p1_v1_term1"));
        Assert.assertEquals((long) 3, (long) persistance.getRefSearchTermsByPages(pageIds, dateInterval).get("s1_p1_v1_term2"));
    }

    @Test
    public void testGetUniqueVisitsForPagesSeparately_ALL_TIME() {
        final Site site = initStructureForTests().get(0);
        final List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : site.getPages()) {
            pageIds.add(page.getPageId());
        }

        DateInterval dateInterval = new StatisticsManager().getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.ALL_TIME);
        Assert.assertEquals(3, persistance.getUniqueVisitsForPagesSeparately(pageIds, dateInterval).size());
        Assert.assertEquals((long) 1, (long) persistance.getUniqueVisitsForPagesSeparately(pageIds, dateInterval).get(1));
        Assert.assertEquals((long) 2, (long) persistance.getUniqueVisitsForPagesSeparately(pageIds, dateInterval).get(2));
        Assert.assertEquals((long) 1, (long) persistance.getUniqueVisitsForPagesSeparately(pageIds, dateInterval).get(3));
    }

    @Test
    public void testGetUniqueVisitsForPagesSeparately_THIS_MONTH() {
        final Site site = initStructureForTests().get(0);
        final List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : site.getPages()) {
            pageIds.add(page.getPageId());
        }

        DateInterval dateInterval = new StatisticsManager().getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.THIS_MONTH);
        Assert.assertEquals(2, persistance.getUniqueVisitsForPagesSeparately(pageIds, dateInterval).size());
        Assert.assertEquals((long) 1, (long) persistance.getUniqueVisitsForPagesSeparately(pageIds, dateInterval).get(2));
        Assert.assertEquals((long) 1, (long) persistance.getUniqueVisitsForPagesSeparately(pageIds, dateInterval).get(3));
    }

    @Test
    public void testGetUniqueVisitsForPagesSeparately_LAST_MONTH() {
        final Site site = initStructureForTests().get(0);
        final List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : site.getPages()) {
            pageIds.add(page.getPageId());
        }

        DateInterval dateInterval = new StatisticsManager().getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.LAST_MONTH);
        Assert.assertEquals(1, persistance.getUniqueVisitsForPagesSeparately(pageIds, dateInterval).size());
        Assert.assertEquals((long) 1, (long) persistance.getUniqueVisitsForPagesSeparately(pageIds, dateInterval).get(1));
    }

    @Test
    public void testGetHitsForPagesSeparately_ALL_TIME() {
        final Site site = initStructureForTests().get(0);
        final List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : site.getPages()) {
            pageIds.add(page.getPageId());
        }

        DateInterval dateInterval = new StatisticsManager().getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.ALL_TIME);
        Assert.assertEquals(3, persistance.getHitsForPagesSeparately(pageIds, dateInterval).size());
        Assert.assertEquals((long) 5, (long) persistance.getHitsForPagesSeparately(pageIds, dateInterval).get(1));
        Assert.assertEquals((long) 4, (long) persistance.getHitsForPagesSeparately(pageIds, dateInterval).get(2));
        Assert.assertEquals((long) 2, (long) persistance.getHitsForPagesSeparately(pageIds, dateInterval).get(3));
    }

    @Test
    public void testGetHitsForPagesSeparately_THIS_MONTH() {
        final Site site = initStructureForTests().get(0);
        final List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : site.getPages()) {
            pageIds.add(page.getPageId());
        }

        DateInterval dateInterval = new StatisticsManager().getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.THIS_MONTH);
        Assert.assertEquals(2, persistance.getHitsForPagesSeparately(pageIds, dateInterval).size());
        Assert.assertEquals((long) 2, (long) persistance.getHitsForPagesSeparately(pageIds, dateInterval).get(2));
        Assert.assertEquals((long) 2, (long) persistance.getHitsForPagesSeparately(pageIds, dateInterval).get(3));
    }

    @Test
    public void testGetHitsForPagesSeparately_LAST_MONTH() {
        final Site site = initStructureForTests().get(0);
        final List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : site.getPages()) {
            pageIds.add(page.getPageId());
        }

        DateInterval dateInterval = new StatisticsManager().getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType.LAST_MONTH);
        Assert.assertEquals(1, persistance.getHitsForPagesSeparately(pageIds, dateInterval).size());
        Assert.assertEquals((long) 5, (long) persistance.getHitsForPagesSeparately(pageIds, dateInterval).get(1));
    }

    private List<Site> initStructureForTests() {
        User user = new User();
        user.setEmail("aa");
        user.setRegistrationDate(new Date());
        persistance.putUser(user);

        final List<Site> returnList = new ArrayList<Site>();

        // First site

        Site site = new Site();site.getSitePaymentSettings().setUserId(-1);
        site.setTitle("title1");
        site.setSubDomain("1");
        site.setCreationDate(new Date());
        ThemeId id = new ThemeId();
        id.setTemplateDirectory("");
        id.setThemeCss("");
        site.setThemeId(id);
        persistance.putSite(site);

        Page page = TestUtil.createPage(site);

        Page page2 = TestUtil.createPage(site);

        Page page3 = TestUtil.createPage(site);

        PageVisitor pageVisitor = new PageVisitor();
        persistance.putPageVisitor(pageVisitor);

        PageVisitor pageVisitor1 = new PageVisitor();
        persistance.putPageVisitor(pageVisitor1);

        // Visit for first page. Minus 1 MONTH. Fits for LAST_MONTH, ALL_TIME
        Visit visit = new Visit();
        Calendar currentMinusOneMonths = new GregorianCalendar();
        currentMinusOneMonths.setTime(new Date());
        currentMinusOneMonths.set(Calendar.MONTH, currentMinusOneMonths.get(Calendar.MONTH) - 1);
        visit.setVisitCreationDate(currentMinusOneMonths.getTime());
        visit.setOverallTimeOfVisit(100);
        visit.setPageVisitor(pageVisitor);
        visit.setVisitedPage(page);
        visit.setVisitCount(5);
        persistance.putVisit(visit);
        pageVisitor.addVisit(visit);
        //1 times
        visit.addReferrerURL("s1_p1_v1_url1");
        //2 times
        visit.addReferrerURL("s1_p1_v1_url2");
        visit.addReferrerURL("s1_p1_v1_url2");
        //2 times
        visit.addReferrerSearchTerm("s1_p1_v1_term1");
        visit.addReferrerSearchTerm("s1_p1_v1_term1");
        //3 times
        visit.addReferrerSearchTerm("s1_p1_v1_term2");
        visit.addReferrerSearchTerm("s1_p1_v1_term2");
        visit.addReferrerSearchTerm("s1_p1_v1_term2");

        // Visit for second page. Minus 1 YEAR. FITS FOR ALL_TIME ONLY.
        Visit visit2 = new Visit();
        Calendar currentMinusYear = new GregorianCalendar();
        currentMinusYear.setTime(new Date());
        currentMinusYear.set(Calendar.YEAR, currentMinusYear.get(Calendar.YEAR) - 1);
        visit2.setVisitCreationDate(currentMinusYear.getTime());
        visit2.setOverallTimeOfVisit(200);
        visit2.setPageVisitor(pageVisitor);
        visit2.setVisitedPage(page2);
        visit2.setVisitCount(2);
        persistance.putVisit(visit2);
        pageVisitor.addVisit(visit2);
        //1 times
        visit2.addReferrerURL("s1_p2_v1_url1");
        //2 times
        visit2.addReferrerSearchTerm("s1_p2_v1_term1");
        visit2.addReferrerSearchTerm("s1_p2_v1_term1");

        // Visit for second page. Current date. FITS FOR THIS_MONTH, ALL_TIME
        Visit visit3 = new Visit();
        visit3.setVisitCreationDate(new Date());
        visit3.setOverallTimeOfVisit(300);
        visit3.setPageVisitor(pageVisitor1);
        visit3.setVisitedPage(page2);
        visit3.setVisitCount(2);
        persistance.putVisit(visit3);
        pageVisitor1.addVisit(visit3);
        //2 times
        visit3.addReferrerURL("s1_p2_v2_url1");
        visit3.addReferrerURL("s1_p2_v2_url1");
        //3 times
        visit3.addReferrerSearchTerm("s1_p2_v2_term1");
        visit3.addReferrerSearchTerm("s1_p2_v2_term1");
        visit3.addReferrerSearchTerm("s1_p2_v2_term1");

        // Visit for third page. Current date. FITS FOR THIS_MONTH, ALL_TIME
        Visit visit4 = new Visit();
        visit4.setVisitCreationDate(new Date());
        visit4.setOverallTimeOfVisit(400);
        visit4.setPageVisitor(pageVisitor);
        visit4.setVisitedPage(page3);
        visit4.setVisitCount(2);
        persistance.putVisit(visit4);
        pageVisitor.addVisit(visit4);
        //5 times
        visit4.addReferrerURL("s1_p3_v1_url1");
        visit4.addReferrerURL("s1_p3_v1_url1");
        visit4.addReferrerURL("s1_p3_v1_url1");
        visit4.addReferrerURL("s1_p3_v1_url1");
        visit4.addReferrerURL("s1_p3_v1_url1");
        //6 times
        visit4.addReferrerSearchTerm("s1_p3_v1_term1");
        visit4.addReferrerSearchTerm("s1_p3_v1_term1");
        visit4.addReferrerSearchTerm("s1_p3_v1_term1");
        visit4.addReferrerSearchTerm("s1_p3_v1_term1");
        visit4.addReferrerSearchTerm("s1_p3_v1_term1");
        visit4.addReferrerSearchTerm("s1_p3_v1_term1");

        // Second site

        Site site1 = new Site();site1.getSitePaymentSettings().setUserId(-1);
        site1.setTitle("qwe1");
        site1.setSubDomain("qwe");
        site1.setCreationDate(new Date());
        ThemeId id_s1 = new ThemeId();
        id_s1.setTemplateDirectory("");
        id_s1.setThemeCss("");
        site1.setThemeId(id_s1);
        persistance.putSite(site1);

        Page page_s1 = TestUtil.createPage(site1);

        PageVisitor pageVisitor_s1 = new PageVisitor();
        persistance.putPageVisitor(pageVisitor_s1);

        Visit visit_s1 = new Visit();
        visit_s1.setVisitCreationDate(new Date());
        visit_s1.setOverallTimeOfVisit(100);
        visit_s1.setPageVisitor(pageVisitor_s1);
        visit_s1.setVisitedPage(page_s1);
        visit_s1.setVisitCount(5);
        persistance.putVisit(visit_s1);
        pageVisitor_s1.addVisit(visit_s1);
        visit_s1.addReferrerURL("s2_p1_v1_url1");
        visit_s1.addReferrerURL("s2_p1_v1_url1");
        visit_s1.addReferrerURL("s2_p1_v1_url1");
        visit_s1.addReferrerSearchTerm("s2_p1_v1_term1");
        visit_s1.addReferrerSearchTerm("s2_p1_v1_term1");
        visit_s1.addReferrerSearchTerm("s2_p1_v1_term1");
        visit_s1.addReferrerSearchTerm("s2_p1_v1_term1");

        returnList.add(site);
        returnList.add(site1);
        return returnList;
    }

}
