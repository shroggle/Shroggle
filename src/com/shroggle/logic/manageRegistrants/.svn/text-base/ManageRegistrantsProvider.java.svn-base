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
package com.shroggle.logic.manageRegistrants;

import com.shroggle.entity.RegistrantFilterType;
import com.shroggle.entity.User;
import com.shroggle.logic.paginator.Paginator;
import com.shroggle.logic.visitor.SearchVisitorManager;
import com.shroggle.logic.visitor.VisitorInfoGetter;
import com.shroggle.presentation.manageRegistrants.ShowManageRegistrantsRequest;
import com.shroggle.presentation.site.RegisteredVisitorInfo;

import java.util.List;

/**
 * @author dmitry.solomadin, Balakirev Anatoliy
 */
public class ManageRegistrantsProvider {

    public Paginator executeWithBlankRequest(final int siteId) {
        final ShowManageRegistrantsRequest request = new ShowManageRegistrantsRequest();
        request.setSearchKey("");
        request.setStatus(RegistrantFilterType.SHOW_ALL);
        request.setSiteId(siteId);
        request.setSortType(ManageRegistrantsSortType.FIRST_NAME);
        request.setDesc(false);
        return execute(request, Paginator.getFirstPageNumber());
    }

    public Paginator execute(final ShowManageRegistrantsRequest request, Integer pageNumber) {
        // First, searching users by search key and status.
        final List<User> registrantsListRaw = new SearchVisitorManager().searchVisitorsByStatusAndSearchKey(
                request.getStatus(), request.getSearchKey(), request.getSiteId());

        // Then converting users info to a suitable format.
        final List<RegisteredVisitorInfo> unsortedRegistrantsList =
                new VisitorInfoGetter().execute(registrantsListRaw, request.getSiteId());

        // Then sorting them.
        final List<RegisteredVisitorInfo> sortedRegistrantsList =
                new ManageRegistrantsSorter().execute(unsortedRegistrantsList, request.getSortType(), request.isDesc());

        // Then paginating sorted list.
        return new Paginator<RegisteredVisitorInfo>(sortedRegistrantsList).setPageNumber(pageNumber);
    }

}
