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

import com.shroggle.entity.KeywordsGroup;
import com.shroggle.entity.Site;
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.logic.accessibility.Right;
import com.shroggle.logic.site.page.PageCreator;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.pageversion.PageTitle;
import com.shroggle.logic.site.page.pageversion.PageTitleGetter;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.presentation.site.WithPageVersionTitle;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * @author Stasuk Artem, dmitry.solomadin
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class PageToEditGetterService extends ServiceWithExecutePage implements WithPageVersionTitle {

    @SynchronizeByMethodParameter(entityClass = Site.class)
    @RemoteMethod
    public void execute(final int siteId, final int pageId) throws IOException, ServletException {
        userManager = new UsersManager().getLogined();
        site = userManager.getRight().getSiteRight().getSiteForEdit(siteId).getSite();

        pageToEdit = new PageManager(persistance.getPage(pageId));
        if (!Right.isAuthorizedUser(pageToEdit, userManager.getUserId())) {
            throw new PageNotFoundException("Can't found page version " + pageId);
        }

        pageTitle = new PageTitleGetter(pageToEdit);

        siteUrlPrefix = site.getSubDomain();
        keywordsGroups = site.getKeywordsGroups();

        getContext().getHttpServletRequest().setAttribute("pageToEditGetterService", this);
    }

    public PageTitle getPageTitle() {
        return pageTitle;
    }

    public Site getSite() {
        return site;
    }

    public String getSiteUrlPrefix() {
        return StringUtil.isNullOrEmpty(siteUrlPrefix) ? international.get("siteDomain") : siteUrlPrefix;
    }

    public List<KeywordsGroup> getKeywordsGroups() {
        return keywordsGroups;
    }

    public PageManager getPageToEdit() {
        return pageToEdit;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    private String siteUrlPrefix;
    private PageTitle pageTitle;
    private Site site;
    private PageManager pageToEdit;
    private List<KeywordsGroup> keywordsGroups;
    private UserManager userManager;
    private final International international = ServiceLocator.getInternationStorage().get("configurePageName", Locale.US);
    private final Persistance persistance = ServiceLocator.getPersistance();

}