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
import com.shroggle.entity.PageVersionType;
import com.shroggle.entity.Site;
import com.shroggle.logic.site.item.ItemPoster;
import com.shroggle.logic.site.item.ItemPosterCache;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.PagesWithoutSystem;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author Stasuk Artem
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class CopyPageVersionService extends AbstractService {

    @RemoteMethod
    public void execute(final Integer selectedPageId, final PageVersionType needPageVersionType, final Integer siteId) {
        final Site site = persistance.getSite(siteId);
        if (site != null) {
            for (Page page : PagesWithoutSystem.get(site.getPages())) {
                executeInternal(page.getPageId(), needPageVersionType);
            }
        } else {
            if (selectedPageId == null){
                throw new IllegalArgumentException("selectedPageId or siteId should be not null");
            }

            executeInternal(selectedPageId, needPageVersionType);
        }
    }

    //todo: Where are tests?! Write tests on real and mock db!
    @SynchronizeByMethodParameter(entityClass = Page.class, method = SynchronizeMethod.WRITE)
    private void executeInternal(final int pageId, final PageVersionType needPageVersionType) {
        final UserManager userManager = new UsersManager().getLogined();
        final PageManager pageManager = userManager.getRight().getSiteRight().getPageForEdit(pageId);
        pageManager.setItemPoster(itemPoster);
        persistanceTransaction.execute(new Runnable() {
            public void run() {
                if (needPageVersionType == PageVersionType.WORK) {
                    pageManager.publish();
                } else {
                    pageManager.resetChanges();
                }
            }
        });
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final ItemPoster itemPoster = new ItemPosterCache();
}