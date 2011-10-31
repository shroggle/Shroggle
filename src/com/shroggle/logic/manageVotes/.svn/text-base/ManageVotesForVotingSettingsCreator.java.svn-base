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
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 *         Date: 25.08.2009
 */
public class ManageVotesForVotingSettingsCreator {

    public static List<ManageVotesForVotingSettings> executeForAllAvailableSites(final int userId, final SiteShowOption siteShowOption) {
        final List<ManageVotesForVotingSettings> manageVotesForVotingSettings = new ArrayList<ManageVotesForVotingSettings>();
        for (Site site : ServiceLocator.getPersistance().getSites(userId,
                SiteAccessLevel.getUserAccessLevels(), SiteType.COMMON)) {
            manageVotesForVotingSettings.addAll(executeForCurrentSite(site.getSiteId(), siteShowOption));
        }
        return manageVotesForVotingSettings;
    }

    public static List<ManageVotesForVotingSettings> executeForCurrentSite(final int siteId, final SiteShowOption siteShowOption) {
        final List<ManageVotesForVotingSettings> manageVotesForVotingSettingsList = new ArrayList<ManageVotesForVotingSettings>();
        final Persistance persistance = ServiceLocator.getPersistance();
        final Site site = persistance.getSite(siteId);
        if (site != null) {
            final List<Widget> widgetItems = persistance.getWidgetsBySitesId(Arrays.asList(site.getSiteId()), siteShowOption);
            for (final Widget widget : widgetItems) {
                if (widget.isWidgetItem()) {
                    WidgetItem widgetItem = (WidgetItem) widget;
                    final DraftItem draftItem = widgetItem.getDraftItem();
                    if (draftItem instanceof DraftManageVotes) {
                        final DraftManageVotes draftManageVotes = (DraftManageVotes) draftItem;
                        ManageVotesForVotingSettings manageVotesForVotingSettings =
                                createManageVotesForVotingSettings(site.getTitle(), widgetItem, draftManageVotes, siteShowOption);
                        manageVotesForVotingSettingsList.add(manageVotesForVotingSettings);
                    }
                }
            }
        }
        return manageVotesForVotingSettingsList;
    }

    private static ManageVotesForVotingSettings createManageVotesForVotingSettings(
            final String siteName, final Widget widget, final DraftManageVotes manageVotes, final SiteShowOption siteShowOption) {

        ManageVotesForVotingSettings manageVotesForVotingSettings = new ManageVotesForVotingSettings();
        manageVotesForVotingSettings.setSiteName(siteName);
        manageVotesForVotingSettings.setPageName(new PageManager(widget.getPage(), siteShowOption).getName());
        manageVotesForVotingSettings.setCrossWidgetId(widget.getCrossWidgetId());
        manageVotesForVotingSettings.setManageVotesName(manageVotes != null ? manageVotes.getName() : "not specified");
        return manageVotesForVotingSettings;
    }
}
