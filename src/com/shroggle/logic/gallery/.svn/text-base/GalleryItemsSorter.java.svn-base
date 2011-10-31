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
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.*;


/**
 * @author Balakirev Anatoliy
 */
public class GalleryItemsSorter {

    public List<FilledForm> getFilledForms(final Gallery gallery, final DraftItem draftItem, final SiteShowOption siteShowOption) {
        final List<FilledForm> filledForms = new GalleryItemsGetter().getFilledForms(gallery, draftItem, siteShowOption);

        //Sorting filtered filled forms.
        sortFilledForms(gallery, filledForms);

        gallery.setFirstFilledFormId(!filledForms.isEmpty() && filledForms.get(0) != null ? filledForms.get(0).getFilledFormId() : null);

        return filledForms;
    }

    private void sortFilledForms(final Gallery gallery, final List<FilledForm> filledForms) {
        Collections.sort(filledForms, new Comparator<FilledForm>() {

            public int compare(final FilledForm filledForm1, final FilledForm filledForm2) {
                final FilledFormItem[] items = new FilledFormItem[2];
                items[0] = FilledFormManager.getFilledFormItemByFormItemId(filledForm1, gallery.getFirstSortItemId());
                items[1] = FilledFormManager.getFilledFormItemByFormItemId(filledForm2, gallery.getFirstSortItemId());
                int result = compareInternal(items, gallery.getFirstSortType());
                if (result == 0) {
                    items[0] = FilledFormManager.getFilledFormItemByFormItemId(filledForm1, gallery.getSecondSortItemId());
                    items[1] = FilledFormManager.getFilledFormItemByFormItemId(filledForm2, gallery.getSecondSortItemId());
                    result = compareInternal(items, gallery.getSecondSortType());
                }
                return result;
            }

        });
    }

    private int compareInternal(final FilledFormItem[] items, final GallerySortOrder sortType) {
        String firstValue = "";
        if (items[0] != null) {
            firstValue = createValue(items[0]);
        }

        String secondValue = "";
        if (items[1] != null) {
            secondValue = createValue(items[1]);
        }

        if (items[0] != null && items[1] != null) {
            if (items[0].getFormItemName().getCheckers().contains(FormItemCheckerType.ONLY_NUMBERS)) {
                if (firstValue.length() < secondValue.length()) {
                    while (firstValue.length() < secondValue.length()) {
                        firstValue = "0" + firstValue;
                    }
                } else {
                    while (firstValue.length() > secondValue.length()) {
                        secondValue = "0" + secondValue;
                    }
                }
            }
        }

        switch (sortType) {
            case ASCENDING: {
                return firstValue.compareTo(secondValue);
            }
            case DESCENDING: {
                return secondValue.compareTo(firstValue);
            }
        }

        return compareInternal(items, random.nextInt(2) == 0 ? GallerySortOrder.ASCENDING : GallerySortOrder.DESCENDING);
    }

    private String createValue(final FilledFormItem item) {
        if (item.getFormItemName().getType() == FormItemType.FILE_UPLOAD) {
            int formFileId = getFormFileIdByFilledFormItem(item);
            FormFile formFile = persistance.getFormFileById(formFileId);
            if (formFile != null) {
                return formFile.getSourceName() + formFile.getSourceExtension();
            }
        } else {
            String newValue = "";
            for (String value : item.getValues()) {
                newValue += value;
            }
            return newValue;
        }
        return "";
    }

    private int getFormFileIdByFilledFormItem(final FilledFormItem filledFormItem) {
        for (String value : filledFormItem.getValues()) {
            if (value != null && !value.isEmpty()) {
                try {
                    return Integer.valueOf(value);
                } catch (Exception e) {
                    return 0;
                }
            }
        }
        return 0;
    }

    public List<FilledForm> reduceSortedItems(final List<FilledForm> filedForms, final int elementsNumber, final Integer pageNumber) {
        if (pageNumber == null) {
            return filedForms;
        } else {
            List<FilledForm> newFiledForms = new ArrayList<FilledForm>();
            for (int i = (pageNumber - 1) * elementsNumber; i < (pageNumber * elementsNumber); i++) {
                if (i <= filedForms.size() - 1) {
                    newFiledForms.add(filedForms.get(i));
                }
            }
            return newFiledForms;
        }
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final Random random = new Random();

}