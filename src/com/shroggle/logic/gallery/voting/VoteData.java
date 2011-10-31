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
 */
public class VoteData {

    public VoteData(int voteId, int voteValue, int filledFormId, boolean winner) {
        this.voteId = voteId;
        this.voteValue = voteValue;
        this.filledFormId = filledFormId;
        this.winner = winner;
    }

    private final int voteId;

    private final int voteValue;

    private final int filledFormId;

    private final boolean winner;

    public int getVoteId() {
        return voteId;
    }

    public int getVoteValue() {
        return voteValue;
    }

    public int getFilledFormId() {
        return filledFormId;
    }

    public boolean isWinner() {
        return winner;
    }
}
