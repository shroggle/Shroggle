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
package com.shroggle.presentation.account.items;

import com.shroggle.logic.paginator.Paginator;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.user.items.UserItemsProvider;
import com.shroggle.presentation.ServiceWithExecutePage;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class ManageItemsService extends ServiceWithExecutePage {

    @RemoteMethod
    public String execute(final ManageItemsRequest request) throws IOException, ServletException {
        new UsersManager().getLogined();

        final Paginator paginator = new UserItemsProvider().executeWithPaginator(request.getItemType(),
                request.getSortType(), request.isDESC(),
                request.getFilterByOwnerSiteId(), request.getPageNumber(), request.getSearchKeyByItemName());

        getContext().getHttpServletRequest().setAttribute("paginator", paginator);
        getContext().getHttpServletRequest().setAttribute("descending", request.isDESC());        
        getContext().getHttpServletRequest().setAttribute("itemType", request.getItemType());
        getContext().getHttpServletRequest().setAttribute("userItemsSortType", request.getSortType());

        return executePage("/user/manageItemsList.jsp");
    }
}
