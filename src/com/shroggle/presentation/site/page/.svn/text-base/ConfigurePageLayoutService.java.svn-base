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

import com.shroggle.entity.*;
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.logic.accessibility.Right;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.pageversion.PageTitle;
import com.shroggle.logic.site.page.pageversion.PageTitleGetter;
import com.shroggle.logic.site.template.TemplateManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.WithPageVersionTitle;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.*;

@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class, params = {})
public class ConfigurePageLayoutService extends AbstractService implements WithPageVersionTitle {

    @SynchronizeByMethodParameter(
            entityClass = Page.class)
    @RemoteMethod
    public void execute(final int pageId) throws IOException, ServletException {
        final Integer loginUserId = new UsersManager().getLogined().getUserId();

        final PageManager pageManager = new PageManager(persistance.getPage(pageId));
        if (!Right.isAuthorizedUser(pageManager, loginUserId)) {
            throw new PageNotFoundException("Can't find page version " + pageId);
        }

        this.pageId = pageId;

        final ThemeId themeId = pageManager.getRealThemeId();
        selectThemeFile = themeId.getThemeCss();
        final TemplateManager templateManager = new TemplateManager(themeId.getTemplateDirectory());

        themes = templateManager.getTemplate().getThemes();
        themeColorTiles = templateManager.getThemeColorTileUrls();

        layouts = new ArrayList<Layout>(templateManager.getTemplate().getLayouts());
        layoutThumbnails = new HashMap<Layout, String>();
        pageTitle = new PageTitleGetter(pageManager);
        selectLayoutFile = pageManager.getLayoutFile();

        for (final Layout layout : layouts) {
            layoutThumbnails.put(layout, fileSystem.getLayoutThumbnailPath(layout));
        }

        Collections.sort(layouts, new Comparator<Layout>() {

            public int compare(Layout o1, Layout o2) {
                return o1.getName().compareTo(o2.getName());
            }

        });

        getContext().getHttpServletRequest().setAttribute("pageLayoutService", this);
    }

    public Map<Layout, String> getLayoutThumbnails() {
        return layoutThumbnails;
    }

    public List<Layout> getLayouts() {
        return layouts;
    }

    public String getSelectLayoutFile() {
        return selectLayoutFile;
    }

    public PageTitle getPageTitle() {
        return pageTitle;
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public Map<Theme, String> getThemeColorTiles() {
        return themeColorTiles;
    }

    public String getSelectThemeFile() {
        return selectThemeFile;
    }

    public int getPageId() {
        return pageId;
    }

    private final FileSystem fileSystem = ServiceLocator.getFileSystem();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private String selectLayoutFile;
    private PageTitle pageTitle;
    private Map<Layout, String> layoutThumbnails;
    private List<Layout> layouts;
    private List<Theme> themes;
    private String selectThemeFile;
    private Map<Theme, String> themeColorTiles;
    private int pageId;

}