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
package com.shroggle.presentation.account.items;

import com.shroggle.entity.DraftItem;
import com.shroggle.entity.Site;
import com.shroggle.exception.SiteItemNotFoundException;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.PersistanceTransactionContext;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.List;

/**
 * @author Artem Stasuk
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class ShareUserItemService extends AbstractService {

    @RemoteMethod
    public void execute(final List<Integer> targetSiteIds, final List<ShowShareUserItem> items) {
        final UserManager userManager = new UsersManager().getLogined();

        persistanceTransaction.execute(new PersistanceTransactionContext<Void>() {

            public Void execute() {
                for (final ShowShareUserItem item : items) {
                    if (item.getShareType() == null) continue;

                    for (final Integer targetSiteId : targetSiteIds) {
                        try {
                            final Site targetSite = userManager.getRight().getSiteRight().getSiteForView(targetSiteId).getSite();
                            final DraftItem siteItem = userManager.getSiteItemForEditById(
                                    item.getItemId(), item.getItemType());

                            new ItemManager(siteItem).share(targetSite, item.getShareType());
                        } catch (final SiteNotFoundException exception) {
                            // None
                        } catch (final SiteItemNotFoundException exception) {
                            // None
                        }
                    }
                }
                return null;
            }

        });
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}