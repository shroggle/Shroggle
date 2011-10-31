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

/**
 * @author Artem Stasuk
 */
public interface Context {

    String getSessionId();

    Integer getUserId();

    DraftAdvancedSearch getAdvancedSearchRequestById(int advancedSearchId);

    void setAdvancedSearchRequest(DraftAdvancedSearch advancedSearch);

    void removeAdvancedSearchRequest(int advancedSearchId);

    String getNoBotCode(final String noBotCodePrefix);

    void setNoBotCode(final String noBotCodePrefix, final String noBotCode);

    void setUserId(final Integer userId);

    Integer getSiteId();

    void setSiteId(Integer siteId);

    void setHideWatchedVideos(int galleryId, boolean value);

    boolean isHideWatchedVideos(int galleryId);

    String getBackToNavigationUrl(int crossWidgetId);

    void setBackToNavigationUrl(int crossWidgetId, String url);

    void setGalleryShowInData(int crossWidgetId, int galleryId);

    Integer getGalleryShowInData(int crossWidgetId);

    void destroy();

}
