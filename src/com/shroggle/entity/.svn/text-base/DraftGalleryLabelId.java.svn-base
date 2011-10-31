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

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * @author Balakirev Anatoliy
 */
public class DraftGalleryLabelId implements Serializable, GalleryLabelId {

    public int getFormItemId() {
        return formItemId;
    }

    public void setFormItemId(int itemId) {
        this.formItemId = itemId;
    }

    public DraftGallery getGallery() {
        return gallery;
    }

    /**
     * Don't use this method from logic. Use specific method from Gallery
     *
     * @param gallery - gallery
     */
    public void setGallery(final Gallery gallery) {
        this.gallery = (DraftGallery)gallery;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[itemId: "
                + formItemId + ", galleryId: " + gallery.getId() + "]";
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null) return false;
        if (object.getClass() != DraftGalleryLabelId.class) return false;
        final DraftGalleryLabelId galleryLabelId = (DraftGalleryLabelId) object;
        return galleryLabelId.gallery == gallery && galleryLabelId.formItemId == formItemId;
    }

    @ManyToOne
    @JoinColumn(nullable = false, name = "galleryId")
    @ForeignKey(name = "galleryLabelsGalleryId")
    private DraftGallery gallery;

    /**
     * Form Item Id
     */
    private int formItemId;
}
