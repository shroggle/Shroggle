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

package com.shroggle.presentation.forum;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.presentation.AbstractService;

@RemoteProxy
public class ShowSubForumService extends AbstractService{

    @RemoteMethod
    public String execute(final int subForumId, final int widgetId, final boolean isShowOnUserPages)
            throws ServletException, IOException {
        final WebContext webContext = getContext();
        if (webContext != null) {
            return webContext.forwardToString("/forum/showSubForum.action?" +
                "subForumId=" + subForumId +
                "&widgetId=" + widgetId + "&isShowOnUserPages=" + isShowOnUserPages);
        } else {
            return "/forum/showSubForum.action?" +
                "subForumId=" + subForumId +
                "&widgetId=" + widgetId + "&isShowOnUserPages=" + isShowOnUserPages;
        }
    }

}
