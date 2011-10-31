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
package com.shroggle.util.context;

import com.shroggle.entity.DraftAdvancedSearch;
import com.shroggle.presentation.site.payment.PaypalPaymentInfoRequest;

import javax.servlet.http.HttpSession;

/**
 * @author Artem Stasuk
 */
public class ContextHttpSession implements Context {

    public ContextHttpSession(final HttpSession session) {
        if (session == null) {
            throw new UnsupportedOperationException("Can't create context with null session!");
        }
        this.session = session;
    }

    public String getSessionId() {
        return getSession().getId();
    }

    public Integer getUserId() {
        return (Integer) getSession().getAttribute("loginUserId");
    }

    public DraftAdvancedSearch getAdvancedSearchRequestById(final int advancedSearchId) {
        return (DraftAdvancedSearch) getSession().getAttribute("advancedSearchRequest" + advancedSearchId);
    }

    public void setAdvancedSearchRequest(DraftAdvancedSearch advancedSearch) {
        getSession().setAttribute("advancedSearchRequest" + advancedSearch.getId(), advancedSearch);
    }

    public void removeAdvancedSearchRequest(int advancedSearchId) {
        getSession().removeAttribute("advancedSearchRequest" + advancedSearchId);
    }

    public String getNoBotCode(String noBotCodePrefix) {
        return (String) getSession().getAttribute("noBotCode" + noBotCodePrefix);
    }

    public void setNoBotCode(final String noBotCodePrefix, final String noBotCode) {
        getSession().setAttribute("noBotCode" + noBotCodePrefix, noBotCode);
    }

    public void setUserId(final Integer userId) {
        getSession().setAttribute("loginUserId", userId);
    }

    @Override
    public Integer getSiteId() {
        return (Integer) getSession().getAttribute("siteId");
    }

    @Override
    public void setSiteId(final Integer siteId) {
        getSession().setAttribute("siteId", siteId);
    }

    @Override
    public void setHideWatchedVideos(final int galleryId, final boolean value) {
        session.setAttribute("hideWhatchedVideos", value);
    }

    @Override
    public boolean isHideWatchedVideos(final int galleryId) {
        final Boolean value = (Boolean) session.getAttribute("hideWhatchedVideos");
        return value != null && value;
    }

    @Override
    public String getBackToNavigationUrl(final int galleryId) {
        return (String) session.getAttribute("galleryBackToNavigationUrl" + galleryId);
    }

    @Override
    public void setBackToNavigationUrl(final int galleryId, final String url) {
        session.setAttribute("galleryBackToNavigationUrl" + galleryId, url);
    }

    @Override
    public void setGalleryShowInData(final int crossWidgetId, final int galleryId) {
        session.setAttribute("galleryShowInData" + crossWidgetId, galleryId);
    }

    @Override
    public Integer getGalleryShowInData(final int crossWidgetId) {
        return (Integer) session.getAttribute("galleryShowInData" + crossWidgetId);
    }

    public void destroy() {
        session = null;
    }

    private HttpSession getSession() {
        if (session == null) {
            throw new UnsupportedOperationException("Can't use destroed context!");
        }
        return session;
    }

    private HttpSession session;

}
