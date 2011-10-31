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
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class CreateItemSizeService { // todo. Add tests.

    @SynchronizeByMethodParameter(
            entityClass = Widget.class)
    @RemoteMethod
    public void execute(final CreateItemSizeRequest request) throws IOException, ServletException {
        final UserManager userManager = new UsersManager().getLogined();

        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                if (request.getWidgetId() != null) {// Saving with widget (from Site Edit Page or from Dashboard).
                    final Widget widget = userManager.getRight().getSiteRight().getWidgetForEdit(request.getWidgetId());
                    final WidgetManager widgetManager = new WidgetManager(widget);
                    if (request.isSaveItemSizeInCurrentPlace()) {
                        widgetManager.setItemSizeInCurrentPlace(request.getItemSize());
                    } else {
                        widgetManager.setItemSizeInAllPlaces(request.getItemSize());
                    }
                } else if (request.getDraftItemId() != null) {// Saving with draftItem (from Manage Items).
                    new ItemManager(request.getDraftItemId()).updateItemSize(request.getItemSize());
                }
            }

        });
    }

    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}
