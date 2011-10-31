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

import javax.servlet.http.HttpSession;
import java.util.*;


/**
 * @author Balakirev Anatoliy
 */
public class FilledFormsInSession {

    public List<Integer> get(final Gallery gallery, final HttpSession session) {
//        if (gallery == null) {
            return null;
//        }
//        NavigationSortedFormsInSession formsInSession =
//                ServiceLocator.getSessionStorage().getSortedFilledFormsByFormId(session, gallery.getFilledFormId());
//        final Persistance persistance = ServiceLocator.getPersistance();
//        if (formsInSession != null && !formsInSession.allItemsAreNull()) {
//            final Integer maxFilledFormId = persistance.getMaxFilledFormIdByFormId(gallery.getFilledFormId());
//            final long filledFormsNumber = persistance.getFilledFormsNumberByFormId(gallery.getFilledFormId());
//            if (formsInSession.equals(gallery, maxFilledFormId == null ? 0 : maxFilledFormId, filledFormsNumber)) {
//                return formsInSession.getSortedFilledFormsIds();
//            } else {
//                return null;
//            }
//        }
//        return null;
    }

    public void set(final Gallery gallery, final List<FilledForm> filedForms,
                                              final HttpSession session) {
        if (gallery != null && filedForms != null) {
//            NavigationSortedFormsInSession formsInSession = new NavigationSortedFormsInSession();
//            formsInSession.setFirstSortItemId(gallery.getFirstSortItemId());
//            formsInSession.setSecondSortItemId(gallery.getSecondSortItemId());
//            formsInSession.setFirstSortType(gallery.getFirstSortType());
//            formsInSession.setSecondSortType(gallery.getSecondSortType());
//            final Persistance persistance = ServiceLocator.getPersistance();
//            final Integer maxFilledFormId = persistance.getMaxFilledFormIdByFormId(gallery.getFilledFormId());
//            formsInSession.setMaxId(maxFilledFormId == null ? 0 : maxFilledFormId);
//            List<Integer> sortedFilledFormsIds = new ArrayList<Integer>();
//            for (FilledForm filledForm : filedForms) {
//                sortedFilledFormsIds.add(filledForm.getFilledFormId());
//            }
//            formsInSession.setSortedFilledFormsIds(sortedFilledFormsIds);
//            ServiceLocator.getSessionStorage().setSortedFilledForms(session, formsInSession, gallery.getFilledFormId());
        }
    }

    public List<Integer> reduceSortedItems(final List<Integer> filedFormsIds, final int elementsNumber, final Integer pageNumber) {
        if (pageNumber == null) {
            return filedFormsIds;
        } else {
            List<Integer> newFiledForms = new ArrayList<Integer>();
            for (int i = (pageNumber - 1) * elementsNumber; i < (pageNumber * elementsNumber); i++) {
                if (i <= filedFormsIds.size() - 1) {
                    newFiledForms.add(filedFormsIds.get(i));
                }
            }
            return newFiledForms;
        }
    }
}