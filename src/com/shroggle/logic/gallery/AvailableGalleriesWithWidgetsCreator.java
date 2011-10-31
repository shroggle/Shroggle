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

package com.shroggle.logic.gallery;

import com.shroggle.entity.*;
import com.shroggle.logic.widget.WidgetSort;
import com.shroggle.presentation.manageVotes.GalleryWithWidgets;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
public class AvailableGalleriesWithWidgetsCreator {

    public AvailableGalleriesWithWidgetsCreator(boolean selectOnlyWithVotingModules) {
        this.selectOnlyWithVotingModules = selectOnlyWithVotingModules;
    }

    private AvailableGalleriesWithWidgetsCreator() {
    }

    public List<GalleryWithWidgets> getAvailableByUser(final int userId, final SiteShowOption siteShowOption) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final List<GalleryWithWidgets> galleryWithWidgetsList = new ArrayList<GalleryWithWidgets>();
        final List<WidgetItem> widgetGalleries = new ArrayList<WidgetItem>();
        final List<Site> sites = persistance.getSites(userId, SiteAccessLevel.getUserAccessLevels(), SiteType.COMMON);
        final List<Integer> sitesId = new ArrayList<Integer>();
        for (Site site : sites) {
            sitesId.add(site.getSiteId());
        }
        for (Widget widget : persistance.getWidgetsBySitesId(sitesId, siteShowOption)) {
            if (widget.isWidgetItem()) {
                widgetGalleries.add((WidgetItem) widget);
            }
        }
        galleryWithWidgetsList.addAll(createGalleriesWithWidgets(widgetGalleries));
        return galleryWithWidgetsList;
    }

    public List<GalleryWithWidgets> getAvailableBySite(final Site site, final SiteShowOption siteShowOption) {
        List<WidgetItem> widgetGalleries = new ArrayList<WidgetItem>();
        for (Widget widget : persistance.getWidgetsBySitesId(Arrays.asList(site.getSiteId()), siteShowOption)) {
            if (widget.isWidgetItem()) {
                widgetGalleries.add((WidgetItem) widget);
            }
        }
        return createGalleriesWithWidgets(widgetGalleries);
    }

    protected List<GalleryWithWidgets> createGalleriesWithWidgets(List<WidgetItem> widgetGalleries) {
        final List<GalleryWithWidgets> galleriesWithWidgets = new ArrayList<GalleryWithWidgets>();
        final List<Integer> galleryIds = getGalleriesIds(widgetGalleries);
        for (int galleryId : galleryIds) {
            final DraftGallery gallery = ServiceLocator.getPersistance().getGalleryById(galleryId);
            if (gallery != null && ((selectOnlyWithVotingModules && gallery.isIncludesVotingModule()) || !selectOnlyWithVotingModules)) {
                final GalleryWithWidgets galleryWithWidget = new GalleryWithWidgets();
                galleryWithWidget.setGallery(gallery);
                galleryWithWidget.setWidgets(getWidgetsByGalleryId(widgetGalleries, gallery.getId()));
                galleriesWithWidgets.add(galleryWithWidget);
            }
        }
        return galleriesWithWidgets;
    }

    private List<Integer> getGalleriesIds(List<WidgetItem> widgetGalleries) {
        final List<Integer> galleryIds = new ArrayList<Integer>();
        for (WidgetItem widgetGallery : widgetGalleries) {
            if (widgetGallery != null && widgetGallery.getDraftItem() != null && !alreadyExistingGalleries.contains(widgetGallery.getDraftItem().getId())) {
                galleryIds.add(widgetGallery.getDraftItem().getId());
                alreadyExistingGalleries.add(widgetGallery.getDraftItem().getId());
            }
        }
        return galleryIds;
    }

    private List<WidgetItem> getWidgetsByGalleryId(final List<WidgetItem> widgetGalleries, final int galleryId) {
        List<WidgetItem> widgets = new ArrayList<WidgetItem>();
        for (WidgetItem widgetGallery : widgetGalleries) {
            if (widgetGallery != null && widgetGallery.getDraftItem() != null && widgetGallery.getDraftItem().getId() == galleryId) {
                widgets.add(widgetGallery);
            }
        }
        Collections.sort(widgets, WidgetSort.widgetPositionComparator);
        return widgets;
    }

    private boolean selectOnlyWithVotingModules = false;
    private Persistance persistance = ServiceLocator.getPersistance();
    private List<Integer> alreadyExistingGalleries = new ArrayList<Integer>();

}
