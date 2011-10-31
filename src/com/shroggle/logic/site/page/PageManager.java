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
package com.shroggle.logic.site.page;

import com.shroggle.entity.*;
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.logic.accessibility.AccessibleForRenderManager;
import com.shroggle.logic.menu.MenuItemsManager;
import com.shroggle.logic.site.SiteCopierUtil;
import com.shroggle.logic.site.WidgetPoster;
import com.shroggle.logic.site.item.ItemCopierUtil;
import com.shroggle.logic.site.item.ItemPoster;
import com.shroggle.logic.site.item.ItemPosterCache;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.copier.CopierUtil;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.resource.provider.ResourceGetterType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
// todo. add tests.
public class PageManager implements AccessibleForRender {

    public PageManager(PageSettingsManager pageSettingsManager) {
        this(pageSettingsManager.getPage(), (pageSettingsManager.isWork() ? SiteShowOption.getWorkOption() : SiteShowOption.getDraftOption()));
    }

    public PageManager(final Page page, SiteShowOption siteShowOption) {
        if (page == null) {
            throw new PageNotFoundException("Can`t create PageManager without page!");
        }
        siteShowOption = siteShowOption != null ? siteShowOption : SiteShowOption.getDraftOption();
        final DraftPageSettings draftPageSettings = page.getPageSettings();
        if (draftPageSettings == null) {
            throw new NullPointerException("Page without pageSettings");
        }
        this.page = page;
        this.site = draftPageSettings.getPage().getSite();
        if (siteShowOption.isWork()) {
            final WorkPageSettings workPageSettings = ServiceLocator.getPersistance().getWorkPageSettings(draftPageSettings.getPageSettingsId());
            this.pageSettingsManager = new PageSettingsManager(workPageSettings);
        } else {
            this.pageSettingsManager = new PageSettingsManager(draftPageSettings);
        }
    }

    public PageManager(final Page page) {
        this(page, SiteShowOption.ON_USER_PAGES);
    }

    public static List<PageManager> convertToPageManagers(final List<Page> pages) {
        final List<PageManager> pageManagers = new ArrayList<PageManager>();
        for (Page page : pages) {
            pageManagers.add(new PageManager(page));
        }
        return pageManagers;
    }

    public static PageManager getPageForRenderOrLoginPage(final Integer pageId, final SiteShowOption siteShowOption) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final Page page = persistance.getPage(pageId);
        if (page == null) {
            logger.severe("Page not found. pageId = " + pageId);
            return null;
        }
        final PageManager pageManager = new PageManager(page, siteShowOption);
        final AccessibleForRenderManager accessManager = new AccessibleForRenderManager(pageManager);
        final boolean elementAccessible = accessManager.isAccessibleForRender();

        if (!elementAccessible) {
            logger.info("You have no access to content of this page/site. Getting login page...");
            final Site site = page.getSite();
            if (accessManager.isOnlyAdminsHasAccess()) {
                if (site.getLoginAdminPage() == null) {
                    logger.info("We have to show login admin page, but it`s not found!");
                    return null;
                }
                return new PageManager(site.getLoginAdminPage(), siteShowOption);
            } else {
                if (site.getLoginPage() == null) {
                    logger.info("We have to show login page, but it`s not found!");
                    return null;
                }
                return new PageManager(site.getLoginPage(), siteShowOption);
            }
        } else {// Actually we don`t need this "else" here, but it`s more readable as for me. Tolik.
            return pageManager;
        }
    }

    public String getScreenShotUrl() {
        if (isScreenShotExists()) {
            return ServiceLocator.getResourceGetter().get(ResourceGetterType.BLUEPRINT_SCREEN_SHOT, getScreenShotId(), 0, 0, 0, false);
        } else {
            return "#";
        }
    }

    public boolean isScreenShotExists() {
        return getScreenShotId() != null;
    }

    public void setScreenShotId(final Integer screenShotId) {
        page.setScreenShotId(screenShotId);
    }

    public Integer getScreenShotId() {
        return page.getScreenShotId();
    }

    public void updateBackground(final Background tempBackground, final boolean applyToAllPages) {
        if (applyToAllPages) {
            for (final Page tempPage : getSite().getPages()) {
                new PageManager(tempPage).updateBackground(tempBackground, false);
            }
        } else {
            final Background background = getBackground() != null ? getBackground() : new Background();
            CopierUtil.copyProperties(tempBackground, background, "Id");

            if (background.getId() <= 0) {
                persistance.putBackground(background);
                setBackground(background);
            }
            setChanged(true);
        }
    }

    public void updateBorder(final Border tempBorder, final boolean applyToAllPages) {
        if (applyToAllPages) {
            for (final Page tempPage : getSite().getPages()) {
                new PageManager(tempPage).updateBorder(tempBorder, false);
            }
        } else {
            final Border border = getBorder() != null ? getBorder() : new Border();
            ItemCopierUtil.copyBorderProperties(tempBorder, border);
            if (border.getId() <= 0) {
                persistance.putBorder(border);
                setBorder(border);
            }
            setChanged(true);
        }
    }

    public WorkPageSettings getWorkPageSettings() {
        return pageSettingsManager.getWorkPageSettings();
    }

    public DraftPageSettings getDraftPageSettings() {
        return pageSettingsManager.getDraftPageSettings();
    }

    public PageSettingsManager getPageSettingsManager() {
        return pageSettingsManager;
    }

    public int getPageId() {
        return page.getPageId();
    }

    public Site getSite() {
        return page.getSite();
    }

    public List<Widget> getWidgets() {
        return pageSettingsManager.getWidgets();
    }

    public List<KeywordsGroup> getKeywordsGroups() {
        return pageSettingsManager.getKeywordsGroups();
    }

    /**
     * @return as example http://artem.shroggle.com/thisPage, only for work page,
     *         if draft changed or not exist return empty.
     */
    public String getWorkPublicUrl() {
        return new PageSettingsManager(getWorkPageSettings()).getPublicUrl();
    }

    public String getPublicUrl() {
        return pageSettingsManager.getPublicUrl();
    }

    public String getSavedHtmlOrDefault() {
        if (pageSettingsManager.getHtml() != null) {
            return pageSettingsManager.getHtml();
        } else {
            return Doctype.addDoctypeIfNeeded(getTemplateHtml());
        }
    }

    public String getSavedCssOrDefault() {
        return pageSettingsManager.getCss() != null ? pageSettingsManager.getCss() : getTemplateCss();
    }

    public String getTemplateHtml() {
        return fileSystem.getTemplateResource(getRealThemeId().getTemplateDirectory(), pageSettingsManager.getLayoutFile());
    }

    public String getTemplateCss() {
        return fileSystem.getTemplateResource(getRealThemeId().getTemplateDirectory(), getRealThemeId().getThemeCss());
    }

    public ThemeId getRealThemeId() {
        return pageSettingsManager.getThemeId() != null ? pageSettingsManager.getThemeId() : site.getThemeId();
    }

    public void remove() {
        // Remove all menuItem`s with current page id from all menus and move all its children on its place.
        MenuItemsManager.removeItemsAndMoveChildren(page.getPageId());// Execution time of this method ~ 234ms. Tolik (for indie roar site, on my pc)
        persistance.removePage(page);// Execution time of this method ~ 1647ms. Tolik (for indie roar site, on my pc)
    }

    public PageManager copy(final Set<Integer> itemIds) {
        return SiteCopierUtil.copyPageDraft(this.page, itemIds);
    }

    public void publish() {
        WorkPageSettings workPageSettings = getWorkPageSettings();
        if (workPageSettings != null) {
            persistance.removePageSettings(workPageSettings);
        }
        workPageSettings = new WorkPageSettings();
        final DraftPageSettings draftPageSettings = getDraftPageSettings();
        workPageSettings.setPageSettingsId(draftPageSettings.getPageSettingsId());
        workPageSettings.setPage(draftPageSettings.getPage());
        new PageSettingsManager(draftPageSettings).copyPageSettingsTo(workPageSettings);

        final ItemPoster itemPoster = getItemPoster();
        for (Widget widget : new PageSettingsManager(workPageSettings).getWidgets()) {
            WidgetPoster.post(widget, itemPoster);
        }
        draftPageSettings.setChanged(false);
    }

    public void resetChanges() {
        final WorkPageSettings workPageSettings = getWorkPageSettings();
        if (workPageSettings == null) {
            logger.warning("Can`t reset changes because workPageSettings doesn`t exist. We should not let users press " +
                    "this button if there is no workPageSettings. Check user`s interface! Tolik");
            return;
        }
        persistance.removePageSettings(getDraftPageSettings());

        final DraftPageSettings draftPageSettings = new DraftPageSettings();
        draftPageSettings.setPageSettingsId(workPageSettings.getPageSettingsId());
        draftPageSettings.setPage(page);
        new PageSettingsManager(workPageSettings).copyPageSettingsTo(draftPageSettings);
        page.setPageSettings(draftPageSettings);
        draftPageSettings.setChanged(false);

        // Here I`m changing this pageSettingsManager, so you can still use this pageManager, its settings correct.
        if (pageSettingsManager.isWork()) {
            pageSettingsManager = new PageSettingsManager(workPageSettings);
        } else {
            pageSettingsManager = new PageSettingsManager(draftPageSettings);
        }
        //todo. Maybe we have to reset changes for items? Tolik.
    }

    private final Page page;
    private final Site site;
    private PageSettingsManager pageSettingsManager;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private static final Logger logger = Logger.getLogger(PageManager.class.getName());
    private final FileSystem fileSystem = ServiceLocator.getFileSystem();

    /*---------------------------------------------PageSettings methods-----------------------------------------------*/

    /*----------------------------------Getters (work only with pageSettingsManager)----------------------------------*/


    public Border getBorder() {
        return pageSettingsManager.getBorder();
    }


    public Background getBackground() {
        return pageSettingsManager.getBackground();
    }


    public String getLayoutFile() {
        return pageSettingsManager.getLayoutFile();
    }


    public String getName() {
        return pageSettingsManager.getName();
    }


    public String getTitle() {
        return pageSettingsManager.getTitle();
    }


    public String getRawUrl() {
        return pageSettingsManager.getRawUrl();
    }


    public String getUrl() {
        return pageSettingsManager.getUrl();
    }


    public Date getUpdated() {
        return pageSettingsManager.getUpdated();
    }


    public Page getPage() {
        return page;
    }


    public int getId() {
        return getPageId();
        /*throw new UnsupportedOperationException("It`s not clear, which id I should return here. pageId (as it`s manager for page), " +
                "or pageSettingsId because this class implements 'PageSettings' interface. ");*/
    }


    public boolean isBlueprintLocked() {
        return pageSettingsManager.isBlueprintLocked();
    }


    public boolean isBlueprintRequired() {
        return pageSettingsManager.isBlueprintRequired();
    }


    public boolean isBlueprintNotEditable() {
        return pageSettingsManager.isBlueprintNotEditable();
    }


    public int getSiteId() {
        return getSite().getSiteId();
    }

    @Override
    public AccessibleForRender getAccessibleParent() {
        if (page.isSystem()) {
            return null;
        }
        return page.getSite();
    }

    @Override
    public AccessibleElementType getAccessibleElementType() {
        return AccessibleElementType.PAGE;
    }

    @Override
    public List<Group> getAvailableGroups() {
        return page.getSite().getAvailableGroups();
    }


    public AccessibleSettings getAccessibleSettings() {
        return pageSettingsManager.getAccessibleSettings();
    }


    public String getOwnDomainName() {
        return pageSettingsManager.getOwnDomainName();
    }


    public String getHtml() {
        return pageSettingsManager.getHtml();
    }


    public String getCss() {
        return pageSettingsManager.getCss();
    }


    public String getKeywords() {
        return pageSettingsManager.getKeywords();
    }


    public List<Integer> getKeywordsGroupsId() {
        return pageSettingsManager.getKeywordsGroupsId();
    }


    public Date getCreationDate() {
        return pageSettingsManager.getCreationDate();
    }


    public int getPageSettingsId() {
        return pageSettingsManager.getPageSettingsId();
    }


    public PageSEOSettings getSeoSettings() {
        return pageSettingsManager.getSeoSettings();
    }


    public ThemeId getThemeId() {
        return pageSettingsManager.getThemeId();
    }

    public boolean isChanged() {
        return getDraftPageSettings().isChanged();
    }

    public boolean isSaved() {
        return page.isSaved();
    }

    /*----------------------------------Getters (work only with pageSettingsManager)----------------------------------*/

    /*-----------------------------------Setters (work only with DraftPageSettings)-----------------------------------*/


    public void setAccessibleSettings(AccessibleSettings accessibleSettings) {
        getDraftPageSettings().setAccessibleSettings(accessibleSettings);
    }


    public void setTitle(String title) {
        getDraftPageSettings().setTitle(title);
    }


    public void setUrl(String url) {
        getDraftPageSettings().setUrl(url);
    }


    public void setOwnDomainName(String ownDomainName) {
        getDraftPageSettings().setOwnDomainName(ownDomainName);
    }


    public void setHtml(String html) {
        getDraftPageSettings().setHtml(html);
    }


    public void setCss(String css) {
        getDraftPageSettings().setCss(css);
    }


    public void setKeywords(String keywords) {
        getDraftPageSettings().setKeywords(keywords);
    }


    public void setPage(Page page) {
        getDraftPageSettings().setPage(page);
    }


    public void setPageSettingsId(int pageSettingsId) {
        throw new UnsupportedOperationException();
    }


    public void setBlueprintLocked(boolean blueprintLocked) {
        getDraftPageSettings().setBlueprintLocked(blueprintLocked);
    }


    public void setBlueprintRequired(boolean blueprintRequired) {
        getDraftPageSettings().setBlueprintRequired(blueprintRequired);
    }


    public void setBlueprintNotEditable(boolean blueprintNotEditable) {
        getDraftPageSettings().setBlueprintNotEditable(blueprintNotEditable);
    }


    public void setName(String name) {
        getDraftPageSettings().setName(name);
    }


    public void setLayoutFile(String layoutFile) {
        getDraftPageSettings().setLayoutFile(layoutFile);
    }


    public void setSeoSettings(PageSEOSettings seoSettings) {
        getDraftPageSettings().setSeoSettings(seoSettings);
    }


    public void setCreationDate(Date creationDate) {
        getDraftPageSettings().setCreationDate(creationDate);
    }


    public void setThemeId(ThemeId themeId) {
        getDraftPageSettings().setThemeId(themeId);
    }


    public void setBorder(Border borderBackground) {
        getDraftPageSettings().setBorder(borderBackground);
    }


    public void setBackground(Background background) {
        getDraftPageSettings().setBackground(background);
    }


    public void setKeywordsGroupsId(List<Integer> keywordsGroupsId) {
        getDraftPageSettings().setKeywordsGroupsId(keywordsGroupsId);
    }


    public void addWidget(Widget widget) {
        getDraftPageSettings().addWidget(widget);
    }


    public void removeWidget(Widget widget) {
        getDraftPageSettings().removeWidget(widget);
    }


    public void removeKeywordsGroup(KeywordsGroup keywordsGroup) {
        getDraftPageSettings().removeKeywordsGroup(keywordsGroup);
    }


    public void addKeywordsGroup(KeywordsGroup keywordsGroup) {
        getDraftPageSettings().addKeywordsGroup(keywordsGroup);
    }

    public void setChanged(final boolean changed) {
        getDraftPageSettings().setChanged(changed);
    }

    public void setSaved(final boolean saved) {
        page.setSaved(saved);
    }

    public void setAdministrators(boolean administrators) {
        getDraftPageSettings().getAccessibleSettings().setAdministrators(administrators);
    }


    public void setVisitors(boolean visitors) {
        getDraftPageSettings().getAccessibleSettings().setVisitors(visitors);
    }

    public void setVisitorsGroups(List<Integer> visitorsGroups) {
        getDraftPageSettings().getAccessibleSettings().setVisitorsGroups(visitorsGroups);
    }

    public void setAccess(AccessForRender accessGroup) {
        getDraftPageSettings().getAccessibleSettings().setAccess(accessGroup);
    }

    public void setWidgets(List<Widget> widgets) {
        getDraftPageSettings().setWidgets(widgets);
    }
    /*-----------------------------------Setters (work only with DraftPageSettings)-----------------------------------*/

    public ItemPoster getItemPoster() {
        return itemPoster != null ? itemPoster : new ItemPosterCache();
    }

    public void setItemPoster(ItemPoster itemPoster) {
        this.itemPoster = itemPoster;
    }

    private ItemPoster itemPoster;
}
