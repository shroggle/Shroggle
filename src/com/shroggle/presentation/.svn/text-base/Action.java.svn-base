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

import com.shroggle.entity.ItemType;
import com.shroggle.presentation.site.render.RenderContext;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.validation.SimpleError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

public class Action implements ActionBean {

    public final void setContext(final ActionBeanContext actionBeanContext) {
        this.actionBeanContext = actionBeanContext;
    }

    public final ActionBeanContext getContext() {
        return actionBeanContext;
    }

    public final HttpServletRequest getHttpServletRequest() {
        return actionBeanContext.getRequest();
    }

    public final RenderContext createRenderContext(final boolean showFromSiteEditPage) {
        return new RenderContext(
                getContext().getRequest(),
                getContext().getResponse(),
                getContext().getServletContext(),
                new HashMap<ItemType, String>(),
                showFromSiteEditPage);
    }

    public void addValidationError(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        printWriter.close();
        getContext().getValidationErrors().add(throwable.getClass().getName(), new SimpleError(stringWriter.toString()));
    }

    public void addValidationError(final String message) {
        getContext().getValidationErrors().add("error", new SimpleError(message));
    }

    public void addValidationError(final String message, final String errorCode) {
        getContext().getValidationErrors().add(errorCode, new SimpleError(message));
    }

    public HttpSession getSession() {
        return getHttpServletRequest().getSession();
    }

    private ActionBeanContext actionBeanContext;

}
