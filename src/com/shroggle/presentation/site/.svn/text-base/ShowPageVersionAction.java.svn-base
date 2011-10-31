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
import com.shroggle.entity.SiteType;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.site.render.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.Context;
import com.shroggle.util.filesystem.FileSystemException;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByClassProperties;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByClassProperty;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stasuk Artem
 */
@UrlBinding("/site/showPageVersion.action")
public class ShowPageVersionAction extends Action {

    @SynchronizeByClassProperties({
            @SynchronizeByClassProperty(
                    entityIdFieldPath = "pageId",
                    entityClass = Page.class)})
    public Resolution execute() throws ServletException, IOException {
        // Here we getting same pageVersion if it`s accessible for render otherwise login page version.
        final PageManager pageManager = PageManager.getPageForRenderOrLoginPage(pageId, siteShowOption);

        if (pageManager == null) {
            return resolutionCreator.forwardToUrl("/site/pageNotFound.jsp");
        }

        if (pageManager.getSite().getType() == SiteType.BLUEPRINT && getContext().getRequest().getParameter("fromDashboard") != null) {
            return resolutionCreator.redirectToUrl("/site/siteUnderConstruction.action?siteUrl=" +
                    pageManager.getSite().getTitle() + "&siteShowOption=" + SiteShowOption.getWorkOption());
        }

        try {
            pageVersionHtml = renderPageHtml(pageManager);
        } catch (final FileSystemException exception) {
            return resolutionCreator.forwardToUrl("showPageVersionWithIncorrectLayout.jsp");
        }

        return resolutionCreator.forwardToUrl("/site/showPage.jsp");
    }

    public String renderPageHtml(final PageManager pageManager) throws IOException, ServletException, FileSystemException {
        context.setSiteId(pageManager.getSiteId());

        final RenderContext context = createRenderContext(false);

        final StringBuilder html = new StringBuilder(pageManager.getSavedHtmlOrDefault());

        final List<Render> renders = new ArrayList<Render>();
        renders.add(new RenderWidgets(pageManager, siteShowOption));
        // If page has restricted access we are showing login page instead, but this login page must has an original title.
        // Whats why I`m explicitly getting an original page with it`s original title from the DB and passing it to the
        // RenderTitle class. Tolik
        renders.add(new RenderTitle(new PageManager(persistance.getPage(pageId), siteShowOption), siteShowOption));
        renders.add(new RenderResources(siteShowOption));
        renders.add(new RenderPattern(new RenderPatternFooter(pageManager.getSite(), siteShowOption)));
        //If we are viewing forum/blog from inside app we do not need theme .css files.
        if (siteShowOption != SiteShowOption.INSIDE_APP) {
            renders.add(new RenderTheme(pageManager, siteShowOption));
        }
        renders.add(new RenderGeneratorMetaTag());
        renders.add(new RenderIcon(pageManager));
        renders.add(new RenderKeywordsMetaTags(pageManager, siteShowOption));
        renders.add(new RenderDescriptionMetaTag(pageManager, siteShowOption));
        renders.add(new RenderAuthorMetaTag(pageManager));
        renders.add(new RenderCopyrightMetaTag(pageManager));
        renders.add(new RenderRobotsMetaTag(pageManager));
        renders.add(new RenderTitleMetaTag(pageManager, siteShowOption));
        renders.add(new RenderCustomMetaTags(pageManager));
        renders.add(new RenderCustomHTMLCode(pageManager));
        renders.add(new RenderCssParameter(pageManager, siteShowOption));
        renders.add(new RenderVisitorTracker(pageManager));
        renders.add(new RenderResourcesPath("/site/templates", pageManager));
        new RenderComposit(renders).execute(context, html);

        return html.toString();
    }

    public String getPageVersionHtml() {
        return pageVersionHtml;
    }

    public void setPageVersionHtml(String pageVersionHtml) {
        this.pageVersionHtml = pageVersionHtml;
    }

    public void setSiteShowOption(SiteShowOption siteShowOption) {
        this.siteShowOption = siteShowOption;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final Context context = ServiceLocator.getContextStorage().get();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private SiteShowOption siteShowOption;
    private Integer pageId;
    private String pageVersionHtml;

}