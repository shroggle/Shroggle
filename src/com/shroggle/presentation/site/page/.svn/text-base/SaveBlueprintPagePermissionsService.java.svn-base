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
import com.shroggle.entity.SiteShowOption;
import com.shroggle.entity.SiteType;
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.logic.accessibility.Right;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.PageSelectTextCreator;
import com.shroggle.logic.site.page.PageTreeTextCreator;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.page.SavePageResponse;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
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
public class SaveBlueprintPagePermissionsService extends AbstractService {

    @SynchronizeByMethodParameter(
            method = SynchronizeMethod.WRITE,
            entityClass = Page.class)
    @RemoteMethod
    public SavePageResponse execute(final int pageId, final boolean required, final boolean notEditable, final boolean locked) {
        final Integer loginUserId = new UsersManager().getLogined().getUserId();

        final PageManager pageManager = new PageManager(persistance.getPage(pageId));
        if (!Right.isAuthorizedUser(pageManager, loginUserId)) {
            throw new PageNotFoundException("Can't find page version " + pageId);
        }

        if (pageManager.getPage().getSite().getType() != SiteType.BLUEPRINT) {
            throw new PageNotFoundException(
                    "Can't find page version " + pageId + " because it isn't blueprint!");
        }

        persistanceTransaction.execute(new Runnable() {

            public void run() {
                pageManager.setBlueprintRequired(required);
                pageManager.setBlueprintNotEditable(notEditable);
                pageManager.setBlueprintLocked(locked);

                pageManager.setChanged(true);
            }

        });

        final SavePageResponse response = new SavePageResponse();
        response.setTreeHtml(new PageTreeTextCreator().execute(pageManager.getPage().getSite().getId(), SiteShowOption.getDraftOption()));
        response.setPageSelectHtml(new PageSelectTextCreator().execute(pageManager.getPage().getSite().getId(), SiteShowOption.getDraftOption()));

        return response;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}