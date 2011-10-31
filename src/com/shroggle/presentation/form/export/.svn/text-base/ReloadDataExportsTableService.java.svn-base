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

import com.shroggle.logic.form.export.ManageDataExportsModel;
import com.shroggle.presentation.AbstractService;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class ReloadDataExportsTableService extends AbstractService {

    @RemoteMethod
    public String execute(final Integer formId) throws Exception {
        getContext().getHttpServletRequest().setAttribute("manageDataExportsModel", new ManageDataExportsModel(formId));
        return getContext().forwardToString("/site/form/export/manageDataExportTable.jsp");
    }

}
