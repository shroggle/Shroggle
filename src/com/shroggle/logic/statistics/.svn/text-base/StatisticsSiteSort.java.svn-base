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
import com.shroggle.logic.paginator.Paginator;

import java.util.*;

public class StatisticsSiteSort {

    //sites - site's list to sort
    //statisticsSortType - site field to sort by
    //DESC - is sort direction if true then lower value will be on the bottom of the table
    public Paginator<SiteTrafficInfo> sort(final List<Site> sites, final StatisticsSortType statisticsSortType, final Boolean DESC,
                                      final Integer pageNumber) {
        if (sites == null || sites.isEmpty()) {
            return new Paginator<SiteTrafficInfo>(Collections.<SiteTrafficInfo>emptyList()).setPageNumber(pageNumber);
        }

        List<SiteTrafficInfo> returnList = new ArrayList<SiteTrafficInfo>();

        if (statisticsSortType != null && DESC != null) {
            Comparator<Site> comparator = null;
            if (statisticsSortType.equals(StatisticsSortType.NAME)) {
                comparator = new TitleComparator();
            } else if (statisticsSortType.equals(StatisticsSortType.VISITS)) {
                comparator = new VisitorComparator();
            } else if (statisticsSortType.equals(StatisticsSortType.HITS)) {
                comparator = new HitsComparator();
            } else if (statisticsSortType.equals(StatisticsSortType.TIME)) {
                comparator = new TimeComparator();
            } else if (statisticsSortType.equals(StatisticsSortType.REF_URLS)) {
                comparator = new RefUrlComparator();
            }

            if (comparator != null) {
                Collections.sort(sites, comparator);
            }

            if (DESC) {
                Collections.reverse(sites);
            }
        } else {
            return new Paginator<SiteTrafficInfo>(Collections.<SiteTrafficInfo>emptyList()).setPageNumber(pageNumber);
        }

        for (Site site : sites) {
            SiteTrafficInfo siteTrafficInfo = new SiteTrafficInfo();
            siteTrafficInfo.setSiteId(site.getSiteId());

            long hits = statisticsManager.getHitsForSite(site, StatisticsTimePeriodType.ALL_TIME);
            long visits = statisticsManager.getVisitsForSite(site, StatisticsTimePeriodType.ALL_TIME);
            long time = statisticsManager.getOverallTimeSpendForSite(site, StatisticsTimePeriodType.ALL_TIME);
            Map<String, Integer> refUrls = statisticsManager.getRefUrlsForSite(site, StatisticsTimePeriodType.ALL_TIME);

            siteTrafficInfo.setSiteName(site.getTitle() == null ? site.getSubDomain() : site.getTitle());
            siteTrafficInfo.setHits(hits);
            siteTrafficInfo.setVisits(visits);
            siteTrafficInfo.setTime(time);

            List<String> refURLsNames = new ArrayList<String>();
            List<Integer> refURLsValues = new ArrayList<Integer>();
            Integer referenceURLsLimit = StatisticsManager.STATISTICS_REFERENCE_URL_LIMIT;
            for (Map.Entry<String, Integer> entry : statisticsManager.sortMapByValue(refUrls)) {
                if (referenceURLsLimit == 0) {
                    break;
                }

                refURLsNames.add(entry.getKey());
                refURLsValues.add(entry.getValue());
                referenceURLsLimit--;
            }
            siteTrafficInfo.setRefURLs(refURLsNames);
            siteTrafficInfo.setRefURLsVisitCount(refURLsValues);

            returnList.add(siteTrafficInfo);
        }

        return new Paginator<SiteTrafficInfo>(returnList).setPageNumber(pageNumber);
    }

    private class TitleComparator implements Comparator<Site> {

        public int compare(Site s1, Site s2) {
            return s1.getTitle().compareTo(s2.getTitle());
        }

    }

    private class TimeComparator implements Comparator<Site> {

        public int compare(Site s1, Site s2) {
            long s1Time = statisticsManager.getOverallTimeSpendForSite(s1, StatisticsTimePeriodType.ALL_TIME);
            long s2Time = statisticsManager.getOverallTimeSpendForSite(s2, StatisticsTimePeriodType.ALL_TIME);

            if (s1Time > s2Time) {
                return 1;
            } else if (s1Time > s2Time) {
                return -1;
            } else {
                return 0;
            }
        }

    }

    private class RefUrlComparator implements Comparator<Site> {

        public int compare(Site s1, Site s2) {
            Map<String, Integer> s1_ref_urls = new HashMap<String, Integer>();
            for (Page page : s1.getPages()) {
                LinkedList<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(statisticsManager.getRefURLsForPage(page, StatisticsTimePeriodType.ALL_TIME).entrySet());
                for (Map.Entry<String, Integer> entry : list) {
                    if (s1_ref_urls.containsKey(entry.getKey())) {
                        s1_ref_urls.put(entry.getKey(), s1_ref_urls.get(entry.getKey()) + entry.getValue());
                    } else {
                        s1_ref_urls.put(entry.getKey(), entry.getValue());
                    }
                }
            }

            Map<String, Integer> s2_ref_urls = new HashMap<String, Integer>();
            for (Page page : s2.getPages()) {
                LinkedList<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(statisticsManager.getRefURLsForPage(page, StatisticsTimePeriodType.ALL_TIME).entrySet());
                for (Map.Entry<String, Integer> entry : list) {
                    if (s1_ref_urls.containsKey(entry.getKey())) {
                        s1_ref_urls.put(entry.getKey(), s1_ref_urls.get(entry.getKey()) + entry.getValue());
                    } else {
                        s1_ref_urls.put(entry.getKey(), entry.getValue());
                    }
                }
            }

            return statisticsManager.maxStringInMap(s1_ref_urls).getValue().compareTo(statisticsManager.maxStringInMap(s2_ref_urls).getValue());
        }

    }

    private class VisitorComparator implements Comparator<Site> {

        public int compare(Site s1, Site s2) {
            Long v1 = statisticsManager.getVisitsForSite(s1, StatisticsTimePeriodType.ALL_TIME);
            Long v2 = statisticsManager.getVisitsForSite(s2, StatisticsTimePeriodType.ALL_TIME);
            return v1.compareTo(v2);
        }

    }

    private class HitsComparator implements Comparator<Site> {

        public int compare(Site s1, Site s2) {
            Long v1 = statisticsManager.getHitsForSite(s1, StatisticsTimePeriodType.ALL_TIME);
            Long v2 = statisticsManager.getHitsForSite(s2, StatisticsTimePeriodType.ALL_TIME);
            return v1.compareTo(v2);
        }
    }

    private final StatisticsManager statisticsManager = new StatisticsManager();

}
