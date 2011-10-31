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
import com.shroggle.presentation.site.CreateSiteKeywordsGroup;

import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
public class CreateSiteRequest {

    public CreateSiteRequest(Site site, User user, String subDomain, String customUrl, String title,
                             String blueprintDescription, SiteBlueprintRightType blueprintRightType,
                             List<CreateSiteKeywordsGroup> keywordsGroups, Integer childSiteSettingsId,
                             SiteType siteType, SEOSettings seoSettings, final String brandedSubDomain) {
        this.site = site;
        this.user = user;
        this.subDomain = subDomain;
        this.customUrl = customUrl;
        this.title = title;
        this.blueprintDescription = blueprintDescription;
        this.blueprintRightType = blueprintRightType;
        this.keywordsGroups = keywordsGroups;
        this.childSiteSettingsId = childSiteSettingsId;
        this.siteType = siteType;
        this.seoSettings = seoSettings;
        this.brandedSubDomain = brandedSubDomain;
    }

    public SEOSettings getSeoSettings() {
        return seoSettings;
    }

    public SiteType getSiteType() {
        return siteType;
    }

    public Integer getChildSiteSettingsId() {
        return childSiteSettingsId;
    }

    public Site getSite() {
        return site;
    }

    public User getUser() {
        return user;
    }

    public String getSubDomain() {
        return subDomain;
    }

    public String getCustomUrl() {
        return customUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getBlueprintDescription() {
        return blueprintDescription;
    }

    public SiteBlueprintRightType getBlueprintRightType() {
        return blueprintRightType;
    }

    public List<CreateSiteKeywordsGroup> getKeywordsGroups() {
        return keywordsGroups;
    }

    public String getBrandedSubDomain() {
        return brandedSubDomain;
    }

    private final Site site;
    private final String brandedSubDomain;
    private final User user;
    private final String subDomain;
    private final String customUrl;
    private final String title;
    private final String blueprintDescription;
    private final SiteBlueprintRightType blueprintRightType;
    private final List<CreateSiteKeywordsGroup> keywordsGroups;
    private final Integer childSiteSettingsId;
    private final SiteType siteType;
    private final SEOSettings seoSettings;

}
