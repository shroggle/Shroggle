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
package com.shroggle.logic.menu;

import com.shroggle.entity.*;
import com.shroggle.exception.MenuItemNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.resource.provider.ResourceGetterType;

/**
 * @author Balakirev Anatoliy
 */
public class MenuItemManager {

    public MenuItemManager(MenuItem menuItem, final SiteShowOption siteShowOption) {
        if (menuItem == null) {
            throw new MenuItemNotFoundException("Can`t create MenuItemManager without MenuItem");
        }
        this.menuItem = menuItem;


        final Page menuPage = ServiceLocator.getPersistance().getPage(menuItem.getPageId());
        if (menuPage == null) {
            this.pageManager = null;
        } else {
            this.pageManager = new PageManager(menuPage, siteShowOption);
        }

        final MenuImage menuImage = ServiceLocator.getPersistance().getMenuImageById(menuItem.getImageId());
        imageExist = ServiceLocator.getFileSystem().isResourceExist(menuImage);
    }

    public MenuItemData createMenuItemData(final int currentPageId, final int level) {
        final MenuItemData data = new MenuItemData();
        data.setName(this.getName());
        data.setDescription(this.getTitle());
        data.setHref(this.getUrl());
        data.setImageUrl(getImageUrl());
        data.setShowImage(false);
        if (this.menuItem.getUrlType() == MenuUrlType.SITE_PAGE && this.menuItem.getPageId() != null &&
                this.menuItem.getPageId().intValue() == currentPageId) {
            data.setSelected(true);
        } else {
            data.setSelected(false);
        }
        data.setLevel(level);
        data.setExternalUrl(this.menuItem.getUrlType() == MenuUrlType.CUSTOM_URL);
        return data;
    }

    public String getName() {
        if (menuItem.getName() != null) {
            return menuItem.getName();
        }

        return pageManager != null ? pageManager.getName() : "";
    }

    public String getTitle() {
        if (menuItem.getTitle() != null) {
            return menuItem.getTitle();
        }

        return pageManager != null ? pageManager.getTitle() : "";
    }

    /**
     * Use this method for forming link ("href" parameter in tag <a>) on this menu item.
     *
     * @return - correctly formed link for this menuItem.
     */
    public String getUrl() {
        final MenuUrlType type = getMenuUrlType();
        switch (type) {
            case CUSTOM_URL: {
                if (getCustomUrl().contains("://")) {
                    return getCustomUrl();
                } else {
                    return "http://" + getCustomUrl();
                }
            }
            case SITE_PAGE: {
                return pageManager.getUrl();
            }
            default: {
                throw new IllegalArgumentException("Unknown MenuUrlType = " + type);
            }
        }
    }

    public MenuUrlType getMenuUrlType() {
        return menuItem.getUrlType();
    }

    /**
     * This method should be used only for settings.
     * If you need url linked to this menuItem you should use "get" method.
     *
     * @return - saved customUrl.
     */
    public String getCustomUrl() {
        return StringUtil.getEmptyOrString(menuItem.getCustomUrl());
    }

    public Integer getImageId() {
        if (imageExist) {
            return menuItem.getImageId();
        } else {
            return null;
        }
    }

    public String getThumbnailImageUrl() {
        if (imageExist) {
            return ResourceGetterType.MENU_IMAGE.getUrl(menuItem.getImageId());
        } else {
            return null;
        }
    }

    public String getImageUrl() {
        if (imageExist) {
            return ResourceGetterType.MENU_IMAGE.getUrl(menuItem.getImageId());
        } else {
            return "";
        }
    }

    public boolean isImageExist() {
        return imageExist;
    }

    private final com.shroggle.entity.MenuItem menuItem;
    private final boolean imageExist;
    private final PageManager pageManager;

}
