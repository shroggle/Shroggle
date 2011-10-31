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
package com.shroggle.logic.gallery;

import com.shroggle.entity.GalleryItemColumn;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;

/**
 * @author Stasuk Artem
 */
public class GalleryCellData {

    public List<GalleryItemData> getItems() {
        Collections.sort(items, new Comparator<GalleryItemData>() {
            public int compare(GalleryItemData data1, GalleryItemData data2) {
                final Integer position1 = data1.getPosition() != null ? data1.getPosition() : 0;
                final Integer position2 = data2.getPosition() != null ? data2.getPosition() : 0;
                return position1.compareTo(position2);
            }
        });

        return items;
    }

    public int getWidth() {
        int maxWidth = 1;
        for (final GalleryItemData item : items) {
            final GalleryItemColumn column = item.getColumn();
            if (column == GalleryItemColumn.COLUMN_123) {
                maxWidth = 3;
            } else if (column == GalleryItemColumn.COLUMN_12 || column == GalleryItemColumn.COLUMN_23) {
                if (maxWidth < 2) {
                    maxWidth = 2;
                }
            }
        }
        return maxWidth;
    }

    private final List<GalleryItemData> items = new ArrayList<GalleryItemData>();

}
