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
import com.shroggle.exception.LayoutNotFoundException;
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.exception.ThemeNotFoundException;
import com.shroggle.logic.accessibility.Right;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.PageSelectTextCreator;
import com.shroggle.logic.site.page.PageTreeTextCreator;
import com.shroggle.logic.site.page.pageversion.PageVersionNormalizerReal;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class, params = {})
public class SetPageLayoutService extends AbstractService {

    @SynchronizeByMethodParameter(
            method = SynchronizeMethod.WRITE,
            entityClass = Page.class)
    @RemoteMethod
    public SavePageResponse execute(final int pageId, final String layoutFile, final String themeFile) {
        final Integer loginUserId = new UsersManager().getLogined().getUserId();

        final PageManager pageManager = new PageManager(persistance.getPage(pageId));
        if (!Right.isAuthorizedUser(pageManager, loginUserId)) {
            throw new PageNotFoundException("Can't find page " + pageId);
        }

        final Template template = fileSystem.getTemplateByDirectory(
                pageManager.getRealThemeId().getTemplateDirectory());
        boolean findLayout = false;
        for (final Layout layout : template.getLayouts()) {
            if (layout.getFile().equals(layoutFile)) {
                findLayout = true;
                break;
            }
        }

        if (!findLayout) {
            throw new LayoutNotFoundException("Can't find layout: " + layoutFile);
        }

        if (themeFile != null) {
            boolean findTheme = false;
            for (final Theme theme : template.getThemes()) {
                if (theme.getFile().equals(themeFile)) {
                    findTheme = true;
                }
            }

            if (!findTheme) {
                throw new ThemeNotFoundException(
                        "Can't find theme " + themeFile + " for template " + template.getDirectory());
            }
        }

        persistanceTransaction.execute(new Runnable() {

            public void run() {
                pageManager.setLayoutFile(layoutFile);
                if (themeFile != null) {
                    final ThemeId siteThemeId = pageManager.getPage().getSite().getThemeId();
                    pageManager.setThemeId(new ThemeId(siteThemeId.getTemplateDirectory(), themeFile));
                }
                pageManager.setChanged(true);
                pageManager.setSaved(true);
                pageManager.setHtml(null);
                new PageVersionNormalizerReal().execute(pageManager);
            }

        });
        
        final SavePageResponse savePageResponse = new SavePageResponse();
        savePageResponse.setTreeHtml(new PageTreeTextCreator().execute(pageManager.getSiteId(), SiteShowOption.getDraftOption()));
        savePageResponse.setPageSelectHtml(new PageSelectTextCreator().execute(pageManager.getSiteId(), SiteShowOption.getDraftOption()));
        return savePageResponse;
    }

    private final FileSystem fileSystem = ServiceLocator.getFileSystem();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}