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
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.site.page.SystemPagesCreator;
import com.shroggle.logic.user.dashboard.keywordManager.KeywordManager;
import com.shroggle.presentation.site.CreateSiteKeywordsGroup;
import com.shroggle.util.CollectionUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.html.HtmlUtil;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * @author Balakirev Anatoliy
 */
public class SiteCreator {

    public static Site updateSiteOrCreateNew(final CreateSiteRequest request) {
        final Site site;
        if (request.getSite() != null) {
            site = request.getSite();
        } else {
            site = new Site();
            site.getSitePaymentSettings().setUserId(request.getUser().getUserId());
            final List<Template> templates = ServiceLocator.getFileSystem().getTemplates();
            site.getThemeId().setTemplateDirectory(templates.get(0).getDirectory());
            site.getThemeId().setThemeCss(templates.get(0).getThemes().get(0).getFile());
            site.setType(request.getSiteType());
        }

        if (request.getSeoSettings() == null) {
            throw new IllegalArgumentException("SeoSettings must always be initialized, at least with empty object.");
        }

        final List<CreateSiteKeywordsGroup> keywordsGroups = request.getKeywordsGroups();
        ServiceLocator.getPersistanceTransaction().execute(new Runnable() {

            @Override
            public void run() {
                final Persistance persistance = ServiceLocator.getPersistance();
                site.setTitle(request.getTitle());

                site.getSeoSettings().setAuthorMetaTag(request.getSeoSettings().getAuthorMetaTag());
                site.getSeoSettings().setCopyrightMetaTag(request.getSeoSettings().getCopyrightMetaTag());
                site.getSeoSettings().setRobotsMetaTag(request.getSeoSettings().getRobotsMetaTag());

                final List<String> newCustomMetaTagList = new ArrayList<String>();
                for (String customMetaTag : request.getSeoSettings().getCustomMetaTagList()) {
                    newCustomMetaTagList.add(customMetaTag);
                }
                site.getSeoSettings().setCustomMetaTagList(newCustomMetaTagList);

                CollectionUtil.removeNull(request.getSeoSettings().getHtmlCodeList());
                site.getSeoSettings().setHtmlCodeList(request.getSeoSettings().getHtmlCodeList());

                if (site.getType() == SiteType.COMMON) {
                    String fixedAlliase = HtmlUtil.removeEndingSlash(request.getCustomUrl());
                    if (fixedAlliase != null)
                        fixedAlliase = fixedAlliase.toLowerCase();
                    site.setCustomUrl(fixedAlliase);
                    site.setSubDomain(request.getSubDomain());
                }

                site.setBrandedSubDomain(request.getBrandedSubDomain());

                if (site.getType() == SiteType.BLUEPRINT) {
                    site.setDescription(request.getBlueprintDescription());
                    site.setBlueprintRightType(request.getBlueprintRightType());
                    if (site.getBlueprintRightType() == null) {
                        site.setBlueprintRightType(SiteBlueprintRightType.CAN_ADD_PAGES);
                    }
                }

                if (request.getSite() == null) {
                    persistance.putSite(site);

                    final DraftMenu defaultMenu = new DraftMenu();
                    defaultMenu.setName("Default menu");
                    defaultMenu.setDefaultSiteMenu(true);
                    site.setMenu(defaultMenu);
                    defaultMenu.setSiteId(site.getSiteId());
                    persistance.putMenu(defaultMenu);

                    UserRightManager userRightManager = new UserRightManager(request.getUser());
                    userRightManager.createUserOnSiteRight(site, SiteAccessLevel.ADMINISTRATOR, false);

                    if (site.getType() == SiteType.COMMON) {
                        SystemPagesCreator.createDefaultLoginPageForSite(site);
                        SystemPagesCreator.createAdminLoginPageForSite(site);
                    }

                    //Creating default registration form.
                    final DraftForm registrationForm = new FormManager().createDefaultAccountRegistrationForm(site.getSiteId());
                    site.setDefaultFormId(registrationForm.getFormId());
                }

                final ChildSiteSettings childSiteSettings = persistance.getChildSiteSettingsById(request.getChildSiteSettingsId());
                if (childSiteSettings != null) {
                    site.setChildSiteSettings(childSiteSettings);
                    childSiteSettings.setSite(site);
                    site.setSitePaymentSettings(childSiteSettings.getSitePaymentSettings());
                    site.setChildSiteFilledFormId(childSiteSettings.getFilledFormId());
                    addRightsForParentSite(site, request.getUser(), childSiteSettings.getParentSite(), childSiteSettings.getAccessLevel());
                    // Setting new userId and siteId for payment logs that relates to child site buying.
                    setUserAndSiteIdForPaymentLogs(childSiteSettings, request.getUser(), site);
                }

                // delete metatags not find in user submit
                final List<KeywordsGroup> siteKeywordsGroups = new ArrayList<KeywordsGroup>(site.getKeywordsGroups());
                final Iterator<KeywordsGroup> siteMetaTagIterator = siteKeywordsGroups.iterator();
                while (siteMetaTagIterator.hasNext()) {
                    final KeywordsGroup siteKeywordsGroup = siteMetaTagIterator.next();
                    boolean find = false;
                    if (keywordsGroups != null) {
                        for (final CreateSiteKeywordsGroup keywordsGroup : keywordsGroups) {
                            if (keywordsGroup.getName().equals(siteKeywordsGroup.getName())) {
                                if (keywordsGroup.getValue() != null) {
                                    find = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (!find) {
                        persistance.removeKeywordsGroup(siteKeywordsGroup);
                        site.removeKeywordsGroup(siteKeywordsGroup);
                        siteMetaTagIterator.remove();
                    }
                }

                if (keywordsGroups != null) {
                    // find meta tag from form in databases
                    for (final CreateSiteKeywordsGroup keywordsGroup : keywordsGroups) {
                        if (keywordsGroup.getValue() != null) {
                            KeywordsGroup findKeywordsGroup = null;
                            for (final KeywordsGroup siteKeywordsGroup : siteKeywordsGroups) {
                                if (siteKeywordsGroup.getName().equals(keywordsGroup.getName())) {
                                    findKeywordsGroup = siteKeywordsGroup;
                                    break;
                                }
                            }

                            if (findKeywordsGroup == null) {
                                findKeywordsGroup = new KeywordsGroup();
                                findKeywordsGroup.setName(keywordsGroup.getName());
                                findKeywordsGroup.setValue(KeywordManager.normalizeKeywords(keywordsGroup.getValue()));
                                site.addKeywordsGroup(findKeywordsGroup);
                                persistance.putKeywordsGroup(findKeywordsGroup);
                            } else {
                                findKeywordsGroup.setValue(keywordsGroup.getValue());
                            }
                        }
                    }
                }
                // Adding default group to site
                if (site.getOwnGroups().isEmpty()) {
                    final Group group = new Group();
                    group.setOwner(site);
                    final International international = ServiceLocator.getInternationStorage().get("configureGroupsWindow", Locale.US);
                    group.setName(international.get("invited"));
                    site.addGroup(group);
                    persistance.putGroup(group);
                }
            }

        });
        return site;
    }

    protected static void addRightsForParentSite(final Site childSite, final User childSiteOwner, final Site parentSite, final SiteAccessLevel newSiteAccessLevel) {
        List<User> users = ServiceLocator.getPersistance().getUsersWithActiveRights(parentSite.getSiteId(), SiteAccessLevel.getUserAccessLevels());
        for (final User user : users) {
            if (user == childSiteOwner) {
                continue;
            }
            new UserRightManager(user).createUserOnSiteRight(childSite, newSiteAccessLevel, true);
        }
    }

    protected static void setUserAndSiteIdForPaymentLogs(final ChildSiteSettings childSiteSettings, final User user, final Site site) {
        List<PaymentLog> paymentLogs = ServiceLocator.getPersistance().getPaymentLogsByChildSiteSettingsId(childSiteSettings.getChildSiteSettingsId());
        for (PaymentLog paymentLog : paymentLogs) {
            paymentLog.setUserId(user.getUserId());
            paymentLog.setSiteId(site.getSiteId());
        }
    }

}
