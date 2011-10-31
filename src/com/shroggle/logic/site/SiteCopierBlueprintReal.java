package com.shroggle.logic.site;

import com.shroggle.entity.*;
import com.shroggle.logic.site.item.CopierStackExecutorItemFull;
import com.shroggle.logic.site.item.ItemCopierUtil;
import com.shroggle.logic.site.item.ItemNamingRealWithAddingCopyWord;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.PageSettingsManager;
import com.shroggle.logic.site.page.PagesWithoutSystem;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.copier.stack.CopierStack;
import com.shroggle.util.copier.stack.CopierStackSimple;
import com.shroggle.util.persistance.Persistance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Artem Stasuk
 */
public class SiteCopierBlueprintReal implements SiteCopierBlueprint {

    @Override
    public Site execute(final Site blueprint) {
        final Persistance persistance = ServiceLocator.getPersistance();

        final Site copiedBlueprint = new Site();
        copiedBlueprint.setThemeId(blueprint.getThemeId());
        copiedBlueprint.setTitle(blueprint.getTitle() + "_copy");
        copiedBlueprint.setType(SiteType.BLUEPRINT);
        persistance.putSite(copiedBlueprint);

        final List<Page> pages = PagesWithoutSystem.get(blueprint.getPages());
        final Map<Integer, Integer> copiedPageIds = new HashMap<Integer, Integer>();
        final List<Page> copiedPages = copyPages(pages, copiedBlueprint, copiedPageIds);

        final DraftMenu copiedDraftMenu = ItemCopierUtil.copyMenu(blueprint.getMenu());
        copiedDraftMenu.setSiteId(copiedBlueprint.getSiteId());
        copiedBlueprint.setMenu(copiedDraftMenu);
        persistance.putMenu(copiedDraftMenu);
        ItemCopierUtil.copyItemsWithoutDraftAndSetCorrectPageIds(
                copiedPageIds, blueprint.getMenu().getMenuItems(), copiedBlueprint.getMenu(), null);

        final CopierStack copierStack = new CopierStackSimple(new CopierStackExecutorItemFull(
                new ItemNamingRealWithAddingCopyWord(), copiedBlueprint.getId(), copiedPageIds));

        final DraftForm defaultForm = persistance.getDraftItem(blueprint.getDefaultFormId());
        copiedBlueprint.setDefaultFormId(copierStack.copy(defaultForm).getId());

        for (final Page page : copiedPages) {
            for (final Widget widget : new PageManager(page).getWidgets()) {
                if (!widget.isWidgetItem()) {
                    continue;
                }

                final WidgetItem widgetItem = (WidgetItem) widget;
                if (widgetItem.getDraftItem() == null) {
                    continue;
                }

                final DraftItem draftItem = widgetItem.getDraftItem();
                widgetItem.setDraftItem(copierStack.copy(draftItem));
            }
        }

        for (final UserOnSiteRight userOnSiteRight : blueprint.getUserOnSiteRights()) {
            if (userOnSiteRight.getSiteAccessType().isUserAccessLevel() && userOnSiteRight.isActive()) {
                final UserOnSiteRight copiedUserOnSiteRight = new UserOnSiteRight();
                copiedUserOnSiteRight.setActive(true);
                copiedUserOnSiteRight.setSiteAccessType(userOnSiteRight.getSiteAccessType());
                userOnSiteRight.getId().getUser().addUserOnSiteRight(copiedUserOnSiteRight);
                copiedBlueprint.addUserOnSiteRight(copiedUserOnSiteRight);
                persistance.putUserOnSiteRight(copiedUserOnSiteRight);
            }
        }

        return copiedBlueprint;
    }

    public static List<Page> copyPages(
            final List<Page> pages, final Site copiedSite, final Map<Integer, Integer> pageToCopiedPageIds) {
        final List<Page> copiedPages = new ArrayList<Page>();
        for (final Page page : pages) {
            final Page copiedPage = copyPage(page);
            if (copiedPage != null) {
                copiedSite.addPage(copiedPage);
                copiedPages.add(copiedPage);
                pageToCopiedPageIds.put(page.getPageId(), copiedPage.getPageId());
            }
        }
        return copiedPages;
    }

    public static Page copyPage(final Page page) {
        final Page copiedPage = SiteCopierUtil.copyPage(page);
        copiedPage.setSite(page.getSite());

        final DraftPageSettings copiedDraftPageSettings = new DraftPageSettings();
        copiedDraftPageSettings.setPage(copiedPage);
        new PageSettingsManager(page.getPageSettings()).copyPageSettingsTo(copiedDraftPageSettings);
        copiedPage.setPageSettings(copiedDraftPageSettings);
        copiedDraftPageSettings.setChanged(true);
        ServiceLocator.getPersistance().putPage(copiedPage);

        return copiedPage;
    }

}
