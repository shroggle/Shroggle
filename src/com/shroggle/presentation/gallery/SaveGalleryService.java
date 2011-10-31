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
import com.shroggle.logic.gallery.GalleryManager;
import com.shroggle.logic.gallery.SaveGalleryRequest;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.FunctionalWidgetInfo;
import com.shroggle.presentation.site.GetFunctionalWidgetsService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Artem Stasuk
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class SaveGalleryService extends AbstractService {

    @RemoteMethod
    public FunctionalWidgetInfo execute(final SaveGalleryRequest request) throws IOException, ServletException {
        final Integer formId = request.getGallerySave().getFormId();
        if (formId != null) {
            sessionStorage.setSortedFilledForms(getContext().getSession(), null, formId);
        }

        final GalleryManager galleryManager = GalleryManager.createInstance(request.getGalleryId(), null);
        galleryManager.saveInTransaction(request);

        // todo refact this in some logic
        final Widget widget;
        if (request.getWidgetGalleryId() != null) {
            widget = persistance.getWidget(request.getWidgetGalleryId());
        } else {
            widget = null;
        }

        return new GetFunctionalWidgetsService().createFunctionalWidgetInfo(widget, "widget", true);
    }

    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
