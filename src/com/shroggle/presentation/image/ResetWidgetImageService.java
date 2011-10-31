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

package com.shroggle.presentation.image;

import com.shroggle.entity.*;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.GetFunctionalWidgetsService;
import com.shroggle.presentation.site.FunctionalWidgetInfo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.logic.user.UsersManager;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author Stasuk Artem
 */
@RemoteProxy
public class ResetWidgetImageService extends AbstractService {

    @SynchronizeByMethodParameter(entityClass = WidgetItem.class)
    public FunctionalWidgetInfo execute(final int widgetImageId) throws Exception {
        new UsersManager().getLogined();

        final Widget widget = persistance.getWidget(widgetImageId);
        if (widget == null) {
            throw new WidgetNotFoundException();
        }

        final WidgetItem widgetImage = (WidgetItem) widget;

        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                // Image with default settings.
                final DraftImage defaultImage = new DraftImage();
                defaultImage.setSiteId(widgetImage.getSiteId());
                persistance.putItem(defaultImage);
                widgetImage.setDraftItem(defaultImage);
            }

        });
        return new GetFunctionalWidgetsService().createFunctionalWidgetInfo(widgetImage, "widget", true);
    }

    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();

}