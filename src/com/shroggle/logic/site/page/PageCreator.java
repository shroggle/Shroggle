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
import com.shroggle.exception.*;
import com.shroggle.logic.menu.MenuItemsManager;
import com.shroggle.logic.site.template.TemplateCreatePageResponse;
import com.shroggle.logic.site.template.TemplateManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.user.dashboard.keywordManager.KeywordManager;
import com.shroggle.presentation.site.page.SavePageRequest;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.html.HtmlUtil;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Stasuk Artem, dmitry.solomadin
 */
public class PageCreator {

    // This method also used for default page creation.

    public SavePageResponse savePageNameTab(final SavePageRequest request, final boolean createNewPage) {
        new UsersManager().getLogined();

        final Site site;
        if (createNewPage) {
            site = persistance.getSite(request.getSiteId());

            if (site == null) {
                throw new SiteNotFoundException(international.get("SITE_NOT_FOUND_EXCEPTION"));
            }

            pageManager = null;
        } else {
            if (request.getPageToEditId() == null) {
                throw new IllegalArgumentException("PageIdToEdit cannot be null.");
            }

            pageManager = getPageManager(request.getPageToEditId());
            site = pageManager.getSite();
        }

        checkGeneralSettings(request, site, createNewPage);

        /* Getting site keywords */
        final Map<Integer, KeywordsGroup> siteKeywordsGroups = getSiteKeywords(request, site);

        /* Execute pageManager version save into DB. Returns pageId */
        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                if (request.getPageToEditId() != null) {
                    pageManager = new PageManager(persistance.getPage(request.getPageToEditId()));
                }

                if (request.getPageType() != null) {
                    if (createNewPage) {
                        final TemplateManager templateManager = new TemplateManager(site.getThemeId().getTemplateDirectory());
                        final TemplateCreatePageResponse createPageResponse =
                                templateManager.createPage(request.getPageType(), site, request.getName());

                        pageManager = createPageResponse.getPageManager();
                        createdWidget = createPageResponse.getCreatedWidget();
                    } else {
                        final TemplateManager templateManager =
                                new TemplateManager(site.getThemeId().getTemplateDirectory());
                        final TemplateCreatePageResponse templateResponse =
                                templateManager.configurePage(request.getPageType(), pageManager);

                        createdWidget = templateResponse.getCreatedWidget();
                    }
                }

                final Page duplicatePage = persistance.getPageByUrlAndAndSiteIgnoreUrlCase(request.getUrl(), site.getSiteId());
                if (duplicatePage != null && duplicatePage != pageManager.getPage()) {
                    throw new PageVersionUrlNotUniqueException(international.get("NOT_UNIQUE_URL_EXCEPTION"));
                }

                if (createNewPage) {
                    pageManager.setSaved(false);
                } else {
                    pageManager.setSaved(true);
                }

                pageManager.setTitle(request.getTitle());
                pageManager.setUrl(StringUtil.toLowerCase(request.getUrl()));
                pageManager.setOwnDomainName(StringUtil.toLowerCase(request.getAliaseUrl()));
                pageManager.setKeywords(KeywordManager.normalizeKeywords(request.getKeywords()));
                pageManager.setName(request.getName());
                pageManager.setChanged(true);
                
                //Accord. to spec we need to set title to title meta tag by default.
                pageManager.getSeoSettings().setTitleMetaTag(request.getTitle());

                if (site.getType() == SiteType.BLUEPRINT) {
                    pageManager.setBlueprintRequired(true);
                }

                if (!createNewPage) {
                    final Iterator<KeywordsGroup> keywordsGroupsIterator = pageManager.getKeywordsGroups().iterator();
                    while (keywordsGroupsIterator.hasNext()) {
                        final KeywordsGroup keywordsGroup = keywordsGroupsIterator.next();
                        if (keywordsGroup.getSite() != null) {
                            // if in request from pageManager not find keywords group with id, delete them
                            if (!request.getKeywordsGroupIds().contains(keywordsGroup.getKeywordsGroupId())) {
                                persistance.removeKeywordsGroup(keywordsGroup);
                                pageManager.removeKeywordsGroup(keywordsGroup);
                                keywordsGroupsIterator.remove();
                            } else {
                                // if keywords group always exist in pageManager delete them from list
                                request.getKeywordsGroupIds().remove(keywordsGroup.getKeywordsGroupId());
                            }
                        }
                    }
                }

                // find meta tag from form in databases
                for (final Integer keywordsGroupId : request.getKeywordsGroupIds()) {
                    pageManager.addKeywordsGroup(siteKeywordsGroups.get(keywordsGroupId));
                }

                if (createNewPage) {
                    final boolean includeInMenu = (request.isIncludeInMenu() != null ? request.isIncludeInMenu() : false);
                    MenuItemsManager.addMenuItemsToAllMenusBySiteId(site.getSiteId(), pageManager.getPageId(), includeInMenu);
                } else {// In editing mode we are checking "Include in menu" checkbox and include/exclude this page from menuItems.
                    if (request.isIncludeInMenu() != null) {// This is true only when we are saving menu first time and false when we are editing it in all other times.
                        MenuItemsManager.includeMenuItemsInMenu(request.isIncludeInMenu(), pageManager.getPageId());
                    }
                }
            }
        });

        final SavePageResponse response = new SavePageResponse();
        response.setPageId(pageManager.getPageId());
        response.setCreateWidgetId(createdWidget != null ? createdWidget.getWidgetId() : null);

        return response;
    }

    private PageManager pageManager = null;

    public void saveSeoMetaTagsTab(final SavePageRequest request) {
        new UsersManager().getLogined();

        pageManager = getPageManager(request.getPageToEditId());

        /* Checking SEO settings. */
        if (request.getPageSEOSettings() == null) {
            throw new IllegalArgumentException("SEO settings must be always initialized at least with empty object.");
        }

        persistanceTransaction.execute(new Runnable() {

            public void run() {
                pageManager.setSaved(true);
                pageManager.setChanged(true);
                pageManager.getSeoSettings().setAuthorMetaTag(request.getPageSEOSettings().getAuthorMetaTag());
                pageManager.getSeoSettings().setCopyrightMetaTag(request.getPageSEOSettings().getCopyrightMetaTag());
                pageManager.getSeoSettings().setTitleMetaTag(request.getPageSEOSettings().getTitleMetaTag());
                pageManager.getSeoSettings().setPageDescription(request.getPageSEOSettings().getPageDescription());
                pageManager.getSeoSettings().setRobotsMetaTag(request.getPageSEOSettings().getRobotsMetaTag());
                pageManager.getSeoSettings().setCustomMetaTagList(request.getPageSEOSettings().getCustomMetaTagList());
                pageManager.getSeoSettings().setHtmlCodeList(request.getPageSEOSettings().getHtmlCodeList());
            }
        });
    }

    public void saveSeoHtmlTab(final SavePageRequest request) {
        new UsersManager().getLogined();

        pageManager = getPageManager(request.getPageToEditId());

        /* Checking SEO settings. */
        if (request.getPageSEOSettings() == null) {
            throw new IllegalArgumentException("SEO settings must be always initialized at least with empty object.");
        }

        persistanceTransaction.execute(new Runnable() {

            public void run() {
                pageManager.setSaved(true);
                pageManager.setChanged(true);
                pageManager.getSeoSettings().setHtmlCodeList(request.getPageSEOSettings().getHtmlCodeList());
            }
        });
    }

    private PageManager getPageManager(int pageToEditId) {
        final Page page = persistance.getPage(pageToEditId);

        if (page == null) {
            throw new PageNotFoundException(international.get("PAGE_NOT_FOUND_EXCEPTION"));
        }

        return new PageManager(page);
    }

    public SavePageResponse createDefault(final Integer siteId) {
        if (siteId == null) {
            throw new IllegalArgumentException("siteId cannot be null.");
        }

        final SavePageRequest request = new SavePageRequest();
        request.setSiteId(siteId);
        request.setName(new PageDefaultNameManager().getNextName(siteId, "Page"));
        request.setUrl(new PageDefaultNameManager().getNextUrl(siteId));
        request.setKeywordsGroupIds(new HashSet<Integer>());
        request.setPageType(PageType.BLANK);
        request.setIncludeInMenu(true);

        return savePageNameTab(request, true);
    }

    private void checkGeneralSettings(final SavePageRequest request, final Site site, final boolean createNewPage) {
        checkPageName(request, site, createNewPage);
        checkPageURL(request);
        checkPageAliaseURL(request, createNewPage);
    }

    private void checkPageName(final SavePageRequest request, final Site site, final boolean createNewPage) {
        if (StringUtil.isNullOrEmpty(request.getName())) {
            throw new PageVersionNameIncorrectException(international.get("EMPTY_PAGE_NAME_EXCEPTION"));
        }

        if (StringUtil.isNullOrEmpty(request.getTitle())) {
            request.setTitle(request.getName());
        }
        // http://jira.web-deva.com/browse/SW-3606
        request.setTitle(StringUtil.cutIfNeed(request.getTitle(), 110));

        if (pageNameDeprecatedSymbols.matcher(request.getName()).find()) {
            throw new PageVersionNameIncorrectException(international.get("PAGE_NAME_WITH_SPECIAL_CHARACHTERS"));
        }
        if (createNewPage || !pageManager.getName().equals(request.getName())) {
            final Page tempPage = persistance.getPageByNameAndSite(request.getName(), site.getSiteId());
            if (tempPage != null && !Integer.valueOf(tempPage.getPageId()).equals(request.getPageToEditId())) {
                throw new PageVersionNameNotUniqueException(international.get("NOT_UNIQUE_PAGE_EXCEPTION"));
            }
        }
    }

    private Map<Integer, KeywordsGroup> getSiteKeywords(final SavePageRequest request, final Site site) {
        final Map<Integer, KeywordsGroup> siteKeywordsGroups = new HashMap<Integer, KeywordsGroup>();
        for (final KeywordsGroup keywordsGroup : site.getKeywordsGroups()) {
            siteKeywordsGroups.put(keywordsGroup.getKeywordsGroupId(), keywordsGroup);
        }
        for (final Integer pageKeywordsGroupId : request.getKeywordsGroupIds()) {
            if (!siteKeywordsGroups.containsKey(pageKeywordsGroupId)) {
                throw new KeywordsGroupNotFoundException(international.get("KEYWORD_GROUP_NOT_FOUND_EXCEPTION"));
            }
        }

        return siteKeywordsGroups;
    }

    private void checkPageURL(final SavePageRequest request) {
        request.setUrl(StringUtil.trimCutIfNeed(request.getUrl(), 250));
        if (StringUtil.isNullOrEmpty(request.getUrl())) {
            throw new PageVersionUrlIncorrectException(international.get("EMPTY_URL_EXCEPTION"));
        }

        if (request.getUrl() != null && !pageUrlDepreatedSymbols.matcher(request.getUrl()).matches()) {
            throw new PageVersionUrlIncorrectException(international.get("URL_INCORRECT_EXCEPTION"));
        }
    }

    private void checkPageAliaseURL(final SavePageRequest request, final boolean createNewPage) {
        request.setAliaseUrl(StringUtil.trimCutIfNeedAndLower(request.getAliaseUrl(), 250));
        request.setAliaseUrl(HtmlUtil.removeEndingSlash(request.getAliaseUrl()));
        if (request.getAliaseUrl() != null) {
            final Page aliseUrlPage = persistance.getPageByOwnDomainName(request.getAliaseUrl());
            if ((aliseUrlPage != null && !createNewPage && aliseUrlPage != pageManager.getPage())
                    || (aliseUrlPage != null && createNewPage)) {
                throw new PageVersionAliaseUrlNotUniqueException(
                        international.get("NOT_UNIQUE_ALIASE_URL_EXCEPTION"));
            }
        }
    }

    private Widget createdWidget = null;
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final International international = ServiceLocator.getInternationStorage().get("configurePageName", Locale.US);
    private final static Pattern pageNameDeprecatedSymbols = Pattern.compile("[<>]");
    private final static Pattern pageUrlDepreatedSymbols = Pattern.compile("[-+\\w]+");

}
