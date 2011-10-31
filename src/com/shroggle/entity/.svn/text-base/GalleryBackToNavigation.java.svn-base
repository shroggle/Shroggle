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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author Balakirev Anatoliy
 */
@Embeddable
public class GalleryBackToNavigation {

    public int getBackToNavigationPosition() {
        return backToNavigationPosition;
    }

    public void setBackToNavigationPosition(final int backToNavigationPosition) {
        this.backToNavigationPosition = backToNavigationPosition;
    }

    public GalleryAlign getBackToNavigationAlign() {
        return backToNavigationAlign;
    }

    public void setBackToNavigationAlign(GalleryAlign backToNavigationAlign) {
        this.backToNavigationAlign = backToNavigationAlign;
    }

    public GalleryItemColumn getBackToNavigationColumn() {
        return backToNavigationColumn;
    }

    public void setBackToNavigationColumn(GalleryItemColumn backToNavigationColumn) {
        this.backToNavigationColumn = backToNavigationColumn;
    }

    public int getBackToNavigationRow() {
        return backToNavigationRow;
    }

    public void setBackToNavigationRow(int backToNavigationRow) {
        this.backToNavigationRow = backToNavigationRow;
    }

    public String getBackToNavigationName() {
        return backToNavigationName;
    }

    public void setBackToNavigationName(String backToNavigationName) {
        this.backToNavigationName = backToNavigationName;
    }

    private int backToNavigationPosition;
    private int backToNavigationRow;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private GalleryAlign backToNavigationAlign = GalleryAlign.CENTER;

    /**
     * If is null, user disable back to navigation
     */
    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private GalleryItemColumn backToNavigationColumn;

    @Column(length = 250)
    private String backToNavigationName;

}