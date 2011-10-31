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
import com.shroggle.logic.paginator.Paginator;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.*;

public class StatisticsPageSort {

    //pages - page's list to sort
    //pageSortType - page field to sort by
    //desc - is sort direction if true then lower value will be on the bottom of the table
    //statisticsTimePeriodType - describes time period.
    public Paginator<PageTrafficInfo> sort(
            final List<Page> pages, final StatisticsSortType sortType,
            final boolean desc, final StatisticsTimePeriodType statisticsTimePeriodType, final Integer pageNumber) {
        if (pages == null || pages.isEmpty()) {
            return new Paginator<PageTrafficInfo>(Collections.<PageTrafficInfo>emptyList()).setPageNumber(pageNumber);
        }

        this.statisticsTimePeriodType = statisticsTimePeriodType;

        if (sortType != null) {
            Comparator<Page> comparator = null;
            if (sortType.equals(StatisticsSortType.NAME)) {
                comparator = nameComparator;
            } else if (sortType.equals(StatisticsSortType.VISITS)) {
                comparator = visitorComparator;
            } else if (sortType.equals(StatisticsSortType.HITS)) {
                comparator = hitsComparator;
            } else if (sortType.equals(StatisticsSortType.TIME)) {
                comparator = timeComparator;
            } else if (sortType.equals(StatisticsSortType.REF_URLS)) {
                comparator = refUrlComparator;
            } else if (sortType.equals(StatisticsSortType.SEARCH_TERMS)) {
                comparator = refTermComparator;
            }

            if (comparator != null) {
                Collections.sort(pages, comparator);
            }

            if (desc) {
                Collections.reverse(pages);
            }
        } else {
            return new Paginator<PageTrafficInfo>(Collections.<PageTrafficInfo>emptyList()).setPageNumber(pageNumber);
        }

        final List<Integer> pageIds = new ArrayList<Integer>();
        for (final Page page : pages) {
            pageIds.add(page.getPageId());
        }

        final Map<Integer, Long> hitsForPages = statisticsManager.getHitsForPagesSeparately(pageIds, statisticsTimePeriodType);
        final Map<Integer, Long> visitsForPages = statisticsManager.getVisitsForPagesSeparately(pageIds, statisticsTimePeriodType);
        final Map<Integer, Long> overallTimesForPages = statisticsManager.getOverallTimeSpendForPagesSeparately(pageIds, statisticsTimePeriodType);

        final List<PageTrafficInfo> results = new ArrayList<PageTrafficInfo>();
        for (final Page page : pages) {
            final PageTrafficInfo pageTrafficInfo = new PageTrafficInfo();
            pageTrafficInfo.setPageId(page.getPageId());

            final long hits = hitsForPages.containsKey(page.getPageId()) ? hitsForPages.get(page.getPageId()) : (long) 0;
            final long visits = visitsForPages.containsKey(page.getPageId()) ? visitsForPages.get(page.getPageId()) : (long) 0;
            final long time = overallTimesForPages.containsKey(page.getPageId()) ? overallTimesForPages.get(page.getPageId()) : (long) 0;

            pageTrafficInfo.setPageName(new PageManager(page).getName());
            pageTrafficInfo.setHits(hits);
            pageTrafficInfo.setVisits(visits);
            pageTrafficInfo.setTime(time);

            final List<String> refURLsNames = new ArrayList<String>();
            final List<Integer> refURLsValues = new ArrayList<Integer>();
            Integer referenceURLsLimit = StatisticsManager.STATISTICS_REFERENCE_URL_LIMIT;
            for (final Map.Entry<String, Integer> entry : statisticsManager.sortMapByValue(statisticsManager.getRefURLsForPage(page, statisticsTimePeriodType))) {
                if (referenceURLsLimit == 0) {
                    break;
                }

                refURLsNames.add(entry.getKey());
                refURLsValues.add(entry.getValue());
                referenceURLsLimit--;
            }
            pageTrafficInfo.setRefURLs(refURLsNames);
            pageTrafficInfo.setRefURLsVisitCount(refURLsValues);

            final List<String> refTermsNames = new ArrayList<String>();
            final List<Integer> refTermsValues = new ArrayList<Integer>();
            Integer referenceSearchTermsLimit = StatisticsManager.STATISTICS_REFERENCE_SEARCH_TERMS_LIMIT;
            for (final Map.Entry<String, Integer> entry : statisticsManager.sortMapByValue(statisticsManager.getRefSearchTermsForPage(page, statisticsTimePeriodType))) {
                if (referenceSearchTermsLimit == 0) {
                    break;
                }

                refTermsNames.add(entry.getKey());
                refTermsValues.add(entry.getValue());
                referenceSearchTermsLimit--;
            }
            pageTrafficInfo.setRefTerms(refTermsNames);
            pageTrafficInfo.setRefTermsVisitCount(refTermsValues);

            results.add(pageTrafficInfo);
        }

        return new Paginator<PageTrafficInfo>(results).setPageNumber(pageNumber);
    }

    private final Comparator<Page> refUrlComparator = new Comparator<Page>() {

        public int compare(final Page p1, final Page p2) {
            Map<String, Integer> p1_ref_urls = new HashMap<String, Integer>();
            LinkedList<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(statisticsManager.getRefURLsForPage(p1, statisticsTimePeriodType).entrySet());
            for (Map.Entry<String, Integer> entry : list) {
                if (p1_ref_urls.containsKey(entry.getKey())) {
                    p1_ref_urls.put(entry.getKey(), p1_ref_urls.get(entry.getKey()) + entry.getValue());
                } else {
                    p1_ref_urls.put(entry.getKey(), entry.getValue());
                }
            }

            Map<String, Integer> p2_ref_urls = new HashMap<String, Integer>();
            list = new LinkedList<Map.Entry<String, Integer>>(statisticsManager.getRefURLsForPage(p2, statisticsTimePeriodType).entrySet());
            for (Map.Entry<String, Integer> entry : list) {
                if (p2_ref_urls.containsKey(entry.getKey())) {
                    p2_ref_urls.put(entry.getKey(), p2_ref_urls.get(entry.getKey()) + entry.getValue());
                } else {
                    p2_ref_urls.put(entry.getKey(), entry.getValue());
                }
            }

            return statisticsManager.maxStringInMap(p1_ref_urls).getValue().compareTo(statisticsManager.maxStringInMap(p2_ref_urls).getValue());
        }

    };

    private final Comparator<Page> refTermComparator = new Comparator<Page>() {

        public int compare(final Page p1, final Page p2) {
            Map<String, Integer> p1_ref_terms = new HashMap<String, Integer>();
            LinkedList<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(statisticsManager.getRefSearchTermsForPage(p1, statisticsTimePeriodType).entrySet());
            for (Map.Entry<String, Integer> entry : list) {
                if (p1_ref_terms.containsKey(entry.getKey())) {
                    p1_ref_terms.put(entry.getKey(), p1_ref_terms.get(entry.getKey()) + entry.getValue());
                } else {
                    p1_ref_terms.put(entry.getKey(), entry.getValue());
                }
            }

            Map<String, Integer> p2_ref_terms = new HashMap<String, Integer>();
            list = new LinkedList<Map.Entry<String, Integer>>(statisticsManager.getRefSearchTermsForPage(p2, statisticsTimePeriodType).entrySet());
            for (Map.Entry<String, Integer> entry : list) {
                if (p2_ref_terms.containsKey(entry.getKey())) {
                    p2_ref_terms.put(entry.getKey(), p2_ref_terms.get(entry.getKey()) + entry.getValue());
                } else {
                    p2_ref_terms.put(entry.getKey(), entry.getValue());
                }
            }

            return statisticsManager.maxStringInMap(p1_ref_terms).getValue().compareTo(statisticsManager.maxStringInMap(p2_ref_terms).getValue());
        }

    };

    final static Comparator<Page> nameComparator = new Comparator<Page>() {

        public int compare(final Page p1, final Page p2) {
            String pv1Name = new PageManager(p1).getName();
            if (pv1Name == null) {
                pv1Name = "";
            }
            String pv2Name = new PageManager(p2).getName();
            if (pv2Name == null) {
                pv2Name = "";
            }

            return pv1Name.compareTo(pv2Name);
        }

    };

    private final Comparator<Page> timeComparator = new Comparator<Page>() {

        public int compare(final Page p1, final Page p2) {
            final StatisticsManager statisticsManager = new StatisticsManager();
            final long s1Time = statisticsManager.getOverallTimeSpendForPage(p1, statisticsTimePeriodType);
            final long s2Time = statisticsManager.getOverallTimeSpendForPage(p2, statisticsTimePeriodType);
            return (int) (s1Time - s2Time);
        }

    };

    private final static Comparator<Page> visitorComparator = new Comparator<Page>() {

        public int compare(final Page p1, final Page p2) {
            final Integer v1 = p1.getPageVisits().size();
            final Integer v2 = p2.getPageVisits().size();
            return v1.compareTo(v2);
        }

    };

    private final Comparator<Page> hitsComparator = new Comparator<Page>() {

        public int compare(final Page p1, final Page p2) {
            final StatisticsManager statisticsManager = new StatisticsManager();
            final Long v1 = statisticsManager.getHitsForPagesSeparately(Arrays.asList(p1.getPageId()), statisticsTimePeriodType).get(p1.getPageId());
            final Long v2 = statisticsManager.getHitsForPagesSeparately(Arrays.asList(p2.getPageId()), statisticsTimePeriodType).get(p2.getPageId());
            return v1.compareTo(v2);
        }

    };

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final StatisticsManager statisticsManager = new StatisticsManager();
    private StatisticsTimePeriodType statisticsTimePeriodType;

}
