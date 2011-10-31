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

package com.shroggle.presentation.site.page;

import com.shroggle.entity.Page;
import com.shroggle.entity.SiteType;
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.logic.accessibility.Right;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.pageversion.PageTitle;
import com.shroggle.logic.site.page.pageversion.PageTitleGetter;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.WithPageVersionTitle;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class, params = {})
public class ConfigureBlueprintPagePermissionsService extends AbstractService implements WithPageVersionTitle {

    @SynchronizeByMethodParameter(
            entityClass = Page.class)
    @RemoteMethod
    public void execute(final int pageId) throws IOException, ServletException {
        final Integer loginUserId = new UsersManager().getLogined().getUserId();

        this.pageId = pageId;

        pageManager = new PageManager(persistance.getPage(pageId));
        if (!Right.isAuthorizedUser(pageManager, loginUserId)) {
            throw new PageNotFoundException("Can't find page version " + pageId);
        }

        if (pageManager.getPage().getSite().getType() != SiteType.BLUEPRINT) {
            throw new PageNotFoundException(
                    "Can't find page version " + pageId + " because it isn't blueprint!");
        }

        pageTitle = new PageTitleGetter(pageManager);

        getContext().getHttpServletRequest().setAttribute("blueprintPagePermissionsService", this);
    }

    public PageTitle getPageTitle() {
        return pageTitle;
    }

    public boolean isRequired() {
        return pageManager.isBlueprintRequired();
    }

    public boolean isNotEditable() {
        return pageManager.isBlueprintNotEditable();
    }

    public boolean isLocked() {
        return pageManager.isBlueprintLocked();
    }

    public int getPageId() {
        return pageId;
    }

    private PageTitle pageTitle;
    private PageManager pageManager;
    private int pageId;
    private final Persistance persistance = ServiceLocator.getPersistance();

}