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

import com.shroggle.entity.DraftAdvancedSearch;
import com.shroggle.entity.DraftAdvancedSearchOption;
import com.shroggle.exception.AdvancedSearchNotFoundException;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.util.ServiceLocator;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class UpdateSearchOptionsListOnPageService extends ServiceWithExecutePage {

    @RemoteMethod
    public String execute(final EditSearchOptionsRequest request) throws IOException, ServletException {
        new UsersManager().getLogined();

        final List<DraftAdvancedSearchOption> searchOptions = new ArrayList<DraftAdvancedSearchOption>();
        if (request.getAdvancedSearchId() != null) {
            final DraftAdvancedSearch advancedSearch = ServiceLocator.getPersistance().getDraftItem(request.getAdvancedSearchId());

            if (advancedSearch == null) {
                throw new AdvancedSearchNotFoundException("Cannot find advanced search by Id=" + request.getAdvancedSearchId());
            }

            searchOptions.addAll(advancedSearch.getAdvancedSearchOptions());
        }

        searchOptions.addAll(request.getNewSearchOptions());

        getContext().getHttpServletRequest().setAttribute("searchOptions", searchOptions);
        getContext().getHttpServletRequest().setAttribute("siteId", request.getSiteId());
        getContext().getHttpServletRequest().setAttribute("formId", request.getFormId());
        return executePage("/advancedSearch/editSearchOptionsList.jsp");
    }
}
