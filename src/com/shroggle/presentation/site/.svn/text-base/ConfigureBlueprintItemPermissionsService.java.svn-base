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
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.logic.widget.WidgetTitleGetter;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.presentation.site.WithWidgetTitle;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class, params = {})
public class ConfigureBlueprintItemPermissionsService extends AbstractService implements WithWidgetTitle {

    @SynchronizeByMethodParameter(
            entityClass = Widget.class)
    @RemoteMethod
    public void execute(final int widgetId) throws IOException, ServletException {
        widgetManager = new WidgetManager(widgetId);
        widgetTitle = new WidgetTitleGetter(widgetManager.getWidget());

        getContext().getHttpServletRequest().setAttribute("blueprintItemPermissionsService", this);
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public WidgetManager getWidgetLogic() {
        return widgetManager;
    }

    private WidgetTitle widgetTitle;
    private WidgetManager widgetManager;

}