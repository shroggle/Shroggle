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
package com.shroggle.logic.gallery.voting;

import com.shroggle.entity.VoteSettings;

/**
 * @author Balakirev Anatoliy
 */
public class VotingStarsData implements Cloneable {

    private int siteId;

    private int widgetId;

    private int galleryId;

    private VoteData voteData;

    private VoteSettings voteSettings;

    private boolean disabled;

    private boolean wrongStartOrEndDate;

    private boolean filledFormExist;

    private String startDate;

    private String endDate;

    //This parameter is true if 'voting module' checkbox is unchecked on the gallery. Work's for Manage Your Votes page.
    private boolean isVotingEnded;

    public boolean isFilledFormExist() {
        return filledFormExist;
    }

    public void setFilledFormExist(boolean filledFormExist) {
        this.filledFormExist = filledFormExist;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isWrongStartOrEndDate() {
        return wrongStartOrEndDate;
    }

    public void setWrongStartOrEndDate(boolean wrongStartOrEndDate) {
        this.wrongStartOrEndDate = wrongStartOrEndDate;
    }

    public boolean isVotingEnded() {
        return isVotingEnded;
    }

    public void setVotingEnded(boolean votingEnded) {
        isVotingEnded = votingEnded;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public VoteSettings getVoteSettings() {
        return voteSettings;
    }

    public void setVoteSettings(VoteSettings voteSettings) {
        this.voteSettings = voteSettings;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public int getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(int widgetId) {
        this.widgetId = widgetId;
    }

    public int getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(int galleryId) {
        this.galleryId = galleryId;
    }

    public VoteData getVoteData() {
        return voteData;
    }

    public void setVoteData(VoteData voteData) {
        this.voteData = voteData;
    }

    public VotingStarsData clone() throws CloneNotSupportedException {
        return (VotingStarsData) super.clone();
    }
}
