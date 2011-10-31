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
import com.shroggle.entity.SiteShowOption;
import com.shroggle.logic.site.page.Doctype;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.PageSelectTextCreator;
import com.shroggle.logic.site.page.PageTreeTextCreator;
import com.shroggle.logic.site.page.pageversion.PageVersionNormalizerReal;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.site.page.SavePageResponse;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemException;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class SavePageHtmlAndCssService {

    @SynchronizeByMethodParameter(
            entityClass = Page.class,
            method = SynchronizeMethod.WRITE)
    @RemoteMethod
    public SavePageResponse execute(final int pageId, final String pageVersionHtml, final String pageVersionThemeCss) {
        final UserManager userManager = new UsersManager().getLogined();
        final PageManager pageManager = userManager.getRight().getSiteRight().getPageForEdit(pageId);

        persistanceTransaction.execute(new Runnable() {

            public void run() {
                pageManager.setChanged(true);
                pageManager.setSaved(true);

                String templateHtml = null;
                try {
                    templateHtml = pageManager.getTemplateHtml();
                } catch (final FileSystemException exception) {
                    // none
                }

                if (templateHtml != null) {
                    if (pageVersionHtml != null && !templateHtml.equals(pageVersionHtml)) {
                        pageManager.setHtml(Doctype.addDoctypeIfNeeded(pageVersionHtml));
                    } else {
                        pageManager.setHtml(null);
                    }

                } else {
                    if (pageVersionHtml != null) {
                        pageManager.setHtml(Doctype.addDoctypeIfNeeded(pageVersionHtml));
                    }
                }

                String templateCss = null;
                try {
                    templateCss = pageManager.getTemplateCss();
                } catch (final FileSystemException exception) {
                    // none
                }

                if (templateCss != null) {
                    if (pageVersionThemeCss != null && !templateCss.equals(pageVersionThemeCss)) {
                        pageManager.setCss(pageVersionThemeCss);
                    } else {
                        pageManager.setCss(null);
                    }
                } else {
                    if (pageVersionThemeCss != null) {
                        pageManager.setCss(pageVersionThemeCss);
                    }
                }

                new PageVersionNormalizerReal().execute(pageManager);
            }

        });

        final SavePageResponse savePageResponse = new SavePageResponse();
        savePageResponse.setTreeHtml(new PageTreeTextCreator().execute(pageManager.getSiteId(), SiteShowOption.getDraftOption()));
        savePageResponse.setPageSelectHtml(new PageSelectTextCreator().execute(pageManager.getSiteId(), SiteShowOption.getDraftOption()));
        return savePageResponse;
    }

    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}