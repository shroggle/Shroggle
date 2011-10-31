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
package com.shroggle.logic.manageVotes;

import com.shroggle.entity.*;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.gallery.GalleryManager;
import com.shroggle.logic.gallery.voting.VoteDataManager;
import com.shroggle.logic.gallery.voting.VoteData;

import java.util.*;

/**
 * @author dmitry.solomadin
 */
public class ManageVotesManager {

    public ManageVotesManager(ManageVotes manageVotes, final SiteShowOption siteShowOption) {
        this.manageVotes = manageVotes;
        this.siteShowOption = siteShowOption;
    }

    public List<WinnerInfo> constructWinnerList() {
        final List<WinnerInfo> winnerList = new ArrayList<WinnerInfo>();
        final UserManager userManager = new UsersManager().getLogined();
        if (userManager.getUser() != null) {
            for (ManageVotesSettings settings : manageVotes.getManageVotesGallerySettingsList()) {
                final DraftGallery gallery = persistance.getGalleryById(ManageVotesGallerySettingsManager.getGalleryId(settings, siteShowOption));
                if (gallery == null) {
                    continue;
                }
                final List<Integer> filledFormsIds = new GalleryManager(gallery).getFilledFormsIds();
                final VoteDataManager voteDataManager = new VoteDataManager(gallery, userManager.getUser());
                for (VoteData vote : voteDataManager.createVoteDataList(filledFormsIds)) {
                    if (vote.isWinner()) {
                        final WinnerInfo winnerInfo = new WinnerInfo();
                        winnerInfo.setManageVotesGallerySettings(settings);
                        winnerInfo.setVoteId(vote.getVoteId());
                        winnerList.add(winnerInfo);
                    }
                }
            }
        }
        return winnerList;
    }

    public List<? extends ManageVotesSettings> getManageVotesGallerySettingsList() {
        //Remove's manage votes gallery settings that have crossWidgetId and this widget is no longer exists (was deleted).
        //Or gallery was deleted from manage galleries page.
        List<ManageVotesSettings> manageVotesGallerySettingsToRemoveList = new ArrayList<ManageVotesSettings>();
        for (ManageVotesSettings manageVotesGallerySettings : manageVotes.getManageVotesGallerySettingsList()) {
            if (ManageVotesGallerySettingsManager.getGalleryId(manageVotesGallerySettings, siteShowOption) <= 0) {
                manageVotesGallerySettingsToRemoveList.add(manageVotesGallerySettings);
            }
        }

        for (final ManageVotesSettings manageVotesGallerySettingsToRemove : manageVotesGallerySettingsToRemoveList) {
            persistanceTransaction.execute(new Runnable() {
                @Override
                public void run() {
                    persistance.removeManageVotesGallerySettings(manageVotesGallerySettingsToRemove);
                }
            });
        }

        return manageVotes.getManageVotesGallerySettingsList();
    }

    private ManageVotes manageVotes;
    private final SiteShowOption siteShowOption;
    private Persistance persistance = ServiceLocator.getPersistance();
    private PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}
