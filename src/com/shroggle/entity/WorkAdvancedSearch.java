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

package com.shroggle.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@Entity(name = "workAdvancedSearches")
public class WorkAdvancedSearch extends WorkItem {

    public void addSearchOption(final WorkAdvancedSearchOption advancedSearchOption) {
        advancedSearchOptions.add(advancedSearchOption);
    }

    public boolean isIncludeResultsNumber() {
        return includeResultsNumber;
    }

    public void setIncludeResultsNumber(boolean includeResultsNumber) {
        this.includeResultsNumber = includeResultsNumber;
    }

    public boolean isDisplayHeaderComments() {
        return isShowDescription();
    }

    public void setDisplayHeaderComments(boolean displayHeaderComments) {
        setShowDescription(displayHeaderComments);
    }

    public int getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(int galleryId) {
        this.galleryId = galleryId;
    }

    public AdvancedSearchOrientationType getAdvancedSearchOrientationType() {
        return advancedSearchOrientationType;
    }

    public void setAdvancedSearchOrientationType(AdvancedSearchOrientationType advancedSearchOrientationType) {
        this.advancedSearchOrientationType = advancedSearchOrientationType;
    }

    public List<WorkAdvancedSearchOption> getAdvancedSearchOptions() {
        return advancedSearchOptions;
    }

    public ItemType getItemType() {
        return ItemType.ADVANCED_SEARCH;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "advancedSearch")
    private List<WorkAdvancedSearchOption> advancedSearchOptions = new ArrayList<WorkAdvancedSearchOption>();

    @Enumerated(value = EnumType.STRING)
    private AdvancedSearchOrientationType advancedSearchOrientationType = AdvancedSearchOrientationType.ABOVE;

    private int galleryId;

    private boolean includeResultsNumber;

}