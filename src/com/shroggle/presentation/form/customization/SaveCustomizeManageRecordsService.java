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
package com.shroggle.presentation.form.customization;

import com.shroggle.entity.CustomizeManageRecords;
import com.shroggle.entity.CustomizeManageRecordsField;
import com.shroggle.entity.Form;
import com.shroggle.entity.FormItemName;
import com.shroggle.exception.FormNotFoundException;
import com.shroggle.logic.form.FormItemNameManager;
import com.shroggle.logic.form.customization.CustomizeManageRecordsFieldManager;
import com.shroggle.logic.form.customization.CustomizeManageRecordsManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.form.filledForms.ShowFilledFormsRequest;
import com.shroggle.presentation.form.filledForms.ShowFilledFormsResponse;
import com.shroggle.presentation.form.filledForms.ShowFormRecordsService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class SaveCustomizeManageRecordsService {

    @RemoteMethod
    public ShowFilledFormsResponse execute(final SaveCustomizeManageRecordsRequest request) throws Exception {
        final UserManager userManager = new UsersManager().getLogined();
        final Form form = persistance.getFormById(request.getFormId());
        if (form == null) {
            throw new FormNotFoundException("Unable to find form by id = " + request.getFormId() + ".");
        }
        persistanceTransaction.execute(new Runnable() {
            @Override
            public void run() {
                CustomizeManageRecords customizeManageRecords = persistance.getCustomizeManageRecords(form.getId(), userManager.getUserId());
                if (customizeManageRecords == null) {
                    customizeManageRecords = new CustomizeManageRecords();
                }
                customizeManageRecords.setFormId(request.getFormId());
                customizeManageRecords.setUserId(userManager.getUserId());
                if (customizeManageRecords.getId() <= 0) {
                    persistance.putCustomizeManageRecords(customizeManageRecords);
                }
                // Removing old fields.
                for (CustomizeManageRecordsFieldManager manager : new CustomizeManageRecordsManager(customizeManageRecords).getFields()) {
                    manager.removeField();
                }
                // Adding new one.
                int includedFieldsQuantity = 0;
                for (CustomizeManageRecordsField field : request.getFields()) {
                    if (field.isInclude()) {
                        includedFieldsQuantity++;
                    }
                    if (includedFieldsQuantity > CustomizeManageRecordsFieldManager.getMaxFieldsQuantity()) {
                        logger.warning("Something is wrong here. We should not get more than " +
                                CustomizeManageRecordsFieldManager.getMaxFieldsQuantity() + "fields here. Check script.");
                        field.setInclude(false);
                    }
                    final FormItemName formItemName = new CustomizeManageRecordsFieldManager(field).getFormItemName();
                    if (field.isInclude() && !FormItemNameManager.showFieldOnManageRecords(formItemName)) {
                        logger.warning("Unable to include field with formItemName =  " + formItemName + ".");
                        field.setInclude(false);
                    }
                    customizeManageRecords.addField(field);
                    persistance.putCustomizeManageRecordsField(field);
                }
            }
        });
        final ShowFilledFormsRequest showFilledFormsRequest = new ShowFilledFormsRequest();
        showFilledFormsRequest.setFormId(form.getId());
        return new ShowFormRecordsService().execute(showFilledFormsRequest);
    }

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
}
