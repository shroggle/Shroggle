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

import com.shroggle.entity.Widget;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.render.RenderContext;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.ServiceLocator;
import com.shroggle.entity.DraftGallery;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class ShowGalleryNavigationService extends AbstractService {

    @RemoteMethod
    public String execute(
            final int widgetId, final int galleryId, final int pageId, final SiteShowOption siteShowOption)
            throws IOException, ServletException {
        final DraftGallery gallery = persistance.getDraftItem(galleryId);
        final Widget widgetGallery = persistance.getWidget(widgetId);
        final RenderContext context = createRenderContext(true);
        final ShowGalleryUtils showGalleryUtils = new ShowGalleryUtils(siteShowOption);
        return showGalleryUtils.createInternalNavigationHtml(gallery, widgetGallery, pageId, context).trim();
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
}