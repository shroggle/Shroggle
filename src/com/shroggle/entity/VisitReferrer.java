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

import com.shroggle.util.cache.CachePolicy;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;

/**
 * @author dmitry.solomadin
 */
@CachePolicy(maxElementsInMemory = 40000)
@Entity(name = "visitReferrers")
public class VisitReferrer {

    @Id
    private int visitReferrerId;

    @ManyToOne
    @JoinColumn(name = "visitId", nullable = false)
    @ForeignKey(name = "visitReferrersVisitId")
    private Visit visit;

    @Column(length = 2000) // max url site among common browsers is 2000.
    private String termOrUrl;

    private int visitCount;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private VisitReferrerType visitReferrerType;

    public VisitReferrerType getVisitReferrerType() {
        return visitReferrerType;
    }

    public void setVisitReferrerType(VisitReferrerType visitReferrerType) {
        this.visitReferrerType = visitReferrerType;
    }

    public int getVisitReferrerId() {
        return visitReferrerId;
    }

    public void setVisitReferrerId(int visitReferrerId) {
        this.visitReferrerId = visitReferrerId;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public String getTermOrUrl() {
        return termOrUrl;
    }

    public void setTermOrUrl(String termOrUrl) {
        this.termOrUrl = termOrUrl;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }
}
