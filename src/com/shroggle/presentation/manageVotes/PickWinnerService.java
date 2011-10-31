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
package com.shroggle.presentation.manageVotes;

import com.shroggle.entity.DraftManageVotes;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.entity.Vote;
import com.shroggle.exception.VoteNotFoundException;
import com.shroggle.logic.manageVotes.ManageVotesManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class PickWinnerService extends ServiceWithExecutePage {
    private PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private Persistance persistance = ServiceLocator.getPersistance();

    @RemoteMethod
    public String execute(final int galleryId, final int voteId, final int manageVotesId, final SiteShowOption siteShowOption) throws IOException, ServletException {
        final UserManager userManager = new UsersManager().getLogined();

        final Vote newWinnerVote = persistance.getVoteById(voteId);

        if (newWinnerVote == null) {
            throw new VoteNotFoundException("Cannot find vote by Id=" + voteId);
        }

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                persistance.setAllWinnerVotesToFalse(userManager.getUserId(), galleryId);
                newWinnerVote.setWinner(true);
            }
        });

        final DraftManageVotes draftManageVotes = persistance.getDraftItem(manageVotesId);
        getContext().getHttpServletRequest().setAttribute("winnerList",
                new ManageVotesManager(draftManageVotes, siteShowOption).constructWinnerList());
        getContext().getHttpServletRequest().setAttribute("siteShowOption", siteShowOption);
        return executePage("/site/render/manageVotes/winnerList.jsp");
    }
}
