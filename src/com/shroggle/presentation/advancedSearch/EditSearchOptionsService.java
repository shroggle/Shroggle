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

package com.shroggle.presentation.advancedSearch;

import com.shroggle.entity.*;
import com.shroggle.exception.AdvancedSearchNotFoundException;
import com.shroggle.exception.FormNotFoundException;
import com.shroggle.logic.advancedSearch.AdvancedSearchHelper;
import com.shroggle.logic.form.FormItemsManager;
import com.shroggle.logic.form.FormLogic;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameters;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class, params = {})
public class EditSearchOptionsService extends ServiceWithExecutePage {

    @SynchronizeByMethodParameters({
            @SynchronizeByMethodParameter(entityClass = DraftForm.class)
    })
    @RemoteMethod
    public String show(final EditSearchOptionsRequest request) throws Exception {
        final UserManager userManager = new UsersManager().getLogined();

        final List<DraftAdvancedSearchOption> searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        if (request.getAdvancedSearchId() != null) {
            final DraftAdvancedSearch advancedSearch = persistance.getDraftItem(request.getAdvancedSearchId());

            if (advancedSearch == null) {
                throw new AdvancedSearchNotFoundException("Cannot find advanced search by Id=" + request.getAdvancedSearchId());
            }

            searchOptions.addAll(advancedSearch.getAdvancedSearchOptions());
        }

        if (request.getNewSearchOptions() != null) {
            searchOptions.addAll(request.getNewSearchOptions());
        }

        if (request.getFormId() != null) {
            final DraftForm selectedForm = persistance.getFormById(request.getFormId());

            if (selectedForm == null) {
                throw new FormNotFoundException("Cannot find form by Id=" + request.getFormId());
            }

            formId = request.getFormId();
            selectedFormItems = FormItemsManager.getCorrectFormItemsForAdvancedSearch(selectedForm.getDraftFormItems());
        } else {
            final FormLogic formLogic =
                    new AdvancedSearchHelper().createDefaultSearchForm(request.getSiteId(), userManager);
            formId = formLogic.getId();
            selectedFormItems = FormItemsManager.getCorrectFormItemsForAdvancedSearch(formLogic.getItems());
        }


        getContext().getHttpServletRequest().setAttribute("searchOptions", searchOptions);
        getContext().getHttpServletRequest().setAttribute("siteId", request.getSiteId());
        getContext().getHttpServletRequest().setAttribute("formId", formId);
        return executePage("/advancedSearch/editSearchOptions.jsp");
    }

    public List<FormItem> getSelectedFormItems() {
        return selectedFormItems;
    }

    public Integer getFormId() {
        return formId;
    }

    private Integer formId;
    private List<FormItem> selectedFormItems = new ArrayList<FormItem>();
    private Persistance persistance = ServiceLocator.getPersistance();

}
