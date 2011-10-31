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

/**
 * @author Balakirev Anatoliy
 *         Date: 02.09.2009
 */
public class VotingLinksData {

    private String manageVotesUrl;

    public boolean manageVotesOnCurrentPage;

    private boolean includeLinkToManageYourVotes;

    public boolean isIncludeLinkToManageYourVotes() {
        return includeLinkToManageYourVotes;
    }

    public void setIncludeLinkToManageYourVotes(boolean includeLinkToManageYourVotes) {
        this.includeLinkToManageYourVotes = includeLinkToManageYourVotes;
    }

    public boolean isManageVotesOnCurrentPage() {
        return manageVotesOnCurrentPage;
    }

    public void setManageVotesOnCurrentPage(boolean manageVotesOnCurrentPage) {
        this.manageVotesOnCurrentPage = manageVotesOnCurrentPage;
    }

    public String getManageVotesUrl() {
        return manageVotesUrl;
    }

    public void setManageVotesUrl(String manageVotesUrl) {
        this.manageVotesUrl = manageVotesUrl;
    }
}
