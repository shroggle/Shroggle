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

/**
 * @author Stasuk Artem
 */
public class MockWebContextGetter implements WebContextGetter {

    public MockWebContextGetter(WebContext webContext) {
        this.webContext = webContext;
    }

    public WebContext get() {
        return webContext;
    }

    private WebContext webContext;

}
