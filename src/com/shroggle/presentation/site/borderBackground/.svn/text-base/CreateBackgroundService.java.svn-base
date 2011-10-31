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
import com.shroggle.logic.site.page.PageSelectTextCreator;
import com.shroggle.logic.site.page.PageTreeTextCreator;
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
public class CreateBackgroundService extends AbstractService {

    @SynchronizeByClassProperty(
            entityClass = Background.class,
            entityIdFieldPath = "backgroundId",
            method = SynchronizeMethod.WRITE)
    @RemoteMethod
    public UpdateBackgroundResponse executeForWidget(final Integer widgetId, final Integer draftItemId, final Background background, final boolean saveBackgroundInCurrentPlace) throws ServletException, IOException {
        new UsersManager().getLogined();

        final Widget widget = persistanceTransaction.execute(new PersistanceTransactionContext<Widget>() {
            @Override
            public Widget execute() {
                if (widgetId != null) {// Saving with widget (from Site Edit Page or from Dashboard).
                    final Widget widget = persistance.getWidget(widgetId);
                    if (widget == null) {
                        return null;
                    }
                    final WidgetManager widgetManager = new WidgetManager(widget);
                    if (saveBackgroundInCurrentPlace || widgetManager.getWidget().isWidgetComposit()) {
                        widgetManager.setBackgroundInCurrentPlace(background);
                    } else {
                        widgetManager.setBackgroundInAllPlaces(background);
                    }
                    if (widget.getDraftPageSettings() != null) {
                        widget.getDraftPageSettings().setChanged(true);
                    }
                    return widget;
                } else if (draftItemId != null) {// Saving with draftItem (from Manage Items).
                    persistanceTransaction.execute(new Runnable() {
                        @Override
                        public void run() {
                            new ItemManager(draftItemId).updateBackground(background);
                        }
                    });
                }
                return null;
            }
        });

        final UpdateBackgroundResponse response = new UpdateBackgroundResponse();
        if (widgetId != null) {
            response.setNewBorderBackgroundStyle(new RenderTheme(new PageManager(widget.getPage()), SiteShowOption.getDraftOption()).createBorderBackgroundStyle(true));
        }
        response.setInnerHTML("ok");
        return response;
    }

    @SynchronizeByClassProperty(
            entityClass = Background.class,
            entityIdFieldPath = "backgroundId",
            method = SynchronizeMethod.WRITE)
    @RemoteMethod
    public UpdateBackgroundResponse executeForPage(final int pageId, final Background background, final boolean applyToAllPages) throws ServletException, IOException {
        new UsersManager().getLogined();

        final Page page = persistance.getPage(pageId);
        if (page == null) {
            return null;
        }
        final PageManager pageManager = new PageManager(page);
        persistanceTransaction.execute(new Runnable() {
            @Override
            public void run() {
                pageManager.updateBackground(background, applyToAllPages);
                pageManager.setSaved(true);
            }
        });

        final UpdateBackgroundResponse response = new UpdateBackgroundResponse();
        response.setNewBorderBackgroundStyle(new RenderTheme(pageManager, SiteShowOption.getDraftOption()).createBorderBackgroundStyle(true));
        response.setInnerHTML("ok");
        response.setTreeHtml(new PageTreeTextCreator().execute(pageManager.getSiteId(), SiteShowOption.getDraftOption()));
        response.setPageSelectHtml(new PageSelectTextCreator().execute(pageManager.getSiteId(), SiteShowOption.getDraftOption()));
        return response;
    }

    private Persistance persistance = ServiceLocator.getPersistance();
    private PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
}