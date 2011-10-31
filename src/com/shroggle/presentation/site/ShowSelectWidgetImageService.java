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

package com.shroggle.presentation.site;

import com.shroggle.presentation.AbstractService;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

@RemoteProxy
public class ShowSelectWidgetImageService extends AbstractService {

    @RemoteMethod
    public String execute(
            final int widgetId)
            throws IOException, ServletException {
        return getContext().forwardToString(
                "/action/site/showSelectWidgetImage.action?widgetId=" +
                        widgetId);

    }

}