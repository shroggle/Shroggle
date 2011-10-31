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

package com.shroggle.presentation.site;

import com.shroggle.entity.Widget;
import com.shroggle.entity.WidgetComposit;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.CollectionUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameterProperty;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stasuk Artem
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class MoveWidgetService {

    @SynchronizeByMethodParameterProperty(
            method = SynchronizeMethod.WRITE,
            entityIdPropertyPath = "widgetId",
            entityClass = Widget.class,
            deepParent = 1)
    @RemoteMethod
    public boolean execute(final MoveWidgetRequest request) {
        final UserManager userManager = new UsersManager().getLogined();

        final Widget moveWidget = userManager.getRight().getSiteRight().getWidgetForEdit(request.getWidgetId());
        final PageManager pageManager = new PageManager(moveWidget.getPage());

        final WidgetComposit moveWidgetParent = moveWidget.getParent();
        if (moveWidgetParent != null && request.getToWidgetId() == moveWidgetParent.getWidgetId()) {
            final List<Widget> widgets = new ArrayList<Widget>(moveWidgetParent.getChilds());
            CollectionUtil.move(moveWidget, widgets, request.getToWidgetPosition());

            persistanceTransaction.execute(new Runnable() {

                public void run() {
                    // resort position
                    reposition(widgets);
                    pageManager.setChanged(true);
                }

            });
        } else {
            // get old list widgets
            final List<Widget> widgets;
            if (moveWidgetParent != null) {
                widgets = new ArrayList<Widget>(moveWidgetParent.getChilds());
            } else {
                widgets = new ArrayList<Widget>();
                for (final Widget widget : pageManager.getWidgets()) {
                    if (widget.getParent() == null) {
                        widgets.add(widget);
                    }
                }
            }

            // delete from collections
            widgets.remove(moveWidget);
            final List<Widget> toWidgets;

            final WidgetComposit widgetComposit = (WidgetComposit)
                    persistance.getWidget(request.getToWidgetId());
            toWidgets = new ArrayList<Widget>(widgetComposit.getChilds());
            widgetComposit.addChild(moveWidget);

            // add to collection
            toWidgets.add(moveWidget);
            CollectionUtil.move(moveWidget, toWidgets, request.getToWidgetPosition());

            // delete from sources
            if (moveWidgetParent != null) {
                moveWidgetParent.removeChild(moveWidget);
            } else {
                pageManager.removeWidget(moveWidget);
            }

            persistanceTransaction.execute(new Runnable() {

                public void run() {
                    // resort position
                    reposition(widgets);
                    // resort position
                    reposition(toWidgets);
                    pageManager.setChanged(true);
                }

            });
        }

        //Durning user manipulations on site edit page some widgets may change floating state. So let's put those changes in db.
        persistanceTransaction.execute(new Runnable() {

            public void run() {
                for (CreateItemSizeRequest widgetsToChangeSize : request.getWidgetsToChangeSize()) {
                    final Widget widget = persistance.getWidget(widgetsToChangeSize.getWidgetId());
                    new WidgetManager(widget).setItemSizeInCurrentPlace(widgetsToChangeSize.getItemSize());
                }
            }

        });

        return true;
    }

    private static void reposition(final List<Widget> widgets) {
        for (int widgetPosition = 0; widgetPosition < widgets.size(); widgetPosition++) {
            widgets.get(widgetPosition).setPosition(widgetPosition);
        }
    }

    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();

}