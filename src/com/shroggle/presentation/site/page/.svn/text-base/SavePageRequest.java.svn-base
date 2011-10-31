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

package com.shroggle.presentation.site.page;

import com.shroggle.entity.PageSEOSettings;
import com.shroggle.entity.PageType;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import java.util.Set;

/**
 * @author Stasuk Artem
 */
@DataTransferObject
public class SavePageRequest {

    @RemoteProperty
    private String url;

    @RemoteProperty
    private PageType pageType = PageType.BLANK;

    @RemoteProperty
    private String name;

    @RemoteProperty
    private String title;

    @RemoteProperty
    private int siteId;

    @RemoteProperty
    private Set<Integer> keywordsGroupIds;

    @RemoteProperty
    private String aliaseUrl;

    @RemoteProperty
    private String keywords;

    @RemoteProperty
    private Boolean includeInMenu;

    @RemoteProperty
    private Integer pageToEditId;

    @RemoteProperty
    private PageSEOSettings pageSEOSettings = new PageSEOSettings();

    public PageSEOSettings getPageSEOSettings() {
        return pageSEOSettings;
    }

    public void setPageSEOSettings(PageSEOSettings pageSEOSettings) {
        this.pageSEOSettings = pageSEOSettings;
    }

    public Integer getPageToEditId() {
        return pageToEditId;
    }

    public void setPageToEditId(Integer pageToEditId) {
        this.pageToEditId = pageToEditId;
    }

    public Set<Integer> getKeywordsGroupIds() {
        return keywordsGroupIds;
    }

    public void setKeywordsGroupIds(Set<Integer> keywordsGroups) {
        this.keywordsGroupIds = keywordsGroups;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PageType getPageType() {
        return pageType;
    }

    public void setPageType(PageType pageType) {
        this.pageType = pageType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getAliaseUrl() {
        return aliaseUrl;
    }

    public void setAliaseUrl(final String aliaseUrl) {
        this.aliaseUrl = aliaseUrl;
    }

    public Boolean isIncludeInMenu() {
        return includeInMenu;
    }

    public void setIncludeInMenu(final Boolean includeInMenu) {
        this.includeInMenu = includeInMenu;
    }

}