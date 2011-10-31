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

import com.shroggle.entity.*;
import com.shroggle.logic.form.FormItemManager;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.RemoteMethod;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.ServiceLocator;
import com.shroggle.exception.FormItemNotFoundException;
import com.shroggle.exception.FormNotFoundException;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class ShowAddLinkedFieldService extends ServiceWithExecutePage {

    @RemoteMethod
    public String execute(final ShowAddLinkedFieldRequest request) throws IOException, ServletException {
        loginedUser = new UsersManager().getLogined();

        if (request.getTargetFormId() != null) {
            targetForm = persistance.getFormById(request.getTargetFormId());

            if (targetForm == null) {
                throw new FormNotFoundException("Cannot find form by Id=" + request.getTargetFormId());
            }
        }

        //Editing linked form item.
        if (request.getLinkedFormItemId() != null) {
            selectedFormItem = new DraftFormItem();
            selectedFormItem.setItemName(request.getFormItemText());
            selectedFormItem.setFormItemDisplayType(request.getFormItemDisplayType());
            selectedFormItem.setLinkedFormItemId(request.getLinkedFormItemId());

            final DraftFormItem linkedFormItem = persistance.getFormItemById(selectedFormItem.getLinkedFormItemId());

            if (linkedFormItem != null) {
                sourceForm = linkedFormItem.getForm();
            }
        }

        return executePage("/site/addLinkedField.jsp");
    }

    public DraftFormItem getSelectedFormItem() {
        return selectedFormItem;
    }

    public DraftForm getTargetForm() {
        return targetForm;
    }

    public Form getSourceForm() {
        return sourceForm;
    }

    public List<FormItem> getLinkedAllowedItems() {
        final List<FormItem> returnList = new ArrayList<FormItem>();
        if (sourceForm != null) {
            for (FormItem formItem : sourceForm.getFormItems()){
                if (FormItemManager.isCorrectFormItemForLinked(formItem)){
                    returnList.add(formItem);
                }
            }
        }

        return returnList;
    }

    public UserManager getLoginedUser() {
        return loginedUser;
    }

    private DraftFormItem selectedFormItem; // Reresents item that is beign edited.
    private Form sourceForm; // Represents form that contains linked item from selected form item.
    private DraftForm targetForm; // Represents form that contains item that is beign created.
    private UserManager loginedUser;
    private Persistance persistance = ServiceLocator.getPersistance();

}
