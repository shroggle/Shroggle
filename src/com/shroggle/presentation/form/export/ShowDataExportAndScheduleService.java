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
package com.shroggle.presentation.form.export;

import com.shroggle.logic.form.export.DataExportAndScheduleModel;
import com.shroggle.presentation.AbstractService;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class ShowDataExportAndScheduleService extends AbstractService {

    @RemoteMethod
    public String show(final Integer formId, final Integer formExportTaskId) throws Exception {
        getRequest().setAttribute("dataExportAndScheduleModel", new DataExportAndScheduleModel(formId, formExportTaskId));
        return forwardToString("/site/form/export/dataExportAndScheduler.jsp");
    }
}
