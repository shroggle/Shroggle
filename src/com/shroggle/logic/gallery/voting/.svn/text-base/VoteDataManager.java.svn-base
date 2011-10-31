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

import com.shroggle.entity.*;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.ServiceLocator;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Balakirev Anatoliy
 */
public class VoteDataManager {

    public VoteDataManager(Gallery gallery, User user) {
        this.gallery = gallery;
        this.userId = user != null ? user.getUserId() : -1;
    }
   
    public List<VoteData> createVoteDataList(final List<Integer> filledFormIds) {
        final VoteSettings voteSettings = gallery.getVoteSettings();
        final List<Vote> votesByStartEndDates = persistance.getVotesByStartEndDates(userId, gallery.getId(),
                voteSettings.getStartDate(), voteSettings.getEndDate(), filledFormIds.toArray(new Integer[filledFormIds.size()]));
        final List<VoteData> voteDatas = new ArrayList<VoteData>();
        for (Vote vote : votesByStartEndDates) {
            voteDatas.add(new VoteData(vote.getVoteId(), vote.getVoteValue(), vote.getFilledFormId(), vote.isWinner()));
            filledFormIds.remove(new Integer(vote.getFilledFormId()));
        }
        if (!filledFormIds.isEmpty()) {
            final List<Vote> votesByTimeInterval = persistance.getVotesByTimeInterval(userId, gallery.getId(),
                    voteSettings.getStartDate(), voteSettings.getEndDate(), filledFormIds.toArray(new Integer[filledFormIds.size()]));
            for (Integer filledFormId : filledFormIds) {
                final List<Vote> votes = selectByFilledFormId(votesByTimeInterval, filledFormId);
                voteDatas.add(new VoteData(0, createAveragedVoteValue(votes), filledFormId, false));
            }
        }
        return voteDatas;
    }

    private List<Vote> selectByFilledFormId(final List<Vote> votes, final int filledFormId) {
        final List<Vote> newVotes = new ArrayList<Vote>();
        for (Vote vote : votes) {
            if (vote.getFilledFormId() == filledFormId) {
                newVotes.add(vote);
            }
        }
        return newVotes;
    }

    private int createAveragedVoteValue(final List<Vote> votes) {
        if (!votes.isEmpty()) {
            int voteValue = 0;
            for (Vote tempVote : votes) {
                voteValue += tempVote.getVoteValue();
            }
            return (voteValue / votes.size());
        } else {
            return 0;
        }
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final Gallery gallery;
    private final Integer userId;
}
