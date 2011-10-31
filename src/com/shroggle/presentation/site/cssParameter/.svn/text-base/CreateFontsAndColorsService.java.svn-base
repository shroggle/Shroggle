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

package com.shroggle.presentation.site.cssParameter;

import com.shroggle.entity.*;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.render.RenderCssParameter;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.PersistanceTransactionContext;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameterProperty;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class, params = {})
public class CreateFontsAndColorsService extends AbstractService {

    @SynchronizeByMethodParameterProperty(
            entityClass = Widget.class,
            entityIdPropertyPath = "widgetId",
            method = SynchronizeMethod.WRITE)
    @RemoteMethod
    public String executeForWidget(final CreateFontsAndColorsRequest request) throws IOException, ServletException {
        final UserManager userManager = new UsersManager().getLogined();

        final PageManager pageManager = persistanceTransaction.execute(new PersistanceTransactionContext<PageManager>() {
            @Override
            public PageManager execute() {
                if (request.getWidgetId() != null) {// Saving with widget (from Site Edit Page or from Dashboard).
                    final Widget widget = userManager.getRight().getSiteRight().getWidgetForEditInPresentationalService(
                            request.getWidgetId());
                    final WidgetManager widgetManager = new WidgetManager(widget);
                    if (request.isSaveCssInCurrentPlace() || widgetManager.getWidget().isWidgetComposit()) {
                        widgetManager.setFontsAndColorsInCurrentPlace(request);
                    } else {
                        widgetManager.setFontsAndColorsInAllPlaces(request);
                    }
                    return new PageManager(widget.getPage(), SiteShowOption.getDraftOption());
                } else if (request.getDraftItemId() != null) {// Saving with draftItem (from Manage Items).
                    final ItemManager itemManager = new ItemManager(request.getDraftItemId());
                    itemManager.updateFontsAndColors(request);
                    return null;
                }
                return null;
            }
        });
        return pageManager != null ? new RenderCssParameter(pageManager, SiteShowOption.ON_USER_PAGES).pageCssValuesToString(pageManager) : "";
    }


    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();

}