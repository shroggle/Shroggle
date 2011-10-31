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
package com.shroggle.presentation.form.filledForms;

import org.directwebremoting.annotations.DataTransferObject;

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject
public class ReloadManageRegistrantsTableRequest {

    private Integer formId;

    private Integer formFilterId;

    private String searchKey;

    private SortProperties sortProperties;

    public Integer getFormId() {
        return formId;
    }

    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    public Integer getFormFilterId() {
        return formFilterId;
    }

    public void setFormFilterId(Integer formFilterId) {
        this.formFilterId = formFilterId;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public SortProperties getSortProperties() {
        return sortProperties;
    }

    public void setSortProperties(SortProperties sortProperties) {
        this.sortProperties = sortProperties;
    }
}
