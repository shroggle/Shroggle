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

import com.shroggle.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Artem Stasuk
 */
public class GalleryNavigationData {

    GalleryNavigationData(final GalleryManager gallery, final Widget widget, final FilledForm filledForm, final SiteShowOption siteShowOption) {
        beforeItems = new ArrayList<Integer>();
        afterItems = new ArrayList<Integer>();
        if (filledForm != null) {
            final DraftItem draftItem = widget == null ? null : ((WidgetItem) widget).getDraftItem();
            final List<FilledForm> filledForms = new GalleryItemsSorter().getFilledForms(gallery.getEntity(), draftItem, siteShowOption);
            final int index = filledForms.indexOf(filledForm);

            for (int i = index - 1; i > -1; i--) {
                afterItems.add(filledForms.get(i).getFilledFormId());
            }

            for (int i = index + 1; i < filledForms.size(); i++) {
                beforeItems.add(filledForms.get(i).getFilledFormId());
            }
        }
    }

    public Integer getBeforeItem() {
        if (beforeItems.size() > 0) {
            return beforeItems.get(0);
        }
        return null;
    }

    public int getCurrentPosition() {
        return afterItems.size() + 1;
    }

    public Map<Integer, Integer> getAfterItems() {
        final Map<Integer, Integer> afterItems = new TreeMap<Integer, Integer>();
        for (int i = this.afterItems.size() - 1; i > -1 && afterItems.size() < 2; i--) {
            afterItems.put(i + 1, this.afterItems.get(i));
        }
        return afterItems;
    }

    public Map<Integer, Integer> getBeforeItems() {
        final Map<Integer, Integer> beforeItems = new TreeMap<Integer, Integer>();
        for (int i = 0; i < this.beforeItems.size() && i < 2; i++) {
            beforeItems.put(i + afterItems.size() + 2, this.beforeItems.get(i));
        }
        return beforeItems;
    }

    public Integer getAfterItem() {
        if (afterItems.size() > 0) {
            return afterItems.get(0);
        }
        return null;
    }

    private final List<Integer> afterItems;
    private final List<Integer> beforeItems;

}