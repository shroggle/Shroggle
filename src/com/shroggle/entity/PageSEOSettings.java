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

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author dmitry.solomadin
 */
@Embeddable
public class PageSEOSettings extends SEOSettings implements Serializable {

    @Column(length = 255)
    private String pageDescription;

    @Column(length = 255)
    private String titleMetaTag;

    public String getTitleMetaTag() {
        return titleMetaTag;
    }

    public void setTitleMetaTag(String titleMetaTag) {
        this.titleMetaTag = titleMetaTag;
    }

    public String getPageDescription() {
        return pageDescription;
    }

    public void setPageDescription(String pageDescription) {
        this.pageDescription = pageDescription;
    }

}
