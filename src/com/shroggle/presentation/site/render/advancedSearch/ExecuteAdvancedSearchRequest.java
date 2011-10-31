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

package com.shroggle.presentation.site.render.advancedSearch;

import com.shroggle.entity.SiteShowOption;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
public class ExecuteAdvancedSearchRequest {

    @RemoteProperty
    private int advancedSearchId;

    @RemoteProperty
    private int widgetId;

    @RemoteProperty
    private int advancedSearchOptionId;

    @RemoteProperty
    private List<String> searchOptionCriteriaList = new ArrayList<String>();

    @RemoteProperty
    private int galleryId;

    @RemoteProperty
    private SiteShowOption siteShowOption;

    @RemoteProperty
    private List<SearchCriteriaResultsNumber> criteriaResultsNumberToReloadList = new ArrayList<SearchCriteriaResultsNumber>();

    public List<SearchCriteriaResultsNumber> getCriteriaResultsNumberToReloadList() {
        return criteriaResultsNumberToReloadList;
    }

    public void setCriteriaResultsNumberToReloadList(List<SearchCriteriaResultsNumber> criteriaResultsNumberToReloadList) {
        this.criteriaResultsNumberToReloadList = criteriaResultsNumberToReloadList;
    }

    public int getAdvancedSearchId() {
        return advancedSearchId;
    }

    public void setAdvancedSearchId(int advancedSearchId) {
        this.advancedSearchId = advancedSearchId;
    }

    public int getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(int widgetId) {
        this.widgetId = widgetId;
    }

    public int getAdvancedSearchOptionId() {
        return advancedSearchOptionId;
    }

    public void setAdvancedSearchOptionId(int advancedSearchOptionId) {
        this.advancedSearchOptionId = advancedSearchOptionId;
    }

    public List<String> getSearchOptionCriteriaList() {
        return searchOptionCriteriaList;
    }

    public void setSearchOptionCriteriaList(List<String> searchOptionCriteriaList) {
        this.searchOptionCriteriaList = searchOptionCriteriaList;
    }

    public int getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(int galleryId) {
        this.galleryId = galleryId;
    }

    public SiteShowOption getSiteShowOption() {
        return siteShowOption;
    }

    public void setSiteShowOption(SiteShowOption siteShowOption) {
        this.siteShowOption = siteShowOption;
    }
}
