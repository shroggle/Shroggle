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
package com.shroggle.presentation.gallery;

import com.shroggle.entity.DraftGallery;
import com.shroggle.entity.GallerySortOrder;

import java.util.List;


/**
 * @author Balakirev Anatoliy
 */
public class NavigationSortedFormsInSession {

    private List<Integer> sortedFilledFormsIds;

    private long maxId;

    private int firstSortItemId;

    private GallerySortOrder firstSortType;

    private int secondSortItemId;

    private GallerySortOrder secondSortType;

    public boolean allItemsAreNull() {
        return sortedFilledFormsIds == null || firstSortType == null || secondSortType == null;
    }

    public boolean equals(final DraftGallery gallery, final int maxFilledFormId, final long filledFormsNumber) {
        if (filledFormsNumber != sortedFilledFormsIds.size()) {
            return false;
        } else if (maxFilledFormId != maxId) {
            return false;
        } else if (gallery.getFirstSortItemId() != firstSortItemId) {
            return false;
        } else if (gallery.getFirstSortType() != firstSortType) {
            return false;
        } else if (gallery.getSecondSortItemId() != secondSortItemId) {
            return false;
        } else if (gallery.getSecondSortType() != secondSortType) {
            return false;
        }
        return true;
    }

    public GallerySortOrder getFirstSortType() {
        return firstSortType;
    }

    public void setFirstSortType(GallerySortOrder firstSortType) {
        this.firstSortType = firstSortType;
    }

    public int getSecondSortItemId() {
        return secondSortItemId;
    }

    public void setSecondSortItemId(int secondSortItemId) {
        this.secondSortItemId = secondSortItemId;
    }

    public GallerySortOrder getSecondSortType() {
        return secondSortType;
    }

    public void setSecondSortType(GallerySortOrder secondSortType) {
        this.secondSortType = secondSortType;
    }

    public int getFirstSortItemId() {
        return firstSortItemId;
    }

    public void setFirstSortItemId(int firstSortItemId) {
        this.firstSortItemId = firstSortItemId;
    }

    public long getMaxId() {
        return maxId;
    }

    public void setMaxId(long maxId) {
        this.maxId = maxId;
    }

    public List<Integer> getSortedFilledFormsIds() {
        return sortedFilledFormsIds;
    }

    public void setSortedFilledFormsIds(List<Integer> sortedFilledFormsIds) {
        this.sortedFilledFormsIds = sortedFilledFormsIds;
    }
}