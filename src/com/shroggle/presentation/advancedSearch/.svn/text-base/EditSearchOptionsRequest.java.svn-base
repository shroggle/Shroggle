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
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
public class EditSearchOptionsRequest {

    @RemoteProperty
    private Integer advancedSearchId;

    @RemoteProperty
    private Integer formId;

    @RemoteProperty
    private List<DraftAdvancedSearchOption> newSearchOptions = new ArrayList<DraftAdvancedSearchOption>();

    @RemoteProperty
    private int siteId;

    public Integer getAdvancedSearchId() {
        return advancedSearchId;
    }

    public void setAdvancedSearchId(Integer advancedSearchId) {
        this.advancedSearchId = advancedSearchId;
    }

    public Integer getFormId() {
        return formId;
    }

    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    public List<DraftAdvancedSearchOption> getNewSearchOptions() {
        return newSearchOptions;
    }

    public void setNewSearchOptions(List<DraftAdvancedSearchOption> newSearchOptions) {
        this.newSearchOptions = newSearchOptions;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }
}
