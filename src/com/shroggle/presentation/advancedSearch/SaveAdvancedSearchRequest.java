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

package com.shroggle.presentation.advancedSearch;

import com.shroggle.entity.DraftAdvancedSearchOption;
import com.shroggle.entity.AdvancedSearchOrientationType;
import org.directwebremoting.annotations.DataTransferObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
public class SaveAdvancedSearchRequest {

    private Integer widgetId;

    private int advancedSearchId;

    // May be null only in case if user selected 'upload manually' option and didn't open 'edit search options' page
    // default form is created. In that case we create default form while saving adv. search.
    private Integer formId;

    // May be null if user selected 'upload manually' or 'existing form' option. In that case we create default gallery
    // while saving adv. search
    private Integer galleryId;

    private String name;

    private AdvancedSearchOrientationType orientationType;

    private boolean includeResultsNumber;

    private String headerComment;

    private boolean displayHeaderComment;

    private List<DraftAdvancedSearchOption> searchOptions = new ArrayList<DraftAdvancedSearchOption>();

    public List<DraftAdvancedSearchOption> getSearchOptions() {
        return searchOptions;
    }

    public void setSearchOptions(List<DraftAdvancedSearchOption> searchOptions) {
        this.searchOptions = searchOptions;
    }

    public boolean isIncludeResultsNumber() {
        return includeResultsNumber;
    }

    public void setIncludeResultsNumber(boolean includeResultsNumber) {
        this.includeResultsNumber = includeResultsNumber;
    }

    public String getHeaderComment() {
        return headerComment;
    }

    public void setHeaderComment(String headerComment) {
        this.headerComment = headerComment;
    }

    public boolean isDisplayHeaderComment() {
        return displayHeaderComment;
    }

    public void setDisplayHeaderComment(boolean displayHeaderComment) {
        this.displayHeaderComment = displayHeaderComment;
    }

    public AdvancedSearchOrientationType getOrientationType() {
        return orientationType;
    }

    public void setOrientationType(AdvancedSearchOrientationType orientationType) {
        this.orientationType = orientationType;
    }

    public Integer getFormId() {
        return formId;
    }

    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    public Integer getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(Integer galleryId) {
        this.galleryId = galleryId;
    }

    public int getAdvancedSearchId() {
        return advancedSearchId;
    }

    public void setAdvancedSearchId(int advancedSearchId) {
        this.advancedSearchId = advancedSearchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(Integer widgetId) {
        this.widgetId = widgetId;
    }
}
