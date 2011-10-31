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
package com.shroggle.logic.site;

import com.shroggle.entity.*;
import com.shroggle.logic.menu.MenuItemsManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.PageSettingsManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.copier.*;
import com.shroggle.util.persistance.Persistance;

import java.util.*;

/**
 * @author Artem Stasuk
 */
public final class SiteCopierUtil {

    private SiteCopierUtil() {

    }

    public static void shareSiteItem(
            final Site site, final DraftItem siteItem, final SiteOnItemRightType siteOnItemRightType) {
        final Persistance persistance = ServiceLocator.getPersistance();

        if (siteItem != null) {
            final SiteOnItem siteOnItemRight = persistance.getSiteOnItemRightBySiteIdItemIdAndType(
                    site.getSiteId(), siteItem.getId(), siteItem.getItemType());
            if (siteOnItemRight == null) {
                final SiteOnItem siteOnItem = siteItem.createSiteOnItemRight(site);
                siteOnItem.setAcceptDate(new Date());
                siteOnItem.setFromBlueprint(true);
                siteOnItem.setType(siteOnItemRightType);
                persistance.putSiteOnItem(siteOnItem);
            } else if (siteOnItemRight.getType() == SiteOnItemRightType.READ) {
                siteOnItemRight.setType(siteOnItemRightType);
            }
        }
    }

    public static Page copyPage(final Page page) {
        final CopierExplorer explorer = new CopierExplorerExclude(
                new CopierExplorerStack(
                        new CopierExplorerList(),
                        new CopierExplorerMethod()
                ),
                "PageVisits", "PageId", "WidgetId", "PageSettings", "Site", "Parent"
        );

        final CopierWorkerWrapper worker = new CopierWorkerWrapper();
        worker.setWorker(
                new CopierWorkerStack(
                        new CopierWorkerUnmodificable(),
                        new CopierWorkerList(explorer, worker),
                        new CopierWorkerHistory(
                                new CopierWorkerObject(worker, explorer)
                        )
                ));

        final CopierWraper<Page> wraper = new CopierWraper<Page>(page);
        worker.copy(explorer.find(wraper, wraper).get(0));
        return wraper.getObject();
    }

    public static List<Page> copyPages(
            final List<Page> pages, final boolean publish,
            final Site copiedSite, final Map<Integer, Integer> pageToCopiedPageIds,
            final WidgetCrossIdSetter widgetCrossIdSetter) {
        final List<Page> copiedPages = new ArrayList<Page>();
        for (final Page page : pages) {
            final Page copiedPage = copyPagePublished(page, publish, widgetCrossIdSetter);
            if (copiedPage != null) {
                copiedSite.addPage(copiedPage);
                copiedPages.add(copiedPage);
                pageToCopiedPageIds.put(page.getPageId(), copiedPage.getPageId());
            }
        }
        return copiedPages;
    }

    public static Page copyPagePublished(final Page page, final boolean publish, final WidgetCrossIdSetter widgetCrossIdSetter) {
        final PageSettings workPageSettings = new PageManager(page).getWorkPageSettings();
        if (workPageSettings == null) {
            return null;
        }

        final Page copiedPage = copyPage(page);
        copiedPage.setSite(page.getSite());

        final DraftPageSettings copiedDraftPageSettings = new DraftPageSettings();
        copiedDraftPageSettings.setPage(copiedPage);
        new PageSettingsManager(workPageSettings).copyPageSettingsTo(copiedDraftPageSettings);
        copiedPage.setPageSettings(copiedDraftPageSettings);
        copiedDraftPageSettings.setChanged(true);
        ServiceLocator.getPersistance().putPage(copiedPage);

        widgetCrossIdSetter.execute(copiedDraftPageSettings);

        if (publish) {
            new PageManager(copiedPage).publish();
        }

        return copiedPage;
    }

    public static PageManager copyPageDraft(final Page page, final Set<Integer> itemIds) {
        final Persistance persistance = ServiceLocator.getPersistance();

        final Page copy = new Page();
        copy.setSite(page.getSite());
        copy.setSystem(page.isSystem());
        copy.setType(page.getType());
        copy.setScreenShotId(page.getScreenShotId());

        final DraftPageSettings draftPageSettings = new DraftPageSettings();
        draftPageSettings.setPage(copy);
        copy.setPageSettings(draftPageSettings);
        new PageSettingsManager(page.getPageSettings()).copyPageSettingsTo(draftPageSettings, itemIds);
        draftPageSettings.setOwnDomainName(null);

        copy.setPageVisits(new ArrayList<Visit>());
        persistance.putPage(copy);
        page.getSite().addPage(copy);

        final PageManager copyPageManager = new PageManager(copy);

        int freeNumber = 1;
        while (persistance.getPageByNameAndSite(
                copyPageManager.getName() + freeNumber, copyPageManager.getSiteId()) != null) {
            freeNumber++;
        }
        copyPageManager.setName(copyPageManager.getName() + freeNumber);

        freeNumber = 1;
        while (persistance.getPageByUrlAndAndSiteIgnoreUrlCase(
                copyPageManager.getDraftPageSettings().getUrl() + freeNumber, copyPageManager.getSiteId()) != null) {
            freeNumber++;
        }
        copyPageManager.setUrl(copyPageManager.getDraftPageSettings().getUrl() + freeNumber);

        copyPageManager.setChanged(true);

        // Adding newly created page to menus.
        MenuItemsManager.addMenuItemsToAllMenusBySiteId(page.getSite().getSiteId(), copyPageManager.getPageId(), true);

        return copyPageManager;
    }
}
