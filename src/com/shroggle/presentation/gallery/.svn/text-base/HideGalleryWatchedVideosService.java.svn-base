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
package com.shroggle.presentation.gallery;

import com.shroggle.entity.DraftGallery;
import com.shroggle.entity.WidgetItem;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.render.RenderContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.ContextStorage;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Artem Stasuk
 */
@RemoteProxy
public class HideGalleryWatchedVideosService extends AbstractService {

    @RemoteMethod
    public HideGalleryWatchedVideosResponse execute(
            final HideGalleryWatchedVideosRequest request)
            throws IOException, ServletException {

        contextStorage.get().setHideWatchedVideos(request.getGalleryId(), request.isHide());

        final DraftGallery gallery = persistance.getDraftItem(request.getGalleryId());
        final WidgetItem widgetGallery = (WidgetItem) persistance.getWidget(request.getWidgetId());
        final RenderContext context = createRenderContext(true);

        final HideGalleryWatchedVideosResponse response = new HideGalleryWatchedVideosResponse();
        final ShowGalleryUtils showGalleryUtils = new ShowGalleryUtils(request.getSiteShowOption());
        response.setNavigationHtml(showGalleryUtils.createInternalNavigationHtml(
                gallery, widgetGallery, 1, context).trim());
        return response;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ContextStorage contextStorage = ServiceLocator.getContextStorage();

}