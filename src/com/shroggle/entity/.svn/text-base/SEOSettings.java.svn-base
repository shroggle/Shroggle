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

import org.hibernate.annotations.CollectionOfElements;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * @author dmitry.solomadin
 */
@MappedSuperclass
@Embeddable
public class SEOSettings implements Serializable{

    @Column(length = 255)
    private String authorMetaTag;

    @Column(length = 255)
    private String copyrightMetaTag;

    @Column(length = 255)
    private String robotsMetaTag;

    @CollectionOfElements
    private List<String> customMetaTagList = new ArrayList<String>();

    @CollectionOfElements
    private List<SEOHtmlCode> htmlCodeList = new ArrayList<SEOHtmlCode>();

    public List<SEOHtmlCode> getHtmlCodeList() {
        return htmlCodeList;
    }

    public void setHtmlCodeList(List<SEOHtmlCode> htmlCodeList) {
        this.htmlCodeList = htmlCodeList;
    }

    public List<String> getCustomMetaTagList() {
        return customMetaTagList;
    }

    public void setCustomMetaTagList(List<String> customMetaTagList) {
        this.customMetaTagList = customMetaTagList;
    }

    public String getAuthorMetaTag() {
        return authorMetaTag;
    }

    public void setAuthorMetaTag(String authorMetaTag) {
        this.authorMetaTag = authorMetaTag;
    }

    public String getCopyrightMetaTag() {
        return copyrightMetaTag;
    }

    public void setCopyrightMetaTag(String copyrightMetaTag) {
        this.copyrightMetaTag = copyrightMetaTag;
    }

    public String getRobotsMetaTag() {
        return robotsMetaTag;
    }

    public void setRobotsMetaTag(String robotsMetaTag) {
        this.robotsMetaTag = robotsMetaTag;
    }
}
