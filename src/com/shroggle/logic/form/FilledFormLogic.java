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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Artem Stasuk
 */
public class FilledFormLogic {

    public FilledFormLogic(final FilledForm filledForm) {
        this.filledForm = filledForm;
    }

    public FilledFormItem getFilledItemByFormItemId(final int formItemId) {
        if (itemsByFormItemId == null) {
            itemsByFormItemId = new HashMap<Integer, FilledFormItem>();
            for (final FilledFormItem filledFormItem : filledForm.getFilledFormItems()) {
                itemsByFormItemId.put(filledFormItem.getFormItemId(), filledFormItem);
            }
        }
        return itemsByFormItemId.get(formItemId);
    }

    private final FilledForm filledForm;
    private Map<Integer, FilledFormItem> itemsByFormItemId;

}
