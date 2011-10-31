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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Artem Stasuk
 */
public class ContextManual implements Context {

    public String getSessionId() {
        return sessionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public DraftAdvancedSearch getAdvancedSearchRequestById(final int advancedSearchId) {
        for (DraftAdvancedSearch advancedSearch : advancedSearchRequests) {
            if (advancedSearch.getId() == advancedSearchId) {
                return advancedSearch;
            }
        }

        return null;
    }

    public void setAdvancedSearchRequest(DraftAdvancedSearch advancedSearch) {
        advancedSearchRequests.add(advancedSearch);
    }

    public void removeAdvancedSearchRequest(int advancedSearchId) {
        DraftAdvancedSearch advancedSearchToRemove = null;
        for (DraftAdvancedSearch advancedSearch : advancedSearchRequests) {
            if (advancedSearch.getId() == advancedSearchId) {
                advancedSearchToRemove = advancedSearch;
            }
        }

        if (advancedSearchToRemove != null) {
            advancedSearchRequests.remove(advancedSearchToRemove);
        }
    }

    public String getNoBotCode(final String noBotCodePrefix) {
        return null;
    }

    public void setNoBotCode(final String noBotCodePrefix, final String noBotCode) {

    }

    public void setUserId(final Integer userId) {
        this.userId = userId;
    }

    @Override
    public Integer getSiteId() {
        return siteId;
    }

    @Override
    public void setSiteId(final Integer siteId) {
        this.siteId = siteId;
    }

    public void setSessionId(final String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public void setHideWatchedVideos(int galleryId, boolean value) {
        this.hideWhatchedVideos = value;
    }

    @Override
    public boolean isHideWatchedVideos(int galleryId) {
        return hideWhatchedVideos;
    }

    @Override
    public String getBackToNavigationUrl(final int galleryId) {
        return galleryBackToNavigationUrl.get(galleryId);
    }

    @Override
    public void setBackToNavigationUrl(final int galleryId, final String url) {
        this.galleryBackToNavigationUrl.put(galleryId, url);
    }

    @Override
    public void setGalleryShowInData(final int crossWidgetId, final int galleryId) {
        galleryShowInDatas.put(crossWidgetId, galleryId);
    }

    @Override
    public Integer getGalleryShowInData(final int crossWidgetId) {
        return galleryShowInDatas.get(crossWidgetId);
    }

    public void destroy() {

    }

    private Integer userId;
    private String sessionId;
    private Integer siteId;
    private boolean hideWhatchedVideos;
    private final List<Integer> videoRangeIds = new ArrayList<Integer>();
    private final List<DraftAdvancedSearch> advancedSearchRequests = new ArrayList<DraftAdvancedSearch>();
    private final Map<Integer, String> galleryBackToNavigationUrl = new HashMap<Integer, String>();
    private final Map<Integer, Integer> galleryShowInDatas = new HashMap<Integer, Integer>();
    private PaypalPaymentInfoRequest paypalPaymentInfoRequest;

}
