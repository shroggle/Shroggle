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
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.Resolution;

import javax.servlet.ServletContext;
import java.util.Map;
import java.io.InputStream;

/**
 * @author Stasuk Artem
 */
public class ResolutionCreatorMock implements ResolutionCreator {

    @Override
    public Resolution redirectToAction(
            final Class<? extends ActionBean> actionClass,
            final ResolutionParameter... parameters) {
        final ResolutionMock resolutionMock = new ResolutionMock();
        resolutionMock.setRedirectByAction(actionClass);
        resolutionMock.setRedirectByActionParameters(parameters);
        return resolutionMock;
    }

    @Override
    public Resolution redirectToUrl(String url, ResolutionParameter... parameters) {
        final ResolutionMock resolutionMock = new ResolutionMock();
        resolutionMock.setRedirectByUrl(url);
        return resolutionMock;
    }

    @Override
    public Resolution stream(final String type, final String data) {
        final ResolutionMock resolutionMock = new ResolutionMock();
        resolutionMock.setStreamData(data);
        resolutionMock.setStreamType(type);
        return resolutionMock;
    }

    @Override
    public Resolution forwardToUrl(final String url) {
        final ResolutionMock resolutionMock = new ResolutionMock();
        resolutionMock.setForwardByUrl(url);
        return resolutionMock;
    }

    @Override
    public Resolution showWidgetPreview(
            final Widget widget, final ServletContext servletContext, final Integer visitorId, final Map<ItemType, String> parameterMap) {
        ResolutionMock resolutionMock = new ResolutionMock();
        resolutionMock.setShowWidgetPreviewVisitorId(visitorId);
        resolutionMock.setShowWidgetPreviewServletContext(servletContext);
        resolutionMock.setShowWidgetPreviewWidget(widget);
        return resolutionMock;
    }

    @Override
    public Resolution loginInUser(final Action action) {
        ResolutionMock resolutionMock = new ResolutionMock();
        resolutionMock.setLoginInUserAction(action);
        return resolutionMock;
    }

    @Override
    public Resolution notFound() {
        final ResolutionMock resolutionMock = new ResolutionMock();
        resolutionMock.setNotFound();
        return resolutionMock;
    }

    @Override
    public Resolution resourceGetter(final String extension, final InputStream inputStream, String fileName) {
        final ResolutionMock resolutionMock = new ResolutionMock();
        resolutionMock.setResourceGetter(true);
        return resolutionMock;
    }

    @Override
    public Resolution resourceDownload(final InputStream inputStream, final String name) {
        final ResolutionMock resolutionMock = new ResolutionMock();
        resolutionMock.setResourceDownlaod(true);
        return resolutionMock;
    }

}
