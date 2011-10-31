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

import javax.persistence.*;

/**
 * @author Artem
 */
@Entity(name = "galleryItemLabels")
public class DraftGalleryLabel implements GalleryLabel {

    public DraftGalleryLabel() {
        align = GalleryAlign.CENTER;
        position = -1;
    }

    public GalleryLabelId getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public GalleryAlign getAlign() {
        return align;
    }

    public void setAlign(GalleryAlign align) {
        this.align = align;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public int getItemId() {
        return id.getFormItemId();
    }

    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Used only for copier
     *
     * @param id - id
     * @see com.shroggle.util.copier.CopierWorker
     */
    public void setId(final GalleryLabelId id) {
        this.id = (DraftGalleryLabelId)id;
    }

    @EmbeddedId
    private DraftGalleryLabelId id = new DraftGalleryLabelId();

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private GalleryAlign align = GalleryAlign.CENTER;

    private int position;

    @Column(name = "column1")
    private int column;

}
