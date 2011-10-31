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


package com.shroggle.presentation.site.borderBackground;

import com.shroggle.entity.*;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.render.RenderTheme;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.PersistanceTransactionContext;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByClassProperty;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

@RemoteProxy
public class CreateBorderService extends AbstractService {

    @SynchronizeByClassProperty(
            entityClass = Border.class,
            entityIdFieldPath = "borderBackgroundId",
            method = SynchronizeMethod.WRITE)
    @RemoteMethod
    public String executeForWidget(final Integer widgetId, final Integer draftItemId, final Border border, final boolean saveBorderInCurrentPlace) throws ServletException, IOException {
        new UsersManager().getLogined();

        final Widget widget = persistanceTransaction.execute(new PersistanceTransactionContext<Widget>() {
            @Override
            public Widget execute() {
                if (widgetId != null) {// Saving with widget (from Site Edit Page or from Dashboard).
                    final Widget widget = persistance.getWidget(widgetId);
                    if (widget == null) {
                        return null;
                    }
                    if (saveBorderInCurrentPlace || widget.isWidgetComposit()) {
                        new WidgetManager(widget).setBorderInCurrentPlace(border);
                    } else {
                        new WidgetManager(widget).setBorderInAllPlaces(border);
                    }
                    if (widget.getDraftPageSettings() != null) {
                        widget.getDraftPageSettings().setChanged(true);
                    }
                    return widget;
                } else if (draftItemId != null) {// Saving with draftItem (from Manage Items).
                    new ItemManager(draftItemId).updateBorder(border);
                }
                return null;
            }
        });

        String widgetBorder = "";
        if (widget != null) {
            widgetBorder = new RenderTheme(new PageManager(widget.getPage()),
                    SiteShowOption.getDraftOption()).createBorderBackgroundStyle(true);
        }
        return widgetBorder;
    }

    private Persistance persistance = ServiceLocator.getPersistance();
    private PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
}
