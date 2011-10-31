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

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import org.hibernate.annotations.CollectionOfElements;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
@Entity(name = "pageVisitors")
public class PageVisitor {
    
    @Id
    @RemoteProperty
    private int pageVisitorId;

    @Column(name = "visitorId")
    private Integer userId;

    @OneToMany(mappedBy = "pageVisitor", cascade = CascadeType.ALL)
    private List<Visit> visits = new ArrayList<Visit>();

    @OneToMany(mappedBy = "pageVisitor", cascade = CascadeType.ALL)
    private List<FilledForm> filledForms = new ArrayList<FilledForm>();

    @CollectionOfElements 
    private List<Integer> videoRangeIds = new ArrayList<Integer>();

    public List<Integer> getVideoRangeIds() {
        return videoRangeIds;
    }

    public void setVideoRangeIds(List<Integer> videoRangeIds) {
        this.videoRangeIds = videoRangeIds;
    }

    public void addVideoRangeId(Integer videoRangeId) {
        videoRangeIds.add(videoRangeId);
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<FilledForm> getFilledForms() {
        return Collections.unmodifiableList(filledForms);
    }

    public void removeFilledForm(FilledForm filledForm) {
        filledForms.remove(filledForm);
    }

    public void setFilledForms(List<FilledForm> filledForms) {
        this.filledForms = filledForms;
    }

    public void addFilledForm(FilledForm filledForm) {
        filledForms.add(filledForm);
    }

    public void addVisit(Visit visit) {
        visits.add(visit);
    }

    public int getPageVisitorId() {
        return pageVisitorId;
    }

    public void setPageVisitorId(int pageVisitorId) {
        this.pageVisitorId = pageVisitorId;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }
}
