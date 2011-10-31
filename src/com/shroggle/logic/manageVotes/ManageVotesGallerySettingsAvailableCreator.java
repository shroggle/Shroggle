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

import com.shroggle.logic.gallery.AvailableGalleriesWithWidgetsCreator;
import com.shroggle.presentation.manageVotes.GalleryWithWidgets;
import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.List;
import java.util.ArrayList;

/**
 * @author dmitry.solomadin
 */
public class ManageVotesGallerySettingsAvailableCreator {

    public List<GalleryWithWidgets> getAvailable(final List<? extends ManageVotesSettings> manageVotesGallerySettingsList,
                                                 boolean byCurrentSite, Integer loginedUserId, Integer siteId, final SiteShowOption siteShowOption) {
        final List<GalleryWithWidgets> galleriesWithWidgets;
        if (byCurrentSite) {
            if (siteId == null) {
                throw new IllegalArgumentException("Cannot get gallery list by null siteId");
            }

            galleriesWithWidgets = new AvailableGalleriesWithWidgetsCreator(true).getAvailableBySite(persistance.getSite(siteId), siteShowOption);
        } else {
            if (loginedUserId == null) {
                throw new IllegalArgumentException("Cannot get gallery list by null logined userId");
            }

            galleriesWithWidgets = new AvailableGalleriesWithWidgetsCreator(true).getAvailableByUser(loginedUserId, siteShowOption);
        }

        return createByGalleries(manageVotesGallerySettingsList, galleriesWithWidgets, siteShowOption);
    }

    private List<GalleryWithWidgets> createByGalleries(final List<? extends ManageVotesSettings> manageVotesGallerySettingsList,
                                                       final List<GalleryWithWidgets> galleriesWithWidgets,
                                                       final SiteShowOption siteShowOption) {
        final List<GalleryWithWidgets> returnList = new ArrayList<GalleryWithWidgets>();
        for (GalleryWithWidgets galleryWithWidget : galleriesWithWidgets) {
            ManageVotesSettings existingSettings = getSettingsByGalleryId(galleryWithWidget, manageVotesGallerySettingsList, siteShowOption);

            if (existingSettings != null) {
                galleryWithWidget.setManageVotesGallerySettings(existingSettings);
                returnList.add(galleryWithWidget);
            } else {
                returnList.add(createByGallery(galleryWithWidget));
            }
        }
        return returnList;
    }

    private ManageVotesSettings getSettingsByGalleryId(GalleryWithWidgets galleryWithWidget,
                                                       List<? extends ManageVotesSettings> manageVotesGallerySettingsList,
                                                       final SiteShowOption siteShowOption) {
        if (manageVotesGallerySettingsList != null) {
            for (ManageVotesSettings manageVotesGallerySettings : manageVotesGallerySettingsList) {
                if (ManageVotesGallerySettingsManager.getGalleryId(manageVotesGallerySettings, siteShowOption) ==
                        galleryWithWidget.getGallery().getId()) {
                    return manageVotesGallerySettings;
                }
            }
        }

        return null;
    }

    private GalleryWithWidgets createByGallery(final GalleryWithWidgets galleryWithWidget) {
        final DraftManageVotesSettings manageVotesGallerySettings = new DraftManageVotesSettings();
        manageVotesGallerySettings.setCustomName(galleryWithWidget.getGallery().getName());
        manageVotesGallerySettings.setColorCode("#FFFFFF");
        manageVotesGallerySettings.setGalleryCrossWidgetId(galleryWithWidget.getWidgets().get(0).getCrossWidgetId());

        galleryWithWidget.setManageVotesGallerySettings(manageVotesGallerySettings);

        return galleryWithWidget;
    }

    private Persistance persistance = ServiceLocator.getPersistance();
    private List<Integer> alreadyExistingGalleries = new ArrayList<Integer>();
}
