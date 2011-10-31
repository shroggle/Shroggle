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
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.Embeddable;

/**
 * @author Balakirev Anatoliy
 */
@Embeddable
public class ChildSiteLink {

    public ChildSiteLink() {
        this.childSiteLinkAlign = GalleryAlign.CENTER;
        this.childSiteLinkName = null;
        this.showChildSiteLink = false;
    }

    private int childSiteLinkPosition;

    private int childSiteLinkRow;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private GalleryAlign childSiteLinkAlign;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private GalleryItemColumn childSiteLinkColumn = GalleryItemColumn.COLUMN_1;

    @Column(length = 250)
    private String childSiteLinkName;

    private boolean showChildSiteLink;

    public int getChildSiteLinkPosition() {
        return childSiteLinkPosition;
    }

    public void setChildSiteLinkPosition(final int childSiteLinkPosition) {
        this.childSiteLinkPosition = childSiteLinkPosition;
    }

    public GalleryAlign getChildSiteLinkAlign() {
        return childSiteLinkAlign;
    }

    public void setChildSiteLinkAlign(GalleryAlign childSiteLinkAlign) {
        this.childSiteLinkAlign = childSiteLinkAlign;
    }

    public GalleryItemColumn getChildSiteLinkColumn() {
        return childSiteLinkColumn;
    }

    public void setChildSiteLinkColumn(GalleryItemColumn childSiteLinkColumn) {
        this.childSiteLinkColumn = childSiteLinkColumn;
    }

    public String getChildSiteLinkName() {
        return childSiteLinkName;
    }

    public void setChildSiteLinkName(String childSiteLinkName) {
        this.childSiteLinkName = childSiteLinkName;
    }

    public boolean isShowChildSiteLink() {
        return showChildSiteLink;
    }

    public void setShowChildSiteLink(boolean showChildSiteLink) {
        this.showChildSiteLink = showChildSiteLink;
    }

    public int getChildSiteLinkRow() {
        return childSiteLinkRow;
    }

    public void setChildSiteLinkRow(int childSiteLinkRow) {
        this.childSiteLinkRow = childSiteLinkRow;
    }
}
