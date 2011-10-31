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
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.Resolution;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Stasuk Artem
 */
public class ResolutionMock implements Resolution {

    public Action getLoginInUserAction() {
        return loginInUserAction;
    }

    public void setLoginInUserAction(Action loginInUserAction) {
        this.loginInUserAction = loginInUserAction;
    }

    public void execute(
            final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
    }

    public Widget getShowWidgetPreviewWidget() {
        return showWidgetPreviewWidget;
    }

    public void setShowWidgetPreviewWidget(Widget showWidgetPreviewWidget) {
        this.showWidgetPreviewWidget = showWidgetPreviewWidget;
    }

    public ServletContext getShowWidgetPreviewServletContext() {
        return showWidgetPreviewServletContext;
    }

    public void setShowWidgetPreviewServletContext(ServletContext showWidgetPreviewServletContext) {
        this.showWidgetPreviewServletContext = showWidgetPreviewServletContext;
    }

    public Integer getShowWidgetPreviewVisitorId() {
        return showWidgetPreviewVisitorId;
    }

    public void setShowWidgetPreviewVisitorId(Integer showWidgetPreviewVisitorId) {
        this.showWidgetPreviewVisitorId = showWidgetPreviewVisitorId;
    }

    public void setForwardByUrl(final String forwardByUrl) {
        this.forwardByUrl = forwardByUrl;
    }

    public String getForwardToUrl() {
        return forwardByUrl;
    }

    public void setRedirectByAction(final Class<? extends ActionBean> actionClass) {
        this.redirectByAction = actionClass;
    }

    public Class<? extends ActionBean> getRedirectByAction() {
        return redirectByAction;
    }

    public void setRedirectByUrl(final String redirectByUrl) {
        this.redirectByUrl = redirectByUrl;
    }

    public String getRedirectByUrl() {
        return redirectByUrl;
    }

    public String getStreamData() {
        return streamData;
    }

    public String getStreamType() {
        return streamType;
    }

    public void setStreamType(final String streamType) {
        this.streamType = streamType;
    }

    public void setStreamData(final String streamData) {
        this.streamData = streamData;
    }

    public ResolutionParameter[] getRedirectByActionParameters() {
        return redirectByActionParameters;
    }

    public void setRedirectByActionParameters(final ResolutionParameter[] parameters) {
        this.redirectByActionParameters = parameters;
    }

    public boolean isNotFound() {
        return notFound;
    }

    public void setNotFound() {
        notFound = true;
    }

    public boolean isResourceDownlaod() {
        return resourceDownlaod;
    }

    public boolean isResourceGetter() {
        return resourceGetter;
    }

    public void setResourceGetter(boolean resourceGetter) {
        this.resourceGetter = resourceGetter;
    }

    public void setResourceDownlaod(boolean resourceDownlaod) {
        this.resourceDownlaod = resourceDownlaod;
    }

    private Integer showWidgetPreviewVisitorId;
    private ServletContext showWidgetPreviewServletContext;
    private Widget showWidgetPreviewWidget;
    private Action loginInUserAction;
    private Class<? extends ActionBean> redirectByAction;
    private ResolutionParameter[] redirectByActionParameters;
    private String redirectByUrl;
    private String forwardByUrl;
    private String streamType;
    private String streamData;
    private boolean notFound;
    private boolean resourceGetter;
    private boolean resourceDownlaod;

}