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

package com.shroggle.logic.advancedSearch.resultsNumber;

import com.shroggle.entity.SiteShowOption;
import com.shroggle.util.ServiceLocator;

import java.util.List;

/**
 * @author dmitry.solomadin
 */
public class AdvancedSearchResultsNumberCreator {

    private int advancedSearchId;
    private int galleryId;
    private List<Integer> currentGalleryItemsHashCodes;
    private List<Integer> fullGalleryItemsHashCodes;

    public AdvancedSearchResultsNumberCreator(final int advancedSearchId, final int galleryId,
                                              final List<Integer> currentGalleryItemsHashCodes,
                                              final List<Integer> fullGalleryItemsHashCodes) {
        this.advancedSearchId = advancedSearchId;
        this.galleryId = galleryId;
        this.currentGalleryItemsHashCodes = currentGalleryItemsHashCodes;
        this.fullGalleryItemsHashCodes = fullGalleryItemsHashCodes;
    }

    private AdvancedSearchResultsNumberCreator() {
    }

    public int getResultsNumber(final int searchOptionId, final String criteria, final SiteShowOption siteShowOption) {
        final AdvancedSearchResultsNumberCache advancedSearchResultsNumberCache =
                ServiceLocator.getAdvancedSearchResultsNumberCache();
        return advancedSearchResultsNumberCache.getResultsNumber(advancedSearchId, galleryId, searchOptionId,
                criteria, currentGalleryItemsHashCodes, fullGalleryItemsHashCodes, siteShowOption);
    }
}
