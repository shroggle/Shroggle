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

import com.shroggle.exception.UserException;
import com.shroggle.logic.form.export.FormExportTaskManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.util.ServiceLocator;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.apache.tools.ant.filters.StringInputStream;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
@UrlBinding("/csvFilesGetter.action")
public class CSVFilesGetterAction extends Action {// todo. Add tests

    @DefaultHandler
    public Resolution execute() {
        try {
            new UsersManager().getLogined();
        } catch (final UserException exception) {
            return ServiceLocator.getResolutionCreator().loginInUser(this);
        }
        try {
            final FormExportTaskManager manager = new FormExportTaskManager(formExportTaskId);
            return ServiceLocator.getResolutionCreator().resourceDownload(new StringInputStream(manager.createCSVFile()), manager.createCsvFileName());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unable to create csv file.", e);
            return null;
        }
    }

    public Integer getFormExportTaskId() {
        return formExportTaskId;
    }

    public void setFormExportTaskId(Integer formExportTaskId) {
        this.formExportTaskId = formExportTaskId;
    }

    private Integer formExportTaskId;
    private final Logger logger = Logger.getLogger(this.getClass().getName());
}
