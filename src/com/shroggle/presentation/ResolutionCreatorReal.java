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

package com.shroggle.presentation;

import com.shroggle.entity.Widget;
import com.shroggle.entity.ItemType;
import com.shroggle.presentation.site.LoginInUserResolution;
import com.shroggle.presentation.site.ShowWidgetPreviewResolution;
import com.shroggle.util.ResolutionNotFound;
import com.shroggle.util.resource.ResourceGetterResolution;
import net.sourceforge.stripes.action.*;

import javax.servlet.ServletContext;
import java.util.Map;
import java.io.InputStream;

/**
 * @author Stasuk Artem
 */
public class ResolutionCreatorReal implements ResolutionCreator {

    @Override
    public Resolution redirectToAction(
            final Class<? extends ActionBean> actionClass,
            final ResolutionParameter... parameters) {
        final RedirectResolution redirectResolution = new RedirectResolution(actionClass);
        for (final ResolutionParameter parameter : parameters) {
            if (parameter != null) {
                redirectResolution.getParameters().put(parameter.getName(), parameter.getValue());
            }
        }
        return redirectResolution;
    }

    @Override
    public Resolution redirectToUrl(final String url, final ResolutionParameter... parameters) {
        final RedirectResolution redirectResolution = new RedirectResolution(url);
        for (final ResolutionParameter parameter : parameters) {
            if (parameter != null) {
                redirectResolution.getParameters().put(parameter.getName(), parameter.getValue());
            }
        }
        return redirectResolution;
    }

    @Override
    public Resolution stream(final String type, final String data) {
        return new StreamingResolution(type, data);
    }

    @Override
    public Resolution forwardToUrl(final String url) {
        return new ForwardResolution(url);
    }

    @Override
    public Resolution showWidgetPreview(
            final Widget widget, final ServletContext servletContext, final Integer visitorId, final Map<ItemType, String> parameterMap) {
        return new ShowWidgetPreviewResolution(widget, servletContext, parameterMap);
    }

    @Override
    public Resolution loginInUser(final Action action) {
        return new LoginInUserResolution(action);
    }

    @Override
    public Resolution notFound() {
        return new ResolutionNotFound();
    }

    @Override
    public Resolution resourceGetter(final String extension, final InputStream inputStream, String fileName) {
        return new ResourceGetterResolution(extension, inputStream).setFilename(fileName).setAttachment(false);
    }

    @Override
    public Resolution resourceDownload(final InputStream inputStream, final String name) {
        return new ResourceGetterResolution(inputStream).setFilename(name);
    }

}
