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

package com.shroggle.presentation.menu;

import com.shroggle.entity.*;
import com.shroggle.exception.MenuNotFoundException;
import com.shroggle.logic.menu.*;
import com.shroggle.logic.site.page.MenuPagesHtmlTextCreator;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.logic.widget.WidgetTitleGetter;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.WithWidgetTitle;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;


@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class ConfigureMenuService extends AbstractService implements WithWidgetTitle {

    @SynchronizeByMethodParameter(entityClass = Widget.class)
    @RemoteMethod
    public void execute(final Integer widgetId, Integer menuId) throws IOException, ServletException {
        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();

        if (widgetId == null && menuId == null) {
            throw new MenuNotFoundException("Both widgetId and menuId cannot be null. " +
                    "This service is only for configuring existing menus.");
        }

        if (widgetId == null) {
            menu = persistance.getDraftItem(menuId);

            if (menu == null) {
                throw new MenuNotFoundException("Cannot find tell friend by Id=" + menuId);
            }

            site = persistance.getSite(menu.getSiteId());
        } else {
            widget = (WidgetItem) userRightManager.getSiteRight().getWidgetForEditInPresentationalService(
                    widgetId);
            site = widget.getSite();

            widgetTitle = new WidgetTitleGetter(widget);

            if (widget.getDraftItem() != null) {
                menu = (DraftMenu) widget.getDraftItem();
            } else {
                throw new MenuNotFoundException("Seems like widget with Id= " + widgetId + " got no item.");
            }
        }

        final MenuPagesHtmlTextCreator pagesHtmlTextCreator = new MenuPagesHtmlTextCreator(menu,
                new MenuPageCheckSometimes(menu), SiteShowOption.getDraftOption());
        menuTreeHtml = pagesHtmlTextCreator.getHtml();

        final InfoAboutMenuItemResponse infoAboutMenuItem = MenuItemsManager.getInfoAboutMenuItems(menu.getMenuItems().get(0));
        pages = infoAboutMenuItem.getPages();
        selectedItem = infoAboutMenuItem.getSelectedMenuItem();

        getContext().getHttpServletRequest().setAttribute("menuService", this);
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public DraftMenu getMenu() {
        return menu;
    }

    public String getMenuTreeHtml() {
        return menuTreeHtml;
    }

    public List<Page> getPages() {
        return pages;
    }

    public MenuItem getSelectedItem() {
        return selectedItem;
    }

    public WidgetItem getWidget() {
        return widget;
    }

    public Site getSite() {
        return site;
    }

    private WidgetItem widget;
    private Site site;
    private DraftMenu menu;
    private String menuTreeHtml;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private WidgetTitle widgetTitle;
    private List<Page> pages;
    private MenuItem selectedItem;
}