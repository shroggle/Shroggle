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
package com.shroggle.logic.form;

import com.shroggle.entity.FilledForm;
import com.shroggle.presentation.site.ManageFormRecordSortType;
import com.shroggle.util.StringUtil;

import java.util.*;

/**
 * @author Balakirev Anatoliy
 */
public class FilledFormsManager {

    public FilledFormsManager(List<FilledForm> filledForms) {
        this.filledForms = filledForms != null ? new ArrayList<FilledForm>(filledForms) : Collections.<FilledForm>emptyList();
    }

    public List<FilledForm> getFilledForms() {
        return filledForms;
    }

    public FilledFormsManager retainBySearchKey(final String searchKey) {
        if (!StringUtil.isNullOrEmpty(searchKey)) {
            final List<FilledForm> keyFilledForms = new ArrayList<FilledForm>();
            for (final String key : searchKey.split(" ")) {
                for (final FilledForm filledForm : filledForms) {
                    if (FilledFormManager.isFilledFormContainsKey(filledForm, key) && !keyFilledForms.contains(filledForm)) {
                        keyFilledForms.add(filledForm);
                    }
                }
            }
            filledForms.retainAll(keyFilledForms);
        }
        return this;
    }

    public FilledFormsManager sort(final ManageFormRecordSortType sortFieldType) {
        return sort(sortFieldType, null);
    }

    public FilledFormsManager sort(final ManageFormRecordSortType sortFieldType, final String itemName) {
        switch (sortFieldType) {
            case CUSTOM_FIELD: {
                Collections.sort(filledForms, new FieldComparator(itemName));
                break;
            }
            case FILL_DATE: {
                Collections.sort(filledForms, new Comparator<FilledForm>() {
                    public int compare(final FilledForm ff1, final FilledForm ff2) {
                        return ff1.getFillDate().compareTo(ff2.getFillDate());
                    }
                });
                break;
            }
            case UPDATE_DATE: {
                Collections.sort(filledForms, new Comparator<FilledForm>() {
                    public int compare(final FilledForm ff1, final FilledForm ff2) {
                        final Date d1 = ff1.getUpdatedDate() != null ? ff1.getUpdatedDate() : ff1.getFillDate();
                        final Date d2 = ff2.getUpdatedDate() != null ? ff2.getUpdatedDate() : ff2.getFillDate();
                        return d1.compareTo(d2);
                    }
                });
                break;
            }
        }
        if (descendingSort) {
            Collections.reverse(filledForms);
        }
        return this;
    }

    private class FieldComparator implements Comparator<FilledForm> {

        private final String itemName;

        public FieldComparator(final String itemName) {
            this.itemName = itemName;
        }

        public int compare(final FilledForm ff1, final FilledForm ff2) {
            final String ff1FieldValue = FilledFormManager.getFilledFormItemValueByItemName(ff1, itemName);
            final String ff2FieldValue = FilledFormManager.getFilledFormItemValueByItemName(ff2, itemName);

            if (StringUtil.isNullOrEmpty(ff1FieldValue)) {
                return -1;
            } else if (StringUtil.isNullOrEmpty(ff2FieldValue)) {
                return 1;
            } else if (StringUtil.isNullOrEmpty(ff1FieldValue) && StringUtil.isNullOrEmpty(ff2FieldValue)) {
                return 0;
            }
            try {
                final Double aDouble1 = Double.valueOf(ff1FieldValue);
                final Double aDouble2 = Double.valueOf(ff2FieldValue);
                if (aDouble1 != null && aDouble2 != null) {
                    return aDouble1.compareTo(aDouble2);
                }
            } catch (Exception e) {
            }
            return ff1FieldValue.compareTo(ff2FieldValue);
        }

    }

    public FilledFormsManager setDescendingSort(boolean descendingSort) {
        this.descendingSort = descendingSort;
        return this;
    }


    private final List<FilledForm> filledForms;
    private boolean descendingSort = false;
}
