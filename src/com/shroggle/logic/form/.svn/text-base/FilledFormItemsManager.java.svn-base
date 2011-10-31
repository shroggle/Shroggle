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
import com.shroggle.entity.FilledFormItem;
import com.shroggle.entity.FormItemType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
public class FilledFormItemsManager {

    public static FilledFormItem getFirstItemByFilledFormId(final List<FilledFormItem> filledFormItems, int filledFormId) {
        if (filledFormItems == null) {
            return null;
        }

        for (FilledFormItem filledFormItem : filledFormItems) {
            if (filledFormItem.getFilledForm().getFilledFormId() == filledFormId) {
                return filledFormItem;
            }
        }

        return null;
    }

    public static List<FilledFormItem> getFilledFormItemByItemType(final FilledForm filledForm, final FormItemType itemType) {
        final List<FilledFormItem> returnList = new ArrayList<FilledFormItem>();
        if (filledForm == null) {
            return returnList;
        }

        for (FilledFormItem filledFormItem : filledForm.getFilledFormItems()) {
            if (filledFormItem.getFormItemName().getType() == itemType) {
                returnList.add(filledFormItem);
            }
        }

        return returnList;
    }

    public static FilledFormItem getFirstFilledFormItemByItemType(final FilledForm filledForm, final FormItemType itemType) {
        final List<FilledFormItem> filledFormItemsByItemType = getFilledFormItemByItemType(filledForm, itemType);
        if (filledFormItemsByItemType != null && !filledFormItemsByItemType.isEmpty()) {
            return filledFormItemsByItemType.get(0);
        }

        return null;
    }

}
