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

import com.shroggle.util.ServiceLocator;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 22.10.2008
 */
public class ServiceWithExecutePageTest {

    @Before
    public void before() {
        final MockWebContext contextMock = new MockWebContext();
        contextMock.setHttpServletRequest(new MockHttpServletRequest("", ""));
        ServiceLocator.setWebContextGetter(new MockWebContextGetter(contextMock));
    }

    @Test
    public void executePage() throws IOException, ServletException {
        service.executePage("ff");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void executePageWithNull() throws IOException, ServletException {
        service.executePage(null);
    }

    private final ServiceWithExecutePage service = new ServiceWithExecutePage();

}
