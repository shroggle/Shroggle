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

package com.shroggle.presentation.site.htmlAndCss;

import com.shroggle.entity.Page;
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.pageversion.PageTitle;
import com.shroggle.logic.site.page.pageversion.PageTitleGetter;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.presentation.site.WithPageVersionTitle;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class ConfigurePageHtmlAndCssService extends AbstractService implements WithPageVersionTitle {

    @SynchronizeByMethodParameter(
            entityClass = Page.class)
    @RemoteMethod
    public void execute(final int pageId) throws Exception {
        this.pageId = pageId;
        final UserManager userManager = new UsersManager().getLogined();
        if (ServiceLocator.getPersistance().getPage(pageId) == null) {
            throw new PageNotFoundException();
        }
        final PageManager pageManager = userManager.getRight().getSiteRight().getPageForEdit(pageId);

        pageTitle = new PageTitleGetter(pageManager);
        pageVersionHtml = pageManager.getSavedHtmlOrDefault();
        pageVersionThemeCss = pageManager.getSavedCssOrDefault();

        getContext().getHttpServletRequest().setAttribute("pageHtmlAndCssService", this);
    }

    public PageTitle getPageTitle() {
        return pageTitle;
    }

    public String getPageVersionThemeCss() {
        return pageVersionThemeCss;
    }

    public String getPageVersionHtml() {
        return pageVersionHtml;
    }

    public int getPageId() {
        return pageId;
    }

    private PageTitle pageTitle;
    private int pageId;
    private String pageVersionHtml;
    private String pageVersionThemeCss;

}