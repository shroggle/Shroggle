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

package com.shroggle.presentation.site.forms;

import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.RemoteMethod;
import com.shroggle.presentation.site.render.RenderContext;
import com.shroggle.presentation.site.render.RenderFormPageBreak;
import com.shroggle.presentation.site.forms.ShowFormPageRequest;
import com.shroggle.presentation.AbstractService;
import com.shroggle.entity.ItemType;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class ShowFormPageService extends AbstractService {

    @RemoteMethod
    public String execute(final ShowFormPageRequest request) throws IOException, ServletException {
        if (request.getWidgetId() != 0) {
            final RenderContext context = createRenderContext(false);

            return new RenderFormPageBreak().execute(request.getWidgetId(), request.getFormId(), request.getPageBreaksToPass(),
                    request.getFilledFormToUpdateId(), request.getAdditionalParameters(), context);
        } else {
            return new RenderFormPageBreak().executeForAddRecord(request.getItemType(), request.getPageBreaksToPass(),
                    request.getFilledFormToUpdateId(), request.getFormId(), request.getAdditionalParameters());
        }
    }

    @RemoteMethod
    public String reset(final int widgetId, final ItemType type, final int formId) throws IOException, ServletException {
        if (widgetId != 0) {
            final RenderContext context = createRenderContext(false);

            return new RenderFormPageBreak().reset(widgetId, formId, context);
        } else {
            return new RenderFormPageBreak().resetForAddRecord(type, formId);
        }
    }

}
