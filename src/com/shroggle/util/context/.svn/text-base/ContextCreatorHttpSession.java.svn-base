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
package com.shroggle.util.context;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 17.10.2008
 */
public class ContextCreatorHttpSession implements ContextCreator {

    public Context create(final HttpServletRequest request) {
        if (request == null) {
            throw new UnsupportedOperationException(
                    "Can't create content by null request!");
        }
        return new ContextHttpSession(request.getSession());
    }

}
