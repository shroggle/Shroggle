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
package com.shroggle.presentation.account.dashboard.keywordManager;

import com.shroggle.entity.Page;
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.user.dashboard.keywordManager.KeywordManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class ShowKeywordManagerTableService extends AbstractService {

    @RemoteMethod
    public String execute(final int pageId) throws IOException, ServletException {
        new UsersManager().getLogined();

        final Page page = ServiceLocator.getPersistance().getPage(pageId);

        if (page == null) {
            throw new PageNotFoundException("Cannot find page by Id=" + pageId);
        }

        final KeywordManager keywordManager = new KeywordManager(pageId);

        getContext().getHttpServletRequest().setAttribute("pageId", pageId);
        getContext().getHttpServletRequest().setAttribute("siteId", page.getSite().getSiteId());
        getContext().getHttpServletRequest().setAttribute("keywordManager", keywordManager);
        return getContext().forwardToString("/account/dashboard/keywordManager/keywordManagerTable.jsp");
    }

}
