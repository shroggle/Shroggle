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

package com.shroggle.presentation.site;

import com.shroggle.entity.*;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.UserException;
import com.shroggle.logic.site.page.PageDefaultNameManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.pageversion.PageVersionUrlNormalizer;
import com.shroggle.logic.site.template.TemplateManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.ResolutionParameter;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByClassProperty;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Stasuk Artem
 */
@UrlBinding("/site/quicklyCreatePages.action")
public class QuicklyCreatePagesAction extends Action implements LoginedUserInfo {

    @SynchronizeByClassProperty(
            entityIdFieldPath = "siteId",
            entityClass = Site.class)
    @DefaultHandler
    public Resolution view() {
        final UserManager userManager;
        try {
            userManager = new UsersManager().getLogined();
        } catch (final UserException exception) {
            return resolutionCreator.loginInUser(this);
        }
        user = userManager.getUser();

        final Site site;
        try {
            site = userManager.getRight().getSiteRight().getSiteForEdit(siteId).getSite();
        } catch (final SiteNotFoundException exception) {
            return resolutionCreator.redirectToAction(DashboardAction.class);
        }

        if (site.getBlueprintParent() != null) {
            return resolutionCreator.redirectToAction(
                    SiteEditPageAction.class, new ResolutionParameter("siteId", siteId));
        }

        return resolutionCreator.forwardToUrl("/site/quicklyCreatePages.jsp");
    }

    @SynchronizeByClassProperty(
            entityIdFieldPath = "siteId",
            method = SynchronizeMethod.WRITE,
            entityClass = Site.class)
    public Resolution execute() {
        final UserManager userManager;
        final Site site;
        try {
            userManager = new UsersManager().getLogined();
            site = userManager.getRight().getSiteRight().getSiteForEdit(siteId).getSite();
        } catch (final UserException exception) {
            return resolutionCreator.loginInUser(this);
        } catch (final SiteNotFoundException exception) {
            return resolutionCreator.redirectToAction(DashboardAction.class);
        }
        user = userManager.getUser();

        if (site.getBlueprintParent() == null) {
            persistanceTransaction.execute(new Runnable() {

                @Override
                public void run() {
                    final TemplateManager templateManager = new TemplateManager(site.getThemeId().getTemplateDirectory());

                    for (final PageType pageType : pages) {
                        createPageAndAddToSite(templateManager, pageType, site);
                    }
                }

            });
        }
        return resolutionCreator.redirectToAction(
                SiteEditPageAction.class, new ResolutionParameter("siteId", siteId));
    }

    public static PageManager createPageAndAddToSite(
            final TemplateManager templateManager,
            final PageType pageType, final Site site) {
        final PageDefaultNameManager pageDefaultNameManager = new PageDefaultNameManager();
        final String pageName = pageDefaultNameManager.getNextName(site.getSiteId(), pageType.getNamePattern());
        final PageManager pageManager = templateManager.createPage(
                pageType, site, pageName).getPageManager();

        final PageVersionUrlNormalizer pageVersionUrlNormalizer = new PageVersionUrlNormalizer();
        pageVersionUrlNormalizer.execute(pageManager.getDraftPageSettings());
        if (pageManager.getWorkPageSettings() != null) {
            pageVersionUrlNormalizer.execute(pageManager.getWorkPageSettings());
        }

        // Add new pages to default menu
        final MenuItem menuItem = new DraftMenuItem(pageManager.getPage().getPageId(), false, site.getMenu());
        menuItem.setParent(null);
        ServiceLocator.getPersistance().putMenuItem(menuItem);

        return pageManager;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public List<PageType> getPages() {
        return pages;
    }

    public void setPages(List<PageType> pages) {
        this.pages = pages;
    }

    public User getLoginUser() {
        return user;
    }

    private User user;
    private int siteId;
    private List<PageType> pages = new ArrayList<PageType>();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final static List<PageType> deniedPageTypes = PageType.getDeniedPageTypes();
    public final static List<PageType> allowedPageTypes;

    static {
        final List<PageType> tempAllowPageTypes = new ArrayList<PageType>(Arrays.asList(PageType.values()));
        tempAllowPageTypes.removeAll(deniedPageTypes);
        allowedPageTypes = Collections.unmodifiableList(tempAllowPageTypes);
    }

}