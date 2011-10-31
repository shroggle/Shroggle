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
public class SiteTrafficInfo {

    private String siteName;
    private long hits;
    private long visits;
    private long time;
    private List<String> refURLs;
    private List<Integer> refURLsVisitCount;

    private int siteId;

    public List<Integer> getRefURLsVisitCount() {
        return refURLsVisitCount;
    }

    public void setRefURLsVisitCount(List<Integer> refURLsVisitCount) {
        this.refURLsVisitCount = refURLsVisitCount;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public List<String> getRefURLs() {
        return refURLs;
    }

    public void setRefURLs(List<String> refURLs) {
        this.refURLs = refURLs;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
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
}
