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
import com.shroggle.entity.MenuStructureType;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.logic.menu.MenuPageCheckSometimes;
import com.shroggle.logic.site.page.MenuPagesHtmlTextCreator;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.ArrayList;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class RemoveMenuItemService extends AbstractService {

    @RemoteMethod
    @SynchronizeByMethodParameter(
            entityClass = MenuItem.class)
    public UpdateMenuItemResponse execute(final Integer menuItemId) throws Exception {
        final UpdateMenuItemResponse response = new UpdateMenuItemResponse();
        final Persistance persistance = ServiceLocator.getPersistance();
        final MenuItem menuItem = persistance.getDraftMenuItem(menuItemId);
        if (menuItem == null) {
            return response;
        }
        final Menu menu = menuItem.getMenu();
        ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
            public void run() {
                // We should set menu structure to CUSTOM here
                menu.setMenuStructure(MenuStructureType.OWN);
                // Move children to parent
                for (MenuItem child : new ArrayList<MenuItem>(menuItem.getChildren())) {
                    child.setParent(menuItem.getParent());
                }
                persistance.removeMenuItem(menuItem);
            }
        });
        final MenuPagesHtmlTextCreator pagesHtmlTextCreator = new MenuPagesHtmlTextCreator(menu, new MenuPageCheckSometimes(menu), SiteShowOption.getDraftOption());

        response.setTreeHtml(pagesHtmlTextCreator.getHtml());
        if (!menu.getMenuItems().isEmpty()) {
            final int selectedMenuItemId = menu.getMenuItems().get(0).getId();
            response.setInfoAboutSelectedItem(new GetInfoAboutItemService().execute(selectedMenuItemId));
            response.setSelectedMenuItemId(selectedMenuItemId);
        }
        return response;
    }
}
