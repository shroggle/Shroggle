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

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Artem Stasuk
 */
@Entity(name = "widgetGalleryItems")
public class WidgetGalleryItems {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<DraftGalleryLabel> getLabels() {
        return (List<DraftGalleryLabel>) labels;
    }

    public List<DraftGalleryItem> getItems() {
        return (List<DraftGalleryItem>) items;
    }

    @Id
    private int id;

    @Lob
    private Serializable items = new ArrayList<DraftGalleryItem>();

    @Lob
    private Serializable labels = new ArrayList<DraftGalleryLabel>();

}
