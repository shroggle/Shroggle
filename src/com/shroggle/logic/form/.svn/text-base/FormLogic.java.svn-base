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

import com.shroggle.entity.DraftForm;
import com.shroggle.entity.DraftFormItem;
import com.shroggle.exception.FormNotFoundException;
import com.shroggle.logic.accessibility.Right;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.ContextStorage;
import com.shroggle.util.persistance.Persistance;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Artem Stasuk
 */
public class FormLogic {

    public FormLogic(final int id) {
        Persistance persistance = ServiceLocator.getPersistance();
        this.form = persistance.getFormById(id);
        ContextStorage contextStorage = ServiceLocator.getContextStorage();
        final Integer userId = contextStorage.get().getUserId();
        if (!Right.isAuthorizedUser(form, userId)) {
            throw new FormNotFoundException("Can't find form " + id);
        }
    }

    public FormLogic(final DraftForm form) {
        this.form = form;
    }

    public List<DraftFormItem> getItems() {
        return (List) form.getFormItems();
    }

    public Set<Integer> getItemIds() {
        final Set<Integer> itemIds = new HashSet<Integer>();
        for (final DraftFormItem item : getItems()) {
            itemIds.add(item.getFormItemId());
        }
        return itemIds;
    }

    public int getId() {
        return form.getFormId();
    }

    public DraftForm getForm() {
        return form;
    }

    private final DraftForm form;

}
