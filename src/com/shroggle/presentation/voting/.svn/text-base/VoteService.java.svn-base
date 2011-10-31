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
package com.shroggle.presentation.voting;

import com.shroggle.entity.*;
import com.shroggle.exception.*;
import com.shroggle.logic.gallery.VideoRangesManager;
import com.shroggle.logic.gallery.voting.VoteManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.ActionUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.PersistanceTransactionContext;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class VoteService extends AbstractService {

    @RemoteMethod
    public int vote(final VoteServiceRequest request) {
        if (!VoteManager.isVoteValueCorrect(request.getVoteValue())) {
            throw new IncorrectVoteValueException("Please enter correct vote value!");
        }

        final DraftGallery gallery = persistance.getGalleryById(request.getGalleryId());
        if (gallery == null) {
            throw new GalleryNotFoundException("Can`t vote without gallery! Gallery Id = " + request.getGalleryId());
        }

        if (!new UsersManager().isUserLoginedAndHasRightsToSite(request.getSiteId())) {
            throw new UserNotLoginedException("Please log in to vote. Click OK to login or register.");
        }

        boolean itHasVideoItem = false;
        final FilledForm filledForm = persistance.getFilledFormById(request.getFilledFormId());
        final DraftForm form = persistance.getFormById(filledForm.getFormId());
        for (final DraftFormItem formItem : form.getDraftFormItems()) {
            if (formItem.getFormItemName() == FormItemName.VIDEO_FILE_UPLOAD) {
                itHasVideoItem = true;
                break;
            }
        }

        final UserManager userManager = new UserManager(new UsersManager().getLoginedUser());

        // I add this because of SW-4311. If user watch his first video and does not stop it - system has no
        // information about watched video and user can`t vote for this video even if he watched enough.
        if (request.getVideoRangeEdit() != null) {
            userManager.getVideoRanges().add(VideoRangesManager.createGalleryVideoRange(request.getVideoRangeEdit()));
        }

        final boolean tempItHasVideoItem = itHasVideoItem;
        final Vote vote = persistanceTransaction.execute(
                new PersistanceTransactionContext<Vote>() {
                    public Vote execute() {
                        /*-------------------Adding videoRanges from pageVisitor to logined user----------------------*/
                        final Integer pageVisitorId = ActionUtil.getPageVisitorId(getContext().getHttpServletRequest().getCookies());
                        final PageVisitor pageVisitor = ServiceLocator.getPersistance().getPageVisitorById(pageVisitorId);
                        if (pageVisitor != null && pageVisitor.getVideoRangeIds() != null && !pageVisitor.getVideoRangeIds().isEmpty()) {
                            for (GalleryVideoRange videoRange : persistance.getGalleryVideoRanges(pageVisitor.getVideoRangeIds())) {
                                userManager.getVideoRanges().add(videoRange);
                            }
                            pageVisitor.setVideoRangeIds(new ArrayList<Integer>());
                        }
                        /*-------------------Adding videoRanges from pageVisitor to logined user----------------------*/

                        if (tempItHasVideoItem) {
                            final VideoRangesManager videoRangesManager = userManager.getVideoRanges();
                            final VoteSettings voteSettings = gallery.getVoteSettings();
                            final int count = videoRangesManager.getCount(request.getGalleryId(), form.getFormId());
                            if (count < voteSettings.getMinimumNumberOfMediaItemsPlayed()) {
                                throw new NotEnoughWatchedFilesException("Please watch more videos!");
                            }

                            final int playedPercent = videoRangesManager.getPercent(
                                    request.getGalleryId(), filledForm.getFilledFormId());
                            if (playedPercent < voteSettings.getMinimumPercentageOfTotalPlayed()) {
                                final int minimumPercentage = voteSettings.getMinimumPercentageOfTotalPlayed();
                                final String defaultMessage = international.get("watchAtLeast", minimumPercentage);
                                final String oneHundredPercentMessage = international.get("watch100Percent");

                                final String errorText = (minimumPercentage == 100 ? oneHundredPercentMessage : defaultMessage);
                                throw new NotEnoughWatchedPercentageOfCurrentFileException(errorText);
                            }
                        }

                        final Vote vote;
                        if (request.getVoteId() != null && request.getVoteId() > 0) {
                            vote = persistance.getVoteById(request.getVoteId());
                        } else {
                            vote = new Vote();
                        }

                        vote.setVoteValue(request.getVoteValue());
                        vote.setVoteDate(new Date());

                        if (request.getVoteId() == null || request.getVoteId() <= 0) {
                            vote.setGalleryId(request.getGalleryId());
                            vote.setFilledFormId(request.getFilledFormId());
                            final User user = new UsersManager().getLoginedUser();
                            vote.setUserId(user != null ? user.getUserId() : null);
                            vote.setStartDate(gallery.getVoteSettings().getStartDate());
                            vote.setEndDate(gallery.getVoteSettings().getEndDate());
                            persistance.putVote(vote);
                        }
                        return vote;
                    }
                });
        return vote != null ? vote.getVoteId() : 0;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final International international = ServiceLocator.getInternationStorage().get("voteSettings", Locale.US);
}
