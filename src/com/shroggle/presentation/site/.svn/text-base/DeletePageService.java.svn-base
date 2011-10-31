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
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.PageTreeTextCreator;
import com.shroggle.logic.site.page.PageSelectTextCreator;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
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
// todo. add tests.
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class DeletePageService extends AbstractService {

    /**
     * Please, attention to this method. Delete page from db but do not delete it
     * from this session site, because hibernate thinks that we persisting page.
     *
     * @param pageId - page id
     * @return treeHtml
     */
    @SynchronizeByMethodParameter(
            entityClass = Page.class,
            method = SynchronizeMethod.WRITE)
    @RemoteMethod
    public DeletePageResponse execute(final int pageId) {
        final UserManager userManager = new UsersManager().getLogined();
        final PageManager pageManager = userManager.getRight().getSiteRight().getPageForEdit(pageId);

        persistanceTransaction.execute(new Runnable() {

            public void run() {
                pageManager.remove();
            }

        });

        final DeletePageResponse deletePageResponse = new DeletePageResponse();
        deletePageResponse.setTreeHtml(new PageTreeTextCreator().execute(pageManager.getSite().getSiteId(), SiteShowOption.getDraftOption()));
        deletePageResponse.setSelectHtml(new PageSelectTextCreator().execute(pageManager.getSite().getSiteId(), SiteShowOption.getDraftOption()));

        return deletePageResponse;
    }

    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}