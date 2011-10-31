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
package com.shroggle.presentation.form;

import com.shroggle.logic.form.FormItemManager;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.RemoteMethod;

import java.util.ArrayList;
import java.util.List;

import com.shroggle.entity.DraftFormItem;
import com.shroggle.entity.DraftForm;
import com.shroggle.entity.FormItem;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.ServiceLocator;
import com.shroggle.exception.FormNotFoundException;
import com.shroggle.presentation.AbstractService;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class GetFormItemsService extends AbstractService {

    @RemoteMethod
    public List<FormItem> execute(final int formId) {
        new UsersManager().getLogined();

        final DraftForm form = persistance.getFormById(formId);

        if (form == null) {
            throw new FormNotFoundException("Cannot find form by Id=" + formId);
        }

        return form.getFormItems();
    }

    @RemoteMethod
    public List<FormItem> executeForLinked(final int formId) {
        new UsersManager().getLogined();

        final DraftForm form = persistance.getFormById(formId);

        if (form == null) {
            throw new FormNotFoundException("Cannot find form by Id=" + formId);
        }

        List<FormItem> formItems = new ArrayList<FormItem>();

        for (FormItem formItem : form.getFormItems()){
            if (FormItemManager.isCorrectFormItemForLinked(formItem)){
                formItems.add(formItem);
            }
        }

        return formItems;
    }

    private Persistance persistance = ServiceLocator.getPersistance();


}
