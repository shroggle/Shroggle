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

import java.util.List;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
public class ExecuteAdvancedSearchResponse {

    private String galleryHtml;
    
    private List<SearchCriteriaResultsNumber> resultsNumberList;

    public String getGalleryHtml() {
        return galleryHtml;
    }

    public void setGalleryHtml(String galleryHtml) {
        this.galleryHtml = galleryHtml;
    }

    public List<SearchCriteriaResultsNumber> getResultsNumberList() {
        return resultsNumberList;
    }

    public void setResultsNumberList(List<SearchCriteriaResultsNumber> resultsNumberList) {
        this.resultsNumberList = resultsNumberList;
    }
}
