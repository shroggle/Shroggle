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

package com.shroggle.presentation.site.render.advancedSearch;

import com.shroggle.entity.DraftAdvancedSearch;
import com.shroggle.entity.Widget;
import com.shroggle.exception.AdvancedSearchNotFoundException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.ContextStorage;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class ResetAdvancedSearchService extends AbstractService {

    @RemoteMethod
    public String execute(final ResetAdvancedSearchRequest request) throws IOException, ServletException {
        final DraftAdvancedSearch advancedSearch = persistance.getDraftItem(request.getAdvancedSearchId());

        if (advancedSearch == null) {
            throw new AdvancedSearchNotFoundException("Cannot find advanced search by Id=" + request.getAdvancedSearchId());
        }

        contextStorage.get().removeAdvancedSearchRequest(request.getAdvancedSearchId());

        final Widget widget = persistance.getWidget(request.getWidgetId());

        if (widget == null) {
            throw new WidgetNotFoundException("Cannot find widget by Id=" + request.getWidgetId());
        }

        return RenderWidgetAdvancedSearch.renderWidgetAdvancedSearch(widget, request.getSiteShowOption(),
                createRenderContext(false));
    }

    private final ContextStorage contextStorage = ServiceLocator.getContextStorage();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
