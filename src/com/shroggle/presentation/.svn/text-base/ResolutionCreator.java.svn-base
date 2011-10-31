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
import com.shroggle.entity.ItemType;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.Resolution;

import javax.servlet.ServletContext;
import java.io.InputStream;
import java.util.Map;

/**
 * @author Stasuk Artem
 */
public interface ResolutionCreator {

    Resolution redirectToAction(
            Class<? extends ActionBean> actionClass,
            ResolutionParameter... parameters);

    Resolution redirectToUrl(String url, ResolutionParameter... parameters);

    Resolution stream(String type, String data);

    Resolution forwardToUrl(String url);

    Resolution showWidgetPreview(
            Widget widget, ServletContext servletContext,
            Integer visitorId, Map<ItemType, String> parameterMap);

    Resolution loginInUser(Action action);

    Resolution notFound();

    Resolution resourceGetter(String extension, InputStream inputStream, String fileName);

    Resolution resourceDownload(InputStream inputStream, String name);

}
