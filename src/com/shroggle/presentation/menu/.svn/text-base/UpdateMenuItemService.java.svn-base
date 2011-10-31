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

import com.shroggle.entity.Menu;
import com.shroggle.entity.MenuItem;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.logic.menu.MenuPageCheckSometimes;
import com.shroggle.logic.site.page.MenuPagesHtmlTextCreator;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class UpdateMenuItemService extends AbstractService {


    @RemoteMethod
    public UpdateMenuItemResponse execute(final UpdateMenuItemRequest request) throws Exception {
        final UpdateMenuItemResponse response = new UpdateMenuItemResponse();
        final Persistance persistance = ServiceLocator.getPersistance();
        final MenuItem menuItem = persistance.getDraftMenuItem(request.getSelectedMenuItemId());
        if (menuItem == null) {
            Logger.getLogger(this.getClass().getName()).warning("Can`t find menuItem by id = " + request.getSelectedMenuItemId());
            return response;
        }

        ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
            public void run() {
                menuItem.setName(request.getName());
                menuItem.setTitle(request.getTitle());
                menuItem.setCustomUrl(request.getCustomUrl());
                menuItem.setImageId(request.getImageId());
                menuItem.setUrlType(request.getItemUrlType());
                menuItem.setPageId(request.getPageId());
                menuItem.setDefaultPageId(null);
            }
        });
        final Menu menu = menuItem.getMenu();
        final MenuPagesHtmlTextCreator pagesHtmlTextCreator = new MenuPagesHtmlTextCreator(menu, new MenuPageCheckSometimes(menu), SiteShowOption.getDraftOption());
        response.setTreeHtml(pagesHtmlTextCreator.getHtml());
        if (!menu.getMenuItems().isEmpty()) {
            response.setInfoAboutSelectedItem(new GetInfoAboutItemService().execute(menuItem.getId()));
            response.setSelectedMenuItemId(menuItem.getId());
        }
        return response;
    }

}
