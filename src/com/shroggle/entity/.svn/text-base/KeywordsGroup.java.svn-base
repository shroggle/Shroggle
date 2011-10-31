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

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;

/**
 * @author Stasuk Artem
 */
@Entity(name = "keywordsGroups")
public class KeywordsGroup {

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getKeywordsGroupId() {
        return keywordsGroupId;
    }

    public void setKeywordsGroupId(int keywordsGroupId) {
        this.keywordsGroupId = keywordsGroupId;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "keywords group id: " + keywordsGroupId + ", name: " + name + " , value: " + value;
    }

    @Id
    private int keywordsGroupId;

    @Column(nullable = true, length = 250)
    private String name;

    @Lob
    private String value;

    @ManyToOne
    @JoinColumn(name = "siteId")
    @ForeignKey(name = "keywordsGroupsSiteId")
    private Site site;

}
