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
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stasuk Artem
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class GetWidgetByPageWithPositionService extends AbstractService {

    @SynchronizeByMethodParameter(
            entityIdParameterIndex = 0,
            entityClass = Page.class)
    @RemoteMethod
    public List<FunctionalWidgetInfo> execute(final int pageId) throws IOException, ServletException {
        final List<FunctionalWidgetInfo> widgetInfos = new ArrayList<FunctionalWidgetInfo>();
        new UsersManager().getLogined();
        final PageManager pageManager = PageManager.getPageForRenderOrLoginPage(pageId, SiteShowOption.INSIDE_APP);
        if (pageManager == null) {
            return null;
        }

        final List<Widget> widgets = pageManager.getWidgets();
        for (Widget widget : widgets) {
            if (widget.isWidgetComposit()) {
                final FunctionalWidgetInfo functionalWidgetInfo = new GetFunctionalWidgetsService().createFunctionalWidgetInfo(widget, "widget", false);

                for (Widget childWidget : ((WidgetComposit) widget).getChilds()) {
                    functionalWidgetInfo.addChildInfo(new GetFunctionalWidgetsService().createFunctionalWidgetInfo(childWidget, "widget", true));
                }

                widgetInfos.add(functionalWidgetInfo);
            }
        }

        return widgetInfos;
    }
}