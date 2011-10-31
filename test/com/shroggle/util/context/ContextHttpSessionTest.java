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
import net.sourceforge.stripes.mock.MockHttpSession;
import org.junit.Test;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 22.10.2008
 */
public class ContextHttpSessionTest {

    @Test(expected = UnsupportedOperationException.class)
    public void createWithNullHttpSession() {
        new ContextHttpSession(null);
    }

    @Test
    public void create() {
        new ContextHttpSession(createSessionMock());
    }

    @Test
    public void getUserId() {
        Context context = new ContextHttpSession(createSessionMock());
        Assert.assertNull(context.getUserId());
    }

    @Test
    public void getUserIdNotNull() {
        final MockHttpSession sessionMock = createSessionMock();
        Context context = new ContextHttpSession(sessionMock);
        context.setUserId(12);
        Assert.assertEquals(new Integer(12), context.getUserId());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getUserIdWithNullSession() {
        final MockHttpSession sessionMock = createSessionMock();
        Context context = new ContextHttpSession(sessionMock);
        context.destroy();
        context.getUserId();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setUserIdWithNullSession() {
        final MockHttpSession sessionMock = createSessionMock();
        Context context = new ContextHttpSession(sessionMock);
        context.destroy();
        context.setUserId(9);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getUserIdWithDestroyContext() {
        final MockHttpSession sessionMock = createSessionMock();
        Context context = new ContextHttpSession(sessionMock);
        context.destroy();
        context.getUserId();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setUserIdWithDestroyContext() {
        final MockHttpSession sessionMock = createSessionMock();
        Context context = new ContextHttpSession(sessionMock);
        context.destroy();
        context.setUserId(11);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setNoBotCodeWithDestroyContext() {
        final MockHttpSession sessionMock = createSessionMock();
        Context context = new ContextHttpSession(sessionMock);
        context.destroy();
        context.setNoBotCode("a", "f");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getNoBotCodeWithDestroyContext() {
        final MockHttpSession sessionMock = createSessionMock();
        Context context = new ContextHttpSession(sessionMock);
        context.destroy();
        context.getNoBotCode("a");
    }

    public void getNoBotCode() {
        final MockHttpSession sessionMock = createSessionMock();
        Context context = new ContextHttpSession(sessionMock);
        Assert.assertNull(context.getNoBotCode("a"));
        context.setNoBotCode("a", "2");
        Assert.assertEquals("a", context.getNoBotCode("a"));
        Assert.assertNull("a", context.getNoBotCode("b"));
        context.setNoBotCode("f", null);
    }

    public void setNoBotCodeWithNullPrefix() {
        final MockHttpSession sessionMock = createSessionMock();
        Context context = new ContextHttpSession(sessionMock);
        Assert.assertNull(context.getNoBotCode("a"));
        context.setNoBotCode(null, "2");
        Assert.assertEquals("2", context.getNoBotCode(null));
    }

    private MockHttpSession createSessionMock() {
        return new MockHttpSession(ActionTestUtil.createServletContext());
    }

}
