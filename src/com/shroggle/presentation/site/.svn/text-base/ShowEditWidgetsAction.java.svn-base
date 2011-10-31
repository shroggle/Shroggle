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

import com.shroggle.entity.Page;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.entity.User;
import com.shroggle.logic.accessibility.Right;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.site.render.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemException;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByClassProperty;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stasuk Artem
 */
@UrlBinding("/site/editWidgets.action")
public class ShowEditWidgetsAction extends Action {

    @SynchronizeByClassProperty(
            entityClass = Page.class,
            entityIdFieldPath = "pageId")
    @DefaultHandler
    public Resolution view() throws IOException, ServletException {
        final User user = new UsersManager().getLoginedUser();
        final Integer loginUserId = user != null ? user.getUserId() : null;
        final Page page = persistance.getPage(pageId);
        if (page == null) {
            pageVersionHtml = "Page not found!";
        } else if (!Right.isAuthorizedUser(new PageManager(page), loginUserId)) {
            pageVersionHtml = "You are not owner of the page!";
        } else {
            final PageManager pageManager = new PageManager(page);
            final RenderContext context = createRenderContext(true);

            ServiceLocator.getSessionStorage().putCurrentlyViewedPageId(getContext().getRequest().getSession(),
                    pageManager.getPage().getSite().getSiteId(), pageManager.getPage().getPageId());

            try {
                final StringBuilder html = new StringBuilder(pageManager.getSavedHtmlOrDefault());
                final List<Render> renders = new ArrayList<Render>();
                renders.add(new RenderTitle(pageManager, SiteShowOption.getDraftOption()));
                renders.add(new RenderEditWidgets(pageManager));
                renders.add(new RenderEditResources());
                renders.add(new RenderPattern(new RenderPatternFooter(page.getSite(), SiteShowOption.getDraftOption())));
                renders.add(new RenderCssParameter(pageManager, SiteShowOption.getDraftOption()));
                renders.add(new RenderTheme(pageManager, SiteShowOption.getDraftOption()));
                renders.add(new RenderResourcesPath("/site/templates", pageManager));
                new RenderComposit(renders).execute(context, html);
                pageVersionHtml = html.toString();
            } catch (FileSystemException exception) {
                exception.printStackTrace();
                return resolutionCreator.forwardToUrl("/site/showEditPageWithIncorrectLayout.jsp");
            }
        }
        return resolutionCreator.forwardToUrl("/site/showEditPage.jsp");
    }

    public String getPageVersionHtml() {
        return pageVersionHtml;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private String pageVersionHtml;
    private int pageId;

}