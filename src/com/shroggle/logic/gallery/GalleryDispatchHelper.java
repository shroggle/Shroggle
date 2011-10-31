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
package com.shroggle.logic.gallery;

import com.shroggle.exception.UnknownGalleryDispatchTypeException;
import com.shroggle.exception.GalleryNotFoundException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.presentation.gallery.GalleryDispatchRequest;
import com.shroggle.presentation.gallery.ShowGalleryDataService;
import com.shroggle.presentation.site.render.RenderContext;
import com.shroggle.entity.Gallery;
import com.shroggle.entity.Widget;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.ServiceLocator;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
public class GalleryDispatchHelper {

    public String dispatch(final GalleryDispatchRequest request, final RenderContext context) throws IOException, ServletException {
        if (request.getGalleryDispatchType() == GalleryDispatchType.SHOW_GALLERY) {
            final Gallery gallery = persistance.getGalleryById(request.getGalleryId());

            if (gallery == null) {
                throw new GalleryNotFoundException("Cannot find gallery by Id given in dispatching request." +
                        " Id=" + request.getGalleryId());
            }

            final Widget widget = persistance.getWidget(request.getWidgetId());

            if (widget == null){
                throw new WidgetNotFoundException("Cannot find widget by Id given in dispatching request." +
                        " Id=" + request.getWidgetId());
            }


            return new ShowGalleryDataService().execute(request.getGalleryId(), request.getFilledFormId(),
                    request.getWidgetId(), request.getSiteShowOption()).getHtml();
        } else {
            throw new UnknownGalleryDispatchTypeException("Unknown blog dispatch type: " + request.getGalleryDispatchType());
        }
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}
