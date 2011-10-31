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

package com.shroggle.presentation.site;

import com.shroggle.entity.*;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.UserException;
import com.shroggle.logic.childSites.childSiteSettings.ChildSiteSettingsManager;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.site.CreateSiteRequest;
import com.shroggle.logic.site.SiteByUrlGetter;
import com.shroggle.logic.site.SiteCreatorOrUpdater;
import com.shroggle.logic.site.SitesManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.ResolutionParameter;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.presentation.account.dashboard.DashboardSiteType;
import com.shroggle.presentation.account.dashboard.siteInfo.DashboardSiteInfo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.config.Config;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByAllEntity;
import com.shroggle.util.security.SecurityUser;
import com.shroggle.util.url.UrlValidator;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.*;

@UrlBinding("/site/createSite.action")
public class CreateSiteAction extends Action implements LoginedUserInfo {

    @SecurityUser
    @DefaultHandler
    public Resolution createOrEdit() {
        try {
            userManager = new UsersManager().getLogined();
        } catch (final UserException exception) {
            return resolutionCreator.loginInUser(this);
        }

        if (siteId != null) {
            return edit();
        } else {
            return create();
        }
    }

    private Resolution create() {
        keywordsGroups = new ArrayList<CreateSiteKeywordsGroup>();

        //If ChildSiteSettings != null - prefill site name and domain name
        final ChildSiteSettings settings = persistance.getChildSiteSettingsById(settingsId);
        if (createChildSite && settings != null) {
            final FilledForm filledForm = persistance.getFilledFormById(settings.getFilledFormId());
            if (filledForm != null) {
                title = FilledFormManager.getPageSiteName(filledForm.getFilledFormItems());
                subDomain = FilledFormManager.getDomainName(filledForm.getFilledFormItems());

                if (settings.getChildSiteRegistration() != null) {
                    ChildSiteRegistration childSiteRegistration = (ChildSiteRegistration)
                            persistance.getWorkItem(settings.getChildSiteRegistration().getId());

                    if (childSiteRegistration == null) {
                        childSiteRegistration = settings.getChildSiteRegistration();
                    }

                    if (childSiteRegistration != null) {
                        childSiteRegistrationId = childSiteRegistration.getId();
                        networkName = childSiteRegistration.getName();
                        brandedAllowShroggleDomain = childSiteRegistration.isBrandedAllowShroggleDomain();
                        brandedUrl = childSiteRegistration.getBrandedUrl();
                    }
                }

                if (subDomain == null) {
                    subDomain = new ChildSiteSettingsManager(settings).getUniqueDomainName();
                }
            }
        }

        return resolutionCreator.forwardToUrl("/site/createSite.jsp");
    }

    private Resolution edit() {
        final Site site;
        try {
            site = userManager.getRight().getSiteRight().getSiteForEdit(siteId).getSite();
        } catch (final SiteNotFoundException exception) {
            return resolutionCreator.redirectToAction(DashboardAction.class);
        }

        title = site.getTitle();
        siteType = site.getType();
        blueprintRightType = site.getBlueprintRightType();
        description = site.getDescription();
        subDomain = site.getSubDomain();
        customUrl = site.getCustomUrl();
        brandedSubDomain = site.getBrandedSubDomain();
        seoSettings = site.getSeoSettings();
        keywordsGroups = new ArrayList<CreateSiteKeywordsGroup>();
        for (final KeywordsGroup keywordsGroup : site.getKeywordsGroups()) {
            keywordsGroups.add(new CreateSiteKeywordsGroup(keywordsGroup.getName(), keywordsGroup.getValue()));
        }
        icon = site.getIcon();
        final ChildSiteSettings settings = persistance.getChildSiteSettingsById(settingsId);
        if (createChildSite && settings != null) {
            if (settings.getChildSiteRegistration() != null) {
                ChildSiteRegistration childSiteRegistration = (WorkChildSiteRegistration)
                        persistance.getWorkItem(settings.getChildSiteRegistration().getId());

                if (childSiteRegistration == null) {
                    childSiteRegistration = settings.getChildSiteRegistration();
                }

                if (childSiteRegistration != null) {
                    childSiteRegistrationId = childSiteRegistration.getId();
                    networkName = childSiteRegistration.getName();
                    brandedAllowShroggleDomain = childSiteRegistration.isBrandedAllowShroggleDomain();
                    brandedUrl = childSiteRegistration.getBrandedUrl();
                }
            }
        }

        return resolutionCreator.forwardToUrl("/site/createSite.jsp");
    }

    @SynchronizeByAllEntity(
            entityClass = Site.class)
    public Resolution persist() {
        try {
            userManager = new UsersManager().getLogined();
        } catch (final UserException exception) {
            return resolutionCreator.loginInUser(this);
        }

        final International international = ServiceLocator.getInternationStorage().get("createSite", Locale.US);

        if (isShowNoBotCodeConfirm()) {
            final String noBotCode = sessionStorage.getNoBotCode(this, "createSite");
            if (noBotCode == null || noBotCodeConfirm == null) {
                addValidationError("Please complete the text verification field.", "noBotCodeConfirm");
            } else if (!noBotCode.equals(noBotCodeConfirm)) {
                addValidationError("Your verification code is incorrect!", "noBotCodeConfirm");
            }
        }

        title = StringUtil.trimCutIfNeed(title, 250);
        if (title == null) {
            addValidationError(international.get("SPECIFY_TITLE"), "name_site");
        }

        if (siteId != null) {
            try {
                site = userManager.getRight().getSiteRight().getSiteForEdit(siteId).getSite();
            } catch (final SiteNotFoundException exception) {
                return resolutionCreator.redirectToAction(DashboardAction.class);
            }
        }

        ChildSiteRegistration childSiteRegistration = null;
        final ChildSiteSettings settings = persistance.getChildSiteSettingsById(settingsId);
        if (settings != null) {
            if (settings.getChildSiteRegistration() != null) {
                childSiteRegistration = (WorkChildSiteRegistration) new ItemManager(settings.getChildSiteRegistration()).getWorkItem();

                if (childSiteRegistration == null) {// If there is no work registration - using draft one. Tolik
                    childSiteRegistration = settings.getChildSiteRegistration();
                }

                childSiteRegistrationId = childSiteRegistration.getId();
                networkName = childSiteRegistration.getName();
                brandedAllowShroggleDomain = childSiteRegistration.isBrandedAllowShroggleDomain();
                brandedUrl = childSiteRegistration.getBrandedUrl();
            }
        }

        subDomain = StringUtil.trimCutIfNeedAndLower(subDomain, 250);
        boolean checkSubDomain =
                (site == null && siteType == SiteType.COMMON)//Creating new site.
                        || (site != null && site.getType() == SiteType.COMMON);//Editing existing site.

        if (childSiteRegistration != null) {
            checkSubDomain = childSiteRegistration.isBrandedAllowShroggleDomain();
        }

        if (checkSubDomain) {
            if (subDomain == null) {
                addValidationError(international.get("SPECIFY_DOMAIN_NAME"), "siteUrlPrefix");
            }

            if (subDomain != null) {
                if (!UrlValidator.isSystemSubDomainValid(subDomain)) {
                    addValidationError(international.get("SPECIFY_CORRECT_DOMAIN_NAME"), "siteUrlPrefix");
                }

                final Config config = configStorage.get();
                if (siteId != null && site.getSubDomain() != null && !site.getSubDomain().equals(subDomain)) {
                    if (siteByUrlGetter.get(subDomain + "." + config.getUserSitesUrl()) != null) {
                        addValidationError(international.get("URL_PREFIX_NOT_UNIQUE"), "siteUrlPrefix");
                    }
                } else if (siteId == null) {
                    if (siteByUrlGetter.get(subDomain + "." + config.getUserSitesUrl()) != null) {
                        addValidationError(international.get("URL_PREFIX_NOT_UNIQUE"), "siteUrlPrefix");
                    }
                }
            }
        }

        customUrl = StringUtil.trimCutIfNeedAndLower(customUrl, 250);
        if (customUrl != null) {
            final Site findSite = siteByUrlGetter.get(customUrl);
            if (findSite != null) {
                if (siteId == null || siteId != findSite.getSiteId()) {
                    addValidationError(international.get("ALIASE_URL_NOT_UNIQUE"), "customUrl");
                }
            }
        }

        if (childSiteRegistration != null && childSiteRegistration.getBrandedUrl() != null) {
            if (brandedSubDomain == null) {
                addValidationError(international.get("SPECIFY_DOMAIN_NAME"), "brandedSubDomain");
            } else {
                brandedSubDomain = StringUtil.trimCutIfNeedAndLower(brandedSubDomain, 250);
                final Site findSite = siteByUrlGetter.get(brandedSubDomain + "." + childSiteRegistration.getBrandedUrl());
                if (findSite != null) {
                    if (siteId == null || siteId != findSite.getSiteId()) {
                        addValidationError(international.get("BRANDED_SUB_DOMAIN_NOT_UNIQUE"), "brandedSubDomain");
                    }
                }
            }
        } else {
            brandedSubDomain = null;
        }

        if (keywordsGroups != null) {
            final Iterator<CreateSiteKeywordsGroup> keywordsGroupsIterator = keywordsGroups.iterator();
            while (keywordsGroupsIterator.hasNext()) {
                final CreateSiteKeywordsGroup keywordsGroup = keywordsGroupsIterator.next();
                if (keywordsGroup == null || keywordsGroup.getName() == null && keywordsGroup.getValue() == null) {
                    keywordsGroupsIterator.remove();
                } else if (keywordsGroup.getName() != null) {
                    keywordsGroup.setName(StringUtil.cutIfNeed(keywordsGroup.getName(), 250));
                    // set all names for keywordsgroup to lower case
                    keywordsGroup.setName(keywordsGroup.getName().toLowerCase());
                }
            }
            final Map<String, String> keywordsGroupNames = new HashMap<String, String>();
            for (final CreateSiteKeywordsGroup keywordsGroup : keywordsGroups) {
                if (keywordsGroup.getName() == null) {
                    addValidationError(
                            international.get("KEYWORDS_GROUP_WITHOUT_NAME"),
                            "keywordsGroupName" + keywordsGroup.getId());
                    break;
                }
                if (keywordsGroup.getValue() == null) {
                    addValidationError(
                            international.get("KEYWORDS_GROUP_WITHOUT_VALUE"),
                            "keywordsGroupValue" + keywordsGroup.getId());
                    break;
                }
                if (keywordsGroupNames.containsKey(keywordsGroup.getName())) {
                    addValidationError("", "keywordsGroupName" + keywordsGroupNames.get(keywordsGroup.getName()));
                    addValidationError(
                            international.get("KEYWORDS_GROUP_NAME_NOT_UNIQUE"),
                            "keywordsGroupName" + keywordsGroup.getId());
                    break;
                }
                keywordsGroupNames.put(keywordsGroup.getName(), keywordsGroup.getId());
            }
        }

        if (getContext().getValidationErrors().size() > 0) {
            return resolutionCreator.forwardToUrl("/site/createSite.jsp");
        }

        ServiceLocator.getPersistanceTransaction().execute(new Runnable() {

            @Override
            public void run() {
                CreateSiteRequest createSiteRequest = new CreateSiteRequest(
                        site, userManager.getUser(), subDomain, customUrl, title, description,
                        blueprintRightType, keywordsGroups, settingsId, siteType, seoSettings,
                        brandedSubDomain);
                site = siteCreatorOrUpdater.execute(createSiteRequest);

                site.setIcon(persistance.getIcon(iconId));
            }

        });

        if (siteId == null) {// Creating new site. It`s id must be saved in session and it must be selected on Dashboard. Tolik
            final DashboardSiteType dashboardSiteType;
            if (site.getType() == SiteType.BLUEPRINT) {
                dashboardSiteType = DashboardSiteType.BLUEPRINT;
            } else if (settings != null) {
                dashboardSiteType = DashboardSiteType.CHILD;
            } else {
                dashboardSiteType = DashboardSiteType.COMMON;
            }
            ServiceLocator.getSessionStorage().setSelectedSiteInfoForDashboard(this.getSession(), DashboardSiteInfo.newInstance(site, dashboardSiteType));

            return resolutionCreator.redirectToAction(
                    SelectSiteDesignPageAction.class,
                    new ResolutionParameter("siteId", site.getSiteId()),
                    new ResolutionParameter("createChildSite", createChildSite)
            );
        }

        return resolutionCreator.redirectToAction(DashboardAction.class);
    }

    public void setNoBotCodeConfirm(final String noBotCodeConfirm) {
        this.noBotCodeConfirm = noBotCodeConfirm;
    }

    public String getSubDomain() {
        return subDomain;
    }

    public void setSubDomain(final String subDomain) {
        this.subDomain = subDomain;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getCustomUrl() {
        return customUrl;
    }

    public void setCustomUrl(final String customUrl) {
        this.customUrl = customUrl;
    }

    public List<CreateSiteKeywordsGroup> getKeywordsGroups() {
        return keywordsGroups;
    }

    public void setKeywordsGroups(final List<CreateSiteKeywordsGroup> keywordsGroups) {
        this.keywordsGroups = keywordsGroups;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(final Integer siteId) {
        this.siteId = siteId;
    }

    public User getLoginUser() {
        return new UsersManager().getLoginedUser();
    }

    public boolean isCreateChildSite() {
        return createChildSite;
    }

    public void setCreateChildSite(final boolean createChildSite) {
        this.createChildSite = createChildSite;
    }

    public Integer getSettingsId() {
        return settingsId;
    }

    public void setSettingsId(final Integer settingsId) {
        this.settingsId = settingsId;
    }

    public SiteType getSiteType() {
        return siteType;
    }

    public void setSiteType(final SiteType siteType) {
        this.siteType = siteType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public int getParentSiteId() {
        return parentSiteId;
    }

    public void setParentSiteId(final int parentSiteId) {
        this.parentSiteId = parentSiteId;
    }

    public SiteBlueprintRightType getBlueprintRightType() {
        return blueprintRightType;
    }

    public void setBlueprintRightType(final SiteBlueprintRightType blueprintRightType) {
        this.blueprintRightType = blueprintRightType;
    }

    public SEOSettings getSeoSettings() {
        return seoSettings;
    }

    public void setSeoSettings(SEOSettings seoSettings) {
        this.seoSettings = seoSettings;
    }

    public boolean isShowSEOTabOnPageLoad() {
        return showSEOTabOnPageLoad;
    }

    public void setShowSEOTabOnPageLoad(boolean showSEOTabOnPageLoad) {
        this.showSEOTabOnPageLoad = showSEOTabOnPageLoad;
    }

    public Icon getIcon() {
        return icon;
    }

    public Integer getIconId() {
        return iconId;
    }

    public void setIconId(Integer iconId) {
        this.iconId = iconId;
    }

    public String getBrandedUrl() {
        return brandedUrl;
    }

    public boolean isBrandedAllowShroggleDomain() {
        return brandedAllowShroggleDomain;
    }

    public String getNetworkName() {
        return networkName;
    }

    public String getBrandedSubDomain() {
        return brandedSubDomain;
    }

    public void setBrandedSubDomain(String brandedSubDomain) {
        this.brandedSubDomain = brandedSubDomain;
    }

    public Integer getChildSiteRegistrationId() {
        return childSiteRegistrationId;
    }

    public boolean isShowNoBotCodeConfirm() {
        if (showNoBotCodeConfirm == null) {
            //According to SW-6360 and SW-6376 we should not show verification text for blueprints and for site editing. Tolik
            if (siteType == SiteType.BLUEPRINT || siteId != null) {
                showNoBotCodeConfirm = false;
            } else {
                final List<Site> availableSites = persistance.getSites(
                        userManager.getUserId(), new SiteAccessLevel[]{SiteAccessLevel.ADMINISTRATOR});
                showNoBotCodeConfirm = availableSites.size()
                        >= SitesManager.MAX_AVAILABLE_SITES_BEFORE_CHECK_VERIFICATION_CODE;
            }
        }
        return showNoBotCodeConfirm;
    }

    private int parentSiteId;
    private boolean createChildSite;
    private Integer settingsId;
    private String noBotCodeConfirm;
    private String description;
    private String subDomain;
    private String customUrl;
    private Integer childSiteRegistrationId;
    private List<CreateSiteKeywordsGroup> keywordsGroups;
    private Integer siteId;
    private String title;
    private String brandedSubDomain;
    private SiteType siteType = SiteType.COMMON;
    private SiteBlueprintRightType blueprintRightType = SiteBlueprintRightType.CAN_ADD_PAGES;
    private SEOSettings seoSettings = new SEOSettings();
    private boolean showSEOTabOnPageLoad;
    private Boolean showNoBotCodeConfirm = null;
    private Icon icon;
    private Integer iconId;
    private String brandedUrl;
    private boolean brandedAllowShroggleDomain;
    private String networkName;

    /**
     * Only for internal use.
     */
    private Site site;
    private UserManager userManager;

    private final SiteByUrlGetter siteByUrlGetter = ServiceLocator.getSiteByUrlGetter();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
    private final SiteCreatorOrUpdater siteCreatorOrUpdater = ServiceLocator.getSiteCreatorOrUpdater();

}