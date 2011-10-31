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
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.User;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * @author Artem Stasuk
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class ShowShareUserItemService extends ServiceWithExecutePage {

    @RemoteMethod
    public String execute(final List<ShowShareUserItem> items)
            throws IOException, ServletException {
        final User user = new UsersManager().getLoginedUser();
        final int loginUserId = user != null ? user.getUserId() : -1;

        this.items = items;
        sites = persistance.getSites(loginUserId, SiteAccessLevel.getUserAccessLevels());
        final Iterator<ShowShareUserItem> itemsIterator = items.iterator();
        while (itemsIterator.hasNext()) {
            final ShowShareUserItem item = itemsIterator.next();
            final DraftItem siteItem = persistance.getDraftItem(
                    item.getItemId());
            if (siteItem == null) {
                itemsIterator.remove();
            } else {
                final Site itemSite = persistance.getSite(siteItem.getSiteId());
                item.setItemName(siteItem.getName());
                item.setItemSiteName(itemSite.getTitle());
            }
        }

        return executePage("/user/showShareUserItemAccess.jsp");
    }

    public List<ShowShareUserItem> getItems() {
        return items;
    }

    public List<Site> getSites() {
        return sites;
    }

    private List<Site> sites;
    private List<ShowShareUserItem> items;
    private final Persistance persistance = ServiceLocator.getPersistance();

}