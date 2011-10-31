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
package com.shroggle.presentation.manageRegistrants;

import com.shroggle.entity.RegistrantFilterType;
import com.shroggle.logic.manageRegistrants.ManageRegistrantsSortType;
import org.directwebremoting.annotations.DataTransferObject;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
public class ShowManageRegistrantsRequest {

    public ShowManageRegistrantsRequest() {
    }

    public ShowManageRegistrantsRequest(RegistrantFilterType status, String searchKey, int siteId, ManageRegistrantsSortType sortType, boolean desc) {
        this.status = status;
        this.searchKey = searchKey;
        this.siteId = siteId;
        this.sortType = sortType;
        this.desc = desc;
    }

    private RegistrantFilterType status;

    private String searchKey;

    private int siteId;

    private ManageRegistrantsSortType sortType;

    private boolean desc;

    private Integer pageNumber;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public ManageRegistrantsSortType getSortType() {
        return sortType;
    }

    public void setSortType(ManageRegistrantsSortType sortType) {
        this.sortType = sortType;
    }

    public boolean isDesc() {
        return desc;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }

    public RegistrantFilterType getStatus() {
        return status;
    }

    public void setStatus(RegistrantFilterType status) {
        this.status = status;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }
}
