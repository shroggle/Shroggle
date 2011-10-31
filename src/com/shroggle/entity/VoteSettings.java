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

import org.directwebremoting.annotations.RemoteProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Balakirev Anatoliy
 */

@Embeddable
public class VoteSettings {

    @RemoteProperty
    @Column(nullable = false)
    private boolean displayVote;

    @RemoteProperty
    @Column(nullable = false)
    private boolean displayComments;

    @RemoteProperty
    @Column(nullable = false)
    private boolean durationOfVoteLimited;

    @RemoteProperty
    @Column(nullable = false)
    private boolean includeLinkToManageYourVotes;

    @RemoteProperty
    @Column(nullable = false)
    private boolean minimumNumberAppliesToCurrentFilter;

    @RemoteProperty
    @Column(nullable = false)
    private boolean showAllAvailablePages;

    @RemoteProperty
    @Column(nullable = false)
    private int minimumNumberOfMediaItemsPlayed = 1;

    @RemoteProperty
    @Column(nullable = false)
    private int minimumPercentageOfTotalPlayed = 10;

    @RemoteProperty
    private Integer manageYourVotesCrossWidgetId;

    @RemoteProperty
    private Integer registrationFormIdForVoters;

    @RemoteProperty
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @RemoteProperty
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;


    /*----------------------------------------vote stars, vote links settings-----------------------------------------*/
    private int votingStarsPosition = 0;
    
    private int votingStarsRow;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private GalleryAlign votingStarsAlign = GalleryAlign.CENTER;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private GalleryItemColumn votingStarsColumn = GalleryItemColumn.COLUMN_1;

    @Column(length = 250)
    private String votingStarsName = "";

    private int votingTextLinksPosition = 0;

    private int votingTextLinksRow;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private GalleryAlign votingTextLinksAlign = GalleryAlign.CENTER;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private GalleryItemColumn votingTextLinksColumn = GalleryItemColumn.COLUMN_1;

    @Column(length = 250)
    private String votingTextLinksName = "";

    /*----------------------------------------vote stars, vote links settings-----------------------------------------*/

    public int getVotingStarsPosition() {
        return votingStarsPosition;
    }

    public void setVotingStarsPosition(int votingStarsPosition) {
        this.votingStarsPosition = votingStarsPosition;
    }

    public GalleryAlign getVotingStarsAlign() {
        return votingStarsAlign;
    }

    public void setVotingStarsAlign(GalleryAlign votingStarsAlign) {
        this.votingStarsAlign = votingStarsAlign;
    }

    public GalleryItemColumn getVotingStarsColumn() {
        return votingStarsColumn;
    }

    public void setVotingStarsColumn(final GalleryItemColumn votingStarsColumn) {
        this.votingStarsColumn = votingStarsColumn;
    }

    public String getVotingStarsName() {
        return votingStarsName;
    }

    public void setVotingStarsName(String votingStarsName) {
        this.votingStarsName = votingStarsName;
    }

    public int getVotingTextLinksPosition() {
        return votingTextLinksPosition;
    }

    public void setVotingTextLinksPosition(int votingTextLinksPosition) {
        this.votingTextLinksPosition = votingTextLinksPosition;
    }

    public GalleryAlign getVotingTextLinksAlign() {
        return votingTextLinksAlign;
    }

    public void setVotingTextLinksAlign(GalleryAlign votingTextLinksAlign) {
        this.votingTextLinksAlign = votingTextLinksAlign;
    }

    public GalleryItemColumn getVotingTextLinksColumn() {
        return votingTextLinksColumn;
    }

    public void setVotingTextLinksColumn(GalleryItemColumn votingTextLinksColumn) {
        this.votingTextLinksColumn = votingTextLinksColumn;
    }

    public String getVotingTextLinksName() {
        return votingTextLinksName;
    }

    public void setVotingTextLinksName(String votingTextLinksName) {
        this.votingTextLinksName = votingTextLinksName;
    }

    public boolean isDisplayVote() {
        return displayVote;
    }

    public void setDisplayVote(boolean displayVote) {
        this.displayVote = displayVote;
    }

    public boolean isDisplayComments() {
        return displayComments;
    }

    public void setDisplayComments(boolean displayComments) {
        this.displayComments = displayComments;
    }

    public boolean isDurationOfVoteLimited() {
        return durationOfVoteLimited;
    }

    public void setDurationOfVoteLimited(boolean durationOfVoteLimited) {
        this.durationOfVoteLimited = durationOfVoteLimited;
    }

    public boolean isIncludeLinkToManageYourVotes() {
        return includeLinkToManageYourVotes;
    }

    public void setIncludeLinkToManageYourVotes(boolean includeLinkToManageYourVotes) {
        this.includeLinkToManageYourVotes = includeLinkToManageYourVotes;
    }

    public boolean isMinimumNumberAppliesToCurrentFilter() {
        return minimumNumberAppliesToCurrentFilter;
    }

    public void setMinimumNumberAppliesToCurrentFilter(boolean minimumNumberAppliesToCurrentFilter) {
        this.minimumNumberAppliesToCurrentFilter = minimumNumberAppliesToCurrentFilter;
    }

    public boolean isShowAllAvailablePages() {
        return showAllAvailablePages;
    }

    public void setShowAllAvailablePages(boolean showAllAvailablePages) {
        this.showAllAvailablePages = showAllAvailablePages;
    }

    public int getMinimumNumberOfMediaItemsPlayed() {
        return minimumNumberOfMediaItemsPlayed;
    }

    public void setMinimumNumberOfMediaItemsPlayed(int minimumNumberOfMediaItemsPlayed) {
        this.minimumNumberOfMediaItemsPlayed = minimumNumberOfMediaItemsPlayed;
    }

    public int getMinimumPercentageOfTotalPlayed() {
        return minimumPercentageOfTotalPlayed;
    }

    public void setMinimumPercentageOfTotalPlayed(int minimumPercentageOfTotalPlayed) {
        this.minimumPercentageOfTotalPlayed = minimumPercentageOfTotalPlayed;
    }

    public Integer getManageYourVotesCrossWidgetId() {
        return manageYourVotesCrossWidgetId;
    }

    public void setManageYourVotesCrossWidgetId(Integer manageYourVotesCrossWidgetId) {
        this.manageYourVotesCrossWidgetId = manageYourVotesCrossWidgetId;
    }

    public Integer getRegistrationFormIdForVoters() {
        return registrationFormIdForVoters;
    }

    public void setRegistrationFormIdForVoters(Integer registrationFormIdForVoters) {
        this.registrationFormIdForVoters = registrationFormIdForVoters;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getVotingStarsRow() {
        return votingStarsRow;
    }

    public void setVotingStarsRow(int votingStarsRow) {
        this.votingStarsRow = votingStarsRow;
    }

    public int getVotingTextLinksRow() {
        return votingTextLinksRow;
    }

    public void setVotingTextLinksRow(int votingTextLinksRow) {
        this.votingTextLinksRow = votingTextLinksRow;
    }
}
