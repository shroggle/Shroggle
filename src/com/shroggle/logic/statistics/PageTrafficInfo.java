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

import org.directwebremoting.annotations.DataTransferObject;

import java.util.List;

@DataTransferObject
public class PageTrafficInfo {
    private String pageName;
    private long hits;
    private long visits;
    private long time;
    private List<String> refURLs;
    private List<String> refTerms;
    private List<Integer> refTermsVisitCount;
    private List<Integer> refURLsVisitCount;

    private int pageId;

    public List<Integer> getRefTermsVisitCount() {
        return refTermsVisitCount;
    }

    public void setRefTermsVisitCount(List<Integer> refTermsVisitCount) {
        this.refTermsVisitCount = refTermsVisitCount;
    }

    public List<Integer> getRefURLsVisitCount() {
        return refURLsVisitCount;
    }

    public void setRefURLsVisitCount(List<Integer> refURLsVisitCount) {
        this.refURLsVisitCount = refURLsVisitCount;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public long getHits() {
        return hits;
    }

    public void setHits(long hits) {
        this.hits = hits;
    }

    public long getVisits() {
        return visits;
    }

    public void setVisits(long visits) {
        this.visits = visits;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<String> getRefURLs() {
        return refURLs;
    }

    public void setRefURLs(List<String> refURLs) {
        this.refURLs = refURLs;
    }

    public List<String> getRefTerms() {
        return refTerms;
    }

    public void setRefTerms(List<String> refTerms) {
        this.refTerms = refTerms;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }
}
