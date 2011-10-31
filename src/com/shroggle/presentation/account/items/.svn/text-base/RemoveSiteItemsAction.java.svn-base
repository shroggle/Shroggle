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

import com.shroggle.entity.*;
import com.shroggle.exception.SiteItemNotFoundException;
import com.shroggle.exception.UserException;
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.user.items.UserItemsSortType;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.ResolutionParameter;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.PersistanceTransactionContext;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByLoginUser;
import com.shroggle.util.security.SecurityUser;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Artem Stasuk
 */
@UrlBinding(value = "/user/removeSiteItems.action")
public class RemoveSiteItemsAction extends Action {

    @SynchronizeByLoginUser
    @SecurityUser
    @DefaultHandler
    public Resolution execute() {
        if (itemType == null) {
            return resolutionCreator.redirectToAction(DashboardAction.class);
        }

        final AtomicInteger success = new AtomicInteger();
        final List<String> failedToRemoveItemsMessages = new ArrayList<String>();
        if (itemTypeByIds.size() > 0) {
            try {
                final UserManager userManager = new UsersManager().getLogined();
                persistanceTransaction.execute(new PersistanceTransactionContext<Void>() {

                    public Void execute() {
                        for (final Integer itemId : itemTypeByIds.keySet()) {
                            final DraftItem siteItem;
                            try {
                                siteItem = userManager.getSiteItemForEditById(itemId, itemTypeByIds.get(itemId));
                            } catch (final SiteItemNotFoundException exception) {
                                final DraftItem item = persistance.getDraftItem(itemId);
                                failedToRemoveItemsMessages.add("Failed to remove item '" + item.getName() +
                                        "' because you have read-only access to this item.");
                                break;
                            }

                            if (siteItem == null) {
                                continue;
                            }

                            if (siteItem.getItemType() == ItemType.CHILD_SITE_REGISTRATION
                                    && persistance.getChildSiteSettingsCountByRegistrationId(itemId) != 0) {
                                failedToRemoveItemsMessages.add("Failed to remove child site registration '" +
                                        siteItem.getName() + "' because it has child sites.");
                                continue;
                            }

                            if (siteItem.getItemType() == ItemType.MENU) {
                                final Site site = persistance.getSite(siteItem.getSiteId());
                                if (site != null && site.getMenu().getId() == siteItem.getId()) {
                                    failedToRemoveItemsMessages.add("Failed to remove menu '" +
                                            siteItem.getName() + "' because it's a default menu for site.");
                                    continue;
                                }
                            }

                            if (siteItem.getItemType() == ItemType.REGISTRATION && persistance.isRegistrationUsedOnAnySiteAsDefault(itemId)) {
                                failedToRemoveItemsMessages.add("Failed to remove registration form '" +
                                        siteItem.getName() + "' because it is default registration form." +
                                        " To delete it you should set some other form as default.");
                                continue;
                            }

                            //Removing item.
                            if (siteItem instanceof Form) {
                                new FormManager().remove((Form) siteItem);
                            } else {
                                persistance.removeDraftItem(siteItem);
                            }
                            success.incrementAndGet();
                        }
                        return null;
                    }

                });
            } catch (final UserException exception) {
                // None
            }
        }

        return resolutionCreator.redirectToAction(
                ManageItemsAction.class,
                new ResolutionParameter("itemType", itemType),
                new ResolutionParameter("fromRemoveItems", true),
                new ResolutionParameter("successRemovedItems", success.get()),
                new ResolutionParameter("failedToRemoveItemsMessages", failedToRemoveItemsMessages),

                new ResolutionParameter("presetFilterByOwnerSiteId", filterByOwnerSiteId),
                new ResolutionParameter("sortType", sortType),
                new ResolutionParameter("descending", descending),
                new ResolutionParameter("pageNumber", pageNumber),
                new ResolutionParameter("searchKeyByItemName", searchKeyByItemName)
        );
    }

    public void setItemTypeByIds(final Map<Integer, ItemType> itemTypeByIds) {
        this.itemTypeByIds = itemTypeByIds;
    }

    public Map<Integer, ItemType> getItemTypeByIds() {
        return itemTypeByIds;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public Integer getFilterByOwnerSiteId() {
        return filterByOwnerSiteId;
    }

    public void setFilterByOwnerSiteId(Integer filterByOwnerSiteId) {
        this.filterByOwnerSiteId = filterByOwnerSiteId;
    }

    public UserItemsSortType getSortType() {
        return sortType;
    }

    public void setSortType(UserItemsSortType sortType) {
        this.sortType = sortType;
    }

    public Boolean getDescending() {
        return descending;
    }

    public void setDescending(Boolean descending) {
        this.descending = descending;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getSearchKeyByItemName() {
        return searchKeyByItemName;
    }

    public void setSearchKeyByItemName(String searchKeyByItemName) {
        this.searchKeyByItemName = searchKeyByItemName;
    }

    private ItemType itemType = ItemType.ALL_ITEMS;
    private Integer filterByOwnerSiteId;
    private UserItemsSortType sortType;
    private Boolean descending;
    private Integer pageNumber = 1;
    private String searchKeyByItemName;

    private Map<Integer, ItemType> itemTypeByIds = new HashMap<Integer, ItemType>();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}