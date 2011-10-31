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

import com.shroggle.entity.Site;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.logic.accessibility.UserSiteRightManager;
import com.shroggle.logic.menu.MenuItemsManager;
import com.shroggle.logic.site.page.PageManager;
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
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class, params = {})
public class MovePageService extends AbstractService {

    @SynchronizeByMethodParameter(
            method = SynchronizeMethod.WRITE,
            deepParent = 1,
            entityClass = Site.class)
    @RemoteMethod
    public String execute(final int pageId, final int toPosition, final Integer toParentId) {
        final UserManager userManager = new UsersManager().getLogined();
        final UserSiteRightManager siteRightManager = userManager.getRight().getSiteRight();
        final PageManager pageManager = siteRightManager.getPageForEdit(pageId);

        persistanceTransaction.execute(new Runnable() {

            public void run() {
                MenuItemsManager.moveItemsToNeededPosition(pageManager.getSite().getSiteId(), pageId, toParentId, toPosition);
            }

        });
        return new PageSelectTextCreator().execute(pageManager.getSite().getSiteId(), SiteShowOption.getDraftOption());
    }


    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}