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

import com.shroggle.entity.DraftText;
import com.shroggle.entity.Widget;
import com.shroggle.entity.WidgetItem;
import com.shroggle.exception.TextItemNotFoundException;
import com.shroggle.exception.TextLargeException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.html.HtmlUtil;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameterProperty;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Stasuk Artem, dmitry.solomadin
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class SaveTextService extends AbstractService {

    @SynchronizeByMethodParameterProperty(
            entityClass = Widget.class,
            method = SynchronizeMethod.WRITE,
            entityIdPropertyPath = "widgetId")
    @RemoteMethod
    public FunctionalWidgetInfo execute(final SaveTextRequest request) throws ServletException, IOException {
        final UserManager userManager = new UsersManager().getLogined();

        final WidgetItem widget;
        if (request.getWidgetId() != null) {
            widget = (WidgetItem) userManager.getRight().getSiteRight().getWidgetForEditInPresentationalService(
                    request.getWidgetId());
        } else {
            widget = null;
        }

        if (request.getWidgetText() == null) {
            request.setWidgetText("");
        }

        if (request.getWidgetText().length() > 500000) {
            throw new TextLargeException();
        }

        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                final DraftText draftText = persistance.getDraftItem(request.getTextItemId());
                if (draftText == null || draftText.getSiteId() <= 0) {
                    throw new TextItemNotFoundException("Cannot find text item by Id=" + request.getTextItemId());
                }

                draftText.setName(request.getName());
                draftText.setText(HtmlUtil.removeIeComments(request.getWidgetText()));
            }
        });

        return new GetFunctionalWidgetsService().createFunctionalWidgetInfo(widget, "widget", true);
    }

    private PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private Persistance persistance = ServiceLocator.getPersistance();

}