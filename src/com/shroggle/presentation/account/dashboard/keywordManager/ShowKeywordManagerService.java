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

import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.user.dashboard.keywordManager.KeywordManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class ShowKeywordManagerService extends AbstractService {

    @RemoteMethod
    public String execute(final int siteId) throws IOException, ServletException {
        final UserManager user = new UsersManager().getLogined();

        siteManager = user.getRight().getSiteRight().getSiteForEdit(siteId);

        getContext().getHttpServletRequest().setAttribute("service", this);
        getContext().getHttpServletRequest().setAttribute("siteId", siteId);

        if (siteManager.getFirstSitePage() != null) {
            getContext().getHttpServletRequest().setAttribute("keywordManager",
                    new KeywordManager(siteManager.getFirstSitePage().getPageId()));
            getContext().getHttpServletRequest().setAttribute("pageId", siteManager.getFirstSitePage().getPageId());
            return getContext().forwardToString("/account/dashboard/keywordManager/keywordManager.jsp");
        } else {
            return getContext().forwardToString("/account/dashboard/keywordManager/keywordManagerNoPages.jsp");
        }
    }

    public SiteManager getSiteManager() {
        return siteManager;
    }

    public void setSiteManager(SiteManager siteManager) {
        this.siteManager = siteManager;
    }

    private SiteManager siteManager;

    private final Persistance persistance = ServiceLocator.getPersistance();

}
