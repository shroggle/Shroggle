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

import org.directwebremoting.WebContext;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Stasuk Artem
 */
public class ServiceWithExecutePage extends AbstractService {

    protected final String executePage(final String pageUrl) throws IOException, ServletException {
        if (pageUrl == null) {
            throw new UnsupportedOperationException("Can't execute page with null url!");
        }
        final WebContext webContext = getContext();
        webContext.getHttpServletRequest().setAttribute("service", this);
        return webContext.forwardToString(pageUrl);
    }

}