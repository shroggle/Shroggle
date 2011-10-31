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

package com.shroggle.entity;

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.cache.CachePolicy;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@CachePolicy(maxElementsInMemory = 20000)
@Entity(name = "visits")
public class Visit {

    @Id
    private int visitId;

    @ManyToOne
    @JoinColumn(name = "pageId", nullable = false)
    @ForeignKey(name = "visitsPageId")
    private Page visitedPage;

    @ManyToOne
    @JoinColumn(name = "pageVisitorId", nullable = false)
    @ForeignKey(name = "visitsPageVisitorId")
    private PageVisitor pageVisitor;

    private long visitCount;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date visitCreationDate;

    @OneToMany(mappedBy = "visit", cascade = CascadeType.ALL)
    private List<VisitReferrer> referrers = new ArrayList<VisitReferrer>();

    //Overall time on page in milliseconds
    private long overallTimeOfVisit;

    public void addReferrerURL(final String referrerURL) {
        for (VisitReferrer URL : getReferrerURLs()) {
            if (URL.getTermOrUrl().equals(referrerURL)) {
                URL.setVisitCount(URL.getVisitCount() + 1);
                return;
            }
        }

        final VisitReferrer newURL = new VisitReferrer();
        newURL.setTermOrUrl(referrerURL);
        newURL.setVisitCount(1);
        newURL.setVisit(this);
        newURL.setVisitReferrerType(VisitReferrerType.URL);
        ServiceLocator.getPersistance().putVisitReferrer(newURL);
        referrers.add(newURL);
    }

    public void addReferrerSearchTerm(final String referrerTerm) {
        for (VisitReferrer term : getReferrerSearchTerms()) {
            if (term.getTermOrUrl().equals(referrerTerm)) {
                term.setVisitCount(term.getVisitCount() + 1);
                return;
            }
        }

        final VisitReferrer newSearchTerm = new VisitReferrer();
        newSearchTerm.setTermOrUrl(referrerTerm);
        newSearchTerm.setVisitCount(1);
        newSearchTerm.setVisitReferrerType(VisitReferrerType.SEARCH_TERM);
        newSearchTerm.setVisit(this);
        ServiceLocator.getPersistance().putVisitReferrer(newSearchTerm);
        referrers.add(newSearchTerm);
    }

    public List<VisitReferrer> getReferrerURLs() {
        List<VisitReferrer> returnList = new ArrayList<VisitReferrer>();
        for (VisitReferrer existingReferrer : referrers) {
            if (existingReferrer.getVisitReferrerType() == VisitReferrerType.URL) {
                returnList.add(existingReferrer);
            }
        }

        return returnList;
    }

    public int getReferrerURLValueByKey(final String key) {
        for (VisitReferrer visitReferrer : getReferrerURLs()) {
            if (visitReferrer.getTermOrUrl().equals(key)) {
                return visitReferrer.getVisitCount();
            }
        }

        return 0;
    }

    public int getReferrerTermValueByKey(final String key) {
        for (VisitReferrer visitReferrer : getReferrerSearchTerms()) {
            if (visitReferrer.getTermOrUrl().equals(key)) {
                return visitReferrer.getVisitCount();
            }
        }

        return 0;
    }

    public List<VisitReferrer> getReferrerSearchTerms() {
        List<VisitReferrer> returnList = new ArrayList<VisitReferrer>();
        for (VisitReferrer existingReferrer : referrers) {
            if (existingReferrer.getVisitReferrerType() == VisitReferrerType.SEARCH_TERM) {
                returnList.add(existingReferrer);
            }
        }

        return returnList;
    }

    public List<VisitReferrer> getReferrers() {
        return referrers;
    }

    public void setReferrers(List<VisitReferrer> referrers) {
        this.referrers = referrers;
    }

    public int getVisitId() {
        return visitId;
    }

    public void setVisitId(int visitId) {
        this.visitId = visitId;
    }

    public PageVisitor getPageVisitor() {
        return pageVisitor;
    }

    public long getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(long visitCount) {
        this.visitCount = visitCount;
    }

    public void setPageVisitor(PageVisitor pageVisitor) {
        this.pageVisitor = pageVisitor;
    }

    public Date getVisitCreationDate() {
        return visitCreationDate;
    }

    public void setVisitCreationDate(Date visitCreationDate) {
        this.visitCreationDate = visitCreationDate;
    }

    public long getOverallTimeOfVisit() {
        return overallTimeOfVisit;
    }

    public void setOverallTimeOfVisit(long overallTimeOfVisit) {
        this.overallTimeOfVisit = overallTimeOfVisit;
    }

    public Page getVisitedPage() {
        return visitedPage;
    }

    public void setVisitedPage(Page visitedPage) {
        this.visitedPage = visitedPage;
    }
}
