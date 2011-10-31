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

import com.shroggle.presentation.ActionTestUtil;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import net.sourceforge.stripes.mock.MockHttpSession;
import org.junit.Test;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 22.10.2008
 */
public class ContextCreatorHttpSessionTest {

    @Test(expected = UnsupportedOperationException.class)
    public void executeWithNullRequest() {
        ContextCreator contextCreator = new ContextCreatorHttpSession();
        contextCreator.create(null);
    }

    @Test
    public void execute() {
        ContextCreator contextCreator = new ContextCreatorHttpSession();
        final MockHttpServletRequest requestMock = new MockHttpServletRequest("", "");
        requestMock.setSession(new MockHttpSession(ActionTestUtil.createServletContext()));
        Context context = contextCreator.create(requestMock);

        Assert.assertNotNull(context);
    }

}