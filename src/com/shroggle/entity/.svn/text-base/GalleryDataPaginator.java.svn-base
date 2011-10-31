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
public class GalleryDataPaginator {

    public GalleryDataPaginatorType getDataPaginatorType() {
        return dataPaginatorType;
    }

    public void setDataPaginatorType(GalleryDataPaginatorType dataPaginatorType) {
        this.dataPaginatorType = dataPaginatorType;
    }

    public String getDataPaginatorArrow() {
        return dataPaginatorArrow;
    }

    public void setDataPaginatorArrow(String dataPaginatorArrow) {
        this.dataPaginatorArrow = dataPaginatorArrow;
    }

    public int getDataPaginatorPosition() {
        return dataPaginatorPosition;
    }

    public void setDataPaginatorPosition(int dataPaginatorPosition) {
        this.dataPaginatorPosition = dataPaginatorPosition;
    }

    public int getDataPaginatorRow() {
        return dataPaginatorRow;
    }

    public void setDataPaginatorRow(int dataPaginatorRow) {
        this.dataPaginatorRow = dataPaginatorRow;
    }

    public GalleryAlign getDataPaginatorAlign() {
        return dataPaginatorAlign;
    }

    public void setDataPaginatorAlign(GalleryAlign dataPaginatorAlign) {
        this.dataPaginatorAlign = dataPaginatorAlign;
    }

    public GalleryItemColumn getDataPaginatorColumn() {
        return dataPaginatorColumn;
    }

    public void setDataPaginatorColumn(GalleryItemColumn dataPaginatorColumn) {
        this.dataPaginatorColumn = dataPaginatorColumn;
    }

    /**
     * If dataPaginatorType = GalleryDataPaginatorType.ARROWS store arrow file prefix.
     * Fox example arrow1, all arrow images stored in /images/gallery/
     */
    @Column(length = 200)
    private String dataPaginatorArrow;

    private int dataPaginatorPosition;

    private int dataPaginatorRow;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private GalleryAlign dataPaginatorAlign = GalleryAlign.CENTER;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private GalleryItemColumn dataPaginatorColumn = GalleryItemColumn.COLUMN_1;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private GalleryDataPaginatorType dataPaginatorType = GalleryDataPaginatorType.PREVIOUS_NEXT;

}