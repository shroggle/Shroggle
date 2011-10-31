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

import com.shroggle.entity.SiteShowOption;
import com.shroggle.entity.Widget;
import com.shroggle.entity.WidgetItem;
import com.shroggle.logic.gallery.GalleriesManager;
import com.shroggle.logic.gallery.GalleryData;
import com.shroggle.logic.gallery.GalleryManager;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.util.ServiceLocator;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Artem Stasuk
 */
@RemoteProxy
public class ShowGalleryDataService extends ServiceWithExecutePage {

    @RemoteMethod
    public ShowGalleryDataResponse execute(
            final int galleryId, final int filledFormId, final int widgetId,
            final SiteShowOption siteShowOption) throws IOException, ServletException {

        final GalleryManager gallery = new GalleriesManager().get(galleryId);
        final Widget widget = ServiceLocator.getPersistance().getWidget(widgetId);
        final ShowGalleryUtils showGalleryUtils = new ShowGalleryUtils(siteShowOption);
        final GalleryData galleryData = new GalleryData(gallery.getEntity(), widget, filledFormId, siteShowOption);


        final ShowGalleryDataResponse response = new ShowGalleryDataResponse();
        final String galleryDataHtml = showGalleryUtils.createDataHtml(galleryData, (WidgetItem)widget, createRenderContext(false));
        response.setHtml(galleryDataHtml);
        return response;
    }

}
