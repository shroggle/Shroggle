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

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

/**
 * @author Artem Stasuk
 */
@DataTransferObject
public class SiteTitlePageName {

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getSiteCustomUrl() {
        return siteCustomUrl;
    }

    public void setSiteCustomUrl(String siteCustomUrl) {
        this.siteCustomUrl = siteCustomUrl;
    }

    public String getSiteSubDomain() {
        return siteSubDomain;
    }

    public void setSiteSubDomain(String siteSubDomain) {
        this.siteSubDomain = siteSubDomain;
    }

    public String getSiteTitle() {
        return siteTitle;
    }

    public void setSiteTitle(String siteTitle) {
        this.siteTitle = siteTitle;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    @RemoteProperty
    private int siteId;

    @RemoteProperty
    private int pageId;

    @RemoteProperty
    private String siteTitle;

    @RemoteProperty
    private String siteSubDomain;

    @RemoteProperty
    private String siteCustomUrl;

    @RemoteProperty
    private String pageName;

    @RemoteProperty
    private String pageUrl;

}
