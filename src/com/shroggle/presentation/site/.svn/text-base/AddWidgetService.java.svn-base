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

import com.shroggle.entity.*;
import com.shroggle.logic.site.item.ItemCreator;
import com.shroggle.logic.site.item.ItemCreatorRequest;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetComparator;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.PersistanceTransactionContext;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameterProperties;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameterProperty;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Create witget with specify type and set on page version in position
 *
 * @author Stasuk Artem
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class AddWidgetService extends AbstractService {

    @SynchronizeByMethodParameterProperties({
            @SynchronizeByMethodParameterProperty(
                    entityIdPropertyPath = "widgetId",
                    entityClass = Widget.class,
                    method = SynchronizeMethod.WRITE)})
    @RemoteMethod
    public FunctionalWidgetInfo execute(final AddWidgetRequest request) throws ServletException, IOException {
        new UsersManager().getLogined();

        final WidgetComposit widgetComposit;
        final PageManager pageManager;
        if (request.getWidgetId() != null) {
            final Widget widget1 = persistance.getWidget(request.getWidgetId());
            if (widget1 == null || !widget1.isWidgetComposit()) {
                return null;
            }
            widgetComposit = (WidgetComposit) widget1;
            pageManager = new PageManager(widgetComposit.getPage());
        } else {
            return null;
        }

        final Widget widget = persistanceTransaction.execute(new PersistanceTransactionContext<Widget>() {

            @Override
            public Widget execute() {
                final Widget widget = new WidgetItem();

                final List<Widget> childs = new ArrayList<Widget>(widgetComposit.getChilds());
                Collections.sort(childs, WidgetComparator.INSTANCE);
                if (childs.size() > 0) {
                    widget.setPosition(childs.get(childs.size() - 1).getPosition() + 1);
                }
                pageManager.setChanged(true);
                widgetComposit.addChild(widget);
                persistance.putWidget(widget);
                pageManager.addWidget(widget);

                if (widget.isWidgetItem()) {
                    final DraftItem draftItem = ItemCreator.create(new ItemCreatorRequest(request.getItemId(),
                            request.isCopyContent(), request.getItemType(), widget.getSite()));
                    ((WidgetItem) widget).setDraftItem(draftItem);
                }
                return widget;
            }

        });

        return new GetFunctionalWidgetsService().createFunctionalWidgetInfo(widget, "widget", true);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}