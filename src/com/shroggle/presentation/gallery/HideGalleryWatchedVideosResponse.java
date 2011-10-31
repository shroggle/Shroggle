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

/**
 * @author Stasuk Artem
 */
public class HideGalleryWatchedVideosResponse {

    public String getDataHtml() {
        return dataHtml;
    }

    public void setDataHtml(String dataHtml) {
        this.dataHtml = dataHtml;
    }

    public String getNavigationHtml() {
        return navigationHtml;
    }

    public void setNavigationHtml(String navigationHtml) {
        this.navigationHtml = navigationHtml;
    }

    private String navigationHtml;
    private String dataHtml;

}
