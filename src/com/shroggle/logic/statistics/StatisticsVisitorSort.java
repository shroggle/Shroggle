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
import com.shroggle.entity.Visit;
import com.shroggle.entity.VisitReferrer;
import com.shroggle.logic.paginator.Paginator;
import com.shroggle.util.DateUtil;

import java.util.*;

/**
 * author: dmitry.solomadin
 * date: 06.11.2008
 */
public class StatisticsVisitorSort {
    private StatisticsManager statisticsManager = new StatisticsManager();

    //visits - visits to specific page
    //statisticsSortType - visitor field to sort by
    //DESC - is sort direction if true then lower value will be on the bottom of the table
    public Paginator<VisitorTrafficInfo> sort(final Page page, final StatisticsSortType statisticsSortType,
                                         final Boolean DESC, final StatisticsTimePeriodType statisticsTimePeriodType,
                                         final Integer pageNumber) {
        List<Visit> rawVisits = statisticsManager.getVisitsAccordToTimePeriod(page, statisticsTimePeriodType);
        Map<Integer, Visit> mergedVisitMap = new HashMap<Integer, Visit>();
        for (Visit visit : rawVisits) {
            if (!mergedVisitMap.containsKey(visit.getPageVisitor().getPageVisitorId())) {
                mergedVisitMap.put(visit.getPageVisitor().getPageVisitorId(), visit);
            } else {
                Visit mergedVisit = mergedVisitMap.get(visit.getPageVisitor().getPageVisitorId());
                mergedVisit.setReferrers(statisticsManager.mergeReferrers(mergedVisit.getReferrerURLs(), visit.getReferrerURLs()));
                mergedVisit.setVisitCount(mergedVisit.getVisitCount() + visit.getVisitCount());
                mergedVisit.setOverallTimeOfVisit(mergedVisit.getOverallTimeOfVisit() + visit.getOverallTimeOfVisit());
                if (mergedVisit.getVisitCreationDate().before(visit.getVisitCreationDate())) {
                    mergedVisit.setVisitCreationDate(visit.getVisitCreationDate());
                }
            }
        }

        List<Visit> mergedVisits = new ArrayList<Visit>();
        mergedVisits.addAll(mergedVisitMap.values());

        if (mergedVisits == null || mergedVisits.isEmpty()) {
            return new Paginator<VisitorTrafficInfo>(Collections.<VisitorTrafficInfo>emptyList()).setPageNumber(pageNumber);
        }

        List<VisitorTrafficInfo> returnList = new ArrayList<VisitorTrafficInfo>();

        if (statisticsSortType != null && DESC != null) {
            Comparator<Visit> comparator = null;
            if (statisticsSortType.equals(StatisticsSortType.VISITOR_ID)) {
                comparator = new VisitorIdComparator();
            } else if (statisticsSortType.equals(StatisticsSortType.VISIT_DATE)) {
                comparator = new VisitDateComparator();
            } else if (statisticsSortType.equals(StatisticsSortType.HITS)) {
                comparator = new HitsComparator();
            } else if (statisticsSortType.equals(StatisticsSortType.TIME)) {
                comparator = new TimeComparator();
            } else if (statisticsSortType.equals(StatisticsSortType.REF_URLS)) {
                comparator = new RefUrlComparator();
            } else if (statisticsSortType.equals(StatisticsSortType.SEARCH_TERMS)) {
                comparator = new RefTermComparator();
            }

            if (comparator != null) {
                Collections.sort(mergedVisits, comparator);
            }

            if (DESC) {
                Collections.reverse(mergedVisits);
            }
        } else {
            return new Paginator<VisitorTrafficInfo>(Collections.<VisitorTrafficInfo>emptyList()).setPageNumber(pageNumber);
        }

        for (Visit visit : mergedVisits) {
            VisitorTrafficInfo visitorTrafficInfo = new VisitorTrafficInfo();
            visitorTrafficInfo.setPageVisitorId(visit.getPageVisitor().getPageVisitorId());

            Locale.setDefault(new Locale("en"));

            long hits = visit.getVisitCount();
            long time = 0;
            if (hits != 0) {
                time = Math.round(visit.getOverallTimeOfVisit() / hits);
            }

            List<String> refURLsNames = new ArrayList<String>();
            List<Integer> refURLsValues = new ArrayList<Integer>();
            Integer referenceURLsLimit = StatisticsManager.STATISTICS_REFERENCE_URL_LIMIT;
            for (VisitReferrer visitReferrer : statisticsManager.sortReferrersByValue(visit.getReferrerURLs())) {
                if (referenceURLsLimit == 0) {
                    break;
                }

                refURLsNames.add(visitReferrer.getTermOrUrl());
                refURLsValues.add(visitReferrer.getVisitCount());
                referenceURLsLimit--;
            }

            List<String> refTermsNames = new ArrayList<String>();
            List<Integer> refTermsValues = new ArrayList<Integer>();
            Integer referenceSearchTermsLimit = StatisticsManager.STATISTICS_REFERENCE_SEARCH_TERMS_LIMIT;
            for (VisitReferrer visitReferrer : statisticsManager.sortReferrersByValue(visit.getReferrerSearchTerms())) {
                if (referenceSearchTermsLimit == 0) {
                    break;
                }

                refTermsNames.add(visitReferrer.getTermOrUrl());
                refTermsValues.add(visitReferrer.getVisitCount());
                referenceSearchTermsLimit--;
            }

            visitorTrafficInfo.setLastVisit(DateUtil.toCommonDateStr(visit.getVisitCreationDate()));
            visitorTrafficInfo.setHits(hits);
            visitorTrafficInfo.setTime(time);
            visitorTrafficInfo.setRefURLs(refURLsNames);
            visitorTrafficInfo.setRefURLsVisitCount(refURLsValues);
            visitorTrafficInfo.setRefTerms(refTermsNames);
            visitorTrafficInfo.setRefTermsVisitCount(refTermsValues);
            visitorTrafficInfo.setVisitorId(visit.getPageVisitor().getUserId());

            returnList.add(visitorTrafficInfo);
        }

        return new Paginator<VisitorTrafficInfo>(returnList).setPageNumber(pageNumber);
    }

    private class RefUrlComparator implements Comparator<Visit> {

        public int compare(Visit v1, Visit v2) {
            Integer v1_c = statisticsManager.maxStringInReferrerList(v1.getReferrerURLs()).getVisitCount();
            Integer v2_c = statisticsManager.maxStringInReferrerList(v2.getReferrerURLs()).getVisitCount();
            return v1_c.compareTo(v2_c);
        }

    }

    private class RefTermComparator implements Comparator<Visit> {

        public int compare(Visit v1, Visit v2) {
            Integer v1_c = statisticsManager.maxStringInReferrerList(v1.getReferrerSearchTerms()).getVisitCount();
            Integer v2_c = statisticsManager.maxStringInReferrerList(v2.getReferrerSearchTerms()).getVisitCount();
            return v1_c.compareTo(v2_c);
        }

    }

    private class VisitorIdComparator implements Comparator<Visit> {

        public int compare(Visit v1, Visit v2) {
            Integer i1 = v1.getPageVisitor().getPageVisitorId();
            Integer i2 = v2.getPageVisitor().getPageVisitorId();
            return i1.compareTo(i2);
        }

    }

    private class TimeComparator implements Comparator<Visit> {

        public int compare(Visit v1, Visit v2) {
            long s1Time = v1.getOverallTimeOfVisit(), s2Time = v2.getOverallTimeOfVisit();

            long hits1 = v1.getVisitCount();
            if (hits1 != 0) {
                s1Time = Math.round(s1Time / hits1);
            }

            long hits2 = v2.getVisitCount();
            if (hits2 != 0) {
                s2Time = Math.round(s2Time / hits2);
            }

            if (s1Time > s2Time) {
                return 1;
            } else if (s1Time > s2Time) {
                return -1;
            } else {
                return 0;
            }
        }

    }

    private class VisitDateComparator implements Comparator<Visit> {

        public int compare(Visit v1, Visit v2) {
            Date d1 = v1.getVisitCreationDate();
            Date d2 = v2.getVisitCreationDate();
            return d1.compareTo(d2);
        }

    }

    private class HitsComparator implements Comparator<Visit> {

        public int compare(Visit v1, Visit v2) {
            Long l1 = v1.getVisitCount();
            Long l2 = v2.getVisitCount();
            return l1.compareTo(l2);
        }

    }
}
