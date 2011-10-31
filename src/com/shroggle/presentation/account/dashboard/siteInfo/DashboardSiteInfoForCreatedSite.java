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
package com.shroggle.presentation.account.dashboard.siteInfo;

import com.shroggle.entity.*;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.site.item.ItemTypeManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.account.dashboard.DashboardSiteType;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.html.HtmlUtil;

import java.util.*;

/**
 * @author Balakirev Anatoliy
 */
public class DashboardSiteInfoForCreatedSite extends DashboardSiteInfo {

    private final SiteManager siteManager;

    private final DashboardSiteType siteType;

    private List<DashboardWidget> dashboardWidgets;

    DashboardSiteInfoForCreatedSite(Site site, final DashboardSiteType siteType) {
        if (site == null) {
            throw new IllegalArgumentException("Unable to create DashboardSiteInfo without Site.");
        }
        this.siteManager = new SiteManager(site);
        this.siteType = siteType;
    }

    DashboardSiteInfoForCreatedSite(Integer siteId, final DashboardSiteType siteType) {
        this(ServiceLocator.getPersistance().getSite(siteId), siteType);
    }

    public boolean isSiteCreated() {
        return siteManager != null;
    }

    public DashboardSiteType getSiteType() {
        return siteType;
    }

    public String getName() {
        return HtmlUtil.limitName(siteManager.getName(), 35);
    }

    public String getLastUpdatedDate() {
        return DateUtil.toCommonDateStr(siteManager.getLastUpdatedDate());
    }

    public String getUrl() {
        return siteManager.getPublicUrl();
    }

    public String getLimitedUrl() {
        return HtmlUtil.limitName(siteManager.getPublicUrl(), 35);
    }

    public boolean isActive() {
        return siteManager.getSiteStatus() == SiteStatus.ACTIVE;
    }

    public int getSiteId() {
        return siteManager.getSite().getSiteId();
    }

    public boolean canBeDeactivated() {
        return hasAdminsRightOnSite() && isActive();
    }

    public boolean hasAdminsRightOnSite() {
        final UserOnSiteRight userOnSiteRight = new UserRightManager(new UsersManager().getLogined().getUser()).toSite(siteManager.getSite());
        return userOnSiteRight != null && userOnSiteRight.getSiteAccessType() == SiteAccessLevel.ADMINISTRATOR;
    }

    public int getFirstSitePageId() {
        final Page firstPage = siteManager.getFirstSitePage();
        return firstPage != null ? firstPage.getPageId() : -1;
    }

    public boolean isEditable() {
        final UserOnSiteRight right = new UsersManager().getLogined().getRight().toSite(siteManager.getSite());
        return right != null && Arrays.asList(SiteAccessLevel.getUserAccessLevels()).contains(right.getSiteAccessType());
    }

    public boolean isBlueprint() {
        return siteType.isBlueprint();
    }

    @Override
    public boolean isPublishedBlueprint() {
        return siteType == DashboardSiteType.PUBLISHED_BLUEPRINT;
    }

    @Override
    public boolean isActivatedBlueprint() {
        return siteType == DashboardSiteType.ACTIVATED_BLUEPRINT;
    }

    @Override
    public boolean hasBeenPublished() {
        return siteManager.getPublicBlueprintsSettings().getPublished() != null;
    }

    /*We can`t just check for existence of ChildSiteSettings or ChildSiteRegistration in site, for isChildSite() and isNetworkSite() methods
because site can be shown more than once on a dashboard like common site and like network, or like network and child.
So I`ve added this method to know in which state site should shown now. Tolik*/

    public boolean isNetworkSite() {
        return siteType == DashboardSiteType.NETWORK;
    }

    public boolean isChildSite() {
        return siteType == DashboardSiteType.CHILD;
    }

    public String getHisNetworkName() {
        return siteManager.getHisNetworkName();
    }

    public List<DashboardWidget> getDashboardWidgets() {
        if (dashboardWidgets != null) {
            return dashboardWidgets;
        }

        dashboardWidgets = new ArrayList<DashboardWidget>();
        final Set<Integer> usedItemsId = new HashSet<Integer>();
        final Set<ItemType> notAllowedForDashboardTypes = new HashSet<ItemType>(
                Arrays.asList(ItemType.IMAGE, ItemType.TEXT, ItemType.VIDEO));
        for (PageManager pageManager : siteManager.getPages()) {// Adding items with widgets
            for (final Widget widget : pageManager.getDraftPageSettings().getWidgets()) {
                if (widget.isWidgetItem() && !notAllowedForDashboardTypes.contains(widget.getItemType())) {
                    final DashboardWidget dashboardWidget = new DashboardWidget((WidgetItem) widget);
                    dashboardWidgets.add(dashboardWidget);
                    usedItemsId.add(dashboardWidget.getItemId());
                }
            }
        }

        final List<ItemManager> itemManagers = ItemManager.siteItemsToManagers(
                ServiceLocator.getPersistance().getDraftItemsBySiteId(
                        siteManager.getSiteId(), ItemType.ALL_ITEMS,
                        notAllowedForDashboardTypes.toArray(new ItemType[3])));
        for (final ItemManager itemManager : itemManagers) {// Adding items without widgets
            if (usedItemsId.add(itemManager.getId())) {
                dashboardWidgets.add(new DashboardWidget(itemManager));
            }
        }

        Collections.sort(dashboardWidgets);
        return dashboardWidgets;
    }

    public static class DashboardWidget implements Comparable<DashboardWidget> {

        public DashboardWidget(WidgetItem widgetItem) {
            this.widgetId = widgetItem.getWidgetId();
            this.pageName = widgetItem.getPageSettings().getName();
            this.itemManager = new ItemManager(widgetItem.getDraftItem());
            work = itemManager.getWorkItem() != null;
        }

        public DashboardWidget(ItemManager itemManager) {
            this.widgetId = null;
            this.pageName = null;
            this.itemManager = itemManager;
            work = itemManager.getWorkItem() != null;
        }

        public boolean isDraft() {
            return !work;
        }

        public Integer getWidgetId() {
            return widgetId;
        }

        public ItemType getItemType() {
            if (itemType != null) {
                return itemType;
            } else {
                itemType = itemManager.getItemTypeConsiderGalleryType();
                return itemType;
            }
        }

        public int getItemId() {
            return itemManager.getId();
        }

        public String getItemName() {
            if (itemName != null) {
                return itemName;
            } else {
                if (isDraft()) {
                    itemName = StringUtil.getEmptyOrString(itemManager.getDraftItem().getName());
                } else {
                    itemName = StringUtil.getEmptyOrString(itemManager.getWorkItem().getName());
                }
                return itemName;
            }
        }

        public String getPageName() {
            return pageName;
        }

        public int compareTo(DashboardWidget dashboardWidget) {
            final String pageNameWithItemType1 = ((this.getPageName() != null ? this.getPageName() + " / " : "") +
                    new ItemTypeManager(this.getItemType()).getItemName() + ": " + this.getItemName()).toLowerCase();

            final String pageNameWithItemType2 = ((dashboardWidget.getPageName() != null ? dashboardWidget.getPageName() +
                    " / " : "") + new ItemTypeManager(dashboardWidget.getItemType()).getItemName() + ": " +
                    dashboardWidget.getItemName()).toLowerCase();

            return pageNameWithItemType1.compareTo(pageNameWithItemType2);
        }

        private ItemType itemType = null;
        private String itemName = null;

        private final boolean work;
        private final Integer widgetId;
        private final String pageName;
        private final ItemManager itemManager;
    }

    @Override
    public boolean isSelected(Integer selectedSiteInfoHashCode) {
        return selectedSiteInfoHashCode != null && selectedSiteInfoHashCode.equals(hashCode());
    }

    @Override
    public int getChildSiteSettingsId() {
        return -1;// It`s correct implementation for this class. Tolik
    }

    /*--------------------------------------------Not implemented methods---------------------------------------------*/

    @Override
    public int getParentSiteId() {
        throw new UnsupportedOperationException("This method can`t be implemented in this class!");
    }

    @Override
    public String getCreatedDateAsString() {
        throw new UnsupportedOperationException("This method can`t be implemented in this class!");
    }
    /*--------------------------------------------Not implemented methods---------------------------------------------*/
}
