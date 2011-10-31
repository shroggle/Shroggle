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
package com.shroggle.logic.form.export;

import com.shroggle.entity.DraftForm;
import com.shroggle.entity.FormExportTask;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
public class ManageDataExportsModel {

    public ManageDataExportsModel(int formId) {
        new UsersManager().getLogined();
        this.formId = formId;
    }

    public List<FormExportTaskManager> getFormExportTasks() {
        if (this.formExportTaskManagers == null) {
            formExportTaskManagers = new ArrayList<FormExportTaskManager>();
            for (FormExportTask formExportTask : persistance.getFormExportTasksByFormId(formId)) {
                formExportTaskManagers.add(new FormExportTaskManager(formExportTask));
            }
        }
        return this.formExportTaskManagers;
    }

    public int getFormId() {
        return formId;
    }

    public String getFormName() {
        final DraftForm draftForm = persistance.getFormById(formId);
        return draftForm != null ? draftForm.getName() : "";
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private List<FormExportTaskManager> formExportTaskManagers;
    private final int formId;
}
