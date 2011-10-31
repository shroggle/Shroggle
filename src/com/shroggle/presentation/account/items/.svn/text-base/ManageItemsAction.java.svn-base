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

import com.shroggle.entity.ItemType;
import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.User;
import com.shroggle.exception.UserException;
import com.shroggle.logic.paginator.Paginator;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.user.items.UserItemsProvider;
import com.shroggle.logic.user.items.UserItemsSortType;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.site.LoginedUserInfo;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.*;

/**
 * @author Artem Stasuk
 */
@UrlBinding(value = "/user/manageItems.action")
public class ManageItemsAction extends Action implements LoginedUserInfo {

    @DefaultHandler
    public Resolution execute() {
        if (itemType == null) {
            return resolutionCreator.redirectToAction(DashboardAction.class);
        }

        final UserManager userManager;
        try {
            userManager = new UsersManager().getLogined();
        } catch (final UserException exception) {
            return resolutionCreator.loginInUser(this);
        }
        user = userManager.getUser();

        sortType = sortType != null ? sortType : UserItemsSortType.NAME;
        descending = descending != null ? descending : false;
        final Paginator paginator = new UserItemsProvider().executeWithPaginator(itemType, sortType, descending,
                presetFilterByOwnerSiteId, pageNumber, searchKeyByItemName);

        sites = persistance.getSites(userManager.getUserId(), SiteAccessLevel.getUserAccessLevels());

        showRequestContentButton = !sites.isEmpty();
        showAddButton = !sites.isEmpty();

        getContext().getRequest().setAttribute("paginator", paginator);
        getContext().getRequest().setAttribute("itemType", itemType);
        getContext().getRequest().setAttribute("userItemsSortType", sortType);
        getContext().getRequest().setAttribute("descending", descending);

        return resolutionCreator.forwardToUrl("/user/manageItems.jsp");
    }

    public static List<String> getFilterByItemTypesNames() {
        final List<String> names = new ArrayList<String>();
        for (ItemType itemType : ItemType.values()) {
            if (itemType == ItemType.COMPOSIT) {
                continue;
            }

            String itemName = international.get(itemType.toString());

            names.add(itemName);
            itemNames.put(itemName, itemType);
        }

        Collections.sort(names);

        return names;
    }

    public static ItemType getItemTypeByItemName(final String itemName){
        return itemNames.get(itemName);
    }

    public User getLoginUser() {
        return user;
    }

    public String getInfoText() {
        return infoText;
    }

    public void setInfoText(final String infoText) {
        this.infoText = infoText;
    }

    public boolean isShowAddButton() {
        return showAddButton;
    }

    public boolean isShowRequestContentButton() {
        return showRequestContentButton;
    }

    public int getSuccessRemovedItems() {
        return successRemovedItems;
    }

    public void setSuccessRemovedItems(int successRemovedItems) {
        this.successRemovedItems = successRemovedItems;
    }

    public List<String> getFailedToRemoveItemsMessages() {
        return failedToRemoveItemsMessages;
    }

    public void setFailedToRemoveItemsMessages(List<String> failedToRemoveItemsMessages) {
        this.failedToRemoveItemsMessages = failedToRemoveItemsMessages;
    }

    public boolean isFromRemoveItems() {
        return fromRemoveItems;
    }

    public void setFromRemoveItems(boolean fromRemoveItems) {
        this.fromRemoveItems = fromRemoveItems;
    }

    public List<Site> getSites() {
        return sites;
    }

    public Integer getPresetFilterByOwnerSiteId() {
        return presetFilterByOwnerSiteId;
    }

    public void setPresetFilterByOwnerSiteId(Integer presetFilterByOwnerSiteId) {
        this.presetFilterByOwnerSiteId = presetFilterByOwnerSiteId;
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

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public String getSearchKeyByItemName() {
        return searchKeyByItemName;
    }

    public void setSearchKeyByItemName(String searchKeyByItemName) {
        this.searchKeyByItemName = searchKeyByItemName;
    }

    private Integer presetFilterByOwnerSiteId = -1;  // default value is any owner site.
    private Integer pageNumber = 1;
    private UserItemsSortType sortType = UserItemsSortType.NAME;
    private Boolean descending = false;
    private ItemType itemType = ItemType.ALL_ITEMS;
    private String searchKeyByItemName = null;

    private User user;
    private String infoText;
    private boolean showAddButton;
    private boolean showRequestContentButton;
    private int successRemovedItems;
    private List<String> failedToRemoveItemsMessages;
    private boolean fromRemoveItems = false;
    private List<Site> sites;
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private static final International international = ServiceLocator.getInternationStorage().get("manageItems", Locale.US);
    private static final Map<String, ItemType> itemNames = new HashMap<String, ItemType>();

}
