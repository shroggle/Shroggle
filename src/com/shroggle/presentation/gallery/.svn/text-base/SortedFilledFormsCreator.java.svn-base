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
package com.shroggle.presentation.gallery;

import com.shroggle.entity.*;
import com.shroggle.logic.gallery.FilledFormsInSession;
import com.shroggle.logic.gallery.GalleryItemsSorter;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
public class SortedFilledFormsCreator {

    public static List<FilledForm> execute(
            final Gallery gallery, final DraftItem draftItem, final Integer pageNumber, final HttpSession session,
            final boolean showOneThumbnail, final SiteShowOption siteShowOption) {
        if (gallery == null) {
            return new ArrayList<FilledForm>();
        }

        final GalleryItemsSorter sorter = new GalleryItemsSorter();
        if (pageNumber == null) {
            return sorter.getFilledForms(gallery, draftItem, siteShowOption);
        }

        final FilledFormsInSession filledFormsInSession = new FilledFormsInSession();
        final List<Integer> filedFormsIds = filledFormsInSession.get(gallery, session);
        if (filedFormsIds != null) {
            List<FilledForm> filledForms = new ArrayList<FilledForm>();
            Persistance persistance = ServiceLocator.getPersistance();
            final List<Integer> filledFormIds;
            if (showOneThumbnail) {
                filledFormIds = filledFormsInSession.reduceSortedItems(filedFormsIds, 1, pageNumber);
            } else {
                filledFormIds = filledFormsInSession.reduceSortedItems(
                        filedFormsIds, (gallery.getRows() * gallery.getColumns()), pageNumber);
            }
            for (int filledFormId : filledFormIds) {
                filledForms.add(persistance.getFilledFormById(filledFormId));
            }
            return filledForms;
        }

        final List<FilledForm> filedForms = sorter.getFilledForms(gallery, draftItem, siteShowOption);
        filledFormsInSession.set(gallery, filedForms, session);
        if (showOneThumbnail) {
            return sorter.reduceSortedItems(filedForms, 1, pageNumber);
        } else {
            return sorter.reduceSortedItems(filedForms, (gallery.getRows() * gallery.getColumns()), pageNumber);
        }
    }

}