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
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.DateUtil;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.entity.SiteShowOption;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public class VoteManager {

    public static boolean isVoteValueCorrect(final int voteValue) {
        return voteValue >= MIN_VOTE_VALUE && voteValue <= MAX_VOTE_VALUE;
    }

    public static int getMaxVoteValue() {
        return MAX_VOTE_VALUE;
    }

    public static VotingLinksData createVotingLinksData(final Gallery gallery, final int currentPageId, final SiteShowOption siteShowOption) {
        Widget widget = ServiceLocator.getPersistance().getWidgetByCrossWidgetsId(gallery.getVoteSettings().getManageYourVotesCrossWidgetId(), siteShowOption);
        VotingLinksData votingLinksData = new VotingLinksData();
        votingLinksData.setManageVotesUrl(createManageVotesUrl(widget, currentPageId, siteShowOption));
        votingLinksData.setManageVotesOnCurrentPage(widget != null && widget.getPage().getPageId() == currentPageId);
        votingLinksData.setIncludeLinkToManageYourVotes(gallery.getVoteSettings().isIncludeLinkToManageYourVotes());
        return votingLinksData;
    }

    //Creates vote stars data by gallery and filledFormIds.

    public static List<VotingStarsData> createVotingStarsData(final Gallery gallery, final int widgetId, final int siteId, final Integer... filledFormIds) {
        if (gallery != null && widgetId > 0 && filledFormIds != null && filledFormIds.length != 0) {
            final VotingStarsData baseVotingStarsData = new VotingStarsData();
            baseVotingStarsData.setWidgetId(widgetId);
            baseVotingStarsData.setSiteId(siteId);
            baseVotingStarsData.setGalleryId(gallery.getId());
            baseVotingStarsData.setVoteSettings(gallery.getVoteSettings());
            baseVotingStarsData.setWrongStartOrEndDate(false);
            if (gallery.getVoteSettings().isDurationOfVoteLimited()) {
                final Date startDate = gallery.getVoteSettings().getStartDate();
                final Date endDate = gallery.getVoteSettings().getEndDate();
                final Date currentDate = new Date();
                baseVotingStarsData.setWrongStartOrEndDate(currentDate.before(startDate) || currentDate.after(endDate));
                baseVotingStarsData.setStartDate(DateUtil.toMonthDayAndYear(startDate));
                baseVotingStarsData.setEndDate(DateUtil.toMonthDayAndYear(endDate));
            }
            baseVotingStarsData.setVotingEnded(!gallery.isIncludesVotingModule());

            List<VotingStarsData> votingStarsDatas = new ArrayList<VotingStarsData>();
            final List<VoteData> voteDatas = new VoteDataManager(gallery, new UsersManager().getLoginedUser()).createVoteDataList(new ArrayList<Integer>(Arrays.asList(filledFormIds)));
            final Persistance persistance = ServiceLocator.getPersistance();
            for (VoteData voteData : voteDatas) {
                try {
                    final VotingStarsData votingStarsData = baseVotingStarsData.clone();
                    votingStarsData.setVoteData(voteData);
                    final boolean filledFormExist = voteData.getFilledFormId() > 0 && persistance.getFilledFormById(voteData.getFilledFormId()) != null;
                    votingStarsData.setFilledFormExist(filledFormExist);
                    votingStarsData.setDisabled(baseVotingStarsData.isWrongStartOrEndDate() || !gallery.isIncludesVotingModule() || !filledFormExist);
                    votingStarsDatas.add(votingStarsData);
                } catch (Exception exception) {
                    Logger.getLogger(VoteManager.class.getName()).log(Level.SEVERE, "Can`t clone VotingStarsData object!", exception);
                }
            }
            return votingStarsDatas;
        } else {
            return Collections.emptyList();
        }
    }

    /*-------------------------------------------------private methods------------------------------------------------*/

    private static String createManageVotesUrl(final Widget widget, final int currentPageId, final SiteShowOption siteShowOption) {
        if (widget != null && siteShowOption != null) {
            if (widget.getPage().getPageId() == currentPageId) {
                return "#widget" + widget.getWidgetId();
            }
            final PageManager widgetPageManager = new PageManager(widget.getPage(), siteShowOption);
            final StringBuilder url = new StringBuilder("");
            if (siteShowOption.isWork()) {
                url.append(new SiteManager(widgetPageManager.getSite()).getPublicUrl());
            }
            url.append(widgetPageManager.getUrl());
            url.append("#widget");
            url.append(widget.getWidgetId());
            return url.toString();
        }
        return "#";
    }
    /*-------------------------------------------------private methods------------------------------------------------*/

    private static final int MIN_VOTE_VALUE = 1;
    private static final int MAX_VOTE_VALUE = 5;
}
