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

/**
 * author: dmitry.solomadin
 * date: 06.11.2008
 */
@DataTransferObject
public class VisitorTrafficInfo {

    private String lastVisit;
    private long hits;
    // time on page in milliseconds
    private long time;
    private List<String> refURLs;
    private List<String> refTerms;
    private List<Integer> refTermsVisitCount;
    private List<Integer> refURLsVisitCount;

    private int pageVisitorId;
    private Integer visitorId;

    public Integer getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(Integer visitorId) {
        this.visitorId = visitorId;
    }

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

    public long getHits() {
        return hits;
    }

    public void setHits(long hits) {
        this.hits = hits;
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

    public String getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(String lastVisit) {
        this.lastVisit = lastVisit;
    }

    public int getPageVisitorId() {
        return pageVisitorId;
    }

    public void setPageVisitorId(int pageVisitorId) {
        this.pageVisitorId = pageVisitorId;
    }
}
