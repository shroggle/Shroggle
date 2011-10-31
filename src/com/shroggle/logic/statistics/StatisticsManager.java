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

import com.shroggle.entity.Page;
import com.shroggle.entity.Site;
import com.shroggle.entity.Visit;
import com.shroggle.entity.VisitReferrer;
import com.shroggle.logic.site.page.PagesWithoutSystem;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.*;

/**
 * @author dmitry.solomadin
 */
public class StatisticsManager {

    /*------------------------------------------------HITS------------------------------------------------------------*/

    public long getHitsForSite(final Site site, final StatisticsTimePeriodType statisticsTimePeriodType) {
        final DateInterval dateInterval = getDateIntervalAccordingToTimePeriod(statisticsTimePeriodType);
        List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : PagesWithoutSystem.get(site.getPages())) {
            pageIds.add(page.getPageId());
        }
        return persistance.getHitsForPages(pageIds, dateInterval);
    }

    public Map<Integer, Long> getHitsForPagesSeparately(final List<Integer> pageIds, final StatisticsTimePeriodType statisticsTimePeriodType) {
        final DateInterval dateInterval = getDateIntervalAccordingToTimePeriod(statisticsTimePeriodType);
        return persistance.getHitsForPagesSeparately(pageIds, dateInterval);
    }

    /*----------------------------------------------REF SEARCH TERMS--------------------------------------------------*/

    public Map<String, Integer> getRefSearchTermsForPage(final Page page, final StatisticsTimePeriodType statisticsTimePeriodType) {
        final DateInterval dateInterval = getDateIntervalAccordingToTimePeriod(statisticsTimePeriodType);
        return persistance.getRefSearchTermsByPages(Arrays.asList(page.getPageId()), dateInterval);
    }

    /*----------------------------------------------TIME SPEND--------------------------------------------------------*/

    public long getOverallTimeSpendForSite(final Site site, final StatisticsTimePeriodType statisticsTimePeriodType) {
        final DateInterval dateInterval = getDateIntervalAccordingToTimePeriod(statisticsTimePeriodType);
        List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : PagesWithoutSystem.get(site.getPages())) {
            pageIds.add(page.getPageId());
        }
        return persistance.getOverallTimeForPages(pageIds, dateInterval);
    }

    public Map<Integer, Long> getOverallTimeSpendForPagesSeparately(final List<Integer> pageIds, final StatisticsTimePeriodType statisticsTimePeriodType) {
        final DateInterval dateInterval = getDateIntervalAccordingToTimePeriod(statisticsTimePeriodType);
        return persistance.getOverallTimeForPagesSeparately(pageIds, dateInterval);
    }

    public long getOverallTimeSpendForPage(final Page page, final StatisticsTimePeriodType statisticsTimePeriodType) {
        final DateInterval dateInterval = getDateIntervalAccordingToTimePeriod(statisticsTimePeriodType);
        return persistance.getOverallTimeForPages(Arrays.asList(page.getPageId()), dateInterval);
    }

    /*-------------------------------------------------REF URLS-------------------------------------------------------*/

    public Map<String, Integer> getRefUrlsForSite(final Site site, final StatisticsTimePeriodType statisticsTimePeriodType) {
        final DateInterval dateInterval = getDateIntervalAccordingToTimePeriod(statisticsTimePeriodType);
        List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : site.getPages()) {
            pageIds.add(page.getPageId());
        }

        return persistance.getRefUrlsByPages(pageIds, dateInterval);
    }

    public Map<String, Integer> getRefURLsForPage(final Page page, final StatisticsTimePeriodType statisticsTimePeriodType) {
        final DateInterval dateInterval = getDateIntervalAccordingToTimePeriod(statisticsTimePeriodType);

        return persistance.getRefUrlsByPages(Arrays.asList(page.getPageId()), dateInterval);
    }

    /*-------------------------------------------------VISITS---------------------------------------------------------*/

    public Map<Integer, Long> getVisitsForPagesSeparately(final List<Integer> pageIds, final StatisticsTimePeriodType statisticsTimePeriodType) {
        final DateInterval dateInterval = getDateIntervalAccordingToTimePeriod(statisticsTimePeriodType);
        return persistance.getUniqueVisitsForPagesSeparately(pageIds, dateInterval);
    }

    public long getVisitsForSite(final Site site, final StatisticsTimePeriodType statisticsTimePeriodType) {
        final DateInterval dateInterval = getDateIntervalAccordingToTimePeriod(statisticsTimePeriodType);
        List<Integer> pageIds = new ArrayList<Integer>();
        for (Page page : PagesWithoutSystem.get(site.getPages())) {
            pageIds.add(page.getPageId());
        }
        return persistance.getUniqueVisitsCountForPages(pageIds, dateInterval);
    }

    /*-------------------------------------------------UTIL METHODTS--------------------------------------------------*/

    private class EntryComparator implements Comparator<Map.Entry<String, Integer>> {
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
            return o1.getValue().compareTo(o2.getValue());
        }
    }

    private class ReferrerComparator implements Comparator<VisitReferrer> {
        public int compare(VisitReferrer r1, VisitReferrer r2) {
            return ((Integer) r1.getVisitCount()).compareTo(r2.getVisitCount());
        }
    }

    public LinkedList<Map.Entry<String, Integer>> sortMapByValue(final Map<String, Integer> map) {
        LinkedList<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, new EntryComparator());
        Collections.reverse(list);
        return list;
    }

    public List<VisitReferrer> sortReferrersByValue(final List<VisitReferrer> visitReferrers) {
        List<VisitReferrer> returnList = new ArrayList<VisitReferrer>(visitReferrers);
        Collections.sort(returnList, new ReferrerComparator());
        Collections.reverse(returnList);
        return returnList;
    }

    public Map.Entry<String, Integer> maxStringInMap(final Map<String, Integer> refUrlMap) {
        Map.Entry<String, Integer> max = new HashMap.SimpleEntry<String, Integer>("", 0);
        LinkedList<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(refUrlMap.entrySet());
        for (Map.Entry<String, Integer> entry : list) {
            if (max.getValue() < entry.getValue()) {
                max = entry;
            }
        }
        return max;
    }

    public VisitReferrer maxStringInReferrerList(final List<VisitReferrer> visitReferrers) {
        VisitReferrer max = new VisitReferrer();
        for (VisitReferrer visitReferrer : visitReferrers) {
            if (max.getVisitCount() < visitReferrer.getVisitCount()) {
                max = visitReferrer;
            }
        }
        return max;
    }

    public List<Visit> getVisitsAccordToTimePeriod(final Page page, final StatisticsTimePeriodType statisticsTimePeriodType) {
        List<Visit> visits = new ArrayList<Visit>();
        if (statisticsTimePeriodType.equals(StatisticsTimePeriodType.THIS_MONTH)) {
            for (Visit visit : page.getPageVisits()) {
                Calendar c1 = new GregorianCalendar(), c2 = new GregorianCalendar();
                c1.setTime(new Date());
                c2.setTime(visit.getVisitCreationDate());
                if (c2.get(Calendar.MONTH) == c1.get(Calendar.MONTH) && c2.get(Calendar.YEAR) == c1.get(Calendar.YEAR)) {
                    visits.add(visit);
                }
            }
        } else if (statisticsTimePeriodType.equals(StatisticsTimePeriodType.LAST_MONTH)) {
            for (Visit visit : page.getPageVisits()) {
                Calendar c1 = new GregorianCalendar(), c2 = new GregorianCalendar();
                c1.setTime(new Date());
                c2.setTime(visit.getVisitCreationDate());
                int lastMonth;
                if (c1.get(Calendar.MONTH) == 0) {
                    lastMonth = 11;
                } else {
                    lastMonth = c1.get(Calendar.MONTH) - 1;
                }

                if (c2.get(Calendar.MONTH) == (lastMonth) && c2.get(Calendar.YEAR) == c1.get(Calendar.YEAR)) {
                    visits.add(visit);
                }
            }
        } else {
            visits.addAll(page.getPageVisits());
        }
        return visits;
    }

    public Visit getMergedVisitForPageVisitor(List<Visit> visits, int pageVisitorId, int pageId) {
        final Visit mergedVisit = new Visit();
        for (Visit visit : visits) {
            if (visit.getPageVisitor().getPageVisitorId() == pageVisitorId && visit.getVisitedPage().getPageId() == pageId) {
                mergedVisit.setReferrers(mergeReferrers(mergedVisit.getReferrers(), visit.getReferrerURLs()));
                mergedVisit.setReferrers(mergeReferrers(mergedVisit.getReferrers(), visit.getReferrerSearchTerms()));
                mergedVisit.setVisitCount(mergedVisit.getVisitCount() + visit.getVisitCount());
                mergedVisit.setOverallTimeOfVisit(mergedVisit.getOverallTimeOfVisit() + visit.getOverallTimeOfVisit());
                if (mergedVisit.getVisitCreationDate() == null || mergedVisit.getVisitCreationDate().before(visit.getVisitCreationDate())) {
                    mergedVisit.setVisitCreationDate(visit.getVisitCreationDate());
                }
            }
        }

        return mergedVisit;
    }

    public DateInterval getDateIntervalAccordingToTimePeriod(StatisticsTimePeriodType timePeriod) {
        final DateInterval dateInterval = new DateInterval();

        switch (timePeriod) {
            case ALL_TIME: {
                dateInterval.setStartDate(null);
                dateInterval.setEndDate(null);
                break;
            }
            case LAST_MONTH: {
                dateInterval.setStartDate(DateUtil.subtractMonthsFromDate(new Date(), 2));
                dateInterval.setEndDate(DateUtil.subtractMonthsFromDate(new Date(), 1));
                break;
            }
            case THIS_MONTH: {
                dateInterval.setStartDate(DateUtil.subtractMonthsFromDate(new Date(), 1));
                dateInterval.setEndDate(new Date());
                break;
            }
        }

        return dateInterval;
    }

    public List<VisitReferrer> mergeReferrers(final List<VisitReferrer> l1, final List<VisitReferrer> l2) {
        final List<VisitReferrer> resultList = new ArrayList<VisitReferrer>();
        resultList.addAll(l1);
        for (VisitReferrer ref2 : l2) {
            boolean foundMatchingKey = false;
            for (VisitReferrer ref1 : resultList) {
                if (ref1.getTermOrUrl().equals(ref2.getTermOrUrl())) {
                    ref1.setVisitCount(ref1.getVisitCount() + ref2.getVisitCount());
                    foundMatchingKey = true;
                    break;
                }
            }

            if (foundMatchingKey) {
                continue;
            }

            resultList.add(ref2);
        }

        return resultList;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    public final static Integer STATISTICS_REFERENCE_URL_LIMIT = 5;
    public final static Integer STATISTICS_REFERENCE_SEARCH_TERMS_LIMIT = 5;

}
