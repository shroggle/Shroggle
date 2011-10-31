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

import com.shroggle.logic.gallery.GalleryDispatchHelper;
import com.shroggle.presentation.site.render.ShowPageVersionUrlGetter;
import com.shroggle.entity.SiteShowOption;

/**
 * @author dmitry.solomadin
 */
public class GalleryNavigationUrl {

    // Actual script that either opens gallery in a new window or executing dispatch.
    private String userScript;

    // Contains parameters for ajax dispatch
    private String ajaxDispatch;

    private String searchEngineUrl;

    private String keywords;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSearchEngineUrl() {
        return searchEngineUrl;
    }

    public void setSearchEngineUrl(String searchEngineUrl) {
        this.searchEngineUrl = searchEngineUrl;
    }

    public String getUserScript() {
        return userScript;
    }

    public void setUserScript(String userScript) {
        this.userScript = userScript;
    }

    public String getAjaxDispatch() {
        return ajaxDispatch;
    }

    public void setAjaxDispatch(String ajaxDispatch) {
        this.ajaxDispatch = ajaxDispatch;
    }

}
