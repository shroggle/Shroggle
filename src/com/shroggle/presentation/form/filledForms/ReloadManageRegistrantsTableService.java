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
package com.shroggle.presentation.form.filledForms;

import com.shroggle.entity.DraftForm;
import com.shroggle.exception.FormNotFoundException;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.util.ServiceLocator;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class ReloadManageRegistrantsTableService extends ServiceWithExecutePage {

    @RemoteMethod
    public String execute(final ReloadManageRegistrantsTableRequest request) throws Exception {
        final DraftForm form = ServiceLocator.getPersistance().getFormById(request.getFormId());
        if (form == null) {
            throw new FormNotFoundException("Unable to find form by id = " + request.getFormId());
        }
        final ManageFormRecordsTableRequestBuilder builder = new ManageFormRecordsTableRequestBuilder(form,
                request.getFormFilterId(), request.getSearchKey(), request.getSortProperties());
        final ManageFormRecordsTableRequest manageFormRecordsTableRequest = builder.build();
        getContext().getHttpServletRequest().setAttribute("manageFormRecordsTableRequest", manageFormRecordsTableRequest);
        return executePage("/site/form/manageFormRecordsTable.jsp");
    }
}
