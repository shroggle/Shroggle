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

import com.shroggle.presentation.manageVotes.SaveManageVotesRequest;
import com.shroggle.entity.*;
import com.shroggle.util.StringUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.logic.user.UserManager;
import com.shroggle.exception.ManageVotesNullOrEmptyNameException;
import com.shroggle.exception.ManageVotesNotFoundException;
import com.shroggle.exception.ManageVotesNotUniqueNameException;

import java.util.Locale;
import java.util.List;
import java.util.ArrayList;

/**
 * @author dmitry.solomadin
 */
public class ManageVotesCreator {

    public ManageVotesCreator(final UserManager userManager) {
        this.userManager = userManager;
    }

    public void save(final SaveManageVotesRequest request, final SiteShowOption siteShowOption) {
        final WidgetItem widget;
        if (request.getWidgetId() != null) {
            widget = (WidgetItem) userManager.getRight().getSiteRight().getWidgetForEditInPresentationalService(
                    request.getWidgetId());
        } else {
            widget = null;
        }

        if (StringUtil.isNullOrEmpty(request.getName())) {
            throw new ManageVotesNullOrEmptyNameException(international.get("ManageVotesNullOrEmptyNameException"));
        }

        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                final DraftManageVotes manageVotes = persistance.getDraftItem(request.getManageVotesId());
                if (manageVotes == null || manageVotes.getSiteId() <= 0) {
                    throw new ManageVotesNotFoundException("Cannot find manage votes by Id=" +
                            request.getManageVotesId());
                }

                final Site site;
                if (widget != null) {
                    site = widget.getSite();
                } else {
                    site = persistance.getSite(manageVotes.getSiteId());
                }

                final DraftManageVotes duplicateManageVotes = persistance.getManageVotesByNameAndSiteId(
                        request.getName(), site.getSiteId());
                if (duplicateManageVotes != null && duplicateManageVotes != manageVotes) {
                    throw new ManageVotesNotUniqueNameException(international.get("ManageVotesNotUniqueNameException"));
                }

                manageVotes.setDescription(StringUtil.getEmptyOrString(request.getDescription()));
                manageVotes.setName(request.getName());
                manageVotes.setShowDescription(request.isShowDescription());
                manageVotes.setShowVotingModulesFromCurrentSite(request.isShowVotingModulesFromCurrentSite());
                manageVotes.setPickAWinner(request.isPickAWinner());

                updateGallerySettings(manageVotes, request.getManageVotesGallerySettingsListChecked(), request.getManageVotesGallerySettingsListUnchecked(), siteShowOption);
            }

        });
    }

    public void updateGallerySettings(final DraftManageVotes manageVotes, final List<DraftManageVotesSettings> newSettings,
                                      final List<DraftManageVotesSettings> settingsToRemove, final SiteShowOption siteShowOption) {
        for (DraftManageVotesSettings newSetting : newSettings) {
            boolean hasItem = false;
            for (DraftManageVotesSettings oldItem : manageVotes.getManageVotesGallerySettingsList()) {
                final int oldItemGallerId = ManageVotesGallerySettingsManager.getGalleryId(oldItem, siteShowOption);
                final int newItemGallerId = ManageVotesGallerySettingsManager.getGalleryId(newSetting, siteShowOption);
                if (newItemGallerId > 0 && oldItemGallerId > 0 && oldItemGallerId == newItemGallerId) {
                    oldItem.setCustomName(newSetting.getCustomName());
                    oldItem.setColorCode(newSetting.getColorCode());
                    oldItem.setFormItemId(newSetting.getFormItemId());
                    oldItem.setGalleryCrossWidgetId(newSetting.getGalleryCrossWidgetId());
                    hasItem = true;
                    break;
                }
            }
            if (!hasItem) {
                newSetting.setManageVotes(manageVotes);
                persistance.putManageVotesGallerySettings(newSetting);
                manageVotes.addGallerySettings(newSetting);
            }
        }

        List<DraftManageVotesSettings> existingSettings = new ArrayList<DraftManageVotesSettings>();
        existingSettings.addAll(manageVotes.getManageVotesGallerySettingsList());
        for (DraftManageVotesSettings settingToRemove : settingsToRemove) {
            for (DraftManageVotesSettings oldItem : existingSettings) {
                if (settingToRemove.getGalleryCrossWidgetId() == oldItem.getGalleryCrossWidgetId()) {
                    persistance.removeManageVotesGallerySettings(oldItem);
                }
            }
        }
    }

    private Persistance persistance = ServiceLocator.getPersistance();
    private PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private International international = ServiceLocator.getInternationStorage().get("manageVotes", Locale.US);
    private UserManager userManager;
}
