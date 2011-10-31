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
import com.shroggle.exception.MenuNameNotUniqueException;
import com.shroggle.exception.MenuNotFoundException;
import com.shroggle.exception.MenuStyleTypeNotSetException;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.FunctionalWidgetInfo;
import com.shroggle.presentation.site.GetFunctionalWidgetsService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.Synchronize;
import com.shroggle.util.process.synchronize.SynchronizeContext;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.SynchronizeRequestEntity;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.Locale;
import java.util.List;
import java.util.ArrayList;

@RemoteProxy
public class CreateMenuService extends AbstractService {

    @RemoteMethod
    public FunctionalWidgetInfo execute(final CreateMenuRequest request) throws Exception {
        final UserManager userManager = new UsersManager().getLogined();

        final WidgetItem widget;
        if (request.getWidgetId() != null) {
            widget = (WidgetItem) userManager.getRight().getSiteRight().getWidgetForEditInPresentationalService(
                    request.getWidgetId());
        } else {
            widget = null;
        }

        if (request.getMenuStyleType() == null) {
            throw new MenuStyleTypeNotSetException();
        }

        final SynchronizeRequestEntity synchronizeRequest = new SynchronizeRequestEntity(
                DraftMenu.class, SynchronizeMethod.WRITE, widget);
        synchronize.execute(
                synchronizeRequest, new SynchronizeContext<Void>() {
                    public Void execute() {
                        persistanceTransaction.execute(new Runnable() {

                            public void run() {
                                final DraftMenu menu = persistance.getDraftItem(request.getMenuId());
                                if (menu == null || menu.getSiteId() <= 0) {
                                    throw new MenuNotFoundException("Cannot find menu by Id=" + request.getMenuId());
                                }

                                final Site site;
                                if (widget != null) {
                                    site = widget.getSite();
                                } else {
                                    site = persistance.getSite(menu.getSiteId());
                                }

                                final DraftMenu duplicateMenu = persistance.getMenuByNameAndSiteId(request.getName(),
                                        site.getSiteId());
                                if (duplicateMenu != null && duplicateMenu != menu) {
                                    throw new MenuNameNotUniqueException(menuBundle.get("menuNameNotUnique"));
                                }

                                menu.setName(request.getName());
                                menu.setIncludePageTitle(request.isIncludePageTitle());
                                menu.setMenuStyleType(request.getMenuStyleType());
                                menu.setMenuStructure(request.getMenuStructure());

                                // Updating inclusion in menu.
                                persistance.setMenuItemsIncludeInMenu(selectItemsIdsByIncludeInMenuParameter(request.getIncludeInMenus(), true), true);
                                persistance.setMenuItemsIncludeInMenu(selectItemsIdsByIncludeInMenuParameter(request.getIncludeInMenus(), false), false);
                            }

                        });
                        return null;

                    }
                });

        return new GetFunctionalWidgetsService().createFunctionalWidgetInfo(widget, "widget", true);
    }

    private List<Integer> selectItemsIdsByIncludeInMenuParameter(final List<ItemIdIncludeInMenu> includeInMenus, final boolean included) {
        final List<Integer> ids = new ArrayList<Integer>();
        for (ItemIdIncludeInMenu includeInMenu : includeInMenus) {
            if (includeInMenu.isIncludeInMenu() == included) {
                ids.add(includeInMenu.getMenuItemId());
            }
        }
        return ids;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final International menuBundle = ServiceLocator.getInternationStorage().get("configureMenu", Locale.US);
    private final Synchronize synchronize = ServiceLocator.getSynchronize();

}